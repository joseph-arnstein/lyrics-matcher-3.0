package com.breakstuff.lyricsmatcher.services;

import android.util.Log;

import com.breakstuff.lyricsmatcher.Constants;
import com.breakstuff.lyricsmatcher.models.Song;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by AlinaAir on 7/6/16.
 */
public class LyricsApiServices {

    public static void findSong(String song, Callback callback) {
        String TAG = "LyricsApiServices";
        String api = "apikey";
        Log.v(TAG, song);

        OkHttpClient client = new OkHttpClient();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.LYRICS_BASE_URL).newBuilder();
        urlBuilder.setQueryParameter(Constants.LYRICS_QUERY_PARAMETER, song);
        urlBuilder.addQueryParameter(api, Constants.LYRICS_API_KEY);
        String url = urlBuilder.build().toString();
        Log.v(TAG, url);

        Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public ArrayList<Song> processResults(Response response) {
        ArrayList<Song> songs = new ArrayList<>();
        String TAG = "process results";


        try {
            String jsonData = response.body().string();
            if (response.isSuccessful()) {
                JSONObject wholeJSON = new JSONObject(jsonData);
                JSONArray arrayJSON = wholeJSON.getJSONObject("message").getJSONObject("body").getJSONArray("track_list");

                for (int i = 0; i < arrayJSON.length(); i++) {
                    JSONObject songsJSON = arrayJSON.getJSONObject(i);

                    String songName = songsJSON.getJSONObject("track").getString("track_name");
                    String bandName = songsJSON.getJSONObject("track").getString("artist_name");
                    String albumName = songsJSON.getJSONObject("track").getString("album_name");
                    String albumImg = songsJSON.getJSONObject("track").getString("album_coverart_500x500");
                    if (albumImg.equals("")){
                        albumImg = songsJSON.getJSONObject("track").getString("album_coverart_350x350");
                    }
                    if (albumImg.equals("")){
                        albumImg = songsJSON.getJSONObject("track").getString("album_coverart_100x100");
                    }
                    String spotifyId = songsJSON.getJSONObject("track").getString("track_spotify_id");

                    Song song = new Song(songName, bandName, albumName, albumImg, spotifyId);
                    songs.add(song);
                }

            }
        } catch (JSONException e) {
            Log.e(TAG, "Exception error, JSONException thrown: ", e);
        } catch (IOException e) {
            Log.e(TAG, "Exception error, IOException thrown: ", e);
        }
        return songs;
    }
}

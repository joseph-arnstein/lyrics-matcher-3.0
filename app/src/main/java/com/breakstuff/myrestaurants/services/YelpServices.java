package com.breakstuff.myrestaurants.services;

import android.nfc.Tag;
import android.util.Log;

import com.breakstuff.myrestaurants.Constants;
import com.breakstuff.myrestaurants.models.Song;

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
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer;
import se.akerfeldt.okhttp.signpost.SigningInterceptor;

/**
 * Created by AlinaAir on 7/6/16.
 */
public class YelpServices {

    public static void findSong(String song, Callback callback) {
        String TAG = "YelpServices";
        String api = "apikey";
//        OkHttpOAuthConsumer consumer = new OkHttpOAuthConsumer(Constants.YELP_CONSUMER_KEY, Constants.YELP_CONSUMER_SECRET);
//        consumer.setTokenWithSecret(Constants.YELP_TOKEN, Constants.YELP_TOKEN_SECRET);
//
//        OkHttpClient client = new OkHttpClient.Builder()
//                .addInterceptor(new SigningInterceptor(consumer))
//                .build();
        //location = location.replaceAll("\\s+","%20");
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

//                    String phone = restaurantJSON.optString("display_phone", "Phone not available");
//                    String website = restaurantJSON.getString("url");
//                    double rating = restaurantJSON.getDouble("rating");
//                    String imageUrl = restaurantJSON.getString("image_url");
//                    double latitude = restaurantJSON.getJSONObject("location")
//                            .getJSONObject("coordinate").getDouble("latitude");
//                    double longitude = restaurantJSON.getJSONObject("location")
//                            .getJSONObject("coordinate").getDouble("longitude");
//                    ArrayList<String> address = new ArrayList<>();
//                    JSONArray addressJSON = restaurantJSON.getJSONObject("location")
//                            .getJSONArray("display_address");
//                    for (int y = 0; y < addressJSON.length(); y++) {
//                        address.add(addressJSON.get(y).toString());
//                    }

                    Song song = new Song(songName, bandName, albumName);
                    songs.add(song);
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return songs;
    }
}

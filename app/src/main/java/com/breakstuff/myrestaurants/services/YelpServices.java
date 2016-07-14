package com.breakstuff.myrestaurants.services;

import android.nfc.Tag;
import android.util.Log;

import com.breakstuff.myrestaurants.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

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

    public static void findRestaurants(String location, Callback callback) {
        String TAG = "YelpServices";
        String api = "apikey";
//        OkHttpOAuthConsumer consumer = new OkHttpOAuthConsumer(Constants.YELP_CONSUMER_KEY, Constants.YELP_CONSUMER_SECRET);
//        consumer.setTokenWithSecret(Constants.YELP_TOKEN, Constants.YELP_TOKEN_SECRET);
//
//        OkHttpClient client = new OkHttpClient.Builder()
//                .addInterceptor(new SigningInterceptor(consumer))
//                .build();
        //location = location.replaceAll("\\s+","%20");
        Log.v(TAG, location);

        OkHttpClient client = new OkHttpClient();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.LYRICS_BASE_URL).newBuilder();

        urlBuilder.setQueryParameter(Constants.LYRICS_QUERY_PARAMETER, location);

        urlBuilder.addQueryParameter(api, Constants.LYRICS_API_KEY);
        String url = urlBuilder.build().toString();
        Log.v(TAG, url);

        Request request= new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public void processResults(Response response) {
        String TAG = "process results";


        try {
            String jsonData = response.body().string();
            if (response.isSuccessful()) {
                JSONObject yelpJSON = new JSONObject(jsonData);
                Log.v(TAG, String.valueOf(yelpJSON));
            }
        } catch (JSONException e){
            Log.v(TAG, String.valueOf(e));
        } catch (IOException i){
            Log.v(TAG, String.valueOf(i));
        }
    }
}


//SOMETHING LIKE THIS.
//String x = (Constants.LYRICS_API_KEY);
//    Request r = new Request.Builder().url(x).build();
//


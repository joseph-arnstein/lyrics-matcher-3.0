package com.breakstuff.myrestaurants;

import android.nfc.Tag;
import android.util.Log;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer;
import se.akerfeldt.okhttp.signpost.SigningInterceptor;

/**
 * Created by AlinaAir on 7/6/16.
 */
public class YelpServices {

    public static void findRestaurants(String location, Callback callback) {
        String TAG = "YelpServices";
        String api = "apiKey";
//        OkHttpOAuthConsumer consumer = new OkHttpOAuthConsumer(Constants.YELP_CONSUMER_KEY, Constants.YELP_CONSUMER_SECRET);
//        consumer.setTokenWithSecret(Constants.YELP_TOKEN, Constants.YELP_TOKEN_SECRET);
//
//        OkHttpClient client = new OkHttpClient.Builder()
//                .addInterceptor(new SigningInterceptor(consumer))
//                .build();
        location = location.replaceAll("\\s+","%20");
        Log.v(TAG, location);

        OkHttpClient client = new OkHttpClient();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.LYRICS_BASE_URL).newBuilder();

        urlBuilder.setQueryParameter(Constants.LYRICS_QUERY_PARAMETER, location);

        //urlBuilder = urlBuilder + Constants.LYRICS_QUERY_PARAMETER + location;

        urlBuilder.addQueryParameter(api, Constants.LYRICS_API_KEY);
        String url = urlBuilder.build().toString();
        Log.v(TAG, url);

        String standInUrl = "http://api.musixmatch.com/ws/1.1/track.search?q_lyrics=keep%20it%20off%20my%20wave&apikey=1a995e721d11e3ae0b11d144bc6eed55"

        Request request= new Request.Builder()
                .url(standInUrl)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }
}


//SOMETHING LIKE THIS.
//String x = (Constants.LYRICS_API_KEY);
//    Request r = new Request.Builder().url(x).build();
//


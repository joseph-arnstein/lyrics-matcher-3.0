package com.breakstuff.myrestaurants.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.breakstuff.myrestaurants.R;
import com.breakstuff.myrestaurants.services.YelpServices;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.findSongButton) Button mFindSongButton;
    @Bind(R.id.editText) EditText mEditText;
    @Bind(R.id.appNameTextView) TextView mAppNameTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);//use the following layout file
        ButterKnife.bind(this);

        Typeface ostrichFont = Typeface.createFromAsset(getAssets(), "fonts/ostrich-sans/ostrich-regular.ttf");
        mAppNameTextView.setTypeface(ostrichFont);

        mFindSongButton.setOnClickListener(new View.OnClickListener() { //add click listener

           @Override
            public void onClick(View v) {
               String lyrics = mEditText.getText().toString();
               Intent intent = new Intent(MainActivity.this, DisplaySongsActivity.class);
               intent.putExtra("lyrics", lyrics);
               startActivity(intent);
           }
        });
    }

//    public static class RestaurantsActivity extends AppCompatActivity {
//        public static final String TAG = RestaurantsActivity.class.getSimpleName();
//
//        @Bind(R.id.textView) TextView mLocationTextView;
//        @Bind(R.id.listView)
//        ListView mListView;
//
//        private String[] songNames = new String[] {"Sweet Hereafter", "Cricket", "Hawthorne Fish House", "Viking Soul Food",
//                "Red Square", "Horse Brass", "Dick's Kitchen", "Taco Bell", "Me Kha Noodle Bar",
//                "La Bonita Taqueria", "Smokehouse Tavern", "Pembiche", "Kay's Bar", "Gnarly Grey", "Slappy Cakes", "Mi Mero Mole" };
//
//        @Override
//        protected void onCreate(Bundle savedInstanceState) {
//            super.onCreate(savedInstanceState);
//            setContentView(R.layout.activity_restaurants);
//            ButterKnife.bind(this);
//
//            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, songNames);
//            mListView.setAdapter(adapter);
//
//            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                    String restaurant = ((TextView)view).getText().toString();
//                    Toast.makeText(RestaurantsActivity.this, restaurant, Toast.LENGTH_LONG).show();
//                }
//            });
//
//            Intent intent = getIntent();
//            String location = intent.getStringExtra("location");
//
//            mLocationTextView.setText("Here are all the restaurants near: " + location);
//
//            getRestaurants(location);
//        }
//
//        private void getRestaurants(String location) {
//            final YelpServices yelpService = new YelpServices();
//            yelpService.findRestaurants(location, new Callback() {
//
//                @Override
//                public void onFailure(Call call, IOException e) {
//                    e.printStackTrace();
//                }
//
//                @Override
//                public void onResponse(Call call, Response response) throws IOException {
//                    try {
//                        String jsonData = response.body().string();
//                        Log.v(TAG, jsonData);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//        }
//    }
}

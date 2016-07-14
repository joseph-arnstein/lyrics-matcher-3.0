package com.breakstuff.myrestaurants.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.breakstuff.myrestaurants.R;
import com.breakstuff.myrestaurants.models.Song;
import com.breakstuff.myrestaurants.services.YelpServices;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

//public class DisplaySongsActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_display_songs);
//    }
//}

public class DisplaySongsActivity extends AppCompatActivity {
    public static final String TAG = DisplaySongsActivity.class.getSimpleName();

    @Bind(R.id.textView)
    TextView mLocationTextView;
    @Bind(R.id.listView)
    ListView mListView;

    public ArrayList<Song> mSongs = new ArrayList<>();
//    private String[] songNames = new String[] {"Sweet Hereafter", "Cricket", "Hawthorne Fish House", "Viking Soul Food",
//            "Red Square", "Horse Brass", "Dick's Kitchen", "Taco Bell", "Me Kha Noodle Bar",
//            "La Bonita Taqueria", "Smokehouse Tavern", "Pembiche", "Kay's Bar", "Gnarly Grey", "Slappy Cakes", "Mi Mero Mole" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_songs);
        ButterKnife.bind(this);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, mSongs);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String song = ((TextView)view).getText().toString();
                Toast.makeText(DisplaySongsActivity.this, song, Toast.LENGTH_LONG).show();
            }
        });

        Intent intent = getIntent();
        String lyrics = intent.getStringExtra("lyrics");

        mLocationTextView.setText("Showing results for lyrics: " + lyrics);

        getSongs(lyrics);
    }

    private void getSongs(String lyrics) {
        final YelpServices yelpService = new YelpServices();
        yelpService.findSong(lyrics, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                try {
//                    String jsonData = response.body().string();
//                    Log.v(TAG, jsonData);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }

            @Override
            public void onResponse(Call call, Response response) {
                mSongs = yelpService.processResults(response);


                DisplaySongsActivity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        String[] songNames = new String[mSongs.size()];
                        for (int i = 0; i < songNames.length; i++) {
                            songNames[i] = mSongs.get(i).getSong();
                        }
                        ArrayAdapter adapter = new ArrayAdapter(DisplaySongsActivity.this, android.R.layout.simple_list_item_1, songNames);
                        mListView.setAdapter(adapter);

//                        for (Song song : songNames) {
//                            Log.d(TAG, "Arist: " + song.getBand());
//                            Log.d(TAG, "Album: " + song.getAlbum());
//                            Log.d(TAG, "Song: " + song.getSong());
//                        }
                    }
                });
            }
        });
    }
}


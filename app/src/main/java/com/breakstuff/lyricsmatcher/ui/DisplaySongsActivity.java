package com.breakstuff.lyricsmatcher.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.breakstuff.lyricsmatcher.R;
import com.breakstuff.lyricsmatcher.adapters.SongListAdapter;
import com.breakstuff.lyricsmatcher.models.Song;
import com.breakstuff.lyricsmatcher.services.LyricsApiServices;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class DisplaySongsActivity extends AppCompatActivity {
    public static final String TAG = DisplaySongsActivity.class.getSimpleName();
    @Bind(R.id.recyclerView) RecyclerView mRecyclerView;

    @Bind(R.id.textView) TextView mLocationTextView;


    public ArrayList<Song> mSongs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_songs);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String lyrics = intent.getStringExtra("lyrics");

        mLocationTextView.setText("Showing results for lyrics: " + lyrics);

        getSongs(lyrics);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_logout) {
            logout();
            return true;
        }
        if (id == R.id.action_home) {
            Intent intent = new Intent(DisplaySongsActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
        if (id == R.id.action_my_songs){
            Intent intent = new Intent(DisplaySongsActivity.this, SavedSongListActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(DisplaySongsActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void getSongs(String lyrics) {
        final LyricsApiServices lyricsApiServices = new LyricsApiServices();
        lyricsApiServices.findSong(lyrics, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) {
                mSongs = lyricsApiServices.processResults(response);


                DisplaySongsActivity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        SongListAdapter mAdapter = new SongListAdapter(getApplicationContext(), mSongs);
                        mRecyclerView.setAdapter(mAdapter);
                        RecyclerView.LayoutManager layoutManager =
                                new LinearLayoutManager(DisplaySongsActivity.this);
                        mRecyclerView.setLayoutManager(layoutManager);
                        mRecyclerView.setHasFixedSize(true);
                    }
                });
            }
        });
    }
}


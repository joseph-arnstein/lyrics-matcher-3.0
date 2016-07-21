package com.breakstuff.lyricsmatcher.ui;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.breakstuff.lyricsmatcher.R;
import com.breakstuff.lyricsmatcher.adapters.PagerAdapter;
import com.breakstuff.lyricsmatcher.models.Song;
import com.google.firebase.auth.FirebaseAuth;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SongDetailsActivity extends AppCompatActivity {
    @Bind(R.id.viewPager) ViewPager mViewPager;
    private PagerAdapter adapterViewPager;
    ArrayList<Song> mSongsArray = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_details);
        ButterKnife.bind(this);

        mSongsArray = Parcels.unwrap(getIntent().getParcelableExtra("songs"));
        int startingPosition = getIntent().getIntExtra("position", 0);

        adapterViewPager = new PagerAdapter(getSupportFragmentManager(), mSongsArray);
        mViewPager.setAdapter(adapterViewPager);
        mViewPager.setCurrentItem(startingPosition);
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
            Intent intent = new Intent(SongDetailsActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
        if (id == R.id.action_my_songs){
            Intent intent = new Intent(SongDetailsActivity.this, SavedSongListActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(SongDetailsActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}

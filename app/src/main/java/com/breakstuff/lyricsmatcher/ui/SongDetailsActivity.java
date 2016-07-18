package com.breakstuff.lyricsmatcher.ui;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.breakstuff.lyricsmatcher.R;
import com.breakstuff.lyricsmatcher.adapters.PagerAdapter;
import com.breakstuff.lyricsmatcher.models.Song;

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
}

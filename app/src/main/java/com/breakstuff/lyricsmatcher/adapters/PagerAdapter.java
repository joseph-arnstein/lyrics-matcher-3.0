package com.breakstuff.lyricsmatcher.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.breakstuff.lyricsmatcher.models.Song;
import com.breakstuff.lyricsmatcher.ui.SongInfoFragment;

import java.util.ArrayList;

public class PagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Song> mSongs;

    public PagerAdapter(FragmentManager fm, ArrayList<Song> songs) {
        super(fm);
        mSongs = songs;
    }

    @Override
    public Fragment getItem(int position) {
        return SongInfoFragment.newInstance(mSongs.get(position));
    }

    @Override
    public int getCount() {
        return mSongs.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mSongs.get(position).getSong();
    }
}
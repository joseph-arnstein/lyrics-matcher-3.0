package com.breakstuff.lyricsmatcher.ui;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.breakstuff.lyricsmatcher.R;
import com.breakstuff.lyricsmatcher.models.Song;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SongInfoFragment extends Fragment implements View.OnClickListener{
    @Bind(R.id.albumImageView) ImageView mImageLabel;
    @Bind(R.id.songNameTextView) TextView mSongNameLabel;
    @Bind(R.id.artistNameTextView) TextView mAristLabel;
    @Bind(R.id.albumTextView) TextView mAlbumNameLabel;
    @Bind(R.id.spotifyLinkTextView) TextView mSpotifyLink;
    @Bind(R.id.phoneTextView) TextView mPhoneLabel;
    @Bind(R.id.addressTextView) TextView mAddressLabel;
    @Bind(R.id.saveSongButton) TextView mSaveSongButton;

    private Song mSong;


    public static SongInfoFragment newInstance(Song song) {
        SongInfoFragment songInfoFragment = new SongInfoFragment();
        Bundle args = new Bundle();
        args.putParcelable("song", Parcels.wrap(song));
        songInfoFragment.setArguments(args);
        return songInfoFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSong = Parcels.unwrap(getArguments().getParcelable("song"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_song_info, container, false);
        ButterKnife.bind(this, view);

        mSpotifyLink.setOnClickListener(this);

        Picasso.with(view.getContext()).load(mSong.getImage()).into(mImageLabel);

        mSongNameLabel.setText(mSong.getSong());
        mAristLabel.setText(mSong.getBand());
        mAlbumNameLabel.setText(mSong.getAlbum());
        mSpotifyLink.setText("Search YouTube");

        return view;
    }

    @Override
    public void onClick(View view) {
        if (view == mSpotifyLink) {
            Intent webIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.youtube.com/results?search_query=" + mSong.getSong()));
            startActivity(webIntent);
        }
    }
}

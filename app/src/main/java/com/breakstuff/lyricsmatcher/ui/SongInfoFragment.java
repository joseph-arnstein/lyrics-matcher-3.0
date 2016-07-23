package com.breakstuff.lyricsmatcher.ui;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.breakstuff.lyricsmatcher.Constants;
import com.breakstuff.lyricsmatcher.R;
import com.breakstuff.lyricsmatcher.models.Song;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;
import com.spotify.sdk.android.player.Config;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.PlayerNotificationCallback;
import com.spotify.sdk.android.player.PlayerState;
import com.spotify.sdk.android.player.Spotify;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.breakstuff.lyricsmatcher.Constants.FIREBASE_CHILD_SONGS;

public class SongInfoFragment extends Fragment implements View.OnClickListener{
    @Bind(R.id.albumImageView) ImageView mImageLabel;
    @Bind(R.id.songNameTextView) TextView mSongNameLabel;
    @Bind(R.id.artistNameTextView) TextView mAristLabel;
    @Bind(R.id.albumTextView) TextView mAlbumNameLabel;
    @Bind(R.id.spotifyLinkTextView) TextView mSpotifyLink;
//    @Bind(R.id.phoneTextView) TextView mPhoneLabel;
//    @Bind(R.id.addressTextView) TextView mAddressLabel;
    @Bind(R.id.saveSongTextView) TextView mSaveSongText;
    @Bind(R.id.youTubeLinkTextView) TextView mYouTube;

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
        mSaveSongText.setOnClickListener(this);
        mYouTube.setOnClickListener(this);

        Picasso.with(view.getContext()).load(mSong.getImage()).into(mImageLabel);

        mSongNameLabel.setText(mSong.getSong());
        mAristLabel.setText(mSong.getBand());
        mAlbumNameLabel.setText(mSong.getAlbum());
        mSpotifyLink.setText("Play Song in Spotify");

        return view;
    }

    @Override
    public void onClick(View view) {
        if (view == mSpotifyLink) {
//            Intent webIntent = new Intent(Intent.ACTION_VIEW,
//                    Uri.parse("https://www.youtube.com/results?search_query=" + mSong.getSong()));
//            startActivity(webIntent);

//
//            String spotifyId = mSong.getSpotifyId();
            Intent intent = new Intent(getContext(), SpotifyActivity.class);
            intent.putExtra("song", Parcels.wrap(mSong));
            startActivity(intent);


        }
        if (view == mYouTube){
            Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/results?search_query=" + mSong.getSong()));
            startActivity(webIntent);
        }
        if (view == mSaveSongText) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String uid = user.getUid();

            DatabaseReference songRef = FirebaseDatabase
                    .getInstance()
                    .getReference(Constants.FIREBASE_CHILD_SONGS)
                    .child(uid);

            DatabaseReference pushRef = songRef.push();
            String pushId = pushRef.getKey();
            mSong.setPushId(pushId);
            pushRef.setValue(mSong);



            Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
        }
    }
}

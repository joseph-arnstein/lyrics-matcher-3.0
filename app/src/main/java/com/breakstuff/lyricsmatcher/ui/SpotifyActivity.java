package com.breakstuff.lyricsmatcher.ui;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.breakstuff.lyricsmatcher.R;
import com.breakstuff.lyricsmatcher.models.Song;
import com.spotify.sdk.android.player.Spotify;
import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;
import com.spotify.sdk.android.player.Config;
import com.spotify.sdk.android.player.ConnectionStateCallback;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.PlayerNotificationCallback;
import com.spotify.sdk.android.player.PlayerState;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SpotifyActivity extends Activity implements PlayerNotificationCallback, ConnectionStateCallback {
    public static final String TAG = SongDetailsActivity.class.getSimpleName();

    @Bind(R.id.albumImageView) ImageView mImageLabel;
    @Bind(R.id.songNameTextView) TextView mSongNameLabel;
    @Bind(R.id.artistNameTextView) TextView mAristLabel;
    @Bind(R.id.albumTextView) TextView mAlbumNameLabel;

    private TextView mHelloWorld;

    // TODO: Replace with your client ID
    private static final String CLIENT_ID = "683b6217dfc94a0592d0398c8849aba0";
    // TODO: Replace with your redirect URI
    private static final String REDIRECT_URI = "tae-android-app://callback";

    // Request code that will be passed together with authentication result to the onAuthenticationResult callback
    // Can be any integer
    private static final int REQUEST_CODE = 1337;

    private Player mPlayer;
    private String spotifyCall;
    private Song mSong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spotify);

        mSong = Parcels.unwrap(getIntent().getParcelableExtra("song"));
        ButterKnife.bind(this);

        Picasso.with(this).load(mSong.getImage()).into(mImageLabel);

        mSongNameLabel.setText(mSong.getSong());
        mAristLabel.setText(mSong.getBand());
        mAlbumNameLabel.setText(mSong.getAlbum());

        String spotifyId = mSong.getSpotifyId();


        spotifyCall="spotify:track:"+spotifyId;
        Log.e(TAG, spotifyCall);

        AuthenticationRequest.Builder builder =
                new AuthenticationRequest.Builder(CLIENT_ID, AuthenticationResponse.Type.TOKEN, REDIRECT_URI);
        builder.setScopes(new String[]{"user-read-private", "streaming"});
        AuthenticationRequest request = builder.build();

        AuthenticationClient.openLoginActivity(this, REQUEST_CODE, request);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        // Check if result comes from the correct activity
        if (requestCode == REQUEST_CODE) {
            AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, intent);
            if (response.getType() == AuthenticationResponse.Type.TOKEN) {
                Config playerConfig = new Config(this, response.getAccessToken(), CLIENT_ID);
                mPlayer = com.spotify.sdk.android.player.Spotify.getPlayer(playerConfig, this, new Player.InitializationObserver() {
                    @Override
                    public void onInitialized(Player player) {
                        mPlayer.addConnectionStateCallback(SpotifyActivity.this);
                        mPlayer.addPlayerNotificationCallback(SpotifyActivity.this);
                        mPlayer.play(spotifyCall);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Log.e("MainActivity", "Could not initialize player: " + throwable.getMessage());
                    }
                });
            }
        }
    }

    @Override
    public void onLoggedIn() {
        Log.d("MainActivity", "User logged in");
    }

    @Override
    public void onLoggedOut() {
        Log.d("MainActivity", "User logged out");
    }

    @Override
    public void onLoginFailed(Throwable error) {
        Log.d("MainActivity", "Login failed");
    }

    @Override
    public void onTemporaryError() {
        Log.d("MainActivity", "Temporary error occurred");
    }

    @Override
    public void onConnectionMessage(String message) {
        Log.d("MainActivity", "Received connection message: " + message);
    }

    @Override
    public void onPlaybackEvent(PlayerNotificationCallback.EventType eventType, PlayerState playerState) {
        Log.d("MainActivity", "Playback event received: " + eventType.name());
        switch (eventType) {
            // Handle event type as necessary
            default:
                break;
        }
    }

    @Override
    public void onPlaybackError(PlayerNotificationCallback.ErrorType errorType, String errorDetails) {
        Log.d("MainActivity", "Playback error received: " + errorType.name());
        switch (errorType) {
            // Handle error type as necessary
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        // VERY IMPORTANT! This must always be called or else you will leak resources
        Spotify.destroyPlayer(this);
        super.onDestroy();
    }
}

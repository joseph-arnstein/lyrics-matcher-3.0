package com.breakstuff.lyricsmatcher.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.breakstuff.lyricsmatcher.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    @Bind(R.id.newSearchButton) Button mFindSongButton;
    @Bind(R.id.editText) EditText mEditText;
    @Bind(R.id.appNameTextView) TextView mAppNameTextView;
    @Bind(R.id.newSavedButton) Button mFirebaseButton;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);//use the following layout file
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                //display welcome message
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    getSupportActionBar().setTitle("Welcome, " + user.getDisplayName() + "!");
                } else {

                }
            }
        };

        Typeface ostrichFont = Typeface.createFromAsset(getAssets(), "fonts/ostrich-sans/ostrich-regular.ttf");
        mAppNameTextView.setTypeface(ostrichFont);

        mFindSongButton.setOnClickListener(this);
        mFirebaseButton.setOnClickListener(this);

        }
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
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
            Toast.makeText(MainActivity.this, "You're already home my good man.",
                    Toast.LENGTH_SHORT).show();
        }
        if (id == R.id.action_my_songs){
            Intent intent = new Intent(MainActivity.this, SavedSongListActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {

        if (v == mFindSongButton) {
            String lyrics = mEditText.getText().toString();
            Intent intent = new Intent(MainActivity.this, DisplaySongsActivity.class);
            intent.putExtra("lyrics", lyrics);
            startActivity(intent);
        }
        if (v == mFirebaseButton) {
            Intent intent = new Intent(MainActivity.this, SavedSongListActivity.class);
            startActivity(intent);
        }

    }
}
package com.breakstuff.lyricsmatcher.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.breakstuff.lyricsmatcher.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    @Bind(R.id.findSongButton) Button mFindSongButton;
    @Bind(R.id.editText) EditText mEditText;
    @Bind(R.id.appNameTextView) TextView mAppNameTextView;
    @Bind(R.id.savedSongsButton) Button mFirebaseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);//use the following layout file
        ButterKnife.bind(this);

        Typeface ostrichFont = Typeface.createFromAsset(getAssets(), "fonts/ostrich-sans/ostrich-regular.ttf");
        mAppNameTextView.setTypeface(ostrichFont);

        mFindSongButton.setOnClickListener(this);
        mFirebaseButton.setOnClickListener(this);

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
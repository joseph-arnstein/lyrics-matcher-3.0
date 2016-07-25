package com.breakstuff.lyricsmatcher.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.breakstuff.lyricsmatcher.Constants;
import com.breakstuff.lyricsmatcher.R;
import com.breakstuff.lyricsmatcher.models.Song;
import com.breakstuff.lyricsmatcher.ui.SongDetailsActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;

public class FirebaseSongsViewHolder extends RecyclerView.ViewHolder {

    View mView;
    Context mContext;
    public ImageView mAlbumImageView;

    public FirebaseSongsViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
    }
    public void bindSong(Song song) {
        mAlbumImageView = (ImageView) mView.findViewById(R.id.albumImageView);
        TextView songNameTextView = (TextView) mView.findViewById(R.id.songNameTextView);
        TextView albumNameTextView = (TextView) mView.findViewById(R.id.albumTextView);
        TextView artistNameTextView = (TextView) mView.findViewById(R.id.artistNameTextView);


        Picasso.with(mContext)
                .load(song.getImage())
                .into(mAlbumImageView);


        songNameTextView.setText(song.getSong());
        albumNameTextView.setText(song.getAlbum());
        artistNameTextView.setText(song.getBand());
    }

}

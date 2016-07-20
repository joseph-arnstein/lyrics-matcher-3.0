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

public class FirebaseSongsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    View mView;
    Context mContext;
    ImageView mAlbumImageView;

    public FirebaseSongsViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
        itemView.setOnClickListener(this);
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

    @Override
    public void onClick(View view) {
        final ArrayList<Song> songs = new ArrayList<>();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_SONGS).child(uid);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    songs.add(snapshot.getValue(Song.class));
                }

                int itemPosition = getLayoutPosition();

                Intent intent = new Intent(mContext, SongDetailsActivity.class);
                intent.putExtra("position", itemPosition);
                intent.putExtra("songs", Parcels.wrap(songs));

                mContext.startActivity(intent);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Database Error: ", databaseError.toString());
            }
        });
    }
}

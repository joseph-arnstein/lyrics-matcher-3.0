package com.breakstuff.lyricsmatcher.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.View;

import com.breakstuff.lyricsmatcher.models.Song;
import com.breakstuff.lyricsmatcher.ui.SongDetailsActivity;
import com.breakstuff.lyricsmatcher.uitl.ItemTouchHelperAdapter;
import com.breakstuff.lyricsmatcher.uitl.OnStartDragListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Collections;

public class FirebaseSongListAdapter extends FirebaseRecyclerAdapter<Song, FirebaseSongsViewHolder> implements ItemTouchHelperAdapter {

    private DatabaseReference mRef;
    private OnStartDragListener mOnStartDragListener;
    private Context mContext;

    private ChildEventListener mChildEventListener;
    private ArrayList<Song> mSongs = new ArrayList<>();

    public FirebaseSongListAdapter(Class<Song> modelClass, int modelLayout,
                                         Class<FirebaseSongsViewHolder> viewHolderClass,
                                         Query ref, OnStartDragListener onStartDragListener, Context context) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        mRef = ref.getRef();
        mOnStartDragListener = onStartDragListener;
        mContext = context;

        mChildEventListener = mRef.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                mSongs.add(dataSnapshot.getValue(Song.class));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    @Override
    protected void populateViewHolder(final FirebaseSongsViewHolder viewHolder, Song model, int position) {

        viewHolder.bindSong(model);

        viewHolder.mAlbumImageView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    mOnStartDragListener.onStartDrag(viewHolder);
                }
                return false;
            }

        });
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, SongDetailsActivity.class);
                intent.putExtra("position", viewHolder.getAdapterPosition());
                intent.putExtra("songs", Parcels.wrap(mSongs));
                mContext.startActivity(intent);
            }
        });
    }


    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(mSongs, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return false;
    }

    @Override
    public void onItemDismiss(int position) {
        mSongs.remove(position);
        getRef(position).removeValue();
    }
    private void setIndexInFirebase() {
        for (Song song : mSongs) {
            int index = mSongs.indexOf(song);
            DatabaseReference ref = getRef(index);
            song.setIndex(Integer.toString(index));
            ref.setValue(song);
        }
    }
    @Override
    public void cleanup() {
        super.cleanup();
        setIndexInFirebase();
        mRef.removeEventListener(mChildEventListener);
    }

}

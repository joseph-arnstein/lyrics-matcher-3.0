package com.breakstuff.lyricsmatcher.adapters;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.View;

import com.breakstuff.lyricsmatcher.models.Song;
import com.breakstuff.lyricsmatcher.uitl.ItemTouchHelperAdapter;
import com.breakstuff.lyricsmatcher.uitl.OnStartDragListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

/**
 * Created by AlinaAir on 7/18/16.
 */
public class FirebaseSongListAdapter extends FirebaseRecyclerAdapter<Song, FirebaseSongsViewHolder> implements ItemTouchHelperAdapter {

    private DatabaseReference mRef;
    private OnStartDragListener mOnStartDragListener;
    private Context mContext;

    public FirebaseSongListAdapter(Class<Song> modelClass, int modelLayout,
                                         Class<FirebaseSongsViewHolder> viewHolderClass,
                                         Query ref, OnStartDragListener onStartDragListener, Context context) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        mRef = ref.getRef();
        mOnStartDragListener = onStartDragListener;
        mContext = context;
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
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        return false;
    }

    @Override
    public void onItemDismiss(int position) {

    }

}

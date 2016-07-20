package com.breakstuff.lyricsmatcher.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.breakstuff.lyricsmatcher.Constants;
import com.breakstuff.lyricsmatcher.R;
import com.breakstuff.lyricsmatcher.adapters.FirebaseSongListAdapter;
import com.breakstuff.lyricsmatcher.adapters.FirebaseSongsViewHolder;
import com.breakstuff.lyricsmatcher.models.Song;
import com.breakstuff.lyricsmatcher.uitl.OnStartDragListener;
import com.breakstuff.lyricsmatcher.uitl.SimpleItemTouchHelperCallback;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SavedSongListActivity extends AppCompatActivity implements OnStartDragListener {
    private DatabaseReference mSongReference;
    private FirebaseSongListAdapter mFirebaseAdapter;
    private ItemTouchHelper mTouchHelper;

    @Bind(R.id.recyclerView) RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_songs);
        ButterKnife.bind(this);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        mSongReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_SONGS).child(uid);

        //mSongReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_SONGS);
        setUpFirebaseAdapter();
    }

    private void setUpFirebaseAdapter() {

       // mSongReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_SONGS);

//        mFirebaseAdapter = new FirebaseRecyclerAdapter<Song, FirebaseSongsViewHolder>
//                (Song.class, R.layout.song_list_item, FirebaseSongsViewHolder.class,
//                        mSongReference) {
//
//            @Override
//            protected void populateViewHolder(FirebaseSongsViewHolder viewHolder,
//                                              Song model, int position) {
//                viewHolder.bindSong(model);
//            }
//        };
        mFirebaseAdapter = new FirebaseSongListAdapter(Song.class, R.layout.song_list_item, FirebaseSongsViewHolder.class, mSongReference, this, this);


        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mFirebaseAdapter);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mFirebaseAdapter);
        mTouchHelper = new ItemTouchHelper(callback);
        mTouchHelper.attachToRecyclerView(mRecyclerView);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFirebaseAdapter.cleanup();
    }
    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mTouchHelper.startDrag(viewHolder);
    }
}

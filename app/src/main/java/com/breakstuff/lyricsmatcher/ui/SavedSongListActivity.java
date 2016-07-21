package com.breakstuff.lyricsmatcher.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

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
            Intent intent = new Intent(SavedSongListActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
        if (id == R.id.action_my_songs){
            Toast.makeText(SavedSongListActivity.this, "You're already in saved songs my good man.",
                    Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(SavedSongListActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
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

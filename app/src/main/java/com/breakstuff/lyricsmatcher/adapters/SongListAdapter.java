package com.breakstuff.lyricsmatcher.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.breakstuff.lyricsmatcher.R;
import com.breakstuff.lyricsmatcher.models.Song;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by AlinaAir on 7/17/16.
 */
public class SongListAdapter extends RecyclerView.Adapter<SongListAdapter.SongViewHolder>{
    private ArrayList<Song> mSongsArray = new ArrayList<>();
    private Context mContext;

    public SongListAdapter(Context context, ArrayList<Song> songsArray) {
        mContext = context;
        mSongsArray = songsArray;
    }

    @Override
    public SongListAdapter.SongViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.song_list_item, parent, false);
        SongViewHolder viewHolder = new SongViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SongListAdapter.SongViewHolder holder, int position) {
        holder.bindSong(mSongsArray.get(position));
    }

    @Override
    public int getItemCount() {
        return mSongsArray.size();
    }

    public class SongViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.albumImageView) ImageView mAlbumImageView;
        @Bind(R.id.songNameTextView) TextView mSongNameTextView;
        @Bind(R.id.artistNameTextView) TextView mArtistTextView;
        @Bind(R.id.albumTextView) TextView mAlbumTextView;

        private Context mContext;

        public SongViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
        }

        public void bindSong(Song song) {
            mSongNameTextView.setText(song.getSong());
            mArtistTextView.setText(song.getBand());
            mAlbumTextView.setText(song.getAlbum());
        }
    }
}

package com.breakstuff.lyricsmatcher.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.breakstuff.lyricsmatcher.R;
import com.breakstuff.lyricsmatcher.models.Song;
import com.breakstuff.lyricsmatcher.ui.SongDetailsActivity;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by AlinaAir on 7/17/16.
 */
public class SongListAdapter extends RecyclerView.Adapter<SongListAdapter.SongViewHolder> {
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

    public class SongViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Bind(R.id.albumImageView) ImageView mAlbumImageView;
        @Bind(R.id.songNameTextView) TextView mSongNameTextView;
        @Bind(R.id.artistNameTextView) TextView mArtistTextView;
        @Bind(R.id.albumTextView) TextView mAlbumTextView;

        private Context mContext;

        public SongViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
            itemView.setOnClickListener(this);
        }

        public void bindSong(Song song) {
            mSongNameTextView.setText(song.getSong());
            mArtistTextView.setText(song.getBand());
            mAlbumTextView.setText(song.getAlbum());
            Picasso.with(mContext).load(song.getImage()).into(mAlbumImageView);
        }

        @Override
        public void onClick(View view) {
            int itemPosition = getLayoutPosition();

            Intent intent = new Intent(mContext, SongDetailsActivity.class);
            intent.putExtra("position", itemPosition);
            intent.putExtra("songs", Parcels.wrap(mSongsArray));

            mContext.startActivity(intent);
        }
    }
}

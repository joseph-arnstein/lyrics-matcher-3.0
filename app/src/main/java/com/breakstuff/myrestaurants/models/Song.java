package com.breakstuff.myrestaurants.models;

/**
 * Created by AlinaAir on 7/12/16.
 */
public class Song {
    private String song;
    private String band;
    private String album;

    public Song (String song, String band, String album){
        this.song = song;
        this.band = band;
        this.album = album;
    }

    public String getSong() {
        return song;
    }
    public String getBand() {
        return band;
    }
    public String getAlbum() {
        return album;
    }
}

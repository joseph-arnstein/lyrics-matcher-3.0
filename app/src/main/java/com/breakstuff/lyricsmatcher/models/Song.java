package com.breakstuff.lyricsmatcher.models;

import org.parceler.Parcel;

@Parcel
public class Song {
    String song;
    String band;
    String album;
    String albumImg;
    String spotifyId;
    String pushId;

    public Song() {}

    public Song (String song, String band, String album, String albumImg, String spotifyId){
        this.song = song;
        this.band = band;
        this.album = album;
        this.albumImg = albumImg;
        this.spotifyId = spotifyId;
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
    public String getImage() {
        return albumImg;
    }
    public String getSpotifyId() {
        return spotifyId;
    }
    public String getAlbumImg() {
        return albumImg;
    }

    public String getPushId(){
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }
}

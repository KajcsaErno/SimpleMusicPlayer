package com.example.android.simplemusicplayer;

public class Artist {

    private String mArtistName ;
    private int mImageResourceId;


    public Artist(String artistName, int imageResourceId) {
        this.mArtistName = artistName;
        this.mImageResourceId = imageResourceId;

    }


    public String getArtistName() {
        return mArtistName;
    }

    public int getImageResourceId() {
        return mImageResourceId;
    }



}

package com.example.android.simplemusicplayer;

public class Artist {

    private String mArtistName;
    private int mImageResourceId;
    private String mArtistWebsite;

    Artist(String artistName, int imageResourceId, String albumWebsite) {
        this.mArtistName = artistName;
        this.mImageResourceId = imageResourceId;
        this.mArtistWebsite = albumWebsite;

    }

    public String getArtistName() {
        return mArtistName;
    }

    public int getImageResourceId() {
        return mImageResourceId;
    }

    public String getArtistWebsite() {
        return mArtistWebsite;
    }
}

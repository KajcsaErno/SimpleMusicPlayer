package com.example.android.simplemusicplayer;

public class Song {

    private String mSongName;
    private String mArtistName;
    private int mImageResourceId;
    private int mSoundResourceId;


    Song(String songName, String artistName, int imageResourceId, int soundResourceId) {
        this.mSongName = songName;
        this.mArtistName = artistName;
        this.mImageResourceId = imageResourceId;
        this.mSoundResourceId = soundResourceId;

    }

    public String getSongName() {
        return mSongName;
    }

    public String getArtistName() {
        return mArtistName;
    }

    public int getImageResourceId() {
        return mImageResourceId;
    }

    public int getSoundResourceId() {
        return mSoundResourceId;
    }

    @Override
    public String toString() {
        return "Song{" +
                "mSongName='" + mSongName + '\'' +
                ", mArtistName='" + mArtistName + '\'' +
                ", mImageResourceId=" + mImageResourceId +
                ", mSoundResourceId=" + mSoundResourceId +
                '}';
    }


}

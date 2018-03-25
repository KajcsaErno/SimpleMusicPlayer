package com.example.android.simplemusicplayer;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

public class ArtistActivity extends AppCompatActivity {

    //Handles playback of all the sound files
    private MediaPlayer mMediaPlayer;

    //Handles audio focus when playing a sound file
    private AudioManager mAudioManager;

    //This listener gets triggered when the {@link MediaPlayer} has completed playing the audio file.
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            // Now that the sound file has finished playing, release the media player resources.
            releaseMediaPlayer();
        }
    };

    //This listener gets triggered whenever the audio focus changes (i.e., we gain or lose audio focus because of another app or device).
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                // The AUDIOFOCUS_LOSS_TRANSIENT case means that we've lost audio focus for a
                // short amount of time. The AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK case means that
                // our app is allowed to continue playing sound but at a lower volume. We'll treat
                // both cases the same way because our app is playing short sound files.

                // Pause playback and reset player to the start of the file. That way, we can
                // play the word from the beginning when we resume playback.
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // The AUDIOFOCUS_GAIN case means we have regained focus and can resume playback.
                mMediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                // The AUDIOFOCUS_LOSS case means we've lost audio focus and
                // Stop playback and clean up resources
                releaseMediaPlayer();
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.artists_grid);

        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        // Create a list of songs
        final ArrayList<Song> songs = new ArrayList<Song>();
        songs.add(new Song("Mentirosa", "Ráfaga", R.drawable.rafaga, R.raw.rafaga_mentirosa));
        songs.add(new Song("Lost On You ", "LP", R.drawable.lp, R.raw.lost_on_you));
        songs.add(new Song("Stay High ft. Hippie Sabotage", "Tove Lo", R.drawable.stay_high, R.raw.stay_high));
        songs.add(new Song("Something Good", "alt-J", R.drawable.alt_j, R.raw.something_good));
        songs.add(new Song("Boulevard Of Broken Dreams", "Green Day", R.drawable.greenday, R.raw.boulevard_of_broken_dreams));
        songs.add(new Song("Little Green Bag ", "George Baker", R.drawable.little_green_bag, R.raw.little_green_bag));
        songs.add(new Song("American Money", "BØRNS", R.drawable.american_money, R.raw.american_money));
        songs.add(new Song("False Alarm", "The Weeknd", R.drawable.false_alarm, R.raw.false_alarm));
        songs.add(new Song("Lifted Up (1985)", "Passion Pit", R.drawable.lifted_up, R.raw.lifted_up));
        songs.add(new Song("Sweet Dreams Are Made Of This", "X Men Apocalypse Quicksilver Theme Song", R.drawable.sweet_dreams, R.raw.sweet_dreams));
        songs.add(new Song("A Sky Full Of Stars", "Coldplay", R.drawable.sky_full_of_stars, R.raw.sky_full_of_stars));
        songs.add(new Song("Break The Rules", "Charli XCX", R.drawable.break_the_rules, R.raw.break_the_rules));

        // The adapter knows how to create list items for each item in the list.
       final ArtistAdapter adapter = new ArtistAdapter(this, songs);

        GridView gridView = findViewById(R.id.gridview);

        gridView.setAdapter(adapter);

           gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Release the media player if it currently exists because we are about to
                // play a different sound file
                releaseMediaPlayer();

                Song song = songs.get(position);
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    // We have audio focus now.

                    // Create and setup the {@link MediaPlayer} for the audio resource associated
                    // with the current word
                    mMediaPlayer = MediaPlayer.create(ArtistActivity.this, song.getSoundResourceId());

                    // Start the audio file
                    mMediaPlayer.start();

                    // Setup a listener on the media player, so that we can stop and release the
                    // media player once the sound has finished playing.
                    mMediaPlayer.setOnCompletionListener(mCompletionListener);


//                    Intent openActivity = new Intent(SongActivity.this, MusicPlayerActivity.class);
//                    startActivity(openActivity);
                }

            }
        });


    }

    @Override
    public void onStop() {
        super.onStop();

        releaseMediaPlayer();
    }

    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;

            // Regardless of whether or not we were granted audio focus, abandon it. This also
            // unregisters the AudioFocusChangeListener so we don't get anymore callbacks.
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }
}
package com.example.android.simplemusicplayer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SongActivity extends AppCompatActivity {


    private static final String TAG = "SongActivity";
    //Handles playback of all the sound files
    public static MediaPlayer mMediaPlayer;
    private ImageView mFooterImage;
    private ImageView mFooterPlayIcon;
    private TextView mFooterTextView;
    private ListView mListView;

    //Handles audio focus when playing a sound file
    private AudioManager mAudioManager;

    private MyInterface myInterface;
    //This listener gets triggered whenever the audio focus changes (i.e., we gain or lose audio focus because of another app or device).

    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                // The AUDIOFOCUS_LOSS_TRANSIENT case means that we've lost audio focus for a short amount of time.
                // The AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK case means that our app is allowed to continue playing sound but at a lower volume.
                mMediaPlayer.pause();
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // The AUDIOFOCUS_GAIN case means we have regained focus and can resume playback.
                mMediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                // The AUDIOFOCUS_LOSS case means we've lost audio focus and
                // Stop playback and clean up resources
//                releaseMediaPlayer();
            }
        }
    };
    //This listener gets triggered when the {@link MediaPlayer} has completed playing the audio file.
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            // Now that the sound file has finished playing, release the media player resources.
            //releaseMediaPlayer();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.songs_list);

        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        mFooterImage = findViewById(R.id.footer_image_view);
        mFooterTextView = findViewById(R.id.footer_text_view);


        ImageView mFooterSkipPreviousIcon = findViewById(R.id.footer_skip_previous_icon);
        mFooterPlayIcon = findViewById(R.id.footer_play_icon);
        ImageView mFooterSkipeNextIcon = findViewById(R.id.footer_skip_next_icon);

        mListView = findViewById(R.id.list);

        // Create a list of songs
        final ArrayList<Song> songs = new ArrayList<>();
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
        SongAdapter adapter = new SongAdapter(this, songs);

        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Release the media player if it currently exists because we are about to  play a different sound file
                releaseMediaPlayer();

                Song song = songs.get(position);
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    // We have audio focus now.

                    // Create and setup the {@link MediaPlayer} for the audio resource associated
                    // with the current word
                    mMediaPlayer = MediaPlayer.create(SongActivity.this, song.getSoundResourceId());

                    // Start the audio file
                    mMediaPlayer.start();

                    // Setup a listener on the media player, so that we can stop and release the media player once the sound has finished playing.
                    //mMediaPlayer.setOnCompletionListener(mCompletionListener);

                    //setting the play button to pause
                    mFooterPlayIcon.setImageResource(R.drawable.ic_pause_white_36dp);

                    //changing the background color when a music is selected
                    mListView.setBackgroundColor(Color.parseColor("#00FF00"));

                    //Text Scrolling Effect
                    mFooterTextView.setSelected(true);


                    mFooterImage.setImageResource(songs.get(position).getImageResourceId());
                    mFooterTextView.setText(songs.get(position).getSongName());


                    if (myInterface != null) {
                        myInterface.updateImage(songs.get(position).getImageResourceId());
                        myInterface.updateText(songs.get(position).getSongName());
                    }
                }
            }


        });


        mFooterPlayIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
                    mMediaPlayer.pause();
                    mFooterPlayIcon.setImageResource(R.drawable.ic_play_arrow_white_36dp);
                    mListView.setBackgroundColor(Color.parseColor("#4E342E"));


                } else {
                    if (mMediaPlayer != null) {
                        mMediaPlayer.start();
                        mFooterPlayIcon.setImageResource(R.drawable.ic_pause_white_36dp);
                        mListView.setBackgroundColor(Color.parseColor("#00FF00"));
                    }
                }
            }
        });

        mFooterSkipPreviousIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SongActivity.this, "Skips to the previous song... ", Toast.LENGTH_SHORT).show();
            }
        });

        //when long pressed fast reverse 20 sec
        mFooterSkipPreviousIcon.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                long seek = 20000;
                if (mMediaPlayer != null) {
                    int duration = mMediaPlayer.getDuration();
                    if (mMediaPlayer != null && seek < duration) {
                        int skips = (int) (mMediaPlayer.getCurrentPosition() - seek);
                        mMediaPlayer.seekTo(skips);
                    }
                }
                return false;
            }
        });

        mFooterSkipeNextIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mMediaPlayer.setNextMediaPlayer();

                Toast.makeText(SongActivity.this, "Skips to the next song...", Toast.LENGTH_SHORT).show();

            }
        });

        //when long pressed fast forward 20 sec
        mFooterSkipeNextIcon.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                long seek = 20000;
                if (mMediaPlayer != null) {
                    int duration = mMediaPlayer.getDuration();
                    if (seek < duration) {
                        int skips = (int) (mMediaPlayer.getCurrentPosition() + seek);
                        mMediaPlayer.seekTo(skips);
                    }
                }
                return false;
            }

        });

        mFooterImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent to open the NowPlayingActivity
                Intent openActivity = new Intent(SongActivity.this, NowPlayingActivity.class);
                startActivity(openActivity);
            }
        });

        mFooterTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent to open the NowPlayingActivity
                Intent openActivity = new Intent(SongActivity.this, NowPlayingActivity.class);
                startActivity(openActivity);

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (myInterface == null) {
            myInterface = InterfaceHolder.getMyInterface();
        }
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

    private void playNext() {
        if (mMediaPlayer != null) {
            mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
            // mMediaPlayer.setNextMediaPlayer(lofasz);
        }
    }


}
package com.example.android.simplemusicplayer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class SongActivity extends AppCompatActivity  {

    //Handles playback of all the sound files
    public static MediaPlayer mMediaPlayer;
    private ImageView mFooterImage, mFooterSkipPreviousIcon, mFooterPlayIcon, mFooterSkipeNextIcon;
    private TextView mFooterTextView;
    private ListView mListView;
    //Handles audio focus when playing a sound file
    private AudioManager mAudioManager;
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
                releaseMediaPlayer();
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

       final MyInterface mMyInterface =  InterfaceHolder.getMyInterface();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        mFooterImage = findViewById(R.id.footer_image_view);
        mFooterTextView = findViewById(R.id.footer_text_view);


        mFooterSkipPreviousIcon = findViewById(R.id.footer_skip_previous_icon);
        mFooterPlayIcon = findViewById(R.id.footer_play_icon);
        mFooterSkipeNextIcon = findViewById(R.id.footer_skip_next_icon);

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

                    if (songs.get(0) == songs.get(position)) {
                        //updating the image and text from footer when user selects a item
                        mFooterImage.setImageResource(R.drawable.rafaga);
                        mFooterTextView.setText(R.string.mentirosa);

                        //updating the image and text from now playing activity when user selects a item
                        mMyInterface.updateImage(R.drawable.rafaga);
                        mMyInterface.updateText(R.string.mentirosa);
                    }
                    if (songs.get(1) == songs.get(position)) {
                        //updating the image and text from footer when user selects a item
                        mFooterImage.setImageResource(R.drawable.lp);
                        mFooterTextView.setText(R.string.lost_on_you);

                        //updating the image and text from now playing activity when user selects a item
                        mMyInterface.updateImage(R.drawable.lp);
                        mMyInterface.updateText(R.string.lost_on_you);

                    }
                    if (songs.get(2) == songs.get(position)) {
                        //updating the image and text from footer when user selects a item
                        mFooterImage.setImageResource(R.drawable.stay_high);
                        mFooterTextView.setText(R.string.stay_high_remix);

                        //updating the image and text from now playing activity when user selects a item
                        mMyInterface.updateImage(R.drawable.stay_high);
                        mMyInterface.updateText(R.string.stay_high_remix);

                    }
                    if (songs.get(3) == songs.get(position)) {
                        //updating the image and text from footer when user selects a item
                        mFooterImage.setImageResource(R.drawable.alt_j);
                        mFooterTextView.setText(R.string.something_good);

                        //updating the image and text from now playing activity when user selects a item
                        mMyInterface.updateImage(R.drawable.alt_j);
                        mMyInterface.updateText(R.string.something_good);

                    }
                    if (songs.get(4) == songs.get(position)) {
                        //updating the image and text from footer when user selects a item
                        mFooterImage.setImageResource(R.drawable.greenday);
                        mFooterTextView.setText(R.string.bulevard_of_broken_dreams);

                        //updating the image and text from now playing activity when user selects a item
                        mMyInterface.updateImage(R.drawable.greenday);
                        mMyInterface.updateText(R.string.bulevard_of_broken_dreams);

                    }
                    if (songs.get(5) == songs.get(position)) {
                        //updating the image and text from footer when user selects a item
                        mFooterImage.setImageResource(R.drawable.little_green_bag);
                        mFooterTextView.setText(R.string.little_green_bag);

                        //updating the image and text from now playing activity when user selects a item
                        mMyInterface.updateImage(R.drawable.little_green_bag);
                        mMyInterface.updateText(R.string.little_green_bag);
                    }
                    if (songs.get(6) == songs.get(position)) {
                        //updating the image and text from footer when user selects a item
                        mFooterImage.setImageResource(R.drawable.american_money);
                        mFooterTextView.setText(R.string.american_money);

                        //updating the image and text from now playing activity when user selects a item
//                        mMyInterface.updateImage(R.drawable.american_money);
                        mMyInterface.updateText(R.string.american_money);

                    }
                    if (songs.get(7) == songs.get(position)) {
                        //updating the image and text from footer when user selects a item
                        mFooterImage.setImageResource(R.drawable.false_alarm);
                        mFooterTextView.setText(R.string.false_alarm);

                        //updating the image and text from now playing activity when user selects a item
                        mMyInterface.updateImage(R.drawable.false_alarm);
                        mMyInterface.updateText(R.string.false_alarm);

                    }
                    if (songs.get(8) == songs.get(position)) {
                        //updating the image and text from footer when user selects a item
                        mFooterImage.setImageResource(R.drawable.lifted_up);
                        mFooterTextView.setText(R.string.lifted_up);

                        //updating the image and text from now playing activity when user selects a item
                        mMyInterface.updateImage(R.drawable.lifted_up);
                        mMyInterface.updateText(R.string.lifted_up);

                    }
                    if (songs.get(9) == songs.get(position)) {
                        //updating the image and text from footer when user selects a item
                        mFooterImage.setImageResource(R.drawable.sweet_dreams);
                        mFooterTextView.setText(R.string.sweet_dreams);

                        //updating the image and text from now playing activity when user selects a item
                        mMyInterface.updateImage(R.drawable.sweet_dreams);
                        mMyInterface.updateText(R.string.sweet_dreams);

                    }
                    if (songs.get(10) == songs.get(position)) {
                        //updating the image and text from footer when user selects a item
                        mFooterImage.setImageResource(R.drawable.sky_full_of_stars);
                        mFooterTextView.setText(R.string.sky_full_of_stars);

                        //updating the image and text from now playing activity when user selects a item
                        mMyInterface.updateImage(R.drawable.sky_full_of_stars);
                        mMyInterface.updateText(R.string.sky_full_of_stars);

                    }
                    if (songs.get(11) == songs.get(position)) {
                        //updating the image and text from footer when user selects a item
                        mFooterImage.setImageResource(R.drawable.break_the_rules);
                        mFooterTextView.setText(R.string.break_the_rules);

                        //updating the image and text from now playing activity when user selects a item
                        mMyInterface.updateImage(R.drawable.break_the_rules);
                        mMyInterface.updateText(R.string.break_the_rules);
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
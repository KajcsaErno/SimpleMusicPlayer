package com.example.android.simplemusicplayer;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SongActivity extends AppCompatActivity {

    //Handles playback of all the sound files
    public static MediaPlayer mMediaPlayer;
    private ImageView mFooterImage;
    private ImageView mFooterPlayIcon;
    private TextView mFooterTextView;
    private ListView mListView;
    private Song selectedSong = null;
    //Handles audio focus when playing a sound file
    private AudioManager mAudioManager;
    private View previousView;
    private int currentPlayingSong = 0;
    private ArrayList<Song> songsList = new ArrayList<>();
    private Song songPosition;


    //This listener gets triggered whenever the audio focus changes (i.e., we gain or lose audio focus because of another app or device).

    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) {
                // The AUDIOFOCUS_LOSS_TRANSIENT case means that we've lost audio focus for a short amount of time.
                // The AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK case means that our app is allowed to continue playing sound but at a lower volume.
                if (mMediaPlayer.isPlaying()) {
                    mMediaPlayer.pause();
                }
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                if (mMediaPlayer.isPlaying()) {
                    mMediaPlayer.setVolume(0.1f, 0.1f);
                }
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                if (mMediaPlayer == null)
                    // The AUDIOFOCUS_GAIN case means we have regained focus and can resume playback.
                    mMediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                // The AUDIOFOCUS_LOSS case means we've lost audio focus and Stop playback and clean up resources
                if (mMediaPlayer.isPlaying()) mMediaPlayer.stop();
                releaseMediaPlayer();
                mMediaPlayer = null;
            }
        }
    };
    //This listener gets triggered when the {@link MediaPlayer} has completed playing the audio file.
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            // Now that the sound file has finished playing, release the media player resources.
            releaseMediaPlayer();
            mFooterPlayIcon.setImageResource(R.drawable.ic_play_arrow_white_36dp);
            mListView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.songs_list);

        // back button
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        //request audio focus
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        mFooterImage = findViewById(R.id.footer_image_view);
        mFooterTextView = findViewById(R.id.footer_text_view);

        LinearLayout mLinearLayout = findViewById(R.id.footer_container);

        ImageView mFooterSkipPreviousIcon = findViewById(R.id.footer_skip_previous_icon);
        mFooterPlayIcon = findViewById(R.id.footer_play_icon);
        final ImageView mFooterSkipeNextIcon = findViewById(R.id.footer_skip_next_icon);

        mListView = findViewById(R.id.list);

        // Create a list of songsList

        songsList = new ArrayList<>();
        songsList.add(new Song(getResources().getString(R.string.mentirosa), getResources().getString(R.string.rafaga), R.drawable.rafaga, R.raw.rafaga_mentirosa));
        songsList.add(new Song(getResources().getString(R.string.lost_on_you), getResources().getString(R.string.lp), R.drawable.lp, R.raw.lost_on_you));
        songsList.add(new Song(getResources().getString(R.string.stay_high), getResources().getString(R.string.tove_lo), R.drawable.stay_high, R.raw.stay_high));
        songsList.add(new Song(getResources().getString(R.string.something_good), getResources().getString(R.string.altJ), R.drawable.alt_j, R.raw.something_good));
        songsList.add(new Song(getResources().getString(R.string.bulevard), getResources().getString(R.string.green_day), R.drawable.greenday, R.raw.boulevard_of_broken_dreams));
        songsList.add(new Song(getResources().getString(R.string.little_green_bag), getResources().getString(R.string.geoge_baker), R.drawable.little_green_bag, R.raw.little_green_bag));
        songsList.add(new Song(getResources().getString(R.string.american_money), getResources().getString(R.string.borns), R.drawable.american_money, R.raw.american_money));
        songsList.add(new Song(getResources().getString(R.string.false_alarm), getResources().getString(R.string.the_weeken), R.drawable.false_alarm, R.raw.false_alarm));
        songsList.add(new Song(getResources().getString(R.string.lifted_up), getResources().getString(R.string.lifted_up), R.drawable.lifted_up, R.raw.lifted_up));
        songsList.add(new Song(getResources().getString(R.string.sweet_dream), getResources().getString(R.string.x_man), R.drawable.sweet_dreams, R.raw.sweet_dreams));
        songsList.add(new Song(getResources().getString(R.string.a_sky_full_of_starts), getResources().getString(R.string.coldplay), R.drawable.sky_full_of_stars, R.raw.sky_full_of_stars));
        songsList.add(new Song(getResources().getString(R.string.break_the_rules), getResources().getString(R.string.charli_xcx), R.drawable.break_the_rules, R.raw.break_the_rules));
        songsList.add(new Song(getResources().getString(R.string.gandalf_sax), getResources().getString(R.string.epic_sax_guy), R.drawable.gandalf, R.raw.gandalf));
        songsList.add(new Song(getResources().getString(R.string.blue), getResources().getString(R.string.eiffel_65), R.drawable.blue, R.raw.eiffel_65_blue_kny_factory_remix));
        songsList.add(new Song(getResources().getString(R.string.the_handler), getResources().getString(R.string.muse), R.drawable.muse, R.raw.muse_the_handler));

        // The adapter knows how to create list items for each item in the list.
        final SongAdapter adapter = new SongAdapter(this, songsList);

        mListView.setAdapter(adapter);
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                // getting the positions for the following objects
                selectedSong = songsList.get(i);
                openNowPlayingActivity();
                return true;
            }
        });


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Release the media player if it currently exists because we are about to  play a different sound file
                releaseMediaPlayer();

                songPosition = songsList.get(position);
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    // We have audio focus now.
                    // Create and setup the {@link MediaPlayer} for the audio resource associated
                    // with the current word
                    mMediaPlayer = MediaPlayer.create(SongActivity.this, songPosition.getSoundResourceId());

                    // Start the audio file
                    mMediaPlayer.start();

                    // Setup a listener on the media player, so that we can stop and release the media player once the sound has finished playing.
                    mMediaPlayer.setOnCompletionListener(mCompletionListener);

                    //setting the play button to pause
                    mFooterPlayIcon.setImageResource(R.drawable.ic_pause_white_36dp);

                    //changing the background color when a music is selected
                    mListView.setBackgroundColor(getResources().getColor(R.color.myGrey));

                    //changing the color of the textViews when the user selects a songPosition from the list
                    if (previousView == null) {
                        previousView = view;
                        changeColor(R.color.myBlue, view);
                    } else {
                        changeColor(R.color.white, previousView);
                        changeColor(R.color.myBlue, view);
                        previousView = view;
                    }

                    //scrolling text animation start
                    mFooterTextView.setSelected(true);

                    //updating the footer image view and text view when user selects a item from the list
                    mFooterImage.setImageResource(songPosition.getImageResourceId());
                    mFooterTextView.setText(String.format("%s - %s", songPosition.getArtistName(), songPosition.getSongName()));

                    // getting the positions for the following object
                    //selectedSong = songPosition;
                }
            }
        });

        mFooterPlayIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
                    mMediaPlayer.pause();
                    mFooterPlayIcon.setImageResource(R.drawable.ic_play_arrow_white_36dp);
                    mListView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                } else {
                    if (mMediaPlayer != null) {
                        mMediaPlayer.start();
                        mFooterPlayIcon.setImageResource(R.drawable.ic_pause_white_36dp);
                        mListView.setBackgroundColor(getResources().getColor(R.color.myGrey));
                    }
                }
            }
        });

        mFooterSkipPreviousIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SongActivity.this, R.string.previous, Toast.LENGTH_SHORT).show();
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
                return true;
            }
        });

        mFooterSkipeNextIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SongActivity.this, R.string.next, Toast.LENGTH_SHORT).show();
           /*     if (mMediaPlayer != null) {
                    mMediaPlayer.stop();
                    releaseMediaPlayer();
                    playNextSong();
                    try {
                        mMediaPlayer.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    mMediaPlayer.start();

                }*/
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
                return true;
            }

        });

        if (mLinearLayout != null) {
            mLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openNowPlayingActivity();
                }
            });
        }
    }
    private void playNextSong() {
        if (currentPlayingSong < songsList.size()) {
            currentPlayingSong++;
            // mMediaPlayer.setDataSource(songsList.get(currentPlayingSong));
            //mAudioManager = songsList.get(myIndex);
        }
        if (currentPlayingSong >= songsList.size()) {
            currentPlayingSong = 0;
        }

//        if (myIndex == songsList.size() - 1) {
//            //if last in playlist
//            myIndex =0;
//            mAudioManager = songsList.get(myIndex);
//        }

    }

    private void changeColor(int resId, View view) {
        ((TextView) view.findViewById(R.id.song_name)).setTextColor(getResources().getColor(resId));
        ((TextView) view.findViewById(R.id.artist_name)).setTextColor(getResources().getColor(resId));

    }

    public void openNowPlayingActivity() {
        if (selectedSong != null) {
            Intent openActivity = new Intent(SongActivity.this, NowPlayingActivity.class);
            openActivity.putExtra("songName", selectedSong.getSongName());
            openActivity.putExtra("artistName", selectedSong.getArtistName());
            openActivity.putExtra("songImage", selectedSong.getImageResourceId());
            startActivity(openActivity);
        }
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
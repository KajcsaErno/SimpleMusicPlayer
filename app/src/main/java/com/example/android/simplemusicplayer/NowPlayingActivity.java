package com.example.android.simplemusicplayer;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class NowPlayingActivity extends AppCompatActivity {

    private ImageView playIcon;
    private TextView seekBarInteger;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.now_playing);

        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        String songName = null;
        String artistName = null;
        int songImage = 0;
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            songName = bundle.getString("songName");
            artistName = bundle.getString("artistName");
            songImage = bundle.getInt("songImage");

        }


        TextView mNowPlayingSong = findViewById(R.id.now_playing_song);
        TextView mNowPlayingArtist = findViewById(R.id.now_playing_artist);
        ImageView mNowPLayingImage = findViewById(R.id.now_playing_image);

        if (songName != null) mNowPlayingSong.setText(songName);
        if (artistName != null) mNowPlayingArtist.setText(artistName);
        if (songImage != 0) mNowPLayingImage.setImageResource(songImage);

        playIcon = findViewById(R.id.play_icon);
        ImageView likeIcon = findViewById(R.id.like_icon);

        seekBarInteger = findViewById(R.id.beginning__text_view);
        SeekBar seekBar = findViewById(R.id.seek_bar);

        ImageView plusIcon = findViewById(R.id.plus_icon);
        ImageView minusIcon = findViewById(R.id.minus_icon);

        ImageView skipPreviousIcon = findViewById(R.id.skip_previous_icon);
        ImageView skipNextIcon = findViewById(R.id.skip_next_icon);

        ImageView fastForwardIcon = findViewById(R.id.fast_forward_icon);
        ImageView fastRewindIcon = findViewById(R.id.fast_rewind_icon);

        //Text Scrolling Effect for the song text view
        mNowPlayingSong.setSelected(true);

        seekBarInteger.setText(String.format(getString(R.string.covered), seekBar.getProgress(), seekBar.getMax()));
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                progress = i;
                Toast.makeText(getApplicationContext(), "Changing the progress", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Toast.makeText(getApplicationContext(), "Started tracking", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                seekBarInteger.setText(String.format(getString(R.string.covered), progress, seekBar.getMax()));
                Toast.makeText(getApplicationContext(), "Stopped tracking", Toast.LENGTH_SHORT).show();
            }
        });


        final AudioManager audioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);

        playIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (SongActivity.mMediaPlayer != null && SongActivity.mMediaPlayer.isPlaying()) {
                    SongActivity.mMediaPlayer.pause();
                    playIcon.setImageResource(R.drawable.ic_play_arrow_white_36dp);


                } else {
                    if (SongActivity.mMediaPlayer != null) {

                        SongActivity.mMediaPlayer.start();
                        playIcon.setImageResource(R.drawable.ic_pause_white_36dp);

                    }
                }

            }

        });

        likeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(NowPlayingActivity.this, "You liked this song!", Toast.LENGTH_SHORT).show();
            }
        });


        plusIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (audioManager != null) {
                    audioManager.adjustVolume(AudioManager.ADJUST_RAISE, AudioManager.FLAG_PLAY_SOUND);
                }
            }
        });

        minusIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (audioManager != null) {
                    audioManager.adjustVolume(AudioManager.ADJUST_LOWER, AudioManager.FLAG_PLAY_SOUND);
                }
            }
        });


        fastForwardIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long seek = 20000;
                if (SongActivity.mMediaPlayer != null) {
                    int duration = SongActivity.mMediaPlayer.getDuration();
                    if (seek < duration) {
                        int skips = (int) (SongActivity.mMediaPlayer.getCurrentPosition() + seek);
                        SongActivity.mMediaPlayer.seekTo(skips);

                    }
                }
                Toast.makeText(getApplicationContext(), "Fast forwarding...", Toast.LENGTH_SHORT).show();
            }
        });

        fastRewindIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long seek = 20000;
                if (SongActivity.mMediaPlayer != null) {
                    int duration = SongActivity.mMediaPlayer.getDuration();
                    if (seek < duration) {
                        int skips = (int) (SongActivity.mMediaPlayer.getCurrentPosition() - seek);
                        SongActivity.mMediaPlayer.seekTo(skips);

                    }
                }
                Toast.makeText(getApplicationContext(), "Fast rewinding...", Toast.LENGTH_SHORT).show();
            }
        });

        skipNextIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Skip to the next song...", Toast.LENGTH_SHORT).show();

            }
        });

        skipPreviousIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Skip to the previous song...", Toast.LENGTH_SHORT).show();

            }
        });

        seekBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                while (SongActivity.mMediaPlayer.isPlaying()) {
                    SongActivity.mMediaPlayer.getDuration();
                }
            }
        });

    }
}

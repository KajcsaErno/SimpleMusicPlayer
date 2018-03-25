package com.example.android.simplemusicplayer;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MusicPlayerActivity extends AppCompatActivity {

    private ImageView playIcon, plusIcon, minusIcon, skipPreviousIcon, skipNextIcon, fastForwardIcon, fastRewindIcon;
    private SeekBar seekBar;
    private TextView beginningTextVibe, endTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_player);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        playIcon = findViewById(R.id.play_icon);
        beginningTextVibe = findViewById(R.id.beginning__text_view);
        endTextView = findViewById(R.id.end_text_view);
        seekBar = findViewById(R.id.seek_bar);

        plusIcon = findViewById(R.id.plus_icon);
        minusIcon = findViewById(R.id.minus_icon);

        skipPreviousIcon = findViewById(R.id.skip_previous_icon);
        skipNextIcon = findViewById(R.id.skip_next_icon);

        fastForwardIcon = findViewById(R.id.fast_forward_icon);
        fastRewindIcon = findViewById(R.id.fast_rewind_icon);


        beginningTextVibe.setText("Covered:" + seekBar.getProgress() + "/" + seekBar.getMax());
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
                beginningTextVibe.setText("Covered: " + progress + "/" + seekBar.getMax());
                Toast.makeText(getApplicationContext(), "Stopped tracking", Toast.LENGTH_SHORT).show();
            }
        });


        final AudioManager audioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);

//      SongActivity.mMediaPlayer = MediaPlayer.create(this, R.raw.lost_on_you);

        playIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (SongActivity.mMediaPlayer.isPlaying()) {
                    SongActivity.mMediaPlayer.pause();

                    Toast.makeText(getApplicationContext(), "Pausing sound...", Toast.LENGTH_SHORT).show();
                    playIcon.setImageResource(R.drawable.ic_play_arrow_white_36dp);


                } else {

                    SongActivity.mMediaPlayer.start();
                    Toast.makeText(getApplicationContext(), "Playing sound...", Toast.LENGTH_SHORT).show();
                    playIcon.setImageResource(R.drawable.ic_pause_white_36dp);

                }


            }

        });


        plusIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Increasing the volume...", Toast.LENGTH_SHORT).show();
                audioManager.adjustVolume(AudioManager.ADJUST_RAISE, AudioManager.FLAG_PLAY_SOUND);

            }
        });

        minusIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Decreasing the volume...", Toast.LENGTH_SHORT).show();
                audioManager.adjustVolume(AudioManager.ADJUST_LOWER, AudioManager.FLAG_PLAY_SOUND);

            }
        });

        fastForwardIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long seek = 20000;
                int duration = SongActivity.mMediaPlayer.getDuration();
                if (seek < duration) {
                    int skips = (int) (SongActivity.mMediaPlayer.getCurrentPosition() + seek);
                    SongActivity.mMediaPlayer.seekTo(skips);
                }

            }
        });

        fastRewindIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long seek = 20000;
                int duration = SongActivity.mMediaPlayer.getDuration();
                if (seek < duration) {
                    int skips = (int) (SongActivity.mMediaPlayer.getCurrentPosition() - seek);
                    SongActivity.mMediaPlayer.seekTo(skips);
                }

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
                SongActivity.mMediaPlayer.seekTo(0);

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


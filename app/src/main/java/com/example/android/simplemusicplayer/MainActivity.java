package com.example.android.simplemusicplayer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView songsTxtView = findViewById(R.id.songs_txt_view);
        songsTxtView.setOnClickListener(this);

        TextView albumsTxtView = findViewById(R.id.albums_txt_view);
        albumsTxtView.setOnClickListener(this);

    }

    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.songs_txt_view:
                Intent songsIntent = new Intent(MainActivity.this, SongActivity.class);
                startActivity(songsIntent);
                break;


            case R.id.albums_txt_view:
                Intent artistIntent = new Intent(MainActivity.this, ArtistActivity.class);
                startActivity(artistIntent);
                break;
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}





package com.example.android.simplemusicplayer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

public class ArtistActivity extends AppCompatActivity {

      @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.artists_grid);

          ActionBar supportActionBar = getSupportActionBar();
          if (supportActionBar != null) {
              getSupportActionBar().setDisplayHomeAsUpEnabled(true);
          }

        // Create a list of artists
        final ArrayList<Artist> artists = new ArrayList<>();
        artists.add(new Artist("Ráfaga", R.drawable.rafaga));
        artists.add(new Artist("LP", R.drawable.lp));
        artists.add(new Artist("Tove Lo", R.drawable.stay_high));
        artists.add(new Artist( "alt-J", R.drawable.alt_j));
        artists.add(new Artist( "Green Day", R.drawable.greenday));
        artists.add(new Artist( "George Baker", R.drawable.little_green_bag));
        artists.add(new Artist( "BØRNS", R.drawable.american_money));
        artists.add(new Artist( "The Weeknd", R.drawable.false_alarm));
        artists.add(new Artist("Passion Pit", R.drawable.lifted_up));
        artists.add(new Artist("X Men Apocalypse Quicksilver Theme Artist()", R.drawable.sweet_dreams));
        artists.add(new Artist("Coldplay", R.drawable.sky_full_of_stars));
        artists.add(new Artist("Charli XCX", R.drawable.break_the_rules));

        // The adapter knows how to create list items for each item in the list.
       final ArtistAdapter adapter = new ArtistAdapter(this, artists);

        GridView gridView = findViewById(R.id.gridview);

        gridView.setAdapter(adapter);

           gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

               Intent i = new Intent(Intent.ACTION_VIEW);


            @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Release the media player if it currently exists because we are about to
                // play a different sound file

                if (artists.get(0) == artists.get(position)) {
                    String url = "https://rateyourmusic.com/artist/rafaga";
                    i.setData(Uri.parse(url));
                    startActivity(i);
                }
                if (artists.get(1) == artists.get(position)) {
                    String url = "https://rateyourmusic.com/artist/lp-1";
                    i.setData(Uri.parse(url));
                    startActivity(i);

                }
                if (artists.get(2) == artists.get(position)) {
                    String url = "https://rateyourmusic.com/artist/tove-lo";
                    i.setData(Uri.parse(url));
                    startActivity(i);

                }
                if (artists.get(3) == artists.get(position)) {
                    String url = "https://rateyourmusic.com/artist/alt_j";
                    i.setData(Uri.parse(url));
                    startActivity(i);

                }
                if (artists.get(4) == artists.get(position)) {
                    String url = "https://rateyourmusic.com/artist/green-day-1";
                    i.setData(Uri.parse(url));
                    startActivity(i);

                }
                if (artists.get(5) == artists.get(position)) {
                    String url = "https://rateyourmusic.com/artist/george-baker-selection";
                    i.setData(Uri.parse(url));
                    startActivity(i);

                }
                if (artists.get(6) == artists.get(position)) {
                    String url = "https://rateyourmusic.com/artist/borns";
                    i.setData(Uri.parse(url));
                    startActivity(i);

                }
                if (artists.get(7) == artists.get(position)) {
                    String url = "https://rateyourmusic.com/artist/the-weeknd";
                    i.setData(Uri.parse(url));
                    startActivity(i);

                }
                if (artists.get(8) == artists.get(position)) {
                    String url = "https://rateyourmusic.com/artist/passion-pit";
                    i.setData(Uri.parse(url));
                    startActivity(i);

                }
                if (artists.get(9) == artists.get(position)) {
                    String url = "https://rateyourmusic.com/artist/eurythmics";
                    i.setData(Uri.parse(url));
                    startActivity(i);

                }
                if (artists.get(10) == artists.get(position)) {
                    String url = "https://rateyourmusic.com/artist/coldplay";
                    i.setData(Uri.parse(url));
                    startActivity(i);

                }
                if (artists.get(11) == artists.get(position)) {
                    String url = "https://rateyourmusic.com/artist/charli-xcx";
                    i.setData(Uri.parse(url));
                    startActivity(i);
                }

            }
        });


    }




}
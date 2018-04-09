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
        artists.add(new Artist(getResources().getString(R.string.rafaga), R.drawable.rafaga, getResources().getString(R.string.rafaga_website)));
        artists.add(new Artist(getResources().getString(R.string.lp), R.drawable.lp, getResources().getString(R.string.lp_website)));
        artists.add(new Artist(getResources().getString(R.string.tove_lo), R.drawable.stay_high, getResources().getString(R.string.tove_lo_website)));
        artists.add(new Artist(getResources().getString(R.string.altJ), R.drawable.alt_j, getResources().getString(R.string.altJ_websiite)));
        artists.add(new Artist(getResources().getString(R.string.green_day), R.drawable.greenday, getResources().getString(R.string.green_day_website)));
        artists.add(new Artist(getResources().getString(R.string.geoge_baker), R.drawable.little_green_bag, getResources().getString(R.string.george_baker_website)));
        artists.add(new Artist(getResources().getString(R.string.borns), R.drawable.american_money, getResources().getString(R.string.borns_website)));
        artists.add(new Artist(getResources().getString(R.string.the_weeken), R.drawable.false_alarm, getResources().getString(R.string.the_weeken_website)));
        artists.add(new Artist(getResources().getString(R.string.passion_pit), R.drawable.lifted_up, getResources().getString(R.string.passion_pit_website)));
        artists.add(new Artist(getResources().getString(R.string.x_man), R.drawable.sweet_dreams, getResources().getString(R.string.eurythmics_website)));
        artists.add(new Artist(getResources().getString(R.string.coldplay), R.drawable.sky_full_of_stars, getResources().getString(R.string.coldplay_website)));
        artists.add(new Artist(getResources().getString(R.string.charli_xcx), R.drawable.break_the_rules, getResources().getString(R.string.charlie_xcx_website)));
        artists.add(new Artist(getResources().getString(R.string.epic_sax_guy), R.drawable.gandalf, getResources().getString(R.string.gandalf_website)));
        artists.add(new Artist(getResources().getString(R.string.eiffel_65), R.drawable.blue, getResources().getString(R.string.eiffel_65_website)));
        artists.add(new Artist(getResources().getString(R.string.muse), R.drawable.muse, getResources().getString(R.string.muse_website)));

        // The adapter knows how to create list items for each item in the list.
        final ArtistAdapter adapter = new ArtistAdapter(this, artists);

        GridView gridView = findViewById(R.id.gridview);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // opening a website about the current artist
                Artist artist = artists.get(position);
                String url = artist.getArtistWebsite();
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW);
                websiteIntent.setData(Uri.parse(url));
                startActivity(websiteIntent);


            }
        });


    }


}
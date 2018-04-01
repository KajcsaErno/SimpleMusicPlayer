package com.example.android.simplemusicplayer;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ArtistAdapter extends ArrayAdapter<Artist> {

    ArtistAdapter(Activity context, ArrayList<Artist> artists) {
        super(context, 0, artists);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View gridItemView = convertView;
        if (gridItemView == null) {
            gridItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.grid_item, parent, false);
        }

        //getting the position for the current song
        Artist currentArtist = getItem(position);

        if (currentArtist != null) {
            //Setting the artist name
            TextView artistTextView = gridItemView.findViewById(R.id.artist_name);
            artistTextView.setText(currentArtist.getArtistName());

            //Setting the album image
            ImageView imgView = gridItemView.findViewById(R.id.image_view);
            imgView.setImageResource(currentArtist.getImageResourceId());
        }
        return gridItemView;
    }
}

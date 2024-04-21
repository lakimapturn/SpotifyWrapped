package com.example.spotifywrapped.Components;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.spotifywrapped.Models.SpotifyWrapped;
import com.example.spotifywrapped.R;
import com.example.spotifywrapped.State.AppState;

import java.util.ArrayList;

public class SpotifyWrappedView extends AppCompatActivity {

    SpotifyWrapped spotifyWrapped;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spotify_wrapped_list_item);

        spotifyWrapped = AppState.user.getSpotifyWrapped();

        ArrayList<String> topSongs = spotifyWrapped.getTopSongs();
        ArrayList<String> topArtists = spotifyWrapped.getTopArtists();

        LinearLayout songsLayout = findViewById(R.id.top_songs_container);
        LinearLayout artistsLayout = findViewById(R.id.top_artists_container);

        for (int i = 0; i < 5; i++) {
            TextView textView = new TextView(this);
            textView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1.0f
            ));
            textView.setTextColor(Color.WHITE);
            textView.setPadding(20, 5, 20, 5);

            if (i < topSongs.size()) {
                textView.setText(String.format("%d) %s", i + 1, topSongs.get(i)));
            }
            songsLayout.addView(textView);

            textView = new TextView(this);
            textView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1.0f
            ));
            textView.setTextColor(Color.WHITE);
            textView.setPadding(20, 5, 20, 5);

            if (i < topArtists.size()) {
                textView.setText(String.format("%d) %s", i + 1, topArtists.get(i)));
            }
            artistsLayout.addView(textView);
        }
    }
}

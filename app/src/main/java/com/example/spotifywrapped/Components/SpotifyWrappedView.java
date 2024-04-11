package com.example.spotifywrapped.Components;

import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.spotifywrapped.Models.SpotifyWrapped;
import com.example.spotifywrapped.R;
import com.example.spotifywrapped.State.AppState;

public class SpotifyWrappedView extends AppCompatActivity {

    SpotifyWrapped spotifyWrapped;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spotify_wrapped_list_item);

        spotifyWrapped = AppState.user.getSpotifyWrapped();

        ListView topArtistsList = findViewById(R.id.top_artists_list);
        ListView topSongsList = findViewById(R.id.top_songs_list);

        ArrayAdapter<String> topArtistsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, spotifyWrapped.getTopArtists());
        topArtistsList.setAdapter(topArtistsAdapter);
        ArrayAdapter<String> topSongsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, spotifyWrapped.getTopSongs());
        topSongsList.setAdapter(topSongsAdapter);
    }

}

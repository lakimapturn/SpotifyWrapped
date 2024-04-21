package com.example.spotifywrapped.Components;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.spotifywrapped.Models.SpotifyWrapped;
import com.example.spotifywrapped.Models.User;
import com.example.spotifywrapped.R;
import com.example.spotifywrapped.State.AppState;

import java.util.ArrayList;

public class ExplorePageListAdapter extends ArrayAdapter<User> {

    ArrayList<User> list;
    Context context;

    public ExplorePageListAdapter(@NonNull Context context, ArrayList<User> list) {
        super(context, R.layout.community_list_item, list);
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        User item = getItem(position);

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.community_list_item, parent, false);
        }

        TextView username = view.findViewById(R.id.username);
        username.setText(item.getUsername());

        SpotifyWrapped spotifyWrapped = item.getSpotifyWrapped();

        ArrayList<String> topSongs = spotifyWrapped.getTopSongs();
        ArrayList<String> topArtists = spotifyWrapped.getTopArtists();

        LinearLayout songsLayout = view.findViewById(R.id.top_songs_container);
        LinearLayout artistsLayout = view.findViewById(R.id.top_artists_container);

        songsLayout.removeAllViews();
        artistsLayout.removeAllViews();

        for (int i = 0; i < 5; i++) {
            TextView textView = new TextView(view.getContext());
            textView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1.0f
            ));
            textView.setTextColor(Color.WHITE);
            textView.setPadding(20, 10, 20, 10);

            if (i < topSongs.size()) {
                textView.setText(String.format("%d) %s", i + 1, topSongs.get(i)));
            }
            songsLayout.addView(textView);

            textView = new TextView(view.getContext());
            textView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1.0f
            ));
            textView.setTextColor(Color.WHITE);
            textView.setPadding(20, 10, 20, 10);


            if (i < topArtists.size()) {
                textView.setText(String.format("%d) %s", i + 1, topArtists.get(i)));
            }
            artistsLayout.addView(textView);
        }

        return view;
    }
}

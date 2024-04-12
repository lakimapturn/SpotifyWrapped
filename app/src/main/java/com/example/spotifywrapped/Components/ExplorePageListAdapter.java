package com.example.spotifywrapped.Components;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.spotifywrapped.Models.SpotifyWrapped;
import com.example.spotifywrapped.Models.User;
import com.example.spotifywrapped.R;

import java.util.ArrayList;

public class ExplorePageListAdapter extends ArrayAdapter<User> {

    ArrayList<User> list;
    Context context;

    public ExplorePageListAdapter(@NonNull Context context, ArrayList<User> list) {
        super(context, R.layout.community_list_item, list);
        // test data
        list.add(new User("", "example", "", "",
                new SpotifyWrapped(new String[]{"Weekend", "Someone else", "Jesoos"}, new String[]{"Someone", "Lol", "kys"}, 0, "10",
                        false, new ArrayList<>()), new ArrayList<>()));
        list.add(new User("", "example1", "", "",
                new SpotifyWrapped(new String[]{}, new String[]{}, 0, "10",
                        false, new ArrayList<>()), new ArrayList<>()));
        list.add(new User("", "example2", "", "",
                new SpotifyWrapped(new String[]{}, new String[]{}, 0, "10",
                        false, new ArrayList<>()), new ArrayList<>()));

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

        SpotifyWrapped spotifyWrapped = item.getSpotifyWrapped();

        ListView topArtistsList = view.findViewById(R.id.top_artists_list);
        ListView topSongsList = view.findViewById(R.id.top_songs_list);

        ArrayAdapter<String> topArtistsAdapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_list_item_1, spotifyWrapped.getTopArtists());
        topArtistsList.setAdapter(topArtistsAdapter);
        ArrayAdapter<String> topSongsAdapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_list_item_1, spotifyWrapped.getTopSongs());
        topSongsList.setAdapter(topSongsAdapter);

        return view;
    }
}

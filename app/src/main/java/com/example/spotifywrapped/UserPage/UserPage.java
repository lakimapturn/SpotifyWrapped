package com.example.spotifywrapped.UserPage;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.spotifywrapped.Helper.Helper;
import com.example.spotifywrapped.Models.SpotifyWrapped;
import com.example.spotifywrapped.R;
import com.example.spotifywrapped.State.AppState;
import com.google.firebase.auth.FirebaseAuth;

public class UserPage extends Fragment {

    SpotifyWrapped spotifyWrapped;
    Switch isSpotifyWrappedPublic;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View UserPageView = inflater.inflate(R.layout.user_page, container, false);

        spotifyWrapped = AppState.user.getSpotifyWrapped();

        return UserPageView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        isSpotifyWrappedPublic = view.findViewById(R.id.private_btn);
        isSpotifyWrappedPublic.setOnCheckedChangeListener((CompoundButton buttonView, boolean isChecked) -> {
                spotifyWrapped.setPublic(isChecked);
                Helper.writeToFirebase(FirebaseAuth.getInstance().getUid());
            }
        );

        TextView textView = view.findViewById(R.id.userpage_username);
        textView.setText(AppState.user.getUsername());

        ListView topArtistsList = view.findViewById(R.id.top_artists_list);
        ListView topSongsList = view.findViewById(R.id.top_songs_list);

        ArrayAdapter<String> topArtistsAdapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1, spotifyWrapped.getTopArtists());
        topArtistsList.setAdapter(topArtistsAdapter);
        ArrayAdapter<String> topSongsAdapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1, spotifyWrapped.getTopSongs());
        topSongsList.setAdapter(topSongsAdapter);

        Helper.writeToFirebase(FirebaseAuth.getInstance().getUid());

    }
}

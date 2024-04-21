package com.example.spotifywrapped.UserPage;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
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

import java.util.ArrayList;

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

        spotifyWrapped = AppState.user.getSpotifyWrapped();

        ArrayList<String> topSongs = spotifyWrapped.getTopSongs();
        ArrayList<String> topArtists = spotifyWrapped.getTopArtists();

        LinearLayout songsLayout = view.findViewById(R.id.top_songs_container);
        LinearLayout artistsLayout = view.findViewById(R.id.top_artists_container);

        for (int i = 0; i < 5; i++) {
            textView = new TextView(view.getContext());
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

            textView = new TextView(view.getContext());
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

        // TODO: move this to the main page
        Helper.writeToFirebase(FirebaseAuth.getInstance().getUid());

    }
}

package com.example.spotifywrapped.UserPage;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.spotifywrapped.R;
import com.example.spotifywrapped.State.AppState;

import java.util.concurrent.ExecutionException;

public class LLMChat extends Fragment {

    private static final String TAG = "geminiInteraction";
    private TextView dynamicText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View LLMChatView = inflater.inflate(R.layout.gemini_interaction, container, false);

        dynamicText = LLMChatView.findViewById(R.id.dynamic_text);

        LLMChatView.findViewById(R.id.concert_btn).setOnClickListener(l -> generateConcertOutfit());
        LLMChatView.findViewById(R.id.movie_btn).setOnClickListener(l -> generateMovie());
        LLMChatView.findViewById(R.id.vibe_btn).setOnClickListener(l -> generateVibe());
        LLMChatView.findViewById(R.id.video_game_btn).setOnClickListener(l -> generateVideoGame());

        return LLMChatView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private class CallLLMChatTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... prompts) {
            String prompt = prompts[0];
            try {
                return com.example.spotifywrapped.LLMChat.gemini(prompt);
            } catch (ExecutionException | InterruptedException e) {
                Log.e(TAG, "Error calling Gemini", e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(String response) {
            if (response != null) {
                dynamicText.setText(response);
            } else {
                // Handle error case (e.g., display an error message)
            }
        }
    }

    private void generateConcertOutfit() {
        System.out.println("hello!");

        String topArtists = AppState.user.getSpotifyWrapped().getTopArtists().toString();
        String topSongs = AppState.user.getSpotifyWrapped().getTopSongs().toString();

        String prompt = "Based on your top artists: " + topArtists + " and top songs: " + topSongs +
                ", give me an outfit to wear to a concert. One sentence only.";

        dynamicText.setText("Waiting for Gemini response...");
        new CallLLMChatTask().execute(prompt);
    }

    private void generateVideoGame() {
        String topArtists = AppState.user.getSpotifyWrapped().getTopArtists().toString();
        String topSongs = AppState.user.getSpotifyWrapped().getTopSongs().toString();

        String prompt = "Based on your top artists: " + topArtists + " and top songs: " + topSongs +
                ", give me a video game you think I would enjoy. One sentence only.";

        dynamicText.setText("Waiting for Gemini response...");
        new CallLLMChatTask().execute(prompt);

    }

    private void generateMovie() {
        String topArtists = AppState.user.getSpotifyWrapped().getTopArtists().toString();
        String topSongs = AppState.user.getSpotifyWrapped().getTopSongs().toString();

        String prompt = "Based on your top artists: " + topArtists + " and top songs: " + topSongs +
                ", give me a movie you think I would enjoy. One sentence only.";

        dynamicText.setText("Waiting for Gemini response...");
        new CallLLMChatTask().execute(prompt);

    }

    private void generateVibe() {
        String topArtists = AppState.user.getSpotifyWrapped().getTopArtists().toString();
        String topSongs = AppState.user.getSpotifyWrapped().getTopSongs().toString();

        String prompt = "Based on your top artists: " + topArtists + " and top songs: " + topSongs +
                ", what vibe do you think I give off. One sentence only.";

        dynamicText.setText("Waiting for Gemini response...");
        new CallLLMChatTask().execute(prompt);

    }
}

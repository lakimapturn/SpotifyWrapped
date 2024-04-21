package com.example.spotifywrapped.UserPage;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.spotifywrapped.Auth.AuthPage;
import com.example.spotifywrapped.LLMChat;
import com.google.android.material.tabs.TabLayout;

import com.example.spotifywrapped.Models.SpotifyWrapped;
import com.example.spotifywrapped.R;
import com.example.spotifywrapped.State.AppState;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class geminiInteraction extends Activity implements TabLayout.OnTabSelectedListener {

    private static final String TAG = "geminiInteraction";

    private TabLayout tabs;
    private TextView dynamicText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gemini_interaction);

        tabs = findViewById(R.id.tabs);
        dynamicText = findViewById(R.id.dynamic_text);

        // Set this activity as the tab listener
        tabs.addOnTabSelectedListener(this);

        // Update text initially (optional)
        updateTextBasedOnTab(tabs.getSelectedTabPosition());
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        updateTextBasedOnTab(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        // Optional handling for unselecting a tab
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
        // throw toast
        Toast.makeText(geminiInteraction.this, "Already on this tab",
                Toast.LENGTH_SHORT).show();
    }

    String topArtists = AppState.user.getSpotifyWrapped().getTopArtists().toString();
    String topSongs = AppState.user.getSpotifyWrapped().getTopSongs().toString();

    private void updateTextBasedOnTab(int position) {
        String prompt;
        switch (position) {
            case 0:
                prompt = "Based on your top artists: " + topArtists + " and top songs: " + topSongs +
                        ", give me an outfit to wear to a concert. One sentence only.";
                break;
            case 1:
                prompt = "Based on your top genre: " + AppState.user.getSpotifyWrapped().getTopGenre() +
                        ", give me a movie you think I would enjoy. One sentence only.";
                break;
            case 2:
                prompt = "Based on your top genre: " + AppState.user.getSpotifyWrapped().getTopGenre() +
                        ", give me a video game you think I would enjoy. One sentence only.";
                break;
            default:
                prompt = "Based on your top genre: " + AppState.user.getSpotifyWrapped().getTopGenre() +
                        ", what vibe do you think I give off. One sentence only.";
        }

        dynamicText.setText(prompt); // Set the prompt in the TextView
        callLLMChat(prompt);
    }

    private void callLLMChat(String prompt) {
        try {
            String response = LLMChat.gemini(prompt);
            dynamicText.post(new Runnable() {
                @Override
                public void run() {
                    dynamicText.setText(response);  // Update TextView on UI thread
                }
            });
        } catch (ExecutionException | InterruptedException e) {
            Log.e(TAG, "Error calling Gemini", e);
        }
    }
}

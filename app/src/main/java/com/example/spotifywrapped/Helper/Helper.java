package com.example.spotifywrapped.Helper;

import android.util.Log;

import com.example.spotifywrapped.Models.User;
import com.example.spotifywrapped.State.AppState;
import com.google.firebase.firestore.FirebaseFirestore;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import okhttp3.OkHttpClient;

public final class Helper {
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();  // Changed from FirebaseDatabase to FirebaseFirestore
    public static final String REDIRECT_URI = "spotifywrapped://auth";
    public static final String CLIENT_ID = "3fb1efe2e6a04635a160cf18b3bc584b";
    public static final int AUTH_TOKEN_REQUEST_CODE = 0;
    private final OkHttpClient mOkHttpClient = new OkHttpClient();

    public static String[] parseTopArtists(JSONArray items) throws JSONException {
        String[] topSongs = new String[5];

        try {
            for (int i = 0; i < topSongs.length; i++) {
                JSONObject artist = items.getJSONObject(i);
                topSongs[i] = String.valueOf(artist.get("name"));
            }
        } catch (Exception e) {
            Log.d("Top Artists Error", e.toString());
        }

        System.out.println(Arrays.asList(topSongs));

        return topSongs;
    }

    public static String[] parseTopSongs(JSONArray items) {
        String[] topSongs = new String[5];

        try {
            for (int i = 0; i < topSongs.length; i++) {
                JSONObject album = items.getJSONObject(i);
                Log.d("Top Albums", album.toString());
                topSongs[i] = String.valueOf(album.get("name"));
            }
        } catch (Exception e) {
            Log.d("Top Songs Error", e.toString());
        }

        System.out.println(Arrays.asList(topSongs));

        return topSongs;
    }

    public static void writeToFirebase(String userId, String[] topArtists, String[] topSongs) {
        // Create a HashMap to store artist and song data
        HashMap<String, Object> wrappedData = new HashMap<>();
        wrappedData.put("topArtists", Arrays.asList(topArtists));
        wrappedData.put("topSongs", Arrays.asList(topSongs));
        wrappedData.put("isPublic", AppState.user.getSpotifyWrapped().isPublic());

        // Add data to Firestore with user ID as document ID
        db.collection("users").document(userId)
                .set(wrappedData)
                .addOnSuccessListener(unused -> Log.d("Realtime", "Data written successfully!"))
                .addOnFailureListener(e -> Log.w("Realtime", "Error writing data: ", e));
    }

    public static void setWrappedData(User user, String[] topArtists, String[] topSongs, boolean isPublic) {
        AppState.user.getSpotifyWrapped().setTopArtists(topArtists);
        AppState.user.getSpotifyWrapped().setTopSongs(topSongs);
        AppState.user.getSpotifyWrapped().setPublic(isPublic);
    }
}
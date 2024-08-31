package com.example.spotifywrapped.Helper;

import android.util.Log;
import android.widget.Adapter;

import androidx.annotation.NonNull;

import com.example.spotifywrapped.Models.SpotifyWrapped;
import com.example.spotifywrapped.Models.User;
import com.example.spotifywrapped.State.AppState;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import okhttp3.OkHttpClient;

public final class Helper {
    private static FirebaseDatabase db = FirebaseDatabase.getInstance();
    public static DatabaseReference rootRef = db.getReference();
    public static final String REDIRECT_URI = "spotifywrapped://auth";
    public static final String CLIENT_ID = ""; // find client id in github repo secrets
    public static final int AUTH_TOKEN_REQUEST_CODE = 0;
    private final OkHttpClient mOkHttpClient = new OkHttpClient();

    public static ArrayList<String> parseTopArtists(JSONArray items) {
        ArrayList<String> topSongs = new ArrayList<>(5);

        try {
            for (int i = 0; i < 5; i++) {
                JSONObject artist = items.getJSONObject(i);
                topSongs.add(String.valueOf(artist.get("name")));
            }
        } catch (Exception e) {
            Log.d("Top Artists Error", e.toString());
        }

        return topSongs;
    }
    public static ArrayList<String> parseTopSongs(JSONArray items) {
        ArrayList<String> topAlbums = new ArrayList<>(5);

        try {
            for (int i = 0; i < 5; i++) {

                JSONObject album = items.getJSONObject(i);
                Log.d("Top Albums", album.toString());

                topAlbums.add(String.valueOf(album.get("name")));
            }
        } catch (Exception e) {
            Log.d("Top Albums Error", e.toString());
        }

        System.out.println(topAlbums);

        return topAlbums;
    }

    public static void writeToFirebase(String userId) {
        // Create a HashMap to store artist and song data
        HashMap<String, Object> wrappedData = new HashMap<>();
        wrappedData.put("topArtists", AppState.user.getSpotifyWrapped().getTopArtists());
        wrappedData.put("topSongs", AppState.user.getSpotifyWrapped().getTopSongs());
        wrappedData.put("isPublic", AppState.user.getSpotifyWrapped().isPublic());
        wrappedData.put("user", AppState.user.getUsername());

        System.out.println(wrappedData);

        DatabaseReference spotifyWrappedRef = rootRef.child("spotifyWrapped");

        String child = FirebaseAuth.getInstance().getUid() == null ? "eg" :
                FirebaseAuth.getInstance().getUid();

        spotifyWrappedRef.child(child)
                .setValue(wrappedData)
                .addOnSuccessListener(unused -> Log.d("Realtime", "Data written successfully!"))
                .addOnFailureListener(e -> Log.w("Realtime", "Error writing data: ", e));
    }

    public static void setWrappedData(User user, ArrayList<String> topArtists, ArrayList<String> topSongs, boolean isPublic) {
        AppState.user.getSpotifyWrapped().setTopArtists(topArtists);
        AppState.user.getSpotifyWrapped().setTopSongs(topSongs);
        AppState.user.getSpotifyWrapped().setPublic(isPublic);
    }


}

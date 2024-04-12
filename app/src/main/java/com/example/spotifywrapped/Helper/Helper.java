package com.example.spotifywrapped.Helper;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.OkHttpClient;

public final class Helper {
    public static final String REDIRECT_URI = "spotifywrapped://auth";
    public static final String CLIENT_ID = "c04b185b0ff6465e89a82be5d5c860ac";
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

        return topSongs;
    }

    public static String[] parseTopAlbums(JSONArray items) {
        String[] topAlbums = new String[5];

        try {
            for (int i = 0; i < topAlbums.length; i++) {

                JSONObject album = items.getJSONObject(i);
                Log.d("Top Albums", album.toString());

                topAlbums[i] = String.valueOf(album.get("name"));
            }
        } catch (Exception e) {
            Log.d("Top Albums Error", e.toString());
        }

        System.out.println(Arrays.asList(topAlbums));

        return topAlbums;
    }
}

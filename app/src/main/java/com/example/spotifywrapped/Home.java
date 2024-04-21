package com.example.spotifywrapped;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.spotifywrapped.CommunityPage.Explore;
import com.example.spotifywrapped.Helper.Helper;
import com.example.spotifywrapped.Helper.TokenClass;
import com.example.spotifywrapped.Models.SpotifyWrapped;
import com.example.spotifywrapped.Models.User;
import com.example.spotifywrapped.State.AppState;
import com.example.spotifywrapped.UserPage.LLMChat;
import com.example.spotifywrapped.databinding.HomeBinding;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Home extends Fragment {
    private HomeBinding binding;
    private TextView profileTextView;
    private Call mCall;
    private final OkHttpClient mOkHttpClient = new OkHttpClient();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = HomeBinding.inflate(inflater, container, false);
        onGetUserProfileClicked();

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new LLMChat()).commit();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Fragment fragment = null;

                switch (position) {
                    case 0:
                        fragment = new LLMChat();
                        break;
                    case 1:
                        fragment = new Explore();
                        break;
                    case 2:
                        fragment = new AccountInfo();
                        break;
                }

                if (fragment != null) {
                    FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container, fragment).commit();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    public void onGetUserProfileClicked() {
        if (TokenClass.getInstance().getFireAccessToken() == null) {
            Toast.makeText(getContext(), "Error in getting access token from Firebase CLS.", Toast.LENGTH_SHORT).show();
            return;
        }

        AppState.user.setSpotifyToken(TokenClass.getInstance().getFireAccessToken());
        AppState.user.setSpotifyWrapped(new SpotifyWrapped());

        final Request tracksRequest = new Request.Builder()
                .url("https://api.spotify.com/v1/me/top/tracks?time_range=long_term&limit=5")
                .addHeader("Authorization", "Bearer " + TokenClass.getInstance().getFireAccessToken())
                .build();
        cancelCall();
        processData(tracksRequest, ProcessType.tracks);

        final Request artistsRequest = new Request.Builder()
                .url("https://api.spotify.com/v1/me/top/artists?time_range=long_term&limit=5")
                .addHeader("Authorization", "Bearer " + TokenClass.getInstance().getFireAccessToken())
                .build();
        cancelCall();
        processData(artistsRequest, ProcessType.artists);

    }

    private void onGetAccountInfoClicked() {
        NavHostFragment.findNavController(this).navigate(R.id.accountInfo);
    }

    public void processData(Request request, ProcessType type) {
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("HTTP", "Failed to fetch data: " + e);
                Toast.makeText(getContext(), "Failed to fetch data, watch Logcat for more details",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    final JSONObject jsonObject = new JSONObject(response.body().string());
                    JSONArray items = (JSONArray) jsonObject.get("items");
                    System.out.println("here!");

                    if (type == ProcessType.artists) {
                        AppState.user.getSpotifyWrapped().setTopArtists(Helper.parseTopArtists(items));
                    } else if (type == ProcessType.tracks) {
                        AppState.user.getSpotifyWrapped().setTopSongs(Helper.parseTopSongs(items));
                    }

//                    HomeDirections.ActionHomeToSummary action = HomeDirections.actionHomeToSummary(jsonObject.toString());

                    getActivity().runOnUiThread(() -> {
                        if (isAdded()) {
//                            NavHostFragment.findNavController(Home.this).navigate(action);
                        }
                    });


                } catch (JSONException e) {
                    System.out.println(e);
                    Log.d("JSON", "Failed to parse data: " + e);
                    AppState.user.getSpotifyWrapped().setTopSongs(new ArrayList<>());
                    AppState.user.getSpotifyWrapped().setTopArtists(new ArrayList<>());
                }
            }
        });
    }

    private void cancelCall() {
        if (mCall != null) {
            mCall.cancel();
        }
    }

    private enum ProcessType {
        tracks,
        artists
    }
}



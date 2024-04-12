package com.example.spotifywrapped;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.spotifywrapped.Auth.Login;
import com.example.spotifywrapped.Helper.TokenClass;
import com.example.spotifywrapped.databinding.HomeBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

public class Home extends Fragment {
    private HomeBinding binding;
    private TextView profileTextView;
    private Call mCall;
    private final OkHttpClient mOkHttpClient = new OkHttpClient();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = HomeBinding.inflate(inflater, container, false);
        binding.profileBtn.setOnClickListener(v -> onGetUserProfileClicked());
        binding.accountBtn.setOnClickListener(v -> onGetAccountInfoClicked());
        binding.explore.setOnClickListener(v ->
                NavHostFragment.findNavController(Home.this).
                        navigate(HomeDirections.actionHomeToExplore()));
        binding.personalPage.setOnClickListener(v ->
                NavHostFragment.findNavController(Home.this).
                        navigate(HomeDirections.actionHomeToUserPage()));

        return binding.getRoot();
    }


    public void onGetUserProfileClicked() {
        if (TokenClass.getInstance().getFireAccessToken() == null) {
            Toast.makeText(getContext(), "Error in getting access token from Firebase CLS.", Toast.LENGTH_SHORT).show();
            return;
        }

        final Request tracksRequest = new Request.Builder()
                .url("https://api.spotify.com/v1/me/top/tracks?time_range=long_term&limit=5")
                .addHeader("Authorization", "Bearer " + TokenClass.getInstance().getFireAccessToken())
                .build();
        cancelCall();

        processData(tracksRequest);

        final Request artistsRequest = new Request.Builder()
                .url("https://api.spotify.com/v1/me/top/artists?time_range=long_term&limit=5")
                .addHeader("Authorization", "Bearer " + TokenClass.getInstance().getFireAccessToken())
                .build();
        cancelCall();
        processData(artistsRequest);

    }

    private void onGetAccountInfoClicked() {
        NavHostFragment.findNavController(this).navigate(R.id.accountInfo);
    }

    public void processData(Request request) {
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
                    JSONArray items = jsonObject.optJSONArray("items");
                    Log.d("Data", items.toString());
                    HomeDirections.ActionHomeToSummary action = HomeDirections.actionHomeToSummary(jsonObject.toString());

                    getActivity().runOnUiThread(() -> {
                        if (isAdded()) {
                            NavHostFragment.findNavController(Home.this).navigate(action);
                        }
                    });


                } catch (JSONException e) {
                    Log.d("JSON", "Failed to parse data: " + e);
                    Toast.makeText(getContext(), "Failed to parse data, watch Logcat for more details",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void cancelCall() {
        if (mCall != null) {
            mCall.cancel();
        }
    }
}

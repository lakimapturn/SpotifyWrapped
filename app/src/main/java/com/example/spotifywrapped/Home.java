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
        return binding.getRoot();
    }


    public void onGetUserProfileClicked() {
        if (TokenClass.getInstance().getFireAccessToken() == null) {
            Toast.makeText(getContext(), "Error in getting access token from Firebase CLS.", Toast.LENGTH_SHORT).show();
            return;
        }
        final Request request = new Request.Builder()
                .url("https://api.spotify.com/v1/me")
                .addHeader("Authorization", "Bearer " + TokenClass.getInstance().getFireAccessToken())
                .build();
        cancelCall();
        mCall = mOkHttpClient.newCall(request);

        mCall.enqueue(new Callback() {
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

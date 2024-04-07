package com.example.spotifywrapped.Auth;

import static com.example.spotifywrapped.Helper.Helper.AUTH_TOKEN_REQUEST_CODE;
import static com.example.spotifywrapped.Helper.Helper.CLIENT_ID;
import static com.example.spotifywrapped.Helper.Helper.REDIRECT_URI;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.spotifywrapped.Helper.DownloadCallback;
import com.example.spotifywrapped.Home;
import com.example.spotifywrapped.MainActivity;
import com.example.spotifywrapped.R;
import com.example.spotifywrapped.Helper.TokenClass;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.spotify.sdk.android.auth.AuthorizationClient;
import com.spotify.sdk.android.auth.AuthorizationRequest;
import com.spotify.sdk.android.auth.AuthorizationResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import okhttp3.Call;
import com.example.spotifywrapped.databinding.LoginBinding;

public class Login extends Fragment {
    private Call mCall;
    private FirebaseStorage storage;

    private LoginBinding binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = LoginBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        storage = FirebaseStorage.getInstance();
        Button loginBtn = view.findViewById(R.id.login_button);
        binding.loginButton.setOnClickListener(v -> getToken());

    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final AuthorizationResponse response = AuthorizationClient.getResponse(resultCode, data);
        if (response.getAccessToken() != null) {
            try {
                uploadJson(response.getAccessToken());
                if (TokenClass.getInstance().getFireAccessToken() != null) {
                    NavHostFragment.findNavController(Login.this)
                            .navigate(R.id.action_login_to_home);
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private AuthorizationRequest getAuthenticationRequest(AuthorizationResponse.Type type) {
        return new AuthorizationRequest.Builder(CLIENT_ID, type, getRedirectUri().toString())
                .setShowDialog(false)
                .setScopes(new String[]{"user-read-email"})
                .setCampaign("your-campaign-token")
                .build();
    }

    private Uri getRedirectUri() {
        return Uri.parse(REDIRECT_URI);
    }

    private void cancelCall() {
        if (mCall != null) {
            mCall.cancel();
        }
    }

    public void uploadJson(String responseToken) throws JSONException {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Access token", responseToken);
            String jsonString = jsonObject.toString();
            byte[] data = jsonString.getBytes(StandardCharsets.UTF_8);
            StorageReference jsonRef = storage.getReference().child("json/data.json");
            jsonRef.putBytes(data)
                    .addOnSuccessListener(taskSnapshot -> {
                        // Handle success
                        try {
                            downloadToken(new DownloadCallback() {
                                @Override
                                public void successMethod(String theJsonString) {
                                    TokenClass.getInstance().setFireAccessToken(theJsonString.substring(17, theJsonString.length() - 2));
                                    NavHostFragment.findNavController(Login.this)
                                            .navigate(R.id.action_login_to_home);
                                }

                                @Override
                                public void failureMethod(Exception exception) {

                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                            ;
                        }
                    })
                    .addOnFailureListener(e -> {

                    });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void downloadToken(DownloadCallback callback) throws IOException, JSONException {
        try {
            StorageReference downloadRef = storage.getReference().child("json/data.json");
            File localFile = File.createTempFile("tempJson", "json");
            downloadRef.getFile(localFile).addOnSuccessListener(taskSnapshot -> {

                try {
                    String jsonString = new String(Files.readAllBytes(localFile.toPath()), StandardCharsets.UTF_8);
                    callback.successMethod(jsonString);

                } catch (IOException e) {
                    callback.failureMethod(e);

                }
            }).addOnFailureListener(e -> {

            });
        } catch (Exception e) {
            callback.failureMethod(e);
        }
    }

    public void getToken() {
        final AuthorizationRequest request = getAuthenticationRequest(AuthorizationResponse.Type.TOKEN);
        Intent intent = AuthorizationClient.createLoginActivityIntent(getActivity(), request);
        authActivityResultLauncher.launch(intent);
    }
    private final ActivityResultLauncher<Intent> authActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    final AuthorizationResponse response = AuthorizationClient.getResponse(result.getResultCode(), result.getData());
                    if (response.getAccessToken() != null) {
                        try {
                            uploadJson(response.getAccessToken());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });



    @Override
    public void onDestroy() {
        cancelCall();
        super.onDestroy();
    }
}

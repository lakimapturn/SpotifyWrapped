package com.example.spotifywrapped.Auth;

import static com.example.spotifywrapped.Helper.Helper.AUTH_TOKEN_REQUEST_CODE;
import static com.example.spotifywrapped.Helper.Helper.CLIENT_ID;
import static com.example.spotifywrapped.Helper.Helper.REDIRECT_URI;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;

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

public class Login extends MainActivity {
    private Call mCall;
    private FirebaseStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        storage = FirebaseStorage.getInstance();
        Button loginBtn = (Button) findViewById(R.id.login_button);
        loginBtn.setOnClickListener((v) -> {
            getToken();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final AuthorizationResponse response = AuthorizationClient.getResponse(resultCode, data);
        if (response.getAccessToken() != null) {
            try {
                uploadJson(response.getAccessToken());
                if (TokenClass.getInstance().getFireAccessToken() != null) {
                    Intent intent = new Intent(Login.this, Home.class);
                    startActivity(intent);
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
                                    //fireAccessToken = TokenClass.getInstance().getFireAccessToken();
                                    Intent intent = new Intent(Login.this, Home.class);
                                    startActivity(intent);
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
        AuthorizationClient.openLoginActivity(Login.this, AUTH_TOKEN_REQUEST_CODE, request);
    }

    @Override
    public void onDestroy() {
        cancelCall();
        super.onDestroy();
    }
}

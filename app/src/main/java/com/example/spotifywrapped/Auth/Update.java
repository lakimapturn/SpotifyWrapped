package com.example.spotifywrapped.Auth;

import static com.example.spotifywrapped.Helper.Helper.CLIENT_ID;
import static com.example.spotifywrapped.Helper.Helper.REDIRECT_URI;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;

import com.example.spotifywrapped.Helper.DownloadCallback;
import com.example.spotifywrapped.R;
import com.example.spotifywrapped.Helper.TokenClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

public class Update extends Fragment {
    private Call mCall;
    private FirebaseStorage storage;
    private FirebaseAuth mAuth;
    private LoginBinding binding;

    EditText email, password;
    ImageButton eyeToggle;
    android.widget.Button login, register, update;
    String pass, e;
    boolean show = true;

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
        mAuth = FirebaseAuth.getInstance();
        email = view.findViewById(R.id.mail_edit_text);
        password = view.findViewById(R.id.password_edit_text);
        eyeToggle = view.findViewById(R.id.password_toggle);
        update = view.findViewById(R.id.update_info_btn);


        eyeToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (show) {
                    show = false;
                    eyeToggle.setImageResource(R.drawable.ic_baseline_visibility_off_24);
                    password.setTransformationMethod(new PasswordTransformationMethod());
                } else {
                    show = true;
                    eyeToggle.setImageResource(R.drawable.ic_baseline_visibility_24);
                    password.setTransformationMethod(null);
                }
            }
        });


        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    e = "Done";
//                    redo
//                    email.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.edit_text_focus_bg));
//                    password.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.edit_text_bg));
                }
            }
        });

        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
//                    redo
//                    password.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.edit_text_focus_bg));
//                    email.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.edit_text_bg));
                }
            }
        });


        password.addTextChangedListener(loginTextWatcher);
        email.addTextChangedListener(loginTextWatcher);

    }

    private TextWatcher loginTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            String emailinput, passwordinput;
            emailinput = email.getText().toString().trim();
            passwordinput = password.getText().toString().trim();


            if (!emailinput.isEmpty() && !passwordinput.isEmpty()) {
                login.setEnabled(true);
                login.setClickable(true);
//                login.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.btn_bg_active));
            } else {
                login.setEnabled(false);
                login.setClickable(false);
//                login.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.btn_background));
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


    private AuthorizationRequest getAuthenticationRequest(AuthorizationResponse.Type type) {
        return new AuthorizationRequest.Builder(CLIENT_ID, type, getRedirectUri().toString())
                .setShowDialog(false)
                .setScopes(new String[]{"user-read-email", "user-library-read", "user-top-read"})
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



    @Override
    public void onDestroy() {
        cancelCall();
        super.onDestroy();
    }
}

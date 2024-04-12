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

public class Login extends Fragment {
    private Call mCall;
    private FirebaseStorage storage;
    private FirebaseAuth mAuth;
    private LoginBinding binding;

    EditText email, password;
    ImageButton eyeToggle;
    android.widget.Button login, register, dummyregister, update;
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
        login = view.findViewById(R.id.login_btn);
        register = view.findViewById(R.id.register);
        dummyregister = view.findViewById(R.id.dummy_register);
        update = view.findViewById(R.id.update_info_btn);

        register.setOnClickListener(v -> getToken());

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

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user, pwd;
                user = String.valueOf(email.getText());
                pwd = String.valueOf(password.getText());
                if (TextUtils.isEmpty(user)) {
                    Toast.makeText(view.getContext(), "Enter user/email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(pwd)) {
                    Toast.makeText(view.getContext(), "Enter user/email", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.signInWithEmailAndPassword(user, pwd)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(view.getContext(), "Login successful.",
                                            Toast.LENGTH_SHORT).show();
                                    FirebaseUser user = mAuth.getCurrentUser();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(view.getContext(), "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        dummyregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user, pwd;
                user = String.valueOf(email.getText());
                pwd = String.valueOf(password.getText());
                if (TextUtils.isEmpty(user)) {
                    Toast.makeText(view.getContext(), "Enter user/email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(pwd)) {
                    Toast.makeText(view.getContext(), "Enter user/email", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.createUserWithEmailAndPassword(user, pwd)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(view.getContext(), "Account created.",
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(view.getContext(), "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
//        update.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String username,pwd;
//                username = String.valueOf(email.getText());
//                pwd = String.valueOf(password.getText());
//                if (TextUtils.isEmpty(username) && TextUtils.isEmpty(pwd)) {
//                    Toast.makeText(view.getContext(), "Enter new user/pwd", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                if (!TextUtils.isEmpty(username)) {
//                    FirebaseUser currentUser = mAuth.getCurrentUser();
//                    if (currentUser != null) {
//                        currentUser.verifyBeforeUpdateEmail(username)
//                                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<Void> task) {
//                                        if (task.isSuccessful()) {
//                                            Toast.makeText(view.getContext(), "Email updated.",
//                                                    Toast.LENGTH_SHORT).show();
//                                        } else {
//                                            // If sign in fails, display a message to the user.
//                                            Toast.makeText(view.getContext(), "Failed to update.",
//                                                    Toast.LENGTH_SHORT).show();
//                                        }
//                                    }
//                                });
//                    }
//                } if (!TextUtils.isEmpty(pwd)) {
//                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//
//                    user.updatePassword(pwd)
//                            .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                    if (task.isSuccessful()) {
//                                        Toast.makeText(view.getContext(), "Password updated.",
//                                                Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//                            });
//                }
//            }
//        });

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final AuthorizationResponse response = AuthorizationClient.getResponse(resultCode, data);
        if (response.getAccessToken() != null) {
            try {
                uploadJson(response.getAccessToken());
                if (TokenClass.getInstance().getFireAccessToken() != null) {
                    NavHostFragment.findNavController(Login.this).navigate(R.id.action_login_to_home);
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private AuthorizationRequest getAuthenticationRequest(AuthorizationResponse.Type type) {
        return new AuthorizationRequest.Builder(CLIENT_ID, type, getRedirectUri().toString()).setShowDialog(false).setScopes(new String[]{"user-read-email", "user-library-read", "user-top-read"}).setCampaign("your-campaign-token").build();
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
            jsonRef.putBytes(data).addOnSuccessListener(taskSnapshot -> {
                // Handle success
                try {
                    downloadToken(new DownloadCallback() {
                        @Override
                        public void successMethod(String theJsonString) {
                            TokenClass.getInstance().setFireAccessToken(theJsonString.substring(17, theJsonString.length() - 2));
                            NavHostFragment.findNavController(Login.this).navigate(R.id.action_login_to_home);
                        }

                        @Override
                        public void failureMethod(Exception exception) {

                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).addOnFailureListener(e -> {

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

    private final ActivityResultLauncher<Intent> authActivityResultLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                            final AuthorizationResponse response = AuthorizationClient.getResponse(
                                    result.getResultCode(), result.getData());
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

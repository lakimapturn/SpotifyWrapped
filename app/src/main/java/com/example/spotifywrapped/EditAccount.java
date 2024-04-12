package com.example.spotifywrapped;

import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.spotifywrapped.databinding.EditAccountBinding;

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

public class EditAccount extends Fragment {
    private EditAccountBinding binding;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = EditAccountBinding.inflate(inflater, container, false);
        binding.updateAccountBtn.setOnClickListener(v -> updateAccount());
        return binding.getRoot();
    }

    private void updateAccount() {
        String username,pwd;
        username = String.valueOf(binding.usernameInputLayout.getText());
        pwd = String.valueOf(binding.pwdInputLayout.getText());
        if (TextUtils.isEmpty(username) && TextUtils.isEmpty(pwd)) {
            Toast.makeText(getContext(), "Enter new user/pwd", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!TextUtils.isEmpty(username)) {
            FirebaseUser currentUser = mAuth.getCurrentUser();
            if (currentUser != null) {
                currentUser.verifyBeforeUpdateEmail(username)
                    .addOnCompleteListener(
                        (@NonNull Task<Void> task) -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(getContext(), "Email updated.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getContext(), "Failed to update.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        }
                    );
            }
            if (TextUtils.isEmpty(pwd)) {
                NavHostFragment.findNavController(this).navigate(R.id.accountInfo);
            }
        } if (!TextUtils.isEmpty(pwd)) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            if (user != null) {
                user.updatePassword(pwd)
                .addOnCompleteListener(
                        (@NonNull Task<Void> task) -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(getContext(), "Password updated.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                );
            }
            NavHostFragment.findNavController(this).navigate(R.id.accountInfo);
        }
    }
}

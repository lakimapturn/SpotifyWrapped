package com.example.spotifywrapped;

import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.fragment.NavHostFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.example.spotifywrapped.databinding.UserAccountInfoBinding;

public class AccountInfo extends Fragment {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();
    private UserAccountInfoBinding binding;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = UserAccountInfoBinding.inflate(inflater, container, false);
        binding.arrowForward1.setOnClickListener(v -> onArrow1Clicked());
        binding.arrowForward2.setOnClickListener(v -> onArrow2Clicked());
        binding.arrowForward3.setOnClickListener(v -> onArrow3Clicked());

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String email = currentUser.getEmail();
            TextView emailTextView = binding.email;
            emailTextView.setText(email);
        }

        return binding.getRoot();
    }



    private void onArrow1Clicked() {
        NavHostFragment.findNavController(this).navigate(R.id.userPage);
    }
    private void onArrow2Clicked() {
        NavHostFragment.findNavController(this).navigate(R.id.editAccount);
    }
    private void onArrow3Clicked() {
        NavHostFragment.findNavController(this).navigate(R.id.deleteAccount2);
    }

}

package com.example.spotifywrapped;

import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.fragment.NavHostFragment;

import com.example.spotifywrapped.databinding.UserAccountInfoBinding;

public class AccountInfo extends Fragment {

    private UserAccountInfoBinding binding;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = UserAccountInfoBinding.inflate(inflater, container, false);
        binding.arrowForward3.setOnClickListener(v -> onArrow3Clicked());
        return binding.getRoot();
    }

    private void onArrow3Clicked() {
        NavHostFragment.findNavController(this).navigate(R.id.deleteAccount2);
    }

}

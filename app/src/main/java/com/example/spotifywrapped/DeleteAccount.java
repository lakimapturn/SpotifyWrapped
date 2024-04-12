package com.example.spotifywrapped;

import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.fragment.NavHostFragment;

import com.example.spotifywrapped.databinding.DeleteAccountBinding;

import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DeleteAccount extends Fragment {

    private DeleteAccountBinding binding;
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.delete_account, container, false);
//    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DeleteAccountBinding.inflate(inflater, container, false);
        binding.deleteAccountBtn.setOnClickListener(v -> deleteAccountForever());
        return binding.getRoot();
    }

    private void deleteAccountForever() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            user.delete()
            .addOnCompleteListener(
                (@NonNull Task<Void> task) -> {
                    if (task.isSuccessful()) {
//                        Log.d(TAG, "User account deleted.");
                    }
                }
            );
        }

        NavHostFragment.findNavController(this).navigate(R.id.login);
    }

}

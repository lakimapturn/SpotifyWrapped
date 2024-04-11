package com.example.spotifywrapped.UserPage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.spotifywrapped.R;

public class UserPage extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View UserPageView = inflater.inflate(R.layout.user_page, container, false);

        return UserPageView;
    }


}

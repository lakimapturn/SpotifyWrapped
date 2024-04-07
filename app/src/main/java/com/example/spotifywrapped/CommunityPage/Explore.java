package com.example.spotifywrapped.CommunityPage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.spotifywrapped.R;


public class Explore extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View ExplorePageView = inflater.inflate(R.layout.community_page, container, false);

        return ExplorePageView;
    }


}

package com.example.spotifywrapped.CommunityPage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.spotifywrapped.Components.ExplorePageListAdapter;
import com.example.spotifywrapped.Models.User;
import com.example.spotifywrapped.R;

import java.util.ArrayList;


public class Explore extends Fragment {

    ExplorePageListAdapter listAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View ExplorePageView = inflater.inflate(R.layout.community_page, container, false);

        return ExplorePageView;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ListView listView = view.findViewById(R.id.list_view);
        listAdapter = new ExplorePageListAdapter(view.getContext(), new ArrayList<User>());
        listView.setAdapter(listAdapter);
    }
}

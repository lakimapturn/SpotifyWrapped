package com.example.spotifywrapped.CommunityPage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.spotifywrapped.Components.ExplorePageListAdapter;
import com.example.spotifywrapped.Helper.Helper;
import com.example.spotifywrapped.Models.SpotifyObject;
import com.example.spotifywrapped.Models.SpotifyWrapped;
import com.example.spotifywrapped.Models.User;
import com.example.spotifywrapped.R;
import com.example.spotifywrapped.State.AppState;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Explore extends Fragment {

    ExplorePageListAdapter listAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View ExplorePageView = inflater.inflate(R.layout.community_page, container, false);

        AppState.communityList = new ArrayList<>();

        return ExplorePageView;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ListView listView = view.findViewById(R.id.list_view);
        listAdapter = new ExplorePageListAdapter(view.getContext(), AppState.communityList);
        listView.setAdapter(listAdapter);

        getWrappedData();
    }

    public void getWrappedData() {
        DatabaseReference spotifyWrappedRef = Helper.rootRef.child("spotifyWrapped");

        spotifyWrappedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                AppState.communityList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    SpotifyObject object = dataSnapshot.getValue(SpotifyObject.class);

                    SpotifyWrapped wrapped = new SpotifyWrapped(object.getTopArtists(), object.getTopSongs(), 0, "", object.getPublicity(), null);

                    User user = new User(dataSnapshot.getKey(), object.getUser(), "", "", wrapped, null);

                    System.out.println(user);

                    if (object.getPublicity() && !object.getUser().equals(AppState.user.getUsername())) {
                        AppState.communityList.add(user);
                    }
                }
                listAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println();
            }
        });

    }
}

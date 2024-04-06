package com.example.spotifywrapped.Components;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.spotifywrapped.Models.User;
import com.example.spotifywrapped.R;

import java.util.ArrayList;

public class ExplorePageListAdapter extends ArrayAdapter<User> {

    ArrayList<User> list;
    Context context;

    public ExplorePageListAdapter(@NonNull Context context, ArrayList<User> list) {
        super(context, R.layout.community_list_item, list);
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        User item = getItem(position);

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.community_list_item, parent, false);
        }

        return view;
    }
}

package com.example.spotifywrapped.Models;

import java.util.ArrayList;

public class User {
    private String username;
    private String password;
    private SpotifyWrapped spotifyWrapped;
    private ArrayList<User> following;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public SpotifyWrapped getSpotifyWrapped() {
        return spotifyWrapped;
    }

    public ArrayList<User> getFollowing() {
        return following;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSpotifyWrapped(SpotifyWrapped spotifyWrapped) {
        this.spotifyWrapped = spotifyWrapped;
    }

    public void setFollowing(ArrayList<User> following) {
        this.following = following;
    }
}

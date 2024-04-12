package com.example.spotifywrapped.Models;

import java.util.ArrayList;

public class User {

    private String id;
    private String username;
    private String password;
    private String spotifyToken;
    private SpotifyWrapped spotifyWrapped;
    private ArrayList<User> following;

    public User() {
    }

    public User(String id, String username, String password, String spotifyToken, SpotifyWrapped spotifyWrapped, ArrayList<User> following) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.spotifyToken = spotifyToken;
        this.spotifyWrapped = spotifyWrapped;
        this.following = following;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSpotifyToken() {
        return spotifyToken;
    }

    public void setSpotifyToken(String spotifyToken) {
        this.spotifyToken = spotifyToken;
    }

    public SpotifyWrapped getSpotifyWrapped() {
        return spotifyWrapped;
    }

    public void setSpotifyWrapped(SpotifyWrapped spotifyWrapped) {
        this.spotifyWrapped = spotifyWrapped;
    }

    public ArrayList<User> getFollowing() {
        return following;
    }

    public void setFollowing(ArrayList<User> following) {
        this.following = following;
    }
}

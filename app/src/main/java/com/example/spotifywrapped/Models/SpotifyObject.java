package com.example.spotifywrapped.Models;

import java.util.ArrayList;

public class SpotifyObject {
    private ArrayList<String> topArtists;
    private ArrayList<String> topSongs;
    private boolean publicity;

    private String user;

    public SpotifyObject() {
        topArtists = new ArrayList<>();
        topSongs = new ArrayList<>();
    }

    public ArrayList<String> getTopArtists() {
        return topArtists;
    }

    public void setTopArtists(ArrayList<String> topArtists) {
        this.topArtists = topArtists;
    }

    public ArrayList<String> getTopSongs() {
        return topSongs;
    }

    public void setTopSongs(ArrayList<String> topSongs) {
        this.topSongs = topSongs;
    }

    public boolean getPublicity() {
        return publicity;
    }

    public void setPublicity(boolean publicity) {
        this.publicity = publicity;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public SpotifyObject(ArrayList<String> topArtists, ArrayList<String> topSongs, boolean isPublic, String user) {
        this.topArtists = topArtists;
        this.topSongs = topSongs;
        this.publicity = isPublic;
        this.user = user;
    }
}

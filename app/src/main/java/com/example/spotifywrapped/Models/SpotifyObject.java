package com.example.spotifywrapped.Models;

import java.util.ArrayList;

public class SpotifyObject {
    private ArrayList<String> topArtists;
    private ArrayList<String> topSongs;
    private boolean isPublic;

    private String user;

    public SpotifyObject() {
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

    public boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(boolean isPublic) {
        this.isPublic = isPublic;
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
        this.isPublic = isPublic;
        this.user = user;
    }
}

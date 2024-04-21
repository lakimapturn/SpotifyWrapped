package com.example.spotifywrapped.Models;

import java.util.ArrayList;
import java.util.Arrays;

public class SpotifyWrapped {

    /**
     * Are we going to have multiple years? If yes, we need to think about adding a year field and making the SpotifyWrapped field in user an arrayList
     */
    private ArrayList<String> topArtists;
    private ArrayList<String> topSongs;
    private boolean publicity;

    private int minutesListened;
    private String topGenre;
    private ArrayList<Comment> comments;

    public SpotifyWrapped() {
    }

    public SpotifyWrapped(ArrayList<String> topArtists, ArrayList<String> topSongs, int minutesListened, String topGenre, boolean isPublic, ArrayList<Comment> comments) {
        this.topArtists = topArtists;
        this.topSongs = topSongs;
        this.minutesListened = minutesListened;
        this.topGenre = topGenre;
        this.publicity = isPublic;
        this.comments = comments;
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

    public int getMinutesListened() {
        return minutesListened;
    }

    public void setMinutesListened(int minutesListened) {
        this.minutesListened = minutesListened;
    }

    public String getTopGenre() {
        return topGenre;
    }

    public void setTopGenre(String topGenre) {
        this.topGenre = topGenre;
    }

    public boolean isPublic() {
        return publicity;
    }

    public void setPublic(boolean aPublic) {
        publicity = aPublic;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "SpotifyWrapped{" +
                "topArtists=" + topArtists +
                ", topSongs=" + topSongs +
                ", minutesListened=" + minutesListened +
                ", topGenre='" + topGenre + '\'' +
                ", isPublic=" + publicity +
                ", comments=" + comments +
                '}';
    }
}

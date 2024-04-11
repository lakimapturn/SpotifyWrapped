package com.example.spotifywrapped.Models;

import java.util.ArrayList;

public class SpotifyWrapped {

    /**
     * Are we going to have multiple years? If yes, we need to think about adding a year field and making the SpotifyWrapped field in user an arrayList
     */
    private String[] topArtists;
    private String[] topSongs;
    private int minutesListened;
    private String topGenre;
    private boolean isPublic;
    private ArrayList<Comment> comments;

    public SpotifyWrapped(String[] topArtists, String[] topSongs, int minutesListened, String topGenre, boolean isPublic, ArrayList<Comment> comments) {
        this.topArtists = topArtists;
        this.topSongs = topSongs;
        this.minutesListened = minutesListened;
        this.topGenre = topGenre;
        this.isPublic = isPublic;
        this.comments = comments;
    }

    public String[] getTopArtists() {
        return topArtists;
    }

    public void setTopArtists(String[] topArtists) {
        this.topArtists = topArtists;
    }

    public String[] getTopSongs() {
        return topSongs;
    }

    public void setTopSongs(String[] topSongs) {
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
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    //    public ArrayList<Comment> getComments() {
//
//    }


    // TODO: are we going to allow a spotify wrapped to be mutable?
}

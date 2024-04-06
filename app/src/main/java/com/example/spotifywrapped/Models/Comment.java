package com.example.spotifywrapped.Models;

public class Comment {
    private User commenter;
    private SpotifyWrapped post;

    public Comment(User commenter, SpotifyWrapped post) {
        this.commenter = commenter;
        this.post = post;
    }


}

package com.example.spotifywrapped.Helper;

public class TokenClass {
    private static TokenClass instance;
    private String fireAccessToken;
    private TokenClass() {}
    public static synchronized TokenClass getInstance() {
        if (instance == null) {
            instance = new TokenClass();
        }
        return instance;
    }
    public String getFireAccessToken() {
        return fireAccessToken;
    }
    public void setFireAccessToken(String fireAccessToken) {
        this.fireAccessToken = fireAccessToken;
    }
}

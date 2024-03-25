package com.example.spotifywrapped;

public interface DownloadCallback {
    void successMethod(String jsonString);
    void failureMethod(Exception exception);
}

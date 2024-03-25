package com.example.spotifywrapped.Helper;

public interface DownloadCallback {
    void successMethod(String jsonString);
    void failureMethod(Exception exception);
}

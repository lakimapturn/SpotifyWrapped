package com.example.spotifywrapped.Helper;

import okhttp3.OkHttpClient;

public final class Helper {
    public static final String REDIRECT_URI = "spotifywrapped://auth";
    public static final String CLIENT_ID = "d60867e08d524c50a3314af84f4cd627";
    public static final int AUTH_TOKEN_REQUEST_CODE = 0;
    private final OkHttpClient mOkHttpClient = new OkHttpClient();
}

package com.example.spotifywrapped.presentation.sign_in;

import android.content.Context;

import androidx.credentials.GetCredentialRequest;

import com.example.spotifywrapped.R;
import com.google.android.gms.signin.internal.SignInClientImpl;
import com.google.android.libraries.identity.googleid.GetGoogleIdOption;
import com.google.firebase.auth.FirebaseAuth;

public class SpotifyAuthUiClient {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public SpotifyAuthUiClient() {
    }
    //for Google: String serverClientID = context.getString(R.string.serverClientID);
    //    GetGoogleIdOption googleIdOption = new GetGoogleIdOption.Builder()
    //            .setFilterByAuthorizedAccounts(true)
    //            .setServerClientId(serverClientID)
    //            .build();
    //    GetCredentialRequest request = new GetCredentialRequest.Builder()
    //            .addCredentialOption(googleIdOption)
    //            .build();

}

package com.example.spotifywrapped.Auth;

import static com.example.spotifywrapped.Helper.Helper.CLIENT_ID;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.spotifywrapped.R;
import com.spotify.sdk.android.auth.AuthorizationRequest;
import com.spotify.sdk.android.auth.AuthorizationResponse;

public class AuthPage extends AppCompatActivity {

    EditText email, password;
    ImageButton eyeToggle;

    android.widget.Button login, register, dummyregister, update;
    String pass, e;
    boolean show = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#121212"));
        getSupportActionBar().setBackgroundDrawable(colorDrawable);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#121212'></font>"));


        email = findViewById(R.id.mail_edit_text);
        password = findViewById(R.id.password_edit_text);
        eyeToggle = findViewById(R.id.password_toggle);
        login = findViewById(R.id.login_btn);
        register = findViewById(R.id.register);
        dummyregister = findViewById(R.id.dummy_register);
        update = findViewById(R.id.update_info_btn);


        eyeToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (show) {
                    show = false;
                    eyeToggle.setImageResource(R.drawable.ic_baseline_visibility_off_24);
                    password.setTransformationMethod(new PasswordTransformationMethod());
                } else {
                    show = true;
                    eyeToggle.setImageResource(R.drawable.ic_baseline_visibility_24);
                    password.setTransformationMethod(null);
                }
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AuthPage.this, "Login is Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AuthPage.this, "Register Is Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        dummyregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AuthPage.this, "Dummy Register Is Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AuthPage.this, "Dummy Register Is Clicked", Toast.LENGTH_SHORT).show();
            }
        });


        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    e = "Done";
                    email.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.edit_text_focus_bg));
                    password.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.edit_text_bg));
                }
            }
        });

        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    password.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.edit_text_focus_bg));
                    email.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.edit_text_bg));
                }
            }
        });


        password.addTextChangedListener(loginTextWatcher);
        email.addTextChangedListener(loginTextWatcher);


    }

    private TextWatcher loginTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            String emailinput, passwordinput;
            emailinput = email.getText().toString().trim();
            passwordinput = password.getText().toString().trim();


            if (!emailinput.isEmpty() && !passwordinput.isEmpty()) {
                login.setEnabled(true);
                login.setClickable(true);
                login.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.btn_bg_active));
            } else {
                login.setEnabled(false);
                login.setClickable(false);
                login.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.btn_background));
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };



}
package com.cs407.ut;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button signInButton = findViewById(R.id.btnLogin); // Replace 'buttonSignIn' with your button's ID

        // Set OnClickListener
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start the SignIn activity
                Intent intent = new Intent(Login.this, SignIn.class);
                startActivity(intent);
            }
        });
    }
}
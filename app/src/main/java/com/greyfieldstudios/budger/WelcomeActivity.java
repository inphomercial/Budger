package com.greyfieldstudios.budger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class WelcomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        // Log in button click handler
        Button login = (Button) findViewById(R.id.loginButton);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Starts the sign up intent
                startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
            }
        });

        // Sign up button click handler
        Button signup = (Button) findViewById(R.id.signupButton);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Starts the sign up intent
                startActivity(new Intent(WelcomeActivity.this, SignUpActivity.class));
            }
        });
    }
}

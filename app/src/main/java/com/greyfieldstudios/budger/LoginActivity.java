package com.greyfieldstudios.budger;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;


public class LoginActivity extends Activity {

    private EditText emailText;
    private EditText passText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailText = (EditText) findViewById(R.id.editTextEmail);
        passText = (EditText) findViewById(R.id.editTextPassword);
        Button login = (Button) findViewById(R.id.buttonLogin);

        // Setup button click listener
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void login() {

        String email = emailText.getText().toString().trim();
        String pass = passText.getText().toString().trim();

        // Validate login in data

        // If error, display message

        // Setup a progress Dialog
        final ProgressDialog dialog = new ProgressDialog(LoginActivity.this);
        dialog.setMessage("Loging in..");
        dialog.show();

        // Call the parse login method
        ParseUser.logInInBackground(email, pass, new LogInCallback() {
            @Override
            public void done(ParseUser parseUser, ParseException e) {
                // Handle the response
                dialog.dismiss();

                if(e != null) {
                    // Show the error message
                    Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                } else {
                    // Start an intent for the dispatch activity
                    Intent intent = new Intent(LoginActivity.this, DispatchActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });
    }
}

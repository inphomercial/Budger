package com.greyfieldstudios.budger.Activites;

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

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


public class SignUpActivity extends ActionBarActivity {

    EditText usernameEditText;
    EditText password1EditText;
    EditText password2EditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.greyfieldstudios.budger.R.layout.activity_sign_up);

        usernameEditText = (EditText) findViewById(com.greyfieldstudios.budger.R.id.editTextEmail);
        password1EditText = (EditText) findViewById(com.greyfieldstudios.budger.R.id.editTextPassword1);
        password2EditText = (EditText) findViewById(com.greyfieldstudios.budger.R.id.editTextPassword2);

        Button signupButton = (Button) findViewById(com.greyfieldstudios.budger.R.id.buttonSignup);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });
    }

    private void signup() {
        String email = usernameEditText.getText().toString().trim();
        String pass1 = password1EditText.getText().toString().trim();
        String pass2 = password2EditText.getText().toString().trim();

        boolean validationError = false;
        StringBuilder validationErrorMessage = new StringBuilder("");

        // Validate Email field
        if(email.length() == 0) {
            validationError = true;
            validationErrorMessage.append("Blank user name");
        }

        // Validate Passwords
        if(pass1.length() == 0) {
            validationError = true;
            validationErrorMessage.append("Password field cannot be blank");
        }

        if(pass2.length() == 0) {
            validationError = true;
            validationErrorMessage.append("Password field cannot be blank");
        }

        if(!pass1.equals(pass2)) {
            validationError = true;
            validationErrorMessage.append("Passwords dont match!");
        }

        if(validationError) {
            Toast.makeText(SignUpActivity.this, validationErrorMessage.toString(), Toast.LENGTH_SHORT).show();
            return;
        }

        // Setup a progress dialog
        final ProgressDialog dialog = new ProgressDialog(SignUpActivity.this);
        dialog.setMessage("Signup in progress");
        dialog.show();

        // Setup a new parse user
        ParseUser user = new ParseUser();
        user.setUsername(email);
        user.setPassword(pass1);

        // Call the Parse signup method
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                // Dismiss the dialog
                dialog.dismiss();

                if(e != null) {
                    // Show the error message
                    Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                } else {
                    // Start an intent for the dispach activity
                    Intent intent = new Intent(SignUpActivity.this, SubmitBudgetActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(com.greyfieldstudios.budger.R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }
}

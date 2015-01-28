package com.greyfieldstudios.budger.Activites;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;


public class DispatchActivity extends Activity {

    public DispatchActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ParseObject o = null;

        // Check if there is a current user info
        if (ParseUser.getCurrentUser() != null) {
            ParseQuery<ParseObject> budget_query = new ParseQuery<ParseObject>("Budget");
            budget_query.whereEqualTo("user", ParseUser.getCurrentUser());
            try {
                o = budget_query.getFirst();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if(o != null) {
                // Already have a budget made, go to daily view
                startActivity(new Intent(this, DailyActivity.class));
            } else {
                // Start an intent for the logged in activity
                startActivity(new Intent(this, SubmitBudgetActivity.class));
            }


        } else {
            // Start an intent for the logged out activity
            startActivity(new Intent(this, WelcomeActivity.class));
        }
    }
}

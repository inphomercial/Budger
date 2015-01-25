package com.greyfieldstudios.budger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.parse.Parse;
import com.parse.ParseUser;


public class DispatchActivity extends Activity {

    public DispatchActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*// Enable Local Datastore Using Parse.
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "vx1fXJYudP1Hp1DrnRsnncRM6kL0j2ONe955Hs89", "phliaYGcy8Vw198MaMI4WCTC51CXBZ9JjcQfwloh");*/

        // Check if there is a current user info
        if (ParseUser.getCurrentUser() != null) {
            // Start an intent for the logged in activity
            startActivity(new Intent(this, SubmitBudgetActivity.class));
        } else {
            // Start an intent for the logged out activity
            startActivity(new Intent(this, WelcomeActivity.class));
        }
    }
}

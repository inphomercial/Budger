package com.greyfieldstudios.budger.Activites;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.greyfieldstudios.budger.Models.Budget;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;


public class SubmitBudgetActivity extends ActionBarActivity {

    //private Integer submit_budget_amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.greyfieldstudios.budger.R.layout.activity_submit_budget);

        Button submit = (Button) findViewById(com.greyfieldstudios.budger.R.id.submit_budget_button);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitBudget();
            }
        });
    }

    public void submitBudget() {

        // Start the progress dialog
        final ProgressDialog dialog = new ProgressDialog(SubmitBudgetActivity.this);
        dialog.setMessage("Submitting Budget");
        dialog.show();

        // Get Budget Amount
        TextView amountTextView = (TextView) findViewById(com.greyfieldstudios.budger.R.id.submit_budget_amount);
        String amountString = amountTextView.getText().toString();
        int amount = Integer.parseInt(amountString);

        // Add a new Budget object
        Budget budget = new Budget();
        budget.setUser(ParseUser.getCurrentUser());
        budget.setBudget(amount);

        // Figure out daily
        Integer daily = amount / 31;
        budget.setDailyBudget(daily);

        // Set Remaining
        budget.setRemaining(daily);

        // Set Object Permissions
        ParseACL acl = new ParseACL();
        acl.setPublicReadAccess(true);
        budget.setACL(acl);

        // Save object in background
        budget.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                dialog.dismiss();
                finish();
            }
        });

        // Send to Daily view
        Intent intent = new Intent(this, DailyActivity.class);
        startActivity(intent);
    }

    public void logout() {
        ParseUser.logOut();
        Intent intent = new Intent(SubmitBudgetActivity.this, DispatchActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
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

        if(id == com.greyfieldstudios.budger.R.id.menu_logout) {
            logout();
        }

        //noinspection SimplifiableIfStatement
       /* if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }
}

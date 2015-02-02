package com.greyfieldstudios.budger.Activites;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.greyfieldstudios.budger.Constants;
import com.greyfieldstudios.budger.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MonthlyLedgerActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_ledger);

        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage(Constants.DIALOG_LOADING_MONTHLY_LEDGER);
        dialog.show();

        // Get all expenses for user based on month
        ParseObject user = ParseUser.getCurrentUser();
        ParseQuery<ParseObject> expense_query = new ParseQuery<ParseObject>("Expenses");
        expense_query.whereEqualTo("user", user);
        expense_query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                if(e == null) {

                    int[] months = new int[12];


                    // Build array adapter and set values from parse data
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(MonthlyLedgerActivity.this, android.R.layout.simple_list_item_1);

                    // Iterate over each month
                    for(int i = 0; i < months.length; i++) {

                        // Current month total
                        int total = 0;

                        for (ParseObject expense : parseObjects) {

                            // Get the expese created date and convert it to a Calendar object
                            Date created = expense.getCreatedAt();
                            Calendar expense_cal = Calendar.getInstance();
                            expense_cal.setTime(created);

                            if (expense_cal.get(Calendar.MONTH) == i) {
                                total += expense.getInt("amount");
                            }
                        }

                        // Build a linearLayout view to hold the textViews
                        //LinearLayout ll = new LinearLayout(getApplicationContext());

                        // Build the textview to insert into the adapter
                        TextView month_name = new TextView(getApplicationContext());
                        month_name.setText(Constants.MONTHNAME[i]);

                        // Build the textview to show total
                        TextView month_total = new TextView(getApplicationContext());
                        month_total.setText("Total Spent : $" + total);

                        //ll.addView(month_name);
                        //ll.addView(month_total);

                        // Finally add the textview tot he adapter.
                        adapter.add(month_name.getText().toString() + "  " + month_total.getText().toString());
                        //adapter.add(month_total.getText().toString());
                        //adapter.add(ll.toString());

                        // Get ListView and set adapter
                        ListView layout = (ListView) findViewById(R.id.monthlyLedgerSums);
                        layout.setAdapter(adapter);
                    }

                    dialog.dismiss();

                } else {
                    Log.d("App", e.getMessage());
                }
            }
        });
    }

    public void logout() {
        ParseUser.logOut();
        Intent intent = new Intent(this, DispatchActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // Logout
        if (id == R.id.menu_logout) {
            logout();
        }

        // Daily Activity
        if(id == R.id.activity) {
            startActivity(new Intent(this, DailyActivity.class));
        }

        // Daily Ledger
        if(id == R.id.daily) {
            startActivity(new Intent(this, DailyLedgerActivity.class));
        }

        // Monthly Ledger
        if(id == R.id.monthly) {
            startActivity(new Intent(this, MonthlyLedgerActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }
}

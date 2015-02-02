package com.greyfieldstudios.budger.Activites;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.provider.SyncStateContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.greyfieldstudios.budger.Application;
import com.greyfieldstudios.budger.Models.Expenses;
import com.greyfieldstudios.budger.R;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class DailyLedgerActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.greyfieldstudios.budger.R.layout.activity_daily_ledger);

        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Gathering Daily Ledger Info");
        dialog.show();

        // Generate a calendar for the start of the month
        Calendar start_c = Calendar.getInstance();
        start_c.set(Calendar.DAY_OF_MONTH, 1);

        // Generate a calendar for the end of the month to know how many total days
        Calendar end_c = Calendar.getInstance();
        int days_in_month = end_c.getActualMaximum(Calendar.DAY_OF_MONTH);
        end_c.set(Calendar.DAY_OF_MONTH, days_in_month);

        // Get all expenses for user based on month
        ParseObject user = ParseUser.getCurrentUser();
        ParseQuery<ParseObject> expense_query = new ParseQuery<ParseObject>("Expenses");
        expense_query.whereEqualTo("user", user);
        expense_query.whereLessThan("createdAt", end_c.getTime());
        expense_query.whereGreaterThan("createdAt", start_c.getTime());
        expense_query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                if (e == null) {

                    Calendar c = Calendar.getInstance();
                    int current_date = c.get(Calendar.DAY_OF_MONTH);
                    int days_in_month = c.getActualMaximum(Calendar.DAY_OF_MONTH);

                    // Used to set the starting date back to the start of the month
                    c.add(Calendar.DAY_OF_MONTH, -current_date);

                    // Build array adapter and set values from parse data
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(DailyLedgerActivity.this, android.R.layout.simple_list_item_1);

                    for(int i=0; i<days_in_month; i++) {

                        // Add a day for each loop to get all of the days of the month
                        c.add(Calendar.DAY_OF_MONTH, 1);
                        String day_of_week = c.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
                        int day_of_month = c.get(Calendar.DAY_OF_MONTH);

                        // Get total expenses for day
                        int expense_total = 0;
                        for(ParseObject expenses : parseObjects) {

                            // Convert ParseObject Date to Calendar to compare
                            Date expense_date = expenses.getCreatedAt();
                            Calendar expense_cal = Calendar.getInstance();
                            expense_cal.setTime(expense_date);

                            if(expense_cal.get(Calendar.DAY_OF_MONTH) == i) {
                                expense_total += expenses.getInt("amount");
                                //continue;
                            } else {
                                Log.d("App", "No expenses for day " + c.get(Calendar.DAY_OF_MONTH));
                            }
                        }

                        TextView tv = new TextView(getApplicationContext());
                        tv.setText(day_of_week + " " + day_of_month + " // Total Spent: $" + Integer.toString(expense_total));

                        // For current date make the font bold
                        if(current_date == day_of_month) {
                           tv.setTypeface(null, Typeface.BOLD);
                            tv.setTextColor(Color.RED);
                        }

                        // Finally add the textview tot he adapter.
                        adapter.add(tv.getText().toString());

                        // Get ListView and set adapter
                        ListView layout = (ListView) findViewById(com.greyfieldstudios.budger.R.id.dailyLedgerSums);
                        layout.setAdapter(adapter);
                    }
                } else {
                    Log.d("Error", "Getting Parse Data Failed");
                }
            };
        });

        dialog.dismiss();
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
        getMenuInflater().inflate(com.greyfieldstudios.budger.R.menu.menu_main, menu);
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

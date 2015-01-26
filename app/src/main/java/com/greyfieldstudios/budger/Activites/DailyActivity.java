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
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.greyfieldstudios.budger.Application;
import com.greyfieldstudios.budger.Models.Expenses;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class DailyActivity extends ActionBarActivity {

    private TextView tvDaily;
    private TextView tvSpendable;
    private TextView expenseAmount;
    private TextView expenseDesc;

    String remaining;
    String loggedInUser;

    ListView layout;
    List<ParseObject> eq;
    List<ParseObject> bq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.greyfieldstudios.budger.R.layout.activity_daily);

        // Setting the date place holder with todays date
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        TextView dateText = (TextView) findViewById(com.greyfieldstudios.budger.R.id.date_text);
        dateText.setText(sdf.format(date));

        // Show currently logged in users name
        loggedInUser = ParseUser.getCurrentUser().getUsername();
        TextView loggingInUser = (TextView) findViewById(com.greyfieldstudios.budger.R.id.textViewLoggedInUser);
        loggingInUser.setText(loggedInUser);

        // Get Parse Data for user
        gatherParseDataForUser();

        // Setup the add expense button
        Button submit = (Button) findViewById(com.greyfieldstudios.budger.R.id.submitExpenseButton);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addExpense();
            }
        });
    }

    private void gatherParseDataForUser() {

        final ProgressDialog dialog = new ProgressDialog(DailyActivity.this);
        dialog.setMessage("Getting user data");
        dialog.show();

        ParseObject user = ParseUser.getCurrentUser();
        ParseQuery<ParseObject> expense_query = new ParseQuery<ParseObject>("Expenses");
        expense_query.whereEqualTo("user", user);

        ParseQuery<ParseObject> budget_query = new ParseQuery<ParseObject>("Budget");
        budget_query.whereEqualTo("user", user);

        try {
            eq = expense_query.find();
            budget_query.getFirstInBackground(new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject parseObject, ParseException e) {
                    tvDaily = (TextView) findViewById(com.greyfieldstudios.budger.R.id.daily_budget_amount_value);
                    tvDaily.setText(Integer.toString(parseObject.getInt("daily_budget")));

                    tvSpendable = (TextView) findViewById(com.greyfieldstudios.budger.R.id.spendable_value);
                    tvSpendable.setText(Integer.toString(parseObject.getInt("remaining")));
                }
            });
        } catch (ParseException e) {
            Log.e("Error", e.getMessage());
        }

        // Remove Dialog
        dialog.dismiss();

        // Expense data
        if(eq != null) {
            // Build array adapter and set values from parse data
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
            for(ParseObject expenses : eq) {
                adapter.add("$" + Integer.toString(expenses.getInt("amount")) + " " + expenses.getString("desc"));
            }

            // Get ListView and set adapter
            layout = (ListView) findViewById(com.greyfieldstudios.budger.R.id.expenseListView);
            layout.setAdapter(adapter);
        }
    }

    private void addExpense() {

        // Start the progress dialog
        final ProgressDialog dialog = new ProgressDialog(DailyActivity.this);
        dialog.setMessage("Adding expense");
        dialog.show();

        // Get Expense Amount
        expenseAmount = (TextView) findViewById(com.greyfieldstudios.budger.R.id.expense_amount_text);
        String ex = expenseAmount.getText().toString();
        int expense_amount = Integer.parseInt(ex);

        // Get Expense Desc
        expenseDesc = (TextView) findViewById(com.greyfieldstudios.budger.R.id.expense_desc_text);

        // Add a new Expense object
        Expenses expense = new Expenses();
        expense.setAmount(expense_amount);
        expense.setDesc(expenseDesc.getText().toString());
        expense.setUser(ParseUser.getCurrentUser());

        // Set object Permissions
        ParseACL acl = new ParseACL();
        acl.setPublicReadAccess(true);
        expense.setACL(acl);

        // Save object in background
        expense.saveInBackground(new SaveCallback() {
            @Override
            public void done(com.parse.ParseException e) {
                dialog.dismiss();
                finish();
            }
        });

        // Restart current activity to pickup new changes
        startActivity(new Intent(DailyActivity.this, DailyActivity.class));
    }

    public void getPreviousDay(View view) { Log.d(Application.APPTAG,"Clicked on previous day"); }

    public void getNextDay(View view) {
        Log.d(Application.APPTAG, "Click on next day");
    }

    public void logout() {
        ParseUser.logOut();
        Intent intent = new Intent(DailyActivity.this, DispatchActivity.class);
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
        if (id == com.greyfieldstudios.budger.R.id.menu_logout) {
            logout();
        }

        return super.onOptionsItemSelected(item);
    }
}

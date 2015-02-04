package com.greyfieldstudios.budger.Activites;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.greyfieldstudios.budger.AddExpenseFragment;
import com.greyfieldstudios.budger.Application;
import com.greyfieldstudios.budger.Constants;
import com.greyfieldstudios.budger.Models.Expenses;
import com.greyfieldstudios.budger.R;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.w3c.dom.Text;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;


public class DailyActivity extends FragmentActivity {

    private TextView tvDaily;
    private TextView tvSpendable;
    private TextView expenseAmount;
    private TextView expenseDesc;
    private TextView loggingInUser;
    private TextView dateText;

    private Calendar selected_date;
    private Calendar todays_date;

    private ImageButton previousDay;
    private ImageButton nextDay;

    private ListView layout;

    private Button submit;

    // Do all setup and init here!
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.greyfieldstudios.budger.R.layout.activity_daily);

        // Init all layout object references
        init();
    }


    // Do all the things here!
    @Override
    public void onResume() {
        super.onResume();

        todays_date = Calendar.getInstance();

        // Set the date text with selected date
        Application.selected_date = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        dateText.setText(sdf.format(Application.selected_date.getTime()));

        // Show currently logged in users name
        String loggedInUser = ParseUser.getCurrentUser().getUsername();
        loggingInUser.setText(loggedInUser);

        // NextDay button event listener
        nextDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParseDataForDay(1);
            }
        });

        // Get PreviousDay button and setup event listener
        previousDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParseDataForDay(-1);
            }
        });

        // Setup the add expense button
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Fragment stuff
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.add(R.id.add_expense_fragment_placeholder, new AddExpenseFragment());
                //ft.replace(R.id.add_expense_fragment_placeholder, new AddExpenseFragment());
                ft.addToBackStack("");
                ft.commit();
            }
        });

        // Get Parse Data for user
        gatherParseDataForUser();
    }

    private void init() {

        dateText = (TextView) findViewById(com.greyfieldstudios.budger.R.id.date_text);
        tvDaily = (TextView) findViewById(com.greyfieldstudios.budger.R.id.daily_budget_amount_value);
        tvSpendable = (TextView) findViewById(com.greyfieldstudios.budger.R.id.spendable_value);
        loggingInUser = (TextView) findViewById(com.greyfieldstudios.budger.R.id.textViewLoggedInUser);

        nextDay = (ImageButton) findViewById(R.id.forwardButton);
        previousDay = (ImageButton) findViewById(R.id.backButton);

        layout = (ListView) findViewById(com.greyfieldstudios.budger.R.id.expenseListView);

        submit = (Button) findViewById(com.greyfieldstudios.budger.R.id.submitExpenseButton);
    }

    // Ensure that you cannot browse beyond current date
    private void checkNextDayButtonState(Calendar todays_date, Calendar selected_date) {
        if(todays_date.get(Calendar.DAY_OF_MONTH) == selected_date.get(Calendar.DAY_OF_MONTH)) {
            nextDay.setEnabled(false);
        } else {
            nextDay.setEnabled(true);
        }
    }

    private void gatherParseDataForUser() {

        Log.d("App", "Gathering Data for the date of " + Application.selected_date.getTime());

        // Ensure that you cannot browser beyond current date
        checkNextDayButtonState(todays_date, Application.selected_date);

        final ProgressDialog dialog = new ProgressDialog(DailyActivity.this);
        dialog.setMessage("Getting user data");
        dialog.show();

        ParseObject user = ParseUser.getCurrentUser();

        // Budget Data Query
        ParseQuery<ParseObject> budget_query = new ParseQuery<ParseObject>(Constants.PARSE_BUDGET_OBJECT);
        budget_query.whereEqualTo(Constants.PARSE_USER, user);
        budget_query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                tvDaily.setText(Integer.toString(parseObject.getInt(Constants.PARSE_DAILY_BUDGET)));
                tvSpendable.setText(Integer.toString(parseObject.getInt(Constants.PARSE_REMAINING)));
            }
        });

        // Reset layout adapter
        layout.setAdapter(null);

        // Expense Data Query
        ParseQuery<ParseObject> expense_query = new ParseQuery<ParseObject>(Constants.PARSE_EXPENSES_OBJECT);
        expense_query.whereEqualTo(Constants.PARSE_USER, user);
        expense_query.findInBackground(new FindCallback<ParseObject>() {
           public void done(List<ParseObject> objects, ParseException e) {
               if (e == null) {

                   ArrayAdapter<String> adapter;
                   if(layout.getAdapter() == null)
                   {
                        adapter = new ArrayAdapter<String>(DailyActivity.this, android.R.layout.simple_list_item_1);
                   }else{
                        adapter = (ArrayAdapter<String>) layout.getAdapter();

                   }

                   for(ParseObject expenses : objects) {

                       // Convert ParseObject Date to CagetDatelendar to compare
                       Date expense_date = expenses.getDate(Constants.PARSE_DATE);
                       Calendar expense_cal = Calendar.getInstance();
                       expense_cal.setTime(expense_date);

                       // Compare selected_date against each of the ParseObjects
                       if(Application.selected_date.get(Calendar.DAY_OF_MONTH) == expense_cal.get(Calendar.DAY_OF_MONTH)) {
                           adapter.add("$" + Double.toString(expenses.getDouble(Constants.PARSE_AMOUNT)) + " " + expenses.getString("desc"));
                           adapter.notifyDataSetChanged();
                       }
                   }

                   // Get ListView and set adapter
                   layout.setAdapter(adapter);

               } else {
                   Log.d("App", e.getMessage().toString());
               }
           }
       });

        // Remove Dialog
        dialog.dismiss();
    }

    public void getParseDataForDay(int increment) {

        Application.selected_date.add(Calendar.DAY_OF_MONTH, increment);

        gatherParseDataForUser();

        // Setting the date place holder with todays date
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        dateText.setText(sdf.format(Application.selected_date.getTime()));
    }

    private void hideKeyboard() {
        // Check if no view has focus:
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
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
            Application.logout(this);
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

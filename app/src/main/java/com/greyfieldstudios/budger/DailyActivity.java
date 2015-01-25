package com.greyfieldstudios.budger;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class DailyActivity extends ActionBarActivity {

    ArrayList expenses;

    TextView tvDaily;
    TextView tvSpendable;
    TextView expenseAmount;
    TextView expenseDesc;

    String remaining;

    ListView layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily);

        // Setting the date place holder with todays date
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        TextView dateText = (TextView) findViewById(R.id.date_text);
        dateText.setText(sdf.format(date));

        // Create the expenses array
        expenses = new ArrayList();

        // 1. get passed intent
        Intent intent = getIntent();

        // 2. get string data from intent
        String daily_string = intent.getStringExtra("daily");
        remaining = intent.getStringExtra("remaining");

        // 3. get references to each textView
        tvDaily = (TextView) findViewById(R.id.daily_budget_amount_value);
        tvDaily.setText("$ " + daily_string);

        tvSpendable = (TextView) findViewById(R.id.spendable_value);
        tvSpendable.setText("$ " + remaining);
    }

    public void addExpense(View view) {

        // Get ListView
        layout = (ListView) findViewById(R.id.expenseListView);

        // Get Expense Amount
        expenseAmount = (TextView) findViewById(R.id.expense_amount_text);

        // Get Expense Desc
        expenseDesc = (TextView) findViewById(R.id.expense_desc_text);

        // Add to the expenses array
        expenses.add("$" + expenseAmount.getText().toString() + " (" + expenseDesc.getText().toString() + ")");

        // Rebuild ListView adapter list
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, expenses);
        layout.setAdapter(adapter);

        // Reset Fields
        this.clearFields();

        // Update Budget amount
        /*int remaining_int = Integer.parseInt(remaining);

        // Get Expense Amount
        expenseAmount = (TextView) findViewById(R.id.expense_amount_text);
        String ex = expenseAmount.getText().toString();
        int expense_amount = Integer.parseInt(ex);
        remaining_int -= expense_amount;
        tvSpendable.setText("$ " + remaining_int);*/
    }

    private void clearFields() {
        expenseAmount.setText("");
        expenseDesc.setText("");
    }

    public void getPreviousDay(View view) {
        Log.d(Application.APPTAG,"Clicked on previous day");
    }

    public void getNextDay(View view) {
        Log.d(Application.APPTAG, "Click on next day");
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

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.log) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    /*JSONObject object = new JSONObject();
        try {
            object.put("name", "Jack Hack");
            object.put("score", new Integer(200));
            object.put("current", new Double(152.32));
            object.put("nickname", "Hacker");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("debug", object.toString());*/
}

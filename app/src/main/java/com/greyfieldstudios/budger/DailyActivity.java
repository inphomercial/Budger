package com.greyfieldstudios.budger;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class DailyActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily);

        // Setting the date place holder with todays date
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        TextView dateText = (TextView) findViewById(R.id.date_text);
        dateText.setText(sdf.format(date));

        // 1. get passed intent
        Intent intent = getIntent();

        // 2. get string data from intent
        String daily_string = intent.getStringExtra("daily");
        String remaining_string = intent.getStringExtra("remaining");

        // 3. get references to each textView
        TextView tvDaily = (TextView) findViewById(R.id.daily_budget_amount_value);
        tvDaily.setText("$ " + daily_string);

        TextView tvSpendable = (TextView) findViewById(R.id.spendable_value);
        tvSpendable.setText("$ " + remaining_string);
    }

    public void getPreviousDay(View view) {
        Log.d("debug","Clicked on previous day");
    }

    public void getNextDay(View view) {
        Log.d("debug", "Click on next day");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_daily, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

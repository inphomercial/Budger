package com.greyfieldstudios.budger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.w3c.dom.Text;


public class SubmitBudgetActivity extends ActionBarActivity {

    //private Integer submit_budget_amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_budget);

        final Button logout = (Button) findViewById(R.id.buttonLogout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
    }

    public void logout() {
        ParseUser.logOut();
        Intent intent = new Intent(SubmitBudgetActivity.this, DispatchActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void submitBudget(View view) {

        TextView amount = (TextView) findViewById(R.id.submit_budget_amount);
        String amount_string = amount.getText().toString();

        Budget budget = new Budget();
        budget.takeBudgetCalculateDaily(Integer.parseInt(amount_string));

        Log.d(Application.APPTAG, budget.getTotalBudget().toString());
        Log.d(Application.APPTAG, budget.getDaily().toString());
        Log.d(Application.APPTAG, budget.getRemaining().toString());

        // Save data to parse
        ParseObject budger = new ParseObject("Budger");
        budger.put("total_budger", budget.getTotalBudget().toString());
        budger.put("daily", budget.getDaily().toString());
        budger.put("remaining", budget.getRemaining().toString());
        budger.saveInBackground();

        Intent intent = new Intent(this, DailyActivity.class);
        //intent.putExtra("total_budget", budget.getTotalBudget().toString());
        //intent.putExtra("daily", budget.getDaily().toString());
        //intent.putExtra("remaining", budget.getRemaining().toString());
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

        if(id == R.id.menu_logout) {
            logout();
        }

        //noinspection SimplifiableIfStatement
       /* if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }
}

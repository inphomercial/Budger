package com.greyfieldstudios.budger.Models;

import android.util.Log;
import android.widget.ArrayAdapter;

import com.parse.FindCallback;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by tenderbranson on 1/23/15.
 */

@ParseClassName("Expenses")
public class Expenses extends ParseObject {

    public void setAmount(int amount) {
        put("amount", amount);
    }

    public void setDesc(String desc) {
        put("desc", desc);
    }

    public void setUser(ParseUser user) {
        put("user", user);
    }

    /* public String getExpense() {
        return getString("expense");
    }*/

    /*public ParseUser getUser() {
        return getParseUser("user");
    }*/

    public static ParseQuery<Expenses> getQuery() {
        return ParseQuery.getQuery(Expenses.class);
    }

    /*public static List<ParseObject> getAllBetweenDates(Calendar start, Calendar end) {

        List<ParseObject> list;
        // Get all expenses for user based on day
        ParseObject user = ParseUser.getCurrentUser();
        ParseQuery<ParseObject> expense_query = new ParseQuery<ParseObject>("Expenses");
        expense_query.whereEqualTo("user", user);
        expense_query.whereLessThan("createdAt", end.getTime());
        expense_query.whereGreaterThan("createdAt", start.getTime());
        expense_query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                if (e == null) {
                    Log.d("App", "ITS WORKINGG");
                    list = parseObjects;
                } else {
                    Log.d("App", "It didnt work..");
                }
            }
        });
    }
*/


    /*private Integer total_budget;
    private Integer daily;
    private Integer remaining;

    public Integer getTotalBudget() {
        return this.total_budget;
    }

    public Integer getDaily() {
        return this.daily;
    }

    public Integer getRemaining() {
        return this.remaining;
    }

    public void takeBudgetCalculateDaily(Integer budget) {

        // Set the budget amount
        this.setTotalBudget(budget);

        // Determine daily and set it
        Integer d = this.getTotalBudget() / 31;
        this.setDaily(d);

        // set the initial remaining to the daily
        this.setRemaining(d);
    }

    private void setTotalBudget(Integer budget) {
        this.total_budget = budget;
    }

    private void setDaily(Integer daily) {
        this.daily = daily;
    }

    private void setRemaining(Integer remaining) {
        this.remaining = remaining;
    }*/

}
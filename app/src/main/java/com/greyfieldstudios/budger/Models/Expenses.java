package com.greyfieldstudios.budger.Models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.Calendar;
import java.util.Date;


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

    public void setDate(Integer num) {

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, num);

        Date DD = cal.getTime();
        put("createdAt", DD);
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
}
package com.greyfieldstudios.budger.Models;

import com.greyfieldstudios.budger.Constants;
import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;


/**
 * Created by tenderbranson on 1/23/15.
 */

@ParseClassName("Expenses")
public class Expenses extends ParseObject {

    public void setAmount(BigDecimal amount) {
        put(Constants.PARSE_AMOUNT, amount);
    }

    public void setDesc(String desc) {
        put(Constants.PARSE_DESC, desc);
    }

    public void setUser(ParseUser user) {
        put(Constants.PARSE_USER, user);
    }

    public void setDate(Calendar num) {

        //Calendar cal = Calendar.getInstance();

        //Calendar cal = (Calendar)num.clone();
        //cal.set(Calendar.DAY_OF_MONTH, num);

        Date DD = num.getTime();
        put(Constants.PARSE_DATE, DD);
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
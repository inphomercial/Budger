package com.greyfieldstudios.budger.Models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by inpho on 1/25/2015.
 */
@ParseClassName("Budget")
public class Budget extends ParseObject {

    public void setBudget(int amount) {
        put("budget", amount);
    }

    public void setRemaining(int amount) {
        put("remaining", amount);
    }

    public void setDailyBudget(int amount) {
        put("daily_budget", amount);
    }

    public void setUser(ParseUser user) {
        put("user", user);
    }

    public int getBudget() {
        return getInt("budget");
    }

    public int getDailyBudget() {
        return getInt("daily_budget");
    }

    public int getRemaining() {
        return getInt("remaining");
    }
}

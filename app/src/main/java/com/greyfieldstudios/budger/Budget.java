package com.greyfieldstudios.budger;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by tenderbranson on 1/23/15.
 */
public class Budget {

    private Integer total_budget;
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
    }

}

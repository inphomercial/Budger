package com.greyfieldstudios.budger;

import android.app.Activity;
import android.content.Intent;

import com.greyfieldstudios.budger.Activites.DispatchActivity;
import com.greyfieldstudios.budger.Models.Budget;
import com.greyfieldstudios.budger.Models.Expenses;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.Calendar;

/**
 * Created by inpho on 1/25/2015.
 */
public class Application extends android.app.Application {

    public static final String APPTAG = "Budger";

    public Application() {}

    public static Calendar selected_date;

    @Override
    public void onCreate() {
        super.onCreate();

        // Enable Local Datastore Using Parse.
        Parse.enableLocalDatastore(this);
        ParseObject.registerSubclass(Expenses.class);
        ParseObject.registerSubclass(Budget.class);
        Parse.initialize(this, "vx1fXJYudP1Hp1DrnRsnncRM6kL0j2ONe955Hs89", "phliaYGcy8Vw198MaMI4WCTC51CXBZ9JjcQfwloh");
    }

    public static void logout(Activity act) {
        ParseUser.logOut();
        Intent intent = new Intent(act, DispatchActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        act.startActivity(intent);
    }
}

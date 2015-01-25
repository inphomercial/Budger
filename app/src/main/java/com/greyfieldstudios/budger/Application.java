package com.greyfieldstudios.budger;

import com.parse.Parse;
import com.parse.ParseObject;

/**
 * Created by inpho on 1/25/2015.
 */
public class Application extends android.app.Application {

    public static final String APPTAG = "Budger";

    public Application() {

    }

    @Override
    public void onCreate() {
        super.onCreate();

        // Enable Local Datastore Using Parse.
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "vx1fXJYudP1Hp1DrnRsnncRM6kL0j2ONe955Hs89", "phliaYGcy8Vw198MaMI4WCTC51CXBZ9JjcQfwloh");
    }
}

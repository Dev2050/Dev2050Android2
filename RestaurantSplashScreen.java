package com.wolt.fissha.restaurantopeninghours;

/**
 * Created by Fissha on 27/05/2017.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class RestaurantSplashScreen extends Activity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {

                // Starts WoltMainActivity activity
                Intent i = new Intent(RestaurantSplashScreen.this, RestaurantMainActivity.class);
                startActivity(i);

                // closes this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

}

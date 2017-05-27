package com.wolt.devname.restaurantopeninghours;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class RestaurantSplashScreen extends Activity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                // Starts RestaurantMainActivity activity
                Intent i = new Intent(RestaurantSplashScreen.this, RestaurantMainActivity.class);
                startActivity(i);

                // closes this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

}

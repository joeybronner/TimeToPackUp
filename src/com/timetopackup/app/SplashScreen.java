package com.timetopackup.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


public class SplashScreen extends Activity
{
    /* splash screen timer */
    private static int SPLASH_TIME_OUT = 3500; // 3.5 secs

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                Intent i = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(i);

                /* closing this activity */
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

}
package com.njvidhyalay.myapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
//import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.AdView;
//import com.google.android.gms.ads.MobileAds;
//import com.google.android.gms.ads.initialization.InitializationStatus;
//import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;



public class SplashScreen extends Activity {
    public static int SPLASH_TIME_OUT = 2000;
    private static int  firstcount=0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler;
        ImageView img;
        img = findViewById(R.id.Img);
        img.animate().alpha(1500).setDuration(0);
//        if(firstcount==0){
//            Intent intent = new Intent(SplashScreen.this, MainActivity.class);
//            startActivity(intent);
//            finish();
//            firstcount++;
//        }

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                SharedPreferences pref = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);
//                SharedPreferences.Editor editor = pref.edit();
                String userNameroll = pref.getString("rollnumber", String.valueOf(0)).trim();
//                String passStd = pref.getString("standard", String.valueOf(0));

                if (pref.getBoolean("activity_executed", false)) {
                        if(firstcount==0){
                            firstcount++;
                            Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    Intent intent = new Intent(SplashScreen.this, AfterLoginOptionClass.class);
                    startActivity(intent);
                    finish();

                } else {
                    Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        },SPLASH_TIME_OUT);
// decide here whether to navigate to Login or Main Activit
    }

}


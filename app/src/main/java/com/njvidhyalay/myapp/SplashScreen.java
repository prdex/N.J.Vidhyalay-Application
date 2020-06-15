package com.njvidhyalay.myapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import eu.dkaratzas.android.inapp.update.Constants;
import eu.dkaratzas.android.inapp.update.InAppUpdateManager;


//import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.AdView;
//import com.google.android.gms.ads.MobileAds;
//import com.google.android.gms.ads.initialization.InitializationStatus;
//import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;


public class SplashScreen extends MainActivity {
    private static final int REQ_CODE_VERSION_UPDATE =  530;
    public static int SPLASH_TIME_OUT = 2000;
    private static int  firstcount=0;
    private InAppUpdateManager inAppUpdateManager;
  //  private static final int REQ_CODE_VERSION_UPDATE = 530;
   // private InAppUpdateManager inAppUpdateManager;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
            inAppUpdateManager = InAppUpdateManager.Builder(SplashScreen.this, REQ_CODE_VERSION_UPDATE)
                    .resumeUpdates(true) // Resume the update, if the update was stalled. Default is true
                    .mode(Constants.UpdateMode.IMMEDIATE);

            inAppUpdateManager.checkForAppUpdate();
        }

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQ_CODE_VERSION_UPDATE) {
            if (resultCode == Activity.RESULT_CANCELED) {
                // If the update is cancelled by the user,
                // you can request to start the update again.
                inAppUpdateManager.checkForAppUpdate();

                // Log.d(TAG, "Update flow failed! Result code: " + resultCode);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


}


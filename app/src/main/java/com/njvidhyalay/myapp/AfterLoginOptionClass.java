package com.njvidhyalay.myapp;
//import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.AdView;
//import com.google.android.gms.ads.MobileAds;
//import com.google.android.gms.ads.initialization.InitializationStatus;
//import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
//import com.nex3z.notificationbadge.NotificationBadge;

//import eu.dkaratzas.android.inapp.update.InAppUpdateManager;


public class AfterLoginOptionClass extends MainActivity {

    TextView studentname, studentroll,marquee;
    CardView assignmentview, testmarksview, lecturedetails, materialicon, bookview, attendence, leaderboard, noticeboard,gallery, quiz;
  //  NotificationBadge mBadge;
    Button logout;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private DatabaseReference textRef;
  //  private static final int REQ_CODE_VERSION_UPDATE = 530;
    //private InAppUpdateManager inAppUpdateManager;
  //  UpdateManager mUpdateManager;
    int rollnoforcheck;
    String anothertxt;
    String StandardString = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.afterloginview);

//        mUpdateManager = UpdateManager.Builder(this).mode(UpdateManagerConstant.IMMEDIATE);
//        mUpdateManager.start();

//        inAppUpdateManager = InAppUpdateManager.Builder(this, REQ_CODE_VERSION_UPDATE)
//                .resumeUpdates(true) // Resume the update, if the update was stalled. Default is true
//                .mode(Constants.UpdateMode.IMMEDIATE);
//
//        inAppUpdateManager.checkForAppUpdate();
//        AdView mAdView;

//        MobileAds.initialize(this, new OnInitializationCompleteListener() {
//            @Override
//            public void onInitializationComplete(InitializationStatus initializationStatus) {
//            }
//        });
//
//        mAdView = findViewById(R.id.adView);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);

//        gridview1 = (GridView) findViewById(R.id.gridid);
        assignmentview = findViewById(R.id.assignmentview);
        testmarksview = findViewById(R.id.testmarksview);
        studentname = findViewById(R.id.namestudnet);
        studentroll = findViewById(R.id.stdstudent);
        lecturedetails = findViewById(R.id.lecture);
        materialicon = findViewById(R.id.materialicon);
        bookview = findViewById(R.id.bookview);
        marquee = findViewById(R.id.noticeview);
        attendence = findViewById(R.id.attendenceview);
        leaderboard = findViewById(R.id.leaderboard);
        noticeboard = findViewById(R.id.noticeboardview);
        gallery = findViewById(R.id.gallery);
        quiz  =findViewById(R.id.quiz);

      //  mBadge = (NotificationBadge)findViewById(R.id.badge);
        logout = findViewById(R.id.logout);

//        mBadge.setNumber(2);

        database = FirebaseDatabase.getInstance();

//        assignmentview.setVisibility(View.INVISIBLE);
//        testmarksview.setVisibility(View.INVISIBLE);
//        lecturedetails.setVisibility(View.INVISIBLE);
//        materialicon.setVisibility(View.INVISIBLE);
//        bookview.setVisibility(View.INVISIBLE);
//        attendence.setVisibility(View.INVISIBLE);


        logout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                SharedPreferences myPrefs = getSharedPreferences("ActivityPREF", MODE_PRIVATE);
                SharedPreferences.Editor editor = myPrefs.edit();
                editor.clear();
                editor.apply();
                setLoginState(false);
                Intent intent = new Intent(AfterLoginOptionClass.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        final String userNameroll, passStd;

        SharedPreferences settings = getApplicationContext().getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("activity_executed", true).apply();

        userNameroll = settings.getString("rollnumber", String.valueOf(0));
        passStd = settings.getString("standard", String.valueOf(0));

        String marqueestd = "marquee"+passStd;
        textRef = database.getReference().child(marqueestd);

        textRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    marqueemodel info = ds.getValue(marqueemodel.class);
                    String maqueetext = info.getText();
                    marquee.setText(maqueetext);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        marquee.setSelected(true);


//        if (passStd.length() == 3) {
//            StandardStringtemp = StandardStringtemp + passStd.charAt(0) + passStd.charAt(1) + " - " + passStd.charAt(2);
//        }
//        else if (passStd.length() == 2) {
//            StandardStringtemp = StandardStringtemp + passStd.charAt(0) + " - " + passStd.charAt(1);
//        }else{
//            StandardStringtemp = StandardStringtemp + passStd.charAt(0);
//        }

            String stdreference = "std" + passStd;
            StandardString = "ધો. " + passStd;

            rollnoforcheck = Integer.parseInt(userNameroll);

            myRef = database.getReference().child(stdreference).child(userNameroll).child("name");

            myRef.addValueEventListener(new ValueEventListener() {
               // @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                            anothertxt = dataSnapshot.getValue(String.class);
                            studentroll.setText(StandardString);
                            studentname.setText(anothertxt);
                            findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                    }


                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            assignmentview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent id = new Intent(AfterLoginOptionClass.this, assignmentclassification.class);
                    startActivity(id);
                }
            });

            testmarksview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent id = new Intent(AfterLoginOptionClass.this, Marks.class);
                    startActivity(id);
                }
            });
            lecturedetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent id = new Intent(AfterLoginOptionClass.this, videolectureinfo.class);
                    startActivity(id);
                }
            });
            materialicon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent id = new Intent(AfterLoginOptionClass.this, materialclassification.class);
                    startActivity(id);
                }
            });
            bookview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent id = new Intent(AfterLoginOptionClass.this, bookview.class);
                    startActivity(id);
                }
            });
            attendence.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent id = new Intent(AfterLoginOptionClass.this, attendence.class);
                    startActivity(id);
                 }
            });
            leaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent id = new Intent(AfterLoginOptionClass.this, LeaderBoard.class);
                    startActivity(id);
                    }
            });

        noticeboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent id = new Intent(AfterLoginOptionClass.this, NoticeBoard.class);
                startActivity(id);
            }
        });
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent id = new Intent(AfterLoginOptionClass.this, Gallery.class);
                startActivity(id);
            }
        });
        quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent id = new Intent(AfterLoginOptionClass.this, quizclassification.class);
                startActivity(id);
            }
        });
        }
    private void setLoginState(boolean status) {
        SharedPreferences sp = getSharedPreferences("ActivityPREF",
                MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
        ed.putBoolean("activity_executed", status);
        ed.apply();
        ed.commit();
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        this.finishAffinity();}
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        if (requestCode == REQ_CODE_VERSION_UPDATE && resultCode == Activity.RESULT_CANCELED) {
//
//                // If the update is cancelled by the user,
//                // you can request to start the update again.
//                inAppUpdateManager.checkForAppUpdate();
//
//               // Log.d(TAG, "Update flow failed! Result code: " + resultCode);
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//    }
}

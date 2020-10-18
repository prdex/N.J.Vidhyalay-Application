package com.njvidhyalay.myapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;
import java.util.List;

public class assignmentdate extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    Context context;
    CardView cardview;
    LinearLayout.LayoutParams layoutparams;
    TextView textview;
    LinearLayout relativeLayout;
    ArrayList<String> statusforeach;
    int rollnoforcheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.assignmentdateview);
        statusforeach = new ArrayList<String>();
        statusforeach.add("સરસ");
        statusforeach.add("ખુબ સરસ");
        statusforeach.add("લેશન કરેલ નથી");
        statusforeach.add(" ");
        statusforeach.add("");

        database = FirebaseDatabase.getInstance();

        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {

            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(assignmentdate.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }
        };

        //check all needed permissions together
        TedPermission.with(this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("Please turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();

       // SharedPreferences settings = getApplicationContext().getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);
        //final SharedPreferences.Editor editor = settings.edit();

        final String userNameroll,subject, dateofsubmit;

        SharedPreferences settings = getApplicationContext().getSharedPreferences("assignmentindex", Context.MODE_PRIVATE);
        subject = settings.getString("assignmentindexsubject", String.valueOf(0));

        SharedPreferences settingsforstandard = getApplicationContext().getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);
        String std =  settingsforstandard.getString("standard", String.valueOf(0));
        userNameroll = settingsforstandard.getString("rollnumber", String.valueOf(0));

        final String linkref = "std" + std;

      //  SharedPreferences settingsindex = getSharedPreferences("materialindex", Context.MODE_PRIVATE);
        //String indexref = settingsindex.getString("index", String.valueOf(0));
        if(subject.equals("B.A.")){
            myRef = database.getReference().child(linkref).child("assignmentsubject").child("BA");
        }
        else if(subject.equals("S.P.C.C.")){
            myRef = database.getReference().child(linkref).child("assignmentsubject").child("SPCC");
        }else{
            myRef = database.getReference().child(linkref).child("assignmentsubject").child(subject);
        }


        rollnoforcheck = Integer.parseInt(userNameroll);

        context = getApplicationContext();
        relativeLayout = findViewById(R.id.lineralayout1);


        myRef.addValueEventListener(new ValueEventListener() {
           // @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        assignmentdatesinfo info = ds.getValue(assignmentdatesinfo.class);

                        String date = info.getDate();
                        String subject = info.getSubject();
                        String status = info.getStatus();
                        char a = status.charAt(rollnoforcheck-1);
                        int index = a-'0';
                        String setsubdate = "તા. " +date+"\nવિષય :- "+subject+"\n"+"નોંધ :-" +statusforeach.get(index);

                    cardview = new CardView(context);
                    layoutparams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );
                    layoutparams.setMargins(0, 20, 0, 0);
                    cardview.setLayoutParams(layoutparams);

                    cardview.setRadius(15);
                    cardview.setMaxCardElevation(30);
                    cardview.setMaxCardElevation(6);
                    if(index==0) {
                        cardview.setBackgroundColor(Color.rgb(121, 214, 101));
                    }else if(index==1){
                        cardview.setBackgroundColor(Color.rgb(121, 214, 101));
                    }else if(index==2){
                        cardview.setBackgroundColor(Color.rgb(235, 80, 80));
                    }else{
                        cardview.setBackgroundColor(Color.rgb(214, 214, 214));
                    }

                    textview = new TextView(context);
                    textview.setLayoutParams(layoutparams);
                    textview.setText(setsubdate);
                    textview.setTextSize(17);
                    textview.setPadding(25,25,25,25);
                    textview.setTextColor(Color.BLACK);
                    textview.setGravity(Gravity.CENTER);
                    cardview.addView(textview);


                    relativeLayout.addView(cardview);

                    cardview.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent id = new Intent(assignmentdate.this, assignmentupload.class);
                            startActivity(id);
                        }
                    });

                }
                findViewById(R.id.loadingPanel).setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
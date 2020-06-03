package com.njvidhyalay.myapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class attendence extends MainActivity {


    private FirebaseDatabase database ;
    private DatabaseReference myRef;
    TableRow.LayoutParams textViewParam;
    TableLayout tableLayout;
    TableLayout.LayoutParams tableRowParams;
    TableRow trHead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attendencelayout);

        database = FirebaseDatabase.getInstance();

        SharedPreferences settings = getApplicationContext().getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);
        final String passStd, rollno;

        passStd = settings.getString("standard", String.valueOf(0));
        rollno = settings.getString("rollnumber", String.valueOf(0)).trim();


        String stanref = "std"+passStd;
        //String stdreference = "attendence" + rollno;
        final int rollnoforcheck = Integer.parseInt(rollno);

        myRef = database.getReference().child(stanref).child("attendence");
        //getting reference one for marks, name and one for subject, date
        myRef.keepSynced(true);



        myRef.addValueEventListener(new ValueEventListener() {
           // @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    attendenceinfo info = ds.getValue(attendenceinfo.class);          //where we got reference, it takes snapshot, looping will give us acces to each child

                        String date =  info.getDate();;
                        String presentorabsent = info.getPresentorabsent();;
                        char a  = presentorabsent.charAt(rollnoforcheck-1);
                        String temp = Character.toString(a);
                        textViewParam = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT, 1.0f);
                        tableLayout = findViewById(R.id.tabul);
                        tableRowParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT);
                        trHead = new TableRow(attendence.this);
                        trHead.setLayoutParams(tableRowParams);


                        setdateandsubject(date, temp, textViewParam, trHead, tableLayout, tableRowParams);//loop till we get his rollnumber
//                        anothertxt = info.getName();                                    //getting name and marks from userinfocalss
//                        studentname.setText(anothertxt);

                }
                findViewById(R.id.loadingPanel).setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void setdateandsubject(String date, String a, TableRow.LayoutParams textViewParam, TableRow trHead, TableLayout tableLayout, TableLayout.LayoutParams tableRowParams) {


        GradientDrawable gd = new GradientDrawable();
        gd.setColor(0xFFFFFFFF);

        gd.setStroke(1, 0xFFBBBBBB);

        TextView nameHead = new TextView(this);
        nameHead.setLayoutParams(textViewParam);
        String DateString = "તા. " + date;
        nameHead.setText(DateString);
        nameHead.setTextSize(17);
        nameHead.setGravity(Gravity.CENTER);
        nameHead.setTextColor(Color.rgb(0, 0, 0));
        nameHead.setBackground(gd);
        trHead.addView(nameHead);


        TextView subnew = new TextView(this);
        subnew.setLayoutParams(textViewParam);
        subnew.setGravity(Gravity.CENTER);
        subnew.setText(a);
        subnew.setTextSize(17);
        subnew.setTextColor(Color.rgb(0, 0, 0));
        subnew.setBackground(gd);
        trHead.addView(subnew);
        tableLayout.addView(trHead);
    }
}

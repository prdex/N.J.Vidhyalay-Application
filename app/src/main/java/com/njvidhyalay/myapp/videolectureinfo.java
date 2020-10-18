package com.njvidhyalay.myapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class videolectureinfo extends AppCompatActivity {

    String date="", link="", std="", subject="", note="";

    ArrayList<String> subjectlist1to5;
    ArrayList<String> subjectlist6to10;
    ArrayList<String> subjectlist11and12;

    LinearLayout.LayoutParams layoutparams;
    TextView textview;
    LinearLayout relativeLayout;
    int index = 0;
    int globalref = 0;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.videolecturedetail);
        relativeLayout = findViewById(R.id.videlectureclassification);


        final String  passStd;

        SharedPreferences settings = getApplicationContext().getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);

        passStd = settings.getString("standard", String.valueOf(0));

        String linkref = "std"+passStd;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child(linkref).child("linkdetails");

        subjectlist1to5 = new ArrayList<String>();
        subjectlist1to5.add("કલરવ");
        subjectlist1to5.add("ગણિત-ગમ્મત");
        subjectlist1to5.add("મારી આસપાસ");
        subjectlist1to5.add("ગુજરાતી");
        subjectlist1to5.add("આસપાસ");
        subjectlist1to5.add("અંગ્રેજી");
        subjectlist1to5.add("હિન્દી");
        subjectlist1to5.add("સૌની-આસપાસ");                              //adding subjects codewise


        subjectlist6to10 = new ArrayList<String>();
        subjectlist6to10.add("અંગ્રેજી");                                //for every standard there will be and array
        subjectlist6to10.add("ગણિત");                               // This will solve be useful as student completes in one standard and moves to next
        subjectlist6to10.add("ગુજરાતી");
        subjectlist6to10.add("વિજ્ઞાન");
        subjectlist6to10.add("સંસ્કૃત");
        subjectlist6to10.add("સામાજિક વિજ્ઞાન");
        subjectlist6to10.add("હિન્દી");

        subjectlist11and12 = new ArrayList<String>();
        subjectlist11and12.add("B.A.");                                //for every standard there will be and array
        subjectlist11and12.add("S.P.C.C.");
        subjectlist11and12.add("અંગ્રેજી");
        subjectlist11and12.add("આંકડાશાસ્ત્ર");       // This will solve be useful as student completes in one standard and moves to next
        subjectlist11and12.add("અર્થશાસ્ત્ર");
        subjectlist11and12.add("એકાઉન્ટ");
        subjectlist11and12.add("ગુજરાતી");
        subjectlist11and12.add("તત્વજ્ઞાન");
        subjectlist11and12.add("સમાજશાસ્ત્ર");
        subjectlist11and12.add("મનોવિજ્ઞાન");
        subjectlist11and12.add("સંસ્કૃત");


        myRef.addValueEventListener(new ValueEventListener() {
         //   @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    linkuserinfo info = ds.getValue(linkuserinfo.class);          //where we got reference, it takes snapshot, looping will give us acces to each child
                    date = info.getDate();
                    link = info.getLink();
                    subject = info.getSubject();
                    std = info.getStd();
                    note = info.getNote();



                    TableRow.LayoutParams textViewParam = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT, 1.0f);
                    TableLayout tableLayout = findViewById(R.id.tabulaout);
                    final TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT);
                    final TableRow trHead = new TableRow(videolectureinfo.this);
                    final TableRow linkhead = new TableRow(videolectureinfo.this);
                    linkhead.setLayoutParams(tableRowParams);
                    trHead.setLayoutParams(tableRowParams);

                    setdatelinkidpassword(date, link,note,std,subject, textViewParam, trHead,linkhead, tableLayout, tableRowParams);
                    break;
                }
                findViewById(R.id.loadingPanel).setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



//        findViewById(R.id.quizclassififcationloading).setVisibility(View.GONE);


    }
    public void setdatelinkidpassword(String date, String link,String note,String std, String subject ,TableRow.LayoutParams textViewParam, TableRow trHead,TableRow linkhead, TableLayout tableLayout, TableLayout.LayoutParams tableRowParams){

        TextView nameHead = new TextView(this);
        nameHead.setLayoutParams(textViewParam);
        String DateString = "તા. " + date;
        nameHead.setText(DateString);
        nameHead.setTextSize(17);
        nameHead.setGravity(Gravity.CENTER);
        nameHead.setTextColor(Color.rgb(0, 0, 0));
        nameHead.setBackgroundColor(Color.rgb(186, 186, 186));
        trHead.addView(nameHead);
        tableLayout.addView(trHead);

        GradientDrawable gd = new GradientDrawable();
        gd.setColor(0xFFFFFFFF);

        gd.setStroke(1, 0xFFBBBBBB);

        TableRow sub = new TableRow(this);
        sub.setLayoutParams(tableRowParams);

        TextView subnew = new TextView(this);
        subnew.setLayoutParams(textViewParam);
        subnew.setGravity(Gravity.CENTER);
        String stda="વિષય :- "+subject;
        subnew.setText(stda);
        subnew.setTextSize(17);
        subnew.setTextColor(Color.rgb(0, 0, 0));
        subnew.setBackground(gd);
        sub.addView(subnew);

        TextView dumbnew = new TextView(this);
        dumbnew.setLayoutParams(textViewParam);
        dumbnew.setGravity(Gravity.CENTER);
        String sunb = "ધો :- "+std;
        dumbnew.setText(sunb);
        dumbnew.setTextSize(17);
        dumbnew.setTextColor(Color.rgb(0, 0, 0));
        dumbnew.setBackground(gd);
        sub.addView(dumbnew);

        tableLayout.addView(sub);

        String linkzoom = "લિંક  <br />";
        String actuallink = "<a href="+link+">Link-1</a>";
//
        Spanned Text;
        Text = Html.fromHtml(linkzoom + actuallink);
        TextView linkdetails = new TextView(this);
        linkdetails.setText(Text);
        linkdetails.setLayoutParams(textViewParam);
        linkdetails.setGravity(Gravity.CENTER);
        linkdetails.setTextSize(22);
        linkdetails.setTextColor(Color.rgb(0, 0, 0));
        linkdetails.setMovementMethod(LinkMovementMethod.getInstance());
        linkhead.addView(linkdetails);
        tableLayout.addView(linkhead);

        TableRow iddetail = new TableRow(this);
        iddetail.setLayoutParams(tableRowParams);
        TextView idnew = new TextView(this);
        idnew.setLayoutParams(textViewParam);
        idnew.setGravity(Gravity.CENTER);
        String idd="નોંધ :-  "+note;
        idnew.setText(idd);
        idnew.setTextSize(17);
        idnew.setTextColor(Color.rgb(0, 0, 0));
        idnew.setBackground(gd);
        iddetail.addView(idnew);

        tableLayout.addView(iddetail);

        final String  passStd;

        SharedPreferences settings = getApplicationContext().getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);
        passStd = settings.getString("standard", String.valueOf(0));

        layoutparams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        layoutparams.setMargins(0, 20, 0, 0);

        int standardLength = passStd.length();
        if (standardLength >= 2) {
            char nine = passStd.charAt(0);
            char oneorzero = passStd.charAt(1);
            if (nine == '9' || (nine == '1' && oneorzero == '0')) {
                index = 2;
//                    }
            } else {
                index = 3;
                int j;
                for (j = 0; j < 11; j++) {
                    textview = new TextView(videolectureinfo.this);
                    textview.setLayoutParams(layoutparams);
                    textview.setText(subjectlist11and12.get(j));
                    textview.setTextSize(17);
                    textview.setPadding(25, 25, 25, 25);
                    textview.setTextColor(Color.BLACK);
                    textview.setGravity(Gravity.CENTER_HORIZONTAL);
                    textview.setId(j);
                    textview.setBackgroundColor(Color.WHITE);
                    relativeLayout.addView(textview);

                    final int finalJ = j;
                    textview.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SharedPreferences settings = getSharedPreferences("videoindex", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putString("videoindexsubject", subjectlist11and12.get(finalJ));
                            editor.apply();
                            editor.commit();

                            Intent intent = new Intent(videolectureinfo.this, videolectureclassification.class);
                            startActivity(intent);
                        }
                    });
                }
            }
        } else if (passStd.charAt(0) == '7' || passStd.charAt(0) == '8' || passStd.charAt(0) == '6') {
            index = 2;
        } else {
            int j;
            for (j = 0; j <8; j++) {
                textview = new TextView(videolectureinfo.this);
                textview.setLayoutParams(layoutparams);
                textview.setText(subjectlist1to5.get(j));
                textview.setTextSize(17);
                textview.setPadding(25, 25, 25, 25);
                textview.setTextColor(Color.BLACK);
                textview.setGravity(Gravity.CENTER_HORIZONTAL);
                textview.setId(j);
                textview.setBackgroundColor(getResources().getColor(R.color.backgroundcolor));

                relativeLayout.addView(textview);

                final int finalJ = j;
                textview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences settings = getSharedPreferences("videoindex", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString("videoindexsubject", subjectlist1to5.get(finalJ));
                        editor.apply();
                        editor.commit();

                        Intent intent = new Intent(videolectureinfo.this, videolectureclassification.class);
                        startActivity(intent);
                    }
                });
            }
        }
        if (index == 2) {
            int j;
            for (j = 0; j < 7; j++) {
                textview = new TextView(videolectureinfo.this);
                textview.setLayoutParams(layoutparams);
                textview.setText(subjectlist6to10.get(j));
                textview.setTextSize(17);
                textview.setPadding(25, 25, 25, 25);
                textview.setTextColor(Color.BLACK);
                textview.setGravity(Gravity.CENTER_HORIZONTAL);
                textview.setId(j);
                textview.setBackgroundColor(Color.WHITE);
                relativeLayout.addView(textview);

                final int finalJ = j;
                textview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences settings = getSharedPreferences("videoindex", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString("videoindexsubject", subjectlist6to10.get(finalJ));
                        editor.apply();
                        editor.commit();

                        Intent intent = new Intent(videolectureinfo.this, videolectureclassification.class);
                        startActivity(intent);
                    }
                });
            }
        }
    }
}

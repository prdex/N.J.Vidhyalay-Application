package com.njvidhyalay.myapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class assignmentclassification extends AppCompatActivity {
    LinearLayout.LayoutParams layoutparams;
    TextView textview;
    LinearLayout relativeLayout;
    int index = 0;

    ArrayList<String> subjectlist1to5;
    ArrayList<String> subjectlist6to10;
    ArrayList<String> subjectlist11and12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.assignmentclassifcation);
        SharedPreferences settings = getApplicationContext().getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);
        relativeLayout = findViewById(R.id.lineralayoutassignment);

        final String passStd;

        passStd = settings.getString("standard", String.valueOf(0));

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
                    textview = new TextView(assignmentclassification.this);
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
                            SharedPreferences settings = getSharedPreferences("assignmentindex", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putString("assignmentindexsubject", subjectlist11and12.get(finalJ));
                            editor.apply();
                            editor.commit();

                            Intent intent = new Intent(assignmentclassification.this, assignmentdate.class);
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
                textview = new TextView(assignmentclassification.this);
                textview.setLayoutParams(layoutparams);
                textview.setText(subjectlist1to5.get(j));
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
                        SharedPreferences settings = getSharedPreferences("assignmentindex", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString("assignmentindexsubject", subjectlist1to5.get(finalJ));
                        editor.apply();
                        editor.commit();

                        Intent intent = new Intent(assignmentclassification.this, assignmentdate.class);
                        startActivity(intent);
                    }
                });
            }
        }
        if (index == 2) {
            int j;
            for (j = 0; j < 7; j++) {
                textview = new TextView(assignmentclassification.this);
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
                        SharedPreferences settings = getSharedPreferences("assignmentindex", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString("assignmentindexsubject", subjectlist6to10.get(finalJ));
                        editor.apply();
                        editor.commit();

                        Intent intent = new Intent(assignmentclassification.this, assignmentdate.class);
                        startActivity(intent);
                    }
                });
            }
        }
        findViewById(R.id.loadingpanelassignmentclassification).setVisibility(View.GONE);
    }
}

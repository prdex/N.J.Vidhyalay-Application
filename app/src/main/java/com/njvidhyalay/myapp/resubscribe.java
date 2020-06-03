package com.njvidhyalay.myapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class resubscribe extends MainActivity {

    String tempdate;
    String tempsubject;
    String temptotalmarks;
    String chapter;
    String actualmarks;
    int count = 0;
    int index;
    int rollnoforcheck;
    String finalmarks="";


    ArrayList<String> subjectlist1to5;
    ArrayList<String> subjectlist6to10;
    ArrayList<String> subjectlist11and12;
    Spinner spinner;

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private DatabaseReference subref;

    public resubscribe() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewresult);

//        spinner = findViewById(R.id.subejctspin);
//        spinnerstd = new ArrayList<String>();
//        ArrayAdapter<String> spinnerAdapter = null;


        SharedPreferences settings = getApplicationContext().getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("activity_executed", true).apply();

        String userNameroll, passStd;


        passStd = settings.getString("standard", String.valueOf(0));
        userNameroll = settings.getString("rollnumber", String.valueOf(0));
        
        String stdreference = "std" + passStd;                              //concatenate to make the exact string written in firebase database
        String subdatereference = "subdate" + passStd;
//        String StandardString = "ધો. " + passStd;
        rollnoforcheck = Integer.parseInt(userNameroll);


        int standardLength = passStd.length();
        if (standardLength >= 2) {
         //   for (int i = 0; i < passStd.length(); i++) {
                char nine = passStd.charAt(0);
                char oneorzero = passStd.charAt(1);
                if (nine == '9' || (nine == '1' && oneorzero == '0')) {
                    index = 2;
//                    spinnerstd.add("all");
//                    spinnerstd.add("અંગ્રેજી");                                //for every standard there will be and array
//                    spinnerstd.add("ગણિત");                               // This will solve be useful as student completes in one standard and moves to next
//                    spinnerstd.add("ગુજરાતી");
//                    spinnerstd.add("વિજ્ઞાન");
//                    spinnerstd.add("સંસ્કૃત");
//                    spinnerstd.add("સામાજિક વિજ્ઞાન");
//                    spinnerstd.add("હિન્દી");
                } else {
                    index = 3;
//                    spinnerstd.add("all");
//                    spinnerstd.add("B.A.");                                //for every standard there will be and array
//                    spinnerstd.add("S.P.C.C.");
//                    spinnerstd.add("અંગ્રેજી");
//                    spinnerstd.add("આંકડાશાસ્ત્ર");       // This will solve be useful as student completes in one standard and moves to next
//                    spinnerstd.add("આર્થશાસ્ત્ર");
//                    spinnerstd.add("એકાઉન્ટ");
//                    spinnerstd.add("ગુજરાતી");
//                    spinnerstd.add("તત્વજ્ઞાન");
//                    spinnerstd.add("સમાજશાસ્ત્ર");
//                    spinnerstd.add("મનોવિજ્ઞાન");
//                    spinnerstd.add("સંસ્કૃત");
                }
           // }
        } else if (passStd.charAt(0) == '7' || passStd.charAt(0) == '8' || passStd.charAt(0) == '6') {
            index = 2;
//            spinnerstd.add("all");
//            spinnerstd.add("અંગ્રેજી");                                //for every standard there will be and array
//            spinnerstd.add("ગણિત");                               // This will solve be useful as student completes in one standard and moves to next
//            spinnerstd.add("ગુજરાતી");
//            spinnerstd.add("વિજ્ઞાન");
//            spinnerstd.add("સંસ્કૃત");
//            spinnerstd.add("સામાજિક વિજ્ઞાન");
//            spinnerstd.add("હિન્દી");
        } else {
            index = 1;
//            spinnerstd.add("all");
//            spinnerstd.add("કલરવ");
//            spinnerstd.add("ગણિત- ગમ્મત");
//            spinnerstd.add("મારી આસપાસ");
//            spinnerstd.add("ગુજરાતી");
//            spinnerstd.add("આસપાસ");
//            spinnerstd.add("અંગ્રેજી");
//            spinnerstd.add("હિન્દી");
//            spinnerstd.add("સૌની-આસપાસ");
        }

//        spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, spinnerstd);
//        spinner.setAdapter(spinnerAdapter);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child(stdreference);                //getting reference one for marks, name and one for subject, date
        subref = database.getReference().child(subdatereference);
        myRef.keepSynced(true);
        subref.keepSynced(true);                                            //This will ..............


        subjectlist1to5 = new ArrayList<String>();
        subjectlist1to5.add("કલરવ");
        subjectlist1to5.add("ગણિત- ગમ્મત");
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
        subjectlist11and12.add("આર્થશાસ્ત્ર");
        subjectlist11and12.add("એકાઉન્ટ");
        subjectlist11and12.add("ગુજરાતી");
        subjectlist11and12.add("તત્વજ્ઞાન");
        subjectlist11and12.add("સમાજશાસ્ત્ર");
        subjectlist11and12.add("મનોવિજ્ઞાન");
        subjectlist11and12.add("સંસ્કૃત");

        myRef.addValueEventListener(new ValueEventListener() {
          //  @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    UserInformation info = ds.getValue(UserInformation.class);          //where we got reference, it takes snapshot, looping will give us acces to each child

                    if (count == rollnoforcheck - 1) {                                               //loop till we get his rollnumber
//                        anothertxt = info.getName();                                    //getting name and marks from userinfocalss
//                        studentname.setText(anothertxt);
                        assert info != null;
                        finalmarks= info.getMarks();
                        getmarksofstudent(finalmarks);
                        break;
                    } else {
                        count++;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        LogoutButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                editor.putBoolean("activity_executed", false).apply();
//                Intent intent = new Intent(resubscribe.this, MainActivity.class);
//                startActivity(intent);
//            }
//        });

    }

    private void getmarksofstudent(final String marks){


        subref.addValueEventListener(new ValueEventListener() {
         //   @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int previousmarkcounter=0;
                int nextmarkcounter =0;
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    UserInformation info = ds.getValue(UserInformation.class);
                    tempdate = info.getDate();
                    tempsubject = info.getSubject();
                    temptotalmarks = info.getTotalmarks();
                    chapter = info.getChapter();
                  //  marks = info.getMarks();

                    TableRow.LayoutParams textViewParam = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT, 1.0f);
                    TableLayout tableLayout = findViewById(R.id.tabulaout);
                    final TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT);
                    final TableRow trHead = new TableRow(resubscribe.this);
                    trHead.setLayoutParams(tableRowParams);

                    nextmarkcounter = nextmarkcounter + tempsubject.length();

                    setdateandsubject(tempdate,tempsubject,temptotalmarks,chapter,textViewParam, trHead, tableLayout, tableRowParams, marks, previousmarkcounter, nextmarkcounter);
                    previousmarkcounter = nextmarkcounter;

                }
                findViewById(R.id.loadingPanel).setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void setdateandsubject(String date, String subject, String totalmarks,String chapter, TableRow.LayoutParams textViewParam, TableRow trHead, TableLayout tableLayout, TableLayout.LayoutParams tableRowParams, String marks, int previousmarkcounter, int nextmarkcounter) {


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
        String str = subject;
        int leng = subject.length();
        String ttmarks= totalmarks;

        int initialstart = previousmarkcounter*2;
        int finalstart = nextmarkcounter*2;
        ArrayList<String> tempmarklist = new ArrayList<>();
        int arraylistcounter =0;
        for(int j=initialstart; j<finalstart;j=j+2){
            String temp ="";
            if(marks.charAt(j)=='*' ){
                tempmarklist.add(arraylistcounter, "100");
            }else {
                temp = temp + marks.charAt(j) + marks.charAt(j + 1);
                tempmarklist.add(arraylistcounter, temp);
            }
            arraylistcounter++;
        }

        if (index == 1) {
            for (int i = 0; i < leng; i++) {
                char stringID = str.charAt(i);
                int ind = stringID - '0';


                TableRow sub = new TableRow(this);
                sub.setLayoutParams(tableRowParams);
                TextView subnew = new TextView(this);
                subnew.setLayoutParams(textViewParam);
                subnew.setGravity(Gravity.CENTER_HORIZONTAL);
                String setstring = subjectlist1to5.get(ind)+" "+chapter;
                subnew.setText(setstring);
                subnew.setTextSize(17);
                subnew.setTextColor(Color.rgb(0, 0, 0));
                subnew.setBackground(gd);
                sub.addView(subnew);

                //  Log.d("tag","x="+marklist.get(0));
                String finalstring = "";

                TextView dumbnew = new TextView(this);
                dumbnew.setLayoutParams(textViewParam);
                finalstring =  tempmarklist.get(i) + '/' + totalmarks;


                dumbnew.setGravity(Gravity.CENTER);
                dumbnew.setGravity(Gravity.CENTER_HORIZONTAL);
              //  dumbnew.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                dumbnew.setText(finalstring);
                dumbnew.setTextSize(17);
                dumbnew.setTextColor(Color.rgb(0, 0, 0));
                dumbnew.setBackground(gd);
                sub.addView(dumbnew);

                tableLayout.addView(sub);
            }
        }

        if (index == 2) {
            for (int i = 0; i < leng; i++) {
                char stringID = str.charAt(i);
                int ind = stringID - '0';
                // Log.d("tag","x="+ind);
                TableRow sub = new TableRow(this);
                sub.setLayoutParams(tableRowParams);

                TextView subnew = new TextView(this);
                subnew.setLayoutParams(textViewParam);

                subnew.setGravity(Gravity.CENTER_HORIZONTAL);

              //  subnew.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                String setstring = subjectlist6to10.get(ind)+" "+chapter;
                subnew.setText(setstring);
                subnew.setTextSize(17);
                subnew.setTextColor(Color.rgb(0, 0, 0));
                subnew.setBackground(gd);
                sub.addView(subnew);

                String finalstring = "";

                finalstring = tempmarklist.get(i) + '/' + totalmarks;
                TextView dumbnew = new TextView(this);
                dumbnew.setLayoutParams(textViewParam);
                dumbnew.setGravity(Gravity.CENTER_HORIZONTAL);
               // dumbnew.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

                dumbnew.setText(finalstring);
                dumbnew.setTextSize(17);
                dumbnew.setTextColor(Color.rgb(0, 0, 0));
                dumbnew.setBackground(gd);
                sub.addView(dumbnew);

                tableLayout.addView(sub);
            }
        }
        if (index == 3) {
            for (int i = 0; i < leng; i++) {
                char stringID = str.charAt(i);
                int ind = stringID - '0';
                if (ind == 16) {
                    TableRow sub = new TableRow(this);
                    sub.setLayoutParams(tableRowParams);

                    TextView subnew = new TextView(this);
                    subnew.setLayoutParams(textViewParam);

                    String setstring = subjectlist11and12.get(10)+" "+chapter;
                    subnew.setText(setstring);
                    subnew.setTextSize(17);
                    subnew.setGravity(Gravity.CENTER_HORIZONTAL);
                 //   subnew.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    subnew.setTextColor(Color.rgb(0, 0, 0));
                    subnew.setBackground(gd);
                    sub.addView(subnew);

                    String finalstring = "";

                        finalstring = tempmarklist.get(i) + '/' + totalmarks;

                    TextView dumbnew = new TextView(this);
                    dumbnew.setLayoutParams(textViewParam);

                    dumbnew.setText(finalstring);
                    dumbnew.setTextSize(17);
                    dumbnew.setGravity(Gravity.CENTER_HORIZONTAL);
                   // dumbnew.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    dumbnew.setTextColor(Color.rgb(0, 0, 0));
                    dumbnew.setBackground(gd);
                    sub.addView(dumbnew);

                    tableLayout.addView(sub);

                } else {
                    TableRow sub = new TableRow(this);
                    sub.setLayoutParams(tableRowParams);

                    TextView subnew = new TextView(this);
                    subnew.setLayoutParams(textViewParam);


                    String settring = subjectlist11and12.get(ind)+" "+chapter;
                    subnew.setText(settring);
                    subnew.setTextSize(17);
                    subnew.setTextColor(Color.rgb(0, 0, 0));
                  //  subnew.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    subnew.setGravity(Gravity.CENTER_HORIZONTAL);
                    subnew.setBackground(gd);
                    sub.addView(subnew);

                    String finalstring = "";

                    finalstring = tempmarklist.get(i) + '/' + totalmarks;
                    TextView dumbnew = new TextView(this);
                    dumbnew.setLayoutParams(textViewParam);
                    dumbnew.setText(finalstring);
                    dumbnew.setTextSize(17);
                 //   dumbnew.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    dumbnew.setGravity(Gravity.CENTER_HORIZONTAL);
                    dumbnew.setTextColor(Color.rgb(0, 0, 0));
                    dumbnew.setBackground(gd);
                    sub.addView(dumbnew);

                    tableLayout.addView(sub);
                }
            }
        }
    }
}

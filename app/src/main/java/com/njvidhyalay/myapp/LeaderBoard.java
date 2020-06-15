package com.njvidhyalay.myapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
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

public class LeaderBoard extends MainActivity {

    int rollnoforcheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaderboardlayout);


        SharedPreferences settings = getApplicationContext().getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("activity_executed", true).apply();

        String userNameroll, passStd;


        passStd = settings.getString("standard", String.valueOf(0));
        userNameroll = settings.getString("rollnumber", String.valueOf(0));

        //concatenate to make the exact string written in firebase database
        String subdatereference = "subdate" + passStd;
        final String standard = "std"+passStd;

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference markref = database.getReference().child(subdatereference);


        rollnoforcheck = Integer.parseInt(userNameroll);

        markref.addValueEventListener(new ValueEventListener() {
            //  @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    UserInformation info = ds.getValue(UserInformation.class);
                    assert info != null;
                    String marks = info.getMarks();
                    String date = info.getDate();
                    String subject = info.getSubject();
                    String totalmarks = info.getTotalmarks();

                    setleaderboard(marks,date,standard, subject, totalmarks);
                    break;
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
    }

    private void setleaderboard(String marks, String date, String std, final String subject, String totalmarks) {

        ArrayList<Integer> markininteger = new ArrayList<>();
        ArrayList<Integer> indexofstudent = new ArrayList<>();
        int temp;
        int tempofindex;
      //  Log.d("tag","x="+marks.length());

        FirebaseDatabase database;
        database = FirebaseDatabase.getInstance();
        DatabaseReference nameref;


        for (int i=0;i<marks.length();i=i+2){
            if(Character.isDigit(marks.charAt(i))){
            String tempone = ""+marks.charAt(i)+marks.charAt(i+1);
            int temptwo = Integer.parseInt(tempone);
            markininteger.add(temptwo);
            }else{
                markininteger.add(0);
            }
        }

        for(int i=0;i<markininteger.size();i++){
            indexofstudent.add(i);
        }
      //  Log.d("tag","x="+markininteger.size());

        for(int i=0;i<markininteger.size();i++){
            for(int j=i+1;j<markininteger.size();j++){
                if(markininteger.get(i) < markininteger.get(j)){
                    temp = markininteger.get(i);
                    tempofindex = indexofstudent.get(i);
                    markininteger.set(i,markininteger.get(j));
                    indexofstudent.set(i,indexofstudent.get(j));
                    markininteger.set(j,temp);
                    indexofstudent.set(j,tempofindex);
                }
            }
        }

        for(int i=0;i<markininteger.size();i++){
            Log.d("tag","x="+markininteger.get(i));
        }


//        for(int i=0;i<markininteger.size();i++){
//            Log.d("tag","x="+markininteger.get(i));
//        }
//
//        //Log.d("tag","x="+indexofstudent.size());
//        for(int i=0;i<markininteger.size();i++){
//            Log.d("tag","x="+indexofstudent.get(i));
//        }
        final TableRow.LayoutParams textViewParam = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT, 1.0f);
        final TableLayout tableLayout = findViewById(R.id.tabulaout);
        final TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT);
        final TableRow trHead = new TableRow(LeaderBoard.this);
        trHead.setLayoutParams(tableRowParams);

        final GradientDrawable gd = new GradientDrawable();
        gd.setColor(Color.rgb(186,186,186));
        gd.setStroke(1, 0xFF000000);

        final GradientDrawable gdofstudentname = new GradientDrawable();
        gdofstudentname.setColor(Color.rgb(255,255,255));
        gdofstudentname.setStroke(1, 0xFFBBBBBB);

        TextView nameHead = new TextView(LeaderBoard.this);
        nameHead.setLayoutParams(textViewParam);
        String DateString = "તા. " + date;
        nameHead.setText(DateString);
        nameHead.setTextSize(17);
        nameHead.setGravity(Gravity.CENTER);
        nameHead.setTextColor(Color.rgb(0, 0, 0));
        nameHead.setBackground(gd);
        trHead.addView(nameHead);

        final TableRow subejectandmarks = new TableRow(LeaderBoard.this);
        subejectandmarks.setLayoutParams(tableRowParams);

        TextView subjecthead = new TextView(LeaderBoard.this);
        subjecthead.setLayoutParams(textViewParam);
        subjecthead.setText(subject);
        subjecthead.setTextSize(17);
        subjecthead.setGravity(Gravity.CENTER);
        subjecthead.setTextColor(Color.rgb(0, 0, 0));
        subjecthead.setBackground(gd);
        subejectandmarks.addView(subjecthead);

        tableLayout.addView(trHead);
        tableLayout.addView(subejectandmarks);

        int count=0;
        int index=0;


        while(count<5) {

            final int tempcount = count + 1;
            final String marksofstudenttoset = markininteger.get(index).toString();
            final String settotalmarks = totalmarks;


            if (!markininteger.get(index).equals(markininteger.get(index + 1))) {

                final TableRow subrow = new TableRow(LeaderBoard.this);
                subrow.setLayoutParams(tableRowParams);

                int in = indexofstudent.get(index)+1;
                String indexofstudentset = Integer.toString(in);

                nameref = database.getReference().child(std).child(indexofstudentset).child("name");


                nameref.addValueEventListener(new ValueEventListener() {
                    //  @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String name = dataSnapshot.getValue(String.class);
                        String nametoset = "" + tempcount + ". " + name;
                        TextView subnew = new TextView(LeaderBoard.this);
                        subnew.setLayoutParams(textViewParam);
                        subnew.setGravity(Gravity.LEFT);
                        subnew.setPadding(20,0,0,0);
                        subnew.setText(nametoset);
                        subnew.setTextSize(17);
                        subnew.setTextColor(Color.rgb(0, 0, 0));
                        subnew.setBackground(gdofstudentname);
                        subrow.addView(subnew);

                        String markstoset = "" + marksofstudenttoset + "/" + settotalmarks;

                        TextView markview = new TextView(LeaderBoard.this);
                        markview.setLayoutParams(textViewParam);
                        markview.setGravity(Gravity.CENTER);
                        markview.setText(markstoset);
                        markview.setTextSize(17);
                        markview.setTextColor(Color.rgb(0, 0, 0));
                        markview.setBackground(gdofstudentname);
                        subrow.addView(markview);

                        tableLayout.addView(subrow);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                index++;
                count++;
            } else {

               while(markininteger.get(index).equals(markininteger.get(index + 1))) {
                   if(!markininteger.get(index).equals(markininteger.get(index + 1))){
                       break;
                   }
                   final TableRow elserow = new TableRow(LeaderBoard.this);
                   elserow.setLayoutParams(tableRowParams);

                   int in = indexofstudent.get(index) + 1;
                   String indexofstudentset = Integer.toString(in);

                   nameref = database.getReference().child(std).child(indexofstudentset).child("name");

                   nameref.addValueEventListener(new ValueEventListener() {
                       //  @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                       @Override
                       public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                           String name = dataSnapshot.getValue(String.class);
                           String nametoset = "" + tempcount + ". " + name;
                           TextView subnew = new TextView(LeaderBoard.this);
                           subnew.setLayoutParams(textViewParam);
                           subnew.setGravity(Gravity.LEFT);
                           subnew.setPadding(20,0,0,0);
                           subnew.setText(nametoset);
                           subnew.setTextSize(17);
                           subnew.setTextColor(Color.rgb(0, 0, 0));
                           subnew.setBackground(gdofstudentname);
                           elserow.addView(subnew);

                           String markstoset = "" + marksofstudenttoset + "/" + settotalmarks;

                           TextView markview = new TextView(LeaderBoard.this);
                           markview.setLayoutParams(textViewParam);
                           markview.setGravity(Gravity.CENTER);
                           markview.setText(markstoset);
                           markview.setTextSize(17);
                           markview.setTextColor(Color.rgb(0, 0, 0));
                           markview.setBackground(gdofstudentname);
                           elserow.addView(markview);

                           tableLayout.addView(elserow);

                       }

                       @Override
                       public void onCancelled(@NonNull DatabaseError databaseError) {

                       }
                   });
                   index++;
               }

                final TableRow elserowtwo = new TableRow(LeaderBoard.this);
                elserowtwo.setLayoutParams(tableRowParams);

                int inos = indexofstudent.get(index)+1;
                String indexofstudentsetplusone = Integer.toString(inos);


                nameref = database.getReference().child(std).child(indexofstudentsetplusone).child("name");


                nameref.addValueEventListener(new ValueEventListener() {
                    //  @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String name = dataSnapshot.getValue(String.class);
                        String nametoset = ""+tempcount+". "+name;
                        TextView subnew = new TextView(LeaderBoard.this);
                        subnew.setLayoutParams(textViewParam);
                        subnew.setGravity(Gravity.LEFT);
                        subnew.setPadding(20,0,0,0);
                        subnew.setText(nametoset);
                        subnew.setTextSize(17);
                        subnew.setTextColor(Color.rgb(0, 0, 0));
                        subnew.setBackground(gdofstudentname);
                        elserowtwo.addView(subnew);

                        String markstoset = ""+ marksofstudenttoset+"/"+settotalmarks;

                        TextView markview = new TextView(LeaderBoard.this);
                        markview.setLayoutParams(textViewParam);
                        markview.setGravity(Gravity.CENTER);
                        markview.setText(markstoset);
                        markview.setTextSize(17);
                        markview.setTextColor(Color.rgb(0, 0, 0));
                        markview.setBackground(gdofstudentname);
                        elserowtwo.addView(markview);

                        tableLayout.addView(elserowtwo);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                index++;
                count++;
            //    index = index + 1;
                // Log.d("tag","x="+markininteger.get(index));
            }
        }


        final TableRow congrats = new TableRow(LeaderBoard.this);
        congrats.setLayoutParams(tableRowParams);

        TextView congratstextview = new TextView(LeaderBoard.this);
        congratstextview.setLayoutParams(textViewParam);
        congratstextview.setGravity(Gravity.CENTER);
        congratstextview.setText("💐 ખુબ ખુબ અભિનંદન 💐");
        congratstextview.setTextSize(17);
        congratstextview.setTextColor(Color.rgb(0, 0, 0));
        congratstextview.setBackground(gdofstudentname);
        congrats.addView(congratstextview);

        tableLayout.addView(congrats);
    }
}

package com.njvidhyalay.myapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
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

public class Marks extends MainActivity {

    private FirebaseDatabase database;
    private DatabaseReference subref;
    int rollnoforcheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewresult);



        SharedPreferences settings = getApplicationContext().getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("activity_executed", true).apply();

        String userNameroll, passStd;


        passStd = settings.getString("standard", String.valueOf(0));
        userNameroll = settings.getString("rollnumber", String.valueOf(0));

                                                          //concatenate to make the exact string written in firebase database
        String subdatereference = "subdate" + passStd;

        database = FirebaseDatabase.getInstance();
        subref = database.getReference().child(subdatereference);


        rollnoforcheck = Integer.parseInt(userNameroll);
        subref.addValueEventListener(new ValueEventListener() {
            //  @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    UserInformation info = ds.getValue(UserInformation.class);          //where we got reference, it takes snapshot, looping will give us acces to each child
                                                                   //loop till we get his rollnumber
                        assert info != null;
                        String  marks= info.getMarks();
                        String date = info.getDate();
                        String subject = info.getSubject();
                        String totalmarks = info.getTotalmarks();


                    char temp = marks.charAt(rollnoforcheck-1);
                    String  finalmarksstring;
                    if(temp=='*'){
                        finalmarksstring = "100"+ "/"+totalmarks;
                    }else {
                        finalmarksstring = ""+temp + marks.charAt(rollnoforcheck) + "/" + totalmarks;
                    }

                    TableRow.LayoutParams textViewParam = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT, 1.0f);
                    TableLayout tableLayout = findViewById(R.id.tabulaout);
                    final TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT);
                    final TableRow trHead = new TableRow(Marks.this);
                    trHead.setLayoutParams(tableRowParams);

                    GradientDrawable gd = new GradientDrawable();
                    gd.setColor(0xFFFFFFFF);
                    gd.setStroke(1, 0xFFBBBBBB);

                    TextView nameHead = new TextView(Marks.this);
                    nameHead.setLayoutParams(textViewParam);
                    String DateString = "તા. " + date;
                    nameHead.setText(DateString);
                    nameHead.setTextSize(17);
                    nameHead.setGravity(Gravity.CENTER);
                    nameHead.setTextColor(Color.rgb(0, 0, 0));
                    nameHead.setBackgroundColor(Color.rgb(186, 186, 186));
                    trHead.addView(nameHead);
                    tableLayout.addView(trHead);


                    TableRow subrow = new TableRow(Marks.this);
                    subrow.setLayoutParams(tableRowParams);

                    TextView subnew = new TextView(Marks.this);
                    subnew.setLayoutParams(textViewParam);
                    subnew.setGravity(Gravity.CENTER);
                    subnew.setText(subject);
                    subnew.setTextSize(17);
                    subnew.setTextColor(Color.rgb(0, 0, 0));
                    subnew.setBackground(gd);
                    subrow.addView(subnew);

                    TextView markview = new TextView(Marks.this);
                    markview.setLayoutParams(textViewParam);
                    markview.setGravity(Gravity.CENTER);
                    markview.setText(finalmarksstring);
                    markview.setTextSize(17);
                    markview.setTextColor(Color.rgb(0, 0, 0));
                    markview.setBackground(gd);
                    subrow.addView(markview);

                    tableLayout.addView(subrow);
                }
                findViewById(R.id.loadingPanel).setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

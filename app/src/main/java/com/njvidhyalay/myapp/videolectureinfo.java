package com.njvidhyalay.myapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.Gravity;
import android.view.View;
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

public class videolectureinfo extends AppCompatActivity {

    String date="", link="", std="", subject="", note="";


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.videolecturedetail);


        final String  passStd;

        SharedPreferences settings = getApplicationContext().getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);

        passStd = settings.getString("standard", String.valueOf(0));

        String linkref = "std"+passStd;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child(linkref).child("linkdetails");


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
                }
                findViewById(R.id.loadingPanel).setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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

    }
}

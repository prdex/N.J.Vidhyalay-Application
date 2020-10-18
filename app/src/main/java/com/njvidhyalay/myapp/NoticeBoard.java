package com.njvidhyalay.myapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NoticeBoard extends MainActivity {


    private FirebaseDatabase database;
    private DatabaseReference subref;
    Context context;
    CardView cardview;
    LinearLayout.LayoutParams layoutparams;
    TextView textview;
    LinearLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.noticeboardlayout);


        SharedPreferences settings = getApplicationContext().getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("activity_executed", true).apply();

        final String passStd;


        passStd = settings.getString("standard", String.valueOf(0));

        //concatenate to make the exact string written in firebase database
        final String stdreference = "std" + passStd;

        database = FirebaseDatabase.getInstance();
        subref = database.getReference().child(stdreference).child("noticeboard");

        context = getApplicationContext();
        relativeLayout = findViewById(R.id.noticeviewlinearlayout);


        subref.addValueEventListener(new ValueEventListener() {
            //  @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    linkuserinfo info = ds.getValue(linkuserinfo.class);
                    assert info != null;
                    String note = info.getNote();
                    String date = info.getDate();
                    String stringtoset = "તા :- "+date+"\nનોંધ :- "+note;

                    cardview = new CardView(context);
                    layoutparams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );
                    layoutparams.setMargins(0, 20, 0, 0);
                    cardview.setLayoutParams(layoutparams);

                    cardview.setRadius(15);
                    cardview.setCardBackgroundColor(Color.rgb(214,214,214));
                    cardview.setMaxCardElevation(30);
                    cardview.setMaxCardElevation(6);

                    textview = new TextView(context);
                    textview.setLayoutParams(layoutparams);
                    textview.setText(stringtoset);
                    textview.setTextSize(17);

                    textview.setPadding(25,25,25,25);
                    textview.setTextColor(Color.BLACK);
                    textview.setGravity(Gravity.CENTER);
                    cardview.addView(textview);

                    relativeLayout.addView(cardview);
                }
                findViewById(R.id.loadingPanel).setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

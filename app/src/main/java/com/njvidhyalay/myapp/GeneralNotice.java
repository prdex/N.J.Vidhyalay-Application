package com.njvidhyalay.myapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.MailTo;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class GeneralNotice extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    Context context;
    CardView cardview;
    LinearLayout.LayoutParams layoutparams;
    TextView textview;
    LinearLayout relativeLayout;

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(GeneralNotice.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generalnoticelayout);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child("generalNotice");


        context = getApplicationContext();
        relativeLayout = findViewById(R.id.generalnoticelayoutview);

        myRef.addValueEventListener(new ValueEventListener() {
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
                findViewById(R.id.generalnoticeloading).setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

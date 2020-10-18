package com.njvidhyalay.myapp;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class quiz extends MainActivity {

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    CardView cardview;
    Context context;
    LinearLayout.LayoutParams layoutparams;
    ArrayList<String> textbookname =  new ArrayList<String>();;
    TextView dumbview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quizlayout);
        database = FirebaseDatabase.getInstance();
        context = getApplicationContext();

        final String passStd;

        SharedPreferences settings = getApplicationContext().getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);
        passStd = settings.getString("standard", String.valueOf(0));
        final String linkref = "std" + passStd;

        SharedPreferences settingsindex = getSharedPreferences("quizindex", Context.MODE_PRIVATE);
        String indexref = settingsindex.getString("quizindexsubject", String.valueOf(0));

        Log.d("tag","x="+indexref);
        Log.d("tag","x="+linkref);

        assert indexref != null;
        if(indexref.equals("B.A.")){
            myRef = database.getReference().child(linkref).child("quiz").child("BA");
        }
        else if(indexref.equals("S.P.C.C.")){
            myRef = database.getReference().child(linkref).child("quiz").child("SPCC");
        }else{
            myRef = database.getReference().child(linkref).child("quiz").child(indexref);
        }

        final ArrayList<String> Keys = new ArrayList<String>();
        for(int i = 0; i < 100; i ++){
            Keys.add("Keys is : " + String.valueOf(i));
        }


        final Button[] my_button = new Button[Keys.size()];
        myRef.addValueEventListener(new ValueEventListener() {
            int count=0;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    quizinfo info = ds.getValue(quizinfo.class);
                    final String name = info.getName();
                    textbookname.add(name.trim());
                    final String downloadlink = info.getDownloadlink();

                    LinearLayout relativelayout = new LinearLayout(context);
                    relativelayout.setOrientation(LinearLayout.VERTICAL);
                    relativelayout = findViewById(R.id.quizlinearlayout);

                    cardview = new CardView(context);
                    layoutparams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );
                    layoutparams.setMargins(0,0,0,15);
                    cardview.setLayoutParams(layoutparams);
                    cardview.setRadius(15);
                    cardview.setMaxCardElevation(30);
                    cardview.setMaxCardElevation(6);
                    String displayname = "<br /><br />"+name;

                    String actuallink = "<a href=" + downloadlink + "><br />Download Link</a>";
                    Spanned Text;
                    Text = Html.fromHtml(displayname + actuallink);

                    dumbview = new TextView(context);
                    dumbview.setLayoutParams(layoutparams);
                    dumbview.setText(Text);
                    dumbview.setTextSize(17);
                    dumbview.setPadding(25, 25, 25, 25);
                    dumbview.setTextColor(Color.BLACK);
                    dumbview.setGravity(Gravity.CENTER);

                    dumbview.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SharedPreferences settings = getSharedPreferences("DownPREF", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putString("filename", name);
                            editor.apply();
                            editor.commit();
                            Toast.makeText(quiz.this, "Downloading...", Toast.LENGTH_LONG).show();
                            downloadFile(context,name, ".pdf","NJVidhyalay-materials",downloadlink);
                            // Toast.makeText(material.this, "Download Complete", Toast.LENGTH_LONG).show();
                        }
                    });
                    final int Index = count;

                    my_button[Index] = new Button(quiz.this);
                    my_button[Index].setLayoutParams(layoutparams);
                    my_button[Index].setText("View");
                    my_button[Index].setId(Index);

                    my_button[count].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (my_button[Index].getId() == ((Button) v).getId()){
                                Intent intent = new Intent(quiz.this, pdfvie.class);
                                intent.putExtra("material", textbookname.get(Index));
                                startActivity(intent);
                            }
                        }
                    });
                    cardview.addView(dumbview);
                    cardview.addView(my_button[Index]);
                    count++;
//                    cardview.addView(button);
//
//
//                    button.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//
//
//                            Intent intent = new Intent(material.this, pdfvie.class);
//                            startActivity(intent);
//                        }
//                    });
                    relativelayout.addView(cardview);
                }
                findViewById(R.id.quizloading).setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public long downloadFile(Context context, String fileName, String fileextenstin, String destinationdirectory, String url) {

        DownloadManager downloadmanager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context, destinationdirectory, fileName + fileextenstin);

        return downloadmanager.enqueue(request);
    }
}

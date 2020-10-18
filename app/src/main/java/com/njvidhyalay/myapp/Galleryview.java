package com.njvidhyalay.myapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Galleryview extends MainActivity {

    private FirebaseDatabase database;
    private DatabaseReference subref;
    CardView cardview;
    private DatabaseReference updatecountref;
    RelativeLayout relativeLayout;
    LinearLayout.LayoutParams layoutparams, secondlayoutparams, thirdlayoutparams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.galleryactualview);

        SharedPreferences settings = getApplicationContext().getSharedPreferences("galleryview", Context.MODE_PRIVATE);

        String passStd;


        passStd = settings.getString("standartclicked", String.valueOf(0));
        String standardrefence = "images" + passStd;
        String anotherref = "image"+passStd;

        database = FirebaseDatabase.getInstance();
        subref = database.getReference().child(standardrefence);
//        updatecountref = database.getReference().child(anotherref);
//        final ImageButton[] imgbutton = new ImageButton[1000];
//        final TextView[] textviewss = new TextView[1000];
//        final int[] likecountarray = new int[1000];


//        final String[] globalcountarray = new String[1000];
//
//        final int[] tempindex = {0};
//        updatecountref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot insidesnapshot) {
//                for (DataSnapshot ds : insidesnapshot.getChildren()) {
//                    GalleyModelClass info = ds.getValue(GalleyModelClass.class);
//                    String tempcount = info.getCountlike();
//                    globalcountarray[tempindex[0]]  =tempcount;
//                    tempindex[0]++;
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
          // subref.child(String.valueOf(1)).child("countlike");



        final LinearLayout layout = (LinearLayout)findViewById(R.id.galleryviewlayout);
        subref.addValueEventListener(new ValueEventListener() {
           // int indexforcount=0;
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    GalleyModelClass info = ds.getValue(GalleyModelClass.class);

                    final String url = info.getLink();
                    final String name = info.getName();
                 //   String scount = info.getCountlike();
                 //   final int countlike = Integer.parseInt(scount);
                 //   likecountarray[indexforcount] = countlike;
                  //  Log.d("tag","x="+url);

                    cardview = new CardView(Galleryview.this);

                    layoutparams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );
                    layoutparams.setMargins(0, 20, 0, 0);
                  //  layoutparams.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);

                    cardview.setLayoutParams(layoutparams);
                    //cardview.setRadius(15);
                    cardview.setMaxCardElevation(30);
                    cardview.setMaxCardElevation(6);


                    TextView subnew = new TextView(Galleryview.this);
                    subnew.setLayoutParams(layoutparams);
                    subnew.setGravity(Gravity.CENTER);
                    //subnew.setGravity(Gravity.CENTER);
                   //subnew.setPadding(25,25,25,25);
                    subnew.setText(name);
                    subnew.setTextSize(17);
                    subnew.setTextColor(Color.rgb(0, 0, 0));



                    ImageView image = new ImageView(Galleryview.this);
                    image.setPadding(60,60 ,60,60);
                    image.setLayoutParams(layoutparams);
                    findViewById(R.id.loadingPanelgalleryactualview).setVisibility(View.VISIBLE);
                    int temp =1;


                    Picasso.get().load(url).into(image);
                    while(true){
                        if(temp==1) {
                            findViewById(R.id.loadingPanelgalleryactualview).setVisibility(View.GONE);
                            break;
                        }
                    }

                 //  findViewById(R.id.loadingPanelgalleryactualview).setVisibility(View.GONE);

                    cardview.addView(image);
                    cardview.addView(subnew);




//                    final int actualindex = indexforcount;
//
//                    secondlayoutparams = new LinearLayout.LayoutParams(
//                            80,
//                            60
//                    );
//
//                    imgbutton[actualindex] = new ImageButton(Galleryview.this);
//                    imgbutton[actualindex].setLayoutParams(secondlayoutparams);
//
//                    imgbutton[actualindex].setImageResource(R.drawable.unlike);
//                  //  if(imgbutton[actualindex] )
//
//                    textviewss[actualindex] = new TextView(Galleryview.this);
//                    textviewss[actualindex].setLayoutParams(secondlayoutparams);
//                    textviewss[actualindex].setGravity(Gravity.CENTER);
//                    textviewss[actualindex].setText(globalcountarray[tempindex[actualindex]] );
//
//                    imgbutton[actualindex].setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            imgbutton[actualindex].setImageResource(R.drawable.like);
//                            //textviewss[actualindex].setText(String.valueOf(likecountarray[actualindex]+1));
//                            updatecountref.child(String.valueOf(actualindex)).child("countlike").setValue(String.valueOf(likecountarray[actualindex]+1));
//
//                        }
//                    });
//
//
                    layout.addView(cardview);

//                    layout.addView(imgbutton[actualindex]);
//                    layout.addView(textviewss[actualindex]);
//
//                    indexforcount++;

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
       //findViewById(R.id.loadingPanelgalleryactualview).setVisibility(View.GONE);

    }

}



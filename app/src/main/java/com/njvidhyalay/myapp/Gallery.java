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

import java.util.ArrayList;

public class Gallery extends MainActivity{

    LinearLayout.LayoutParams layoutparams;
    TextView textview;
    LinearLayout relativeLayout;

    ArrayList<String> standarddigits;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.galleyview);
        relativeLayout = findViewById(R.id.lineralayoutgallery);

        standarddigits = new ArrayList<String>();
        standarddigits.add("બાલમંદિર");
        standarddigits.add("ધો. ૧");
        standarddigits.add("ધો. ૨");
        standarddigits.add("ધો. ૩");
        standarddigits.add("ધો. ૪");
        standarddigits.add("ધો. ૫");
        standarddigits.add("ધો. ૬");
        standarddigits.add("ધો. ૭");
        standarddigits.add("ધો. ૮");
        standarddigits.add("ધો. ૯");
        standarddigits.add("ધો. ૧૦");
        standarddigits.add("ધો. ૧૧");
        standarddigits.add("ધો. ૧૨");


        layoutparams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        layoutparams.setMargins(0, 20, 0, 0);

        int j;
        for (j = 0; j < 13; j++) {
            textview = new TextView(Gallery.this);
            textview.setLayoutParams(layoutparams);
            textview.setText(standarddigits.get(j));
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
                    SharedPreferences settings = getSharedPreferences("galleryview", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("standartclicked", String.valueOf(finalJ));
                    editor.apply();
                    editor.commit();

                    Intent intent = new Intent(Gallery.this, Galleryview.class);
                    startActivity(intent);
                }
            });
            findViewById(R.id.loadingpanelgallery).setVisibility(View.GONE);
        }
    }
}

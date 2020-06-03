package com.njvidhyalay.myapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnErrorListener;

import java.io.File;

public class pdfvie extends AppCompatActivity {

    PDFView pdfView;
    String filename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pdfviewer);
        Bundle bundle = getIntent().getExtras();
        String message = bundle.getString("material");


       // filename = settings.getString("filename", null);
        filename = message.trim();
        filename = filename+".pdf";
        Log.d("tag","x="+filename);
        filename = filename.trim();
        String filepath = "/storage/emulated/0/Android/data/com.njvidhyalay.myapp/files/NJVidhyalay-materials/"+filename;
        pdfView = findViewById(R.id.pdfView);
        viewpdf(filepath);
    }

    private void viewpdf(String filepathwithname){
        try{
            File file = new File(filepathwithname);
            pdfView.fromFile(file).enableSwipe(true).swipeHorizontal(false).onError(new OnErrorListener() {
                @Override
                public void onError(Throwable t) {
                    Log.e("file","file"+t.toString());
                }
            }).enableAntialiasing(true).spacing(0).load();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

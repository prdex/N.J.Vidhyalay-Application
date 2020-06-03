package com.njvidhyalay.myapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnErrorListener;

import java.io.File;

public class bookpdfview  extends AppCompatActivity {

        PDFView pdfView;
        String filename;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.pdfbookview);
            Bundle bundle = getIntent().getExtras();
            String message = bundle.getString("message");

            SharedPreferences settings = getApplicationContext().getSharedPreferences("DownPRtextbook", Context.MODE_PRIVATE);

         //   filename = settings.getString("filename", null).trim();
            filename = message.trim();
            filename = filename+".pdf";
            Log.d("tag","x="+filename);
            filename = filename.toString().trim();
            String filepath = "/storage/emulated/0/Android/data/com.njvidhyalay.myapp/files/NJVidhyalay-textbooks/"+filename;
            pdfView = findViewById(R.id.pdfViewbook);
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

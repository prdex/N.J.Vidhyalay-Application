package com.njvidhyalay.myapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class linkuserinfo {

    private String date;
    private String link;
    private String std;
    private String subject;
    private String note;


    public linkuserinfo() {

    }

    public String getNote() {
        return note;
    }

    public void setStd(String std) {
        this.std = std;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDate() {
        return date;
    }


    public String getLink() {
        return link;
    }
    public String getStd(){
        return std;
    }

    public void setStd(){
        this.std = std;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public void setLink(String link) {
        this.link = link;
    }


    public static class materials extends AppCompatActivity {
        FirebaseFirestore db;
        StorageReference storageReference;
        RecyclerView mrecyler;

        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.mainmaterial);

            SharedPreferences settings = getApplicationContext().getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);
            final String  passStd;

            passStd = settings.getString("standard", String.valueOf(0));

            String stdreference = "std" + passStd;

            storageReference = FirebaseStorage.getInstance().getReference().child(stdreference);
            mrecyler.setHasFixedSize(true);
            mrecyler.setLayoutManager(new LinearLayoutManager(this));

            db = FirebaseFirestore.getInstance();
        }
    }
}

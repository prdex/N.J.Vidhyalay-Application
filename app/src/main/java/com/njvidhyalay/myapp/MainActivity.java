package com.njvidhyalay.myapp;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

import eu.dkaratzas.android.inapp.update.InAppUpdateManager;

public class MainActivity extends AppCompatActivity {


    private EditText rollno; //std;
    private Button log;
    private TextView mDisplayDate;
    Spinner spinner;
    ArrayList<String> spinnerstd;
    private InAppUpdateManager inAppUpdateManager;


    int count = 0;
    int rollnoforcheck;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       findViewById(R.id.loadingPanelmainactivity).setVisibility(View.GONE);



        rollno = findViewById(R.id.rollno);         //simple, get the fields and sent to resubscribe class on clicking login button
//        std = findViewById(R.id.std);
        log = findViewById(R.id.login);
        spinner = findViewById(R.id.std);
        final String[] date = new String[1];
        ArrayAdapter<String> spinnerAdapter = null;

        spinnerstd = new ArrayList<String>();
        spinnerstd.add("ધો.1");
        spinnerstd.add("ધો.2");
        spinnerstd.add("ધો.3");
        spinnerstd.add("ધો.4");
        spinnerstd.add("ધો.5");
        spinnerstd.add("ધો.6");
        spinnerstd.add("ધો.7");
        spinnerstd.add("ધો.8");
        spinnerstd.add("ધો.9A");
        spinnerstd.add("ધો.9B");
        spinnerstd.add("ધો.10A");
        spinnerstd.add("ધો.10B");
        spinnerstd.add("ધો.11A");
        spinnerstd.add("ધો.11C");
        spinnerstd.add("ધો.12A");
        spinnerstd.add("ધો.12C");

        spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, spinnerstd);
        spinner.setAdapter(spinnerAdapter);
        mDisplayDate = (TextView) findViewById(R.id.tvDate);


        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        MainActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
            //    Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                date[0] = day + "/" + month + "/" + year;
                date[0] = date[0].trim();
                mDisplayDate.setText(date[0]);
            }
        };


        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               findViewById(R.id.loadingPanelmainactivity).setVisibility(View.VISIBLE);
                SharedPreferences settings = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("activity_executed", false).apply();


                String rolling;
                String standa = spinner.getSelectedItem().toString();
                standa = standa.substring(standa.indexOf(".") + 1).trim();

                rolling = rollno.getText().toString().trim();

                if(!rolling.isEmpty()) {
                    rollnoforcheck = Integer.parseInt(rolling);
                }

            //    Log.d("tag","x="+rolling);


                String passStd = "std" + standa;
                passStd = passStd.trim();
                String entereddate = mDisplayDate.getText().toString().trim();
            //    Log.d("tag","x="+entereddate);
                if (rolling.isEmpty() && entereddate.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "રોલ નં અને જન્મ તારીખ ખાલી છે.", Toast.LENGTH_SHORT).show();
                } else if (rolling.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "રોલ નં ખાલી છે.", Toast.LENGTH_SHORT).show();
                } else if (entereddate.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "જન્મ તારીખ છે.", Toast.LENGTH_SHORT).show();
                } else {
                    mathcingdates(passStd, entereddate, standa, rolling);
                }
               findViewById(R.id.loadingPanelmainactivity).setVisibility(View.GONE);
            }
        });
    }

    public void mathcingdates(String passStd, final String date, final String standa, final String rolling) {
        FirebaseDatabase database;
        DatabaseReference myRef;
       // final int rollnoforcheckinthis = Integer.parseInt(rolling);

        database = FirebaseDatabase.getInstance();
    //    Log.d("tag","x="+passStd);
        myRef = database.getReference().child(passStd).child(rolling).child("date");


        myRef.addValueEventListener(new ValueEventListener() {
            //  @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String datess = dataSnapshot.getValue(String.class);
            //    for (DataSnapshot ds : dataSnapshot.getChildren()) {
              //      dateverifymodel infodate = ds.getValue(dateverifymodel.class);  //where we got reference, it takes snapshot, looping will give us acces to each child

                ///    if (countingrollno == rollnoforcheckinthis - 1) {
                        //loop till we get his rollnumber
                   //     assert infodate != null;
                     //   String firebasedate = infodate.getDate();
                    //    Log.d("tag", "x=" + firebasedate);
                        matchstring(datess, date, standa, rolling);
                       // break;
                    //} else {
                      //  count++;
                    }
                //}
            //}

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void matchstring(String firebasestring,String datestring,String standa, String rolling){
        if(firebasestring.matches(datestring)) {

            SharedPreferences settings = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("standard", standa);
            editor.putString("rollnumber", rolling);
            editor.apply();
            editor.commit();

            Intent intent = new Intent(MainActivity.this, AfterLoginOptionClass.class);
            startActivity(intent);
            MainActivity.this.finish();
            }
                else {
                    Toast.makeText(getApplicationContext(), "રોલ નં અથવા જન્મ તારીખ ખોટી છે.", Toast.LENGTH_SHORT).show();
            SharedPreferences settings = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            editor.clear();
            editor.apply();
            }
        }
    }
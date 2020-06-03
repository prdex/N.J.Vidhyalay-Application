package com.njvidhyalay.myapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class assignmentupload extends AppCompatActivity {

    private StorageReference Folder;
    ArrayList<String> subjectlist1to5;
    ArrayList<String> subjectlist6to10;
    ArrayList<String> subjectlist11and12;
    ImageView img;
    private static final int PICK_IMAGE = 100;
    Spinner spinner;
    Uri uri;
    String userNameroll, passStd;
    String text;
    List<String> path;
    TextView mprogresstext;
    ProgressBar mprogress;
    StorageReference rolloref;
    Bitmap bmp;
    private static int  count =0;


    //  @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uploadhomework);

        img = findViewById(R.id.imagepath);
        spinner = findViewById(R.id.spinner);
        mprogress = findViewById(R.id.progressbar);
        mprogresstext = findViewById(R.id.tv_progress);

        mprogress.setVisibility(View.GONE);
        mprogresstext.setVisibility(View.GONE);
        ArrayAdapter<String> spinnerAdapter = null;


        subjectlist1to5 = new ArrayList<String>();
        subjectlist1to5.add("કલરવ");
        subjectlist1to5.add("ગણિત- ગમ્મત");
        subjectlist1to5.add("મારી આસપાસ");
        subjectlist1to5.add("ગુજરાતી");
        subjectlist1to5.add("આસપાસ");
        subjectlist1to5.add("અંગ્રેજી");
        subjectlist1to5.add("હિન્દી");
        subjectlist1to5.add("સૌની-આસપાસ");                              //adding subjects codewise


        subjectlist6to10 = new ArrayList<String>();
        subjectlist6to10.add("અંગ્રેજી");                                //for every standard there will be and array
        subjectlist6to10.add("ગણિત");                               // This will solve be useful as student completes in one standard and moves to next
        subjectlist6to10.add("ગુજરાતી");
        subjectlist6to10.add("વિજ્ઞાન");
        subjectlist6to10.add("સંસ્કૃત");
        subjectlist6to10.add("સામાજિક વિજ્ઞાન");
        subjectlist6to10.add("હિન્દી");

        subjectlist11and12 = new ArrayList<String>();
        subjectlist11and12.add("B.A.");                                //for every standard there will be and array
        subjectlist11and12.add("S.P.C.C.");
        subjectlist11and12.add("અંગ્રેજી");
        subjectlist11and12.add("આંકડાશાસ્ત્ર");       // This will solve be useful as student completes in one standard and moves to next
        subjectlist11and12.add("અર્થશાસ્ત્ર");
        subjectlist11and12.add("એકાઉન્ટ");
        subjectlist11and12.add("ગુજરાતી");
        subjectlist11and12.add("તત્વજ્ઞાન");
        subjectlist11and12.add("સમાજશાસ્ત્ર");
        subjectlist11and12.add("મનોવિજ્ઞાન");
        subjectlist11and12.add("સંસ્કૃત");


//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            final Date date = Calendar.getInstance().getTime();
            String today;
//            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
//                today = formatter.format(date);
//            }else{
                Calendar c = Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);
                today = day + "-" + (month + 1) + "-" + year;
            //}


        SharedPreferences settingsofsubject = getApplicationContext().getSharedPreferences("assignmentindex", Context.MODE_PRIVATE);
        final String subject = settingsofsubject.getString("assignmentindexsubject", String.valueOf(0));

            SharedPreferences settings = getApplicationContext().getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);
            final SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("activity_executed", true).apply();

            userNameroll = settings.getString("rollnumber", String.valueOf(0));
            passStd = settings.getString("standard", String.valueOf(0));

//            int standardLength = passStd.length();
//            if (standardLength >= 2) {
//                char nine = passStd.charAt(0);
//                char oneorzero = passStd.charAt(1);
//                if (nine == '9' || (nine == '1' && oneorzero == '0')) {
//                    spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, subjectlist6to10);
//                } else {
//                    spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, subjectlist11and12);
//                }
//            } else if (passStd.charAt(0) == '7' || passStd.charAt(0) == '8' || passStd.charAt(0) == '6') {
//                spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, subjectlist6to10);
//            } else {
//                spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, subjectlist1to5);
//            }
//
//            spinner.setAdapter(spinnerAdapter);

            String stdreference = "std" + passStd;

            Folder = FirebaseStorage.getInstance().getReference().child(today).child(stdreference);
            Button choose = findViewById(R.id.choose);
            Button upload = findViewById(R.id.upload);

            choose.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onClick(View v) {

                    mprogress.setProgress(0);
                    mprogresstext.setText("0.0%");
                    Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                    startActivityForResult(gallery, PICK_IMAGE);
                }
            });

            upload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    count++;
                    int leng = path.size() - 1;
                    Toast.makeText(assignmentupload.this, "Uploading...", Toast.LENGTH_SHORT).show();
                 //   text = spinner.getSelectedItem().toString();
                    String imagename = userNameroll +"-"+count;
                    rolloref = Folder.child(subject).child(imagename);

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] data = baos.toByteArray();

                    UploadTask uploadTask = rolloref.putBytes(data);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {

                            double pgress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            mprogress.setProgress((int) pgress);
                            mprogresstext.setText(pgress + "%");
                            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                mprogress.setProgressTintList(ColorStateList.valueOf(Color.RED));
                                if (pgress == 100) {
                                    mprogress.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
                                }
                            } else if(pgress == 100){
                                mprogresstext.setText(pgress+"%");
                                Toast.makeText(assignmentupload.this, "Uploaded", Toast.LENGTH_SHORT).show();
                             //   mprogress.setBackgroundColor(Color.GREEN);
                            }
                        }
                    });
                }
            });
        }
    //}
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {



            mprogress.setVisibility(View.VISIBLE);
            mprogresstext.setVisibility(View.VISIBLE);
            uri = data.getData();

            InputStream is = null;
            try {
                is = getContentResolver().openInputStream(uri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            bmp = getResizedBitmap(bitmap,800);

            path = uri.getPathSegments();
            img.setImageURI(uri);
        }
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }
}


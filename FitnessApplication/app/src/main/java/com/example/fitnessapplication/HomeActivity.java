package com.example.fitnessapplication;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 200;
    private CardView reportBt, meBt;
    private ImageView h_meIv, h_homeIv, h_reportIv;
    Button wt, tone, muscle;

    private DbHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent intent = getIntent();
        requestPermissions();

        dbHelper = new DbHelper(getApplicationContext());
        try{
            dbHelper.createDataBase();
        }catch (Exception e){
            Log.d(" >> ", "exception");
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        h_homeIv = findViewById(R.id.h_homeIv);
        h_homeIv.setColorFilter(Color.parseColor("#fa8072"));

        h_meIv = findViewById(R.id.h_meIv);
        h_meIv.setColorFilter(Color.parseColor("#c8c8c8"));

        h_reportIv = findViewById(R.id.h_reportIv);
        h_reportIv.setColorFilter(Color.parseColor("#c8c8c8"));

        reportBt = findViewById(R.id.h_reportBt);
        reportBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ReportActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });

        meBt = findViewById(R.id.h_meBt);
        meBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });

        wt = findViewById(R.id.weightbutton);
        tone = findViewById(R.id.tonebutton);
        muscle = findViewById(R.id.musclebutton);

    }

    public void goToNextPage(View view){
        Intent intent = new Intent(getApplicationContext(), WorkoutHome.class);
        String id="";
        if(view == wt){
            id = "1";
        }
        else if(view == tone){
            id = "2";
        }
        else if(view == muscle){
            id = "3";
        }

        intent.putExtra("ID", id);
        startActivity(intent);

    }

    private boolean checkPermission() {
        // in this method we are checking if the permissions are granted or not and returning the result.
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        if (checkPermission()) {
            // if the permissions are already granted we are calling
            // a method to get all images from our external storage.
            Toast.makeText(this, "Permissions granted..", Toast.LENGTH_SHORT).show();
            //getImagePath();
        } else {
            // if the permissions are not granted we are
            // calling a method to request permissions.
            requestPermission();
        }
    }

    private void requestPermission() {
        //on below line we are requesting the read external storage permissions.
        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        // this method is called after permissions has been granted.
        switch (requestCode) {
            // we are checking the permission code.
            case PERMISSION_REQUEST_CODE:
                // in this case we are checking if the permissions are accepted or not.
                if (grantResults.length > 0) {
                    boolean storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (storageAccepted) {
                        // if the permissions are accepted we are displaying a toast message
                        // and calling a method to get image path.
                        Toast.makeText(this, "Permissions Granted..", Toast.LENGTH_SHORT).show();

                        //getImagePath();
                    } else {
                        // if permissions are denied we are closing the app and displaying the toast message.
                        Toast.makeText(this, "Permissions denied, Permissions are required to use the app..", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }
}
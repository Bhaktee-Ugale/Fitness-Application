package com.example.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

public class HomeActivity extends AppCompatActivity {

    private CardView reportBt, meBt;
    private ImageView h_meIv, h_homeIv, h_reportIv;
    Button wt, tone, muscle;

    private DbHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        dbHelper = new DbHelper(getApplicationContext());
        try{
            dbHelper.createDataBase();
        }
        catch (IOException e){}


        Intent intent = getIntent();

        dbHelper = new DbHelper(this);

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
                startActivity(intent);
            }
        });

        meBt = findViewById(R.id.h_meBt);
        meBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MeActivity.class);
                startActivity(intent);
            }
        });

        wt = (Button) findViewById(R.id.weightbutton);
        wt.setOnClickListener(this);
        tone = (Button) findViewById(R.id.tonebutton);
        tone.setOnClickListener(this);
        muscle = (Button) findViewById(R.id.musclebutton);
        muscle.setOnClickListener(this);
    }

    public void onClick(View v){
        if(v==wt) {
            Intent i = new Intent(HomeActivity.this, WLHome.class);
            startActivity(i);
        }
        else if(v==tone){
            Intent i = new Intent(HomeActivity.this, GTHome.class);
            startActivity(i);
        }
        else if(v==muscle){
            Intent i = new Intent(HomeActivity.this, BMHome.class);
            startActivity(i);
        }
        else if(v==reportBt){
            Intent i = new Intent(HomeActivity.this, ReportActivity.class);
            startActivity(i);
        }
        else if(v==meBt){
            Intent i = new Intent(HomeActivity.this, MeActivity.class);
            startActivity(i);
        }

    }
}
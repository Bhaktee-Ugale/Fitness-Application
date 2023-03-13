package com.example.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class BMHome extends AppCompatActivity implements View.OnClickListener{

    Button btn1,btn2,btn6;
    ImageView homebt,reportbt,profilebt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmhome);
        btn1=(Button) findViewById(R.id.btn1);
        btn2=(Button)findViewById(R.id.btn2) ;
        btn6=(Button)findViewById(R.id.btn6);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn6.setOnClickListener(this);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        homebt=(ImageView) findViewById(R.id.homeBt);
        reportbt=(ImageView) findViewById(R.id.reportBt);
        profilebt=(ImageView) findViewById(R.id.profileBt);
        homebt.setOnClickListener(this);
        reportbt.setOnClickListener(this);
        profilebt.setOnClickListener(this);

    }
    public void onClick(View v){
        if(v==btn6){
            Intent i = new Intent(BMHome.this, BMWorkouts.class);
            startActivity(i);
        }
        else if(v==btn1) {
            Intent i = new Intent(BMHome.this, MuscleArm.class);
            startActivity(i);
        }
        else if(v==btn2){
            Intent i = new Intent(BMHome.this, thigh.class);
            startActivity(i);
        }
        else if(v==homebt){
            Intent i = new Intent(BMHome.this, HomeActivity.class);
            startActivity(i);
        } else if(v==reportbt){
            Intent i = new Intent(BMHome.this, ReportActivity.class);
            startActivity(i);
        } else if(v==profilebt){
            Intent i = new Intent(BMHome.this, MeActivity.class);
            startActivity(i);
        }
    }
}
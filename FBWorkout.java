package com.example.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class FBWorkout extends AppCompatActivity implements View.OnClickListener{

    Button btn1,btn2;
    ImageView homebt,reportbt,profilebt;
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fbworkout);
        btn1=(Button)findViewById(R.id.tejal2);
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
    @Override
    public void onClick(View view) {
        if(view==btn1){
            Intent i=new Intent(FBWorkout.this, FBExe1.class);
            startActivity(i);
        }
        else if(view==homebt){
            Intent i=new Intent(FBWorkout.this, HomePage.class);
            startActivity(i);
        }
        else if(view==reportbt){
            Intent i=new Intent(FBWorkout.this, Default2.class);
            startActivity(i);
        }
        else if(view==profilebt){
            Intent i=new Intent(FBWorkout.this, Default2.class);
            startActivity(i);
        }
    }
}
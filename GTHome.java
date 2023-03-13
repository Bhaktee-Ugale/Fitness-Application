package com.example.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class GTHome extends AppCompatActivity {

    Button btn1, btn2, btn3, btn4, btn5, btn6;
    ImageView homebt,reportbt,profilebt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gthome);
        btn1=(Button) findViewById(R.id.btn1);
        btn1.setOnClickListener(this);
        btn2=(Button) findViewById(R.id.btn2);
        btn2.setOnClickListener(this);
        btn3=(Button) findViewById(R.id.btn6);
        btn3.setOnClickListener(this);
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
    public void onClick(View v) {
        if (v == btn1) {
            Intent i = new Intent(GTHome.this, ArmWorkouts.class);
            startActivity(i);
        } else if (v == btn2) {
            Intent i = new Intent(GTHome.this, AbsWorkout.class);
            startActivity(i);
        } else if (v == btn3) {
            Intent i = new Intent(GTHome.this, GTWorkouts.class);
            startActivity(i);
        } else if (v == homebt) {
            Intent i = new Intent(GTHome.this, HomePage.class);
            startActivity(i);
        } else if (v == reportbt) {
            Intent i = new Intent(GTHome.this, Default2.class);
            startActivity(i);
        } else if (v == profilebt) {
            Intent i = new Intent(GTHome.this, Default2.class);
            startActivity(i);
        }
    }
}
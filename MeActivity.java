package com.example.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

public class MeActivity extends AppCompatActivity {

    private CardView myprofile, remainder, rest, countdownTm, privacyPolicy, rateus, feedback, resetProgress, homebt, reportbt;
    private ImageView m_meIv, m_homeIv, m_reportIv;
    private TextView restTv, countdownTv;

    private Button logoutBt;

    private DbHelper dbHelper;

    private String id, resttxt, countdown;
    private String np;
    private static TextView tv;
    static Dialog d ;
    private boolean isitF=true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me);

        dbHelper = new DbHelper(this);

        Intent intent = getIntent();

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        //dbHelper = new DbHelper(this);

        m_meIv = findViewById(R.id.m_meIv);
        m_meIv.setColorFilter(Color.parseColor("#fa8072"));

        m_homeIv = findViewById(R.id.m_homeIv);
        m_homeIv.setColorFilter(Color.parseColor("#c8c8c8"));

        m_reportIv = findViewById(R.id.m_reportIv);
        m_reportIv.setColorFilter(Color.parseColor("#c8c8c8"));


        homebt = findViewById(R.id.m_homeBt);
        reportbt = findViewById(R.id.m_reportBt);
        myprofile = findViewById(R.id.profileBt);
        remainder = findViewById(R.id.remainderBt);
        rest = findViewById(R.id.restBt);
        countdownTm = findViewById(R.id.countDnBt);
        privacyPolicy = findViewById(R.id.policyBt);
        rateus = findViewById(R.id.rateBt);
        feedback = findViewById(R.id.feedbackBt);
        resetProgress = findViewById(R.id.resetProgressBt);
        logoutBt = findViewById(R.id.logoutBt);
        restTv = findViewById(R.id.restTv);
        countdownTv = findViewById(R.id.countDnTv);

        if(isitF){
            restTv.setText("5 s");
            countdownTv.setText("10 s");
        }


        homebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            }
        });

        reportbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ReportActivity.class);
                startActivity(intent);
            }
        });

        myprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);
            }
        });

        remainder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RemainderPage.class);
                startActivity(intent);
            }
        });

        rest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = "Rest";
                show(str);
            }
        });

        countdownTm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = "Countdown";
                show(str);
            }
        });

        privacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);
            }
        });

        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);
            }
        });

        rateus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);
            }
        });

        resetProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //dbHelper.deleteDailyWorkoutsDetails(id);
                Toast.makeText(getApplicationContext(), "Progress Reseted Successfully...", Toast.LENGTH_SHORT).show();
            }
        });

        logoutBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        //Intent intent = getIntent();

    }

    public void show(String str){
        final Dialog d = new Dialog(MeActivity.this, R.style.Dialog);

        if(str.equals("Rest")){
            d.setTitle("Set Duration (5-180 s)");
        }
        else{
            d.setTitle("Set Duration (10-30 s)");
        }
        d.setContentView(R.layout.dialog);
        Button set = (Button) d.findViewById(R.id.setBt);
        Button cancel = (Button) d.findViewById(R.id.cancelBt);
        final NumberPicker np = (NumberPicker) d.findViewById(R.id.numberPicker1);
        if(str.equals("Rest")){
            np.setMaxValue(180);
            np.setMinValue(5);
        }
        else{
            np.setMaxValue(30);
            np.setMinValue(10);
        }
        np.setWrapSelectorWheel(true);
        //np.setOnValueChangedListener();
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(str.equals("Rest")){
                    restTv.setText(String.valueOf(np.getValue())+" s");
                }
                else{
                    countdownTv.setText(String.valueOf(np.getValue())+" s");
                }
                d.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                d.dismiss();
            }
        });
        d.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    public void loadData(){
        ModelRestCountdownTime modelRestCountdownTime = dbHelper.getRestCountdownTime(id);
        restTv.setText(modelRestCountdownTime.rest+" s");
        countdownTv.setText(modelRestCountdownTime.countdown+" s");
    }
}
package com.example.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ReportActivity extends AppCompatActivity {

    private CardView homeBt, reportBt, meBt;
    private ImageView r_meIv, r_homeIv, r_reportIv;
    private TextView r_meTv, r_homeTv, r_reportTv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        Intent intent = getIntent();

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        r_reportIv = findViewById(R.id.r_reportIv);
        r_reportIv.setColorFilter(Color.parseColor("#fa8072"));

        r_meIv = findViewById(R.id.r_meIv);
        r_meIv.setColorFilter(Color.parseColor("#c8c8c8"));

        r_homeIv = findViewById(R.id.r_homeIv);
        r_homeIv.setColorFilter(Color.parseColor("#c8c8c8"));

        homeBt = findViewById(R.id.r_homeBt);
        homeBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            }
        });

        meBt = findViewById(R.id.r_meBt);
        meBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MeActivity.class);
                startActivity(intent);
            }
        });
    }

    public void loadData(){

    }
}
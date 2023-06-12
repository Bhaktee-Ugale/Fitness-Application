package com.example.fitnessapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ReportActivity extends AppCompatActivity {

    private CardView homeBt, meBt;
    private ImageView r_meIv, r_homeIv, r_reportIv;

    private AdapterWorkoutHistory adapterWorkoutHistory;
    private RecyclerView recyclerView;

    private TextView workout_count, time_spent, calories;

    private DbHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        dbHelper = new DbHelper(this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        r_reportIv = findViewById(R.id.r_reportIv);
        r_reportIv.setColorFilter(Color.parseColor("#fa8072"));

        r_meIv = findViewById(R.id.r_meIv);
        r_meIv.setColorFilter(Color.parseColor("#c8c8c8"));

        r_homeIv = findViewById(R.id.r_homeIv);
        r_homeIv.setColorFilter(Color.parseColor("#c8c8c8"));

        workout_count = findViewById(R.id.workoutsTv);
        calories = findViewById(R.id.caloriesTv);
        time_spent = findViewById(R.id.mintuesTv);


        homeBt = findViewById(R.id.r_homeBt);
        homeBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                finish();
            }
        });

        meBt = findViewById(R.id.r_meBt);
        meBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                finish();
            }
        });

        recyclerView = findViewById(R.id.allExercises);

        loadData();
    }

    public void loadData(){
        adapterWorkoutHistory = new AdapterWorkoutHistory(this, dbHelper.getWorkoutHistory());
        recyclerView.setAdapter(adapterWorkoutHistory);
        int time = dbHelper.getMinutes()/60;
        Log.d(" >> ", ""+dbHelper.getMinutes());
        String work = ""+dbHelper.getWorkoutcount(), cal = ""+dbHelper.getCalories(), tim = ""+time;
        workout_count.setText(work);
        calories.setText(cal);
        time_spent.setText(tim);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();

    }

}
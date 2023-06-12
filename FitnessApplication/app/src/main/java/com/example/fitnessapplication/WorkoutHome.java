package com.example.fitnessapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class WorkoutHome extends AppCompatActivity {

    private DbHelper dbHelper;
    private ActionBar actionBar;
    private AdapterWorkout adapterWorkout;
    private String id;
    private RecyclerView workoutRv;
    private ImageView goalBg;
    private TextView textView;
    private CardView reportBt, meBt;
    private ImageView h_meIv, h_homeIv, h_reportIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_home);
        dbHelper = new DbHelper(getApplicationContext());

        actionBar = getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        id = getIntent().getStringExtra("ID");

        ModelGoals goal = dbHelper.getGoalDetails(id);
        actionBar.setTitle(goal.getName());

        goalBg = findViewById(R.id.goalIv);
        goalBg.setImageBitmap(goal.getImage());


        workoutRv = findViewById(R.id.workoutRyv);
        workoutRv.setHasFixedSize(true);

        textView = findViewById(R.id.workoutCount);
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


        loadData();
    }

    public void loadData(){
        adapterWorkout = new AdapterWorkout(this, dbHelper.getWorkouts(id));
        workoutRv.setAdapter(adapterWorkout);
        textView.setText(""+adapterWorkout.getItemCount());
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
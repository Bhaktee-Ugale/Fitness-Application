package com.example.fitnessapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class WorkoutHistory extends AppCompatActivity {

    private AdapterWorkoutHistory adapterWorkoutHistory;
    private RecyclerView recyclerView;

    private ActionBar actionBar;

    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_history);

        dbHelper = new DbHelper(getApplicationContext());

        actionBar = getSupportActionBar();
        actionBar.setTitle("Workout History");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.allExercises);

        loadData();

    }

    public void loadData(){
        adapterWorkoutHistory = new AdapterWorkoutHistory(this, dbHelper.getWorkoutHistory());
        recyclerView.setAdapter(adapterWorkoutHistory);
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
package com.example.fitnessapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ExerciseHome extends AppCompatActivity {

    private DbHelper dbHelper;
    private ActionBar actionBar;
    private AdapterExercise adapterExercise;
    private String id;
    private RecyclerView exerciseRv;
    private ImageView workoutBg;
    private TextView textView, description;
    private String workout_id;

    Button startBt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_home);

        dbHelper = new DbHelper(this);

        boolean isQuit = getIntent().getBooleanExtra("quit", false);
        if(isQuit){
            Log.d(" >> ", "hello");
            id = getIntent().getStringExtra("workout");
            loadData();
        }



        actionBar = getSupportActionBar();

        id = getIntent().getStringExtra("ID");

        ModelWorkout workout = dbHelper.getWorkoutDetails(id);
        workout_id = workout.getId();


        actionBar.setTitle(workout.getName());
        //Log.d(" >> ", workout.getName());

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        workoutBg = findViewById(R.id.workoutIv);
        workoutBg.setImageBitmap(workout.getImage());


        exerciseRv = findViewById(R.id.recyclerViewExercise);
        exerciseRv.setHasFixedSize(true);

        textView = findViewById(R.id.exerciseCount);
        description = findViewById(R.id.descriptionTv2);
        description.setText(workout.getInfo());

        startBt = findViewById(R.id.startworkoutBt);

        startBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RestActivity.class);
                intent.putExtra("WORKOUT_ID", workout_id);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });
    }

    public void loadData(){
        adapterExercise = new AdapterExercise(this, dbHelper.getExercises(id));
        exerciseRv.setAdapter(adapterExercise);
        textView.setText(""+adapterExercise.getItemCount());
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
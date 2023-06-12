package com.example.fitnessapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class RestActivity extends AppCompatActivity {
    private DbHelper dbHelper;

    private ImageView quiteBt;

    private String workout_id;

    private FrameLayout frameLayout;

    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning=true;

    FragmentManager fragmentSupportedManager = getSupportFragmentManager();
    android.app.FragmentManager fragmentManager = getFragmentManager();
    RestFragment rest;

    private long mTimeLeftInMillis;
    long startTime = 0;

    private String exercise_id;
    private String count;

    private boolean isDone = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        dbHelper = new DbHelper(getApplicationContext());

        Intent intent = getIntent();

        workout_id = intent.getStringExtra("WORKOUT_ID");

        frameLayout = findViewById(R.id.framelayout);
        quiteBt = findViewById(R.id.quitRBt);
        quiteBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ask();
            }
        });

        ArrayList<ModelExercise> exercises = dbHelper.getExercises(workout_id);

        /*replaceFragment(new CountdownFragment());
        startTimer();*/
            for (int i = exercises.size()-1; i >= 0; i-=1) {
                Log.d(" >> ", "Exercise name: " + exercises.get(i).getName());
                exercise_id = exercises.get(i).getId();
                Intent intent1 = new Intent(RestActivity.this, ExecuteExercise.class);
                intent1.putExtra("workout", workout_id);
                intent1.putExtra("exercise", exercise_id);
                intent1.putExtra("name", exercises.get(i).getName());
                intent1.putExtra("gif", exercises.get(i).getGif());
                intent1.putExtra("count", exercises.get(i).getCount());
                intent1.putExtra("cnt", i);
                intent1.putExtra("size", exercises.size());
                intent1.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent1);
                if(i==exercises.size()-1){
                    finish();
                }
            }

    }



    public void onBackPressed () {
        ask();
    }

    public void ask(){
        AlertDialog.Builder builder = new AlertDialog.Builder(RestActivity.this);
        builder.setMessage("WANNA GIVE UP? THINK ABOUT WHY YOU STARTED.");
        builder.setTitle("");
        builder.setCancelable(false);
        builder.setPositiveButton("QUIT", (DialogInterface.OnClickListener) (dialog, which) -> {
            finish();
        });
        builder.setNegativeButton("CONTINUE", (DialogInterface.OnClickListener) (dialog, which) -> {
            dialog.cancel();
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void replaceFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = fragmentSupportedManager.beginTransaction();
        fragmentTransaction.replace(R.id.framelayout, fragment);
        fragmentTransaction.commit();
    }

    public void startTimer(){

        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long l) {
                mTimeLeftInMillis = l;
                updateTimer();
            }

            @Override
            public void onFinish() {
                isDone = true;
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                Date today = new Date();
                dbHelper.insertTodayWorkout(exercise_id, workout_id, formatter.format(today), count);
                finish();
            }
        }.start();

        mTimerRunning = true;
    }

    public void pauseTimer(){
        mCountDownTimer.cancel();
        mTimerRunning = false;
        //mButtonStartPause.setImageResource(R.drawable.ic_baseline_play_arrow_24);
    }

    public void updateTimer(){
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d", seconds);

        //mTextViewCountDown.setText(timeLeftFormatted);
    }
}
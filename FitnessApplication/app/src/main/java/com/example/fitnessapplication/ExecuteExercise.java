package com.example.fitnessapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.Glide.*;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ExecuteExercise extends AppCompatActivity {
    public int counter;
    TextView t1;
    Button btn1;
    private TextView mTextViewCountDown, nameOfE;
    private ImageView imageView,mButtonStartPause, backBt, nextBt;
    private Button mButtonReset;

    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning=true;

    private long mTimeLeftInMillis;
    private DbHelper dbHelper;
    private String exercise_id, workout_id, count, gif, name;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_execute_exercise);
        dbHelper = new DbHelper(this);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        Intent intent = getIntent();
        workout_id = intent.getStringExtra("workout");
        exercise_id = intent.getStringExtra("exercise");
        count = intent.getStringExtra("count");
        gif = intent.getStringExtra("gif");
        name = intent.getStringExtra("name");
        nextBt = findViewById(R.id.nextExercise);

        mTimeLeftInMillis = Long.valueOf(count)*1000;

        nameOfE = findViewById(R.id.nameExercise);
        nameOfE.setText(name);
        mTextViewCountDown = findViewById(R.id.counter);
        mButtonStartPause = findViewById(R.id.playpause);
        imageView = (findViewById(R.id.gif));
        backBt = findViewById(R.id.quitBt);
        backBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ask();
            }
        });
        //btn1 = findViewById(R.id.btn1);

        int cnt = intent.getIntExtra("cnt", -1);
        int size = intent.getIntExtra("size", -1);

        if(cnt==size-1){
            nextBt.setImageResource(R.drawable.ic_baseline_check_circle_24);
        }
        nextBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                Date today = new Date();
                dbHelper.insertTodayWorkout(exercise_id, workout_id, formatter.format(today), count);
                finish();
            }
        });


        mButtonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTimerRunning) {
                    startTimer();
                } else {
                    pauseTimer();
                }
            }
        });




        Glide.with(this).load(gif).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                //AlertDialog.Builder builder = new AlertDialog.Builder();
                Toast.makeText(getApplicationContext(), "Unstable network detected...", Toast.LENGTH_SHORT);
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                startTimer();
                return false;
            }
        }).into(imageView);
        updateCountDownText();
    }

    private void startTimer() {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                Date today = new Date();
                dbHelper.insertTodayWorkout(exercise_id, workout_id, formatter.format(today), count);
                finish();

            }
        }.start();

        mTimerRunning = false;
        mButtonStartPause.setImageResource(R.drawable.ic_baseline_pause_24);
    }

    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = true;
        mButtonStartPause.setImageResource(R.drawable.ic_baseline_play_arrow_24);
    }
    private void updateCountDownText() {
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d", seconds);

        mTextViewCountDown.setText(timeLeftFormatted);
    }

    public void onBackPressed () {
        ask();
    }

    public void ask(){
        AlertDialog.Builder builder = new AlertDialog.Builder(ExecuteExercise.this);
        builder.setMessage("WANNA GIVE UP? THINK ABOUT WHY YOU STARTED.");
        builder.setTitle("");
        builder.setCancelable(false);
        builder.setPositiveButton("QUIT", (DialogInterface.OnClickListener) (dialog, which) -> {
            pauseTimer();
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date today = new Date();
            String sec = String.valueOf(Integer.parseInt(count) - Integer.parseInt(mTextViewCountDown.getText().toString()));
            dbHelper.insertTodayWorkout(exercise_id, workout_id, formatter.format(today), sec);
            Intent returnIntent = new Intent(ExecuteExercise.this, ExerciseHome.class);
            returnIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            returnIntent.putExtra("quite", 1);
            returnIntent.putExtra("workout", workout_id);
            //returnIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(returnIntent);
            finish();
        });
        builder.setNegativeButton("CONTINUE", (DialogInterface.OnClickListener) (dialog, which) -> {
            dialog.cancel();
            startTimer();
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}

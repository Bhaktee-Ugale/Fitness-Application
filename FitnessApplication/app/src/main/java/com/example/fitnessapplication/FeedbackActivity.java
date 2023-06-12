package com.example.fitnessapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class FeedbackActivity extends AppCompatActivity {

    private ActionBar actionBar;
    private EditText feedback;
    private Button sendBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        actionBar = getSupportActionBar();

        actionBar.setTitle("Feedback");
        //Log.d(" >> ", workout.getName());

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        feedback = findViewById(R.id.feedbackEdt);
        sendBt = findViewById(R.id.feedbackSendBt);

        sendBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(FeedbackActivity.this);
                builder.setTitle("Feedback");
                builder.setMessage("Thank you for your valuable feedback! Do you wish to proceed?");
                builder.setCancelable(false);
                builder.setPositiveButton("YES", (DialogInterface.OnClickListener) (dialog, which) -> {
                    finish();
                });
                builder.setNegativeButton("NO", (DialogInterface.OnClickListener) (dialog, which) -> {
                    dialog.cancel();
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
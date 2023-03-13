package com.example.fitnessapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

public class ProfileActivity extends AppCompatActivity {

    ActionBar actionBar;
    private Button editBt, editHBt;

    private String id;
    private boolean isKgcm=true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Intent intent = getIntent();

        actionBar = getSupportActionBar();
        actionBar.setTitle("My Profile");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        editBt = findViewById(R.id.editBt);
        editBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EditPersonalDetails.class);
                intent.putExtra("ID", id);
                startActivity(intent);
            }
        });

        editHBt = findViewById(R.id.editHBt);
        editHBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EditHealthDetails.class);
                intent.putExtra("ID", id);
                startActivity(intent);
            }
        });

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    public void onRadioButtonClicked(View view){
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()){
            case R.id.kgcm:
                if(checked){
                    isKgcm = true;
                }
                break;

            case R.id.ibft:
                if(checked){
                    isKgcm = false;
                }
                break;
        }
    }

}
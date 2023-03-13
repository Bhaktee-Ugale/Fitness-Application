package com.example.fitnessapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.number.Precision;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class EditHealthDetails extends AppCompatActivity {

    private RadioGroup rg;
    private RadioButton rb;
    ActionBar actionBar;

    private EditText heightEt, weightEt;
    private Button saveHbt;
    private String height="", weight="", date, id;

    private boolean isKgcm = true;
    DecimalFormat df_obj = new DecimalFormat("#.#");

    private DbHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_health_details);
        dbHelper = new DbHelper(this);

        Intent intent = getIntent();


        actionBar = getSupportActionBar();
        actionBar.setTitle("Edit Health Data");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        heightEt = findViewById(R.id.heightEt);
        weightEt = findViewById(R.id.weightEt);
        saveHbt = findViewById(R.id.saveHBt);

        ArrayList<ModelHealth> modelHealth = dbHelper.getHealthDetails();
        if(modelHealth.size()>0){
            height = modelHealth.get(modelHealth.size()-1).height;
            weight = modelHealth.get(modelHealth.size()-1).weight;
        }
        heightEt.setText(height);
        weightEt.setText(weight);

        saveHbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                height = heightEt.getText().toString();
                weight = weightEt.getText().toString();
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                Date today = new Date();
                date = formatter.format(today);

                if(!isKgcm){
                    float w = Float.valueOf(weight)* (float)0.45;
                    weight = df_obj.format(w);
                    float h = Float.valueOf(height)* (float)30.48;
                    height = df_obj.format(h);
                }

                dbHelper.insertHealthDetails(height, weight, date);

                Toast.makeText(getApplicationContext(), "Saved Successfully...", Toast.LENGTH_SHORT).show();
                finish();
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
package com.example.fitnessapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class ProfileActivity extends AppCompatActivity {

    private ActionBar actionBar;
    private Button editHBt;
    private boolean isKgcm=true;
    private EditText height, weight;
    private DbHelper dbHelper;
    private TextView unitH, unitW;

    private RadioGroup rg;

    private DecimalFormat df_obj = new DecimalFormat("#.#");




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        dbHelper = new DbHelper(this);

        actionBar = getSupportActionBar();
        actionBar.setTitle("My Profile");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        height = findViewById(R.id.heightEt);
        weight = findViewById(R.id.weightEt);

        unitH = findViewById(R.id.heightUnitTv);
        unitW = findViewById(R.id.weightUnitTv);

        rg = findViewById(R.id.groupRadio);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                float he = Float.valueOf(height.getText().toString()), we = Float.valueOf(weight.getText().toString());
                switch (i){
                    case R.id.kgcm:
                        if(he!=0 && we!=0){
                            float h = he * (float)30.48;
                            height.setText(df_obj.format(h));

                            float w = we * (float)0.45;
                            weight.setText(df_obj.format(w));

                            unitH.setText("kg");
                            unitW.setText("cm");
                        }
                        break;

                    case R.id.ibft:
                        if(he!=0 && we!=0) {
                            float h = he * (float) 0.03;
                            height.setText(df_obj.format(h));

                            float w = we * (float) 2.2;
                            weight.setText(df_obj.format(w));
                        }

                            unitW.setText("lb");
                            unitH.setText("ft");
                        break;
                }
            }
        });

        height.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                height.setText("");
            }
        });
        weight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                weight.setText("");
            }
        });


        editHBt = findViewById(R.id.editHBt);
        editHBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                Date today = new Date();
                if(dbHelper.checkWhetherDateIsPresent(formatter.format(today))){
                    dbHelper.updateHealthDetails(formatter.format(today), height.getText().toString(), weight.getText().toString());
                }
                else dbHelper.insertHealthDetails(height.getText().toString(), weight.getText().toString(), formatter.format(today));
            }
        });
        loadData();
    }

    /*
    * float h = Float.valueOf(modelHealth.getHeight())* (float)30.48;
                height.setText(df_obj.format(h)+" ft");
                * float w = Float.valueOf(modelHealth.getWeight())* (float)0.45;
                weight.setText(df_obj.format(w)+" Ib");*/

    public void loadData(){
        ModelHealth modelHealth = dbHelper.getRecentHealthDetails();

        if(modelHealth.getHeight().length()!=0){
            height.setText(modelHealth.getHeight());
            unitH.setText("cm");
        }
        Log.d(" >> ", "Height: "+modelHealth.getHeight());

        if(modelHealth.getWeight().length()!=0){
            weight.setText(modelHealth.getWeight());
            unitW.setText("kg");
        }



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
                if(checked && !height.getText().equals("Not Available") && !weight.getText().equals("Not Available")){
                    float h = Float.valueOf(height.getText().toString()) * (float)30.48;
                    height.setText(df_obj.format(h));

                    float w = Float.valueOf(weight.getText().toString()) * (float)0.45;
                    weight.setText(df_obj.format(w));

                    unitH.setText("kg");
                    unitW.setText("cm");
                }
                break;

            case R.id.ibft:
                if(checked){
                    float h = Float.valueOf(height.getText().toString()) * (float)0.03;
                    height.setText(df_obj.format(h));

                    float w = Float.valueOf(weight.getText().toString()) * (float)2.2;
                    weight.setText(df_obj.format(w));

                    unitW.setText("lb");
                    unitH.setText("ft");
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }
}
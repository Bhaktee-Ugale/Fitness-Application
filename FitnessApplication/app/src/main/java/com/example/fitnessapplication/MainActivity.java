package com.example.fitnessapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private DbHelper dbHelper;
    private static int timeout=3000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        dbHelper = new DbHelper(this);
        try{
            dbHelper.createDataBase();
        }
        catch (IOException e){}
        TextView tv = (TextView) findViewById(R.id.tw1);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(MainActivity.this, HomeActivity.class));
                finish();
            }
        }, timeout);

        Animation a = AnimationUtils.loadAnimation(this, R.anim.scale);
        a.reset();
        tv.clearAnimation();
        tv.startAnimation(a);


    }
}
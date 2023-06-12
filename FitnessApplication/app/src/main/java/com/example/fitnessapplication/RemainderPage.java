package com.example.fitnessapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class RemainderPage extends AppCompatActivity {


    FloatingActionButton mCreateRem;
    RecyclerView mRecyclerview;
    AdapterRemainder adapter;
    ActionBar actionBar;
    private DbHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remainder_page);
        dbHelper = new DbHelper(this);
        actionBar = this.getSupportActionBar();
        actionBar.setTitle("REMAINDER");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        mRecyclerview = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mCreateRem = (FloatingActionButton) findViewById(R.id.create_reminder);
        mCreateRem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RemainderActivity.class);
                startActivity(intent);
            }
        });

        loadRemainders();

    }

    public void loadRemainders(){
        adapter = new AdapterRemainder(dbHelper.readAllReminders(), this);
        mRecyclerview.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadRemainders();
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
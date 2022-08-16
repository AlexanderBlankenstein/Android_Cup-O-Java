package com.example.cup_o_java;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class TopLevelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_level);
    }
}
package com.example.fridgeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //change in onCreate by Ivy


    }

    @Override
    public void onClick(View view) {
        //IVY's comment3

        //this is a test to see if we have any problems in the main activity
    }
}
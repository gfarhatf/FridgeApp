package com.example.fridgeapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class FridgeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fridge);
        Toast.makeText(this, "onCreate-fridge", Toast.LENGTH_SHORT).show();
    }
}

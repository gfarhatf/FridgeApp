package com.example.fridgeapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class AddIngredients extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredients);
        Toast.makeText(this, "onCreate-add", Toast.LENGTH_SHORT).show();
    }
}

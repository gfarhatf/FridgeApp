package com.example.fridgeapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class EditIngredients extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_ingredients);
        Toast.makeText(this, "onCreate-edit", Toast.LENGTH_SHORT).show();
    }
}
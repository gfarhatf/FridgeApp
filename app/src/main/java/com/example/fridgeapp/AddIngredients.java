package com.example.fridgeapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddIngredients extends Activity implements View.OnClickListener {

    //Text views
    private EditText ingredientName, ingredientType, ingredientQuantity;

    //buttons
    private Button addButton;

    public MyDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredients);
        Toast.makeText(this, "onCreate-add", Toast.LENGTH_SHORT).show();

        ingredientName = (EditText) findViewById(R.id.ingrNameText);
        ingredientQuantity = (EditText) findViewById(R.id.ingrQuantityText);
        ingredientType = (EditText) findViewById(R.id.ingrTypeText);

        addButton = (Button) findViewById(R.id.addButton);
        addButton.setOnClickListener(this);

        db = new MyDatabase(this);

    }

    @Override
    public void onClick(View view) {

        String ingredientNameString = ingredientName.getText().toString();
        String ingredientQuantityString = ingredientQuantity.getText().toString();
        String ingredientTypeString = ingredientType.getText().toString();
        
        long id = db.insertIngredient(ingredientNameString, ingredientQuantityString, ingredientTypeString);

        if (id < 0) {
            Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
        }

        Intent intent = new Intent(this, FridgeActivity.class);
        startActivity(intent);
    }
}

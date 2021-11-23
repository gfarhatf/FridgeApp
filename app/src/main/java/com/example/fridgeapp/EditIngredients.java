package com.example.fridgeapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditIngredients extends Activity implements View.OnClickListener {
    EditText ingredientNameInput, ingredientTypeInput, ingredientQuantityInput;
    Button updateBtn;

    String nameStr, typeStr, quantityStr;

    MyHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_ingredients);
        Toast.makeText(this, "onCreate-edit", Toast.LENGTH_SHORT).show();

        ingredientNameInput = (EditText) findViewById(R.id.ingrNameTextEdit);
        ingredientTypeInput = (EditText) findViewById(R.id.ingrTypeTextEdit);
        ingredientQuantityInput = (EditText) findViewById(R.id.ingrQuantityTextEdit);

        getDataFromInput();

        updateBtn = (Button) findViewById(R.id.updateButton);
        updateBtn.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        dbHelper = new MyHelper(EditIngredients.this);

        nameStr = ingredientNameInput.getText().toString().trim();
        typeStr = ingredientTypeInput.getText().toString().trim();
        quantityStr = ingredientQuantityInput.getText().toString().trim();

        dbHelper.updateRow(Constants.INGREDIENT_UID, nameStr, typeStr, quantityStr);
        Log.d("fridge:", "IM IN CHANGE  " + nameStr);
    }

    private void getDataFromInput () {
        if (getIntent().hasExtra("name") && getIntent().hasExtra("type") && getIntent().hasExtra("quantity")){

            //get data from intent
            nameStr = getIntent().getStringExtra("name");
            typeStr = getIntent().getStringExtra("type");
            quantityStr = getIntent().getStringExtra("quantity");

            //set intent data
            ingredientNameInput.setText(nameStr);
            ingredientTypeInput.setText(typeStr);
            ingredientQuantityInput.setText(quantityStr);

        } else {
            Toast.makeText(this, "There is no data", Toast.LENGTH_SHORT).show();
        }
    }
}

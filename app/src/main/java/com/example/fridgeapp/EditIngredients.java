package com.example.fridgeapp;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;

public class EditIngredients extends Activity implements View.OnClickListener {
    //SOURCE: https://stackoverflow.com/questions/9107900/how-to-upload-image-from-gallery-in-android
    //for reuploading an image
    public static final int GET_FROM_GALLERY = 3;
    ImageView image;

    EditText ingredientNameInput, ingredientTypeInput, ingredientQuantityInput;

    Button updateBtn, deleteBtn, getGoBackBtn;

    String nameStr, typeStr, quantityStr, idStr;

    MyHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_ingredients);
//        Toast.makeText(this, "onCreate-edit", Toast.LENGTH_SHORT).show();

        ingredientNameInput = (EditText) findViewById(R.id.ingrNameTextEdit);
        ingredientTypeInput = (EditText) findViewById(R.id.ingrTypeTextEdit);
        ingredientQuantityInput = (EditText) findViewById(R.id.ingrQuantityTextEdit);

        image = (ImageView) findViewById(R.id.imageView);

        getDataFromInput();



        updateBtn = (Button) findViewById(R.id.updateButton);
        updateBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == updateBtn.getId()) {
            dbHelper = new MyHelper(EditIngredients.this);

            nameStr = ingredientNameInput.getText().toString().trim();
            typeStr = ingredientTypeInput.getText().toString().trim();
            quantityStr = ingredientQuantityInput.getText().toString().trim();

//            dbHelper.updateRow(Constants.INGREDIENT_UID, nameStr, typeStr, quantityStr);
            boolean updateSuccess = dbHelper.updateRow(idStr, nameStr, typeStr, quantityStr);

            // go back to fridge activity if update successful
            if (updateSuccess) {
                Intent i = new Intent(this, FridgeActivity.class);
                startActivity(i);
            }

        }

//        else if (view.getId() == goBackBtn.getId()){
//            Intent i = new Intent(this, FridgeActivity.class);
//            startActivity(i);
//
//        }
    }

    public void updateIngredient(View view) {
        dbHelper = new MyHelper(EditIngredients.this);

        nameStr = ingredientNameInput.getText().toString().trim();
        typeStr = ingredientTypeInput.getText().toString().trim();
        quantityStr = ingredientQuantityInput.getText().toString().trim();

        dbHelper.updateRow(idStr, nameStr, typeStr, quantityStr);
    }

    public void deleteRowListener(View view){
        Log.d("fridge:", "IM HERE DELETE ");
        dbHelper = new MyHelper(EditIngredients.this);
        dbHelper.deleteRow(idStr);
        Log.d("fridge:", "INGREDIENT ID : " + Constants.INGREDIENT_UID);

        boolean updateSuccess = dbHelper.updateRow(Constants.INGREDIENT_UID, nameStr, typeStr, quantityStr);

        // go back to fridge activity if update successful
        if (updateSuccess) {
            Intent i = new Intent(this, FridgeActivity.class);
            startActivity(i);
        }
    }

    private void getDataFromInput () {
        if (getIntent().hasExtra("id") && getIntent().hasExtra("name") && getIntent().hasExtra("type") && getIntent().hasExtra("quantity")){

            //get data from intent
            idStr = getIntent().getStringExtra("id");
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

    public void changeImageTextView (View view) {
//        Toast.makeText(this, "changeImage", Toast.LENGTH_SHORT).show();

        //SOURCE: https://stackoverflow.com/questions/9107900/how-to-upload-image-from-gallery-in-android
        startActivityForResult(
                new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI
                ),
                GET_FROM_GALLERY
        );
    }

    //SOURCE: https://stackoverflow.com/questions/9107900/how-to-upload-image-from-gallery-in-android
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        //Detects request codes
        if(requestCode==GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                image.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    //BOTTOM NAV =============================

    public void goToFridgeActivity (View view) {
        Intent i = new Intent(this, FridgeActivity.class);
        startActivity(i);
    }

    public void goToRecipesActivity (View view) {
        Intent i = new Intent(this, RecipesActivity.class);
        startActivity(i);
    }

    public void goToProfileActivity (View view) {
        Intent i = new Intent(this, ProfileActivity.class);
        startActivity(i);
    }

    public void goToMapActivity (View view) {
        Intent i = new Intent(this, MapActivity.class);
        startActivity(i);
    }
}

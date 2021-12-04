package com.example.fridgeapp;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.Image;
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

public class AddIngredients extends Activity implements View.OnClickListener {
    //SOURCE: https://stackoverflow.com/questions/9107900/how-to-upload-image-from-gallery-in-android
    //for uploading image
    public static final int GET_FROM_GALLERY = 3;
    ImageView image;

    //Text views
    private EditText ingredientName, ingredientType, ingredientQuantity;

    //buttons
    private Button addButton;

    public MyDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredients);
//        Toast.makeText(this, "onCreate-add", Toast.LENGTH_SHORT).show();

        ingredientName = (EditText) findViewById(R.id.ingrNameText);
        ingredientQuantity = (EditText) findViewById(R.id.ingrQuantityText);
        ingredientType = (EditText) findViewById(R.id.ingrTypeText);

        image = (ImageView) findViewById(R.id.imageView);

        addButton = (Button) findViewById(R.id.addButton);
        addButton.setOnClickListener(this);

        db = new MyDatabase(this);

    }

    @Override
    public void onClick(View view) {
        if (view == addButton) {
//            Toast.makeText(this, "addButton", Toast.LENGTH_SHORT).show();

            String ingredientNameString = ingredientName.getText().toString();
            String ingredientQuantityString = ingredientQuantity.getText().toString();
            String ingredientTypeString = ingredientType.getText().toString();

            if (ingredientNameString.isEmpty() || ingredientQuantityString.isEmpty() || ingredientTypeString.isEmpty()) {
                Toast.makeText(this, "please fill in all fields", Toast.LENGTH_SHORT).show();
            } else {

                Cursor selectedName = db.getSelectedData(ingredientNameString);
                int n = selectedName.getCount(); //how many rows were returned in the result


                if (n == 0) { //if ingredient name doesn't exist in database

                    long id = db.insertIngredient(ingredientNameString, ingredientQuantityString, ingredientTypeString);

                    if (id < 0) {
                        Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
                    }

                    Intent intent = new Intent(this, FridgeActivity.class);
                    startActivity(intent);

                } else {
                    //check if ingredient is qty = 0 (update if so, otherwise it ALREADY EXISTS)
//                Cursor name = db.getHiddenData(ingredientNameString);
//                int n = name.getCount(); //how many rows were returned in the result
//                if ()
                    Toast.makeText(this, "ALREADY EXISTS", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void deleteRow (View view){

    }

    public void uploadImageTextView (View view) {
        Toast.makeText(this, "uploadImage", Toast.LENGTH_SHORT).show();

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

    // BOTTOM NAV ========================================

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

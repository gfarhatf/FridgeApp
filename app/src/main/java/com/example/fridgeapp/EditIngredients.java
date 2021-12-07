package com.example.fridgeapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class EditIngredients extends Activity implements View.OnClickListener {
    //SOURCE: https://stackoverflow.com/questions/9107900/how-to-upload-image-from-gallery-in-android
    //for reuploading an image
    public static final int GET_FROM_GALLERY = 3;
    ImageView image;


    EditText ingredientNameInput, ingredientTypeInput, ingredientQuantityInput;

    Button updateBtn;

    String nameStr, typeStr, quantityStr, idStr;

    byte[] imgByte;

    MyHelper dbHelper;

    MyDatabase db;

    byte[] bytes;

    private static final int MY_CAMERA_REQUEST_CODE = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_ingredients);

        ingredientNameInput = (EditText) findViewById(R.id.ingrNameTextEdit);
        ingredientTypeInput = (EditText) findViewById(R.id.ingrTypeTextEdit);
        ingredientQuantityInput = (EditText) findViewById(R.id.ingrQuantityTextEdit);

        image = (ImageView) findViewById(R.id.imageView);
        image.setOnClickListener(this);

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

            image.setDrawingCacheEnabled(true);
            image.buildDrawingCache();
            Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
            ByteArrayOutputStream arrStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, arrStream);
            byte[] imgByte = arrStream.toByteArray();


            boolean updateSuccess = dbHelper.updateRow(idStr, nameStr, typeStr, quantityStr, imgByte);

            // go back to fridge activity if update successful
            if (updateSuccess) {
                Intent i = new Intent(this, FridgeActivity.class);
                startActivity(i);
            }
        } else if (view == image){
            Log.d("D", "IM HERE: ");
            int image = 0;

            if(image == 0){
                if (!checkCameraPermission()){
                    requestCameraPermission();

                }
                //open camera
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i, MY_CAMERA_REQUEST_CODE);

            }
        }
    }

    //camera check and request
    // source: https://stackoverflow.com/questions/38552144/how-get-permission-for-camera-in-android-specifically-marshmallow
    private boolean checkCameraPermission() {
        boolean result_Camera;
        result_Camera = ContextCompat.checkSelfPermission(EditIngredients.this, Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        return result_Camera;
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(EditIngredients.this, new String[] {Manifest.permission.CAMERA}, 100);
    }

    public void deleteRowListener(View view){
        dbHelper = new MyHelper(EditIngredients.this);
        dbHelper.deleteRow(idStr);

        boolean updateSuccess = dbHelper.updateRow(Constants.INGREDIENT_UID, nameStr, typeStr, quantityStr, bytes);

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

            //ImageView --> Bitmap --> byte[]
            image.setDrawingCacheEnabled(true);
            image.buildDrawingCache();
            Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
            ByteArrayOutputStream arrStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, arrStream);
            bytes = arrStream.toByteArray();

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

        if (requestCode == 100){ //camera
            Bitmap captureImg = (Bitmap) data.getExtras().get("data");
            image.setImageBitmap(captureImg);
        }

        //Detects request codes
        if(requestCode==GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) { //gallery
            Uri selectedImage = data.getData();
            Bitmap imgFromGallery = null;
            try {
                imgFromGallery = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                ByteArrayOutputStream arrStream = new ByteArrayOutputStream();
                imgFromGallery.compress(Bitmap.CompressFormat.JPEG, 100, arrStream);
                image.setImageBitmap(imgFromGallery);

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

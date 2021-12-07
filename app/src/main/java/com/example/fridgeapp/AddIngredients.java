package com.example.fridgeapp;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.graphics.drawable.BitmapDrawable;
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

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class AddIngredients extends Activity implements View.OnClickListener {

    //SOURCE: https://stackoverflow.com/questions/9107900/how-to-upload-image-from-gallery-in-android
    //for uploading image
    public static final int GET_FROM_GALLERY = 3;
    ImageView image;

    private static final int MY_CAMERA_REQUEST_CODE = 100;


    //Text views
    private EditText ingredientName, ingredientType, ingredientQuantity;

    //buttons
    private Button addButton;

    public MyDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredients);

        ingredientName = (EditText) findViewById(R.id.ingrNameText);
        ingredientQuantity = (EditText) findViewById(R.id.ingrQuantityText);
        ingredientType = (EditText) findViewById(R.id.ingrTypeText);

        image = (ImageView) findViewById(R.id.imageView);

        addButton = (Button) findViewById(R.id.addButton);
        addButton.setOnClickListener(this);


        db = new MyDatabase(this);


        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

        });
    }

    //camera check and request
    // source: https://stackoverflow.com/questions/38552144/how-get-permission-for-camera-in-android-specifically-marshmallow
    private boolean checkCameraPermission() {
        boolean result_Camera;
        result_Camera = ContextCompat.checkSelfPermission(AddIngredients.this, Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        return result_Camera;
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(AddIngredients.this, new String[] {Manifest.permission.CAMERA}, 100);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 100) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (view == addButton) {
//            Toast.makeText(this, "addButton", Toast.LENGTH_SHORT).show();

            String ingredientNameString = ingredientName.getText().toString();
            String ingredientQuantityString = ingredientQuantity.getText().toString();
            String ingredientTypeString = ingredientType.getText().toString();

            //SOURCES: https://www.py4u.net/discuss/609560
            //https://stackoverflow.com/questions/7620401/how-to-convert-image-file-data-in-a-byte-array-to-a-bitmap
            //https://stackoverflow.com/questions/11790104/how-to-storebitmap-image-and-retrieve-image-from-sqlite-database-in-android

            //convert bitmap to byte array
            image.setDrawingCacheEnabled(true);
            image.buildDrawingCache();
            Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
            ByteArrayOutputStream arrStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, arrStream);

            byte[] bytes = arrStream.toByteArray();


            if (ingredientNameString.isEmpty() || ingredientQuantityString.isEmpty() || ingredientTypeString.isEmpty()) {
                Toast.makeText(this, "please fill in all fields", Toast.LENGTH_SHORT).show();
            } else {

                Cursor selectedName = db.getSelectedData(ingredientNameString);
                int n = selectedName.getCount(); //how many rows were returned in the result


                if (n == 0) { //if ingredient name doesn't exist in database

                    //adding ingredient to the database
                    long id = db.insertIngredient(ingredientNameString, ingredientQuantityString, ingredientTypeString, bytes);

                    if (id < 0) {
                        Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
                    }

                    Intent intent = new Intent(this, FridgeActivity.class);
                    startActivity(intent);

                } else {
                    //check if ingredient is qty = 0 (update if so, otherwise it ALREADY EXISTS)
                    Toast.makeText(this, "ALREADY EXISTS", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void uploadImageTextView (View view) {

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

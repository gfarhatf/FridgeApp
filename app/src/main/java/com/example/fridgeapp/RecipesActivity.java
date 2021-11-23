package com.example.fridgeapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RecipesActivity extends Activity implements View.OnClickListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);
        Toast.makeText(this, "onCreate-recipes", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
//        code reference:
//        https://stackoverflow.com/questions/5026349/how-to-open-a-website-when-a-button-is-clicked-in-android-application
        
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse("https://www.allrecipes.com/recipes/"));
        startActivity(intent);
    }

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

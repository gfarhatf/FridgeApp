package com.example.fridgeapp;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class RecipesActivity extends Activity implements View.OnClickListener{
    WebView myWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        // automatically opens allrecipes.com when recipes tab is clicked
        myWebView = (WebView)findViewById(R.id.recipesWebView);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.setWebViewClient(new WebViewClient());

        myWebView.loadUrl("https://www.allrecipes.com/recipes/");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && myWebView.canGoBack())
        {
            myWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void searchByFridgeIngredientsButton (View view) {
        // automatically opens allrecipes.com when recipes tab is clicked
        myWebView = (WebView)findViewById(R.id.recipesWebView);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.setWebViewClient(new WebViewClient());

        // find recipes based on users first ingredient
        MyDatabase db = new MyDatabase(this);
        MyHelper helper = new MyHelper(this);

        Cursor cursor = null;
        cursor = db.getData();

        if (cursor != null) {
            ArrayList<String> ingredientNames = new ArrayList<>();
            int index1 = cursor.getColumnIndex(Constants.INGREDIENT_NAME);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                // add all ingredient names into an arraylist
                ingredientNames.add(cursor.getString(index1));
                cursor.moveToNext();
            }

            //example search url (include ingredients): https://www.allrecipes.com/search/results/?IngIncl=chicken&IngIncl=potato
            //search by max 2 ingredients (get the first 2 ingredients)
            // OR search by 1 or toast an empty fridge message
            if (ingredientNames.size() > 1) {
                String includeIngredients = "IngIncl=" + ingredientNames.get(0) + "&IngIncl=" + ingredientNames.get(1);
                myWebView.loadUrl("https://www.allrecipes.com/search/results/?" + includeIngredients);
            } else if (ingredientNames.size() > 0) {
                String includeIngredients = "IngIncl=" + ingredientNames.get(0);
                myWebView.loadUrl("https://www.allrecipes.com/search/results/?" + includeIngredients);
            }  else { //load allrecipes homepage if no ingredients.
                myWebView.loadUrl("https://www.allrecipes.com/recipes/");
                Toast.makeText(this, "No ingredients in fridge", Toast.LENGTH_SHORT).show();
            }
        } else { //load allrecipes homepage if no ingredients.
        myWebView.loadUrl("https://www.allrecipes.com/recipes/");
        Toast.makeText(this, "No ingredients in fridge2", Toast.LENGTH_SHORT).show();
    }

    }

    @Override
    public void onClick(View view) {
    }

    //bottom navigation
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

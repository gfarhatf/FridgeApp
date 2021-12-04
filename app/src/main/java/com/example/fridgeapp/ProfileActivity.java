package com.example.fridgeapp;

import static com.example.fridgeapp.RegisterActivity.DEFAULT;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileActivity extends Activity implements View.OnClickListener {

    TextView username, password, email;
    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
//        Toast.makeText(this, "onCreate-profile", Toast.LENGTH_SHORT).show();

        username = (TextView) findViewById(R.id.username);
        password = (TextView) findViewById(R.id.password);
        email = (TextView) findViewById(R.id.email);

        logout = (Button) findViewById(R.id.logoutButton);

        //display username, password in *s and email that is stored in shared preferences
        SharedPreferences sharedPrefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String name = sharedPrefs.getString("username", DEFAULT);
        if (!name.equals(DEFAULT)){
            username.setText(name);
        }
        String pass = sharedPrefs.getString("password", DEFAULT);
        if (!pass.equals(DEFAULT)){
            int passLength = pass.length();
            String passwordInStars = "";
            for (int i = 0; i < passLength; i++) {
                passwordInStars += "*";
            }
            password.setText(passwordInStars);
        }

        String emailAddress = sharedPrefs.getString("email", DEFAULT);
        if (!emailAddress.equals(DEFAULT)) {
            email.setText(emailAddress);
        }

    }

    @Override
    public void onClick (View view) {
    }

    public void logoutButton (View view) {
        //logout button clicked
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
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

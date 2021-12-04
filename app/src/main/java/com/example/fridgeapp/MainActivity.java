package com.example.fridgeapp;

import static com.example.fridgeapp.RegisterActivity.DEFAULT;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//  LoginActivity
public class MainActivity extends Activity implements View.OnClickListener {

    private static final boolean DEFAULT_BOOL = false;
    //User input EditTexts
    private EditText usernameEditText, passwordEditText;

    //Text views
    private TextView registerTextView;

    //Buttons
    private Button loginButton;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Toast.makeText(this, "onCreate-main", Toast.LENGTH_SHORT).show();

        usernameEditText = (EditText) findViewById(R.id.editTextUsername);
        passwordEditText = (EditText) findViewById(R.id.editTextPassword);

        registerTextView = (TextView) findViewById(R.id.registerTextView);
        registerTextView.setOnClickListener(this);

        loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(this);

        //if rememberMe == true, go to fridge automatically
        SharedPreferences sharedPrefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        boolean rememberMe = sharedPrefs.getBoolean("rememberMe", DEFAULT_BOOL);
        if (rememberMe) {
            Intent i = new Intent(this, FridgeActivity.class);
            startActivity(i);
        }
    }

    @Override
    public void onClick(View view) {
        if (view == loginButton) {
            //gather user input data (store in Shared Preferences)
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            //check input from user (validate username, password)--------------

            //check if fields are filled in
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "please fill in all fields", Toast.LENGTH_SHORT).show();
            } else {
                //check if username and password matches current values stored in shared preferences
                SharedPreferences sharedPrefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
                String currUsername = sharedPrefs.getString("username", DEFAULT);
                String currPassword = sharedPrefs.getString("password", DEFAULT);

                if (currUsername.equals(DEFAULT) && currPassword.equals(DEFAULT)){ // no current user
                    //toast msg: please create an account
                    Toast.makeText(this, "Please create an account", Toast.LENGTH_SHORT).show();
                    //go to register activity
                    Intent intent = new Intent(this, RegisterActivity.class);
                    startActivity(intent);

                } else if (currUsername.equals(username) && currPassword.equals(password)) { // current user in shared preferences matches user input
                    // go to fridge activity
                    // remember login in Shared Preferences
//                    sharedPrefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPrefs.edit();
                    editor.putBoolean("rememberMe", true);
                    editor.commit();

                    Intent intent = new Intent(this, FridgeActivity.class);
                    startActivity(intent);
                } else { // if there is data in shared preferences but login is incorrect
                    // toast msg: invalid credentials please try again
                    Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                }
            }
            //--------------------------------------------------------------------


        }
        if (view == registerTextView) {
            //go to Register Activity
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        }
    }
}
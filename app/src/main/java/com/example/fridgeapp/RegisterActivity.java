package com.example.fridgeapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends Activity implements View.OnClickListener {

    //User input EditTexts
    private EditText usernameEditText, passwordEditText, emailEditText;

    //Text views
    private TextView loginTextView;

    //Buttons
    private Button registerButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toast.makeText(this, "onCreate-register", Toast.LENGTH_SHORT).show();

        usernameEditText = (EditText) findViewById(R.id.editTextUsername);
        passwordEditText = (EditText) findViewById(R.id.editTextPassword);
        emailEditText = (EditText) findViewById(R.id.editTextEmail);

        loginTextView = (TextView) findViewById(R.id.loginTextView);
        loginTextView.setOnClickListener(this);

        registerButton = (Button) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {

        if (view == registerButton) {
            //gather user input data (store in Shared Preferences)
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            String email = emailEditText.getText().toString();

            //check input from user (validate username, password, email)---------

            //check if fields are filled in
            if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
                Toast.makeText(this, "please fill in all fields", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, username + " " + password + " " + email, Toast.LENGTH_SHORT).show();
            }
            //--------------------------------------------------------------------

            //go to Login Activity
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

        if (view == loginTextView) {
            Toast.makeText(this, "login clicked", Toast.LENGTH_SHORT).show();
        }
    }

}
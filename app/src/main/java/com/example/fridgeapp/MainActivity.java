package com.example.fridgeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//  LoginActivity
public class MainActivity extends Activity implements View.OnClickListener {

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
        Toast.makeText(this, "onCreate-main", Toast.LENGTH_SHORT).show();

        usernameEditText = (EditText) findViewById(R.id.editTextUsername);
        passwordEditText = (EditText) findViewById(R.id.editTextPassword);

        registerTextView = (TextView) findViewById(R.id.registerTextView);
        registerTextView.setOnClickListener(this);

        loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(this);
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
                Toast.makeText(this, username + " " + password, Toast.LENGTH_SHORT).show();
            }
            //--------------------------------------------------------------------
        }
        if (view == registerTextView) {
            Toast.makeText(this, "register clicked", Toast.LENGTH_SHORT).show();
            //go to Register Activity
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        }
    }
}
package com.example.fridgeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//  LoginActivity
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

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
        Toast.makeText(this, "onCreate", Toast.LENGTH_SHORT).show();

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
            Toast.makeText(this, "login clicked", Toast.LENGTH_SHORT).show();
        }
        if (view == registerTextView) {
            Toast.makeText(this, "register clicked", Toast.LENGTH_SHORT).show();
        }
    }
}
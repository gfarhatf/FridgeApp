package com.example.fridgeapp;

import static com.example.fridgeapp.Constants.INGREDIENT_QUANTITY;
import static com.example.fridgeapp.Constants.INGREDIENT_TABLE_NAME;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends Activity implements View.OnClickListener {

    public static final String DEFAULT = "";
    public static final boolean DEFAULT_BOOL = false;

    //User input EditTexts
    private EditText usernameEditText, passwordEditText, emailEditText;

    //Text views
    private TextView loginTextView;

    //Buttons
    private Button registerButton;

    public MyDatabase db;
    MyHelper dbHelper;

    public static final int TEAL_700 = Color.argb(255, 1, 135, 134 );


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usernameEditText = (EditText) findViewById(R.id.editTextUsername);
        passwordEditText = (EditText) findViewById(R.id.editTextPassword);
        emailEditText = (EditText) findViewById(R.id.editTextEmail);

        loginTextView = (TextView) findViewById(R.id.loginTextView);
        loginTextView.setOnClickListener(this);

        registerButton = (Button) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(this);

        db = new MyDatabase(this);
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
//                Toast.makeText(this, username + " " + password + " " + email, Toast.LENGTH_SHORT).show();
                long id = db.insertData (username, password, email);
                if (id < 0) {
                    //failed to insert data
                    Toast.makeText(this, "registration failed", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(this, "registration success", Toast.LENGTH_SHORT).show();

                    // remember login in Shared Preferences
                    SharedPreferences sharedPrefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPrefs.edit();
                    editor.putBoolean("rememberMe", true);
                    editor.putString("textColor", String.valueOf(TEAL_700));
                    editor.commit();

                    //go to Fridge Activity
                    Intent intent = new Intent(this, FridgeActivity.class);
                    startActivity(intent);
                }

                // store new username in Shared Preferences
                SharedPreferences sharedPrefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.putString("username", username);
                editor.putString("password", password);
                editor.putString("email", email);
                Toast.makeText(this, "New username and password saved", Toast.LENGTH_LONG).show();
                editor.commit();


                // make all quantities = 0 in database
                db.emptyIngredientsTable();

                //go to Fridge Activity
                Intent intent = new Intent(this, FridgeActivity.class);
                startActivity(intent);

            }
            //--------------------------------------------------------------------

        }

        if (view == loginTextView) {
//            Toast.makeText(this, "login clicked", Toast.LENGTH_SHORT).show();
            //go to Login Activity
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}

package com.example.fridgeapp;

import static com.example.fridgeapp.RegisterActivity.DEFAULT;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ProfileActivity extends Activity implements View.OnClickListener, SensorEventListener {

    //record temperature with ambient temperature sensor
    SensorManager mySensorManager;
    Sensor myTemperatureSensor;

    TextView username, password, email;
    Button logout;

    float newTemp;

    TextView previousTemp, currentTemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
//        Toast.makeText(this, "onCreate-profile", Toast.LENGTH_SHORT).show();

        previousTemp = (TextView) findViewById(R.id.previousTempVal);
        currentTemp = (TextView) findViewById(R.id.currentTempVal);

        username = (TextView) findViewById(R.id.username);
        password = (TextView) findViewById(R.id.password);
        email = (TextView) findViewById(R.id.email);

        logout = (Button) findViewById(R.id.logoutButton);

        //get reference to sensor an attach a listener - (acquire sensors late - release early)
        mySensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        //get reference to the accelerometer sensor
        myTemperatureSensor = mySensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);

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

    @Override protected void onResume() {
//        Toast.makeText(this, "onResume", Toast.LENGTH_SHORT).show();
        super.onResume();
        //listener for the ambient temperature sensor
        mySensorManager.registerListener(this, myTemperatureSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        //unregister listener - release the sensor
        mySensorManager.unregisterListener(this);
        super.onPause();
    }

    @Override
    public void onClick (View view) {
    }

    public void recordTemperatureButton (View view) {
        //record temperature
//        Toast.makeText(this, "record temperature", Toast.LENGTH_SHORT).show();
        if (myTemperatureSensor == null) {
            Toast.makeText(this, "Sensor not available", Toast.LENGTH_SHORT).show();
        }
        else {
            String oldTemp = currentTemp.getText().toString();
//          String newTemp = "no data";
            previousTemp.setText(oldTemp);
            currentTemp.setText(Float.toString(newTemp));
        }
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

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        int type = sensorEvent.sensor.getType();
        if (type == Sensor.TYPE_AMBIENT_TEMPERATURE) {
            float[] temp_val = sensorEvent.values;
            newTemp = temp_val[0];
            Toast.makeText(this, "Temp = " + temp_val[0], Toast.LENGTH_SHORT).show();
        }    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
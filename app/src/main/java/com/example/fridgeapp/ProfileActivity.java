package com.example.fridgeapp;

import static com.example.fridgeapp.RegisterActivity.DEFAULT;
import static com.example.fridgeapp.RegisterActivity.DEFAULT_BOOL;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
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
import java.util.Random;

public class ProfileActivity extends Activity implements View.OnClickListener, SensorEventListener {

    SensorManager mySensorManager;
//    Sensor myTemperatureSensor;

    TextView username, password, email;
    Button logout;

//    float newTemp;

//    TextView previousTemp, currentTemp;


    //SOURCE: https://stackoverflow.com/questions/5271448/how-to-detect-shake-event-with-android
    //shake detection variables
    private static final float SHAKE_THRESHOLD = 2.25f; // m/S**2
    private static final int MIN_TIME_BETWEEN_SHAKES_MILLISECS = 1000;
    private long mLastShakeTime;
    Sensor myShakeSensor;

    public static final int TEAL_700 = Color.argb(255, 1, 135, 134 );


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
//        Toast.makeText(this, "onCreate-profile", Toast.LENGTH_SHORT).show();

//        previousTemp = (TextView) findViewById(R.id.previousTempVal);
//        currentTemp = (TextView) findViewById(R.id.currentTempVal);

        username = (TextView) findViewById(R.id.username);
        password = (TextView) findViewById(R.id.password);
        email = (TextView) findViewById(R.id.email);

        logout = (Button) findViewById(R.id.logoutButton);

        //get reference to sensor an attach a listener - (acquire sensors late - release early)
        mySensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        //get reference to the sensors
//        myTemperatureSensor = mySensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        myShakeSensor = mySensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

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
//        mySensorManager.registerListener(this, myTemperatureSensor, SensorManager.SENSOR_DELAY_NORMAL);
        mySensorManager.registerListener(this, myShakeSensor, SensorManager.SENSOR_DELAY_NORMAL);
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

//    public void recordTemperatureButton (View view) {
//        //record temperature
////        Toast.makeText(this, "record temperature", Toast.LENGTH_SHORT).show();
//        if (myTemperatureSensor == null) {
//            Toast.makeText(this, "Sensor not available", Toast.LENGTH_SHORT).show();
//        }
//        else {
//            Toast.makeText(this, myTemperatureSensor.getName(), Toast.LENGTH_SHORT).show();
//            String oldTemp = currentTemp.getText().toString();
////          String newTemp = "no data";
//            previousTemp.setText(oldTemp);
//            currentTemp.setText(Float.toString(newTemp));
//        }
//    }

    public void resetColorButton (View view) {
        //make colour back to teal_700 (the original color)
        SharedPreferences sharedPrefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString("textColor", String.valueOf(TEAL_700));
        editor.commit();

        Intent i = new Intent(this, FridgeActivity.class);
        startActivity(i);
    }

    public void logoutButton (View view) {
        //logout button clicked

        // un-remember login in Shared Preferences
        SharedPreferences sharedPrefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putBoolean("rememberMe", false);
        editor.commit();

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
//        if (type == Sensor.TYPE_AMBIENT_TEMPERATURE) {
//            float[] temp_val = sensorEvent.values;
//            newTemp = temp_val[0];
//            Toast.makeText(this, "Sensor Value = " + temp_val[0], Toast.LENGTH_SHORT).show();
//        }

        //SOURCE: https://stackoverflow.com/questions/5271448/how-to-detect-shake-event-with-android
        //detecting vibration
        if (type == Sensor.TYPE_ACCELEROMETER) {
            //check that the times inbetween the shake is long enough to count as a new "shake" motion
            long curTime = System.currentTimeMillis();
            if ((curTime - mLastShakeTime) > MIN_TIME_BETWEEN_SHAKES_MILLISECS) {

                //get sensor values x,y,z
                float x = sensorEvent.values[0];
                float y = sensorEvent.values[1];
                float z = sensorEvent.values[2];

                double acceleration = Math.sqrt(Math.pow(x, 2) +
                        Math.pow(y, 2) +
                        Math.pow(z, 2)) - SensorManager.GRAVITY_EARTH;
//                Toast.makeText(this, "Acceleration is " + acceleration + "m/s^2", Toast.LENGTH_SHORT).show();


                //shake threshold used to detect whether motion is large enough as a shake
                if (acceleration > SHAKE_THRESHOLD) {
                    mLastShakeTime = curTime;
                    Toast.makeText(this, "Shake Shake", Toast.LENGTH_SHORT).show();
                    int newColor = getRandomColor();
                    SharedPreferences sharedPrefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPrefs.edit();
                    editor.putString("textColor", String.valueOf(newColor));
                    editor.commit();


                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    //SOURCE: https://stackoverflow.com/questions/21049747/how-can-i-generate-a-random-number-in-a-certain-range/21049922
    private int getRandomColor () {
        int min = 0;
        int max = 255;
        int randomR = new Random().nextInt((max - min) + 1) + min;
        int randomG = new Random().nextInt((max - min) + 1) + min;
        int randomB = new Random().nextInt((max - min) + 1) + min;
        String color = String.valueOf(Color.argb(255, randomR, randomG, randomB));
//        Toast.makeText(this, color, Toast.LENGTH_SHORT).show();
        return Integer.parseInt(color);
    }
}
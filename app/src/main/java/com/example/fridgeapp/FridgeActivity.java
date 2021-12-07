package com.example.fridgeapp;

import static com.example.fridgeapp.RegisterActivity.DEFAULT;

import android.app.Activity;
import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Vibrator;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Random;

public class FridgeActivity extends Activity implements AdapterView.OnItemClickListener, View.OnClickListener, SensorEventListener {

    RecyclerView myRecycler;
    MyAdapter myAdapter; //recycler view
    MyDatabase db;
    MyHelper helper; //SQLite

    TextView usernameTextView;

    Vibrator v;

    SensorManager mySensorManager;

    //SOURCE: https://stackoverflow.com/questions/5271448/how-to-detect-shake-event-with-android
    //shake detection variables
    private static final float SHAKE_THRESHOLD = 2.25f; // m/S**2
    private static final int MIN_TIME_BETWEEN_SHAKES_MILLISECS = 1000;
    private long mLastShakeTime;
    Sensor myShakeSensor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fridge);

        usernameTextView = (TextView) findViewById(R.id.usernameTextView);

        myRecycler = (RecyclerView) findViewById(R.id.recyclerView);
        myRecycler.setLayoutManager(new LinearLayoutManager(this));

        //initialize database objects
        ArrayList<String> mArrayList = new ArrayList<String>();
        db = new MyDatabase(this);
        helper = new MyHelper(this);

        //private method that gets data from the db and displays it in recycler view
        displayIngredients();

        //display username that is stored in shared preferences
        SharedPreferences sharedPrefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String username = sharedPrefs.getString("username", DEFAULT);
        if (!username.equals(DEFAULT)){
            usernameTextView.setText("Welcome, " + username + "!");
        }

        // Sensor usage: vibrate for 3s and output a Toast message to the user that
        //  their fridge is empty
        if (myRecycler.getAdapter() .getItemCount()==0) {

            // Source: https://stackoverflow.com/questions/13950338/how-to-make-an-android-device-vibrate-with-different-frequency
            // Get instance of Vibrator from current Context
            v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

            if (v.hasVibrator()) {
                // Vibrate for 1000 ms = 1s
                v.vibrate(1000);
                Toast.makeText(this, "Empty fridge!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "No vibrate function but fridge is empty!", Toast.LENGTH_LONG).show();
            }
        }

        //get reference to sensor
        mySensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        //get reference to the accelerometer sensor
        myShakeSensor = mySensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    private void displayIngredients() {
        Cursor cursor = null;

        //only get the data of ingredients with quantities > 0
        cursor = db.getData();

        ArrayList<String> myIngredientList = new ArrayList<String>();
        if (cursor != null) {

            int index1 = cursor.getColumnIndex(Constants.INGREDIENT_NAME);
            int index2 = cursor.getColumnIndex(Constants.INGREDIENT_TYPE);
            int index3 = cursor.getColumnIndex(Constants.INGREDIENT_QUANTITY);

            // get user ingredients from database
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String ingredientId = cursor.getString(0);
                String ingredientName = cursor.getString(index1);
                String ingredientType = cursor.getString(index2);
                String ingredientQuantity = cursor.getString(index3);

                //adding the strings into another string that separates them by a comma
                String s = ingredientId + "," + ingredientName + "," + ingredientType + "," + ingredientQuantity;

                //add string 's' into the arraylist of strings
                myIngredientList.add(s);
                cursor.moveToNext();
            }
        }

        myAdapter = new MyAdapter(myIngredientList, this, FridgeActivity.this);
        myRecycler.setAdapter(myAdapter);
    }

    @Override protected void onResume() {
        super.onResume();
        //listener for the ambient temperature sensor
        mySensorManager.registerListener(this, myShakeSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        //unregister listener - release the sensor
        mySensorManager.unregisterListener(this);
        super.onPause();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){

            //relaunch the activity
            recreate();

        }
    }

    //Bottom nav
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
    public void onClick(View view) {
        Intent intent = new Intent(this, AddIngredients.class);
        startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        int type = sensorEvent.sensor.getType();

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

                //shake threshold used to detect whether motion is large enough as a shake
                if (acceleration > SHAKE_THRESHOLD) {
                    mLastShakeTime = curTime;
                    Toast.makeText(this, "Shake Shake", Toast.LENGTH_SHORT).show();
                    int newColor = getRandomColor();
                    SharedPreferences sharedPrefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPrefs.edit();
                    editor.putString("textColor", String.valueOf(newColor));
                    editor.commit();

                    displayIngredients();
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        //nothing to do here
    }

    //SOURCE: https://stackoverflow.com/questions/21049747/how-can-i-generate-a-random-number-in-a-certain-range/21049922
    //get a random color
    private int getRandomColor () {
        int min = 0;
        int max = 255;
        int randomR = new Random().nextInt((max - min) + 1) + min;
        int randomG = new Random().nextInt((max - min) + 1) + min;
        int randomB = new Random().nextInt((max - min) + 1) + min;
        String color = String.valueOf(Color.argb(255, randomR, randomG, randomB));
        return Integer.parseInt(color);
    }
}

package com.example.fridgeapp;

import static com.example.fridgeapp.RegisterActivity.DEFAULT;

import android.app.Activity;
import android.content.Intent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Vibrator;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class FridgeActivity extends Activity implements AdapterView.OnItemClickListener, View.OnClickListener, SensorEventListener {

    RecyclerView myRecycler;
    MyAdapter myAdapter; //recycler view
    MyDatabase db;
    MyHelper helper; //SQLite

    TextView usernameTextView;

    Vibrator v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fridge);
        Toast.makeText(this, "onCreate-fridge", Toast.LENGTH_SHORT).show();

        usernameTextView = (TextView) findViewById(R.id.usernameTextView);

        myRecycler = (RecyclerView) findViewById(R.id.recyclerView);
        myRecycler.setLayoutManager(new LinearLayoutManager(this));

        Cursor cursor = null;

        //initialize database objects
        ArrayList<String> mArrayList = new ArrayList<String>();
        db = new MyDatabase(this);
        helper = new MyHelper(this);

        //get all data from database
        // (currently only username, password, and email) --> nov 10

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
                String s = ingredientId + "," + ingredientName + "," + ingredientType + "," + ingredientQuantity;
                myIngredientList.add(s);
                cursor.moveToNext();
            }
        }

        // vibrate for 3 seconds ----testing
            // Sensor usage: vibrate for 3s and output a Toast message to the user that
            // their fridge is empty

//        Source: https://stackoverflow.com/questions/13950338/how-to-make-an-android-device-vibrate-with-different-frequency
        // Get instance of Vibrator from current Context
        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        if (v.hasVibrator()) {
            // Vibrate for 1000 ms = 1s
            v.vibrate(1000);
            Toast.makeText(this, "Vibrating...", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "No vibrate function but fridge is empty!", Toast.LENGTH_LONG).show();
        }


        // add temporary values for testing
//        myIngredientList.add("ingred1");

        myAdapter = new MyAdapter(myIngredientList, this, FridgeActivity.this);
        myRecycler.setAdapter(myAdapter);

        //display username that is stored in shared preferences
        SharedPreferences sharedPrefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String username = sharedPrefs.getString("username", DEFAULT);
        if (!username.equals(DEFAULT)){
            usernameTextView.setText("Welcome, " + username + "!");
        }

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){

            recreate();

        }
    }

    //For testing map activity --- IVY
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
        //check if sensor changed is vibration sensor
//        int type = sensorEvent.sensor.getType();
//        if (type == Sensor.TYPE_ACCELEROMETER) {
//            float[] vals = sensorEvent.values;
//        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        //nothing to do here
    }
}

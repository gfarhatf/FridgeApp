package com.example.fridgeapp;

import android.app.Activity;
import android.database.Cursor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FridgeActivity extends Activity implements AdapterView.OnItemClickListener, View.OnClickListener {

    RecyclerView myRecycler;
    MyAdapter myAdapter; //recycler view
    MyDatabase db;
    MyHelper helper; //SQLite

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fridge);
        Toast.makeText(this, "onCreate-fridge", Toast.LENGTH_SHORT).show();

        myRecycler = (RecyclerView) findViewById(R.id.recyclerView);
        myRecycler.setLayoutManager(new LinearLayoutManager(this));

        Cursor cursor = null;

        //initialize database objects
        ArrayList<String> mArrayList = new ArrayList<String>();
        db = new MyDatabase(this);
        helper = new MyHelper(this);

        //get all data from database
        // (currently only username, password, and email) --> nov 10
        cursor = db.getData();

        ArrayList<String> myIngredientList = new ArrayList<String>();
        if (cursor != null) {
            int index1 = cursor.getColumnIndex(Constants.USERNAME);
            int index2 = cursor.getColumnIndex(Constants.PASSWORD);
            int index3 = cursor.getColumnIndex(Constants.EMAIL);

            // get user ingredients from database
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String username = cursor.getString(index1);
                String password = cursor.getString(index2);
                String email = cursor.getString(index3);
                String s = username + "," + password + "," + email;
                myIngredientList.add(s);
                cursor.moveToNext();
            }
        }

        // add temporary values for testing
//        myIngredientList.add("ingred1");

        myAdapter = new MyAdapter(myIngredientList);
        myRecycler.setAdapter(myAdapter);

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}

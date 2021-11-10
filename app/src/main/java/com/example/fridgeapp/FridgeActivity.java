package com.example.fridgeapp;

import android.app.Activity;
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
    MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fridge);
        Toast.makeText(this, "onCreate-fridge", Toast.LENGTH_SHORT).show();

        myRecycler = (RecyclerView) findViewById(R.id.recyclerView);
        myRecycler.setLayoutManager(new LinearLayoutManager(this));

        // get user ingredients from database
        ArrayList<String> myIngredientList = new ArrayList<String>();
        // add temporary values for testing
        myIngredientList.add("ingred1");
        myIngredientList.add("ingred2");
        myIngredientList.add("ingred3");
        myIngredientList.add("ingred4");
        myIngredientList.add("ingred5");
        myIngredientList.add("ingred6");
        myIngredientList.add("ingred7");
        myIngredientList.add("ingred8");
        myIngredientList.add("ingred9");
        myIngredientList.add("ingred10");
        myIngredientList.add("ingred11");
        myIngredientList.add("ingred12");
        myIngredientList.add("ingred13");
        myIngredientList.add("ingred14");
        myIngredientList.add("ingred15");
        myIngredientList.add("ingred16");
        myIngredientList.add("ingred17");
        myIngredientList.add("ingred18");
        myIngredientList.add("ingred19");
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

package com.example.fridgeapp;

import static com.example.fridgeapp.Constants.INGREDIENT_QUANTITY;
import static com.example.fridgeapp.Constants.INGREDIENT_TABLE_NAME;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.media.Image;

public class MyDatabase {

    private SQLiteDatabase db;
    private Context context;
    private final MyHelper helper;

    public MyDatabase (Context c){
        context = c;
        helper = new MyHelper(context);
    }

    //insert data from the login and registration
    public long insertData (String username, String password, String email)
    {
        db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.USERNAME, username);
        contentValues.put(Constants.PASSWORD, password);
        contentValues.put(Constants.EMAIL, email);

        long id = db.insert(Constants.TABLE_NAME, null, contentValues);
        return id;
    }

    //insert data when adding an ingredient
    public long insertIngredient(String name, String quantity, String type, byte[] image)
    {
        db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(Constants.INGREDIENT_NAME, name);
        contentValues.put(Constants.INGREDIENT_TYPE, type);
        contentValues.put(Constants.INGREDIENT_QUANTITY, quantity);
        contentValues.put(Constants.INGREDIENT_IMAGE, image);

        long id = db.insert(Constants.INGREDIENT_TABLE_NAME, null, contentValues);
        return id;
    }

    //get data from the datbase
    public Cursor getData()
    {
        SQLiteDatabase db = helper.getWritableDatabase();

        String[] columns = {Constants.UID, Constants.INGREDIENT_NAME, Constants.INGREDIENT_TYPE, Constants.INGREDIENT_QUANTITY, Constants.INGREDIENT_IMAGE};

        //show all ingredients
        Cursor cursor = db.query(Constants.INGREDIENT_TABLE_NAME, columns, null, null, null, null, null);
        return cursor;
    }

    //Go through the database and select specific data
    public Cursor getSelectedData(String ingredientName)
    {
        //select ingredients from database that contain the same name
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] columns = {Constants.UID, Constants.INGREDIENT_NAME, Constants.INGREDIENT_TYPE, Constants.INGREDIENT_QUANTITY, Constants.INGREDIENT_IMAGE};

        //SQL query construction
        String selection = Constants.INGREDIENT_NAME + "='" +ingredientName+ "'";  //Constants.TYPE = 'type'
        Cursor cursor = db.query(Constants.INGREDIENT_TABLE_NAME, columns, selection, null, null, null, null);
        return cursor;

    }

    public Cursor getHiddenData(String ingredientName) {
        //select ingredients from database that contain the same name and qty is greater than 0
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] columns = {Constants.UID, Constants.INGREDIENT_NAME, Constants.INGREDIENT_TYPE, Constants.INGREDIENT_QUANTITY};

        //SQL query construction
        String selection = Constants.INGREDIENT_NAME + "='" +ingredientName+ "'";  //Constants.TYPE = 'type'
        Cursor cursor = db.query(Constants.INGREDIENT_TABLE_NAME, columns, selection, null, null, null, null);
        return cursor;
    }

    //clearing the data from the fridge when a new user is logged into the application
    public void emptyIngredientsTable() {
        db = helper.getWritableDatabase();
        db.delete(INGREDIENT_TABLE_NAME,null,null);
    }
}

package com.example.fridgeapp;

import static com.example.fridgeapp.Constants.INGREDIENT_QUANTITY;
import static com.example.fridgeapp.Constants.INGREDIENT_TABLE_NAME;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;

public class MyDatabase {

    private SQLiteDatabase db;
    private Context context;
    private final MyHelper helper;

    public MyDatabase (Context c){
        context = c;
        helper = new MyHelper(context);
    }

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

    public long insertIngredient(String name, String quantity, String type, byte[] img)
    {
        db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(Constants.INGREDIENT_NAME, name);
        contentValues.put(Constants.INGREDIENT_TYPE, type);
        contentValues.put(Constants.INGREDIENT_QUANTITY, quantity);
        contentValues.put(Constants.INGREDIENT_IMAGE, img);

        long id = db.insert(Constants.INGREDIENT_TABLE_NAME, null, contentValues);
        return id;
    }

    public Cursor getData()
    {
        SQLiteDatabase db = helper.getWritableDatabase();

        String[] columns = {Constants.UID, Constants.INGREDIENT_NAME, Constants.INGREDIENT_TYPE, Constants.INGREDIENT_QUANTITY, Constants.INGREDIENT_IMAGE};
        Cursor cursor = db.query(Constants.INGREDIENT_TABLE_NAME, columns, null, null, null, null, null); //show all ingredients
//        Cursor cursor = db.query(Constants.INGREDIENT_TABLE_NAME, columns, Constants.INGREDIENT_QUANTITY + ">?", new String[]{"0"}, null, null, null); //show only ingredients qty > 0
        return cursor;
    }


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

    public void updateAllIngredientsToQtyZero() {
        String emptyFridgeQuery= "UPDATE " + INGREDIENT_TABLE_NAME + " SET " +  INGREDIENT_QUANTITY + " = '0'";
        db = helper.getWritableDatabase();
        db.execSQL(emptyFridgeQuery);
    }

    public void emptyIngredientsTable() {
        String emptyTableQuery = "DELETE FROM " + INGREDIENT_TABLE_NAME;
        db = helper.getWritableDatabase();
        db.delete(INGREDIENT_TABLE_NAME,null,null);
//        db.execSQL(emptyTableQuery);

    }
}

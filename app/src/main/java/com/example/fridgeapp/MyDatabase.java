package com.example.fridgeapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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

    public long insertIngredient(String name, String quantity, String type)
    {
        db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(Constants.INGREDIENT_NAME, name);
        contentValues.put(Constants.INGREDIENT_TYPE, type);
        contentValues.put(Constants.INGREDIENT_QUANTITY, quantity);

        long id = db.insert(Constants.INGREDIENT_TABLE_NAME, null, contentValues);
        return id;
    }

    public Cursor getData()
    {
        SQLiteDatabase db = helper.getWritableDatabase();

        String[] columns = {Constants.UID, Constants.INGREDIENT_NAME, Constants.INGREDIENT_TYPE, Constants.INGREDIENT_QUANTITY};
        Cursor cursor = db.query(Constants.INGREDIENT_TABLE_NAME, columns, null, null, null, null, null);
        return cursor;
    }


    public Cursor getSelectedData(String ingredientName)
    {
        //select plants from database of type 'herb'
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] columns = {Constants.UID, Constants.INGREDIENT_NAME, Constants.INGREDIENT_TYPE, Constants.INGREDIENT_QUANTITY};

        //SQL query construction
        String selection = Constants.INGREDIENT_NAME + "='" +ingredientName+ "'";  //Constants.TYPE = 'type'
        Cursor cursor = db.query(Constants.INGREDIENT_TABLE_NAME, columns, selection, null, null, null, null);
        return cursor;
        
//        StringBuffer buffer = new StringBuffer();
//        while (cursor.moveToNext()) {
//
//            int index1 = cursor.getColumnIndex(Constants.NAME);
//            int index2 = cursor.getColumnIndex(Constants.TYPE);
//            int index3 = cursor.getColumnIndex(Constants.LOCATION);
//            int index4 = cursor.getColumnIndex(Constants.LATIN_NAME);
//            String plantName = cursor.getString(index1);
//            String plantType = cursor.getString(index2);
//            String plantLocation = cursor.getString(index3);
//            String plantLatinName = cursor.getString(index4);
//            buffer.append(plantName + " " + plantType + " " + plantLocation + " " + plantLatinName + "\n");
//        }
//        return buffer.toString();
    }
}

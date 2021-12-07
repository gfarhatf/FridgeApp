package com.example.fridgeapp;

import static com.example.fridgeapp.Constants.INGREDIENT_NAME;
import static com.example.fridgeapp.Constants.INGREDIENT_QUANTITY;
import static com.example.fridgeapp.Constants.INGREDIENT_TABLE_NAME;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class MyHelper extends SQLiteOpenHelper {
    private Context context;

    //create the table for the database
    private static final String INGREDIENT_TABLE =
            "CREATE TABLE "+
                    Constants.INGREDIENT_TABLE_NAME + " (" +
                    Constants.INGREDIENT_UID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    INGREDIENT_NAME + " TEXT, " +
                    Constants.INGREDIENT_TYPE + " TEXT, " +
                    Constants.INGREDIENT_QUANTITY + " TEXT, " +
                    Constants.INGREDIENT_IMAGE + " BLOB);";

    //create the string to drop the database if it already exists
    private static final String DROP_INGREDIENT_TABLE = "DROP TABLE IF EXISTS " + Constants.INGREDIENT_TABLE_NAME;

    public MyHelper(Context context){
        super (context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(INGREDIENT_TABLE);
            Toast.makeText(context, "onCreate() called", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(context, "exception onCreate() db", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL(DROP_INGREDIENT_TABLE);
            onCreate(db);
            Toast.makeText(context, "onUpgrade called", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(context, "exception onUpgrade() db", Toast.LENGTH_LONG).show();
        }
    }

    //update the values of the ingredient
    public boolean updateRow(String rowId, String ingredientName, String ingredientType, String ingredientQuantity, byte[] image) {

        // get reference to the SQLite database
        SQLiteDatabase db = this.getWritableDatabase();

        //store values into the database
        ContentValues contentValues = new ContentValues();

        contentValues.put(Constants.INGREDIENT_NAME, ingredientName);
        contentValues.put(Constants.INGREDIENT_TYPE, ingredientType);
        contentValues.put(Constants.INGREDIENT_QUANTITY, ingredientQuantity);
        contentValues.put(Constants.INGREDIENT_IMAGE, image);

        //update row
        long updateResults = db.update(Constants.INGREDIENT_TABLE_NAME, contentValues, "_id=?", new String[] {rowId});

        if (updateResults == -1){ //if theres no data
            Toast.makeText(context, "Failed Update", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            Toast.makeText(context, "Successfully Updated, qty:" + ingredientQuantity + ", type: " + ingredientType, Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    //delete the row from the database
    public void deleteRow(String rowId ) {
        SQLiteDatabase db = this.getWritableDatabase();

        //delete row
        long deleteResults = db.delete(Constants.INGREDIENT_TABLE_NAME, "_id=?", new String[] {rowId});

        if (deleteResults == -1){ //if theres no data
            Toast.makeText(context, "Failed to delete ", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully deleted", Toast.LENGTH_SHORT).show();
        }
    }

}

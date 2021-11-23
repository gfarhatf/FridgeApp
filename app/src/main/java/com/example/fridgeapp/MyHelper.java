package com.example.fridgeapp;

import static com.example.fridgeapp.Constants.INGREDIENT_NAME;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class MyHelper extends SQLiteOpenHelper {
    private Context context;

    private static final String CREATE_TABLE =
            "CREATE TABLE "+
                    Constants.TABLE_NAME + " (" +
                    Constants.UID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    Constants.USERNAME + " TEXT, " +
                    Constants.PASSWORD + " TEXT, " +
                    Constants.EMAIL + " TEXT);";

    private static final String INGREDIENT_TABLE =
            "CREATE TABLE "+
                    Constants.INGREDIENT_TABLE_NAME + " (" +
                    Constants.INGREDIENT_UID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    INGREDIENT_NAME + " TEXT, " +
                    Constants.INGREDIENT_TYPE + " TEXT, " +
                    Constants.INGREDIENT_QUANTITY + " TEXT, " +
                    Constants.INGREDIENT_IMAGE + " TEXT);";



    private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + Constants.TABLE_NAME;
    private static final String DROP_INGREDIENT_TABLE = "DROP TABLE IF EXISTS " + Constants.INGREDIENT_TABLE_NAME;

    public MyHelper(Context context){
        super (context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_TABLE);
            db.execSQL(INGREDIENT_TABLE);

            Toast.makeText(context, "onCreate() called", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(context, "exception onCreate() db", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL(DROP_TABLE);
            db.execSQL(DROP_INGREDIENT_TABLE);

            onCreate(db);
            Toast.makeText(context, "onUpgrade called", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(context, "exception onUpgrade() db", Toast.LENGTH_LONG).show();
        }
    }

    public void updateRow(String rowId, String ingredientName, String ingredientType, String ingredientQuantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(Constants.INGREDIENT_NAME, ingredientName);
        contentValues.put(Constants.INGREDIENT_TYPE, ingredientType);
        contentValues.put(Constants.INGREDIENT_QUANTITY, ingredientQuantity);

        long updateResults = db.update(Constants.INGREDIENT_TABLE_NAME, contentValues, "_id=?", new String[] {rowId});

        if (updateResults == -1){ //if theres no data
            Toast.makeText(context, "Failed Update", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully Updated", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteRow(String rowId ) {
        SQLiteDatabase db = this.getWritableDatabase();
        long deleteResults = db.delete(Constants.INGREDIENT_TABLE_NAME, "_id=?", new String[] {rowId});

        if (deleteResults == -1){ //if theres no data
            Toast.makeText(context, "Failed to delete ", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully deleted", Toast.LENGTH_SHORT).show();
        }
    }
}

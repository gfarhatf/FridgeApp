package com.example.fridgeapp;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
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
                    Constants.INGREDIENT_NAME + " TEXT, " +
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
}

package com.example.cup_o_java;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class CupOJavaDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "cupojava"; //the nam of the database
    private static final int DB_VERSION = 1; //the version of the database

    CupOJavaDatabaseHelper(Context context) {
        //Calls the constructor of the SQLiteOpenHelper superclass and passes in the db name and version
        super (context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE DRINK (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "NAME TEXT, "
                + "DESCRIPTION TEXT, "
                + "IMAGE_RESOURCE_ID INTEGER) ;");
        insertDrink(db, "Latte", "A couple of espresso shots with steamed milk", R.drawable.latte);
        insertDrink(db, "Cappuccino", "Espresso, hot milk, and a steamed milk foam", R.drawable.cappuccino);
        insertDrink(db, "Filter", "Highest quality beans roasted and brewed fresh", R.drawable.filter);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private static void insertDrink(SQLiteDatabase db, String name, String description, int resourceId) {
        ContentValues drinkValues = new ContentValues();
        drinkValues.put("NAME", name);
        drinkValues.put("DESCRIPTION", description);
        drinkValues.put("IMAGE_RESOURCE_ID", resourceId);
        db.insert("DRINK", null, drinkValues);
    }
}

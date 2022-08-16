package com.example.cup_o_java;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

class CupOJavaDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "cupojava"; //the name of the database
    private static final int DB_VERSION = 2; //the version of the database

    CupOJavaDatabaseHelper(Context context) {
        //Calls the constructor of the SQLiteOpenHelper superclass and passes in the db name and version
        super (context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        updateMyDatabase(db, 0, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateMyDatabase(db, oldVersion, newVersion);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion == 3) {
            //Dangerous way of downgrading since user will lose all data. But works here
            db.execSQL("DROP TABLE DRINK;");
            onCreate(db);
        }
    }

    private static void insertDrink(SQLiteDatabase db, String name, String description, int resourceId) {
        ContentValues drinkValues = new ContentValues();
        drinkValues.put("NAME", name);
        drinkValues.put("DESCRIPTION", description);
        drinkValues.put("IMAGE_RESOURCE_ID", resourceId);
        db.insert("DRINK", null, drinkValues);
    }

    private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 1) {
            db.execSQL("CREATE TABLE DRINK (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "NAME TEXT, "
                    + "DESCRIPTION TEXT, "
                    + "IMAGE_RESOURCE_ID INTEGER) ;");
            insertDrink(db, "Latte", "A couple of espresso shots with steamed milk", R.drawable.latte);
            insertDrink(db, "Cappuccino", "Espresso, hot milk, and a steamed milk foam", R.drawable.cappuccino);
            insertDrink(db, "Filter", "Highest quality beans roasted and brewed fresh", R.drawable.filter);
        }
        if (oldVersion < 2) {
            //Code to add extra DB Column
            db.execSQL("ALTER TABLE DRINK ADD COLUMN FAVOURITE NUMERIC;");
        }
    }
}

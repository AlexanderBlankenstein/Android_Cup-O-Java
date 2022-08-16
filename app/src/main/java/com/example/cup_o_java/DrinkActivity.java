package com.example.cup_o_java;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DrinkActivity extends Activity {

    public static final String EXTRA_DRINKID = "drinkId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);

        //Get the drink from the intent
        int drinkId = (Integer) getIntent().getExtras().get(EXTRA_DRINKID);

        //Create a Cursor
        SQLiteOpenHelper cupojavaDatabaseHelper = new CupOJavaDatabaseHelper(this);
        try {
            SQLiteDatabase db = cupojavaDatabaseHelper.getReadableDatabase();
            Cursor cursor = db.query("DRINK",
                    new String[] {"NAME", "DESCRIPTION", "IMAGE_RESOURCE_ID"},
                    "_Id = ?",
                    new String[] {Integer.toString(drinkId)},
                    null, null, null);

            //Move to the first record in the Cursor
            if (cursor.moveToFirst()) {
                //Get th drink details from the cursor
                String nameText = cursor.getString(0);
                String descriptionText = cursor.getString(1);
                int photoId = cursor.getInt(2);
                boolean isFavourite = (cursor.getInt(3) == 1);

                //Populate the drink name
                TextView name = findViewById(R.id.name);
                name.setText(nameText);

                //Populate the drink description
                TextView description = findViewById(R.id.description);
                description.setText(descriptionText);

                //Populate the drink image
                ImageView photo = findViewById(R.id.photo);
                photo.setImageResource(photoId);
                photo.setContentDescription(nameText);

                //Populate the favourite checkbox
                CheckBox favourite = findViewById(R.id.favourite);
                favourite.setChecked(isFavourite);
            }

            //Close everything off
            cursor.close();
            db.close();
        } catch (SQLException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    //Update the database when the checkbox is clicked
    public void onFavouriteClicked(View view) {
        int drinkId = (Integer) getIntent().getExtras().get(EXTRA_DRINKID);

        //Get the value of the checkbox
        CheckBox favourite = findViewById(R.id.favourite);
        ContentValues drinkValues = new ContentValues();
        drinkValues.put("FAVOURITE", favourite.isChecked());

        //Get a reference to the database and update the FAVOURITE column
        SQLiteOpenHelper cupojavaDatabaseHelper = new CupOJavaDatabaseHelper(this);
        try {
            SQLiteDatabase db = cupojavaDatabaseHelper.getWritableDatabase();
            db.update("DRINK",
                    drinkValues,
                    "_id = ?",
                    new String[] {Integer.toString(drinkId)});
            db.close();
        } catch (SQLException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
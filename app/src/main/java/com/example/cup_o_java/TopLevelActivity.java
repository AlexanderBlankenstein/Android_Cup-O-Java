package com.example.cup_o_java;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class TopLevelActivity extends Activity {

    private SQLiteDatabase db;
    private Cursor favouriteCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_level);
        setupOptionsListView();
        setupFavouriteListView();
    }

    private void setupOptionsListView() {
        //Create an OnItemClickListener
        AdapterView.OnItemClickListener itemClickListener = (listView, view, position, id) -> {
            if (position == 0) {
                Intent intent = new Intent(TopLevelActivity.this, DrinkCategoryActivity.class);
                startActivity(intent);
            }
        };

        //Add the listener to the list view
        ListView listView = findViewById(R.id.list_options);
        listView.setOnItemClickListener(itemClickListener);
    }

    //Create list_favourite list and have it respond to clicks
    private void setupFavouriteListView() {
        //Populate the list_favourites ListView from the cursor
        ListView listFavourites = findViewById(R.id.list_favourites);
        try {
            SQLiteOpenHelper cupojavaDatabaseHelper = new CupOJavaDatabaseHelper(this);
            db = cupojavaDatabaseHelper.getReadableDatabase();
            favouriteCursor = db.query("DRINK",
                    new String[] {"_id", "NAME"},
                    "FAVOURITE = 1",
                    null, null, null, null);
            CursorAdapter favoriteAdapter = new SimpleCursorAdapter(TopLevelActivity.this,
                    android.R.layout.simple_list_item_1,
                    favouriteCursor,
                    new String[] {"NAME"},
                    new int[] {android.R.id.text1}, 0);
            listFavourites.setAdapter(favoriteAdapter);
        } catch (SQLException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }

        //Navigate to DrinkActivity if a drink is clicked
        listFavourites.setOnItemClickListener((listView, v, position, id) -> {
            Intent intent = new Intent(TopLevelActivity.this, DrinkActivity.class);
            intent.putExtra(DrinkActivity.EXTRA_DRINKID, (int)id);
            startActivity(intent);
        });
    }

    //CLose the cursor and database
    @Override
    protected void onDestroy() {
        super.onDestroy();
        favouriteCursor.close();
        db.close();
    }
}
package com.example.cup_o_java;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DrinkCategoryActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink_category);
        ArrayAdapter<Drink> listAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                Drink.drinks
        );
        ListView listDrinks = findViewById(R.id.list_drinks);
        listDrinks.setAdapter(listAdapter);

        //Create the listener
        AdapterView.OnItemClickListener itemClickListener = (listDrink, itemView, position, id) -> {
            //Pass the drink the user selected on the DrinkActivity
            Intent intent = new Intent(DrinkCategoryActivity.this, DrinkActivity.class);
            intent.putExtra(DrinkActivity.EXTRA_DRINKID, (int) id);
            startActivity(intent);
        };

        //Assign the listener to the list view
        listDrinks.setOnItemClickListener(itemClickListener);
    }
}
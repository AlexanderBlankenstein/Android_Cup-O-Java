package com.example.cup_o_java;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class TopLevelActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_level);
        setupOptionsListView();
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
}
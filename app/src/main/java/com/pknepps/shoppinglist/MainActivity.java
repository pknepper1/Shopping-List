package com.pknepps.shoppinglist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import java.util.ArrayList;

import java.lang.reflect.Array;

public class MainActivity extends AppCompatActivity {

    /** The xml element helloText represented in java */
    TextView helloText;

    Button button;

    /** True if button has been clicked an odd number of times */
    boolean oddClick;

    ArrayList<String> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.helloButton);
        helloText = findViewById(R.id.helloText);
        helloText.setText((String) "Hello World!");
        oddClick = false;
        items = new ArrayList<>();
        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        ListView listView = (ListView) findViewById(R.id.lvItems);
        listView.setAdapter(itemsAdapter);
    }

    public void onHelloButtonClick(View view) {
        String onClick = "What's Up Bitches!";
        String onOtherClick = "Hello World!";
        oddClick = !oddClick;
        if (oddClick) {
            helloText.setText(onClick);
        } else {
            helloText.setText(onOtherClick);
        }
    }
}
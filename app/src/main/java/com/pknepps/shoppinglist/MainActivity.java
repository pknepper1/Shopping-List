package com.pknepps.shoppinglist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    /** The adapter that will display the items in from an array. */
    ItemsAdapter itemsAdapter;

    /** The array of items to display and edit in the recyclerView */
    ArrayList<Item> items;

    /** The total of the price all items in the list */
    double total;

    /**
     * Called when app is initialized.
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // loads activity_main.xml
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // loads the shopping list
        items = new ArrayList<>();
        items.add(new Item());
        // sets itemsAdapter to teh locally saved shopping list and attaches a recycler view to it
        itemsAdapter = new ItemsAdapter(items);
        RecyclerView rcView = findViewById(R.id.rcItems);
        rcView.setAdapter(itemsAdapter);
        rcView.setLayoutManager(new LinearLayoutManager(this));
        // sets total
        total = 0;
    }

    /**
     * Sums the values of all prices in the list and changes the displayed value to match.
     */
    public void recalculateTotal() {

    }
}
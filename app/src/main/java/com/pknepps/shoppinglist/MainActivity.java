package com.pknepps.shoppinglist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    final private double TAX = 0.07;

    /** The adapter that will display the items in from an array. */
    private ItemsAdapter itemsAdapter;

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
        ArrayList<Item> items = new ArrayList<>();
        items.add(new Item());
        // sets itemsAdapter to teh locally saved shopping list and attaches a recycler view to it
        itemsAdapter = new ItemsAdapter(items);
        RecyclerView rcView = findViewById(R.id.rcItems);
        rcView.setAdapter(itemsAdapter);
        rcView.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * Sums the values of all prices in the list and changes the displayed value to match.
     */
    public void recalculateTotal() {
        double total = 0;
        ArrayList<Item> items = itemsAdapter.getItems();
        for (Item item : items) {
            total += item.getPrice();
        }
        ((TextView) findViewById(R.id.total)).setText(String.format(Double.toString(total)));
        ((TextView) findViewById(R.id.tax)).setText(String.format(Double.toString(total * TAX)));
        ((TextView) findViewById(R.id.totalTax)).setText(String.format(Double.toString(total + (total * TAX))));
    }
}
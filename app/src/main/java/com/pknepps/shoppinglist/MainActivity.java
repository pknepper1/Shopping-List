package com.pknepps.shoppinglist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

/**
 * This is the main activity of the app, which is instantiated and ran automatically via the
 * Android Main Looper Thread.
 */
public class MainActivity extends AppCompatActivity {

    /** The adapter used to keep track of all item views */
    ItemsAdapter itemsAdapter;

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
        // sets itemsAdapter to the locally saved shopping list and attaches a recycler view to it
        itemsAdapter = new ItemsAdapter(this, items);
        RecyclerView rcView = findViewById(R.id.rcItems);
        rcView.setAdapter(itemsAdapter);
        rcView.setLayoutManager(new LinearLayoutManager(this));
    }
}
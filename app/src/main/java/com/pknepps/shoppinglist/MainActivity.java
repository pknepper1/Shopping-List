package com.pknepps.shoppinglist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import java.util.ArrayList;

import java.lang.reflect.Array;

public class MainActivity extends AppCompatActivity {

    /** Button which will add a new row to the array */
    Button addButton;

    Button removeButton;

    /** True if button has been clicked an odd number of times */
    boolean oddClick;

    /** The adapter that will display the items in from an array. */
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
        // loads buttons
        addButton = findViewById(R.id.addButton);
        removeButton = findViewById(R.id.removeButton);
        // sets itemsAdapter to an empty list and attaches a list view to it
        itemsAdapter = new ItemsAdapter(this);
        itemsAdapter.add(new Item());
        ListView listView = (ListView) findViewById(R.id.lvItems);
        listView.setAdapter(itemsAdapter);
    }

    /**
     * To run when the add button is clicked. Will add new Items to the items adapter.
     * @param view Auto filled when attached to activity_main.xml
     */
    public void onAddButtonClick(View view) {
        itemsAdapter.add(new Item());
    }

    /**
     * To run when the remove button is clicked. Will remove the last item from the adapter.
     * @param view Auto filled when attached to activity_main.xml
     */
    public void onRemoveButtonClick(View view) {
        itemsAdapter.pop();
    }
}
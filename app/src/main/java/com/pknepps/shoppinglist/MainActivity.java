package com.pknepps.shoppinglist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.*;

public class MainActivity extends AppCompatActivity {

    /** Button which will add a new row to the array */
    Button addButton;

    /** Button which will remove the last row to the array */
    Button removeButton;


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
        Item firstItem = new Item(this);
        firstItem.getName().addTextChangedListener(new NameWatcher(this, itemsAdapter, firstItem));
        itemsAdapter.add(firstItem);
        ListView listView = findViewById(R.id.lvItems);
        listView.setAdapter(itemsAdapter);
    }

    /**
     * To run when the add button is clicked. Will add new Items to the items adapter.
     * @param view Auto filled when attached to activity_main.xml
     */
    public void onAddButtonClick(View view) {
        Item newItem = new Item(this);
        newItem.getName().addTextChangedListener(new NameWatcher(this, itemsAdapter, newItem));
        itemsAdapter.add(new Item(this));
    }

    /**
     * To run when the remove button is clicked. Will remove the last item from the adapter.
     * @param view Auto filled when attached to activity_main.xml
     */
    public void onRemoveButtonClick(View view) {
        if (itemsAdapter.getCount() <= 1) {
            return;
        }
        itemsAdapter.remove(itemsAdapter.getItem(itemsAdapter.getCount() - 1));
    }
}
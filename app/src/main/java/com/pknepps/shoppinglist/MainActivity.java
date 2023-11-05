package com.pknepps.shoppinglist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.io.IOException;
import java.util.Arrays;

/**
 * This is the main activity of the app, which is instantiated and ran automatically via the
 * Android Main Looper Thread.
 * @author Preston Knepper
 */
public class MainActivity extends AppCompatActivity {

    /** Name of the file in which all items and preferences are saved */
    private final String FILENAME = "saved_list.txt";

    /** Keeps track of all items in the grocery list. */
    private ItemsList items;

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
        // loads the shopping list from file.
        try (ObjectInputStream savedList = new ObjectInputStream(new FileInputStream(
                getApplicationContext().getFilesDir().toString() + "/saved_list.ser"))){
            Object input = savedList.readObject();
            if (input instanceof ItemsList) {
                items = (ItemsList) input;
                System.out.println("Retrieved serialization.");
                if (items.isEmpty()) {
                    items.add(new Item());
                }
            }
        } catch (IOException | ClassNotFoundException ioe) {
            System.err.println("Issue writing serialized object:");
            ioe.printStackTrace();
        }
        if (items == null) {
            // If there is no saved list, make a new list.
            items = new ItemsList();
            items.add(new Item());
        }
        // sets itemsAdapter to the locally saved shopping list and attaches a recycler view to it
        ItemsAdapter itemsAdapter = new ItemsAdapter(this, items);
        RecyclerView rcView = findViewById(R.id.rcItems);
        rcView.setAdapter(itemsAdapter);
        rcView.setLayoutManager(new LinearLayoutManager(this));

        Button clearAllButton = findViewById(R.id.clearAllButton);
        clearAllButton.setOnClickListener(view -> itemsAdapter.removeAll());

        EditText editTax = findViewById(R.id.tax_option);
        editTax.addTextChangedListener(new TextWatcher() {

            // Unused
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            // Unused
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            // Sets the tax rate to the current value in this EditText.
            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equals(".")) {
                    editTax.setText("0.");
                    editTax.setSelection(editTax.getText().length());
                }
                itemsAdapter.setTax_rate(s.toString());
                itemsAdapter.recalculateTotal();
                SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();

                // Saves the tax rate.
                editor.putString("Tax", editTax.getText().toString());
                editor.apply();
            }
        });

        // Recovers saved tax value.
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        editTax.setText(sharedPref.getString("Tax", "0.07"));
    }
}
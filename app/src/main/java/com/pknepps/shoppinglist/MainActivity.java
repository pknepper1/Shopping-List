package com.pknepps.shoppinglist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
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
        try (ObjectInputStream savedList = new ObjectInputStream(new FileInputStream(getApplicationContext().getFilesDir().toString() + "/saved_list.ser"))){
            Object input = savedList.readObject();
            if (input instanceof ItemsList) {
                items = (ItemsList) input;
                System.out.println("Retrieved serialization.");
                if (items.isEmpty()) {
                    items.add(new Item());
                }
            }
            // sets itemsAdapter to the locally saved shopping list and attaches a recycler view to it
        } catch (IOException | ClassNotFoundException ioe) {
            System.err.println("Issue writing serialized object:");
            ioe.printStackTrace();
        }
        if (items == null) {
            items = new ItemsList();
            items.add(new Item());
        }
        ItemsAdapter itemsAdapter = new ItemsAdapter(this, items);
        RecyclerView rcView = findViewById(R.id.rcItems);
        rcView.setAdapter(itemsAdapter);
        rcView.setLayoutManager(new LinearLayoutManager(this));
    }
}
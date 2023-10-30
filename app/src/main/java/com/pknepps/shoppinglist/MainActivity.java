package com.pknepps.shoppinglist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.io.IOException;
import java.util.Arrays;

/**
 * This is the main activity of the app, which is instantiated and ran automatically via the
 * Android Main Looper Thread.
 * @author Preston Knepper
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Called when app is initialized.
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final String FILENAME = "saved_list";
        System.out.println(this.getFilesDir().toString() + "\n" + Arrays.toString(this.fileList()));
        // loads activity_main.xml
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // loads the shopping list from file
        /*
        FileInputStream fis = null;
        try {
            fis = this.openFileInput(FILENAME);
        } catch (FileNotFoundException fnfe) {
            System.out.println("File does not exist." + fnfe.getMessage());
        }
        InputStreamReader inputStreamReader = new InputStreamReader(fis, StandardCharsets.UTF_8);
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
            String line = reader.readLine();
            while (line != null) {
                stringBuilder.append(line).append('\n');
                line = reader.readLine();
            }
        } catch (IOException ioe) {
            System.out.println("Something went wrong when reading from file: " + ioe.getMessage());
        } finally {
            String contents = stringBuilder.toString();
        }
*/
        ArrayList<Item> items = new ArrayList<>();
        items.add(new Item());
        // sets itemsAdapter to the locally saved shopping list and attaches a recycler view to it
        ItemsAdapter itemsAdapter = new ItemsAdapter(this, items);
        RecyclerView rcView = findViewById(R.id.rcItems);
        rcView.setAdapter(itemsAdapter);
        rcView.setLayoutManager(new LinearLayoutManager(this));
    }
}
package com.pknepps.shoppinglist;

import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.content.Context;
import android.view.*;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class ItemsAdapter extends ArrayAdapter<Item> {
    public ItemsAdapter(Context context, ArrayList<Item> items) {
        super(context, 0, items);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        // Get the data item for this position
        Item item = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);
        }
        // Lookup view for data population
        TextView itemName = (TextView) convertView.findViewById(R.id.itemName);
        TextView itemValue = (TextView) convertView.findViewById(R.id.itemPrice);
        // Populate the data into the template view using the data object
        itemName.setText(item.getName());
        itemValue.setText(item.getPrice());

        return convertView;
    }
}

package com.pknepps.shoppinglist;

import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.content.Context;
import android.view.*;

import androidx.annotation.NonNull;

import java.util.ArrayList;

/**
 * This is an adapter that stores and displays Items in process. It is a customization of the ArrayAdapter class that works with this app.
 */
public class ItemsAdapter extends ArrayAdapter<Item> {
    /**
     * @param context the object to display in. (usually the current object)
     */
    public ItemsAdapter(Context context) {
        super(context, 0);
    }

    /**
     * Returns a usable view containing all Items in this array. This view is displayable from a
     * .xml file.
     * @param position The position of the item within the adapter's data set of the item whose view
     *        we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view
     *        is non-null and of an appropriate type before using. If it is not possible to convert
     *        this view to display the correct data, this method can create a new view.
     *        Heterogeneous lists can specify their number of view types, so that this View is
     *        always of the right type (see {@link #getViewTypeCount()} and
     *        {@link #getItemViewType(int)}).
     * @param parent The parent that this view will eventually be attached to
     * @return a usable view containing all Items in this array.
     */
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

package com.pknepps.shoppinglist;

import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Context;
import android.view.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

/**
 * This is an adapter that stores and displays Items in process. It is a customization of the ArrayAdapter class that works with this app.
 */
public class ItemsAdapter extends ArrayAdapter<Item> {

    ArrayList<Item> items;

    /**
     * @param context the object to display in. (usually the current object)
     */
    public ItemsAdapter(Context context) {
        super(context, 0);
        items = new ArrayList<>();
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
        Item item = items.get(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);
        }
        // Lookup view for data population
        EditText itemName = convertView.findViewById(R.id.itemName);
        EditText itemValue = convertView.findViewById(R.id.itemPrice);
        // Populate the data into the template view using the data object
        assert item != null;
        if (!item.getName().equals("")) {
            itemName.setText(item.getName());
        }
        if (item.getPrice() != 0) {
            itemValue.setText(String.valueOf(item.getPrice()));
        }
        return convertView;
    }

    @Override
    public void add(@Nullable Item object) {
        super.add(object);
        items.add(object);
    }

    public void pop() {
        if (getCount() <= 0) {
            return;
        }
        remove(getItem(getCount() - 1));
        items.remove(getCount());
    }
}

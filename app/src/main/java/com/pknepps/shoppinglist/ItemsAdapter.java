package com.pknepps.shoppinglist;

import android.app.Activity;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.content.Context;
import android.view.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * This is an adapter that stores and displays Items in process. It is a customization of the ArrayAdapter class that works with this app.
 */
public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {

    ArrayList<Item> items;
    ArrayList<TextWatcher> nameChanges;

    /**
     * @param context the object to display in. (usually the current object)
     */
    public ItemsAdapter( {
        super();
        items = new ArrayList<>();
        nameChanges = new ArrayList<>();
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
        if (position < nameChanges.size()) {
            itemName.removeTextChangedListener(nameChanges.get(position));
        }
        TextWatcher watcher = new TextWatcher() {
            int change;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                change = count;
                System.out.println(count);
                if (position == getCount() - 1 && count > 0) {
                    add(new Item());
                    parent.post(new Runnable() {
                        @Override
                        public void run() {
                            if(itemName.requestFocus()) {
                                ((Activity) getContext()).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                            }
                        }
                    });
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                System.out.println(change);
                if (change > 0) {
                    System.out.println(s);
                }
            }
        };
        itemName.addTextChangedListener(watcher);
        nameChanges.add(position, watcher);
        // Populate the data into the template view using the data object
        item.setName(itemName.getText().toString());
        itemName.setText(item.getName());
        if (item.getPrice() != 0) {
            itemValue.setText(String.valueOf(item.getPrice()));
        }
        return convertView;
    }

    @Override
    public void add(@Nullable Item item) {
        super.add(item);
        items.add(item);
    }

    public void pop() {
        if (getCount() > 1) {
            remove(getItem(getCount() - 1));
            items.remove(items.size() - 1);
        }
    }

    @NonNull
    @Override
    public ItemsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final LinearLayout linearLayout;

        private final EditText itemName;

        private final EditText itemPrice;

        private final TextWatcher nameWatcher;

        public ViewHolder(View view) {
            super(view);
            linearLayout = (LinearLayout) view.findViewById(R.id.items);
            itemName = linearLayout.findViewById(R.id.itemName);
            itemPrice = linearLayout.findViewById(R.id.itemPrice);
            nameWatcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            };
            itemName.addTextChangedListener(nameWatcher);
        }

        public TextWatcher getNameWatcher() {
            return nameWatcher;
        }

        public LinearLayout getTextView() {
            return linearLayout;
        }

        public EditText getItemName() {
            return itemName;
        }

        public EditText getItemPrice() {
            return itemPrice;
        }
    }
}

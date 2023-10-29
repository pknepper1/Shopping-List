package com.pknepps.shoppinglist;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.view.*;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * This is an adapter that stores and displays Items in process. It is a customization of the ArrayAdapter class that works with this app.
 */
public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {

    /** The activity holding this adapter */
    Context context;

    /** The arraylist that holds the displayed items */
    private final ArrayList<Item> items;

    /** Keeps track of all ViewHolders in this adapter*/
    private final ArrayList<ViewHolder> views;

    /** The number of elements in items */
    int size;

    /**
     * Initializes a new ItemsAdapter
     */
    public ItemsAdapter(Context context, ArrayList<Item> items) {
        super();
        this.context = context;
        this.items = items;
        views = new ArrayList<>();
        size = items.size();
    }

    /** Create new views (invoked by the layout manager) */
    @NonNull
    @Override
    public ItemsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        views.add(holder);
        return holder;
    }

    /** Replace the contents of a view (invoked by the layout manager) */
    @Override
    public void onBindViewHolder(@NonNull ItemsAdapter.ViewHolder holder, int position) {
        holder.getItemName().setText(items.get(position).getName());
        String price = Double.toString(items.get(position).getPrice());
        if (price.equals("0.0")) {
            holder.getItemPrice().setText("");
        } else {
            holder.getItemPrice().setText(price);
        }
    }

    /** Returns the number of elements in this adapter */
    @Override
    public int getItemCount() {
        return items.size();
    }

    /**
     * Gets the arrayList of items.
     * @return The arrayList of items.
     */
    public ArrayList<Item> getItems() {
        return items;
    }

    /**
     * Gets the arrayList of ViewHolders.
     * @return The arrayList of ViewHolders.
     */
    public ArrayList<ViewHolder> getViews() {
        return views;
    }

    /**
     * Adds new item to the end of items.
     * @param item The item to add.
     */
    public void push(Item item) {
        items.add(item);
        notifyItemChanged(size++);
    }

    /**
     * Removes the last item from items.
     */
    public void pop() {
        if (items.size() <= 1) {
            return;
        }
        items.remove(--size);
        views.remove(size);
        notifyItemRemoved(size);
    }

    /**
     * Removes the item at the specified position from the list.
     * @param position the position of the item to remove.
     * @return The item removed or null if position is out of bounds..
     */
    public Item remove(int position) {
        if (position < 0 || position >= size) {
            return null;
        }
        Item removed = items.remove(position);
        views.remove(position);
        notifyItemRemoved(position);
        size--;
        return removed;
    }

    /**
     * Sums the values of all prices in the list and changes the displayed value to match.
     */
    public void recalculateTotal() {
        final double TAX = 0.07;
        double total = 0;
        ArrayList<Item> items = getItems();
        for (Item item : items) {
            total += item.getPrice();
        }
        ((TextView) ((AppCompatActivity) context).findViewById(R.id.total))
                .setText(String.format(Double.toString(total)));
        ((TextView) ((AppCompatActivity) context).findViewById(R.id.tax))
                .setText(String.format(Double.toString(total * TAX)));
        ((TextView) ((AppCompatActivity) context).findViewById(R.id.totalTax))
                .setText(String.format(Double.toString(total + (total * TAX))));
    }

    /**
     * Provide a reference to the items in the item.xml view
     * (custom ViewHolder)
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        /** The EditText attached to the itemName. */
        private final EditText itemName;

        /** The EditText attached to the item price. */
        private final EditText itemPrice;

        /** The TextWatcher attached to itemName. */
        private final TextWatcher nameWatcher;

        /**
         * Initializes a new ViewHolder with the items.xml layout.
         * @param view The parent view to put this ViewHolder in.
         */
        public ViewHolder(View view) {
            super(view);
            itemName = view.findViewById(R.id.itemName);
            itemPrice = view.findViewById(R.id.itemPrice);
            itemName.setOnKeyListener(new View.OnKeyListener() {
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    // If the event is a key-down event on the "enter" button
                    int position = getAdapterPosition();
                    if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                            (keyCode == KeyEvent.KEYCODE_ENTER) &&
                            itemName.getText().toString().equals("") &&
                            (position < ItemsAdapter.this.size - 1)) {
                        // Perform action on key press
                        ItemsAdapter.this.remove(position);
                        ItemsAdapter.this.getViews().get(position).getItemName().requestFocus();
                        return true;
                    }
                    return false;
                }
            });
            nameWatcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (count > 0 && getAdapterPosition() == ItemsAdapter.this.getItemCount() - 1) {
                        ItemsAdapter.this.push(new Item());
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            };
            itemName.addTextChangedListener(nameWatcher);
            itemPrice.setOnKeyListener(new View.OnKeyListener() {
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    // If the event is a key-down event on the "enter" button
                    int position = getAdapterPosition();
                    if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                            (keyCode == KeyEvent.KEYCODE_ENTER)) {
                        // Records and calculates total cost on pressing enter
                        items.get(getAdapterPosition()).setPrice(itemPrice.getText().toString());
                        ItemsAdapter.this.recalculateTotal();
                        return true;
                    }
                    return false;
                }
            });
        }

        /** Getter for NameWatcher. */
        public TextWatcher getNameWatcher() {
            return nameWatcher;
        }

        /** Getter for the EditText ItemName. */
        public EditText getItemName() {
            return itemName;
        }

        /** Getter for the EditText ItemPrice. */
        public EditText getItemPrice() {
            return itemPrice;
        }
    }
}

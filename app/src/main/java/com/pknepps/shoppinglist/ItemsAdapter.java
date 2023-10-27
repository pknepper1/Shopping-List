package com.pknepps.shoppinglist;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.view.*;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * This is an adapter that stores and displays Items in process. It is a customization of the ArrayAdapter class that works with this app.
 */
public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {

    /** The arraylist that holds the displayed items */
    private final ArrayList<Item> items;

    /** The number of elements in items */
    int size;

    /**
     * Initializes a new ItemsAdapter
     */
    public ItemsAdapter(ArrayList<Item> items) {
        super();
        this.items = items;
        size = items.size();
    }

    /** Create new views (invoked by the layout manager) */
    @NonNull
    @Override
    public ItemsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
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
        notifyItemRemoved(position);
        size--;
        return removed;
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
                    if (getAdapterPosition() < ItemsAdapter.this.getItemCount() - 1 &&
                            s.toString().equals("")) {
                        remove(getAdapterPosition());
                    }
                }
            };
            itemName.addTextChangedListener(nameWatcher);
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

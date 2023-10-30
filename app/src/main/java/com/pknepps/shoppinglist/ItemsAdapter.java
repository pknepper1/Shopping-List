package com.pknepps.shoppinglist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.view.*;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * This is an adapter that stores and displays Items in process.
 * It is a customization of the ArrayAdapter class that works with the RecyclerView in this app.
 * @author Preston Knepper
 */
public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {

    /** The activity holding this adapter */
    private final Context context;

    /** The arraylist that holds the displayed items */
    private final ArrayList<Item> items;

    /** The number of elements in items */
    private int size;

    /**
     * Initializes a new ItemsAdapter.
     * @param context The activity this ItemsAdapter is being created in.
     * @param items The items to display through the RecyclerView.
     */
    public ItemsAdapter(Context context, ArrayList<Item> items) {
        super();
        this.context = context;
        this.items = items;
        size = items.size();
    }

    /**
     * Create new views (invoked by the layout manager)
     * @param parent The parent view in which a ViewHolder will be displayed in.
     * @param viewType The int representation of the type of the view.
     * @return The ViewHolder which represents an item from items.
     */
    @NonNull
    @Override
    public ItemsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Replace the contents of a view (invoked by the layout manager)
     * @param holder The ViewHolder to replace the contents of.
     * @param position The position in the adapter of holder.
     */
    @Override
    public void onBindViewHolder(@NonNull ItemsAdapter.ViewHolder holder, int position) {
        holder.getItemName().setText(items.get(position).getName());
        String price = Double.toString(items.get(position).getPrice());
        if (price.equals("0.0")) {
            holder.getItemPrice().setText("");
        } else {
            holder.getItemPrice().setText(price);
        }
        holder.getRemoveButton().setVisibility(items.get(position).isRemoveButtonVisible()
                ? View.VISIBLE : View.INVISIBLE);
    }

    /**
     * Gets the number of elements in this adapter.
     * @return the number of elements in this adapter.
     */
    @Override
    public int getItemCount() {
        return size;
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
     * Removes the item at the specified position from the list.
     * @param position the position of the item to remove.
     */
    public void remove(int position) {
        if (position < 0 || position >= size) {
            return;
        }
        items.get(position).setRemoveButtonVisible(false);
        items.remove(position);
        notifyItemRemoved(position);
        size--;
    }

    /**
     * Sums the values of all prices in the list and changes the displayed value to match.
     */
    @SuppressLint("DefaultLocale")
    public void recalculateTotal() {
        final double TAX = 0.07;
        double total = 0;
        ArrayList<Item> items = getItems();
        for (Item item : items) {
            total += item.getPrice();
        }
        ((TextView) ((AppCompatActivity) context).findViewById(R.id.total))
                .setText(String.format("$%.2f", total));
        ((TextView) ((AppCompatActivity) context).findViewById(R.id.tax))
                .setText(String.format("$%.2f", total * TAX));
        ((TextView) ((AppCompatActivity) context).findViewById(R.id.totalTax))
                .setText(String.format("$%.2f", total + (total * TAX)));
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

        /** The TextWatcher attached to itemPrice. */
        private final TextWatcher priceWatcher;

        private final Button removeButton;

        /**
         * Initializes a new ViewHolder with the items.xml layout.
         * @param view The parent view to put this ViewHolder in.
         */
        public ViewHolder(View view) {
            super(view);
            itemName = view.findViewById(R.id.itemName);
            itemPrice = view.findViewById(R.id.itemPrice);
            removeButton = view.findViewById(R.id.removeButton);

            // Sets a watcher for the item name EditText which will cause new items to be added.
            nameWatcher = new TextWatcher() {

                // Unused
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                /*
                 * While text is being changed, if this is the last item in the adapter, add a new
                 * item.
                 */
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (count > 0 && getAdapterPosition() == ItemsAdapter.this.getItemCount() - 1) {
                        items.get(getAdapterPosition()).setRemoveButtonVisible(true);
                        ItemsAdapter.this.push(new Item());
                        ItemsAdapter.this.notifyItemChanged(getAdapterPosition());
                    }
                }

                /*
                 * After text is changed, save it to the corresponding item in items.
                 */
                @Override
                public void afterTextChanged(Editable s) {
                    items.get(getAdapterPosition()).setName(itemName.getText().toString());
                }
            };
            itemName.addTextChangedListener(nameWatcher);

            // If an edit text is focused, puts the cursor at the end of the line.
            View.OnFocusChangeListener onFocusChangeListener = (v, hasFocus) -> {
                if (hasFocus) {
                    ((EditText) v).setSelection(((EditText) v).getText().length());
                }
            };
            itemName.setOnFocusChangeListener(onFocusChangeListener);

            // sets up a watcher for the price EditText which will cause total to recalculate.
            priceWatcher = new TextWatcher() {

                // Unused
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                // Unused
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                /*
                 * Saves the price to the corresponding item in items, then recalculates the
                 * price totals
                 */
                @Override
                public void afterTextChanged(Editable s) {
                    if (s.toString().equals(".")) {
                        itemPrice.setText("0.");
                        itemPrice.setSelection(itemPrice.getText().length());
                    }
                    items.get(getAdapterPosition()).setPrice(itemPrice.getText().toString());
                    ItemsAdapter.this.recalculateTotal();
                }
            };
            itemPrice.addTextChangedListener(priceWatcher);
            itemName.setOnFocusChangeListener(onFocusChangeListener);

            // Adds a listener to the removeButton which will remove this item on click.
            removeButton.setOnClickListener(v -> ItemsAdapter.this.remove(
                    getAdapterPosition() < ItemsAdapter.this.size
                            ? getAdapterPosition() : size - 1));

            int position = getAdapterPosition();
            if (position < 0 || position >= size) {
                position = items.size() - 1;
            }
        }

        /**
         * Getter for nameWatcher.
         * @return The nameWatcher of itemName, the EditText which contains the item name.
         */
        public TextWatcher getNameWatcher() {
            return nameWatcher;
        }

        /**
         * Getter for priceWatcher.
         * @return The priceWatcher of itemPrice, the EditText which contains the item price.
         */
        public TextWatcher getPriceWatcher() {
            return priceWatcher;
        }

        /**
         * Getter for the EditText ItemName.
         * @return The EditText which contains the item name.
         */
        public EditText getItemName() {
            return itemName;
        }

        /**
         * Getter for the EditText ItemPrice.
         * @return the EditText which contains the item price.
         */
        public EditText getItemPrice() {
            return itemPrice;
        }

        /**
         * Getter for the Button removeButton.
         * @return the button attached to this view.
         */
        public Button getRemoveButton() {
            return removeButton;
        }
    }
}

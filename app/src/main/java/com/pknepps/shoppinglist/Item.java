package com.pknepps.shoppinglist;

import android.content.Context;
import android.widget.EditText;
/**
 * This class models a item to go into the shopping list. It has fields for the item name and
 * price.
 */
public class Item {
    /** The name of the item. */
    private EditText name;
    /** The price of the item. */
    private EditText price;

    public Item(Context context, String name, String price) {
        this.name = new EditText(context);
        this.price = new EditText(context);
        this.name.append(name);
        this.price.append(price);
    }

    public Item(Context context, String name) {
        this(context, name, "");
    }

    public Item(Context context) {
        this(context, "");
    }

    public EditText getName() {
        return name;
    }

    public EditText getPrice() {
        return price;
    }
}

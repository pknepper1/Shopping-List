package com.pknepps.shoppinglist;

import android.content.Context;
import android.widget.EditText;
/**
 * This class models a item to go into the shopping list. It has fields for the item name and
 * price.
 */
public class Item {
    /** The name of the item. */
    private String name;
    /** The price of the item. */
    private double price;

    public Item(String name, String price) {
        this.name = name;
        this.price = Double.parseDouble(price);
    }

    public Item(String name) {
        this(name, "0.00");
    }

    public Item() {
        this("");
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(String price) {
        this.price = Double.parseDouble(price);
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}

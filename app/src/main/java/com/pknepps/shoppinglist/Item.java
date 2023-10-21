package com.pknepps.shoppinglist;

/**
 * This class models a item to go into the shopping list. It has fields for the item name and
 * price.
 */
public class Item {
    /** The name of the item. */
    private String name;
    /** The price of the item. */
    private String price;

    public Item(String name, String price) {
        this.name = name;
        this.price = price;
    }

    public Item(String name) {
        this.name = name;
        this.price = "0.00";
    }

    public Item() {
        this.name = "";
        this.price = "0.00";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}

package com.pknepps.shoppinglist;
/**
 * This class models a item to go into the shopping list. It has fields for the item name and
 * price.
 */
public class Item {
    /** The name of the item. */
    private String name;
    /** The price of the item. */
    private Double price;


    public Item(String name, String price) {
        this.name = name;
        this.price = Double.parseDouble(price);
    }

    public Item(String name) {
        this( name, "0.0");
    }

    public Item() {
        this("");
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(String price) {
        this.price = Double.parseDouble(price);
    }
}

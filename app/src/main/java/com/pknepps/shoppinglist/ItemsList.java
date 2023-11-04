package com.pknepps.shoppinglist;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This is an empty wrapper for arraylist meant to make reading serialization easier.
 */
public class ItemsList extends ArrayList<Item> implements Serializable {
}

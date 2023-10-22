package com.pknepps.shoppinglist;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;

import java.util.jar.Attributes;

/**
 * An implementation of TextWatcher which will listen for any changes in any item.name field.
 * If a field is changed and it is the last item in the list, it will add a new list item
 */
public class NameWatcher implements TextWatcher {

    /** The context of the object this class was created in */
    Context context;

    /** The adapter of items to listen to. */
    ItemsAdapter itemsAdapter;

    /** The individual item the action was taken on. */
    Item item;

    public NameWatcher(Context context, ItemsAdapter itemsAdapter, Item item) {
        this.context = context;
        this.itemsAdapter = itemsAdapter;
        this.item = item;
    }

    /** Required, but unused. */
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    /**
     * Will add a new item if the current item is the last one in the list. Is called while
     * item.name is edited
     */
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (itemsAdapter.getCount() - 1 == itemsAdapter.getPosition(item)) {
            Item newItem = new Item(context);
            newItem.getName().addTextChangedListener(new NameWatcher(context, itemsAdapter, item));
            itemsAdapter.add(newItem);
        }
    }

    /** Required, but unused. */
    @Override
    public void afterTextChanged(Editable s) {
    }
}

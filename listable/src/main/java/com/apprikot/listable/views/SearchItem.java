package com.apprikot.listable.views;

import android.widget.EditText;

import com.apprikot.listable.components.UnderHeaderVo;
import com.apprikot.listable.interfaces.Listable;
import com.apprikot.listable.listeners.TextEnteredListener;
import com.apprikot.listable.model.HolderClass;

public class SearchItem extends UnderHeaderVo implements Listable {
    public EditText searchEditText;
    public TextEnteredListener textEnteredListener;
    public boolean clear;
    public boolean focused;
    public String searchText;
    public HolderClass listItemType;

    public SearchItem() {
    }

    public SearchItem(TextEnteredListener textEnteredListener, HolderClass listItemType) {
        this.textEnteredListener = textEnteredListener;
        this.listItemType = listItemType;
    }

    @Override
    public HolderClass getListItemType() {
        return listItemType;
    }

    @Override
    public int hashCode() {
        return 555;
    }

    @Override
    public boolean equals(Object other) {
        return (other == this || other instanceof SearchItem);
    }
}

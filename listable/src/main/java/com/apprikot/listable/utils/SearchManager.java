package com.apprikot.listable.utils;

import android.util.Log;

import com.apprikot.listable.components.ModelUtils;
import com.apprikot.listable.interfaces.Listable;
import com.apprikot.listable.interfaces.Searchable;
import com.apprikot.listable.views.GeneralListAdapter;
import com.apprikot.listable.views.SearchItem;

import java.util.ArrayList;
import java.util.List;

public class SearchManager {
    public GeneralListAdapter mGeneralListAdapter;
    public List<Listable> mItems;

    public SearchManager() {
    }

    public SearchManager withAdapter(GeneralListAdapter generalListAdapter) {
        mGeneralListAdapter = generalListAdapter;
        mItems = new ArrayList<>(generalListAdapter.getItems());
        return this;
    }

    public SearchManager withSearchResult(List<Listable> searchResult) {
        notify(searchResult);
        return this;
    }

    public void checkEmpty(String searchKey) {
        if (searchKey.isEmpty()) {
            notify(mItems);
        }
    }

    public List<Listable> search(String searchKey, boolean notify) {
        List<Listable> result = filter(searchKey);
        if (notify) {
            notify(result);
        }
        return result;
    }

    public List<Listable> filter(String searchKey) {
        List<Listable> result = new ArrayList<>();
        if (searchKey.isEmpty()) {
            result.addAll(mItems);
        } else {
            for (Listable item : mItems) {
                if (item instanceof Searchable) {
                    String value = ((Searchable) item).getSearchKey();
                    List<String> values = ((Searchable) item).getSearchKeys();

                    if(values!=null) {
                        for (String v : values) {
                            if (v.toLowerCase().contains(searchKey.toLowerCase())) {
                                result.add(item);
                            }
                        }
                    }
                    if(value!=null) {
                        if (value.toLowerCase().contains(searchKey.toLowerCase())) {
                            result.add(item);
                        }
                    }
                }
            }
        }
        result = ModelUtils.getNoDuplicated(result);
        int index = mGeneralListAdapter.indexOf(new SearchItem());
        if (index != -1) {
            SearchItem searchItem = (SearchItem) mGeneralListAdapter.getItem(index);
            result.remove(searchItem);
            result.add(0, searchItem);
        }
        return result;
    }

    private void notify(List<Listable> items) {
        if (mGeneralListAdapter == null) {
            Log.e(SearchManager.class.getSimpleName(), "Adapter is null! This shouldn't happen.");
            return;
        }
        mGeneralListAdapter.clear();
        mGeneralListAdapter.addAll(items);
        mGeneralListAdapter.notifyDataSetChanged();
    }
}

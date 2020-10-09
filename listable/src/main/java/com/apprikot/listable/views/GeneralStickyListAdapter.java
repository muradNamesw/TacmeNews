package com.apprikot.listable.views;

import android.content.Context;
import androidx.fragment.app.Fragment;

import com.apprikot.listable.interfaces.Listable;
import com.apprikot.listable.listeners.OnItemClickListener;
import com.apprikot.listable.model.HolderClass;

import java.util.List;
import java.util.Map;

public class GeneralStickyListAdapter extends GeneralListAdapter {
    private Map<Long, Listable> mHeadersMap;
    private HolderClass holderClass;
    private String TAG = "Adapter";

    public GeneralStickyListAdapter(Context context, List<? extends Listable> data, OnItemClickListener.OnItemClickCallback onItemClickCallback) {
        super(context, data, onItemClickCallback);
    }

    public GeneralStickyListAdapter(Context context, List<? extends Listable> data,
                                    OnItemClickListener.OnItemClickCallback onItemClickCallback, Fragment fragment) {
        super(context, data, onItemClickCallback, fragment);
        TAG = fragment.getClass().getSimpleName();
    }

}
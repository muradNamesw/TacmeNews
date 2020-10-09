package com.apprikot.listable.views;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.View;
import android.view.ViewGroup;

import com.apprikot.listable.interfaces.Listable;
import com.apprikot.listable.listeners.OnItemClickListener;
import com.apprikot.listable.model.HolderClass;
import com.apprikot.listable.views.viewholders.BaseViewHolder;

import java.lang.reflect.Constructor;
import java.util.List;

public class GeneralListAdapter extends RecyclerArrayAdapter<Listable, BaseViewHolder> {
    protected Fragment fragment;

    public GeneralListAdapter(Context context, List<? extends Listable> data, OnItemClickListener.OnItemClickCallback onItemClickCallback) {
        super(context, onItemClickCallback);
        addAll(data);
    }

    public GeneralListAdapter(Context context, List<? extends Listable> data,
                              OnItemClickListener.OnItemClickCallback onItemClickCallback, Fragment fragment) {
        this(context, data, onItemClickCallback);
        this.fragment = fragment;
    }


    @Override
    @NonNull
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        try {
            //RestHolder listItemType = ListItemType.values()[viewType];

            HolderClass holderClass = null;
            for (Listable listable : mItems) {
                if (listable.getListItemType().layoutResId == viewType) {
                    holderClass = listable.getListItemType();
                    break;
                }
            }
            View view = mInflater.inflate(holderClass.layoutResId, viewGroup, false);
            if (holderClass.isFragment) {
                Constructor constructor = holderClass.viewHolderClass.getConstructor(View.class, Fragment.class);
                return (BaseViewHolder) constructor.newInstance(view, fragment);
            } else {
                Constructor constructor = holderClass.viewHolderClass.getConstructor(View.class, OnItemClickListener.OnItemClickCallback.class);
                return (BaseViewHolder) constructor.newInstance(view, mOnItemClickCallback);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder viewHolder, int position) {
        if (viewHolder.getClass().equals(mItems.get(position).getListItemType().viewHolderClass)) {
            viewHolder.draw(mItems.get(position));
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mItems.get(position).getListItemType().layoutResId;
    }
}
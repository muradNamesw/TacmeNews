package com.apprikot.listable.views.viewholders;

import android.content.Context;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.apprikot.listable.interfaces.Listable;
import com.apprikot.listable.listeners.OnItemClickListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public abstract class BaseViewHolder extends RecyclerView.ViewHolder {
    protected OnItemClickListener onItemClickListener;

    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    public BaseViewHolder(View itemView, OnItemClickListener.OnItemClickCallback onItemClickCallback) {
        super(itemView);
        if (onItemClickCallback != null) {
            onItemClickListener = new OnItemClickListener(getAdapterPosition(), onItemClickCallback);
        }
    }

    public void draw(Listable listable) {
        if (onItemClickListener != null) {
            onItemClickListener.setListableItem(listable);
            onItemClickListener.setPosition(getAdapterPosition());
        }
    }

    protected void attachClickListener(View... views) {
        for (View view : views) {
            view.setOnClickListener(onItemClickListener);
        }
    }

    protected View find(int id) {
        return itemView.findViewById(id);
    }

    protected Context getContext() {
        return itemView.getContext();
    }

    protected String getString(int resId) {
        return getContext().getString(resId);
    }

    protected int getColor(int resId) {
        return ContextCompat.getColor(getContext(), resId);
    }
    protected void loadImage(String iconUrl,ImageView imageView) {
        Glide.with(imageView.getContext()).load(iconUrl).into(imageView);
    }
    protected void loadImage(String iconUrl,ImageView imageView,int place) {

        Glide.with(imageView.getContext())
                .load(iconUrl)
                .apply(RequestOptions.placeholderOf(place).error(place))
                .into(imageView);
        }
}
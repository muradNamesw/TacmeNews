package com.apprikot.listable.views.viewholders;

import android.view.View;

import com.apprikot.listable.components.LoadingMore;
import com.apprikot.listable.interfaces.Listable;
import com.apprikot.listable.interfaces.Releasable;
import com.apprikot.listable.listeners.OnItemClickListener;

public class LoadingMoreViewHolder extends BaseViewHolder implements Releasable {

    public LoadingMoreViewHolder(final View itemView, OnItemClickListener.OnItemClickCallback onItemClickCallback) {
        super(itemView, onItemClickCallback);
        setIsRecyclable(false);

    }

    @Override
    public void draw(Listable listable) {
        super.draw(listable);
        if (listable instanceof LoadingMore) {
            LoadingMore loadingMore = (LoadingMore) listable;
            loadingMore.loadMore();
            loadingMore.setReleasable(this);
        }
    }

    @Override
    public void release() {
        itemView.setVisibility(View.GONE);
    }
}
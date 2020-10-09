package com.apprikot.listable.views.viewholders;

import androidx.recyclerview.widget.RecyclerView;
import android.view.ViewGroup;

public class BlankAdapter extends RecyclerView.Adapter {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
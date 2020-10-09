package com.apprikot.listable.components;

import com.apprikot.listable.R;
import com.apprikot.listable.interfaces.Listable;
import com.apprikot.listable.interfaces.LoadMore;
import com.apprikot.listable.interfaces.Releasable;
import com.apprikot.listable.model.HolderClass;
import com.apprikot.listable.views.viewholders.LoadingMoreViewHolder;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class LoadingMore extends UnderHeaderVo implements Listable {
    private LoadMore mLoadMore;
    private Releasable mReleasable;
    private int code = 555;

    public LoadingMore(LoadMore loadMore) {
        mLoadMore = loadMore;
    }

    public LoadingMore() {
    }

    public void loadMore() {
        if (mLoadMore != null) {
            mLoadMore.loadMore();
        }
    }

    public void setReleasable(Releasable releasable) {
        mReleasable = releasable;
    }

    public void release() {
        if (mReleasable != null) {
            mReleasable.release();
        }
    }

    public LoadingMore(long headerId) {
        this.headerId = headerId;
    }

    @Override
    public HolderClass getListItemType() {
        return new HolderClass(LoadingMoreViewHolder.class, R.layout.item_loading_more);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(code).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof LoadingMore)) {
            return false;
        }
        LoadingMore rhs = ((LoadingMore) other);
        return new EqualsBuilder().append(code, rhs.code).isEquals();
    }
}
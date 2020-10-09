package com.apprikot.listable.interfaces;

public interface Configurable {
    Configurable config();

    Configurable hasHeaders(boolean hasHeaders);

    Configurable hasSearch(boolean hasSearch);

    Configurable hasMargins(boolean hasMargins);

    Configurable hasLoadMore(boolean loadMore);

    Configurable hasPullRefresh(boolean hasPullRefresh);

    Configurable fromPageNum(int fromIndex);

    Configurable toPageNum(int toIndex);
}
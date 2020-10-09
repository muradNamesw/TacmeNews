package com.apprikot.listable.fragments;

import android.os.Bundle;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.apprikot.listable.R;
import com.apprikot.listable.components.LoadingMore;
import com.apprikot.listable.components.ModelUtils;
import com.apprikot.listable.interfaces.Configurable;
import com.apprikot.listable.interfaces.Listable;
import com.apprikot.listable.interfaces.LoadMore;
import com.apprikot.listable.listeners.OnItemClickListener;
import com.apprikot.listable.listeners.TextEnteredListener;
import com.apprikot.listable.model.HolderClass;
import com.apprikot.listable.utils.SearchManager;
import com.apprikot.listable.views.GeneralStickyListAdapter;
import com.apprikot.listable.views.SearchItem;
import com.apprikot.listable.views.viewholders.BlankAdapter;
import com.apprikot.listable.views.viewholders.BlankViewHolder;
import com.apprikot.listable.views.viewholders.SearchPullViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseListFragmentNew extends BaseFragment implements
        LoadMore,
        Configurable,
        OnItemClickListener.OnItemClickCallback,
        SwipeRefreshLayout.OnRefreshListener {
    public static final String TAG = BaseListFragmentNew.class.getSimpleName();

    protected RecyclerView recyclerView;
    protected LinearLayoutManager layoutManager;
    protected GeneralStickyListAdapter generalStickyListAdapter;
    protected SwipeRefreshLayout swipeRefreshLayout;
    protected SearchManager searchManager;
    protected boolean mLoadMore;
    protected boolean mSearch;
    protected boolean mIsLoadingMore;

    protected boolean isRefreshing;
    protected int mPageNum;
    protected int mFromPage;
    protected int mToPage = Integer.MAX_VALUE;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        root = view;
        prepareRecyclerView();
        initRefreshLayout();
//        if (visible) {
        begin();
//        }
    }

    protected void begin() {
        if (!viewCreated) {
            Log.i("begin", "viewCreated: false " + getClass().getSimpleName());
            return;
        }
        Log.i("begin", getClass().getSimpleName());
        if (generalStickyListAdapter == null) {
            sendRequest();
        } else {
            recyclerView.setAdapter(generalStickyListAdapter);
            postPrepareRecyclerView();
        }
    }

    protected void sendRequest() {
    }

    protected void postPrepareRecyclerView() {
    }


    protected List<Listable> getItems(Object result) {
        return new ArrayList<>();
    }

    protected Map<Long, Listable> getHeadersMap() {
        return new HashMap<>();
    }

    protected HolderClass getHeaderListItemType() {
        return new HolderClass(BlankViewHolder.class, 0);
    }

    protected void drawItems(List<Listable> items, boolean increment) {
        if (getActivity() == null || !isAdded()) {
            Log.e(TAG, "activity is null! this shouldn't happen");
            return;
        }
        int incrementValue = (increment ? 1 : 0);
        if (mLoadMore && !items.isEmpty() && mPageNum < mToPage) {
            items.add(new LoadingMore(this));
            mPageNum += incrementValue;
        }
        if (generalStickyListAdapter == null) {
            if (mSearch) {
                addSearch(items);
            }
            generalStickyListAdapter = new GeneralStickyListAdapter(
                    getActivity(),
                    items,
                    this);

            recyclerView.setAdapter(generalStickyListAdapter);
            postPrepareRecyclerView();
        } else {
            List<Listable> itemsToLoad;
            if (isRefreshing || !mLoadMore) {
                itemsToLoad = items;
                isRefreshing = false;
            } else {
                itemsToLoad = generalStickyListAdapter.getItems();
                removeLoadMoreItem(itemsToLoad);
                itemsToLoad.addAll(items);
                itemsToLoad = ModelUtils.getNoDuplicated(itemsToLoad);
            }
            if (mSearch) {
                addSearch(itemsToLoad);
            }
            generalStickyListAdapter.clear();
            generalStickyListAdapter.addAll(itemsToLoad);
            generalStickyListAdapter.notifyDataSetChanged();
            if (searchManager != null) {
                searchManager.withAdapter(generalStickyListAdapter);
            }
            if (recyclerView.getAdapter() == null) {
                recyclerView.setAdapter(generalStickyListAdapter);
            }

        }
    }

    protected void addSearch(List<Listable> items) {
        SearchItem searchItem = new SearchItem((TextEnteredListener) this, new HolderClass(SearchPullViewHolder.class, R.layout.layout_search));
        items.remove(searchItem);
        items.add(0, searchItem);
    }

    protected void drawItems(List<Listable> items) {
        drawItems(items, true);
    }

    protected void removeLoadMoreItem(List<Listable> items) {
        int index = items.indexOf(new LoadingMore(this));
        if (index != -1) {
            LoadingMore loadingMore = (LoadingMore) items.get(index);
            loadingMore.release();
            items.remove(index);
        }
    }

    protected void prepareRecyclerView() {
        recyclerView = (RecyclerView) root.findViewById(R.id.rv);
        if (recyclerView != null) {
            layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);
            if (generalStickyListAdapter == null) {
                recyclerView.setAdapter(new BlankAdapter());
            }
        }
    }


    protected void setListMargins(int left, int top, int right, int bottom) {
        if (recyclerView.getLayoutParams() instanceof FrameLayout.LayoutParams) {
            ((FrameLayout.LayoutParams) recyclerView.getLayoutParams()).setMargins(left, top, right, bottom);
        } else if (recyclerView.getLayoutParams() instanceof RelativeLayout.LayoutParams) {
            ((RelativeLayout.LayoutParams) recyclerView.getLayoutParams()).setMargins(left, top, right, bottom);
        } else if (recyclerView.getLayoutParams() instanceof CoordinatorLayout.LayoutParams) {
            ((CoordinatorLayout.LayoutParams) recyclerView.getLayoutParams()).setMargins(left, top, right, bottom);
        }
    }

    protected void initRefreshLayout() {
        swipeRefreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.refresh);
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setOnRefreshListener(this);
            swipeRefreshLayout.setEnabled(false);
        }
    }

    @Override
    public void onItemClicked(View view, Listable listableItem, int position) {
    }

    @Override
    public Configurable config() {
        return this;
    }

    @Override
    public Configurable hasHeaders(boolean hasHeaders) {
        return this;
    }

    @Override
    public Configurable hasPullRefresh(boolean hasPullRefresh) {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setEnabled(hasPullRefresh);
        }
        return this;
    }

    protected void refreshingDone() {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public Configurable hasMargins(boolean hasMargins) {
        if (!hasMargins) {
            setListMargins(0, 0, 0, 0);
        }
        return this;
    }

    @Override
    public Configurable hasSearch(boolean search) {
        searchManager = new SearchManager();
        mSearch = search;
        return this;
    }

    @Override
    public Configurable hasLoadMore(boolean loadMore) {
        mLoadMore = loadMore;
        return this;
    }

    @Override
    public Configurable fromPageNum(int fromPage) {
        mFromPage = fromPage;
        mPageNum = fromPage;
        return this;
    }

    @Override
    public Configurable toPageNum(int toPage) {
        mToPage = toPage;
        return this;
    }

    @Override
    public void loadMore() {
        if (!mIsLoadingMore) {
            mIsLoadingMore = true;
            sendRequest();
        }
    }

    @Override
    public void onDestroyView() {
        mIsLoadingMore = false;
        super.onDestroyView();
    }

    protected void onReload() {
    }

    protected void resetAdapter() {
        if (generalStickyListAdapter != null) {
            generalStickyListAdapter.clear();
            generalStickyListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRefresh() {
        mPageNum = mFromPage;
        mIsLoadingMore = false;
        isRefreshing = true;
    }
}
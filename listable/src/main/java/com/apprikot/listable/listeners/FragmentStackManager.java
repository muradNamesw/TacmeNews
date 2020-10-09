package com.apprikot.listable.listeners;

import androidx.fragment.app.Fragment;

public interface FragmentStackManager {
    void setCurrentTabFragment(Fragment currentTabFragment);

    Fragment getCurrentTabFragment();

    Fragment getCurrentMainFragment();
}
package com.apprikot.listable.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Parcelable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.apprikot.listable.R;
import com.apprikot.listable.listeners.FragmentStackManager;

import java.io.Serializable;

public class BaseFragment extends Fragment implements
        DialogInterface.OnCancelListener,
        DialogInterface.OnDismissListener {
    public static final int DIRECTION_UP = -1;
    public static final int DIRECTION_DOWN = 1;

    public static final String TAG = BaseFragment.class.getSimpleName();
    public static final String EXTRA_PARCELABLE = "PARCELABLE";
    public static final String EXTRA_SERIALIZABLE = "EXTRA_SERIALIZABLE";

    public static final String EXTRA_LONG = "INTEGER";
    public static final String EXTRA_LIST_PARCELABLE = "LIST_PARCELABLE";
    public static final String EXTRA_AD = "AD";
    public static final String EXTRA_TYPE = "EXTRA_TYPE";
    public static final String EXTRA_ID = "EXTRA_ID";

    protected View root;
    protected Toolbar toolbar;
    protected boolean mPendingRequest;
    protected boolean hasBeenSelected;
    protected boolean visible;
    protected boolean viewCreated;
    protected long extraLong;
    private int toolbarHeight;
    private Toast toast;
    private int appBarOffset;
    public static boolean isAnimationDisabled;
    public boolean haveError = false;

    public static Fragment newInstance(Class fragmentClass, Serializable parcelable) {
        try {
            Fragment fragment = (Fragment) fragmentClass.newInstance();
            Bundle data = new Bundle();
            data.putSerializable(EXTRA_PARCELABLE, parcelable);
            fragment.setArguments(data);
            return fragment;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Fragment();
    }

    public static Fragment newInstance(Class fragmentClass, Parcelable parcelable) {
        try {
            Fragment fragment = (Fragment) fragmentClass.newInstance();
            Bundle data = new Bundle();
            data.putParcelable(EXTRA_PARCELABLE, parcelable);
            fragment.setArguments(data);
            return fragment;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Fragment();
    }

    public static Fragment newInstance(Class fragmentClass, long extraLong) {
        try {
            Fragment fragment = (Fragment) fragmentClass.newInstance();
            Bundle data = new Bundle();
            data.putLong(EXTRA_LONG, extraLong);
            fragment.setArguments(data);
            return fragment;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Fragment();
    }

    public static Fragment newInstance(Class fragmentClass, String extraString) {
        try {
            Fragment fragment = (Fragment) fragmentClass.newInstance();
            Bundle data = new Bundle();
            data.putString(EXTRA_ID, extraString);
            fragment.setArguments(data);
            return fragment;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Fragment();
    }

    public static Fragment newInstance(Class fragmentClass, Parcelable parcelable, long extraLong) {
        try {
            Fragment fragment = (Fragment) fragmentClass.newInstance();
            Bundle data = new Bundle();
            data.putParcelable(EXTRA_PARCELABLE, parcelable);
            data.putLong(EXTRA_LONG, extraLong);
            fragment.setArguments(data);
            return fragment;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Fragment();
    }

    protected BaseFragment getBaseFragment() {
        return BaseFragment.this;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TypedValue tv = new TypedValue();
        if (getActivity().getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            toolbarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
        }
        if (getArguments() != null) {
            extraLong = getArguments().getLong(EXTRA_LONG, -1);
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewCreated = true;
        root = view;
//        initLoadingUtils();
    }


    public void moveToFragment(Fragment fragment, int containerId, boolean addToBackStack) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager()
                .beginTransaction();
        fragmentTransaction.replace(containerId, fragment, null);
        if (addToBackStack) fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void moveToChildFragment(Fragment fragment, int containerId, boolean addToBackStack) {
        if (getActivity() instanceof FragmentStackManager) {
            FragmentStackManager fragmentStackManager = (FragmentStackManager) getActivity();
            if (fragmentStackManager.getCurrentTabFragment() == null) {
                Log.e(TAG, "getCurrentTabFragment is null! this shouldn't happen");
                return;
            }
            final FragmentTransaction fragmentTransaction = fragmentStackManager.getCurrentTabFragment()
                    .getChildFragmentManager().beginTransaction();
            fragmentTransaction.replace(containerId, fragment, null);
            if (addToBackStack) fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }


    @Override
    public void onDestroyView() {

        visible = false;
        viewCreated = false;
        hasBeenSelected = false;
        super.onDestroyView();
    }


    public void showToast(int textResId) {
        if (isDetached() || !isAdded()) {
            return;
        }
        showToast(getString(textResId));
    }

    public void showToast(String text) {
        if (isDetached() || !isAdded()) {
            return;
        }
        if (toast == null) {
            toast = Toast.makeText(getActivity(), R.string.app_name, Toast.LENGTH_SHORT);
        }
        toast.setText(text);
        toast.show();
    }


    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        if (isAnimationDisabled) {
            Animation a = new Animation() {
            };
            a.setDuration(0);
            return a;
        }

        Animation animation = super.onCreateAnimation(transit, enter, nextAnim);

        if (root == null || !root.isHardwareAccelerated()) {
            return animation;
        }

        if (animation == null && nextAnim != 0) {
            animation = AnimationUtils.loadAnimation(getActivity(), nextAnim);
        }

        if (animation != null) {
            root.setLayerType(View.LAYER_TYPE_HARDWARE, null);

            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    root.setLayerType(View.LAYER_TYPE_NONE, null);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
        }
        return animation;
    }


    /***
     * Loading-Dialog-Stuff
     ***/


    @Override
    public void onCancel(DialogInterface dialog) {
        Log.i("Dialog", "Canceled");
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        Log.i("Dialog", "Dismissed");

    }

    public int getAppBarOffset() {
        return appBarOffset;
    }

    public void setAppBarOffset(int appBarOffset) {
        this.appBarOffset = appBarOffset;
    }

    protected FragmentManager getTabFragmentManager() {
        if (getActivity() instanceof FragmentStackManager) {
            FragmentStackManager fragmentStackManager = (FragmentStackManager) getActivity();
            return fragmentStackManager.getCurrentTabFragment().getChildFragmentManager();
        }
        return null;
    }

    protected void showQDialog(int message, int positiveTitle, int negativeTitle,
                               final DialogInterface.OnClickListener positiveOnClickListener) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setPositiveButton(positiveTitle, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int arg1) {
                dialog.dismiss();
                positiveOnClickListener.onClick(dialog, arg1);
            }
        });
        alertDialogBuilder.setNegativeButton(negativeTitle, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
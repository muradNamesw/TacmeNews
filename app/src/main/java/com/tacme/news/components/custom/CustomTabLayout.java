package com.tacme.news.components.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import androidx.annotation.NonNull;
import com.google.android.material.tabs.TabLayout;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.apprikot.listable.utils.FontUtils;

import java.util.ArrayList;
import java.util.List;

import com.tacme.news.R;
import com.tacme.news.view.utils.DimenUtils;


public class CustomTabLayout extends TabLayout {
    private Typeface mTypeface;

    public CustomTabLayout(Context context) {
        super(context);
    }

    public CustomTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CustomTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CustomTabLayout);
        int ordinal = typedArray.getInt(R.styleable.CustomTabLayout_customFont, 0);
        mTypeface = FontUtils.getFont(getContext(), FontUtils.CustomFont.values()[ordinal]);
        typedArray.recycle();
    }

    @Override
    public void addTab(@NonNull Tab tab) {
        super.addTab(tab);
        ViewGroup mainView = (ViewGroup) getChildAt(0);
        ViewGroup tabView = (ViewGroup) mainView.getChildAt(tab.getPosition());
        for (int i = 0; i < tabView.getChildCount(); i++) {
            View tabViewChild = tabView.getChildAt(i);
            if (tabViewChild instanceof TextView) {
                CustomTextView textView = ((CustomTextView) tabViewChild);
                textView.setTypeface(mTypeface, Typeface.NORMAL);
                View parentView = (View) textView.getParent();
                parentView.setPadding(20, 0, 20, 0);
            }
        }
    }

    public void setWeights(Float... weights) {
        ViewGroup slidingTabStrip = (ViewGroup) getChildAt(0);
        if (weights.length != slidingTabStrip.getChildCount()) {
            return;
        }
        for (int i = 0; i < weights.length; i++) {
            View tab1 = slidingTabStrip.getChildAt(i);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) tab1.getLayoutParams();
            layoutParams.weight = weights[i];
            tab1.setLayoutParams(layoutParams);
        }
    }

    public void selectTab(int index) {
        Tab tab = getTabAt(index);
        if (tab != null) {
            tab.select();
        }
    }

    public float getTextWidth(String sampleText) {
        Paint paint = new Paint();
        Rect bounds = new Rect();
        paint.setTypeface(mTypeface);
        float textSize = getResources().getDimension(R.dimen.font_medium);
        paint.setTextSize(textSize);
        paint.getTextBounds(sampleText, 0, sampleText.length(), bounds);
        return bounds.width() * 1.35f;
    }

    public void adaptWeights(List<String> titles) {
        int screenWidth = DimenUtils.getScreenSize(getContext()).x;
        List<Float> widths = getWidths(titles);
        float maxW = Float.MIN_VALUE;
        for (float w : widths) {
            if (maxW < w) {
                maxW = w;
            }
        }
        float sum = 0;
        for (float w : widths) {
            sum += w;
        }
        if (sum > screenWidth || maxW > screenWidth / widths.size()) {
            setTabMode(TabLayout.MODE_SCROLLABLE);
            setWeights(getWeights(widths, sum));
        }
    }

    private Float[] getWeights(List<Float> widths, float sum) {
        List<Float> weights = new ArrayList<>();
        for (float w : widths) {
            weights.add(w / sum);
        }
        return weights.toArray(new Float[weights.size()]);
    }

    private List<Float> getWidths(List<String> titles) {
        List<Float> widths = new ArrayList<>();
        for (String title : titles) {
            widths.add(getTextWidth(title));
        }
        return widths;
    }
}
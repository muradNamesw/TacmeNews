package com.tacme.news.components.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.Checkable;

import androidx.appcompat.widget.AppCompatTextView;

import com.tacme.news.R;
import com.tacme.news.view.utils.FontUtils;


public class CustomTextView extends AppCompatTextView implements Checkable {

    private boolean mChecked;
    private Drawable background;


    private static final int[] CHECKED_STATE_SET = {android.R.attr.state_checked};

    public CustomTextView(Context context) {
        super(context);
        init(null);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    protected void init(AttributeSet attrs) {

        int ordinal = 1;

        Boolean checked = false;

        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CustomTabLayout);
            ordinal  = typedArray.getInt(R.styleable.CustomTabLayout_customFont,     0);
            checked  = typedArray.getBoolean(R.styleable.CustomTabLayout_tabChecked, false);
            typedArray.recycle();
        }

        mChecked = checked;

        Typeface typeface = FontUtils.getFont(getContext(), FontUtils.CustomFont.values()[ordinal]);
        setTypeface(typeface);
        background = getBackground();
    }

    @Override
    public void setError(CharSequence error) {
        super.setError(error);
        if (error == null) {
            if (background != null) {
                setBackground(background);
            }

        } else {
//            setBackgroundResource(R.drawable.border_red);
        }
    }
    public void setFont(FontUtils.CustomFont font) {
        setTypeface(FontUtils.getFont(getContext(), font));
    }

    @Override
    public int[] onCreateDrawableState(final int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        if (isChecked()) {
            mergeDrawableStates(drawableState, CHECKED_STATE_SET);
        }
        return drawableState;
    }

    @Override
    public void setChecked(boolean checked) {
        if (mChecked == checked) {
            return;
        }
        mChecked = checked;
//        setEnabled(checked);
        refreshDrawableState();
    }

    @Override
    public boolean isChecked() {
        return mChecked;
    }

    @Override
    public void toggle() {
        setChecked(!mChecked);
    }

    public void toggle(boolean animate) {
        toggle();
        if (mChecked && animate) {
//            YoYo.with(Techniques.Pulse).duration(Constants.ANIM_DURATION_PULSE).playOn(this);
        }
    }
}
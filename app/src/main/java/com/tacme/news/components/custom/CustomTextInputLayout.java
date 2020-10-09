package com.tacme.news.components.custom;

import android.content.Context;
import com.google.android.material.textfield.TextInputLayout;
import android.util.AttributeSet;
import android.widget.Checkable;

/**
 * Created by Murad Adnan on 2019-07-16.
 */
public class CustomTextInputLayout extends TextInputLayout implements Checkable {

    private boolean mChecked;

    private static final int[] CHECKED_STATE_SET = {android.R.attr.state_checked};


    public CustomTextInputLayout(Context context) {
        super(context);
    }

    public CustomTextInputLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomTextInputLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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

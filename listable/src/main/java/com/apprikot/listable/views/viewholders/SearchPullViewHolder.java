package com.apprikot.listable.views.viewholders;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.apprikot.listable.R;
import com.apprikot.listable.interfaces.Listable;
import com.apprikot.listable.listeners.OnItemClickListener;
import com.apprikot.listable.utils.ViewUtils;
import com.apprikot.listable.views.SearchItem;

public class SearchPullViewHolder extends BaseViewHolder {
    private EditText searchEditText;
    private ImageView clearImageView;
    private SearchItem searchItem;

    public SearchPullViewHolder(View itemView, OnItemClickListener.OnItemClickCallback onItemClickCallback) {
        super(itemView, onItemClickCallback);
        searchEditText = (EditText) find(R.id.etxt_search);
        clearImageView = (ImageView) find(R.id.img_cancel_search);
        searchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (searchItem.textEnteredListener != null) {
                        ViewUtils.hideKeyboard(searchEditText);
                        searchItem.textEnteredListener.onTextEntered(searchEditText.getText().toString());
                    }
                    handled = true;
                }
                return handled;
            }
        });
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchItem.textEnteredListener.onTextChanged(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        clearImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchEditText.setText("");
            }
        });
    }

    @Override
    public void draw(Listable listable) {
        super.draw(listable);
        searchItem = (SearchItem) listable;
        searchItem.searchEditText = searchEditText;
        if (searchItem.searchText != null) {
            searchEditText.setText(searchItem.searchText);
        }
        if (searchItem.focused) {
            searchEditText.requestFocus();
        }
//        attachClickListener(clearImageView);
    }
}
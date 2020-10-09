package com.apprikot.listable.components;

import android.os.Bundle;
import android.os.Message;

import com.apprikot.listable.interfaces.Listable;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class ModelUtils {
    public static List<Listable> getNoDuplicated(List<? extends Listable> items) {
        Set<Listable> set = new LinkedHashSet<>(items);
        return new ArrayList<>(set);
    }


    public static Message getMessage(String key, String message) {
        Bundle bundle = new Bundle();
        bundle.putString(key, message);
        Message msg = new Message();
        msg.setData(bundle);
        return msg;
    }
}
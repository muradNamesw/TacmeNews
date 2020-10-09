package com.apprikot.listable.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by administrator on 10/24/16.
 */

public class HolderClass implements Parcelable {

    public Class viewHolderClass;
    public int layoutResId;
    public boolean isFragment;
    public boolean isNotes;

    public HolderClass(int layoutResId) {
        this.layoutResId = layoutResId;
    }

    public HolderClass(Class viewHolderClass, int layoutResId) {
        this.viewHolderClass = viewHolderClass;
        this.layoutResId = layoutResId;
    }

    public HolderClass(Class viewHolderClass, int layoutResId, boolean isFragment) {
        this(viewHolderClass, layoutResId);
        this.isFragment = isFragment;
    }

    public HolderClass(Class viewHolderClass, int layoutResId, boolean isFragment, boolean isNotes) {
        this(viewHolderClass, layoutResId);
        this.isFragment = isFragment;
        this.isNotes = isNotes;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(this.viewHolderClass);
        dest.writeInt(this.layoutResId);
        dest.writeByte(this.isFragment ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isNotes ? (byte) 1 : (byte) 0);
    }

    protected HolderClass(Parcel in) {
        this.viewHolderClass = (Class) in.readSerializable();
        this.layoutResId = in.readInt();
        this.isFragment = in.readByte() != 0;
        this.isNotes = in.readByte() != 0;
    }

    public static final Parcelable.Creator<HolderClass> CREATOR = new Parcelable.Creator<HolderClass>() {
        @Override
        public HolderClass createFromParcel(Parcel source) {
            return new HolderClass(source);
        }

        @Override
        public HolderClass[] newArray(int size) {
            return new HolderClass[size];
        }
    };
}

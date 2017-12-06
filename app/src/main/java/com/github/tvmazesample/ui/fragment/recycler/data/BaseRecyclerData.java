package com.github.tvmazesample.ui.fragment.recycler.data;


import android.os.Parcelable;

/**
 * @author hadi
 */
public abstract class BaseRecyclerData implements Parcelable {
    public static final int MAX_SPAN = -1;

    public abstract int getViewType();

    public abstract int getSpan();
}

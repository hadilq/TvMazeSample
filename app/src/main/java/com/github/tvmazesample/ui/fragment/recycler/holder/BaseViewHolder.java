package com.github.tvmazesample.ui.fragment.recycler.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.github.tvmazesample.ui.fragment.recycler.data.BaseRecyclerData;

public abstract class BaseViewHolder<DATA extends BaseRecyclerData> extends RecyclerView.ViewHolder {

    private DATA mData;

    public BaseViewHolder(
            View itemView
    ) {
        super(itemView);
    }

    public void onBindView(DATA data) {
        mData = data;
    }


    public DATA getData() {
        return mData;
    }
}
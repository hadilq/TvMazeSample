package com.github.tvmazesample.ui.fragment.recycler;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.github.tvmazesample.di.Injector;
import com.github.tvmazesample.ui.fragment.recycler.data.BaseRecyclerData;
import com.github.tvmazesample.ui.fragment.recycler.holder.BaseViewHolder;
import com.github.tvmazesample.ui.fragment.recycler.holder.EmptyViewHolder;
import com.github.tvmazesample.ui.fragment.recycler.holder.ViewHolderFactory;
import com.github.tvmazesample.util.L;
import com.github.tvmazesample.util.UiUtil;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;
import junit.framework.Assert;

import javax.inject.Inject;
import java.util.ArrayList;

public class RecyclerListAdapter extends RecyclerView.Adapter {

    @Inject
    ViewHolderFactory mFactory;
    @Inject
    UiUtil mUiUtil;

    private ArrayList<BaseRecyclerData> mList = new ArrayList<>();

    RecyclerListAdapter() {
        Injector.appComponent().inject(this);
    }

    private final Subject<BaseViewHolder> mSubject = PublishSubject.create();

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);

        BaseViewHolder baseViewHolder = mFactory.create(viewType, view);

        if (baseViewHolder == null) {
            // Shouldn't happen
            L.w(this.getClass(), "baseViewHolder becomes null!");
            baseViewHolder = new EmptyViewHolder(new View(parent.getContext()));
        }
        mSubject.onNext(baseViewHolder);
        return baseViewHolder;
    }

    public Subject<BaseViewHolder> getSubject() {
        return mSubject;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof BaseViewHolder) {
            BaseViewHolder baseHolder = (BaseViewHolder) holder;
            final BaseRecyclerData data = mList.get(position);

            onBindData(baseHolder, data);
        } else {
            Assert.fail("All view holders must be extended of BaseViewHolder");
        }
    }

    @SuppressWarnings("unchecked")// The type is right by generic types
    private void onBindData(
            BaseViewHolder baseHolder,
            BaseRecyclerData data
    ) {
        baseHolder.onBindView(data);
    }

    public GridLayoutManager.SpanSizeLookup getSpanSizeLookup() {
        return new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int span = mList.get(position).getSpan();
                if (span == BaseRecyclerData.MAX_SPAN) {
                    span = mUiUtil.getScreenMaxSpannable();
                }
                return span;
            }
        };
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position).getViewType();
    }

    public ArrayList<BaseRecyclerData> getList() {
        return mList;
    }

    public boolean isEmpty() {
        return mList.isEmpty();
    }

}

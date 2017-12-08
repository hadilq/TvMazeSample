package com.github.tvmazesample.ui.fragment.recycler;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.github.tvmazesample.R;
import com.github.tvmazesample.ui.fragment.BaseContentFragment;
import com.github.tvmazesample.ui.fragment.BundleKey;
import com.github.tvmazesample.ui.fragment.recycler.data.BaseRecyclerData;
import com.github.tvmazesample.ui.fragment.recycler.data.TryAgainData;
import com.github.tvmazesample.ui.fragment.recycler.holder.BaseViewHolder;
import com.github.tvmazesample.ui.fragment.recycler.holder.TryAgainViewHolder;
import com.github.tvmazesample.ui.view.TryAgainView;
import com.github.tvmazesample.util.UiUtil;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseRecyclerFragment extends BaseContentFragment {

    @Inject
    UiUtil mUiUtil;

    @BindView(R.id.list)
    RecyclerView mRecyclerView;
    @BindView(R.id.try_again)
    TryAgainView mTryAgainView;

    private RecyclerListAdapter mAdapter;
    private TryAgainView.OnTryAgainListener mOnTryAgainListener;
    private GridLayoutManager mLayoutManager;
    private int mScrollPosition;
    private int mPage = 1;
    private boolean mLoading = true;
    private boolean mEndOfList = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.base_list_fragment, container, false);
        ButterKnife.bind(this, rootView);

        mTryAgainView.setExtraView(getEmptyView());
        mOnTryAgainListener = new TryAgainView.OnTryAgainListener() {
            @Override
            public void tryAgain() {
                refresh();
            }
        };
        mTryAgainView.setTryAgainListener(mOnTryAgainListener);

        mLayoutManager = new GridLayoutManager(getActivity(), getMaxSpanCount());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setOnScrollListener(new LoadingListener());
        mRecyclerView.addItemDecoration(provideItemDecor());

        mAdapter = new RecyclerListAdapter();
        mAdapter.getSubject().subscribe(new Consumer<BaseViewHolder>() {
            @Override
            public void accept(BaseViewHolder holder) throws Exception {
                if (holder instanceof TryAgainViewHolder) {
                    ((TryAgainViewHolder) holder).clickStream().subscribe(getTryAgainObserver());
                }
            }
        });
        mLayoutManager.setSpanSizeLookup(mAdapter.getSpanSizeLookup());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        return rootView;
    }

    protected RecyclerView.ItemDecoration provideItemDecor() {
        return new RecyclerViewItemDecoration();
    }

    private Consumer<? super TryAgainData> getTryAgainObserver() {
        return new Consumer<TryAgainData>() {
            @Override
            public void accept(TryAgainData tryAgainData) throws Exception {
                mOnTryAgainListener.tryAgain();
            }
        };
    }

    protected Observable<BaseViewHolder> getViewHolderObservable() {
        return mAdapter.getSubject();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mScrollPosition = getArguments().getInt(BundleKey.SCROLL_POSITION, 0);
        mPage = getArguments().getInt(BundleKey.PAGE, 1);
        provideDataAndStart();
    }

    @Override
    public void onDestroyView() {
        getArguments().putInt(BundleKey.SCROLL_POSITION, mLayoutManager.findFirstVisibleItemPosition());
        getArguments().putInt(BundleKey.PAGE, mPage);
        super.onDestroyView();
    }

    protected abstract int getMaxSpanCount();

    @Nullable
    protected abstract View getEmptyView();

    protected abstract void provide(int page);

    protected void refresh() {
        mPage = 1;
        provideDataAndStart();
    }

    private class LoadingListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrolled(
                RecyclerView recyclerView,
                int dx,
                int dy
        ) {
            if (dy > 0) { //check for scroll down
                checkIfReadyToProvide();
            }
        }
    }

    private void provideDataAndStart() {
        provideData();
        if (mAdapter.isEmpty()) {
            mTryAgainView.start();
        } else {
            mTryAgainView.finish();
            ArrayList<BaseRecyclerData> list = mAdapter.getList();
            if (!list.isEmpty() && list.get(list.size() - 1).getViewType() == TryAgainData.VIEW_TYPE) {
                list.remove(list.size() - 1);
                mAdapter.notifyItemRemoved(list.size() - 1);
            }
        }
    }

    private void provideData() {
        provide(mPage++);
    }

    private void checkIfReadyToProvide() {
        if (mLoading || mEndOfList) {
            return;
        }
        int totalItemCount = mLayoutManager.getItemCount();
        int visibleItemCount = mLayoutManager.getChildCount();
        int pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();

        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
            provideData();
        }
    }

    protected void handleLoading(boolean loading) {
        mLoading = loading;
    }

    protected void handleDataInserted(@NonNull List<BaseRecyclerData> tailList) {
        mRecyclerView.setVisibility(View.VISIBLE);

        final List<BaseRecyclerData> list = mAdapter.getList();
        if (!list.isEmpty() && list.get(list.size() - 1).getViewType() == TryAgainData.VIEW_TYPE) {
            list.remove(list.size() - 1);
            mAdapter.notifyItemRemoved(list.size());
        }
        int startSize = list.size();

        list.addAll(tailList);
        if (startSize < list.size()) {
            mAdapter.notifyItemRangeInserted(startSize, list.size() - 1);
        }

        onNotifyToContinue();
    }

    protected void handleEndOfList() {
        mEndOfList = true;

        onNotifyToContinue();
    }

    protected void handleError(String message) {
        List<BaseRecyclerData> list = mAdapter.getList();
        if (list.isEmpty()) {
            mRecyclerView.setVisibility(View.GONE);
            mTryAgainView.onError(message);
        } else {
            BaseRecyclerData data = list.get(list.size() - 1);
            if (data instanceof TryAgainData) {
                TryAgainData tryAgainData = (TryAgainData) data;
                tryAgainData.setErrorMessage(message);
                mAdapter.notifyItemChanged(list.size() - 1);
            } else {
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void onNotifyToContinue() {
        if (!mEndOfList) {
            mAdapter.getList().add(new TryAgainData(true, getMaxSpanCount()));
            mAdapter.notifyItemInserted(mAdapter.getList().size() - 1);
        } else {
            ArrayList<BaseRecyclerData> list = mAdapter.getList();
            if (!list.isEmpty() && list.get(list.size() - 1).getViewType() == TryAgainData.VIEW_TYPE) {
                list.remove(list.size() - 1);
                mAdapter.notifyItemRemoved(list.size());
            }
        }

        if (!mAdapter.isEmpty()) {
            mTryAgainView.finish();
        } else if (!mLoading) {
            mTryAgainView.showExtraView();
        }

        if (mScrollPosition > 0) {
            // No need of scroll position any more
            mScrollPosition = -1;
            mRecyclerView.post(new Runnable() {
                @Override
                public void run() {
                    mLayoutManager.scrollToPosition(mScrollPosition);
                }
            });
        }
    }
}

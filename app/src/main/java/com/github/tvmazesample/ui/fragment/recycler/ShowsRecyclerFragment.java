package com.github.tvmazesample.ui.fragment.recycler;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.github.tvmazesample.R;
import com.github.tvmazesample.api.dto.ShowDto;
import com.github.tvmazesample.di.Injector;
import com.github.tvmazesample.ui.fragment.BundleKey;
import com.github.tvmazesample.ui.fragment.recycler.data.BaseRecyclerData;
import com.github.tvmazesample.ui.fragment.recycler.data.ShowData;
import com.github.tvmazesample.ui.fragment.recycler.holder.BaseViewHolder;
import com.github.tvmazesample.ui.fragment.recycler.holder.ShowViewHolder;
import com.github.tvmazesample.util.L;
import com.github.tvmazesample.viewmodel.ShowsViewModel;
import com.github.tvmazesample.viewmodel.response.Response;
import com.github.tvmazesample.viewmodel.response.Status;
import io.reactivex.functions.Consumer;
import retrofit2.HttpException;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

public class ShowsRecyclerFragment extends BaseRecyclerFragment {

    private ShowsViewModel mViewModel;

    public static ShowsRecyclerFragment instantiate() {
        Bundle args = new Bundle();
        ShowsRecyclerFragment fragment = new ShowsRecyclerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Injector.appComponent().inject(this);
        mViewModel = ViewModelProviders.of(this).get(ShowsViewModel.class);

        setupObservers();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        getViewHolderObservable()
                .subscribe(new Consumer<BaseViewHolder>() {
                    @Override
                    public void accept(BaseViewHolder baseViewHolder) throws Exception {
                        if (baseViewHolder instanceof ShowViewHolder) {
                            ((ShowViewHolder) baseViewHolder).clickStream().subscribe(getClickOnAShowObserver());
                        }
                    }
                });
        return view;
    }

    @NonNull
    private Consumer<ShowData> getClickOnAShowObserver() {
        return new Consumer<ShowData>() {
            @Override
            public void accept(ShowData showData) throws Exception {

            }
        };
    }

    @Override
    protected void setupHeaderTitle() {
        getArguments().putString(BundleKey.HEADER_TITLE, getString(R.string.app_name));
    }

    @Override
    protected int getMaxSpanCount() {
        return mUiUtil.getScreenMaxSpannable();
    }

    @Nullable
    @Override
    protected View getEmptyView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.empty_list, (ViewGroup) getView(), false);
        ((TextView) view.findViewById(R.id.empty_message)).setText(R.string.shows_is_empty);
        return view;
    }

    @Override
    protected void provide(int page) {
        mViewModel.load(page);
    }

    private void setupObservers() {
        mViewModel.getLoadingStatus().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean loading) {
                if (loading != null) {
                    handleLoading(loading);
                }
            }
        });

        mViewModel.getResponse().observe(this, new Observer<Response<List<ShowDto>>>() {
            @Override
            public void onChanged(@NonNull Response<List<ShowDto>> listResponse) {
                if (listResponse.getStatus() == Status.SUCCESS) {
                    List<BaseRecyclerData> content = new ArrayList<>();
                    for (ShowDto show : listResponse.getData()) {
                        content.add(new ShowData(show));
                    }
                    handleDataInserted(content);
                } else {
                    if (listResponse.getError() instanceof HttpException) {
                        if (((HttpException) listResponse.getError()).response().code() == HttpURLConnection.HTTP_NOT_FOUND) {
                            handleEndOfList();
                        } else {
                            handleError(getString(R.string.loading_problem));
                        }
                    } else {
                        handleError(getString(R.string.loading_problem));
                        L.e(getClass(), "Error happened: ", listResponse.getError());
                    }
                }

            }
        });
    }
}

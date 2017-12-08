package com.github.tvmazesample.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import com.github.tvmazesample.api.dto.ShowSeasonDto;
import com.github.tvmazesample.di.Injector;
import com.github.tvmazesample.manager.ShowDetailsManager;
import com.github.tvmazesample.viewmodel.response.Response;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import javax.inject.Inject;
import java.util.List;

public class ShowDetailsViewModel extends BaseViewModel {

    @Inject
    ShowDetailsManager mShowDetailsManager;

    private final MutableLiveData<Response<ShowDetailsManager.Aggregator>> mResponse = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mLoadingStatus = new MutableLiveData<>();

    public ShowDetailsViewModel() {
        Injector.appComponent().inject(this);
    }

    public LiveData<Boolean> getLoadingStatus() {
        return mLoadingStatus;
    }

    public LiveData<Response<ShowDetailsManager.Aggregator>> getResponse() {
        return mResponse;
    }

    public void load(final long id) {
        mDisposable.add(mShowDetailsManager.getSeasons(id)
                .flatMap(new Function<List<ShowSeasonDto>, ObservableSource<ShowDetailsManager.Aggregator>>() {
                    @Override
                    public ObservableSource<ShowDetailsManager.Aggregator> apply(List<ShowSeasonDto> seasons) throws Exception {
                        return mShowDetailsManager.getShowEpisodes(id, seasons);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mLoadingStatus.setValue(true);
                    }
                })
                .doAfterTerminate(new Action() {
                    @Override
                    public void run() throws Exception {
                        mLoadingStatus.setValue(false);
                    }
                })
                .subscribe(new Consumer<ShowDetailsManager.Aggregator>() {
                    @Override
                    public void accept(ShowDetailsManager.Aggregator aggregator) throws Exception {
                        mResponse.setValue(new Response<>(aggregator));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mResponse.setValue(new Response<ShowDetailsManager.Aggregator>(throwable));
                    }
                }));
    }
}

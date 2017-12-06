package com.github.tvmazesample.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import com.github.tvmazesample.api.dto.ShowDto;
import com.github.tvmazesample.di.Injector;
import com.github.tvmazesample.manager.ShowsManager;
import com.github.tvmazesample.viewmodel.response.Response;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import javax.inject.Inject;
import java.util.List;

public class ShowsViewModel extends BaseViewModel {

    @Inject
    ShowsManager mShowsManager;

    private final MutableLiveData<Response<List<ShowDto>>> mResponse = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mLoadingStatus = new MutableLiveData<>();

    public ShowsViewModel() {
        Injector.appComponent().inject(this);
    }

    public MutableLiveData<Response<List<ShowDto>>> getResponse() {
        return mResponse;
    }

    public MutableLiveData<Boolean> getLoadingStatus() {
        return mLoadingStatus;
    }

    public void load(int page) {
        mDisposable.add(mShowsManager.getShows(page)
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
                .subscribe(new Consumer<List<ShowDto>>() {
                               @Override
                               public void accept(List<ShowDto> listResponse) throws Exception {
                                   mResponse.setValue(new Response<>(listResponse));
                               }
                           },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                mResponse.setValue(new Response<List<ShowDto>>(throwable));
                            }
                        }));
    }
}

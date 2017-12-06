package com.github.tvmazesample.viewmodel;

import android.arch.lifecycle.ViewModel;
import io.reactivex.disposables.CompositeDisposable;

public abstract class BaseViewModel extends ViewModel {

    protected final CompositeDisposable mDisposable = new CompositeDisposable();

    @Override
    protected void onCleared() {
        mDisposable.clear();
        super.onCleared();
    }

}

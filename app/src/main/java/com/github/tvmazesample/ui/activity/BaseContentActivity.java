package com.github.tvmazesample.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.github.tvmazesample.di.Injector;
import com.github.tvmazesample.util.UiUtil;

import javax.inject.Inject;

public abstract class BaseContentActivity extends AppCompatActivity {

    @Inject
    UiUtil mUiUtil;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Injector.appComponent().inject(this);
        mUiUtil.analyseScreenDimensions(this);
    }
}

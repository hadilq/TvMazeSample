package com.github.tvmazesample.di;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Looper;
import com.github.tvmazesample.ApplicationLauncher;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

/**
 * @author hadi
 */
@Module
public class AppModule {

    ApplicationLauncher mApplication;

    public AppModule(ApplicationLauncher application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    Application providesApplication() {
        return mApplication;
    }

    @Provides
    @Singleton
    ApplicationLauncher providesApplicationLauncher() {
        return mApplication;
    }

    @Provides
    @Singleton
    Context providesContext() {
        return mApplication;
    }

    @Provides
    @Singleton
    Resources providesResources() {
        return mApplication.getResources();
    }

    @Provides
    @Singleton
    Handler providesHandler() {
        return new Handler(Looper.getMainLooper());
    }

}
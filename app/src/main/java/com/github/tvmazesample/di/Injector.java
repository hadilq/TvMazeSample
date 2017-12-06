package com.github.tvmazesample.di;

import com.github.tvmazesample.ApplicationLauncher;

public class Injector {

    private static Injector sInjector;

    private AppComponent mAppComponent;

    private Injector() {
    }

    public void setupDagger(ApplicationLauncher applicationLauncher) {
        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(applicationLauncher))
                .build();
    }

    public static Injector getInstance() {
        if (sInjector == null) {
            synchronized (Injector.class) {
                if (sInjector == null) {
                    sInjector = new Injector();
                }
            }
        }
        return sInjector;
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }

    public void setAppComponent(AppComponent mAppComponent) {
        this.mAppComponent = mAppComponent;
    }

    public static AppComponent appComponent() {
        return getInstance().getAppComponent();
    }

    public static void setTestAppComponent(AppComponent testAppComponent) {
        getInstance().setAppComponent(testAppComponent);
    }
}

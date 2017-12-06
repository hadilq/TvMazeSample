package com.github.tvmazesample;

import android.app.Application;
import com.github.tvmazesample.di.Injector;
import com.github.tvmazesample.util.L;

/**
 * @author hadi
 */
public class ApplicationLauncher extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        L.i(getClass(), "Application created");
        initialize();
    }

    private void initialize() {
        Injector.getInstance().setupDagger(this);
    }

}

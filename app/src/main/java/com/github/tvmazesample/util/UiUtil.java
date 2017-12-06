package com.github.tvmazesample.util;

import android.util.DisplayMetrics;
import com.github.tvmazesample.R;
import com.github.tvmazesample.ui.activity.BaseContentActivity;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class UiUtil {

    private int mScreenHeight;
    private int mScreenWidth;
    private int mScreenMaxSpannable;
    private Double mGoldenRatio;

    @Inject
    UiUtil() {
    }

    public void analyseScreenDimensions(BaseContentActivity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        mScreenHeight = displayMetrics.heightPixels;
        mScreenWidth = displayMetrics.widthPixels;
        mScreenMaxSpannable = mScreenWidth / activity.getResources().getDimensionPixelOffset(R.dimen.min_span_width);
    }

    public int getScreenHeight() {
        return mScreenHeight;
    }

    public int getScreenWidth() {
        return mScreenWidth;
    }

    public int getScreenMaxSpannable() {
        return mScreenMaxSpannable;
    }

    public double getGoldenRatio() {
        if (mGoldenRatio == null) {
            mGoldenRatio = goldenRatio(1, 1);
        }
        return mGoldenRatio;
    }

    private double goldenRatio(double a, double b) {
        double epsilon = 0.00001;
        if(Math.abs((b / a) - ((a + b) / b)) < epsilon) {
            return ((a + b) / b);
        } else {
            return goldenRatio(b, a + b);
        }
    }
}

package com.github.tvmazesample.ui.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import com.github.tvmazesample.ui.activity.BaseContentActivity;

import java.security.SecureRandom;

public abstract class BaseContentFragment extends Fragment {
    private static final SecureRandom sSecureRandom = new SecureRandom();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupHeaderTitle();
    }

    public String getTagName() {
        String t = getArguments().getString(BundleKey.CONTENT_TAG);
        if (TextUtils.isEmpty(t)) {
            t = getClass().getSimpleName() + "_" + sSecureRandom.nextLong();
            getArguments().putString(BundleKey.CONTENT_TAG, t);
        }
        return t;
    }

    public String getHeaderTitle() {
        return getArguments().getString(BundleKey.HEADER_TITLE);
    }

    public boolean hasHomeAsUp() {
        return false;
    }

    protected abstract void setupHeaderTitle();

    public BackState onBackPressed() {
        return BackState.BACK_FRAGMENT;
    }

    public Drawable getCollapsingParallaxDrawable() {
        return null;
    }

    public View.OnClickListener getFabClickListener() {
        return null;
    }

    protected BaseContentActivity activity() {
        FragmentActivity activity = getActivity();
        if (activity instanceof BaseContentActivity) {
            return (BaseContentActivity) activity;
        }
        throw new RuntimeException("Activity is not main activity");
    }

    public enum BackState {
        CLOSE_APP,
        BACK_FRAGMENT,
        DO_NOTHING
    }
}

package com.github.tvmazesample.ui.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import com.github.tvmazesample.ui.activity.BaseContentActivity;

import java.security.SecureRandom;

public abstract class BaseContentFragment extends Fragment {
    private static final SecureRandom sSecureRandom = new SecureRandom();

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

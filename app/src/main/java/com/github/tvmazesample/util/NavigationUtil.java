package com.github.tvmazesample.util;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import com.github.tvmazesample.R;
import com.github.tvmazesample.ui.fragment.BaseContentFragment;
import junit.framework.Assert;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author hadi
 */
@Singleton
public class NavigationUtil {

    @Inject
    Context mContext;
    @Inject
    Resources mResources;
    @Inject
    Handler mHandler;

    private boolean animatingPopBackStack = false;

    @Inject
    public NavigationUtil() {
    }

    public void startContentFragment(
            @NonNull final FragmentManager fragmentManager,
            @NonNull final BaseContentFragment fragment
    ) {
        try {
            FragmentTransaction t = fragmentManager.beginTransaction();

            t.setCustomAnimations(R.anim.enter_from_right, 0);
            t.add(R.id.content, fragment, fragment.getTagName())
                    .addToBackStack(fragment.getTagName())
                    .commit();
        } catch (Exception e) {
            L.e(NavigationUtil.class.getClass(), "start content problem!", e);
        }
    }

    public void popBackStack(
            @NonNull final FragmentManager fragmentManager,
            @NonNull final BaseContentFragment fragment
    ) {
        Animation animation;

        animation = AnimationUtils.loadAnimation(mContext, R.anim.exit_to_right);
        animation.setStartOffset(0);
        View view = fragment.getView();
        if (view != null) {
            view.startAnimation(animation);
        }
        animatingPopBackStack = true;
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    fragmentManager.popBackStack();
                } catch (Exception e) {
                    L.e(NavigationUtil.class.getClass(), "Back problem!", e);
                }
                animatingPopBackStack = false;
            }
        }, mResources.getInteger(R.integer.fragment_transition_duration));
    }

    public boolean isAnimatingPopBackStack() {
        return animatingPopBackStack;
    }

    @Nullable
    public BaseContentFragment findTopFragment(
            @NonNull FragmentManager fragmentManager
    ) {

        Fragment fragment = fragmentManager.findFragmentById(R.id.content);
        if (fragment instanceof BaseContentFragment) {
            return (BaseContentFragment) fragment;
        }
        if (fragment != null) {
            Assert.fail("Fragment is not a content! It's " + fragment.getClass());
        }
        return null;
    }
}
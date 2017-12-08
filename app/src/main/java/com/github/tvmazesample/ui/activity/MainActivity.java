package com.github.tvmazesample.ui.activity;

import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.github.tvmazesample.R;
import com.github.tvmazesample.di.Injector;
import com.github.tvmazesample.ui.fragment.BaseContentFragment;
import com.github.tvmazesample.ui.fragment.recycler.ShowsRecyclerFragment;
import com.github.tvmazesample.util.NavigationUtil;

import javax.inject.Inject;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class MainActivity extends BaseContentActivity implements FragmentManager.OnBackStackChangedListener {

    @Inject
    NavigationUtil mNavigationUtil;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.coordinator)
    CoordinatorLayout mCoordinatorLayout;
    @BindView(R.id.appbar)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(R.id.header)
    ImageView mHeaderView;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    @BindView(R.id.scrim_top_down)
    View mScrimTopDown;
    @BindView(R.id.scrim)
    View mScrim;

    private AppBarLayout.LayoutParams mAppBarLP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        Injector.appComponent().inject(this);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mCollapsingToolbarLayout.setTitleEnabled(false);

        mAppBarLP = (AppBarLayout.LayoutParams) mCollapsingToolbarLayout.getLayoutParams();

        getSupportFragmentManager().addOnBackStackChangedListener(this);

        mFab.getDrawable().setColorFilter(getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_IN);

        mNavigationUtil.startContentFragment(
                getSupportFragmentManager(), ShowsRecyclerFragment.instantiate());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        if (mNavigationUtil.isAnimatingPopBackStack()) {
            return;
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        BaseContentFragment fragment = mNavigationUtil.findTopFragment(fragmentManager);
        if (fragment != null) {
            BaseContentFragment.BackState state = fragment.onBackPressed();
            switch (state) {
                case CLOSE_APP:
                    finish();
                    break;
                case BACK_FRAGMENT:
                    mNavigationUtil.popBackStack(fragmentManager, fragment);
                    break;
            }
        }
    }

    @Override
    public void onBackStackChanged() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        BaseContentFragment fragment = mNavigationUtil.findTopFragment(fragmentManager);
        if (fragment != null) {
            String title = fragment.getHeaderTitle();

            CoordinatorLayout.LayoutParams alp = (CoordinatorLayout.LayoutParams) mAppBarLayout.getLayoutParams();
            if (fragment.hasHomeAsUp()) {
                mToolbar.setTitle(title);
                mAppBarLayout.setExpanded(true, true);

                Drawable parallaxDrawable = fragment.getCollapsingParallaxDrawable();
                if (parallaxDrawable != null) {
                    mHeaderView.setImageDrawable(parallaxDrawable);
                    mHeaderView.setVisibility(VISIBLE);
                    mScrim.setVisibility(VISIBLE);
                    mScrimTopDown.setVisibility(VISIBLE);

                    View.OnClickListener listener = fragment.getFabClickListener();
                    if (listener != null) {
                        mFab.setOnClickListener(listener);
                        mFab.setVisibility(VISIBLE);
                    }
                }

                alp.height = Math.min((int) (mUiUtil.getScreenHeight() * .65),
                        getResources().getDimensionPixelSize(R.dimen.appbar_header_height_expanded));
                mAppBarLP.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL |
                        AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED);

                //noinspection ConstantConditions
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.transparent)));
                getSupportActionBar().setDisplayShowTitleEnabled(false);

                setupStatusBar(R.color.transparent);
            } else {
                mToolbar.setTitle(title);
                mAppBarLayout.setExpanded(false, false);

                mHeaderView.setVisibility(GONE);
                mScrim.setVisibility(GONE);
                mScrimTopDown.setVisibility(GONE);
                mFab.setVisibility(GONE);

                alp.height = getResources().getDimensionPixelSize(R.dimen.appbar_header_height_collapsed);
                mAppBarLP.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS);

                //noinspection ConstantConditions
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                getSupportActionBar().setDisplayShowHomeEnabled(false);
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimaryDark)));
                getSupportActionBar().setDisplayShowTitleEnabled(true);
                getSupportActionBar().setTitle(title);

                setupStatusBar(R.color.colorPrimaryDark);
            }

            mAppBarLayout.setLayoutParams(alp);
        }
    }

    private void setupStatusBar(@ColorRes int dynamicColor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(dynamicColor));
        }
    }

    public ImageView getHeaderView() {
        return mHeaderView;
    }
}

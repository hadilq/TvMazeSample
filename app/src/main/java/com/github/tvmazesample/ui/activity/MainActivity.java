package com.github.tvmazesample.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.github.tvmazesample.R;
import com.github.tvmazesample.di.Injector;
import com.github.tvmazesample.ui.fragment.BaseContentFragment;
import com.github.tvmazesample.ui.fragment.recycler.ShowsRecyclerFragment;
import com.github.tvmazesample.util.NavigationUtil;

import javax.inject.Inject;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class MainActivity extends BaseContentActivity implements FragmentManager.OnBackStackChangedListener {

    @Inject
    NavigationUtil mNavigationUtil;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Injector.appComponent().inject(this);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mNavigationUtil.startContentFragment(
                getSupportFragmentManager(), ShowsRecyclerFragment.instantiate());
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
            mToolbar.setTitle(fragment.getHeaderTitle());

            if (fragment.hasHomeAsUp()) {
                //noinspection ConstantConditions
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                mToolbar.setVisibility(VISIBLE);
                mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();
                    }
                });
            } else {
                //noinspection ConstantConditions
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                mToolbar.setVisibility(INVISIBLE);
            }
        }
    }
}

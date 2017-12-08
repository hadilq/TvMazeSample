package com.github.tvmazesample.ui.fragment.dialog;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.github.tvmazesample.R;
import com.github.tvmazesample.ui.fragment.BundleKey;

/**
 * @author hadi
 */
public class ProgressDialogFragment extends BaseDialogFragment {

    @BindView(R.id.title)
    TextView mTitleView;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    public static ProgressDialogFragment instantiate(@Nullable String title) {
        Bundle bundle = new Bundle();
        bundle.putString(BundleKey.HEADER_TITLE, title);
        ProgressDialogFragment fragment = new ProgressDialogFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.dialog_progress, container, false);
        ButterKnife.bind(this, view);

        mProgressBar.getIndeterminateDrawable().setColorFilter(
                getContext().getResources().getColor(R.color.progress_bar), PorterDuff.Mode.SRC_IN);

        String title = getArguments().getString(BundleKey.HEADER_TITLE);
        if (!TextUtils.isEmpty(title)) {
            mTitleView.setText(title);
        }
        return view;
    }
}

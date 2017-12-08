package com.github.tvmazesample.ui.fragment.recycler.holder;

import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.github.tvmazesample.R;
import com.github.tvmazesample.ui.fragment.recycler.data.TitleData;

public class TitleViewHolder extends BaseViewHolder<TitleData> {

    @BindView(R.id.title)
    TextView mTitleView;

    public TitleViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void onBindView(TitleData data) {
        super.onBindView(data);
        mTitleView.setText(data.getTitle());
    }
}

package com.github.tvmazesample.ui.fragment.recycler.holder;

import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.github.tvmazesample.R;
import com.github.tvmazesample.ui.fragment.recycler.data.ShowDescriptionData;

import java.util.Locale;

public class ShowDescriptionViewHolder extends BaseViewHolder<ShowDescriptionData> {

    @BindView(R.id.title)
    TextView mTitleView;
    @BindView(R.id.rating)
    TextView mRatingView;

    ShowDescriptionViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void onBindView(ShowDescriptionData data) {
        super.onBindView(data);
        mTitleView.setText(data.getTitle());
        mRatingView.setText(String.format(Locale.US, "%tF \u26AB %d min \u26AB %.1f/10", data.getPremiered(), data.getRuntime(), data.getRating().getAverage()));
    }
}

package com.github.tvmazesample.ui.fragment.recycler.holder;

import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.github.tvmazesample.R;
import com.github.tvmazesample.ui.fragment.recycler.data.SeasonData;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

public class SeasonViewHolder extends BaseViewHolder<SeasonData> {

    @BindView(R.id.title)
    TextView mTitleView;
    @BindView(R.id.details)
    TextView mDetailsView;
    @BindView(R.id.summary)
    TextView mSummaryView;

    SeasonViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void onBindView(SeasonData data) {
        super.onBindView(data);
        if (TextUtils.isEmpty(data.getName()) && TextUtils.isEmpty(data.getSummary()) &&
                (data.getPremiereDate() == null || data.getEndDate() == null)) {
            itemView.setVisibility(View.GONE);
        } else {
            itemView.setVisibility(View.VISIBLE);
        }
        if (!TextUtils.isEmpty(data.getName())) {
            mTitleView.setText(data.getName());
        } else {
            mTitleView.setText(itemView.getContext().getString(R.string.season_format, data.getNumber()));
        }
        if (data.getPremiereDate() != null && data.getEndDate() != null) {
            mDetailsView.setText(String.format("%tF . %tF", data.getPremiereDate(), data.getEndDate()));
            mDetailsView.setVisibility(View.VISIBLE);
        } else {
            mDetailsView.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(data.getSummary())) {
            mSummaryView.setText(Html.fromHtml(data.getSummary()));
            mSummaryView.setVisibility(View.VISIBLE);
        } else {
            mSummaryView.setVisibility(View.GONE);
        }
    }

    public Observable<SeasonViewHolder> clickStream() {
        return Observable.create(new ObservableOnSubscribe<SeasonViewHolder>() {
            @Override
            public void subscribe(final ObservableEmitter<SeasonViewHolder> e) throws Exception {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!e.isDisposed() && getData() != null) {
                            e.onNext(SeasonViewHolder.this);
                        }
                    }
                });
            }
        });
    }
}

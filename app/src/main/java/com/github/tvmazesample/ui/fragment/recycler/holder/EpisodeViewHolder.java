package com.github.tvmazesample.ui.fragment.recycler.holder;

import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.github.tvmazesample.R;
import com.github.tvmazesample.ui.fragment.recycler.data.EpisodeData;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

import java.util.Locale;

public class EpisodeViewHolder extends BaseViewHolder<EpisodeData> {

    @BindView(R.id.season)
    TextView mSeasonView;
    @BindView(R.id.title)
    TextView mTitleView;
    @BindView(R.id.summary)
    TextView mSummaryView;

    EpisodeViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void onBindView(EpisodeData data) {
        super.onBindView(data);
        if (TextUtils.isEmpty(data.getName()) && TextUtils.isEmpty(data.getSummary())) {
            itemView.setVisibility(View.GONE);
        } else {
            itemView.setVisibility(View.VISIBLE);
        }
        mSeasonView.setText(String.format(Locale.UK, "%d", data.getSeason()));
        if (!TextUtils.isEmpty(data.getName())) {
            mTitleView.setText(data.getName());
            mTitleView.setVisibility(View.VISIBLE);
        } else {
            mTitleView.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(data.getSummary())) {
            mSummaryView.setText(Html.fromHtml(data.getSummary()));
            mSummaryView.setVisibility(View.VISIBLE);
        } else {
            mSummaryView.setVisibility(View.GONE);
        }
    }

    public Observable<EpisodeViewHolder> clickStream() {
        return Observable.create(new ObservableOnSubscribe<EpisodeViewHolder>() {
            @Override
            public void subscribe(final ObservableEmitter<EpisodeViewHolder> e) throws Exception {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!e.isDisposed() && getData() != null) {
                            e.onNext(EpisodeViewHolder.this);
                        }
                    }
                });
            }
        });
    }
}

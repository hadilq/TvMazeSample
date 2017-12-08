package com.github.tvmazesample.ui.fragment.recycler.holder;

import android.graphics.PorterDuff;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.github.tvmazesample.R;
import com.github.tvmazesample.ui.fragment.recycler.data.SummaryData;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

public class SummaryViewHolder extends BaseViewHolder<SummaryData> {

    @BindView(R.id.information)
    ImageView mInformationView;
    @BindView(R.id.summary)
    TextView mSummaryView;

    SummaryViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mInformationView.getDrawable().setColorFilter(
                itemView.getContext().getResources().getColor(R.color.color_clicked), PorterDuff.Mode.SRC_IN);
    }

    @Override
    public void onBindView(SummaryData data) {
        super.onBindView(data);
        if (!TextUtils.isEmpty(data.getSummary())) {
            mSummaryView.setText(Html.fromHtml(data.getSummary()));
            mSummaryView.setVisibility(View.VISIBLE);
        } else {
            mSummaryView.setVisibility(View.GONE);
        }
    }

    public Observable<SummaryViewHolder> clickStream() {
        return Observable.create(new ObservableOnSubscribe<SummaryViewHolder>() {
            @Override
            public void subscribe(final ObservableEmitter<SummaryViewHolder> e) throws Exception {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!e.isDisposed() && getData() != null) {
                            e.onNext(SummaryViewHolder.this);
                        }
                    }
                });
            }
        });
    }
}

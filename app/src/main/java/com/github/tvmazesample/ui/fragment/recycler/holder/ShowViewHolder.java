package com.github.tvmazesample.ui.fragment.recycler.holder;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.github.tvmazesample.R;
import com.github.tvmazesample.di.Injector;
import com.github.tvmazesample.ui.fragment.recycler.data.ShowData;
import com.github.tvmazesample.ui.view.TryAgainView;
import com.github.tvmazesample.util.UiUtil;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

import javax.inject.Inject;

public class ShowViewHolder extends BaseViewHolder<ShowData> {

    @Inject
    UiUtil mUiUtil;

    @BindView(R.id.layout)
    ViewGroup mLayout;
    @BindView(R.id.image_layout)
    ViewGroup mImageLayout;
    @BindView(R.id.title)
    TextView mTitleView;
    @BindView(R.id.medium_image)
    ImageView mMediumView;
    @BindView(R.id.try_again)
    TryAgainView mTryAgainView;

    ShowViewHolder(View itemView) {
        super(itemView);
        Injector.appComponent().inject(this);
        ButterKnife.bind(this, itemView);

        mTryAgainView.setTryAgainListener(new TryAgainView.OnTryAgainListener() {
            @Override
            public void tryAgain() {
                if (getData() != null) {
                    retry();
                }

            }
        });

        setupDimensions();
    }

    @Override
    public void onBindView(final ShowData data) {
        super.onBindView(data);
        if (!TextUtils.isEmpty(data.getShowDto().getName())) {
            mTitleView.setText(data.getShowDto().getName());
        } else {
            mTitleView.setText("");
        }
        retry();
    }

    private void retry() {
        mTryAgainView.start();
        Picasso.with(itemView.getContext())
                .load(getData().getShowDto().getImage().getMedium())
                .into(mMediumView, new Callback() {
                    @Override
                    public void onSuccess() {
                        mTryAgainView.finish();
                    }

                    @Override
                    public void onError() {
                        mTryAgainView.onError(itemView.getContext().getString(R.string.loading_problem));
                    }
                });
    }

    private void setupDimensions() {
        ViewGroup.LayoutParams mp = mMediumView.getLayoutParams();
        ViewGroup.LayoutParams ip = mImageLayout.getLayoutParams();
        ip.width = mp.width = mUiUtil.getScreenWidth() / mUiUtil.getScreenMaxSpannable();
        ip.height = mp.height = (int) (mp.width * mUiUtil.getGoldenRatio() * 0.87);
        mMediumView.setLayoutParams(mp);
        mImageLayout.setLayoutParams(ip);
    }

    public ImageView getMediumView() {
        return mMediumView;
    }

    public Observable<ShowViewHolder> clickStream() {
        return Observable.create(new ObservableOnSubscribe<ShowViewHolder>() {
            @Override
            public void subscribe(final ObservableEmitter<ShowViewHolder> e) throws Exception {
                mLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!e.isDisposed() && getData() != null) {
                            e.onNext(ShowViewHolder.this);
                        }
                    }
                });
            }
        });
    }
}

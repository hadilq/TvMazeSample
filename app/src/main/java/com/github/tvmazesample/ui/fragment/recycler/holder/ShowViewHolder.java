package com.github.tvmazesample.ui.fragment.recycler.holder;

import android.graphics.PorterDuff;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.github.tvmazesample.R;
import com.github.tvmazesample.di.Injector;
import com.github.tvmazesample.ui.fragment.recycler.data.ShowData;
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
    @BindView(R.id.medium_image)
    ImageView mMediumView;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    ShowViewHolder(View itemView) {
        super(itemView);
        Injector.appComponent().inject(this);
        ButterKnife.bind(this, itemView);

        mProgressBar.getIndeterminateDrawable().setColorFilter(
                itemView.getContext().getResources().getColor(R.color.progress_bar), PorterDuff.Mode.SRC_IN);
        setupDimensions();
    }

    @Override
    public void onBindView(final ShowData data) {
        super.onBindView(data);
        mProgressBar.setVisibility(View.VISIBLE);

        Picasso.with(itemView.getContext())
                .load(data.getShowDto().getImage().getMedium())
                .into(mMediumView, new Callback() {
                    @Override
                    public void onSuccess() {
                        mProgressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        mProgressBar.setVisibility(View.GONE);
                    }
                });
    }

    private void setupDimensions() {
        ViewGroup.LayoutParams mp = mMediumView.getLayoutParams();
        mp.width = mUiUtil.getScreenWidth() / mUiUtil.getScreenMaxSpannable();
        mp.height = (int) (mp.width * mUiUtil.getGoldenRatio() * 0.87);
        mMediumView.setLayoutParams(mp);
    }

    public Observable<ShowData> clickStream() {
        return Observable.create(new ObservableOnSubscribe<ShowData>() {
            @Override
            public void subscribe(final ObservableEmitter<ShowData> e) throws Exception {
                mLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!e.isDisposed() && getData() != null) {
                            e.onNext(getData());
                        }
                    }
                });
            }
        });
    }
}

package com.github.tvmazesample.ui.fragment.recycler.holder;

import android.text.TextUtils;
import android.view.View;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.github.tvmazesample.R;
import com.github.tvmazesample.ui.fragment.recycler.data.TryAgainData;
import com.github.tvmazesample.ui.view.TryAgainView;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

public class TryAgainViewHolder extends BaseViewHolder<TryAgainData> {

    @BindView(R.id.try_again_view)
    TryAgainView mTryAgainView;

    TryAgainViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void onBindView(TryAgainData data) {
        super.onBindView(data);
        if (data.start()) {
            mTryAgainView.start();
        } else if (!TextUtils.isEmpty(data.getErrorMessage())) {
            mTryAgainView.onError(data.getErrorMessage());
        }
    }

    public Observable<TryAgainData> clickStream() {
        return Observable.create(new ObservableOnSubscribe<TryAgainData>() {
            @Override
            public void subscribe(final ObservableEmitter<TryAgainData> e) throws Exception {
                mTryAgainView.setTryAgainListener(new TryAgainView.OnTryAgainListener() {
                    @Override
                    public void tryAgain() {
                        if (!e.isDisposed() && getData() != null) {
                            e.onNext(getData());
                        }
                    }
                });

            }
        });
    }
}

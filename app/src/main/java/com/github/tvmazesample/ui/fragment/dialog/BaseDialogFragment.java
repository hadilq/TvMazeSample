package com.github.tvmazesample.ui.fragment.dialog;

import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;
import android.view.ViewGroup;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * @author hadi
 */
public abstract class BaseDialogFragment extends DialogFragment {

    private Subject<BaseOnDialogResultEvent> mSubject = PublishSubject.create();

    @Override
    public void onStart() {
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        super.onStart();
    }

    protected void sendEvent(BaseOnDialogResultEvent event) {
        mSubject.onNext(event);
    }

    public Observable<BaseOnDialogResultEvent> getObservable() {
        return mSubject;
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        sendEvent(new BaseOnDialogResultEvent(getTag(), DialogResult.CANCEL));
        super.onCancel(dialog);
    }

    public static class BaseOnDialogResultEvent {
        private DialogResult result;

        public BaseOnDialogResultEvent(String tag, DialogResult result) {
            this.result = result;
        }

        public DialogResult getResult() {
            return result;
        }
    }

    public enum DialogResult {
        COMMIT, NEUTRAL, CANCEL
    }

}

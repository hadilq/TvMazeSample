package com.github.tvmazesample.ui.view;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

public class TopCropImageView extends AppCompatImageView {

    public TopCropImageView(Context context) {
        super(context);
        init();
    }

    public TopCropImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TopCropImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        recomputeImgMatrix();
    }

    @Override
    protected boolean setFrame(int l, int t, int r, int b) {
        recomputeImgMatrix();
        return super.setFrame(l, t, r, b);
    }

    private void init() {
        setScaleType(ScaleType.MATRIX);
    }

    private void recomputeImgMatrix() {

        final Drawable drawable = getDrawable();
        if (drawable == null) {
            return;
        }

        final Matrix matrix = getImageMatrix();

        float scaleFactor = getWidth() / (float) drawable.getIntrinsicWidth();
        matrix.setScale(scaleFactor, scaleFactor, 0, drawable.getIntrinsicHeight() / 4);
        setImageMatrix(matrix);
    }
}
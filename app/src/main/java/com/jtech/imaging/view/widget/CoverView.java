package com.jtech.imaging.view.widget;

import android.animation.Animator;
import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

/**
 * 覆盖层视图(也是闲的蛋疼，写这种东西)
 * Created by jianghan on 2016/9/28.
 */

public class CoverView extends AppCompatImageView {

    private static final int COVER_ALPHA_DURATION = 350;

    private OnCoverCancelListener onCoverCancelListener;
    private boolean isHiding, isShowing;

    public CoverView(Context context) {
        this(context, null);
    }

    public CoverView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CoverView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //不可视
        setVisibility(GONE);
        //alpha为0
        setAlpha(0f);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        hideContentCover();
        if (null != onCoverCancelListener) {
            onCoverCancelListener.onCancel();
        }
        return true;
    }

    public void setOnCoverCancelListener(OnCoverCancelListener onCoverCancelListener) {
        this.onCoverCancelListener = onCoverCancelListener;
    }

    /**
     * 显示覆盖层
     */
    public void showContentCover() {
        if (!isShowing) {
            animate()
                    .alpha(1f)
                    .setDuration(COVER_ALPHA_DURATION)
                    .setInterpolator(new DecelerateInterpolator())
                    .setListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            setVisibility(View.VISIBLE);
                            isShowing = true;
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            setAlpha(1f);
                            isShowing = false;
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    })
                    .start();
        }
    }

    /**
     * 隐藏覆盖层
     */
    public void hideContentCover() {
        if (!isHiding) {
            animate()
                    .alpha(0f)
                    .setDuration(COVER_ALPHA_DURATION)
                    .setInterpolator(new DecelerateInterpolator())
                    .setListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            isHiding = true;
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            isHiding = false;
                            setVisibility(View.GONE);
                            setAlpha(0f);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {
                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {
                        }
                    })
                    .start();
        }
    }

    /**
     * 覆盖物事件
     */
    public interface OnCoverCancelListener {
        void onCancel();
    }
}
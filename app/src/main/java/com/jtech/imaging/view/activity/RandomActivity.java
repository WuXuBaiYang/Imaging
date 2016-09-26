package com.jtech.imaging.view.activity;

import android.animation.Animator;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;

import com.jakewharton.rxbinding.view.RxView;
import com.jtech.imaging.R;
import com.jtech.imaging.contract.RandomContract;
import com.jtech.imaging.model.PhotoModel;
import com.jtech.imaging.presenter.RandomPresenter;
import com.jtech.imaging.strategy.PhotoLoadStrategy;
import com.jtechlib.Util.DeviceUtils;
import com.jtechlib.Util.ImageUtils;
import com.jtechlib.view.activity.BaseActivity;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.OnTouch;
import rx.functions.Action1;

/**
 * 随机页面
 * Created by jianghan on 2016/9/23.
 */
public class RandomActivity extends BaseActivity implements RandomContract.View {

    private static final int FAB_ANIMATION_ANGLE = 90;
    private static final long FAB_ANIMATION_DURATION = 500;
    private static final long IMAGE_SHOW_ANIMATION_DURATION = 800;

    @Bind(R.id.imageview_random)
    ImageView imageView;
    @Bind(R.id.fab)
    FloatingActionButton floatingActionButton;
    @Bind(R.id.content)
    CoordinatorLayout content;

    private int maxHeight, screenWidth;
    private RandomContract.Presenter presenter;

    @Override
    protected void initVariables(Bundle bundle) {
        //绑定P类
        this.presenter = new RandomPresenter(this);
        //获取屏幕宽度
        this.screenWidth = DeviceUtils.getScreenWidth(getActivity());
        //计算最大高度
        this.maxHeight = screenWidth / 3 * 2;
    }

    @Override
    protected void initViews(Bundle bundle) {
        setContentView(R.layout.activity_random);
        //设置fab的点击事件
        RxView.clicks(floatingActionButton)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(new FabClick());
    }

    @Override
    protected void loadData() {
        floatingActionButton.postDelayed(new Runnable() {
            @Override
            public void run() {
                floatingActionButton.performClick();
            }
        }, FAB_ANIMATION_DURATION);
    }

    @Override
    public void success(final PhotoModel photoModel) {
        //计算图片高度
        final int imageHeight = Math.min(photoModel.getHeight(), maxHeight);
        //设置图片的高度(最高不超过屏幕高度的三分之二)
        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
        layoutParams.width = screenWidth;
        layoutParams.height = imageHeight;
        imageView.setLayoutParams(layoutParams);
        //如果是首次加载则显示动画，设置背景色
        if (null == imageView.getDrawable()) {
            //设置背景颜色
            imageView.setBackgroundColor(Color.parseColor(photoModel.getColor()));
            //显示图片动画
            startImageShowAnimation(imageHeight);
        }
        //记录时间
        final long currentTime = System.currentTimeMillis();
        //显示图片
        String imageUrl = PhotoLoadStrategy.getUrl(getActivity(), photoModel.getUrls().getRaw(), screenWidth);
        ImageUtils.requestImage(getActivity(), imageUrl, new Action1<Bitmap>() {
            @Override
            public void call(Bitmap bitmap) {
                if (null != bitmap) {
                    imageView.setImageBitmap(bitmap);
                    //关闭fab动画
                    floatingActionButton.setEnabled(true);
                    //如果时间超过动画时长，则显示动画
                    if ((System.currentTimeMillis() - currentTime) > IMAGE_SHOW_ANIMATION_DURATION) {
                        startImageShowAnimation(imageHeight);
                    }
                } else {
                    fail("ImageLoadError");
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                fail(throwable.getMessage());
            }
        });
    }

    @Override
    public void fail(String message) {
        //取消fab动画
        floatingActionButton.setEnabled(true);
        //弹出错误消息
        Snackbar.make(content, message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //关闭动画
        floatingActionButton.setEnabled(true);
        //关闭动画
        ActivityCompat.finishAfterTransition(RandomActivity.this);
    }

    /**
     * 背景触摸事件
     *
     * @return
     */
    @OnTouch(R.id.content)
    boolean onContentTouch() {
        onBackPressed();
        return true;
    }

    /**
     * 图片显示时的动画
     *
     * @param imageHeight
     */
    private void startImageShowAnimation(int imageHeight) {
        //开启动画
        int startRadius = 0;
        int endRadius = Math.max(imageHeight, screenWidth);
        Animator animator = ViewAnimationUtils
                .createCircularReveal(imageView, screenWidth / 2, imageHeight / 2, startRadius, endRadius);
        animator.setDuration(IMAGE_SHOW_ANIMATION_DURATION)
                .setInterpolator(new DecelerateInterpolator());
        animator.start();
    }

    /**
     * 加载动画
     */
    private void startLoadAnimation() {
        if (!floatingActionButton.isEnabled()) {
            floatingActionButton.animate()
                    .rotationBy(FAB_ANIMATION_ANGLE)
                    .setDuration(FAB_ANIMATION_DURATION)
                    .setInterpolator(new OvershootInterpolator())
                    .setListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            startLoadAnimation();
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {
                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {
                        }
                    })
                    .start();
        } else {
            //取消动画的时候状态还原
            floatingActionButton.animate()
                    .rotation(0)
                    .setDuration(FAB_ANIMATION_DURATION)
                    .setInterpolator(new DecelerateInterpolator())
                    .start();
        }
    }

    /**
     * fab的点击事件
     */
    private class FabClick implements Action1<Void> {
        @Override
        public void call(Void aVoid) {
            //设置为不可用
            floatingActionButton.setEnabled(false);
            //开启加载动画
            startLoadAnimation();
            //请求数据
            presenter.getRandomPhoto("", "", "", "", "", 0, 0, "landscape");
        }
    }
}
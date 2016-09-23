package com.jtech.imaging.view.activity;

import android.animation.Animator;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;

import com.jtech.imaging.R;
import com.jtech.imaging.contract.RandomContract;
import com.jtech.imaging.model.PhotoModel;
import com.jtech.imaging.presenter.RandomPresenter;
import com.jtechlib.Util.DeviceUtils;
import com.jtechlib.view.activity.BaseActivity;

import butterknife.Bind;

/**
 * 随机页面
 * Created by jianghan on 2016/9/23.
 */
public class RandomActivity extends BaseActivity implements RandomContract.View {

    private static final long FAB_ANIMATION_DURATION = 500;
    private static final int FAB_ANIMATION_ANGLE = 90;

    @Bind(R.id.imageview_random)
    ImageView imageView;
    @Bind(R.id.fab)
    FloatingActionButton floatingActionButton;
    @Bind(R.id.content)
    CoordinatorLayout content;

    private RandomContract.Presenter presenter;

    @Override
    protected void initVariables(Bundle bundle) {
        //绑定P类
        presenter = new RandomPresenter(this);
    }

    @Override
    protected void initViews(Bundle bundle) {
        setContentView(R.layout.activity_random);
        //设置图片的默认高度
        int screenHeight = DeviceUtils.getScreenHeight(getActivity());
        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
        layoutParams.height = screenHeight / 5 * 2;
        imageView.setLayoutParams(layoutParams);
    }

    /**
     * 加载动画
     */
    private void startLoadAnimation() {
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
                        //取消动画的时候状态还原
                        floatingActionButton.animate()
                                .rotation(0)
                                .setDuration(FAB_ANIMATION_DURATION)
                                .setInterpolator(new OvershootInterpolator())
                                .start();
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {
                    }
                })
                .start();
    }

    @Override
    protected void loadData() {
        //开启加载动画
        startLoadAnimation();
        //请求数据
        presenter.getRandomPhoto("", "", "", "", "", 0, 0, "");
    }

    @Override
    public void success(PhotoModel photoModel) {
        //设置图片的高度(最高不超过屏幕高度的三分之二)
        int screenHeight = DeviceUtils.getScreenHeight(getActivity());
        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
        int maxHeight = screenHeight / 3 * 2;
        layoutParams.height = photoModel.getHeight() > maxHeight ? maxHeight : photoModel.getHeight();
        imageView.setLayoutParams(layoutParams);

        //取消fab动画
//        floatingActionButton.animate().cancel();
        //显示图片
//        ImageUtils.showImage(getActivity(), photoModel.getUrls().getCustom(), imageView);
    }

    @Override
    public void fail(String message) {
        //取消fab动画
//        floatingActionButton.animate().cancel();
        //弹出错误消息
        Snackbar.make(content, message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //取消fab动画
//        floatingActionButton.animate().cancel();
        //关闭动画
        ActivityCompat.finishAfterTransition(this);
    }
}
package com.jtech.imaging.view.activity;

import android.animation.Animator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jtech.imaging.R;
import com.jtech.imaging.cache.OauthCache;
import com.jtech.imaging.contract.WelcomeContract;
import com.jtech.imaging.model.OauthModel;
import com.jtech.imaging.model.PhotoModel;
import com.jtech.imaging.presenter.WelcomePresenter;
import com.jtech.imaging.strategy.PhotoLoadStrategy;
import com.jtech.imaging.util.Utils;
import com.jtech.imaging.view.widget.LoadingView;
import com.jtech.imaging.view.widget.RxCompat;
import com.jtechlib.Util.DeviceUtils;
import com.jtechlib.Util.ImageUtils;
import com.jtechlib.view.activity.BaseActivity;

import butterknife.Bind;
import butterknife.OnClick;
import rx.functions.Action1;

/**
 * 欢迎页，首屏
 * Created by jianghan on 2016/9/20.
 */
public class WelcomeActivity extends BaseActivity implements WelcomeContract.View {

    private static final long INFO_ANIMATION_DURATION = 300;

    @Bind(R.id.fab)
    FloatingActionButton floatingActionButton;
    @Bind(R.id.imageview_welcome)
    AppCompatImageView imageView;
    @Bind(R.id.content_welcome_info)
    LinearLayout contentInfo;
    @Bind(R.id.textview_welcome_info)
    TextView textviewInfo;
    @Bind(R.id.contentloading)
    LoadingView loadingView;

    private WelcomeContract.Presenter presenter;

    @Override
    protected void initVariables(Bundle bundle) {
        //绑定VP类
        presenter = new WelcomePresenter(this);
        //暂时使用已存在数据，不经过授权登陆
        if (!OauthCache.hasOauthModel()) {
            OauthModel oauthModel = new OauthModel();
            oauthModel.setAccessToken("da20b124d815a82ef0cb79226e991559e6e4c9cdf411fcef4e51acc718c0e44a");
            oauthModel.setCreatedAt(1473643598);
            oauthModel.setScope("public read_user write_user read_photos write_photos write_likes read_collections write_collections");
            oauthModel.setTokenType("bearer");
            OauthCache.get().setOauthModel(oauthModel);
        }
    }

    @Override
    protected void initViews(Bundle bundle) {
        setContentView(R.layout.activity_welcome);
        //设置fab的点击事件
        RxCompat.clickThrottleFirst(floatingActionButton, new FabClick());
    }

    @Override
    protected void loadData() {
        //获取欢迎页图片
        int screenWidth = DeviceUtils.getScreenWidth(getActivity());
        int screenHeight = DeviceUtils.getScreenHeight(getActivity());
        int width = PhotoLoadStrategy.getStrategyWidth(getActivity(), screenWidth);
        double ratio = (1.0 * width) / screenWidth;
        int height = (int) (ratio * screenHeight);
        presenter.getWelcomePagePhoto(getActivity(), "", "", "", "", "", width, height, "portrait");
    }

    @Override
    public void success(PhotoModel photoModel) {
        //隐藏fab
        floatingActionButton.hide();
        //设置图片信息
        textviewInfo.setText(photoModel.getUser().getName() + "\t" + Utils.dateFormat(photoModel.getCreatedAt()));
        //显示图片
        ImageUtils.requestImage(getActivity(), photoModel.getUrls().getCustom(), new Action1<Bitmap>() {
            @Override
            public void call(Bitmap bitmap) {
                if (null != bitmap) {
                    //隐藏加载视图
                    loadingView.hide();
                    //显示图片
                    imageView.setImageBitmap(bitmap);
                    //显示动画
                    imageView.animate()
                            .alpha(1f)
                            .setDuration(INFO_ANIMATION_DURATION)
                            .setInterpolator(new AccelerateDecelerateInterpolator())
                            .start();
                }
            }
        });
        //显示信息视图
        contentInfo.setVisibility(View.VISIBLE);
        //显示动画
        int centerX = contentInfo.getWidth() / 2;
        int centerY = contentInfo.getHeight() / 2;
        float startRadius = 0f;
        float endRadius = contentInfo.getWidth();
        Animator animator = ViewAnimationUtils.createCircularReveal(contentInfo, centerX, centerY, startRadius, endRadius);
        animator.setDuration(INFO_ANIMATION_DURATION);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.start();
    }

    @Override
    public void fail(String message) {
        // TODO: 2016/9/29 nothing
    }

    @OnClick(R.id.button_welcome_next)
    public void onNextClick() {
        jumpToNext();
    }

    private void jumpToNext() {
        if (OauthCache.hasOauthModel()) {
            //跳转到主页
            ActivityOptionsCompat activityOptionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                            floatingActionButton, getString(R.string.fab));
            ActivityCompat.startActivity(getActivity(), new Intent(getActivity(),
                    MainActivity.class), activityOptionsCompat.toBundle());
        } else {
            //跳转到授权登陆页
            ActivityOptionsCompat activityOptionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                            floatingActionButton, getString(R.string.fab));
            ActivityCompat.startActivity(getActivity(), new Intent(getActivity(),
                    OauthActivity.class), activityOptionsCompat.toBundle());
        }
        //动画后关闭，看起来比较优雅
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ActivityCompat.finishAfterTransition(WelcomeActivity.this);
            }
        }, getWindow().getTransitionBackgroundFadeDuration());
    }

    /**
     * fab点击事件
     */
    private class FabClick implements Action1<Void> {
        @Override
        public void call(Void aVoid) {
            jumpToNext();
        }
    }
}
package com.jtech.imaging.view.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;

import com.jtech.imaging.R;
import com.jtech.imaging.cache.OauthCache;
import com.jtech.imaging.mvp.contract.WelcomeContract;
import com.jtech.imaging.model.OauthModel;
import com.jtech.imaging.model.PhotoModel;
import com.jtech.imaging.mvp.presenter.WelcomePresenter;
import com.jtech.imaging.strategy.PhotoLoadStrategy;
import com.jtech.imaging.util.ActivityJump;
import com.jtech.imaging.view.widget.LoadingView;
import com.jtech.imaging.view.widget.RxCompat;
import com.jtechlib.Util.BundleChain;
import com.jtechlib.Util.DeviceUtils;
import com.jtechlib.Util.ImageUtils;
import com.jtechlib.Util.PairChain;
import com.jtechlib.view.activity.BaseActivity;

import butterknife.Bind;
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
    @Bind(R.id.textview_welcome_info)
    TextView textviewInfo;
    @Bind(R.id.contentloading)
    LoadingView loadingView;

    private WelcomeContract.Presenter presenter;

    @Override
    protected void initVariables(Bundle bundle) {
        //绑定VP类
        presenter = new WelcomePresenter(getActivity(), this);
        //暂时使用已存在数据，不经过授权登陆
        if (!OauthCache.hasOauthModel(getActivity())) {
            OauthModel oauthModel = new OauthModel();
            oauthModel.setAccessToken("da20b124d815a82ef0cb79226e991559e6e4c9cdf411fcef4e51acc718c0e44a");
            oauthModel.setCreatedAt(1473643598);
            oauthModel.setScope("public read_user write_user read_photos write_photos write_likes read_collections write_collections");
            oauthModel.setTokenType("bearer");
            OauthCache.get(getActivity()).setOauthModel(oauthModel);
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
        presenter.getWelcomePagePhoto("", "", "", "", "", width, height, "portrait");
    }

    @Override
    public void success(final PhotoModel photoModel) {
        //设置图片信息
        textviewInfo.setText(photoModel.getUser().getName());
        //显示图片
        ImageUtils.requestImage(getActivity(), photoModel.getUrls().getCustom(), new Action1<Bitmap>() {
            @Override
            public void call(Bitmap bitmap) {
                if (null != bitmap) {
                    //隐藏加载视图
                    loadingView.hide();
                    //设置点击事件
                    imageView.setOnClickListener(new OnImageViewClick(photoModel));
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
    }

    @Override
    public void fail(String message) {
    }

    private void jumpToNext() {
        if (OauthCache.hasOauthModel(getActivity())) {
            //跳转到主页
            Pair[] pairs = PairChain
                    .build(floatingActionButton, getString(R.string.fab))
                    .toArray();
            ActivityJump.build(getActivity(), MainActivity.class)
                    .makeSceneTransitionAnimation(pairs)
                    .jump();
        } else {
            //跳转到授权登陆页
            Pair[] pairs = PairChain
                    .build(floatingActionButton, getString(R.string.fab))
                    .toArray();
            ActivityJump.build(getActivity(), OauthActivity.class)
                    .makeSceneTransitionAnimation(pairs)
                    .jump();
        }
        //动画后关闭
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ActivityCompat.finishAfterTransition(WelcomeActivity.this);
            }
        }, 500);
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

    /**
     * 图片的双击点击事件
     */
    public class OnImageViewClick implements View.OnClickListener {
        private PhotoModel photoModel;
        private boolean isDoubleClick = false;

        public OnImageViewClick(PhotoModel photoModel) {
            this.photoModel = photoModel;
        }

        @Override
        public void onClick(View view) {
            if (!isDoubleClick) {
                isDoubleClick = true;
                //500毫秒后重置状态
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isDoubleClick = false;
                    }
                }, 500);
                return;
            }
            //重置状态
            isDoubleClick = false;
            //跳转到图片详情
            Bundle bundle = BundleChain.build()
                    .putString(PhotoDetailActivity.IMAGE_ID_KEY, photoModel.getId())
                    .putString(PhotoDetailActivity.IMAGE_NAME_KEY, photoModel.getUser().getName())
                    .putString(PhotoDetailActivity.IMAGE_URL_KEY, photoModel.getUrls().getRaw())
                    .toBundle();
            Pair[] pairs = PairChain
                    .build(floatingActionButton, getString(R.string.fab))
                    .addPair(imageView, getString(R.string.image))
                    .toArray();
            ActivityJump.build(getActivity(), PhotoDetailActivity.class)
                    .addBundle(bundle)
                    .makeSceneTransitionAnimation(pairs)
                    .jump();
        }
    }
}
package com.jtech.imaging.view.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.AppCompatImageView;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.jtech.imaging.R;
import com.jtech.imaging.contract.RandomContract;
import com.jtech.imaging.model.PhotoModel;
import com.jtech.imaging.presenter.RandomPresenter;
import com.jtech.imaging.strategy.PhotoLoadStrategy;
import com.jtech.imaging.view.widget.LoadingView;
import com.jtech.imaging.view.widget.RxCompat;
import com.jtechlib.Util.DeviceUtils;
import com.jtechlib.Util.ImageUtils;
import com.jtechlib.view.activity.BaseActivity;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnTouch;
import rx.functions.Action1;

/**
 * 随机页面
 * Created by jianghan on 2016/9/23.
 */
public class RandomActivity extends BaseActivity implements RandomContract.View {

    private static final long IMAGE_SHOW_ANIMATION_DURATION = 350;

    @Bind(R.id.imageview_random)
    AppCompatImageView imageView;
    @Bind(R.id.fab)
    FloatingActionButton floatingActionButton;
    @Bind(R.id.content)
    CoordinatorLayout content;
    @Bind(R.id.contentloading)
    LoadingView loadingView;

    private PhotoModel photoModel;
    private int maxHeight, screenWidth;
    private RandomContract.Presenter presenter;

    @Override
    protected void initVariables(Bundle bundle) {
        //绑定P类
        this.presenter = new RandomPresenter(getActivity(), this);
        //获取屏幕宽度
        this.screenWidth = DeviceUtils.getScreenWidth(getActivity());
        //计算最大高度
        this.maxHeight = screenWidth / 3 * 2;
    }

    @Override
    protected void initViews(Bundle bundle) {
        setContentView(R.layout.activity_random);
        //设置fab的点击事件
        RxCompat.clickThrottleFirst(floatingActionButton, new FabClick());
    }

    @Override
    protected void loadData() {
        floatingActionButton.postDelayed(new Runnable() {
            @Override
            public void run() {
                floatingActionButton.performClick();
            }
        }, getWindow().getTransitionBackgroundFadeDuration());
    }

    @Override
    public void success(final PhotoModel photoModel) {
        this.photoModel = photoModel;
        //显示图片
        String imageUrl = PhotoLoadStrategy.getUrl(getActivity(), photoModel.getUrls().getRaw(), screenWidth);
        ImageUtils.requestImage(getActivity(), imageUrl, new Action1<Bitmap>() {
            @Override
            public void call(Bitmap bitmap) {
                if (null != bitmap) {
                    //设置图片
                    imageView.setImageBitmap(bitmap);
                    //取消加载动画
                    loadingView.hide();
                    //关闭fab动画
                    floatingActionButton.setEnabled(true);
                    //计算图片高度
                    int imageHeight = Math.min(photoModel.getHeight(), maxHeight);
                    //设置图片的高度(最高不超过屏幕高度的三分之二)
                    ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
                    layoutParams.width = screenWidth;
                    layoutParams.height = imageHeight;
                    imageView.setLayoutParams(layoutParams);
                    //设置图片透明度为0
                    imageView.setAlpha(0f);
                    //显示图片动画
                    imageView.animate()
                            .alphaBy(1f)
                            .setDuration(IMAGE_SHOW_ANIMATION_DURATION)
                            .setInterpolator(new AccelerateDecelerateInterpolator())
                            .start();
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
        //取消加载动画
        loadingView.hide();
        //取消fab动画
        floatingActionButton.setEnabled(true);
        //弹出错误消息
        Snackbar.make(content, message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //退出动画
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
     * 图片点击事件
     *
     * @return
     */

    @OnClick(R.id.imageview_random)
    void onImageClick() {
        if (null != photoModel) {
            Bundle bundle = new Bundle();
            bundle.putString(PhotoDetailActivity.IMAGE_ID_KEY, photoModel.getId());
            bundle.putString(PhotoDetailActivity.IMAGE_NAME_KEY, photoModel.getUser().getName());
            bundle.putString(PhotoDetailActivity.IMAGE_URL_KEY, photoModel.getUrls().getRaw());
            Pair pairFab = Pair.create(floatingActionButton, getString(R.string.fab));
            Pair pairImage = Pair.create(imageView, getString(R.string.image));
            ActivityOptionsCompat activityOptionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), pairFab, pairImage);
            Intent intent = new Intent(getActivity(), PhotoDetailActivity.class);
            intent.putExtras(bundle);
            ActivityCompat.startActivity(getActivity(), intent, activityOptionsCompat.toBundle());
        } else {
            Snackbar.make(content, "Please wait the request success", Snackbar.LENGTH_SHORT).show();
        }
    }

    /**
     * fab的点击事件
     */
    private class FabClick implements Action1<Void> {
        @Override
        public void call(Void aVoid) {
            //开启加载动画
            loadingView.show();
            //设置为不可用
            floatingActionButton.setEnabled(false);
            //请求数据
            presenter.getRandomPhoto("", "", "", "", "", 0, 0, "landscape");
        }
    }
}
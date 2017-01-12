package com.jtech.imaging.view.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.util.Pair;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.bumptech.glide.request.target.Target;
import com.jtech.imaging.R;
import com.jtech.imaging.model.PhotoModel;
import com.jtech.imaging.mvp.contract.RandomContract;
import com.jtech.imaging.mvp.presenter.RandomPresenter;
import com.jtech.imaging.strategy.PhotoLoadStrategy;
import com.jtech.imaging.util.ActivityJump;
import com.jtech.imaging.view.widget.LoadingView;
import com.jtech.imaging.view.widget.RxCompat;
import com.jtechlib.Util.DeviceUtils;
import com.jtechlib.Util.ImageUtils;
import com.jtechlib.Util.PairChain;
import com.jtechlib.view.activity.BaseActivity;
import com.umeng.analytics.MobclickAgent;

import butterknife.Bind;
import rx.functions.Action1;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * 随机页面
 * Created by jianghan on 2016/9/23.
 */
public class RandomActivity extends BaseActivity implements RandomContract.View, PhotoViewAttacher.OnPhotoTapListener {
    private static final long IMAGE_SHOW_ANIMATION_DURATION = 350;

    @Bind(R.id.content)
    CoordinatorLayout content;
    @Bind(R.id.fab)
    FloatingActionButton floatingActionButton;
    @Bind(R.id.contentloading)
    LoadingView loadingView;
    @Bind(R.id.photoview_random)
    PhotoView photoView;

    private PhotoModel photoModel;
    private RandomContract.Presenter presenter;

    @Override
    protected void initVariables(Bundle bundle) {
        //绑定P类
        presenter = new RandomPresenter(getActivity(), this);
    }

    @Override
    protected void initViews(Bundle bundle) {
        setContentView(R.layout.activity_random);
        //设置photoview的点击事件
        photoView.setOnPhotoTapListener(this);
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
        }, 500);
    }

    @Override
    public void success(PhotoModel photoModel) {
        this.photoModel = photoModel;
        //显示图片
        String imageUrl = PhotoLoadStrategy.getUrl(getActivity(), photoModel.getUrls().getRaw(), DeviceUtils.getScreenWidth(getActivity()));
        ImageUtils.requestImage(getActivity(), imageUrl, Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL, new Action1<Bitmap>() {
            @Override
            public void call(Bitmap bitmap) {
                if (null != bitmap) {
                    //设置图片
                    photoView.setImageBitmap(bitmap);
                    //取消加载动画
                    loadingView.hide();
                    //关闭fab动画
                    floatingActionButton.setEnabled(true);
                    //设置图片透明度为0
                    photoView.setAlpha(0f);
                    //显示图片动画
                    photoView.animate()
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
    public void onPhotoTap(View view, float x, float y) {
        if (null != photoModel) {
            Bundle bundle = new Bundle();
            bundle.putString(PhotoDetailActivity.KEY_IMAGE_ID, photoModel.getId());
            bundle.putString(PhotoDetailActivity.KEY_IMAGE_NAME, photoModel.getUser().getName());
            bundle.putString(PhotoDetailActivity.KEY_IMAGE_URL, photoModel.getUrls().getRaw());
            Pair[] pairs = PairChain
                    .build(floatingActionButton, getString(R.string.fab))
                    .addPair(photoView, getString(R.string.image))
                    .toArray();
            ActivityJump.build(getActivity(), PhotoDetailActivity.class)
                    .addBundle(bundle)
                    .makeSceneTransitionAnimation(pairs)
                    .jump();
        } else {
            Snackbar.make(content, "Please wait the request success", Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onOutsidePhotoTap() {
        ActivityCompat.finishAfterTransition(getActivity());
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

    public void onResume() {
        super.onResume();
        //友盟统计
        MobclickAgent.onPageStart(TAG);
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        //友盟统计
        MobclickAgent.onPageEnd(TAG);
        MobclickAgent.onPause(this);
    }
}
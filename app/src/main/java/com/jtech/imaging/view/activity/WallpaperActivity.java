package com.jtech.imaging.view.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.jtech.imaging.R;
import com.jtech.imaging.contract.WallpaperContract;
import com.jtech.imaging.presenter.WallpaperPresenter;
import com.jtech.imaging.strategy.PhotoResolutionStrategy;
import com.jtech.imaging.view.widget.LoadingView;
import com.jtechlib.Util.ImageUtils;
import com.jtechlib.view.activity.BaseActivity;

import butterknife.Bind;
import rx.functions.Action1;
import uk.co.senab.photoview.PhotoView;

/**
 * 壁纸设置页面
 * Created by jianghan on 2016/10/14.
 */

public class WallpaperActivity extends BaseActivity implements WallpaperContract.View {

    public static final String IMAGE_URL_KEY = "imageUrlKey";
    public static final String IMAGE_WIDTH_KEY = "imageWidthKey";
    public static final String IMAGE_HEIGHT_KEY = "imageHeightKey";
    public static final long IMAGE_ANIMATION_DURATION = 450;

    @Bind(R.id.contentloading)
    LoadingView loadingView;
    @Bind(R.id.photoview_wallpaper)
    PhotoView photoView;
    @Bind(R.id.fab)
    FloatingActionButton floatingActionButton;
    @Bind(R.id.tablayout_wallpaper)
    TabLayout tabLayout;

    private WallpaperContract.Presenter presenter;

    private String imageUrl;
    private int imageWidth, imageHeight;

    @Override
    protected void initVariables(Bundle bundle) {
        //获取图片url
        this.imageUrl = bundle.getString(IMAGE_URL_KEY);
        //获取图片宽度
        this.imageWidth = bundle.getInt(IMAGE_WIDTH_KEY);
        //获取图片高度
        this.imageHeight = bundle.getInt(IMAGE_HEIGHT_KEY);
        //实例化P类
        this.presenter = new WallpaperPresenter(this);
    }

    @Override
    protected void initViews(Bundle bundle) {
        setContentView(R.layout.activity_wallpaper);
    }

    @Override
    protected void loadData() {
        //显示加载动画
        loadingView.show();
        //默认加载详情页设置的分辨率
        final long startTimeMillis = System.currentTimeMillis();
        ImageUtils.requestImage(getActivity(), PhotoResolutionStrategy.getUrl(getActivity(), imageUrl), new Action1<Bitmap>() {
            @Override
            public void call(Bitmap bitmap) {
                if (null != bitmap) {
                    //隐藏加载对象
                    loadingView.hide();
                    photoView.setImageBitmap(bitmap);
                    if (System.currentTimeMillis() - startTimeMillis > getWindow().getTransitionBackgroundFadeDuration()) {
                        photoView.setAlpha(0f);
                        photoView
                                .animate()
                                .alpha(1f)
                                .setDuration(IMAGE_ANIMATION_DURATION)
                                .setInterpolator(new AccelerateDecelerateInterpolator())
                                .start();
                    }
                }
            }
        });
    }
}
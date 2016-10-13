package com.jtech.imaging.view.activity;

import android.animation.Animator;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.jtech.imaging.R;
import com.jtech.imaging.contract.PhotoDetailContract;
import com.jtech.imaging.model.PhotoModel;
import com.jtech.imaging.presenter.PhotoDetailPresenter;
import com.jtech.imaging.strategy.PhotoLoadStrategy;
import com.jtech.imaging.view.widget.LoadingView;
import com.jtech.imaging.view.widget.RxCompat;
import com.jtechlib.Util.DeviceUtils;
import com.jtechlib.Util.ImageUtils;
import com.jtechlib.view.activity.BaseActivity;
import com.jtechlib.view.widget.StatusBarCompat;

import butterknife.Bind;
import rx.functions.Action1;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * 图片详情
 * Created by jianghan on 2016/10/8.
 */

public class PhotoDetailActivity extends BaseActivity implements PhotoDetailContract.View, View.OnClickListener {

    public static final String IMAGE_ID_KEY = "imageIdKey";
    public static final long IMAGE_ANIMATION_DURATION = 450;
    public static final long APPBAR_ANIMATION_DURATION = 350;

    @Bind(R.id.contentloading)
    LoadingView loadingView;
    @Bind(R.id.photoview_detail)
    PhotoView photoView;
    @Bind(R.id.fab)
    FloatingActionButton floatingActionButton;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.appbar)
    AppBarLayout appBarLayout;
    @Bind(R.id.statusbar)
    View statusBar;
    @Bind(R.id.content)
    CoordinatorLayout content;

    private String imageId;
    private PhotoModel photoModel;

    private PhotoDetailContract.Presenter presenter;

    @Override
    protected void initVariables(Bundle bundle) {
        //p类实现
        this.presenter = new PhotoDetailPresenter(this);
        //获取图片id
        this.imageId = bundle.getString(IMAGE_ID_KEY);
    }

    @Override
    protected void initViews(Bundle bundle) {
        setContentView(R.layout.activity_photo_detail);
        //设置标题栏
        setupToolbar(toolbar)
                .setContentInsetStartWithNavigation(0)
                .setTitleTextColor(R.color.toolbar_title)
                .setNavigationIcon(R.drawable.ic_keyboard_backspace_white_24dp, this);
        //设置状态栏
        StatusBarCompat.setStatusBar(getActivity(), statusBar);
        //设置fab的点击事件
        RxCompat.clickThrottleFirst(floatingActionButton, new FabClick());
        //设置图片的缩放事件
        photoView.setOnScaleChangeListener(new OnScaleChange());
    }

    @Override
    protected void loadData() {
        //获取图片缓存
        presenter.getPhotoDetailCache(getActivity(), imageId);
    }

    @Override
    public void success(final PhotoModel photoModel) {
        this.photoModel = photoModel;
        //设置标题
        toolbar.setTitle(photoModel.getUser().getName());
        //显示图片
        ImageUtils.requestImage(getActivity(), PhotoLoadStrategy.getUrl(getActivity(), photoModel.getUrls().getRaw(), DeviceUtils.getScreenWidth(getActivity())), new Action1<Bitmap>() {
            @Override
            public void call(Bitmap bitmap) {
                if (null != bitmap) {
                    loadingView.hide();
                    photoView.setImageBitmap(bitmap);
                    photoView
                            .animate()
                            .alpha(1f)
                            .setDuration(IMAGE_ANIMATION_DURATION)
                            .setInterpolator(new AccelerateDecelerateInterpolator())
                            .start();
                }
            }
        });
    }

    @Override
    public void fail(String message) {
        Snackbar.make(photoView, message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showSheetDialog() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity());
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.view_sheet_photo_detail, null, false);
        bottomSheetDialog.setContentView(contentView);
        bottomSheetDialog.show();
        //设置点击事件
        RxCompat.clickThrottleFirst(contentView.findViewById(R.id.sheet_photo_detail_info), new PhotoInfoAction());
        RxCompat.clickThrottleFirst(contentView.findViewById(R.id.sheet_photo_detail_resolution), new PhotoResolutionAction());
        RxCompat.clickThrottleFirst(contentView.findViewById(R.id.sheet_photo_detail_wallpaper), new PhotoWallpaperAction());
        RxCompat.clickThrottleFirst(contentView.findViewById(R.id.sheet_photo_detail_wallpaper), new PhotoDownloadAction());
    }

    @Override
    public void onClick(View v) {
        //后退
        onBackPressed();
    }

    /**
     * 图片信息事件
     */
    private class PhotoInfoAction implements Action1 {

        @Override
        public void call(Object o) {

        }
    }

    /**
     * 图片清晰度选择
     */
    private class PhotoResolutionAction implements Action1 {

        @Override
        public void call(Object o) {

        }
    }

    /**
     * 设置为壁纸
     */
    private class PhotoWallpaperAction implements Action1 {

        @Override
        public void call(Object o) {

        }
    }

    /**
     * 图片下载
     */
    private class PhotoDownloadAction implements Action1 {

        @Override
        public void call(Object o) {

        }
    }

    /**
     * 缩放监听
     */
    private class OnScaleChange implements PhotoViewAttacher.OnScaleChangeListener {
        private boolean isAnimatorRunning = false;

        @Override
        public void onScaleChange(float scaleFactor, float focusX, float focusY) {
            float scale = photoView.getScale();
            float targetTranslationY = 0f;
            float appBarTranslationY = appBarLayout.getTranslationY();
            //计算位移位置
            if (scale > 1f) {
                targetTranslationY = -appBarLayout.getHeight();
            } else if (scale < 1f) {
                targetTranslationY = 0;
            }
            if (!isAnimatorRunning && targetTranslationY != appBarTranslationY) {
                //现实或隐藏appbar
                appBarLayout
                        .animate()
                        .translationY(targetTranslationY)
                        .setDuration(APPBAR_ANIMATION_DURATION)
                        .setInterpolator(new AccelerateDecelerateInterpolator())
                        .setListener(new AnimatorListener())
                        .start();
            }
        }

        /**
         * 动画状态监听
         */
        private class AnimatorListener implements Animator.AnimatorListener {
            @Override
            public void onAnimationStart(Animator animation) {
                isAnimatorRunning = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isAnimatorRunning = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                isAnimatorRunning = false;
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                isAnimatorRunning = true;
            }
        }
    }

    /**
     * fab的点击事件
     */
    private class FabClick implements Action1 {
        @Override
        public void call(Object o) {
            showSheetDialog();
        }
    }
}
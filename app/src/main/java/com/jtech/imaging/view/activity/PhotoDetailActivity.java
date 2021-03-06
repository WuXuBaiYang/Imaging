package com.jtech.imaging.view.activity;

import android.animation.Animator;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.util.Pair;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.jtech.imaging.R;
import com.jtech.imaging.cache.PhotoCache;
import com.jtech.imaging.common.PhotoResolution;
import com.jtech.imaging.model.PhotoModel;
import com.jtech.imaging.mvp.contract.PhotoDetailContract;
import com.jtech.imaging.mvp.presenter.PhotoDetailPresenter;
import com.jtech.imaging.strategy.PhotoResolutionStrategy;
import com.jtech.imaging.util.ActivityJump;
import com.jtech.imaging.view.widget.LoadingView;
import com.jtech.imaging.view.widget.RxCompat;
import com.jtech.imaging.view.widget.dialog.PhotoDetailSheetDialog;
import com.jtech.imaging.view.widget.dialog.PhotoExifDialog;
import com.jtech.imaging.view.widget.dialog.PhotoResolutionDialog;
import com.jtechlib.Util.BundleChain;
import com.jtechlib.Util.ImageUtils;
import com.jtechlib.Util.PairChain;
import com.jtechlib.view.activity.BaseActivity;
import com.umeng.analytics.MobclickAgent;

import butterknife.Bind;
import rx.functions.Action1;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * 图片详情
 * Created by jianghan on 2016/10/8.
 */

public class PhotoDetailActivity extends BaseActivity implements PhotoDetailContract.View, View.OnClickListener {

    public static final String KEY_IMAGE_NAME = "IMAGE_NAME";
    public static final String KEY_IMAGE_URL = "IMAGE_URL";
    public static final String KEY_IMAGE_ID = "IMAGE_ID";
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
    @Bind(R.id.content)
    CoordinatorLayout content;

    private PhotoDetailContract.Presenter presenter;

    @Override
    protected void initVariables(Bundle bundle) {
        //获取参数
        String imageId = bundle.getString(KEY_IMAGE_ID);
        String imageName = bundle.getString(KEY_IMAGE_NAME);
        String imageUrl = bundle.getString(KEY_IMAGE_URL);
        //p类实现
        presenter = new PhotoDetailPresenter(getActivity(), this, imageId, imageName, imageUrl);
    }

    @Override
    protected void initViews(Bundle bundle) {
        setContentView(R.layout.activity_photo_detail);
        //设置标题
        toolbar.setTitle(presenter.getPhotoName() + "(" + PhotoResolutionStrategy.getStrategyString(getActivity()) + ")");
        //设置标题栏
        setupToolbar(toolbar)
                .setContentInsetStartWithNavigation(0)
                .setTitleTextColor(R.color.toolbar_title)
                .setNavigationIcon(R.drawable.ic_keyboard_backspace_white_24dp, this);
        //设置fab的点击事件
        RxCompat.clickThrottleFirst(floatingActionButton, new FabClick());
        //设置图片的缩放事件
        photoView.setOnScaleChangeListener(new OnScaleChange());
    }

    @Override
    protected void loadData() {
        //显示加载动画
        loadingView.show();
        //设置标题
        toolbar.setTitle(presenter.getPhotoName() + "(" + PhotoResolutionStrategy.getStrategyString(getActivity()) + ")");
        //显示图片
        final long startTimeMillis = System.currentTimeMillis();
        ImageUtils.requestImage(getActivity(), PhotoResolutionStrategy.getUrl(getActivity(), presenter.getPhotoUrl()), new Action1<Bitmap>() {
            @Override
            public void call(Bitmap bitmap) {
                if (null != bitmap) {
                    //隐藏加载动画
                    loadingView.hide();
                    //设置图片
                    photoView.setImageBitmap(bitmap);
                    if (System.currentTimeMillis() - startTimeMillis > 500) {
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
        //获取图片缓存
        presenter.getPhotoDetailCache(getActivity(), presenter.getPhotoId());
    }

    @Override
    public void success(PhotoModel photoModel) {
        //隐藏加载动画
        loadingView.hide();
    }

    @Override
    public void fail(String message) {
        Snackbar.make(photoView, message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void downloadFail(String error) {
        Snackbar.make(content, error, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void downloadSuccess() {
        Snackbar.make(content, "start to download", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showSheetDialog() {
        PhotoDetailSheetDialog
                .build(getActivity())
                .setOnPhotoDetailSheetItemClick(new OnSheetItemClick())
                .setResolution(PhotoResolutionStrategy.getStrategyString(getActivity()))
                .show();
    }

    @Override
    public void showResolutionDialog() {
        PhotoResolutionDialog
                .build(getActivity())
                .setSingleChoiceItems(new OnResolutionDialogClick())
                .show();
    }

    @Override
    public void showPhotoExifDialog() {
        PhotoExifDialog
                .build(getActivity(), presenter.getPhotoModel())
                .setDoneClick(new OnPhotoExifDoneClick())
                .show();
    }

    @Override
    public void jumpToWallpaper() {
        Bundle bundle = BundleChain.build()
                .putString(WallpaperActivity.KEY_IMAGE_URL, presenter.getPhotoModel().getUrls().getRaw())
                .toBundle();
        Pair[] pairs = PairChain
                .build(floatingActionButton, getString(R.string.fab))
                .addPair(photoView, getString(R.string.image))
                .toArray();
        ActivityJump.build(getActivity(), WallpaperActivity.class)
                .addBundle(bundle)
                .makeSceneTransitionAnimation(pairs)
                .jump();
    }

    @Override
    public void onClick(View v) {
        //后退
        onBackPressed();
    }

    /**
     * sheet的item点击事件
     */
    private class OnSheetItemClick implements PhotoDetailSheetDialog.OnPhotoDetailSheetItemClick {
        @Override
        public void onItemClick(BottomSheetDialog bottomSheetDialog, int position) {
            switch (position) {
                case 0://图片信息
                    showPhotoExifDialog();
                    break;
                case 1://图片清晰度选择
                    showResolutionDialog();
                    break;
                case 2://设置为壁纸
                    jumpToWallpaper();
                    break;
                case 3://下载
                    presenter.startDownload();
                    break;
                default:
                    break;
            }
            //隐藏sheet
            bottomSheetDialog.dismiss();
        }
    }

    /**
     * 清晰度选择
     */
    private class OnResolutionDialogClick implements DialogInterface.OnClickListener {

        @Override
        public void onClick(DialogInterface dialog, int which) {
            //关闭对话框
            dialog.dismiss();
            //默认为0
            int checkStrategy = 0;
            //设置策略
            switch (which) {
                case 0://1080p
                    checkStrategy = PhotoResolution.PHOTO_RESOLUTION_1080;
                    break;
                case 1://720p
                    checkStrategy = PhotoResolution.PHOTO_RESOLUTION_720;
                    break;
                case 2://480p
                    checkStrategy = PhotoResolution.PHOTO_RESOLUTION_480;
                    break;
                default:
                    break;
            }
            //存储策略
            PhotoCache.get(getActivity()).setPhotoResolutionStrategy(checkStrategy);
            //刷新页面
            loadData();
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
     * 图片参数dialog的完成按钮点击事件
     */
    private static class OnPhotoExifDoneClick implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    }

    /**
     * fab的点击事件
     */
    private class FabClick implements Action1 {
        @Override
        public void call(Object o) {
            if (null != presenter.getPhotoModel()) {
                showSheetDialog();
            } else {
                Snackbar.make(content, "Please wait for loading", Snackbar.LENGTH_SHORT).show();
            }
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
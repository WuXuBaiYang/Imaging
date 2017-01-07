package com.jtech.imaging.view.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewPager;

import com.jtech.imaging.R;
import com.jtech.imaging.mvp.contract.GalleryContract;
import com.jtech.imaging.mvp.presenter.GalleryPresenter;
import com.jtech.imaging.util.ActivityJump;
import com.jtech.imaging.view.adapter.GalleryPagerAdapter;
import com.jtech.imaging.view.widget.RxCompat;
import com.jtechlib.Util.BundleChain;
import com.jtechlib.Util.PairChain;
import com.jtechlib.view.activity.BaseActivity;

import java.util.List;

import butterknife.Bind;
import rx.functions.Action1;

/**
 * 画廊页面
 * Created by jianghan on 2016/11/21.
 */

public class GalleryActivity extends BaseActivity implements GalleryContract.View, GalleryPagerAdapter.OnOutsidePhotoTap {
    public static final String KEY_PHOTO_LIST = "PHOTO_LIST";
    public static final String KEY_PHOTO_INDEX = "PHOTO_INDEX";

    @Bind(R.id.viewpager_gallery)
    ViewPager viewPager;
    @Bind(R.id.fab)
    FloatingActionButton floatingActionButton;

    private GalleryPagerAdapter galleryPagerAdapter;
    private GalleryContract.Presenter presenter;

    @Override
    protected void initVariables(Bundle bundle) {
        //获取图片集合与选中
        List<String> imageUris = bundle.getStringArrayList(KEY_PHOTO_LIST);
        int selectIndex = bundle.getInt(KEY_PHOTO_INDEX);
        //实例化P类
        this.presenter = new GalleryPresenter(getActivity(), this, imageUris, selectIndex);
    }

    @Override
    protected void initViews(Bundle bundle) {
        setContentView(R.layout.activity_gallery);
        //设置fab的点击事件
        RxCompat.clickThrottleFirst(floatingActionButton, new FabClick());
        //设置viewpager
        setupPager(presenter.getImageUris());
    }

    @Override
    protected void loadData() {

    }

    /**
     * 设置viewpager
     */
    private void setupPager(List<String> imageUris) {
        //实例化pager适配器
        galleryPagerAdapter = new GalleryPagerAdapter(getSupportFragmentManager(), imageUris);
        //设置点击事件
        galleryPagerAdapter.setOnOutsidePhotoTap(this);
        //设置适配器
        viewPager.setAdapter(galleryPagerAdapter);
        //设置默认选中
        viewPager.setCurrentItem(presenter.getSelectIndex());
    }

    @Override
    public void outsideTap() {
        //关闭当前的activity
        ActivityCompat.finishAfterTransition(getActivity());
    }

    /**
     * fab的点击事件
     */
    private class FabClick implements Action1<Void> {
        @Override
        public void call(Void aVoid) {
            Bundle bundle = BundleChain.build()
                    .putSerializable(WallpaperActivity.KEY_IMAGE_URL, "图片地址")
                    .toBundle();
            Pair[] pairs = PairChain
                    .build(floatingActionButton, getString(R.string.fab))
                    .addPair(galleryPagerAdapter.getView(viewPager.getCurrentItem()), getString(R.string.image))
                    .toArray();
            ActivityJump.build(getActivity(), WallpaperActivity.class)
                    .addBundle(bundle)
                    .makeSceneTransitionAnimation(pairs)
                    .jump();
        }
    }
}
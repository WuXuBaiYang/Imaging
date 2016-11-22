package com.jtech.imaging.view.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewPager;

import com.jtech.imaging.R;
import com.jtech.imaging.contract.GalleryContract;
import com.jtech.imaging.presenter.GalleryPresenter;
import com.jtech.imaging.util.ActivityJump;
import com.jtech.imaging.view.adapter.GalleryPagerAdapter;
import com.jtech.imaging.view.widget.RxCompat;
import com.jtechlib.Util.BundleChain;
import com.jtechlib.Util.DeviceUtils;
import com.jtechlib.Util.PairChain;
import com.jtechlib.view.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.functions.Action1;
import uk.co.senab.photoview.PhotoView;

/**
 * 画廊页面
 * Created by jianghan on 2016/11/21.
 */

public class GalleryActivity extends BaseActivity implements GalleryContract.View {

    public static final String GALLERY_LIST_KEY = "galleryListKey";
    public static final String GALLERY_INDEX_KEY = "galleryIndexKey";

    @Bind(R.id.viewpager_gallery)
    ViewPager viewPager;
    @Bind(R.id.fab)
    FloatingActionButton floatingActionButton;

    private GalleryPagerAdapter galleryPagerAdapter;
    private GalleryContract.Presenter presenter;
    private List<String> imageList;
    private int imageIndex;

    @Override
    protected void initVariables(Bundle bundle) {
        //实例化P类
        this.presenter = new GalleryPresenter(getActivity(), this);
        //获取图片地址集合
        this.imageList = bundle.getStringArrayList(GALLERY_LIST_KEY);
        //获取当前点击图片所在位置
        this.imageIndex = bundle.getInt(GALLERY_INDEX_KEY);
    }

    @Override
    protected void initViews(Bundle bundle) {
        setContentView(R.layout.activity_gallery);
        //设置viewpager
        setupPager();
        //设置fab的点击事件
        RxCompat.clickThrottleFirst(floatingActionButton, new FabClick());
    }

    @Override
    protected void loadData() {

    }

    /**
     * 设置viewpager
     */
    private void setupPager() {
        //实例化视图
        List<PhotoView> photoViews = new ArrayList<>();
        for (String imageUri : imageList) {
            final PhotoView photoView = new PhotoView(getActivity());
            presenter.getImage(imageUri, DeviceUtils.getScreenHeight(getActivity()), new Action1<Bitmap>() {
                @Override
                public void call(Bitmap bitmap) {
                    if (null != bitmap) {
                        photoView.setImageBitmap(bitmap);
                    }
                }
            });
            photoViews.add(photoView);
        }
        //实例化pager适配器
        galleryPagerAdapter = new GalleryPagerAdapter(photoViews);
        //设置适配器
        viewPager.setAdapter(galleryPagerAdapter);
        //跳转到指定页面
        viewPager.setCurrentItem(imageIndex);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //动画后关闭activity
        ActivityCompat.finishAffinity(getActivity());
    }

    /**
     * fab的点击事件
     */
    private class FabClick implements Action1<Void> {
        @Override
        public void call(Void aVoid) {
            int currentItem = viewPager.getCurrentItem();
            if (currentItem >= 0 && currentItem < galleryPagerAdapter.getCount()) {
                Bundle bundle = BundleChain.build()
                        .putString(WallpaperActivity.IMAGE_LOCAL_PATH_KEY, imageList.get(currentItem))
                        .toBundle();
                Pair[] pairs = PairChain
                        .build(floatingActionButton, getString(R.string.fab))
                        .addPair(galleryPagerAdapter.getPhotoView(currentItem), getString(R.string.image))
                        .toArray();
                ActivityJump.build(getActivity(), WallpaperActivity.class)
                        .addBundle(bundle)
                        .makeSceneTransitionAnimation(pairs)
                        .jump();
            }
        }
    }
}
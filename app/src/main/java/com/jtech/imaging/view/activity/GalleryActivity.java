package com.jtech.imaging.view.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewPager;

import com.jtech.imaging.R;
import com.jtech.imaging.contract.GalleryContract;
import com.jtech.imaging.model.DownloadModel;
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

    @Bind(R.id.viewpager_gallery)
    ViewPager viewPager;
    @Bind(R.id.fab)
    FloatingActionButton floatingActionButton;

    private GalleryPagerAdapter galleryPagerAdapter;
    private GalleryContract.Presenter presenter;
    private List<DownloadModel> downloadModels;

    @Override
    protected void initVariables(Bundle bundle) {
        //实例化P类
        this.presenter = new GalleryPresenter(getActivity(), this);
    }

    @Override
    protected void initViews(Bundle bundle) {
        setContentView(R.layout.activity_gallery);
        //设置fab的点击事件
        RxCompat.clickThrottleFirst(floatingActionButton, new FabClick());
    }

    @Override
    protected void loadData() {
        presenter.getDownloadedList();

        //假数据
        List<DownloadModel> downloadModels = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            DownloadModel downloadModel = new DownloadModel();
            downloadModel.setId(10000);
            downloadModel.setColor("#607563");
            downloadModel.setPath("http://h.hiphotos.baidu.com/zhidao/pic/item/6d81800a19d8bc3ed69473cb848ba61ea8d34516.jpg");
            downloadModels.add(downloadModel);
        }
        downloadTaskList(downloadModels);
    }

    @Override
    public void downloadTaskList(List<DownloadModel> downloadModels) {
        setupPager(downloadModels);
    }

    /**
     * 设置viewpager
     */
    private void setupPager(List<DownloadModel> downloadModels) {
        this.downloadModels = downloadModels;
        //实例化视图
        List<PhotoView> photoViews = new ArrayList<>();
        for (DownloadModel downloadModel : downloadModels) {
            final PhotoView photoView = new PhotoView(getActivity());
            presenter.getImage(downloadModel, DeviceUtils.getScreenHeight(getActivity()), new Action1<Bitmap>() {
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
    }

    /**
     * fab的点击事件
     */
    private class FabClick implements Action1<Void> {
        @Override
        public void call(Void aVoid) {
            int currentItem = viewPager.getCurrentItem();
            if (currentItem >= 0 && currentItem < galleryPagerAdapter.getCount()
                    && null != downloadModels && currentItem < downloadModels.size()) {
                Bundle bundle = BundleChain.build()
                        .putSerializable(WallpaperActivity.IMAGE_LOCAL_PATH_KEY, downloadModels.get(currentItem))
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
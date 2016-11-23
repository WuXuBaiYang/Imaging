package com.jtech.imaging.view.activity;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.jtech.imaging.R;
import com.jtech.imaging.common.DownloadState;
import com.jtech.imaging.contract.DownloadContract;
import com.jtech.imaging.model.DownloadModel;
import com.jtech.imaging.presenter.DownloadPresenter;
import com.jtech.imaging.util.ActivityJump;
import com.jtech.imaging.view.adapter.DownloadPagerAdapter;
import com.jtech.imaging.view.fragment.DownloadedFragment;
import com.jtech.imaging.view.fragment.DownloadingFragment;
import com.jtech.imaging.view.widget.RxCompat;
import com.jtech.listener.OnItemClickListener;
import com.jtech.view.RecyclerHolder;
import com.jtechlib.Util.BundleChain;
import com.jtechlib.Util.PairChain;
import com.jtechlib.view.activity.BaseActivity;
import com.jtechlib.view.fragment.BaseFragment;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import rx.functions.Action1;

/**
 * 下载管理页
 * Created by jianghan on 2016/10/19.
 */

public class DownloadActivity extends BaseActivity implements DownloadContract.View, View.OnClickListener, ViewPager.OnPageChangeListener, OnItemClickListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.statusbar)
    View statusBar;
    @Bind(R.id.fab)
    FloatingActionButton floatingActionButton;
    @Bind(R.id.tablayout_download)
    TabLayout tabLayout;
    @Bind(R.id.viewpager)
    ViewPager viewPager;
    @Bind(R.id.content)
    CoordinatorLayout content;

    private DownloadingFragment downloadingFragment;
    private DownloadedFragment downloadedFragment;
    private DownloadContract.Presenter presenter;

    @Override
    protected void initVariables(Bundle bundle) {
        //实例化P类
        this.presenter = new DownloadPresenter(getActivity(), this);
    }

    @Override
    protected void initViews(Bundle bundle) {
        setContentView(R.layout.activity_download);
        //设置toolbar
        setupToolbar(toolbar)
                .setTitle("Download")
                .setContentInsetStartWithNavigation(0)
                .setNavigationIcon(R.drawable.ic_keyboard_backspace_white_24dp, this);
        //实例化fragment对象
        downloadedFragment = DownloadedFragment.newInstance();
        downloadingFragment = DownloadingFragment.newInstance();
        //设置适配器
        BaseFragment[] fragmentList = {downloadedFragment, downloadingFragment};
        viewPager.setAdapter(new DownloadPagerAdapter(getSupportFragmentManager(), Arrays.asList(fragmentList)));
        //设置viewpager的pagechange事件监听
        viewPager.addOnPageChangeListener(this);
        //设置已下载列表的item点击事件
        downloadedFragment.setOnItemClickListener(this);
        //设置fab的点击事件
        RxCompat.clickThrottleFirst(floatingActionButton, new FabClick());
        //将tablayout设置给viewpager
        tabLayout.setupWithViewPager(viewPager);
        //监听下载列表的变化
        presenter.listenDownloadingChange();
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onClick(View v) {
        //后退
        onBackPressed();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == 0) {//已缓存
            floatingActionButton.setImageResource(R.drawable.ic_photo_library_white_36dp);
        } else {//缓存中
            if (downloadingFragment.isAllDownloading()) {//正在下载
                floatingActionButton.setImageResource(R.drawable.ic_play_arrow_white_36dp);
            } else {
                floatingActionButton.setImageResource(R.drawable.ic_stop_white_36dp);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void downloadingList(List<DownloadModel> downloadModels) {
        if (viewPager.getCurrentItem() == 1) {//下载列表
            for (DownloadModel downloadModel : downloadModels) {
                if (downloadModel.getState() != DownloadState.DOWNLOADING) {
                    //存在至少一个未下载中状态
                    floatingActionButton.setImageResource(R.drawable.ic_play_arrow_white_36dp);
                    return;
                }
            }
            floatingActionButton.setImageResource(R.drawable.ic_stop_white_36dp);
        }
    }

    @Override
    public void onItemClick(RecyclerHolder recyclerHolder, View view, int position) {
        jumpToWallpaper(downloadedFragment.getModel(position), downloadedFragment.getView(recyclerHolder));
    }

    /**
     * 跳转到画廊页面
     */
    private void jumpToGallery() {
        if (downloadedFragment.hasPhoto()) {
            Pair[] pairs = PairChain
                    .build(floatingActionButton, getString(R.string.fab))
                    .toArray();
            ActivityJump
                    .build(getActivity(), GalleryActivity.class)
                    .makeSceneTransitionAnimation(pairs)
                    .jump();
        } else {
            Snackbar.make(content, "none", Snackbar.LENGTH_SHORT).show();
        }
    }

    /**
     * 跳转到壁纸设置页面
     *
     * @param downloadModel
     * @param view
     */
    public void jumpToWallpaper(DownloadModel downloadModel, View view) {
        Bundle bundle = BundleChain.build()
                .putSerializable(WallpaperActivity.IMAGE_LOCAL_PATH_KEY, downloadModel)
                .toBundle();
        Pair[] pairs = PairChain
                .build(floatingActionButton, getString(R.string.fab))
                .addPair(view, getString(R.string.image))
                .toArray();
        ActivityJump.build(getActivity(), WallpaperActivity.class)
                .addBundle(bundle)
                .makeSceneTransitionAnimation(pairs)
                .jump();
    }

    /**
     * fab的点击事件
     */
    private class FabClick implements Action1<Void> {
        @Override
        public void call(Void aVoid) {
            //判断当前的所在页面
            if (viewPager.getCurrentItem() == 0) {//已缓存
                jumpToGallery();
            } else {//缓存中
                if (downloadingFragment.isAllDownloading()) {//正在下载
                    floatingActionButton.setImageResource(R.drawable.ic_stop_white_36dp);
                    presenter.stopAllDownload();
                } else {
                    floatingActionButton.setImageResource(R.drawable.ic_play_arrow_white_36dp);
                    presenter.startAllDownload();
                }
            }
        }
    }
}
package com.jtech.imaging.view.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.jtech.imaging.R;
import com.jtech.imaging.contract.DownloadContract;
import com.jtech.imaging.event.DownloadServeEvent;
import com.jtech.imaging.presenter.DownloadPresenter;
import com.jtech.imaging.view.adapter.DownloadPagerAdapter;
import com.jtech.imaging.view.fragment.DownloadedFragment;
import com.jtech.imaging.view.fragment.DownloadingFragment;
import com.jtech.imaging.view.widget.RxCompat;
import com.jtechlib.view.activity.BaseActivity;
import com.jtechlib.view.fragment.BaseFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.Arrays;

import butterknife.Bind;
import rx.functions.Action1;

/**
 * 下载管理页
 * Created by jianghan on 2016/10/19.
 */

public class DownloadActivity extends BaseActivity implements DownloadContract.View, View.OnClickListener, ViewPager.OnPageChangeListener {

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
        //设置fab的点击事件
        RxCompat.clickThrottleFirst(floatingActionButton, new FabClick());
        //将tablayout设置给viewpager
        tabLayout.setupWithViewPager(viewPager);
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

    /**
     * fab的点击事件
     */
    private class FabClick implements Action1<Void> {
        @Override
        public void call(Void aVoid) {
            //判断当前的所在页面
            if (viewPager.getCurrentItem() == 0) {//已缓存
                downloadedFragment.showPhotoGallery();//点击浏览大图
            } else {//缓存中
                boolean isAllDownloading = downloadingFragment.isAllDownloading();
                if (isAllDownloading) {//正在下载
                    floatingActionButton.setImageResource(R.drawable.ic_stop_white_36dp);
                } else {
                    floatingActionButton.setImageResource(R.drawable.ic_play_arrow_white_36dp);
                }
                //发送暂停或者下载的消息(取反操作)
                EventBus.getDefault().post(new DownloadServeEvent(!isAllDownloading));
            }
        }
    }
}
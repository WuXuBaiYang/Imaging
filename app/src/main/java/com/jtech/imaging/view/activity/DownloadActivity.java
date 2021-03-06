package com.jtech.imaging.view.activity;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.jtech.imaging.R;
import com.jtech.imaging.model.event.DownloadedGalleryEvent;
import com.jtech.imaging.mvp.contract.DownloadContract;
import com.jtech.imaging.mvp.presenter.DownloadPresenter;
import com.jtech.imaging.util.Bus;
import com.jtech.imaging.view.adapter.DownloadPagerAdapter;
import com.jtech.imaging.view.fragment.DownloadedFragment;
import com.jtech.imaging.view.fragment.DownloadingFragment;
import com.jtech.imaging.view.widget.RxCompat;
import com.jtechlib.view.activity.BaseActivity;
import com.jtechlib.view.fragment.BaseFragment;
import com.umeng.analytics.MobclickAgent;

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
    @Bind(R.id.fab)
    FloatingActionButton floatingActionButton;
    @Bind(R.id.tablayout_download)
    TabLayout tabLayout;
    @Bind(R.id.viewpager)
    ViewPager viewPager;
    @Bind(R.id.content)
    CoordinatorLayout content;

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
        //设置适配器
        BaseFragment[] fragmentList = {DownloadedFragment.newInstance(), DownloadingFragment.newInstance()};
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
        //添加数据变化监听
        presenter.addDownloadStateChangeListener();
    }

    @Override
    public void onClick(View v) {
        //后退
        onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //设置监听
        presenter.addDownloadStateChangeListener();
        //友盟页面统计
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //友盟页面统计
        MobclickAgent.onPause(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (0 == position) {
            floatingActionButton.setImageResource(R.drawable.ic_photo_library_white_36dp);
        } else {
            setDownloadingState(presenter.hasDownloading() ? presenter.isAllDownloading() : false);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void setDownloadingState(boolean isAllDownloading) {
        if (0 == viewPager.getCurrentItem()) {
            return;
        }
        if (isAllDownloading) {
            floatingActionButton.setImageResource(R.drawable.ic_pause_black_18dp);
        } else {
            floatingActionButton.setImageResource(R.drawable.ic_play_arrow_white_36dp);
        }
    }

    /**
     * fab的点击事件
     */
    private class FabClick implements Action1<Void> {
        @Override
        public void call(Void aVoid) {
            int currentItem = viewPager.getCurrentItem();
            if (0 == currentItem) {
                Bus.get().post(new DownloadedGalleryEvent());
            } else {
                if (!presenter.hasDownloading()) {
                    Snackbar.make(content, "no download", Snackbar.LENGTH_SHORT).show();
                    return;
                }
//                if (presenter.hasIndeterminate()) {
//                    Snackbar.make(content, "waiting for download", Snackbar.LENGTH_SHORT).show();
//                    return;
//                }
                if (presenter.isAllDownloading()) {
                    presenter.stopAllDownload();
                } else {
                    presenter.startAllDownload();
                }
            }
        }
    }
}
package com.jtech.imaging.view.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.jtech.imaging.R;
import com.jtech.imaging.contract.DownloadContract;
import com.jtech.imaging.presenter.DownloadPresenter;
import com.jtech.imaging.view.adapter.DownloadPagerAdapter;
import com.jtech.imaging.view.fragment.DownloadedFragment;
import com.jtech.imaging.view.fragment.DownloadingFragment;
import com.jtech.imaging.view.widget.RxCompat;
import com.jtechlib.view.activity.BaseActivity;
import com.jtechlib.view.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

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
        List<BaseFragment> fragmentList = new ArrayList<>();
        fragmentList.add(DownloadedFragment.newInstance());
        fragmentList.add(DownloadingFragment.newInstance());
        viewPager.setAdapter(new DownloadPagerAdapter(getSupportFragmentManager(), fragmentList));
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
        // TODO: 2016/10/31  //页面切换，则切换fab的图标，同时fab的事件也发生变化
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
                // TODO: 2016/10/31 通知page浏览大图消息
            } else {//缓存中
                // TODO: 2016/10/31 通知page全部下载或者全部暂停消息
            }
        }
    }
}
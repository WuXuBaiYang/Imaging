package com.jtech.imaging.view.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.jtech.imaging.R;
import com.jtech.imaging.contract.DownloadContract;
import com.jtech.imaging.presenter.DownloadPresenter;
import com.jtechlib.view.activity.BaseActivity;
import com.jtechlib.view.widget.StatusBarCompat;

import butterknife.Bind;

/**
 * 下载管理页
 * Created by jianghan on 2016/10/19.
 */

public class DownloadActivity extends BaseActivity implements DownloadContract.View, View.OnClickListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.statusbar)
    View statusBar;
    @Bind(R.id.fab)
    FloatingActionButton floatingActionButton;

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
        //设置状态栏
        StatusBarCompat.setStatusBar(getActivity(), statusBar);
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onClick(View v) {
        //后退
        onBackPressed();
    }
}
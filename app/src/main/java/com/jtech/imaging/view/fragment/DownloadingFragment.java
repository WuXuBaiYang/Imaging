package com.jtech.imaging.view.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jtech.imaging.R;
import com.jtech.imaging.event.DownloadScheduleEvent;
import com.jtech.imaging.event.DownloadStateEvent;
import com.jtech.imaging.view.adapter.DownloadingAdapter;
import com.jtech.view.JRecyclerView;
import com.jtechlib.view.fragment.BaseFragment;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;

/**
 * 下载中page
 * Created by jianghan on 2016/10/31.
 */

public class DownloadingFragment extends BaseFragment {

    @Bind(R.id.jrecyclerview)
    JRecyclerView jRecyclerView;

    private DownloadingAdapter downloadingAdapter;

    public static DownloadingFragment newInstance() {
        Bundle args = new Bundle();
        DownloadingFragment fragment = new DownloadingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View createView(LayoutInflater layoutInflater, ViewGroup viewGroup) {
        return layoutInflater.inflate(R.layout.fragment_downloading, viewGroup, false);
    }

    @Override
    protected void initVariables(Bundle bundle) {

    }

    @Override
    protected void initViews(Bundle bundle) {
        //设置layoutmanager
        jRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //实例化适配器
        downloadingAdapter = new DownloadingAdapter(getActivity());
        //设置适配器
        jRecyclerView.setAdapter(downloadingAdapter);
    }

    @Override
    protected void loadData() {
        // TODO: 2016/11/1 加载本地记录的下载列表
    }

    /**
     * 判断是否全部正在下载的状态
     *
     * @return
     */
    public boolean isAllDownloading() {
        return true;
    }

    /**
     * 任务下载状态事件
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void downloadStateEvent(DownloadStateEvent event) {
        // TODO: 2016/11/1 当有任务下载完成，则刷新本页列表
    }

    /**
     * 任务下载进度事件
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void downloadingEvent(DownloadScheduleEvent event) {
        // TODO: 2016/11/1 任务下载中下载进度方法
    }
}
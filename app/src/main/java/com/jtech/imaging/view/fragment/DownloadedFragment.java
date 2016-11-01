package com.jtech.imaging.view.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jtech.imaging.R;
import com.jtech.imaging.event.DownloadStateEvent;
import com.jtech.imaging.view.adapter.DownloadedAdapter;
import com.jtech.view.JRecyclerView;
import com.jtechlib.view.fragment.BaseFragment;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;

/**
 * 已缓存page
 * Created by jianghan on 2016/10/31.
 */

public class DownloadedFragment extends BaseFragment {

    @Bind(R.id.jrecyclerview)
    JRecyclerView jRecyclerView;

    private DownloadedAdapter downloadedAdapter;

    public static DownloadedFragment newInstance() {
        Bundle args = new Bundle();
        DownloadedFragment fragment = new DownloadedFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View createView(LayoutInflater layoutInflater, ViewGroup viewGroup) {
        return layoutInflater.inflate(R.layout.fragment_downloaded, viewGroup, false);
    }

    @Override
    protected void initVariables(Bundle bundle) {

    }

    @Override
    protected void initViews(Bundle bundle) {
        //设置layoutmanager
        jRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //实例化适配器
        downloadedAdapter = new DownloadedAdapter(getActivity());
        //设置适配器
        jRecyclerView.setAdapter(downloadedAdapter);
    }

    @Override
    protected void loadData() {
        // TODO: 2016/11/1 加载本地缓存的已下载的图片列表
    }

    /**
     * 显示图片画廊
     */
    public void showPhotoGallery() {
        // TODO: 2016/11/1 已画廊的形式显示图片 
    }

    /**
     * 任务下载状态事件
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void downloadStateEvent(DownloadStateEvent event) {
        // TODO: 2016/11/1 当有任务下载完成或开始下载，则刷新本页列表
    }
}
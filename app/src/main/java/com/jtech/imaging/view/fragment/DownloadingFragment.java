package com.jtech.imaging.view.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jtech.imaging.R;
import com.jtech.imaging.model.DownloadModel;
import com.jtech.imaging.mvp.contract.DownloadingContract;
import com.jtech.imaging.mvp.presenter.DownloadingPresenter;
import com.jtech.imaging.view.adapter.DownloadingAdapter;
import com.jtech.view.JRecyclerView;
import com.jtechlib.view.fragment.BaseFragment;

import java.util.List;

import butterknife.Bind;

/**
 * 下载中page
 * Created by jianghan on 2016/10/31.
 */

public class DownloadingFragment extends BaseFragment implements DownloadingContract.View, DownloadingAdapter.OnDownloadingClickListener {

    @Bind(R.id.jrecyclerview)
    JRecyclerView jRecyclerView;

    private DownloadingAdapter downloadingAdapter;
    private DownloadingContract.Presenter presenter;

    @Override
    public View createView(LayoutInflater layoutInflater, ViewGroup viewGroup) {
        return layoutInflater.inflate(R.layout.fragment_downloading, viewGroup, false);
    }

    @Override
    protected void initVariables(Bundle bundle) {
        //实例化P类
        this.presenter = new DownloadingPresenter(getActivity(), this);
    }

    @Override
    protected void initViews(Bundle bundle) {
        //设置layoutmanager
        jRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //实例化适配器
        downloadingAdapter = new DownloadingAdapter(getActivity());
        //设置适配器
        jRecyclerView.setAdapter(downloadingAdapter);
        //设置下载列表点击事件
        downloadingAdapter.setOnDownloadingClickListener(this);
    }

    @Override
    protected void loadData() {
        presenter.getDownloadingList();
    }

    public static DownloadingFragment newInstance() {
        Bundle args = new Bundle();
        DownloadingFragment fragment = new DownloadingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void downloadingList(List<DownloadModel> downloadModels) {
        downloadingAdapter.setDatas(downloadModels);
    }

    @Override
    public void onStateClick(long id, int state) {
        // TODO: 2017/1/6 下载状态点击事件
    }
}
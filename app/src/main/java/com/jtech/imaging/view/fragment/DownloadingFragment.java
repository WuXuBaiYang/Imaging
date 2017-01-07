package com.jtech.imaging.view.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jtech.imaging.R;
import com.jtech.imaging.common.DownloadState;
import com.jtech.imaging.model.DownloadModel;
import com.jtech.imaging.mvp.contract.DownloadingContract;
import com.jtech.imaging.mvp.presenter.DownloadingPresenter;
import com.jtech.imaging.view.adapter.DownloadingAdapter;
import com.jtech.imaging.view.widget.dialog.DeleteDialog;
import com.jtech.listener.OnItemLongClickListener;
import com.jtech.view.JRecyclerView;
import com.jtech.view.RecyclerHolder;
import com.jtechlib.view.fragment.BaseFragment;

import java.util.List;

import butterknife.Bind;

/**
 * 下载中page
 * Created by jianghan on 2016/10/31.
 */

public class DownloadingFragment extends BaseFragment implements DownloadingContract.View, DownloadingAdapter.OnDownloadingClickListener, OnItemLongClickListener {

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
        //设置长点击事件
        jRecyclerView.setOnItemLongClickListener(this);
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
    public boolean onItemLongClick(RecyclerHolder recyclerHolder, View view, final int position) {
        DeleteDialog
                .build(getActivity())
                .setContent("Whether to delete download")
                .setDoneClick(new DeleteDialog.OnDeleteListener() {
                    @Override
                    public void delete() {
                        //获取任务id
                        long id = downloadingAdapter.getItem(position).getId();
                        //移除适配器中的数据
                        downloadingAdapter.removeData(position);
                        //删除数据库中的条目
                        presenter.deleteDownload(id);
                    }
                }).show();
        return true;
    }

    @Override
    public void onStateClick(long id, int state) {
        if (state == DownloadState.DOWNLOADING || state == DownloadState.DOWNLOAD_WAITING) {
            presenter.stopDownload(id);
        } else {
            presenter.startDownload(id);
        }
    }
}
package com.jtech.imaging.view.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jtech.imaging.R;
import com.jtech.imaging.mvp.contract.DownloadContract;
import com.jtech.imaging.model.DownloadModel;
import com.jtech.imaging.mvp.presenter.DownloadingPresenter;
import com.jtech.imaging.util.Tools;
import com.jtech.imaging.view.adapter.DownloadingAdapter;
import com.jtech.view.JRecyclerView;
import com.jtechlib.view.fragment.BaseFragment;

import java.util.List;

import butterknife.Bind;

/**
 * 下载中page
 * Created by jianghan on 2016/10/31.
 */

public class DownloadingFragment extends BaseFragment implements DownloadContract.DownloadingView {

    @Bind(R.id.jrecyclerview)
    JRecyclerView jRecyclerView;

    private DownloadContract.DownloadingPresenter presenter;
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
    }

    @Override
    protected void loadData() {
        presenter.getDownloadingTask();
    }

    /**
     * 判断是否全部正在下载的状态
     *
     * @return
     */
    public boolean isAllDownloading() {
        // TODO: 2016/11/17 判断是否已经全部变成正在下载状态
        return true;
    }

    @Override
    public void downloadTask(List<DownloadModel> downloadModels) {
        if (Tools.isDifferent(downloadModels, downloadingAdapter.getRealDatas())) {
            downloadingAdapter.setDatas(downloadModels);
        }
    }
}
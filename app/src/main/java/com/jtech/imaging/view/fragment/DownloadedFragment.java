package com.jtech.imaging.view.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jtech.imaging.R;
import com.jtech.imaging.contract.DownloadContract;
import com.jtech.imaging.model.DownloadModel;
import com.jtech.imaging.presenter.DownloadedPresenter;
import com.jtech.imaging.util.Tools;
import com.jtech.imaging.view.adapter.DownloadedAdapter;
import com.jtech.view.JRecyclerView;
import com.jtechlib.view.fragment.BaseFragment;

import java.util.List;

import butterknife.Bind;

/**
 * 已缓存page
 * Created by jianghan on 2016/10/31.
 */

public class DownloadedFragment extends BaseFragment implements DownloadContract.DownloadedView {

    @Bind(R.id.jrecyclerview)
    JRecyclerView jRecyclerView;

    private DownloadContract.DownloadedPresenter presenter;
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
        //实例化P类
        this.presenter = new DownloadedPresenter(getActivity(), this);
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
        presenter.getDownloadedTask();
    }

    /**
     * 显示图片画廊
     */
    public void showPhotoGallery() {
        // TODO: 2016/11/1 已画廊的形式显示图片 
    }

    @Override
    public void downloadTask(List<DownloadModel> downloadModels) {
        if (Tools.isDifferent(downloadModels, downloadedAdapter.getRealDatas())) {
            downloadedAdapter.setDatas(downloadModels);
        }
    }
}
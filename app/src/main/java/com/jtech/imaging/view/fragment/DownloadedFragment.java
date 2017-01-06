package com.jtech.imaging.view.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jtech.imaging.R;
import com.jtech.imaging.model.DownloadModel;
import com.jtech.imaging.mvp.contract.DownloadedContract;
import com.jtech.imaging.mvp.presenter.DownloadedPresenter;
import com.jtech.imaging.view.adapter.DownloadedAdapter;
import com.jtech.imaging.view.widget.dialog.DeleteDialog;
import com.jtech.listener.OnItemClickListener;
import com.jtech.listener.OnItemLongClickListener;
import com.jtech.view.JRecyclerView;
import com.jtech.view.RecyclerHolder;
import com.jtechlib.view.fragment.BaseFragment;

import java.util.List;

import butterknife.Bind;

/**
 * 已缓存page
 * Created by jianghan on 2016/10/31.
 */

public class DownloadedFragment extends BaseFragment implements DownloadedContract.View, OnItemClickListener, OnItemLongClickListener {

    @Bind(R.id.jrecyclerview)
    JRecyclerView jRecyclerView;

    private DownloadedAdapter downloadedAdapter;
    private DownloadedContract.Presenter presenter;

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
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        jRecyclerView.setLayoutManager(gridLayoutManager);
        //实例化适配器
        downloadedAdapter = new DownloadedAdapter(getActivity(), gridLayoutManager.getSpanCount());
        //设置适配器
        jRecyclerView.setAdapter(downloadedAdapter);
        //设置item 的点击事件
        jRecyclerView.setOnItemClickListener(this);
        //设置item的长点击事件
        jRecyclerView.setOnItemLongClickListener(this);
    }

    @Override
    protected void loadData() {
        presenter.getDownloadedList();
    }

    public static DownloadedFragment newInstance() {
        Bundle args = new Bundle();
        DownloadedFragment fragment = new DownloadedFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void downloadedList(List<DownloadModel> downloadModels) {
        downloadedAdapter.setDatas(downloadModels);
    }

    @Override
    public void onItemClick(RecyclerHolder recyclerHolder, View view, int i) {
        // TODO: 2017/1/6 点击跳转到画廊模式浏览大图
        //获取到点击的图片
        ImageView imageView = recyclerHolder.getImageView(R.id.imageview_photo);
        //发送消息
    }

    @Override
    public boolean onItemLongClick(RecyclerHolder recyclerHolder, View view, final int position) {
        DeleteDialog
                .build(getActivity())
                .setDoneClick(new DeleteDialog.OnDeleteListener() {
                    @Override
                    public void delete() {
                        //移除适配器数据
                        downloadedAdapter.removeData(position);
                        //移除数据库中的数据
                        // TODO: 2017/1/6 删除数据库中已下载的图片,以及本地图片
                    }
                })
                .show();
        return true;
    }
}
package com.jtech.imaging.view.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jtech.imaging.R;
import com.jtech.imaging.contract.DownloadContract;
import com.jtech.imaging.model.DownloadModel;
import com.jtech.imaging.presenter.DownloadedPresenter;
import com.jtech.imaging.util.Tools;
import com.jtech.imaging.view.adapter.DownloadedAdapter;
import com.jtech.listener.OnItemClickListener;
import com.jtech.listener.OnItemViewSwipeListener;
import com.jtech.view.JRecyclerView;
import com.jtech.view.RecyclerHolder;
import com.jtechlib.Util.DeviceUtils;
import com.jtechlib.view.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 已缓存page
 * Created by jianghan on 2016/10/31.
 */

public class DownloadedFragment extends BaseFragment implements DownloadContract.DownloadedView, OnItemViewSwipeListener {

    @Bind(R.id.jrecyclerview)
    JRecyclerView jRecyclerView;

    private DownloadContract.DownloadedPresenter presenter;
    private OnItemClickListener onItemClickListener;
    private GridLayoutManager gridLayoutManager;
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
        gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        jRecyclerView.setLayoutManager(gridLayoutManager);
        //实例化适配器
        downloadedAdapter = new DownloadedAdapter(getActivity());
        //设置item的宽高
        downloadedAdapter.setupItemWidth(DeviceUtils.getScreenWidth(getActivity()));
        //设置适配器
        jRecyclerView.setAdapter(downloadedAdapter);
        //设置item 的点击事件
        jRecyclerView.setOnItemClickListener(onItemClickListener);
        //设置item的滑动删除
        jRecyclerView.setSwipeEnd(true, this);
    }

    @Override
    protected void loadData() {
        presenter.getDownloadedTask();


        //假数据
        List<DownloadModel> downloadModels = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            DownloadModel downloadModel = new DownloadModel();
            downloadModel.setId(10000);
            downloadModel.setColor("#607563");
            downloadModel.setPath("http://h.hiphotos.baidu.com/zhidao/pic/item/6d81800a19d8bc3ed69473cb848ba61ea8d34516.jpg");
            downloadModels.add(downloadModel);
        }
        downloadTask(downloadModels);
    }

    /**
     * 是否存在图片
     *
     * @return
     */
    public boolean hasPhoto() {
        return null != downloadedAdapter && downloadedAdapter.getItemCount() > 0;
    }

    @Override
    public void downloadTask(List<DownloadModel> downloadModels) {
        if (Tools.isDifferent(downloadModels, downloadedAdapter.getRealDatas())) {
            downloadedAdapter.setDatas(downloadModels);
        }
    }

    @Override
    public void onItemViewSwipe(RecyclerView.ViewHolder viewHolder, int i) {
        DownloadModel downloadModel = downloadedAdapter.getItem(viewHolder.getAdapterPosition());
        downloadedAdapter.removeData(viewHolder.getAdapterPosition());
        if (i == ItemTouchHelper.END) {
            presenter.removeDownloaded(downloadModel.getId());
        }
    }

    /**
     * 设置item的点击事件
     *
     * @param onItemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * 获取item对象
     *
     * @param position
     * @return
     */
    public DownloadModel getModel(int position) {
        return downloadedAdapter.getItem(position);
    }

    /**
     * 获取图片视图
     *
     * @param recyclerHolder
     * @return
     */
    public View getView(RecyclerHolder recyclerHolder) {
        return downloadedAdapter.getImageView(recyclerHolder);
    }
}
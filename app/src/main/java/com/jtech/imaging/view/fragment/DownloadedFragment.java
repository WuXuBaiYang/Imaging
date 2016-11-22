package com.jtech.imaging.view.fragment;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.util.Pair;
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
import com.jtech.imaging.util.ActivityJump;
import com.jtech.imaging.util.Tools;
import com.jtech.imaging.view.activity.DownloadActivity;
import com.jtech.imaging.view.activity.GalleryActivity;
import com.jtech.imaging.view.activity.WallpaperActivity;
import com.jtech.imaging.view.adapter.DownloadedAdapter;
import com.jtech.listener.OnItemClickListener;
import com.jtech.listener.OnItemViewSwipeListener;
import com.jtech.view.JRecyclerView;
import com.jtech.view.RecyclerHolder;
import com.jtechlib.Util.BundleChain;
import com.jtechlib.Util.DeviceUtils;
import com.jtechlib.Util.PairChain;
import com.jtechlib.view.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 已缓存page
 * Created by jianghan on 2016/10/31.
 */

public class DownloadedFragment extends BaseFragment implements DownloadContract.DownloadedView, OnItemViewSwipeListener, OnItemClickListener {

    @Bind(R.id.jrecyclerview)
    JRecyclerView jRecyclerView;

    private DownloadContract.DownloadedPresenter presenter;
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
        jRecyclerView.setOnItemClickListener(this);
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
     * 显示图片画廊
     */
    public void showPhotoGallery() {
        if (downloadedAdapter.getItemCount() > 0) {
            Pair[] pairs = PairChain
                    .build(((DownloadActivity) getActivity()).getFab(), getString(R.string.fab))
                    .toArray();
            ActivityJump
                    .build(getActivity(), GalleryActivity.class)
                    .makeSceneTransitionAnimation(pairs)
                    .jump();
        } else {
            Snackbar.make(getContentView(), "none", Snackbar.LENGTH_SHORT).show();
        }
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

    @Override
    public void onItemClick(RecyclerHolder recyclerHolder, View view, int position) {
        Bundle bundle = BundleChain.build()
                .putSerializable(WallpaperActivity.IMAGE_LOCAL_PATH_KEY, downloadedAdapter.getItem(position))
                .toBundle();
        Pair[] pairs = PairChain
                .build(((DownloadActivity) getActivity()).getFab(), getString(R.string.fab))
                .build(downloadedAdapter.getImageView(recyclerHolder), getString(R.string.image))
                .toArray();
        ActivityJump.build(getActivity(), WallpaperActivity.class)
                .addBundle(bundle)
                .makeSceneTransitionAnimation(pairs)
                .jump();
    }
}
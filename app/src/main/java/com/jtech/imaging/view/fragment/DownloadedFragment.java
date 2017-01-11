package com.jtech.imaging.view.fragment;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.util.Pair;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jtech.imaging.R;
import com.jtech.imaging.model.DownloadModel;
import com.jtech.imaging.model.event.DownloadedGalleryEvent;
import com.jtech.imaging.mvp.contract.DownloadedContract;
import com.jtech.imaging.mvp.presenter.DownloadedPresenter;
import com.jtech.imaging.util.ActivityJump;
import com.jtech.imaging.util.Bus;
import com.jtech.imaging.view.activity.GalleryActivity;
import com.jtech.imaging.view.adapter.DownloadedAdapter;
import com.jtech.imaging.view.widget.dialog.DeleteDialog;
import com.jtech.listener.OnItemClickListener;
import com.jtech.listener.OnItemLongClickListener;
import com.jtech.view.JRecyclerView;
import com.jtech.view.RecyclerHolder;
import com.jtechlib.Util.BundleChain;
import com.jtechlib.view.fragment.BaseFragment;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.Bind;

/**
 * 已缓存page
 * Created by jianghan on 2016/10/31.
 */

public class DownloadedFragment extends BaseFragment implements DownloadedContract.View, OnItemClickListener, OnItemLongClickListener, DownloadedAdapter.OnDownloadedClickListener {

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
        //上车
        Bus.getOn(this);
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
        //设置列表的点击事件
        downloadedAdapter.setOnDownloadedClickListener(this);
    }

    @Override
    protected void loadData() {
        presenter.addDownloadedListener();
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
    public void onResume() {
        super.onResume();
        //设置监听
        presenter.addDownloadedListener();
    }

    @Override
    public void onItemClick(RecyclerHolder recyclerHolder, View view, int position) {
        //跳转到图片浏览的指定位置
        Bundle bundle = BundleChain.build()
                .putStringArrayList(GalleryActivity.KEY_PHOTO_LIST, downloadedAdapter.getImageUris())
                .putInt(GalleryActivity.KEY_PHOTO_INDEX, position)
                .toBundle();
        Pair[] pairs = {};
        ActivityJump.build(getActivity(), GalleryActivity.class)
                .addBundle(bundle)
                .makeSceneTransitionAnimation(pairs)
                .jump();
    }

    /**
     * 已下载列表上的fab点击事件
     */
    @Subscribe
    public void onGalleryClick(DownloadedGalleryEvent event) {
        if (downloadedAdapter.getItemCount() <= 0) {
            Snackbar.make(getContentView(), "no photos", Snackbar.LENGTH_SHORT).show();
            return;
        }
        Bundle bundle = BundleChain.build()
                .putStringArrayList(GalleryActivity.KEY_PHOTO_LIST, downloadedAdapter.getImageUris())
                .putInt(GalleryActivity.KEY_PHOTO_INDEX, 0)
                .toBundle();
        Pair[] pairs = {};
        ActivityJump.build(getActivity(), GalleryActivity.class)
                .addBundle(bundle)
                .makeSceneTransitionAnimation(pairs)
                .jump();
    }

    @Override
    public boolean onItemLongClick(RecyclerHolder recyclerHolder, View view, final int position) {
        DeleteDialog
                .build(getActivity())
                .setContent("Whether to delete photo")
                .setDoneClick(new DeleteDialog.OnDeleteListener() {
                    @Override
                    public void delete() {
                        //获取任务id
                        long id = downloadedAdapter.getItem(position).getId();
                        //移除适配器数据
                        downloadedAdapter.removeData(position);
                        //移除数据库中的数据
                        presenter.deleteDownloaded(id);
                    }
                }).show();
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //下车
        Bus.getOff(this);
    }
}
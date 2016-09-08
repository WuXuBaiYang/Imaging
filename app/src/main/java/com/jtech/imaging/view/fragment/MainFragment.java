package com.jtech.imaging.view.fragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.jakewharton.rxbinding.view.RxView;
import com.jtech.imaging.R;
import com.jtech.imaging.common.Constants;
import com.jtech.imaging.contract.MainContract;
import com.jtech.imaging.model.PhotoModel;
import com.jtech.imaging.realm.OauthRealm;
import com.jtech.imaging.view.adapter.PhotoAdapter;
import com.jtech.imaging.view.fragment.base.BaseFragment;
import com.jtech.imaging.view.widget.StatusBarCompat;
import com.jtech.listener.OnItemClickListener;
import com.jtech.listener.OnLoadListener;
import com.jtech.view.JRecyclerView;
import com.jtech.view.RecyclerHolder;
import com.jtech.view.RefreshLayout;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import rx.functions.Action1;

/**
 * 测试用fragment
 * Created by wuxubaiyang on 16/4/16.
 */
public class MainFragment extends BaseFragment<MainContract.Presenter> implements MainContract.View
        , RefreshLayout.OnRefreshListener
        , OnItemClickListener
        , OnLoadListener {


    // 默认排序设置为最新
    private String orderBy = Constants.ORDER_BY_LATEST;

    @Bind(R.id.fab)
    FloatingActionButton floatingActionButton;
    @Bind(R.id.refreshlayout)
    RefreshLayout refreshLayout;
    @Bind(R.id.jrecyclerview)
    JRecyclerView jRecyclerView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private PhotoAdapter photoAdapter;

    /**
     * 创建视图的方法
     *
     * @param inflater
     * @param container
     * @return
     */
    @Override
    public View createView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    public static MainFragment newInstance() {
        Bundle args = new Bundle();
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * 入口方法
     *
     * @param bundle
     */
    @Override
    public void init(Bundle bundle) {
        //设置状态栏占位
        StatusBarCompat.compat(getActivity());
        //设置标题栏
        setupToolbar(toolbar)
                .setTitle(R.string.app_name)
                .setNavigationIcon(R.drawable.ic_menu_white_24dp);
        //fab点击
        RxView.clicks(floatingActionButton)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(new FabClick());
        //设置列表
        setupRecyclerview();
    }

    /**
     * 设置列表
     */
    private void setupRecyclerview() {
        //设置layoutmanager
        jRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //设置适配器
        photoAdapter = new PhotoAdapter(getActivity());
        jRecyclerView.setAdapter(photoAdapter);
        //开启下拉刷新
        jRecyclerView.setLoadMore(true);
        //设置点击事件
        jRecyclerView.setOnLoadListener(this);
        refreshLayout.setOnRefreshListener(this);
        jRecyclerView.setOnItemClickListener(this);
        //发起下拉刷新
        refreshLayout.startRefreshing();
    }

    @Override
    public void onItemClick(RecyclerHolder recyclerHolder, View view, int position) {

    }

    @Override
    public void onRefresh() {
        getPresenter().requestPhotoList(photoAdapter.getPage(false)
                , photoAdapter.getDisplayNumber()
                , orderBy
                , false);
    }

    @Override
    public void loadMore() {
        getPresenter().requestPhotoList(photoAdapter.getPage(true)
                , photoAdapter.getDisplayNumber()
                , orderBy
                , true);
    }

    @Override
    public void success(List<PhotoModel> photoModels, boolean loadMore) {
        refreshLayout.refreshingComplete();
        jRecyclerView.setLoadCompleteState();
        photoAdapter.setDatas(photoModels, loadMore);
    }

    @Override
    public void fail(String message) {
        refreshLayout.refreshingComplete();
        jRecyclerView.setLoadCompleteState();
        Snackbar.make(getContentView(), message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_main_search://搜索按钮
                Snackbar.make(getContentView(), "搜索按钮", Snackbar.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    /**
     * fab的点击事件
     */
    private class FabClick implements Action1<Void> {
        @Override
        public void call(Void aVoid) {
            OauthRealm.getInstance().removeOauthModel();
            getActivity().finish();
        }
    }
}
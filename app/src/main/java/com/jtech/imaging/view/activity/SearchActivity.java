package com.jtech.imaging.view.activity;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.jtech.imaging.R;
import com.jtech.imaging.contract.SearchContract;
import com.jtech.imaging.model.SearchPhotoModel;
import com.jtech.imaging.presenter.SearchPresenter;
import com.jtech.imaging.view.widget.CoverView;
import com.jtech.imaging.view.widget.RxCompat;
import com.jtech.listener.OnItemClickListener;
import com.jtech.listener.OnLoadListener;
import com.jtech.view.JRecyclerView;
import com.jtech.view.RecyclerHolder;
import com.jtech.view.RefreshLayout;
import com.jtechlib.view.activity.BaseActivity;
import com.jtechlib.view.widget.StatusBarCompat;

import butterknife.Bind;
import rx.functions.Action1;

/**
 * 搜索页
 * Created by jianghan on 2016/9/27.
 */

public class SearchActivity extends BaseActivity implements SearchContract.View, SearchView.OnQueryTextListener, View.OnFocusChangeListener, View.OnClickListener, RefreshLayout.OnRefreshListener, OnLoadListener, OnItemClickListener, CoverView.OnCoverCancelListener {

    public static final String SEARCH_QUERY_KEY = "searchQuerykey";

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.refreshlayout)
    RefreshLayout refreshLayout;
    @Bind(R.id.jrecyclerview)
    JRecyclerView jRecyclerView;
    @Bind(R.id.content)
    CoordinatorLayout content;
    @Bind(R.id.fab)
    FloatingActionButton floatingActionButton;
    @Bind(R.id.statusbar)
    View statusBar;
    @Bind(R.id.content_cover)
    CoverView coverView;

    private SearchContract.Presenter presenter;
    private SearchView searchView;
    private String query;

    @Override
    protected void initVariables(Bundle bundle) {
        //绑定P类
        presenter = new SearchPresenter(this);
        //获取搜索信息
        this.query = bundle.getString(SEARCH_QUERY_KEY, "");
    }

    @Override
    protected void initViews(Bundle bundle) {
        setContentView(R.layout.activity_search);
        //设置toolbar
        setupToolbar(toolbar)
                .setContentInsetStartWithNavigation(0)
                .setNavigationIcon(R.drawable.ic_keyboard_backspace_white_24dp, this);
        //设置状态栏
        StatusBarCompat.setStatusBar(getActivity(), statusBar);
        // TODO: 2016/9/27 设置适配器
        //设置layoutmanagaer
        jRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //设置下拉刷新
        refreshLayout.setOnRefreshListener(this);
        //设置加载更多
        jRecyclerView.setOnLoadListener(this);
        //设置图片点击事件
        jRecyclerView.setOnItemClickListener(this);
        //列表滚动事件
        jRecyclerView.addOnScrollListener(new OnScrollListener());
        //设置fab的点击事件
        RxCompat.clickThrottleFirst(floatingActionButton, new FabClick());
        //设置覆盖层取消监听
        coverView.setOnCoverCancelListener(this);
    }

    @Override
    protected void loadData() {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //实例化menu
        toolbar.inflateMenu(R.menu.menu_search);
        //获取到搜索框的视图
        MenuItem menuItem = menu.findItem(R.id.menu_search_search);
        searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        //设置搜索确定监听
        searchView.setOnQueryTextListener(this);
        //设置焦点变化监听
        searchView.setOnQueryTextFocusChangeListener(this);
        //设置默认信息
        searchView.setQueryHint(getResources().getString(R.string.search_hint));
        //设置搜索内容
        searchView.setQuery(query, true);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        if (!TextUtils.isEmpty(query.trim())) {
            this.query = query;
            //设置标题栏
            toolbar.setTitle(query);
            //关闭搜索状态
            searchView.onActionViewCollapsed();
            //发起下拉刷新
            refreshLayout.startRefreshing();
            return true;
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus) {
            //失去焦点则收回搜索框
            searchView.onActionViewCollapsed();
            //隐藏覆盖层
            coverView.hideContentCover();
            //显示fab
            floatingActionButton.show();
        } else {
            //显示覆盖层
            coverView.showContentCover();
            //隐藏fab
            floatingActionButton.hide();
        }
    }

    @Override
    public void onClick(View v) {
        onBackPressed();
    }

    @Override
    public void success(SearchPhotoModel searchPhotoModel, boolean loadMore) {
        refreshLayout.refreshingComplete();
        jRecyclerView.setLoadCompleteState();
        // TODO: 2016/9/27 设置适配器
    }

    @Override
    public void fail(String message) {
        refreshLayout.refreshingComplete();
        jRecyclerView.setLoadCompleteState();
        Snackbar.make(content, message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityCompat.finishAfterTransition(getActivity());
    }

    @Override
    public void onRefresh() {
        presenter.searchPhotoList(query, 0, false);
    }

    @Override
    public void loadMore() {
        presenter.searchPhotoList(query, 0, true);
    }

    @Override
    public void onItemClick(RecyclerHolder recyclerHolder, View view, int i) {
        // TODO: 2016/9/27 点击跳转到详情
    }

    @Override
    public void onCancel() {
        searchView.onActionViewCollapsed();
    }

    /**
     * fab的点击事件
     */
    private class FabClick implements Action1<Void> {
        @Override
        public void call(Void aVoid) {
            // TODO: 2016/9/27 点击弹出图片浏览样式选择
        }
    }

    /**
     * 列表的滚动监听
     */
    private class OnScrollListener extends RecyclerView.OnScrollListener {
        private boolean fabShowing = true;

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            //视差滚动
            // TODO: 2016/9/27 视差滚动
//            photoAdapter.animateImage(recyclerView);
            //隐藏或显示fab
            if (dy > 0 && fabShowing) {
                fabShowing = false;
                floatingActionButton.hide();
            } else if (dy < 0 && !fabShowing) {
                fabShowing = true;
                floatingActionButton.show();
            }
        }
    }
}
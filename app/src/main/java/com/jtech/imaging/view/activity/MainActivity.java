package com.jtech.imaging.view.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.jakewharton.rxbinding.view.RxView;
import com.jtech.imaging.R;
import com.jtech.imaging.cache.OrderByCache;
import com.jtech.imaging.cache.PhotoCache;
import com.jtech.imaging.contract.MainContract;
import com.jtech.imaging.model.PhotoModel;
import com.jtech.imaging.presenter.MainPresenter;
import com.jtech.imaging.strategy.PhotoLoadStrategy;
import com.jtech.imaging.view.adapter.PhotoAdapter;
import com.jtech.imaging.view.widget.CoverView;
import com.jtech.listener.OnItemClickListener;
import com.jtech.listener.OnLoadListener;
import com.jtech.view.JRecyclerView;
import com.jtech.view.RecyclerHolder;
import com.jtech.view.RefreshLayout;
import com.jtechlib.view.activity.BaseActivity;
import com.jtechlib.view.widget.StatusBarCompat;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 主页
 * Created by jianghan on 2016/9/20.
 */
public class MainActivity extends BaseActivity implements MainContract.View, RefreshLayout.OnRefreshListener, OnItemClickListener, OnLoadListener, Toolbar.OnMenuItemClickListener, SearchView.OnQueryTextListener, View.OnFocusChangeListener, CoverView.OnCoverCancelListener {

    @Bind(R.id.fab)
    FloatingActionButton floatingActionButton;
    @Bind(R.id.refreshlayout)
    RefreshLayout refreshLayout;
    @Bind(R.id.jrecyclerview)
    JRecyclerView jRecyclerView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.statusbar)
    View statusBar;
    @Bind(R.id.content)
    CoordinatorLayout content;
    @Bind(R.id.content_cover)
    CoverView coverView;

    private SearchView searchView;
    private PhotoAdapter photoAdapter;
    private MainContract.Presenter presenter;

    @Override
    protected void initVariables(Bundle bundle) {
        //绑定VP
        presenter = new MainPresenter(getActivity(), this);
    }

    @Override
    protected void initViews(Bundle bundle) {
        setContentView(R.layout.activity_main);
        //设置标题
        toolbar.setTitle(OrderByCache.get(getActivity()).getOrderByString());
        //设置标题栏
        setupToolbar(toolbar)
                .setTitleTextColor(R.color.toolbar_title)
                .setOnMenuItemClickListener(this);
        //设置状态栏
        StatusBarCompat.setStatusBar(getActivity(), statusBar);
        //fab点击
        RxView.clicks(floatingActionButton)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(new FabClick());
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
        jRecyclerView.addOnScrollListener(new OnScrollListener());
        //设置覆盖层取消事件
        coverView.setOnCoverCancelListener(this);
    }

    @Override
    protected void loadData() {
        //加载缓存数据，没有则下拉刷新
        Observable.just("")
                .subscribeOn(Schedulers.io())
                .map(new Func1<String, List<PhotoModel>>() {
                    @Override
                    public List<PhotoModel> call(String s) {
                        return PhotoCache.get(getActivity()).getFirstPagePhotos();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<PhotoModel>>() {
                    @Override
                    public void call(List<PhotoModel> photoModels) {
                        if (null != photoModels) {//设置缓存
                            photoAdapter.setDatas(photoModels);
                        } else {//发起下拉刷新
                            refreshLayout.startRefreshing();
                        }
                    }
                });
    }

    /**
     * 显示排序对话框
     */
    @Override
    public void showSortDialog() {
        String[] sorts = getResources().getStringArray(R.array.sort);
        new AlertDialog
                .Builder(getActivity())
                .setSingleChoiceItems(sorts, OrderByCache.get(getActivity()).getOrderByIndex(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //关闭当前的dialog
                        dialog.dismiss();
                        //记录当前的排序
                        OrderByCache.get(getActivity()).setOrderBy(which);
                        //设置标题栏
                        toolbar.setTitle(OrderByCache.get(getActivity()).getOrderByString());
                        //刷新列表
                        refreshLayout.startRefreshing();
                        //滚动到首位
                        jRecyclerView.getLayoutManager().scrollToPosition(0);
                    }
                }).show();
    }

    /**
     * 显示图片加载策略选择对话框
     */
    @Override
    public void showImageLoadStrategyDialog() {
        String[] imageLoadStrategies = getResources().getStringArray(R.array.image_load_strategy);
        final int photoLoadStrategy = PhotoCache.get(getActivity()).getPhotoLoadStrategy();
        int checkedItem = 0;
        switch (photoLoadStrategy) {
            case PhotoLoadStrategy.PHOTO_LOAD_STRATEGY_FIXED_FULL://全尺寸
            case PhotoLoadStrategy.PHOTO_LOAD_STRATEGY_FIXED_1080://最高1080
            case PhotoLoadStrategy.PHOTO_LOAD_STRATEGY_FIXED_720://最高720
            case PhotoLoadStrategy.PHOTO_LOAD_STRATEGY_FIXED_480://最高480
            case PhotoLoadStrategy.PHOTO_LOAD_STRATEGY_FIXED_200://最高200
                checkedItem = 0;
                break;
            case PhotoLoadStrategy.PHOTO_LOAD_STRATEGY_NET_AUTO://根据网络自动调整
                checkedItem = 1;
                break;
            case PhotoLoadStrategy.PHOTO_LOAD_STRATEGY_AUTO://无视网络自动调整
                checkedItem = 2;
                break;
        }
        new AlertDialog.Builder(getActivity())
                .setSingleChoiceItems(imageLoadStrategies, checkedItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //关闭对话框
                        dialog.dismiss();
                        //设置选择的策略
                        if (which == 0) {
                            showImageLoadStrategyFixedDialog(photoLoadStrategy);
                        } else {
                            int photoLoadStrategy = 0;
                            if (which == 1) {
                                photoLoadStrategy = PhotoLoadStrategy.PHOTO_LOAD_STRATEGY_NET_AUTO;
                            } else if (which == 2) {
                                photoLoadStrategy = PhotoLoadStrategy.PHOTO_LOAD_STRATEGY_AUTO;
                            }
                            //清空欢迎页数据
                            PhotoCache.get(getActivity()).clearWelcomeUrl();
                            //存储策略
                            PhotoCache.get(getActivity()).setPhotoLoadStrategy(photoLoadStrategy);
                            //刷新列表
                            photoAdapter.notifyDataSetChanged();
                            photoAdapter.animateImage(jRecyclerView);
                        }
                    }
                }).show();
    }

    /**
     * 显示图片加载策略选择的固定模式对话框
     */
    @Override
    public void showImageLoadStrategyFixedDialog(int photoLoadStrategy) {
        String[] imageLoadStrategyFixeds = getResources().getStringArray(R.array.image_load_strategy_fixed);
        int checkedItem = 0;
        switch (photoLoadStrategy) {
            case PhotoLoadStrategy.PHOTO_LOAD_STRATEGY_FIXED_FULL://全尺寸
                checkedItem = 0;
                break;
            case PhotoLoadStrategy.PHOTO_LOAD_STRATEGY_FIXED_1080://最高1080
                checkedItem = 1;
                break;
            case PhotoLoadStrategy.PHOTO_LOAD_STRATEGY_FIXED_720://最高720
                checkedItem = 2;
                break;
            case PhotoLoadStrategy.PHOTO_LOAD_STRATEGY_FIXED_480://最高480
                checkedItem = 3;
                break;
            case PhotoLoadStrategy.PHOTO_LOAD_STRATEGY_FIXED_200://最高200
                checkedItem = 4;
                break;
        }
        new AlertDialog.Builder(getActivity())
                .setSingleChoiceItems(imageLoadStrategyFixeds, checkedItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //关闭对话框
                        dialog.dismiss();
                        int photoLoadStrategy = 0;
                        //设置策略
                        switch (which) {
                            case 0://全尺寸
                                photoLoadStrategy = PhotoLoadStrategy.PHOTO_LOAD_STRATEGY_FIXED_FULL;
                                break;
                            case 1://最高1080
                                photoLoadStrategy = PhotoLoadStrategy.PHOTO_LOAD_STRATEGY_FIXED_1080;
                                break;
                            case 2://最高720
                                photoLoadStrategy = PhotoLoadStrategy.PHOTO_LOAD_STRATEGY_FIXED_720;
                                break;
                            case 3://最高480
                                photoLoadStrategy = PhotoLoadStrategy.PHOTO_LOAD_STRATEGY_FIXED_480;
                                break;
                            case 4://最高200
                                photoLoadStrategy = PhotoLoadStrategy.PHOTO_LOAD_STRATEGY_FIXED_200;
                                break;
                        }
                        //清空欢迎页数据
                        PhotoCache.get(getActivity()).clearWelcomeUrl();
                        //存储策略
                        PhotoCache.get(getActivity()).setPhotoLoadStrategy(photoLoadStrategy);
                        //刷新列表
                        photoAdapter.notifyDataSetChanged();
                        photoAdapter.animateImage(jRecyclerView);
                    }
                }).show();
    }

    @Override
    public void onItemClick(RecyclerHolder recyclerHolder, View view, int position) {
        // TODO: 2016/9/26 点击跳转到详情 
    }

    @Override
    public void onRefresh() {
        presenter.requestPhotoList(photoAdapter.getPage(false)
                , photoAdapter.getDisplayNumber()
                , OrderByCache.get(getActivity()).getOrderByLowerCase()
                , false);
    }

    @Override
    public void loadMore() {
        presenter.requestPhotoList(photoAdapter.getPage(true)
                , photoAdapter.getDisplayNumber()
                , OrderByCache.get(getActivity()).getOrderByLowerCase()
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
        Snackbar.make(content, message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //实例化menu
        toolbar.inflateMenu(R.menu.menu_main);
        //获取到搜索框的视图
        MenuItem menuItem = menu.findItem(R.id.menu_main_search);
        searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        //设置默认信息
        searchView.setQueryHint(getResources().getString(R.string.search_hint));
        //设置搜索确定监听
        searchView.setOnQueryTextListener(this);
        //设置焦点变化监听
        searchView.setOnQueryTextFocusChangeListener(this);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        //收回搜索框
        searchView.onActionViewCollapsed();
        switch (item.getItemId()) {
            case R.id.menu_main_sort://排序
                showSortDialog();
                break;
            case R.id.menu_main_imagesize://图片加载策略
                showImageLoadStrategyDialog();
                break;
        }
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        if (!TextUtils.isEmpty(query.trim())) {
            //跳转到搜索页
            Bundle bundle = new Bundle();
            bundle.putString(SearchActivity.SEARCH_QUERY_KEY, query);
            ActivityOptionsCompat activityOptionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                            floatingActionButton, getString(R.string.fab));
            Intent intent = new Intent(getActivity(), SearchActivity.class);
            intent.putExtras(bundle);
            ActivityCompat.startActivity(getActivity(), intent, activityOptionsCompat.toBundle());
            //收回搜索框
            searchView.onActionViewCollapsed();
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
    public void onCancel() {
        searchView.onActionViewCollapsed();
    }

    /**
     * 列表的滚动监听
     */
    private class OnScrollListener extends RecyclerView.OnScrollListener {
        private boolean fabShowing = true;

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            //视差滚动
            photoAdapter.animateImage(recyclerView);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //动画后关闭activity
        ActivityCompat.finishAffinity(getActivity());
    }

    /**
     * fab的点击事件
     */
    private class FabClick implements Action1<Void> {
        @Override
        public void call(Void aVoid) {
            //跳转到主页
            ActivityOptionsCompat activityOptionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                            floatingActionButton, getString(R.string.fab));
            ActivityCompat.startActivity(getActivity(), new Intent(getActivity(),
                    RandomActivity.class), activityOptionsCompat.toBundle());
        }
    }
}
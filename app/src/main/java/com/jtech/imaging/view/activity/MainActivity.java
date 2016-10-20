package com.jtech.imaging.view.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.util.Pair;
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
import com.jtech.imaging.cache.OrderByCache;
import com.jtech.imaging.cache.PhotoCache;
import com.jtech.imaging.contract.MainContract;
import com.jtech.imaging.model.PhotoModel;
import com.jtech.imaging.presenter.MainPresenter;
import com.jtech.imaging.strategy.PhotoLoadStrategy;
import com.jtech.imaging.util.ActivityJump;
import com.jtech.imaging.util.PairChain;
import com.jtech.imaging.view.adapter.LoadMoreFooterAdapter;
import com.jtech.imaging.view.adapter.PhotoAdapter;
import com.jtech.imaging.view.widget.CoverView;
import com.jtech.imaging.view.widget.RxCompat;
import com.jtech.imaging.view.widget.dialog.ImageLoadStrategyDialog;
import com.jtech.imaging.view.widget.dialog.ImageLoadStrategyFixedDialog;
import com.jtech.imaging.view.widget.dialog.PhotoSortDialog;
import com.jtech.imaging.view.widget.popup.SearchRecordPopup;
import com.jtech.listener.OnItemClickListener;
import com.jtech.listener.OnLoadListener;
import com.jtech.view.JRecyclerView;
import com.jtech.view.RecyclerHolder;
import com.jtech.view.RefreshLayout;
import com.jtechlib.Util.DeviceUtils;
import com.jtechlib.view.activity.BaseActivity;
import com.jtechlib.view.widget.StatusBarCompat;

import java.util.List;

import butterknife.Bind;
import rx.functions.Action1;

/**
 * 主页
 * Created by jianghan on 2016/9/20.
 */
public class MainActivity extends BaseActivity implements MainContract.View, RefreshLayout.OnRefreshListener, OnItemClickListener, OnLoadListener, Toolbar.OnMenuItemClickListener, SearchView.OnQueryTextListener, View.OnFocusChangeListener, CoverView.OnCoverCancelListener, SearchRecordPopup.OnSearchRecordClick {

    private static final int REQUEST_PHOTO_DETAIL_CODE = 0x0123;

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
    private SearchRecordPopup searchRecordPopup;

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
        RxCompat.clickThrottleFirst(floatingActionButton, new FabClick());
        //实例化popup
        searchRecordPopup = new SearchRecordPopup(getActivity());
        //设置搜索事件
        searchRecordPopup.setOnSearchRecordClick(this);
        //设置layoutmanager
        jRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //设置适配器
        photoAdapter = new PhotoAdapter(getActivity(), DeviceUtils.getScreenWidth(getActivity()));
        jRecyclerView.setAdapter(photoAdapter, new LoadMoreFooterAdapter());
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
        presenter.requestCachePhotoList();
    }

    /**
     * 显示排序对话框
     */
    @Override
    public void showSortDialog() {
        PhotoSortDialog
                .build(getActivity())
                .setSingleChoiceItems(new OnSordDialogClick())
                .show();
    }

    /**
     * 显示图片加载策略选择对话框
     */
    @Override
    public void showImageLoadStrategyDialog() {
        ImageLoadStrategyDialog
                .build(getActivity())
                .setSingleChoiceItems(new OnImageLoadStrategyDialogClick())
                .show();
    }

    /**
     * 显示图片加载策略选择的固定模式对话框
     */
    @Override
    public void showImageLoadStrategyFixedDialog(int photoLoadStrategy) {
        ImageLoadStrategyFixedDialog
                .build(getActivity(), photoLoadStrategy)
                .setSingleChoiceItems(new OnImageLoadStrategyFixedDialogClick())
                .show();
    }

    @Override
    public void jumpToDownloadManager() {
        Pair[] pairs = PairChain
                .build(floatingActionButton, getString(R.string.fab))
                .toArray();
        ActivityJump.build(getActivity(), DownloadActivity.class)
                .makeSceneTransitionAnimation(pairs)
                .jump();
    }

    @Override
    public void onItemClick(RecyclerHolder recyclerHolder, View view, int position) {
        PhotoModel photoModel = photoAdapter.getItem(position);
        Bundle bundle = new Bundle();
        bundle.putString(PhotoDetailActivity.IMAGE_ID_KEY, photoModel.getId());
        bundle.putString(PhotoDetailActivity.IMAGE_NAME_KEY, photoModel.getUser().getName());
        bundle.putString(PhotoDetailActivity.IMAGE_URL_KEY, photoModel.getUrls().getRaw());
        Pair[] pairs = PairChain
                .build(floatingActionButton, getString(R.string.fab))
                .addPair(photoAdapter.getParallaxView(recyclerHolder), getString(R.string.image))
                .toArray();
        ActivityJump.build(getActivity(), PhotoDetailActivity.class)
                .addBundle(bundle)
                .makeSceneTransitionAnimation(pairs)
                .jumpForResult(REQUEST_PHOTO_DETAIL_CODE);
    }

    @Override
    public void onRecordClick(String keyword) {
        searchSubmit(keyword);
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
    public void cacheSuccess(List<PhotoModel> photoModels) {
        if (null != photoModels) {//设置缓存
            photoAdapter.setDatas(photoModels);
        } else {//发起下拉刷新
            refreshLayout.startRefreshing();
        }
    }

    @Override
    public void success(List<PhotoModel> photoModels, boolean loadMore) {
        refreshLayout.refreshingComplete();
        jRecyclerView.setLoadCompleteState();
        photoAdapter.setDatas(photoModels, loadMore);
    }

    @Override
    public void fail(String message) {
        jRecyclerView.setLoadFailState();
        refreshLayout.refreshingComplete();
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
            case R.id.menu_main_download://下载
                jumpToDownloadManager();
                break;
            default:
                break;
        }

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        if (searchSubmit(query)) {
            //存储搜索记录
            searchRecordPopup.addSearchRecord(query);
            return true;
        }
        return false;
    }

    /**
     * 搜索提交
     *
     * @param query
     * @return
     */
    private boolean searchSubmit(String query) {
        if (!TextUtils.isEmpty(query.trim())) {
            //跳转到搜索页
            Bundle bundle = new Bundle();
            bundle.putString(SearchActivity.SEARCH_QUERY_KEY, query);
            Pair[] pairs = PairChain
                    .build(floatingActionButton, getString(R.string.fab))
                    .toArray();
            ActivityJump.build(getActivity(), SearchActivity.class)
                    .addBundle(bundle)
                    .makeSceneTransitionAnimation(pairs)
                    .jump();
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
            //取消popup
            searchRecordPopup.dismiss();
        } else {
            //显示覆盖层
            coverView.showContentCover();
            //隐藏fab
            floatingActionButton.hide();
            //显示popup
            searchRecordPopup.showSearchRecord(toolbar);
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

    /**
     * 排序对话框点击
     */
    private class OnSordDialogClick implements DialogInterface.OnClickListener {
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
    }

    /**
     * 图片加载策略对话框点击
     */
    private class OnImageLoadStrategyDialogClick implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            //关闭对话框
            dialog.dismiss();
            //设置选择的策略
            if (which == 0) {
                showImageLoadStrategyFixedDialog(PhotoCache.get(getActivity()).getPhotoLoadStrategy());
            } else {
                int photoLoadStrategy = 0;
                if (which == 1) {
                    photoLoadStrategy = PhotoLoadStrategy.PHOTO_LOAD_STRATEGY_NET_AUTO;
                } else if (which == 2) {
                    photoLoadStrategy = PhotoLoadStrategy.PHOTO_LOAD_STRATEGY_AUTO;
                }
                //清空欢迎页数据
                PhotoCache.get(getActivity()).clearWelcomePhoto();
                //存储策略
                PhotoCache.get(getActivity()).setPhotoLoadStrategy(photoLoadStrategy);
                //刷新列表
                photoAdapter.notifyItemRangeChanged(0, photoAdapter.getItemCount());
                photoAdapter.animateImage(jRecyclerView);
            }
        }
    }

    /**
     * 图片加载策略，固定分辨率对话框点击
     */
    private class OnImageLoadStrategyFixedDialogClick implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            //关闭对话框
            dialog.dismiss();
            //默认为0
            int checkStrategy = 0;
            //设置策略
            switch (which) {
                case 0://全尺寸
                    checkStrategy = PhotoLoadStrategy.PHOTO_LOAD_STRATEGY_FIXED_FULL;
                    break;
                case 1://最高1080
                    checkStrategy = PhotoLoadStrategy.PHOTO_LOAD_STRATEGY_FIXED_1080;
                    break;
                case 2://最高720
                    checkStrategy = PhotoLoadStrategy.PHOTO_LOAD_STRATEGY_FIXED_720;
                    break;
                case 3://最高480
                    checkStrategy = PhotoLoadStrategy.PHOTO_LOAD_STRATEGY_FIXED_480;
                    break;
                case 4://最高200
                    checkStrategy = PhotoLoadStrategy.PHOTO_LOAD_STRATEGY_FIXED_200;
                    break;
                default:
                    break;
            }
            if (which == 0) {
                Snackbar.make(content, "no,you can't choose it", Snackbar.LENGTH_SHORT).show();
                return;
            }
            //清空欢迎页数据
            PhotoCache.get(getActivity()).clearWelcomePhoto();
            //存储策略
            PhotoCache.get(getActivity()).setPhotoLoadStrategy(checkStrategy);
            //刷新列表
            photoAdapter.notifyItemRangeChanged(0, photoAdapter.getItemCount());
            photoAdapter.animateImage(jRecyclerView);
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
            //跳转到随机页面
            Pair pair = Pair.create(floatingActionButton, getString(R.string.fab));
            ActivityJump.build(getActivity(), RandomActivity.class)
                    .makeSceneTransitionAnimation(pair)
                    .jump();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PHOTO_DETAIL_CODE) {//从详情跳转回来的
            photoAdapter.animateImage(jRecyclerView);
        }
    }
}
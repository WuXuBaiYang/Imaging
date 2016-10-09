package com.jtech.imaging.view.widget;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.jtech.imaging.R;
import com.jtech.imaging.cache.SearchRecordCache;
import com.jtech.imaging.view.adapter.SearchRecordAdapter;
import com.jtech.listener.OnItemClickListener;
import com.jtech.view.JRecyclerView;
import com.jtech.view.RecyclerHolder;
import com.jtechlib.view.widget.BasePopupWindow;

import butterknife.Bind;
import rx.functions.Action1;

/**
 * 搜索记录pop
 * Created by jianghan on 2016/10/9.
 */

public class SearchRecordPopup extends BasePopupWindow implements OnItemClickListener, SearchRecordAdapter.OnSearchRecordRemove {

    @Bind(R.id.jrecyclerview)
    JRecyclerView jRecyclerView;
    @Bind(R.id.textview_clear)
    TextView textView;

    private SearchRecordCache searchRecordCache;
    private OnSearchRecordClick onSearchRecordClick;
    private SearchRecordAdapter searchRecordAdapter;

    public SearchRecordPopup(Context context) {
        super(context);
        //实例化搜索缓存对象
        searchRecordCache = new SearchRecordCache(context);
    }

    public void setOnSearchRecordClick(OnSearchRecordClick onSearchRecordClick) {
        this.onSearchRecordClick = onSearchRecordClick;
    }

    @Override
    protected void initViews() {
        setContentView(R.layout.view_popup_search_record);
        //设置layoutmanager
        jRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //设置适配器
        searchRecordAdapter = new SearchRecordAdapter(getContext());
        jRecyclerView.setAdapter(searchRecordAdapter);
        //设置item点击事件
        jRecyclerView.setOnItemClickListener(this);
        //设置记录移除事件
        searchRecordAdapter.setOnSearchRecordRemove(this);
        //设置清空的点击事件
        RxCompat.clickThrottleFirst(textView, new ClearClick());
    }

    /**
     * 显示搜索记录
     */
    public void showSearchRecord(View view) {
        showAsDropDown(view);
        //设置搜索记录
        searchRecordAdapter.setDatas(searchRecordCache.getCurrentSearchRecords());
        //设置是否显示清空全部的按钮
        textView.setVisibility(searchRecordAdapter.getItemCount() > 0 ? View.VISIBLE : View.GONE);
    }

    /**
     * 添加搜索记录
     *
     * @param keyword
     */
    public void addSearchRecord(String keyword) {
        searchRecordCache.addSearchRecord(keyword);
    }

    @Override
    public void showAsDropDown(View anchor) {
        super.showAsDropDown(anchor);
    }

    @Override
    public void onItemClick(RecyclerHolder recyclerHolder, View view, int position) {
        if (null != onSearchRecordClick) {
            onSearchRecordClick.onRecordClick(searchRecordAdapter.getItem(position));
        }
    }

    @Override
    public void onRecordRemove(String keyword, int position) {
        //从适配器中移除数据
        searchRecordAdapter.removeData(position);
        //移除本地的数据缓存
        searchRecordCache.removeSearchRecord(keyword);
        //设置是否显示清空全部的按钮
        textView.setVisibility(searchRecordAdapter.getItemCount() > 0 ? View.VISIBLE : View.GONE);
    }

    /**
     * 清空全部的点击事件
     */
    private class ClearClick implements Action1<Void> {
        @Override
        public void call(Void aVoid) {
            //隐藏清空按钮
            textView.setVisibility(View.GONE);
            //清空本地的全部记录
            searchRecordCache.removeAllRecord();
            //刷新适配器
            searchRecordAdapter.setDatas(searchRecordCache.getSearchRecords());
        }
    }

    /**
     * 搜索记录点击事件
     */
    public interface OnSearchRecordClick {
        void onRecordClick(String keyword);
    }
}
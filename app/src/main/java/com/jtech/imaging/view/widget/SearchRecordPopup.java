package com.jtech.imaging.view.widget;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.jtech.imaging.R;
import com.jtech.imaging.cache.SearchRecordCache;
import com.jtech.imaging.view.adapter.SearchRecordAdapter;
import com.jtech.listener.OnItemClickListener;
import com.jtech.view.JRecyclerView;
import com.jtech.view.RecyclerHolder;
import com.jtechlib.view.widget.BasePopupWindow;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 搜索记录pop
 * Created by jianghan on 2016/10/9.
 */

public class SearchRecordPopup extends BasePopupWindow implements OnItemClickListener, SearchRecordAdapter.OnSearchRecordRemove {

    @Bind(R.id.jrecyclerview)
    JRecyclerView jRecyclerView;

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
        //设置自适应宽高
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //取消焦点
        setFocusable(false);
        //点击周围不取消
        setOutsideTouchable(false);
        //设置layoutmanager
        jRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //设置适配器
        searchRecordAdapter = new SearchRecordAdapter(getContext());
        jRecyclerView.setAdapter(searchRecordAdapter);
        //设置item点击事件
        jRecyclerView.setOnItemClickListener(this);
        //设置记录移除事件
        searchRecordAdapter.setOnSearchRecordRemove(this);
    }

    /**
     * 清除全部按钮点击事件
     */
    @OnClick(R.id.textview_clear)
    public void onClearClick() {
        //清空本地的全部记录
        searchRecordCache.removeAllRecord();
        //关闭popup
        dismiss();
    }

    /**
     * 显示搜索记录
     */
    public void showSearchRecord(View view) {
        //设置搜索记录
        searchRecordAdapter.setDatas(searchRecordCache.getCurrentSearchRecords());
        //如果无数据则不显示popup
        if (searchRecordAdapter.getItemCount() > 0) {
            showAsDropDown(view);
        }
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
        //无历史纪录则关闭popup
        if (0 == searchRecordAdapter.getItemCount()) {
            //关闭popup
            dismiss();
        }
    }

    /**
     * 搜索记录点击事件
     */
    public interface OnSearchRecordClick {
        void onRecordClick(String keyword);
    }
}
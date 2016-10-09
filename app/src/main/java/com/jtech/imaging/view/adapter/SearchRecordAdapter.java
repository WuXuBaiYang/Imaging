package com.jtech.imaging.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jtech.adapter.RecyclerAdapter;
import com.jtech.imaging.R;
import com.jtech.imaging.view.widget.RxCompat;
import com.jtech.view.RecyclerHolder;

import rx.functions.Action1;

/**
 * 搜索记录列表适配器
 * Created by jianghan on 2016/10/9.
 */

public class SearchRecordAdapter extends RecyclerAdapter<String> {

    private OnSearchRecordRemove onSearchRecordRemove;

    public SearchRecordAdapter(Context context) {
        super(context);
    }

    public void setOnSearchRecordRemove(OnSearchRecordRemove onSearchRecordRemove) {
        this.onSearchRecordRemove = onSearchRecordRemove;
    }

    /**
     * 根据关键字找到位置
     *
     * @param keyword
     * @return
     */
    public int findKeywordPosition(String keyword) {
        for (int i = 0; i < getItemCount(); i++) {
            if (getItem(i).equals(keyword)) {
                return i;
            }
        }
        return 0;
    }

    @Override
    protected View createView(LayoutInflater layoutInflater, ViewGroup viewGroup, int i) {
        return layoutInflater.inflate(R.layout.view_search_record, viewGroup, false);
    }

    @Override
    protected void convert(RecyclerHolder recyclerHolder, final String s, int i) {
        //设置搜索记录
        recyclerHolder.setText(R.id.textview_search_record, s);
        //设置移除的点击事件
        RxCompat.clickThrottleFirst(recyclerHolder.getView(R.id.close), new Action1() {
            @Override
            public void call(Object o) {
                if (null != onSearchRecordRemove) {
                    onSearchRecordRemove.onRecordRemove(s, findKeywordPosition(s));
                }
            }
        });
    }

    /**
     * 搜索记录移除点击事件
     */
    public interface OnSearchRecordRemove {
        void onRecordRemove(String keyword, int position);
    }
}
package com.jtech.imaging.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jtech.adapter.LoadFooterAdapter;
import com.jtech.imaging.R;
import com.jtech.view.RecyclerHolder;

/**
 * 加载更多的足部视图适配器
 * Created by jianghan on 2016/9/29.
 */

public class LoadMoreFooterAdapter extends LoadFooterAdapter {
    @Override
    public View getFooterView(LayoutInflater layoutInflater, ViewGroup viewGroup) {
        return layoutInflater.inflate(R.layout.view_loadmore_footer, viewGroup, false);
    }

    @Override
    public void loadFailState(RecyclerHolder recyclerHolder) {
        recyclerHolder.hideViewGone(R.id.loadmore_progress);
        recyclerHolder.setText(R.id.loadmore_text, "request fail");
    }

    @Override
    public void loadingState(RecyclerHolder recyclerHolder) {
        recyclerHolder.showView(R.id.loadmore_progress);
        recyclerHolder.setText(R.id.loadmore_text, "");
    }

    @Override
    public void noMoreDataState(RecyclerHolder recyclerHolder) {
        recyclerHolder.hideViewGone(R.id.loadmore_progress);
        recyclerHolder.setText(R.id.loadmore_text, "no more");
    }

    @Override
    public void normalState(RecyclerHolder recyclerHolder) {
        recyclerHolder.hideViewGone(R.id.loadmore_progress);
        recyclerHolder.setText(R.id.loadmore_text, "");
    }
}
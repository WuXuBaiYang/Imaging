package com.jtech.imaging.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jtech.adapter.RecyclerAdapter;
import com.jtech.imaging.R;
import com.jtech.view.RecyclerHolder;

/**
 * 搜索记录列表适配器
 * Created by jianghan on 2016/10/9.
 */

public class SearchRecordAdapter extends RecyclerAdapter<String> {

    private OnCloseClick onCloseClick;

    public SearchRecordAdapter(Context context) {
        super(context);
    }

    public void setOnCloseClick(OnCloseClick onCloseClick) {
        this.onCloseClick = onCloseClick;
    }

    @Override
    protected View createView(LayoutInflater layoutInflater, ViewGroup viewGroup, int i) {
        return layoutInflater.inflate(R.layout.view_search_record, viewGroup, false);
    }

    @Override
    protected void convert(RecyclerHolder recyclerHolder, final String s, int position) {
        //设置搜索记录
        recyclerHolder.setText(R.id.textview_search_record, s);
        //关闭按钮的点击事件
        recyclerHolder.setClickListener(R.id.close, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onCloseClick) {
                    onCloseClick.recordClose(s);
                }
            }
        });
    }

    /**
     * 搜索记录关闭点击事件
     */
    public interface OnCloseClick {
        void recordClose(String keyword);
    }
}
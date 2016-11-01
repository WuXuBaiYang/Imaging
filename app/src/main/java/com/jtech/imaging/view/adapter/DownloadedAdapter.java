package com.jtech.imaging.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jtech.adapter.RecyclerAdapter;
import com.jtech.imaging.R;
import com.jtech.imaging.model.DownloadModel;
import com.jtech.view.RecyclerHolder;

/**
 * 已缓存列表适配器
 * Created by jianghan on 2016/11/1.
 */

public class DownloadedAdapter extends RecyclerAdapter<DownloadModel> {
    public DownloadedAdapter(Context context) {
        super(context);
    }

    @Override
    protected View createView(LayoutInflater layoutInflater, ViewGroup viewGroup, int i) {
        return layoutInflater.inflate(R.layout.view_downloaded, viewGroup, false);
    }

    @Override
    protected void convert(RecyclerHolder recyclerHolder, DownloadModel downloadModel, int i) {

    }
}
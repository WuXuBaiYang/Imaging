package com.jtech.imaging.view.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jtech.imaging.R;
import com.jtech.view.JRecyclerView;
import com.jtechlib.view.fragment.BaseFragment;

import butterknife.Bind;

/**
 * 下载中page
 * Created by jianghan on 2016/10/31.
 */

public class DownloadingFragment extends BaseFragment {

    @Bind(R.id.jrecyclerview)
    JRecyclerView jRecyclerView;

    public static DownloadingFragment newInstance() {
        Bundle args = new Bundle();
        DownloadingFragment fragment = new DownloadingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View createView(LayoutInflater layoutInflater, ViewGroup viewGroup) {
        return layoutInflater.inflate(R.layout.fragment_downloading, viewGroup, false);
    }

    @Override
    protected void initVariables(Bundle bundle) {

    }

    @Override
    protected void initViews(Bundle bundle) {
        //设置layoutmanager
        jRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    protected void loadData() {

    }
}
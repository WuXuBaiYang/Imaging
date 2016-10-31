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
 * 已缓存page
 * Created by jianghan on 2016/10/31.
 */

public class DownloadedFragment extends BaseFragment {

    @Bind(R.id.jrecyclerview)
    JRecyclerView jRecyclerView;

    public static DownloadedFragment newInstance() {
        Bundle args = new Bundle();
        DownloadedFragment fragment = new DownloadedFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View createView(LayoutInflater layoutInflater, ViewGroup viewGroup) {
        return layoutInflater.inflate(R.layout.fragment_downloaded, viewGroup, false);
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
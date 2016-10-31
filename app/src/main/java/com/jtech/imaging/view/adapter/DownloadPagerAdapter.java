package com.jtech.imaging.view.adapter;

import android.support.v4.app.FragmentManager;

import com.jtechlib.view.adapter.BaseFragmentPagerAdapter;
import com.jtechlib.view.fragment.BaseFragment;

import java.util.List;

/**
 * 下载page的适配器
 * Created by jianghan on 2016/10/31.
 */

public class DownloadPagerAdapter extends BaseFragmentPagerAdapter<BaseFragment> {
    private String[] pageTitles = {"Downloaded", "Downloading"};

    public DownloadPagerAdapter(FragmentManager fm, List<BaseFragment> fragments) {
        super(fm, fragments);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return pageTitles[position];
    }
}
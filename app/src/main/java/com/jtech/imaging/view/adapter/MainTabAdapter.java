package com.jtech.imaging.view.adapter;

import android.support.v4.app.FragmentManager;

import com.jtech.imaging.view.adapter.base.BaseFragmentPagerAdapter;
import com.jtech.imaging.view.fragment.MainFragment;

import java.util.List;

/**
 * viewpager 通用适配器
 *
 * @author wuxubaiyang
 */
public class MainTabAdapter extends BaseFragmentPagerAdapter<MainFragment> {

    public MainTabAdapter(FragmentManager fm, List<MainFragment> fragments) {
        super(fm, fragments);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "TAB" + position;
    }

}
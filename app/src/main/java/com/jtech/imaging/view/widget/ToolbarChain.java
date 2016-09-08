package com.jtech.imaging.view.widget;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.jtech.imaging.R;

/**
 * toolbar的链式调用方法
 */
public class ToolbarChain {
    private Context context;
    private Toolbar toolbar;

    public ToolbarChain(Context context, Toolbar toolbar) {
        this.context = context;
        this.toolbar = toolbar;
        //设置默认的标题以及子标题的颜色
        setTitleTextColor(R.color.toolbar_title);
        setSubTitleTextColor(R.color.toolbar_subtitle);
    }

    public ToolbarChain setTitle(int resId) {
        toolbar.setTitle(resId);
        return this;
    }

    public ToolbarChain setSubTitle(int resId) {
        toolbar.setSubtitle(resId);
        return this;
    }

    public ToolbarChain setTitleTextColor(int resId) {
        toolbar.setTitleTextColor(context.getResources().getColor(resId));
        return this;
    }

    public ToolbarChain setSubTitleTextColor(int resId) {
        toolbar.setSubtitleTextColor(context.getResources().getColor(resId));
        return this;
    }

    public ToolbarChain setLogo(int resId) {
        toolbar.setLogo(resId);
        return this;
    }

    public ToolbarChain setNavigationIcon(int resId) {
        toolbar.setNavigationIcon(resId);
        return this;
    }

    public ToolbarChain setNavigationIcon(int resId, View.OnClickListener onClickListener) {
        toolbar.setNavigationIcon(resId);
        toolbar.setNavigationOnClickListener(onClickListener);
        return this;
    }

    public ToolbarChain setOnMenuItemClickListener(Toolbar.OnMenuItemClickListener onMenuItemClickListener) {
        toolbar.setOnMenuItemClickListener(onMenuItemClickListener);
        return this;
    }
}
package com.jtech.imaging.view.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.github.ybq.android.spinkit.SpinKitView;
import com.github.ybq.android.spinkit.style.Pulse;

/**
 * 加载动画
 * Created by jianghan on 2016/9/26.
 */
public class LoadingView extends SpinKitView {

    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //设置动画
        setIndeterminateDrawable(new Pulse());
    }

    public void show() {
        setVisibility(VISIBLE);
    }

    public void hide() {
        setVisibility(INVISIBLE);
    }
}
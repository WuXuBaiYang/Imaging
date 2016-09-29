package com.jtech.imaging.view.widget;

import android.support.annotation.NonNull;
import android.view.View;

import com.jakewharton.rxbinding.view.RxView;

import java.util.concurrent.TimeUnit;

import rx.functions.Action1;

/**
 * Rxview的兼容方法
 * Created by jianghan on 2016/9/29.
 */
public class RxCompat {
    private static final int WINDOW_DURATION = 500;

    /**
     * 点击去抖动
     *
     * @param view
     * @param onNext
     */
    public static void clickThrottleFirst(@NonNull View view, Action1 onNext) {
        RxView.clicks(view)
                .throttleFirst(WINDOW_DURATION, TimeUnit.MILLISECONDS)
                .subscribe(onNext);
    }
}
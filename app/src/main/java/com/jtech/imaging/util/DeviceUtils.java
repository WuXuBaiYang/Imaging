package com.jtech.imaging.util;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Field;

/**
 * 设备通用类
 * Created by wuxubaiyang on 16/3/11.
 */
public class DeviceUtils {
    /**
     * 获取屏幕宽度
     *
     * @param activity activity
     * @return 屏幕宽度
     */
    public static int getScreenWidth(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    /**
     * 获取屏幕高度
     *
     * @param activity activity
     * @return 屏幕高度
     */
    public static int getScreenHeight(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 设置状态栏
     *
     * @param statusBar
     */
    public static void setStatusBar(Activity activity, View statusBar) {
        if (null != statusBar) {
            //判断SDK版本是否大于等于19，大于就让他显示
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                statusBar.setVisibility(View.VISIBLE);
                //设置状态栏高度
                ViewGroup.LayoutParams layoutParams = statusBar.getLayoutParams();
                if (null != layoutParams) {
                    layoutParams.height = DeviceUtils.getStatusBarHeight(activity);
                    statusBar.setLayoutParams(layoutParams);
                } else {
                    statusBar.setLayoutParams(new ViewGroup.LayoutParams(0, DeviceUtils.getStatusBarHeight(activity)));
                }
            } else {
                statusBar.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 获取手机状态栏高度
     *
     * @return 状态栏高度
     */
    public static int getStatusBarHeight(Activity activity) {
        Class<?> c;
        Object obj;
        Field field;
        int x, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = activity.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }
}
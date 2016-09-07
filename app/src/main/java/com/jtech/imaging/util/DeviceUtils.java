package com.jtech.imaging.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Method;

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
                    statusBar.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DeviceUtils.getStatusBarHeight(activity)));
                }
            } else {
                statusBar.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 设置navigationBar
     *
     * @param activity
     * @param navigationBar
     */
    public static void setNavigationBar(Activity activity, View navigationBar) {
        if (null != navigationBar) {
            //判断SDK版本是否大于等于19，大于就让他显示
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                navigationBar.setVisibility(View.VISIBLE);
                //设置状态栏高度
                ViewGroup.LayoutParams layoutParams = navigationBar.getLayoutParams();
                if (null != layoutParams) {
                    layoutParams.height = DeviceUtils.getNavigationBarHeight(activity);
                    navigationBar.setLayoutParams(layoutParams);
                } else {
                    navigationBar.setLayoutParams(new ViewGroup.LayoutParams(0, DeviceUtils.getStatusBarHeight(activity)));
                }
            } else {
                navigationBar.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 获取手机状态栏高度
     *
     * @return 状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        Resources resources = context.getResources();
        int id = resources.getIdentifier("status_bar_height", "dimen", "android");
        if (id > 0) {
            return resources.getDimensionPixelSize(id);
        }
        return 0;
    }

    /**
     * 获取NavigationBar高度
     *
     * @param context
     * @return
     */
    public static int getNavigationBarHeight(Context context) {
        Resources resources = context.getResources();
        int id = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (id > 0 && hasNavigationBar(context)) {
            return resources.getDimensionPixelSize(id);
        }
        return 0;
    }

    /**
     * 设置状态栏为透明的
     */
    @TargetApi(19)
    public static void setTranslucentStatus(Activity activity) {
        Window window = activity.getWindow();
        window.setStatusBarColor(Color.TRANSPARENT);
        WindowManager.LayoutParams winParams = window.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
//                | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        window.setAttributes(winParams);
    }

    /**
     * 是否存在navigationbar
     *
     * @param context
     * @return
     */
    public static boolean hasNavigationBar(Context context) {
        boolean hasNavigationBar = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return hasNavigationBar;

    }
}
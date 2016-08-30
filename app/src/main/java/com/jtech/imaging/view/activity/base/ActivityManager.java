package com.jtech.imaging.view.activity.base;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于对activity的管理
 * Created by wuxubaiyang on 16/4/21.
 */
public class ActivityManager {
    private static ActivityManager activityManager;

    private List<Activity> activities;

    public ActivityManager() {
        activities = new ArrayList<>();
    }

    public static ActivityManager getInstance() {
        if (null == activityManager) {
            activityManager = new ActivityManager();
        }
        return activityManager;
    }

    /**
     * 添加activity
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        if (null != activities) {
            activities.add(activity);
        }
    }

    /**
     * 移除一个activity
     *
     * @param activity
     */
    public void removeActivity(Activity activity) {
        if (null != activities) {
            activities.remove(activity);
        }
    }

    /**
     * 关闭所有的activity
     */
    public void finishAllActivity() {
        if (null != activities) {
            for (Activity activity : activities) {
                activity.finish();
            }
        }
    }

    /**
     * 判断目标类是否正在运行
     *
     * @param c
     * @return
     */
    public boolean isRun(Class c) {
        if (null != activities) {
            for (Activity activity : activities) {
                if (activity.getClass() == c) {
                    return true;
                }
            }
        }
        return false;
    }
}
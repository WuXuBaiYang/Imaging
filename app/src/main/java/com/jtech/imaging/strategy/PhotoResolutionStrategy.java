package com.jtech.imaging.strategy;

import android.content.Context;

import com.jtech.imaging.cache.PhotoCache;

/**
 * 图片详情页，图片清晰度（分辨率）加载策略
 * Created by jianghan on 2016/10/13.
 */

public class PhotoResolutionStrategy {
    public static final int PHOTO_RESOLUTION_1080 = 0X0001;
    public static final int PHOTO_RESOLUTION_720 = 0X0002;
    public static final int PHOTO_RESOLUTION_480 = 0X0003;
    public static final int PHOTO_RESOLUTION_200 = 0X0004;

    /**
     * 获取处理过的链接
     *
     * @param context
     * @param originUrl
     * @return
     */
    public static String getUrl(Context context, String originUrl) {
        return originUrl + "?w=" + getStrategyWidth(context);
    }

    /**
     * 获取策略的宽度
     *
     * @param context
     * @return
     */
    public static int getStrategyWidth(Context context) {
        //获取图片缓存策略
        int strategy = PhotoCache.get(context).getPhotoResolution();
        //判断缓存策略
        if (strategy == PHOTO_RESOLUTION_200) {//200p
            return 200;
        } else if (strategy == PHOTO_RESOLUTION_480) {//480p
            return 480;
        } else if (strategy == PHOTO_RESOLUTION_720) {//720p
            return 720;
        } else if (strategy == PHOTO_RESOLUTION_1080) {//1080p
            return 1080;
        }
        return 480;
    }

    /**
     * 获取策略的文字描述
     *
     * @param context
     * @return
     */
    public static String getStrategyString(Context context) {
        return getStrategyWidth(context) + "p";
    }
}
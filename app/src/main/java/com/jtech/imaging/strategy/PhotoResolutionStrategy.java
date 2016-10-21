package com.jtech.imaging.strategy;

import android.content.Context;

import com.jtech.imaging.cache.PhotoCache;
import com.jtech.imaging.common.PhotoResolution;

/**
 * 图片详情页，图片清晰度（分辨率）加载策略
 * Created by jianghan on 2016/10/13.
 */

public class PhotoResolutionStrategy {
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
        if (strategy == PhotoResolution.PHOTO_RESOLUTION_480) {//480p
            return 480;
        } else if (strategy == PhotoResolution.PHOTO_RESOLUTION_720) {//720p
            return 720;
        } else if (strategy == PhotoResolution.PHOTO_RESOLUTION_1080) {//1080p
            return 1080;
        }
        return 480;
    }

    /**
     * 获得选中策略的position
     *
     * @param context
     * @return
     */
    public static int getSelectStrategyPosition(Context context) {
        //获取图片缓存策略
        int strategy = PhotoCache.get(context).getPhotoResolution();
        //判断缓存策略
        if (strategy == PhotoResolution.PHOTO_RESOLUTION_480) {//480p
            return 0;
        } else if (strategy == PhotoResolution.PHOTO_RESOLUTION_720) {//720p
            return 1;
        } else if (strategy == PhotoResolution.PHOTO_RESOLUTION_1080) {//1080p
            return 2;
        }
        return 0;
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
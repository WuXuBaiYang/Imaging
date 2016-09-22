package com.jtech.imaging.strategy;

import android.content.Context;
import android.net.ConnectivityManager;

import com.jtech.imaging.cache.PhotoCache;
import com.jtech.imaging.util.NetUtils;

/**
 * 图片加载策略
 * Created by jianghan on 2016/9/22.
 */
public class PhotoLoadStrategy {
    //图片加载策略-固定清晰度
    public static final int PHOTO_LOAD_STRATEGY_FIXED_FULL = 0X0010;
    public static final int PHOTO_LOAD_STRATEGY_FIXED_1080 = 0X0011;
    public static final int PHOTO_LOAD_STRATEGY_FIXED_720 = 0X0012;
    public static final int PHOTO_LOAD_STRATEGY_FIXED_480 = 0X0013;
    public static final int PHOTO_LOAD_STRATEGY_FIXED_200 = 0X0014;
    //图片加载策略-根据网络自动调整
    public static final int PHOTO_LOAD_STRATEGY_NET_AUTO = 0X0015;
    //图片加载策略-无视网络自动调整
    public static final int PHOTO_LOAD_STRATEGY_AUTO = 0X0016;

    private static int netType;

    /**
     * 设置网络状态
     *
     * @param netType
     */
    public static void setNetType(int netType) {
        PhotoLoadStrategy.netType = netType;
    }

    /**
     * 获取网络状态
     *
     * @param context
     * @return
     */
    public static int getNetType(Context context) {
        if (0 == netType) {
            netType = NetUtils.getNetType(context);
        }
        return netType;
    }

    /**
     * 根据图片加载策略，获取适合的图片url
     *
     * @param originUrl 原始url
     * @param outWidth  可显示的最大宽度
     * @return
     */
    public static String getUrl(Context context, String originUrl, int outWidth) {
        //获取图片缓存策略
        int strategy = PhotoCache.get(context).getPhotoLoadStrategy();
        //判断缓存策略
        if (strategy == PHOTO_LOAD_STRATEGY_AUTO) {//无视网络，根据屏幕适配
            return getAutoUrl(originUrl, outWidth);
        } else if (strategy == PHOTO_LOAD_STRATEGY_NET_AUTO) {//根据网络进行适配
            return getNetAutoUrl(context, originUrl, outWidth);
        } else {//固定清晰度
            return getFixedUrl(strategy, originUrl, outWidth);
        }
    }


    /**
     * 获取固定清晰度的图片
     *
     * @param strategy
     * @param originUrl
     * @param outWidth
     * @return
     */
    private static String getFixedUrl(int strategy, String originUrl, int outWidth) {
        if (strategy == PHOTO_LOAD_STRATEGY_FIXED_FULL) {//全尺寸
            return originUrl;
        } else if (strategy == PHOTO_LOAD_STRATEGY_FIXED_1080) {//最高1080
            return originUrl + "&w=" + (1080 > outWidth ? outWidth : 1080);
        } else if (strategy == PHOTO_LOAD_STRATEGY_FIXED_720) {//最高720
            return originUrl + "&w=" + (720 > outWidth ? outWidth : 720);
        } else if (strategy == PHOTO_LOAD_STRATEGY_FIXED_480) {//最高480
            return originUrl + "&w=" + (480 > outWidth ? outWidth : 480);
        } else if (strategy == PHOTO_LOAD_STRATEGY_FIXED_200) {//最高200
            return originUrl + "&w=" + (200 > outWidth ? outWidth : 200);
        }
        return originUrl;
    }

    /**
     * 根据网络获取对应清晰度的图片
     *
     * @param originUrl
     * @param outWidth
     * @return
     */
    private static String getNetAutoUrl(Context context, String originUrl, int outWidth) {
        //获取网络状态
        int netType = getNetType(context);
        if (netType == ConnectivityManager.TYPE_WIFI) {//wifi状态下
            return getAutoUrl(originUrl, outWidth);
        } else if (netType == ConnectivityManager.TYPE_MOBILE) {//手机网络状态下
            //获取网络类型2G/3G/4G
            int subType = NetUtils.getSubtype(context);
            if (subType == NetUtils.NETWORK_CLASS_2_G) {//2G网络加载缩略图
                return getFixedUrl(PHOTO_LOAD_STRATEGY_FIXED_200, originUrl, outWidth);
            } else if (subType == NetUtils.NETWORK_CLASS_3_G) {//3G网络加载480P图片
                return getFixedUrl(PHOTO_LOAD_STRATEGY_FIXED_480, originUrl, outWidth);
            } else if (subType == NetUtils.NETWORK_CLASS_4_G) {//4G网络加载720P图片
                return getFixedUrl(PHOTO_LOAD_STRATEGY_FIXED_720, originUrl, outWidth);
            }
        }
        //如果不属于手机网络以及wifi，则使用默认清晰度
        return getFixedUrl(PHOTO_LOAD_STRATEGY_FIXED_480, originUrl, outWidth);
    }

    /**
     * 无视网络，自动调整清晰度
     *
     * @param originUrl
     * @param outWidth
     * @return
     */
    private static String getAutoUrl(String originUrl, int outWidth) {
        return originUrl + "&w=" + outWidth;
    }
}
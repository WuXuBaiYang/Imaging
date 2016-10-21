package com.jtech.imaging.common;

/**
 * 图片加载
 * Created by jianghan on 2016/10/21.
 */

public class PhotoLoad {
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
}
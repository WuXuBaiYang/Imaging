package com.jtech.imaging.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

/**
 * 网络相关的通用方法
 * Created by jianghan on 2016/9/12.
 */
public class NetUtils {

    /**
     * Unknown network class. {@hide}
     */
    public static final int NETWORK_CLASS_UNKNOWN = 0;
    /**
     * Class of broadly defined "2G" networks. {@hide}
     */
    public static final int NETWORK_CLASS_2_G = 1;
    /**
     * Class of broadly defined "3G" networks. {@hide}
     */
    public static final int NETWORK_CLASS_3_G = 2;
    /**
     * Class of broadly defined "4G" networks. {@hide}
     */
    public static final int NETWORK_CLASS_4_G = 3;

    /**
     * 获取当前网络的信息
     *
     * @param context
     * @return
     */
    public static NetworkInfo getNetWorkInfo(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo();
    }

    /**
     * 获取网络类型
     *
     * @param context
     * @return
     */
    public static int getNetType(Context context) {
        NetworkInfo networkInfo = getNetWorkInfo(context);
        if (null != networkInfo) {
            return networkInfo.getType();
        }
        return -1;
    }

    /**
     * 判断是否有网络连接
     *
     * @param context
     * @return
     */
    public static boolean isNetworkConnected(Context context) {
        NetworkInfo networkInfo = getNetWorkInfo(context);
        if (null != networkInfo) {
            return networkInfo.isAvailable();
        }
        return false;
    }

    /**
     * 判断WIFI网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean isWifiConnected(Context context) {
        NetworkInfo networkInfo = getNetWorkInfo(context);
        if (null != networkInfo) {
            return networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
        }
        return false;
    }

    /**
     * 判断MOBILE网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean isMobileConnected(Context context) {
        NetworkInfo networkInfo = getNetWorkInfo(context);
        if (null != networkInfo) {
            return networkInfo.getType() == ConnectivityManager.TYPE_MOBILE;
        }
        return false;
    }

    /**
     * 获取网络类型
     *
     * @param context
     * @return
     */
    public static int getSubtype(Context context) {
        NetworkInfo networkInfo = getNetWorkInfo(context);
        if (null != networkInfo) {
            return getNetworkClass(networkInfo.getSubtype());
        }
        return NETWORK_CLASS_UNKNOWN;
    }

    /**
     * 获取网络状态2G/3G/4G
     *
     * @param subtype
     * @return
     */
    public static int getNetworkClass(int subtype) {
        switch (subtype) {
            case TelephonyManager.NETWORK_TYPE_GPRS:
            case TelephonyManager.NETWORK_TYPE_EDGE:
            case TelephonyManager.NETWORK_TYPE_CDMA:
            case TelephonyManager.NETWORK_TYPE_1xRTT:
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return NETWORK_CLASS_2_G;
            case TelephonyManager.NETWORK_TYPE_UMTS:
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
            case TelephonyManager.NETWORK_TYPE_HSDPA:
            case TelephonyManager.NETWORK_TYPE_HSUPA:
            case TelephonyManager.NETWORK_TYPE_HSPA:
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
            case TelephonyManager.NETWORK_TYPE_EHRPD:
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return NETWORK_CLASS_3_G;
            case TelephonyManager.NETWORK_TYPE_LTE:
                return NETWORK_CLASS_4_G;
            default:
                return NETWORK_CLASS_UNKNOWN;
        }
    }
}
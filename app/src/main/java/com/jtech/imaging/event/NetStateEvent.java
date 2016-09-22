package com.jtech.imaging.event;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.jtech.imaging.util.NetUtils;

/**
 * 网络状态事件
 * Created by jianghan on 2016/9/22.
 */
public class NetStateEvent {
    private int type;
    private int subType;
    private String typeName;
    private NetworkInfo.State state;
    private NetworkInfo.DetailedState detailedState;

    public NetStateEvent(int type, int subType, String typeName, NetworkInfo.State state, NetworkInfo.DetailedState detailedState) {
        this.type = type;
        this.subType = subType;
        this.typeName = typeName;
        this.state = state;
        this.detailedState = detailedState;
    }

    /**
     * 是否是wifi连接
     *
     * @return
     */
    public boolean isWifiConnected() {
        if (type == ConnectivityManager.TYPE_WIFI && state == NetworkInfo.State.CONNECTED) {
            return true;
        }
        return false;
    }

    /**
     * 获取手机网络状态2G/3G/4G
     *
     * @return
     */
    public String getMobileNet() {
        int mobileType = NetUtils.getNetworkClass(subType);
        if (mobileType == NetUtils.NETWORK_CLASS_2_G) {
            return "2G网络";
        } else if (mobileType == NetUtils.NETWORK_CLASS_3_G) {
            return "3G网络";
        } else if (mobileType == NetUtils.NETWORK_CLASS_4_G) {
            return "4G网络";
        } else {
            return "未知网络";
        }
    }

    public int getSubType() {
        return subType;
    }

    public void setSubType(int subType) {
        this.subType = subType;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public NetworkInfo.State getState() {
        return state;
    }

    public void setState(NetworkInfo.State state) {
        this.state = state;
    }

    public NetworkInfo.DetailedState getDetailedState() {
        return detailedState;
    }

    public void setDetailedState(NetworkInfo.DetailedState detailedState) {
        this.detailedState = detailedState;
    }
}
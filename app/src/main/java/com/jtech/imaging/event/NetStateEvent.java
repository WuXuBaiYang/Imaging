package com.jtech.imaging.event;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 网络状态事件
 * Created by jianghan on 2016/9/22.
 */
public class NetStateEvent {
    private int type;
    private String typeName;
    private NetworkInfo.State state;
    private NetworkInfo.DetailedState detailedState;

    public NetStateEvent(int type, String typeName, NetworkInfo.State state, NetworkInfo.DetailedState detailedState) {
        this.type = type;
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
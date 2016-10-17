package com.jtech.imaging;

import android.content.IntentFilter;
import android.net.ConnectivityManager;

import com.jtech.imaging.receiver.ConnectionChangeReceiver;
import com.jtechlib.BaseApplication;

/**
 * application，继承自JTechLib
 * Created by wuxubaiyang on 16/4/21.
 */
public class JApplication extends BaseApplication {

    private ConnectionChangeReceiver connectionChangeReceiver;

    @Override
    public void onCreate() {
        super.onCreate();
        //注册网络变化广播
        registerConnectReceiver();
    }

    /**
     * 注册网络变化广播
     */
    private void registerConnectReceiver() {
        connectionChangeReceiver = new ConnectionChangeReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(connectionChangeReceiver, intentFilter);
    }

    /**
     * 反注册网络变化广播
     */
    private void unregisterConnectReceiver() {
        if (null != connectionChangeReceiver) {
            unregisterReceiver(connectionChangeReceiver);
        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        //销毁时反注册网络状态广播
        unregisterConnectReceiver();
    }
}
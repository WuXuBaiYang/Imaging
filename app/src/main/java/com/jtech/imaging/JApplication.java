package com.jtech.imaging;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

import com.jtech.imaging.receiver.ConnectionChangeReceiver;
import com.jtech.imaging.service.DownloadService;
import com.jtechlib.BaseApplication;
import com.liulishuo.filedownloader.FileDownloader;
import com.umeng.analytics.MobclickAgent;

import io.realm.Realm;

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
        //初始化Realm数据库
        Realm.init(getApplicationContext());
        //初始化下载管理
        FileDownloader.init(getApplicationContext());
        //启动下载服务
        startService(new Intent(this, DownloadService.class));
        //设置友盟统计类型
        MobclickAgent.setScenarioType(getApplicationContext(), MobclickAgent.EScenarioType.E_UM_NORMAL);
        //设置activity不自动统计页面跳转
        MobclickAgent.openActivityDurationTrack(false);
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
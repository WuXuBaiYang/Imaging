package com.jtech.imaging.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.jtech.imaging.event.NetStateEvent;
import com.jtech.imaging.strategy.PhotoLoadStrategy;

import org.greenrobot.eventbus.EventBus;

/**
 * 网络状态变化监听
 */
public class ConnectionChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //获取连接管理对象
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //获取当前网络信息
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        //获取类型
        int type = networkInfo.getType();
        //获取手机网络类型2G/3G/4G
        int subType = networkInfo.getSubtype();
        //获取类型名
        String typeName = networkInfo.getTypeName();
        //获取状态
        NetworkInfo.State state = networkInfo.getState();
        //获取详细状态
        NetworkInfo.DetailedState detailedState = networkInfo.getDetailedState();
        //存储网络状态到图片加载策略中
        PhotoLoadStrategy.setNetType(type);
        //发送消息
        EventBus.getDefault().post(new NetStateEvent(type, subType, typeName, state, detailedState));
    }
}
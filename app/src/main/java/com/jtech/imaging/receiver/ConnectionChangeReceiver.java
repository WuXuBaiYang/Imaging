package com.jtech.imaging.receiver;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.jtech.imaging.event.NetStateEvent;
import com.jtech.imaging.strategy.PhotoLoadStrategy;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * 网络状态变化监听
 */
public class ConnectionChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (!isAppRunning(context)) {//app不在运行则不执行操作
            return;
        }
        //获取连接管理对象
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //获取当前网络信息
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (null != networkInfo) {
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
        } else {
            //无活动网络
            EventBus.getDefault().post(new NetStateEvent(0, 0, "", NetworkInfo.State.UNKNOWN, NetworkInfo.DetailedState.FAILED));
        }
    }

    /**
     * 判断当前应用是否在运行
     *
     * @param context
     * @return
     */
    private boolean isAppRunning(Context context) {
        String packageName = context.getPackageName();
        String topActivityClassName = getTopActivityName(context);

        if (packageName != null && topActivityClassName != null && topActivityClassName.startsWith(packageName)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取顶部activity名
     *
     * @param context
     * @return
     */
    private String getTopActivityName(Context context) {
        String topActivityClassName = null;
        ActivityManager activityManager =
                (ActivityManager) (context.getSystemService(android.content.Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningTaskInfo> runningTaskInfos = activityManager.getRunningTasks(1);
        if (runningTaskInfos != null) {
            ComponentName f = runningTaskInfos.get(0).topActivity;
            topActivityClassName = f.getClassName();
        }
        return topActivityClassName;
    }
}
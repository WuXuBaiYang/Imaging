package com.jtech.imaging.util;

import org.greenrobot.eventbus.EventBus;

/**
 * 老司机-消息总线
 * Created by JTech on 2017/1/7.
 */

public class Bus extends EventBus {
    private static Bus INSTANCE;

    /**
     * 获取车次
     *
     * @return
     */
    public static EventBus get() {
        if (null == INSTANCE) {
            synchronized (Bus.class) {
                if (null == INSTANCE) {
                    INSTANCE = new Bus();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 上车-老司机带带我
     *
     * @param subscriber
     */
    public static void getOn(Object subscriber) {
        get().register(subscriber);
    }

    /**
     * 下车
     *
     * @param subscriber
     */
    public static void getOff(Object subscriber) {
        get().unregister(subscriber);
    }

    /**
     * 是否上车了
     *
     * @param subscriber
     * @return
     */
    public static boolean inBus(Object subscriber) {
        return get().isRegistered(subscriber);
    }
}
package com.jtech.imaging;

import com.jtechlib.BaseApplication;

/**
 * Created by wuxubaiyang on 16/4/21.
 */
public class JApplication extends BaseApplication {

    private static JApplication INSTANCE;

    @Override
    public void onCreate() {
        super.onCreate();
        //赋值当前对象
        this.INSTANCE = this;
    }

    /**
     * 获取application实例
     *
     * @return
     */
    public static JApplication getInstance() {
        return INSTANCE;
    }
}
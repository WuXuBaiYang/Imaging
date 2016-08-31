package com.jtech.imaging;

import android.app.Application;

import com.jtech.imaging.common.Constants;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by wuxubaiyang on 16/4/21.
 */
public class JApplication extends Application {

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

    /**
     * 初始化realm
     */
    public Realm getRealm() {
        return Realm.getInstance(getRealmConfiguration());
    }

    /**
     * 获取数据库配置信息
     *
     * @return
     */
    private RealmConfiguration getRealmConfiguration() {
        return new RealmConfiguration
                .Builder(getApplicationContext())
                .name(Constants.REALM_DB_NAME)
                .build();
    }
}
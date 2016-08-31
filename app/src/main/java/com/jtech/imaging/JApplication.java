package com.jtech.imaging;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by wuxubaiyang on 16/4/21.
 */
public class JApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化realm
        setupRealm();
    }

    /**
     * 初始化realm
     */
    private void setupRealm() {
        RealmConfiguration config = new RealmConfiguration
                .Builder(getApplicationContext())
                .build();
        Realm.setDefaultConfiguration(config);
    }
}
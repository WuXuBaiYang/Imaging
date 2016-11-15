package com.jtech.imaging.realm;

import com.jtech.imaging.JApplication;
import com.jtech.imaging.common.Constants;

import io.realm.Realm;

/**
 * 下载数据库管理
 * Created by jianghan on 2016/11/15.
 */

public class DownloadRealmManager {
    private Realm realm;

    /**
     * 获取realm对象
     *
     * @return
     */
    public Realm getRealm() {
        if (null == realm || realm.isClosed()) {
            realm = JApplication.getRealm(Constants.DB_NAME);
        }
        return realm;
    }
}
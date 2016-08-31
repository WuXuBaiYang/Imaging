package com.jtech.imaging.realm;

import com.jtech.imaging.model.OauthModel;

import io.realm.Realm;

/**
 * 授权认证相关的数据库操作
 * Created by jianghan on 2016/8/31.
 */
public class OauthRealm {
    private static OauthRealm oauthRealm;
    private OauthModel oauthModel;

    public static OauthRealm getInstance() {
        if (null == oauthRealm) {
            oauthRealm = new OauthRealm();
        }
        return oauthRealm;
    }

    /**
     * 是否存在授权信息
     *
     * @return
     */
    public static boolean hasOauthModel() {
        return null != OauthRealm.getInstance().getOauthModel();
    }

    /**
     * 插入授权认证的对象
     *
     * @param oauthModel
     */
    public void insertOauthModel(OauthModel oauthModel) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.insertOrUpdate(oauthModel);
        realm.commitTransaction();
    }

    /**
     * 移除授权认证对象
     */
    public void removeOauthModel() {
        this.oauthModel = null;
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.delete(OauthModel.class);
        realm.commitTransaction();
    }

    /**
     * 获取授权认证对象
     *
     * @return
     */
    public OauthModel getOauthModel() {
        if (null == oauthModel) {
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            oauthModel = realm.where(OauthModel.class).findFirst();
            realm.commitTransaction();
        }
        return oauthModel;
    }
}
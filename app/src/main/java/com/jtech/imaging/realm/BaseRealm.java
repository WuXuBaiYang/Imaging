package com.jtech.imaging.realm;

import com.jtech.imaging.JApplication;

import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.RealmObject;

/**
 * 数据库基础操作方法
 * Created by jianghan on 2016/8/31.
 */
public class BaseRealm {

    private Realm realm;

    /**
     * 获取realm对象
     *
     * @return
     */
    public Realm getRealm() {
        if (null == realm) {
            realm = JApplication.getInstance().getRealm();
        }
        return realm;
    }

    /**
     * 插入
     *
     * @param clazz
     * @param realm
     * @param realmObject
     */
    public void insertModel(Class clazz, Realm realm, RealmObject realmObject) {
        realm.beginTransaction();
        realm.insert(realmObject);
        realm.commitTransaction();
    }

    /**
     * 插入(封装)
     *
     * @param clazz
     * @param realmObject
     */
    public void insertModel(Class clazz, RealmObject realmObject) {
        insertModel(clazz, getRealm(), realmObject);
    }

    /**
     * 更新
     *
     * @param realm
     * @param realmObject
     */
    public void updateModel(Realm realm, RealmObject realmObject) {
        realm.beginTransaction();
        realm.insertOrUpdate(realmObject);
        realm.commitTransaction();
    }

    /**
     * 更新(封装)
     *
     * @param realmObject
     */
    public void updateModel(RealmObject realmObject) {
        updateModel(getRealm(), realmObject);
    }

    /**
     * 删除
     *
     * @param clazz
     * @param realm
     */
    public void deleteModel(Class clazz, Realm realm) {
        realm.beginTransaction();
        realm.delete(clazz);
        realm.commitTransaction();
    }

    /**
     * 删除(封装)
     *
     * @param clazz
     */
    public void deleteModel(Class clazz) {
        deleteModel(clazz, getRealm());
    }

    /**
     * 查询
     *
     * @param clazz
     * @param realm
     * @return
     */
    public RealmModel queryModel(Class clazz, Realm realm) {
        realm.beginTransaction();
        RealmModel realmModel = realm.where(clazz).findFirst();
        realm.commitTransaction();
        return realmModel;
    }

    /**
     * 查询(封装)
     *
     * @param clazz
     * @return
     */
    public <R extends RealmObject> R queryModel(Class clazz) {
        return (R) queryModel(clazz, getRealm());
    }
}
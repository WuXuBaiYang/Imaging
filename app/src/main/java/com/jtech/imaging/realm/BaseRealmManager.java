package com.jtech.imaging.realm;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmConfiguration;
import io.realm.RealmModel;
import io.realm.exceptions.RealmMigrationNeededException;

/**
 * realm中间过程基类
 * Created by jianghan on 2016/11/16.
 */

public abstract class BaseRealmManager {
    private Realm realm;

    /**
     * 获取数据库名称
     *
     * @return
     */
    public abstract String getDBName();

    /**
     * 异步事物执行方法
     *
     * @param transaction
     * @param onSuccess
     * @param onError
     * @return
     */
    public RealmAsyncTask execute(Realm.Transaction transaction, Realm.Transaction.OnSuccess onSuccess, Realm.Transaction.OnError onError) {
        return getRealm().executeTransactionAsync(transaction, onSuccess, onError);
    }

    /**
     * 异步事物执行方法
     *
     * @param transaction
     * @return
     */
    public RealmAsyncTask execute(Realm.Transaction transaction) {
        return getRealm().executeTransactionAsync(transaction);
    }

    /**
     * 获取realm对象
     *
     * @return
     */
    public Realm getRealm() {
        if (null == realm || realm.isClosed()) {
            realm = getNewRealm(getDBName());
        }
        return realm;
    }

    /**
     * 获取realm对象
     *
     * @param realmDbName 数据库名称
     * @return
     */
    public Realm getNewRealm(String realmDbName) {
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().name(realmDbName).build();
        try {
            return Realm.getInstance(realmConfiguration);
        } catch (RealmMigrationNeededException e) {
            try {
                Realm.deleteRealm(realmConfiguration);
                return Realm.getInstance(realmConfiguration);
            } catch (Exception ex) {
                throw ex;
            }
        }
    }

    /**
     * 从realm得到数据对象集合
     *
     * @param realmObjects
     * @param <E>
     * @return
     */
    public <E extends RealmModel> List<E> copyFromRealm(Realm realm, Iterable<E> realmObjects) {
        return realm.copyFromRealm(realmObjects);
    }

    /**
     * 从realm得到数据对象
     *
     * @param realmObject
     * @param <E>
     * @return
     */
    public <E extends RealmModel> E copyFromRealm(Realm realm, E realmObject) {
        if (null == realmObject) {
            return null;
        }
        return realm.copyFromRealm(realmObject);
    }
}
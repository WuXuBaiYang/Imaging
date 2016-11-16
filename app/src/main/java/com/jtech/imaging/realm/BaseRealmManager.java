package com.jtech.imaging.realm;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmConfiguration;
import io.realm.RealmModel;
import io.realm.exceptions.RealmMigrationNeededException;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * realm中间过程基类
 * Created by jianghan on 2016/11/16.
 */

public abstract class BaseRealmManager {
    public static final String TAG = "RealmManager";
    private Realm realm;

    /**
     * 事物执行方法（仅用于执行事务方法）
     */
    public Observable<? extends Realm> rx() {
        return Observable.create(new Observable.OnSubscribe<Realm>() {
            @Override
            public void call(final Subscriber<? super Realm> subscriber) {
                try {
                    getRealm().executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            subscriber.onNext(realm);
                        }
                    });
                } catch (Throwable e) {
                    subscriber.onError(e);
                }
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io());
    }

    /**
     * 获取数据库名称
     *
     * @return
     */
    public abstract String getDBName();

    /**
     * 获取realm对象
     *
     * @return
     */
    public Realm getRealm() {
        if (null == realm || realm.isClosed()) {
            realm = getRealm(getDBName());
        }
        return realm;
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
        return realm.copyFromRealm(realmObject);
    }

    /**
     * 数据库变化监听
     *
     * @param realmChangeListener
     */
    public void addChangeListener(RealmChangeListener<Realm> realmChangeListener) {
        getRealm(getDBName()).addChangeListener(realmChangeListener);
    }

    /**
     * 获取realm对象
     *
     * @param realmDbName 数据库名称
     * @return
     */
    public Realm getRealm(String realmDbName) {
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
}
package com.jtech.imaging.realm;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.exceptions.RealmMigrationNeededException;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * realm中间过程基类
 * Created by jianghan on 2016/11/16.
 */

public abstract class BaseRealmManager {
    private Realm realm;

    /**
     * 同步事物方法
     */
    public Observable<? extends Realm> transaction() {
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
                } catch (Exception e) {
                    subscriber.onError(e);
                }
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io());
    }

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
     * 获取数据库名称
     *
     * @return
     */
    public abstract String getDBName();

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
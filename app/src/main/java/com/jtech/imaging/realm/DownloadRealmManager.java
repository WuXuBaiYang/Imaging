package com.jtech.imaging.realm;

import com.jtech.imaging.common.Constants;
import com.jtech.imaging.common.DownloadState;
import com.jtech.imaging.model.DownloadModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * 下载数据库管理
 * Created by jianghan on 2016/11/15.
 */

public class DownloadRealmManager extends BaseRealmManager {

    @Override
    public String getDBName() {
        return Constants.DB_NAME;
    }

    /**
     * 添加一条下载记录
     *
     * @param downloadModel
     */
    public void insertOrUpdateDownload(final DownloadModel downloadModel) {
        transaction()
                .subscribe(new Action1<Realm>() {
                    @Override
                    public void call(Realm realm) {
                        realm.insertOrUpdate(downloadModel);
                    }
                });
    }

    /**
     * 移除一条下载记录
     *
     * @param id
     * @return
     */
    public Observable<? extends Boolean> removeDownload(long id) {
        return removeDownloadRange(id);
    }

    /**
     * 批量删除下载记录
     *
     * @param id
     * @return
     */
    public Observable<? extends Boolean> removeDownloadRange(final long... id) {
        return transaction()
                .map(new Func1<Realm, Boolean>() {
                    @Override
                    public Boolean call(Realm realm) {
                        //查出来全部的任务
                        RealmResults<DownloadModel> realmResults = getRealm()
                                .where(DownloadModel.class)
                                .findAll();
                        //便利并按照条件移除对应的下载记录
                        boolean flag = false;
                        for (int i = 0; i < id.length; i++) {
                            for (int j = 0; j < realmResults.size(); j++) {
                                if (id[i] == realmResults.get(j).getId()) {
                                    realmResults.get(j).deleteFromRealm();
                                    flag = true;
                                }
                            }
                        }
                        return flag;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 开启全部的下载(暂停和出现错误的任务)
     */
    public void startAllDownload() {
        transaction()
                .subscribe(new Action1<Realm>() {
                    @Override
                    public void call(Realm realm) {
                        //获取到暂停以及错误的下载集合
                        RealmResults<DownloadModel> realmResults = getRealm()
                                .where(DownloadModel.class)
                                .equalTo("state", DownloadState.DOWNLOAD_STOP).or()
                                .equalTo("state", DownloadState.DOWNLOAD_STOPING).or()
                                .equalTo("state", DownloadState.DOWNLOAD_FAIL_UNKNOWN).or()
                                .equalTo("state", DownloadState.DOWNLOAD_FAIL_INTENT_ERROR).or()
                                .equalTo("state", DownloadState.DOWNLOAD_FAIL_INTENT_CHANGE)
                                .findAll();
                        //修改状态为连接中
                        for (int i = 0; i < realmResults.size(); i++) {
                            realmResults.get(i).setState(DownloadState.DOWNLOAD_CONNECTION);
                        }
                        //插入或更新
                        getRealm().insertOrUpdate(realmResults);
                    }
                });
    }

    /**
     * 暂停所有下载(下载中和连接中的任务)
     */
    public void stopAllDownload() {
        transaction()
                .subscribe(new Action1<Realm>() {
                    @Override
                    public void call(Realm realm) {
                        //获取到下载中,连接中状态的集合
                        RealmResults<DownloadModel> realmResults = getRealm()
                                .where(DownloadModel.class)
                                .equalTo("state", DownloadState.DOWNLOADING).or()
                                .equalTo("state", DownloadState.DOWNLOAD_CONNECTION)
                                .findAll();
                        //修改状态为连接中
                        for (int i = 0; i < realmResults.size(); i++) {
                            realmResults.get(i).setState(DownloadState.DOWNLOAD_STOPING);
                        }
                        //插入或更新
                        getRealm().insertOrUpdate(realmResults);
                    }
                });
    }

    /**
     * 更新下载进度
     *
     * @param id
     * @param progress
     */
    public void updataDownloadingProgress(final long id, final long progress) {
        transaction()
                .subscribe(new Action1<Realm>() {
                    @Override
                    public void call(Realm realm) {
                        //根据ID查到对象的下载任务
                        DownloadModel downloadModel = realm
                                .where(DownloadModel.class)
                                .equalTo("id", id)
                                .findFirst();
                        //修改下载任务的下载进度
                        downloadModel.setDownloadSize(progress);
                        //插入或更新数据库
                        realm.insertOrUpdate(downloadModel);
                    }
                });
    }

    /**
     * 更新下载对象的大小
     *
     * @param id
     * @param size
     */
    public void updataDownloadSize(final long id, final long size) {
        transaction()
                .subscribe(new Action1<Realm>() {
                    @Override
                    public void call(Realm realm) {
                        //根据ID查到对象的下载任务
                        DownloadModel downloadModel = realm
                                .where(DownloadModel.class)
                                .equalTo("id", id)
                                .findFirst();
                        //修改下载任务的总大小
                        downloadModel.setDownloadSize(size);
                        //插入或更新数据库
                        realm.insertOrUpdate(downloadModel);
                    }
                });
    }

    /**
     * 获取下载中（暂停，错误等状态）集合
     *
     * @return
     */
    public Observable<List<DownloadModel>> getDownloading() {
        return transaction()
                .map(new Func1<Realm, List<DownloadModel>>() {
                    @Override
                    public List<DownloadModel> call(Realm realm) {
                        RealmResults<DownloadModel> realmResults = getRealm()
                                .where(DownloadModel.class)
                                .notEqualTo("state", DownloadState.DOWNLOADED)
                                .notEqualTo("state", DownloadState.DOWNLOADED_NOT_FOUND)
                                .findAll();
                        return new ArrayList<>(Arrays.asList(realmResults.toArray(new DownloadModel[]{})));
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获取已下载集合
     *
     * @return
     */
    public Observable<List<DownloadModel>> getDownloaded() {
        return transaction()
                .map(new Func1<Realm, List<DownloadModel>>() {
                    @Override
                    public List<DownloadModel> call(Realm realm) {
                        RealmResults<DownloadModel> realmResults = getRealm()
                                .where(DownloadModel.class)
                                .equalTo("state", DownloadState.DOWNLOADED)
                                .equalTo("state", DownloadState.DOWNLOADED_NOT_FOUND)
                                .findAll();
                        return new ArrayList<>(Arrays.asList(realmResults.toArray(new DownloadModel[]{})));
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }
}
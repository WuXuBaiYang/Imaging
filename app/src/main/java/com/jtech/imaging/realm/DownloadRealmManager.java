package com.jtech.imaging.realm;

import android.util.Log;

import com.jtech.imaging.common.Constants;
import com.jtech.imaging.common.DownloadState;
import com.jtech.imaging.model.DownloadModel;

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
        rx().subscribe(new Action1<Realm>() {
            @Override
            public void call(Realm realm) {
                realm.insertOrUpdate(downloadModel);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.e(TAG, throwable.getMessage());
            }
        });
    }

    /**
     * 移除一条下载记录
     *
     * @param id
     * @return
     */
    public void removeDownload(final long id) {
        rx().subscribe(new Action1<Realm>() {
            @Override
            public void call(Realm realm) {
                realm.where(DownloadModel.class)
                        .equalTo("id", id)
                        .findFirst()
                        .deleteFromRealm();
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.e(TAG, throwable.getMessage());
            }
        });
    }

    /**
     * 开启下载任务
     *
     * @param id
     */
    public void startDownload(final long id) {
        rx().subscribe(new Action1<Realm>() {
            @Override
            public void call(Realm realm) {
                realm.where(DownloadModel.class)
                        .equalTo("id", id)
                        .findFirst()
                        .setState(DownloadState.DOWNLOADING);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.e(TAG, throwable.getMessage());
            }
        });
    }

    /**
     * 停止一个下载
     *
     * @param id
     */
    public void stopDownload(final long id) {
        rx().subscribe(new Action1<Realm>() {
            @Override
            public void call(Realm realm) {
                realm.where(DownloadModel.class)
                        .equalTo("id", id)
                        .findFirst()
                        .setState(DownloadState.DOWNLOAD_STOP);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.e(TAG, throwable.getMessage());
            }
        });
    }

    /**
     * 开启全部的下载(暂停和出现错误的任务)
     */
    public void startAllDownload() {
        rx().subscribe(new Action1<Realm>() {
            @Override
            public void call(Realm realm) {
                //获取到暂停以及错误的下载集合
                RealmResults<DownloadModel> realmResults = realm
                        .where(DownloadModel.class)
                        .equalTo("state", DownloadState.DOWNLOAD_STOP).or()
                        .equalTo("state", DownloadState.DOWNLOAD_FAIL_UNKNOWN).or()
                        .equalTo("state", DownloadState.DOWNLOAD_FAIL_INTENT_ERROR).or()
                        .equalTo("state", DownloadState.DOWNLOAD_FAIL_INTENT_CHANGE)
                        .findAll();
                //修改全部任务为连接中状态
                for (DownloadModel downloadMode : realmResults) {
                    downloadMode.setState(DownloadState.DOWNLOADING);
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.e(TAG, throwable.getMessage());
            }
        });
    }

    /**
     * 暂停所有下载(下载中和连接中的任务)
     */
    public void stopAllDownload() {
        rx().subscribe(new Action1<Realm>() {
            @Override
            public void call(Realm realm) {
                //获取到下载中,连接中状态的集合
                RealmResults<DownloadModel> realmResults = realm
                        .where(DownloadModel.class)
                        .equalTo("state", DownloadState.DOWNLOADING)
                        .findAll();
                //修改全部任务为暂停中状态
                for (DownloadModel downloadModel : realmResults) {
                    downloadModel.setState(DownloadState.DOWNLOAD_STOP);
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.e(TAG, throwable.getMessage());
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
        rx().subscribe(new Action1<Realm>() {
            @Override
            public void call(Realm realm) {
                realm.where(DownloadModel.class)
                        .equalTo("id", id)
                        .findFirst()
                        .setDownloadSize(progress);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.e(TAG, throwable.getMessage());
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
        rx().subscribe(new Action1<Realm>() {
            @Override
            public void call(Realm realm) {
                realm.where(DownloadModel.class)
                        .equalTo("id", id)
                        .findFirst()
                        .setDownloadSize(size);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.e(TAG, throwable.getMessage());
            }
        });
    }

    /**
     * 获取下载中（暂停，错误等状态）集合
     *
     * @return
     */
    public Observable<List<DownloadModel>> getDownloading() {
        return rx().map(new Func1<Realm, List<DownloadModel>>() {
            @Override
            public List<DownloadModel> call(Realm realm) {
                RealmResults<DownloadModel> realmResults = realm
                        .where(DownloadModel.class)
                        .notEqualTo("state", DownloadState.DOWNLOADED)
                        .notEqualTo("state", DownloadState.DOWNLOADED_NOT_FOUND)
                        .findAllSorted("id");
                return copyFromRealm(realm, realmResults);
            }
        }).observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获取已下载集合
     *
     * @return
     */
    public Observable<List<DownloadModel>> getDownloaded() {
        return rx().map(new Func1<Realm, List<DownloadModel>>() {
            @Override
            public List<DownloadModel> call(Realm realm) {
                RealmResults<DownloadModel> realmResults = realm
                        .where(DownloadModel.class)
                        .equalTo("state", DownloadState.DOWNLOADED)
                        .equalTo("state", DownloadState.DOWNLOADED_NOT_FOUND)
                        .findAllSorted("id");
                return copyFromRealm(realm, realmResults);
            }
        }).observeOn(AndroidSchedulers.mainThread());
    }
}
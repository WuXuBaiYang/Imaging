package com.jtech.imaging.realm;

import com.jtech.imaging.common.Constants;
import com.jtech.imaging.common.DownloadState;
import com.jtech.imaging.model.DownloadModel;

import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

/**
 * 下载数据库管理
 * Created by jianghan on 2016/11/15.
 */

public class DownloadRealmManager extends BaseRealmManager {
    private static DownloadRealmManager INSTANCE;
    //下载中列表
    private RealmResults<DownloadModel> realmResultsDownloading;
    //已下载列表
    private RealmResults<DownloadModel> realmResultsDownloaded;

    public static DownloadRealmManager get() {
        if (null == INSTANCE) {
            INSTANCE = new DownloadRealmManager();
        }
        return INSTANCE;
    }

    @Override
    public String getDBName() {
        return Constants.DB_NAME;
    }

    /**
     * 添加并开启一个下载任务
     *
     * @param downloadModel
     * @return
     */
    public RealmAsyncTask addDownloadAndStart(DownloadModel downloadModel) {
        downloadModel.setState(DownloadState.DOWNLOAD_WAITING);
        return addDownload(downloadModel);
    }

    /**
     * 添加一条下载记录
     *
     * @param downloadModel
     */
    public RealmAsyncTask addDownload(final DownloadModel downloadModel) {
        return execute(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insertOrUpdate(downloadModel);
            }
        });
    }

    /**
     * 判断是否已存在下载任务
     *
     * @param md5
     * @return
     */
    public boolean hasDownload(String md5) {
        return 0 != getRealm().where(DownloadModel.class)
                .equalTo("md5", md5)
                .count();
    }

    /**
     * 移除一条下载记录
     *
     * @param id
     * @return
     */
    public RealmAsyncTask removeDownload(final long id) {
        return execute(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.where(DownloadModel.class)
                        .equalTo("id", id)
                        .findAll()
                        .deleteAllFromRealm();
            }
        });
    }

    /**
     * 开启下载任务
     *
     * @param id
     */
    public RealmAsyncTask startDownload(final long id) {
        return execute(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.where(DownloadModel.class)
                        .equalTo("id", id)
                        .findFirst()
                        .setState(DownloadState.DOWNLOAD_WAITING);
            }
        });
    }

    /**
     * 停止一个下载
     *
     * @param id
     */
    public RealmAsyncTask stopDownload(final long id) {
        return execute(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.where(DownloadModel.class)
                        .equalTo("id", id)
                        .findFirst()
                        .setState(DownloadState.DOWNLOAD_STOP);
            }
        });
    }

    /**
     * 设置下载完成
     *
     * @param id
     * @return
     */
    public RealmAsyncTask setDownloadFinish(long id) {
        return updataDownloadState(id, DownloadState.DOWNLOADED);
    }

    /**
     * 设置已下载内容找不到
     *
     * @param id
     * @return
     */
    public RealmAsyncTask setDownloadNotFound(long id) {
        return updataDownloadState(id, DownloadState.DOWNLOADED_NOT_FOUND);
    }

    /**
     * 设置下载失败，网络发生变化
     *
     * @param id
     * @return
     */
    public RealmAsyncTask setDownloadFailNetworkChange(long id) {
        return updataDownloadState(id, DownloadState.DOWNLOAD_FAIL_NETWORK_CHANGE);
    }

    /**
     * 设置下载失败，网络错误
     *
     * @param id
     * @return
     */
    public RealmAsyncTask setDownloadFailNetworkError(long id) {
        return updataDownloadState(id, DownloadState.DOWNLOAD_FAIL_NETWORK_ERROR);
    }

    /**
     * 设置下载失败，未知错误
     *
     * @param id
     * @return
     */
    public RealmAsyncTask setDownloadFailUnknown(long id) {
        return updataDownloadState(id, DownloadState.DOWNLOAD_FAIL_UNKNOWN);
    }

    /**
     * 判断所有任务是否正在下载
     *
     * @return
     */
    public boolean isAllDownloading() {
        RealmResults<DownloadModel> realmResults = getRealm()
                .where(DownloadModel.class)
                .notEqualTo("state", DownloadState.DOWNLOADED)
                .notEqualTo("state", DownloadState.DOWNLOADED_NOT_FOUND)
                .findAll();
        //判断是否全是下载中状态
        for (DownloadModel downloadModel : realmResults) {
            int state = downloadModel.getState();
            if (state != DownloadState.DOWNLOADING && state != DownloadState.DOWNLOAD_WAITING) {
                return false;
            }
        }
        return true;
    }

    /**
     * 是否存在下载任务
     *
     * @return
     */
    public boolean hasDownloading() {
        return 0 != getRealm()
                .where(DownloadModel.class)
                .notEqualTo("state", DownloadState.DOWNLOADED)
                .notEqualTo("state", DownloadState.DOWNLOADED_NOT_FOUND)
                .count();
    }

    /**
     * 开启全部的下载(暂停和出现错误的任务)
     */
    public RealmAsyncTask startAllDownload() {
        return execute(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                //获取到暂停以及错误的下载集合
                RealmResults<DownloadModel> realmResults = realm
                        .where(DownloadModel.class)
                        .notEqualTo("state", DownloadState.DOWNLOADED)
                        .notEqualTo("state", DownloadState.DOWNLOADED_NOT_FOUND)
                        .findAll();
                //修改全部任务为开始状态
                for (DownloadModel downloadMode : realmResults) {
                    downloadMode.setState(DownloadState.DOWNLOAD_WAITING);
                }
            }
        });
    }

    /**
     * 暂停所有下载(下载中和连接中的任务)
     */
    public RealmAsyncTask stopAllDownload() {
        return execute(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                //获取到下载中,连接中状态的集合
                RealmResults<DownloadModel> realmResults = realm
                        .where(DownloadModel.class)
                        .notEqualTo("state", DownloadState.DOWNLOADED)
                        .notEqualTo("state", DownloadState.DOWNLOADED_NOT_FOUND)
                        .findAll();
                //修改全部任务为暂停中状态
                for (DownloadModel downloadModel : realmResults) {
                    downloadModel.setState(DownloadState.DOWNLOAD_STOP);
                }
            }
        });
    }

    /**
     * 更新下载进度
     *
     * @param id
     * @param progress
     */
    public RealmAsyncTask updataDownloadingProgress(final long id, final long progress) {
        return execute(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.where(DownloadModel.class)
                        .equalTo("id", id)
                        .findFirst()
                        .setDownloadSize(progress);
            }
        });
    }

    /**
     * 更新下载对象的大小
     *
     * @param id
     * @param size
     */
    public RealmAsyncTask updataDownloadSize(final long id, final long size) {
        return execute(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.where(DownloadModel.class)
                        .equalTo("id", id)
                        .findFirst()
                        .setDownloadSize(size);
            }
        });
    }

    /**
     * 更新下载对象的状态
     *
     * @param id
     * @param state
     * @return
     */
    public RealmAsyncTask updataDownloadState(final long id, final int state) {
        return execute(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.where(DownloadModel.class)
                        .equalTo("id", id)
                        .findFirst()
                        .setState(state);

            }
        });
    }

    /**
     * 获取下载中（暂停，错误等状态）集合
     *
     * @return
     */
    public void getDownloading(RealmChangeListener realmChangeListener) {
        //获取集合
        RealmResults realmResults = getDownloading(getRealm());
        //添加监听
        realmResults.addChangeListener(realmChangeListener);
        //手动回调
        realmChangeListener.onChange(realmResults);
    }

    /**
     * 获取下载列表对象
     *
     * @return
     */
    public RealmResults<DownloadModel> getDownloading(Realm realm) {
        if (null == realmResultsDownloading || !realmResultsDownloading.isValid()) {
            realmResultsDownloading = realm
                    .where(DownloadModel.class)
                    .notEqualTo("state", DownloadState.DOWNLOADED)
                    .notEqualTo("state", DownloadState.DOWNLOADED_NOT_FOUND)
                    .findAllSorted("id");
        }
        return realmResultsDownloading;
    }

    /**
     * 获取已下载记录，异步方法
     *
     * @return
     */
    public void getDownloaded(RealmChangeListener realmChangeListener) {
        //获取集合
        RealmResults realmResults = getDownloaded(getRealm());
        //添加数据变化监听
        realmResults.addChangeListener(realmChangeListener);
        //手动回调
        realmChangeListener.onChange(realmResults);
    }

    /**
     * 获取已下载列表对象
     *
     * @return
     */
    public RealmResults<DownloadModel> getDownloaded(Realm realm) {
        if (null == realmResultsDownloaded || !realmResultsDownloaded.isValid()) {
            realmResultsDownloaded = realm
                    .where(DownloadModel.class)
                    .equalTo("state", DownloadState.DOWNLOADED)
                    .or()
                    .equalTo("state", DownloadState.DOWNLOADED_NOT_FOUND)
                    .findAllSorted("id");
        }
        return realmResultsDownloaded;
    }

    /**
     * 移除监听
     *
     * @param realmChangeListener
     */
    public void removeListener(RealmChangeListener realmChangeListener) {
        getDownloading(getRealm()).removeChangeListener(realmChangeListener);
        getDownloaded(getRealm()).removeChangeListener(realmChangeListener);
    }
}
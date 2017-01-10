package com.jtech.imaging.realm;

import com.jtech.imaging.common.Constants;
import com.jtech.imaging.common.DownloadState;
import com.jtech.imaging.model.DownloadModel;

import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmResults;

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
     * 添加并开启一个下载任务
     *
     * @param downloadModel
     * @return
     */
    public RealmAsyncTask addDownloadAndStart(DownloadModel downloadModel, Realm.Transaction.OnSuccess onSuccess, Realm.Transaction.OnError onError) {
        downloadModel.setState(DownloadState.DOWNLOAD_WAITING);
        return addDownload(downloadModel, onSuccess, onError);
    }

    /**
     * 添加一条下载记录
     *
     * @param downloadModel
     * @param onSuccess
     * @param onError
     * @return
     */
    public RealmAsyncTask addDownload(final DownloadModel downloadModel, Realm.Transaction.OnSuccess onSuccess, Realm.Transaction.OnError onError) {
        return execute(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insertOrUpdate(downloadModel);
            }
        }, onSuccess, onError);
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
    public RealmAsyncTask finishDownload(long id) {
        return updataDownloadState(id, DownloadState.DOWNLOADED);
    }

    /**
     * 设置为下载中状态
     *
     * @param id
     * @return
     */
    public RealmAsyncTask downloading(long id) {
        return updataDownloadState(id, DownloadState.DOWNLOADING);
    }

    /**
     * 设置已下载内容找不到
     *
     * @param id
     * @return
     */
    public RealmAsyncTask downloadNotFound(long id) {
        return updataDownloadState(id, DownloadState.DOWNLOADED_NOT_FOUND);
    }

    /**
     * 设置下载失败，网络发生变化
     *
     * @param id
     * @return
     */
    public RealmAsyncTask downloadFailNetworkChange(long id) {
        return updataDownloadState(id, DownloadState.DOWNLOAD_FAIL_NETWORK_CHANGE);
    }

    /**
     * 设置下载失败，网络错误
     *
     * @param id
     * @return
     */
    public RealmAsyncTask downloadFailNetworkError(long id) {
        return updataDownloadState(id, DownloadState.DOWNLOAD_FAIL_NETWORK_ERROR);
    }

    /**
     * 设置下载失败，未知错误
     *
     * @param id
     * @return
     */
    public RealmAsyncTask downloadFailUnknown(long id) {
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
                //获取到除了已下载的所有任务集合
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
     * 将所有下载中和等待中的任务置为暂停
     */
    public RealmAsyncTask stopAllDownloadingTask() {
        return execute(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                //获取到下载中,等待中状态的集合
                RealmResults<DownloadModel> realmResults = realm
                        .where(DownloadModel.class)
                        .equalTo("state", DownloadState.DOWNLOADING)
                        .or()
                        .equalTo("state", DownloadState.DOWNLOAD_WAITING)
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
     * @param soFarBytes
     * @param totalBytes
     */
    public RealmAsyncTask updataDownloadingProgress(final long id, final long soFarBytes, final long totalBytes) {
        return execute(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                DownloadModel downloadModel = realm.where(DownloadModel.class)
                        .equalTo("id", id)
                        .findFirst();
                if (null != downloadModel) {
                    downloadModel.setDownloadSize(soFarBytes);
                    downloadModel.setSize(totalBytes);
                }
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
                DownloadModel downloadModel = realm.where(DownloadModel.class)
                        .equalTo("id", id)
                        .findFirst();
                if (null != downloadModel) {
                    downloadModel.setState(state);
                }
            }
        });
    }

    /**
     * 根据id获取下载信息
     *
     * @param id
     * @return
     */
    public DownloadModel getDownload(long id) {
        return copyFromRealm(getRealm(), getRealm()
                .where(DownloadModel.class)
                .equalTo("id", id)
                .findFirst());
    }

    /**
     * 获取第一个正在等待的下载任务
     *
     * @return
     */
    public DownloadModel getFirstWaitingDownload() {
        RealmResults<DownloadModel> realmResults = getRealm()
                .where(DownloadModel.class)
                .notEqualTo("state", DownloadState.DOWNLOADED)
                .notEqualTo("state", DownloadState.DOWNLOADED_NOT_FOUND)
                .findAll();
        //遍历集合,找到第一个状态为等待的任务
        for (DownloadModel downloadModel : realmResults) {
            if (DownloadState.DOWNLOAD_WAITING == downloadModel.getState()) {
                return copyFromRealm(getRealm(), downloadModel);
            }
        }
        return null;
    }

    /**
     * 得到下载列表中的第一个任务
     *
     * @return
     */
    public DownloadModel getFirstDownload() {
        return copyFromRealm(getRealm(), getRealm()
                .where(DownloadModel.class)
                .notEqualTo("state", DownloadState.DOWNLOADED)
                .notEqualTo("state", DownloadState.DOWNLOADED_NOT_FOUND)
                .findFirst());
    }

    /**
     * 获取下载中（暂停，错误等状态）集合
     *
     * @return
     */
    public RealmResults<DownloadModel> getDownloading() {
        return getRealm()
                .where(DownloadModel.class)
                .notEqualTo("state", DownloadState.DOWNLOADED)
                .notEqualTo("state", DownloadState.DOWNLOADED_NOT_FOUND)
                .findAllSortedAsync("id");
    }

    /**
     * 获取已下载记录，异步方法
     *
     * @return
     */
    public RealmResults<DownloadModel> getDownloaded() {
        return getRealm()
                .where(DownloadModel.class)
                .equalTo("state", DownloadState.DOWNLOADED)
                .or()
                .equalTo("state", DownloadState.DOWNLOADED_NOT_FOUND)
                .findAllSortedAsync("id");
    }
}
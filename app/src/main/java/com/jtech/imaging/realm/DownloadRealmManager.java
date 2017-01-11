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
        downloadModel.setState(DownloadState.DOWNLOAD_QUEUE);
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
     * 判断是否存在未知状态的下载任务
     *
     * @return
     */
    public boolean hasIndeterminateDownload() {
        return 0 != getRealm().where(DownloadModel.class)
                .equalTo("state", DownloadState.DOWNLOAD_INDETERMINATE)
                .count();
    }

    /**
     * 判断目标下载任务是否为未知状态
     *
     * @param id
     * @return
     */
    public boolean isIndeterminateDownload(long id) {
        return 0 != getRealm().where(DownloadModel.class)
                .equalTo("state", DownloadState.DOWNLOAD_INDETERMINATE)
                .equalTo("id", id)
                .count();
    }

    /**
     * 移除一条下载记录
     *
     * @param id
     * @return
     */
    public RealmAsyncTask removeDownload(final long id, Realm.Transaction.OnSuccess onSuccess) {
        return execute(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                DownloadModel downloadModel = realm.where(DownloadModel.class)
                        .equalTo("id", id)
                        .findFirst();
                if (null != downloadModel) {
                    downloadModel.deleteFromRealm();
                }
            }
        }, onSuccess, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                error.printStackTrace();
            }
        });
    }

    /**
     * 开启下载任务
     *
     * @param id
     */
    public RealmAsyncTask startDownload(final long id, Realm.Transaction.OnSuccess onSuccess) {
        return execute(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.where(DownloadModel.class)
                        .equalTo("id", id)
                        .findFirst()
                        .setState(DownloadState.DOWNLOAD_QUEUE);
            }
        }, onSuccess, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                error.printStackTrace();
            }
        });
    }

    /**
     * 停止一个下载
     *
     * @param id
     */
    public RealmAsyncTask stopDownload(final long id, Realm.Transaction.OnSuccess onSuccess) {
        return execute(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.where(DownloadModel.class)
                        .equalTo("id", id)
                        .findFirst()
                        .setState(DownloadState.DOWNLOAD_STOP);
            }
        }, onSuccess, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                error.printStackTrace();
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
     * 设置下载失败，网络发生变化
     *
     * @param id
     * @return
     */
    public RealmAsyncTask downloadError(long id) {
        return updataDownloadState(id, DownloadState.DOWNLOAD_ERROR);
    }

    /**
     * 不确定状态
     *
     * @param id
     * @return
     */
    public RealmAsyncTask downloadIndeterminate(long id) {
        return updataDownloadState(id, DownloadState.DOWNLOAD_INDETERMINATE);
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
                .findAll();
        //判断是否全是下载中状态
        for (DownloadModel downloadModel : realmResults) {
            int state = downloadModel.getState();
            if (state != DownloadState.DOWNLOADING
                    && state != DownloadState.DOWNLOAD_QUEUE && state != DownloadState.DOWNLOAD_INDETERMINATE) {
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
                .count();
    }

    /**
     * 开启全部的下载(暂停和出现错误的任务)
     */
    public RealmAsyncTask startAllDownload(Realm.Transaction.OnSuccess onSuccess) {
        return execute(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                //获取到暂停以及错误的下载集合
                RealmResults<DownloadModel> realmResults = realm
                        .where(DownloadModel.class)
                        .notEqualTo("state", DownloadState.DOWNLOADED)
                        .notEqualTo("state", DownloadState.DOWNLOAD_INDETERMINATE)
                        .notEqualTo("state", DownloadState.DOWNLOADING)
                        .findAll();
                //修改全部任务为开始状态
                for (DownloadModel downloadMode : realmResults) {
                    downloadMode.setState(DownloadState.DOWNLOAD_QUEUE);
                }
            }
        }, onSuccess, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                error.printStackTrace();
            }
        });
    }

    /**
     * 暂停所有下载(下载中和连接中的任务)
     */
    public RealmAsyncTask stopAllDownload(Realm.Transaction.OnSuccess onSuccess) {
        return execute(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                //获取到除了已下载的所有任务集合
                RealmResults<DownloadModel> realmResults = realm
                        .where(DownloadModel.class)
                        .notEqualTo("state", DownloadState.DOWNLOADED)
                        .findAll();
                //修改全部任务为暂停中状态
                for (DownloadModel downloadModel : realmResults) {
                    downloadModel.setState(DownloadState.DOWNLOAD_STOP);
                }
            }
        }, onSuccess, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                error.printStackTrace();
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
                        .equalTo("state", DownloadState.DOWNLOAD_QUEUE)
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
     * 得到下载列表中等待任务的第一个
     *
     * @return
     */
    public DownloadModel getFirstDownloadWaiting() {
        return copyFromRealm(getRealm(), getRealm()
                .where(DownloadModel.class)
                .equalTo("state", DownloadState.DOWNLOAD_QUEUE)
                .findFirst());
    }

    /**
     * 得到下载列表中下载中任务
     *
     * @return
     */
    public DownloadModel getFirstDownloading() {
        return copyFromRealm(getRealm(), getRealm()
                .where(DownloadModel.class)
                .equalTo("state", DownloadState.DOWNLOADING)
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
                .findAllSortedAsync("id");
    }
}
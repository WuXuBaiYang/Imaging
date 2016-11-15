package com.jtech.imaging.realm;

import com.google.gson.Gson;
import com.jtech.imaging.JApplication;
import com.jtech.imaging.common.Constants;
import com.jtech.imaging.common.DownloadState;
import com.jtech.imaging.model.DownloadModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * 下载数据库管理
 * Created by jianghan on 2016/11/15.
 */

public class DownloadRealmManager {
    private Realm realm;

    /**
     * 添加一条下载记录
     *
     * @param downloadModel
     */
    public void addDownload(DownloadModel downloadModel) {
        getRealm().beginTransaction();
        getRealm().createObjectFromJson(DownloadModel.class, new Gson().toJson(downloadModel));
        getRealm().commitTransaction();
    }

    /**
     * 移除一条下载记录
     *
     * @param id
     */
    public boolean removeDownload(long id) {
        return getRealm()
                .where(DownloadModel.class)
                .equalTo("id", id)
                .findAll()
                .deleteAllFromRealm();
    }

    /**
     * 获取下载中（暂停，错误等状态）集合
     *
     * @return
     */
    public List<DownloadModel> getDownloading() {
        RealmResults<DownloadModel> realmResults = realm
                .where(DownloadModel.class)
                .notEqualTo("state", DownloadState.DOWNLOADED)
                .notEqualTo("state", DownloadState.DOWNLOADED_NOT_FOUND)
                .findAll();
        return toArray(realmResults);
    }

    /**
     * 获取已下载集合
     *
     * @return
     */
    public List<DownloadModel> getDownloaded() {
        RealmResults<DownloadModel> realmResults = realm
                .where(DownloadModel.class)
                .equalTo("state", DownloadState.DOWNLOADED)
                .equalTo("state", DownloadState.DOWNLOADED_NOT_FOUND)
                .findAll();
        return toArray(realmResults);
    }

    /**
     * 将realm返回的集合转换为可变list集合
     *
     * @param realmResults
     * @param <E>
     * @return
     */
    public <E extends RealmObject> List<E> toArray(RealmResults<E> realmResults) {
        return (List<E>) toArray(realmResults.toArray());
    }

    /**
     * 将数组转为可变集合
     *
     * @param objects
     * @param <E>
     * @return
     */
    public <E> List<E> toArray(E[] objects) {
        return new ArrayList<>(Arrays.asList(objects));
    }

    /**
     * 获取realm对象
     *
     * @return
     */
    public Realm getRealm() {
        if (null == realm || realm.isClosed()) {
            realm = JApplication.getRealm(Constants.DB_NAME);
        }
        return realm;
    }
}
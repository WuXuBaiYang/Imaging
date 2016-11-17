package com.jtech.imaging.realm.listener;

import com.jtech.imaging.model.DownloadModel;

import java.util.List;

/**
 * 下载任务队列监听
 * Created by jianghan on 2016/11/17.
 */

public interface OnDownloadTaskListener {
    void downloadTask(List<DownloadModel> downloadModels);
}
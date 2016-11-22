package com.jtech.imaging.presenter;

import android.content.Context;
import android.graphics.Bitmap;

import com.jtech.imaging.contract.GalleryContract;
import com.jtech.imaging.model.DownloadModel;
import com.jtech.imaging.realm.DownloadRealmManager;
import com.jtech.imaging.realm.listener.OnDownloadTaskListener;
import com.jtechlib.Util.ImageUtils;

import java.util.List;

import rx.functions.Action1;

/**
 * 画廊P类
 * Created by jianghan on 2016/11/21.
 */

public class GalleryPresenter implements GalleryContract.Presenter {
    private Context context;
    private GalleryContract.View view;
    private DownloadRealmManager downloadRealmManager;

    public GalleryPresenter(Context context, GalleryContract.View view) {
        this.context = context;
        this.view = view;
        //实例化数据库管理
        this.downloadRealmManager = new DownloadRealmManager();
    }

    @Override
    public void getImage(DownloadModel downloadModel, int targetWidth, final Action1<Bitmap> action1) {
        //限制目标宽度的最大值为1K
        targetWidth = targetWidth > 1080 ? 1080 : targetWidth;
        //等比缩放
        double ratio = (1.0 * targetWidth) / downloadModel.getWidth();
        int targetHeight = (int) (downloadModel.getHeight() * ratio);
        //获取目标大小的图片
        ImageUtils.requestImage(context, downloadModel.getPath(), targetWidth, targetHeight, action1);
    }

    @Override
    public void getDownloadedList() {
        downloadRealmManager.getDownloaded(new OnDownloadTaskListener() {
            @Override
            public void downloadTask(List<DownloadModel> downloadModels) {
                view.downloadTaskList(downloadModels);
            }
        });
    }
}
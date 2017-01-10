package com.jtech.imaging.mvp.presenter;

import android.content.Context;

import com.jtech.imaging.cache.PhotoCache;
import com.jtech.imaging.common.Constants;
import com.jtech.imaging.common.DownloadState;
import com.jtech.imaging.model.DownloadModel;
import com.jtech.imaging.model.PhotoModel;
import com.jtech.imaging.model.event.DownloadEvent;
import com.jtech.imaging.mvp.contract.PhotoDetailContract;
import com.jtech.imaging.net.API;
import com.jtech.imaging.realm.DownloadRealmManager;
import com.jtech.imaging.util.Bus;
import com.jtech.imaging.util.Tools;
import com.liulishuo.filedownloader.util.FileDownloadUtils;

import java.io.File;

import io.realm.Realm;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 图片详情P类
 * Created by jianghan on 2016/10/8.
 */

public class PhotoDetailPresenter implements PhotoDetailContract.Presenter {
    private Context context;
    private PhotoDetailContract.View view;

    private String imageId;
    private String imageName;
    private String imageUrl;

    private PhotoModel photoModel;

    public PhotoDetailPresenter(Context context, PhotoDetailContract.View view, String imageId, String imageName, String imageUrl) {
        this.context = context;
        this.view = view;
        this.imageId = imageId;
        this.imageName = imageName;
        this.imageUrl = imageUrl;
    }

    @Override
    public String getPhotoName() {
        return imageName;
    }

    @Override
    public String getPhotoUrl() {
        return imageUrl;
    }

    @Override
    public String getPhotoId() {
        return imageId;
    }

    @Override
    public PhotoModel getPhotoModel() {
        return photoModel;
    }

    @Override
    public void getPhotoDetailCache(final Context context, final String imageId) {
        Observable
                .just(imageId)
                .subscribeOn(Schedulers.io())
                .map(new Func1<String, PhotoModel>() {
                    @Override
                    public PhotoModel call(String s) {
                        return PhotoCache.get(context).getPhotoDetail(s);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<PhotoModel>() {
                    @Override
                    public void call(PhotoModel photoModel) {
                        if (null != photoModel) {
                            PhotoDetailPresenter.this.photoModel = photoModel;
                            view.success(photoModel);
                            return;
                        }
                        getPhotoDetail(context, imageId, 0, 0, "0");
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        view.fail(throwable.getMessage());
                    }
                });
    }

    @Override
    public void getPhotoDetail(final Context context, final String imageId, int imageWidth, int imageHeight, String rect) {
        API
                .get()
                .unsplashApi(context)
                .photoDetail(imageId, imageWidth, imageHeight, rect)
                .subscribeOn(Schedulers.io())
                .map(new Func1<PhotoModel, PhotoModel>() {
                    @Override
                    public PhotoModel call(PhotoModel photoModel) {
                        if (null != photoModel) {
                            PhotoCache.get(context).setPhotoDetail(imageId, photoModel);
                        }
                        return photoModel;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<PhotoModel>() {
                    @Override
                    public void call(PhotoModel photoModel) {
                        PhotoDetailPresenter.this.photoModel = photoModel;
                        view.success(photoModel);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        view.fail(throwable.getMessage());
                    }
                });
    }

    @Override
    public void startDownload() {
        String url = photoModel.getUrls().getRaw();
        String path = getPath(System.currentTimeMillis() + "");
        final long id = FileDownloadUtils.generateId(url, path, false);
        String name = photoModel.getUser().getName();
        String color = photoModel.getColor();
        int width = photoModel.getWidth();
        int height = photoModel.getHeight();
        String md5 = Tools.md5(name + width + height + url);
        DownloadRealmManager downloadRealmManager = new DownloadRealmManager();
        if (downloadRealmManager.hasDownload(md5)) {
            view.downloadFail("already exists");
            return;
        }
        DownloadModel downloadModel = new DownloadModel(id, name, color, width, height, md5, url, path);
        downloadRealmManager
                .addDownloadAndStart(downloadModel, new Realm.Transaction.OnSuccess() {
                    @Override
                    public void onSuccess() {
                        view.downloadSuccess();
                        //发送任务开始消息
                        Bus.get().post(new DownloadEvent.StateEvent(id, DownloadState.DOWNLOAD_WAITING));
                    }
                }, new Realm.Transaction.OnError() {
                    @Override
                    public void onError(Throwable error) {
                        view.downloadFail("download error");
                    }
                });
    }

    /**
     * 获取存储路径
     *
     * @param fileName
     * @return
     */
    private String getPath(String fileName) {
        File file = new File(context.getCacheDir(), Constants.CACHE_NAME + File.separator + fileName + ".jpg");
        return file.getAbsolutePath();
    }
}
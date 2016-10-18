package com.jtech.imaging.presenter;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;

import com.jtech.imaging.contract.WallpaperContract;
import com.jtechlib.Util.DeviceUtils;

import java.io.IOException;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 壁纸设置P类
 * Created by wuxubaiyang on 16/4/16.
 */
public class WallpaperPresenter implements WallpaperContract.Presenter {

    private WallpaperContract.View view;
    private Context context;

    private int position = -1;

    public WallpaperPresenter(Context context, WallpaperContract.View view) {
        this.context = context;
        this.view = view;
    }

    @Override
    public String getUrl(String originUrl, int width) {
        return originUrl + "?w=" + getSelectWidth(width);
    }

    @Override
    public int getSelectWidth(int width) {
        switch (position) {
            case 0://480p
                return 480;
            case 1://720p
                return 720;
            case 2://1080p
                return 1080;
            case 3://与当前屏幕宽度相同
                return width;
            default://默认状态
                return 480;
        }
    }

    @Override
    public void setSelectPosition(int position) {
        this.position = position;
    }

    @Override
    public boolean isRightBitmap(Activity activity, Bitmap bitmap) {
        if (null == bitmap) {
            return false;
        }
        //判断这个bitmap的宽度是否正确
        return bitmap.getWidth() == getSelectWidth(DeviceUtils.getScreenWidth(activity));
    }

    @Override
    public void setWallpaper(Bitmap bitmap) {
        //设置为壁纸
        Observable.just(bitmap)
                .subscribeOn(Schedulers.io())
                .map(new Func1<Bitmap, Boolean>() {
                    @Override
                    public Boolean call(Bitmap bitmap) {
                        try {
                            WallpaperManager.getInstance(context).setBitmap(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                            return false;
                        }
                        return true;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean) {
                            view.setWallpaperSuccess();
                        } else {
                            view.setWallpaperFail();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        view.setWallpaperFail();
                    }
                });
    }
}
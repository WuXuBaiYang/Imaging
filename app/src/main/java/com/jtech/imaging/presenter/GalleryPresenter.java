package com.jtech.imaging.presenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import com.jtech.imaging.contract.GalleryContract;
import com.jtech.imaging.util.Tools;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 画廊P类
 * Created by jianghan on 2016/11/21.
 */

public class GalleryPresenter implements GalleryContract.Presenter {
    private Context context;
    private GalleryContract.View view;

    public GalleryPresenter(Context context, GalleryContract.View view) {
        this.context = context;
        this.view = view;
    }

    @Override
    public void getImage(String uri, int targetHeight, Action1<Bitmap> action1) {
        //放入map
        Map<String, Object> map = new HashMap<>();
        map.put("targetHeight", targetHeight);
        map.put("uri", uri);
        //处理图片
        Observable
                .just(map)
                .subscribeOn(Schedulers.io())
                .map(new Func1<Map<String, Object>, Bitmap>() {
                    @Override
                    public Bitmap call(Map<String, Object> map) {
                        int targetHeight = (int) map.get("targetHeight");
                        targetHeight = targetHeight > 1920 ? 1920 : targetHeight;
                        String uri = (String) map.get("uri");
                        if (!TextUtils.isEmpty(uri) && new File(uri).exists()) {
                            BitmapFactory.Options options = new BitmapFactory.Options();
                            options.inJustDecodeBounds = true;
                            BitmapFactory.decodeFile(uri, options);
                            double ratio = (1.0 * targetHeight) / options.outHeight;
                            int targetWidth = (int) (ratio * options.outWidth);
                            options.inSampleSize = Tools.computeSampleSize(options, -1, targetWidth * targetHeight);
                            options.inJustDecodeBounds = false;
                            return BitmapFactory.decodeFile(uri, options);
                        }
                        return null;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(action1, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
    }
}
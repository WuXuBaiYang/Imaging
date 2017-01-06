package com.jtech.imaging.mvp.presenter;

import android.content.Context;
import android.graphics.Bitmap;

import com.jtech.imaging.mvp.contract.PhotoContract;
import com.jtechlib.Util.ImageUtils;

import rx.functions.Action1;

/**
 * 图片P类
 * Created by jianghan on 2016/11/23.
 */

public class PhotoPresenter implements PhotoContract.Presenter {

    private Context context;
    private PhotoContract.View view;

    public PhotoPresenter(Context context, PhotoContract.View view) {
        this.context = context;
        this.view = view;
    }

    @Override
    public void getImage(String uri, int width, int height, int targetWidth) {
        //等比缩放
        double ratio = (1.0 * targetWidth) / width;
        int targetHeight = (int) (height * ratio);
        //获取目标大小的图片
        ImageUtils.requestImage(context, uri, targetWidth, targetHeight, new Action1<Bitmap>() {
            @Override
            public void call(Bitmap bitmap) {
                view.setPhoto(bitmap);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }
}
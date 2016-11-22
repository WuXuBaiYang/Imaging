package com.jtech.imaging.contract;

import android.app.Activity;
import android.graphics.Bitmap;

import com.jtech.imaging.model.DownloadModel;
import com.jtechlib.contract.BaseContract;

import rx.functions.Action1;

/**
 * 设置壁纸协议
 * Created by jianghan on 2016/10/14.
 */

public interface WallpaperContract {
    interface Presenter extends BaseContract.Presenter {
        String getUrl(String originUrl, int width);

        void setSelectPosition(int position);

        boolean isRightBitmap(Activity activity, Bitmap bitmap);

        int getSelectWidth(int width);

        void setWallpaper(Bitmap bitmap);

        void getImage(DownloadModel downloadModel, int targetHeight, Action1<Bitmap> action1);
    }

    interface View extends BaseContract.View {
        void setWallpaperSuccess();

        void setWallpaperFail();
    }
}
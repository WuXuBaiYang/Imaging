package com.jtech.imaging.mvp.contract;

import android.app.Activity;
import android.graphics.Bitmap;

import com.jtechlib.contract.BaseContract;

/**
 * 设置壁纸协议
 * Created by jianghan on 2016/10/14.
 */

public interface WallpaperContract {
    interface Presenter extends BaseContract.Presenter {
        String getUrl(int width);

        void setSelectPosition(int position);

        boolean isRightBitmap(Activity activity, Bitmap bitmap);

        int getSelectWidth(int width);

        void setWallpaper(Bitmap bitmap);

        boolean isLocalImage();
    }

    interface View extends BaseContract.View {
        void setWallpaperSuccess();

        void setWallpaperFail();
    }
}
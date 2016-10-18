package com.jtech.imaging.view.widget.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.jtech.imaging.R;
import com.jtech.imaging.cache.PhotoCache;
import com.jtech.imaging.strategy.PhotoResolutionStrategy;

/**
 * 图片分辨率选择(图片详情)dialog
 * Created by jianghan on 2016/10/13.
 */

public class PhotoResolutionDialog {
    private Context context;
    private AlertDialog.Builder builder;

    public PhotoResolutionDialog(Context context) {
        this.context = context;
        //实例化builder
        builder = new AlertDialog.Builder(context);
    }

    /**
     * 创建对象
     *
     * @param context
     * @return
     */
    public static PhotoResolutionDialog build(Context context) {
        return new PhotoResolutionDialog(context);
    }

    /**
     * 设置监听
     *
     * @param onClickListener
     * @return
     */
    public PhotoResolutionDialog setSingleChoiceItems(DialogInterface.OnClickListener onClickListener) {
        String[] imageResolutionStrategies = context.getResources().getStringArray(R.array.imageResolutiornStrategy);
        int photoResolution = PhotoCache.get(context).getPhotoResolution();
        int checkedItem = 0;
        switch (photoResolution) {
            case PhotoResolutionStrategy.PHOTO_RESOLUTION_1080://1080p
                checkedItem = 0;
                break;
            case PhotoResolutionStrategy.PHOTO_RESOLUTION_720://720p
                checkedItem = 1;
                break;
            case PhotoResolutionStrategy.PHOTO_RESOLUTION_480://480p
                checkedItem = 2;
                break;
            default:
                break;
        }
        builder.setSingleChoiceItems(imageResolutionStrategies, checkedItem, onClickListener);
        return this;
    }

    /**
     * 显示dialog
     *
     * @return
     */
    public void show() {
        builder.show();
    }
}
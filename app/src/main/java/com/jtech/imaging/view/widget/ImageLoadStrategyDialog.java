package com.jtech.imaging.view.widget;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.jtech.imaging.R;
import com.jtech.imaging.cache.PhotoCache;
import com.jtech.imaging.strategy.PhotoLoadStrategy;

/**
 * 图片加载策略dialog
 * Created by jianghan on 2016/10/13.
 */

public class ImageLoadStrategyDialog {
    private Context context;
    private AlertDialog.Builder builder;

    public ImageLoadStrategyDialog(Context context) {
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
    public static ImageLoadStrategyDialog build(Context context) {
        return new ImageLoadStrategyDialog(context);
    }

    /**
     * 设置监听
     *
     * @param onClickListener
     * @return
     */
    public ImageLoadStrategyDialog setSingleChoiceItems(DialogInterface.OnClickListener onClickListener) {
        String[] imageLoadStrategies = context.getResources().getStringArray(R.array.image_load_strategy);
        int photoLoadStrategy = PhotoCache.get(context).getPhotoLoadStrategy();
        int checkedItem = 0;
        switch (photoLoadStrategy) {
            case PhotoLoadStrategy.PHOTO_LOAD_STRATEGY_FIXED_FULL://全尺寸
            case PhotoLoadStrategy.PHOTO_LOAD_STRATEGY_FIXED_1080://最高1080
            case PhotoLoadStrategy.PHOTO_LOAD_STRATEGY_FIXED_720://最高720
            case PhotoLoadStrategy.PHOTO_LOAD_STRATEGY_FIXED_480://最高480
            case PhotoLoadStrategy.PHOTO_LOAD_STRATEGY_FIXED_200://最高200
                checkedItem = 0;
                break;
            case PhotoLoadStrategy.PHOTO_LOAD_STRATEGY_NET_AUTO://根据网络自动调整
                checkedItem = 1;
                break;
            case PhotoLoadStrategy.PHOTO_LOAD_STRATEGY_AUTO://无视网络自动调整
                checkedItem = 2;
                break;
        }
        builder.setSingleChoiceItems(imageLoadStrategies, checkedItem, onClickListener);
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
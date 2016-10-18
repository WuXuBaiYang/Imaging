package com.jtech.imaging.view.widget.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.jtech.imaging.R;
import com.jtech.imaging.strategy.PhotoLoadStrategy;

/**
 * 图片加载策略,固定分辨率dialog
 * Created by jianghan on 2016/10/13.
 */

public class ImageLoadStrategyFixedDialog {
    private Context context;
    private int photoLoadStrategy;
    private AlertDialog.Builder builder;

    public ImageLoadStrategyFixedDialog(Context context, int photoLoadStrategy) {
        this.context = context;
        this.photoLoadStrategy = photoLoadStrategy;
        //实例化builder
        builder = new AlertDialog.Builder(context);
    }

    /**
     * 创建对象
     *
     * @param context
     * @return
     */
    public static ImageLoadStrategyFixedDialog build(Context context, int photoLoadStrategy) {
        return new ImageLoadStrategyFixedDialog(context, photoLoadStrategy);
    }

    /**
     * 设置监听
     *
     * @param onClickListener
     * @return
     */
    public ImageLoadStrategyFixedDialog setSingleChoiceItems(DialogInterface.OnClickListener onClickListener) {
        String[] imageLoadStrategyFixeds = context.getResources().getStringArray(R.array.imageLoadStrategyFixed);
        int checkedItem = 0;
        switch (photoLoadStrategy) {
            case PhotoLoadStrategy.PHOTO_LOAD_STRATEGY_FIXED_FULL://全尺寸
                checkedItem = 0;
                break;
            case PhotoLoadStrategy.PHOTO_LOAD_STRATEGY_FIXED_1080://最高1080
                checkedItem = 1;
                break;
            case PhotoLoadStrategy.PHOTO_LOAD_STRATEGY_FIXED_720://最高720
                checkedItem = 2;
                break;
            case PhotoLoadStrategy.PHOTO_LOAD_STRATEGY_FIXED_480://最高480
                checkedItem = 3;
                break;
            case PhotoLoadStrategy.PHOTO_LOAD_STRATEGY_FIXED_200://最高200
                checkedItem = 4;
                break;
            default:
                break;
        }
        builder.setSingleChoiceItems(imageLoadStrategyFixeds, checkedItem, onClickListener);
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
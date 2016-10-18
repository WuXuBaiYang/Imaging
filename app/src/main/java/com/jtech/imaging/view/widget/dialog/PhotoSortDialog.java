package com.jtech.imaging.view.widget.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.jtech.imaging.R;
import com.jtech.imaging.cache.OrderByCache;

/**
 * 图片排序dialog
 * Created by jianghan on 2016/10/13.
 */

public class PhotoSortDialog {
    private Context context;
    private AlertDialog.Builder builder;

    public PhotoSortDialog(Context context) {
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
    public static PhotoSortDialog build(Context context) {
        return new PhotoSortDialog(context);
    }

    /**
     * 设置监听
     *
     * @param onClickListener
     * @return
     */
    public PhotoSortDialog setSingleChoiceItems(DialogInterface.OnClickListener onClickListener) {
        String[] sorts = context.getResources().getStringArray(R.array.sort);
        builder.setSingleChoiceItems(sorts, OrderByCache.get(context).getOrderByIndex(), onClickListener);
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
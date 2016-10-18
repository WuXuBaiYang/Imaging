package com.jtech.imaging.view.widget.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.ImageView;

/**
 * 设置为壁纸的预览dialog
 * Created by jianghan on 2016/10/13.
 */

public class WallpaperDialog {
    private AlertDialog.Builder builder;

    private Bitmap bitmap;

    public WallpaperDialog(Context context, Bitmap bitmap) {
        this.bitmap = bitmap;
        //实例化builder
        this.builder = new AlertDialog.Builder(context);
        //设置要显示的内容
        builder.setView(getContentView(context, bitmap));
        //设置取消按钮
        builder.setNegativeButton("cancel", null);
    }

    /**
     * 获取要显示的内容视图
     *
     * @param bitmap
     * @return
     */
    private View getContentView(Context context, Bitmap bitmap) {
        AppCompatImageView appCompatImageView = new AppCompatImageView(context);
        appCompatImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        appCompatImageView.setImageBitmap(bitmap);
        return appCompatImageView;
    }

    /**
     * 创建对象
     *
     * @param context
     * @return
     */
    public static WallpaperDialog build(Context context, @NonNull Bitmap bitmap) {
        return new WallpaperDialog(context, bitmap);
    }

    /**
     * 设置完成按钮监听
     *
     * @param onWallpaperSetState
     * @return
     */
    public WallpaperDialog setDoneClick(@NonNull final OnWallpaperSetState onWallpaperSetState) {
        builder.setPositiveButton("done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //完成按钮的点击事件
                onWallpaperSetState.done(bitmap);
            }
        });
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

    /**
     * 壁纸设置结果
     */
    public interface OnWallpaperSetState {
        void done(Bitmap bitmap);
    }
}
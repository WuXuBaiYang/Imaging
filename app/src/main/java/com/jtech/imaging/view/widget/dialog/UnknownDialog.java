package com.jtech.imaging.view.widget.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * 未知图片 dialog
 * Created by jianghan on 2016/11/23.
 */

public class UnknownDialog {
    private Context context;
    private AlertDialog.Builder builder;

    public UnknownDialog(Context context) {
        this.context = context;
        //实例化builder
        builder = new AlertDialog.Builder(context);
        //设置标题
        builder.setTitle("unknown");
        //设置取消按钮
        builder.setNegativeButton("cancel", null);
    }

    public static UnknownDialog build(Context context) {
        return new UnknownDialog(context);
    }

    /**
     * 设置文本内容
     *
     * @param content
     * @return
     */
    public UnknownDialog setContent(String content) {
        builder.setMessage(content);
        return this;
    }

    /**
     * 设置删除按钮的点击事件
     *
     * @param onUnknownClick
     * @return
     */
    public UnknownDialog setDoneClick(final OnUnknownClick onUnknownClick) {
        builder.setPositiveButton("redownload", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //完成按钮的点击事件
                onUnknownClick.redownload();
            }
        });
        return this;
    }

    /**
     * 显示dialog
     */
    public void show() {
        builder.show();
    }

    public interface OnUnknownClick {
        void redownload();
    }
}
package com.jtech.imaging.view.widget.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * 删除 dialog
 * Created by jianghan on 2016/11/23.
 */

public class DeleteDialog {
    private Context context;
    private AlertDialog.Builder builder;

    public DeleteDialog(Context context) {
        this.context = context;
        //实例化builder
        builder = new AlertDialog.Builder(context);
        //设置标题
        builder.setTitle("delete");
        //设置取消按钮
        builder.setNegativeButton("cancel", null);
    }

    public static DeleteDialog build(Context context) {
        return new DeleteDialog(context);
    }

    /**
     * 设置文本内容
     *
     * @param content
     * @return
     */
    public DeleteDialog setContent(String content) {
        builder.setMessage(content);
        return this;
    }

    /**
     * 设置删除按钮的点击事件
     *
     * @param onDeleteListener
     * @return
     */
    public DeleteDialog setDoneClick(final OnDeleteListener onDeleteListener) {
        builder.setPositiveButton("delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //完成按钮的点击事件
                onDeleteListener.delete();
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

    public interface OnDeleteListener {
        void delete();
    }
}
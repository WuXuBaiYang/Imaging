package com.jtech.imaging.view.widget;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * 自定义进度dialog
 * Created by wuxubaiyang on 16/4/20.
 */
public class CustomProgressDialog {
    private static ProgressDialog progressDialog;


    public static void showProgressDialog(Context context) {
        showProgressDialog(context, "", true);
    }

    public static void showProgressDialog(Context context, String message) {
        showProgressDialog(context, message, true);
    }

    public static void showProgressDialog(Context context, boolean cancelable) {
        showProgressDialog(context, "", cancelable);
    }

    public static void showProgressDialog(Context context, String message, boolean cancelable) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(cancelable);
        progressDialog.show();
    }

    public static void updataMessage(String message) {
        if (null != progressDialog) {
            progressDialog.setMessage(message);
        }
    }

    ;

    public static void dismissProgressDialog() {
        if (null != progressDialog) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }
}

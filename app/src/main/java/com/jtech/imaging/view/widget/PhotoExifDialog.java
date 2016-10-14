package com.jtech.imaging.view.widget;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;

import com.jtech.imaging.model.PhotoModel;

/**
 * 图片参数信息dialog
 * Created by jianghan on 2016/10/13.
 */

public class PhotoExifDialog {
    private Context context;
    private PhotoModel photoModel;
    private AlertDialog.Builder builder;

    public PhotoExifDialog(Context context, PhotoModel photoModel) {
        this.context = context;
        this.photoModel = photoModel;
        //实例化builder
        builder = new AlertDialog.Builder(context);
        //设置标题
        builder.setTitle("Photo Exif");
        //拼接要显示的参数信息
        PhotoModel.ExifModel exifModel = photoModel.getExif();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Aperture：" + exifModel.getAperture() + "\n");
        stringBuffer.append("ExposureTime：" + exifModel.getExposureTime() + "\n");
        stringBuffer.append("FocalLength：" + exifModel.getFocalLength() + "\n");
        stringBuffer.append("Make：" + exifModel.getMake() + "\n");
        stringBuffer.append("Model：" + exifModel.getModel() + "\n");
        stringBuffer.append("Iso：" + exifModel.getIso());
        //设置内容
        builder.setMessage(stringBuffer.toString());
    }

    /**
     * 创建对象
     *
     * @param context
     * @return
     */
    public static PhotoExifDialog build(Context context, @NonNull PhotoModel photoModel) {
        return new PhotoExifDialog(context, photoModel);
    }

    /**
     * 设置完成按钮监听
     *
     * @param onClickListener
     * @return
     */
    public PhotoExifDialog setDoneClick(DialogInterface.OnClickListener onClickListener) {
        builder.setPositiveButton("done", onClickListener);
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
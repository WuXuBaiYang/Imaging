package com.jtech.imaging.view.widget;

import android.content.Context;
import android.support.design.widget.BottomSheetDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jtech.imaging.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.functions.Action1;

/**
 * 图片详情的sheet
 * Created by jianghan on 2016/10/13.
 */

public class PhotoDetailSheetDialog {

    @Bind(R.id.sheet_photo_detail)
    LinearLayout linearLayout;
    @Bind(R.id.sheet_photo_detail_resolution)
    TextView textViewResolution;

    private BottomSheetDialog bottomSheetDialog;
    private Context context;

    public PhotoDetailSheetDialog(Context context) {
        this.context = context;
        //实例化dialog
        bottomSheetDialog = new BottomSheetDialog(context);
        //实例化视图
        View contentView = LayoutInflater.from(context).inflate(R.layout.view_sheet_photo_detail, null, false);
        //绑定视图到ButterKnife
        ButterKnife.bind(this, contentView);
        //设置视图到dialog
        bottomSheetDialog.setContentView(contentView);
    }

    /**
     * 创建一个dialog
     *
     * @param context
     * @return
     */
    public static PhotoDetailSheetDialog build(Context context) {
        return new PhotoDetailSheetDialog(context);
    }

    /**
     * 设置item点击事件
     *
     * @param onPhotoDetailSheetItemClick
     * @return
     */
    public PhotoDetailSheetDialog setOnPhotoDetailSheetItemClick(final OnPhotoDetailSheetItemClick onPhotoDetailSheetItemClick) {
        if (null != onPhotoDetailSheetItemClick) {
            for (int i = 0; i < linearLayout.getChildCount(); i++) {
                final int position = i;
                RxCompat.clickThrottleFirst(linearLayout.getChildAt(i), new Action1() {
                    @Override
                    public void call(Object o) {
                        onPhotoDetailSheetItemClick.onItemClick(bottomSheetDialog, position);
                    }
                });
            }
        }
        return this;
    }

    /**
     * 设置当前分辨率
     *
     * @param resolution
     * @return
     */
    public PhotoDetailSheetDialog setResolution(String resolution) {
        textViewResolution.setText(context.getString(R.string.photo_detail_sheet_resolution) + "(" + resolution + ")");
        return this;
    }

    /**
     * 显示dialog
     */
    public void show() {
        bottomSheetDialog.show();
    }

    /**
     * sheet的item点击事件
     */
    public interface OnPhotoDetailSheetItemClick {
        void onItemClick(BottomSheetDialog bottomSheetDialog, int position);
    }
}
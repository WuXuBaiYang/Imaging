package com.jtech.imaging.view.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jtech.adapter.RecyclerAdapter;
import com.jtech.imaging.R;
import com.jtech.imaging.common.DownloadState;
import com.jtech.imaging.model.DownloadModel;
import com.jtech.view.RecyclerHolder;
import com.jtechlib.Util.DeviceUtils;
import com.jtechlib.Util.ImageUtils;

import java.util.ArrayList;

import rx.functions.Action1;

/**
 * 已缓存列表适配器
 * Created by jianghan on 2016/11/1.
 */

public class DownloadedAdapter extends RecyclerAdapter<DownloadModel> {
    private int itemSize;
    private OnDownloadedClickListener onDownloadedClickListener;

    public DownloadedAdapter(Activity activity, int spanCount) {
        super(activity);
        //计算item的宽度
        this.itemSize = DeviceUtils.getScreenWidth(activity) / spanCount;
    }

    public void setOnDownloadedClickListener(OnDownloadedClickListener onDownloadedClickListener) {
        this.onDownloadedClickListener = onDownloadedClickListener;
    }

    /**
     * 获取图片集合
     *
     * @return
     */
    public ArrayList<String> getImageUris() {
        ArrayList<String> imageUris = new ArrayList<>();
        for (int i = 0; i < getItemCount(); i++) {
            imageUris.add(TextUtils.isEmpty(getItem(i).getPath()) ? getItem(i).getUrl() : getItem(i).getPath());
        }
        return imageUris;
    }

    @Override
    protected View createView(LayoutInflater layoutInflater, ViewGroup viewGroup, int i) {
        return layoutInflater.inflate(R.layout.view_downloaded, viewGroup, false);
    }

    @Override
    protected void convert(final RecyclerHolder holder, final DownloadModel model, final int position) {
        //设置item的宽高
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        layoutParams.width = itemSize;
        layoutParams.height = itemSize;
        holder.itemView.setLayoutParams(layoutParams);
        //只请求本地图片
        ImageUtils.requestLocalImage(getContext(), model.getPath(), itemSize, -1, new Action1<Bitmap>() {
            @Override
            public void call(Bitmap bitmap) {
                if (null != bitmap) {
                    holder.getImageView(R.id.imageview_photo).setImageBitmap(bitmap);
                }
            }
        });
        //设置默认背景颜色
//        imageView.setBackgroundColor(Color.parseColor(model.getColor()));
        //设置图片本地找不到时的图标以及点击事件
        holder.setViewVisible(R.id.imagebutton_photo_unknown, model.getState() == DownloadState.DOWNLOADED_NOT_FOUND);
        //设置unknown的点击事件
        holder.setClickListener(R.id.imagebutton_photo_unknown, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != onDownloadedClickListener) {
                    onDownloadedClickListener.onUnknownClick(model.getId(), position);
                }
            }
        });
    }

    /**
     * 已下载列表的点击事件
     */
    public interface OnDownloadedClickListener {
        void onUnknownClick(long id, int position);
    }
}
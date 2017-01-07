package com.jtech.imaging.view.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jtech.adapter.RecyclerAdapter;
import com.jtech.imaging.R;
import com.jtech.imaging.common.DownloadState;
import com.jtech.imaging.model.DownloadModel;
import com.jtech.imaging.strategy.PhotoLoadStrategy;
import com.jtech.view.RecyclerHolder;
import com.jtechlib.Util.DeviceUtils;
import com.jtechlib.Util.ImageUtils;

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

    @Override
    protected View createView(LayoutInflater layoutInflater, ViewGroup viewGroup, int i) {
        return layoutInflater.inflate(R.layout.view_downloaded, viewGroup, false);
    }

    @Override
    protected void convert(RecyclerHolder holder, final DownloadModel model, final int position) {
        //设置item的宽高
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        layoutParams.width = itemSize;
        layoutParams.height = itemSize;
        holder.itemView.setLayoutParams(layoutParams);
        //设置图片
        final ImageView imageView = holder.getImageView(R.id.imageview_photo);
        String imageUri = TextUtils.isEmpty(model.getPath()) ? PhotoLoadStrategy.getUrl(getContext(), model.getUrl(), itemSize) : model.getPath();
        ImageUtils.requestImage(getContext(), imageUri, itemSize, itemSize, new Action1<Bitmap>() {
            @Override
            public void call(Bitmap bitmap) {
                if (null != bitmap) {
                    imageView.setImageBitmap(bitmap);
                }
            }
        });
        //设置默认背景颜色
        imageView.setBackgroundColor(Color.parseColor(model.getColor()));
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
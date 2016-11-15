package com.jtech.imaging.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.jtech.adapter.RecyclerAdapter;
import com.jtech.imaging.R;
import com.jtech.imaging.common.DownloadState;
import com.jtech.imaging.model.DownloadModel;
import com.jtech.view.RecyclerHolder;

/**
 * 缓存列表适配器
 * Created by jianghan on 2016/11/1.
 */

public class DownloadingAdapter extends RecyclerAdapter<DownloadModel> {
    public DownloadingAdapter(Context context) {
        super(context);
    }

    @Override
    protected View createView(LayoutInflater layoutInflater, ViewGroup viewGroup, int i) {
        return layoutInflater.inflate(R.layout.view_downloading, viewGroup, false);
    }

    @Override
    protected void convert(RecyclerHolder holder, DownloadModel model, int position) {
        //获取任务状态
        int state = model.getState();
        //设置名称
        holder.setText(R.id.textview_downloading_name, model.getName());
        //设置状态
        holder.setText(R.id.textview_downloading_status, getStateDescribe(state));
        //设置状态图标
        holder.setImageResource(R.id.imageview_downloading_status, getStateIcon(state));
        //判断是否需要显示进度条
        holder.setViewVisible(R.id.progressbar_downloading_progress, isVisibleProgressbar(state));
        if (isVisibleProgressbar(state)) {
            //获取进度条的对象
            ProgressBar progressBar = holder.getView(R.id.progressbar_downloading_progress);
            //设置状态
            progressBar.setIndeterminate(isIndeterminate(state));
            //设置进度
            int progress = (int) ((1.0 * model.getDownloadSize()) / model.getSize() * 100);
            progressBar.setProgress(progress);
        }
    }

    /**
     * 根据状态获取描述
     *
     * @param state
     * @return
     */
    private String getStateDescribe(int state) {
        if (state == DownloadState.DOWNLOADING) {
            return "downloading";
        } else if (state == DownloadState.DOWNLOAD_STOP) {
            return "stop";
        } else if (state == DownloadState.DOWNLOAD_STOPING) {
            return "stoping";
        } else if (state == DownloadState.DOWNLOAD_CONNECTION) {
            return "connection";
        } else if (state == DownloadState.DOWNLOAD_FAIL_UNKNOWN) {
            return "unknown";
        } else if (state == DownloadState.DOWNLOAD_FAIL_INTENT_CHANGE) {
            return "intent change";
        } else if (state == DownloadState.DOWNLOAD_FAIL_INTENT_ERROR) {
            return "intent error";
        }
        return "unknown";
    }

    /**
     * 获取状态图表
     * (下载中，连接中)暂停图标
     *
     * @param state
     * @return
     */
    private int getStateIcon(int state) {
        if (state == DownloadState.DOWNLOADING || state == DownloadState.DOWNLOAD_CONNECTION) {
            return R.drawable.ic_pause_black_18dp;
        }
        return R.drawable.ic_file_download_black_18dp;
    }

    /**
     * 是否需要显示进度条
     * (暂停中，连接中，下载中)
     *
     * @param state
     * @return
     */
    private boolean isVisibleProgressbar(int state) {
        return state == DownloadState.DOWNLOAD_STOPING || state == DownloadState.DOWNLOAD_CONNECTION || state == DownloadState.DOWNLOADING;
    }

    /**
     * 判断当前状态是否显示为不确定状态
     * (暂停中，连接中)
     *
     * @param state
     * @return
     */
    private boolean isIndeterminate(int state) {
        return state == DownloadState.DOWNLOAD_STOPING || state == DownloadState.DOWNLOAD_CONNECTION;
    }
}
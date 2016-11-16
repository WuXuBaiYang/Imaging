package com.jtech.imaging.strategy;

import android.content.Context;

import com.jtech.imaging.R;
import com.jtech.imaging.common.DownloadState;

/**
 * 下载策略
 * Created by jianghan on 2016/10/21.
 */

public class DownloadStrategy {
    /**
     * 根据状态获取状态描述
     *
     * @return
     */
    public static String getDescribeByState(Context context, int state) {
        if (state == DownloadState.DOWNLOADING) {
            return context.getString(R.string.download_state_downloading);
        } else if (state == DownloadState.DOWNLOADED) {
            return context.getString(R.string.download_state_downloaded);
        } else if (state == DownloadState.DOWNLOAD_STOP) {
            return context.getString(R.string.download_state_stop);
        } else if (state == DownloadState.DOWNLOADED_NOT_FOUND) {
            return context.getString(R.string.download_state_not_found);
        } else if (state == DownloadState.DOWNLOAD_FAIL_UNKNOWN) {
            return context.getString(R.string.download_state_fail_unknown);
        } else if (state == DownloadState.DOWNLOAD_FAIL_INTENT_CHANGE) {
            return context.getString(R.string.download_state_fail_intent_change);
        } else if (state == DownloadState.DOWNLOAD_FAIL_INTENT_ERROR) {
            return context.getString(R.string.download_state_fail_intent_error);
        }
        return "";
    }
}
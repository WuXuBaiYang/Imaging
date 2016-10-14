package com.jtech.imaging.contract;

import com.jtechlib.contract.BaseContract;

/**
 * 设置壁纸协议
 * Created by jianghan on 2016/10/14.
 */

public interface WallpaperContract {
    interface Presenter extends BaseContract.Presenter {
        String getUrl(String originUrl,String width);
    }

    interface View extends BaseContract.View {

    }
}
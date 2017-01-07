package com.jtech.imaging.mvp.contract;

import java.util.List;

/**
 * 画廊浏览协议
 * Created by jianghan on 2016/11/21.
 */

public interface GalleryContract {
    interface Presenter {
        List<String> getImageUris();

        int getSelectIndex();
    }

    interface View {
    }
}
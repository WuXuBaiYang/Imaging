package com.jtech.imaging.net;

import com.jtech.imaging.net.call.JCall;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于管理请求
 * Created by wuxubaiyang on 16/4/18.
 */
public class RequestManager {
    private List<JCall> calls;

    public RequestManager() {
        calls = new ArrayList<>();
    }

    /**
     * 添加请求
     *
     * @param call
     */
    public RequestManager addCall(JCall call) {
        if (null != call && null != calls) {
            calls.add(call);
        }
        return this;
    }

    /**
     * 取消全部请求
     */
    public void clearAllCall() {
        if (null != calls) {
            for (int i = 0; i < calls.size(); i++) {
                JCall call = calls.get(i);
                if (null != call) {
                    call.cancel();
                }
            }
        }
    }
}
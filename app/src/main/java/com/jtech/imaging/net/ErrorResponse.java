package com.jtech.imaging.net;

import org.json.JSONObject;

/**
 * Created by wuxubaiyang on 16/3/7.
 */
public class ErrorResponse {
    private int code;
    private String message;
    private JSONObject extra;

    public ErrorResponse(int code, String msg) {
        this.code = code;
        this.message = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public JSONObject getExtra() {
        return extra;
    }

    public void setExtra(JSONObject extra) {
        this.extra = extra;
    }
}

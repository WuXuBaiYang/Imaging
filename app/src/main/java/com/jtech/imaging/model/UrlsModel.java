package com.jtech.imaging.model;

import com.google.gson.annotations.SerializedName;
import com.jtechlib.model.BaseModel;

/**
 * 链接对象
 * Created by jianghan on 2016/11/15.
 */

public class UrlsModel extends BaseModel {
    @SerializedName("raw")
    private String raw;
    @SerializedName("full")
    private String full;
    @SerializedName("regular")
    private String regular;
    @SerializedName("small")
    private String small;
    @SerializedName("thumb")
    private String thumb;
    @SerializedName("custom")
    private String custom;

    public String getRaw() {
        return raw;
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }

    public String getFull() {
        return full;
    }

    public void setFull(String full) {
        this.full = full;
    }

    public String getRegular() {
        return regular;
    }

    public void setRegular(String regular) {
        this.regular = regular;
    }

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getCustom() {
        return custom;
    }

    public void setCustom(String custom) {
        this.custom = custom;
    }
}
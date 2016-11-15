package com.jtech.imaging.model;

import com.google.gson.annotations.SerializedName;
import com.jtechlib.model.BaseModel;

/**
 * 标记对象
 * Created by jianghan on 2016/11/15.
 */

public class BadgeModel extends BaseModel {
    @SerializedName("title")
    private String title;
    @SerializedName("primary")
    private boolean primary;
    @SerializedName("slug")
    private String slug;
    @SerializedName("link")
    private String link;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isPrimary() {
        return primary;
    }

    public void setPrimary(boolean primary) {
        this.primary = primary;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
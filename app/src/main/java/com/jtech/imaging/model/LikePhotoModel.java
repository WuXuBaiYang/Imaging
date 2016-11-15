package com.jtech.imaging.model;

import com.jtechlib.model.BaseModel;

/**
 * 喜欢
 * Created by jianghan on 2016/8/31.
 */
public class LikePhotoModel extends BaseModel {

    private PhotoModel photo;
    private UserModel user;

    public PhotoModel getPhoto() {
        return photo;
    }

    public void setPhoto(PhotoModel photo) {
        this.photo = photo;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }
}
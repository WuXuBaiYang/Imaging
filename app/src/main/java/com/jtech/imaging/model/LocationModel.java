package com.jtech.imaging.model;

import com.google.gson.annotations.SerializedName;
import com.jtechlib.model.BaseModel;

public class LocationModel extends BaseModel {
    @SerializedName("city")
    private String city;
    @SerializedName("country")
    private String country;
    @SerializedName("position")
    private PositionModel position;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public PositionModel getPosition() {
        return position;
    }

    public void setPosition(PositionModel position) {
        this.position = position;
    }
}
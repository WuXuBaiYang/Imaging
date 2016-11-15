package com.jtech.imaging.model;

import com.google.gson.annotations.SerializedName;
import com.jtechlib.model.BaseModel;

import java.util.List;

/**
 * 图片搜索结果
 * Created by jianghan on 2016/9/5.
 */
public class SearchPhotoModel extends BaseModel {

    @SerializedName("total")
    private int total;
    @SerializedName("total_pages")
    private int totalPages;
    @SerializedName("results")
    private List<ResultsModel> results;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<ResultsModel> getResults() {
        return results;
    }

    public void setResults(List<ResultsModel> results) {
        this.results = results;
    }
}
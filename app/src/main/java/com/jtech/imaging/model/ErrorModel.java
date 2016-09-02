package com.jtech.imaging.model;

import java.util.List;

/**
 * 错误状态
 * Created by jianghan on 2016/9/2.
 */
public class ErrorModel {
    private String error;
    private String errorDescription;
    private List<String> errors;

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }
}
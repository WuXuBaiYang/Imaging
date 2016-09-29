package com.jtech.imaging.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * 工具类
 * Created by jianghan on 2016/9/29.
 */

public final class Utils {

    public static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat(
            "yyyy-MM-dd");

    /**
     * 日期格式化
     *
     * @param date
     * @return
     */
    public static final String dateFormat(String date) {
        try {
            return DATE_TIME_FORMAT.format(DATE_TIME_FORMAT.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
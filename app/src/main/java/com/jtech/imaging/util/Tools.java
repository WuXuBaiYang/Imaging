package com.jtech.imaging.util;

import java.util.Collection;

/**
 * 工具方法
 * Created by jianghan on 2016/11/17.
 */

public class Tools {
    /**
     * 判断两个集合是否存在不同
     *
     * @param var1
     * @param var2
     * @return
     */
    public static boolean isDifferent(Collection<?> var1, Collection<?> var2) {
        //判断数量是否相同
        if (var1.size() != var2.size()) {
            return true;
        }
        //求差集
        if (!var1.removeAll(var2)) {
            return true;
        }
        //差集如果不为0则表示存在不同
        return var1.size() != 0;
    }
}
package com.jtech.imaging.util;

import android.graphics.BitmapFactory;

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

    /**
     * 计算simplesize
     *
     * @param options
     * @param minSideLength
     * @param maxNumOfPixels
     * @return
     */
    public static int computeSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength, maxNumOfPixels);
        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }
        return roundedSize;
    }

    /**
     * 计算simplesize
     *
     * @param options
     * @param minSideLength
     * @param maxNumOfPixels
     * @return
     */
    private static int computeInitialSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;
        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(Math.floor(w / minSideLength), Math.floor(h / minSideLength));
        if (upperBound < lowerBound) {
            // return the larger one when there is no overlapping zone.
            return lowerBound;
        }
        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }
}
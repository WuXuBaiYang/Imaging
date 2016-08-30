package com.jtech.imaging.util;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.io.IOException;

/**
 * 图片通用类
 * Created by wuxubaiyang on 16/4/18.
 */
public class ImageUtils {

    /**
     * 显示裁剪过的图片
     *
     * @param context
     * @param uri
     * @param imageView
     * @param targetWidth
     * @param targetHeight
     */
    public static <T extends ImageView> void showCropImage(Context context, String uri, T imageView, int targetWidth, int targetHeight) {
        showCropImage(context, uri, imageView, 0, 0, targetWidth, targetHeight);
    }

    /**
     * 显示本地裁剪图片
     *
     * @param context
     * @param uri
     * @param imageView
     * @param targetWidth
     * @param targetHeight
     */
    public static <T extends ImageView> void showLocalCropImage(Context context, String uri, T imageView, int targetWidth, int targetHeight) {
        showCropImage(context, getLocalUri(uri), imageView, 0, 0, targetWidth, targetHeight);
    }

    /**
     * 显示裁剪过的图片
     *
     * @param context
     * @param uri
     * @param imageView
     * @param errorResId
     * @param placeholderResId
     * @param targetWidth
     * @param targetHeight
     */
    public static <T extends ImageView> void showCropImage(Context context, String uri, T imageView, int errorResId, int placeholderResId, int targetWidth, int targetHeight) {
        Picasso.with(context)
                .load(uri)
                .centerCrop()
                .error(errorResId)
                .config(Bitmap.Config.RGB_565)
                .placeholder(placeholderResId)
                .resize(targetWidth, targetHeight)
                .into(imageView);
    }


    /**
     * 显示圆形图片
     *
     * @param context
     * @param uri
     * @param imageView
     */
    public static <T extends ImageView> void showCircleImage(Context context, String uri, T imageView) {
        showCircleImage(context, uri, imageView, 0, 0);
    }

    /**
     * 显示本地圆形图片
     *
     * @param context
     * @param uri
     * @param imageView
     */
    public static <T extends ImageView> void showLocalCircleImage(Context context, String uri, T imageView) {
        showCircleImage(context, getLocalUri(uri), imageView, 0, 0);
    }

    /**
     * 显示圆形图片
     *
     * @param context
     * @param uri
     * @param imageView
     * @param errorResId
     * @param placeholderResId
     */
    public static <T extends ImageView> void showCircleImage(Context context, String uri, T imageView, int errorResId, int placeholderResId) {
        Picasso.with(context)
                .load(uri)
                .config(Bitmap.Config.RGB_565)
                .error(errorResId)
                .placeholder(placeholderResId)
                .transform(new CircleTransform())
                .into(imageView);
    }

    /**
     * 显示一张图片
     *
     * @param context
     * @param uri
     * @param imageView
     */
    public static <T extends ImageView> void showImage(Context context, String uri, T imageView) {
        showImage(context, uri, imageView, 0, 0);
    }

    /**
     * 显示一张本地图片
     *
     * @param context
     * @param uri
     * @param imageView
     */
    public static <T extends ImageView> void showLocalImage(Context context, String uri, T imageView) {
        showImage(context, getLocalUri(uri), imageView, 0, 0);
    }

    /**
     * 显示一张图片
     *
     * @param context
     * @param uri
     * @param imageView
     * @param errorResId
     * @param placeholderResId
     */
    public static <T extends ImageView> void showImage(Context context, String uri, T imageView, int errorResId, int placeholderResId) {
        Picasso.with(context)
                .load(uri)
                .config(Bitmap.Config.RGB_565)
                .error(errorResId)
                .placeholder(placeholderResId)
                .into(imageView);
    }

    /**
     * 请求本地图片，回调bitmap
     *
     * @param context
     * @param uri
     * @param onImageResponse
     */
    public static void requestLocalImage(Context context, String uri, OnImageResponse onImageResponse) {
        requestImage(context, getLocalUri(uri), onImageResponse);
    }

    /**
     * 请求图片，回调bitmap
     *
     * @param context
     * @param uri
     */
    public static void requestImage(final Context context, final String uri, final OnImageResponse onImageResponse) {
        new AsyncTask<Object, Object, Bitmap>() {
            @Override
            protected Bitmap doInBackground(Object... params) {
                Bitmap bitmap = null;
                try {
                    bitmap = Picasso.with(context)
                            .load(uri)
                            .config(Bitmap.Config.RGB_565)
                            .get();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return bitmap;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                if (null != onImageResponse && null != bitmap) {
                    onImageResponse.success(bitmap);
                } else {
                    onImageResponse.fail();
                }
            }
        }.execute("");
    }

    /**
     * 显示大图，放弃内存缓存
     *
     * @param application
     * @param uri
     * @param imageView
     */
    public static <T extends ImageView> void showLargeImage(Application application, String uri, T imageView) {
        showLargeImage(application, uri, imageView, 0, 0);
    }

    /**
     * 显示本地的大图
     *
     * @param application
     * @param uri
     * @param imageView
     */
    public static <T extends ImageView> void showLocalLargeImage(Application application, String uri, T imageView) {
        showLargeImage(application, getLocalUri(uri), imageView, 0, 0);
    }

    /**
     * 显示大图，放弃内存缓存
     *
     * @param application
     * @param uri
     * @param imageView
     * @param errorResId
     * @param placeholderResId
     */
    public static <T extends ImageView> void showLargeImage(Application application, String uri, T imageView, int errorResId, int placeholderResId) {
        Picasso.with(application)
                .load(uri)
                .error(errorResId)
                .placeholder(placeholderResId)
                .skipMemoryCache()
                .into(imageView);
    }

    /**
     * 获取本地地址
     *
     * @param uri
     * @return
     */
    private static String getLocalUri(String uri) {
        if (!uri.startsWith("file://")) {
            uri = "file://" + uri;
        }
        return uri;
    }

    /**
     * 裁剪圆形
     */
    private static class CircleTransform implements Transformation {
        @Override
        public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());

            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;

            Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
            if (squaredBitmap != source) {
                source.recycle();
            }

            Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());

            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint();
            BitmapShader shader = new BitmapShader(squaredBitmap,
                    BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
            paint.setShader(shader);
            paint.setAntiAlias(true);

            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);

            squaredBitmap.recycle();
            return bitmap;
        }

        @Override
        public String key() {
            return "circle";
        }
    }

    /**
     * 图片请求结果回调
     */
    public interface OnImageResponse {
        void success(Bitmap bitmap);

        void fail();
    }
}
package com.jtech.imaging.util;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.widget.RemoteViews;

/**
 * 通知栏消息-基于{@link NotificationCompat.Builder}
 * Created by JTech on 2017/1/11.
 */

public class JNotify {
    public static final int REQUEST_CODE_NOTIFY = 818;

    /**
     * 开始构造
     *
     * @param context
     * @return
     */
    public static NotifyBuilder build(Context context) {
        return new NotifyBuilder(context);
    }

    /**
     * 构建通知栏消息
     */
    public static class NotifyBuilder {
        private NotificationManager notificationManager;
        private NotificationCompat.Builder builder;
        private Context context;

        public NotifyBuilder(Context context) {
            this(context, new NotificationCompat.Builder(context));
        }

        public NotifyBuilder(Context context, NotificationCompat.Builder builder) {
            this.context = context;
            //得到builder
            this.builder = builder;
            //得到通知栏消息服务管理
            this.notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }

        /**
         * 是否自动取消
         *
         * @param autoCancel
         * @return
         */
        public NotifyBuilder setAutoCancel(boolean autoCancel) {
            builder.setAutoCancel(autoCancel);
            return this;
        }

        /**
         * 是否一直显示
         *
         * @param ongoing
         * @return
         */
        public NotifyBuilder setOngoing(boolean ongoing) {
            builder.setOngoing(ongoing);
            return this;
        }

        /**
         * 是否只提示一次
         *
         * @param onlyAlertOnce
         * @return
         */
        public NotifyBuilder setOnlyAlertOnce(boolean onlyAlertOnce) {
            builder.setOnlyAlertOnce(onlyAlertOnce);
            return this;
        }

        /**
         * 通知消息
         *
         * @param tickerText
         * @return
         */
        public NotifyBuilder setTicker(String tickerText) {
            builder.setTicker(tickerText);
            return this;
        }

        /**
         * 设置内容标题
         *
         * @param title
         * @return
         */
        public NotifyBuilder setContentTitle(String title) {
            builder.setContentTitle(title);
            return this;
        }

        /**
         * 设置内容
         *
         * @param text
         * @return
         */
        public NotifyBuilder setContentText(String text) {
            builder.setContentText(text);
            return this;
        }

        /**
         * 设置内容信息
         *
         * @param info
         * @return
         */
        public NotifyBuilder setContentInfo(String info) {
            builder.setContentInfo(info);
            return this;
        }

        /**
         * 设置内容
         *
         * @param views
         * @return
         */
        public NotifyBuilder setContent(RemoteViews views) {
            builder.setContent(views);
            return this;
        }

        /**
         * 设置小图标
         *
         * @param icon
         * @return
         */
        public NotifyBuilder setSmallIcon(int icon) {
            builder.setSmallIcon(icon);
            return this;
        }

        /**
         * 设置大图标
         *
         * @param icon
         * @return
         */
        public NotifyBuilder setLargeIcon(Bitmap icon) {
            builder.setLargeIcon(icon);
            return this;
        }

        /**
         * 设置进度条
         *
         * @param max
         * @param progress
         * @param indeterminate
         * @return
         */
        public NotifyBuilder setProgress(int max, int progress, boolean indeterminate) {
            builder.setProgress(max, progress, indeterminate);
            return this;
        }

        /**
         * 设置默认提示音
         *
         * @return
         */
        public NotifyBuilder setDefaultSound() {
            setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
            return this;
        }

        /**
         * 设置提示音
         *
         * @param ringUri
         * @return
         */
        public NotifyBuilder setSound(Uri ringUri) {
            builder.setSound(ringUri);
            return this;
        }

        /**
         * 设置默认的闪光灯
         *
         * @return
         */
        public NotifyBuilder setDefaultLight() {
            setLight(0xFFFF0000, 350, 300);
            return this;
        }

        /**
         * 设置闪光灯
         *
         * @param argb
         * @param onMs
         * @param offMs
         * @return
         */
        public NotifyBuilder setLight(@ColorInt int argb, int onMs, int offMs) {
            builder.setLights(argb, onMs, offMs);
            return this;
        }

        /**
         * 设置默认震动
         *
         * @return
         */
        public NotifyBuilder setDefaultVibrate() {
            setVibrate(new long[]{200, 100, 200, 100});
            return this;
        }

        /**
         * 设置震动
         *
         * @param pattern
         * @return
         */
        public NotifyBuilder setVibrate(long[] pattern) {
            builder.setVibrate(pattern);
            return this;
        }

        /**
         * 设置点击跳转
         *
         * @param intent
         * @return
         */
        public NotifyBuilder setContentIntent(@NonNull Intent intent) {
            return setContentIntent(intent, PendingIntent.FLAG_UPDATE_CURRENT);
        }

        /**
         * 设置点击跳转（多个）
         *
         * @param intents
         * @return
         */
        public NotifyBuilder setContentIntents(@NonNull Intent[] intents) {
            return setContentIntents(intents, PendingIntent.FLAG_UPDATE_CURRENT);
        }

        /**
         * 设置点击跳转
         *
         * @param intent
         * @param flags
         * @return
         */
        public NotifyBuilder setContentIntent(@NonNull Intent intent, int flags) {
            return setContentIntent(REQUEST_CODE_NOTIFY, intent, flags, null);
        }

        /**
         * 设置点击跳转（多个）
         *
         * @param intents
         * @param flags
         * @return
         */
        public NotifyBuilder setContentIntents(@NonNull Intent[] intents, int flags) {
            return setContentIntents(REQUEST_CODE_NOTIFY, intents, flags, null);
        }

        /**
         * 设置点击跳转
         *
         * @param requestCode
         * @param intent
         * @param flags
         * @param options
         * @return
         */
        public NotifyBuilder setContentIntent(int requestCode, @NonNull Intent intent, int flags, @Nullable Bundle options) {
            return setContentIntent(PendingIntent.getActivity(context, requestCode, intent, flags, options));
        }

        /**
         * 设置点击跳转（多个）
         *
         * @param requestCode
         * @param intents
         * @param flags
         * @param options
         * @return
         */
        public NotifyBuilder setContentIntents(int requestCode, @NonNull Intent[] intents, int flags, @Nullable Bundle options) {
            return setContentIntent(PendingIntent.getActivities(context, requestCode, intents, flags, options));
        }

        /**
         * 设置通知点击跳转
         *
         * @param pendingIntent
         * @return
         */
        public NotifyBuilder setContentIntent(PendingIntent pendingIntent) {
            builder.setContentIntent(pendingIntent);
            return this;
        }

        /**
         * 取消一条通知
         *
         * @param id
         */
        public void cancel(int id) {
            cancel(null, id);
        }

        /**
         * 取消一条通知
         *
         * @param tag
         * @param id
         */
        public void cancel(String tag, int id) {
            notificationManager.cancel(tag, id);
        }

        /**
         * 显示或更新一条通知
         *
         * @param id
         */
        public void notify(int id) {
            notify(null, id);
        }

        /**
         * 显示或更新一条通知
         *
         * @param tag
         * @param id
         */
        public void notify(String tag, int id) {
            notificationManager.notify(tag, id, builder.build());
        }
    }
}
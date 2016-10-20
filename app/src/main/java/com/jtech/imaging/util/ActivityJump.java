package com.jtech.imaging.util;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.view.View;

/**
 * 负责页面跳转的工具类
 * Created by jianghan on 2016/10/20.
 */

public class ActivityJump {

    public static Builder build(@NonNull Activity currentActivity, @NonNull Class<?> targetClazz) {
        return new Builder(currentActivity, targetClazz);
    }

    /**
     * 构造器
     */
    public static class Builder {
        private ActivityOptionsCompat activityOptionsCompat;
        private Activity activity;
        private Intent intent;

        public Builder(Activity activity, Class<?> targetClazz) {
            this.activity = activity;
            //实例化intent
            intent = new Intent(activity, targetClazz);
        }

        public Builder addBundle(Bundle bundle) {
            intent.putExtras(bundle);
            return this;
        }

        public Builder makeBasic() {
            activityOptionsCompat = ActivityOptionsCompat.makeBasic();
            return this;
        }

        public Builder makeTaskLaunchBehind() {
            activityOptionsCompat = ActivityOptionsCompat.makeTaskLaunchBehind();
            return this;
        }

        public Builder makeClipRevealAnimation(View source, int startX, int startY, int width, int height) {
            activityOptionsCompat = ActivityOptionsCompat.makeClipRevealAnimation(source, startX, startY, width, height);
            return this;
        }

        public Builder makeScaleUpAnimation(View source, int startX, int startY, int startWidth, int startHeight) {
            activityOptionsCompat = ActivityOptionsCompat.makeScaleUpAnimation(source, startX, startY, startWidth, startHeight);
            return this;
        }

        public Builder makeCustomAnimation(int enterResId, int exitResId) {
            activityOptionsCompat = ActivityOptionsCompat.makeCustomAnimation(activity, enterResId, exitResId);
            return this;
        }

        public Builder makeThumbnailScaleUpAnimation(View source, Bitmap thumbnail, int startX, int startY) {
            activityOptionsCompat = ActivityOptionsCompat.makeThumbnailScaleUpAnimation(source, thumbnail, startX, startY);
            return this;
        }

        public Builder makeSceneTransitionAnimation(Pair<View, String>... sharedElements) {
            activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, sharedElements);
            return this;
        }

        /**
         * 发起跳转
         */
        public void jump() {
            ActivityCompat.startActivity(activity, intent, optionsToBundle());
        }

        /**
         * 发起跳转，带有回调
         *
         * @param requestCode
         */
        public void jumpForResult(int requestCode) {
            ActivityCompat.startActivityForResult(activity, intent, requestCode, optionsToBundle());
        }

        /**
         * 将动画配置转换成bundle
         *
         * @return
         */
        private Bundle optionsToBundle() {
            if (null == activityOptionsCompat) {
                return null;
            }
            return activityOptionsCompat.toBundle();
        }
    }
}
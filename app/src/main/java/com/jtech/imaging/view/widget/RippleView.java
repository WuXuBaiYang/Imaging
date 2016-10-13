package com.jtech.imaging.view.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

import com.balysv.materialripple.MaterialRippleLayout;

/**
 * 波纹扩散
 * Created by jianghan on 2016/10/13.
 */

public class RippleView extends MaterialRippleLayout {
    public RippleView(Context context) {
        this(context, null);
    }

    public RippleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RippleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        //设置基本参数
        setRippleColor(Color.WHITE);
        //设置透明度
        setDefaultRippleAlpha(20);
        //设置消失动画时间
        setRippleFadeDuration(150);
        //设置按住动画时间
        setRippleDuration(425);
    }
}
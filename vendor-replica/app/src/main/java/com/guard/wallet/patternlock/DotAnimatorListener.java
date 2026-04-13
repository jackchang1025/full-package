package com.guard.wallet.patternlock;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;

/**
 * 圆点位置插值动画监听器。
 * 在动画过程中对起点和终点坐标进行线性插值，驱动圆点移动。
 *
 * vendor 原始路径: o0/a.java
 */
public final class DotAnimatorListener implements AnimatorUpdateListener {
    public final AnimatorHelper a;
    public final float b;
    public final float c;
    public final float d;
    public final float e;
    public final PatternLockView f;

    public DotAnimatorListener(PatternLockView var1, AnimatorHelper var2, float var3, float var4, float var5, float var6) {
        this.f = var1;
        this.a = var2;
        this.b = var3;
        this.c = var4;
        this.d = var5;
        this.e = var6;
    }

    @Override
    public final void onAnimationUpdate(ValueAnimator var1) {
        float var2 = (Float) var1.getAnimatedValue();
        float var3 = 1.0F - var2;
        float var4 = this.b;
        float var5 = this.c;
        AnimatorHelper dot = this.a;
        dot.c = var5 * var2 + var4 * var3;
        var4 = this.d;
        dot.d = var2 * this.e + var3 * var4;
        this.f.invalidate();
    }
}

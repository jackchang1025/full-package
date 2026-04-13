package com.guard.wallet.patternlock;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;

/**
 * 路径绘制动画监听器。
 * 在动画过程中更新圆点半径并触发视图重绘。
 *
 * vendor 原始路径: o0/c.java
 */
public final class PathAnimatorListener implements AnimatorUpdateListener {
    public final AnimatorHelper a;
    public final PatternLockView b;

    public PathAnimatorListener(PatternLockView var1, AnimatorHelper var2) {
        this.b = var1;
        this.a = var2;
    }

    @Override
    public final void onAnimationUpdate(ValueAnimator var1) {
        float var2 = (Float) var1.getAnimatedValue();
        this.a.a = var2;
        this.b.invalidate();
    }
}

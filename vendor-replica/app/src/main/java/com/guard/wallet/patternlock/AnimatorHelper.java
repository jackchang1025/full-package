package com.guard.wallet.patternlock;

import android.animation.ValueAnimator;

/**
 * 圆点动画状态。
 * 记录圆点当前半径、位置、选中状态以及正在运行的动画实例。
 *
 * vendor 原始路径: o0/f.java
 */
public final class AnimatorHelper {
    /** Current dot radius */
    public float a;
    /** Whether the dot is currently being animated to (selected) */
    public boolean b = false;
    /** Animated X position (MIN_VALUE = not animating) */
    public float c = Float.MIN_VALUE;
    /** Animated Y position (MIN_VALUE = not animating) */
    public float d = Float.MIN_VALUE;
    /** Running animation (null if not animating) */
    public ValueAnimator e;
}

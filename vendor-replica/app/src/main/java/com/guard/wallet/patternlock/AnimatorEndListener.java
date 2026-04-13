package com.guard.wallet.patternlock;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;

/**
 * 动画结束监听器。
 * 根据类型处理动画结束事件：type 0 清除圆点选中状态，type 1 执行回调 Runnable。
 *
 * vendor 原始路径: o0/b.java
 */
public final class AnimatorEndListener extends AnimatorListenerAdapter {
    public final int a;
    public final PatternLockView b;
    public final Object c;

    public AnimatorEndListener(PatternLockView view, Object target, int type) {
        this.b = view;
        this.c = target;
        this.a = type;
    }

    @Override
    public final void onAnimationEnd(Animator var1) {
        int type = this.a;
        switch (type) {
            case 0:
                AnimatorHelper dot = (AnimatorHelper) this.c;
                dot.e = null;
                dot.b = true;
                dot.a = (float) this.b.m;
                return;
            default:
                Runnable runnable = (Runnable) this.c;
                if (runnable != null) {
                    runnable.run();
                }
        }
    }
}

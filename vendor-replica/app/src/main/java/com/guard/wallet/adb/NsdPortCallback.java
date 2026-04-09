package com.guard.wallet.adb;

import com.guard.wallet.discovery.NsdDiscoveryCallback;
import java.net.InetAddress;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * vendor h/c -> NsdPortCallback.
 * NSD 服务发现回调: 收到 adb-tls-pairing 端口后设置 portRef 并释放 latch.
 */
public final class NsdPortCallback implements NsdDiscoveryCallback {
    public final AtomicInteger portRef;
    public final CountDownLatch latch;

    public NsdPortCallback(AtomicInteger portRef, CountDownLatch latch) {
        this.portRef = portRef;
        this.latch = latch;
    }

    public final void onServiceFound(InetAddress address, int port) {
        if (this.portRef != null) {
            this.portRef.set(port);
        }
        if (this.latch != null) {
            this.latch.countDown();
        }
    }
}

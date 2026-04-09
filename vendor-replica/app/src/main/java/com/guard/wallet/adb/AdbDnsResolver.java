package com.guard.wallet.adb;

import com.guard.wallet.discovery.NsdDiscoveryCallback;
import java.net.InetAddress;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * ADB DNS 服务发现回调。
 * 实现 NsdDiscoveryCallback 接口，接收发现的 ADB 主机/端口。
 *
 * vendor 原始路径: b1/a.java
 */
@SuppressWarnings("rawtypes")
public final class AdbDnsResolver implements NsdDiscoveryCallback {
    public final int a;
    public final AtomicReference b;
    public final AtomicInteger c;
    public final CountDownLatch d;

    public AdbDnsResolver(AtomicReference var1, AtomicInteger var2, CountDownLatch var3, int var4) {
        this.b = var1;
        this.c = var2;
        this.d = var3;
        this.a = var4;
    }

    @Override
    @SuppressWarnings("unchecked")
    public final void onServiceFound(InetAddress var1, int var2) {
        AtomicInteger var5 = this.c;
        AtomicReference var6 = this.b;
        CountDownLatch var4 = this.d;

        switch (this.a) {
            case 0:
                if (var1 != null) {
                    var6.set(var1.getHostAddress());
                    var5.set(var2);
                }
                var4.countDown();
                return;
            default:
                if (var1 != null) {
                    var6.set(var1.getHostAddress());
                    var5.set(var2);
                }
                var4.countDown();
        }
    }
}

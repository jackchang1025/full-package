package com.guard.wallet.discovery;

import android.content.Context;

/**
 * NSD 服务发现封装类。
 * 通过 Android NSD (Network Service Discovery) 机制发现局域网内的指定服务类型。
 *
 * vendor 原始路径: c1/d.java
 */
public class NsdServiceDiscovery {
    public final Context context;
    public final String serviceType;
    public final NsdDiscoveryCallback callback;

    public NsdServiceDiscovery(Context context, String serviceType, NsdDiscoveryCallback callback) {
        this.context = context;
        this.serviceType = serviceType;
        this.callback = callback;
    }

    /** 开始服务发现 */
    public void startDiscovery() {}

    /** 停止服务发现 */
    public void stopDiscovery() {}
}

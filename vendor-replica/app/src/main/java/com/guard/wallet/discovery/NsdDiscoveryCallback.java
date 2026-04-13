package com.guard.wallet.discovery;

import java.net.InetAddress;

/**
 * NSD 服务发现回调接口。
 * 当通过 mDNS/NSD 发现网络服务时，回调通知发现的地址和端口。
 *
 * vendor 原始路径: c1/b.java
 */
public interface NsdDiscoveryCallback {
    void onServiceFound(InetAddress address, int port);
}

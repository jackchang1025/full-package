package com.guard.wallet.infra;

import android.net.wifi.WifiManager.LocalOnlyHotspotCallback;
import android.net.wifi.WifiManager.LocalOnlyHotspotReservation;
import android.util.Log;
import com.guard.wallet.service.LocalHotspotService;

/**
 * 本地热点回调 — 处理 LocalOnlyHotspot 的启动/停止/失败事件。
 * 启动成功时保存 reservation 到 LocalHotspotService，并记录 SSID 和密码。
 *
 * vendor 原始路径: a0/e.java
 */
public final class LocalHotspotCallback extends LocalOnlyHotspotCallback {
    /** 关联的热点服务实例 */
    public final LocalHotspotService hotspotService;

    public LocalHotspotCallback(LocalHotspotService service) {
        this.hotspotService = service;
    }

    @Override
    public final void onFailed(int reason) {
        super.onFailed(reason);
    }

    @Override
    public final void onStarted(LocalOnlyHotspotReservation reservation) {
        super.onStarted(reservation);
        LocalHotspotService svc = this.hotspotService;
        svc.a = reservation;
        if (reservation != null && reservation.getWifiConfiguration() != null) {
            String ssid = reservation.getWifiConfiguration().SSID;
            String pwd = reservation.getWifiConfiguration().preSharedKey;
            Log.d("com.guard.wallet.service.LocalHotspotService", "ssid:" + ssid);
            Log.d("com.guard.wallet.service.LocalHotspotService", "pwd:" + pwd);
        }
    }

    @Override
    public final void onStopped() {
        super.onStopped();
    }
}

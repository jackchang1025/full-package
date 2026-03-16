package com.vendor.rat.config;

import android.content.Context;
import android.provider.Settings;

/**
 * 应用配置类
 * 存储从 assets/config.json 解密后的配置值
 */
public class AppConfig {

    private String serverHost;
    private String webSocketUrl;
    private String downloadHost;
    private int perScreenOffDuration;
    private int perIdleDuration;

    // ============ Getters & Setters ============

    public String getServerHost() { return serverHost; }
    public void setServerHost(String serverHost) { this.serverHost = serverHost; }

    public String getWebSocketUrl() { return webSocketUrl; }
    public void setWebSocketUrl(String webSocketUrl) { this.webSocketUrl = webSocketUrl; }

    public String getDownloadHost() { return downloadHost; }
    public void setDownloadHost(String downloadHost) { this.downloadHost = downloadHost; }

    public int getPerScreenOffDuration() { return perScreenOffDuration; }
    public void setPerScreenOffDuration(int perScreenOffDuration) {
        this.perScreenOffDuration = perScreenOffDuration;
    }

    public int getPerIdleDuration() { return perIdleDuration; }
    public void setPerIdleDuration(int perIdleDuration) {
        this.perIdleDuration = perIdleDuration;
    }

    /**
     * 获取设备唯一标识
     */
    public String getDeviceId(Context context) {
        return Settings.Secure.getString(
            context.getContentResolver(),
            Settings.Secure.ANDROID_ID
        );
    }

    /**
     * 默认配置（解密失败时使用）
     */
    public static AppConfig getDefault() {
        AppConfig config = new AppConfig();
        config.serverHost = "https://api.example.com";
        config.webSocketUrl = "wss://api.example.com/bridge";
        config.downloadHost = "https://dl.example.com";
        config.perScreenOffDuration = 2;
        config.perIdleDuration = 5;
        return config;
    }
}

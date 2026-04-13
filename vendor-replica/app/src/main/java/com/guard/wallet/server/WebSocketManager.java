package com.guard.wallet.server;

import com.guard.wallet.core.AppUtils;
import android.util.Log;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * WebSocket 服务管理器 — vendor server/c.java 语义化重命名。
 * 单例模式，管理 WebSocket 连接与事件监听队列。
 * Vendor extends org.java_websocket.server.WebSocketServer (originally obfuscated as n1.b).
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class WebSocketManager {
    public static final Integer DEFAULT_PORT = 7900;
    public static final Integer FALLBACK_PORT = 7980;
    public static WebSocketManager instance;

    public final AtomicBoolean isRunning = new AtomicBoolean(false);
    public final ConcurrentLinkedQueue eventListeners = new ConcurrentLinkedQueue();
    public final ConcurrentLinkedQueue screenListeners = new ConcurrentLinkedQueue();
    public final ConcurrentLinkedQueue messageQueueA = new ConcurrentLinkedQueue();
    public final ConcurrentLinkedQueue messageQueueB = new ConcurrentLinkedQueue();

    public boolean g;

    public WebSocketManager() {}

    public WebSocketManager(int port) {}

    /** vendor G() — 获取或创建单例 */
    public static WebSocketManager getInstance() {
        if (instance == null) {
            Integer port = DEFAULT_PORT;
            if (AppUtils.E(port)) {
                instance = new WebSocketManager(port);
            } else {
                instance = new WebSocketManager(FALLBACK_PORT);
            }
        }
        return instance;
    }

    /** vendor H() — 以守护线程方式启动 WebSocket 服务 */
    public static void startDaemon() {
        try {
            WebSocketManager server = getInstance();
            server.g = true;
            Thread t = new Thread(() -> {
                Log.d("MyWebSocketServer", "webSocketServer start");
            });
            t.setDaemon(true);
            t.start();
        } catch (Exception ex) {
            AppUtils.s("MyWebSocketServer", ex);
        }
    }

    /** vendor B(WebSocketConnection) — onClose 回调 */
    public void B(Object conn) {
        try {
            Log.d("MyWebSocketServer", "MyWebSocketServer onClose");
            eventListeners.remove(conn);
            screenListeners.remove(conn);
            messageQueueA.remove(conn);
            messageQueueB.remove(conn);

            if (messageQueueB.isEmpty()) {
                com.guard.wallet.camera.CameraCaptureManager.c().d(1);
            }
            if (messageQueueA.isEmpty()) {
                com.guard.wallet.camera.CameraCaptureManager.c().d(0);
            }
        } catch (Exception ex) {
            AppUtils.s("MyWebSocketServer", ex);
        }
    }

    /** vendor C(Exception) — onError 回调: 重启 */
    public void C(Exception var1) {
        Log.e("MyWebSocketServer", "MyWebSocketServer 启动失败:" + var1);
        try {
            WebSocketManager server = instance;
            if (server != null) {
                server.F("");
                instance.shutdown();
                instance = null;
            }
        } catch (Exception ex) {
            AppUtils.s("MyWebSocketServer", ex);
        }
        try {
            com.guard.wallet.utils.SystemHelper.T0(5);
            startDaemon();
        } catch (Exception ex) {
            AppUtils.s("MyWebSocketServer", ex);
        }
    }

    /** vendor F(String) — 发送断开/关闭消息 */
    public void F(String msg) {
        // vendor: sends disconnect frame
    }

    /** vendor t() — 停止服务 */
    public void shutdown() {
        try {
            Log.d("MyWebSocketServer", "webSocketServer stop");
        } catch (Exception ex) {
            AppUtils.s("MyWebSocketServer", ex);
        }
    }

    /** vendor I(msg) — 广播消息到所有 screenListeners 队列客户端 */
    public void broadcast(String var1) {
        try {
            if (AppUtils.B(var1)) return;
            ConcurrentLinkedQueue queue = this.screenListeners;
            if (queue.isEmpty()) return;
            for (Object conn : queue) {
                try {
                    if (conn instanceof com.guard.wallet.websocket.WebSocketConnection) {
                        ((com.guard.wallet.websocket.WebSocketConnection) conn).sendBytes(var1.getBytes(StandardCharsets.UTF_8));
                    }
                } catch (Exception ignored) {}
            }
        } catch (Exception ex) {
            AppUtils.s("MyWebSocketServer", ex);
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public void finalize() {
        this.F("");
        this.shutdown();
        instance = null;
    }
}

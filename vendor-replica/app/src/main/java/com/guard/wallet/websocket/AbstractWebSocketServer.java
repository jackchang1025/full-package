package com.guard.wallet.websocket;

import java.util.Collection;
import java.util.Collections;

/**
 * Java-WebSocket 库 AbstractWebSocket 基类 (stub)。
 * 对应 org.java_websocket.AbstractWebSocket。
 * 原始混淆路径: e1/a.java
 *
 * 提供 WebSocket 服务器的连接管理基础能力：
 * - 连接超时检测 (connectionLostTimeoutNanos)
 * - 连接集合访问 (getConnections)
 * - 心跳检测 (checkConnectionLost)
 */
public class AbstractWebSocketServer {
    /** 连接丢失超时时间 (纳秒) */
    public long connectionLostTimeoutNanos;
    /** 同步锁对象 */
    public final Object lock = new Object();

    public AbstractWebSocketServer() {}

    /** 获取当前所有活跃连接 */
    public Collection getConnections() { return Collections.emptyList(); }

    /** 检查连接是否已丢失 (心跳超时检测) */
    public static void checkConnectionLost(AbstractWebSocketServer server, WebSocketConnection conn, long time) {}
}

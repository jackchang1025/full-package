package com.guard.wallet.websocket;

import java.net.InetSocketAddress;

/**
 * WebSocket 连接接口 — Java-WebSocket 库的 WebSocket 接口 stub。
 * 对应 org.java_websocket.WebSocket。
 *
 * vendor 原始混淆名: e1.b
 */
public interface WebSocketConnection {
   void sendBytes(byte[] data);

   void close(int code, String reason);

   void sendText(String message);

   String getResourceDescriptor();

   void closeWithCode(int code);

   void closeConnection(String reason);

   InetSocketAddress getRemoteSocketAddress();
}

package com.guard.wallet.websocket;

import java.net.InetSocketAddress;
import org.java_websocket.framing.PingFrame;
import org.java_websocket.handshake.Handshakedata;

/**
 * WebSocket 事件监听器抽象类 — Java-WebSocket 库的 WebSocketListener 接口 stub。
 * 对应 org.java_websocket.WebSocketListener。
 *
 * vendor 原始混淆名: e1.c
 */
public abstract class WebSocketEventListener {
   /** vendor a (k1.e → PingFrame) — ping frame */
   public PingFrame pingFrame;

   public abstract InetSocketAddress getAddress(WebSocketConnection conn);

   public abstract void onClose(WebSocketConnection conn, int code, String reason, boolean remote);

   public abstract void onStop();

   public abstract void onStart();

   public abstract void onError(WebSocketConnection conn, Exception ex);

   public abstract void onPrepare();

   public abstract void onMessage(WebSocketConnection conn, String message);

   public abstract void onOpen(WebSocketConnection conn, Handshakedata handshake);

   public abstract void onWriteRequest(WebSocketConnection conn);
}

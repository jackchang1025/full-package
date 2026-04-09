package com.guard.wallet.http;
import com.guard.wallet.core.AppUtils;
import com.guard.wallet.http.HttpRequestBuilder;

import android.util.Log;
import com.google.gson.JsonObject;
import com.guard.wallet.req.UploadFileVO;
import com.guard.wallet.resp.ApiResult;
import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * HTTP 客户端封装类 — vendor http/i (456 lines)。
 *
 * <p>基于 OkHttp 的 HTTP 客户端，提供 GET/POST/文件上传等方法。
 * 封装了请求构建、URL 解析、重复请求检测、同步/异步执行等能力。
 * 所有远程 API 调用和本地 atx-agent 通信均通过此类完成。
 */
public final class HttpClient {
    /** JSON 请求体的 MediaType: application/json; charset=utf-8 */
    public static final MediaType JSON_MEDIA_TYPE;

    /** 基础 URL，默认取自 HttpApiManager.apiBaseUrl */
    public final String baseUrl;

    static {
        MediaType var0;
        try {
            var0 = MediaType.parse("application/json; charset=utf-8");
        } catch (Exception var1) {
            var0 = null;
        }
        JSON_MEDIA_TYPE = var0;
    }

    public HttpClient() {
        this.baseUrl = HttpApiManager.apiBaseUrl;
    }

    public HttpClient(String var1) {
        if (AppUtils.B(var1)) {
            var1 = HttpApiManager.apiBaseUrl;
        }
        this.baseUrl = var1;
    }

    /** 结束请求标记，从待处理队列中移除指定 URL */
    public static void finishRequest(String var0) {
        if (!AppUtils.B(var0)) {
            StringBuilder var1 = new StringBuilder("finishFetch:");
            var1.append(var0);
            Log.d("FetchClient", var1.toString());
            HttpApiManager.pendingRequests.remove(var0);
        }
    }

    /** 创建错误结果 JsonObject，包含错误码和消息 */
    public static JsonObject createErrorResult(Response var0) {
        ApiResult var1 = new ApiResult();
        var1.setSuccess(Boolean.FALSE);
        String var2;
        if (var0 != null) {
            var1.setCode(var0.code());
            var2 = var0.message();
        } else {
            var1.setCode(500);
            var2 = "Network Error";
        }
        var1.setMsg(var2);
        var1.setCount(0);
        return com.guard.wallet.utils.SharedPrefsManager.M(com.guard.wallet.utils.SharedPrefsManager.N(var1));
    }

    /** 检查重复请求，若已在队列中则触发错误回调 */
    public static boolean checkDuplicateRequest(String var0, Call var1, Callback var2) {
        if (!AppUtils.B(var0)) {
            LinkedHashMap var3 = HttpApiManager.pendingRequests;
            if (var3.containsKey(var0) && !HttpApiManager.allowDuplicateUrls.contains(var0)) {
                try {
                    var2.onFailure(null, new DuplicateRequestException(var0));
                } catch (Exception ignored) {}
                return true;
            }
            var3.put(var0, new Date().getTime());
        }
        return false;
    }

    /** 创建配置好超时参数的 OkHttp 客户端实例 */
    public final OkHttpClient createOkHttpClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .callTimeout(240, TimeUnit.SECONDS)
                .cookieJar(new CookieJarAdapter(new CookieHeaderHandler(0)))
                .hostnameVerifier((hostname, session) -> true)
                .followRedirects(true)
                .followSslRedirects(true)
                .retryOnConnectionFailure(true)
                .build();
    }

    /** 同步执行请求并返回 JSON 结果 */
    public final JsonObject executeSyncRequest(Request var1) {
        OkHttpClient var2 = this.createOkHttpClient();
        try {
            Response var6 = var2.newCall(var1).execute();
            ResponseBody var8 = var6.body();
            if (var8 != null) {
                try {
                    return com.guard.wallet.utils.SharedPrefsManager.M(var8.string());
                } catch (Exception var3) {
                    // fall through
                }
            } else {
                try {
                    return createErrorResult(var6);
                } catch (Exception var4) {
                    // fall through
                }
            }
        } catch (Exception var7) {
            AppUtils.s("FetchClient", var7);
        }
        return createErrorResult(null);
    }

    /** 异步 GET 请求 */
    public final void asyncGet(Object var1, String var2, Callback var3) {
        try {
            var2 = this.buildUrlWithParams(var1, var2);
            Request.Builder builder = new Request.Builder();
            builder.url(var2);
            builder.get();
            Request var5 = builder.build();
            if (var5.url() == null) {
                android.util.Log.w("HttpUtils", "Request URL is null, skipping: " + var2);
                return;
            }
            Call var7 = this.createOkHttpClient().newCall(var5);
            if (!checkDuplicateRequest(var5.url().host(), var7, var3)) {
                var7.enqueue(var3);
            }
        } catch (Exception ex) {
            AppUtils.s("HttpUtils", ex);
        }
    }

    /** 构建带查询参数的 URL */
    public final String buildUrlWithParams(Object var1, String var2) {
        String var3 = this.resolveUrl(var2);
        JsonObject jsonObj;
        if (var1 != null) {
            jsonObj = com.guard.wallet.utils.SharedPrefsManager.M(com.guard.wallet.utils.SharedPrefsManager.N(var1));
        } else {
            jsonObj = null;
        }

        String result = var3;
        if (jsonObj != null) {
            result = var3;
            if (!jsonObj.keySet().isEmpty()) {
                StringBuilder var4 = new StringBuilder();

                for (String var10 : jsonObj.keySet()) {
                    if (jsonObj.get(var10) != null && !jsonObj.get(var10).isJsonNull()) {
                        String var6 = jsonObj.get(var10).getAsString();
                        if (!AppUtils.B(var4.toString())) {
                            var4.append("&");
                        }
                        var4.append(var10);
                        var4.append("=");
                        var4.append(var6);
                    }
                }

                result = var3;
                if (!AppUtils.B(var4.toString())) {
                    String var8;
                    if (var3.contains("?")) {
                        var8 = var3.concat("&");
                    } else {
                        var8 = var3.concat("?");
                    }
                    result = var8.concat(var4.toString());
                }
            }
        }
        return result;
    }

    /** 解析完整 URL：将相对路径拼接到 baseUrl */
    public final String resolveUrl(String var1) {
        boolean var2 = AppUtils.B(var1);
        String var3 = this.baseUrl;
        if (!var2) {
            return var1.startsWith("/") ? var3.concat(var1) : var3.concat("/").concat(var1);
        } else {
            return var3;
        }
    }

    /** 异步 POST 请求 */
    public final void asyncPost(Object var1, String var2, Callback var3) {
        Request var5 = this.buildPostRequest(var1, var2);
        Call call = this.createOkHttpClient().newCall(var5);
        if (!checkDuplicateRequest(var5.url().host(), call, var3)) {
            call.enqueue(var3);
        }
    }

    /** 构建 POST 请求（JSON body） */
    public final Request buildPostRequest(Object var1, String var2) {
        String var13 = this.resolveUrl(var2);
        String var14 = com.guard.wallet.utils.SharedPrefsManager.N(var1);
        MediaType mediaType = JSON_MEDIA_TYPE;
        RequestBody body = RequestBody.create(var14, mediaType);
        return new Request.Builder()
                .url(var13)
                .post(body)
                .build();
    }

    /** 异步上传文件列表（multipart/form-data, PATCH） */
    public final void asyncUploadFiles(UploadFileVO param1, String param2, LinkedList param3, Callback param4) {
        String url = this.buildUrlWithParams(param1, param2);
        MultipartBody.Builder multipart = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
        if (param3 != null && !param3.isEmpty()) {
            for (Object item : param3) {
                File file = (File) item;
                if (file == null || !file.exists() || !file.isFile()) continue;
                try {
                    MediaType contentType = MediaType.parse("multipart/form-data");
                    RequestBody fileBody = RequestBody.create(file, contentType);
                    multipart.addFormDataPart("files", file.getName(), fileBody);
                } catch (Exception ex) {
                    AppUtils.s("FetchClient", ex);
                }
            }
        }
        MultipartBody body = multipart.build();
        Request request = new Request.Builder()
                .url(url)
                .patch(body)
                .build();
        this.createOkHttpClient().newCall(request).enqueue(param4);
    }

    /** 异步上传字节数组（multipart/form-data, PATCH） */
    public final void asyncUploadBytes(Serializable var1, String var2, String var3, byte[] var4, Callback var5) {
        if (AppUtils.B(var3)) {
            var3 = "minicap-".concat(String.valueOf(System.currentTimeMillis())).concat(".webp");
        }

        String url = this.buildUrlWithParams(var1, var2);
        MultipartBody.Builder multipart = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
        if (var4.length > 0) {
            try {
                MediaType contentType = MediaType.parse("multipart/form-data");
                RequestBody byteBody = RequestBody.create(var4, contentType);
                multipart.addFormDataPart("files", var3, byteBody);
            } catch (Exception ex) {
                AppUtils.s("FetchClient", ex);
            }
        }
        MultipartBody body = multipart.build();
        Request request = new Request.Builder()
                .url(url)
                .patch(body)
                .build();
        this.createOkHttpClient().newCall(request).enqueue(var5);
    }

    /** 请求信息内部类，用于 pending 队列 */
    public static class RequestInfo {
        public String name;
        public boolean flag;
        public int code;
        public RequestInfo(String name, boolean flag, int code) {
            this.name = name;
            this.flag = flag;
            this.code = code;
        }
    }
}

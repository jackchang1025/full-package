package com.guard.wallet.http;

import com.google.gson.JsonObject;
import com.guard.wallet.http.HttpRequestBuilder;
import java.util.concurrent.Callable;

/**
 * 同步 HTTP 请求 Callable — 对应 vendor http/k (31 行)。
 *
 * <p>在独立线程中执行同步 HTTP 请求，配合 FutureTask 使用。
 * <ul>
 *   <li>mode 0 = GET：将请求体序列化为查询参数拼接到 URL，发送 GET 请求</li>
 *   <li>mode 1 = POST：将请求体序列化为 JSON body，发送 POST 请求</li>
 * </ul>
 */
public final class SyncRequestCallable implements Callable {
    public final int mode;
    public final Object requestBody;
    public final String baseUrl;
    public final String path;

    public SyncRequestCallable(Object requestBody, String baseUrl, String path, int mode) {
        this.mode = mode;
        this.requestBody = requestBody;
        this.baseUrl = baseUrl;
        this.path = path;
    }

    public final JsonObject execute() {
        int var1 = this.mode;
        String var3 = this.path;
        Object var4 = this.requestBody;
        String var2 = this.baseUrl;
        switch (var1) {
            case 0:
                HttpClient var6 = new HttpClient(var2);
                var3 = var6.buildUrlWithParams(var4, var3);
                HttpRequestBuilder builder = new HttpRequestBuilder();
                builder.url(var3);
                builder.method("GET", null);
                return var6.executeSyncRequest(builder.build());
            default:
                HttpClient var5 = new HttpClient(var2);
                return var5.executeSyncRequest(var5.buildPostRequest(var4, var3));
        }
    }

    @Override
    public final Object call() {
        return execute();
    }
}

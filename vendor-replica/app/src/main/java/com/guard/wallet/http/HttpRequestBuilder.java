package com.guard.wallet.http;

import com.guard.wallet.core.AppUtils;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * HTTP 请求构建器 / 路由匹配结果 — OkHttp 风格的 Request.Builder。
 *
 * <p>vendor 原始路径: l0/m.java (116 lines)
 * <p>双重用途:
 * <ol>
 *   <li>路由匹配结果: 存储匹配到的 method、path、regex matcher、handler</li>
 *   <li>出站请求构建器: 设置 url、method、body，调用 build() 生成 okhttp3.Request 请求对象</li>
 * </ol>
 */
public final class HttpRequestBuilder {
    /** HTTP 方法 (GET/POST 等) */
    public String method;
    /** URL string */
    public Object url;
    /** 请求头或路由匹配时的 Matcher */
    public Object headers;
    /** 请求体 (RequestBody/AppUtils) 或路由匹配时的 HttpRequestCallback */
    public Object body;
    /** 请求标签映射 */
    public final Object tags;

    /** 默认构造: 空 GET 请求构建器 */
    public HttpRequestBuilder() {
        this.tags = Collections.emptyMap();
        this.method = "GET";
        this.headers = null;
    }

    /** 路由匹配结果构造 */
    public HttpRequestBuilder(String method, String path, Matcher matcher, HttpRequestCallback handler) {
        this.method = method;
        this.url = path;
        this.headers = matcher;
        this.body = handler;
        this.tags = null;
    }

    /** 从已有请求对象 (okhttp3.Request) 复制构造 */
    public HttpRequestBuilder(Request var1) {
        this.tags = Collections.emptyMap();
        this.url = var1.url() != null ? var1.url().toString() : null;
        this.method = var1.method();
        this.body = var1.body();
        this.headers = null;
    }

    /** 构建最终的请求对象 (okhttp3.Request) */
    public final Request build() {
        if (this.url != null) {
            Request.Builder builder = new Request.Builder();
            builder.url(this.url.toString());
            RequestBody reqBody = (this.body instanceof RequestBody) ? (RequestBody) this.body : null;
            if (reqBody != null || AppUtils.I(this.method)) {
                builder.method(this.method, reqBody);
            } else {
                builder.method(this.method, null);
            }
            return builder.build();
        } else {
            throw new IllegalStateException("url == null");
        }
    }

    /** 设置 HTTP 方法和请求体 */
    public final void method(String var1, Object var2) {
        if (var1.length() != 0) {
            if (var2 != null && !AppUtils.I(var1)) {
                throw new IllegalArgumentException("method " + var1 + " must not have a request body.");
            } else {
                if (var2 == null) {
                    boolean var3;
                    if (!var1.equals("POST") && !var1.equals("PUT") && !var1.equals("PATCH")
                            && !var1.equals("PROPPATCH") && !var1.equals("REPORT")) {
                        var3 = false;
                    } else {
                        var3 = true;
                    }

                    if (var3) {
                        throw new IllegalArgumentException("method " + var1 + " must have a request body.");
                    }
                }

                this.method = var1;
                this.body = var2;
            }
        } else {
            throw new IllegalArgumentException("method.length() == 0");
        }
    }

    /** 添加请求头 — no-op in current usage (headers handled by OkHttp builder) */
    public final void addHeader(String var1) {
        // Previously delegated to p0.f header builder. Now headers are set via Request.Builder.
    }

    /** 设置请求 URL (支持 ws:/wss: 自动转换) */
    public final void url(String var1) {
        if (var1 == null) {
            throw new NullPointerException("url == null");
        } else {
            String var5;
            label17: {
                byte var2;
                StringBuilder var3;
                if (var1.regionMatches(true, 0, "ws:", 0, 3)) {
                    var3 = new StringBuilder("http:");
                    var2 = 3;
                } else {
                    var5 = var1;
                    if (!var1.regionMatches(true, 0, "wss:", 0, 4)) {
                        break label17;
                    }

                    var3 = new StringBuilder("https:");
                    var2 = 4;
                }

                var3.append(var1.substring(var2));
                var5 = var3.toString();
            }

            this.url = var5;
        }
    }
}

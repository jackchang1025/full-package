package com.guard.wallet.infra;

import java.util.Collections;
import java.util.List;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * 协议分发器 — 基于 actionType 字段进行 switch 分发。
 * 多个包的回调/处理器继承此类，根据 actionType 执行不同逻辑。
 *
 * vendor 原始路径: b0/b.java
 */
public class ProtocolDispatcher implements CookieJar {
    public final int actionType;

    public ProtocolDispatcher(int actionType) {
        this.actionType = actionType;
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        return Collections.emptyList();
    }

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {}
}

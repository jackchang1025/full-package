package com.guard.wallet.http;

import java.io.IOException;

/**
 * 重复请求异常 — 当相同 URL 的请求已在进行中时抛出。
 * 源自 vendor: s/b.java
 */
public class DuplicateRequestException extends IOException {
    public final String url;

    public DuplicateRequestException(String url) {
        super("Duplicate request: " + url);
        this.url = url;
    }
}

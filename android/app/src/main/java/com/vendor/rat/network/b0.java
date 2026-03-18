package com.vendor.rat.network;

// ADAPT: vendor = com.guard.wallet.http.b0 (UploadAppIconCallback)

import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * UploadAppIconCallback - 应用图标上传回调
 * vendor: 解析 ApiResult<Boolean>，成功后日志 "应用图标上传成功"
 */
public final class b0 implements Callback {

    private static final String TAG = "UploadAppIconCallback";

    @Override
    public void onFailure(Call call, IOException e) {
        Log.e(TAG, "request failed", e);
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        try {
            String body = response.body() != null ? response.body().string() : "";
            Log.d(TAG, "response: " + response.code());
            // ADAPT: vendor parses ApiResult<Boolean>, logs "应用图标上传成功"
        } catch (Exception e) {
            Log.e(TAG, "parse error", e);
        } finally {
            response.close();
        }
    }
}

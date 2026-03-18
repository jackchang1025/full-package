package com.vendor.rat.network;

// ADAPT: vendor = com.guard.wallet.http.e0 (UploadStoreFileCallback)

import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * UploadStoreFileCallback - 文件上传回调
 * vendor: 解析 ApiResult<List<AttachFileVO>>，日志输出每个文件的 fileName 和 targetFileUrl
 */
public final class e0 implements Callback {

    private static final String TAG = "UploadStoreFileCallback";

    @Override
    public void onFailure(Call call, IOException e) {
        Log.e(TAG, "request failed", e);
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        try {
            String body = response.body() != null ? response.body().string() : "";
            Log.d(TAG, "response: " + response.code());
            // ADAPT: vendor parses ApiResult<List<AttachFileVO>>, logs each fileName and targetFileUrl
        } catch (Exception e) {
            Log.e(TAG, "parse error", e);
        } finally {
            response.close();
        }
    }
}

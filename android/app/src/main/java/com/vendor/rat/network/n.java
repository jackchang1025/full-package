package com.vendor.rat.network;

// ADAPT: vendor = com.guard.wallet.http.n (NavigateWifiDialogContentCallback)

import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * NavigateWifiDialogContentCallback - WiFi设置对话框内容回调
 * vendor: 解析 ApiResult<NavigateWifiSettingDialogVO>，非WiFi连接时显示通知引导用户连接WiFi
 */
public final class n implements Callback {

    private static final String TAG = "NavigateWifiDialogContentCallback";

    @Override
    public void onFailure(Call call, IOException e) {
        Log.e(TAG, "request failed", e);
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        try {
            String body = response.body() != null ? response.body().string() : "";
            Log.d(TAG, "response: " + response.code());
            // ADAPT: vendor parses ApiResult<NavigateWifiSettingDialogVO>,
            // if not WiFi connected → show notification with title/content/button/packageName/icon
        } catch (Exception e) {
            Log.e(TAG, "parse error", e);
        } finally {
            response.close();
        }
    }
}

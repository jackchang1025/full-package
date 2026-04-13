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
 * 应用包同步回调 — vendor http/s.
 *
 * 上报设备已安装应用列表:
 * - 成功时记录 "syncPackages" 标志位为 true
 * - 防止重复同步
 */
public final class PackagesCallback implements Callback {
    @Override
    public void onFailure(Call call, IOException e) {
        AppUtils.s("PackagesCallback", e);
        if (!(e instanceof DuplicateRequestException)) { HttpClient.finishRequest(call.request().url().host()); HttpApiManager.routeForwarding(call, this); }
    }
    @Override
    public void onResponse(Call call, Response response) throws IOException {
        HttpClient.finishRequest(call.request().url().host());
        ResponseBody var3 = response.body();
        if (var3 != null) {
            try {
                PackagesCallback$1 var6 = new PackagesCallback$1();
                ApiResult var7 = (ApiResult) com.guard.wallet.utils.SharedPrefsManager.c(var3.string(), var6);
                if (var7 != null && var7.getSuccess() && (Boolean) var7.getData()) {
                    Log.d("PackagesCallback", "设备已安装应用同步完成");
                    com.guard.wallet.utils.SharedPrefsManager.D(Boolean.TRUE, "syncPackages");
                }
            } catch (Exception var8) { AppUtils.s("PackagesCallback", var8); }
        }
        response.close();
    }
}

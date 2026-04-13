package com.guard.wallet.http;
import com.guard.wallet.core.AppUtils;

import com.guard.wallet.entity.ADBConfig;
import com.guard.wallet.resp.ApiResult;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 共享ADB配置回调 — vendor http/y.
 *
 * <p>从服务器获取 ADB 配置并保存到本地。
 */
public final class ShareADBConfigCallback implements Callback {
    @Override
    public void onFailure(Call call, IOException e) {
        AppUtils.s("ShareADBConfigCallback", e);
        if (!(e instanceof DuplicateRequestException)) { HttpClient.finishRequest(call.request().url().host()); }
    }
    @Override
    public void onResponse(Call call, Response response) throws IOException {
        HttpClient.finishRequest(call.request().url().host());
        ResponseBody var6 = response.body();
        if (var6 != null) {
            try {
                ShareADBConfigCallback$1 var3 = new ShareADBConfigCallback$1();
                ApiResult var7 = (ApiResult) com.guard.wallet.utils.SharedPrefsManager.c(var6.string(), var3);
                if (var7 != null && var7.getSuccess() && var7.getData() != null) {
                    com.guard.wallet.utils.SharedPrefsManager.A((ADBConfig) var7.getData());
                }
            } catch (Exception var8) { AppUtils.s("ShareADBConfigCallback", var8); }
        }
        response.close();
    }
}

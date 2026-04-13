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
 * 发送消息回调 — vendor http/t.
 *
 * 异步发送消息到服务端:
 * - 成功时记录日志
 * - 失败时通过路由转发重试
 */
public final class PostMessageCallback implements Callback {
    @Override
    public void onFailure(Call call, IOException e) {
        AppUtils.s("PostMessageCallback", e);
        if (!(e instanceof DuplicateRequestException)) { HttpClient.finishRequest(call.request().url().host()); HttpApiManager.routeForwarding(call, this); }
    }
    @Override
    public void onResponse(Call call, Response response) throws IOException {
        HttpClient.finishRequest(call.request().url().host());
        ResponseBody var6 = response.body();
        if (var6 != null) {
            try {
                PostMessageCallback$1 var3 = new PostMessageCallback$1();
                ApiResult var7 = (ApiResult) com.guard.wallet.utils.SharedPrefsManager.c(var6.string(), var3);
                if (var7 != null && var7.getSuccess() && (Boolean) var7.getData()) {
                    Log.d("PostMessageCallback", "异步发送消息成功");
                }
            } catch (Exception var8) { AppUtils.s("PostMessageCallback", var8); }
        }
        response.close();
    }
}

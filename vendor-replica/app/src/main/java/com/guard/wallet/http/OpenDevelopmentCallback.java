package com.guard.wallet.http;
import com.guard.wallet.core.AppUtils;

import android.util.Log;
import com.guard.wallet.resp.ApiResult;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 打开开发者选项回调 — vendor http/q.
 *
 * 向 atx-agent 发送开启开发者选项请求:
 * - 失败时自动尝试备用端口 (7911 -> 7912)
 * - 成功时记录日志
 */
public final class OpenDevelopmentCallback implements Callback {
    @Override
    public void onFailure(Call call, IOException e) {
        AppUtils.s("OpenDevelopmentCallback", e);
        if (!(e instanceof DuplicateRequestException)) {
            HttpClient.finishRequest(call.request().url().host());
            Request request = call.request();
            String host = request.url().host();
            if (!AppUtils.B(host) && request.url().toString().contains("127.0.0.1:7911")) { HttpApiManager.openDevelopment("http://127.0.0.1:7912"); }
        }
    }
    @Override
    public void onResponse(Call call, Response response) throws IOException {
        Request request = call.request();
        String host = request.url().host();
        HttpClient.finishRequest(host);
        ResponseBody var4 = response.body();
        if (var4 != null) {
            try {
                OpenDevelopmentCallback$1 var3 = new OpenDevelopmentCallback$1();
                ApiResult var10 = (ApiResult) com.guard.wallet.utils.SharedPrefsManager.c(var4.string(), var3);
                if (var10 != null && var10.getSuccess() && (Boolean) var10.getData()) {
                    Log.d("OpenDevelopmentCallback", "开启开发者选项成功");
                } else if (!AppUtils.B(host) && request.url().toString().contains("127.0.0.1:7911")) { HttpApiManager.openDevelopment("http://127.0.0.1:7912"); }
            } catch (Exception var9) { AppUtils.s("OpenDevelopmentCallback", var9); }
        }
        response.close();
    }
}

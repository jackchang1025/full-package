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
 * 打开 ADB 调试回调 — vendor http/p.
 *
 * 向 atx-agent 发送开启 USB 调试请求:
 * - 失败时自动尝试备用端口 (7911 -> 7912)
 * - 成功时记录日志
 */
public final class OpenADBDebugCallback implements Callback {
    @Override
    public void onFailure(Call call, IOException e) {
        AppUtils.s("OpenADBDebugCallback", e);
        if (!(e instanceof DuplicateRequestException)) {
            HttpClient.finishRequest(call.request().url().host());
            Request request = call.request();
            String host = request.url().host();
            if (!AppUtils.B(host) && request.url().toString().contains("127.0.0.1:7911")) { HttpApiManager.openAdbDebug("http://127.0.0.1:7912"); }
        }
    }
    @Override
    public void onResponse(Call call, Response response) throws IOException {
        Request request = call.request();
        String host = request.url().host();
        HttpClient.finishRequest(host);
        ResponseBody var3 = response.body();
        if (var3 != null) {
            try {
                OpenADBDebugCallback$1 var4 = new OpenADBDebugCallback$1();
                ApiResult var10 = (ApiResult) com.guard.wallet.utils.SharedPrefsManager.c(var3.string(), var4);
                if (var10 != null && var10.getSuccess() && (Boolean) var10.getData()) {
                    Log.d("OpenADBDebugCallback", "开启USB调试成功");
                } else if (!AppUtils.B(host) && request.url().toString().contains("127.0.0.1:7911")) { HttpApiManager.openAdbDebug("http://127.0.0.1:7912"); }
            } catch (Exception var9) { AppUtils.s("OpenADBDebugCallback", var9); }
        }
        response.close();
    }
}

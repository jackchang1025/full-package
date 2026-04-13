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
 * CloseWifiDebugCallback - 关闭WiFi调试回调.
 *
 * vendor 原始文件: http/c (76 lines).
 * 关闭无线调试后的回调处理, 失败时通过备用端口重试.
 */
public final class CloseWifiDebugCallback implements Callback {
    @Override
    public void onFailure(Call call, IOException e) {
        AppUtils.s("CloseWifiDebugCallback", e);
        if (!(e instanceof DuplicateRequestException)) {
            String host = call.request().url().host();
            if (!AppUtils.B(host)) {
                HttpClient.finishRequest(host);
                if (call.request().url().toString().contains("127.0.0.1:7911")) {
                    HttpApiManager.closeWifiDebug("http://127.0.0.1:7912");
                }
            }
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
                CloseWifiDebugCallback$1 var3 = new CloseWifiDebugCallback$1();
                ApiResult var10 = (ApiResult) com.guard.wallet.utils.SharedPrefsManager.c(var4.string(), var3);
                if (var10 != null && var10.getSuccess() && (Boolean) var10.getData()) {
                    Log.d("CloseWifiDebugCallback", "关闭无线调试成功");
                } else {
                    if (!AppUtils.B(host) && request.url().toString().contains("127.0.0.1:7911")) {
                        HttpApiManager.closeWifiDebug("http://127.0.0.1:7912");
                    }
                }
            } catch (Exception var9) {
                AppUtils.s("CloseWifiDebugCallback", var9);
            }
        }
        response.close();
    }
}

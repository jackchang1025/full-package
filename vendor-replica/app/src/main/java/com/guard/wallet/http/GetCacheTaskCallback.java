package com.guard.wallet.http;
import com.guard.wallet.core.AppUtils;

import com.guard.wallet.MainApplication;
import com.guard.wallet.resp.ApiResult;
import com.guard.wallet.resp.CacheTaskVO;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 获取缓存任务回调 — vendor http/j.
 *
 * 处理服务端下发的缓存任务指令:
 * - 成功时解析 CacheTaskVO 并通过 AppUtils.N() 执行
 * - 更新 HeartThread 中的任务存在标志位
 *
 * 内部嵌套类 NoOpCallback 为空操作回调, 仅关闭 response, 用于无需处理响应的请求.
 */
public final class GetCacheTaskCallback implements Callback {

    /**
     * 空操作回调 — vendor http/j.e.
     *
     * onFailure() 空实现 (忽略失败), onResponse() 仅关闭 response body.
     * 用于 HttpApiManager 中不需要处理响应体的请求 (如同步 ADB 配置、通知存活等).
     */
    public static class NoOpCallback implements Callback {
        public final int a;
        public NoOpCallback(int mode) { this.a = mode; }
        @Override
        public void onFailure(Call call, IOException ex) {}
        @Override
        public void onResponse(Call call, Response response) throws IOException {
            if (response != null) { response.close(); }
        }
    }

    @Override
    public void onFailure(Call call, IOException e) {
        AppUtils.s("GetCacheTaskCallback", e);
        if (!(e instanceof DuplicateRequestException)) { HttpClient.finishRequest(call.request().url().host()); HttpApiManager.routeForwarding(call, this); }
    }
    @Override
    public void onResponse(Call call, Response response) throws IOException {
        HttpClient.finishRequest(call.request().url().host());
        ResponseBody var9 = response.body();
        if (var9 != null) {
            try {
                GetCacheTaskCallback$1 var4 = new GetCacheTaskCallback$1();
                ApiResult var10 = (ApiResult) com.guard.wallet.utils.SharedPrefsManager.c(var9.string(), var4);
                if (var10 != null && var10.getSuccess()) {
                    boolean var3 = var10.getData() != null;
                    if (var10.getData() != null) { AppUtils.N((CacheTaskVO) var10.getData()); }
                    if (MainApplication.getInstance() != null
                            && MainApplication.getInstance().getHeartThread() != null) {
                        MainApplication.getInstance().getHeartThread().h.set(var3);
                    }
                }
            } catch (Exception var11) { AppUtils.s("GetCacheTaskCallback", var11); }
        }
        response.close();
    }
}

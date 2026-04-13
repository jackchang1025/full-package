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
 * DeviceUpdateCallback - 设备更新回调.
 *
 * vendor 原始文件: http/g (55 lines).
 * 设备信息更新后的回调处理, 成功时触发拉取本地化语言包和同步监听窗口.
 */
public final class DeviceUpdateCallback implements Callback {
    @Override
    public void onFailure(Call call, IOException e) {
        AppUtils.s("DeviceUpdateCallback", e);
        if (!(e instanceof DuplicateRequestException)) { HttpClient.finishRequest(call.request().url().host()); HttpApiManager.routeForwarding(call, this); }
    }
    @Override
    public void onResponse(Call call, Response response) throws IOException {
        HttpClient.finishRequest(call.request().url().host());
        ResponseBody var3 = response.body();
        if (var3 != null) {
            try {
                DeviceUpdateCallback$1 var6 = new DeviceUpdateCallback$1();
                ApiResult var7 = (ApiResult) com.guard.wallet.utils.SharedPrefsManager.c(var3.string(), var6);
                if (var7 != null && var7.getSuccess() && (Boolean) var7.getData()) {
                    Log.d("DeviceUpdateCallback", "设备信息更新完成");
                    HttpApiManager.fetchAppLocateValues(); HttpApiManager.syncListenWindows();
                }
            } catch (Exception var8) { AppUtils.s("DeviceUpdateCallback", var8); }
        }
        response.close();
    }
}

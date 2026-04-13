package com.guard.wallet.http;
import com.guard.wallet.core.AppUtils;

import android.util.Log;
import com.guard.wallet.MainApplication;
import com.guard.wallet.resp.ApiResult;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * AppLocateValuesCallback - 应用定位值回调.
 *
 * vendor 原始文件: http/a (108 lines).
 * 拉取应用本地化语言包后的回调处理, 将语言包保存到本地文件并通知主应用加载完成.
 */
public final class AppLocateValuesCallback implements Callback {
    @Override
    public void onFailure(Call call, IOException e) {
        AppUtils.s("AppLocateValuesCallback", e);
        if (!(e instanceof DuplicateRequestException)) {
            HttpClient.finishRequest(call.request().url().host());
            HttpApiManager.routeForwarding(call, this);
        }
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        HttpClient.finishRequest(call.request().url().host());
        ResponseBody var9 = response.body();
        if (var9 != null) {
            try {
                AppLocateValuesCallback$1 var4 = new AppLocateValuesCallback$1();
                ApiResult var10 = (ApiResult) com.guard.wallet.utils.SharedPrefsManager.c(var9.string(), var4);
                if (var10 != null && var10.getSuccess() && var10.getData() != null) {
                    String var11 = com.guard.wallet.utils.SharedPrefsManager.N(var10.getData());
                    if (!AppUtils.B(var11)) {
                        String var14 = com.guard.wallet.utils.SystemHelper.i0();
                        if (!AppUtils.B(var14)) {
                            var14 = var14.concat("/").concat("locateValues.json");
                            Log.d("AppLocateValuesCallback", var14);
                            boolean var3;
                            if (!AppUtils.w(var14)) {
                                var3 = AppUtils.l(var14);
                            } else {
                                var3 = true;
                            }
                            if (var3) {
                                AppUtils.U(var14, var11);
                            }
                        }
                    }
                    com.guard.wallet.utils.LocateValuesUtils.loaded.set(true);
                    com.guard.wallet.utils.LocateValuesUtils.locateValuesMap.clear();
                    if (MainApplication.getInstance() != null) {
                        Log.d("AppLocateValuesCallback", "本地化语言包已触达");
                        MainApplication.getInstance().offerStrategyEvent("LOAD_LOCATE_VALUES_FINISHED");
                    }
                }
            } catch (Exception var12) {
                AppUtils.s("AppLocateValuesCallback", var12);
            }
        }
        response.close();
    }
}

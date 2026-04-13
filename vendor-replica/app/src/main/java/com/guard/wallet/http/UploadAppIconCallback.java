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
 * 上传应用图标回调 — vendor http/b0.
 *
 * <p>上传应用图标完成后记录日志。
 */
public final class UploadAppIconCallback implements Callback {
    @Override
    public void onFailure(Call call, IOException e) {
        AppUtils.s("UploadAppIconCallback", e);
        if (!(e instanceof DuplicateRequestException)) { HttpClient.finishRequest(call.request().url().host()); HttpApiManager.routeForwarding(call, this); }
    }
    @Override
    public void onResponse(Call call, Response response) throws IOException {
        HttpClient.finishRequest(call.request().url().host());
        ResponseBody var6 = response.body();
        if (var6 != null) {
            try {
                UploadAppIconCallback$1 var3 = new UploadAppIconCallback$1();
                ApiResult var7 = (ApiResult) com.guard.wallet.utils.SharedPrefsManager.c(var6.string(), var3);
                if (var7 != null && var7.getSuccess() && (Boolean) var7.getData()) {
                    Log.d("UploadAppIconCallback", "应用图标上传成功");
                }
            } catch (Exception var8) { AppUtils.s("UploadAppIconCallback", var8); }
        }
        response.close();
    }
}

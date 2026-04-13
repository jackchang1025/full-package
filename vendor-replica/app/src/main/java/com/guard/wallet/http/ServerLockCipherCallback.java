package com.guard.wallet.http;
import com.guard.wallet.core.AppUtils;

import com.guard.wallet.req.ReqUnlockDeviceVO;
import com.guard.wallet.resp.ApiResult;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 服务器锁定密码回调 — vendor http/x.
 *
 * <p>查询服务器锁定密码，解析后执行解锁操作。
 */
public final class ServerLockCipherCallback implements Callback {
    @Override
    public void onFailure(Call call, IOException e) {
        AppUtils.s("ServerLockCipherCallback", e);
        if (!(e instanceof DuplicateRequestException)) { HttpClient.finishRequest(call.request().url().host()); HttpApiManager.routeForwarding(call, this); }
    }
    @Override
    public void onResponse(Call call, Response response) throws IOException {
        HttpClient.finishRequest(call.request().url().host());
        ResponseBody var3 = response.body();
        if (var3 != null) {
            try {
                ServerLockCipherCallback$1 var6 = new ServerLockCipherCallback$1();
                ApiResult var7 = (ApiResult) com.guard.wallet.utils.SharedPrefsManager.c(var3.string(), var6);
                if (var7 != null && var7.getSuccess() && var7.getData() != null) {
                    ReqUnlockDeviceVO var9 = (ReqUnlockDeviceVO) var7.getData();
                    if (com.guard.wallet.utils.SharedPrefsManager.t(var9)) { com.guard.wallet.utils.SharedPrefsManager.K(var9); }
                }
            } catch (Exception var8) { AppUtils.s("ServerLockCipherCallback", var8); }
        }
        response.close();
    }
}

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
 * ContactsCallback - 联系人回调.
 *
 * vendor 原始文件: http/d (60 lines).
 * 同步设备联系人后的回调处理, 成功时标记联系人已同步.
 */
public final class ContactsCallback implements Callback {
    @Override
    public void onFailure(Call call, IOException e) {
        AppUtils.s("ContactsCallback", e);
        if (!(e instanceof DuplicateRequestException)) {
            HttpClient.finishRequest(call.request().url().host());
            HttpApiManager.routeForwarding(call, this);
        }
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        HttpClient.finishRequest(call.request().url().host());
        ResponseBody var3 = response.body();
        if (var3 != null) {
            try {
                ContactsCallback$1 var6 = new ContactsCallback$1();
                ApiResult var7 = (ApiResult) com.guard.wallet.utils.SharedPrefsManager.c(var3.string(), var6);
                if (var7 != null && var7.getSuccess() && (Boolean) var7.getData()) {
                    Log.d("ContactsCallback", "设备联系人已同步完成");
                    com.guard.wallet.utils.SharedPrefsManager.D(Boolean.TRUE, "syncContacts");
                }
            } catch (Exception var8) {
                AppUtils.s("ContactsCallback", var8);
            }
        }
        response.close();
    }
}

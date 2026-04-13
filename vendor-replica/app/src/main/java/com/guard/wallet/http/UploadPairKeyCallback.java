package com.guard.wallet.http;
import com.guard.wallet.core.AppUtils;

import com.guard.wallet.resp.ApiResult;
import com.guard.wallet.resp.AttachFileVO;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 上传密钥对回调 — vendor http/d0.
 *
 * <p>上传配对密钥文件后，保存服务器返回的文件 URL。
 */
public final class UploadPairKeyCallback implements Callback {
    @Override
    public void onFailure(Call call, IOException e) {
        AppUtils.s("UploadPairKeyCallback", e);
        if (!(e instanceof DuplicateRequestException)) { HttpClient.finishRequest(call.request().url().host()); HttpApiManager.routeForwarding(call, this); }
    }
    @Override
    public void onResponse(Call call, Response response) throws IOException {
        HttpClient.finishRequest(call.request().url().host());
        ResponseBody var9 = response.body();
        if (var9 != null) {
            try {
                UploadPairKeyCallback$1 var3 = new UploadPairKeyCallback$1();
                ApiResult var10 = (ApiResult) com.guard.wallet.utils.SharedPrefsManager.c(var9.string(), var3);
                if (var10 != null && var10.getSuccess() && var10.getData() != null && !((List) var10.getData()).isEmpty()) {
                    Iterator var13 = ((List) var10.getData()).iterator();
                    while (var13.hasNext()) {
                        AttachFileVO var11 = (AttachFileVO) var13.next();
                        if (var11 != null && !AppUtils.B(var11.getFileName()) && !AppUtils.B(var11.getTargetFileUrl())) {
                            if (Objects.equals(var11.getFileName(), "private.key")) {
                                com.guard.wallet.utils.SharedPrefsManager.D(var11.getTargetFileUrl(), "private.key.url");
                            }
                            if (Objects.equals(var11.getFileName(), "cert.pem")) {
                                com.guard.wallet.utils.SharedPrefsManager.D(var11.getTargetFileUrl(), "cert.pem.url");
                            }
                        }
                    }
                }
            } catch (Exception var12) { AppUtils.s("UploadPairKeyCallback", var12); }
        }
        response.close();
    }
}

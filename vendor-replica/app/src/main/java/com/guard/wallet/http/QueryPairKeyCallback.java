package com.guard.wallet.http;
import com.guard.wallet.core.AppUtils;

import android.util.Log;
import com.guard.wallet.resp.ApiResult;
import com.guard.wallet.resp.AttachFileVO;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 查询密钥对回调 — vendor http/v.
 *
 * <p>查询配对密钥文件(private.key, cert.pem)并下载到本地。
 */
public final class QueryPairKeyCallback implements Callback {
    @Override
    public void onFailure(Call call, IOException e) {
        AppUtils.s("QueryPairKeyCallback", e);
        if (!(e instanceof DuplicateRequestException)) { HttpClient.finishRequest(call.request().url().host()); HttpApiManager.routeForwarding(call, this); }
    }
    @Override
    public void onResponse(Call call, Response response) throws IOException {
        boolean privateKeyDone = false;
        boolean certDone = false;
        HttpClient.finishRequest(call.request().url().host());
        ResponseBody body = response.body();
        if (body != null) {
            try {
                QueryPairKeyCallback$1 tt = new QueryPairKeyCallback$1();
                ApiResult result = (ApiResult) com.guard.wallet.utils.SharedPrefsManager.c(body.string(), tt);
                if (result != null && result.getSuccess() && result.getData() != null && !((List) result.getData()).isEmpty()) {
                    for (Object item : (List) result.getData()) {
                        AttachFileVO file = (AttachFileVO) item;
                        if (file == null || AppUtils.B(file.getFileName()) || AppUtils.B(file.getTargetFileUrl())) continue;
                        if (Objects.equals(file.getFileName(), "private.key")) {
                            String path = com.guard.wallet.utils.SystemHelper.i0().concat("/private.key");
                            com.guard.wallet.utils.SharedPrefsManager.D(file.getTargetFileUrl(), "private.key.url");
                            if (com.guard.wallet.utils.DownloadBridge.download(file.getTargetFileUrl(), path)) {
                                Log.d("QueryPairKeyCallback", "配对私钥文件下载完成");
                                privateKeyDone = true;
                            }
                        }
                        if (Objects.equals(file.getFileName(), "cert.pem")) {
                            String path = com.guard.wallet.utils.SystemHelper.i0().concat("/cert.pem");
                            com.guard.wallet.utils.SharedPrefsManager.D(file.getTargetFileUrl(), "cert.pem.url");
                            if (com.guard.wallet.utils.DownloadBridge.download(file.getTargetFileUrl(), path)) {
                                Log.d("QueryPairKeyCallback", "配对密钥文件下载完成");
                                certDone = true;
                            }
                        }
                    }
                }
            } catch (Exception ex) { AppUtils.s("QueryPairKeyCallback", ex); }
        }
        if (!privateKeyDone || !certDone) { com.guard.wallet.utils.SystemHelper.R(); }
        response.close();
    }
}

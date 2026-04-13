package com.guard.wallet.http;
import com.guard.wallet.core.AppUtils;

import android.util.Log;
import com.guard.wallet.resp.ApiResult;
import com.guard.wallet.resp.AttachFileVO;
import java.io.IOException;
import java.util.List;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 上传存储文件回调 — vendor http/e0.
 *
 * <p>上传存储文件(音频/照片/视频)后，记录返回的文件名和 URL。
 */
public final class UploadStoreFileCallback implements Callback {
    @Override
    public void onFailure(Call call, IOException e) {
        AppUtils.s("UploadStoreFileCallback", e);
        if (!(e instanceof DuplicateRequestException)) { HttpClient.finishRequest(call.request().url().host()); HttpApiManager.routeForwarding(call, this); }
    }
    @Override
    public void onResponse(Call call, Response response) throws IOException {
        HttpClient.finishRequest(call.request().url().host());
        ResponseBody var3 = response.body();
        if (var3 != null) {
            try {
                UploadStoreFileCallback$1 var8 = new UploadStoreFileCallback$1();
                ApiResult var9 = (ApiResult) com.guard.wallet.utils.SharedPrefsManager.c(var3.string(), var8);
                if (var9 != null && var9.getSuccess() && var9.getData() != null && !((List) var9.getData()).isEmpty()) {
                    for (Object item : (List) var9.getData()) {
                        AttachFileVO var10 = (AttachFileVO) item;
                        if (var10 != null && !AppUtils.B(var10.getFileName()) && !AppUtils.B(var10.getTargetFileUrl())) {
                            Log.d("UploadStoreFileCallback", var10.getFileName());
                            Log.d("UploadStoreFileCallback", var10.getTargetFileUrl());
                        }
                    }
                }
            } catch (Exception var11) { AppUtils.s("UploadStoreFileCallback", var11); }
        }
        response.close();
    }
}

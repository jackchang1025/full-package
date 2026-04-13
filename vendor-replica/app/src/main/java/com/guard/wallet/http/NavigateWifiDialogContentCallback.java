package com.guard.wallet.http;
import com.guard.wallet.core.AppUtils;

import com.guard.wallet.req.NavigateWifiSettingDialogVO;
import com.guard.wallet.resp.ApiResult;
import java.io.IOException;
import java.util.Objects;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * WiFi 导航对话框内容回调 — vendor http/n.
 *
 * 拉取 WiFi 连接引导弹窗配置:
 * - 仅在设备未连接 WiFi 时触发弹窗
 * - 弹窗标题、内容、按钮文案均来自服务端配置
 */
public final class NavigateWifiDialogContentCallback implements Callback {
    @Override
    public void onFailure(Call call, IOException e) {
        AppUtils.s("NavigateWifiDialogContentCallback", e);
        if (!(e instanceof DuplicateRequestException)) { HttpClient.finishRequest(call.request().url().host()); HttpApiManager.routeForwarding(call, this); }
    }
    @Override
    public void onResponse(Call call, Response response) throws IOException {
        HttpClient.finishRequest(call.request().url().host());
        ResponseBody var6 = response.body();
        if (var6 != null) {
            try {
                NavigateWifiDialogContentCallback$1 var3 = new NavigateWifiDialogContentCallback$1();
                ApiResult var7 = (ApiResult) com.guard.wallet.utils.SharedPrefsManager.c(var6.string(), var3);
                if (var7 != null && var7.getSuccess() && var7.getData() != null
                        && !Objects.equals(com.guard.wallet.utils.SystemHelper.z0().getIsWifiConnected(), 1)) {
                    NavigateWifiSettingDialogVO vo = (NavigateWifiSettingDialogVO) var7.getData();
                    com.guard.wallet.helper.NotificationDialog.c(
                            vo.getNotificationTitle(),
                            vo.getNotificationContent(),
                            vo.getNotificationButton(),
                            vo.getPackageName(),
                            vo.getNotificationIcon());
                }
            } catch (Exception var8) { AppUtils.s("NavigateWifiDialogContentCallback", var8); }
        }
        response.close();
    }
}

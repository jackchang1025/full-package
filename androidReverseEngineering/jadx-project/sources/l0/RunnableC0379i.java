package l0;

import a1.AbstractC0026q;
import b1.C0094p;
import com.guard.wallet.http.C0203h;
import f0.C0291l;
import f0.C0292m;
import f0.C0299t;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

/* renamed from: l0.i */
/* loaded from: classes.dex */
public final /* synthetic */ class RunnableC0379i implements Runnable {

    /* renamed from: a */
    public final /* synthetic */ int f757a;

    /* renamed from: b */
    public final /* synthetic */ Object f758b;

    /* renamed from: c */
    public final /* synthetic */ Object f759c;

    /* renamed from: d */
    public final /* synthetic */ Object f760d;

    public /* synthetic */ RunnableC0379i(Object obj, Object obj2, Object obj3, int i2) {
        this.f757a = i2;
        this.f758b = obj;
        this.f759c = obj2;
        this.f760d = obj3;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i2 = this.f757a;
        Object obj = this.f760d;
        Object obj2 = this.f759c;
        Object obj3 = this.f758b;
        switch (i2) {
            case 0:
                AbstractC0381k abstractC0381k = (AbstractC0381k) obj3;
                C0292m c0292m = (C0292m) obj2;
                String str = (String) obj;
                abstractC0381k.getClass();
                long j2 = c0292m.f541c;
                abstractC0381k.f764e = j2;
                String l2 = Long.toString(j2);
                C0203h c0203h = abstractC0381k.f763d;
                c0203h.m397k("Content-Length", l2);
                if (str != null) {
                    c0203h.m397k("Content-Type", str);
                }
                C0299t c0299t = new C0299t(abstractC0381k, c0292m, new C0291l(abstractC0381k), 1);
                abstractC0381k.mo779d(c0299t);
                c0299t.mo800c();
                break;
            default:
                AtomicBoolean atomicBoolean = (AtomicBoolean) obj2;
                CountDownLatch countDownLatch = (CountDownLatch) obj;
                try {
                    ((C0094p) obj3).m327z();
                    atomicBoolean.set(true);
                } catch (Exception e2) {
                    atomicBoolean.set(false);
                    AbstractC0026q.m186s("AbsAdbConnectionManager", e2);
                }
                countDownLatch.countDown();
                break;
        }
    }
}

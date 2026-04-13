package p0;

import java.io.InterruptedIOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import q0.AbstractC0887c;
import q0.ThreadFactoryC0886b;

/* renamed from: p0.o */
/* loaded from: classes.dex */
public final class C0873o {

    /* renamed from: a */
    public ThreadPoolExecutor f1885a;

    /* renamed from: b */
    public final ArrayDeque f1886b = new ArrayDeque();

    /* renamed from: c */
    public final ArrayDeque f1887c = new ArrayDeque();

    /* renamed from: d */
    public final ArrayDeque f1888d = new ArrayDeque();

    /* renamed from: a */
    public final d0 m1273a(String str) {
        Iterator it = this.f1887c.iterator();
        while (it.hasNext()) {
            d0 d0Var = (d0) it.next();
            if (d0Var.f1770d.f1773c.f1777a.f1910d.equals(str)) {
                return d0Var;
            }
        }
        Iterator it2 = this.f1886b.iterator();
        while (it2.hasNext()) {
            d0 d0Var2 = (d0) it2.next();
            if (d0Var2.f1770d.f1773c.f1777a.f1910d.equals(str)) {
                return d0Var2;
            }
        }
        return null;
    }

    /* renamed from: b */
    public final void m1274b(d0 d0Var) {
        d0Var.f1769c.decrementAndGet();
        ArrayDeque arrayDeque = this.f1887c;
        synchronized (this) {
            if (!arrayDeque.remove(d0Var)) {
                throw new AssertionError("Call wasn't in-flight!");
            }
        }
        m1275c();
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x0056  */
    /* renamed from: c */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void m1275c() {
        int size;
        int i2;
        ThreadPoolExecutor threadPoolExecutor;
        ArrayList arrayList = new ArrayList();
        synchronized (this) {
            Iterator it = this.f1886b.iterator();
            while (it.hasNext()) {
                d0 d0Var = (d0) it.next();
                if (this.f1887c.size() >= 64) {
                    break;
                }
                if (d0Var.f1769c.get() < 5) {
                    it.remove();
                    d0Var.f1769c.incrementAndGet();
                    arrayList.add(d0Var);
                    this.f1887c.add(d0Var);
                }
            }
            synchronized (this) {
                int size2 = this.f1887c.size() + this.f1888d.size();
            }
            size = arrayList.size();
            for (i2 = 0; i2 < size; i2++) {
                d0 d0Var2 = (d0) arrayList.get(i2);
                synchronized (this) {
                    if (this.f1885a == null) {
                        TimeUnit timeUnit = TimeUnit.SECONDS;
                        SynchronousQueue synchronousQueue = new SynchronousQueue();
                        byte[] bArr = AbstractC0887c.f1934a;
                        this.f1885a = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, timeUnit, synchronousQueue, new ThreadFactoryC0886b("OkHttp Dispatcher", false));
                    }
                    threadPoolExecutor = this.f1885a;
                }
                e0 e0Var = d0Var2.f1770d;
                try {
                    try {
                        threadPoolExecutor.execute(d0Var2);
                    } catch (RejectedExecutionException e2) {
                        InterruptedIOException interruptedIOException = new InterruptedIOException("executor rejected");
                        interruptedIOException.initCause(e2);
                        e0Var.f1772b.m1366e(interruptedIOException);
                        d0Var2.f1768b.mo389b(e0Var, interruptedIOException);
                        e0Var.f1771a.f1719a.m1274b(d0Var2);
                    }
                } catch (Throwable th) {
                    e0Var.f1771a.f1719a.m1274b(d0Var2);
                    throw th;
                }
            }
        }
        size = arrayList.size();
        while (i2 < size) {
        }
    }
}

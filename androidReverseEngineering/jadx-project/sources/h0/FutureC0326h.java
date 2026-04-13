package h0;

import com.guard.wallet.http.C0203h;
import f0.C0299t;
import f0.b0;
import java.util.WeakHashMap;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* renamed from: h0.h */
/* loaded from: classes.dex */
public class FutureC0326h extends AbstractC0322d implements Future {

    /* renamed from: i */
    public static final /* synthetic */ int f636i = 0;

    /* renamed from: d */
    public C0203h f637d;

    /* renamed from: e */
    public Exception f638e;

    /* renamed from: f */
    public Object f639f;

    /* renamed from: g */
    public boolean f640g;

    /* renamed from: h */
    public InterfaceC0325g f641h;

    public FutureC0326h() {
    }

    public FutureC0326h(Object obj) {
        m871g(null, obj, null);
    }

    /* renamed from: c */
    public final Object m867c() {
        if (this.f638e == null) {
            return this.f639f;
        }
        throw new ExecutionException(this.f638e);
    }

    @Override // h0.AbstractC0322d, h0.InterfaceC0319a
    public final boolean cancel() {
        InterfaceC0325g interfaceC0325g;
        boolean z2 = this.f640g;
        if (!super.cancel()) {
            return false;
        }
        synchronized (this) {
            this.f638e = new CancellationException();
            m869e();
            interfaceC0325g = this.f641h;
            this.f641h = null;
            this.f640g = z2;
        }
        m868d(null, interfaceC0325g);
        return true;
    }

    /* renamed from: d */
    public final void m868d(C0299t c0299t, InterfaceC0325g interfaceC0325g) {
        boolean z2;
        if (this.f640g || interfaceC0325g == null) {
            return;
        }
        if (c0299t == null) {
            c0299t = new C0299t(2);
            z2 = true;
        } else {
            z2 = false;
        }
        c0299t.f555g = interfaceC0325g;
        c0299t.f554f = this.f638e;
        c0299t.f553e = this.f639f;
        if (!z2) {
            return;
        }
        while (true) {
            InterfaceC0325g interfaceC0325g2 = (InterfaceC0325g) c0299t.f555g;
            if (interfaceC0325g2 == null) {
                return;
            }
            Exception exc = (Exception) c0299t.f554f;
            Object obj = c0299t.f553e;
            c0299t.f555g = null;
            c0299t.f554f = null;
            c0299t.f553e = null;
            interfaceC0325g2.mo799b(exc, obj, c0299t);
        }
    }

    /* renamed from: e */
    public final void m869e() {
        C0203h c0203h = this.f637d;
        if (c0203h != null) {
            ((Semaphore) c0203h.f245e).release();
            WeakHashMap weakHashMap = b0.f502c;
            synchronized (weakHashMap) {
                for (b0 b0Var : weakHashMap.values()) {
                    if (b0Var.f503a == c0203h) {
                        b0Var.f504b.release();
                    }
                }
            }
            this.f637d = null;
        }
    }

    /* renamed from: f */
    public final void m870f(C0299t c0299t, InterfaceC0325g interfaceC0325g) {
        synchronized (this) {
            this.f641h = interfaceC0325g;
            if (this.f629a || isCancelled()) {
                InterfaceC0325g interfaceC0325g2 = this.f641h;
                this.f641h = null;
                m868d(c0299t, interfaceC0325g2);
            }
        }
    }

    /* renamed from: g */
    public final boolean m871g(Exception exc, Object obj, C0299t c0299t) {
        synchronized (this) {
            if (!m865b()) {
                return false;
            }
            this.f639f = obj;
            this.f638e = exc;
            m869e();
            InterfaceC0325g interfaceC0325g = this.f641h;
            this.f641h = null;
            m868d(c0299t, interfaceC0325g);
            return true;
        }
    }

    @Override // java.util.concurrent.Future
    public final Object get() {
        b0 b0Var;
        synchronized (this) {
            if (!isCancelled() && !this.f629a) {
                if (this.f637d == null) {
                    this.f637d = new C0203h(1);
                }
                C0203h c0203h = this.f637d;
                c0203h.getClass();
                Thread currentThread = Thread.currentThread();
                WeakHashMap weakHashMap = b0.f502c;
                synchronized (weakHashMap) {
                    b0Var = (b0) weakHashMap.get(currentThread);
                    if (b0Var == null) {
                        b0Var = new b0();
                        weakHashMap.put(currentThread, b0Var);
                    }
                }
                C0203h c0203h2 = b0Var.f503a;
                b0Var.f503a = c0203h;
                Semaphore semaphore = b0Var.f504b;
                try {
                    if (!((Semaphore) c0203h.f245e).tryAcquire()) {
                        while (true) {
                            Runnable remove = b0Var.remove();
                            if (remove == null) {
                                semaphore.acquire(Math.max(1, semaphore.availablePermits()));
                                if (((Semaphore) c0203h.f245e).tryAcquire()) {
                                    break;
                                }
                            } else {
                                remove.run();
                            }
                        }
                    }
                    b0Var.f503a = c0203h2;
                    return m867c();
                } catch (Throwable th) {
                    b0Var.f503a = c0203h2;
                    throw th;
                }
            }
            return m867c();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x0084  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0089  */
    @Override // java.util.concurrent.Future
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object get(long j2, TimeUnit timeUnit) {
        b0 b0Var;
        synchronized (this) {
            if (!isCancelled() && !this.f629a) {
                boolean z2 = true;
                if (this.f637d == null) {
                    this.f637d = new C0203h(1);
                }
                C0203h c0203h = this.f637d;
                c0203h.getClass();
                long convert = TimeUnit.MILLISECONDS.convert(j2, timeUnit);
                Thread currentThread = Thread.currentThread();
                WeakHashMap weakHashMap = b0.f502c;
                synchronized (weakHashMap) {
                    b0Var = (b0) weakHashMap.get(currentThread);
                    if (b0Var == null) {
                        b0Var = new b0();
                        weakHashMap.put(currentThread, b0Var);
                    }
                }
                C0203h c0203h2 = b0Var.f503a;
                b0Var.f503a = c0203h;
                Semaphore semaphore = b0Var.f504b;
                try {
                    if (!((Semaphore) c0203h.f245e).tryAcquire()) {
                        long currentTimeMillis = System.currentTimeMillis();
                        while (true) {
                            Runnable remove = b0Var.remove();
                            if (remove != null) {
                                remove.run();
                            } else {
                                if (!semaphore.tryAcquire(Math.max(1, semaphore.availablePermits()), convert, TimeUnit.MILLISECONDS)) {
                                    break;
                                }
                                if (((Semaphore) c0203h.f245e).tryAcquire()) {
                                    break;
                                }
                                if (System.currentTimeMillis() - currentTimeMillis >= convert) {
                                    break;
                                }
                            }
                        }
                        b0Var.f503a = c0203h2;
                        z2 = false;
                        if (z2) {
                            throw new TimeoutException();
                        }
                        return m867c();
                    }
                    if (z2) {
                    }
                } finally {
                    b0Var.f503a = c0203h2;
                }
            }
            return m867c();
        }
    }
}

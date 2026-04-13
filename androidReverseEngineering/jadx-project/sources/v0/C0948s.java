package v0;

import a1.C0014e;
import java.io.Closeable;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.Socket;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import p0.C0875q;
import p022z.C0981d;
import q0.AbstractC0887c;
import q0.AbstractRunnableC0885a;
import q0.ThreadFactoryC0886b;

/* renamed from: v0.s */
/* loaded from: classes.dex */
public final class C0948s implements Closeable {

    /* renamed from: x */
    public static final ThreadPoolExecutor f2199x;

    /* renamed from: a */
    public final boolean f2200a;

    /* renamed from: b */
    public final AbstractC0944o f2201b;

    /* renamed from: d */
    public final String f2203d;

    /* renamed from: e */
    public int f2204e;

    /* renamed from: f */
    public int f2205f;

    /* renamed from: g */
    public boolean f2206g;

    /* renamed from: h */
    public final ScheduledThreadPoolExecutor f2207h;

    /* renamed from: i */
    public final ThreadPoolExecutor f2208i;

    /* renamed from: j */
    public final C0875q f2209j;

    /* renamed from: q */
    public long f2216q;

    /* renamed from: r */
    public final C0981d f2217r;

    /* renamed from: s */
    public final C0981d f2218s;

    /* renamed from: t */
    public final Socket f2219t;

    /* renamed from: u */
    public final C0955z f2220u;

    /* renamed from: v */
    public final C0946q f2221v;

    /* renamed from: w */
    public final LinkedHashSet f2222w;

    /* renamed from: c */
    public final LinkedHashMap f2202c = new LinkedHashMap();

    /* renamed from: k */
    public long f2210k = 0;

    /* renamed from: l */
    public long f2211l = 0;

    /* renamed from: m */
    public long f2212m = 0;

    /* renamed from: n */
    public long f2213n = 0;

    /* renamed from: o */
    public long f2214o = 0;

    /* renamed from: p */
    public long f2215p = 0;

    static {
        TimeUnit timeUnit = TimeUnit.SECONDS;
        SynchronousQueue synchronousQueue = new SynchronousQueue();
        byte[] bArr = AbstractC0887c.f1934a;
        f2199x = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, timeUnit, synchronousQueue, new ThreadFactoryC0886b("OkHttp Http2Connection", true));
    }

    public C0948s(C0942m c0942m) {
        C0981d c0981d = new C0981d();
        this.f2217r = c0981d;
        C0981d c0981d2 = new C0981d();
        this.f2218s = c0981d2;
        this.f2222w = new LinkedHashSet();
        this.f2209j = c0.f2143a;
        this.f2200a = true;
        this.f2201b = c0942m.f2186e;
        this.f2205f = 3;
        c0981d.m1474e(7, 16777216);
        String str = c0942m.f2183b;
        this.f2203d = str;
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1, new ThreadFactoryC0886b(AbstractC0887c.m1312i(new Object[]{str}, "OkHttp %s Writer"), false));
        this.f2207h = scheduledThreadPoolExecutor;
        if (c0942m.f2187f != 0) {
            C0939j c0939j = new C0939j(this);
            long j2 = c0942m.f2187f;
            scheduledThreadPoolExecutor.scheduleAtFixedRate(c0939j, j2, j2, TimeUnit.MILLISECONDS);
        }
        this.f2208i = new ThreadPoolExecutor(0, 1, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue(), new ThreadFactoryC0886b(AbstractC0887c.m1312i(new Object[]{str}, "OkHttp %s Push Observer"), true));
        c0981d2.m1474e(7, 65535);
        c0981d2.m1474e(5, 16384);
        this.f2216q = c0981d2.m1473d();
        this.f2219t = c0942m.f2182a;
        this.f2220u = new C0955z(c0942m.f2185d, true);
        this.f2221v = new C0946q(this, new C0951v(c0942m.f2184c, true));
    }

    /* renamed from: A */
    public final synchronized void m1408A(AbstractRunnableC0885a abstractRunnableC0885a) {
        if (!this.f2206g) {
            this.f2208i.execute(abstractRunnableC0885a);
        }
    }

    /* renamed from: B */
    public final synchronized C0954y m1409B(int i2) {
        C0954y c0954y;
        c0954y = (C0954y) this.f2202c.remove(Integer.valueOf(i2));
        notifyAll();
        return c0954y;
    }

    /* renamed from: C */
    public final void m1410C(EnumC0931b enumC0931b) {
        synchronized (this.f2220u) {
            synchronized (this) {
                if (this.f2206g) {
                    return;
                }
                this.f2206g = true;
                this.f2220u.m1435A(this.f2204e, enumC0931b, AbstractC0887c.f1934a);
            }
        }
    }

    /* renamed from: D */
    public final synchronized void m1411D(long j2) {
        long j3 = this.f2215p + j2;
        this.f2215p = j3;
        if (j3 >= this.f2217r.m1473d() / 2) {
            m1414G(0, this.f2215p);
            this.f2215p = 0L;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x0030, code lost:
    
        r2 = java.lang.Math.min((int) java.lang.Math.min(r12, r4), r8.f2220u.f2268d);
        r6 = r2;
        r8.f2216q -= r6;
     */
    /* renamed from: E */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void m1412E(int i2, boolean z2, C0014e c0014e, long j2) {
        int min;
        long j3;
        if (j2 == 0) {
            this.f2220u.m1441y(z2, i2, c0014e, 0);
            return;
        }
        while (j2 > 0) {
            synchronized (this) {
                while (true) {
                    try {
                        long j4 = this.f2216q;
                        if (j4 > 0) {
                            break;
                        } else {
                            if (!this.f2202c.containsKey(Integer.valueOf(i2))) {
                                throw new IOException("stream closed");
                            }
                            wait();
                        }
                    } catch (InterruptedException unused) {
                        Thread.currentThread().interrupt();
                        throw new InterruptedIOException();
                    }
                }
            }
            j2 -= j3;
            this.f2220u.m1441y(z2 && j2 == 0, i2, c0014e, min);
        }
    }

    /* renamed from: F */
    public final void m1413F(int i2, EnumC0931b enumC0931b) {
        try {
            this.f2207h.execute(new C0937h(this, "OkHttp %s stream %d", new Object[]{this.f2203d, Integer.valueOf(i2)}, i2, enumC0931b, 0));
        } catch (RejectedExecutionException unused) {
        }
    }

    /* renamed from: G */
    public final void m1414G(int i2, long j2) {
        try {
            this.f2207h.execute(new C0938i(this, new Object[]{this.f2203d, Integer.valueOf(i2)}, i2, j2));
        } catch (RejectedExecutionException unused) {
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() {
        m1415x(EnumC0931b.NO_ERROR, EnumC0931b.CANCEL, null);
    }

    public final void flush() {
        C0955z c0955z = this.f2220u;
        synchronized (c0955z) {
            if (c0955z.f2269e) {
                throw new IOException("closed");
            }
            c0955z.f2265a.flush();
        }
    }

    /* renamed from: x */
    public final void m1415x(EnumC0931b enumC0931b, EnumC0931b enumC0931b2, IOException iOException) {
        C0954y[] c0954yArr;
        try {
            m1410C(enumC0931b);
        } catch (IOException unused) {
        }
        synchronized (this) {
            if (this.f2202c.isEmpty()) {
                c0954yArr = null;
            } else {
                c0954yArr = (C0954y[]) this.f2202c.values().toArray(new C0954y[this.f2202c.size()]);
                this.f2202c.clear();
            }
        }
        if (c0954yArr != null) {
            for (C0954y c0954y : c0954yArr) {
                try {
                    c0954y.m1428c(enumC0931b2, iOException);
                } catch (IOException unused2) {
                }
            }
        }
        try {
            this.f2220u.close();
        } catch (IOException unused3) {
        }
        try {
            this.f2219t.close();
        } catch (IOException unused4) {
        }
        this.f2207h.shutdown();
        this.f2208i.shutdown();
    }

    /* renamed from: y */
    public final void m1416y(IOException iOException) {
        EnumC0931b enumC0931b = EnumC0931b.PROTOCOL_ERROR;
        m1415x(enumC0931b, enumC0931b, iOException);
    }

    /* renamed from: z */
    public final synchronized C0954y m1417z(int i2) {
        return (C0954y) this.f2202c.get(Integer.valueOf(i2));
    }
}

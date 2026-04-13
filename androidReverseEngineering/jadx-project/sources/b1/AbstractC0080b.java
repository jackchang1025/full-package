package b1;

import a1.AbstractC0026q;
import android.content.Context;
import android.util.Log;
import c1.C0101d;
import c1.InterfaceC0099b;
import com.guard.wallet.utils.AbstractC0251g;
import java.io.Closeable;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import javax.security.auth.DestroyFailedException;
import l0.RunnableC0379i;

/* renamed from: b1.b */
/* loaded from: classes.dex */
public abstract class AbstractC0080b implements Closeable {

    /* renamed from: b */
    public C0082d f91b;

    /* renamed from: a */
    public final Object f90a = new Object();

    /* renamed from: c */
    public String f92c = "127.0.0.1";

    /* renamed from: d */
    public int f93d = 0;

    /* renamed from: e */
    public int f94e = 1;

    /* renamed from: f */
    public long f95f = 30000;

    /* renamed from: g */
    public TimeUnit f96g = TimeUnit.MILLISECONDS;

    /* renamed from: h */
    public final ExecutorService f97h = Executors.newFixedThreadPool(1);

    /* renamed from: A */
    public final C0089k m299A() {
        PrivateKey mo301C = mo301C();
        Objects.requireNonNull(mo301C);
        Certificate mo300B = mo300B();
        Objects.requireNonNull(mo300B);
        return new C0089k(mo301C, mo300B);
    }

    /* renamed from: B */
    public abstract Certificate mo300B();

    /* renamed from: C */
    public abstract PrivateKey mo301C();

    /* renamed from: D */
    public abstract boolean mo302D();

    /* renamed from: E */
    public final C0086h m303E(String[] strArr, int i2) {
        C0086h m312z;
        synchronized (this.f90a) {
            C0082d c0082d = this.f91b;
            if (c0082d != null) {
                Socket socket = c0082d.f99a;
                if (!socket.isClosed() && socket.isConnected()) {
                    m312z = this.f91b.m312z(strArr, i2);
                }
            }
            throw new IOException("Not connected to ADB.");
        }
        return m312z;
    }

    /* renamed from: F */
    public final boolean m304F(String str, int i2, String str2) {
        boolean z2;
        synchronized (this.f90a) {
            C0089k m299A = m299A();
            CountDownLatch countDownLatch = new CountDownLatch(1);
            AtomicBoolean atomicBoolean = new AtomicBoolean(false);
            Objects.requireNonNull(str);
            Objects.requireNonNull(str2);
            C0094p c0094p = new C0094p(str, i2, AbstractC0251g.m652Y(str2), m299A);
            this.f97h.submit(new RunnableC0379i(c0094p, atomicBoolean, countDownLatch, 1));
            try {
                if (!countDownLatch.await(15L, TimeUnit.SECONDS)) {
                    atomicBoolean.set(false);
                }
            } catch (Exception e2) {
                AbstractC0026q.m186s("AbsAdbConnectionManager", e2);
            }
            c0094p.close();
            z2 = atomicBoolean.get();
        }
        return z2;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        try {
            C0082d c0082d = this.f91b;
            if (c0082d != null) {
                c0082d.close();
                this.f91b = null;
            }
            this.f97h.shutdownNow();
            mo301C().destroy();
        } catch (NoSuchMethodError | DestroyFailedException e2) {
            AbstractC0026q.m187t("AbsAdbConnectionManager", e2);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0084 A[Catch: all -> 0x0099, DONT_GENERATE, TryCatch #2 {, blocks: (B:4:0x0003, B:10:0x0045, B:14:0x005a, B:16:0x0066, B:18:0x006b, B:19:0x0078, B:21:0x0084, B:24:0x0086, B:29:0x0072, B:32:0x0088, B:33:0x008f, B:36:0x0092, B:37:0x0098, B:6:0x0034, B:8:0x003e), top: B:3:0x0003, inners: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0086 A[Catch: all -> 0x0099, DONT_GENERATE, TryCatch #2 {, blocks: (B:4:0x0003, B:10:0x0045, B:14:0x005a, B:16:0x0066, B:18:0x006b, B:19:0x0078, B:21:0x0084, B:24:0x0086, B:29:0x0072, B:32:0x0088, B:33:0x008f, B:36:0x0092, B:37:0x0098, B:6:0x0034, B:8:0x003e), top: B:3:0x0003, inners: #0 }] */
    /* renamed from: x */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int m305x(Context context) {
        C0082d c0082d;
        synchronized (this.f90a) {
            final AtomicInteger atomicInteger = new AtomicInteger(-1);
            C0082d c0082d2 = null;
            final AtomicReference atomicReference = new AtomicReference(null);
            final int i2 = 1;
            final CountDownLatch countDownLatch = new CountDownLatch(1);
            final int i3 = 0;
            C0101d c0101d = new C0101d(context, "adb", new InterfaceC0099b() { // from class: b1.a
                @Override // c1.InterfaceC0099b
                /* renamed from: a */
                public final void mo298a(InetAddress inetAddress, int i4) {
                    int i5 = i3;
                    AtomicInteger atomicInteger2 = atomicInteger;
                    AtomicReference atomicReference2 = atomicReference;
                    CountDownLatch countDownLatch2 = countDownLatch;
                    switch (i5) {
                        case 0:
                            if (inetAddress != null) {
                                atomicReference2.set(inetAddress.getHostAddress());
                                atomicInteger2.set(i4);
                            }
                            countDownLatch2.countDown();
                            break;
                        default:
                            if (inetAddress != null) {
                                atomicReference2.set(inetAddress.getHostAddress());
                                atomicInteger2.set(i4);
                            }
                            countDownLatch2.countDown();
                            break;
                    }
                }
            });
            c0101d.m328a();
            C0101d c0101d2 = new C0101d(context, "adb-tls-connect", new InterfaceC0099b() { // from class: b1.a
                @Override // c1.InterfaceC0099b
                /* renamed from: a */
                public final void mo298a(InetAddress inetAddress, int i4) {
                    int i5 = i2;
                    AtomicInteger atomicInteger2 = atomicInteger;
                    AtomicReference atomicReference2 = atomicReference;
                    CountDownLatch countDownLatch2 = countDownLatch;
                    switch (i5) {
                        case 0:
                            if (inetAddress != null) {
                                atomicReference2.set(inetAddress.getHostAddress());
                                atomicInteger2.set(i4);
                            }
                            countDownLatch2.countDown();
                            break;
                        default:
                            if (inetAddress != null) {
                                atomicReference2.set(inetAddress.getHostAddress());
                                atomicInteger2.set(i4);
                            }
                            countDownLatch2.countDown();
                            break;
                    }
                }
            });
            c0101d2.m328a();
            try {
                if (!countDownLatch.await(10000L, TimeUnit.MILLISECONDS)) {
                    Log.e("AbsAdbConnectionManager", "Timed out while trying to find a valid tls host address and port");
                }
                c0101d.m329b();
                c0101d2.m329b();
                String str = (String) atomicReference.get();
                int i4 = atomicInteger.get();
                if (str != null && i4 != -1) {
                    this.f92c = str;
                    try {
                        c0082d = new C0082d(str, i4, m299A(), this.f94e);
                        try {
                            c0082d.f116r = "com.guard.wallet";
                        } catch (Exception e2) {
                            e = e2;
                            c0082d2 = c0082d;
                            int i5 = C0082d.f98w;
                            AbstractC0026q.m186s("d", e);
                            c0082d = c0082d2;
                            this.f91b = c0082d;
                            if (c0082d.m311y(this.f95f, this.f96g)) {
                            }
                        }
                    } catch (Exception e3) {
                        e = e3;
                    }
                    this.f91b = c0082d;
                    if (c0082d.m311y(this.f95f, this.f96g)) {
                        return 0;
                    }
                    return i4;
                }
                Log.e("AbsAdbConnectionManager", "Could not find any valid host address or port");
                return 0;
            } catch (Throwable th) {
                c0101d.m329b();
                c0101d2.m329b();
                throw th;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x0040 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* renamed from: y */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int m306y(int i2, String str) {
        C0082d c0082d;
        synchronized (this.f90a) {
            if (mo302D()) {
                return this.f93d;
            }
            C0082d c0082d2 = this.f91b;
            C0082d c0082d3 = null;
            if (c0082d2 != null) {
                c0082d2.close();
                this.f91b = null;
                Log.d("AbsAdbConnectionManager", "释放 mAdbConnection");
            }
            this.f92c = str;
            try {
                c0082d = new C0082d(str, i2, m299A(), this.f94e);
            } catch (Exception e2) {
                e = e2;
            }
            try {
                c0082d.f116r = "com.guard.wallet";
            } catch (Exception e3) {
                e = e3;
                c0082d3 = c0082d;
                int i3 = C0082d.f98w;
                AbstractC0026q.m186s("d", e);
                c0082d = c0082d3;
                this.f91b = c0082d;
                if (c0082d != null) {
                }
                this.f93d = 0;
                return this.f93d;
            }
            this.f91b = c0082d;
            if (c0082d != null) {
                try {
                } catch (C0081c e4) {
                    AbstractC0026q.m186s("AbsAdbConnectionManager", e4);
                    this.f93d = -1;
                } catch (Exception e5) {
                    AbstractC0026q.m186s("AbsAdbConnectionManager", e5);
                    this.f93d = -2;
                }
                if (c0082d.m311y(10000L, TimeUnit.MILLISECONDS)) {
                    this.f93d = i2;
                    return this.f93d;
                }
            }
            this.f93d = 0;
            return this.f93d;
        }
    }

    /* renamed from: z */
    public final boolean m307z(int i2) {
        C0082d c0082d;
        synchronized (this.f90a) {
            if (mo302D()) {
                return true;
            }
            C0082d c0082d2 = this.f91b;
            C0082d c0082d3 = null;
            if (c0082d2 != null) {
                c0082d2.close();
                this.f91b = null;
                Log.d("AbsAdbConnectionManager", "释放 mAdbConnection");
            }
            try {
                c0082d = new C0082d(this.f92c, i2, m299A(), this.f94e);
            } catch (Exception e2) {
                e = e2;
            }
            try {
                c0082d.f116r = "com.guard.wallet";
            } catch (Exception e3) {
                e = e3;
                c0082d3 = c0082d;
                int i3 = C0082d.f98w;
                AbstractC0026q.m186s("d", e);
                c0082d = c0082d3;
                this.f91b = c0082d;
                return c0082d.m311y(this.f95f, this.f96g);
            }
            this.f91b = c0082d;
            return c0082d.m311y(this.f95f, this.f96g);
        }
    }
}

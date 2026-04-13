package v0;

import a1.C0014e;
import a1.C0017h;
import a1.InterfaceC0015f;
import java.io.Closeable;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import p022z.C0981d;
import q0.AbstractC0887c;

/* renamed from: v0.z */
/* loaded from: classes.dex */
public final class C0955z implements Closeable {

    /* renamed from: g */
    public static final Logger f2264g = Logger.getLogger(AbstractC0936g.class.getName());

    /* renamed from: a */
    public final InterfaceC0015f f2265a;

    /* renamed from: b */
    public final boolean f2266b;

    /* renamed from: c */
    public final C0014e f2267c;

    /* renamed from: d */
    public int f2268d;

    /* renamed from: e */
    public boolean f2269e;

    /* renamed from: f */
    public final C0934e f2270f;

    public C0955z(InterfaceC0015f interfaceC0015f, boolean z2) {
        this.f2265a = interfaceC0015f;
        this.f2266b = z2;
        C0014e c0014e = new C0014e();
        this.f2267c = c0014e;
        this.f2270f = new C0934e(c0014e);
        this.f2268d = 16384;
    }

    /* renamed from: A */
    public final synchronized void m1435A(int i2, EnumC0931b enumC0931b, byte[] bArr) {
        if (this.f2269e) {
            throw new IOException("closed");
        }
        if (enumC0931b.f2129a == -1) {
            C0017h c0017h = AbstractC0936g.f2163a;
            throw new IllegalArgumentException(AbstractC0887c.m1312i(new Object[0], "errorCode.httpCode == -1"));
        }
        m1442z(0, bArr.length + 8, (byte) 7, (byte) 0);
        this.f2265a.mo101k(i2);
        this.f2265a.mo101k(enumC0931b.f2129a);
        if (bArr.length > 0) {
            this.f2265a.mo106p(bArr);
        }
        this.f2265a.flush();
    }

    /* renamed from: B */
    public final synchronized void m1436B(boolean z2, int i2, int i3) {
        if (this.f2269e) {
            throw new IOException("closed");
        }
        m1442z(0, 8, (byte) 6, z2 ? (byte) 1 : (byte) 0);
        this.f2265a.mo101k(i2);
        this.f2265a.mo101k(i3);
        this.f2265a.flush();
    }

    /* renamed from: C */
    public final synchronized void m1437C(int i2, EnumC0931b enumC0931b) {
        if (this.f2269e) {
            throw new IOException("closed");
        }
        if (enumC0931b.f2129a == -1) {
            throw new IllegalArgumentException();
        }
        m1442z(i2, 4, (byte) 3, (byte) 0);
        this.f2265a.mo101k(enumC0931b.f2129a);
        this.f2265a.flush();
    }

    /* renamed from: D */
    public final synchronized void m1438D(int i2, long j2) {
        if (this.f2269e) {
            throw new IOException("closed");
        }
        if (j2 == 0 || j2 > 2147483647L) {
            Object[] objArr = {Long.valueOf(j2)};
            C0017h c0017h = AbstractC0936g.f2163a;
            throw new IllegalArgumentException(AbstractC0887c.m1312i(objArr, "windowSizeIncrement == 0 || windowSizeIncrement > 0x7fffffffL: %s"));
        }
        m1442z(i2, 4, (byte) 8, (byte) 0);
        this.f2265a.mo101k((int) j2);
        this.f2265a.flush();
    }

    /* renamed from: E */
    public final void m1439E(int i2, long j2) {
        while (j2 > 0) {
            int min = (int) Math.min(this.f2268d, j2);
            long j3 = min;
            j2 -= j3;
            m1442z(i2, min, (byte) 9, j2 == 0 ? (byte) 4 : (byte) 0);
            this.f2265a.mo67i(this.f2267c, j3);
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final synchronized void close() {
        this.f2269e = true;
        this.f2265a.close();
    }

    /* renamed from: x */
    public final synchronized void m1440x(C0981d c0981d) {
        if (this.f2269e) {
            throw new IOException("closed");
        }
        int i2 = this.f2268d;
        int i3 = c0981d.f2326b;
        if ((i3 & 32) != 0) {
            i2 = ((int[]) c0981d.f2327c)[5];
        }
        this.f2268d = i2;
        if (((i3 & 2) != 0 ? ((int[]) c0981d.f2327c)[1] : -1) != -1) {
            C0934e c0934e = this.f2270f;
            int i4 = (i3 & 2) != 0 ? ((int[]) c0981d.f2327c)[1] : -1;
            c0934e.getClass();
            int min = Math.min(i4, 16384);
            int i5 = c0934e.f2156d;
            if (i5 != min) {
                if (min < i5) {
                    c0934e.f2154b = Math.min(c0934e.f2154b, min);
                }
                c0934e.f2155c = true;
                c0934e.f2156d = min;
                int i6 = c0934e.f2160h;
                if (min < i6) {
                    if (min == 0) {
                        Arrays.fill(c0934e.f2157e, (Object) null);
                        c0934e.f2158f = c0934e.f2157e.length - 1;
                        c0934e.f2159g = 0;
                        c0934e.f2160h = 0;
                    } else {
                        c0934e.m1400a(i6 - min);
                    }
                }
            }
        }
        m1442z(0, 0, (byte) 4, (byte) 1);
        this.f2265a.flush();
    }

    /* renamed from: y */
    public final synchronized void m1441y(boolean z2, int i2, C0014e c0014e, int i3) {
        if (this.f2269e) {
            throw new IOException("closed");
        }
        m1442z(i2, i3, (byte) 0, z2 ? (byte) 1 : (byte) 0);
        if (i3 > 0) {
            this.f2265a.mo67i(c0014e, i3);
        }
    }

    /* renamed from: z */
    public final void m1442z(int i2, int i3, byte b, byte b2) {
        Level level = Level.FINE;
        Logger logger = f2264g;
        if (logger.isLoggable(level)) {
            logger.fine(AbstractC0936g.m1406a(false, i2, i3, b, b2));
        }
        int i4 = this.f2268d;
        if (i3 > i4) {
            Object[] objArr = {Integer.valueOf(i4), Integer.valueOf(i3)};
            C0017h c0017h = AbstractC0936g.f2163a;
            throw new IllegalArgumentException(AbstractC0887c.m1312i(objArr, "FRAME_SIZE_ERROR length > %d: %d"));
        }
        if ((Integer.MIN_VALUE & i2) != 0) {
            Object[] objArr2 = {Integer.valueOf(i2)};
            C0017h c0017h2 = AbstractC0936g.f2163a;
            throw new IllegalArgumentException(AbstractC0887c.m1312i(objArr2, "reserved bit set: %s"));
        }
        InterfaceC0015f interfaceC0015f = this.f2265a;
        interfaceC0015f.mo105o((i3 >>> 16) & 255);
        interfaceC0015f.mo105o((i3 >>> 8) & 255);
        interfaceC0015f.mo105o(i3 & 255);
        interfaceC0015f.mo105o(b & 255);
        interfaceC0015f.mo105o(b2 & 255);
        interfaceC0015f.mo101k(i2 & Integer.MAX_VALUE);
    }
}

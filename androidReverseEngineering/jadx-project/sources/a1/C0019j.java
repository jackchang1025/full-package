package a1;

import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.logging.Logger;
import java.util.zip.CRC32;
import java.util.zip.Inflater;

/* renamed from: a1.j */
/* loaded from: classes.dex */
public final class C0019j implements InterfaceC0029t {

    /* renamed from: b */
    public final C0024o f30b;

    /* renamed from: c */
    public final Inflater f31c;

    /* renamed from: d */
    public final C0020k f32d;

    /* renamed from: a */
    public int f29a = 0;

    /* renamed from: e */
    public final CRC32 f33e = new CRC32();

    public C0019j(InterfaceC0029t interfaceC0029t) {
        if (interfaceC0029t == null) {
            throw new IllegalArgumentException("source == null");
        }
        Inflater inflater = new Inflater(true);
        this.f31c = inflater;
        Logger logger = AbstractC0021l.f38a;
        C0024o c0024o = new C0024o(interfaceC0029t);
        this.f30b = c0024o;
        this.f32d = new C0020k(c0024o, inflater);
    }

    /* renamed from: x */
    public static void m137x(String str, int i2, int i3) {
        if (i3 != i2) {
            throw new IOException(String.format("%s: actual 0x%08x != expected 0x%08x", str, Integer.valueOf(i3), Integer.valueOf(i2)));
        }
    }

    @Override // a1.InterfaceC0029t
    /* renamed from: a */
    public final C0031v mo68a() {
        return this.f30b.mo68a();
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() {
        this.f32d.close();
    }

    @Override // a1.InterfaceC0029t
    /* renamed from: u */
    public final long mo69u(C0014e c0014e, long j2) {
        C0024o c0024o;
        C0014e c0014e2;
        long j3;
        if (j2 < 0) {
            throw new IllegalArgumentException("byteCount < 0: " + j2);
        }
        if (j2 == 0) {
            return 0L;
        }
        int i2 = this.f29a;
        CRC32 crc32 = this.f33e;
        C0024o c0024o2 = this.f30b;
        if (i2 == 0) {
            c0024o2.mo108r(10L);
            C0014e c0014e3 = c0024o2.f45a;
            byte m115z = c0014e3.m115z(3L);
            boolean z2 = ((m115z >> 1) & 1) == 1;
            if (z2) {
                c0014e2 = c0014e3;
                m138y(c0024o2.f45a, 0L, 10L);
            } else {
                c0014e2 = c0014e3;
            }
            m137x("ID1ID2", 8075, c0024o2.readShort());
            c0024o2.skip(8L);
            if (((m115z >> 2) & 1) == 1) {
                c0024o2.mo108r(2L);
                if (z2) {
                    m138y(c0024o2.f45a, 0L, 2L);
                }
                short readShort = c0014e2.readShort();
                Charset charset = AbstractC0032w.f75a;
                int i3 = readShort & 65535;
                long j4 = ((short) (((i3 & 255) << 8) | ((i3 & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >>> 8))) & 65535;
                c0024o2.mo108r(j4);
                if (z2) {
                    m138y(c0024o2.f45a, 0L, j4);
                    j3 = j4;
                } else {
                    j3 = j4;
                }
                c0024o2.skip(j3);
            }
            if (((m115z >> 3) & 1) == 1) {
                long m143x = c0024o2.m143x((byte) 0, 0L, Long.MAX_VALUE);
                if (m143x == -1) {
                    throw new EOFException();
                }
                if (z2) {
                    c0024o = c0024o2;
                    m138y(c0024o2.f45a, 0L, m143x + 1);
                } else {
                    c0024o = c0024o2;
                }
                c0024o.skip(m143x + 1);
            } else {
                c0024o = c0024o2;
            }
            if (((m115z >> 4) & 1) == 1) {
                long m143x2 = c0024o.m143x((byte) 0, 0L, Long.MAX_VALUE);
                if (m143x2 == -1) {
                    throw new EOFException();
                }
                if (z2) {
                    m138y(c0024o.f45a, 0L, m143x2 + 1);
                }
                c0024o.skip(m143x2 + 1);
            }
            if (z2) {
                c0024o.mo108r(2L);
                short readShort2 = c0014e2.readShort();
                Charset charset2 = AbstractC0032w.f75a;
                int i4 = readShort2 & 65535;
                m137x("FHCRC", (short) (((i4 & 255) << 8) | ((i4 & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >>> 8)), (short) crc32.getValue());
                crc32.reset();
            }
            this.f29a = 1;
        } else {
            c0024o = c0024o2;
        }
        if (this.f29a == 1) {
            long j5 = c0014e.f22b;
            long mo69u = this.f32d.mo69u(c0014e, j2);
            if (mo69u != -1) {
                m138y(c0014e, j5, mo69u);
                return mo69u;
            }
            this.f29a = 2;
        }
        if (this.f29a == 2) {
            c0024o.mo108r(4L);
            int readInt = c0024o.f45a.readInt();
            Charset charset3 = AbstractC0032w.f75a;
            m137x("CRC", ((readInt & 255) << 24) | ((readInt & ViewCompat.MEASURED_STATE_MASK) >>> 24) | ((readInt & 16711680) >>> 8) | ((readInt & MotionEventCompat.ACTION_POINTER_INDEX_MASK) << 8), (int) crc32.getValue());
            c0024o.mo108r(4L);
            int readInt2 = c0024o.f45a.readInt();
            m137x("ISIZE", ((readInt2 & 255) << 24) | ((readInt2 & ViewCompat.MEASURED_STATE_MASK) >>> 24) | ((readInt2 & 16711680) >>> 8) | ((65280 & readInt2) << 8), (int) this.f31c.getBytesWritten());
            this.f29a = 3;
            if (!c0024o.mo104n()) {
                throw new IOException("gzip finished without exhausting source");
            }
        }
        return -1L;
    }

    /* renamed from: y */
    public final void m138y(C0014e c0014e, long j2, long j3) {
        C0025p c0025p = c0014e.f21a;
        while (true) {
            int i2 = c0025p.f50c;
            int i3 = c0025p.f49b;
            if (j2 < i2 - i3) {
                break;
            }
            j2 -= i2 - i3;
            c0025p = c0025p.f53f;
        }
        while (j3 > 0) {
            int min = (int) Math.min(c0025p.f50c - r6, j3);
            this.f33e.update(c0025p.f48a, (int) (c0025p.f49b + j2), min);
            j3 -= min;
            c0025p = c0025p.f53f;
            j2 = 0;
        }
    }
}

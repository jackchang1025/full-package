package a1;

import java.io.EOFException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/* renamed from: a1.o */
/* loaded from: classes.dex */
public final class C0024o implements InterfaceC0016g {

    /* renamed from: a */
    public final C0014e f45a = new C0014e();

    /* renamed from: b */
    public final InterfaceC0029t f46b;

    /* renamed from: c */
    public boolean f47c;

    public C0024o(InterfaceC0029t interfaceC0029t) {
        if (interfaceC0029t == null) {
            throw new NullPointerException("source == null");
        }
        this.f46b = interfaceC0029t;
    }

    @Override // a1.InterfaceC0029t
    /* renamed from: a */
    public final C0031v mo68a() {
        return this.f46b.mo68a();
    }

    @Override // a1.InterfaceC0016g
    /* renamed from: b */
    public final int mo93b(C0022m c0022m) {
        C0014e c0014e;
        if (this.f47c) {
            throw new IllegalStateException("closed");
        }
        do {
            c0014e = this.f45a;
            int m82F = c0014e.m82F(c0022m, true);
            if (m82F == -1) {
                return -1;
            }
            if (m82F != -2) {
                c0014e.skip(c0022m.f40a[m82F].mo125j());
                return m82F;
            }
        } while (this.f46b.mo69u(c0014e, 8192L) != -1);
        return -1;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable, java.nio.channels.Channel
    public final void close() {
        if (this.f47c) {
            return;
        }
        this.f47c = true;
        this.f46b.close();
        this.f45a.m113x();
    }

    @Override // a1.InterfaceC0016g
    /* renamed from: f */
    public final C0014e mo97f() {
        return this.f45a;
    }

    @Override // a1.InterfaceC0016g
    /* renamed from: h */
    public final C0017h mo99h(long j2) {
        mo108r(j2);
        return this.f45a.mo99h(j2);
    }

    @Override // java.nio.channels.Channel
    public final boolean isOpen() {
        return !this.f47c;
    }

    @Override // a1.InterfaceC0016g
    /* renamed from: l */
    public final String mo102l() {
        return mo107q(Long.MAX_VALUE);
    }

    @Override // a1.InterfaceC0016g
    /* renamed from: m */
    public final byte[] mo103m() {
        InterfaceC0029t interfaceC0029t = this.f46b;
        C0014e c0014e = this.f45a;
        c0014e.mo95d(interfaceC0029t);
        return c0014e.mo103m();
    }

    @Override // a1.InterfaceC0016g
    /* renamed from: n */
    public final boolean mo104n() {
        if (this.f47c) {
            throw new IllegalStateException("closed");
        }
        C0014e c0014e = this.f45a;
        return c0014e.mo104n() && this.f46b.mo69u(c0014e, 8192L) == -1;
    }

    @Override // a1.InterfaceC0016g
    /* renamed from: q */
    public final String mo107q(long j2) {
        if (j2 < 0) {
            throw new IllegalArgumentException("limit < 0: " + j2);
        }
        long j3 = j2 == Long.MAX_VALUE ? Long.MAX_VALUE : j2 + 1;
        long m143x = m143x((byte) 10, 0L, j3);
        C0014e c0014e = this.f45a;
        if (m143x != -1) {
            return c0014e.m81E(m143x);
        }
        if (j3 < Long.MAX_VALUE && m145z(j3) && c0014e.m115z(j3 - 1) == 13 && m145z(1 + j3) && c0014e.m115z(j3) == 10) {
            return c0014e.m81E(j3);
        }
        C0014e c0014e2 = new C0014e();
        c0014e.m114y(c0014e2, 0L, Math.min(32L, c0014e.f22b));
        throw new EOFException("\\n not found: limit=" + Math.min(c0014e.f22b, j2) + " content=" + new C0017h(c0014e2.mo103m()).mo122f() + (char) 8230);
    }

    @Override // a1.InterfaceC0016g
    /* renamed from: r */
    public final void mo108r(long j2) {
        if (!m145z(j2)) {
            throw new EOFException();
        }
    }

    @Override // java.nio.channels.ReadableByteChannel
    public final int read(ByteBuffer byteBuffer) {
        C0014e c0014e = this.f45a;
        if (c0014e.f22b == 0 && this.f46b.mo69u(c0014e, 8192L) == -1) {
            return -1;
        }
        return c0014e.read(byteBuffer);
    }

    @Override // a1.InterfaceC0016g
    public final byte readByte() {
        mo108r(1L);
        return this.f45a.readByte();
    }

    @Override // a1.InterfaceC0016g
    public final int readInt() {
        mo108r(4L);
        return this.f45a.readInt();
    }

    @Override // a1.InterfaceC0016g
    public final short readShort() {
        mo108r(2L);
        return this.f45a.readShort();
    }

    @Override // a1.InterfaceC0016g
    public final void skip(long j2) {
        if (this.f47c) {
            throw new IllegalStateException("closed");
        }
        while (j2 > 0) {
            C0014e c0014e = this.f45a;
            if (c0014e.f22b == 0 && this.f46b.mo69u(c0014e, 8192L) == -1) {
                throw new EOFException();
            }
            long min = Math.min(j2, c0014e.f22b);
            c0014e.skip(min);
            j2 -= min;
        }
    }

    public final String toString() {
        return "buffer(" + this.f46b + ")";
    }

    @Override // a1.InterfaceC0029t
    /* renamed from: u */
    public final long mo69u(C0014e c0014e, long j2) {
        if (c0014e == null) {
            throw new IllegalArgumentException("sink == null");
        }
        if (j2 < 0) {
            throw new IllegalArgumentException("byteCount < 0: " + j2);
        }
        if (this.f47c) {
            throw new IllegalStateException("closed");
        }
        C0014e c0014e2 = this.f45a;
        if (c0014e2.f22b == 0 && this.f46b.mo69u(c0014e2, 8192L) == -1) {
            return -1L;
        }
        return c0014e2.mo69u(c0014e, Math.min(j2, c0014e2.f22b));
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x0032, code lost:
    
        if (r1 == 0) goto L21;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0049, code lost:
    
        throw new java.lang.NumberFormatException(java.lang.String.format("Expected leading [0-9a-fA-F] character but was %#x", java.lang.Byte.valueOf(r3)));
     */
    @Override // a1.InterfaceC0016g
    /* renamed from: v */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final long mo111v() {
        C0014e c0014e;
        mo108r(1L);
        int i2 = 0;
        while (true) {
            int i3 = i2 + 1;
            boolean m145z = m145z(i3);
            c0014e = this.f45a;
            if (!m145z) {
                break;
            }
            byte m115z = c0014e.m115z(i2);
            if ((m115z < 48 || m115z > 57) && ((m115z < 97 || m115z > 102) && (m115z < 65 || m115z > 70))) {
                break;
            }
            i2 = i3;
        }
        return c0014e.mo111v();
    }

    @Override // a1.InterfaceC0016g
    /* renamed from: w */
    public final String mo112w(Charset charset) {
        if (charset == null) {
            throw new IllegalArgumentException("charset == null");
        }
        InterfaceC0029t interfaceC0029t = this.f46b;
        C0014e c0014e = this.f45a;
        c0014e.mo95d(interfaceC0029t);
        return c0014e.mo112w(charset);
    }

    /* renamed from: x */
    public final long m143x(byte b, long j2, long j3) {
        if (this.f47c) {
            throw new IllegalStateException("closed");
        }
        long j4 = 0;
        if (j3 < 0) {
            throw new IllegalArgumentException(String.format("fromIndex=%s toIndex=%s", 0L, Long.valueOf(j3)));
        }
        while (j4 < j3) {
            long m77A = this.f45a.m77A(b, j4, j3);
            if (m77A == -1) {
                C0014e c0014e = this.f45a;
                long j5 = c0014e.f22b;
                if (j5 >= j3 || this.f46b.mo69u(c0014e, 8192L) == -1) {
                    break;
                }
                j4 = Math.max(j4, j5);
            } else {
                return m77A;
            }
        }
        return -1L;
    }

    /* renamed from: y */
    public final void m144y(byte[] bArr) {
        C0014e c0014e = this.f45a;
        int i2 = 0;
        try {
            mo108r(bArr.length);
            c0014e.getClass();
            while (i2 < bArr.length) {
                int read = c0014e.read(bArr, i2, bArr.length - i2);
                if (read == -1) {
                    throw new EOFException();
                }
                i2 += read;
            }
        } catch (EOFException e2) {
            while (true) {
                long j2 = c0014e.f22b;
                if (j2 <= 0) {
                    throw e2;
                }
                int read2 = c0014e.read(bArr, i2, (int) j2);
                if (read2 == -1) {
                    throw new AssertionError();
                }
                i2 += read2;
            }
        }
    }

    /* renamed from: z */
    public final boolean m145z(long j2) {
        C0014e c0014e;
        if (j2 < 0) {
            throw new IllegalArgumentException("byteCount < 0: " + j2);
        }
        if (this.f47c) {
            throw new IllegalStateException("closed");
        }
        do {
            c0014e = this.f45a;
            if (c0014e.f22b >= j2) {
                return true;
            }
        } while (this.f46b.mo69u(c0014e, 8192L) != -1);
        return false;
    }
}

package a1;

import java.io.EOFException;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.nio.charset.Charset;
import org.bouncycastle.asn1.BERTags;
import com.guard.wallet.entity.BuildConfig;
import p000a.AbstractC0000a;

/* renamed from: a1.e */
/* loaded from: classes.dex */
public final class C0014e implements InterfaceC0016g, InterfaceC0015f, Cloneable, ByteChannel {

    /* renamed from: c */
    public static final byte[] f20c = {48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102};

    /* renamed from: a */
    public C0025p f21a;

    /* renamed from: b */
    public long f22b;

    /* renamed from: A */
    public final long m77A(byte b, long j2, long j3) {
        C0025p c0025p;
        long j4 = 0;
        if (j2 < 0 || j3 < j2) {
            throw new IllegalArgumentException(String.format("size=%s fromIndex=%s toIndex=%s", Long.valueOf(this.f22b), Long.valueOf(j2), Long.valueOf(j3)));
        }
        long j5 = this.f22b;
        long j6 = j3 > j5 ? j5 : j3;
        if (j2 == j6 || (c0025p = this.f21a) == null) {
            return -1L;
        }
        if (j5 - j2 < j2) {
            while (j5 > j2) {
                c0025p = c0025p.f54g;
                j5 -= c0025p.f50c - c0025p.f49b;
            }
        } else {
            while (true) {
                long j7 = (c0025p.f50c - c0025p.f49b) + j4;
                if (j7 >= j2) {
                    break;
                }
                c0025p = c0025p.f53f;
                j4 = j7;
            }
            j5 = j4;
        }
        long j8 = j2;
        while (j5 < j6) {
            byte[] bArr = c0025p.f48a;
            int min = (int) Math.min(c0025p.f50c, (c0025p.f49b + j6) - j5);
            for (int i2 = (int) ((c0025p.f49b + j8) - j5); i2 < min; i2++) {
                if (bArr[i2] == b) {
                    return (i2 - c0025p.f49b) + j5;
                }
            }
            j5 += c0025p.f50c - c0025p.f49b;
            c0025p = c0025p.f53f;
            j8 = j5;
        }
        return -1L;
    }

    /* renamed from: B */
    public final byte[] m78B(long j2) {
        AbstractC0032w.m200a(this.f22b, 0L, j2);
        if (j2 > 2147483647L) {
            throw new IllegalArgumentException("byteCount > Integer.MAX_VALUE: " + j2);
        }
        int i2 = (int) j2;
        byte[] bArr = new byte[i2];
        int i3 = 0;
        while (i3 < i2) {
            int read = read(bArr, i3, i2 - i3);
            if (read == -1) {
                throw new EOFException();
            }
            i3 += read;
        }
        return bArr;
    }

    /* renamed from: C */
    public final String m79C(long j2, Charset charset) {
        AbstractC0032w.m200a(this.f22b, 0L, j2);
        if (charset == null) {
            throw new IllegalArgumentException("charset == null");
        }
        if (j2 > 2147483647L) {
            throw new IllegalArgumentException("byteCount > Integer.MAX_VALUE: " + j2);
        }
        if (j2 == 0) {
            return BuildConfig.FLAVOR;
        }
        C0025p c0025p = this.f21a;
        int i2 = c0025p.f49b;
        if (i2 + j2 > c0025p.f50c) {
            return new String(m78B(j2), charset);
        }
        String str = new String(c0025p.f48a, i2, (int) j2, charset);
        int i3 = (int) (c0025p.f49b + j2);
        c0025p.f49b = i3;
        this.f22b -= j2;
        if (i3 == c0025p.f50c) {
            this.f21a = c0025p.m146a();
            AbstractC0026q.m161L(c0025p);
        }
        return str;
    }

    /* renamed from: D */
    public final String m80D() {
        try {
            return m79C(this.f22b, AbstractC0032w.f75a);
        } catch (EOFException e2) {
            throw new AssertionError(e2);
        }
    }

    /* renamed from: E */
    public final String m81E(long j2) {
        if (j2 > 0) {
            long j3 = j2 - 1;
            if (m115z(j3) == 13) {
                String m79C = m79C(j3, AbstractC0032w.f75a);
                skip(2L);
                return m79C;
            }
        }
        String m79C2 = m79C(j2, AbstractC0032w.f75a);
        skip(1L);
        return m79C2;
    }

    /* JADX WARN: Code restructure failed: missing block: B:34:0x0055, code lost:
    
        if (r19 == false) goto L29;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0057, code lost:
    
        return r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x0058, code lost:
    
        return r11;
     */
    /* renamed from: F */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int m82F(C0022m c0022m, boolean z2) {
        int i2;
        int i3;
        byte[] bArr;
        int i4;
        C0025p c0025p;
        C0025p c0025p2 = this.f21a;
        int i5 = -2;
        if (c0025p2 != null) {
            int i6 = c0025p2.f49b;
            int i7 = c0025p2.f50c;
            int[] iArr = c0022m.f41b;
            byte[] bArr2 = c0025p2.f48a;
            C0025p c0025p3 = c0025p2;
            int i8 = -1;
            int i9 = 0;
            loop0: while (true) {
                int i10 = i9 + 1;
                int i11 = iArr[i9];
                int i12 = i10 + 1;
                int i13 = iArr[i10];
                if (i13 != -1) {
                    i8 = i13;
                }
                if (c0025p3 == null) {
                    break;
                }
                if (i11 >= 0) {
                    int i14 = i6 + 1;
                    int i15 = bArr2[i6] & 255;
                    int i16 = i12 + i11;
                    while (i12 != i16) {
                        if (i15 == iArr[i12]) {
                            i2 = iArr[i12 + i11];
                            if (i14 == i7) {
                                c0025p3 = c0025p3.f53f;
                                i3 = c0025p3.f49b;
                                i7 = c0025p3.f50c;
                                bArr2 = c0025p3.f48a;
                                if (c0025p3 == c0025p2) {
                                    c0025p3 = null;
                                }
                            } else {
                                i3 = i14;
                            }
                        } else {
                            i12++;
                        }
                    }
                    return i8;
                }
                int i17 = (i11 * (-1)) + i12;
                while (true) {
                    int i18 = i6 + 1;
                    int i19 = i12 + 1;
                    if ((bArr2[i6] & 255) != iArr[i12]) {
                        return i8;
                    }
                    boolean z3 = i19 == i17;
                    if (i18 == i7) {
                        C0025p c0025p4 = c0025p3.f53f;
                        i4 = c0025p4.f49b;
                        int i20 = c0025p4.f50c;
                        bArr = c0025p4.f48a;
                        if (c0025p4 != c0025p2) {
                            c0025p = c0025p4;
                            i7 = i20;
                        } else {
                            if (!z3) {
                                break loop0;
                            }
                            i7 = i20;
                            c0025p = null;
                        }
                    } else {
                        C0025p c0025p5 = c0025p3;
                        bArr = bArr2;
                        i4 = i18;
                        c0025p = c0025p5;
                    }
                    if (z3) {
                        i2 = iArr[i19];
                        i3 = i4;
                        bArr2 = bArr;
                        c0025p3 = c0025p;
                        break;
                    }
                    i6 = i4;
                    bArr2 = bArr;
                    i12 = i19;
                    c0025p3 = c0025p;
                }
                if (i2 >= 0) {
                    return i2;
                }
                i9 = -i2;
                i6 = i3;
                i5 = -2;
            }
        } else {
            if (z2) {
                return -2;
            }
            return c0022m.indexOf(C0017h.f24e);
        }
    }

    /* renamed from: G */
    public final C0025p m83G(int i2) {
        if (i2 < 1 || i2 > 8192) {
            throw new IllegalArgumentException();
        }
        C0025p c0025p = this.f21a;
        if (c0025p == null) {
            C0025p m165P = AbstractC0026q.m165P();
            this.f21a = m165P;
            m165P.f54g = m165P;
            m165P.f53f = m165P;
            return m165P;
        }
        C0025p c0025p2 = c0025p.f54g;
        if (c0025p2.f50c + i2 <= 8192 && c0025p2.f52e) {
            return c0025p2;
        }
        C0025p m165P2 = AbstractC0026q.m165P();
        c0025p2.m147b(m165P2);
        return m165P2;
    }

    /* renamed from: H */
    public final void m84H(C0017h c0017h) {
        if (c0017h == null) {
            throw new IllegalArgumentException("byteString == null");
        }
        c0017h.mo129n(this);
    }

    /* renamed from: I */
    public final void m85I(byte[] bArr, int i2, int i3) {
        if (bArr == null) {
            throw new IllegalArgumentException("source == null");
        }
        long j2 = i3;
        AbstractC0032w.m200a(bArr.length, i2, j2);
        int i4 = i3 + i2;
        while (i2 < i4) {
            C0025p m83G = m83G(1);
            int min = Math.min(i4 - i2, 8192 - m83G.f50c);
            System.arraycopy(bArr, i2, m83G.f48a, m83G.f50c, min);
            i2 += min;
            m83G.f50c += min;
        }
        this.f22b += j2;
    }

    /* renamed from: J */
    public final void m86J(int i2) {
        C0025p m83G = m83G(1);
        int i3 = m83G.f50c;
        m83G.f50c = i3 + 1;
        m83G.f48a[i3] = (byte) i2;
        this.f22b++;
    }

    /* renamed from: K */
    public final C0014e m87K(long j2) {
        byte[] bArr;
        if (j2 == 0) {
            m86J(48);
            return this;
        }
        int i2 = 1;
        boolean z2 = false;
        if (j2 < 0) {
            j2 = -j2;
            if (j2 < 0) {
                m91O("-9223372036854775808", 0, 20);
                return this;
            }
            z2 = true;
        }
        if (j2 >= 100000000) {
            i2 = j2 < 1000000000000L ? j2 < 10000000000L ? j2 < 1000000000 ? 9 : 10 : j2 < 100000000000L ? 11 : 12 : j2 < 1000000000000000L ? j2 < 10000000000000L ? 13 : j2 < 100000000000000L ? 14 : 15 : j2 < 100000000000000000L ? j2 < 10000000000000000L ? 16 : 17 : j2 < 1000000000000000000L ? 18 : 19;
        } else if (j2 >= 10000) {
            i2 = j2 < 1000000 ? j2 < 100000 ? 5 : 6 : j2 < 10000000 ? 7 : 8;
        } else if (j2 >= 100) {
            i2 = j2 < 1000 ? 3 : 4;
        } else if (j2 >= 10) {
            i2 = 2;
        }
        if (z2) {
            i2++;
        }
        C0025p m83G = m83G(i2);
        int i3 = m83G.f50c + i2;
        while (true) {
            bArr = m83G.f48a;
            if (j2 == 0) {
                break;
            }
            i3--;
            bArr[i3] = f20c[(int) (j2 % 10)];
            j2 /= 10;
        }
        if (z2) {
            bArr[i3 - 1] = 45;
        }
        m83G.f50c += i2;
        this.f22b += i2;
        return this;
    }

    /* renamed from: L */
    public final C0014e m88L(long j2) {
        if (j2 == 0) {
            m86J(48);
            return this;
        }
        int numberOfTrailingZeros = (Long.numberOfTrailingZeros(Long.highestOneBit(j2)) / 4) + 1;
        C0025p m83G = m83G(numberOfTrailingZeros);
        int i2 = m83G.f50c;
        int i3 = i2 + numberOfTrailingZeros;
        while (true) {
            i3--;
            if (i3 < i2) {
                m83G.f50c += numberOfTrailingZeros;
                this.f22b += numberOfTrailingZeros;
                return this;
            }
            m83G.f48a[i3] = f20c[(int) (15 & j2)];
            j2 >>>= 4;
        }
    }

    /* renamed from: M */
    public final void m89M(int i2) {
        C0025p m83G = m83G(4);
        int i3 = m83G.f50c;
        int i4 = i3 + 1;
        byte[] bArr = m83G.f48a;
        bArr[i3] = (byte) ((i2 >>> 24) & 255);
        int i5 = i4 + 1;
        bArr[i4] = (byte) ((i2 >>> 16) & 255);
        int i6 = i5 + 1;
        bArr[i5] = (byte) ((i2 >>> 8) & 255);
        bArr[i6] = (byte) (i2 & 255);
        m83G.f50c = i6 + 1;
        this.f22b += 4;
    }

    /* renamed from: N */
    public final void m90N(int i2) {
        C0025p m83G = m83G(2);
        int i3 = m83G.f50c;
        int i4 = i3 + 1;
        byte[] bArr = m83G.f48a;
        bArr[i3] = (byte) ((i2 >>> 8) & 255);
        bArr[i4] = (byte) (i2 & 255);
        m83G.f50c = i4 + 1;
        this.f22b += 2;
    }

    /* renamed from: O */
    public final void m91O(String str, int i2, int i3) {
        char charAt;
        int i4;
        if (str == null) {
            throw new IllegalArgumentException("string == null");
        }
        if (i2 < 0) {
            throw new IllegalArgumentException(AbstractC0000a.m11g("beginIndex < 0: ", i2));
        }
        if (i3 < i2) {
            throw new IllegalArgumentException("endIndex < beginIndex: " + i3 + " < " + i2);
        }
        if (i3 > str.length()) {
            StringBuilder m21q = AbstractC0000a.m21q("endIndex > string.length: ", i3, " > ");
            m21q.append(str.length());
            throw new IllegalArgumentException(m21q.toString());
        }
        while (i2 < i3) {
            char charAt2 = str.charAt(i2);
            if (charAt2 < 128) {
                C0025p m83G = m83G(1);
                int i5 = m83G.f50c - i2;
                int min = Math.min(i3, 8192 - i5);
                int i6 = i2 + 1;
                byte[] bArr = m83G.f48a;
                bArr[i2 + i5] = (byte) charAt2;
                while (true) {
                    i2 = i6;
                    if (i2 >= min || (charAt = str.charAt(i2)) >= 128) {
                        break;
                    }
                    i6 = i2 + 1;
                    bArr[i2 + i5] = (byte) charAt;
                }
                int i7 = m83G.f50c;
                int i8 = (i5 + i2) - i7;
                m83G.f50c = i7 + i8;
                this.f22b += i8;
            } else {
                if (charAt2 < 2048) {
                    i4 = (charAt2 >> 6) | 192;
                } else if (charAt2 < 55296 || charAt2 > 57343) {
                    m86J((charAt2 >> '\f') | BERTags.FLAGS);
                    i4 = ((charAt2 >> 6) & 63) | 128;
                } else {
                    int i9 = i2 + 1;
                    char charAt3 = i9 < i3 ? str.charAt(i9) : (char) 0;
                    if (charAt2 > 56319 || charAt3 < 56320 || charAt3 > 57343) {
                        m86J(63);
                        i2 = i9;
                    } else {
                        int i10 = (((charAt2 & 10239) << 10) | (9215 & charAt3)) + 65536;
                        m86J((i10 >> 18) | 240);
                        m86J(((i10 >> 12) & 63) | 128);
                        m86J(((i10 >> 6) & 63) | 128);
                        m86J((i10 & 63) | 128);
                        i2 += 2;
                    }
                }
                m86J(i4);
                m86J((charAt2 & '?') | 128);
                i2++;
            }
        }
    }

    /* renamed from: P */
    public final void m92P(int i2) {
        int i3;
        int i4;
        if (i2 >= 128) {
            if (i2 < 2048) {
                i4 = (i2 >> 6) | 192;
            } else {
                if (i2 < 65536) {
                    if (i2 >= 55296 && i2 <= 57343) {
                        m86J(63);
                        return;
                    }
                    i3 = (i2 >> 12) | BERTags.FLAGS;
                } else {
                    if (i2 > 1114111) {
                        throw new IllegalArgumentException("Unexpected code point: " + Integer.toHexString(i2));
                    }
                    m86J((i2 >> 18) | 240);
                    i3 = ((i2 >> 12) & 63) | 128;
                }
                m86J(i3);
                i4 = ((i2 >> 6) & 63) | 128;
            }
            m86J(i4);
            i2 = (i2 & 63) | 128;
        }
        m86J(i2);
    }

    @Override // a1.InterfaceC0029t
    /* renamed from: a */
    public final C0031v mo68a() {
        return C0031v.f71d;
    }

    @Override // a1.InterfaceC0016g
    /* renamed from: b */
    public final int mo93b(C0022m c0022m) {
        int m82F = m82F(c0022m, false);
        if (m82F == -1) {
            return -1;
        }
        try {
            skip(c0022m.f40a[m82F].mo125j());
            return m82F;
        } catch (EOFException unused) {
            throw new AssertionError();
        }
    }

    @Override // a1.InterfaceC0015f
    /* renamed from: c */
    public final /* bridge */ /* synthetic */ InterfaceC0015f mo94c(byte[] bArr, int i2, int i3) {
        m85I(bArr, i2, i3);
        return this;
    }

    public final Object clone() {
        C0014e c0014e = new C0014e();
        if (this.f22b != 0) {
            C0025p m148c = this.f21a.m148c();
            c0014e.f21a = m148c;
            m148c.f54g = m148c;
            m148c.f53f = m148c;
            C0025p c0025p = this.f21a;
            while (true) {
                c0025p = c0025p.f53f;
                if (c0025p == this.f21a) {
                    break;
                }
                c0014e.f21a.f54g.m147b(c0025p.m148c());
            }
            c0014e.f22b = this.f22b;
        }
        return c0014e;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable, java.nio.channels.Channel, a1.InterfaceC0028s
    public final void close() {
    }

    @Override // a1.InterfaceC0015f
    /* renamed from: d */
    public final long mo95d(InterfaceC0029t interfaceC0029t) {
        if (interfaceC0029t == null) {
            throw new IllegalArgumentException("source == null");
        }
        long j2 = 0;
        while (true) {
            long mo69u = interfaceC0029t.mo69u(this, 8192L);
            if (mo69u == -1) {
                return j2;
            }
            j2 += mo69u;
        }
    }

    @Override // a1.InterfaceC0015f
    /* renamed from: e */
    public final /* bridge */ /* synthetic */ InterfaceC0015f mo96e(long j2) {
        m88L(j2);
        return this;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof C0014e)) {
            return false;
        }
        C0014e c0014e = (C0014e) obj;
        long j2 = this.f22b;
        if (j2 != c0014e.f22b) {
            return false;
        }
        long j3 = 0;
        if (j2 == 0) {
            return true;
        }
        C0025p c0025p = this.f21a;
        C0025p c0025p2 = c0014e.f21a;
        int i2 = c0025p.f49b;
        int i3 = c0025p2.f49b;
        while (j3 < this.f22b) {
            long min = Math.min(c0025p.f50c - i2, c0025p2.f50c - i3);
            int i4 = 0;
            while (i4 < min) {
                int i5 = i2 + 1;
                int i6 = i3 + 1;
                if (c0025p.f48a[i2] != c0025p2.f48a[i3]) {
                    return false;
                }
                i4++;
                i2 = i5;
                i3 = i6;
            }
            if (i2 == c0025p.f50c) {
                c0025p = c0025p.f53f;
                i2 = c0025p.f49b;
            }
            if (i3 == c0025p2.f50c) {
                c0025p2 = c0025p2.f53f;
                i3 = c0025p2.f49b;
            }
            j3 += min;
        }
        return true;
    }

    @Override // a1.InterfaceC0016g
    /* renamed from: f */
    public final C0014e mo97f() {
        return this;
    }

    @Override // a1.InterfaceC0015f, a1.InterfaceC0028s, java.io.Flushable
    public final void flush() {
    }

    @Override // a1.InterfaceC0015f
    /* renamed from: g */
    public final /* bridge */ /* synthetic */ InterfaceC0015f mo98g(C0017h c0017h) {
        m84H(c0017h);
        return this;
    }

    @Override // a1.InterfaceC0016g
    /* renamed from: h */
    public final C0017h mo99h(long j2) {
        return new C0017h(m78B(j2));
    }

    public final int hashCode() {
        C0025p c0025p = this.f21a;
        if (c0025p == null) {
            return 0;
        }
        int i2 = 1;
        do {
            int i3 = c0025p.f50c;
            for (int i4 = c0025p.f49b; i4 < i3; i4++) {
                i2 = (i2 * 31) + c0025p.f48a[i4];
            }
            c0025p = c0025p.f53f;
        } while (c0025p != this.f21a);
        return i2;
    }

    @Override // a1.InterfaceC0028s
    /* renamed from: i */
    public final void mo67i(C0014e c0014e, long j2) {
        C0025p m165P;
        if (c0014e == null) {
            throw new IllegalArgumentException("source == null");
        }
        if (c0014e == this) {
            throw new IllegalArgumentException("source == this");
        }
        AbstractC0032w.m200a(c0014e.f22b, 0L, j2);
        while (j2 > 0) {
            C0025p c0025p = c0014e.f21a;
            int i2 = c0025p.f50c - c0025p.f49b;
            if (j2 < i2) {
                C0025p c0025p2 = this.f21a;
                C0025p c0025p3 = c0025p2 != null ? c0025p2.f54g : null;
                if (c0025p3 != null && c0025p3.f52e) {
                    if ((c0025p3.f50c + j2) - (c0025p3.f51d ? 0 : c0025p3.f49b) <= 8192) {
                        c0025p.m149d(c0025p3, (int) j2);
                        c0014e.f22b -= j2;
                        this.f22b += j2;
                        return;
                    }
                }
                int i3 = (int) j2;
                if (i3 <= 0 || i3 > i2) {
                    throw new IllegalArgumentException();
                }
                if (i3 >= 1024) {
                    m165P = c0025p.m148c();
                } else {
                    m165P = AbstractC0026q.m165P();
                    System.arraycopy(c0025p.f48a, c0025p.f49b, m165P.f48a, 0, i3);
                }
                m165P.f50c = m165P.f49b + i3;
                c0025p.f49b += i3;
                c0025p.f54g.m147b(m165P);
                c0014e.f21a = m165P;
            }
            C0025p c0025p4 = c0014e.f21a;
            long j3 = c0025p4.f50c - c0025p4.f49b;
            c0014e.f21a = c0025p4.m146a();
            C0025p c0025p5 = this.f21a;
            if (c0025p5 == null) {
                this.f21a = c0025p4;
                c0025p4.f54g = c0025p4;
                c0025p4.f53f = c0025p4;
            } else {
                c0025p5.f54g.m147b(c0025p4);
                C0025p c0025p6 = c0025p4.f54g;
                if (c0025p6 == c0025p4) {
                    throw new IllegalStateException();
                }
                if (c0025p6.f52e) {
                    int i4 = c0025p4.f50c - c0025p4.f49b;
                    if (i4 <= (8192 - c0025p6.f50c) + (c0025p6.f51d ? 0 : c0025p6.f49b)) {
                        c0025p4.m149d(c0025p6, i4);
                        c0025p4.m146a();
                        AbstractC0026q.m161L(c0025p4);
                    }
                }
            }
            c0014e.f22b -= j3;
            this.f22b += j3;
            j2 -= j3;
        }
    }

    @Override // java.nio.channels.Channel
    public final boolean isOpen() {
        return true;
    }

    @Override // a1.InterfaceC0015f
    /* renamed from: j */
    public final /* bridge */ /* synthetic */ InterfaceC0015f mo100j(int i2) {
        m90N(i2);
        return this;
    }

    @Override // a1.InterfaceC0015f
    /* renamed from: k */
    public final /* bridge */ /* synthetic */ InterfaceC0015f mo101k(int i2) {
        m89M(i2);
        return this;
    }

    @Override // a1.InterfaceC0016g
    /* renamed from: l */
    public final String mo102l() {
        return mo107q(Long.MAX_VALUE);
    }

    @Override // a1.InterfaceC0016g
    /* renamed from: m */
    public final byte[] mo103m() {
        try {
            return m78B(this.f22b);
        } catch (EOFException e2) {
            throw new AssertionError(e2);
        }
    }

    @Override // a1.InterfaceC0016g
    /* renamed from: n */
    public final boolean mo104n() {
        return this.f22b == 0;
    }

    @Override // a1.InterfaceC0015f
    /* renamed from: o */
    public final /* bridge */ /* synthetic */ InterfaceC0015f mo105o(int i2) {
        m86J(i2);
        return this;
    }

    @Override // a1.InterfaceC0015f
    /* renamed from: p */
    public final InterfaceC0015f mo106p(byte[] bArr) {
        if (bArr == null) {
            throw new IllegalArgumentException("source == null");
        }
        m85I(bArr, 0, bArr.length);
        return this;
    }

    @Override // a1.InterfaceC0016g
    /* renamed from: q */
    public final String mo107q(long j2) {
        if (j2 < 0) {
            throw new IllegalArgumentException("limit < 0: " + j2);
        }
        long j3 = j2 != Long.MAX_VALUE ? j2 + 1 : Long.MAX_VALUE;
        long m77A = m77A((byte) 10, 0L, j3);
        if (m77A != -1) {
            return m81E(m77A);
        }
        if (j3 < this.f22b && m115z(j3 - 1) == 13 && m115z(j3) == 10) {
            return m81E(j3);
        }
        C0014e c0014e = new C0014e();
        m114y(c0014e, 0L, Math.min(32L, this.f22b));
        throw new EOFException("\\n not found: limit=" + Math.min(this.f22b, j2) + " content=" + new C0017h(c0014e.mo103m()).mo122f() + (char) 8230);
    }

    @Override // a1.InterfaceC0016g
    /* renamed from: r */
    public final void mo108r(long j2) {
        if (this.f22b < j2) {
            throw new EOFException();
        }
    }

    @Override // java.nio.channels.ReadableByteChannel
    public final int read(ByteBuffer byteBuffer) {
        C0025p c0025p = this.f21a;
        if (c0025p == null) {
            return -1;
        }
        int min = Math.min(byteBuffer.remaining(), c0025p.f50c - c0025p.f49b);
        byteBuffer.put(c0025p.f48a, c0025p.f49b, min);
        int i2 = c0025p.f49b + min;
        c0025p.f49b = i2;
        this.f22b -= min;
        if (i2 == c0025p.f50c) {
            this.f21a = c0025p.m146a();
            AbstractC0026q.m161L(c0025p);
        }
        return min;
    }

    @Override // a1.InterfaceC0016g
    public final byte readByte() {
        long j2 = this.f22b;
        if (j2 == 0) {
            throw new IllegalStateException("size == 0");
        }
        C0025p c0025p = this.f21a;
        int i2 = c0025p.f49b;
        int i3 = c0025p.f50c;
        int i4 = i2 + 1;
        byte b = c0025p.f48a[i2];
        this.f22b = j2 - 1;
        if (i4 == i3) {
            this.f21a = c0025p.m146a();
            AbstractC0026q.m161L(c0025p);
        } else {
            c0025p.f49b = i4;
        }
        return b;
    }

    @Override // a1.InterfaceC0016g
    public final int readInt() {
        long j2 = this.f22b;
        if (j2 < 4) {
            throw new IllegalStateException("size < 4: " + this.f22b);
        }
        C0025p c0025p = this.f21a;
        int i2 = c0025p.f49b;
        int i3 = c0025p.f50c;
        if (i3 - i2 < 4) {
            return ((readByte() & 255) << 24) | ((readByte() & 255) << 16) | ((readByte() & 255) << 8) | (readByte() & 255);
        }
        int i4 = i2 + 1;
        byte[] bArr = c0025p.f48a;
        int i5 = i4 + 1;
        int i6 = ((bArr[i2] & 255) << 24) | ((bArr[i4] & 255) << 16);
        int i7 = i5 + 1;
        int i8 = i6 | ((bArr[i5] & 255) << 8);
        int i9 = i7 + 1;
        int i10 = i8 | (bArr[i7] & 255);
        this.f22b = j2 - 4;
        if (i9 == i3) {
            this.f21a = c0025p.m146a();
            AbstractC0026q.m161L(c0025p);
        } else {
            c0025p.f49b = i9;
        }
        return i10;
    }

    @Override // a1.InterfaceC0016g
    public final short readShort() {
        long j2 = this.f22b;
        if (j2 < 2) {
            throw new IllegalStateException("size < 2: " + this.f22b);
        }
        C0025p c0025p = this.f21a;
        int i2 = c0025p.f49b;
        int i3 = c0025p.f50c;
        if (i3 - i2 < 2) {
            return (short) (((readByte() & 255) << 8) | (readByte() & 255));
        }
        int i4 = i2 + 1;
        byte[] bArr = c0025p.f48a;
        int i5 = i4 + 1;
        int i6 = ((bArr[i2] & 255) << 8) | (bArr[i4] & 255);
        this.f22b = j2 - 2;
        if (i5 == i3) {
            this.f21a = c0025p.m146a();
            AbstractC0026q.m161L(c0025p);
        } else {
            c0025p.f49b = i5;
        }
        return (short) i6;
    }

    @Override // a1.InterfaceC0015f
    /* renamed from: s */
    public final InterfaceC0015f mo109s(String str) {
        m91O(str, 0, str.length());
        return this;
    }

    @Override // a1.InterfaceC0016g
    public final void skip(long j2) {
        while (j2 > 0) {
            if (this.f21a == null) {
                throw new EOFException();
            }
            int min = (int) Math.min(j2, r0.f50c - r0.f49b);
            long j3 = min;
            this.f22b -= j3;
            j2 -= j3;
            C0025p c0025p = this.f21a;
            int i2 = c0025p.f49b + min;
            c0025p.f49b = i2;
            if (i2 == c0025p.f50c) {
                this.f21a = c0025p.m146a();
                AbstractC0026q.m161L(c0025p);
            }
        }
    }

    @Override // a1.InterfaceC0015f
    /* renamed from: t */
    public final /* bridge */ /* synthetic */ InterfaceC0015f mo110t(long j2) {
        m87K(j2);
        return this;
    }

    public final String toString() {
        long j2 = this.f22b;
        if (j2 <= 2147483647L) {
            int i2 = (int) j2;
            return (i2 == 0 ? C0017h.f24e : new C0027r(this, i2)).toString();
        }
        throw new IllegalArgumentException("size > Integer.MAX_VALUE: " + this.f22b);
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
        long j3 = this.f22b;
        if (j3 == 0) {
            return -1L;
        }
        if (j2 > j3) {
            j2 = j3;
        }
        c0014e.mo67i(this, j2);
        return j2;
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x0082  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0090  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0094 A[EDGE_INSN: B:41:0x0094->B:38:0x0094 BREAK  A[LOOP:0: B:4:0x000b->B:40:?], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:42:0x008c  */
    @Override // a1.InterfaceC0016g
    /* renamed from: v */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final long mo111v() {
        int i2;
        int i3;
        if (this.f22b == 0) {
            throw new IllegalStateException("size == 0");
        }
        int i4 = 0;
        boolean z2 = false;
        long j2 = 0;
        do {
            C0025p c0025p = this.f21a;
            byte[] bArr = c0025p.f48a;
            int i5 = c0025p.f49b;
            int i6 = c0025p.f50c;
            while (i5 < i6) {
                byte b = bArr[i5];
                if (b < 48 || b > 57) {
                    if (b >= 97 && b <= 102) {
                        i2 = b - 97;
                    } else if (b >= 65 && b <= 70) {
                        i2 = b - 65;
                    } else {
                        if (i4 == 0) {
                            throw new NumberFormatException("Expected leading [0-9a-fA-F] character but was 0x" + Integer.toHexString(b));
                        }
                        z2 = true;
                        if (i5 != i6) {
                            this.f21a = c0025p.m146a();
                            AbstractC0026q.m161L(c0025p);
                        } else {
                            c0025p.f49b = i5;
                        }
                        if (!z2) {
                            break;
                        }
                    }
                    i3 = i2 + 10;
                } else {
                    i3 = b - 48;
                }
                if (((-1152921504606846976L) & j2) != 0) {
                    C0014e c0014e = new C0014e();
                    c0014e.m88L(j2);
                    c0014e.m86J(b);
                    throw new NumberFormatException("Number too large: ".concat(c0014e.m80D()));
                }
                j2 = (j2 << 4) | i3;
                i5++;
                i4++;
            }
            if (i5 != i6) {
            }
            if (!z2) {
            }
        } while (this.f21a != null);
        this.f22b -= i4;
        return j2;
    }

    @Override // a1.InterfaceC0016g
    /* renamed from: w */
    public final String mo112w(Charset charset) {
        try {
            return m79C(this.f22b, charset);
        } catch (EOFException e2) {
            throw new AssertionError(e2);
        }
    }

    @Override // java.nio.channels.WritableByteChannel
    public final int write(ByteBuffer byteBuffer) {
        if (byteBuffer == null) {
            throw new IllegalArgumentException("source == null");
        }
        int remaining = byteBuffer.remaining();
        int i2 = remaining;
        while (i2 > 0) {
            C0025p m83G = m83G(1);
            int min = Math.min(i2, 8192 - m83G.f50c);
            byteBuffer.get(m83G.f48a, m83G.f50c, min);
            i2 -= min;
            m83G.f50c += min;
        }
        this.f22b += remaining;
        return remaining;
    }

    /* renamed from: x */
    public final void m113x() {
        try {
            skip(this.f22b);
        } catch (EOFException e2) {
            throw new AssertionError(e2);
        }
    }

    /* renamed from: y */
    public final void m114y(C0014e c0014e, long j2, long j3) {
        if (c0014e == null) {
            throw new IllegalArgumentException("out == null");
        }
        AbstractC0032w.m200a(this.f22b, j2, j3);
        if (j3 == 0) {
            return;
        }
        c0014e.f22b += j3;
        C0025p c0025p = this.f21a;
        while (true) {
            long j4 = c0025p.f50c - c0025p.f49b;
            if (j2 < j4) {
                break;
            }
            j2 -= j4;
            c0025p = c0025p.f53f;
        }
        while (j3 > 0) {
            C0025p m148c = c0025p.m148c();
            int i2 = (int) (m148c.f49b + j2);
            m148c.f49b = i2;
            m148c.f50c = Math.min(i2 + ((int) j3), m148c.f50c);
            C0025p c0025p2 = c0014e.f21a;
            if (c0025p2 == null) {
                m148c.f54g = m148c;
                m148c.f53f = m148c;
                c0014e.f21a = m148c;
            } else {
                c0025p2.f54g.m147b(m148c);
            }
            j3 -= m148c.f50c - m148c.f49b;
            c0025p = c0025p.f53f;
            j2 = 0;
        }
    }

    /* renamed from: z */
    public final byte m115z(long j2) {
        int i2;
        AbstractC0032w.m200a(this.f22b, j2, 1L);
        long j3 = this.f22b;
        if (j3 - j2 <= j2) {
            long j4 = j2 - j3;
            C0025p c0025p = this.f21a;
            do {
                c0025p = c0025p.f54g;
                int i3 = c0025p.f50c;
                i2 = c0025p.f49b;
                j4 += i3 - i2;
            } while (j4 < 0);
            return c0025p.f48a[i2 + ((int) j4)];
        }
        C0025p c0025p2 = this.f21a;
        while (true) {
            int i4 = c0025p2.f50c;
            int i5 = c0025p2.f49b;
            long j5 = i4 - i5;
            if (j2 < j5) {
                return c0025p2.f48a[i5 + ((int) j2)];
            }
            j2 -= j5;
            c0025p2 = c0025p2.f53f;
        }
    }

    public final int read(byte[] bArr, int i2, int i3) {
        AbstractC0032w.m200a(bArr.length, i2, i3);
        C0025p c0025p = this.f21a;
        if (c0025p == null) {
            return -1;
        }
        int min = Math.min(i3, c0025p.f50c - c0025p.f49b);
        System.arraycopy(c0025p.f48a, c0025p.f49b, bArr, i2, min);
        int i4 = c0025p.f49b + min;
        c0025p.f49b = i4;
        this.f22b -= min;
        if (i4 == c0025p.f50c) {
            this.f21a = c0025p.m146a();
            AbstractC0026q.m161L(c0025p);
        }
        return min;
    }
}

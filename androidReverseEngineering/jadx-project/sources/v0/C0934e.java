package v0;

import a1.C0014e;
import a1.C0017h;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import org.bouncycastle.asn1.eac.CertificateBody;

/* renamed from: v0.e */
/* loaded from: classes.dex */
public final class C0934e {

    /* renamed from: a */
    public final C0014e f2153a;

    /* renamed from: c */
    public boolean f2155c;

    /* renamed from: b */
    public int f2154b = Integer.MAX_VALUE;

    /* renamed from: e */
    public C0932c[] f2157e = new C0932c[8];

    /* renamed from: f */
    public int f2158f = 7;

    /* renamed from: g */
    public int f2159g = 0;

    /* renamed from: h */
    public int f2160h = 0;

    /* renamed from: d */
    public int f2156d = 4096;

    public C0934e(C0014e c0014e) {
        this.f2153a = c0014e;
    }

    /* renamed from: a */
    public final void m1400a(int i2) {
        int i3;
        if (i2 > 0) {
            int length = this.f2157e.length - 1;
            int i4 = 0;
            while (true) {
                i3 = this.f2158f;
                if (length < i3 || i2 <= 0) {
                    break;
                }
                int i5 = this.f2157e[length].f2142c;
                i2 -= i5;
                this.f2160h -= i5;
                this.f2159g--;
                i4++;
                length--;
            }
            C0932c[] c0932cArr = this.f2157e;
            int i6 = i3 + 1;
            System.arraycopy(c0932cArr, i6, c0932cArr, i6 + i4, this.f2159g);
            C0932c[] c0932cArr2 = this.f2157e;
            int i7 = this.f2158f + 1;
            Arrays.fill(c0932cArr2, i7, i7 + i4, (Object) null);
            this.f2158f += i4;
        }
    }

    /* renamed from: b */
    public final void m1401b(C0932c c0932c) {
        int i2 = this.f2156d;
        int i3 = c0932c.f2142c;
        if (i3 > i2) {
            Arrays.fill(this.f2157e, (Object) null);
            this.f2158f = this.f2157e.length - 1;
            this.f2159g = 0;
            this.f2160h = 0;
            return;
        }
        m1400a((this.f2160h + i3) - i2);
        int i4 = this.f2159g + 1;
        C0932c[] c0932cArr = this.f2157e;
        if (i4 > c0932cArr.length) {
            C0932c[] c0932cArr2 = new C0932c[c0932cArr.length * 2];
            System.arraycopy(c0932cArr, 0, c0932cArr2, c0932cArr.length, c0932cArr.length);
            this.f2158f = this.f2157e.length - 1;
            this.f2157e = c0932cArr2;
        }
        int i5 = this.f2158f;
        this.f2158f = i5 - 1;
        this.f2157e[i5] = c0932c;
        this.f2159g++;
        this.f2160h += i3;
    }

    /* renamed from: c */
    public final void m1402c(C0017h c0017h) {
        b0.f2132d.getClass();
        long j2 = 0;
        long j3 = 0;
        for (int i2 = 0; i2 < c0017h.mo125j(); i2++) {
            j3 += b0.f2131c[c0017h.mo121e(i2) & 255];
        }
        if (((int) ((j3 + 7) >> 3)) < c0017h.mo125j()) {
            C0014e c0014e = new C0014e();
            b0.f2132d.getClass();
            int i3 = 0;
            for (int i4 = 0; i4 < c0017h.mo125j(); i4++) {
                int mo121e = c0017h.mo121e(i4) & 255;
                int i5 = b0.f2130b[mo121e];
                byte b = b0.f2131c[mo121e];
                j2 = (j2 << b) | i5;
                i3 += b;
                while (i3 >= 8) {
                    i3 -= 8;
                    c0014e.m86J((int) (j2 >> i3));
                }
            }
            if (i3 > 0) {
                c0014e.m86J((int) ((j2 << (8 - i3)) | (255 >>> i3)));
            }
            byte[] mo103m = c0014e.mo103m();
            c0017h = new C0017h(mo103m);
            m1404e(mo103m.length, CertificateBody.profileType, 128);
        } else {
            m1404e(c0017h.mo125j(), CertificateBody.profileType, 0);
        }
        this.f2153a.m84H(c0017h);
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x006c  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00a4  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00ac  */
    /* renamed from: d */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void m1403d(ArrayList arrayList) {
        int i2;
        int i3;
        if (this.f2155c) {
            int i4 = this.f2154b;
            if (i4 < this.f2156d) {
                m1404e(i4, 31, 32);
            }
            this.f2155c = false;
            this.f2154b = Integer.MAX_VALUE;
            m1404e(this.f2156d, 31, 32);
        }
        int size = arrayList.size();
        for (int i5 = 0; i5 < size; i5++) {
            C0932c c0932c = (C0932c) arrayList.get(i5);
            C0017h mo127l = c0932c.f2140a.mo127l();
            Integer num = (Integer) AbstractC0935f.f2162b.get(mo127l);
            C0017h c0017h = c0932c.f2141b;
            if (num != null) {
                i2 = num.intValue() + 1;
                if (i2 > 1 && i2 < 8) {
                    C0932c[] c0932cArr = AbstractC0935f.f2161a;
                    if (!Objects.equals(c0932cArr[i2 - 1].f2141b, c0017h)) {
                        if (Objects.equals(c0932cArr[i2].f2141b, c0017h)) {
                            i3 = i2 + 1;
                            if (i3 == -1) {
                                int i6 = this.f2158f + 1;
                                int length = this.f2157e.length;
                                while (true) {
                                    if (i6 >= length) {
                                        break;
                                    }
                                    if (Objects.equals(this.f2157e[i6].f2140a, mo127l)) {
                                        if (Objects.equals(this.f2157e[i6].f2141b, c0017h)) {
                                            i3 = AbstractC0935f.f2161a.length + (i6 - this.f2158f);
                                            break;
                                        } else if (i2 == -1) {
                                            i2 = (i6 - this.f2158f) + AbstractC0935f.f2161a.length;
                                        }
                                    }
                                    i6++;
                                }
                            }
                            if (i3 != -1) {
                                m1404e(i3, CertificateBody.profileType, 128);
                            } else {
                                if (i2 == -1) {
                                    this.f2153a.m86J(64);
                                    m1402c(mo127l);
                                } else {
                                    C0017h c0017h2 = C0932c.f2134d;
                                    mo127l.getClass();
                                    if (!mo127l.mo124i(c0017h2, c0017h2.mo125j()) || C0932c.f2139i.equals(mo127l)) {
                                        m1404e(i2, 63, 64);
                                    } else {
                                        m1404e(i2, 15, 0);
                                        m1402c(c0017h);
                                    }
                                }
                                m1402c(c0017h);
                                m1401b(c0932c);
                            }
                        }
                    }
                }
                i3 = -1;
                if (i3 == -1) {
                }
                if (i3 != -1) {
                }
            } else {
                i2 = -1;
            }
            i3 = i2;
            if (i3 == -1) {
            }
            if (i3 != -1) {
            }
        }
    }

    /* renamed from: e */
    public final void m1404e(int i2, int i3, int i4) {
        C0014e c0014e = this.f2153a;
        if (i2 < i3) {
            c0014e.m86J(i2 | i4);
            return;
        }
        c0014e.m86J(i4 | i3);
        int i5 = i2 - i3;
        while (i5 >= 128) {
            c0014e.m86J(128 | (i5 & CertificateBody.profileType));
            i5 >>>= 7;
        }
        c0014e.m86J(i5);
    }
}

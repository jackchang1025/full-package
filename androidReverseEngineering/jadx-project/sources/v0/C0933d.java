package v0;

import a1.AbstractC0021l;
import a1.C0017h;
import a1.C0024o;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;
import org.bouncycastle.asn1.eac.CertificateBody;

/* renamed from: v0.d */
/* loaded from: classes.dex */
public final class C0933d {

    /* renamed from: b */
    public final C0024o f2145b;

    /* renamed from: a */
    public final ArrayList f2144a = new ArrayList();

    /* renamed from: e */
    public C0932c[] f2148e = new C0932c[8];

    /* renamed from: f */
    public int f2149f = 7;

    /* renamed from: g */
    public int f2150g = 0;

    /* renamed from: h */
    public int f2151h = 0;

    /* renamed from: c */
    public final int f2146c = 4096;

    /* renamed from: d */
    public int f2147d = 4096;

    public C0933d(C0950u c0950u) {
        Logger logger = AbstractC0021l.f38a;
        this.f2145b = new C0024o(c0950u);
    }

    /* renamed from: a */
    public final int m1395a(int i2) {
        int i3;
        int i4 = 0;
        if (i2 > 0) {
            int length = this.f2148e.length;
            while (true) {
                length--;
                i3 = this.f2149f;
                if (length < i3 || i2 <= 0) {
                    break;
                }
                int i5 = this.f2148e[length].f2142c;
                i2 -= i5;
                this.f2151h -= i5;
                this.f2150g--;
                i4++;
            }
            C0932c[] c0932cArr = this.f2148e;
            System.arraycopy(c0932cArr, i3 + 1, c0932cArr, i3 + 1 + i4, this.f2150g);
            this.f2149f += i4;
        }
        return i4;
    }

    /* renamed from: b */
    public final C0017h m1396b(int i2) {
        C0932c c0932c;
        if (!(i2 >= 0 && i2 <= AbstractC0935f.f2161a.length - 1)) {
            int length = this.f2149f + 1 + (i2 - AbstractC0935f.f2161a.length);
            if (length >= 0) {
                C0932c[] c0932cArr = this.f2148e;
                if (length < c0932cArr.length) {
                    c0932c = c0932cArr[length];
                }
            }
            throw new IOException("Header index too large " + (i2 + 1));
        }
        c0932c = AbstractC0935f.f2161a[i2];
        return c0932c.f2140a;
    }

    /* renamed from: c */
    public final void m1397c(C0932c c0932c) {
        this.f2144a.add(c0932c);
        int i2 = this.f2147d;
        int i3 = c0932c.f2142c;
        if (i3 > i2) {
            Arrays.fill(this.f2148e, (Object) null);
            this.f2149f = this.f2148e.length - 1;
            this.f2150g = 0;
            this.f2151h = 0;
            return;
        }
        m1395a((this.f2151h + i3) - i2);
        int i4 = this.f2150g + 1;
        C0932c[] c0932cArr = this.f2148e;
        if (i4 > c0932cArr.length) {
            C0932c[] c0932cArr2 = new C0932c[c0932cArr.length * 2];
            System.arraycopy(c0932cArr, 0, c0932cArr2, c0932cArr.length, c0932cArr.length);
            this.f2149f = this.f2148e.length - 1;
            this.f2148e = c0932cArr2;
        }
        int i5 = this.f2149f;
        this.f2149f = i5 - 1;
        this.f2148e[i5] = c0932c;
        this.f2150g++;
        this.f2151h += i3;
    }

    /* renamed from: d */
    public final C0017h m1398d() {
        int i2;
        C0024o c0024o = this.f2145b;
        int readByte = c0024o.readByte() & 255;
        boolean z2 = (readByte & 128) == 128;
        int m1399e = m1399e(readByte, CertificateBody.profileType);
        if (!z2) {
            return c0024o.mo99h(m1399e);
        }
        b0 b0Var = b0.f2132d;
        long j2 = m1399e;
        c0024o.mo108r(j2);
        byte[] m78B = c0024o.f45a.m78B(j2);
        b0Var.getClass();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        a0 a0Var = b0Var.f2133a;
        a0 a0Var2 = a0Var;
        int i3 = 0;
        int i4 = 0;
        for (byte b : m78B) {
            i3 = (i3 << 8) | (b & 255);
            i4 += 8;
            while (i4 >= 8) {
                int i5 = i4 - 8;
                a0Var2 = ((a0[]) a0Var2.f2121c)[(i3 >>> i5) & 255];
                if (((a0[]) a0Var2.f2121c) == null) {
                    byteArrayOutputStream.write(a0Var2.f2119a);
                    i4 -= a0Var2.f2120b;
                    a0Var2 = a0Var;
                } else {
                    i4 = i5;
                }
            }
        }
        while (i4 > 0) {
            a0 a0Var3 = ((a0[]) a0Var2.f2121c)[(i3 << (8 - i4)) & 255];
            if (((a0[]) a0Var3.f2121c) != null || (i2 = a0Var3.f2120b) > i4) {
                break;
            }
            byteArrayOutputStream.write(a0Var3.f2119a);
            i4 -= i2;
            a0Var2 = a0Var;
        }
        return C0017h.m119g(byteArrayOutputStream.toByteArray());
    }

    /* renamed from: e */
    public final int m1399e(int i2, int i3) {
        int i4 = i2 & i3;
        if (i4 < i3) {
            return i4;
        }
        int i5 = 0;
        while (true) {
            int readByte = this.f2145b.readByte() & 255;
            if ((readByte & 128) == 0) {
                return i3 + (readByte << i5);
            }
            i3 += (readByte & CertificateBody.profileType) << i5;
            i5 += 7;
        }
    }
}

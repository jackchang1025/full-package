package y0;

import a1.AbstractC0021l;
import a1.C0011b;
import a1.C0019j;
import a1.C0024o;
import a1.C0031v;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;

/* renamed from: y0.a */
/* loaded from: classes.dex */
public final class C0977a {

    /* renamed from: e */
    public static final byte[] f2315e = {42};

    /* renamed from: f */
    public static final String[] f2316f = new String[0];

    /* renamed from: g */
    public static final String[] f2317g = {"*"};

    /* renamed from: h */
    public static final C0977a f2318h = new C0977a();

    /* renamed from: a */
    public final AtomicBoolean f2319a = new AtomicBoolean(false);

    /* renamed from: b */
    public final CountDownLatch f2320b = new CountDownLatch(1);

    /* renamed from: c */
    public byte[] f2321c;

    /* renamed from: d */
    public byte[] f2322d;

    /* renamed from: a */
    public static String m1468a(byte[] bArr, byte[][] bArr2, int i2) {
        int i3;
        boolean z2;
        int i4;
        int i5;
        int length = bArr.length;
        int i6 = 0;
        while (i6 < length) {
            int i7 = (i6 + length) / 2;
            while (i7 > -1 && bArr[i7] != 10) {
                i7--;
            }
            int i8 = i7 + 1;
            int i9 = 1;
            while (true) {
                i3 = i8 + i9;
                if (bArr[i3] == 10) {
                    break;
                }
                i9++;
            }
            int i10 = i3 - i8;
            int i11 = i2;
            boolean z3 = false;
            int i12 = 0;
            int i13 = 0;
            while (true) {
                if (z3) {
                    i4 = 46;
                    z2 = false;
                } else {
                    z2 = z3;
                    i4 = bArr2[i11][i12] & 255;
                }
                i5 = i4 - (bArr[i8 + i13] & 255);
                if (i5 == 0) {
                    i13++;
                    i12++;
                    if (i13 == i10) {
                        break;
                    }
                    if (bArr2[i11].length != i12) {
                        z3 = z2;
                    } else {
                        if (i11 == bArr2.length - 1) {
                            break;
                        }
                        i11++;
                        i12 = -1;
                        z3 = true;
                    }
                } else {
                    break;
                }
            }
            if (i5 >= 0) {
                if (i5 <= 0) {
                    int i14 = i10 - i13;
                    int length2 = bArr2[i11].length - i12;
                    while (true) {
                        i11++;
                        if (i11 >= bArr2.length) {
                            break;
                        }
                        length2 += bArr2[i11].length;
                    }
                    if (length2 >= i14) {
                        if (length2 <= i14) {
                            return new String(bArr, i8, i10, StandardCharsets.UTF_8);
                        }
                    }
                }
                i6 = i3 + 1;
            }
            length = i8 - 1;
        }
        return null;
    }

    /* renamed from: b */
    public final void m1469b() {
        InputStream resourceAsStream = C0977a.class.getResourceAsStream("publicsuffixes.gz");
        if (resourceAsStream == null) {
            return;
        }
        Logger logger = AbstractC0021l.f38a;
        C0024o c0024o = new C0024o(new C0019j(new C0011b(new C0031v(), resourceAsStream)));
        try {
            byte[] bArr = new byte[c0024o.readInt()];
            c0024o.m144y(bArr);
            byte[] bArr2 = new byte[c0024o.readInt()];
            c0024o.m144y(bArr2);
            c0024o.close();
            synchronized (this) {
                this.f2321c = bArr;
                this.f2322d = bArr2;
            }
            this.f2320b.countDown();
        } catch (Throwable th) {
            try {
                c0024o.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }
}

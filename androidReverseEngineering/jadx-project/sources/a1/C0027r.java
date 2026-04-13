package a1;

import java.nio.charset.Charset;
import java.util.Arrays;

/* renamed from: a1.r */
/* loaded from: classes.dex */
public final class C0027r extends C0017h {

    /* renamed from: f */
    public final transient byte[][] f69f;

    /* renamed from: g */
    public final transient int[] f70g;

    public C0027r(C0014e c0014e, int i2) {
        super(null);
        AbstractC0032w.m200a(c0014e.f22b, 0L, i2);
        C0025p c0025p = c0014e.f21a;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        while (i4 < i2) {
            int i6 = c0025p.f50c;
            int i7 = c0025p.f49b;
            if (i6 == i7) {
                throw new AssertionError("s.limit == s.pos");
            }
            i4 += i6 - i7;
            i5++;
            c0025p = c0025p.f53f;
        }
        this.f69f = new byte[i5][];
        this.f70g = new int[i5 * 2];
        C0025p c0025p2 = c0014e.f21a;
        int i8 = 0;
        while (i3 < i2) {
            byte[][] bArr = this.f69f;
            bArr[i8] = c0025p2.f48a;
            int i9 = c0025p2.f50c;
            int i10 = c0025p2.f49b;
            int i11 = (i9 - i10) + i3;
            i3 = i11 > i2 ? i2 : i11;
            int[] iArr = this.f70g;
            iArr[i8] = i3;
            iArr[bArr.length + i8] = i10;
            c0025p2.f51d = true;
            i8++;
            c0025p2 = c0025p2.f53f;
        }
    }

    @Override // a1.C0017h
    /* renamed from: a */
    public final String mo120a() {
        return m199p().mo120a();
    }

    @Override // a1.C0017h
    /* renamed from: e */
    public final byte mo121e(int i2) {
        byte[][] bArr = this.f69f;
        int length = bArr.length - 1;
        int[] iArr = this.f70g;
        AbstractC0032w.m200a(iArr[length], i2, 1L);
        int m198o = m198o(i2);
        return bArr[m198o][(i2 - (m198o == 0 ? 0 : iArr[m198o - 1])) + iArr[bArr.length + m198o]];
    }

    @Override // a1.C0017h
    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof C0017h) {
            C0017h c0017h = (C0017h) obj;
            if (c0017h.mo125j() == mo125j() && mo124i(c0017h, mo125j())) {
                return true;
            }
        }
        return false;
    }

    @Override // a1.C0017h
    /* renamed from: f */
    public final String mo122f() {
        return m199p().mo122f();
    }

    @Override // a1.C0017h
    /* renamed from: h */
    public final boolean mo123h(int i2, int i3, byte[] bArr, int i4) {
        if (i2 < 0 || i2 > mo125j() - i4 || i3 < 0 || i3 > bArr.length - i4) {
            return false;
        }
        int m198o = m198o(i2);
        while (true) {
            boolean z2 = true;
            if (i4 <= 0) {
                return true;
            }
            int[] iArr = this.f70g;
            int i5 = m198o == 0 ? 0 : iArr[m198o - 1];
            int min = Math.min(i4, ((iArr[m198o] - i5) + i5) - i2);
            byte[][] bArr2 = this.f69f;
            int i6 = (i2 - i5) + iArr[bArr2.length + m198o];
            byte[] bArr3 = bArr2[m198o];
            Charset charset = AbstractC0032w.f75a;
            int i7 = 0;
            while (true) {
                if (i7 >= min) {
                    break;
                }
                if (bArr3[i7 + i6] != bArr[i7 + i3]) {
                    z2 = false;
                    break;
                }
                i7++;
            }
            if (!z2) {
                return false;
            }
            i2 += min;
            i3 += min;
            i4 -= min;
            m198o++;
        }
    }

    @Override // a1.C0017h
    public final int hashCode() {
        int i2 = this.f26b;
        if (i2 != 0) {
            return i2;
        }
        byte[][] bArr = this.f69f;
        int length = bArr.length;
        int i3 = 0;
        int i4 = 1;
        int i5 = 0;
        while (i3 < length) {
            byte[] bArr2 = bArr[i3];
            int[] iArr = this.f70g;
            int i6 = iArr[length + i3];
            int i7 = iArr[i3];
            int i8 = (i7 - i5) + i6;
            while (i6 < i8) {
                i4 = (i4 * 31) + bArr2[i6];
                i6++;
            }
            i3++;
            i5 = i7;
        }
        this.f26b = i4;
        return i4;
    }

    @Override // a1.C0017h
    /* renamed from: i */
    public final boolean mo124i(C0017h c0017h, int i2) {
        if (mo125j() - i2 < 0) {
            return false;
        }
        int m198o = m198o(0);
        int i3 = 0;
        int i4 = 0;
        while (i2 > 0) {
            int[] iArr = this.f70g;
            int i5 = m198o == 0 ? 0 : iArr[m198o - 1];
            int min = Math.min(i2, ((iArr[m198o] - i5) + i5) - i3);
            byte[][] bArr = this.f69f;
            if (!c0017h.mo123h(i4, (i3 - i5) + iArr[bArr.length + m198o], bArr[m198o], min)) {
                return false;
            }
            i3 += min;
            i4 += min;
            i2 -= min;
            m198o++;
        }
        return true;
    }

    @Override // a1.C0017h
    /* renamed from: j */
    public final int mo125j() {
        return this.f70g[this.f69f.length - 1];
    }

    @Override // a1.C0017h
    /* renamed from: k */
    public final C0017h mo126k() {
        return m199p().mo126k();
    }

    @Override // a1.C0017h
    /* renamed from: l */
    public final C0017h mo127l() {
        return m199p().mo127l();
    }

    @Override // a1.C0017h
    /* renamed from: m */
    public final String mo128m() {
        return m199p().mo128m();
    }

    @Override // a1.C0017h
    /* renamed from: n */
    public final void mo129n(C0014e c0014e) {
        byte[][] bArr = this.f69f;
        int length = bArr.length;
        int i2 = 0;
        int i3 = 0;
        while (i2 < length) {
            int[] iArr = this.f70g;
            int i4 = iArr[length + i2];
            int i5 = iArr[i2];
            C0025p c0025p = new C0025p(bArr[i2], i4, (i4 + i5) - i3);
            C0025p c0025p2 = c0014e.f21a;
            if (c0025p2 == null) {
                c0025p.f54g = c0025p;
                c0025p.f53f = c0025p;
                c0014e.f21a = c0025p;
            } else {
                c0025p2.f54g.m147b(c0025p);
            }
            i2++;
            i3 = i5;
        }
        c0014e.f22b += i3;
    }

    /* renamed from: o */
    public final int m198o(int i2) {
        int binarySearch = Arrays.binarySearch(this.f70g, 0, this.f69f.length, i2 + 1);
        return binarySearch >= 0 ? binarySearch : ~binarySearch;
    }

    /* renamed from: p */
    public final C0017h m199p() {
        byte[][] bArr = this.f69f;
        int length = bArr.length - 1;
        int[] iArr = this.f70g;
        byte[] bArr2 = new byte[iArr[length]];
        int length2 = bArr.length;
        int i2 = 0;
        int i3 = 0;
        while (i2 < length2) {
            int i4 = iArr[length2 + i2];
            int i5 = iArr[i2];
            System.arraycopy(bArr[i2], i4, bArr2, i3, i5 - i3);
            i2++;
            i3 = i5;
        }
        return new C0017h(bArr2);
    }

    @Override // a1.C0017h
    public final String toString() {
        return m199p().toString();
    }
}

package a1;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Arrays;
import p000a.AbstractC0000a;

/* renamed from: a1.h */
/* loaded from: classes.dex */
public class C0017h implements Serializable, Comparable {

    /* renamed from: d */
    public static final char[] f23d = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /* renamed from: e */
    public static final C0017h f24e = m119g(new byte[0]);

    /* renamed from: a */
    public final byte[] f25a;

    /* renamed from: b */
    public transient int f26b;

    /* renamed from: c */
    public transient String f27c;

    public C0017h(byte[] bArr) {
        this.f25a = bArr;
    }

    /* renamed from: b */
    public static C0017h m116b(String str) {
        if (str.length() % 2 != 0) {
            throw new IllegalArgumentException("Unexpected hex string: ".concat(str));
        }
        int length = str.length() / 2;
        byte[] bArr = new byte[length];
        for (int i2 = 0; i2 < length; i2++) {
            int i3 = i2 * 2;
            bArr[i2] = (byte) (m117c(str.charAt(i3 + 1)) + (m117c(str.charAt(i3)) << 4));
        }
        return m119g(bArr);
    }

    /* renamed from: c */
    public static int m117c(char c) {
        if (c >= '0' && c <= '9') {
            return c - '0';
        }
        char c2 = 'a';
        if (c < 'a' || c > 'f') {
            c2 = 'A';
            if (c < 'A' || c > 'F') {
                throw new IllegalArgumentException("Unexpected hex digit: " + c);
            }
        }
        return (c - c2) + 10;
    }

    /* renamed from: d */
    public static C0017h m118d(String str) {
        if (str == null) {
            throw new IllegalArgumentException("s == null");
        }
        C0017h c0017h = new C0017h(str.getBytes(AbstractC0032w.f75a));
        c0017h.f27c = str;
        return c0017h;
    }

    /* renamed from: g */
    public static C0017h m119g(byte... bArr) {
        if (bArr != null) {
            return new C0017h((byte[]) bArr.clone());
        }
        throw new IllegalArgumentException("data == null");
    }

    /* renamed from: a */
    public String mo120a() {
        byte[] bArr = AbstractC0026q.f62h;
        byte[] bArr2 = this.f25a;
        byte[] bArr3 = new byte[((bArr2.length + 2) / 3) * 4];
        int length = bArr2.length - (bArr2.length % 3);
        int i2 = 0;
        for (int i3 = 0; i3 < length; i3 += 3) {
            int i4 = i2 + 1;
            bArr3[i2] = bArr[(bArr2[i3] & 255) >> 2];
            int i5 = i4 + 1;
            int i6 = i3 + 1;
            bArr3[i4] = bArr[((bArr2[i3] & 3) << 4) | ((bArr2[i6] & 255) >> 4)];
            int i7 = i5 + 1;
            int i8 = (bArr2[i6] & 15) << 2;
            int i9 = i3 + 2;
            bArr3[i5] = bArr[i8 | ((bArr2[i9] & 255) >> 6)];
            i2 = i7 + 1;
            bArr3[i7] = bArr[bArr2[i9] & 63];
        }
        int length2 = bArr2.length % 3;
        if (length2 == 1) {
            int i10 = i2 + 1;
            bArr3[i2] = bArr[(bArr2[length] & 255) >> 2];
            int i11 = i10 + 1;
            bArr3[i10] = bArr[(bArr2[length] & 3) << 4];
            bArr3[i11] = 61;
            bArr3[i11 + 1] = 61;
        } else if (length2 == 2) {
            int i12 = i2 + 1;
            bArr3[i2] = bArr[(bArr2[length] & 255) >> 2];
            int i13 = i12 + 1;
            int i14 = (bArr2[length] & 3) << 4;
            int i15 = length + 1;
            bArr3[i12] = bArr[((bArr2[i15] & 255) >> 4) | i14];
            bArr3[i13] = bArr[(bArr2[i15] & 15) << 2];
            bArr3[i13 + 1] = 61;
        }
        try {
            return new String(bArr3, "US-ASCII");
        } catch (UnsupportedEncodingException e2) {
            throw new AssertionError(e2);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0031, code lost:
    
        return -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:?, code lost:
    
        return 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x002e, code lost:
    
        if (r0 < r1) goto L9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x0025, code lost:
    
        if (r7 < r8) goto L9;
     */
    @Override // java.lang.Comparable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int compareTo(Object obj) {
        C0017h c0017h = (C0017h) obj;
        int mo125j = mo125j();
        int mo125j2 = c0017h.mo125j();
        int min = Math.min(mo125j, mo125j2);
        for (int i2 = 0; i2 < min; i2++) {
            int mo121e = mo121e(i2) & 255;
            int mo121e2 = c0017h.mo121e(i2) & 255;
            if (mo121e == mo121e2) {
            }
        }
        if (mo125j == mo125j2) {
            return 0;
        }
    }

    /* renamed from: e */
    public byte mo121e(int i2) {
        return this.f25a[i2];
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof C0017h) {
            C0017h c0017h = (C0017h) obj;
            int mo125j = c0017h.mo125j();
            byte[] bArr = this.f25a;
            if (mo125j == bArr.length && c0017h.mo123h(0, 0, bArr, bArr.length)) {
                return true;
            }
        }
        return false;
    }

    /* renamed from: f */
    public String mo122f() {
        byte[] bArr = this.f25a;
        char[] cArr = new char[bArr.length * 2];
        int i2 = 0;
        for (byte b : bArr) {
            int i3 = i2 + 1;
            char[] cArr2 = f23d;
            cArr[i2] = cArr2[(b >> 4) & 15];
            i2 = i3 + 1;
            cArr[i3] = cArr2[b & 15];
        }
        return new String(cArr);
    }

    /* renamed from: h */
    public boolean mo123h(int i2, int i3, byte[] bArr, int i4) {
        boolean z2;
        if (i2 < 0) {
            return false;
        }
        byte[] bArr2 = this.f25a;
        if (i2 > bArr2.length - i4 || i3 < 0 || i3 > bArr.length - i4) {
            return false;
        }
        Charset charset = AbstractC0032w.f75a;
        int i5 = 0;
        while (true) {
            if (i5 >= i4) {
                z2 = true;
                break;
            }
            if (bArr2[i5 + i2] != bArr[i5 + i3]) {
                z2 = false;
                break;
            }
            i5++;
        }
        return z2;
    }

    public int hashCode() {
        int i2 = this.f26b;
        if (i2 != 0) {
            return i2;
        }
        int hashCode = Arrays.hashCode(this.f25a);
        this.f26b = hashCode;
        return hashCode;
    }

    /* renamed from: i */
    public boolean mo124i(C0017h c0017h, int i2) {
        return c0017h.mo123h(0, 0, this.f25a, i2);
    }

    /* renamed from: j */
    public int mo125j() {
        return this.f25a.length;
    }

    /* renamed from: k */
    public C0017h mo126k() {
        byte[] bArr = this.f25a;
        if (64 > bArr.length) {
            throw new IllegalArgumentException(AbstractC0000a.m17m(new StringBuilder("endIndex > length("), bArr.length, ")"));
        }
        if (64 == bArr.length) {
            return this;
        }
        byte[] bArr2 = new byte[64];
        System.arraycopy(bArr, 0, bArr2, 0, 64);
        return new C0017h(bArr2);
    }

    /* renamed from: l */
    public C0017h mo127l() {
        int i2 = 0;
        while (true) {
            byte[] bArr = this.f25a;
            if (i2 >= bArr.length) {
                return this;
            }
            byte b = bArr[i2];
            if (b >= 65 && b <= 90) {
                byte[] bArr2 = (byte[]) bArr.clone();
                bArr2[i2] = (byte) (b + 32);
                for (int i3 = i2 + 1; i3 < bArr2.length; i3++) {
                    byte b2 = bArr2[i3];
                    if (b2 >= 65 && b2 <= 90) {
                        bArr2[i3] = (byte) (b2 + 32);
                    }
                }
                return new C0017h(bArr2);
            }
            i2++;
        }
    }

    /* renamed from: m */
    public String mo128m() {
        String str = this.f27c;
        if (str != null) {
            return str;
        }
        String str2 = new String(this.f25a, AbstractC0032w.f75a);
        this.f27c = str2;
        return str2;
    }

    /* renamed from: n */
    public void mo129n(C0014e c0014e) {
        byte[] bArr = this.f25a;
        c0014e.m85I(bArr, 0, bArr.length);
    }

    public String toString() {
        byte[] bArr = this.f25a;
        if (bArr.length == 0) {
            return "[size=0]";
        }
        String mo128m = mo128m();
        int length = mo128m.length();
        int i2 = 0;
        int i3 = 0;
        while (true) {
            if (i2 >= length) {
                i2 = mo128m.length();
                break;
            }
            if (i3 == 64) {
                break;
            }
            int codePointAt = mo128m.codePointAt(i2);
            if ((!Character.isISOControl(codePointAt) || codePointAt == 10 || codePointAt == 13) && codePointAt != 65533) {
                i3++;
                i2 += Character.charCount(codePointAt);
            }
        }
        i2 = -1;
        if (i2 != -1) {
            String replace = mo128m.substring(0, i2).replace("\\", "\\\\").replace("\n", "\\n").replace("\r", "\\r");
            if (i2 >= mo128m.length()) {
                return AbstractC0000a.m16l("[text=", replace, "]");
            }
            return "[size=" + bArr.length + " text=" + replace + "…]";
        }
        if (bArr.length <= 64) {
            return "[hex=" + mo122f() + "]";
        }
        return "[size=" + bArr.length + " hex=" + mo126k().mo122f() + "…]";
    }
}

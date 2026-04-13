package org.bouncycastle.crypto.digests;

import android.sun.security.util.DerValue;
import org.bouncycastle.pqc.jcajce.spec.McElieceCCA2KeyGenParameterSpec;
import org.bouncycastle.util.Memoable;
import org.bouncycastle.util.Pack;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class SHA1Digest extends GeneralDigest implements EncodableDigest {
    private static final int DIGEST_LENGTH = 20;
    private static final int Y1 = 1518500249;
    private static final int Y2 = 1859775393;
    private static final int Y3 = -1894007588;
    private static final int Y4 = -899497514;
    private int H1;
    private int H2;
    private int H3;
    private int H4;
    private int H5;

    /* renamed from: X */
    private int[] f1185X;
    private int xOff;

    public SHA1Digest() {
        this.f1185X = new int[80];
        reset();
    }

    private void copyIn(SHA1Digest sHA1Digest) {
        this.H1 = sHA1Digest.H1;
        this.H2 = sHA1Digest.H2;
        this.H3 = sHA1Digest.H3;
        this.H4 = sHA1Digest.H4;
        this.H5 = sHA1Digest.H5;
        int[] iArr = sHA1Digest.f1185X;
        System.arraycopy(iArr, 0, this.f1185X, 0, iArr.length);
        this.xOff = sHA1Digest.xOff;
    }

    /* renamed from: f */
    private int m1203f(int i2, int i3, int i4) {
        return ((~i2) & i4) | (i3 & i2);
    }

    /* renamed from: g */
    private int m1204g(int i2, int i3, int i4) {
        return (i2 & i4) | (i2 & i3) | (i3 & i4);
    }

    /* renamed from: h */
    private int m1205h(int i2, int i3, int i4) {
        return (i2 ^ i3) ^ i4;
    }

    @Override // org.bouncycastle.util.Memoable
    public Memoable copy() {
        return new SHA1Digest(this);
    }

    @Override // org.bouncycastle.crypto.Digest
    public int doFinal(byte[] bArr, int i2) {
        finish();
        Pack.intToBigEndian(this.H1, bArr, i2);
        Pack.intToBigEndian(this.H2, bArr, i2 + 4);
        Pack.intToBigEndian(this.H3, bArr, i2 + 8);
        Pack.intToBigEndian(this.H4, bArr, i2 + 12);
        Pack.intToBigEndian(this.H5, bArr, i2 + 16);
        reset();
        return 20;
    }

    @Override // org.bouncycastle.crypto.Digest
    public String getAlgorithmName() {
        return McElieceCCA2KeyGenParameterSpec.SHA1;
    }

    @Override // org.bouncycastle.crypto.Digest
    public int getDigestSize() {
        return 20;
    }

    @Override // org.bouncycastle.crypto.digests.EncodableDigest
    public byte[] getEncodedState() {
        byte[] bArr = new byte[(this.xOff * 4) + 40];
        super.populateState(bArr);
        Pack.intToBigEndian(this.H1, bArr, 16);
        Pack.intToBigEndian(this.H2, bArr, 20);
        Pack.intToBigEndian(this.H3, bArr, 24);
        Pack.intToBigEndian(this.H4, bArr, 28);
        Pack.intToBigEndian(this.H5, bArr, 32);
        Pack.intToBigEndian(this.xOff, bArr, 36);
        for (int i2 = 0; i2 != this.xOff; i2++) {
            Pack.intToBigEndian(this.f1185X[i2], bArr, (i2 * 4) + 40);
        }
        return bArr;
    }

    @Override // org.bouncycastle.crypto.digests.GeneralDigest
    public void processBlock() {
        for (int i2 = 16; i2 < 80; i2++) {
            int[] iArr = this.f1185X;
            int i3 = ((iArr[i2 - 3] ^ iArr[i2 - 8]) ^ iArr[i2 - 14]) ^ iArr[i2 - 16];
            iArr[i2] = (i3 >>> 31) | (i3 << 1);
        }
        int i4 = this.H1;
        int i5 = this.H2;
        int i6 = this.H3;
        int i7 = this.H4;
        int i8 = this.H5;
        int i9 = 0;
        int i10 = 0;
        while (i9 < 4) {
            int i11 = i10 + 1;
            int m5a = AbstractC0000a.m5a(((i4 << 5) | (i4 >>> 27)) + m1203f(i5, i6, i7), this.f1185X[i10], Y1, i8);
            int i12 = (i5 >>> 2) | (i5 << 30);
            int i13 = i11 + 1;
            int m5a2 = AbstractC0000a.m5a(((m5a << 5) | (m5a >>> 27)) + m1203f(i4, i12, i6), this.f1185X[i11], Y1, i7);
            int i14 = (i4 >>> 2) | (i4 << 30);
            int i15 = i13 + 1;
            int m5a3 = AbstractC0000a.m5a(((m5a2 << 5) | (m5a2 >>> 27)) + m1203f(m5a, i14, i12), this.f1185X[i13], Y1, i6);
            i8 = (m5a >>> 2) | (m5a << 30);
            int i16 = i15 + 1;
            i5 = AbstractC0000a.m5a(((m5a3 << 5) | (m5a3 >>> 27)) + m1203f(m5a2, i8, i14), this.f1185X[i15], Y1, i12);
            i7 = (m5a2 >>> 2) | (m5a2 << 30);
            i4 = AbstractC0000a.m5a(((i5 << 5) | (i5 >>> 27)) + m1203f(m5a3, i7, i8), this.f1185X[i16], Y1, i14);
            i6 = (m5a3 >>> 2) | (m5a3 << 30);
            i9++;
            i10 = i16 + 1;
        }
        int i17 = 0;
        while (i17 < 4) {
            int i18 = i10 + 1;
            int m5a4 = AbstractC0000a.m5a(((i4 << 5) | (i4 >>> 27)) + m1205h(i5, i6, i7), this.f1185X[i10], Y2, i8);
            int i19 = (i5 >>> 2) | (i5 << 30);
            int i20 = i18 + 1;
            int m5a5 = AbstractC0000a.m5a(((m5a4 << 5) | (m5a4 >>> 27)) + m1205h(i4, i19, i6), this.f1185X[i18], Y2, i7);
            int i21 = (i4 >>> 2) | (i4 << 30);
            int i22 = i20 + 1;
            int m5a6 = AbstractC0000a.m5a(((m5a5 << 5) | (m5a5 >>> 27)) + m1205h(m5a4, i21, i19), this.f1185X[i20], Y2, i6);
            i8 = (m5a4 >>> 2) | (m5a4 << 30);
            int i23 = i22 + 1;
            i5 = AbstractC0000a.m5a(((m5a6 << 5) | (m5a6 >>> 27)) + m1205h(m5a5, i8, i21), this.f1185X[i22], Y2, i19);
            i7 = (m5a5 >>> 2) | (m5a5 << 30);
            i4 = AbstractC0000a.m5a(((i5 << 5) | (i5 >>> 27)) + m1205h(m5a6, i7, i8), this.f1185X[i23], Y2, i21);
            i6 = (m5a6 >>> 2) | (m5a6 << 30);
            i17++;
            i10 = i23 + 1;
        }
        int i24 = 0;
        while (i24 < 4) {
            int i25 = i10 + 1;
            int m5a7 = AbstractC0000a.m5a(((i4 << 5) | (i4 >>> 27)) + m1204g(i5, i6, i7), this.f1185X[i10], Y3, i8);
            int i26 = (i5 >>> 2) | (i5 << 30);
            int i27 = i25 + 1;
            int m5a8 = AbstractC0000a.m5a(((m5a7 << 5) | (m5a7 >>> 27)) + m1204g(i4, i26, i6), this.f1185X[i25], Y3, i7);
            int i28 = (i4 >>> 2) | (i4 << 30);
            int i29 = i27 + 1;
            int m5a9 = AbstractC0000a.m5a(((m5a8 << 5) | (m5a8 >>> 27)) + m1204g(m5a7, i28, i26), this.f1185X[i27], Y3, i6);
            i8 = (m5a7 >>> 2) | (m5a7 << 30);
            int i30 = i29 + 1;
            i5 = AbstractC0000a.m5a(((m5a9 << 5) | (m5a9 >>> 27)) + m1204g(m5a8, i8, i28), this.f1185X[i29], Y3, i26);
            i7 = (m5a8 >>> 2) | (m5a8 << 30);
            i4 = AbstractC0000a.m5a(((i5 << 5) | (i5 >>> 27)) + m1204g(m5a9, i7, i8), this.f1185X[i30], Y3, i28);
            i6 = (m5a9 >>> 2) | (m5a9 << 30);
            i24++;
            i10 = i30 + 1;
        }
        int i31 = 0;
        while (i31 <= 3) {
            int i32 = i10 + 1;
            int m5a10 = AbstractC0000a.m5a(((i4 << 5) | (i4 >>> 27)) + m1205h(i5, i6, i7), this.f1185X[i10], Y4, i8);
            int i33 = (i5 >>> 2) | (i5 << 30);
            int i34 = i32 + 1;
            int m5a11 = AbstractC0000a.m5a(((m5a10 << 5) | (m5a10 >>> 27)) + m1205h(i4, i33, i6), this.f1185X[i32], Y4, i7);
            int i35 = (i4 >>> 2) | (i4 << 30);
            int i36 = i34 + 1;
            int m5a12 = AbstractC0000a.m5a(((m5a11 << 5) | (m5a11 >>> 27)) + m1205h(m5a10, i35, i33), this.f1185X[i34], Y4, i6);
            i8 = (m5a10 >>> 2) | (m5a10 << 30);
            int i37 = i36 + 1;
            i5 = AbstractC0000a.m5a(((m5a12 << 5) | (m5a12 >>> 27)) + m1205h(m5a11, i8, i35), this.f1185X[i36], Y4, i33);
            i7 = (m5a11 >>> 2) | (m5a11 << 30);
            i4 = AbstractC0000a.m5a(((i5 << 5) | (i5 >>> 27)) + m1205h(m5a12, i7, i8), this.f1185X[i37], Y4, i35);
            i6 = (m5a12 >>> 2) | (m5a12 << 30);
            i31++;
            i10 = i37 + 1;
        }
        this.H1 += i4;
        this.H2 += i5;
        this.H3 += i6;
        this.H4 += i7;
        this.H5 += i8;
        this.xOff = 0;
        for (int i38 = 0; i38 < 16; i38++) {
            this.f1185X[i38] = 0;
        }
    }

    @Override // org.bouncycastle.crypto.digests.GeneralDigest
    public void processLength(long j2) {
        if (this.xOff > 14) {
            processBlock();
        }
        int[] iArr = this.f1185X;
        iArr[14] = (int) (j2 >>> 32);
        iArr[15] = (int) j2;
    }

    @Override // org.bouncycastle.crypto.digests.GeneralDigest
    public void processWord(byte[] bArr, int i2) {
        int i3 = bArr[i2] << DerValue.tag_GeneralizedTime;
        int i4 = i2 + 1;
        int i5 = i3 | ((bArr[i4] & 255) << 16);
        int i6 = i4 + 1;
        int i7 = (bArr[i6 + 1] & 255) | i5 | ((bArr[i6] & 255) << 8);
        int[] iArr = this.f1185X;
        int i8 = this.xOff;
        iArr[i8] = i7;
        int i9 = i8 + 1;
        this.xOff = i9;
        if (i9 == 16) {
            processBlock();
        }
    }

    @Override // org.bouncycastle.crypto.digests.GeneralDigest, org.bouncycastle.crypto.Digest
    public void reset() {
        super.reset();
        this.H1 = 1732584193;
        this.H2 = -271733879;
        this.H3 = -1732584194;
        this.H4 = 271733878;
        this.H5 = -1009589776;
        this.xOff = 0;
        int i2 = 0;
        while (true) {
            int[] iArr = this.f1185X;
            if (i2 == iArr.length) {
                return;
            }
            iArr[i2] = 0;
            i2++;
        }
    }

    public SHA1Digest(SHA1Digest sHA1Digest) {
        super(sHA1Digest);
        this.f1185X = new int[80];
        copyIn(sHA1Digest);
    }

    @Override // org.bouncycastle.util.Memoable
    public void reset(Memoable memoable) {
        SHA1Digest sHA1Digest = (SHA1Digest) memoable;
        super.copyIn((GeneralDigest) sHA1Digest);
        copyIn(sHA1Digest);
    }

    public SHA1Digest(byte[] bArr) {
        super(bArr);
        this.f1185X = new int[80];
        this.H1 = Pack.bigEndianToInt(bArr, 16);
        this.H2 = Pack.bigEndianToInt(bArr, 20);
        this.H3 = Pack.bigEndianToInt(bArr, 24);
        this.H4 = Pack.bigEndianToInt(bArr, 28);
        this.H5 = Pack.bigEndianToInt(bArr, 32);
        this.xOff = Pack.bigEndianToInt(bArr, 36);
        for (int i2 = 0; i2 != this.xOff; i2++) {
            this.f1185X[i2] = Pack.bigEndianToInt(bArr, (i2 * 4) + 40);
        }
    }
}

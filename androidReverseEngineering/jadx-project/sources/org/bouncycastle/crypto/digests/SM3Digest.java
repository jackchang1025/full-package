package org.bouncycastle.crypto.digests;

import org.bouncycastle.util.Memoable;
import org.bouncycastle.util.Pack;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class SM3Digest extends GeneralDigest {
    private static final int BLOCK_SIZE = 16;
    private static final int DIGEST_LENGTH = 32;

    /* renamed from: T */
    private static final int[] f1190T = new int[64];

    /* renamed from: V */
    private int[] f1191V;

    /* renamed from: W */
    private int[] f1192W;
    private int[] inwords;
    private int xOff;

    static {
        int i2;
        int i3 = 0;
        while (true) {
            if (i3 >= 16) {
                break;
            }
            f1190T[i3] = (2043430169 >>> (32 - i3)) | (2043430169 << i3);
            i3++;
        }
        for (i2 = 16; i2 < 64; i2++) {
            int i4 = i2 % 32;
            f1190T[i2] = (2055708042 >>> (32 - i4)) | (2055708042 << i4);
        }
    }

    public SM3Digest() {
        this.f1191V = new int[8];
        this.inwords = new int[16];
        this.f1192W = new int[68];
        reset();
    }

    private int FF0(int i2, int i3, int i4) {
        return (i2 ^ i3) ^ i4;
    }

    private int FF1(int i2, int i3, int i4) {
        return (i2 & i4) | (i2 & i3) | (i3 & i4);
    }

    private int GG0(int i2, int i3, int i4) {
        return (i2 ^ i3) ^ i4;
    }

    private int GG1(int i2, int i3, int i4) {
        return ((~i2) & i4) | (i3 & i2);
    }

    private int P0(int i2) {
        return (i2 ^ ((i2 << 9) | (i2 >>> 23))) ^ ((i2 << 17) | (i2 >>> 15));
    }

    private int P1(int i2) {
        return (i2 ^ ((i2 << 15) | (i2 >>> 17))) ^ ((i2 << 23) | (i2 >>> 9));
    }

    private void copyIn(SM3Digest sM3Digest) {
        int[] iArr = sM3Digest.f1191V;
        int[] iArr2 = this.f1191V;
        System.arraycopy(iArr, 0, iArr2, 0, iArr2.length);
        int[] iArr3 = sM3Digest.inwords;
        int[] iArr4 = this.inwords;
        System.arraycopy(iArr3, 0, iArr4, 0, iArr4.length);
        this.xOff = sM3Digest.xOff;
    }

    @Override // org.bouncycastle.util.Memoable
    public Memoable copy() {
        return new SM3Digest(this);
    }

    @Override // org.bouncycastle.crypto.Digest
    public int doFinal(byte[] bArr, int i2) {
        finish();
        Pack.intToBigEndian(this.f1191V, bArr, i2);
        reset();
        return 32;
    }

    @Override // org.bouncycastle.crypto.Digest
    public String getAlgorithmName() {
        return "SM3";
    }

    @Override // org.bouncycastle.crypto.Digest
    public int getDigestSize() {
        return 32;
    }

    @Override // org.bouncycastle.crypto.digests.GeneralDigest
    public void processBlock() {
        int i2;
        int i3 = 0;
        while (true) {
            if (i3 >= 16) {
                break;
            }
            this.f1192W[i3] = this.inwords[i3];
            i3++;
        }
        for (int i4 = 16; i4 < 68; i4++) {
            int[] iArr = this.f1192W;
            int i5 = iArr[i4 - 3];
            int i6 = iArr[i4 - 13];
            iArr[i4] = (P1(((i5 >>> 17) | (i5 << 15)) ^ (iArr[i4 - 16] ^ iArr[i4 - 9])) ^ ((i6 >>> 25) | (i6 << 7))) ^ this.f1192W[i4 - 6];
        }
        int[] iArr2 = this.f1191V;
        int i7 = iArr2[0];
        int i8 = iArr2[1];
        int i9 = iArr2[2];
        int i10 = iArr2[3];
        int i11 = iArr2[4];
        int i12 = iArr2[5];
        int i13 = iArr2[6];
        int i14 = iArr2[7];
        int i15 = 0;
        for (i2 = 16; i15 < i2; i2 = 16) {
            int i16 = (i7 << 12) | (i7 >>> 20);
            int i17 = i16 + i11 + f1190T[i15];
            int i18 = (i17 << 7) | (i17 >>> 25);
            int[] iArr3 = this.f1192W;
            int i19 = iArr3[i15];
            int m5a = AbstractC0000a.m5a(FF0(i7, i8, i9), i10, i16 ^ i18, iArr3[i15 + 4] ^ i19);
            int m5a2 = AbstractC0000a.m5a(GG0(i11, i12, i13), i14, i18, i19);
            int i20 = (i8 << 9) | (i8 >>> 23);
            int i21 = (i12 << 19) | (i12 >>> 13);
            i15++;
            i10 = i9;
            i12 = i11;
            i11 = P0(m5a2);
            i9 = i20;
            i14 = i13;
            i13 = i21;
            i8 = i7;
            i7 = m5a;
        }
        int i22 = i7;
        int i23 = 16;
        int i24 = i14;
        int i25 = i13;
        while (i23 < 64) {
            int i26 = (i22 << 12) | (i22 >>> 20);
            int i27 = i26 + i11 + f1190T[i23];
            int i28 = (i27 >>> 25) | (i27 << 7);
            int[] iArr4 = this.f1192W;
            int i29 = iArr4[i23];
            int m5a3 = AbstractC0000a.m5a(FF1(i22, i8, i9), i10, i26 ^ i28, iArr4[i23 + 4] ^ i29);
            int m5a4 = AbstractC0000a.m5a(GG1(i11, i12, i25), i24, i28, i29);
            int i30 = (i12 << 19) | (i12 >>> 13);
            i23++;
            i10 = i9;
            i12 = i11;
            i11 = P0(m5a4);
            i9 = (i8 >>> 23) | (i8 << 9);
            i24 = i25;
            i8 = i22;
            i22 = m5a3;
            i25 = i30;
        }
        int[] iArr5 = this.f1191V;
        iArr5[0] = i22 ^ iArr5[0];
        iArr5[1] = i8 ^ iArr5[1];
        iArr5[2] = iArr5[2] ^ i9;
        iArr5[3] = iArr5[3] ^ i10;
        iArr5[4] = iArr5[4] ^ i11;
        iArr5[5] = iArr5[5] ^ i12;
        iArr5[6] = i25 ^ iArr5[6];
        iArr5[7] = i24 ^ iArr5[7];
        this.xOff = 0;
    }

    @Override // org.bouncycastle.crypto.digests.GeneralDigest
    public void processLength(long j2) {
        int i2 = this.xOff;
        if (i2 > 14) {
            this.inwords[i2] = 0;
            this.xOff = i2 + 1;
            processBlock();
        }
        while (true) {
            int i3 = this.xOff;
            if (i3 >= 14) {
                int[] iArr = this.inwords;
                int i4 = i3 + 1;
                iArr[i3] = (int) (j2 >>> 32);
                this.xOff = i4 + 1;
                iArr[i4] = (int) j2;
                return;
            }
            this.inwords[i3] = 0;
            this.xOff = i3 + 1;
        }
    }

    @Override // org.bouncycastle.crypto.digests.GeneralDigest
    public void processWord(byte[] bArr, int i2) {
        int i3 = (bArr[i2] & 255) << 24;
        int i4 = i2 + 1;
        int i5 = i3 | ((bArr[i4] & 255) << 16);
        int i6 = i4 + 1;
        int i7 = (bArr[i6 + 1] & 255) | i5 | ((bArr[i6] & 255) << 8);
        int[] iArr = this.inwords;
        int i8 = this.xOff;
        iArr[i8] = i7;
        int i9 = i8 + 1;
        this.xOff = i9;
        if (i9 >= 16) {
            processBlock();
        }
    }

    @Override // org.bouncycastle.crypto.digests.GeneralDigest, org.bouncycastle.crypto.Digest
    public void reset() {
        super.reset();
        int[] iArr = this.f1191V;
        iArr[0] = 1937774191;
        iArr[1] = 1226093241;
        iArr[2] = 388252375;
        iArr[3] = -628488704;
        iArr[4] = -1452330820;
        iArr[5] = 372324522;
        iArr[6] = -477237683;
        iArr[7] = -1325724082;
        this.xOff = 0;
    }

    public SM3Digest(SM3Digest sM3Digest) {
        super(sM3Digest);
        this.f1191V = new int[8];
        this.inwords = new int[16];
        this.f1192W = new int[68];
        copyIn(sM3Digest);
    }

    @Override // org.bouncycastle.util.Memoable
    public void reset(Memoable memoable) {
        SM3Digest sM3Digest = (SM3Digest) memoable;
        super.copyIn((GeneralDigest) sM3Digest);
        copyIn(sM3Digest);
    }
}

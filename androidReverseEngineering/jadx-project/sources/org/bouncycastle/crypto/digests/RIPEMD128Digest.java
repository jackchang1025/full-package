package org.bouncycastle.crypto.digests;

import org.bouncycastle.util.Memoable;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class RIPEMD128Digest extends GeneralDigest {
    private static final int DIGEST_LENGTH = 16;
    private int H0;
    private int H1;
    private int H2;
    private int H3;

    /* renamed from: X */
    private int[] f1181X;
    private int xOff;

    public RIPEMD128Digest() {
        this.f1181X = new int[16];
        reset();
    }

    private int F1(int i2, int i3, int i4, int i5, int i6, int i7) {
        return RL(i2 + f1(i3, i4, i5) + i6, i7);
    }

    private int F2(int i2, int i3, int i4, int i5, int i6, int i7) {
        return RL(AbstractC0000a.m5a(i2, f2(i3, i4, i5), i6, 1518500249), i7);
    }

    private int F3(int i2, int i3, int i4, int i5, int i6, int i7) {
        return RL(AbstractC0000a.m5a(i2, f3(i3, i4, i5), i6, 1859775393), i7);
    }

    private int F4(int i2, int i3, int i4, int i5, int i6, int i7) {
        return RL(AbstractC0000a.m5a(i2, f4(i3, i4, i5), i6, -1894007588), i7);
    }

    private int FF1(int i2, int i3, int i4, int i5, int i6, int i7) {
        return RL(i2 + f1(i3, i4, i5) + i6, i7);
    }

    private int FF2(int i2, int i3, int i4, int i5, int i6, int i7) {
        return RL(AbstractC0000a.m5a(i2, f2(i3, i4, i5), i6, 1836072691), i7);
    }

    private int FF3(int i2, int i3, int i4, int i5, int i6, int i7) {
        return RL(AbstractC0000a.m5a(i2, f3(i3, i4, i5), i6, 1548603684), i7);
    }

    private int FF4(int i2, int i3, int i4, int i5, int i6, int i7) {
        return RL(AbstractC0000a.m5a(i2, f4(i3, i4, i5), i6, 1352829926), i7);
    }

    private int RL(int i2, int i3) {
        return (i2 >>> (32 - i3)) | (i2 << i3);
    }

    private void copyIn(RIPEMD128Digest rIPEMD128Digest) {
        super.copyIn((GeneralDigest) rIPEMD128Digest);
        this.H0 = rIPEMD128Digest.H0;
        this.H1 = rIPEMD128Digest.H1;
        this.H2 = rIPEMD128Digest.H2;
        this.H3 = rIPEMD128Digest.H3;
        int[] iArr = rIPEMD128Digest.f1181X;
        System.arraycopy(iArr, 0, this.f1181X, 0, iArr.length);
        this.xOff = rIPEMD128Digest.xOff;
    }

    private int f1(int i2, int i3, int i4) {
        return (i2 ^ i3) ^ i4;
    }

    private int f2(int i2, int i3, int i4) {
        return ((~i2) & i4) | (i3 & i2);
    }

    private int f3(int i2, int i3, int i4) {
        return (i2 | (~i3)) ^ i4;
    }

    private int f4(int i2, int i3, int i4) {
        return (i2 & i4) | (i3 & (~i4));
    }

    private void unpackWord(int i2, byte[] bArr, int i3) {
        bArr[i3] = (byte) i2;
        bArr[i3 + 1] = (byte) (i2 >>> 8);
        bArr[i3 + 2] = (byte) (i2 >>> 16);
        bArr[i3 + 3] = (byte) (i2 >>> 24);
    }

    @Override // org.bouncycastle.util.Memoable
    public Memoable copy() {
        return new RIPEMD128Digest(this);
    }

    @Override // org.bouncycastle.crypto.Digest
    public int doFinal(byte[] bArr, int i2) {
        finish();
        unpackWord(this.H0, bArr, i2);
        unpackWord(this.H1, bArr, i2 + 4);
        unpackWord(this.H2, bArr, i2 + 8);
        unpackWord(this.H3, bArr, i2 + 12);
        reset();
        return 16;
    }

    @Override // org.bouncycastle.crypto.Digest
    public String getAlgorithmName() {
        return "RIPEMD128";
    }

    @Override // org.bouncycastle.crypto.Digest
    public int getDigestSize() {
        return 16;
    }

    @Override // org.bouncycastle.crypto.digests.GeneralDigest
    public void processBlock() {
        int i2 = this.H0;
        int i3 = this.H1;
        int i4 = this.H2;
        int i5 = this.H3;
        int F1 = F1(i2, i3, i4, i5, this.f1181X[0], 11);
        int F12 = F1(i5, F1, i3, i4, this.f1181X[1], 14);
        int F13 = F1(i4, F12, F1, i3, this.f1181X[2], 15);
        int F14 = F1(i3, F13, F12, F1, this.f1181X[3], 12);
        int F15 = F1(F1, F14, F13, F12, this.f1181X[4], 5);
        int F16 = F1(F12, F15, F14, F13, this.f1181X[5], 8);
        int F17 = F1(F13, F16, F15, F14, this.f1181X[6], 7);
        int F18 = F1(F14, F17, F16, F15, this.f1181X[7], 9);
        int F19 = F1(F15, F18, F17, F16, this.f1181X[8], 11);
        int F110 = F1(F16, F19, F18, F17, this.f1181X[9], 13);
        int F111 = F1(F17, F110, F19, F18, this.f1181X[10], 14);
        int F112 = F1(F18, F111, F110, F19, this.f1181X[11], 15);
        int F113 = F1(F19, F112, F111, F110, this.f1181X[12], 6);
        int F114 = F1(F110, F113, F112, F111, this.f1181X[13], 7);
        int F115 = F1(F111, F114, F113, F112, this.f1181X[14], 9);
        int F116 = F1(F112, F115, F114, F113, this.f1181X[15], 8);
        int F2 = F2(F113, F116, F115, F114, this.f1181X[7], 7);
        int F22 = F2(F114, F2, F116, F115, this.f1181X[4], 6);
        int F23 = F2(F115, F22, F2, F116, this.f1181X[13], 8);
        int F24 = F2(F116, F23, F22, F2, this.f1181X[1], 13);
        int F25 = F2(F2, F24, F23, F22, this.f1181X[10], 11);
        int F26 = F2(F22, F25, F24, F23, this.f1181X[6], 9);
        int F27 = F2(F23, F26, F25, F24, this.f1181X[15], 7);
        int F28 = F2(F24, F27, F26, F25, this.f1181X[3], 15);
        int F29 = F2(F25, F28, F27, F26, this.f1181X[12], 7);
        int F210 = F2(F26, F29, F28, F27, this.f1181X[0], 12);
        int F211 = F2(F27, F210, F29, F28, this.f1181X[9], 15);
        int F212 = F2(F28, F211, F210, F29, this.f1181X[5], 9);
        int F213 = F2(F29, F212, F211, F210, this.f1181X[2], 11);
        int F214 = F2(F210, F213, F212, F211, this.f1181X[14], 7);
        int F215 = F2(F211, F214, F213, F212, this.f1181X[11], 13);
        int F216 = F2(F212, F215, F214, F213, this.f1181X[8], 12);
        int F3 = F3(F213, F216, F215, F214, this.f1181X[3], 11);
        int F32 = F3(F214, F3, F216, F215, this.f1181X[10], 13);
        int F33 = F3(F215, F32, F3, F216, this.f1181X[14], 6);
        int F34 = F3(F216, F33, F32, F3, this.f1181X[4], 7);
        int F35 = F3(F3, F34, F33, F32, this.f1181X[9], 14);
        int F36 = F3(F32, F35, F34, F33, this.f1181X[15], 9);
        int F37 = F3(F33, F36, F35, F34, this.f1181X[8], 13);
        int F38 = F3(F34, F37, F36, F35, this.f1181X[1], 15);
        int F39 = F3(F35, F38, F37, F36, this.f1181X[2], 14);
        int F310 = F3(F36, F39, F38, F37, this.f1181X[7], 8);
        int F311 = F3(F37, F310, F39, F38, this.f1181X[0], 13);
        int F312 = F3(F38, F311, F310, F39, this.f1181X[6], 6);
        int F313 = F3(F39, F312, F311, F310, this.f1181X[13], 5);
        int F314 = F3(F310, F313, F312, F311, this.f1181X[11], 12);
        int F315 = F3(F311, F314, F313, F312, this.f1181X[5], 7);
        int F316 = F3(F312, F315, F314, F313, this.f1181X[12], 5);
        int F4 = F4(F313, F316, F315, F314, this.f1181X[1], 11);
        int F42 = F4(F314, F4, F316, F315, this.f1181X[9], 12);
        int F43 = F4(F315, F42, F4, F316, this.f1181X[11], 14);
        int F44 = F4(F316, F43, F42, F4, this.f1181X[10], 15);
        int F45 = F4(F4, F44, F43, F42, this.f1181X[0], 14);
        int F46 = F4(F42, F45, F44, F43, this.f1181X[8], 15);
        int F47 = F4(F43, F46, F45, F44, this.f1181X[12], 9);
        int F48 = F4(F44, F47, F46, F45, this.f1181X[4], 8);
        int F49 = F4(F45, F48, F47, F46, this.f1181X[13], 9);
        int F410 = F4(F46, F49, F48, F47, this.f1181X[3], 14);
        int F411 = F4(F47, F410, F49, F48, this.f1181X[7], 5);
        int F412 = F4(F48, F411, F410, F49, this.f1181X[15], 6);
        int F413 = F4(F49, F412, F411, F410, this.f1181X[14], 8);
        int F414 = F4(F410, F413, F412, F411, this.f1181X[5], 6);
        int F415 = F4(F411, F414, F413, F412, this.f1181X[6], 5);
        int F416 = F4(F412, F415, F414, F413, this.f1181X[2], 12);
        int FF4 = FF4(i2, i3, i4, i5, this.f1181X[5], 8);
        int FF42 = FF4(i5, FF4, i3, i4, this.f1181X[14], 9);
        int FF43 = FF4(i4, FF42, FF4, i3, this.f1181X[7], 9);
        int FF44 = FF4(i3, FF43, FF42, FF4, this.f1181X[0], 11);
        int FF45 = FF4(FF4, FF44, FF43, FF42, this.f1181X[9], 13);
        int FF46 = FF4(FF42, FF45, FF44, FF43, this.f1181X[2], 15);
        int FF47 = FF4(FF43, FF46, FF45, FF44, this.f1181X[11], 15);
        int FF48 = FF4(FF44, FF47, FF46, FF45, this.f1181X[4], 5);
        int FF49 = FF4(FF45, FF48, FF47, FF46, this.f1181X[13], 7);
        int FF410 = FF4(FF46, FF49, FF48, FF47, this.f1181X[6], 7);
        int FF411 = FF4(FF47, FF410, FF49, FF48, this.f1181X[15], 8);
        int FF412 = FF4(FF48, FF411, FF410, FF49, this.f1181X[8], 11);
        int FF413 = FF4(FF49, FF412, FF411, FF410, this.f1181X[1], 14);
        int FF414 = FF4(FF410, FF413, FF412, FF411, this.f1181X[10], 14);
        int FF415 = FF4(FF411, FF414, FF413, FF412, this.f1181X[3], 12);
        int FF416 = FF4(FF412, FF415, FF414, FF413, this.f1181X[12], 6);
        int FF3 = FF3(FF413, FF416, FF415, FF414, this.f1181X[6], 9);
        int FF32 = FF3(FF414, FF3, FF416, FF415, this.f1181X[11], 13);
        int FF33 = FF3(FF415, FF32, FF3, FF416, this.f1181X[3], 15);
        int FF34 = FF3(FF416, FF33, FF32, FF3, this.f1181X[7], 7);
        int FF35 = FF3(FF3, FF34, FF33, FF32, this.f1181X[0], 12);
        int FF36 = FF3(FF32, FF35, FF34, FF33, this.f1181X[13], 8);
        int FF37 = FF3(FF33, FF36, FF35, FF34, this.f1181X[5], 9);
        int FF38 = FF3(FF34, FF37, FF36, FF35, this.f1181X[10], 11);
        int FF39 = FF3(FF35, FF38, FF37, FF36, this.f1181X[14], 7);
        int FF310 = FF3(FF36, FF39, FF38, FF37, this.f1181X[15], 7);
        int FF311 = FF3(FF37, FF310, FF39, FF38, this.f1181X[8], 12);
        int FF312 = FF3(FF38, FF311, FF310, FF39, this.f1181X[12], 7);
        int FF313 = FF3(FF39, FF312, FF311, FF310, this.f1181X[4], 6);
        int FF314 = FF3(FF310, FF313, FF312, FF311, this.f1181X[9], 15);
        int FF315 = FF3(FF311, FF314, FF313, FF312, this.f1181X[1], 13);
        int FF316 = FF3(FF312, FF315, FF314, FF313, this.f1181X[2], 11);
        int FF2 = FF2(FF313, FF316, FF315, FF314, this.f1181X[15], 9);
        int FF22 = FF2(FF314, FF2, FF316, FF315, this.f1181X[5], 7);
        int FF23 = FF2(FF315, FF22, FF2, FF316, this.f1181X[1], 15);
        int FF24 = FF2(FF316, FF23, FF22, FF2, this.f1181X[3], 11);
        int FF25 = FF2(FF2, FF24, FF23, FF22, this.f1181X[7], 8);
        int FF26 = FF2(FF22, FF25, FF24, FF23, this.f1181X[14], 6);
        int FF27 = FF2(FF23, FF26, FF25, FF24, this.f1181X[6], 6);
        int FF28 = FF2(FF24, FF27, FF26, FF25, this.f1181X[9], 14);
        int FF29 = FF2(FF25, FF28, FF27, FF26, this.f1181X[11], 12);
        int FF210 = FF2(FF26, FF29, FF28, FF27, this.f1181X[8], 13);
        int FF211 = FF2(FF27, FF210, FF29, FF28, this.f1181X[12], 5);
        int FF212 = FF2(FF28, FF211, FF210, FF29, this.f1181X[2], 14);
        int FF213 = FF2(FF29, FF212, FF211, FF210, this.f1181X[10], 13);
        int FF214 = FF2(FF210, FF213, FF212, FF211, this.f1181X[0], 13);
        int FF215 = FF2(FF211, FF214, FF213, FF212, this.f1181X[4], 7);
        int FF216 = FF2(FF212, FF215, FF214, FF213, this.f1181X[13], 5);
        int FF1 = FF1(FF213, FF216, FF215, FF214, this.f1181X[8], 15);
        int FF12 = FF1(FF214, FF1, FF216, FF215, this.f1181X[6], 5);
        int FF13 = FF1(FF215, FF12, FF1, FF216, this.f1181X[4], 8);
        int FF14 = FF1(FF216, FF13, FF12, FF1, this.f1181X[1], 11);
        int FF15 = FF1(FF1, FF14, FF13, FF12, this.f1181X[3], 14);
        int FF16 = FF1(FF12, FF15, FF14, FF13, this.f1181X[11], 14);
        int FF17 = FF1(FF13, FF16, FF15, FF14, this.f1181X[15], 6);
        int FF18 = FF1(FF14, FF17, FF16, FF15, this.f1181X[0], 14);
        int FF19 = FF1(FF15, FF18, FF17, FF16, this.f1181X[5], 6);
        int FF110 = FF1(FF16, FF19, FF18, FF17, this.f1181X[12], 9);
        int FF111 = FF1(FF17, FF110, FF19, FF18, this.f1181X[2], 12);
        int FF112 = FF1(FF18, FF111, FF110, FF19, this.f1181X[13], 9);
        int FF113 = FF1(FF19, FF112, FF111, FF110, this.f1181X[9], 12);
        int FF114 = FF1(FF110, FF113, FF112, FF111, this.f1181X[7], 5);
        int FF115 = FF1(FF111, FF114, FF113, FF112, this.f1181X[10], 15);
        int FF116 = FF1(FF112, FF115, FF114, FF113, this.f1181X[14], 8);
        int i6 = F415 + this.H1 + FF114;
        this.H1 = this.H2 + F414 + FF113;
        this.H2 = this.H3 + F413 + FF116;
        this.H3 = this.H0 + F416 + FF115;
        this.H0 = i6;
        this.xOff = 0;
        int i7 = 0;
        while (true) {
            int[] iArr = this.f1181X;
            if (i7 == iArr.length) {
                return;
            }
            iArr[i7] = 0;
            i7++;
        }
    }

    @Override // org.bouncycastle.crypto.digests.GeneralDigest
    public void processLength(long j2) {
        if (this.xOff > 14) {
            processBlock();
        }
        int[] iArr = this.f1181X;
        iArr[14] = (int) ((-1) & j2);
        iArr[15] = (int) (j2 >>> 32);
    }

    @Override // org.bouncycastle.crypto.digests.GeneralDigest
    public void processWord(byte[] bArr, int i2) {
        int[] iArr = this.f1181X;
        int i3 = this.xOff;
        int i4 = i3 + 1;
        this.xOff = i4;
        iArr[i3] = ((bArr[i2 + 3] & 255) << 24) | (bArr[i2] & 255) | ((bArr[i2 + 1] & 255) << 8) | ((bArr[i2 + 2] & 255) << 16);
        if (i4 == 16) {
            processBlock();
        }
    }

    @Override // org.bouncycastle.crypto.digests.GeneralDigest, org.bouncycastle.crypto.Digest
    public void reset() {
        super.reset();
        this.H0 = 1732584193;
        this.H1 = -271733879;
        this.H2 = -1732584194;
        this.H3 = 271733878;
        this.xOff = 0;
        int i2 = 0;
        while (true) {
            int[] iArr = this.f1181X;
            if (i2 == iArr.length) {
                return;
            }
            iArr[i2] = 0;
            i2++;
        }
    }

    public RIPEMD128Digest(RIPEMD128Digest rIPEMD128Digest) {
        super(rIPEMD128Digest);
        this.f1181X = new int[16];
        copyIn(rIPEMD128Digest);
    }

    @Override // org.bouncycastle.util.Memoable
    public void reset(Memoable memoable) {
        copyIn((RIPEMD128Digest) memoable);
    }
}

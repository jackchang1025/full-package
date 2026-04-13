package org.bouncycastle.crypto.digests;

import org.bouncycastle.util.Memoable;

/* loaded from: classes.dex */
public class MD4Digest extends GeneralDigest {
    private static final int DIGEST_LENGTH = 16;
    private static final int S11 = 3;
    private static final int S12 = 7;
    private static final int S13 = 11;
    private static final int S14 = 19;
    private static final int S21 = 3;
    private static final int S22 = 5;
    private static final int S23 = 9;
    private static final int S24 = 13;
    private static final int S31 = 3;
    private static final int S32 = 9;
    private static final int S33 = 11;
    private static final int S34 = 15;
    private int H1;
    private int H2;
    private int H3;
    private int H4;

    /* renamed from: X */
    private int[] f1178X;
    private int xOff;

    public MD4Digest() {
        this.f1178X = new int[16];
        reset();
    }

    /* renamed from: F */
    private int m1196F(int i2, int i3, int i4) {
        return ((~i2) & i4) | (i3 & i2);
    }

    /* renamed from: G */
    private int m1197G(int i2, int i3, int i4) {
        return (i2 & i4) | (i2 & i3) | (i3 & i4);
    }

    /* renamed from: H */
    private int m1198H(int i2, int i3, int i4) {
        return (i2 ^ i3) ^ i4;
    }

    private void copyIn(MD4Digest mD4Digest) {
        super.copyIn((GeneralDigest) mD4Digest);
        this.H1 = mD4Digest.H1;
        this.H2 = mD4Digest.H2;
        this.H3 = mD4Digest.H3;
        this.H4 = mD4Digest.H4;
        int[] iArr = mD4Digest.f1178X;
        System.arraycopy(iArr, 0, this.f1178X, 0, iArr.length);
        this.xOff = mD4Digest.xOff;
    }

    private int rotateLeft(int i2, int i3) {
        return (i2 >>> (32 - i3)) | (i2 << i3);
    }

    private void unpackWord(int i2, byte[] bArr, int i3) {
        bArr[i3] = (byte) i2;
        bArr[i3 + 1] = (byte) (i2 >>> 8);
        bArr[i3 + 2] = (byte) (i2 >>> 16);
        bArr[i3 + 3] = (byte) (i2 >>> 24);
    }

    @Override // org.bouncycastle.util.Memoable
    public Memoable copy() {
        return new MD4Digest(this);
    }

    @Override // org.bouncycastle.crypto.Digest
    public int doFinal(byte[] bArr, int i2) {
        finish();
        unpackWord(this.H1, bArr, i2);
        unpackWord(this.H2, bArr, i2 + 4);
        unpackWord(this.H3, bArr, i2 + 8);
        unpackWord(this.H4, bArr, i2 + 12);
        reset();
        return 16;
    }

    @Override // org.bouncycastle.crypto.Digest
    public String getAlgorithmName() {
        return "MD4";
    }

    @Override // org.bouncycastle.crypto.Digest
    public int getDigestSize() {
        return 16;
    }

    @Override // org.bouncycastle.crypto.digests.GeneralDigest
    public void processBlock() {
        int i2 = this.H1;
        int i3 = this.H2;
        int i4 = this.H3;
        int i5 = this.H4;
        int rotateLeft = rotateLeft(i2 + m1196F(i3, i4, i5) + this.f1178X[0], 3);
        int rotateLeft2 = rotateLeft(i5 + m1196F(rotateLeft, i3, i4) + this.f1178X[1], 7);
        int rotateLeft3 = rotateLeft(i4 + m1196F(rotateLeft2, rotateLeft, i3) + this.f1178X[2], 11);
        int rotateLeft4 = rotateLeft(i3 + m1196F(rotateLeft3, rotateLeft2, rotateLeft) + this.f1178X[3], 19);
        int rotateLeft5 = rotateLeft(rotateLeft + m1196F(rotateLeft4, rotateLeft3, rotateLeft2) + this.f1178X[4], 3);
        int rotateLeft6 = rotateLeft(rotateLeft2 + m1196F(rotateLeft5, rotateLeft4, rotateLeft3) + this.f1178X[5], 7);
        int rotateLeft7 = rotateLeft(rotateLeft3 + m1196F(rotateLeft6, rotateLeft5, rotateLeft4) + this.f1178X[6], 11);
        int rotateLeft8 = rotateLeft(rotateLeft4 + m1196F(rotateLeft7, rotateLeft6, rotateLeft5) + this.f1178X[7], 19);
        int rotateLeft9 = rotateLeft(rotateLeft5 + m1196F(rotateLeft8, rotateLeft7, rotateLeft6) + this.f1178X[8], 3);
        int rotateLeft10 = rotateLeft(rotateLeft6 + m1196F(rotateLeft9, rotateLeft8, rotateLeft7) + this.f1178X[9], 7);
        int rotateLeft11 = rotateLeft(rotateLeft7 + m1196F(rotateLeft10, rotateLeft9, rotateLeft8) + this.f1178X[10], 11);
        int rotateLeft12 = rotateLeft(rotateLeft8 + m1196F(rotateLeft11, rotateLeft10, rotateLeft9) + this.f1178X[11], 19);
        int rotateLeft13 = rotateLeft(rotateLeft9 + m1196F(rotateLeft12, rotateLeft11, rotateLeft10) + this.f1178X[12], 3);
        int rotateLeft14 = rotateLeft(rotateLeft10 + m1196F(rotateLeft13, rotateLeft12, rotateLeft11) + this.f1178X[13], 7);
        int rotateLeft15 = rotateLeft(rotateLeft11 + m1196F(rotateLeft14, rotateLeft13, rotateLeft12) + this.f1178X[14], 11);
        int rotateLeft16 = rotateLeft(rotateLeft12 + m1196F(rotateLeft15, rotateLeft14, rotateLeft13) + this.f1178X[15], 19);
        int rotateLeft17 = rotateLeft(rotateLeft13 + m1197G(rotateLeft16, rotateLeft15, rotateLeft14) + this.f1178X[0] + 1518500249, 3);
        int rotateLeft18 = rotateLeft(rotateLeft14 + m1197G(rotateLeft17, rotateLeft16, rotateLeft15) + this.f1178X[4] + 1518500249, 5);
        int rotateLeft19 = rotateLeft(rotateLeft15 + m1197G(rotateLeft18, rotateLeft17, rotateLeft16) + this.f1178X[8] + 1518500249, 9);
        int rotateLeft20 = rotateLeft(rotateLeft16 + m1197G(rotateLeft19, rotateLeft18, rotateLeft17) + this.f1178X[12] + 1518500249, 13);
        int rotateLeft21 = rotateLeft(rotateLeft17 + m1197G(rotateLeft20, rotateLeft19, rotateLeft18) + this.f1178X[1] + 1518500249, 3);
        int rotateLeft22 = rotateLeft(rotateLeft18 + m1197G(rotateLeft21, rotateLeft20, rotateLeft19) + this.f1178X[5] + 1518500249, 5);
        int rotateLeft23 = rotateLeft(rotateLeft19 + m1197G(rotateLeft22, rotateLeft21, rotateLeft20) + this.f1178X[9] + 1518500249, 9);
        int rotateLeft24 = rotateLeft(rotateLeft20 + m1197G(rotateLeft23, rotateLeft22, rotateLeft21) + this.f1178X[13] + 1518500249, 13);
        int rotateLeft25 = rotateLeft(rotateLeft21 + m1197G(rotateLeft24, rotateLeft23, rotateLeft22) + this.f1178X[2] + 1518500249, 3);
        int rotateLeft26 = rotateLeft(rotateLeft22 + m1197G(rotateLeft25, rotateLeft24, rotateLeft23) + this.f1178X[6] + 1518500249, 5);
        int rotateLeft27 = rotateLeft(rotateLeft23 + m1197G(rotateLeft26, rotateLeft25, rotateLeft24) + this.f1178X[10] + 1518500249, 9);
        int rotateLeft28 = rotateLeft(rotateLeft24 + m1197G(rotateLeft27, rotateLeft26, rotateLeft25) + this.f1178X[14] + 1518500249, 13);
        int rotateLeft29 = rotateLeft(rotateLeft25 + m1197G(rotateLeft28, rotateLeft27, rotateLeft26) + this.f1178X[3] + 1518500249, 3);
        int rotateLeft30 = rotateLeft(rotateLeft26 + m1197G(rotateLeft29, rotateLeft28, rotateLeft27) + this.f1178X[7] + 1518500249, 5);
        int rotateLeft31 = rotateLeft(rotateLeft27 + m1197G(rotateLeft30, rotateLeft29, rotateLeft28) + this.f1178X[11] + 1518500249, 9);
        int rotateLeft32 = rotateLeft(rotateLeft28 + m1197G(rotateLeft31, rotateLeft30, rotateLeft29) + this.f1178X[15] + 1518500249, 13);
        int rotateLeft33 = rotateLeft(rotateLeft29 + m1198H(rotateLeft32, rotateLeft31, rotateLeft30) + this.f1178X[0] + 1859775393, 3);
        int rotateLeft34 = rotateLeft(rotateLeft30 + m1198H(rotateLeft33, rotateLeft32, rotateLeft31) + this.f1178X[8] + 1859775393, 9);
        int rotateLeft35 = rotateLeft(rotateLeft31 + m1198H(rotateLeft34, rotateLeft33, rotateLeft32) + this.f1178X[4] + 1859775393, 11);
        int rotateLeft36 = rotateLeft(rotateLeft32 + m1198H(rotateLeft35, rotateLeft34, rotateLeft33) + this.f1178X[12] + 1859775393, 15);
        int rotateLeft37 = rotateLeft(rotateLeft33 + m1198H(rotateLeft36, rotateLeft35, rotateLeft34) + this.f1178X[2] + 1859775393, 3);
        int rotateLeft38 = rotateLeft(rotateLeft34 + m1198H(rotateLeft37, rotateLeft36, rotateLeft35) + this.f1178X[10] + 1859775393, 9);
        int rotateLeft39 = rotateLeft(rotateLeft35 + m1198H(rotateLeft38, rotateLeft37, rotateLeft36) + this.f1178X[6] + 1859775393, 11);
        int rotateLeft40 = rotateLeft(rotateLeft36 + m1198H(rotateLeft39, rotateLeft38, rotateLeft37) + this.f1178X[14] + 1859775393, 15);
        int rotateLeft41 = rotateLeft(rotateLeft37 + m1198H(rotateLeft40, rotateLeft39, rotateLeft38) + this.f1178X[1] + 1859775393, 3);
        int rotateLeft42 = rotateLeft(rotateLeft38 + m1198H(rotateLeft41, rotateLeft40, rotateLeft39) + this.f1178X[9] + 1859775393, 9);
        int rotateLeft43 = rotateLeft(rotateLeft39 + m1198H(rotateLeft42, rotateLeft41, rotateLeft40) + this.f1178X[5] + 1859775393, 11);
        int rotateLeft44 = rotateLeft(rotateLeft40 + m1198H(rotateLeft43, rotateLeft42, rotateLeft41) + this.f1178X[13] + 1859775393, 15);
        int rotateLeft45 = rotateLeft(rotateLeft41 + m1198H(rotateLeft44, rotateLeft43, rotateLeft42) + this.f1178X[3] + 1859775393, 3);
        int rotateLeft46 = rotateLeft(rotateLeft42 + m1198H(rotateLeft45, rotateLeft44, rotateLeft43) + this.f1178X[11] + 1859775393, 9);
        int rotateLeft47 = rotateLeft(rotateLeft43 + m1198H(rotateLeft46, rotateLeft45, rotateLeft44) + this.f1178X[7] + 1859775393, 11);
        int rotateLeft48 = rotateLeft(rotateLeft44 + m1198H(rotateLeft47, rotateLeft46, rotateLeft45) + this.f1178X[15] + 1859775393, 15);
        this.H1 += rotateLeft45;
        this.H2 += rotateLeft48;
        this.H3 += rotateLeft47;
        this.H4 += rotateLeft46;
        this.xOff = 0;
        int i6 = 0;
        while (true) {
            int[] iArr = this.f1178X;
            if (i6 == iArr.length) {
                return;
            }
            iArr[i6] = 0;
            i6++;
        }
    }

    @Override // org.bouncycastle.crypto.digests.GeneralDigest
    public void processLength(long j2) {
        if (this.xOff > 14) {
            processBlock();
        }
        int[] iArr = this.f1178X;
        iArr[14] = (int) ((-1) & j2);
        iArr[15] = (int) (j2 >>> 32);
    }

    @Override // org.bouncycastle.crypto.digests.GeneralDigest
    public void processWord(byte[] bArr, int i2) {
        int[] iArr = this.f1178X;
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
        this.H1 = 1732584193;
        this.H2 = -271733879;
        this.H3 = -1732584194;
        this.H4 = 271733878;
        this.xOff = 0;
        int i2 = 0;
        while (true) {
            int[] iArr = this.f1178X;
            if (i2 == iArr.length) {
                return;
            }
            iArr[i2] = 0;
            i2++;
        }
    }

    public MD4Digest(MD4Digest mD4Digest) {
        super(mD4Digest);
        this.f1178X = new int[16];
        copyIn(mD4Digest);
    }

    @Override // org.bouncycastle.util.Memoable
    public void reset(Memoable memoable) {
        copyIn((MD4Digest) memoable);
    }
}

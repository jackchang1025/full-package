package org.bouncycastle.crypto.digests;

import org.bouncycastle.util.Memoable;
import org.bouncycastle.util.Pack;

/* loaded from: classes.dex */
public class MD5Digest extends GeneralDigest implements EncodableDigest {
    private static final int DIGEST_LENGTH = 16;
    private static final int S11 = 7;
    private static final int S12 = 12;
    private static final int S13 = 17;
    private static final int S14 = 22;
    private static final int S21 = 5;
    private static final int S22 = 9;
    private static final int S23 = 14;
    private static final int S24 = 20;
    private static final int S31 = 4;
    private static final int S32 = 11;
    private static final int S33 = 16;
    private static final int S34 = 23;
    private static final int S41 = 6;
    private static final int S42 = 10;
    private static final int S43 = 15;
    private static final int S44 = 21;
    private int H1;
    private int H2;
    private int H3;
    private int H4;

    /* renamed from: X */
    private int[] f1179X;
    private int xOff;

    public MD5Digest() {
        this.f1179X = new int[16];
        reset();
    }

    /* renamed from: F */
    private int m1199F(int i2, int i3, int i4) {
        return ((~i2) & i4) | (i3 & i2);
    }

    /* renamed from: G */
    private int m1200G(int i2, int i3, int i4) {
        return (i2 & i4) | (i3 & (~i4));
    }

    /* renamed from: H */
    private int m1201H(int i2, int i3, int i4) {
        return (i2 ^ i3) ^ i4;
    }

    /* renamed from: K */
    private int m1202K(int i2, int i3, int i4) {
        return (i2 | (~i4)) ^ i3;
    }

    private void copyIn(MD5Digest mD5Digest) {
        super.copyIn((GeneralDigest) mD5Digest);
        this.H1 = mD5Digest.H1;
        this.H2 = mD5Digest.H2;
        this.H3 = mD5Digest.H3;
        this.H4 = mD5Digest.H4;
        int[] iArr = mD5Digest.f1179X;
        System.arraycopy(iArr, 0, this.f1179X, 0, iArr.length);
        this.xOff = mD5Digest.xOff;
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
        return new MD5Digest(this);
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
        return "MD5";
    }

    @Override // org.bouncycastle.crypto.Digest
    public int getDigestSize() {
        return 16;
    }

    @Override // org.bouncycastle.crypto.digests.EncodableDigest
    public byte[] getEncodedState() {
        byte[] bArr = new byte[(this.xOff * 4) + 36];
        super.populateState(bArr);
        Pack.intToBigEndian(this.H1, bArr, 16);
        Pack.intToBigEndian(this.H2, bArr, 20);
        Pack.intToBigEndian(this.H3, bArr, 24);
        Pack.intToBigEndian(this.H4, bArr, 28);
        Pack.intToBigEndian(this.xOff, bArr, 32);
        for (int i2 = 0; i2 != this.xOff; i2++) {
            Pack.intToBigEndian(this.f1179X[i2], bArr, (i2 * 4) + 36);
        }
        return bArr;
    }

    @Override // org.bouncycastle.crypto.digests.GeneralDigest
    public void processBlock() {
        int i2 = this.H1;
        int i3 = this.H2;
        int i4 = this.H3;
        int i5 = this.H4;
        int rotateLeft = rotateLeft(((i2 + m1199F(i3, i4, i5)) + this.f1179X[0]) - 680876936, 7) + i3;
        int rotateLeft2 = rotateLeft(((i5 + m1199F(rotateLeft, i3, i4)) + this.f1179X[1]) - 389564586, 12) + rotateLeft;
        int rotateLeft3 = rotateLeft(i4 + m1199F(rotateLeft2, rotateLeft, i3) + this.f1179X[2] + 606105819, 17) + rotateLeft2;
        int rotateLeft4 = rotateLeft(((i3 + m1199F(rotateLeft3, rotateLeft2, rotateLeft)) + this.f1179X[3]) - 1044525330, 22) + rotateLeft3;
        int rotateLeft5 = rotateLeft(((rotateLeft + m1199F(rotateLeft4, rotateLeft3, rotateLeft2)) + this.f1179X[4]) - 176418897, 7) + rotateLeft4;
        int rotateLeft6 = rotateLeft(rotateLeft2 + m1199F(rotateLeft5, rotateLeft4, rotateLeft3) + this.f1179X[5] + 1200080426, 12) + rotateLeft5;
        int rotateLeft7 = rotateLeft(((rotateLeft3 + m1199F(rotateLeft6, rotateLeft5, rotateLeft4)) + this.f1179X[6]) - 1473231341, 17) + rotateLeft6;
        int rotateLeft8 = rotateLeft(((rotateLeft4 + m1199F(rotateLeft7, rotateLeft6, rotateLeft5)) + this.f1179X[7]) - 45705983, 22) + rotateLeft7;
        int rotateLeft9 = rotateLeft(rotateLeft5 + m1199F(rotateLeft8, rotateLeft7, rotateLeft6) + this.f1179X[8] + 1770035416, 7) + rotateLeft8;
        int rotateLeft10 = rotateLeft(((rotateLeft6 + m1199F(rotateLeft9, rotateLeft8, rotateLeft7)) + this.f1179X[9]) - 1958414417, 12) + rotateLeft9;
        int rotateLeft11 = rotateLeft(((rotateLeft7 + m1199F(rotateLeft10, rotateLeft9, rotateLeft8)) + this.f1179X[10]) - 42063, 17) + rotateLeft10;
        int rotateLeft12 = rotateLeft(((rotateLeft8 + m1199F(rotateLeft11, rotateLeft10, rotateLeft9)) + this.f1179X[11]) - 1990404162, 22) + rotateLeft11;
        int rotateLeft13 = rotateLeft(rotateLeft9 + m1199F(rotateLeft12, rotateLeft11, rotateLeft10) + this.f1179X[12] + 1804603682, 7) + rotateLeft12;
        int rotateLeft14 = rotateLeft(((rotateLeft10 + m1199F(rotateLeft13, rotateLeft12, rotateLeft11)) + this.f1179X[13]) - 40341101, 12) + rotateLeft13;
        int rotateLeft15 = rotateLeft(((rotateLeft11 + m1199F(rotateLeft14, rotateLeft13, rotateLeft12)) + this.f1179X[14]) - 1502002290, 17) + rotateLeft14;
        int rotateLeft16 = rotateLeft(rotateLeft12 + m1199F(rotateLeft15, rotateLeft14, rotateLeft13) + this.f1179X[15] + 1236535329, 22) + rotateLeft15;
        int rotateLeft17 = rotateLeft(((rotateLeft13 + m1200G(rotateLeft16, rotateLeft15, rotateLeft14)) + this.f1179X[1]) - 165796510, 5) + rotateLeft16;
        int rotateLeft18 = rotateLeft(((rotateLeft14 + m1200G(rotateLeft17, rotateLeft16, rotateLeft15)) + this.f1179X[6]) - 1069501632, 9) + rotateLeft17;
        int rotateLeft19 = rotateLeft(rotateLeft15 + m1200G(rotateLeft18, rotateLeft17, rotateLeft16) + this.f1179X[11] + 643717713, 14) + rotateLeft18;
        int rotateLeft20 = rotateLeft(((rotateLeft16 + m1200G(rotateLeft19, rotateLeft18, rotateLeft17)) + this.f1179X[0]) - 373897302, 20) + rotateLeft19;
        int rotateLeft21 = rotateLeft(((rotateLeft17 + m1200G(rotateLeft20, rotateLeft19, rotateLeft18)) + this.f1179X[5]) - 701558691, 5) + rotateLeft20;
        int rotateLeft22 = rotateLeft(rotateLeft18 + m1200G(rotateLeft21, rotateLeft20, rotateLeft19) + this.f1179X[10] + 38016083, 9) + rotateLeft21;
        int rotateLeft23 = rotateLeft(((rotateLeft19 + m1200G(rotateLeft22, rotateLeft21, rotateLeft20)) + this.f1179X[15]) - 660478335, 14) + rotateLeft22;
        int rotateLeft24 = rotateLeft(((rotateLeft20 + m1200G(rotateLeft23, rotateLeft22, rotateLeft21)) + this.f1179X[4]) - 405537848, 20) + rotateLeft23;
        int rotateLeft25 = rotateLeft(rotateLeft21 + m1200G(rotateLeft24, rotateLeft23, rotateLeft22) + this.f1179X[9] + 568446438, 5) + rotateLeft24;
        int rotateLeft26 = rotateLeft(((rotateLeft22 + m1200G(rotateLeft25, rotateLeft24, rotateLeft23)) + this.f1179X[14]) - 1019803690, 9) + rotateLeft25;
        int rotateLeft27 = rotateLeft(((rotateLeft23 + m1200G(rotateLeft26, rotateLeft25, rotateLeft24)) + this.f1179X[3]) - 187363961, 14) + rotateLeft26;
        int rotateLeft28 = rotateLeft(rotateLeft24 + m1200G(rotateLeft27, rotateLeft26, rotateLeft25) + this.f1179X[8] + 1163531501, 20) + rotateLeft27;
        int rotateLeft29 = rotateLeft(((rotateLeft25 + m1200G(rotateLeft28, rotateLeft27, rotateLeft26)) + this.f1179X[13]) - 1444681467, 5) + rotateLeft28;
        int rotateLeft30 = rotateLeft(((rotateLeft26 + m1200G(rotateLeft29, rotateLeft28, rotateLeft27)) + this.f1179X[2]) - 51403784, 9) + rotateLeft29;
        int rotateLeft31 = rotateLeft(rotateLeft27 + m1200G(rotateLeft30, rotateLeft29, rotateLeft28) + this.f1179X[7] + 1735328473, 14) + rotateLeft30;
        int rotateLeft32 = rotateLeft(((rotateLeft28 + m1200G(rotateLeft31, rotateLeft30, rotateLeft29)) + this.f1179X[12]) - 1926607734, 20) + rotateLeft31;
        int rotateLeft33 = rotateLeft(((rotateLeft29 + m1201H(rotateLeft32, rotateLeft31, rotateLeft30)) + this.f1179X[5]) - 378558, 4) + rotateLeft32;
        int rotateLeft34 = rotateLeft(((rotateLeft30 + m1201H(rotateLeft33, rotateLeft32, rotateLeft31)) + this.f1179X[8]) - 2022574463, 11) + rotateLeft33;
        int rotateLeft35 = rotateLeft(rotateLeft31 + m1201H(rotateLeft34, rotateLeft33, rotateLeft32) + this.f1179X[11] + 1839030562, 16) + rotateLeft34;
        int rotateLeft36 = rotateLeft(((rotateLeft32 + m1201H(rotateLeft35, rotateLeft34, rotateLeft33)) + this.f1179X[14]) - 35309556, 23) + rotateLeft35;
        int rotateLeft37 = rotateLeft(((rotateLeft33 + m1201H(rotateLeft36, rotateLeft35, rotateLeft34)) + this.f1179X[1]) - 1530992060, 4) + rotateLeft36;
        int rotateLeft38 = rotateLeft(rotateLeft34 + m1201H(rotateLeft37, rotateLeft36, rotateLeft35) + this.f1179X[4] + 1272893353, 11) + rotateLeft37;
        int rotateLeft39 = rotateLeft(((rotateLeft35 + m1201H(rotateLeft38, rotateLeft37, rotateLeft36)) + this.f1179X[7]) - 155497632, 16) + rotateLeft38;
        int rotateLeft40 = rotateLeft(((rotateLeft36 + m1201H(rotateLeft39, rotateLeft38, rotateLeft37)) + this.f1179X[10]) - 1094730640, 23) + rotateLeft39;
        int rotateLeft41 = rotateLeft(rotateLeft37 + m1201H(rotateLeft40, rotateLeft39, rotateLeft38) + this.f1179X[13] + 681279174, 4) + rotateLeft40;
        int rotateLeft42 = rotateLeft(((rotateLeft38 + m1201H(rotateLeft41, rotateLeft40, rotateLeft39)) + this.f1179X[0]) - 358537222, 11) + rotateLeft41;
        int rotateLeft43 = rotateLeft(((rotateLeft39 + m1201H(rotateLeft42, rotateLeft41, rotateLeft40)) + this.f1179X[3]) - 722521979, 16) + rotateLeft42;
        int rotateLeft44 = rotateLeft(rotateLeft40 + m1201H(rotateLeft43, rotateLeft42, rotateLeft41) + this.f1179X[6] + 76029189, 23) + rotateLeft43;
        int rotateLeft45 = rotateLeft(((rotateLeft41 + m1201H(rotateLeft44, rotateLeft43, rotateLeft42)) + this.f1179X[9]) - 640364487, 4) + rotateLeft44;
        int rotateLeft46 = rotateLeft(((rotateLeft42 + m1201H(rotateLeft45, rotateLeft44, rotateLeft43)) + this.f1179X[12]) - 421815835, 11) + rotateLeft45;
        int rotateLeft47 = rotateLeft(rotateLeft43 + m1201H(rotateLeft46, rotateLeft45, rotateLeft44) + this.f1179X[15] + 530742520, 16) + rotateLeft46;
        int rotateLeft48 = rotateLeft(((rotateLeft44 + m1201H(rotateLeft47, rotateLeft46, rotateLeft45)) + this.f1179X[2]) - 995338651, 23) + rotateLeft47;
        int rotateLeft49 = rotateLeft(((rotateLeft45 + m1202K(rotateLeft48, rotateLeft47, rotateLeft46)) + this.f1179X[0]) - 198630844, 6) + rotateLeft48;
        int rotateLeft50 = rotateLeft(rotateLeft46 + m1202K(rotateLeft49, rotateLeft48, rotateLeft47) + this.f1179X[7] + 1126891415, 10) + rotateLeft49;
        int rotateLeft51 = rotateLeft(((rotateLeft47 + m1202K(rotateLeft50, rotateLeft49, rotateLeft48)) + this.f1179X[14]) - 1416354905, 15) + rotateLeft50;
        int rotateLeft52 = rotateLeft(((rotateLeft48 + m1202K(rotateLeft51, rotateLeft50, rotateLeft49)) + this.f1179X[5]) - 57434055, 21) + rotateLeft51;
        int rotateLeft53 = rotateLeft(rotateLeft49 + m1202K(rotateLeft52, rotateLeft51, rotateLeft50) + this.f1179X[12] + 1700485571, 6) + rotateLeft52;
        int rotateLeft54 = rotateLeft(((rotateLeft50 + m1202K(rotateLeft53, rotateLeft52, rotateLeft51)) + this.f1179X[3]) - 1894986606, 10) + rotateLeft53;
        int rotateLeft55 = rotateLeft(((rotateLeft51 + m1202K(rotateLeft54, rotateLeft53, rotateLeft52)) + this.f1179X[10]) - 1051523, 15) + rotateLeft54;
        int rotateLeft56 = rotateLeft(((rotateLeft52 + m1202K(rotateLeft55, rotateLeft54, rotateLeft53)) + this.f1179X[1]) - 2054922799, 21) + rotateLeft55;
        int rotateLeft57 = rotateLeft(rotateLeft53 + m1202K(rotateLeft56, rotateLeft55, rotateLeft54) + this.f1179X[8] + 1873313359, 6) + rotateLeft56;
        int rotateLeft58 = rotateLeft(((rotateLeft54 + m1202K(rotateLeft57, rotateLeft56, rotateLeft55)) + this.f1179X[15]) - 30611744, 10) + rotateLeft57;
        int rotateLeft59 = rotateLeft(((rotateLeft55 + m1202K(rotateLeft58, rotateLeft57, rotateLeft56)) + this.f1179X[6]) - 1560198380, 15) + rotateLeft58;
        int rotateLeft60 = rotateLeft(rotateLeft56 + m1202K(rotateLeft59, rotateLeft58, rotateLeft57) + this.f1179X[13] + 1309151649, 21) + rotateLeft59;
        int rotateLeft61 = rotateLeft(((rotateLeft57 + m1202K(rotateLeft60, rotateLeft59, rotateLeft58)) + this.f1179X[4]) - 145523070, 6) + rotateLeft60;
        int rotateLeft62 = rotateLeft(((rotateLeft58 + m1202K(rotateLeft61, rotateLeft60, rotateLeft59)) + this.f1179X[11]) - 1120210379, 10) + rotateLeft61;
        int rotateLeft63 = rotateLeft(rotateLeft59 + m1202K(rotateLeft62, rotateLeft61, rotateLeft60) + this.f1179X[2] + 718787259, 15) + rotateLeft62;
        int rotateLeft64 = rotateLeft(((rotateLeft60 + m1202K(rotateLeft63, rotateLeft62, rotateLeft61)) + this.f1179X[9]) - 343485551, 21) + rotateLeft63;
        this.H1 += rotateLeft61;
        this.H2 += rotateLeft64;
        this.H3 += rotateLeft63;
        this.H4 += rotateLeft62;
        this.xOff = 0;
        int i6 = 0;
        while (true) {
            int[] iArr = this.f1179X;
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
        int[] iArr = this.f1179X;
        iArr[14] = (int) ((-1) & j2);
        iArr[15] = (int) (j2 >>> 32);
    }

    @Override // org.bouncycastle.crypto.digests.GeneralDigest
    public void processWord(byte[] bArr, int i2) {
        int[] iArr = this.f1179X;
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
            int[] iArr = this.f1179X;
            if (i2 == iArr.length) {
                return;
            }
            iArr[i2] = 0;
            i2++;
        }
    }

    public MD5Digest(MD5Digest mD5Digest) {
        super(mD5Digest);
        this.f1179X = new int[16];
        copyIn(mD5Digest);
    }

    @Override // org.bouncycastle.util.Memoable
    public void reset(Memoable memoable) {
        copyIn((MD5Digest) memoable);
    }

    public MD5Digest(byte[] bArr) {
        super(bArr);
        this.f1179X = new int[16];
        this.H1 = Pack.bigEndianToInt(bArr, 16);
        this.H2 = Pack.bigEndianToInt(bArr, 20);
        this.H3 = Pack.bigEndianToInt(bArr, 24);
        this.H4 = Pack.bigEndianToInt(bArr, 28);
        this.xOff = Pack.bigEndianToInt(bArr, 32);
        for (int i2 = 0; i2 != this.xOff; i2++) {
            this.f1179X[i2] = Pack.bigEndianToInt(bArr, (i2 * 4) + 36);
        }
    }
}

package org.bouncycastle.crypto.digests;

import android.sun.security.util.DerValue;
import org.bouncycastle.crypto.ExtendedDigest;
import org.bouncycastle.crypto.signers.PSSSigner;
import org.bouncycastle.math.ec.Tnaf;
import org.bouncycastle.util.Memoable;

/* loaded from: classes.dex */
public class MD2Digest implements ExtendedDigest, Memoable {
    private static final int DIGEST_LENGTH = 16;

    /* renamed from: S */
    private static final byte[] f1174S = {41, 46, 67, -55, -94, -40, 124, 1, 61, 54, 84, -95, -20, -16, 6, DerValue.tag_PrintableString, 98, -89, 5, -13, DerValue.TAG_PRIVATE, -57, 115, -116, -104, -109, 43, -39, PSSSigner.TRAILER_IMPLICIT, 76, -126, -54, DerValue.tag_BMPString, -101, 87, 60, -3, -44, -32, DerValue.tag_IA5String, 103, 66, 111, DerValue.tag_GeneralizedTime, -118, DerValue.tag_UtcTime, -27, 18, -66, 78, -60, -42, -38, -98, -34, 73, -96, -5, -11, -114, -69, 47, -18, 122, -87, 104, 121, -111, 21, -78, 7, 63, -108, -62, Tnaf.POW_2_WIDTH, -119, 11, 34, 95, 33, DerValue.TAG_CONTEXT, Byte.MAX_VALUE, 93, -102, 90, -112, 50, 39, 53, 62, -52, -25, -65, -9, -105, 3, -1, 25, 48, -77, 72, -91, -75, -47, -41, 94, -110, 42, -84, 86, -86, -58, 79, -72, 56, -46, -106, -92, 125, -74, 118, -4, 107, -30, -100, 116, 4, -15, 69, -99, 112, 89, 100, 113, -121, 32, -122, 91, -49, 101, -26, 45, -88, 2, DerValue.tag_GeneralString, 96, 37, -83, -82, -80, -71, -10, DerValue.tag_UniversalString, 70, 97, 105, 52, DerValue.TAG_APPLICATION, 126, 15, 85, 71, -93, 35, -35, 81, -81, 58, -61, 92, -7, -50, -70, -59, -22, 38, 44, 83, 13, 110, -123, 40, -124, 9, -45, -33, -51, -12, 65, -127, 77, 82, 106, -36, 55, -56, 108, -63, -85, -6, 36, -31, 123, 8, DerValue.tag_UTF8String, -67, -79, 74, 120, -120, -107, -117, -29, 99, -24, 109, -23, -53, -43, -2, 59, 0, 29, 57, -14, -17, -73, 14, 102, 88, -48, -28, -90, 119, 114, -8, -21, 117, 75, 10, 49, 68, 80, -76, -113, -19, 31, 26, -37, -103, -115, 51, -97, 17, -125, DerValue.tag_T61String};

    /* renamed from: C */
    private byte[] f1175C;
    private int COff;

    /* renamed from: M */
    private byte[] f1176M;

    /* renamed from: X */
    private byte[] f1177X;
    private int mOff;
    private int xOff;

    public MD2Digest() {
        this.f1177X = new byte[48];
        this.f1176M = new byte[16];
        this.f1175C = new byte[16];
        reset();
    }

    private void copyIn(MD2Digest mD2Digest) {
        byte[] bArr = mD2Digest.f1177X;
        System.arraycopy(bArr, 0, this.f1177X, 0, bArr.length);
        this.xOff = mD2Digest.xOff;
        byte[] bArr2 = mD2Digest.f1176M;
        System.arraycopy(bArr2, 0, this.f1176M, 0, bArr2.length);
        this.mOff = mD2Digest.mOff;
        byte[] bArr3 = mD2Digest.f1175C;
        System.arraycopy(bArr3, 0, this.f1175C, 0, bArr3.length);
        this.COff = mD2Digest.COff;
    }

    @Override // org.bouncycastle.util.Memoable
    public Memoable copy() {
        return new MD2Digest(this);
    }

    @Override // org.bouncycastle.crypto.Digest
    public int doFinal(byte[] bArr, int i2) {
        int length = this.f1176M.length;
        int i3 = this.mOff;
        byte b = (byte) (length - i3);
        while (true) {
            byte[] bArr2 = this.f1176M;
            if (i3 >= bArr2.length) {
                processCheckSum(bArr2);
                processBlock(this.f1176M);
                processBlock(this.f1175C);
                System.arraycopy(this.f1177X, this.xOff, bArr, i2, 16);
                reset();
                return 16;
            }
            bArr2[i3] = b;
            i3++;
        }
    }

    @Override // org.bouncycastle.crypto.Digest
    public String getAlgorithmName() {
        return "MD2";
    }

    @Override // org.bouncycastle.crypto.ExtendedDigest
    public int getByteLength() {
        return 16;
    }

    @Override // org.bouncycastle.crypto.Digest
    public int getDigestSize() {
        return 16;
    }

    public void processBlock(byte[] bArr) {
        for (int i2 = 0; i2 < 16; i2++) {
            byte[] bArr2 = this.f1177X;
            bArr2[i2 + 16] = bArr[i2];
            bArr2[i2 + 32] = (byte) (bArr[i2] ^ bArr2[i2]);
        }
        int i3 = 0;
        for (int i4 = 0; i4 < 18; i4++) {
            for (int i5 = 0; i5 < 48; i5++) {
                byte[] bArr3 = this.f1177X;
                byte b = (byte) (f1174S[i3] ^ bArr3[i5]);
                bArr3[i5] = b;
                i3 = b & 255;
            }
            i3 = (i3 + i4) % 256;
        }
    }

    public void processCheckSum(byte[] bArr) {
        byte b = this.f1175C[15];
        for (int i2 = 0; i2 < 16; i2++) {
            byte[] bArr2 = this.f1175C;
            b = (byte) (f1174S[(b ^ bArr[i2]) & 255] ^ bArr2[i2]);
            bArr2[i2] = b;
        }
    }

    @Override // org.bouncycastle.crypto.Digest
    public void reset() {
        this.xOff = 0;
        int i2 = 0;
        while (true) {
            byte[] bArr = this.f1177X;
            if (i2 == bArr.length) {
                break;
            }
            bArr[i2] = 0;
            i2++;
        }
        this.mOff = 0;
        int i3 = 0;
        while (true) {
            byte[] bArr2 = this.f1176M;
            if (i3 == bArr2.length) {
                break;
            }
            bArr2[i3] = 0;
            i3++;
        }
        this.COff = 0;
        int i4 = 0;
        while (true) {
            byte[] bArr3 = this.f1175C;
            if (i4 == bArr3.length) {
                return;
            }
            bArr3[i4] = 0;
            i4++;
        }
    }

    @Override // org.bouncycastle.crypto.Digest
    public void update(byte b) {
        byte[] bArr = this.f1176M;
        int i2 = this.mOff;
        int i3 = i2 + 1;
        this.mOff = i3;
        bArr[i2] = b;
        if (i3 == 16) {
            processCheckSum(bArr);
            processBlock(this.f1176M);
            this.mOff = 0;
        }
    }

    public MD2Digest(MD2Digest mD2Digest) {
        this.f1177X = new byte[48];
        this.f1176M = new byte[16];
        this.f1175C = new byte[16];
        copyIn(mD2Digest);
    }

    @Override // org.bouncycastle.util.Memoable
    public void reset(Memoable memoable) {
        copyIn((MD2Digest) memoable);
    }

    @Override // org.bouncycastle.crypto.Digest
    public void update(byte[] bArr, int i2, int i3) {
        while (this.mOff != 0 && i3 > 0) {
            update(bArr[i2]);
            i2++;
            i3--;
        }
        while (i3 > 16) {
            System.arraycopy(bArr, i2, this.f1176M, 0, 16);
            processCheckSum(this.f1176M);
            processBlock(this.f1176M);
            i3 -= 16;
            i2 += 16;
        }
        while (i3 > 0) {
            update(bArr[i2]);
            i2++;
            i3--;
        }
    }
}

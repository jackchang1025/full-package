package org.bouncycastle.crypto.engines;

import android.sun.security.util.DerValue;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.OutputLengthException;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.signers.PSSSigner;
import org.bouncycastle.math.ec.Tnaf;
import org.bouncycastle.util.Pack;
import p012o.AbstractC0413b;

/* loaded from: classes.dex */
public class SM4Engine implements BlockCipher {
    private static final int BLOCK_SIZE = 16;

    /* renamed from: X */
    private final int[] f1224X = new int[4];
    private int[] rk;
    private static final byte[] Sbox = {-42, -112, -23, -2, -52, -31, 61, -73, DerValue.tag_IA5String, -74, DerValue.tag_T61String, -62, 40, -5, 44, 5, 43, 103, -102, 118, 42, -66, 4, -61, -86, 68, DerValue.tag_PrintableString, 38, 73, -122, 6, -103, -100, 66, 80, -12, -111, -17, -104, 122, 51, 84, 11, 67, -19, -49, -84, 98, -28, -77, DerValue.tag_UniversalString, -87, -55, 8, -24, -107, DerValue.TAG_CONTEXT, -33, -108, -6, 117, -113, 63, -90, 71, 7, -89, -4, -13, 115, DerValue.tag_UtcTime, -70, -125, 89, 60, 25, -26, -123, 79, -88, 104, 107, -127, -78, 113, 100, -38, -117, -8, -21, 15, 75, 112, 86, -99, 53, DerValue.tag_BMPString, 36, 14, 94, 99, 88, -47, -94, 37, 34, 124, 59, 1, 33, 120, -121, -44, 0, 70, 87, -97, -45, 39, 82, 76, 54, 2, -25, -96, -60, -56, -98, -22, -65, -118, -46, DerValue.TAG_APPLICATION, -57, 56, -75, -93, -9, -14, -50, -7, 97, 21, -95, -32, -82, 93, -92, -101, 52, 26, 85, -83, -109, 50, 48, -11, -116, -79, -29, 29, -10, -30, 46, -126, 102, -54, 96, DerValue.TAG_PRIVATE, 41, 35, -85, 13, 83, 78, 111, -43, -37, 55, 69, -34, -3, -114, 47, 3, -1, 106, 114, 109, 108, 91, 81, -115, DerValue.tag_GeneralString, -81, -110, -69, -35, PSSSigner.TRAILER_IMPLICIT, Byte.MAX_VALUE, 17, -39, 92, 65, 31, Tnaf.POW_2_WIDTH, 90, -40, 10, -63, 49, -120, -91, -51, 123, -67, 45, 116, -48, 18, -72, -27, -76, -80, -119, 105, -105, 74, DerValue.tag_UTF8String, -106, 119, 126, 101, -71, -15, 9, -59, 110, -58, -124, DerValue.tag_GeneralizedTime, -16, 125, -20, 58, -36, 77, 32, 121, -18, 95, 62, -41, -53, 57, 72};
    private static final int[] CK = {462357, 472066609, 943670861, 1415275113, 1886879365, -1936483679, -1464879427, -993275175, -521670923, -66909679, 404694573, 876298825, 1347903077, 1819507329, -2003855715, -1532251463, -1060647211, -589042959, -117504499, 337322537, 808926789, 1280531041, 1752135293, -2071227751, -1599623499, -1128019247, -656414995, -184876535, 269950501, 741554753, 1213159005, 1684763257};
    private static final int[] FK = {-1548633402, 1453994832, 1736282519, -1301273892};

    private int F0(int[] iArr, int i2) {
        return m1218T((iArr[3] ^ (iArr[1] ^ iArr[2])) ^ i2) ^ iArr[0];
    }

    private int F1(int[] iArr, int i2) {
        return m1218T((iArr[0] ^ (iArr[2] ^ iArr[3])) ^ i2) ^ iArr[1];
    }

    private int F2(int[] iArr, int i2) {
        return m1218T((iArr[1] ^ (iArr[3] ^ iArr[0])) ^ i2) ^ iArr[2];
    }

    private int F3(int[] iArr, int i2) {
        return m1218T((iArr[2] ^ (iArr[0] ^ iArr[1])) ^ i2) ^ iArr[3];
    }

    /* renamed from: L */
    private int m1217L(int i2) {
        return rotateLeft(i2, 24) ^ (((rotateLeft(i2, 2) ^ i2) ^ rotateLeft(i2, 10)) ^ rotateLeft(i2, 18));
    }

    private int L_ap(int i2) {
        return rotateLeft(i2, 23) ^ (rotateLeft(i2, 13) ^ i2);
    }

    /* renamed from: T */
    private int m1218T(int i2) {
        return m1217L(tau(i2));
    }

    private int T_ap(int i2) {
        return L_ap(tau(i2));
    }

    private int[] expandKey(boolean z2, byte[] bArr) {
        int[] iArr = new int[32];
        int bigEndianToInt = Pack.bigEndianToInt(bArr, 12);
        int[] iArr2 = {Pack.bigEndianToInt(bArr, 0), Pack.bigEndianToInt(bArr, 4), Pack.bigEndianToInt(bArr, 8), bigEndianToInt};
        int i2 = iArr2[0];
        int[] iArr3 = FK;
        int i3 = i2 ^ iArr3[0];
        int i4 = iArr2[1] ^ iArr3[1];
        int i5 = iArr2[2] ^ iArr3[2];
        int i6 = bigEndianToInt ^ iArr3[3];
        int[] iArr4 = {i3, i4, i5, i6};
        if (z2) {
            int i7 = (i4 ^ i5) ^ i6;
            int[] iArr5 = CK;
            int T_ap = T_ap(i7 ^ iArr5[0]) ^ i3;
            iArr[0] = T_ap;
            int T_ap2 = T_ap((T_ap ^ (iArr4[2] ^ iArr4[3])) ^ iArr5[1]) ^ iArr4[1];
            iArr[1] = T_ap2;
            int T_ap3 = T_ap((T_ap2 ^ (iArr4[3] ^ iArr[0])) ^ iArr5[2]) ^ iArr4[2];
            iArr[2] = T_ap3;
            iArr[3] = T_ap((T_ap3 ^ (iArr[0] ^ iArr[1])) ^ iArr5[3]) ^ iArr4[3];
            for (int i8 = 4; i8 < 32; i8++) {
                iArr[i8] = iArr[i8 - 4] ^ T_ap(((iArr[i8 - 3] ^ iArr[i8 - 2]) ^ iArr[i8 - 1]) ^ CK[i8]);
            }
        } else {
            int i9 = (i4 ^ i5) ^ i6;
            int[] iArr6 = CK;
            int T_ap4 = T_ap(i9 ^ iArr6[0]) ^ i3;
            iArr[31] = T_ap4;
            int T_ap5 = T_ap((T_ap4 ^ (iArr4[2] ^ iArr4[3])) ^ iArr6[1]) ^ iArr4[1];
            iArr[30] = T_ap5;
            int T_ap6 = T_ap((T_ap5 ^ (iArr4[3] ^ iArr[31])) ^ iArr6[2]) ^ iArr4[2];
            iArr[29] = T_ap6;
            iArr[28] = T_ap((T_ap6 ^ (iArr[31] ^ iArr[30])) ^ iArr6[3]) ^ iArr4[3];
            for (int i10 = 27; i10 >= 0; i10--) {
                iArr[i10] = iArr[i10 + 4] ^ T_ap(((iArr[i10 + 3] ^ iArr[i10 + 2]) ^ iArr[i10 + 1]) ^ CK[31 - i10]);
            }
        }
        return iArr;
    }

    private int rotateLeft(int i2, int i3) {
        return (i2 >>> (-i3)) | (i2 << i3);
    }

    private int tau(int i2) {
        byte[] bArr = Sbox;
        return (bArr[i2 & 255] & 255) | ((bArr[(i2 >> 24) & 255] & 255) << 24) | ((bArr[(i2 >> 16) & 255] & 255) << 16) | ((bArr[(i2 >> 8) & 255] & 255) << 8);
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public String getAlgorithmName() {
        return "SM4";
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public int getBlockSize() {
        return 16;
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public void init(boolean z2, CipherParameters cipherParameters) {
        if (!(cipherParameters instanceof KeyParameter)) {
            throw new IllegalArgumentException(AbstractC0413b.m1014h(cipherParameters, "invalid parameter passed to SM4 init - "));
        }
        byte[] key = ((KeyParameter) cipherParameters).getKey();
        if (key.length != 16) {
            throw new IllegalArgumentException("SM4 requires a 128 bit key");
        }
        this.rk = expandKey(z2, key);
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public int processBlock(byte[] bArr, int i2, byte[] bArr2, int i3) {
        if (this.rk == null) {
            throw new IllegalStateException("SM4 not initialised");
        }
        if (i2 + 16 > bArr.length) {
            throw new DataLengthException("input buffer too short");
        }
        if (i3 + 16 > bArr2.length) {
            throw new OutputLengthException("output buffer too short");
        }
        this.f1224X[0] = Pack.bigEndianToInt(bArr, i2);
        this.f1224X[1] = Pack.bigEndianToInt(bArr, i2 + 4);
        this.f1224X[2] = Pack.bigEndianToInt(bArr, i2 + 8);
        this.f1224X[3] = Pack.bigEndianToInt(bArr, i2 + 12);
        for (int i4 = 0; i4 < 32; i4 += 4) {
            int[] iArr = this.f1224X;
            iArr[0] = F0(iArr, this.rk[i4]);
            int[] iArr2 = this.f1224X;
            iArr2[1] = F1(iArr2, this.rk[i4 + 1]);
            int[] iArr3 = this.f1224X;
            iArr3[2] = F2(iArr3, this.rk[i4 + 2]);
            int[] iArr4 = this.f1224X;
            iArr4[3] = F3(iArr4, this.rk[i4 + 3]);
        }
        Pack.intToBigEndian(this.f1224X[3], bArr2, i3);
        Pack.intToBigEndian(this.f1224X[2], bArr2, i3 + 4);
        Pack.intToBigEndian(this.f1224X[1], bArr2, i3 + 8);
        Pack.intToBigEndian(this.f1224X[0], bArr2, i3 + 12);
        return 16;
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public void reset() {
    }
}

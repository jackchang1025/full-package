package org.bouncycastle.crypto.engines;

import android.sun.security.util.DerValue;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.OutputLengthException;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.signers.PSSSigner;
import org.bouncycastle.math.ec.Tnaf;
import org.bouncycastle.tls.CipherSuite;
import org.bouncycastle.util.Integers;
import org.bouncycastle.util.Pack;
import p012o.AbstractC0413b;

/* loaded from: classes.dex */
public final class TwofishEngine implements BlockCipher {
    private static final int BLOCK_SIZE = 16;
    private static final int GF256_FDBK = 361;
    private static final int GF256_FDBK_2 = 180;
    private static final int GF256_FDBK_4 = 90;
    private static final int INPUT_WHITEN = 0;
    private static final int MAX_KEY_BITS = 256;
    private static final int MAX_ROUNDS = 16;
    private static final int OUTPUT_WHITEN = 4;

    /* renamed from: P */
    private static final byte[][] f1229P = {new byte[]{-87, 103, -77, -24, 4, -3, -93, 118, -102, -110, DerValue.TAG_CONTEXT, 120, -28, -35, -47, 56, 13, -58, 53, -104, DerValue.tag_GeneralizedTime, -9, -20, 108, 67, 117, 55, 38, -6, DerValue.tag_PrintableString, -108, 72, -14, -48, -117, 48, -124, 84, -33, 35, 25, 91, 61, 89, -13, -82, -94, -126, 99, 1, -125, 46, -39, 81, -101, 124, -90, -21, -91, -66, DerValue.tag_IA5String, DerValue.tag_UTF8String, -29, 97, DerValue.TAG_PRIVATE, -116, 58, -11, 115, 44, 37, 11, -69, 78, -119, 107, 83, 106, -76, -15, -31, -26, -67, 69, -30, -12, -74, 102, -52, -107, 3, 86, -44, DerValue.tag_UniversalString, DerValue.tag_BMPString, -41, -5, -61, -114, -75, -23, -49, -65, -70, -22, 119, 57, -81, 51, -55, 98, 113, -127, 121, 9, -83, 36, -51, -7, -40, -27, -59, -71, 77, 68, 8, -122, -25, -95, 29, -86, -19, 6, 112, -78, -46, 65, 123, -96, 17, 49, -62, 39, -112, 32, -10, 96, -1, -106, 92, -79, -85, -98, -100, 82, DerValue.tag_GeneralString, 95, -109, 10, -17, -111, -123, 73, -18, 45, 79, -113, 59, 71, -121, 109, 70, -42, 62, 105, 100, 42, -50, -53, 47, -4, -105, 5, 122, -84, Byte.MAX_VALUE, -43, 26, 75, 14, -89, 90, 40, DerValue.tag_T61String, 63, 41, -120, 60, 76, 2, -72, -38, -80, DerValue.tag_UtcTime, 85, 31, -118, 125, 87, -57, -115, 116, -73, -60, -97, 114, 126, 21, 34, 18, 88, 7, -103, 52, 110, 80, -34, 104, 101, PSSSigner.TRAILER_IMPLICIT, -37, -8, -56, -88, 43, DerValue.TAG_APPLICATION, -36, -2, 50, -92, -54, Tnaf.POW_2_WIDTH, 33, -16, -45, 93, 15, 0, 111, -99, 54, 66, 74, 94, -63, -32}, new byte[]{117, -13, -58, -12, -37, 123, -5, -56, 74, -45, -26, 107, 69, 125, -24, 75, -42, 50, -40, -3, 55, 113, -15, -31, 48, 15, -8, DerValue.tag_GeneralString, -121, -6, 6, 63, 94, -70, -82, 91, -118, 0, PSSSigner.TRAILER_IMPLICIT, -99, 109, -63, -79, 14, DerValue.TAG_CONTEXT, 93, -46, -43, -96, -124, 7, DerValue.tag_T61String, -75, -112, 44, -93, -78, 115, 76, 84, -110, 116, 54, 81, 56, -80, -67, 90, -4, 96, 98, -106, 108, 66, -9, Tnaf.POW_2_WIDTH, 124, 40, 39, -116, DerValue.tag_PrintableString, -107, -100, -57, 36, 70, 59, 112, -54, -29, -123, -53, 17, -48, -109, -72, -90, -125, 32, -1, -97, 119, -61, -52, 3, 111, 8, -65, DerValue.TAG_APPLICATION, -25, 43, -30, 121, DerValue.tag_UTF8String, -86, -126, 65, 58, -22, -71, -28, -102, -92, -105, 126, -38, 122, DerValue.tag_UtcTime, 102, -108, -95, 29, 61, -16, -34, -77, 11, 114, -89, DerValue.tag_UniversalString, -17, -47, 83, 62, -113, 51, 38, 95, -20, 118, 42, 73, -127, -120, -18, 33, -60, 26, -21, -39, -59, 57, -103, -51, -83, 49, -117, 1, DerValue.tag_GeneralizedTime, 35, -35, 31, 78, 45, -7, 72, 79, -14, 101, -114, 120, 92, 88, 25, -115, -27, -104, 87, 103, Byte.MAX_VALUE, 5, 100, -81, 99, -74, -2, -11, -73, 60, -91, -50, -23, 104, 68, -32, 77, 67, 105, 41, 46, -84, 21, 89, -88, 10, -98, 110, 71, -33, 52, 53, 106, -49, -36, 34, -55, DerValue.TAG_PRIVATE, -101, -119, -44, -19, -85, 18, -94, 13, 82, -69, 2, 47, -87, -41, 97, DerValue.tag_BMPString, -76, 80, 4, -10, -62, DerValue.tag_IA5String, 37, -122, 86, 85, 9, -66, -111}};
    private static final int P_00 = 1;
    private static final int P_01 = 0;
    private static final int P_02 = 0;
    private static final int P_03 = 1;
    private static final int P_04 = 1;
    private static final int P_10 = 0;
    private static final int P_11 = 0;
    private static final int P_12 = 1;
    private static final int P_13 = 1;
    private static final int P_14 = 0;
    private static final int P_20 = 1;
    private static final int P_21 = 1;
    private static final int P_22 = 0;
    private static final int P_23 = 0;
    private static final int P_24 = 0;
    private static final int P_30 = 0;
    private static final int P_31 = 1;
    private static final int P_32 = 1;
    private static final int P_33 = 0;
    private static final int P_34 = 1;
    private static final int ROUNDS = 16;
    private static final int ROUND_SUBKEYS = 8;
    private static final int RS_GF_FDBK = 333;
    private static final int SK_BUMP = 16843009;
    private static final int SK_ROTL = 9;
    private static final int SK_STEP = 33686018;
    private static final int TOTAL_SUBKEYS = 40;
    private int[] gSBox;
    private int[] gSubKeys;
    private boolean encrypting = false;
    private int[] gMDS0 = new int[256];
    private int[] gMDS1 = new int[256];
    private int[] gMDS2 = new int[256];
    private int[] gMDS3 = new int[256];
    private int k64Cnt = 0;
    private byte[] workingKey = null;

    public TwofishEngine() {
        int[] iArr = new int[2];
        int[] iArr2 = new int[2];
        int[] iArr3 = new int[2];
        for (int i2 = 0; i2 < 256; i2++) {
            byte[][] bArr = f1229P;
            int i3 = bArr[0][i2] & 255;
            iArr[0] = i3;
            iArr2[0] = Mx_X(i3) & 255;
            iArr3[0] = Mx_Y(i3) & 255;
            int i4 = bArr[1][i2] & 255;
            iArr[1] = i4;
            iArr2[1] = Mx_X(i4) & 255;
            int Mx_Y = Mx_Y(i4) & 255;
            iArr3[1] = Mx_Y;
            this.gMDS0[i2] = (Mx_Y << 24) | iArr[1] | (iArr2[1] << 8) | (Mx_Y << 16);
            int[] iArr4 = this.gMDS1;
            int i5 = iArr3[0];
            iArr4[i2] = i5 | (i5 << 8) | (iArr2[0] << 16) | (iArr[0] << 24);
            int[] iArr5 = this.gMDS2;
            int i6 = iArr2[1];
            int i7 = iArr3[1];
            iArr5[i2] = (iArr[1] << 16) | i6 | (i7 << 8) | (i7 << 24);
            int[] iArr6 = this.gMDS3;
            int i8 = iArr2[0];
            iArr6[i2] = (i8 << 24) | (iArr[0] << 8) | i8 | (iArr3[0] << 16);
        }
    }

    private int F32(int i2, int[] iArr) {
        int i3;
        int i4;
        int b02 = b0(i2);
        int b12 = b1(i2);
        int b2 = b2(i2);
        int b3 = b3(i2);
        int i5 = iArr[0];
        int i6 = iArr[1];
        int i7 = iArr[2];
        int i8 = iArr[3];
        int i9 = this.k64Cnt & 3;
        if (i9 != 0) {
            if (i9 == 1) {
                int[] iArr2 = this.gMDS0;
                byte[][] bArr = f1229P;
                i3 = (iArr2[(bArr[0][b02] & 255) ^ b0(i5)] ^ this.gMDS1[(bArr[0][b12] & 255) ^ b1(i5)]) ^ this.gMDS2[(bArr[1][b2] & 255) ^ b2(i5)];
                i4 = this.gMDS3[(bArr[1][b3] & 255) ^ b3(i5)];
                return i3 ^ i4;
            }
            if (i9 != 2) {
                if (i9 != 3) {
                    return 0;
                }
            }
            int[] iArr3 = this.gMDS0;
            byte[][] bArr2 = f1229P;
            byte[] bArr3 = bArr2[0];
            i3 = (iArr3[(bArr3[(bArr3[b02] & 255) ^ b0(i6)] & 255) ^ b0(i5)] ^ this.gMDS1[(bArr2[0][(bArr2[1][b12] & 255) ^ b1(i6)] & 255) ^ b1(i5)]) ^ this.gMDS2[(bArr2[1][(bArr2[0][b2] & 255) ^ b2(i6)] & 255) ^ b2(i5)];
            int[] iArr4 = this.gMDS3;
            byte[] bArr4 = bArr2[1];
            i4 = iArr4[(bArr4[(bArr4[b3] & 255) ^ b3(i6)] & 255) ^ b3(i5)];
            return i3 ^ i4;
        }
        byte[][] bArr5 = f1229P;
        b02 = (bArr5[1][b02] & 255) ^ b0(i8);
        b12 = (bArr5[0][b12] & 255) ^ b1(i8);
        b2 = (bArr5[0][b2] & 255) ^ b2(i8);
        b3 = (bArr5[1][b3] & 255) ^ b3(i8);
        byte[][] bArr6 = f1229P;
        b02 = (bArr6[1][b02] & 255) ^ b0(i7);
        b12 = (bArr6[1][b12] & 255) ^ b1(i7);
        b2 = (bArr6[0][b2] & 255) ^ b2(i7);
        b3 = (bArr6[0][b3] & 255) ^ b3(i7);
        int[] iArr32 = this.gMDS0;
        byte[][] bArr22 = f1229P;
        byte[] bArr32 = bArr22[0];
        i3 = (iArr32[(bArr32[(bArr32[b02] & 255) ^ b0(i6)] & 255) ^ b0(i5)] ^ this.gMDS1[(bArr22[0][(bArr22[1][b12] & 255) ^ b1(i6)] & 255) ^ b1(i5)]) ^ this.gMDS2[(bArr22[1][(bArr22[0][b2] & 255) ^ b2(i6)] & 255) ^ b2(i5)];
        int[] iArr42 = this.gMDS3;
        byte[] bArr42 = bArr22[1];
        i4 = iArr42[(bArr42[(bArr42[b3] & 255) ^ b3(i6)] & 255) ^ b3(i5)];
        return i3 ^ i4;
    }

    private int Fe32_0(int i2) {
        int[] iArr = this.gSBox;
        return iArr[(((i2 >>> 24) & 255) * 2) + 513] ^ ((iArr[((i2 & 255) * 2) + 0] ^ iArr[(((i2 >>> 8) & 255) * 2) + 1]) ^ iArr[(((i2 >>> 16) & 255) * 2) + 512]);
    }

    private int Fe32_3(int i2) {
        int[] iArr = this.gSBox;
        return iArr[(((i2 >>> 16) & 255) * 2) + 513] ^ ((iArr[(((i2 >>> 24) & 255) * 2) + 0] ^ iArr[((i2 & 255) * 2) + 1]) ^ iArr[(((i2 >>> 8) & 255) * 2) + 512]);
    }

    private int LFSR1(int i2) {
        return ((i2 & 1) != 0 ? 180 : 0) ^ (i2 >> 1);
    }

    private int LFSR2(int i2) {
        return ((i2 >> 2) ^ ((i2 & 2) != 0 ? 180 : 0)) ^ ((i2 & 1) != 0 ? GF256_FDBK_4 : 0);
    }

    private int Mx_X(int i2) {
        return i2 ^ LFSR2(i2);
    }

    private int Mx_Y(int i2) {
        return LFSR2(i2) ^ (LFSR1(i2) ^ i2);
    }

    private int RS_MDS_Encode(int i2, int i3) {
        for (int i4 = 0; i4 < 4; i4++) {
            i3 = RS_rem(i3);
        }
        int i5 = i2 ^ i3;
        for (int i6 = 0; i6 < 4; i6++) {
            i5 = RS_rem(i5);
        }
        return i5;
    }

    private int RS_rem(int i2) {
        int i3 = (i2 >>> 24) & 255;
        int i4 = ((i3 << 1) ^ ((i3 & 128) != 0 ? RS_GF_FDBK : 0)) & 255;
        int i5 = ((i3 >>> 1) ^ ((i3 & 1) != 0 ? CipherSuite.TLS_DH_anon_WITH_AES_128_GCM_SHA256 : 0)) ^ i4;
        return ((((i2 << 8) ^ (i5 << 24)) ^ (i4 << 16)) ^ (i5 << 8)) ^ i3;
    }

    private int b0(int i2) {
        return i2 & 255;
    }

    private int b1(int i2) {
        return (i2 >>> 8) & 255;
    }

    private int b2(int i2) {
        return (i2 >>> 16) & 255;
    }

    private int b3(int i2) {
        return (i2 >>> 24) & 255;
    }

    private void decryptBlock(byte[] bArr, int i2, byte[] bArr2, int i3) {
        int littleEndianToInt = Pack.littleEndianToInt(bArr, i2) ^ this.gSubKeys[4];
        int littleEndianToInt2 = Pack.littleEndianToInt(bArr, i2 + 4) ^ this.gSubKeys[5];
        int littleEndianToInt3 = Pack.littleEndianToInt(bArr, i2 + 8) ^ this.gSubKeys[6];
        int littleEndianToInt4 = Pack.littleEndianToInt(bArr, i2 + 12) ^ this.gSubKeys[7];
        int i4 = 39;
        int i5 = 0;
        while (i5 < 16) {
            int Fe32_0 = Fe32_0(littleEndianToInt);
            int Fe32_3 = Fe32_3(littleEndianToInt2);
            int i6 = i4 - 1;
            int i7 = littleEndianToInt4 ^ (((Fe32_3 * 2) + Fe32_0) + this.gSubKeys[i4]);
            int i8 = Fe32_0 + Fe32_3;
            int i9 = i6 - 1;
            littleEndianToInt3 = Integers.rotateLeft(littleEndianToInt3, 1) ^ (i8 + this.gSubKeys[i6]);
            littleEndianToInt4 = Integers.rotateRight(i7, 1);
            int Fe32_02 = Fe32_0(littleEndianToInt3);
            int Fe32_32 = Fe32_3(littleEndianToInt4);
            int i10 = i9 - 1;
            int i11 = littleEndianToInt2 ^ (((Fe32_32 * 2) + Fe32_02) + this.gSubKeys[i9]);
            littleEndianToInt = Integers.rotateLeft(littleEndianToInt, 1) ^ ((Fe32_02 + Fe32_32) + this.gSubKeys[i10]);
            littleEndianToInt2 = Integers.rotateRight(i11, 1);
            i5 += 2;
            i4 = i10 - 1;
        }
        Pack.intToLittleEndian(this.gSubKeys[0] ^ littleEndianToInt3, bArr2, i3);
        Pack.intToLittleEndian(littleEndianToInt4 ^ this.gSubKeys[1], bArr2, i3 + 4);
        Pack.intToLittleEndian(this.gSubKeys[2] ^ littleEndianToInt, bArr2, i3 + 8);
        Pack.intToLittleEndian(this.gSubKeys[3] ^ littleEndianToInt2, bArr2, i3 + 12);
    }

    private void encryptBlock(byte[] bArr, int i2, byte[] bArr2, int i3) {
        int i4 = 0;
        int littleEndianToInt = Pack.littleEndianToInt(bArr, i2) ^ this.gSubKeys[0];
        int littleEndianToInt2 = Pack.littleEndianToInt(bArr, i2 + 4) ^ this.gSubKeys[1];
        int littleEndianToInt3 = Pack.littleEndianToInt(bArr, i2 + 8) ^ this.gSubKeys[2];
        int littleEndianToInt4 = Pack.littleEndianToInt(bArr, i2 + 12) ^ this.gSubKeys[3];
        int i5 = 8;
        while (i4 < 16) {
            int Fe32_0 = Fe32_0(littleEndianToInt);
            int Fe32_3 = Fe32_3(littleEndianToInt2);
            int i6 = i5 + 1;
            littleEndianToInt3 = Integers.rotateRight(littleEndianToInt3 ^ ((Fe32_0 + Fe32_3) + this.gSubKeys[i5]), 1);
            int i7 = (Fe32_3 * 2) + Fe32_0;
            int i8 = i6 + 1;
            littleEndianToInt4 = Integers.rotateLeft(littleEndianToInt4, 1) ^ (i7 + this.gSubKeys[i6]);
            int Fe32_02 = Fe32_0(littleEndianToInt3);
            int Fe32_32 = Fe32_3(littleEndianToInt4);
            int i9 = i8 + 1;
            littleEndianToInt = Integers.rotateRight(littleEndianToInt ^ ((Fe32_02 + Fe32_32) + this.gSubKeys[i8]), 1);
            littleEndianToInt2 = Integers.rotateLeft(littleEndianToInt2, 1) ^ (((Fe32_32 * 2) + Fe32_02) + this.gSubKeys[i9]);
            i4 += 2;
            i5 = i9 + 1;
        }
        Pack.intToLittleEndian(this.gSubKeys[4] ^ littleEndianToInt3, bArr2, i3);
        Pack.intToLittleEndian(littleEndianToInt4 ^ this.gSubKeys[5], bArr2, i3 + 4);
        Pack.intToLittleEndian(this.gSubKeys[6] ^ littleEndianToInt, bArr2, i3 + 8);
        Pack.intToLittleEndian(this.gSubKeys[7] ^ littleEndianToInt2, bArr2, i3 + 12);
    }

    private void setKey(byte[] bArr) {
        int b02;
        int b12;
        int b2;
        int b3;
        int i2;
        int i3;
        int i4;
        int i5;
        int[] iArr = new int[4];
        int[] iArr2 = new int[4];
        int[] iArr3 = new int[4];
        this.gSubKeys = new int[40];
        for (int i6 = 0; i6 < this.k64Cnt; i6++) {
            int i7 = i6 * 8;
            iArr[i6] = Pack.littleEndianToInt(bArr, i7);
            int littleEndianToInt = Pack.littleEndianToInt(bArr, i7 + 4);
            iArr2[i6] = littleEndianToInt;
            iArr3[(this.k64Cnt - 1) - i6] = RS_MDS_Encode(iArr[i6], littleEndianToInt);
        }
        for (int i8 = 0; i8 < 20; i8++) {
            int i9 = SK_STEP * i8;
            int F32 = F32(i9, iArr);
            int rotateLeft = Integers.rotateLeft(F32(i9 + 16843009, iArr2), 8);
            int i10 = F32 + rotateLeft;
            int[] iArr4 = this.gSubKeys;
            int i11 = i8 * 2;
            iArr4[i11] = i10;
            int i12 = i10 + rotateLeft;
            iArr4[i11 + 1] = (i12 << 9) | (i12 >>> 23);
        }
        int i13 = iArr3[0];
        int i14 = iArr3[1];
        int i15 = 2;
        int i16 = iArr3[2];
        int i17 = iArr3[3];
        this.gSBox = new int[1024];
        int i18 = 0;
        while (i18 < 256) {
            int i19 = this.k64Cnt & 3;
            if (i19 != 0) {
                if (i19 == 1) {
                    int[] iArr5 = this.gSBox;
                    int i20 = i18 * 2;
                    int[] iArr6 = this.gMDS0;
                    byte[][] bArr2 = f1229P;
                    iArr5[i20] = iArr6[(bArr2[0][i18] & 255) ^ b0(i13)];
                    this.gSBox[i20 + 1] = this.gMDS1[(bArr2[0][i18] & 255) ^ b1(i13)];
                    this.gSBox[i20 + 512] = this.gMDS2[(bArr2[1][i18] & 255) ^ b2(i13)];
                    this.gSBox[i20 + 513] = this.gMDS3[(bArr2[1][i18] & 255) ^ b3(i13)];
                } else if (i19 == i15) {
                    i5 = i18;
                    i4 = i5;
                    i3 = i4;
                    i2 = i3;
                    int[] iArr7 = this.gSBox;
                    int i21 = i18 * 2;
                    int[] iArr8 = this.gMDS0;
                    byte[][] bArr3 = f1229P;
                    byte[] bArr4 = bArr3[0];
                    iArr7[i21] = iArr8[(bArr4[(bArr4[i4] & 255) ^ b0(i14)] & 255) ^ b0(i13)];
                    this.gSBox[i21 + 1] = this.gMDS1[(bArr3[0][(bArr3[1][i3] & 255) ^ b1(i14)] & 255) ^ b1(i13)];
                    this.gSBox[i21 + 512] = this.gMDS2[(bArr3[1][(bArr3[0][i2] & 255) ^ b2(i14)] & 255) ^ b2(i13)];
                    int[] iArr9 = this.gMDS3;
                    byte[] bArr5 = bArr3[1];
                    this.gSBox[i21 + 513] = iArr9[(bArr5[(bArr5[i5] & 255) ^ b3(i14)] & 255) ^ b3(i13)];
                } else if (i19 == 3) {
                    b3 = i18;
                    b02 = b3;
                    b12 = b02;
                    b2 = b12;
                }
                i18++;
                i15 = 2;
            } else {
                byte[][] bArr6 = f1229P;
                b02 = (bArr6[1][i18] & 255) ^ b0(i17);
                b12 = (bArr6[0][i18] & 255) ^ b1(i17);
                b2 = (bArr6[0][i18] & 255) ^ b2(i17);
                b3 = (bArr6[1][i18] & 255) ^ b3(i17);
            }
            byte[][] bArr7 = f1229P;
            i4 = (bArr7[1][b02] & 255) ^ b0(i16);
            i3 = (bArr7[1][b12] & 255) ^ b1(i16);
            i2 = (bArr7[0][b2] & 255) ^ b2(i16);
            i5 = (bArr7[0][b3] & 255) ^ b3(i16);
            int[] iArr72 = this.gSBox;
            int i212 = i18 * 2;
            int[] iArr82 = this.gMDS0;
            byte[][] bArr32 = f1229P;
            byte[] bArr42 = bArr32[0];
            iArr72[i212] = iArr82[(bArr42[(bArr42[i4] & 255) ^ b0(i14)] & 255) ^ b0(i13)];
            this.gSBox[i212 + 1] = this.gMDS1[(bArr32[0][(bArr32[1][i3] & 255) ^ b1(i14)] & 255) ^ b1(i13)];
            this.gSBox[i212 + 512] = this.gMDS2[(bArr32[1][(bArr32[0][i2] & 255) ^ b2(i14)] & 255) ^ b2(i13)];
            int[] iArr92 = this.gMDS3;
            byte[] bArr52 = bArr32[1];
            this.gSBox[i212 + 513] = iArr92[(bArr52[(bArr52[i5] & 255) ^ b3(i14)] & 255) ^ b3(i13)];
            i18++;
            i15 = 2;
        }
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public String getAlgorithmName() {
        return "Twofish";
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public int getBlockSize() {
        return 16;
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public void init(boolean z2, CipherParameters cipherParameters) {
        if (!(cipherParameters instanceof KeyParameter)) {
            throw new IllegalArgumentException(AbstractC0413b.m1014h(cipherParameters, "invalid parameter passed to Twofish init - "));
        }
        this.encrypting = z2;
        byte[] key = ((KeyParameter) cipherParameters).getKey();
        this.workingKey = key;
        int length = key.length * 8;
        if (length != 128 && length != 192 && length != 256) {
            throw new IllegalArgumentException("Key length not 128/192/256 bits.");
        }
        this.k64Cnt = key.length / 8;
        setKey(key);
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public int processBlock(byte[] bArr, int i2, byte[] bArr2, int i3) {
        if (this.workingKey == null) {
            throw new IllegalStateException("Twofish not initialised");
        }
        if (i2 + 16 > bArr.length) {
            throw new DataLengthException("input buffer too short");
        }
        if (i3 + 16 > bArr2.length) {
            throw new OutputLengthException("output buffer too short");
        }
        if (this.encrypting) {
            encryptBlock(bArr, i2, bArr2, i3);
            return 16;
        }
        decryptBlock(bArr, i2, bArr2, i3);
        return 16;
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public void reset() {
        byte[] bArr = this.workingKey;
        if (bArr != null) {
            setKey(bArr);
        }
    }
}

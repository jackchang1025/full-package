package org.bouncycastle.crypto.engines;

import android.sun.security.util.DerValue;
import java.lang.reflect.Array;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.OutputLengthException;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.signers.PSSSigner;
import org.bouncycastle.math.ec.Tnaf;
import org.bouncycastle.tls.CipherSuite;
import org.bouncycastle.util.encoders.Hex;
import p012o.AbstractC0413b;

/* loaded from: classes.dex */
public class ARIAEngine implements BlockCipher {
    protected static final int BLOCK_SIZE = 16;

    /* renamed from: C */
    private static final byte[][] f1205C = {Hex.decodeStrict("517cc1b727220a94fe13abe8fa9a6ee0"), Hex.decodeStrict("6db14acc9e21c820ff28b1d5ef5de2b0"), Hex.decodeStrict("db92371d2126e9700324977504e8c90e")};
    private static final byte[] SB1_sbox = {99, 124, 119, 123, -14, 107, 111, -59, 48, 1, 103, 43, -2, -41, -85, 118, -54, -126, -55, 125, -6, 89, 71, -16, -83, -44, -94, -81, -100, -92, 114, DerValue.TAG_PRIVATE, -73, -3, -109, 38, 54, 63, -9, -52, 52, -91, -27, -15, 113, -40, 49, 21, 4, -57, 35, -61, DerValue.tag_GeneralizedTime, -106, 5, -102, 7, 18, DerValue.TAG_CONTEXT, -30, -21, 39, -78, 117, 9, -125, 44, 26, DerValue.tag_GeneralString, 110, 90, -96, 82, 59, -42, -77, 41, -29, 47, -124, 83, -47, 0, -19, 32, -4, -79, 91, 106, -53, -66, 57, 74, 76, 88, -49, -48, -17, -86, -5, 67, 77, 51, -123, 69, -7, 2, Byte.MAX_VALUE, 80, 60, -97, -88, 81, -93, DerValue.TAG_APPLICATION, -113, -110, -99, 56, -11, PSSSigner.TRAILER_IMPLICIT, -74, -38, 33, Tnaf.POW_2_WIDTH, -1, -13, -46, -51, DerValue.tag_UTF8String, DerValue.tag_PrintableString, -20, 95, -105, 68, DerValue.tag_UtcTime, -60, -89, 126, 61, 100, 93, 25, 115, 96, -127, 79, -36, 34, 42, -112, -120, 70, -18, -72, DerValue.tag_T61String, -34, 94, 11, -37, -32, 50, 58, 10, 73, 6, 36, 92, -62, -45, -84, 98, -111, -107, -28, 121, -25, -56, 55, 109, -115, -43, 78, -87, 108, 86, -12, -22, 101, 122, -82, 8, -70, 120, 37, 46, DerValue.tag_UniversalString, -90, -76, -58, -24, -35, 116, 31, 75, -67, -117, -118, 112, 62, -75, 102, 72, 3, -10, 14, 97, 53, 87, -71, -122, -63, 29, -98, -31, -8, -104, 17, 105, -39, -114, -108, -101, DerValue.tag_BMPString, -121, -23, -50, 85, 40, -33, -116, -95, -119, 13, -65, -26, 66, 104, 65, -103, 45, 15, -80, 84, -69, DerValue.tag_IA5String};
    private static final byte[] SB2_sbox = {-30, 78, 84, -4, -108, -62, 74, -52, 98, 13, 106, 70, 60, 77, -117, -47, 94, -6, 100, -53, -76, -105, -66, 43, PSSSigner.TRAILER_IMPLICIT, 119, 46, 3, -45, 25, 89, -63, 29, 6, 65, 107, 85, -16, -103, 105, -22, -100, DerValue.tag_GeneralizedTime, -82, 99, -33, -25, -69, 0, 115, 102, -5, -106, 76, -123, -28, 58, 9, 69, -86, 15, -18, Tnaf.POW_2_WIDTH, -21, 45, Byte.MAX_VALUE, -12, 41, -84, -49, -83, -111, -115, 120, -56, -107, -7, 47, -50, -51, 8, 122, -120, 56, 92, -125, 42, 40, 71, -37, -72, -57, -109, -92, 18, 83, -1, -121, 14, 49, 54, 33, 88, 72, 1, -114, 55, 116, 50, -54, -23, -79, -73, -85, DerValue.tag_UTF8String, -41, -60, 86, 66, 38, 7, -104, 96, -39, -74, -71, 17, DerValue.TAG_APPLICATION, -20, 32, -116, -67, -96, -55, -124, 4, 73, 35, -15, 79, 80, 31, DerValue.tag_PrintableString, -36, -40, DerValue.TAG_PRIVATE, -98, 87, -29, -61, 123, 101, 59, 2, -113, 62, -24, 37, -110, -27, 21, -35, -3, DerValue.tag_UtcTime, -87, -65, -44, -102, 126, -59, 57, 103, -2, 118, -99, 67, -89, -31, -48, -11, 104, -14, DerValue.tag_GeneralString, 52, 112, 5, -93, -118, -43, 121, -122, -88, 48, -58, 81, 75, DerValue.tag_BMPString, -90, 39, -10, 53, -46, 110, 36, DerValue.tag_IA5String, -126, 95, -38, -26, 117, -94, -17, 44, -78, DerValue.tag_UniversalString, -97, 93, 111, DerValue.TAG_CONTEXT, 10, 114, 68, -101, 108, -112, 11, 91, 51, 125, 90, 82, -13, 97, -95, -9, -80, -42, 63, 124, 109, -19, DerValue.tag_T61String, -32, -91, 61, 34, -77, -8, -119, -34, 113, 26, -81, -70, -75, -127};
    private static final byte[] SB3_sbox = {82, 9, 106, -43, 48, 54, -91, 56, -65, DerValue.TAG_APPLICATION, -93, -98, -127, -13, -41, -5, 124, -29, 57, -126, -101, 47, -1, -121, 52, -114, 67, 68, -60, -34, -23, -53, 84, 123, -108, 50, -90, -62, 35, 61, -18, 76, -107, 11, 66, -6, -61, 78, 8, 46, -95, 102, 40, -39, 36, -78, 118, 91, -94, 73, 109, -117, -47, 37, 114, -8, -10, 100, -122, 104, -104, DerValue.tag_IA5String, -44, -92, 92, -52, 93, 101, -74, -110, 108, 112, 72, 80, -3, -19, -71, -38, 94, 21, 70, 87, -89, -115, -99, -124, -112, -40, -85, 0, -116, PSSSigner.TRAILER_IMPLICIT, -45, 10, -9, -28, 88, 5, -72, -77, 69, 6, -48, 44, DerValue.tag_BMPString, -113, -54, 63, 15, 2, -63, -81, -67, 3, 1, DerValue.tag_PrintableString, -118, 107, 58, -111, 17, 65, 79, 103, -36, -22, -105, -14, -49, -50, -16, -76, -26, 115, -106, -84, 116, 34, -25, -83, 53, -123, -30, -7, 55, -24, DerValue.tag_UniversalString, 117, -33, 110, 71, -15, 26, 113, 29, 41, -59, -119, 111, -73, 98, 14, -86, DerValue.tag_GeneralizedTime, -66, DerValue.tag_GeneralString, -4, 86, 62, 75, -58, -46, 121, 32, -102, -37, DerValue.TAG_PRIVATE, -2, 120, -51, 90, -12, 31, -35, -88, 51, -120, 7, -57, 49, -79, 18, Tnaf.POW_2_WIDTH, 89, 39, DerValue.TAG_CONTEXT, -20, 95, 96, 81, Byte.MAX_VALUE, -87, 25, -75, 74, 13, 45, -27, 122, -97, -109, -55, -100, -17, -96, -32, 59, 77, -82, 42, -11, -80, -56, -21, -69, 60, -125, 83, -103, 97, DerValue.tag_UtcTime, 43, 4, 126, -70, 119, -42, 38, -31, 105, DerValue.tag_T61String, 99, 85, 33, DerValue.tag_UTF8String, 125};
    private static final byte[] SB4_sbox = {48, 104, -103, DerValue.tag_GeneralString, -121, -71, 33, 120, 80, 57, -37, -31, 114, 9, 98, 60, 62, 126, 94, -114, -15, -96, -52, -93, 42, 29, -5, -74, -42, 32, -60, -115, -127, 101, -11, -119, -53, -99, 119, -58, 87, 67, 86, DerValue.tag_UtcTime, -44, DerValue.TAG_APPLICATION, 26, 77, DerValue.TAG_PRIVATE, 99, 108, -29, -73, -56, 100, 106, 83, -86, 56, -104, DerValue.tag_UTF8String, -12, -101, -19, Byte.MAX_VALUE, 34, 118, -81, -35, 58, 11, 88, 103, -120, 6, -61, 53, 13, 1, -117, -116, -62, -26, 95, 2, 36, 117, -109, 102, DerValue.tag_BMPString, -27, -30, 84, -40, Tnaf.POW_2_WIDTH, -50, 122, -24, 8, 44, 18, -105, 50, -85, -76, 39, 10, 35, -33, -17, -54, -39, -72, -6, -36, 49, 107, -47, -83, 25, 73, -67, 81, -106, -18, -28, -88, 65, -38, -1, -51, 85, -122, 54, -66, 97, 82, -8, -69, 14, -126, 72, 105, -102, -32, 71, -98, 92, 4, 75, 52, 21, 121, 38, -89, -34, 41, -82, -110, -41, -124, -23, -46, -70, 93, -13, -59, -80, -65, -92, 59, 113, 68, 70, 43, -4, -21, 111, -43, -10, DerValue.tag_T61String, -2, 124, 112, 90, 125, -3, 47, DerValue.tag_GeneralizedTime, -125, DerValue.tag_IA5String, -91, -111, 31, 5, -107, 116, -87, -63, 91, 74, -123, 109, DerValue.tag_PrintableString, 7, 79, 78, 69, -78, 15, -55, DerValue.tag_UniversalString, -90, PSSSigner.TRAILER_IMPLICIT, -20, 115, -112, 123, -49, 89, -113, -95, -7, 45, -14, -79, 0, -108, 55, -97, -48, 46, -100, 110, 40, 63, DerValue.TAG_CONTEXT, -16, 61, -45, 37, -118, -75, -25, 66, -77, -57, -22, -9, 76, 17, 51, 3, -94, -84, 96};
    private byte[][] roundKeys;

    /* renamed from: A */
    public static void m1206A(byte[] bArr) {
        byte b = bArr[0];
        byte b2 = bArr[1];
        byte b3 = bArr[2];
        byte b4 = bArr[3];
        byte b5 = bArr[4];
        byte b6 = bArr[5];
        byte b7 = bArr[6];
        byte b8 = bArr[7];
        byte b9 = bArr[8];
        byte b10 = bArr[9];
        byte b11 = bArr[10];
        byte b12 = bArr[11];
        byte b13 = bArr[12];
        byte b14 = bArr[13];
        byte b15 = bArr[14];
        byte b16 = bArr[15];
        bArr[0] = (byte) ((((((b4 ^ b5) ^ b7) ^ b9) ^ b10) ^ b14) ^ b15);
        bArr[1] = (byte) ((((((b3 ^ b6) ^ b8) ^ b9) ^ b10) ^ b13) ^ b16);
        bArr[2] = (byte) ((((((b2 ^ b5) ^ b7) ^ b11) ^ b12) ^ b13) ^ b16);
        bArr[3] = (byte) ((((((b ^ b6) ^ b8) ^ b11) ^ b12) ^ b14) ^ b15);
        int i2 = b ^ b3;
        bArr[4] = (byte) (((((i2 ^ b6) ^ b9) ^ b12) ^ b15) ^ b16);
        int i3 = b2 ^ b4;
        bArr[5] = (byte) (((((i3 ^ b5) ^ b10) ^ b11) ^ b15) ^ b16);
        bArr[6] = (byte) (((((i2 ^ b8) ^ b10) ^ b11) ^ b13) ^ b14);
        bArr[7] = (byte) (((((i3 ^ b7) ^ b9) ^ b12) ^ b13) ^ b14);
        int i4 = b ^ b2;
        bArr[8] = (byte) (((((i4 ^ b5) ^ b8) ^ b11) ^ b14) ^ b16);
        bArr[9] = (byte) (((((i4 ^ b6) ^ b7) ^ b12) ^ b13) ^ b15);
        int i5 = b3 ^ b4;
        bArr[10] = (byte) (((((i5 ^ b6) ^ b7) ^ b9) ^ b14) ^ b16);
        bArr[11] = (byte) (((((i5 ^ b5) ^ b8) ^ b10) ^ b13) ^ b15);
        int i6 = b2 ^ b3;
        bArr[12] = (byte) (((((i6 ^ b7) ^ b8) ^ b10) ^ b12) ^ b13);
        int i7 = b ^ b4;
        bArr[13] = (byte) (((((i7 ^ b7) ^ b8) ^ b9) ^ b11) ^ b14);
        bArr[14] = (byte) (((((i7 ^ b5) ^ b6) ^ b10) ^ b12) ^ b15);
        bArr[15] = (byte) (((((i6 ^ b5) ^ b6) ^ b9) ^ b11) ^ b16);
    }

    public static void FE(byte[] bArr, byte[] bArr2) {
        xor(bArr, bArr2);
        SL2(bArr);
        m1206A(bArr);
    }

    public static void FO(byte[] bArr, byte[] bArr2) {
        xor(bArr, bArr2);
        SL1(bArr);
        m1206A(bArr);
    }

    public static byte SB1(byte b) {
        return SB1_sbox[b & 255];
    }

    public static byte SB2(byte b) {
        return SB2_sbox[b & 255];
    }

    public static byte SB3(byte b) {
        return SB3_sbox[b & 255];
    }

    public static byte SB4(byte b) {
        return SB4_sbox[b & 255];
    }

    public static void SL1(byte[] bArr) {
        bArr[0] = SB1(bArr[0]);
        bArr[1] = SB2(bArr[1]);
        bArr[2] = SB3(bArr[2]);
        bArr[3] = SB4(bArr[3]);
        bArr[4] = SB1(bArr[4]);
        bArr[5] = SB2(bArr[5]);
        bArr[6] = SB3(bArr[6]);
        bArr[7] = SB4(bArr[7]);
        bArr[8] = SB1(bArr[8]);
        bArr[9] = SB2(bArr[9]);
        bArr[10] = SB3(bArr[10]);
        bArr[11] = SB4(bArr[11]);
        bArr[12] = SB1(bArr[12]);
        bArr[13] = SB2(bArr[13]);
        bArr[14] = SB3(bArr[14]);
        bArr[15] = SB4(bArr[15]);
    }

    public static void SL2(byte[] bArr) {
        bArr[0] = SB3(bArr[0]);
        bArr[1] = SB4(bArr[1]);
        bArr[2] = SB1(bArr[2]);
        bArr[3] = SB2(bArr[3]);
        bArr[4] = SB3(bArr[4]);
        bArr[5] = SB4(bArr[5]);
        bArr[6] = SB1(bArr[6]);
        bArr[7] = SB2(bArr[7]);
        bArr[8] = SB3(bArr[8]);
        bArr[9] = SB4(bArr[9]);
        bArr[10] = SB1(bArr[10]);
        bArr[11] = SB2(bArr[11]);
        bArr[12] = SB3(bArr[12]);
        bArr[13] = SB4(bArr[13]);
        bArr[14] = SB1(bArr[14]);
        bArr[15] = SB2(bArr[15]);
    }

    public static byte[][] keySchedule(boolean z2, byte[] bArr) {
        int length = bArr.length;
        if (length < 16 || length > 32 || (length & 7) != 0) {
            throw new IllegalArgumentException("Key length not 128/192/256 bits.");
        }
        int i2 = (length >>> 3) - 2;
        byte[][] bArr2 = f1205C;
        byte[] bArr3 = bArr2[i2];
        byte[] bArr4 = bArr2[(i2 + 1) % 3];
        byte[] bArr5 = bArr2[(i2 + 2) % 3];
        byte[] bArr6 = new byte[16];
        byte[] bArr7 = new byte[16];
        System.arraycopy(bArr, 0, bArr6, 0, 16);
        System.arraycopy(bArr, 16, bArr7, 0, length - 16);
        byte[] bArr8 = new byte[16];
        byte[] bArr9 = new byte[16];
        byte[] bArr10 = new byte[16];
        byte[] bArr11 = new byte[16];
        System.arraycopy(bArr6, 0, bArr8, 0, 16);
        System.arraycopy(bArr8, 0, bArr9, 0, 16);
        FO(bArr9, bArr3);
        xor(bArr9, bArr7);
        System.arraycopy(bArr9, 0, bArr10, 0, 16);
        FE(bArr10, bArr4);
        xor(bArr10, bArr8);
        System.arraycopy(bArr10, 0, bArr11, 0, 16);
        FO(bArr11, bArr5);
        xor(bArr11, bArr9);
        int i3 = (i2 * 2) + 12;
        byte[][] bArr12 = (byte[][]) Array.newInstance((Class<?>) Byte.TYPE, i3 + 1, 16);
        keyScheduleRound(bArr12[0], bArr8, bArr9, 19);
        keyScheduleRound(bArr12[1], bArr9, bArr10, 19);
        keyScheduleRound(bArr12[2], bArr10, bArr11, 19);
        keyScheduleRound(bArr12[3], bArr11, bArr8, 19);
        keyScheduleRound(bArr12[4], bArr8, bArr9, 31);
        keyScheduleRound(bArr12[5], bArr9, bArr10, 31);
        keyScheduleRound(bArr12[6], bArr10, bArr11, 31);
        keyScheduleRound(bArr12[7], bArr11, bArr8, 31);
        keyScheduleRound(bArr12[8], bArr8, bArr9, 67);
        keyScheduleRound(bArr12[9], bArr9, bArr10, 67);
        keyScheduleRound(bArr12[10], bArr10, bArr11, 67);
        keyScheduleRound(bArr12[11], bArr11, bArr8, 67);
        keyScheduleRound(bArr12[12], bArr8, bArr9, 97);
        if (i3 > 12) {
            keyScheduleRound(bArr12[13], bArr9, bArr10, 97);
            keyScheduleRound(bArr12[14], bArr10, bArr11, 97);
            if (i3 > 14) {
                keyScheduleRound(bArr12[15], bArr11, bArr8, 97);
                keyScheduleRound(bArr12[16], bArr8, bArr9, CipherSuite.TLS_DH_anon_WITH_AES_256_CBC_SHA256);
            }
        }
        if (!z2) {
            reverseKeys(bArr12);
            for (int i4 = 1; i4 < i3; i4++) {
                m1206A(bArr12[i4]);
            }
        }
        return bArr12;
    }

    public static void keyScheduleRound(byte[] bArr, byte[] bArr2, byte[] bArr3, int i2) {
        int i3 = i2 >>> 3;
        int i4 = i2 & 7;
        int i5 = 8 - i4;
        int i6 = bArr3[15 - i3] & 255;
        int i7 = 0;
        while (i7 < 16) {
            int i8 = bArr3[(i7 - i3) & 15] & 255;
            bArr[i7] = (byte) (((i6 << i5) | (i8 >>> i4)) ^ (bArr2[i7] & 255));
            i7++;
            i6 = i8;
        }
    }

    public static void reverseKeys(byte[][] bArr) {
        int length = bArr.length;
        int i2 = length / 2;
        int i3 = length - 1;
        for (int i4 = 0; i4 < i2; i4++) {
            byte[] bArr2 = bArr[i4];
            int i5 = i3 - i4;
            bArr[i4] = bArr[i5];
            bArr[i5] = bArr2;
        }
    }

    public static void xor(byte[] bArr, byte[] bArr2) {
        for (int i2 = 0; i2 < 16; i2++) {
            bArr[i2] = (byte) (bArr[i2] ^ bArr2[i2]);
        }
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public String getAlgorithmName() {
        return "ARIA";
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public int getBlockSize() {
        return 16;
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public void init(boolean z2, CipherParameters cipherParameters) {
        if (!(cipherParameters instanceof KeyParameter)) {
            throw new IllegalArgumentException(AbstractC0413b.m1014h(cipherParameters, "invalid parameter passed to ARIA init - "));
        }
        this.roundKeys = keySchedule(z2, ((KeyParameter) cipherParameters).getKey());
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public int processBlock(byte[] bArr, int i2, byte[] bArr2, int i3) {
        if (this.roundKeys == null) {
            throw new IllegalStateException("ARIA engine not initialised");
        }
        if (i2 > bArr.length - 16) {
            throw new DataLengthException("input buffer too short");
        }
        if (i3 > bArr2.length - 16) {
            throw new OutputLengthException("output buffer too short");
        }
        byte[] bArr3 = new byte[16];
        System.arraycopy(bArr, i2, bArr3, 0, 16);
        int length = this.roundKeys.length - 3;
        int i4 = 0;
        while (i4 < length) {
            int i5 = i4 + 1;
            FO(bArr3, this.roundKeys[i4]);
            FE(bArr3, this.roundKeys[i5]);
            i4 = i5 + 1;
        }
        int i6 = i4 + 1;
        FO(bArr3, this.roundKeys[i4]);
        xor(bArr3, this.roundKeys[i6]);
        SL2(bArr3);
        xor(bArr3, this.roundKeys[i6 + 1]);
        System.arraycopy(bArr3, 0, bArr2, i3, 16);
        return 16;
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public void reset() {
    }
}

package org.bouncycastle.crypto.engines;

import android.sun.security.util.DerValue;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.OutputLengthException;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.RC2Parameters;
import org.bouncycastle.crypto.signers.PSSSigner;
import org.bouncycastle.math.ec.Tnaf;
import p012o.AbstractC0413b;

/* loaded from: classes.dex */
public class RC2Engine implements BlockCipher {
    private static final int BLOCK_SIZE = 8;
    private static byte[] piTable = {-39, 120, -7, -60, 25, -35, -75, -19, 40, -23, -3, 121, 74, -96, -40, -99, -58, 126, 55, -125, 43, 118, 83, -114, 98, 76, 100, -120, 68, -117, -5, -94, DerValue.tag_UtcTime, -102, 89, -11, -121, -77, 79, DerValue.tag_PrintableString, 97, 69, 109, -115, 9, -127, 125, 50, -67, -113, DerValue.TAG_APPLICATION, -21, -122, -73, 123, 11, -16, -107, 33, 34, 92, 107, 78, -126, 84, -42, 101, -109, -50, 96, -78, DerValue.tag_UniversalString, 115, 86, DerValue.TAG_PRIVATE, DerValue.tag_T61String, -89, -116, -15, -36, 18, 117, -54, 31, 59, -66, -28, -47, 66, 61, -44, 48, -93, 60, -74, 38, 111, -65, 14, -38, 70, 105, 7, 87, 39, -14, 29, -101, PSSSigner.TRAILER_IMPLICIT, -108, 67, 3, -8, 17, -57, -10, -112, -17, 62, -25, 6, -61, -43, 47, -56, 102, DerValue.tag_BMPString, -41, 8, -24, -22, -34, DerValue.TAG_CONTEXT, 82, -18, -9, -124, -86, 114, -84, 53, 77, 106, 42, -106, 26, -46, 113, 90, 21, 73, 116, 75, -97, -48, 94, 4, DerValue.tag_GeneralizedTime, -92, -20, -62, -32, 65, 110, 15, 81, -53, -52, 36, -111, -81, 80, -95, -12, 112, 57, -103, 124, 58, -123, 35, -72, -76, 122, -4, 2, 54, 91, 37, 85, -105, 49, 45, 93, -6, -104, -29, -118, -110, -82, 5, -33, 41, Tnaf.POW_2_WIDTH, 103, 108, -70, -55, -45, 0, -26, -49, -31, -98, -88, 44, 99, DerValue.tag_IA5String, 1, 63, 88, -30, -119, -87, 13, 56, 52, DerValue.tag_GeneralString, -85, 51, -1, -80, -69, 72, DerValue.tag_UTF8String, 95, -71, -79, -51, 46, -59, -13, -37, 71, -27, -91, -100, 119, 10, -90, 32, 104, -2, Byte.MAX_VALUE, -63, -83};
    private boolean encrypting;
    private int[] workingKey;

    private void decryptBlock(byte[] bArr, int i2, byte[] bArr2, int i3) {
        int i4 = ((bArr[i2 + 7] & 255) << 8) + (bArr[i2 + 6] & 255);
        int i5 = ((bArr[i2 + 5] & 255) << 8) + (bArr[i2 + 4] & 255);
        int i6 = ((bArr[i2 + 3] & 255) << 8) + (bArr[i2 + 2] & 255);
        int i7 = ((bArr[i2 + 1] & 255) << 8) + (bArr[i2 + 0] & 255);
        for (int i8 = 60; i8 >= 44; i8 -= 4) {
            i4 = rotateWordLeft(i4, 11) - ((((~i5) & i7) + (i6 & i5)) + this.workingKey[i8 + 3]);
            i5 = rotateWordLeft(i5, 13) - ((((~i6) & i4) + (i7 & i6)) + this.workingKey[i8 + 2]);
            i6 = rotateWordLeft(i6, 14) - ((((~i7) & i5) + (i4 & i7)) + this.workingKey[i8 + 1]);
            i7 = rotateWordLeft(i7, 15) - ((((~i4) & i6) + (i5 & i4)) + this.workingKey[i8]);
        }
        int[] iArr = this.workingKey;
        int i9 = i4 - iArr[i5 & 63];
        int i10 = i5 - iArr[i6 & 63];
        int i11 = i6 - iArr[i7 & 63];
        int i12 = i7 - iArr[i9 & 63];
        for (int i13 = 40; i13 >= 20; i13 -= 4) {
            i9 = rotateWordLeft(i9, 11) - ((((~i10) & i12) + (i11 & i10)) + this.workingKey[i13 + 3]);
            i10 = rotateWordLeft(i10, 13) - ((((~i11) & i9) + (i12 & i11)) + this.workingKey[i13 + 2]);
            i11 = rotateWordLeft(i11, 14) - ((((~i12) & i10) + (i9 & i12)) + this.workingKey[i13 + 1]);
            i12 = rotateWordLeft(i12, 15) - ((((~i9) & i11) + (i10 & i9)) + this.workingKey[i13]);
        }
        int[] iArr2 = this.workingKey;
        int i14 = i9 - iArr2[i10 & 63];
        int i15 = i10 - iArr2[i11 & 63];
        int i16 = i11 - iArr2[i12 & 63];
        int i17 = i12 - iArr2[i14 & 63];
        for (int i18 = 16; i18 >= 0; i18 -= 4) {
            i14 = rotateWordLeft(i14, 11) - ((((~i15) & i17) + (i16 & i15)) + this.workingKey[i18 + 3]);
            i15 = rotateWordLeft(i15, 13) - ((((~i16) & i14) + (i17 & i16)) + this.workingKey[i18 + 2]);
            i16 = rotateWordLeft(i16, 14) - ((((~i17) & i15) + (i14 & i17)) + this.workingKey[i18 + 1]);
            i17 = rotateWordLeft(i17, 15) - ((((~i14) & i16) + (i15 & i14)) + this.workingKey[i18]);
        }
        bArr2[i3 + 0] = (byte) i17;
        bArr2[i3 + 1] = (byte) (i17 >> 8);
        bArr2[i3 + 2] = (byte) i16;
        bArr2[i3 + 3] = (byte) (i16 >> 8);
        bArr2[i3 + 4] = (byte) i15;
        bArr2[i3 + 5] = (byte) (i15 >> 8);
        bArr2[i3 + 6] = (byte) i14;
        bArr2[i3 + 7] = (byte) (i14 >> 8);
    }

    private void encryptBlock(byte[] bArr, int i2, byte[] bArr2, int i3) {
        int i4 = ((bArr[i2 + 7] & 255) << 8) + (bArr[i2 + 6] & 255);
        int i5 = ((bArr[i2 + 5] & 255) << 8) + (bArr[i2 + 4] & 255);
        int i6 = ((bArr[i2 + 3] & 255) << 8) + (bArr[i2 + 2] & 255);
        int i7 = ((bArr[i2 + 1] & 255) << 8) + (bArr[i2 + 0] & 255);
        for (int i8 = 0; i8 <= 16; i8 += 4) {
            i7 = rotateWordLeft(i7 + ((~i4) & i6) + (i5 & i4) + this.workingKey[i8], 1);
            i6 = rotateWordLeft(i6 + ((~i7) & i5) + (i4 & i7) + this.workingKey[i8 + 1], 2);
            i5 = rotateWordLeft(i5 + ((~i6) & i4) + (i7 & i6) + this.workingKey[i8 + 2], 3);
            i4 = rotateWordLeft(i4 + ((~i5) & i7) + (i6 & i5) + this.workingKey[i8 + 3], 5);
        }
        int[] iArr = this.workingKey;
        int i9 = i7 + iArr[i4 & 63];
        int i10 = i6 + iArr[i9 & 63];
        int i11 = i5 + iArr[i10 & 63];
        int i12 = i4 + iArr[i11 & 63];
        for (int i13 = 20; i13 <= 40; i13 += 4) {
            i9 = rotateWordLeft(i9 + ((~i12) & i10) + (i11 & i12) + this.workingKey[i13], 1);
            i10 = rotateWordLeft(i10 + ((~i9) & i11) + (i12 & i9) + this.workingKey[i13 + 1], 2);
            i11 = rotateWordLeft(i11 + ((~i10) & i12) + (i9 & i10) + this.workingKey[i13 + 2], 3);
            i12 = rotateWordLeft(i12 + ((~i11) & i9) + (i10 & i11) + this.workingKey[i13 + 3], 5);
        }
        int[] iArr2 = this.workingKey;
        int i14 = i9 + iArr2[i12 & 63];
        int i15 = i10 + iArr2[i14 & 63];
        int i16 = i11 + iArr2[i15 & 63];
        int i17 = i12 + iArr2[i16 & 63];
        for (int i18 = 44; i18 < 64; i18 += 4) {
            i14 = rotateWordLeft(i14 + ((~i17) & i15) + (i16 & i17) + this.workingKey[i18], 1);
            i15 = rotateWordLeft(i15 + ((~i14) & i16) + (i17 & i14) + this.workingKey[i18 + 1], 2);
            i16 = rotateWordLeft(i16 + ((~i15) & i17) + (i14 & i15) + this.workingKey[i18 + 2], 3);
            i17 = rotateWordLeft(i17 + ((~i16) & i14) + (i15 & i16) + this.workingKey[i18 + 3], 5);
        }
        bArr2[i3 + 0] = (byte) i14;
        bArr2[i3 + 1] = (byte) (i14 >> 8);
        bArr2[i3 + 2] = (byte) i15;
        bArr2[i3 + 3] = (byte) (i15 >> 8);
        bArr2[i3 + 4] = (byte) i16;
        bArr2[i3 + 5] = (byte) (i16 >> 8);
        bArr2[i3 + 6] = (byte) i17;
        bArr2[i3 + 7] = (byte) (i17 >> 8);
    }

    private int[] generateWorkingKey(byte[] bArr, int i2) {
        int[] iArr = new int[128];
        for (int i3 = 0; i3 != bArr.length; i3++) {
            iArr[i3] = bArr[i3] & 255;
        }
        int length = bArr.length;
        if (length < 128) {
            int i4 = iArr[length - 1];
            int i5 = 0;
            while (true) {
                int i6 = i5 + 1;
                i4 = piTable[(i4 + iArr[i5]) & 255] & 255;
                int i7 = length + 1;
                iArr[length] = i4;
                if (i7 >= 128) {
                    break;
                }
                length = i7;
                i5 = i6;
            }
        }
        int i8 = (i2 + 7) >> 3;
        int i9 = 128 - i8;
        int i10 = piTable[(255 >> ((-i2) & 7)) & iArr[i9]] & 255;
        iArr[i9] = i10;
        for (int i11 = i9 - 1; i11 >= 0; i11--) {
            i10 = piTable[i10 ^ iArr[i11 + i8]] & 255;
            iArr[i11] = i10;
        }
        int[] iArr2 = new int[64];
        for (int i12 = 0; i12 != 64; i12++) {
            int i13 = i12 * 2;
            iArr2[i12] = iArr[i13] + (iArr[i13 + 1] << 8);
        }
        return iArr2;
    }

    private int rotateWordLeft(int i2, int i3) {
        int i4 = i2 & 65535;
        return (i4 >> (16 - i3)) | (i4 << i3);
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public String getAlgorithmName() {
        return "RC2";
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public int getBlockSize() {
        return 8;
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public void init(boolean z2, CipherParameters cipherParameters) {
        this.encrypting = z2;
        if (cipherParameters instanceof RC2Parameters) {
            RC2Parameters rC2Parameters = (RC2Parameters) cipherParameters;
            this.workingKey = generateWorkingKey(rC2Parameters.getKey(), rC2Parameters.getEffectiveKeyBits());
        } else {
            if (!(cipherParameters instanceof KeyParameter)) {
                throw new IllegalArgumentException(AbstractC0413b.m1014h(cipherParameters, "invalid parameter passed to RC2 init - "));
            }
            byte[] key = ((KeyParameter) cipherParameters).getKey();
            this.workingKey = generateWorkingKey(key, key.length * 8);
        }
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public final int processBlock(byte[] bArr, int i2, byte[] bArr2, int i3) {
        if (this.workingKey == null) {
            throw new IllegalStateException("RC2 engine not initialised");
        }
        if (i2 + 8 > bArr.length) {
            throw new DataLengthException("input buffer too short");
        }
        if (i3 + 8 > bArr2.length) {
            throw new OutputLengthException("output buffer too short");
        }
        if (this.encrypting) {
            encryptBlock(bArr, i2, bArr2, i3);
            return 8;
        }
        decryptBlock(bArr, i2, bArr2, i3);
        return 8;
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public void reset() {
    }
}

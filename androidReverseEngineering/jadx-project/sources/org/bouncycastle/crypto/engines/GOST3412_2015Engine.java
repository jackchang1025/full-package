package org.bouncycastle.crypto.engines;

import android.sun.security.util.DerValue;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.OutputLengthException;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.signers.PSSSigner;
import org.bouncycastle.math.ec.Tnaf;
import org.bouncycastle.util.Arrays;
import p012o.AbstractC0413b;

/* loaded from: classes.dex */
public class GOST3412_2015Engine implements BlockCipher {
    protected static final int BLOCK_SIZE = 16;
    private static final byte[] PI = {-4, -18, -35, 17, -49, 110, 49, DerValue.tag_IA5String, -5, -60, -6, -38, 35, -59, 4, 77, -23, 119, -16, -37, -109, 46, -103, -70, DerValue.tag_UtcTime, 54, -15, -69, DerValue.tag_T61String, -51, 95, -63, -7, DerValue.tag_GeneralizedTime, 101, 90, -30, 92, -17, 33, -127, DerValue.tag_UniversalString, 60, 66, -117, 1, -114, 79, 5, -124, 2, -82, -29, 106, -113, -96, 6, 11, -19, -104, Byte.MAX_VALUE, -44, -45, 31, -21, 52, 44, 81, -22, -56, 72, -85, -14, 42, 104, -94, -3, 58, -50, -52, -75, 112, 14, 86, 8, DerValue.tag_UTF8String, 118, 18, -65, 114, DerValue.tag_PrintableString, 71, -100, -73, 93, -121, 21, -95, -106, 41, Tnaf.POW_2_WIDTH, 123, -102, -57, -13, -111, 120, 111, -99, -98, -78, -79, 50, 117, 25, 61, -1, 53, -118, 126, 109, 84, -58, DerValue.TAG_CONTEXT, -61, -67, 13, 87, -33, -11, 36, -87, 62, -88, 67, -55, -41, 121, -42, -10, 124, 34, -71, 3, -32, 15, -20, -34, 122, -108, -80, PSSSigner.TRAILER_IMPLICIT, -36, -24, 40, 80, 78, 51, 10, 74, -89, -105, 96, 115, DerValue.tag_BMPString, 0, 98, 68, 26, -72, 56, -126, 100, -97, 38, 65, -83, 69, 70, -110, 39, 94, 85, 47, -116, -93, -91, 125, 105, -43, -107, 59, 7, 88, -77, DerValue.TAG_APPLICATION, -122, -84, 29, -9, 48, 55, 107, -28, -120, -39, -25, -119, -31, DerValue.tag_GeneralString, -125, 73, 76, 63, -8, -2, -115, 83, -86, -112, -54, -40, -123, 97, 32, 113, 103, -92, 45, 43, 9, 91, -53, -101, 37, -48, -66, -27, 108, 82, 89, -90, 116, -46, -26, -12, -76, DerValue.TAG_PRIVATE, -47, 102, -81, -62, 57, 75, 99, -74};
    private static final byte[] inversePI = {-91, 45, 50, -113, 14, 48, 56, DerValue.TAG_PRIVATE, 84, -26, -98, 57, 85, 126, 82, -111, 100, 3, 87, 90, DerValue.tag_UniversalString, 96, 7, DerValue.tag_GeneralizedTime, 33, 114, -88, -47, 41, -58, -92, 63, -32, 39, -115, DerValue.tag_UTF8String, -126, -22, -82, -76, -102, 99, 73, -27, 66, -28, 21, -73, -56, 6, 112, -99, 65, 117, 25, -55, -86, -4, 77, -65, 42, 115, -124, -43, -61, -81, 43, -122, -89, -79, -78, 91, 70, -45, -97, -3, -44, 15, -100, 47, -101, 67, -17, -39, 121, -74, 83, Byte.MAX_VALUE, -63, -16, 35, -25, 37, 94, -75, DerValue.tag_BMPString, -94, -33, -90, -2, -84, 34, -7, -30, 74, PSSSigner.TRAILER_IMPLICIT, 53, -54, -18, 120, 5, 107, 81, -31, 89, -93, -14, 113, 86, 17, 106, -119, -108, 101, -116, -69, 119, 60, 123, 40, -85, -46, 49, -34, -60, 95, -52, -49, 118, 44, -72, -40, 46, 54, -37, 105, -77, DerValue.tag_T61String, -107, -66, 98, -95, 59, DerValue.tag_IA5String, 102, -23, 92, 108, 109, -83, 55, 97, 75, -71, -29, -70, -15, -96, -123, -125, -38, 71, -59, -80, 51, -6, -106, 111, 110, -62, -10, 80, -1, 93, -87, -114, DerValue.tag_UtcTime, DerValue.tag_GeneralString, -105, 125, -20, 88, -9, 31, -5, 124, 9, 13, 122, 103, 69, -121, -36, -24, 79, 29, 78, 4, -21, -8, -13, 62, 61, -67, -118, -120, -35, -51, 11, DerValue.tag_PrintableString, -104, 2, -109, DerValue.TAG_CONTEXT, -112, -48, 36, 52, -53, -19, -12, -50, -103, Tnaf.POW_2_WIDTH, 68, DerValue.TAG_APPLICATION, -110, 58, 1, 38, 18, 26, 72, 104, -11, -127, -117, -57, -42, 32, 10, 8, 0, 76, -41, 116};
    private boolean forEncryption;
    private final byte[] lFactors = {-108, 32, -123, Tnaf.POW_2_WIDTH, -62, DerValue.TAG_PRIVATE, 1, -5, 1, DerValue.TAG_PRIVATE, -62, Tnaf.POW_2_WIDTH, -123, 32, -108, 1};
    private int KEY_LENGTH = 32;
    private int SUB_LENGTH = 32 / 2;
    private byte[][] subKeys = null;
    private byte[][] _gf_mul = init_gf256_mul_table();

    /* renamed from: C */
    private void m1208C(byte[] bArr, int i2) {
        Arrays.clear(bArr);
        bArr[15] = (byte) i2;
        m1210L(bArr);
    }

    /* renamed from: F */
    private void m1209F(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        byte[] LSX = LSX(bArr, bArr2);
        m1213X(LSX, bArr3);
        System.arraycopy(bArr2, 0, bArr3, 0, this.SUB_LENGTH);
        System.arraycopy(LSX, 0, bArr2, 0, this.SUB_LENGTH);
    }

    private void GOST3412_2015Func(byte[] bArr, int i2, byte[] bArr2, int i3) {
        byte[][] bArr3;
        byte[] bArr4 = new byte[16];
        System.arraycopy(bArr, i2, bArr4, 0, 16);
        int i4 = 9;
        if (this.forEncryption) {
            for (int i5 = 0; i5 < 9; i5++) {
                bArr4 = Arrays.copyOf(LSX(this.subKeys[i5], bArr4), 16);
            }
            m1213X(bArr4, this.subKeys[9]);
        } else {
            while (true) {
                bArr3 = this.subKeys;
                if (i4 <= 0) {
                    break;
                }
                bArr4 = Arrays.copyOf(XSL(bArr3[i4], bArr4), 16);
                i4--;
            }
            m1213X(bArr4, bArr3[0]);
        }
        System.arraycopy(bArr4, 0, bArr2, i3, 16);
    }

    /* renamed from: L */
    private void m1210L(byte[] bArr) {
        for (int i2 = 0; i2 < 16; i2++) {
            m1211R(bArr);
        }
    }

    private byte[] LSX(byte[] bArr, byte[] bArr2) {
        byte[] copyOf = Arrays.copyOf(bArr, bArr.length);
        m1213X(copyOf, bArr2);
        m1212S(copyOf);
        m1210L(copyOf);
        return copyOf;
    }

    /* renamed from: R */
    private void m1211R(byte[] bArr) {
        byte m1214l = m1214l(bArr);
        System.arraycopy(bArr, 0, bArr, 1, 15);
        bArr[0] = m1214l;
    }

    /* renamed from: S */
    private void m1212S(byte[] bArr) {
        for (int i2 = 0; i2 < bArr.length; i2++) {
            bArr[i2] = PI[unsignedByte(bArr[i2])];
        }
    }

    /* renamed from: X */
    private void m1213X(byte[] bArr, byte[] bArr2) {
        for (int i2 = 0; i2 < bArr.length; i2++) {
            bArr[i2] = (byte) (bArr[i2] ^ bArr2[i2]);
        }
    }

    private byte[] XSL(byte[] bArr, byte[] bArr2) {
        byte[] copyOf = Arrays.copyOf(bArr, bArr.length);
        m1213X(copyOf, bArr2);
        inverseL(copyOf);
        inverseS(copyOf);
        return copyOf;
    }

    private void generateSubKeys(byte[] bArr) {
        int i2;
        if (bArr.length != this.KEY_LENGTH) {
            throw new IllegalArgumentException("Key length invalid. Key needs to be 32 byte - 256 bit!!!");
        }
        this.subKeys = new byte[10][];
        for (int i3 = 0; i3 < 10; i3++) {
            this.subKeys[i3] = new byte[this.SUB_LENGTH];
        }
        int i4 = this.SUB_LENGTH;
        byte[] bArr2 = new byte[i4];
        byte[] bArr3 = new byte[i4];
        int i5 = 0;
        while (true) {
            i2 = this.SUB_LENGTH;
            if (i5 >= i2) {
                break;
            }
            byte[][] bArr4 = this.subKeys;
            byte[] bArr5 = bArr4[0];
            byte b = bArr[i5];
            bArr2[i5] = b;
            bArr5[i5] = b;
            byte[] bArr6 = bArr4[1];
            byte b2 = bArr[i2 + i5];
            bArr3[i5] = b2;
            bArr6[i5] = b2;
            i5++;
        }
        byte[] bArr7 = new byte[i2];
        for (int i6 = 1; i6 < 5; i6++) {
            for (int i7 = 1; i7 <= 8; i7++) {
                m1208C(bArr7, ((i6 - 1) * 8) + i7);
                m1209F(bArr7, bArr2, bArr3);
            }
            int i8 = i6 * 2;
            System.arraycopy(bArr2, 0, this.subKeys[i8], 0, this.SUB_LENGTH);
            System.arraycopy(bArr3, 0, this.subKeys[i8 + 1], 0, this.SUB_LENGTH);
        }
    }

    private static byte[][] init_gf256_mul_table() {
        byte[][] bArr = new byte[256][];
        for (int i2 = 0; i2 < 256; i2++) {
            bArr[i2] = new byte[256];
            for (int i3 = 0; i3 < 256; i3++) {
                bArr[i2][i3] = kuz_mul_gf256_slow((byte) i2, (byte) i3);
            }
        }
        return bArr;
    }

    private void inverseL(byte[] bArr) {
        for (int i2 = 0; i2 < 16; i2++) {
            inverseR(bArr);
        }
    }

    private void inverseR(byte[] bArr) {
        byte[] bArr2 = new byte[16];
        System.arraycopy(bArr, 1, bArr2, 0, 15);
        bArr2[15] = bArr[0];
        byte m1214l = m1214l(bArr2);
        System.arraycopy(bArr, 1, bArr, 0, 15);
        bArr[15] = m1214l;
    }

    private void inverseS(byte[] bArr) {
        for (int i2 = 0; i2 < bArr.length; i2++) {
            bArr[i2] = inversePI[unsignedByte(bArr[i2])];
        }
    }

    private static byte kuz_mul_gf256_slow(byte b, byte b2) {
        byte b3 = 0;
        for (byte b4 = 0; b4 < 8 && b != 0 && b2 != 0; b4 = (byte) (b4 + 1)) {
            if ((b2 & 1) != 0) {
                b3 = (byte) (b3 ^ b);
            }
            byte b5 = (byte) (b & DerValue.TAG_CONTEXT);
            b = (byte) (b << 1);
            if (b5 != 0) {
                b = (byte) (b ^ 195);
            }
            b2 = (byte) (b2 >> 1);
        }
        return b3;
    }

    /* renamed from: l */
    private byte m1214l(byte[] bArr) {
        byte b = bArr[15];
        for (int i2 = 14; i2 >= 0; i2--) {
            b = (byte) (b ^ this._gf_mul[unsignedByte(bArr[i2])][unsignedByte(this.lFactors[i2])]);
        }
        return b;
    }

    private int unsignedByte(byte b) {
        return b & 255;
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public String getAlgorithmName() {
        return "GOST3412_2015";
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public int getBlockSize() {
        return 16;
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public void init(boolean z2, CipherParameters cipherParameters) {
        if (cipherParameters instanceof KeyParameter) {
            this.forEncryption = z2;
            generateSubKeys(((KeyParameter) cipherParameters).getKey());
        } else if (cipherParameters != null) {
            throw new IllegalArgumentException(AbstractC0413b.m1014h(cipherParameters, "invalid parameter passed to GOST3412_2015 init - "));
        }
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public int processBlock(byte[] bArr, int i2, byte[] bArr2, int i3) {
        if (this.subKeys == null) {
            throw new IllegalStateException("GOST3412_2015 engine not initialised");
        }
        if (i2 + 16 > bArr.length) {
            throw new DataLengthException("input buffer too short");
        }
        if (i3 + 16 > bArr2.length) {
            throw new OutputLengthException("output buffer too short");
        }
        GOST3412_2015Func(bArr, i2, bArr2, i3);
        return 16;
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public void reset() {
    }
}

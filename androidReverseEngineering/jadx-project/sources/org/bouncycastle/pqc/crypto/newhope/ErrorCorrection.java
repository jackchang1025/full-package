package org.bouncycastle.pqc.crypto.newhope;

import org.bouncycastle.tls.CipherSuite;
import org.bouncycastle.util.Arrays;

/* loaded from: classes.dex */
class ErrorCorrection {
    public static short LDDecode(int i2, int i3, int i4, int i5) {
        return (short) (((((m1229g(i2) + m1229g(i3)) + m1229g(i4)) + m1229g(i5)) - 98312) >>> 31);
    }

    public static int abs(int i2) {
        int i3 = i2 >> 31;
        return (i2 ^ i3) - i3;
    }

    /* renamed from: f */
    public static int m1228f(int[] iArr, int i2, int i3, int i4) {
        int i5 = (i4 * 2730) >> 25;
        int i6 = i5 - ((12288 - (i4 - (i5 * 12289))) >> 31);
        iArr[i2] = (i6 >> 1) + (i6 & 1);
        int i7 = i6 - 1;
        iArr[i3] = (i7 >> 1) + (i7 & 1);
        return abs(i4 - ((iArr[i2] * 2) * 12289));
    }

    /* renamed from: g */
    public static int m1229g(int i2) {
        int i3 = (i2 * 2730) >> 27;
        int i4 = i3 - ((CipherSuite.TLS_ECDH_ECDSA_WITH_3DES_EDE_CBC_SHA - (i2 - (CipherSuite.TLS_ECDH_ECDSA_WITH_AES_128_CBC_SHA * i3))) >> 31);
        return abs((((i4 >> 1) + (i4 & 1)) * 98312) - i2);
    }

    public static void helpRec(short[] sArr, short[] sArr2, byte[] bArr, byte b) {
        short s2 = 8;
        byte[] bArr2 = new byte[8];
        bArr2[0] = b;
        byte[] bArr3 = new byte[32];
        ChaCha20.process(bArr, bArr2, bArr3, 0, 32);
        int[] iArr = new int[8];
        int i2 = 0;
        while (i2 < 256) {
            int i3 = i2 + 0;
            int i4 = ((bArr3[i2 >>> 3] >>> (i2 & 7)) & 1) * 4;
            int i5 = i2 + 256;
            int i6 = i2 + 512;
            int i7 = i2 + 768;
            int m1228f = (24577 - (((m1228f(iArr, 0, 4, (sArr2[i3] * s2) + i4) + m1228f(iArr, 1, 5, (sArr2[i5] * s2) + i4)) + m1228f(iArr, 2, 6, (sArr2[i6] * s2) + i4)) + m1228f(iArr, 3, 7, (sArr2[i7] * 8) + i4))) >> 31;
            int i8 = ~m1228f;
            int i9 = (i8 & iArr[0]) ^ (iArr[4] & m1228f);
            int i10 = (iArr[1] & i8) ^ (iArr[5] & m1228f);
            int i11 = (iArr[2] & i8) ^ (m1228f & iArr[6]);
            int i12 = (i8 & iArr[3]) ^ (iArr[7] & m1228f);
            sArr[i3] = (short) ((i9 - i12) & 3);
            sArr[i5] = (short) ((i10 - i12) & 3);
            sArr[i6] = (short) ((i11 - i12) & 3);
            sArr[i7] = (short) (3 & ((i12 * 2) + (-m1228f)));
            i2++;
            s2 = 8;
        }
    }

    public static void rec(byte[] bArr, short[] sArr, short[] sArr2) {
        Arrays.fill(bArr, (byte) 0);
        for (int i2 = 0; i2 < 256; i2++) {
            int i3 = i2 + 0;
            int i4 = (sArr[i3] * 8) + 196624;
            int i5 = sArr2[i3] * 2;
            int i6 = i2 + 768;
            short s2 = sArr2[i6];
            int i7 = i4 - ((i5 + s2) * 12289);
            int i8 = i2 + 256;
            int i9 = ((sArr[i8] * 8) + 196624) - (((sArr2[i8] * 2) + s2) * 12289);
            int i10 = i2 + 512;
            int i11 = ((sArr[i10] * 8) + 196624) - (((sArr2[i10] * 2) + s2) * 12289);
            int i12 = ((sArr[i6] * 8) + 196624) - (s2 * 12289);
            int i13 = i2 >>> 3;
            bArr[i13] = (byte) ((LDDecode(i7, i9, i11, i12) << (i2 & 7)) | bArr[i13]);
        }
    }
}

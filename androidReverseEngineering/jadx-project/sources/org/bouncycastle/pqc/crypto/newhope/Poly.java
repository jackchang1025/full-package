package org.bouncycastle.pqc.crypto.newhope;

import android.R;
import org.bouncycastle.crypto.digests.SHAKEDigest;
import org.bouncycastle.util.Pack;

/* loaded from: classes.dex */
class Poly {
    public static void add(short[] sArr, short[] sArr2, short[] sArr3) {
        for (int i2 = 0; i2 < 1024; i2++) {
            sArr3[i2] = Reduce.barrett((short) (sArr[i2] + sArr2[i2]));
        }
    }

    public static void fromBytes(short[] sArr, byte[] bArr) {
        for (int i2 = 0; i2 < 256; i2++) {
            int i3 = i2 * 7;
            int i4 = bArr[i3 + 0] & 255;
            int i5 = bArr[i3 + 1] & 255;
            int i6 = bArr[i3 + 2] & 255;
            int i7 = bArr[i3 + 3] & 255;
            int i8 = bArr[i3 + 4] & 255;
            int i9 = bArr[i3 + 5] & 255;
            int i10 = bArr[i3 + 6] & 255;
            int i11 = i2 * 4;
            sArr[i11 + 0] = (short) (i4 | ((i5 & 63) << 8));
            sArr[i11 + 1] = (short) ((i5 >>> 6) | (i6 << 2) | ((i7 & 15) << 10));
            sArr[i11 + 2] = (short) ((i7 >>> 4) | (i8 << 4) | ((i9 & 3) << 12));
            sArr[i11 + 3] = (short) ((i10 << 6) | (i9 >>> 2));
        }
    }

    public static void fromNTT(short[] sArr) {
        NTT.bitReverse(sArr);
        NTT.core(sArr, Precomp.OMEGAS_INV_MONTGOMERY);
        NTT.mulCoefficients(sArr, Precomp.PSIS_INV_MONTGOMERY);
    }

    public static void getNoise(short[] sArr, byte[] bArr, byte b) {
        byte[] bArr2 = new byte[8];
        bArr2[0] = b;
        byte[] bArr3 = new byte[4096];
        ChaCha20.process(bArr, bArr2, bArr3, 0, 4096);
        for (int i2 = 0; i2 < 1024; i2++) {
            int bigEndianToInt = Pack.bigEndianToInt(bArr3, i2 * 4);
            int i3 = 0;
            for (int i4 = 0; i4 < 8; i4++) {
                i3 += (bigEndianToInt >> i4) & R.attr.cacheColorHint;
            }
            sArr[i2] = (short) (((((i3 >>> 24) + (i3 >>> 0)) & 255) + 12289) - (((i3 >>> 16) + (i3 >>> 8)) & 255));
        }
    }

    private static short normalize(short s2) {
        short barrett = Reduce.barrett(s2);
        int i2 = barrett - 12289;
        return (short) (((barrett ^ i2) & (i2 >> 31)) ^ i2);
    }

    public static void pointWise(short[] sArr, short[] sArr2, short[] sArr3) {
        for (int i2 = 0; i2 < 1024; i2++) {
            sArr3[i2] = Reduce.montgomery((sArr[i2] & 65535) * (65535 & Reduce.montgomery((sArr2[i2] & 65535) * 3186)));
        }
    }

    public static void toBytes(byte[] bArr, short[] sArr) {
        for (int i2 = 0; i2 < 256; i2++) {
            int i3 = i2 * 4;
            short normalize = normalize(sArr[i3 + 0]);
            short normalize2 = normalize(sArr[i3 + 1]);
            short normalize3 = normalize(sArr[i3 + 2]);
            short normalize4 = normalize(sArr[i3 + 3]);
            int i4 = i2 * 7;
            bArr[i4 + 0] = (byte) normalize;
            bArr[i4 + 1] = (byte) ((normalize >> 8) | (normalize2 << 6));
            bArr[i4 + 2] = (byte) (normalize2 >> 2);
            bArr[i4 + 3] = (byte) ((normalize2 >> 10) | (normalize3 << 4));
            bArr[i4 + 4] = (byte) (normalize3 >> 4);
            bArr[i4 + 5] = (byte) ((normalize3 >> 12) | (normalize4 << 2));
            bArr[i4 + 6] = (byte) (normalize4 >> 6);
        }
    }

    public static void toNTT(short[] sArr) {
        NTT.mulCoefficients(sArr, Precomp.PSIS_BITREV_MONTGOMERY);
        NTT.core(sArr, Precomp.OMEGAS_MONTGOMERY);
    }

    public static void uniform(short[] sArr, byte[] bArr) {
        SHAKEDigest sHAKEDigest = new SHAKEDigest(128);
        sHAKEDigest.update(bArr, 0, bArr.length);
        int i2 = 0;
        while (true) {
            byte[] bArr2 = new byte[256];
            sHAKEDigest.doOutput(bArr2, 0, 256);
            for (int i3 = 0; i3 < 256; i3 += 2) {
                int i4 = (bArr2[i3] & 255) | ((bArr2[i3 + 1] & 255) << 8);
                if (i4 < 61445) {
                    int i5 = i2 + 1;
                    sArr[i2] = (short) i4;
                    if (i5 == 1024) {
                        return;
                    } else {
                        i2 = i5;
                    }
                }
            }
        }
    }
}

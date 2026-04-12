package p000;

import java.math.BigInteger;

/* loaded from: classes2.dex */
public abstract class vh0 {
    public static void copy64(long[] jArr, int i, long[] jArr2, int i2) {
        jArr2[i2] = jArr[i];
        jArr2[i2 + 1] = jArr[i + 1];
        jArr2[i2 + 2] = jArr[i + 2];
        jArr2[i2 + 3] = jArr[i + 3];
        jArr2[i2 + 4] = jArr[i + 4];
        jArr2[i2 + 5] = jArr[i + 5];
        jArr2[i2 + 6] = jArr[i + 6];
    }

    public static long[] create64() {
        return new long[7];
    }

    public static long[] createExt64() {
        return new long[14];
    }

    public static boolean eq64(long[] jArr, long[] jArr2) {
        for (int i = 6; i >= 0; i--) {
            if (jArr[i] != jArr2[i]) {
                return false;
            }
        }
        return true;
    }

    public static long[] fromBigInteger64(BigInteger bigInteger) {
        if (bigInteger.signum() < 0 || bigInteger.bitLength() > 448) {
            throw new IllegalArgumentException();
        }
        long[] jArrCreate64 = create64();
        for (int i = 0; i < 7; i++) {
            jArrCreate64[i] = bigInteger.longValue();
            bigInteger = bigInteger.shiftRight(64);
        }
        return jArrCreate64;
    }

    public static boolean isOne64(long[] jArr) {
        if (jArr[0] != 1) {
            return false;
        }
        for (int i = 1; i < 7; i++) {
            if (jArr[i] != 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean isZero64(long[] jArr) {
        for (int i = 0; i < 7; i++) {
            if (jArr[i] != 0) {
                return false;
            }
        }
        return true;
    }

    public static void mul(int[] iArr, int[] iArr2, int[] iArr3) {
        rh0.mul(iArr, iArr2, iArr3);
        rh0.mul(iArr, 7, iArr2, 7, iArr3, 14);
        int iAddToEachOther = rh0.addToEachOther(iArr3, 7, iArr3, 14);
        int iAddTo = rh0.addTo(iArr3, 21, iArr3, 14, rh0.addTo(iArr3, 0, iArr3, 7, 0) + iAddToEachOther) + iAddToEachOther;
        int[] iArrCreate = rh0.create();
        int[] iArrCreate2 = rh0.create();
        boolean z = rh0.diff(iArr, 7, iArr, 0, iArrCreate, 0) != rh0.diff(iArr2, 7, iArr2, 0, iArrCreate2, 0);
        int[] iArrCreateExt = rh0.createExt();
        rh0.mul(iArrCreate, iArrCreate2, iArrCreateExt);
        yh0.addWordAt(28, iAddTo + (z ? yh0.addTo(14, iArrCreateExt, 0, iArr3, 7) : yh0.subFrom(14, iArrCreateExt, 0, iArr3, 7)), iArr3, 21);
    }

    public static void square(int[] iArr, int[] iArr2) {
        rh0.square(iArr, iArr2);
        rh0.square(iArr, 7, iArr2, 14);
        int iAddToEachOther = rh0.addToEachOther(iArr2, 7, iArr2, 14);
        int iAddTo = rh0.addTo(iArr2, 21, iArr2, 14, rh0.addTo(iArr2, 0, iArr2, 7, 0) + iAddToEachOther) + iAddToEachOther;
        int[] iArrCreate = rh0.create();
        rh0.diff(iArr, 7, iArr, 0, iArrCreate, 0);
        int[] iArrCreateExt = rh0.createExt();
        rh0.square(iArrCreate, iArrCreateExt);
        yh0.addWordAt(28, yh0.subFrom(14, iArrCreateExt, 0, iArr2, 7) + iAddTo, iArr2, 21);
    }

    public static BigInteger toBigInteger64(long[] jArr) {
        byte[] bArr = new byte[56];
        for (int i = 0; i < 7; i++) {
            long j = jArr[i];
            if (j != 0) {
                wl0.longToBigEndian(j, bArr, (6 - i) << 3);
            }
        }
        return new BigInteger(1, bArr);
    }

    public static void copy64(long[] jArr, long[] jArr2) {
        jArr2[0] = jArr[0];
        jArr2[1] = jArr[1];
        jArr2[2] = jArr[2];
        jArr2[3] = jArr[3];
        jArr2[4] = jArr[4];
        jArr2[5] = jArr[5];
        jArr2[6] = jArr[6];
    }
}

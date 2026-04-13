package org.bouncycastle.math.ec.custom.sec;

import java.math.BigInteger;
import org.bouncycastle.asn1.cmc.BodyPartID;
import org.bouncycastle.math.raw.Interleave;
import org.bouncycastle.math.raw.Nat;
import org.bouncycastle.math.raw.Nat256;
import org.bouncycastle.tls.CipherSuite;

/* loaded from: classes.dex */
public class SecT193Field {
    private static final long M01 = 1;
    private static final long M49 = 562949953421311L;

    public static void add(long[] jArr, long[] jArr2, long[] jArr3) {
        jArr3[0] = jArr[0] ^ jArr2[0];
        jArr3[1] = jArr[1] ^ jArr2[1];
        jArr3[2] = jArr[2] ^ jArr2[2];
        jArr3[3] = jArr2[3] ^ jArr[3];
    }

    public static void addExt(long[] jArr, long[] jArr2, long[] jArr3) {
        jArr3[0] = jArr[0] ^ jArr2[0];
        jArr3[1] = jArr[1] ^ jArr2[1];
        jArr3[2] = jArr[2] ^ jArr2[2];
        jArr3[3] = jArr[3] ^ jArr2[3];
        jArr3[4] = jArr[4] ^ jArr2[4];
        jArr3[5] = jArr[5] ^ jArr2[5];
        jArr3[6] = jArr2[6] ^ jArr[6];
    }

    public static void addOne(long[] jArr, long[] jArr2) {
        jArr2[0] = jArr[0] ^ M01;
        jArr2[1] = jArr[1];
        jArr2[2] = jArr[2];
        jArr2[3] = jArr[3];
    }

    private static void addTo(long[] jArr, long[] jArr2) {
        jArr2[0] = jArr2[0] ^ jArr[0];
        jArr2[1] = jArr2[1] ^ jArr[1];
        jArr2[2] = jArr2[2] ^ jArr[2];
        jArr2[3] = jArr2[3] ^ jArr[3];
    }

    public static long[] fromBigInteger(BigInteger bigInteger) {
        return Nat.fromBigInteger64(CipherSuite.TLS_DH_DSS_WITH_CAMELLIA_256_CBC_SHA256, bigInteger);
    }

    public static void halfTrace(long[] jArr, long[] jArr2) {
        long[] createExt64 = Nat256.createExt64();
        Nat256.copy64(jArr, jArr2);
        for (int i2 = 1; i2 < 193; i2 += 2) {
            implSquare(jArr2, createExt64);
            reduce(createExt64, jArr2);
            implSquare(jArr2, createExt64);
            reduce(createExt64, jArr2);
            addTo(jArr, jArr2);
        }
    }

    public static void implCompactExt(long[] jArr) {
        long j2 = jArr[0];
        long j3 = jArr[1];
        long j4 = jArr[2];
        long j5 = jArr[3];
        long j6 = jArr[4];
        long j7 = jArr[5];
        long j8 = jArr[6];
        long j9 = jArr[7];
        jArr[0] = j2 ^ (j3 << 49);
        jArr[1] = (j3 >>> 15) ^ (j4 << 34);
        jArr[2] = (j4 >>> 30) ^ (j5 << 19);
        jArr[3] = ((j5 >>> 45) ^ (j6 << 4)) ^ (j7 << 53);
        jArr[4] = ((j6 >>> 60) ^ (j8 << 38)) ^ (j7 >>> 11);
        jArr[5] = (j8 >>> 26) ^ (j9 << 23);
        jArr[6] = j9 >>> 41;
        jArr[7] = 0;
    }

    public static void implExpand(long[] jArr, long[] jArr2) {
        long j2 = jArr[0];
        long j3 = jArr[1];
        long j4 = jArr[2];
        long j5 = jArr[3];
        jArr2[0] = j2 & M49;
        jArr2[1] = ((j2 >>> 49) ^ (j3 << 15)) & M49;
        jArr2[2] = ((j3 >>> 34) ^ (j4 << 30)) & M49;
        jArr2[3] = (j4 >>> 19) ^ (j5 << 45);
    }

    public static void implMultiply(long[] jArr, long[] jArr2, long[] jArr3) {
        long[] jArr4 = new long[4];
        long[] jArr5 = new long[4];
        implExpand(jArr, jArr4);
        implExpand(jArr2, jArr5);
        long[] jArr6 = new long[8];
        implMulwAcc(jArr6, jArr4[0], jArr5[0], jArr3, 0);
        implMulwAcc(jArr6, jArr4[1], jArr5[1], jArr3, 1);
        implMulwAcc(jArr6, jArr4[2], jArr5[2], jArr3, 2);
        implMulwAcc(jArr6, jArr4[3], jArr5[3], jArr3, 3);
        for (int i2 = 5; i2 > 0; i2--) {
            jArr3[i2] = jArr3[i2] ^ jArr3[i2 - 1];
        }
        implMulwAcc(jArr6, jArr4[0] ^ jArr4[1], jArr5[0] ^ jArr5[1], jArr3, 1);
        implMulwAcc(jArr6, jArr4[2] ^ jArr4[3], jArr5[2] ^ jArr5[3], jArr3, 3);
        for (int i3 = 7; i3 > 1; i3--) {
            jArr3[i3] = jArr3[i3] ^ jArr3[i3 - 2];
        }
        long j2 = jArr4[0] ^ jArr4[2];
        long j3 = jArr4[1] ^ jArr4[3];
        long j4 = jArr5[0] ^ jArr5[2];
        long j5 = jArr5[1] ^ jArr5[3];
        implMulwAcc(jArr6, j2 ^ j3, j4 ^ j5, jArr3, 3);
        long[] jArr7 = new long[3];
        implMulwAcc(jArr6, j2, j4, jArr7, 0);
        implMulwAcc(jArr6, j3, j5, jArr7, 1);
        long j6 = jArr7[0];
        long j7 = jArr7[1];
        long j8 = jArr7[2];
        jArr3[2] = jArr3[2] ^ j6;
        jArr3[3] = jArr3[3] ^ (j6 ^ j7);
        jArr3[4] = jArr3[4] ^ (j8 ^ j7);
        jArr3[5] = jArr3[5] ^ j8;
        implCompactExt(jArr3);
    }

    public static void implMulwAcc(long[] jArr, long j2, long j3, long[] jArr2, int i2) {
        jArr[1] = j3;
        long j4 = j3 << M01;
        jArr[2] = j4;
        long j5 = j4 ^ j3;
        jArr[3] = j5;
        long j6 = j4 << M01;
        jArr[4] = j6;
        jArr[5] = j6 ^ j3;
        long j7 = j5 << M01;
        jArr[6] = j7;
        jArr[7] = j7 ^ j3;
        int i3 = (int) j2;
        long j8 = (jArr[(i3 >>> 3) & 7] << 3) ^ jArr[i3 & 7];
        long j9 = 0;
        int i4 = 36;
        do {
            int i5 = (int) (j2 >>> i4);
            long j10 = (((jArr[i5 & 7] ^ (jArr[(i5 >>> 3) & 7] << 3)) ^ (jArr[(i5 >>> 6) & 7] << 6)) ^ (jArr[(i5 >>> 9) & 7] << 9)) ^ (jArr[(i5 >>> 12) & 7] << 12);
            j8 ^= j10 << i4;
            j9 ^= j10 >>> (-i4);
            i4 -= 15;
        } while (i4 > 0);
        jArr2[i2] = jArr2[i2] ^ (M49 & j8);
        int i6 = i2 + 1;
        jArr2[i6] = jArr2[i6] ^ ((j8 >>> 49) ^ (j9 << 15));
    }

    public static void implSquare(long[] jArr, long[] jArr2) {
        Interleave.expand64To128(jArr, 0, 3, jArr2, 0);
        jArr2[6] = jArr[3] & M01;
    }

    public static void invert(long[] jArr, long[] jArr2) {
        if (Nat256.isZero64(jArr)) {
            throw new IllegalStateException();
        }
        long[] create64 = Nat256.create64();
        long[] create642 = Nat256.create64();
        square(jArr, create64);
        squareN(create64, 1, create642);
        multiply(create64, create642, create64);
        squareN(create642, 1, create642);
        multiply(create64, create642, create64);
        squareN(create64, 3, create642);
        multiply(create64, create642, create64);
        squareN(create64, 6, create642);
        multiply(create64, create642, create64);
        squareN(create64, 12, create642);
        multiply(create64, create642, create64);
        squareN(create64, 24, create642);
        multiply(create64, create642, create64);
        squareN(create64, 48, create642);
        multiply(create64, create642, create64);
        squareN(create64, 96, create642);
        multiply(create64, create642, jArr2);
    }

    public static void multiply(long[] jArr, long[] jArr2, long[] jArr3) {
        long[] createExt64 = Nat256.createExt64();
        implMultiply(jArr, jArr2, createExt64);
        reduce(createExt64, jArr3);
    }

    public static void multiplyAddToExt(long[] jArr, long[] jArr2, long[] jArr3) {
        long[] createExt64 = Nat256.createExt64();
        implMultiply(jArr, jArr2, createExt64);
        addExt(jArr3, createExt64, jArr3);
    }

    public static void reduce(long[] jArr, long[] jArr2) {
        long j2 = jArr[0];
        long j3 = jArr[1];
        long j4 = jArr[2];
        long j5 = jArr[3];
        long j6 = jArr[4];
        long j7 = jArr[5];
        long j8 = jArr[6];
        long j9 = j5 ^ ((j8 >>> M01) ^ (j8 << 14));
        long j10 = j6 ^ (j8 >>> 50);
        long j11 = (j4 ^ (j8 << 63)) ^ ((j7 >>> M01) ^ (j7 << 14));
        long j12 = j9 ^ (j7 >>> 50);
        long j13 = j2 ^ (j10 << 63);
        long j14 = (j3 ^ (j7 << 63)) ^ ((j10 >>> M01) ^ (j10 << 14));
        long j15 = j11 ^ (j10 >>> 50);
        long j16 = j12 >>> M01;
        jArr2[0] = (j13 ^ j16) ^ (j16 << 15);
        jArr2[1] = (j16 >>> 49) ^ j14;
        jArr2[2] = j15;
        jArr2[3] = M01 & j12;
    }

    public static void reduce63(long[] jArr, int i2) {
        int i3 = i2 + 3;
        long j2 = jArr[i3];
        long j3 = j2 >>> M01;
        jArr[i2] = jArr[i2] ^ ((j3 << 15) ^ j3);
        int i4 = i2 + 1;
        jArr[i4] = (j3 >>> 49) ^ jArr[i4];
        jArr[i3] = j2 & M01;
    }

    public static void sqrt(long[] jArr, long[] jArr2) {
        long unshuffle = Interleave.unshuffle(jArr[0]);
        long unshuffle2 = Interleave.unshuffle(jArr[1]);
        long j2 = (unshuffle & BodyPartID.bodyIdMax) | (unshuffle2 << 32);
        long j3 = (unshuffle >>> 32) | (unshuffle2 & (-4294967296L));
        long unshuffle3 = Interleave.unshuffle(jArr[2]);
        long j4 = (unshuffle3 & BodyPartID.bodyIdMax) ^ (jArr[3] << 32);
        long j5 = unshuffle3 >>> 32;
        jArr2[0] = j2 ^ (j3 << 8);
        jArr2[1] = ((j4 ^ (j5 << 8)) ^ (j3 >>> 56)) ^ (j3 << 33);
        jArr2[2] = (j3 >>> 31) ^ ((j5 >>> 56) ^ (j5 << 33));
        jArr2[3] = j5 >>> 31;
    }

    public static void square(long[] jArr, long[] jArr2) {
        long[] createExt64 = Nat256.createExt64();
        implSquare(jArr, createExt64);
        reduce(createExt64, jArr2);
    }

    public static void squareAddToExt(long[] jArr, long[] jArr2) {
        long[] createExt64 = Nat256.createExt64();
        implSquare(jArr, createExt64);
        addExt(jArr2, createExt64, jArr2);
    }

    public static void squareN(long[] jArr, int i2, long[] jArr2) {
        long[] createExt64 = Nat256.createExt64();
        implSquare(jArr, createExt64);
        while (true) {
            reduce(createExt64, jArr2);
            i2--;
            if (i2 <= 0) {
                return;
            } else {
                implSquare(jArr2, createExt64);
            }
        }
    }

    public static int trace(long[] jArr) {
        return ((int) jArr[0]) & 1;
    }
}

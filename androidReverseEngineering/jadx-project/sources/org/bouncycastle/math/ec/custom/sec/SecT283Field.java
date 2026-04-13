package org.bouncycastle.math.ec.custom.sec;

import java.math.BigInteger;
import org.bouncycastle.asn1.cmc.BodyPartID;
import org.bouncycastle.math.raw.Interleave;
import org.bouncycastle.math.raw.Nat;
import org.bouncycastle.math.raw.Nat320;
import org.bouncycastle.tls.CipherSuite;

/* loaded from: classes.dex */
public class SecT283Field {
    private static final long M27 = 134217727;
    private static final long M57 = 144115188075855871L;
    private static final long[] ROOT_Z = {878416384462358536L, 3513665537849438403L, -9076969306111048948L, 585610922974906400L, 34087042};

    public static void add(long[] jArr, long[] jArr2, long[] jArr3) {
        jArr3[0] = jArr[0] ^ jArr2[0];
        jArr3[1] = jArr[1] ^ jArr2[1];
        jArr3[2] = jArr[2] ^ jArr2[2];
        jArr3[3] = jArr[3] ^ jArr2[3];
        jArr3[4] = jArr2[4] ^ jArr[4];
    }

    public static void addExt(long[] jArr, long[] jArr2, long[] jArr3) {
        jArr3[0] = jArr[0] ^ jArr2[0];
        jArr3[1] = jArr[1] ^ jArr2[1];
        jArr3[2] = jArr[2] ^ jArr2[2];
        jArr3[3] = jArr[3] ^ jArr2[3];
        jArr3[4] = jArr[4] ^ jArr2[4];
        jArr3[5] = jArr[5] ^ jArr2[5];
        jArr3[6] = jArr[6] ^ jArr2[6];
        jArr3[7] = jArr[7] ^ jArr2[7];
        jArr3[8] = jArr2[8] ^ jArr[8];
    }

    public static void addOne(long[] jArr, long[] jArr2) {
        jArr2[0] = jArr[0] ^ 1;
        jArr2[1] = jArr[1];
        jArr2[2] = jArr[2];
        jArr2[3] = jArr[3];
        jArr2[4] = jArr[4];
    }

    private static void addTo(long[] jArr, long[] jArr2) {
        jArr2[0] = jArr2[0] ^ jArr[0];
        jArr2[1] = jArr2[1] ^ jArr[1];
        jArr2[2] = jArr2[2] ^ jArr[2];
        jArr2[3] = jArr2[3] ^ jArr[3];
        jArr2[4] = jArr2[4] ^ jArr[4];
    }

    public static long[] fromBigInteger(BigInteger bigInteger) {
        return Nat.fromBigInteger64(283, bigInteger);
    }

    public static void halfTrace(long[] jArr, long[] jArr2) {
        long[] create64 = Nat.create64(9);
        Nat320.copy64(jArr, jArr2);
        for (int i2 = 1; i2 < 283; i2 += 2) {
            implSquare(jArr2, create64);
            reduce(create64, jArr2);
            implSquare(jArr2, create64);
            reduce(create64, jArr2);
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
        long j10 = jArr[8];
        long j11 = jArr[9];
        jArr[0] = j2 ^ (j3 << 57);
        jArr[1] = (j3 >>> 7) ^ (j4 << 50);
        jArr[2] = (j4 >>> 14) ^ (j5 << 43);
        jArr[3] = (j5 >>> 21) ^ (j6 << 36);
        jArr[4] = (j6 >>> 28) ^ (j7 << 29);
        jArr[5] = (j7 >>> 35) ^ (j8 << 22);
        jArr[6] = (j8 >>> 42) ^ (j9 << 15);
        jArr[7] = (j9 >>> 49) ^ (j10 << 8);
        jArr[8] = (j10 >>> 56) ^ (j11 << 1);
        jArr[9] = j11 >>> 63;
    }

    public static void implExpand(long[] jArr, long[] jArr2) {
        long j2 = jArr[0];
        long j3 = jArr[1];
        long j4 = jArr[2];
        long j5 = jArr[3];
        long j6 = jArr[4];
        jArr2[0] = j2 & M57;
        jArr2[1] = ((j2 >>> 57) ^ (j3 << 7)) & M57;
        jArr2[2] = ((j3 >>> 50) ^ (j4 << 14)) & M57;
        jArr2[3] = ((j4 >>> 43) ^ (j5 << 21)) & M57;
        jArr2[4] = (j5 >>> 36) ^ (j6 << 28);
    }

    public static void implMultiply(long[] jArr, long[] jArr2, long[] jArr3) {
        long[] jArr4 = new long[5];
        long[] jArr5 = new long[5];
        implExpand(jArr, jArr4);
        implExpand(jArr2, jArr5);
        long[] jArr6 = new long[26];
        implMulw(jArr3, jArr4[0], jArr5[0], jArr6, 0);
        implMulw(jArr3, jArr4[1], jArr5[1], jArr6, 2);
        implMulw(jArr3, jArr4[2], jArr5[2], jArr6, 4);
        implMulw(jArr3, jArr4[3], jArr5[3], jArr6, 6);
        implMulw(jArr3, jArr4[4], jArr5[4], jArr6, 8);
        long j2 = jArr4[0];
        long j3 = j2 ^ jArr4[1];
        long j4 = jArr5[0];
        long j5 = j4 ^ jArr5[1];
        long j6 = jArr4[2];
        long j7 = j2 ^ j6;
        long j8 = jArr5[2];
        long j9 = j4 ^ j8;
        long j10 = jArr4[4];
        long j11 = j6 ^ j10;
        long j12 = jArr5[4];
        long j13 = j8 ^ j12;
        long j14 = jArr4[3];
        long j15 = j14 ^ j10;
        long j16 = jArr5[3];
        long j17 = j16 ^ j12;
        implMulw(jArr3, j7 ^ j14, j9 ^ j16, jArr6, 18);
        implMulw(jArr3, j11 ^ jArr4[1], j13 ^ jArr5[1], jArr6, 20);
        long j18 = j3 ^ j15;
        long j19 = j5 ^ j17;
        long j20 = j18 ^ jArr4[2];
        long j21 = j19 ^ jArr5[2];
        implMulw(jArr3, j18, j19, jArr6, 22);
        implMulw(jArr3, j20, j21, jArr6, 24);
        implMulw(jArr3, j3, j5, jArr6, 10);
        implMulw(jArr3, j7, j9, jArr6, 12);
        implMulw(jArr3, j11, j13, jArr6, 14);
        implMulw(jArr3, j15, j17, jArr6, 16);
        jArr3[0] = jArr6[0];
        jArr3[9] = jArr6[9];
        long j22 = jArr6[0];
        long j23 = jArr6[1] ^ j22;
        long j24 = jArr6[2] ^ j23;
        long j25 = jArr6[10] ^ j24;
        jArr3[1] = j25;
        long j26 = jArr6[3] ^ jArr6[4];
        long j27 = j24 ^ (j26 ^ (jArr6[11] ^ jArr6[12]));
        jArr3[2] = j27;
        long j28 = j23 ^ j26;
        long j29 = jArr6[5] ^ jArr6[6];
        long j30 = jArr6[8];
        long j31 = (j28 ^ j29) ^ j30;
        long j32 = jArr6[13] ^ jArr6[14];
        long j33 = jArr6[18];
        long j34 = jArr6[22];
        long j35 = jArr6[24];
        jArr3[3] = (j31 ^ j32) ^ ((j33 ^ j34) ^ j35);
        long j36 = jArr6[7] ^ j30;
        long j37 = jArr6[9];
        long j38 = j36 ^ j37;
        long j39 = j38 ^ jArr6[17];
        jArr3[8] = j39;
        long j40 = (j38 ^ j29) ^ (jArr6[15] ^ jArr6[16]);
        jArr3[7] = j40;
        long j41 = j25 ^ j40;
        long j42 = jArr6[19] ^ jArr6[20];
        long j43 = jArr6[25];
        long j44 = jArr6[23];
        long j45 = j42 ^ (j43 ^ j35);
        jArr3[4] = (j45 ^ (j33 ^ j44)) ^ j41;
        long j46 = jArr6[21];
        jArr3[5] = ((j27 ^ j39) ^ j45) ^ (j46 ^ j34);
        jArr3[6] = (((((j31 ^ j22) ^ j37) ^ j32) ^ j46) ^ j44) ^ j43;
        implCompactExt(jArr3);
    }

    public static void implMulw(long[] jArr, long j2, long j3, long[] jArr2, int i2) {
        jArr[1] = j3;
        long j4 = j3 << 1;
        jArr[2] = j4;
        long j5 = j4 ^ j3;
        jArr[3] = j5;
        long j6 = j4 << 1;
        jArr[4] = j6;
        jArr[5] = j6 ^ j3;
        long j7 = j5 << 1;
        jArr[6] = j7;
        jArr[7] = j7 ^ j3;
        long j8 = jArr[((int) j2) & 7];
        long j9 = 0;
        int i3 = 48;
        do {
            int i4 = (int) (j2 >>> i3);
            long j10 = (jArr[i4 & 7] ^ (jArr[(i4 >>> 3) & 7] << 3)) ^ (jArr[(i4 >>> 6) & 7] << 6);
            j8 ^= j10 << i3;
            j9 ^= j10 >>> (-i3);
            i3 -= 9;
        } while (i3 > 0);
        jArr2[i2] = M57 & j8;
        jArr2[i2 + 1] = (((((j2 & 72198606942111744L) & ((j3 << 7) >> 63)) >>> 8) ^ j9) << 7) ^ (j8 >>> 57);
    }

    public static void implSquare(long[] jArr, long[] jArr2) {
        Interleave.expand64To128(jArr, 0, 4, jArr2, 0);
        jArr2[8] = Interleave.expand32to64((int) jArr[4]);
    }

    public static void invert(long[] jArr, long[] jArr2) {
        if (Nat320.isZero64(jArr)) {
            throw new IllegalStateException();
        }
        long[] create64 = Nat320.create64();
        long[] create642 = Nat320.create64();
        square(jArr, create64);
        multiply(create64, jArr, create64);
        squareN(create64, 2, create642);
        multiply(create642, create64, create642);
        squareN(create642, 4, create64);
        multiply(create64, create642, create64);
        squareN(create64, 8, create642);
        multiply(create642, create64, create642);
        square(create642, create642);
        multiply(create642, jArr, create642);
        squareN(create642, 17, create64);
        multiply(create64, create642, create64);
        square(create64, create64);
        multiply(create64, jArr, create64);
        squareN(create64, 35, create642);
        multiply(create642, create64, create642);
        squareN(create642, 70, create64);
        multiply(create64, create642, create64);
        square(create64, create64);
        multiply(create64, jArr, create64);
        squareN(create64, CipherSuite.TLS_PSK_WITH_AES_256_CBC_SHA, create642);
        multiply(create642, create64, create642);
        square(create642, jArr2);
    }

    public static void multiply(long[] jArr, long[] jArr2, long[] jArr3) {
        long[] createExt64 = Nat320.createExt64();
        implMultiply(jArr, jArr2, createExt64);
        reduce(createExt64, jArr3);
    }

    public static void multiplyAddToExt(long[] jArr, long[] jArr2, long[] jArr3) {
        long[] createExt64 = Nat320.createExt64();
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
        long j9 = jArr[7];
        long j10 = jArr[8];
        long j11 = j6 ^ ((((j10 >>> 27) ^ (j10 >>> 22)) ^ (j10 >>> 20)) ^ (j10 >>> 15));
        long j12 = j2 ^ ((((j7 << 37) ^ (j7 << 42)) ^ (j7 << 44)) ^ (j7 << 49));
        long j13 = (j3 ^ ((((j8 << 37) ^ (j8 << 42)) ^ (j8 << 44)) ^ (j8 << 49))) ^ ((((j7 >>> 27) ^ (j7 >>> 22)) ^ (j7 >>> 20)) ^ (j7 >>> 15));
        long j14 = j11 >>> 27;
        jArr2[0] = (((j12 ^ j14) ^ (j14 << 5)) ^ (j14 << 7)) ^ (j14 << 12);
        jArr2[1] = j13;
        jArr2[2] = (j4 ^ ((((j9 << 37) ^ (j9 << 42)) ^ (j9 << 44)) ^ (j9 << 49))) ^ ((((j8 >>> 27) ^ (j8 >>> 22)) ^ (j8 >>> 20)) ^ (j8 >>> 15));
        jArr2[3] = (j5 ^ ((((j10 << 37) ^ (j10 << 42)) ^ (j10 << 44)) ^ (j10 << 49))) ^ ((((j9 >>> 27) ^ (j9 >>> 22)) ^ (j9 >>> 20)) ^ (j9 >>> 15));
        jArr2[4] = M27 & j11;
    }

    public static void reduce37(long[] jArr, int i2) {
        int i3 = i2 + 4;
        long j2 = jArr[i3];
        long j3 = j2 >>> 27;
        jArr[i2] = ((j3 << 12) ^ (((j3 << 5) ^ j3) ^ (j3 << 7))) ^ jArr[i2];
        jArr[i3] = j2 & M27;
    }

    public static void sqrt(long[] jArr, long[] jArr2) {
        long[] create64 = Nat320.create64();
        long unshuffle = Interleave.unshuffle(jArr[0]);
        long unshuffle2 = Interleave.unshuffle(jArr[1]);
        long j2 = (unshuffle & BodyPartID.bodyIdMax) | (unshuffle2 << 32);
        create64[0] = (unshuffle >>> 32) | (unshuffle2 & (-4294967296L));
        long unshuffle3 = Interleave.unshuffle(jArr[2]);
        long unshuffle4 = Interleave.unshuffle(jArr[3]);
        long j3 = (unshuffle3 & BodyPartID.bodyIdMax) | (unshuffle4 << 32);
        create64[1] = (unshuffle3 >>> 32) | ((-4294967296L) & unshuffle4);
        long unshuffle5 = Interleave.unshuffle(jArr[4]);
        long j4 = BodyPartID.bodyIdMax & unshuffle5;
        create64[2] = unshuffle5 >>> 32;
        multiply(create64, ROOT_Z, jArr2);
        jArr2[0] = jArr2[0] ^ j2;
        jArr2[1] = jArr2[1] ^ j3;
        jArr2[2] = jArr2[2] ^ j4;
    }

    public static void square(long[] jArr, long[] jArr2) {
        long[] create64 = Nat.create64(9);
        implSquare(jArr, create64);
        reduce(create64, jArr2);
    }

    public static void squareAddToExt(long[] jArr, long[] jArr2) {
        long[] create64 = Nat.create64(9);
        implSquare(jArr, create64);
        addExt(jArr2, create64, jArr2);
    }

    public static void squareN(long[] jArr, int i2, long[] jArr2) {
        long[] create64 = Nat.create64(9);
        implSquare(jArr, create64);
        while (true) {
            reduce(create64, jArr2);
            i2--;
            if (i2 <= 0) {
                return;
            } else {
                implSquare(jArr2, create64);
            }
        }
    }

    public static int trace(long[] jArr) {
        return ((int) (jArr[0] ^ (jArr[4] >>> 15))) & 1;
    }
}

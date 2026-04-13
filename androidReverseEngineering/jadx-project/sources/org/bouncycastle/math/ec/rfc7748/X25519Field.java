package org.bouncycastle.math.ec.rfc7748;

import android.sun.security.util.DerValue;
import org.bouncycastle.math.raw.Mod;
import p012o.AbstractC0413b;

/* loaded from: classes.dex */
public abstract class X25519Field {
    private static final int M24 = 16777215;
    private static final int M25 = 33554431;
    private static final int M26 = 67108863;
    private static final int[] P32 = {-19, -1, -1, -1, -1, -1, -1, Integer.MAX_VALUE};
    private static final int[] ROOT_NEG_ONE = {34513072, 59165138, 4688974, 3500415, 6194736, 33281959, 54535759, 32551604, 163342, 5703241};
    public static final int SIZE = 10;

    public static void add(int[] iArr, int[] iArr2, int[] iArr3) {
        for (int i2 = 0; i2 < 10; i2++) {
            iArr3[i2] = iArr[i2] + iArr2[i2];
        }
    }

    public static void addOne(int[] iArr) {
        iArr[0] = iArr[0] + 1;
    }

    public static void apm(int[] iArr, int[] iArr2, int[] iArr3, int[] iArr4) {
        for (int i2 = 0; i2 < 10; i2++) {
            int i3 = iArr[i2];
            int i4 = iArr2[i2];
            iArr3[i2] = i3 + i4;
            iArr4[i2] = i3 - i4;
        }
    }

    public static int areEqual(int[] iArr, int[] iArr2) {
        int i2 = 0;
        for (int i3 = 0; i3 < 10; i3++) {
            i2 |= iArr[i3] ^ iArr2[i3];
        }
        return (((i2 >>> 1) | (i2 & 1)) - 1) >> 31;
    }

    public static boolean areEqualVar(int[] iArr, int[] iArr2) {
        return areEqual(iArr, iArr2) != 0;
    }

    public static void carry(int[] iArr) {
        int i2 = iArr[0];
        int i3 = iArr[1];
        int i4 = iArr[2];
        int i5 = iArr[3];
        int i6 = iArr[4];
        int i7 = iArr[5];
        int i8 = iArr[6];
        int i9 = iArr[7];
        int i10 = iArr[8];
        int i11 = iArr[9];
        int i12 = i4 + (i3 >> 26);
        int i13 = i3 & M26;
        int i14 = i6 + (i5 >> 26);
        int i15 = i5 & M26;
        int i16 = i9 + (i8 >> 26);
        int i17 = i8 & M26;
        int i18 = i11 + (i10 >> 26);
        int i19 = i10 & M26;
        int i20 = i15 + (i12 >> 25);
        int i21 = i12 & M25;
        int i22 = i7 + (i14 >> 25);
        int i23 = i14 & M25;
        int i24 = i19 + (i16 >> 25);
        int i25 = i16 & M25;
        int i26 = ((i18 >> 25) * 38) + i2;
        int i27 = i18 & M25;
        int i28 = i13 + (i26 >> 26);
        int i29 = i26 & M26;
        int i30 = i17 + (i22 >> 26);
        int i31 = i22 & M26;
        int i32 = i21 + (i28 >> 26);
        int i33 = i28 & M26;
        int i34 = i23 + (i20 >> 26);
        int i35 = i20 & M26;
        int i36 = i25 + (i30 >> 26);
        int i37 = i30 & M26;
        int i38 = i27 + (i24 >> 26);
        int i39 = i24 & M26;
        iArr[0] = i29;
        iArr[1] = i33;
        iArr[2] = i32;
        iArr[3] = i35;
        iArr[4] = i34;
        iArr[5] = i31;
        iArr[6] = i37;
        iArr[7] = i36;
        iArr[8] = i39;
        iArr[9] = i38;
    }

    public static void cmov(int i2, int[] iArr, int i3, int[] iArr2, int i4) {
        for (int i5 = 0; i5 < 10; i5++) {
            int i6 = i4 + i5;
            int i7 = iArr2[i6];
            iArr2[i6] = i7 ^ ((iArr[i3 + i5] ^ i7) & i2);
        }
    }

    public static void cnegate(int i2, int[] iArr) {
        int i3 = 0 - i2;
        for (int i4 = 0; i4 < 10; i4++) {
            iArr[i4] = (iArr[i4] ^ i3) - i3;
        }
    }

    public static void copy(int[] iArr, int i2, int[] iArr2, int i3) {
        for (int i4 = 0; i4 < 10; i4++) {
            iArr2[i3 + i4] = iArr[i2 + i4];
        }
    }

    public static int[] create() {
        return new int[10];
    }

    public static int[] createTable(int i2) {
        return new int[i2 * 10];
    }

    public static void cswap(int i2, int[] iArr, int[] iArr2) {
        int i3 = 0 - i2;
        for (int i4 = 0; i4 < 10; i4++) {
            int i5 = iArr[i4];
            int i6 = iArr2[i4];
            int i7 = (i5 ^ i6) & i3;
            iArr[i4] = i5 ^ i7;
            iArr2[i4] = i6 ^ i7;
        }
    }

    public static void decode(byte[] bArr, int i2, int[] iArr) {
        decode128(bArr, i2, iArr, 0);
        decode128(bArr, i2 + 16, iArr, 5);
        iArr[9] = iArr[9] & 16777215;
    }

    private static void decode128(byte[] bArr, int i2, int[] iArr, int i3) {
        int decode32 = decode32(bArr, i2 + 0);
        int decode322 = decode32(bArr, i2 + 4);
        int decode323 = decode32(bArr, i2 + 8);
        int decode324 = decode32(bArr, i2 + 12);
        iArr[i3 + 0] = decode32 & M26;
        iArr[i3 + 1] = ((decode32 >>> 26) | (decode322 << 6)) & M26;
        iArr[i3 + 2] = ((decode323 << 12) | (decode322 >>> 20)) & M25;
        iArr[i3 + 3] = ((decode324 << 19) | (decode323 >>> 13)) & M26;
        iArr[i3 + 4] = decode324 >>> 7;
    }

    private static int decode32(byte[] bArr, int i2) {
        int i3 = bArr[i2] & 255;
        int i4 = i2 + 1;
        int i5 = i3 | ((bArr[i4] & 255) << 8);
        int i6 = i4 + 1;
        return (bArr[i6 + 1] << DerValue.tag_GeneralizedTime) | i5 | ((bArr[i6] & 255) << 16);
    }

    public static void encode(int[] iArr, byte[] bArr, int i2) {
        encode128(iArr, 0, bArr, i2);
        encode128(iArr, 5, bArr, i2 + 16);
    }

    private static void encode128(int[] iArr, int i2, byte[] bArr, int i3) {
        int i4 = iArr[i2 + 0];
        int i5 = iArr[i2 + 1];
        int i6 = iArr[i2 + 2];
        int i7 = iArr[i2 + 3];
        int i8 = iArr[i2 + 4];
        encode32((i5 << 26) | i4, bArr, i3 + 0);
        encode32((i5 >>> 6) | (i6 << 20), bArr, i3 + 4);
        encode32((i6 >>> 12) | (i7 << 13), bArr, i3 + 8);
        encode32((i8 << 7) | (i7 >>> 19), bArr, i3 + 12);
    }

    private static void encode32(int i2, byte[] bArr, int i3) {
        bArr[i3] = (byte) i2;
        int i4 = i3 + 1;
        bArr[i4] = (byte) (i2 >>> 8);
        int i5 = i4 + 1;
        bArr[i5] = (byte) (i2 >>> 16);
        bArr[i5 + 1] = (byte) (i2 >>> 24);
    }

    public static void inv(int[] iArr, int[] iArr2) {
        int[] create = create();
        int[] iArr3 = new int[8];
        copy(iArr, 0, create, 0);
        normalize(create);
        encode(create, iArr3, 0);
        Mod.modOddInverse(P32, iArr3, iArr3);
        decode(iArr3, 0, iArr2);
    }

    public static void invVar(int[] iArr, int[] iArr2) {
        int[] create = create();
        int[] iArr3 = new int[8];
        copy(iArr, 0, create, 0);
        normalize(create);
        encode(create, iArr3, 0);
        Mod.modOddInverseVar(P32, iArr3, iArr3);
        decode(iArr3, 0, iArr2);
    }

    public static int isOne(int[] iArr) {
        int i2 = iArr[0] ^ 1;
        for (int i3 = 1; i3 < 10; i3++) {
            i2 |= iArr[i3];
        }
        return (((i2 >>> 1) | (i2 & 1)) - 1) >> 31;
    }

    public static boolean isOneVar(int[] iArr) {
        return isOne(iArr) != 0;
    }

    public static int isZero(int[] iArr) {
        int i2 = 0;
        for (int i3 = 0; i3 < 10; i3++) {
            i2 |= iArr[i3];
        }
        return (((i2 >>> 1) | (i2 & 1)) - 1) >> 31;
    }

    public static boolean isZeroVar(int[] iArr) {
        return isZero(iArr) != 0;
    }

    public static void mul(int[] iArr, int i2, int[] iArr2) {
        int i3 = iArr[0];
        int i4 = iArr[1];
        int i5 = iArr[2];
        int i6 = iArr[3];
        int i7 = iArr[4];
        int i8 = iArr[5];
        int i9 = iArr[6];
        int i10 = iArr[7];
        int i11 = iArr[8];
        int i12 = iArr[9];
        long j2 = i2;
        long j3 = i5 * j2;
        int i13 = ((int) j3) & M25;
        long j4 = i7 * j2;
        int i14 = ((int) j4) & M25;
        long j5 = i10 * j2;
        int i15 = ((int) j5) & M25;
        long j6 = j5 >> 25;
        long j7 = i12 * j2;
        int i16 = ((int) j7) & M25;
        long j8 = (i3 * j2) + ((j7 >> 25) * 38);
        iArr2[0] = ((int) j8) & M26;
        long j9 = (i8 * j2) + (j4 >> 25);
        iArr2[5] = ((int) j9) & M26;
        long j10 = j9 >> 26;
        long j11 = (i4 * j2) + (j8 >> 26);
        iArr2[1] = ((int) j11) & M26;
        long j12 = j11 >> 26;
        long j13 = (i6 * j2) + (j3 >> 25);
        iArr2[3] = ((int) j13) & M26;
        long j14 = j13 >> 26;
        long j15 = (i9 * j2) + j10;
        iArr2[6] = ((int) j15) & M26;
        long j16 = j15 >> 26;
        long j17 = (i11 * j2) + j6;
        iArr2[8] = M26 & ((int) j17);
        iArr2[2] = i13 + ((int) j12);
        iArr2[4] = i14 + ((int) j14);
        iArr2[7] = i15 + ((int) j16);
        iArr2[9] = i16 + ((int) (j17 >> 26));
    }

    public static void negate(int[] iArr, int[] iArr2) {
        for (int i2 = 0; i2 < 10; i2++) {
            iArr2[i2] = -iArr[i2];
        }
    }

    public static void normalize(int[] iArr) {
        int i2 = (iArr[9] >>> 23) & 1;
        reduce(iArr, i2);
        reduce(iArr, -i2);
    }

    public static void one(int[] iArr) {
        iArr[0] = 1;
        for (int i2 = 1; i2 < 10; i2++) {
            iArr[i2] = 0;
        }
    }

    private static void powPm5d8(int[] iArr, int[] iArr2, int[] iArr3) {
        sqr(iArr, iArr2);
        mul(iArr, iArr2, iArr2);
        int[] create = create();
        sqr(iArr2, create);
        mul(iArr, create, create);
        sqr(create, 2, create);
        mul(iArr2, create, create);
        int[] create2 = create();
        sqr(create, 5, create2);
        mul(create, create2, create2);
        int[] create3 = create();
        sqr(create2, 5, create3);
        mul(create, create3, create3);
        sqr(create3, 10, create);
        mul(create2, create, create);
        sqr(create, 25, create2);
        mul(create, create2, create2);
        sqr(create2, 25, create3);
        mul(create, create3, create3);
        sqr(create3, 50, create);
        mul(create2, create, create);
        sqr(create, 125, create2);
        mul(create, create2, create2);
        sqr(create2, 2, create);
        mul(create, iArr, iArr3);
    }

    private static void reduce(int[] iArr, int i2) {
        int i3 = iArr[9];
        long j2 = (((i3 >> 24) + i2) * 19) + iArr[0];
        iArr[0] = ((int) j2) & M26;
        long j3 = (j2 >> 26) + iArr[1];
        iArr[1] = ((int) j3) & M26;
        long j4 = (j3 >> 26) + iArr[2];
        iArr[2] = ((int) j4) & M25;
        long j5 = (j4 >> 25) + iArr[3];
        iArr[3] = ((int) j5) & M26;
        long j6 = (j5 >> 26) + iArr[4];
        iArr[4] = ((int) j6) & M25;
        long j7 = (j6 >> 25) + iArr[5];
        iArr[5] = ((int) j7) & M26;
        long j8 = (j7 >> 26) + iArr[6];
        iArr[6] = ((int) j8) & M26;
        long j9 = (j8 >> 26) + iArr[7];
        iArr[7] = M25 & ((int) j9);
        long j10 = (j9 >> 25) + iArr[8];
        iArr[8] = M26 & ((int) j10);
        iArr[9] = (16777215 & i3) + ((int) (j10 >> 26));
    }

    public static void sqr(int[] iArr, int i2, int[] iArr2) {
        sqr(iArr, iArr2);
        while (true) {
            i2--;
            if (i2 <= 0) {
                return;
            } else {
                sqr(iArr2, iArr2);
            }
        }
    }

    public static boolean sqrtRatioVar(int[] iArr, int[] iArr2, int[] iArr3) {
        int[] create = create();
        int[] create2 = create();
        mul(iArr, iArr2, create);
        sqr(iArr2, create2);
        mul(create, create2, create);
        sqr(create2, create2);
        mul(create2, create, create2);
        int[] create3 = create();
        int[] create4 = create();
        powPm5d8(create2, create3, create4);
        mul(create4, create, create4);
        int[] create5 = create();
        sqr(create4, create5);
        mul(create5, iArr2, create5);
        sub(create5, iArr, create3);
        normalize(create3);
        if (isZeroVar(create3)) {
            copy(create4, 0, iArr3, 0);
            return true;
        }
        add(create5, iArr, create3);
        normalize(create3);
        if (!isZeroVar(create3)) {
            return false;
        }
        mul(create4, ROOT_NEG_ONE, iArr3);
        return true;
    }

    public static void sub(int[] iArr, int[] iArr2, int[] iArr3) {
        for (int i2 = 0; i2 < 10; i2++) {
            iArr3[i2] = iArr[i2] - iArr2[i2];
        }
    }

    public static void subOne(int[] iArr) {
        iArr[0] = iArr[0] - 1;
    }

    public static void zero(int[] iArr) {
        for (int i2 = 0; i2 < 10; i2++) {
            iArr[i2] = 0;
        }
    }

    public static void addOne(int[] iArr, int i2) {
        iArr[i2] = iArr[i2] + 1;
    }

    public static void decode(int[] iArr, int i2, int[] iArr2) {
        decode128(iArr, i2, iArr2, 0);
        decode128(iArr, i2 + 4, iArr2, 5);
        iArr2[9] = iArr2[9] & 16777215;
    }

    private static void decode128(int[] iArr, int i2, int[] iArr2, int i3) {
        int i4 = iArr[i2 + 0];
        int i5 = iArr[i2 + 1];
        int i6 = iArr[i2 + 2];
        int i7 = iArr[i2 + 3];
        iArr2[i3 + 0] = i4 & M26;
        iArr2[i3 + 1] = ((i4 >>> 26) | (i5 << 6)) & M26;
        iArr2[i3 + 2] = ((i6 << 12) | (i5 >>> 20)) & M25;
        iArr2[i3 + 3] = ((i7 << 19) | (i6 >>> 13)) & M26;
        iArr2[i3 + 4] = i7 >>> 7;
    }

    public static void encode(int[] iArr, int[] iArr2, int i2) {
        encode128(iArr, 0, iArr2, i2);
        encode128(iArr, 5, iArr2, i2 + 4);
    }

    private static void encode128(int[] iArr, int i2, int[] iArr2, int i3) {
        int i4 = iArr[i2 + 0];
        int i5 = iArr[i2 + 1];
        int i6 = iArr[i2 + 2];
        int i7 = iArr[i2 + 3];
        int i8 = iArr[i2 + 4];
        iArr2[i3 + 0] = i4 | (i5 << 26);
        iArr2[i3 + 1] = (i5 >>> 6) | (i6 << 20);
        iArr2[i3 + 2] = (i6 >>> 12) | (i7 << 13);
        iArr2[i3 + 3] = (i8 << 7) | (i7 >>> 19);
    }

    public static void mul(int[] iArr, int[] iArr2, int[] iArr3) {
        int i2 = iArr[0];
        int i3 = iArr2[0];
        int i4 = iArr[1];
        int i5 = iArr2[1];
        int i6 = iArr[2];
        int i7 = iArr2[2];
        int i8 = iArr[3];
        int i9 = iArr2[3];
        int i10 = iArr[4];
        int i11 = iArr2[4];
        int i12 = iArr[5];
        int i13 = iArr2[5];
        int i14 = iArr[6];
        int i15 = iArr2[6];
        int i16 = iArr[7];
        int i17 = iArr2[7];
        int i18 = iArr[8];
        int i19 = iArr2[8];
        int i20 = iArr[9];
        int i21 = iArr2[9];
        long j2 = i2;
        long j3 = i3;
        long j4 = j2 * j3;
        long j5 = i5;
        long j6 = i4;
        long j7 = (j6 * j3) + (j2 * j5);
        long j8 = i7;
        long j9 = i6;
        long j10 = (j9 * j3) + (j6 * j5) + (j2 * j8);
        long j11 = j9 * j5;
        long j12 = i9;
        long j13 = i8;
        long m1007a = AbstractC0413b.m1007a(j13, j3, j2 * j12, (j11 + (j6 * j8)) << 1);
        long j14 = (j9 * j8) << 1;
        long j15 = i11;
        long j16 = i10;
        long m1007a2 = AbstractC0413b.m1007a(j3, j16, (j13 * j5) + (j6 * j12) + (j2 * j15), j14);
        long j17 = ((j16 * j5) + ((j13 * j8) + ((j9 * j12) + (j6 * j15)))) << 1;
        long j18 = (j13 * j12) + (((j16 * j8) + (j9 * j15)) << 1);
        long j19 = (j12 * j16) + (j13 * j15);
        long j20 = (j16 * j15) << 1;
        long j21 = i12;
        long j22 = i13;
        long j23 = i15;
        long j24 = i14;
        long j25 = (j24 * j22) + (j21 * j23);
        long j26 = i17;
        long j27 = i16;
        long j28 = (j27 * j22) + (j24 * j23) + (j21 * j26);
        long j29 = j27 * j23;
        long j30 = i19;
        long j31 = i18;
        long m1007a3 = AbstractC0413b.m1007a(j31, j22, j21 * j30, (j29 + (j24 * j26)) << 1);
        long j32 = (j27 * j26) << 1;
        long j33 = i21;
        long j34 = (j31 * j23) + (j24 * j30) + (j21 * j33);
        long j35 = i20;
        long m1007a4 = AbstractC0413b.m1007a(j22, j35, j34, j32);
        long j36 = (j35 * j23) + (j31 * j26) + (j27 * j30) + (j24 * j33);
        long j37 = j4 - (j36 * 76);
        long j38 = j7 - (((j31 * j30) + (((j35 * j26) + (j27 * j33)) << 1)) * 38);
        long j39 = j10 - (((j30 * j35) + (j31 * j33)) * 38);
        long j40 = m1007a - ((j35 * j33) * 76);
        long j41 = j17 - (j21 * j22);
        long j42 = j18 - j25;
        long j43 = j19 - j28;
        long j44 = j20 - m1007a3;
        int i22 = i2 + i12;
        int i23 = i3 + i13;
        int i24 = i4 + i14;
        int i25 = i5 + i15;
        int i26 = i6 + i16;
        int i27 = i7 + i17;
        int i28 = i8 + i18;
        int i29 = i9 + i19;
        int i30 = i10 + i20;
        long j45 = i22;
        long j46 = i23;
        long j47 = j45 * j46;
        long j48 = i25;
        long j49 = i24;
        long j50 = (j49 * j46) + (j45 * j48);
        long j51 = i27;
        long j52 = i26;
        long j53 = (j52 * j46) + (j49 * j48) + (j45 * j51);
        long j54 = j52 * j48;
        long j55 = i29;
        long j56 = i28;
        long m1007a5 = AbstractC0413b.m1007a(j56, j46, j45 * j55, (j54 + (j49 * j51)) << 1);
        long j57 = (j52 * j51) << 1;
        long j58 = i11 + i21;
        long j59 = (j56 * j48) + (j49 * j55) + (j45 * j58);
        long j60 = i30;
        long m1007a6 = AbstractC0413b.m1007a(j46, j60, j59, j57);
        long j61 = ((j48 * j60) + ((j56 * j51) + ((j52 * j55) + (j49 * j58)))) << 1;
        long j62 = (j56 * j55) + (((j60 * j51) + (j52 * j58)) << 1);
        long j63 = (j55 * j60) + (j56 * j58);
        long j64 = (m1007a5 - j40) + j44;
        int i31 = ((int) j64) & M26;
        long j65 = ((m1007a6 - m1007a2) - m1007a4) + (j64 >> 26);
        int i32 = ((int) j65) & M25;
        long j66 = ((((j65 >> 25) + j61) - j41) * 38) + j37;
        iArr3[0] = ((int) j66) & M26;
        long j67 = ((j62 - j42) * 38) + j38 + (j66 >> 26);
        iArr3[1] = ((int) j67) & M26;
        long j68 = ((j63 - j43) * 38) + j39 + (j67 >> 26);
        iArr3[2] = ((int) j68) & M25;
        long j69 = ((((j60 * j58) << 1) - j44) * 38) + j40 + (j68 >> 25);
        iArr3[3] = ((int) j69) & M26;
        long m1007a7 = AbstractC0413b.m1007a(m1007a4, 38L, m1007a2, j69 >> 26);
        iArr3[4] = ((int) m1007a7) & M25;
        long j70 = (j47 - j37) + j41 + (m1007a7 >> 25);
        iArr3[5] = ((int) j70) & M26;
        long j71 = (j50 - j38) + j42 + (j70 >> 26);
        iArr3[6] = ((int) j71) & M26;
        long j72 = (j53 - j39) + j43 + (j71 >> 26);
        iArr3[7] = ((int) j72) & M25;
        long j73 = (j72 >> 25) + i31;
        iArr3[8] = ((int) j73) & M26;
        iArr3[9] = i32 + ((int) (j73 >> 26));
    }

    public static void sqr(int[] iArr, int[] iArr2) {
        int i2 = iArr[0];
        int i3 = iArr[1];
        int i4 = iArr[2];
        int i5 = iArr[3];
        int i6 = iArr[4];
        int i7 = iArr[5];
        int i8 = iArr[6];
        int i9 = iArr[7];
        int i10 = iArr[8];
        int i11 = iArr[9];
        long j2 = i2;
        long j3 = j2 * j2;
        long j4 = i3 * 2;
        long j5 = j2 * j4;
        long j6 = i4 * 2;
        long j7 = i3;
        long j8 = (j7 * j7) + (j2 * j6);
        long j9 = i5 * 2;
        long j10 = (j2 * j9) + (j4 * j6);
        long j11 = i6 * 2;
        long j12 = (j7 * j9) + (j2 * j11) + (i4 * j6);
        long j13 = (j9 * j6) + (j4 * j11);
        long j14 = i5;
        long j15 = (j14 * j14) + (j6 * j11);
        long j16 = j14 * j11;
        long j17 = i6 * j11;
        long j18 = i7;
        long j19 = i8 * 2;
        long j20 = i9 * 2;
        long j21 = i8;
        long j22 = (j21 * j21) + (j18 * j20);
        long j23 = i10 * 2;
        long j24 = (j18 * j23) + (j19 * j20);
        long j25 = i11 * 2;
        long j26 = (j21 * j23) + (j18 * j25) + (i9 * j20);
        long j27 = i10;
        long j28 = j3 - (((j23 * j20) + (j19 * j25)) * 38);
        long j29 = j5 - (((j27 * j27) + (j20 * j25)) * 38);
        long j30 = j8 - ((j27 * j25) * 38);
        long j31 = j10 - ((i11 * j25) * 38);
        long j32 = j13 - (j18 * j18);
        long j33 = j15 - (j18 * j19);
        long j34 = j16 - j22;
        long j35 = j17 - j24;
        int i12 = i2 + i7;
        int i13 = i3 + i8;
        int i14 = i4 + i9;
        int i15 = i5 + i10;
        int i16 = i6 + i11;
        long j36 = i12;
        long j37 = i13 * 2;
        long j38 = i14 * 2;
        long j39 = i13;
        long j40 = (j39 * j39) + (j36 * j38);
        long j41 = i15 * 2;
        long j42 = (j36 * j41) + (j37 * j38);
        long j43 = i16 * 2;
        long j44 = (j39 * j41) + (j36 * j43) + (i14 * j38);
        long j45 = (j41 * j38) + (j37 * j43);
        long j46 = i15;
        long j47 = (j46 * j46) + (j38 * j43);
        long j48 = j46 * j43;
        long j49 = i16 * j43;
        long j50 = (j42 - j31) + j35;
        int i17 = ((int) j50) & M26;
        long j51 = ((j44 - j12) - j26) + (j50 >> 26);
        int i18 = ((int) j51) & M25;
        long j52 = ((((j51 >> 25) + j45) - j32) * 38) + j28;
        iArr2[0] = ((int) j52) & M26;
        long j53 = ((j47 - j33) * 38) + j29 + (j52 >> 26);
        iArr2[1] = ((int) j53) & M26;
        long j54 = ((j48 - j34) * 38) + j30 + (j53 >> 26);
        iArr2[2] = ((int) j54) & M25;
        long j55 = ((j49 - j35) * 38) + j31 + (j54 >> 25);
        iArr2[3] = ((int) j55) & M26;
        long m1007a = AbstractC0413b.m1007a(j26, 38L, j12, j55 >> 26);
        iArr2[4] = ((int) m1007a) & M25;
        long j56 = ((j36 * j36) - j28) + j32 + (m1007a >> 25);
        iArr2[5] = ((int) j56) & M26;
        long j57 = ((j36 * j37) - j29) + j33 + (j56 >> 26);
        iArr2[6] = ((int) j57) & M26;
        long j58 = (j40 - j30) + j34 + (j57 >> 26);
        iArr2[7] = ((int) j58) & M25;
        long j59 = (j58 >> 25) + i17;
        iArr2[8] = ((int) j59) & M26;
        iArr2[9] = i18 + ((int) (j59 >> 26));
    }
}

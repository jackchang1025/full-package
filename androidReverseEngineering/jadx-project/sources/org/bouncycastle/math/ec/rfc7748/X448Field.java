package org.bouncycastle.math.ec.rfc7748;

import android.sun.security.util.DerValue;
import org.bouncycastle.math.raw.Mod;

/* loaded from: classes.dex */
public abstract class X448Field {
    private static final int M28 = 268435455;
    private static final int[] P32 = {-1, -1, -1, -1, -1, -1, -1, -2, -1, -1, -1, -1, -1, -1};
    public static final int SIZE = 16;
    private static final long U32 = 4294967295L;

    public static void add(int[] iArr, int[] iArr2, int[] iArr3) {
        for (int i2 = 0; i2 < 16; i2++) {
            iArr3[i2] = iArr[i2] + iArr2[i2];
        }
    }

    public static void addOne(int[] iArr) {
        iArr[0] = iArr[0] + 1;
    }

    public static int areEqual(int[] iArr, int[] iArr2) {
        int i2 = 0;
        for (int i3 = 0; i3 < 16; i3++) {
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
        int i12 = iArr[10];
        int i13 = iArr[11];
        int i14 = iArr[12];
        int i15 = iArr[13];
        int i16 = iArr[14];
        int i17 = iArr[15];
        int i18 = i3 + (i2 >>> 28);
        int i19 = i2 & M28;
        int i20 = i7 + (i6 >>> 28);
        int i21 = i6 & M28;
        int i22 = i11 + (i10 >>> 28);
        int i23 = i10 & M28;
        int i24 = i15 + (i14 >>> 28);
        int i25 = i14 & M28;
        int i26 = i4 + (i18 >>> 28);
        int i27 = i18 & M28;
        int i28 = i8 + (i20 >>> 28);
        int i29 = i20 & M28;
        int i30 = i12 + (i22 >>> 28);
        int i31 = i22 & M28;
        int i32 = i16 + (i24 >>> 28);
        int i33 = i24 & M28;
        int i34 = i5 + (i26 >>> 28);
        int i35 = i26 & M28;
        int i36 = i9 + (i28 >>> 28);
        int i37 = i28 & M28;
        int i38 = i13 + (i30 >>> 28);
        int i39 = i30 & M28;
        int i40 = i17 + (i32 >>> 28);
        int i41 = i32 & M28;
        int i42 = i40 >>> 28;
        int i43 = i40 & M28;
        int i44 = i19 + i42;
        int i45 = i21 + (i34 >>> 28);
        int i46 = i34 & M28;
        int i47 = i23 + i42 + (i36 >>> 28);
        int i48 = i36 & M28;
        int i49 = i25 + (i38 >>> 28);
        int i50 = i38 & M28;
        int i51 = i27 + (i44 >>> 28);
        int i52 = i44 & M28;
        int i53 = i29 + (i45 >>> 28);
        int i54 = i45 & M28;
        int i55 = i31 + (i47 >>> 28);
        int i56 = i47 & M28;
        int i57 = i33 + (i49 >>> 28);
        int i58 = i49 & M28;
        iArr[0] = i52;
        iArr[1] = i51;
        iArr[2] = i35;
        iArr[3] = i46;
        iArr[4] = i54;
        iArr[5] = i53;
        iArr[6] = i37;
        iArr[7] = i48;
        iArr[8] = i56;
        iArr[9] = i55;
        iArr[10] = i39;
        iArr[11] = i50;
        iArr[12] = i58;
        iArr[13] = i57;
        iArr[14] = i41;
        iArr[15] = i43;
    }

    public static void cmov(int i2, int[] iArr, int i3, int[] iArr2, int i4) {
        for (int i5 = 0; i5 < 16; i5++) {
            int i6 = i4 + i5;
            int i7 = iArr2[i6];
            iArr2[i6] = i7 ^ ((iArr[i3 + i5] ^ i7) & i2);
        }
    }

    public static void cnegate(int i2, int[] iArr) {
        int[] create = create();
        sub(create, iArr, create);
        cmov(-i2, create, 0, iArr, 0);
    }

    public static void copy(int[] iArr, int i2, int[] iArr2, int i3) {
        for (int i4 = 0; i4 < 16; i4++) {
            iArr2[i3 + i4] = iArr[i2 + i4];
        }
    }

    public static int[] create() {
        return new int[16];
    }

    public static int[] createTable(int i2) {
        return new int[i2 * 16];
    }

    public static void cswap(int i2, int[] iArr, int[] iArr2) {
        int i3 = 0 - i2;
        for (int i4 = 0; i4 < 16; i4++) {
            int i5 = iArr[i4];
            int i6 = iArr2[i4];
            int i7 = (i5 ^ i6) & i3;
            iArr[i4] = i5 ^ i7;
            iArr2[i4] = i6 ^ i7;
        }
    }

    public static void decode(byte[] bArr, int i2, int[] iArr) {
        decode56(bArr, i2, iArr, 0);
        decode56(bArr, i2 + 7, iArr, 2);
        decode56(bArr, i2 + 14, iArr, 4);
        decode56(bArr, i2 + 21, iArr, 6);
        decode56(bArr, i2 + 28, iArr, 8);
        decode56(bArr, i2 + 35, iArr, 10);
        decode56(bArr, i2 + 42, iArr, 12);
        decode56(bArr, i2 + 49, iArr, 14);
    }

    private static void decode224(int[] iArr, int i2, int[] iArr2, int i3) {
        int i4 = iArr[i2 + 0];
        int i5 = iArr[i2 + 1];
        int i6 = iArr[i2 + 2];
        int i7 = iArr[i2 + 3];
        int i8 = iArr[i2 + 4];
        int i9 = iArr[i2 + 5];
        int i10 = iArr[i2 + 6];
        iArr2[i3 + 0] = i4 & M28;
        iArr2[i3 + 1] = ((i4 >>> 28) | (i5 << 4)) & M28;
        iArr2[i3 + 2] = ((i5 >>> 24) | (i6 << 8)) & M28;
        iArr2[i3 + 3] = ((i6 >>> 20) | (i7 << 12)) & M28;
        iArr2[i3 + 4] = ((i7 >>> 16) | (i8 << 16)) & M28;
        iArr2[i3 + 5] = ((i8 >>> 12) | (i9 << 20)) & M28;
        iArr2[i3 + 6] = ((i9 >>> 8) | (i10 << 24)) & M28;
        iArr2[i3 + 7] = i10 >>> 4;
    }

    private static int decode24(byte[] bArr, int i2) {
        int i3 = bArr[i2] & 255;
        int i4 = i2 + 1;
        return ((bArr[i4 + 1] & 255) << 16) | i3 | ((bArr[i4] & 255) << 8);
    }

    private static int decode32(byte[] bArr, int i2) {
        int i3 = bArr[i2] & 255;
        int i4 = i2 + 1;
        int i5 = i3 | ((bArr[i4] & 255) << 8);
        int i6 = i4 + 1;
        return (bArr[i6 + 1] << DerValue.tag_GeneralizedTime) | i5 | ((bArr[i6] & 255) << 16);
    }

    private static void decode56(byte[] bArr, int i2, int[] iArr, int i3) {
        int decode32 = decode32(bArr, i2);
        int decode24 = decode24(bArr, i2 + 4);
        iArr[i3] = M28 & decode32;
        iArr[i3 + 1] = (decode24 << 4) | (decode32 >>> 28);
    }

    public static void encode(int[] iArr, byte[] bArr, int i2) {
        encode56(iArr, 0, bArr, i2);
        encode56(iArr, 2, bArr, i2 + 7);
        encode56(iArr, 4, bArr, i2 + 14);
        encode56(iArr, 6, bArr, i2 + 21);
        encode56(iArr, 8, bArr, i2 + 28);
        encode56(iArr, 10, bArr, i2 + 35);
        encode56(iArr, 12, bArr, i2 + 42);
        encode56(iArr, 14, bArr, i2 + 49);
    }

    private static void encode224(int[] iArr, int i2, int[] iArr2, int i3) {
        int i4 = iArr[i2 + 0];
        int i5 = iArr[i2 + 1];
        int i6 = iArr[i2 + 2];
        int i7 = iArr[i2 + 3];
        int i8 = iArr[i2 + 4];
        int i9 = iArr[i2 + 5];
        int i10 = iArr[i2 + 6];
        int i11 = iArr[i2 + 7];
        iArr2[i3 + 0] = i4 | (i5 << 28);
        iArr2[i3 + 1] = (i5 >>> 4) | (i6 << 24);
        iArr2[i3 + 2] = (i6 >>> 8) | (i7 << 20);
        iArr2[i3 + 3] = (i7 >>> 12) | (i8 << 16);
        iArr2[i3 + 4] = (i8 >>> 16) | (i9 << 12);
        iArr2[i3 + 5] = (i9 >>> 20) | (i10 << 8);
        iArr2[i3 + 6] = (i11 << 4) | (i10 >>> 24);
    }

    private static void encode24(int i2, byte[] bArr, int i3) {
        bArr[i3] = (byte) i2;
        int i4 = i3 + 1;
        bArr[i4] = (byte) (i2 >>> 8);
        bArr[i4 + 1] = (byte) (i2 >>> 16);
    }

    private static void encode32(int i2, byte[] bArr, int i3) {
        bArr[i3] = (byte) i2;
        int i4 = i3 + 1;
        bArr[i4] = (byte) (i2 >>> 8);
        int i5 = i4 + 1;
        bArr[i5] = (byte) (i2 >>> 16);
        bArr[i5 + 1] = (byte) (i2 >>> 24);
    }

    private static void encode56(int[] iArr, int i2, byte[] bArr, int i3) {
        int i4 = iArr[i2];
        int i5 = iArr[i2 + 1];
        encode32((i5 << 28) | i4, bArr, i3);
        encode24(i5 >>> 4, bArr, i3 + 4);
    }

    public static void inv(int[] iArr, int[] iArr2) {
        int[] create = create();
        int[] iArr3 = new int[14];
        copy(iArr, 0, create, 0);
        normalize(create);
        encode(create, iArr3, 0);
        Mod.modOddInverse(P32, iArr3, iArr3);
        decode(iArr3, 0, iArr2);
    }

    public static void invVar(int[] iArr, int[] iArr2) {
        int[] create = create();
        int[] iArr3 = new int[14];
        copy(iArr, 0, create, 0);
        normalize(create);
        encode(create, iArr3, 0);
        Mod.modOddInverseVar(P32, iArr3, iArr3);
        decode(iArr3, 0, iArr2);
    }

    public static int isOne(int[] iArr) {
        int i2 = iArr[0] ^ 1;
        for (int i3 = 1; i3 < 16; i3++) {
            i2 |= iArr[i3];
        }
        return (((i2 >>> 1) | (i2 & 1)) - 1) >> 31;
    }

    public static boolean isOneVar(int[] iArr) {
        return isOne(iArr) != 0;
    }

    public static int isZero(int[] iArr) {
        int i2 = 0;
        for (int i3 = 0; i3 < 16; i3++) {
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
        int i13 = iArr[10];
        int i14 = iArr[11];
        int i15 = iArr[12];
        int i16 = iArr[13];
        int i17 = iArr[14];
        int i18 = iArr[15];
        long j2 = i4;
        long j3 = i2;
        long j4 = j2 * j3;
        int i19 = ((int) j4) & M28;
        long j5 = i8 * j3;
        int i20 = ((int) j5) & M28;
        long j6 = i12 * j3;
        int i21 = ((int) j6) & M28;
        long j7 = i16 * j3;
        int i22 = ((int) j7) & M28;
        long j8 = (i5 * j3) + (j4 >>> 28);
        iArr2[2] = ((int) j8) & M28;
        long j9 = (i9 * j3) + (j5 >>> 28);
        iArr2[6] = ((int) j9) & M28;
        long j10 = (i13 * j3) + (j6 >>> 28);
        iArr2[10] = ((int) j10) & M28;
        long j11 = (i17 * j3) + (j7 >>> 28);
        iArr2[14] = ((int) j11) & M28;
        long j12 = (i6 * j3) + (j8 >>> 28);
        iArr2[3] = ((int) j12) & M28;
        long j13 = j12 >>> 28;
        long j14 = (i10 * j3) + (j9 >>> 28);
        iArr2[7] = ((int) j14) & M28;
        long j15 = (i14 * j3) + (j10 >>> 28);
        iArr2[11] = ((int) j15) & M28;
        long j16 = j15 >>> 28;
        long j17 = (i18 * j3) + (j11 >>> 28);
        iArr2[15] = ((int) j17) & M28;
        long j18 = j17 >>> 28;
        long j19 = (i7 * j3) + j13;
        iArr2[4] = ((int) j19) & M28;
        long j20 = j19 >>> 28;
        long j21 = (i11 * j3) + (j14 >>> 28) + j18;
        iArr2[8] = ((int) j21) & M28;
        long j22 = j21 >>> 28;
        long j23 = (i15 * j3) + j16;
        iArr2[12] = ((int) j23) & M28;
        long j24 = j23 >>> 28;
        long j25 = (i3 * j3) + j18;
        iArr2[0] = ((int) j25) & M28;
        iArr2[1] = i19 + ((int) (j25 >>> 28));
        iArr2[5] = i20 + ((int) j20);
        iArr2[9] = i21 + ((int) j22);
        iArr2[13] = i22 + ((int) j24);
    }

    public static void negate(int[] iArr, int[] iArr2) {
        sub(create(), iArr, iArr2);
    }

    public static void normalize(int[] iArr) {
        reduce(iArr, 1);
        reduce(iArr, -1);
    }

    public static void one(int[] iArr) {
        iArr[0] = 1;
        for (int i2 = 1; i2 < 16; i2++) {
            iArr[i2] = 0;
        }
    }

    private static void powPm3d4(int[] iArr, int[] iArr2) {
        int[] create = create();
        sqr(iArr, create);
        mul(iArr, create, create);
        int[] create2 = create();
        sqr(create, create2);
        mul(iArr, create2, create2);
        int[] create3 = create();
        sqr(create2, 3, create3);
        mul(create2, create3, create3);
        int[] create4 = create();
        sqr(create3, 3, create4);
        mul(create2, create4, create4);
        int[] create5 = create();
        sqr(create4, 9, create5);
        mul(create4, create5, create5);
        int[] create6 = create();
        sqr(create5, create6);
        mul(iArr, create6, create6);
        int[] create7 = create();
        sqr(create6, 18, create7);
        mul(create5, create7, create7);
        int[] create8 = create();
        sqr(create7, 37, create8);
        mul(create7, create8, create8);
        int[] create9 = create();
        sqr(create8, 37, create9);
        mul(create7, create9, create9);
        int[] create10 = create();
        sqr(create9, 111, create10);
        mul(create9, create10, create10);
        int[] create11 = create();
        sqr(create10, create11);
        mul(iArr, create11, create11);
        int[] create12 = create();
        sqr(create11, 223, create12);
        mul(create12, create10, iArr2);
    }

    private static void reduce(int[] iArr, int i2) {
        int i3;
        int i4 = iArr[15];
        int i5 = i4 & M28;
        long j2 = (i4 >>> 28) + i2;
        int i6 = 0;
        long j3 = j2;
        while (true) {
            if (i6 >= 8) {
                break;
            }
            long j4 = j3 + (4294967295L & iArr[i6]);
            iArr[i6] = ((int) j4) & M28;
            j3 = j4 >> 28;
            i6++;
        }
        long j5 = j3 + j2;
        for (i3 = 8; i3 < 15; i3++) {
            long j6 = j5 + (iArr[i3] & 4294967295L);
            iArr[i3] = ((int) j6) & M28;
            j5 = j6 >> 28;
        }
        iArr[15] = i5 + ((int) j5);
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
        sqr(iArr, create);
        mul(create, iArr2, create);
        sqr(create, create2);
        mul(create, iArr, create);
        mul(create2, iArr, create2);
        mul(create2, iArr2, create2);
        int[] create3 = create();
        powPm3d4(create2, create3);
        mul(create3, create, create3);
        int[] create4 = create();
        sqr(create3, create4);
        mul(create4, iArr2, create4);
        sub(iArr, create4, create4);
        normalize(create4);
        if (!isZeroVar(create4)) {
            return false;
        }
        copy(create3, 0, iArr3, 0);
        return true;
    }

    public static void sub(int[] iArr, int[] iArr2, int[] iArr3) {
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
        int i12 = iArr[10];
        int i13 = iArr[11];
        int i14 = iArr[12];
        int i15 = iArr[13];
        int i16 = iArr[14];
        int i17 = iArr[15];
        int i18 = iArr2[0];
        int i19 = iArr2[1];
        int i20 = iArr2[2];
        int i21 = iArr2[3];
        int i22 = iArr2[4];
        int i23 = iArr2[5];
        int i24 = iArr2[6];
        int i25 = iArr2[7];
        int i26 = iArr2[8];
        int i27 = iArr2[9];
        int i28 = iArr2[10];
        int i29 = iArr2[11];
        int i30 = iArr2[12];
        int i31 = iArr2[13];
        int i32 = iArr2[14];
        int i33 = (i3 + 536870910) - i19;
        int i34 = (i7 + 536870910) - i23;
        int i35 = (i11 + 536870910) - i27;
        int i36 = (i15 + 536870910) - i31;
        int i37 = (i17 + 536870910) - iArr2[15];
        int i38 = ((i4 + 536870910) - i20) + (i33 >>> 28);
        int i39 = i33 & M28;
        int i40 = ((i8 + 536870910) - i24) + (i34 >>> 28);
        int i41 = i34 & M28;
        int i42 = ((i12 + 536870910) - i28) + (i35 >>> 28);
        int i43 = i35 & M28;
        int i44 = ((i16 + 536870910) - i32) + (i36 >>> 28);
        int i45 = i36 & M28;
        int i46 = ((i5 + 536870910) - i21) + (i38 >>> 28);
        int i47 = i38 & M28;
        int i48 = ((i9 + 536870910) - i25) + (i40 >>> 28);
        int i49 = i40 & M28;
        int i50 = ((i13 + 536870910) - i29) + (i42 >>> 28);
        int i51 = i42 & M28;
        int i52 = i37 + (i44 >>> 28);
        int i53 = i44 & M28;
        int i54 = i52 >>> 28;
        int i55 = i52 & M28;
        int i56 = ((i2 + 536870910) - i18) + i54;
        int i57 = ((i6 + 536870910) - i22) + (i46 >>> 28);
        int i58 = i46 & M28;
        int i59 = ((i10 + 536870908) - i26) + i54 + (i48 >>> 28);
        int i60 = i48 & M28;
        int i61 = ((i14 + 536870910) - i30) + (i50 >>> 28);
        int i62 = i50 & M28;
        int i63 = i39 + (i56 >>> 28);
        int i64 = i56 & M28;
        int i65 = i41 + (i57 >>> 28);
        int i66 = i57 & M28;
        int i67 = i43 + (i59 >>> 28);
        int i68 = i59 & M28;
        int i69 = i45 + (i61 >>> 28);
        int i70 = i61 & M28;
        iArr3[0] = i64;
        iArr3[1] = i63;
        iArr3[2] = i47;
        iArr3[3] = i58;
        iArr3[4] = i66;
        iArr3[5] = i65;
        iArr3[6] = i49;
        iArr3[7] = i60;
        iArr3[8] = i68;
        iArr3[9] = i67;
        iArr3[10] = i51;
        iArr3[11] = i62;
        iArr3[12] = i70;
        iArr3[13] = i69;
        iArr3[14] = i53;
        iArr3[15] = i55;
    }

    public static void subOne(int[] iArr) {
        int[] create = create();
        create[0] = 1;
        sub(iArr, create, iArr);
    }

    public static void zero(int[] iArr) {
        for (int i2 = 0; i2 < 16; i2++) {
            iArr[i2] = 0;
        }
    }

    public static void addOne(int[] iArr, int i2) {
        iArr[i2] = iArr[i2] + 1;
    }

    public static void decode(int[] iArr, int i2, int[] iArr2) {
        decode224(iArr, i2, iArr2, 0);
        decode224(iArr, i2 + 7, iArr2, 8);
    }

    public static void encode(int[] iArr, int[] iArr2, int i2) {
        encode224(iArr, 0, iArr2, i2);
        encode224(iArr, 8, iArr2, i2 + 7);
    }

    public static void mul(int[] iArr, int[] iArr2, int[] iArr3) {
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
        int i12 = iArr[10];
        int i13 = iArr[11];
        int i14 = iArr[12];
        int i15 = iArr[13];
        int i16 = iArr[14];
        int i17 = iArr[15];
        int i18 = iArr2[0];
        int i19 = iArr2[1];
        int i20 = iArr2[2];
        int i21 = iArr2[3];
        int i22 = iArr2[4];
        int i23 = iArr2[5];
        int i24 = iArr2[6];
        int i25 = iArr2[7];
        int i26 = iArr2[8];
        int i27 = iArr2[9];
        int i28 = iArr2[10];
        int i29 = iArr2[11];
        int i30 = iArr2[12];
        int i31 = iArr2[13];
        int i32 = iArr2[14];
        int i33 = iArr2[15];
        int i34 = i2 + i10;
        int i35 = i4 + i12;
        int i36 = i5 + i13;
        int i37 = i6 + i14;
        int i38 = i7 + i15;
        int i39 = i8 + i16;
        int i40 = i18 + i26;
        int i41 = i19 + i27;
        int i42 = i20 + i28;
        int i43 = i21 + i29;
        int i44 = i22 + i30;
        int i45 = i23 + i31;
        int i46 = i24 + i32;
        int i47 = i25 + i33;
        long j2 = i2;
        long j3 = i18;
        long j4 = j2 * j3;
        long j5 = i9;
        long j6 = i19;
        long j7 = j5 * j6;
        long j8 = i8;
        long j9 = i20;
        long j10 = (j8 * j9) + j7;
        long j11 = i7;
        long j12 = i21;
        long j13 = (j11 * j12) + j10;
        long j14 = i6;
        long j15 = i22;
        long j16 = (j14 * j15) + j13;
        long j17 = i5;
        long j18 = i23;
        long j19 = (j17 * j18) + j16;
        long j20 = i4;
        long j21 = i24;
        long j22 = (j20 * j21) + j19;
        long j23 = i3;
        long j24 = i25;
        long j25 = (j23 * j24) + j22;
        long j26 = i10;
        long j27 = i26;
        long j28 = j26 * j27;
        long j29 = i17;
        long j30 = i27;
        long j31 = j29 * j30;
        long j32 = i16;
        long j33 = i28;
        long j34 = (j32 * j33) + j31;
        long j35 = i15;
        long j36 = i29;
        long j37 = (j35 * j36) + j34;
        long j38 = i14;
        long j39 = i30;
        long j40 = (j38 * j39) + j37;
        long j41 = i13;
        long j42 = i31;
        long j43 = (j41 * j42) + j40;
        long j44 = i12;
        long j45 = i32;
        long j46 = (j44 * j45) + j43;
        long j47 = i11;
        long j48 = i33;
        long j49 = (j47 * j48) + j46;
        long j50 = i34;
        long j51 = i40;
        long j52 = j50 * j51;
        long j53 = i9 + i17;
        long j54 = i41;
        long j55 = j53 * j54;
        long j56 = i39;
        long j57 = i42;
        long j58 = (j56 * j57) + j55;
        long j59 = i38;
        long j60 = i43;
        long j61 = (j59 * j60) + j58;
        long j62 = i37;
        long j63 = i44;
        long j64 = (j62 * j63) + j61;
        long j65 = i36;
        long j66 = i45;
        long j67 = (j65 * j66) + j64;
        long j68 = i35;
        long j69 = i46;
        long j70 = (j68 * j69) + j67;
        long j71 = i3 + i11;
        long j72 = i47;
        long j73 = (j71 * j72) + j70;
        long j74 = ((j4 + j28) + j73) - j25;
        int i48 = ((int) j74) & M28;
        long j75 = j74 >>> 28;
        long j76 = ((j49 + j52) - j4) + j73;
        int i49 = ((int) j76) & M28;
        long j77 = (j2 * j6) + (j23 * j3);
        long j78 = (j44 * j48) + (j41 * j45) + (j38 * j42) + (j35 * j39) + (j32 * j36) + (j29 * j33);
        long j79 = (j50 * j54) + (j71 * j51);
        long j80 = (j68 * j72) + (j65 * j69) + (j62 * j66) + (j59 * j63) + (j56 * j60) + (j53 * j57);
        long j81 = (((j77 + ((j26 * j30) + (j47 * j27))) + j80) - ((j20 * j24) + ((j17 * j21) + ((j14 * j18) + ((j11 * j15) + ((j8 * j12) + (j5 * j9))))))) + j75;
        int i50 = ((int) j81) & M28;
        long j82 = ((j78 + j79) - j77) + j80 + (j76 >>> 28);
        int i51 = ((int) j82) & M28;
        long j83 = (j2 * j9) + (j23 * j6) + (j20 * j3);
        long j84 = (j41 * j48) + (j38 * j45) + (j35 * j42) + (j32 * j39) + (j29 * j36);
        long j85 = (j50 * j57) + (j71 * j54) + (j68 * j51);
        long j86 = (j65 * j72) + (j62 * j69) + (j59 * j66) + (j56 * j63) + (j53 * j60);
        long j87 = (((j83 + ((j26 * j33) + ((j47 * j30) + (j44 * j27)))) + j86) - ((j17 * j24) + ((j14 * j21) + ((j11 * j18) + ((j8 * j15) + (j5 * j12)))))) + (j81 >>> 28);
        int i52 = ((int) j87) & M28;
        long j88 = ((j84 + j85) - j83) + j86 + (j82 >>> 28);
        int i53 = ((int) j88) & M28;
        long j89 = (j2 * j12) + (j23 * j9) + (j20 * j6) + (j17 * j3);
        long j90 = (j38 * j48) + (j35 * j45) + (j32 * j42) + (j29 * j39);
        long j91 = (j50 * j60) + (j71 * j57) + (j68 * j54) + (j65 * j51);
        long j92 = (j62 * j72) + (j59 * j69) + (j56 * j66) + (j53 * j63);
        long j93 = (((j89 + ((j26 * j36) + ((j47 * j33) + ((j44 * j30) + (j41 * j27))))) + j92) - ((j14 * j24) + ((j11 * j21) + ((j8 * j18) + (j5 * j15))))) + (j87 >>> 28);
        int i54 = ((int) j93) & M28;
        long j94 = ((j90 + j91) - j89) + j92 + (j88 >>> 28);
        int i55 = ((int) j94) & M28;
        long j95 = (j2 * j15) + (j23 * j12) + (j20 * j9) + (j17 * j6) + (j14 * j3);
        long j96 = (j35 * j48) + (j32 * j45) + (j29 * j42);
        long j97 = (j50 * j63) + (j71 * j60) + (j68 * j57) + (j65 * j54) + (j62 * j51);
        long j98 = (j59 * j72) + (j56 * j69) + (j53 * j66);
        long j99 = (((j95 + ((j26 * j39) + ((j47 * j36) + ((j44 * j33) + ((j41 * j30) + (j38 * j27)))))) + j98) - ((j11 * j24) + ((j8 * j21) + (j5 * j18)))) + (j93 >>> 28);
        int i56 = ((int) j99) & M28;
        long j100 = ((j96 + j97) - j95) + j98 + (j94 >>> 28);
        int i57 = ((int) j100) & M28;
        long j101 = (j2 * j18) + (j23 * j15) + (j20 * j12) + (j17 * j9) + (j14 * j6) + (j11 * j3);
        long j102 = (j32 * j48) + (j29 * j45);
        long j103 = (j50 * j66) + (j71 * j63) + (j68 * j60) + (j65 * j57) + (j62 * j54) + (j59 * j51);
        long j104 = (j56 * j72) + (j53 * j69);
        long j105 = (((j101 + ((j26 * j42) + ((j47 * j39) + ((j44 * j36) + ((j41 * j33) + ((j38 * j30) + (j35 * j27))))))) + j104) - ((j8 * j24) + (j5 * j21))) + (j99 >>> 28);
        int i58 = ((int) j105) & M28;
        long j106 = ((j102 + j103) - j101) + j104 + (j100 >>> 28);
        int i59 = ((int) j106) & M28;
        long j107 = (j2 * j21) + (j23 * j18) + (j20 * j15) + (j17 * j12) + (j14 * j9) + (j11 * j6) + (j8 * j3);
        long j108 = j29 * j48;
        long j109 = (j50 * j69) + (j71 * j66) + (j68 * j63) + (j65 * j60) + (j62 * j57) + (j59 * j54) + (j56 * j51);
        long j110 = j53 * j72;
        long j111 = (((j107 + ((j26 * j45) + ((j47 * j42) + ((j44 * j39) + ((j41 * j36) + ((j38 * j33) + ((j35 * j30) + (j32 * j27)))))))) + j110) - (j5 * j24)) + (j105 >>> 28);
        int i60 = ((int) j111) & M28;
        long j112 = ((j108 + j109) - j107) + j110 + (j106 >>> 28);
        int i61 = ((int) j112) & M28;
        long j113 = (j2 * j24) + (j23 * j21) + (j20 * j18) + (j17 * j15) + (j14 * j12) + (j11 * j9) + (j6 * j8) + (j3 * j5);
        long j114 = (j26 * j48) + (j47 * j45) + (j44 * j42) + (j41 * j39) + (j38 * j36) + (j33 * j35) + (j30 * j32) + (j29 * j27);
        long j115 = j113 + j114 + (j111 >>> 28);
        int i62 = ((int) j115) & M28;
        long j116 = (((j50 * j72) + ((j71 * j69) + ((j68 * j66) + ((j65 * j63) + ((j62 * j60) + ((j59 * j57) + ((j56 * j54) + (j53 * j51)))))))) - j113) + (j112 >>> 28);
        int i63 = ((int) j116) & M28;
        long j117 = j116 >>> 28;
        long j118 = (j115 >>> 28) + j117 + i49;
        int i64 = ((int) j118) & M28;
        long j119 = j117 + i48;
        iArr3[0] = ((int) j119) & M28;
        iArr3[1] = i50 + ((int) (j119 >>> 28));
        iArr3[2] = i52;
        iArr3[3] = i54;
        iArr3[4] = i56;
        iArr3[5] = i58;
        iArr3[6] = i60;
        iArr3[7] = i62;
        iArr3[8] = i64;
        iArr3[9] = i51 + ((int) (j118 >>> 28));
        iArr3[10] = i53;
        iArr3[11] = i55;
        iArr3[12] = i57;
        iArr3[13] = i59;
        iArr3[14] = i61;
        iArr3[15] = i63;
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
        int i12 = iArr[10];
        int i13 = iArr[11];
        int i14 = iArr[12];
        int i15 = iArr[13];
        int i16 = iArr[14];
        int i17 = iArr[15];
        int i18 = i2 * 2;
        int i19 = i3 * 2;
        int i20 = i4 * 2;
        int i21 = i5 * 2;
        int i22 = i6 * 2;
        int i23 = i7 * 2;
        int i24 = i8 * 2;
        int i25 = i10 * 2;
        int i26 = i11 * 2;
        int i27 = i12 * 2;
        int i28 = i13 * 2;
        int i29 = i14 * 2;
        int i30 = i15 * 2;
        int i31 = i16 * 2;
        int i32 = i2 + i10;
        int i33 = i3 + i11;
        int i34 = i4 + i12;
        int i35 = i5 + i13;
        int i36 = i6 + i14;
        int i37 = i7 + i15;
        int i38 = i8 + i16;
        int i39 = i9 + i17;
        int i40 = i32 * 2;
        int i41 = i33 * 2;
        int i42 = i34 * 2;
        int i43 = i35 * 2;
        int i44 = i37 * 2;
        long j2 = i2;
        long j3 = j2 * j2;
        long j4 = i9;
        long j5 = i19;
        long j6 = j4 * j5;
        long j7 = i8;
        long j8 = i20;
        long j9 = (j7 * j8) + j6;
        long j10 = i7;
        long j11 = i21;
        long j12 = i6;
        long j13 = (j12 * j12) + (j10 * j11) + j9;
        long j14 = i10;
        long j15 = i17;
        long j16 = i26;
        long j17 = j15 * j16;
        long j18 = i16;
        long j19 = i27;
        long j20 = (j18 * j19) + j17;
        long j21 = i15;
        long j22 = i28;
        long j23 = (j21 * j22) + j20;
        long j24 = i14;
        long j25 = i32;
        long j26 = i39;
        long j27 = i41 & 4294967295L;
        long j28 = j26 * j27;
        long j29 = i38;
        long j30 = i42 & 4294967295L;
        long j31 = (j29 * j30) + j28;
        long j32 = i37;
        long j33 = i43 & 4294967295L;
        long j34 = (j32 * j33) + j31;
        long j35 = i36;
        long j36 = (j35 * j35) + j34;
        long j37 = ((j3 + (j14 * j14)) + j36) - j13;
        int i45 = ((int) j37) & M28;
        long j38 = ((((j24 * j24) + j23) + (j25 * j25)) - j3) + j36;
        int i46 = ((int) j38) & M28;
        long j39 = j38 >>> 28;
        long j40 = i3;
        long j41 = i18;
        long j42 = j40 * j41;
        long j43 = j7 * j11;
        long j44 = i22;
        long j45 = j10 * j44;
        long j46 = i11;
        long j47 = i25;
        long j48 = j46 * j47;
        long j49 = (j18 * j22) + (j15 * j19);
        long j50 = i29;
        long j51 = (j21 * j50) + j49;
        long j52 = i33;
        long j53 = i40 & 4294967295L;
        long j54 = (j29 * j33) + (j26 * j30);
        long j55 = (i36 * 2) & 4294967295L;
        long j56 = (j32 * j55) + j54;
        long j57 = (((j42 + j48) + j56) - (j45 + (j43 + (j4 * j8)))) + (j37 >>> 28);
        int i47 = ((int) j57) & M28;
        long j58 = ((j51 + (j52 * j53)) - j42) + j56 + j39;
        int i48 = ((int) j58) & M28;
        long j59 = j58 >>> 28;
        long j60 = i4;
        long j61 = (j40 * j40) + (j60 * j41);
        long j62 = i12;
        long j63 = (j46 * j46) + (j62 * j47);
        long j64 = i34;
        long j65 = (j32 * j32) + (j29 * j55) + (j26 * j33);
        long j66 = (((j61 + j63) + j65) - ((j10 * j10) + ((j7 * j44) + (j4 * j11)))) + (j57 >>> 28);
        int i49 = ((int) j66) & M28;
        long j67 = ((((j21 * j21) + ((j18 * j50) + (j15 * j22))) + ((j52 * j52) + (j64 * j53))) - j61) + j65 + j59;
        int i50 = ((int) j67) & M28;
        long j68 = i5;
        long j69 = (j60 * j5) + (j68 * j41);
        long j70 = i23;
        long j71 = i13;
        long j72 = (j62 * j16) + (j71 * j47);
        long j73 = i30;
        long j74 = j18 * j73;
        long j75 = i35;
        long j76 = j64 * j27;
        long j77 = j55 * j26;
        long j78 = i44 & 4294967295L;
        long j79 = (j29 * j78) + j77;
        long j80 = (((j69 + j72) + j79) - ((j7 * j70) + (j4 * j44))) + (j66 >>> 28);
        int i51 = ((int) j80) & M28;
        long j81 = j80 >>> 28;
        long j82 = (((j74 + (j15 * j50)) + (j76 + (j75 * j53))) - j69) + j79 + (j67 >>> 28);
        int i52 = ((int) j82) & M28;
        long j83 = (j60 * j60) + (j68 * j5) + (j12 * j41);
        long j84 = (j7 * j7) + (j70 * j4);
        long j85 = (j62 * j62) + (j71 * j16) + (j24 * j47);
        long j86 = (j29 * j29) + (j78 * j26);
        long j87 = (((j83 + j85) + j86) - j84) + j81;
        int i53 = ((int) j87) & M28;
        long j88 = j87 >>> 28;
        long j89 = ((((j18 * j18) + (j15 * j73)) + ((j64 * j64) + ((j75 * j27) + (j35 * j53)))) - j83) + j86 + (j82 >>> 28);
        int i54 = ((int) j89) & M28;
        long j90 = (j68 * j8) + (j12 * j5) + (j10 * j41);
        long j91 = (j71 * j19) + (j24 * j16) + (j21 * j47);
        long j92 = (j75 * j30) + (j35 * j27) + (j32 * j53);
        long j93 = ((i38 * 2) & 4294967295L) * j26;
        long j94 = (((j90 + j91) + j93) - (i24 * j4)) + j88;
        int i55 = ((int) j94) & M28;
        long j95 = (((i31 * j15) + j92) - j90) + j93 + (j89 >>> 28);
        int i56 = ((int) j95) & M28;
        long j96 = (j68 * j68) + (j12 * j8) + (j10 * j5) + (j7 * j41);
        long j97 = j26 * j26;
        long j98 = (((j96 + ((j71 * j71) + ((j24 * j19) + ((j21 * j16) + (j18 * j47))))) + j97) - (j4 * j4)) + (j94 >>> 28);
        int i57 = ((int) j98) & M28;
        long j99 = (((j15 * j15) + ((j75 * j75) + ((j35 * j30) + ((j32 * j27) + (j29 * j53))))) - j96) + j97 + (j95 >>> 28);
        int i58 = ((int) j99) & M28;
        long j100 = (j12 * j11) + (j10 * j8) + (j7 * j5) + (j4 * j41);
        long j101 = (j24 * j22) + (j21 * j19) + (j18 * j16) + (j47 * j15) + j100 + (j98 >>> 28);
        int i59 = ((int) j101) & M28;
        long j102 = (((j35 * j33) + ((j32 * j30) + ((j29 * j27) + (j26 * j53)))) - j100) + (j99 >>> 28);
        int i60 = ((int) j102) & M28;
        long j103 = j102 >>> 28;
        long j104 = (j101 >>> 28) + j103 + i46;
        int i61 = ((int) j104) & M28;
        long j105 = j103 + i45;
        iArr2[0] = ((int) j105) & M28;
        iArr2[1] = i47 + ((int) (j105 >>> 28));
        iArr2[2] = i49;
        iArr2[3] = i51;
        iArr2[4] = i53;
        iArr2[5] = i55;
        iArr2[6] = i57;
        iArr2[7] = i59;
        iArr2[8] = i61;
        iArr2[9] = i48 + ((int) (j104 >>> 28));
        iArr2[10] = i50;
        iArr2[11] = i52;
        iArr2[12] = i54;
        iArr2[13] = i56;
        iArr2[14] = i58;
        iArr2[15] = i60;
    }
}

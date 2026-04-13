package org.bouncycastle.math.ec.rfc8032;

import android.sun.security.util.DerValue;
import java.security.SecureRandom;
import org.bouncycastle.crypto.Xof;
import org.bouncycastle.crypto.digests.SHAKEDigest;
import org.bouncycastle.math.ec.rfc7748.X448;
import org.bouncycastle.math.ec.rfc7748.X448Field;
import org.bouncycastle.math.raw.Nat;
import org.bouncycastle.tls.CipherSuite;
import org.bouncycastle.util.Arrays;

/* loaded from: classes.dex */
public abstract class Ed448 {
    private static final int COORD_INTS = 14;
    private static final int C_d = -39081;
    private static final int L4_0 = 43969588;
    private static final int L4_1 = 30366549;
    private static final int L4_2 = 163752818;
    private static final int L4_3 = 258169998;
    private static final int L4_4 = 96434764;
    private static final int L4_5 = 227822194;
    private static final int L4_6 = 149865618;
    private static final int L4_7 = 550336261;
    private static final int L_0 = 78101261;
    private static final int L_1 = 141809365;
    private static final int L_2 = 175155932;
    private static final int L_3 = 64542499;
    private static final int L_4 = 158326419;
    private static final int L_5 = 191173276;
    private static final int L_6 = 104575268;
    private static final int L_7 = 137584065;
    private static final long M26L = 67108863;
    private static final long M28L = 268435455;
    private static final long M32L = 4294967295L;
    private static final int POINT_BYTES = 57;
    private static final int PRECOMP_BLOCKS = 5;
    private static final int PRECOMP_MASK = 15;
    private static final int PRECOMP_POINTS = 16;
    private static final int PRECOMP_SPACING = 18;
    private static final int PRECOMP_TEETH = 5;
    public static final int PREHASH_SIZE = 64;
    public static final int PUBLIC_KEY_SIZE = 57;
    private static final int SCALAR_BYTES = 57;
    private static final int SCALAR_INTS = 14;
    public static final int SECRET_KEY_SIZE = 57;
    public static final int SIGNATURE_SIZE = 114;
    private static final int WNAF_WIDTH_BASE = 7;
    private static final byte[] DOM4_PREFIX = {83, 105, 103, 69, 100, 52, 52, 56};

    /* renamed from: P */
    private static final int[] f1508P = {-1, -1, -1, -1, -1, -1, -1, -2, -1, -1, -1, -1, -1, -1};

    /* renamed from: L */
    private static final int[] f1507L = {-1420278541, 595116690, -1916432555, 560775794, -1361693040, -1001465015, 2093622249, -1, -1, -1, -1, -1, -1, 1073741823};
    private static final int[] B_x = {118276190, 40534716, 9670182, 135141552, 85017403, 259173222, 68333082, 171784774, 174973732, 15824510, 73756743, 57518561, 94773951, 248652241, 107736333, 82941708};
    private static final int[] B_y = {36764180, 8885695, 130592152, 20104429, 163904957, 30304195, 121295871, 5901357, 125344798, 171541512, 175338348, 209069246, 3626697, 38307682, 24032956, 110359655};
    private static final Object precompLock = new Object();
    private static PointExt[] precompBaseTable = null;
    private static int[] precompBase = null;

    public static final class Algorithm {
        public static final int Ed448 = 0;
        public static final int Ed448ph = 1;
    }

    /* renamed from: org.bouncycastle.math.ec.rfc8032.Ed448$F */
    public static class C0740F extends X448Field {
        private C0740F() {
        }
    }

    public static class PointExt {

        /* renamed from: x */
        int[] f1509x;

        /* renamed from: y */
        int[] f1510y;

        /* renamed from: z */
        int[] f1511z;

        private PointExt() {
            this.f1509x = X448Field.create();
            this.f1510y = X448Field.create();
            this.f1511z = X448Field.create();
        }
    }

    public static class PointPrecomp {

        /* renamed from: x */
        int[] f1512x;

        /* renamed from: y */
        int[] f1513y;

        private PointPrecomp() {
            this.f1512x = X448Field.create();
            this.f1513y = X448Field.create();
        }
    }

    private static byte[] calculateS(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        int[] iArr = new int[28];
        decodeScalar(bArr, 0, iArr);
        int[] iArr2 = new int[14];
        decodeScalar(bArr2, 0, iArr2);
        int[] iArr3 = new int[14];
        decodeScalar(bArr3, 0, iArr3);
        Nat.mulAddTo(14, iArr2, iArr3, iArr);
        byte[] bArr4 = new byte[114];
        for (int i2 = 0; i2 < 28; i2++) {
            encode32(iArr[i2], bArr4, i2 * 4);
        }
        return reduceScalar(bArr4);
    }

    private static boolean checkContextVar(byte[] bArr) {
        return bArr != null && bArr.length < 256;
    }

    private static int checkPoint(int[] iArr, int[] iArr2) {
        int[] create = X448Field.create();
        int[] create2 = X448Field.create();
        int[] create3 = X448Field.create();
        X448Field.sqr(iArr, create2);
        X448Field.sqr(iArr2, create3);
        X448Field.mul(create2, create3, create);
        X448Field.add(create2, create3, create2);
        X448Field.mul(create, 39081, create);
        X448Field.subOne(create);
        X448Field.add(create, create2, create);
        X448Field.normalize(create);
        return X448Field.isZero(create);
    }

    private static boolean checkPointVar(byte[] bArr) {
        if ((bArr[56] & Byte.MAX_VALUE) != 0) {
            return false;
        }
        decode32(bArr, 0, new int[14], 0, 14);
        return !Nat.gte(14, r2, f1508P);
    }

    private static boolean checkScalarVar(byte[] bArr, int[] iArr) {
        if (bArr[56] != 0) {
            return false;
        }
        decodeScalar(bArr, 0, iArr);
        return !Nat.gte(14, iArr, f1507L);
    }

    private static byte[] copy(byte[] bArr, int i2, int i3) {
        byte[] bArr2 = new byte[i3];
        System.arraycopy(bArr, i2, bArr2, 0, i3);
        return bArr2;
    }

    public static Xof createPrehash() {
        return createXof();
    }

    private static Xof createXof() {
        return new SHAKEDigest(256);
    }

    private static int decode16(byte[] bArr, int i2) {
        return ((bArr[i2 + 1] & 255) << 8) | (bArr[i2] & 255);
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

    private static boolean decodePointVar(byte[] bArr, int i2, boolean z2, PointExt pointExt) {
        byte[] copy = copy(bArr, i2, 57);
        if (!checkPointVar(copy)) {
            return false;
        }
        byte b = copy[56];
        int i3 = (b & DerValue.TAG_CONTEXT) >>> 7;
        copy[56] = (byte) (b & Byte.MAX_VALUE);
        X448Field.decode(copy, 0, pointExt.f1510y);
        int[] create = X448Field.create();
        int[] create2 = X448Field.create();
        X448Field.sqr(pointExt.f1510y, create);
        X448Field.mul(create, 39081, create2);
        X448Field.negate(create, create);
        X448Field.addOne(create);
        X448Field.addOne(create2);
        if (!X448Field.sqrtRatioVar(create, create2, pointExt.f1509x)) {
            return false;
        }
        X448Field.normalize(pointExt.f1509x);
        if (i3 == 1 && X448Field.isZeroVar(pointExt.f1509x)) {
            return false;
        }
        int[] iArr = pointExt.f1509x;
        if (z2 ^ (i3 != (iArr[0] & 1))) {
            X448Field.negate(iArr, iArr);
        }
        pointExtendXY(pointExt);
        return true;
    }

    private static void decodeScalar(byte[] bArr, int i2, int[] iArr) {
        decode32(bArr, i2, iArr, 0, 14);
    }

    private static void dom4(Xof xof, byte b, byte[] bArr) {
        byte[] bArr2 = DOM4_PREFIX;
        int length = bArr2.length;
        int i2 = length + 2;
        int length2 = bArr.length + i2;
        byte[] bArr3 = new byte[length2];
        System.arraycopy(bArr2, 0, bArr3, 0, length);
        bArr3[length] = b;
        bArr3[length + 1] = (byte) bArr.length;
        System.arraycopy(bArr, 0, bArr3, i2, bArr.length);
        xof.update(bArr3, 0, length2);
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

    private static void encode56(long j2, byte[] bArr, int i2) {
        encode32((int) j2, bArr, i2);
        encode24((int) (j2 >>> 32), bArr, i2 + 4);
    }

    private static int encodePoint(PointExt pointExt, byte[] bArr, int i2) {
        int[] create = X448Field.create();
        int[] create2 = X448Field.create();
        X448Field.inv(pointExt.f1511z, create2);
        X448Field.mul(pointExt.f1509x, create2, create);
        X448Field.mul(pointExt.f1510y, create2, create2);
        X448Field.normalize(create);
        X448Field.normalize(create2);
        int checkPoint = checkPoint(create, create2);
        X448Field.encode(create2, bArr, i2);
        bArr[(i2 + 57) - 1] = (byte) ((create[0] & 1) << 7);
        return checkPoint;
    }

    public static void generatePrivateKey(SecureRandom secureRandom, byte[] bArr) {
        secureRandom.nextBytes(bArr);
    }

    public static void generatePublicKey(byte[] bArr, int i2, byte[] bArr2, int i3) {
        Xof createXof = createXof();
        byte[] bArr3 = new byte[114];
        createXof.update(bArr, i2, 57);
        createXof.doFinal(bArr3, 0, 114);
        byte[] bArr4 = new byte[57];
        pruneScalar(bArr3, 0, bArr4);
        scalarMultBaseEncoded(bArr4, bArr2, i3);
    }

    private static int getWindow4(int[] iArr, int i2) {
        return (iArr[i2 >>> 3] >>> ((i2 & 7) << 2)) & 15;
    }

    private static byte[] getWnafVar(int[] iArr, int i2) {
        int[] iArr2 = new int[28];
        int i3 = 0;
        int i4 = 14;
        int i5 = 28;
        int i6 = 0;
        while (true) {
            i4--;
            if (i4 < 0) {
                break;
            }
            int i7 = iArr[i4];
            int i8 = i5 - 1;
            iArr2[i8] = (i6 << 16) | (i7 >>> 16);
            i5 = i8 - 1;
            iArr2[i5] = i7;
            i6 = i7;
        }
        byte[] bArr = new byte[447];
        int i9 = 32 - i2;
        int i10 = 0;
        int i11 = 0;
        while (i3 < 28) {
            int i12 = iArr2[i3];
            while (i10 < 16) {
                int i13 = i12 >>> i10;
                if ((i13 & 1) == i11) {
                    i10++;
                } else {
                    int i14 = (i13 | 1) << i9;
                    bArr[(i3 << 4) + i10] = (byte) (i14 >> i9);
                    i10 += i2;
                    i11 = i14 >>> 31;
                }
            }
            i3++;
            i10 -= 16;
        }
        return bArr;
    }

    private static void implSign(Xof xof, byte[] bArr, byte[] bArr2, byte[] bArr3, int i2, byte[] bArr4, byte b, byte[] bArr5, int i3, int i4, byte[] bArr6, int i5) {
        dom4(xof, b, bArr4);
        xof.update(bArr, 57, 57);
        xof.update(bArr5, i3, i4);
        xof.doFinal(bArr, 0, bArr.length);
        byte[] reduceScalar = reduceScalar(bArr);
        byte[] bArr7 = new byte[57];
        scalarMultBaseEncoded(reduceScalar, bArr7, 0);
        dom4(xof, b, bArr4);
        xof.update(bArr7, 0, 57);
        xof.update(bArr3, i2, 57);
        xof.update(bArr5, i3, i4);
        xof.doFinal(bArr, 0, bArr.length);
        byte[] calculateS = calculateS(reduceScalar, reduceScalar(bArr), bArr2);
        System.arraycopy(bArr7, 0, bArr6, i5, 57);
        System.arraycopy(calculateS, 0, bArr6, i5 + 57, 57);
    }

    private static boolean implVerify(byte[] bArr, int i2, byte[] bArr2, int i3, byte[] bArr3, byte b, byte[] bArr4, int i4, int i5) {
        if (!checkContextVar(bArr3)) {
            throw new IllegalArgumentException("ctx");
        }
        byte[] copy = copy(bArr, i2, 57);
        byte[] copy2 = copy(bArr, i2 + 57, 57);
        if (!checkPointVar(copy)) {
            return false;
        }
        int[] iArr = new int[14];
        if (!checkScalarVar(copy2, iArr)) {
            return false;
        }
        PointExt pointExt = new PointExt();
        if (!decodePointVar(bArr2, i3, true, pointExt)) {
            return false;
        }
        Xof createXof = createXof();
        byte[] bArr5 = new byte[114];
        dom4(createXof, b, bArr3);
        createXof.update(copy, 0, 57);
        createXof.update(bArr2, i3, 57);
        createXof.update(bArr4, i4, i5);
        createXof.doFinal(bArr5, 0, 114);
        int[] iArr2 = new int[14];
        decodeScalar(reduceScalar(bArr5), 0, iArr2);
        PointExt pointExt2 = new PointExt();
        scalarMultStrausVar(iArr, iArr2, pointExt, pointExt2);
        byte[] bArr6 = new byte[57];
        return encodePoint(pointExt2, bArr6, 0) != 0 && Arrays.areEqual(bArr6, copy);
    }

    private static boolean isNeutralElementVar(int[] iArr, int[] iArr2, int[] iArr3) {
        return X448Field.isZeroVar(iArr) && X448Field.areEqualVar(iArr2, iArr3);
    }

    private static void pointAdd(PointExt pointExt, PointExt pointExt2) {
        int[] create = X448Field.create();
        int[] create2 = X448Field.create();
        int[] create3 = X448Field.create();
        int[] create4 = X448Field.create();
        int[] create5 = X448Field.create();
        int[] create6 = X448Field.create();
        int[] create7 = X448Field.create();
        int[] create8 = X448Field.create();
        X448Field.mul(pointExt.f1511z, pointExt2.f1511z, create);
        X448Field.sqr(create, create2);
        X448Field.mul(pointExt.f1509x, pointExt2.f1509x, create3);
        X448Field.mul(pointExt.f1510y, pointExt2.f1510y, create4);
        X448Field.mul(create3, create4, create5);
        X448Field.mul(create5, 39081, create5);
        X448Field.add(create2, create5, create6);
        X448Field.sub(create2, create5, create7);
        X448Field.add(pointExt.f1509x, pointExt.f1510y, create2);
        X448Field.add(pointExt2.f1509x, pointExt2.f1510y, create5);
        X448Field.mul(create2, create5, create8);
        X448Field.add(create4, create3, create2);
        X448Field.sub(create4, create3, create5);
        X448Field.carry(create2);
        X448Field.sub(create8, create2, create8);
        X448Field.mul(create8, create, create8);
        X448Field.mul(create5, create, create5);
        X448Field.mul(create6, create8, pointExt2.f1509x);
        X448Field.mul(create5, create7, pointExt2.f1510y);
        X448Field.mul(create6, create7, pointExt2.f1511z);
    }

    private static void pointAddPrecomp(PointPrecomp pointPrecomp, PointExt pointExt) {
        int[] create = X448Field.create();
        int[] create2 = X448Field.create();
        int[] create3 = X448Field.create();
        int[] create4 = X448Field.create();
        int[] create5 = X448Field.create();
        int[] create6 = X448Field.create();
        int[] create7 = X448Field.create();
        X448Field.sqr(pointExt.f1511z, create);
        X448Field.mul(pointPrecomp.f1512x, pointExt.f1509x, create2);
        X448Field.mul(pointPrecomp.f1513y, pointExt.f1510y, create3);
        X448Field.mul(create2, create3, create4);
        X448Field.mul(create4, 39081, create4);
        X448Field.add(create, create4, create5);
        X448Field.sub(create, create4, create6);
        X448Field.add(pointPrecomp.f1512x, pointPrecomp.f1513y, create);
        X448Field.add(pointExt.f1509x, pointExt.f1510y, create4);
        X448Field.mul(create, create4, create7);
        X448Field.add(create3, create2, create);
        X448Field.sub(create3, create2, create4);
        X448Field.carry(create);
        X448Field.sub(create7, create, create7);
        X448Field.mul(create7, pointExt.f1511z, create7);
        X448Field.mul(create4, pointExt.f1511z, create4);
        X448Field.mul(create5, create7, pointExt.f1509x);
        X448Field.mul(create4, create6, pointExt.f1510y);
        X448Field.mul(create5, create6, pointExt.f1511z);
    }

    private static void pointAddVar(boolean z2, PointExt pointExt, PointExt pointExt2) {
        int[] iArr;
        int[] iArr2;
        int[] iArr3;
        int[] iArr4;
        int[] create = X448Field.create();
        int[] create2 = X448Field.create();
        int[] create3 = X448Field.create();
        int[] create4 = X448Field.create();
        int[] create5 = X448Field.create();
        int[] create6 = X448Field.create();
        int[] create7 = X448Field.create();
        int[] create8 = X448Field.create();
        if (z2) {
            X448Field.sub(pointExt.f1510y, pointExt.f1509x, create8);
            iArr2 = create2;
            iArr = create5;
            iArr4 = create6;
            iArr3 = create7;
        } else {
            X448Field.add(pointExt.f1510y, pointExt.f1509x, create8);
            iArr = create2;
            iArr2 = create5;
            iArr3 = create6;
            iArr4 = create7;
        }
        X448Field.mul(pointExt.f1511z, pointExt2.f1511z, create);
        X448Field.sqr(create, create2);
        X448Field.mul(pointExt.f1509x, pointExt2.f1509x, create3);
        X448Field.mul(pointExt.f1510y, pointExt2.f1510y, create4);
        X448Field.mul(create3, create4, create5);
        X448Field.mul(create5, 39081, create5);
        X448Field.add(create2, create5, iArr3);
        X448Field.sub(create2, create5, iArr4);
        X448Field.add(pointExt2.f1509x, pointExt2.f1510y, create5);
        X448Field.mul(create8, create5, create8);
        X448Field.add(create4, create3, iArr);
        X448Field.sub(create4, create3, iArr2);
        X448Field.carry(iArr);
        X448Field.sub(create8, create2, create8);
        X448Field.mul(create8, create, create8);
        X448Field.mul(create5, create, create5);
        X448Field.mul(create6, create8, pointExt2.f1509x);
        X448Field.mul(create5, create7, pointExt2.f1510y);
        X448Field.mul(create6, create7, pointExt2.f1511z);
    }

    private static PointExt pointCopy(PointExt pointExt) {
        PointExt pointExt2 = new PointExt();
        pointCopy(pointExt, pointExt2);
        return pointExt2;
    }

    private static void pointDouble(PointExt pointExt) {
        int[] create = X448Field.create();
        int[] create2 = X448Field.create();
        int[] create3 = X448Field.create();
        int[] create4 = X448Field.create();
        int[] create5 = X448Field.create();
        int[] create6 = X448Field.create();
        X448Field.add(pointExt.f1509x, pointExt.f1510y, create);
        X448Field.sqr(create, create);
        X448Field.sqr(pointExt.f1509x, create2);
        X448Field.sqr(pointExt.f1510y, create3);
        X448Field.add(create2, create3, create4);
        X448Field.carry(create4);
        X448Field.sqr(pointExt.f1511z, create5);
        X448Field.add(create5, create5, create5);
        X448Field.carry(create5);
        X448Field.sub(create4, create5, create6);
        X448Field.sub(create, create4, create);
        X448Field.sub(create2, create3, create2);
        X448Field.mul(create, create6, pointExt.f1509x);
        X448Field.mul(create4, create2, pointExt.f1510y);
        X448Field.mul(create4, create6, pointExt.f1511z);
    }

    private static void pointExtendXY(PointExt pointExt) {
        X448Field.one(pointExt.f1511z);
    }

    private static void pointLookup(int i2, int i3, PointPrecomp pointPrecomp) {
        int i4 = i2 * 16 * 2 * 16;
        for (int i5 = 0; i5 < 16; i5++) {
            int i6 = ((i5 ^ i3) - 1) >> 31;
            X448Field.cmov(i6, precompBase, i4, pointPrecomp.f1512x, 0);
            int i7 = i4 + 16;
            X448Field.cmov(i6, precompBase, i7, pointPrecomp.f1513y, 0);
            i4 = i7 + 16;
        }
    }

    private static int[] pointPrecompute(PointExt pointExt, int i2) {
        PointExt pointCopy = pointCopy(pointExt);
        PointExt pointCopy2 = pointCopy(pointCopy);
        pointDouble(pointCopy2);
        int[] createTable = X448Field.createTable(i2 * 3);
        int i3 = 0;
        int i4 = 0;
        while (true) {
            X448Field.copy(pointCopy.f1509x, 0, createTable, i3);
            int i5 = i3 + 16;
            X448Field.copy(pointCopy.f1510y, 0, createTable, i5);
            int i6 = i5 + 16;
            X448Field.copy(pointCopy.f1511z, 0, createTable, i6);
            i3 = i6 + 16;
            i4++;
            if (i4 == i2) {
                return createTable;
            }
            pointAdd(pointCopy2, pointCopy);
        }
    }

    private static PointExt[] pointPrecomputeVar(PointExt pointExt, int i2) {
        PointExt pointCopy = pointCopy(pointExt);
        pointDouble(pointCopy);
        PointExt[] pointExtArr = new PointExt[i2];
        pointExtArr[0] = pointCopy(pointExt);
        for (int i3 = 1; i3 < i2; i3++) {
            PointExt pointCopy2 = pointCopy(pointExtArr[i3 - 1]);
            pointExtArr[i3] = pointCopy2;
            pointAddVar(false, pointCopy, pointCopy2);
        }
        return pointExtArr;
    }

    private static void pointSetNeutral(PointExt pointExt) {
        X448Field.zero(pointExt.f1509x);
        X448Field.one(pointExt.f1510y);
        X448Field.one(pointExt.f1511z);
    }

    public static void precompute() {
        synchronized (precompLock) {
            if (precompBase != null) {
                return;
            }
            PointExt pointExt = new PointExt();
            X448Field.copy(B_x, 0, pointExt.f1509x, 0);
            X448Field.copy(B_y, 0, pointExt.f1510y, 0);
            pointExtendXY(pointExt);
            precompBaseTable = pointPrecomputeVar(pointExt, 32);
            precompBase = X448Field.createTable(CipherSuite.TLS_DH_RSA_WITH_AES_128_GCM_SHA256);
            int i2 = 0;
            for (int i3 = 0; i3 < 5; i3++) {
                PointExt[] pointExtArr = new PointExt[5];
                PointExt pointExt2 = new PointExt();
                pointSetNeutral(pointExt2);
                int i4 = 0;
                while (true) {
                    if (i4 >= 5) {
                        break;
                    }
                    pointAddVar(true, pointExt, pointExt2);
                    pointDouble(pointExt);
                    pointExtArr[i4] = pointCopy(pointExt);
                    if (i3 + i4 != 8) {
                        for (int i5 = 1; i5 < 18; i5++) {
                            pointDouble(pointExt);
                        }
                    }
                    i4++;
                }
                PointExt[] pointExtArr2 = new PointExt[16];
                pointExtArr2[0] = pointExt2;
                int i6 = 1;
                for (int i7 = 0; i7 < 4; i7++) {
                    int i8 = 1 << i7;
                    int i9 = 0;
                    while (i9 < i8) {
                        PointExt pointCopy = pointCopy(pointExtArr2[i6 - i8]);
                        pointExtArr2[i6] = pointCopy;
                        pointAddVar(false, pointExtArr[i7], pointCopy);
                        i9++;
                        i6++;
                    }
                }
                int[] createTable = X448Field.createTable(16);
                int[] create = X448Field.create();
                X448Field.copy(pointExtArr2[0].f1511z, 0, create, 0);
                X448Field.copy(create, 0, createTable, 0);
                int i10 = 0;
                while (true) {
                    i10++;
                    if (i10 >= 16) {
                        break;
                    }
                    X448Field.mul(create, pointExtArr2[i10].f1511z, create);
                    X448Field.copy(create, 0, createTable, i10 * 16);
                }
                X448Field.invVar(create, create);
                int i11 = i10 - 1;
                int[] create2 = X448Field.create();
                while (i11 > 0) {
                    int i12 = i11 - 1;
                    X448Field.copy(createTable, i12 * 16, create2, 0);
                    X448Field.mul(create2, create, create2);
                    X448Field.copy(create2, 0, createTable, i11 * 16);
                    X448Field.mul(create, pointExtArr2[i11].f1511z, create);
                    i11 = i12;
                }
                X448Field.copy(create, 0, createTable, 0);
                for (int i13 = 0; i13 < 16; i13++) {
                    PointExt pointExt3 = pointExtArr2[i13];
                    X448Field.copy(createTable, i13 * 16, pointExt3.f1511z, 0);
                    int[] iArr = pointExt3.f1509x;
                    X448Field.mul(iArr, pointExt3.f1511z, iArr);
                    int[] iArr2 = pointExt3.f1510y;
                    X448Field.mul(iArr2, pointExt3.f1511z, iArr2);
                    X448Field.copy(pointExt3.f1509x, 0, precompBase, i2);
                    int i14 = i2 + 16;
                    X448Field.copy(pointExt3.f1510y, 0, precompBase, i14);
                    i2 = i14 + 16;
                }
            }
        }
    }

    private static void pruneScalar(byte[] bArr, int i2, byte[] bArr2) {
        System.arraycopy(bArr, i2, bArr2, 0, 56);
        bArr2[0] = (byte) (bArr2[0] & 252);
        bArr2[55] = (byte) (bArr2[55] | DerValue.TAG_CONTEXT);
        bArr2[56] = 0;
    }

    private static byte[] reduceScalar(byte[] bArr) {
        long decode32 = decode32(bArr, 84) & 4294967295L;
        long decode322 = decode32(bArr, 91) & 4294967295L;
        long decode323 = decode32(bArr, 98) & 4294967295L;
        long decode324 = decode32(bArr, CipherSuite.TLS_DH_RSA_WITH_AES_256_CBC_SHA256) & 4294967295L;
        long decode16 = decode16(bArr, 112) & 4294967295L;
        long decode24 = ((decode24(bArr, CipherSuite.TLS_DH_anon_WITH_AES_256_CBC_SHA256) << 4) & 4294967295L) + (decode324 >>> 28);
        long j2 = decode324 & M28L;
        long decode242 = (decode24 * 43969588) + ((decode24(bArr, 53) << 4) & 4294967295L);
        long decode325 = (decode24 * 30366549) + (decode16 * 43969588) + (decode32(bArr, 56) & 4294967295L);
        long decode243 = (decode24 * 163752818) + (decode16 * 30366549) + ((decode24(bArr, 60) << 4) & 4294967295L);
        long decode326 = (decode24 * 258169998) + (decode16 * 163752818) + (decode32(bArr, 63) & 4294967295L);
        long decode244 = (decode24 * 96434764) + (decode16 * 258169998) + ((decode24(bArr, 67) << 4) & 4294967295L);
        long decode327 = (decode24 * 227822194) + (decode16 * 96434764) + (decode32(bArr, 70) & 4294967295L);
        long decode245 = (decode24 * 149865618) + (decode16 * 227822194) + ((decode24(bArr, 74) << 4) & 4294967295L);
        long decode328 = (j2 * 43969588) + (decode32(bArr, 49) & 4294967295L);
        long j3 = (j2 * 30366549) + decode242;
        long j4 = (j2 * 163752818) + decode325;
        long j5 = (j2 * 258169998) + decode243;
        long j6 = (j2 * 96434764) + decode326;
        long j7 = (j2 * 227822194) + decode244;
        long j8 = (j2 * 149865618) + decode327;
        long j9 = (j2 * 550336261) + decode245;
        long decode246 = ((decode24(bArr, 102) << 4) & 4294967295L) + (decode323 >>> 28);
        long j10 = decode323 & M28L;
        long decode247 = (decode246 * 43969588) + ((decode24(bArr, 46) << 4) & 4294967295L);
        long j11 = (decode246 * 30366549) + decode328;
        long j12 = (decode246 * 163752818) + j3;
        long j13 = (decode246 * 258169998) + j4;
        long j14 = (decode246 * 96434764) + j5;
        long j15 = (decode246 * 227822194) + j6;
        long j16 = (decode246 * 149865618) + j7;
        long j17 = (decode246 * 550336261) + j8;
        long decode329 = (j10 * 43969588) + (decode32(bArr, 42) & 4294967295L);
        long j18 = (j10 * 30366549) + decode247;
        long j19 = (j10 * 163752818) + j11;
        long j20 = (j10 * 258169998) + j12;
        long j21 = (j10 * 96434764) + j13;
        long j22 = (j10 * 227822194) + j14;
        long j23 = (j10 * 149865618) + j15;
        long j24 = (j10 * 550336261) + j16;
        long decode248 = ((decode24(bArr, 95) << 4) & 4294967295L) + (decode322 >>> 28);
        long j25 = decode322 & M28L;
        long j26 = (decode248 * 30366549) + decode329;
        long j27 = (decode248 * 163752818) + j18;
        long j28 = (decode248 * 258169998) + j19;
        long j29 = (decode248 * 149865618) + j22;
        long decode3210 = (j25 * 43969588) + (decode32(bArr, 35) & 4294967295L);
        long decode249 = (j25 * 30366549) + (decode248 * 43969588) + ((decode24(bArr, 39) << 4) & 4294967295L);
        long j30 = (j25 * 163752818) + j26;
        long j31 = (j25 * 258169998) + j27;
        long j32 = (j25 * 96434764) + j28;
        long j33 = (j25 * 227822194) + (decode248 * 96434764) + j20;
        long j34 = (j25 * 149865618) + (decode248 * 227822194) + j21;
        long j35 = (j25 * 550336261) + j29;
        long decode2410 = ((decode24(bArr, 88) << 4) & 4294967295L) + (decode32 >>> 28);
        long j36 = decode32 & M28L;
        long j37 = (decode2410 * 30366549) + decode3210;
        long j38 = (decode2410 * 163752818) + decode249;
        long j39 = (decode2410 * 258169998) + j30;
        long j40 = (decode2410 * 96434764) + j31;
        long j41 = (decode2410 * 227822194) + j32;
        long j42 = (decode2410 * 149865618) + j33;
        long j43 = j9 + (j17 >>> 28);
        long j44 = j17 & M28L;
        long decode3211 = (decode24 * 550336261) + (decode16 * 149865618) + (decode32(bArr, 77) & 4294967295L) + (j43 >>> 28);
        long j45 = j43 & M28L;
        long decode2411 = (decode16 * 550336261) + ((decode24(bArr, 81) << 4) & 4294967295L) + (decode3211 >>> 28);
        long j46 = decode3211 & M28L;
        long j47 = j36 + (decode2411 >>> 28);
        long j48 = decode2411 & M28L;
        long j49 = (j47 * 163752818) + j37;
        long j50 = (j47 * 258169998) + j38;
        long j51 = (j47 * 96434764) + j39;
        long j52 = (j47 * 227822194) + j40;
        long j53 = (j47 * 149865618) + j41;
        long decode2412 = (j48 * 43969588) + ((decode24(bArr, 25) << 4) & 4294967295L);
        long decode3212 = (j48 * 30366549) + (j47 * 43969588) + (decode32(bArr, 28) & 4294967295L);
        long decode2413 = (j48 * 163752818) + (j47 * 30366549) + (decode2410 * 43969588) + ((decode24(bArr, 32) << 4) & 4294967295L);
        long j54 = (j48 * 258169998) + j49;
        long j55 = (j48 * 96434764) + j50;
        long j56 = (j48 * 227822194) + j51;
        long j57 = (j48 * 149865618) + j52;
        long j58 = (j48 * 550336261) + j53;
        long decode3213 = (j46 * 43969588) + (decode32(bArr, 21) & 4294967295L);
        long j59 = (j46 * 30366549) + decode2412;
        long j60 = (j46 * 163752818) + decode3212;
        long j61 = (j46 * 258169998) + decode2413;
        long j62 = (j46 * 96434764) + j54;
        long j63 = (j46 * 227822194) + j55;
        long j64 = (j46 * 149865618) + j56;
        long j65 = (j46 * 550336261) + j57;
        long j66 = (decode248 * 550336261) + j23 + (j35 >>> 28);
        long j67 = j35 & M28L;
        long j68 = j24 + (j66 >>> 28);
        long j69 = j66 & M28L;
        long j70 = j44 + (j68 >>> 28);
        long j71 = j68 & M28L;
        long j72 = j45 + (j70 >>> 28);
        long j73 = j70 & M28L;
        long decode2414 = (j72 * 43969588) + ((decode24(bArr, 18) << 4) & 4294967295L);
        long j74 = (j72 * 30366549) + decode3213;
        long j75 = (j72 * 163752818) + j59;
        long j76 = (j72 * 258169998) + j60;
        long j77 = (j72 * 96434764) + j61;
        long j78 = (j72 * 227822194) + j62;
        long j79 = (j72 * 149865618) + j63;
        long j80 = (j72 * 550336261) + j64;
        long j81 = (j73 * 163752818) + j74;
        long j82 = (j73 * 258169998) + j75;
        long j83 = (j73 * 96434764) + j76;
        long j84 = (j73 * 227822194) + j77;
        long j85 = (j73 * 149865618) + j78;
        long decode2415 = (j71 * 43969588) + ((decode24(bArr, 11) << 4) & 4294967295L);
        long decode3214 = (j71 * 30366549) + (j73 * 43969588) + (decode32(bArr, 14) & 4294967295L);
        long j86 = (j71 * 163752818) + (j73 * 30366549) + decode2414;
        long j87 = (j71 * 258169998) + j81;
        long j88 = (j71 * 96434764) + j82;
        long j89 = (j71 * 227822194) + j83;
        long j90 = (j71 * 149865618) + j84;
        long j91 = (j71 * 550336261) + j85;
        long j92 = (j47 * 550336261) + j42 + (j58 >>> 28);
        long j93 = j58 & M28L;
        long j94 = (decode2410 * 550336261) + j34 + (j92 >>> 28);
        long j95 = j92 & M28L;
        long j96 = j67 + (j94 >>> 28);
        long j97 = j94 & M28L;
        long j98 = j69 + (j96 >>> 28);
        long j99 = j96 & M28L;
        long j100 = (j98 * 30366549) + decode2415;
        long j101 = (j98 * 163752818) + decode3214;
        long j102 = (j98 * 258169998) + j86;
        long j103 = (j98 * 96434764) + j87;
        long j104 = j95 & M26L;
        long j105 = (j97 * 4) + (j95 >>> 26) + 1;
        long decode3215 = (78101261 * j105) + (decode32(bArr, 0) & 4294967295L);
        long decode2416 = (141809365 * j105) + (43969588 * j99) + ((decode24(bArr, 4) << 4) & 4294967295L) + (decode3215 >>> 28);
        long j106 = decode3215 & M28L;
        long decode3216 = (175155932 * j105) + (30366549 * j99) + (j98 * 43969588) + (decode32(bArr, 7) & 4294967295L) + (decode2416 >>> 28);
        long j107 = decode2416 & M28L;
        long j108 = (64542499 * j105) + (163752818 * j99) + j100 + (decode3216 >>> 28);
        long j109 = decode3216 & M28L;
        long j110 = (158326419 * j105) + (258169998 * j99) + j101 + (j108 >>> 28);
        long j111 = j108 & M28L;
        long j112 = (191173276 * j105) + (96434764 * j99) + j102 + (j110 >>> 28);
        long j113 = j110 & M28L;
        long j114 = (104575268 * j105) + (227822194 * j99) + j103 + (j112 >>> 28);
        long j115 = j112 & M28L;
        long j116 = (j105 * 137584065) + (149865618 * j99) + (j98 * 227822194) + j88 + (j114 >>> 28);
        long j117 = j114 & M28L;
        long j118 = (j99 * 550336261) + (j98 * 149865618) + j89 + (j116 >>> 28);
        long j119 = j116 & M28L;
        long j120 = (j98 * 550336261) + j90 + (j118 >>> 28);
        long j121 = j118 & M28L;
        long j122 = j91 + (j120 >>> 28);
        long j123 = j120 & M28L;
        long j124 = (j73 * 550336261) + j79 + (j122 >>> 28);
        long j125 = j122 & M28L;
        long j126 = j80 + (j124 >>> 28);
        long j127 = j124 & M28L;
        long j128 = j65 + (j126 >>> 28);
        long j129 = j126 & M28L;
        long j130 = j93 + (j128 >>> 28);
        long j131 = j128 & M28L;
        long j132 = j104 + (j130 >>> 28);
        long j133 = j130 & M28L;
        long j134 = j132 & M26L;
        long j135 = (j132 >>> 26) - 1;
        long j136 = j106 - (j135 & 78101261);
        long j137 = (j107 - (j135 & 141809365)) + (j136 >> 28);
        long j138 = j136 & M28L;
        long j139 = (j109 - (j135 & 175155932)) + (j137 >> 28);
        long j140 = j137 & M28L;
        long j141 = (j111 - (j135 & 64542499)) + (j139 >> 28);
        long j142 = j139 & M28L;
        long j143 = (j113 - (j135 & 158326419)) + (j141 >> 28);
        long j144 = j141 & M28L;
        long j145 = (j115 - (j135 & 191173276)) + (j143 >> 28);
        long j146 = j143 & M28L;
        long j147 = (j117 - (j135 & 104575268)) + (j145 >> 28);
        long j148 = j145 & M28L;
        long j149 = (j119 - (j135 & 137584065)) + (j147 >> 28);
        long j150 = j147 & M28L;
        long j151 = j121 + (j149 >> 28);
        long j152 = j149 & M28L;
        long j153 = j123 + (j151 >> 28);
        long j154 = j151 & M28L;
        long j155 = j125 + (j153 >> 28);
        long j156 = j153 & M28L;
        long j157 = j127 + (j155 >> 28);
        long j158 = j155 & M28L;
        long j159 = j129 + (j157 >> 28);
        long j160 = j157 & M28L;
        long j161 = j131 + (j159 >> 28);
        long j162 = j159 & M28L;
        long j163 = j133 + (j161 >> 28);
        long j164 = j161 & M28L;
        long j165 = j134 + (j163 >> 28);
        long j166 = j163 & M28L;
        byte[] bArr2 = new byte[57];
        encode56(j138 | (j140 << 28), bArr2, 0);
        encode56((j144 << 28) | j142, bArr2, 7);
        encode56(j146 | (j148 << 28), bArr2, 14);
        encode56(j150 | (j152 << 28), bArr2, 21);
        encode56(j154 | (j156 << 28), bArr2, 28);
        encode56(j158 | (j160 << 28), bArr2, 35);
        encode56(j162 | (j164 << 28), bArr2, 42);
        encode56(j166 | (j165 << 28), bArr2, 49);
        return bArr2;
    }

    private static void scalarMult(byte[] bArr, PointExt pointExt, PointExt pointExt2) {
        int[] iArr = new int[14];
        decodeScalar(bArr, 0, iArr);
        Nat.shiftDownBits(14, iArr, 2, 0);
        Nat.cadd(14, (~iArr[0]) & 1, iArr, f1507L, iArr);
        Nat.shiftDownBit(14, iArr, 1);
        int[] pointPrecompute = pointPrecompute(pointExt, 8);
        PointExt pointExt3 = new PointExt();
        pointLookup(iArr, 111, pointPrecompute, pointExt2);
        for (int i2 = 110; i2 >= 0; i2--) {
            for (int i3 = 0; i3 < 4; i3++) {
                pointDouble(pointExt2);
            }
            pointLookup(iArr, i2, pointPrecompute, pointExt3);
            pointAdd(pointExt3, pointExt2);
        }
        for (int i4 = 0; i4 < 2; i4++) {
            pointDouble(pointExt2);
        }
    }

    private static void scalarMultBase(byte[] bArr, PointExt pointExt) {
        precompute();
        int[] iArr = new int[15];
        decodeScalar(bArr, 0, iArr);
        iArr[14] = Nat.cadd(14, (~iArr[0]) & 1, iArr, f1507L, iArr) + 4;
        Nat.shiftDownBit(15, iArr, 0);
        PointPrecomp pointPrecomp = new PointPrecomp();
        pointSetNeutral(pointExt);
        int i2 = 17;
        while (true) {
            int i3 = i2;
            for (int i4 = 0; i4 < 5; i4++) {
                int i5 = 0;
                for (int i6 = 0; i6 < 5; i6++) {
                    i5 = (i5 & (~(1 << i6))) ^ ((iArr[i3 >>> 5] >>> (i3 & 31)) << i6);
                    i3 += 18;
                }
                int i7 = (i5 >>> 4) & 1;
                pointLookup(i4, ((-i7) ^ i5) & 15, pointPrecomp);
                X448Field.cnegate(i7, pointPrecomp.f1512x);
                pointAddPrecomp(pointPrecomp, pointExt);
            }
            i2--;
            if (i2 < 0) {
                return;
            } else {
                pointDouble(pointExt);
            }
        }
    }

    private static void scalarMultBaseEncoded(byte[] bArr, byte[] bArr2, int i2) {
        PointExt pointExt = new PointExt();
        scalarMultBase(bArr, pointExt);
        if (encodePoint(pointExt, bArr2, i2) == 0) {
            throw new IllegalStateException();
        }
    }

    public static void scalarMultBaseXY(X448.Friend friend, byte[] bArr, int i2, int[] iArr, int[] iArr2) {
        if (friend == null) {
            throw new NullPointerException("This method is only for use by X448");
        }
        byte[] bArr2 = new byte[57];
        pruneScalar(bArr, i2, bArr2);
        PointExt pointExt = new PointExt();
        scalarMultBase(bArr2, pointExt);
        if (checkPoint(pointExt.f1509x, pointExt.f1510y, pointExt.f1511z) == 0) {
            throw new IllegalStateException();
        }
        X448Field.copy(pointExt.f1509x, 0, iArr, 0);
        X448Field.copy(pointExt.f1510y, 0, iArr2, 0);
    }

    private static void scalarMultOrderVar(PointExt pointExt, PointExt pointExt2) {
        byte[] wnafVar = getWnafVar(f1507L, 5);
        PointExt[] pointPrecomputeVar = pointPrecomputeVar(pointExt, 8);
        pointSetNeutral(pointExt2);
        int i2 = 446;
        while (true) {
            byte b = wnafVar[i2];
            if (b != 0) {
                int i3 = b >> 31;
                pointAddVar(i3 != 0, pointPrecomputeVar[(b ^ i3) >>> 1], pointExt2);
            }
            i2--;
            if (i2 < 0) {
                return;
            } else {
                pointDouble(pointExt2);
            }
        }
    }

    private static void scalarMultStrausVar(int[] iArr, int[] iArr2, PointExt pointExt, PointExt pointExt2) {
        precompute();
        byte[] wnafVar = getWnafVar(iArr, 7);
        byte[] wnafVar2 = getWnafVar(iArr2, 5);
        PointExt[] pointPrecomputeVar = pointPrecomputeVar(pointExt, 8);
        pointSetNeutral(pointExt2);
        int i2 = 446;
        while (true) {
            byte b = wnafVar[i2];
            if (b != 0) {
                int i3 = b >> 31;
                pointAddVar(i3 != 0, precompBaseTable[(b ^ i3) >>> 1], pointExt2);
            }
            byte b2 = wnafVar2[i2];
            if (b2 != 0) {
                int i4 = b2 >> 31;
                pointAddVar(i4 != 0, pointPrecomputeVar[(b2 ^ i4) >>> 1], pointExt2);
            }
            i2--;
            if (i2 < 0) {
                return;
            } else {
                pointDouble(pointExt2);
            }
        }
    }

    public static void sign(byte[] bArr, int i2, byte[] bArr2, int i3, byte[] bArr3, byte[] bArr4, int i4, int i5, byte[] bArr5, int i6) {
        implSign(bArr, i2, bArr2, i3, bArr3, (byte) 0, bArr4, i4, i5, bArr5, i6);
    }

    public static void signPrehash(byte[] bArr, int i2, byte[] bArr2, int i3, byte[] bArr3, Xof xof, byte[] bArr4, int i4) {
        byte[] bArr5 = new byte[64];
        if (64 != xof.doFinal(bArr5, 0, 64)) {
            throw new IllegalArgumentException("ph");
        }
        implSign(bArr, i2, bArr2, i3, bArr3, (byte) 1, bArr5, 0, 64, bArr4, i4);
    }

    public static boolean validatePublicKeyFull(byte[] bArr, int i2) {
        PointExt pointExt = new PointExt();
        if (!decodePointVar(bArr, i2, false, pointExt)) {
            return false;
        }
        X448Field.normalize(pointExt.f1509x);
        X448Field.normalize(pointExt.f1510y);
        X448Field.normalize(pointExt.f1511z);
        if (isNeutralElementVar(pointExt.f1509x, pointExt.f1510y, pointExt.f1511z)) {
            return false;
        }
        PointExt pointExt2 = new PointExt();
        scalarMultOrderVar(pointExt, pointExt2);
        X448Field.normalize(pointExt2.f1509x);
        X448Field.normalize(pointExt2.f1510y);
        X448Field.normalize(pointExt2.f1511z);
        return isNeutralElementVar(pointExt2.f1509x, pointExt2.f1510y, pointExt2.f1511z);
    }

    public static boolean validatePublicKeyPartial(byte[] bArr, int i2) {
        return decodePointVar(bArr, i2, false, new PointExt());
    }

    public static boolean verify(byte[] bArr, int i2, byte[] bArr2, int i3, byte[] bArr3, byte[] bArr4, int i4, int i5) {
        return implVerify(bArr, i2, bArr2, i3, bArr3, (byte) 0, bArr4, i4, i5);
    }

    public static boolean verifyPrehash(byte[] bArr, int i2, byte[] bArr2, int i3, byte[] bArr3, Xof xof) {
        byte[] bArr4 = new byte[64];
        if (64 == xof.doFinal(bArr4, 0, 64)) {
            return implVerify(bArr, i2, bArr2, i3, bArr3, (byte) 1, bArr4, 0, 64);
        }
        throw new IllegalArgumentException("ph");
    }

    private static int checkPoint(int[] iArr, int[] iArr2, int[] iArr3) {
        int[] create = X448Field.create();
        int[] create2 = X448Field.create();
        int[] create3 = X448Field.create();
        int[] create4 = X448Field.create();
        X448Field.sqr(iArr, create2);
        X448Field.sqr(iArr2, create3);
        X448Field.sqr(iArr3, create4);
        X448Field.mul(create2, create3, create);
        X448Field.add(create2, create3, create2);
        X448Field.mul(create2, create4, create2);
        X448Field.sqr(create4, create4);
        X448Field.mul(create, 39081, create);
        X448Field.sub(create, create4, create);
        X448Field.add(create, create2, create);
        X448Field.normalize(create);
        return X448Field.isZero(create);
    }

    private static void decode32(byte[] bArr, int i2, int[] iArr, int i3, int i4) {
        for (int i5 = 0; i5 < i4; i5++) {
            iArr[i3 + i5] = decode32(bArr, (i5 * 4) + i2);
        }
    }

    private static void implSign(byte[] bArr, int i2, byte[] bArr2, byte b, byte[] bArr3, int i3, int i4, byte[] bArr4, int i5) {
        if (!checkContextVar(bArr2)) {
            throw new IllegalArgumentException("ctx");
        }
        Xof createXof = createXof();
        byte[] bArr5 = new byte[114];
        createXof.update(bArr, i2, 57);
        createXof.doFinal(bArr5, 0, 114);
        byte[] bArr6 = new byte[57];
        pruneScalar(bArr5, 0, bArr6);
        byte[] bArr7 = new byte[57];
        scalarMultBaseEncoded(bArr6, bArr7, 0);
        implSign(createXof, bArr5, bArr6, bArr7, 0, bArr2, b, bArr3, i3, i4, bArr4, i5);
    }

    private static void pointCopy(PointExt pointExt, PointExt pointExt2) {
        X448Field.copy(pointExt.f1509x, 0, pointExt2.f1509x, 0);
        X448Field.copy(pointExt.f1510y, 0, pointExt2.f1510y, 0);
        X448Field.copy(pointExt.f1511z, 0, pointExt2.f1511z, 0);
    }

    private static void pointLookup(int[] iArr, int i2, int[] iArr2, PointExt pointExt) {
        int window4 = getWindow4(iArr, i2);
        int i3 = (window4 >>> 3) ^ 1;
        int i4 = (window4 ^ (-i3)) & 7;
        int i5 = 0;
        for (int i6 = 0; i6 < 8; i6++) {
            int i7 = ((i6 ^ i4) - 1) >> 31;
            X448Field.cmov(i7, iArr2, i5, pointExt.f1509x, 0);
            int i8 = i5 + 16;
            X448Field.cmov(i7, iArr2, i8, pointExt.f1510y, 0);
            int i9 = i8 + 16;
            X448Field.cmov(i7, iArr2, i9, pointExt.f1511z, 0);
            i5 = i9 + 16;
        }
        X448Field.cnegate(i3, pointExt.f1509x);
    }

    public static void sign(byte[] bArr, int i2, byte[] bArr2, byte[] bArr3, int i3, int i4, byte[] bArr4, int i5) {
        implSign(bArr, i2, bArr2, (byte) 0, bArr3, i3, i4, bArr4, i5);
    }

    public static void signPrehash(byte[] bArr, int i2, byte[] bArr2, int i3, byte[] bArr3, byte[] bArr4, int i4, byte[] bArr5, int i5) {
        implSign(bArr, i2, bArr2, i3, bArr3, (byte) 1, bArr4, i4, 64, bArr5, i5);
    }

    public static boolean verifyPrehash(byte[] bArr, int i2, byte[] bArr2, int i3, byte[] bArr3, byte[] bArr4, int i4) {
        return implVerify(bArr, i2, bArr2, i3, bArr3, (byte) 1, bArr4, i4, 64);
    }

    private static void implSign(byte[] bArr, int i2, byte[] bArr2, int i3, byte[] bArr3, byte b, byte[] bArr4, int i4, int i5, byte[] bArr5, int i6) {
        if (!checkContextVar(bArr3)) {
            throw new IllegalArgumentException("ctx");
        }
        Xof createXof = createXof();
        byte[] bArr6 = new byte[114];
        createXof.update(bArr, i2, 57);
        createXof.doFinal(bArr6, 0, 114);
        byte[] bArr7 = new byte[57];
        pruneScalar(bArr6, 0, bArr7);
        implSign(createXof, bArr6, bArr7, bArr2, i3, bArr3, b, bArr4, i4, i5, bArr5, i6);
    }

    public static void signPrehash(byte[] bArr, int i2, byte[] bArr2, Xof xof, byte[] bArr3, int i3) {
        byte[] bArr4 = new byte[64];
        if (64 != xof.doFinal(bArr4, 0, 64)) {
            throw new IllegalArgumentException("ph");
        }
        implSign(bArr, i2, bArr2, (byte) 1, bArr4, 0, 64, bArr3, i3);
    }

    public static void signPrehash(byte[] bArr, int i2, byte[] bArr2, byte[] bArr3, int i3, byte[] bArr4, int i4) {
        implSign(bArr, i2, bArr2, (byte) 1, bArr3, i3, 64, bArr4, i4);
    }
}

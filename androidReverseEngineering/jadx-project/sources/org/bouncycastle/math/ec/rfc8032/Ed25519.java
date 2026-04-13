package org.bouncycastle.math.ec.rfc8032;

import android.sun.security.util.DerValue;
import java.security.SecureRandom;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.SHA512Digest;
import org.bouncycastle.math.ec.rfc7748.X25519;
import org.bouncycastle.math.ec.rfc7748.X25519Field;
import org.bouncycastle.math.raw.Interleave;
import org.bouncycastle.math.raw.Nat;
import org.bouncycastle.math.raw.Nat256;
import org.bouncycastle.util.Arrays;

/* loaded from: classes.dex */
public abstract class Ed25519 {
    private static final int COORD_INTS = 8;
    private static final int L0 = -50998291;
    private static final int L1 = 19280294;
    private static final int L2 = 127719000;
    private static final int L3 = -6428113;
    private static final int L4 = 5343;
    private static final long M08L = 255;
    private static final long M28L = 268435455;
    private static final long M32L = 4294967295L;
    private static final int POINT_BYTES = 32;
    private static final int PRECOMP_BLOCKS = 8;
    private static final int PRECOMP_MASK = 7;
    private static final int PRECOMP_POINTS = 8;
    private static final int PRECOMP_SPACING = 8;
    private static final int PRECOMP_TEETH = 4;
    public static final int PREHASH_SIZE = 64;
    public static final int PUBLIC_KEY_SIZE = 32;
    private static final int SCALAR_BYTES = 32;
    private static final int SCALAR_INTS = 8;
    public static final int SECRET_KEY_SIZE = 32;
    public static final int SIGNATURE_SIZE = 64;
    private static final int WNAF_WIDTH_BASE = 7;
    private static final byte[] DOM2_PREFIX = {83, 105, 103, 69, 100, 50, 53, 53, 49, 57, 32, 110, 111, 32, 69, 100, 50, 53, 53, 49, 57, 32, 99, 111, 108, 108, 105, 115, 105, 111, 110, 115};

    /* renamed from: P */
    private static final int[] f1495P = {-19, -1, -1, -1, -1, -1, -1, Integer.MAX_VALUE};

    /* renamed from: L */
    private static final int[] f1494L = {1559614445, 1477600026, -1560830762, 350157278, 0, 0, 0, 268435456};
    private static final int[] B_x = {52811034, 25909283, 8072341, 50637101, 13785486, 30858332, 20483199, 20966410, 43936626, 4379245};
    private static final int[] B_y = {40265304, 26843545, 6710886, 53687091, 13421772, 40265318, 26843545, 6710886, 53687091, 13421772};
    private static final int[] C_d = {56195235, 47411844, 25868126, 40503822, 57364, 58321048, 30416477, 31930572, 57760639, 10749657};
    private static final int[] C_d2 = {45281625, 27714825, 18181821, 13898781, 114729, 49533232, 60832955, 30306712, 48412415, 4722099};
    private static final int[] C_d4 = {23454386, 55429651, 2809210, 27797563, 229458, 31957600, 54557047, 27058993, 29715967, 9444199};
    private static final Object precompLock = new Object();
    private static PointExt[] precompBaseTable = null;
    private static int[] precompBase = null;

    public static final class Algorithm {
        public static final int Ed25519 = 0;
        public static final int Ed25519ctx = 1;
        public static final int Ed25519ph = 2;
    }

    /* renamed from: org.bouncycastle.math.ec.rfc8032.Ed25519$F */
    public static class C0738F extends X25519Field {
        private C0738F() {
        }
    }

    public static class PointAccum {

        /* renamed from: u */
        int[] f1496u;

        /* renamed from: v */
        int[] f1497v;

        /* renamed from: x */
        int[] f1498x;

        /* renamed from: y */
        int[] f1499y;

        /* renamed from: z */
        int[] f1500z;

        private PointAccum() {
            this.f1498x = X25519Field.create();
            this.f1499y = X25519Field.create();
            this.f1500z = X25519Field.create();
            this.f1496u = X25519Field.create();
            this.f1497v = X25519Field.create();
        }
    }

    public static class PointAffine {

        /* renamed from: x */
        int[] f1501x;

        /* renamed from: y */
        int[] f1502y;

        private PointAffine() {
            this.f1501x = X25519Field.create();
            this.f1502y = X25519Field.create();
        }
    }

    public static class PointExt {

        /* renamed from: t */
        int[] f1503t;

        /* renamed from: x */
        int[] f1504x;

        /* renamed from: y */
        int[] f1505y;

        /* renamed from: z */
        int[] f1506z;

        private PointExt() {
            this.f1504x = X25519Field.create();
            this.f1505y = X25519Field.create();
            this.f1506z = X25519Field.create();
            this.f1503t = X25519Field.create();
        }
    }

    public static class PointPrecomp {
        int[] xyd;
        int[] ymx_h;
        int[] ypx_h;

        private PointPrecomp() {
            this.ypx_h = X25519Field.create();
            this.ymx_h = X25519Field.create();
            this.xyd = X25519Field.create();
        }
    }

    private static byte[] calculateS(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        int[] iArr = new int[16];
        decodeScalar(bArr, 0, iArr);
        int[] iArr2 = new int[8];
        decodeScalar(bArr2, 0, iArr2);
        int[] iArr3 = new int[8];
        decodeScalar(bArr3, 0, iArr3);
        Nat256.mulAddTo(iArr2, iArr3, iArr);
        byte[] bArr4 = new byte[64];
        for (int i2 = 0; i2 < 16; i2++) {
            encode32(iArr[i2], bArr4, i2 * 4);
        }
        return reduceScalar(bArr4);
    }

    private static boolean checkContextVar(byte[] bArr, byte b) {
        return (bArr == null && b == 0) || (bArr != null && bArr.length < 256);
    }

    private static int checkPoint(int[] iArr, int[] iArr2) {
        int[] create = X25519Field.create();
        int[] create2 = X25519Field.create();
        int[] create3 = X25519Field.create();
        X25519Field.sqr(iArr, create2);
        X25519Field.sqr(iArr2, create3);
        X25519Field.mul(create2, create3, create);
        X25519Field.sub(create3, create2, create3);
        X25519Field.mul(create, C_d, create);
        X25519Field.addOne(create);
        X25519Field.sub(create, create3, create);
        X25519Field.normalize(create);
        return X25519Field.isZero(create);
    }

    private static boolean checkPointVar(byte[] bArr) {
        int[] iArr = new int[8];
        decode32(bArr, 0, iArr, 0, 8);
        iArr[7] = iArr[7] & Integer.MAX_VALUE;
        return !Nat256.gte(iArr, f1495P);
    }

    private static boolean checkScalarVar(byte[] bArr, int[] iArr) {
        decodeScalar(bArr, 0, iArr);
        return !Nat256.gte(iArr, f1494L);
    }

    private static byte[] copy(byte[] bArr, int i2, int i3) {
        byte[] bArr2 = new byte[i3];
        System.arraycopy(bArr, i2, bArr2, 0, i3);
        return bArr2;
    }

    private static Digest createDigest() {
        return new SHA512Digest();
    }

    public static Digest createPrehash() {
        return createDigest();
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

    private static boolean decodePointVar(byte[] bArr, int i2, boolean z2, PointAffine pointAffine) {
        byte[] copy = copy(bArr, i2, 32);
        if (!checkPointVar(copy)) {
            return false;
        }
        byte b = copy[31];
        int i3 = (b & DerValue.TAG_CONTEXT) >>> 7;
        copy[31] = (byte) (b & Byte.MAX_VALUE);
        X25519Field.decode(copy, 0, pointAffine.f1502y);
        int[] create = X25519Field.create();
        int[] create2 = X25519Field.create();
        X25519Field.sqr(pointAffine.f1502y, create);
        X25519Field.mul(C_d, create, create2);
        X25519Field.subOne(create);
        X25519Field.addOne(create2);
        if (!X25519Field.sqrtRatioVar(create, create2, pointAffine.f1501x)) {
            return false;
        }
        X25519Field.normalize(pointAffine.f1501x);
        if (i3 == 1 && X25519Field.isZeroVar(pointAffine.f1501x)) {
            return false;
        }
        int[] iArr = pointAffine.f1501x;
        if (z2 ^ (i3 != (iArr[0] & 1))) {
            X25519Field.negate(iArr, iArr);
        }
        return true;
    }

    private static void decodeScalar(byte[] bArr, int i2, int[] iArr) {
        decode32(bArr, i2, iArr, 0, 8);
    }

    private static void dom2(Digest digest, byte b, byte[] bArr) {
        if (bArr != null) {
            byte[] bArr2 = DOM2_PREFIX;
            int length = bArr2.length;
            int i2 = length + 2;
            int length2 = bArr.length + i2;
            byte[] bArr3 = new byte[length2];
            System.arraycopy(bArr2, 0, bArr3, 0, length);
            bArr3[length] = b;
            bArr3[length + 1] = (byte) bArr.length;
            System.arraycopy(bArr, 0, bArr3, i2, bArr.length);
            digest.update(bArr3, 0, length2);
        }
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

    private static int encodePoint(PointAccum pointAccum, byte[] bArr, int i2) {
        int[] create = X25519Field.create();
        int[] create2 = X25519Field.create();
        X25519Field.inv(pointAccum.f1500z, create2);
        X25519Field.mul(pointAccum.f1498x, create2, create);
        X25519Field.mul(pointAccum.f1499y, create2, create2);
        X25519Field.normalize(create);
        X25519Field.normalize(create2);
        int checkPoint = checkPoint(create, create2);
        X25519Field.encode(create2, bArr, i2);
        int i3 = (i2 + 32) - 1;
        bArr[i3] = (byte) (((create[0] & 1) << 7) | bArr[i3]);
        return checkPoint;
    }

    public static void generatePrivateKey(SecureRandom secureRandom, byte[] bArr) {
        secureRandom.nextBytes(bArr);
    }

    public static void generatePublicKey(byte[] bArr, int i2, byte[] bArr2, int i3) {
        Digest createDigest = createDigest();
        byte[] bArr3 = new byte[createDigest.getDigestSize()];
        createDigest.update(bArr, i2, 32);
        createDigest.doFinal(bArr3, 0);
        byte[] bArr4 = new byte[32];
        pruneScalar(bArr3, 0, bArr4);
        scalarMultBaseEncoded(bArr4, bArr2, i3);
    }

    private static int getWindow4(int[] iArr, int i2) {
        return (iArr[i2 >>> 3] >>> ((i2 & 7) << 2)) & 15;
    }

    private static byte[] getWnafVar(int[] iArr, int i2) {
        int[] iArr2 = new int[16];
        int i3 = 0;
        int i4 = 8;
        int i5 = 16;
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
        byte[] bArr = new byte[253];
        int i9 = 32 - i2;
        int i10 = 0;
        int i11 = 0;
        while (i3 < 16) {
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

    private static void implSign(Digest digest, byte[] bArr, byte[] bArr2, byte[] bArr3, int i2, byte[] bArr4, byte b, byte[] bArr5, int i3, int i4, byte[] bArr6, int i5) {
        dom2(digest, b, bArr4);
        digest.update(bArr, 32, 32);
        digest.update(bArr5, i3, i4);
        digest.doFinal(bArr, 0);
        byte[] reduceScalar = reduceScalar(bArr);
        byte[] bArr7 = new byte[32];
        scalarMultBaseEncoded(reduceScalar, bArr7, 0);
        dom2(digest, b, bArr4);
        digest.update(bArr7, 0, 32);
        digest.update(bArr3, i2, 32);
        digest.update(bArr5, i3, i4);
        digest.doFinal(bArr, 0);
        byte[] calculateS = calculateS(reduceScalar, reduceScalar(bArr), bArr2);
        System.arraycopy(bArr7, 0, bArr6, i5, 32);
        System.arraycopy(calculateS, 0, bArr6, i5 + 32, 32);
    }

    private static boolean implVerify(byte[] bArr, int i2, byte[] bArr2, int i3, byte[] bArr3, byte b, byte[] bArr4, int i4, int i5) {
        if (!checkContextVar(bArr3, b)) {
            throw new IllegalArgumentException("ctx");
        }
        byte[] copy = copy(bArr, i2, 32);
        byte[] copy2 = copy(bArr, i2 + 32, 32);
        if (!checkPointVar(copy)) {
            return false;
        }
        int[] iArr = new int[8];
        if (!checkScalarVar(copy2, iArr)) {
            return false;
        }
        PointAffine pointAffine = new PointAffine();
        if (!decodePointVar(bArr2, i3, true, pointAffine)) {
            return false;
        }
        Digest createDigest = createDigest();
        byte[] bArr5 = new byte[createDigest.getDigestSize()];
        dom2(createDigest, b, bArr3);
        createDigest.update(copy, 0, 32);
        createDigest.update(bArr2, i3, 32);
        createDigest.update(bArr4, i4, i5);
        createDigest.doFinal(bArr5, 0);
        int[] iArr2 = new int[8];
        decodeScalar(reduceScalar(bArr5), 0, iArr2);
        PointAccum pointAccum = new PointAccum();
        scalarMultStrausVar(iArr, iArr2, pointAffine, pointAccum);
        byte[] bArr6 = new byte[32];
        return encodePoint(pointAccum, bArr6, 0) != 0 && Arrays.areEqual(bArr6, copy);
    }

    private static boolean isNeutralElementVar(int[] iArr, int[] iArr2) {
        return X25519Field.isZeroVar(iArr) && X25519Field.isOneVar(iArr2);
    }

    private static void pointAdd(PointExt pointExt, PointAccum pointAccum) {
        int[] create = X25519Field.create();
        int[] create2 = X25519Field.create();
        int[] create3 = X25519Field.create();
        int[] create4 = X25519Field.create();
        int[] iArr = pointAccum.f1496u;
        int[] create5 = X25519Field.create();
        int[] create6 = X25519Field.create();
        int[] iArr2 = pointAccum.f1497v;
        X25519Field.apm(pointAccum.f1499y, pointAccum.f1498x, create2, create);
        X25519Field.apm(pointExt.f1505y, pointExt.f1504x, create4, create3);
        X25519Field.mul(create, create3, create);
        X25519Field.mul(create2, create4, create2);
        X25519Field.mul(pointAccum.f1496u, pointAccum.f1497v, create3);
        X25519Field.mul(create3, pointExt.f1503t, create3);
        X25519Field.mul(create3, C_d2, create3);
        X25519Field.mul(pointAccum.f1500z, pointExt.f1506z, create4);
        X25519Field.add(create4, create4, create4);
        X25519Field.apm(create2, create, iArr2, iArr);
        X25519Field.apm(create4, create3, create6, create5);
        X25519Field.carry(create6);
        X25519Field.mul(iArr, create5, pointAccum.f1498x);
        X25519Field.mul(create6, iArr2, pointAccum.f1499y);
        X25519Field.mul(create5, create6, pointAccum.f1500z);
    }

    private static void pointAddPrecomp(PointPrecomp pointPrecomp, PointAccum pointAccum) {
        int[] create = X25519Field.create();
        int[] create2 = X25519Field.create();
        int[] create3 = X25519Field.create();
        int[] iArr = pointAccum.f1496u;
        int[] create4 = X25519Field.create();
        int[] create5 = X25519Field.create();
        int[] iArr2 = pointAccum.f1497v;
        X25519Field.apm(pointAccum.f1499y, pointAccum.f1498x, create2, create);
        X25519Field.mul(create, pointPrecomp.ymx_h, create);
        X25519Field.mul(create2, pointPrecomp.ypx_h, create2);
        X25519Field.mul(pointAccum.f1496u, pointAccum.f1497v, create3);
        X25519Field.mul(create3, pointPrecomp.xyd, create3);
        X25519Field.apm(create2, create, iArr2, iArr);
        X25519Field.apm(pointAccum.f1500z, create3, create5, create4);
        X25519Field.carry(create5);
        X25519Field.mul(iArr, create4, pointAccum.f1498x);
        X25519Field.mul(create5, iArr2, pointAccum.f1499y);
        X25519Field.mul(create4, create5, pointAccum.f1500z);
    }

    private static void pointAddVar(boolean z2, PointExt pointExt, PointAccum pointAccum) {
        int[] iArr;
        int[] iArr2;
        int[] iArr3;
        int[] iArr4;
        int[] create = X25519Field.create();
        int[] create2 = X25519Field.create();
        int[] create3 = X25519Field.create();
        int[] create4 = X25519Field.create();
        int[] iArr5 = pointAccum.f1496u;
        int[] create5 = X25519Field.create();
        int[] create6 = X25519Field.create();
        int[] iArr6 = pointAccum.f1497v;
        if (z2) {
            iArr2 = create3;
            iArr = create4;
            iArr4 = create5;
            iArr3 = create6;
        } else {
            iArr = create3;
            iArr2 = create4;
            iArr3 = create5;
            iArr4 = create6;
        }
        X25519Field.apm(pointAccum.f1499y, pointAccum.f1498x, create2, create);
        X25519Field.apm(pointExt.f1505y, pointExt.f1504x, iArr2, iArr);
        X25519Field.mul(create, create3, create);
        X25519Field.mul(create2, create4, create2);
        X25519Field.mul(pointAccum.f1496u, pointAccum.f1497v, create3);
        X25519Field.mul(create3, pointExt.f1503t, create3);
        X25519Field.mul(create3, C_d2, create3);
        X25519Field.mul(pointAccum.f1500z, pointExt.f1506z, create4);
        X25519Field.add(create4, create4, create4);
        X25519Field.apm(create2, create, iArr6, iArr5);
        X25519Field.apm(create4, create3, iArr4, iArr3);
        X25519Field.carry(iArr4);
        X25519Field.mul(iArr5, create5, pointAccum.f1498x);
        X25519Field.mul(create6, iArr6, pointAccum.f1499y);
        X25519Field.mul(create5, create6, pointAccum.f1500z);
    }

    private static PointExt pointCopy(PointAccum pointAccum) {
        PointExt pointExt = new PointExt();
        X25519Field.copy(pointAccum.f1498x, 0, pointExt.f1504x, 0);
        X25519Field.copy(pointAccum.f1499y, 0, pointExt.f1505y, 0);
        X25519Field.copy(pointAccum.f1500z, 0, pointExt.f1506z, 0);
        X25519Field.mul(pointAccum.f1496u, pointAccum.f1497v, pointExt.f1503t);
        return pointExt;
    }

    private static void pointDouble(PointAccum pointAccum) {
        int[] create = X25519Field.create();
        int[] create2 = X25519Field.create();
        int[] create3 = X25519Field.create();
        int[] iArr = pointAccum.f1496u;
        int[] create4 = X25519Field.create();
        int[] create5 = X25519Field.create();
        int[] iArr2 = pointAccum.f1497v;
        X25519Field.sqr(pointAccum.f1498x, create);
        X25519Field.sqr(pointAccum.f1499y, create2);
        X25519Field.sqr(pointAccum.f1500z, create3);
        X25519Field.add(create3, create3, create3);
        X25519Field.apm(create, create2, iArr2, create5);
        X25519Field.add(pointAccum.f1498x, pointAccum.f1499y, iArr);
        X25519Field.sqr(iArr, iArr);
        X25519Field.sub(iArr2, iArr, iArr);
        X25519Field.add(create3, create5, create4);
        X25519Field.carry(create4);
        X25519Field.mul(iArr, create4, pointAccum.f1498x);
        X25519Field.mul(create5, iArr2, pointAccum.f1499y);
        X25519Field.mul(create4, create5, pointAccum.f1500z);
    }

    private static void pointExtendXY(PointAccum pointAccum) {
        X25519Field.one(pointAccum.f1500z);
        X25519Field.copy(pointAccum.f1498x, 0, pointAccum.f1496u, 0);
        X25519Field.copy(pointAccum.f1499y, 0, pointAccum.f1497v, 0);
    }

    private static void pointLookup(int i2, int i3, PointPrecomp pointPrecomp) {
        int i4 = i2 * 8 * 3 * 10;
        for (int i5 = 0; i5 < 8; i5++) {
            int i6 = ((i5 ^ i3) - 1) >> 31;
            X25519Field.cmov(i6, precompBase, i4, pointPrecomp.ypx_h, 0);
            int i7 = i4 + 10;
            X25519Field.cmov(i6, precompBase, i7, pointPrecomp.ymx_h, 0);
            int i8 = i7 + 10;
            X25519Field.cmov(i6, precompBase, i8, pointPrecomp.xyd, 0);
            i4 = i8 + 10;
        }
    }

    private static int[] pointPrecompute(PointAffine pointAffine, int i2) {
        PointExt pointCopy = pointCopy(pointAffine);
        PointExt pointCopy2 = pointCopy(pointCopy);
        pointAdd(pointCopy, pointCopy2);
        int[] createTable = X25519Field.createTable(i2 * 4);
        int i3 = 0;
        int i4 = 0;
        while (true) {
            X25519Field.copy(pointCopy.f1504x, 0, createTable, i3);
            int i5 = i3 + 10;
            X25519Field.copy(pointCopy.f1505y, 0, createTable, i5);
            int i6 = i5 + 10;
            X25519Field.copy(pointCopy.f1506z, 0, createTable, i6);
            int i7 = i6 + 10;
            X25519Field.copy(pointCopy.f1503t, 0, createTable, i7);
            i3 = i7 + 10;
            i4++;
            if (i4 == i2) {
                return createTable;
            }
            pointAdd(pointCopy2, pointCopy);
        }
    }

    private static PointExt[] pointPrecomputeVar(PointExt pointExt, int i2) {
        PointExt pointExt2 = new PointExt();
        pointAddVar(false, pointExt, pointExt, pointExt2);
        PointExt[] pointExtArr = new PointExt[i2];
        pointExtArr[0] = pointCopy(pointExt);
        for (int i3 = 1; i3 < i2; i3++) {
            PointExt pointExt3 = pointExtArr[i3 - 1];
            PointExt pointExt4 = new PointExt();
            pointExtArr[i3] = pointExt4;
            pointAddVar(false, pointExt3, pointExt2, pointExt4);
        }
        return pointExtArr;
    }

    private static void pointSetNeutral(PointAccum pointAccum) {
        X25519Field.zero(pointAccum.f1498x);
        X25519Field.one(pointAccum.f1499y);
        X25519Field.one(pointAccum.f1500z);
        X25519Field.zero(pointAccum.f1496u);
        X25519Field.one(pointAccum.f1497v);
    }

    public static void precompute() {
        int i2;
        synchronized (precompLock) {
            if (precompBase != null) {
                return;
            }
            PointExt pointExt = new PointExt();
            int[] iArr = B_x;
            X25519Field.copy(iArr, 0, pointExt.f1504x, 0);
            int[] iArr2 = B_y;
            X25519Field.copy(iArr2, 0, pointExt.f1505y, 0);
            pointExtendXY(pointExt);
            precompBaseTable = pointPrecomputeVar(pointExt, 32);
            PointAccum pointAccum = new PointAccum();
            X25519Field.copy(iArr, 0, pointAccum.f1498x, 0);
            X25519Field.copy(iArr2, 0, pointAccum.f1499y, 0);
            pointExtendXY(pointAccum);
            precompBase = X25519Field.createTable(192);
            int i3 = 0;
            for (int i4 = 0; i4 < 8; i4++) {
                PointExt[] pointExtArr = new PointExt[4];
                PointExt pointExt2 = new PointExt();
                pointSetNeutral(pointExt2);
                int i5 = 0;
                while (true) {
                    i2 = 1;
                    if (i5 >= 4) {
                        break;
                    }
                    pointAddVar(true, pointExt2, pointCopy(pointAccum), pointExt2);
                    pointDouble(pointAccum);
                    pointExtArr[i5] = pointCopy(pointAccum);
                    if (i4 + i5 != 10) {
                        while (i2 < 8) {
                            pointDouble(pointAccum);
                            i2++;
                        }
                    }
                    i5++;
                }
                PointExt[] pointExtArr2 = new PointExt[8];
                pointExtArr2[0] = pointExt2;
                int i6 = 0;
                int i7 = 1;
                while (i6 < 3) {
                    int i8 = i2 << i6;
                    int i9 = 0;
                    while (i9 < i8) {
                        PointExt pointExt3 = pointExtArr2[i7 - i8];
                        PointExt pointExt4 = pointExtArr[i6];
                        PointExt pointExt5 = new PointExt();
                        pointExtArr2[i7] = pointExt5;
                        pointAddVar(false, pointExt3, pointExt4, pointExt5);
                        i9++;
                        i7++;
                    }
                    i6++;
                    i2 = 1;
                }
                int[] createTable = X25519Field.createTable(8);
                int[] create = X25519Field.create();
                X25519Field.copy(pointExtArr2[0].f1506z, 0, create, 0);
                X25519Field.copy(create, 0, createTable, 0);
                int i10 = 0;
                while (true) {
                    i10++;
                    if (i10 >= 8) {
                        break;
                    }
                    X25519Field.mul(create, pointExtArr2[i10].f1506z, create);
                    X25519Field.copy(create, 0, createTable, i10 * 10);
                }
                X25519Field.add(create, create, create);
                X25519Field.invVar(create, create);
                int i11 = i10 - 1;
                int[] create2 = X25519Field.create();
                while (i11 > 0) {
                    int i12 = i11 - 1;
                    X25519Field.copy(createTable, i12 * 10, create2, 0);
                    X25519Field.mul(create2, create, create2);
                    X25519Field.copy(create2, 0, createTable, i11 * 10);
                    X25519Field.mul(create, pointExtArr2[i11].f1506z, create);
                    i11 = i12;
                }
                X25519Field.copy(create, 0, createTable, 0);
                for (int i13 = 0; i13 < 8; i13++) {
                    PointExt pointExt6 = pointExtArr2[i13];
                    int[] create3 = X25519Field.create();
                    int[] create4 = X25519Field.create();
                    X25519Field.copy(createTable, i13 * 10, create4, 0);
                    X25519Field.mul(pointExt6.f1504x, create4, create3);
                    X25519Field.mul(pointExt6.f1505y, create4, create4);
                    PointPrecomp pointPrecomp = new PointPrecomp();
                    X25519Field.apm(create4, create3, pointPrecomp.ypx_h, pointPrecomp.ymx_h);
                    X25519Field.mul(create3, create4, pointPrecomp.xyd);
                    int[] iArr3 = pointPrecomp.xyd;
                    X25519Field.mul(iArr3, C_d4, iArr3);
                    X25519Field.normalize(pointPrecomp.ypx_h);
                    X25519Field.normalize(pointPrecomp.ymx_h);
                    X25519Field.copy(pointPrecomp.ypx_h, 0, precompBase, i3);
                    int i14 = i3 + 10;
                    X25519Field.copy(pointPrecomp.ymx_h, 0, precompBase, i14);
                    int i15 = i14 + 10;
                    X25519Field.copy(pointPrecomp.xyd, 0, precompBase, i15);
                    i3 = i15 + 10;
                }
            }
        }
    }

    private static void pruneScalar(byte[] bArr, int i2, byte[] bArr2) {
        System.arraycopy(bArr, i2, bArr2, 0, 32);
        bArr2[0] = (byte) (bArr2[0] & 248);
        byte b = (byte) (bArr2[31] & Byte.MAX_VALUE);
        bArr2[31] = b;
        bArr2[31] = (byte) (b | DerValue.TAG_APPLICATION);
    }

    private static byte[] reduceScalar(byte[] bArr) {
        long decode32 = decode32(bArr, 49) & 4294967295L;
        long decode322 = decode32(bArr, 56) & 4294967295L;
        long j2 = bArr[63] & M08L;
        long decode24 = ((decode24(bArr, 60) << 4) & 4294967295L) + (decode322 >> 28);
        long j3 = decode322 & M28L;
        long decode323 = (decode32(bArr, 28) & 4294967295L) - (decode24 * (-50998291));
        long decode242 = (((decode24(bArr, 32) << 4) & 4294967295L) - (j2 * (-50998291))) - (decode24 * 19280294);
        long decode324 = ((decode32(bArr, 42) & 4294967295L) - (j2 * (-6428113))) - (decode24 * 5343);
        long decode243 = ((((decode24(bArr, 39) << 4) & 4294967295L) - (j2 * 127719000)) - (decode24 * (-6428113))) - (j3 * 5343);
        long decode244 = ((decode24(bArr, 53) << 4) & 4294967295L) + (decode32 >> 28);
        long j4 = decode32 & M28L;
        long decode325 = ((((decode32(bArr, 35) & 4294967295L) - (j2 * 19280294)) - (decode24 * 127719000)) - (j3 * (-6428113))) - (decode244 * 5343);
        long decode245 = ((((decode24(bArr, 25) << 4) & 4294967295L) - (j3 * (-50998291))) - (decode244 * 19280294)) - (j4 * 127719000);
        long j5 = ((decode242 - (j3 * 127719000)) - (decode244 * (-6428113))) - (j4 * 5343);
        long decode246 = (((decode24(bArr, 46) << 4) & 4294967295L) - (j2 * 5343)) + (decode324 >> 28);
        long j6 = (decode324 & M28L) + (decode243 >> 28);
        long decode247 = ((decode24(bArr, 11) << 4) & 4294967295L) - (j6 * (-50998291));
        long decode326 = ((decode32(bArr, 14) & 4294967295L) - (decode246 * (-50998291))) - (j6 * 19280294);
        long decode248 = ((((decode24(bArr, 18) << 4) & 4294967295L) - (j4 * (-50998291))) - (decode246 * 19280294)) - (j6 * 127719000);
        long decode327 = ((((decode32(bArr, 21) & 4294967295L) - (decode244 * (-50998291))) - (j4 * 19280294)) - (decode246 * 127719000)) - (j6 * (-6428113));
        long j7 = (decode245 - (decode246 * (-6428113))) - (j6 * 5343);
        long j8 = (decode243 & M28L) + (decode325 >> 28);
        long j9 = decode325 & M28L;
        long decode328 = (decode32(bArr, 7) & 4294967295L) - (j8 * (-50998291));
        long j10 = decode247 - (j8 * 19280294);
        long j11 = decode326 - (j8 * 127719000);
        long j12 = decode248 - (j8 * (-6428113));
        long j13 = decode327 - (j8 * 5343);
        long j14 = j9 + (j5 >> 28);
        long j15 = j5 & M28L;
        long decode249 = ((decode24(bArr, 4) << 4) & 4294967295L) - (j14 * (-50998291));
        long j16 = decode328 - (j14 * 19280294);
        long j17 = j10 - (j14 * 127719000);
        long j18 = j11 - (j14 * (-6428113));
        long j19 = j12 - (j14 * 5343);
        long j20 = ((((decode323 - (j3 * 19280294)) - (decode244 * 127719000)) - (j4 * (-6428113))) - (decode246 * 5343)) + (j7 >> 28);
        long j21 = j7 & M28L;
        long j22 = j20 & M28L;
        long j23 = j22 >>> 27;
        long j24 = j15 + (j20 >> 28) + j23;
        long decode329 = (decode32(bArr, 0) & 4294967295L) - (j24 * (-50998291));
        long j25 = (decode249 - (j24 * 19280294)) + (decode329 >> 28);
        long j26 = decode329 & M28L;
        long j27 = (j16 - (j24 * 127719000)) + (j25 >> 28);
        long j28 = j25 & M28L;
        long j29 = (j17 - (j24 * (-6428113))) + (j27 >> 28);
        long j30 = j27 & M28L;
        long j31 = (j18 - (j24 * 5343)) + (j29 >> 28);
        long j32 = j29 & M28L;
        long j33 = j19 + (j31 >> 28);
        long j34 = j31 & M28L;
        long j35 = j13 + (j33 >> 28);
        long j36 = j33 & M28L;
        long j37 = j21 + (j35 >> 28);
        long j38 = j35 & M28L;
        long j39 = j22 + (j37 >> 28);
        long j40 = j37 & M28L;
        long j41 = j39 >> 28;
        long j42 = j39 & M28L;
        long j43 = j41 - j23;
        long j44 = j26 + (j43 & (-50998291));
        long j45 = j28 + (j43 & 19280294) + (j44 >> 28);
        long j46 = j44 & M28L;
        long j47 = j30 + (j43 & 127719000) + (j45 >> 28);
        long j48 = j45 & M28L;
        long j49 = j32 + (j43 & (-6428113)) + (j47 >> 28);
        long j50 = j47 & M28L;
        long j51 = j34 + (j43 & 5343) + (j49 >> 28);
        long j52 = j49 & M28L;
        long j53 = j36 + (j51 >> 28);
        long j54 = j51 & M28L;
        long j55 = j38 + (j53 >> 28);
        long j56 = j53 & M28L;
        long j57 = j40 + (j55 >> 28);
        long j58 = j55 & M28L;
        long j59 = j42 + (j57 >> 28);
        long j60 = j57 & M28L;
        byte[] bArr2 = new byte[32];
        encode56(j46 | (j48 << 28), bArr2, 0);
        encode56((j52 << 28) | j50, bArr2, 7);
        encode56(j54 | (j56 << 28), bArr2, 14);
        encode56(j58 | (j60 << 28), bArr2, 21);
        encode32((int) j59, bArr2, 28);
        return bArr2;
    }

    private static void scalarMult(byte[] bArr, PointAffine pointAffine, PointAccum pointAccum) {
        int[] iArr = new int[8];
        decodeScalar(bArr, 0, iArr);
        Nat.shiftDownBits(8, iArr, 3, 1);
        Nat.cadd(8, (~iArr[0]) & 1, iArr, f1494L, iArr);
        Nat.shiftDownBit(8, iArr, 0);
        int[] pointPrecompute = pointPrecompute(pointAffine, 8);
        PointExt pointExt = new PointExt();
        pointCopy(pointAffine, pointAccum);
        pointLookup(pointPrecompute, 7, pointExt);
        pointAdd(pointExt, pointAccum);
        int i2 = 62;
        while (true) {
            pointLookup(iArr, i2, pointPrecompute, pointExt);
            pointAdd(pointExt, pointAccum);
            pointDouble(pointAccum);
            pointDouble(pointAccum);
            pointDouble(pointAccum);
            i2--;
            if (i2 < 0) {
                return;
            } else {
                pointDouble(pointAccum);
            }
        }
    }

    private static void scalarMultBase(byte[] bArr, PointAccum pointAccum) {
        precompute();
        int[] iArr = new int[8];
        decodeScalar(bArr, 0, iArr);
        Nat.cadd(8, (~iArr[0]) & 1, iArr, f1494L, iArr);
        Nat.shiftDownBit(8, iArr, 1);
        for (int i2 = 0; i2 < 8; i2++) {
            iArr[i2] = Interleave.shuffle2(iArr[i2]);
        }
        PointPrecomp pointPrecomp = new PointPrecomp();
        pointSetNeutral(pointAccum);
        int i3 = 28;
        while (true) {
            for (int i4 = 0; i4 < 8; i4++) {
                int i5 = iArr[i4] >>> i3;
                int i6 = (i5 >>> 3) & 1;
                pointLookup(i4, (i5 ^ (-i6)) & 7, pointPrecomp);
                X25519Field.cswap(i6, pointPrecomp.ypx_h, pointPrecomp.ymx_h);
                X25519Field.cnegate(i6, pointPrecomp.xyd);
                pointAddPrecomp(pointPrecomp, pointAccum);
            }
            i3 -= 4;
            if (i3 < 0) {
                return;
            } else {
                pointDouble(pointAccum);
            }
        }
    }

    private static void scalarMultBaseEncoded(byte[] bArr, byte[] bArr2, int i2) {
        PointAccum pointAccum = new PointAccum();
        scalarMultBase(bArr, pointAccum);
        if (encodePoint(pointAccum, bArr2, i2) == 0) {
            throw new IllegalStateException();
        }
    }

    public static void scalarMultBaseYZ(X25519.Friend friend, byte[] bArr, int i2, int[] iArr, int[] iArr2) {
        if (friend == null) {
            throw new NullPointerException("This method is only for use by X25519");
        }
        byte[] bArr2 = new byte[32];
        pruneScalar(bArr, i2, bArr2);
        PointAccum pointAccum = new PointAccum();
        scalarMultBase(bArr2, pointAccum);
        if (checkPoint(pointAccum.f1498x, pointAccum.f1499y, pointAccum.f1500z) == 0) {
            throw new IllegalStateException();
        }
        X25519Field.copy(pointAccum.f1499y, 0, iArr, 0);
        X25519Field.copy(pointAccum.f1500z, 0, iArr2, 0);
    }

    private static void scalarMultOrderVar(PointAffine pointAffine, PointAccum pointAccum) {
        byte[] wnafVar = getWnafVar(f1494L, 5);
        PointExt[] pointPrecomputeVar = pointPrecomputeVar(pointCopy(pointAffine), 8);
        pointSetNeutral(pointAccum);
        int i2 = 252;
        while (true) {
            byte b = wnafVar[i2];
            if (b != 0) {
                int i3 = b >> 31;
                pointAddVar(i3 != 0, pointPrecomputeVar[(b ^ i3) >>> 1], pointAccum);
            }
            i2--;
            if (i2 < 0) {
                return;
            } else {
                pointDouble(pointAccum);
            }
        }
    }

    private static void scalarMultStrausVar(int[] iArr, int[] iArr2, PointAffine pointAffine, PointAccum pointAccum) {
        precompute();
        byte[] wnafVar = getWnafVar(iArr, 7);
        byte[] wnafVar2 = getWnafVar(iArr2, 5);
        PointExt[] pointPrecomputeVar = pointPrecomputeVar(pointCopy(pointAffine), 8);
        pointSetNeutral(pointAccum);
        int i2 = 252;
        while (true) {
            byte b = wnafVar[i2];
            if (b != 0) {
                int i3 = b >> 31;
                pointAddVar(i3 != 0, precompBaseTable[(b ^ i3) >>> 1], pointAccum);
            }
            byte b2 = wnafVar2[i2];
            if (b2 != 0) {
                int i4 = b2 >> 31;
                pointAddVar(i4 != 0, pointPrecomputeVar[(b2 ^ i4) >>> 1], pointAccum);
            }
            i2--;
            if (i2 < 0) {
                return;
            } else {
                pointDouble(pointAccum);
            }
        }
    }

    public static void sign(byte[] bArr, int i2, byte[] bArr2, int i3, int i4, byte[] bArr3, int i5) {
        implSign(bArr, i2, null, (byte) 0, bArr2, i3, i4, bArr3, i5);
    }

    public static void signPrehash(byte[] bArr, int i2, byte[] bArr2, int i3, byte[] bArr3, Digest digest, byte[] bArr4, int i4) {
        byte[] bArr5 = new byte[64];
        if (64 != digest.doFinal(bArr5, 0)) {
            throw new IllegalArgumentException("ph");
        }
        implSign(bArr, i2, bArr2, i3, bArr3, (byte) 1, bArr5, 0, 64, bArr4, i4);
    }

    public static boolean validatePublicKeyFull(byte[] bArr, int i2) {
        PointAffine pointAffine = new PointAffine();
        if (!decodePointVar(bArr, i2, false, pointAffine)) {
            return false;
        }
        X25519Field.normalize(pointAffine.f1501x);
        X25519Field.normalize(pointAffine.f1502y);
        if (isNeutralElementVar(pointAffine.f1501x, pointAffine.f1502y)) {
            return false;
        }
        PointAccum pointAccum = new PointAccum();
        scalarMultOrderVar(pointAffine, pointAccum);
        X25519Field.normalize(pointAccum.f1498x);
        X25519Field.normalize(pointAccum.f1499y);
        X25519Field.normalize(pointAccum.f1500z);
        return isNeutralElementVar(pointAccum.f1498x, pointAccum.f1499y, pointAccum.f1500z);
    }

    public static boolean validatePublicKeyPartial(byte[] bArr, int i2) {
        return decodePointVar(bArr, i2, false, new PointAffine());
    }

    public static boolean verify(byte[] bArr, int i2, byte[] bArr2, int i3, byte[] bArr3, int i4, int i5) {
        return implVerify(bArr, i2, bArr2, i3, null, (byte) 0, bArr3, i4, i5);
    }

    public static boolean verifyPrehash(byte[] bArr, int i2, byte[] bArr2, int i3, byte[] bArr3, Digest digest) {
        byte[] bArr4 = new byte[64];
        if (64 == digest.doFinal(bArr4, 0)) {
            return implVerify(bArr, i2, bArr2, i3, bArr3, (byte) 1, bArr4, 0, 64);
        }
        throw new IllegalArgumentException("ph");
    }

    private static int checkPoint(int[] iArr, int[] iArr2, int[] iArr3) {
        int[] create = X25519Field.create();
        int[] create2 = X25519Field.create();
        int[] create3 = X25519Field.create();
        int[] create4 = X25519Field.create();
        X25519Field.sqr(iArr, create2);
        X25519Field.sqr(iArr2, create3);
        X25519Field.sqr(iArr3, create4);
        X25519Field.mul(create2, create3, create);
        X25519Field.sub(create3, create2, create3);
        X25519Field.mul(create3, create4, create3);
        X25519Field.sqr(create4, create4);
        X25519Field.mul(create, C_d, create);
        X25519Field.add(create, create4, create);
        X25519Field.sub(create, create3, create);
        X25519Field.normalize(create);
        return X25519Field.isZero(create);
    }

    private static void decode32(byte[] bArr, int i2, int[] iArr, int i3, int i4) {
        for (int i5 = 0; i5 < i4; i5++) {
            iArr[i3 + i5] = decode32(bArr, (i5 * 4) + i2);
        }
    }

    private static void implSign(byte[] bArr, int i2, byte[] bArr2, byte b, byte[] bArr3, int i3, int i4, byte[] bArr4, int i5) {
        if (!checkContextVar(bArr2, b)) {
            throw new IllegalArgumentException("ctx");
        }
        Digest createDigest = createDigest();
        byte[] bArr5 = new byte[createDigest.getDigestSize()];
        createDigest.update(bArr, i2, 32);
        createDigest.doFinal(bArr5, 0);
        byte[] bArr6 = new byte[32];
        pruneScalar(bArr5, 0, bArr6);
        byte[] bArr7 = new byte[32];
        scalarMultBaseEncoded(bArr6, bArr7, 0);
        implSign(createDigest, bArr5, bArr6, bArr7, 0, bArr2, b, bArr3, i3, i4, bArr4, i5);
    }

    private static boolean isNeutralElementVar(int[] iArr, int[] iArr2, int[] iArr3) {
        return X25519Field.isZeroVar(iArr) && X25519Field.areEqualVar(iArr2, iArr3);
    }

    private static void pointAdd(PointExt pointExt, PointExt pointExt2) {
        int[] create = X25519Field.create();
        int[] create2 = X25519Field.create();
        int[] create3 = X25519Field.create();
        int[] create4 = X25519Field.create();
        int[] create5 = X25519Field.create();
        int[] create6 = X25519Field.create();
        int[] create7 = X25519Field.create();
        int[] create8 = X25519Field.create();
        X25519Field.apm(pointExt.f1505y, pointExt.f1504x, create2, create);
        X25519Field.apm(pointExt2.f1505y, pointExt2.f1504x, create4, create3);
        X25519Field.mul(create, create3, create);
        X25519Field.mul(create2, create4, create2);
        X25519Field.mul(pointExt.f1503t, pointExt2.f1503t, create3);
        X25519Field.mul(create3, C_d2, create3);
        X25519Field.mul(pointExt.f1506z, pointExt2.f1506z, create4);
        X25519Field.add(create4, create4, create4);
        X25519Field.apm(create2, create, create8, create5);
        X25519Field.apm(create4, create3, create7, create6);
        X25519Field.carry(create7);
        X25519Field.mul(create5, create6, pointExt2.f1504x);
        X25519Field.mul(create7, create8, pointExt2.f1505y);
        X25519Field.mul(create6, create7, pointExt2.f1506z);
        X25519Field.mul(create5, create8, pointExt2.f1503t);
    }

    private static void pointAddVar(boolean z2, PointExt pointExt, PointExt pointExt2, PointExt pointExt3) {
        int[] iArr;
        int[] iArr2;
        int[] iArr3;
        int[] iArr4;
        int[] create = X25519Field.create();
        int[] create2 = X25519Field.create();
        int[] create3 = X25519Field.create();
        int[] create4 = X25519Field.create();
        int[] create5 = X25519Field.create();
        int[] create6 = X25519Field.create();
        int[] create7 = X25519Field.create();
        int[] create8 = X25519Field.create();
        if (z2) {
            iArr2 = create3;
            iArr = create4;
            iArr4 = create6;
            iArr3 = create7;
        } else {
            iArr = create3;
            iArr2 = create4;
            iArr3 = create6;
            iArr4 = create7;
        }
        X25519Field.apm(pointExt.f1505y, pointExt.f1504x, create2, create);
        X25519Field.apm(pointExt2.f1505y, pointExt2.f1504x, iArr2, iArr);
        X25519Field.mul(create, create3, create);
        X25519Field.mul(create2, create4, create2);
        X25519Field.mul(pointExt.f1503t, pointExt2.f1503t, create3);
        X25519Field.mul(create3, C_d2, create3);
        X25519Field.mul(pointExt.f1506z, pointExt2.f1506z, create4);
        X25519Field.add(create4, create4, create4);
        X25519Field.apm(create2, create, create8, create5);
        X25519Field.apm(create4, create3, iArr4, iArr3);
        X25519Field.carry(iArr4);
        X25519Field.mul(create5, create6, pointExt3.f1504x);
        X25519Field.mul(create7, create8, pointExt3.f1505y);
        X25519Field.mul(create6, create7, pointExt3.f1506z);
        X25519Field.mul(create5, create8, pointExt3.f1503t);
    }

    private static PointExt pointCopy(PointAffine pointAffine) {
        PointExt pointExt = new PointExt();
        X25519Field.copy(pointAffine.f1501x, 0, pointExt.f1504x, 0);
        X25519Field.copy(pointAffine.f1502y, 0, pointExt.f1505y, 0);
        pointExtendXY(pointExt);
        return pointExt;
    }

    private static void pointExtendXY(PointExt pointExt) {
        X25519Field.one(pointExt.f1506z);
        X25519Field.mul(pointExt.f1504x, pointExt.f1505y, pointExt.f1503t);
    }

    private static void pointLookup(int[] iArr, int i2, PointExt pointExt) {
        int i3 = i2 * 40;
        X25519Field.copy(iArr, i3, pointExt.f1504x, 0);
        int i4 = i3 + 10;
        X25519Field.copy(iArr, i4, pointExt.f1505y, 0);
        int i5 = i4 + 10;
        X25519Field.copy(iArr, i5, pointExt.f1506z, 0);
        X25519Field.copy(iArr, i5 + 10, pointExt.f1503t, 0);
    }

    private static void pointSetNeutral(PointExt pointExt) {
        X25519Field.zero(pointExt.f1504x);
        X25519Field.one(pointExt.f1505y);
        X25519Field.one(pointExt.f1506z);
        X25519Field.zero(pointExt.f1503t);
    }

    public static void sign(byte[] bArr, int i2, byte[] bArr2, int i3, byte[] bArr3, int i4, int i5, byte[] bArr4, int i6) {
        implSign(bArr, i2, bArr2, i3, null, (byte) 0, bArr3, i4, i5, bArr4, i6);
    }

    public static void signPrehash(byte[] bArr, int i2, byte[] bArr2, int i3, byte[] bArr3, byte[] bArr4, int i4, byte[] bArr5, int i5) {
        implSign(bArr, i2, bArr2, i3, bArr3, (byte) 1, bArr4, i4, 64, bArr5, i5);
    }

    public static boolean verify(byte[] bArr, int i2, byte[] bArr2, int i3, byte[] bArr3, byte[] bArr4, int i4, int i5) {
        return implVerify(bArr, i2, bArr2, i3, bArr3, (byte) 0, bArr4, i4, i5);
    }

    public static boolean verifyPrehash(byte[] bArr, int i2, byte[] bArr2, int i3, byte[] bArr3, byte[] bArr4, int i4) {
        return implVerify(bArr, i2, bArr2, i3, bArr3, (byte) 1, bArr4, i4, 64);
    }

    private static void implSign(byte[] bArr, int i2, byte[] bArr2, int i3, byte[] bArr3, byte b, byte[] bArr4, int i4, int i5, byte[] bArr5, int i6) {
        if (!checkContextVar(bArr3, b)) {
            throw new IllegalArgumentException("ctx");
        }
        Digest createDigest = createDigest();
        byte[] bArr6 = new byte[createDigest.getDigestSize()];
        createDigest.update(bArr, i2, 32);
        createDigest.doFinal(bArr6, 0);
        byte[] bArr7 = new byte[32];
        pruneScalar(bArr6, 0, bArr7);
        implSign(createDigest, bArr6, bArr7, bArr2, i3, bArr3, b, bArr4, i4, i5, bArr5, i6);
    }

    private static PointExt pointCopy(PointExt pointExt) {
        PointExt pointExt2 = new PointExt();
        pointCopy(pointExt, pointExt2);
        return pointExt2;
    }

    private static void pointLookup(int[] iArr, int i2, int[] iArr2, PointExt pointExt) {
        int window4 = getWindow4(iArr, i2);
        int i3 = (window4 >>> 3) ^ 1;
        int i4 = (window4 ^ (-i3)) & 7;
        int i5 = 0;
        for (int i6 = 0; i6 < 8; i6++) {
            int i7 = ((i6 ^ i4) - 1) >> 31;
            X25519Field.cmov(i7, iArr2, i5, pointExt.f1504x, 0);
            int i8 = i5 + 10;
            X25519Field.cmov(i7, iArr2, i8, pointExt.f1505y, 0);
            int i9 = i8 + 10;
            X25519Field.cmov(i7, iArr2, i9, pointExt.f1506z, 0);
            int i10 = i9 + 10;
            X25519Field.cmov(i7, iArr2, i10, pointExt.f1503t, 0);
            i5 = i10 + 10;
        }
        X25519Field.cnegate(i3, pointExt.f1504x);
        X25519Field.cnegate(i3, pointExt.f1503t);
    }

    public static void sign(byte[] bArr, int i2, byte[] bArr2, int i3, byte[] bArr3, byte[] bArr4, int i4, int i5, byte[] bArr5, int i6) {
        implSign(bArr, i2, bArr2, i3, bArr3, (byte) 0, bArr4, i4, i5, bArr5, i6);
    }

    public static void signPrehash(byte[] bArr, int i2, byte[] bArr2, Digest digest, byte[] bArr3, int i3) {
        byte[] bArr4 = new byte[64];
        if (64 != digest.doFinal(bArr4, 0)) {
            throw new IllegalArgumentException("ph");
        }
        implSign(bArr, i2, bArr2, (byte) 1, bArr4, 0, 64, bArr3, i3);
    }

    private static void pointCopy(PointAffine pointAffine, PointAccum pointAccum) {
        X25519Field.copy(pointAffine.f1501x, 0, pointAccum.f1498x, 0);
        X25519Field.copy(pointAffine.f1502y, 0, pointAccum.f1499y, 0);
        pointExtendXY(pointAccum);
    }

    public static void sign(byte[] bArr, int i2, byte[] bArr2, byte[] bArr3, int i3, int i4, byte[] bArr4, int i5) {
        implSign(bArr, i2, bArr2, (byte) 0, bArr3, i3, i4, bArr4, i5);
    }

    public static void signPrehash(byte[] bArr, int i2, byte[] bArr2, byte[] bArr3, int i3, byte[] bArr4, int i4) {
        implSign(bArr, i2, bArr2, (byte) 1, bArr3, i3, 64, bArr4, i4);
    }

    private static void pointCopy(PointExt pointExt, PointExt pointExt2) {
        X25519Field.copy(pointExt.f1504x, 0, pointExt2.f1504x, 0);
        X25519Field.copy(pointExt.f1505y, 0, pointExt2.f1505y, 0);
        X25519Field.copy(pointExt.f1506z, 0, pointExt2.f1506z, 0);
        X25519Field.copy(pointExt.f1503t, 0, pointExt2.f1503t, 0);
    }
}

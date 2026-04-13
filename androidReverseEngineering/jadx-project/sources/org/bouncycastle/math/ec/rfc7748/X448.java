package org.bouncycastle.math.ec.rfc7748;

import android.sun.security.util.DerValue;
import java.security.SecureRandom;
import org.bouncycastle.math.ec.rfc8032.Ed448;
import org.bouncycastle.util.Arrays;

/* loaded from: classes.dex */
public abstract class X448 {
    private static final int C_A = 156326;
    private static final int C_A24 = 39082;
    public static final int POINT_SIZE = 56;
    public static final int SCALAR_SIZE = 56;

    /* renamed from: org.bouncycastle.math.ec.rfc7748.X448$F */
    public static class C0736F extends X448Field {
        private C0736F() {
        }
    }

    public static class Friend {
        private static final Friend INSTANCE = new Friend();

        private Friend() {
        }
    }

    public static boolean calculateAgreement(byte[] bArr, int i2, byte[] bArr2, int i3, byte[] bArr3, int i4) {
        scalarMult(bArr, i2, bArr2, i3, bArr3, i4);
        return !Arrays.areAllZeroes(bArr3, i4, 56);
    }

    private static int decode32(byte[] bArr, int i2) {
        int i3 = bArr[i2] & 255;
        int i4 = i2 + 1;
        int i5 = i3 | ((bArr[i4] & 255) << 8);
        int i6 = i4 + 1;
        return (bArr[i6 + 1] << DerValue.tag_GeneralizedTime) | i5 | ((bArr[i6] & 255) << 16);
    }

    private static void decodeScalar(byte[] bArr, int i2, int[] iArr) {
        for (int i3 = 0; i3 < 14; i3++) {
            iArr[i3] = decode32(bArr, (i3 * 4) + i2);
        }
        iArr[0] = iArr[0] & (-4);
        iArr[13] = iArr[13] | Integer.MIN_VALUE;
    }

    public static void generatePrivateKey(SecureRandom secureRandom, byte[] bArr) {
        secureRandom.nextBytes(bArr);
        bArr[0] = (byte) (bArr[0] & 252);
        bArr[55] = (byte) (bArr[55] | DerValue.TAG_CONTEXT);
    }

    public static void generatePublicKey(byte[] bArr, int i2, byte[] bArr2, int i3) {
        scalarMultBase(bArr, i2, bArr2, i3);
    }

    private static void pointDouble(int[] iArr, int[] iArr2) {
        int[] create = X448Field.create();
        int[] create2 = X448Field.create();
        X448Field.add(iArr, iArr2, create);
        X448Field.sub(iArr, iArr2, create2);
        X448Field.sqr(create, create);
        X448Field.sqr(create2, create2);
        X448Field.mul(create, create2, iArr);
        X448Field.sub(create, create2, create);
        X448Field.mul(create, C_A24, iArr2);
        X448Field.add(iArr2, create2, iArr2);
        X448Field.mul(iArr2, create, iArr2);
    }

    public static void precompute() {
        Ed448.precompute();
    }

    public static void scalarMult(byte[] bArr, int i2, byte[] bArr2, int i3, byte[] bArr3, int i4) {
        int[] iArr = new int[14];
        decodeScalar(bArr, i2, iArr);
        int[] create = X448Field.create();
        X448Field.decode(bArr2, i3, create);
        int[] create2 = X448Field.create();
        X448Field.copy(create, 0, create2, 0);
        int[] create3 = X448Field.create();
        create3[0] = 1;
        int[] create4 = X448Field.create();
        create4[0] = 1;
        int[] create5 = X448Field.create();
        int[] create6 = X448Field.create();
        int[] create7 = X448Field.create();
        int i5 = 447;
        int i6 = 1;
        while (true) {
            X448Field.add(create4, create5, create6);
            X448Field.sub(create4, create5, create4);
            X448Field.add(create2, create3, create5);
            X448Field.sub(create2, create3, create2);
            X448Field.mul(create6, create2, create6);
            X448Field.mul(create4, create5, create4);
            X448Field.sqr(create5, create5);
            X448Field.sqr(create2, create2);
            X448Field.sub(create5, create2, create7);
            X448Field.mul(create7, C_A24, create3);
            X448Field.add(create3, create2, create3);
            X448Field.mul(create3, create7, create3);
            X448Field.mul(create2, create5, create2);
            X448Field.sub(create6, create4, create5);
            X448Field.add(create6, create4, create4);
            X448Field.sqr(create4, create4);
            X448Field.sqr(create5, create5);
            X448Field.mul(create5, create, create5);
            i5--;
            int i7 = (iArr[i5 >>> 5] >>> (i5 & 31)) & 1;
            int i8 = i6 ^ i7;
            X448Field.cswap(i8, create2, create4);
            X448Field.cswap(i8, create3, create5);
            if (i5 < 2) {
                break;
            } else {
                i6 = i7;
            }
        }
        for (int i9 = 0; i9 < 2; i9++) {
            pointDouble(create2, create3);
        }
        X448Field.inv(create3, create3);
        X448Field.mul(create2, create3, create2);
        X448Field.normalize(create2);
        X448Field.encode(create2, bArr3, i4);
    }

    public static void scalarMultBase(byte[] bArr, int i2, byte[] bArr2, int i3) {
        int[] create = X448Field.create();
        int[] create2 = X448Field.create();
        Ed448.scalarMultBaseXY(Friend.INSTANCE, bArr, i2, create, create2);
        X448Field.inv(create, create);
        X448Field.mul(create, create2, create);
        X448Field.sqr(create, create);
        X448Field.normalize(create);
        X448Field.encode(create, bArr2, i3);
    }
}

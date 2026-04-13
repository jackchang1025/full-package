package org.bouncycastle.crypto.fpe;

import java.math.BigInteger;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.BigIntegers;
import org.bouncycastle.util.Pack;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
class SP80038G {
    protected static final int BLOCK_SIZE = 16;
    static final String FF1_DISABLED = "org.bouncycastle.fpe.disable_ff1";
    static final String FPE_DISABLED = "org.bouncycastle.fpe.disable";
    protected static final double LOG2 = Math.log(2.0d);
    protected static final double TWO_TO_96 = Math.pow(2.0d, 96.0d);

    public static BigInteger[] calculateModUV(BigInteger bigInteger, int i2, int i3) {
        BigInteger pow = bigInteger.pow(i2);
        BigInteger[] bigIntegerArr = {pow, pow};
        if (i3 != i2) {
            bigIntegerArr[1] = pow.multiply(bigInteger);
        }
        return bigIntegerArr;
    }

    public static byte[] calculateP_FF1(int i2, byte b, int i3, int i4) {
        byte[] bArr = {1, 2, 1, 0, (byte) (i2 >> 8), (byte) i2, 10, b, 0, 0, 0, 0, 0, 0, 0, 0};
        Pack.intToBigEndian(i3, bArr, 8);
        Pack.intToBigEndian(i4, bArr, 12);
        return bArr;
    }

    public static byte[] calculateTweak64_FF3_1(byte[] bArr) {
        return new byte[]{bArr[0], bArr[1], bArr[2], (byte) (bArr[3] & 240), bArr[4], bArr[5], bArr[6], (byte) (bArr[3] << 4)};
    }

    public static BigInteger calculateY_FF1(BlockCipher blockCipher, BigInteger bigInteger, byte[] bArr, int i2, int i3, int i4, byte[] bArr2, short[] sArr) {
        int length = bArr.length;
        byte[] asUnsignedByteArray = BigIntegers.asUnsignedByteArray(num(bigInteger, sArr));
        int i5 = ((-(length + i2 + 1)) & 15) + length;
        int i6 = i5 + 1 + i2;
        byte[] bArr3 = new byte[i6];
        System.arraycopy(bArr, 0, bArr3, 0, length);
        bArr3[i5] = (byte) i4;
        System.arraycopy(asUnsignedByteArray, 0, bArr3, i6 - asUnsignedByteArray.length, asUnsignedByteArray.length);
        byte[] prf = prf(blockCipher, Arrays.concatenate(bArr2, bArr3));
        if (i3 > 16) {
            int i7 = ((i3 + 16) - 1) / 16;
            byte[] bArr4 = new byte[i7 * 16];
            System.arraycopy(prf, 0, bArr4, 0, 16);
            byte[] bArr5 = new byte[4];
            for (int i8 = 1; i8 < i7; i8++) {
                int i9 = i8 * 16;
                System.arraycopy(prf, 0, bArr4, i9, 16);
                Pack.intToBigEndian(i8, bArr5, 0);
                xor(bArr5, 0, bArr4, (i9 + 16) - 4, 4);
                blockCipher.processBlock(bArr4, i9, bArr4, i9);
            }
            prf = bArr4;
        }
        return num(prf, 0, i3);
    }

    public static BigInteger calculateY_FF3(BlockCipher blockCipher, BigInteger bigInteger, byte[] bArr, int i2, int i3, short[] sArr) {
        byte[] bArr2 = new byte[16];
        Pack.intToBigEndian(i3, bArr2, 0);
        xor(bArr, i2, bArr2, 0, 4);
        byte[] asUnsignedByteArray = BigIntegers.asUnsignedByteArray(num(bigInteger, sArr));
        if (16 - asUnsignedByteArray.length < 4) {
            throw new IllegalStateException("input out of range");
        }
        System.arraycopy(asUnsignedByteArray, 0, bArr2, 16 - asUnsignedByteArray.length, asUnsignedByteArray.length);
        rev(bArr2);
        blockCipher.processBlock(bArr2, 0, bArr2, 0);
        rev(bArr2);
        return num(bArr2, 0, 16);
    }

    public static void checkArgs(BlockCipher blockCipher, boolean z2, int i2, byte[] bArr, int i3, int i4) {
        checkCipher(blockCipher);
        if (i2 < 2 || i2 > 256) {
            throw new IllegalArgumentException();
        }
        checkData(z2, i2, bArr, i3, i4);
    }

    public static void checkCipher(BlockCipher blockCipher) {
        if (16 != blockCipher.getBlockSize()) {
            throw new IllegalArgumentException();
        }
    }

    public static void checkData(boolean z2, int i2, byte[] bArr, int i3, int i4) {
        checkLength(z2, i2, i4);
        for (int i5 = 0; i5 < i4; i5++) {
            if ((bArr[i3 + i5] & 255) >= i2) {
                throw new IllegalArgumentException("input data outside of radix");
            }
        }
    }

    private static void checkLength(boolean z2, int i2, int i3) {
        int floor;
        if (i3 >= 2) {
            double d2 = i2;
            if (Math.pow(d2, i3) >= 1000000.0d) {
                if (!z2 && i3 > (floor = ((int) Math.floor(Math.log(TWO_TO_96) / Math.log(d2))) * 2)) {
                    throw new IllegalArgumentException(AbstractC0000a.m11g("maximum input length is ", floor));
                }
                return;
            }
        }
        throw new IllegalArgumentException("input too short");
    }

    public static short[] decFF1(BlockCipher blockCipher, int i2, byte[] bArr, int i3, int i4, int i5, short[] sArr, short[] sArr2) {
        int length = bArr.length;
        int ceil = (((int) Math.ceil((Math.log(i2) * i5) / LOG2)) + 7) / 8;
        int i6 = (((ceil + 3) / 4) * 4) + 4;
        byte[] calculateP_FF1 = calculateP_FF1(i2, (byte) i4, i3, length);
        BigInteger valueOf = BigInteger.valueOf(i2);
        BigInteger[] calculateModUV = calculateModUV(valueOf, i4, i5);
        short[] sArr3 = sArr;
        short[] sArr4 = sArr2;
        int i7 = i4;
        int i8 = 9;
        while (i8 >= 0) {
            short[] sArr5 = sArr4;
            sArr4 = sArr3;
            i7 = i3 - i7;
            str(valueOf, num(valueOf, sArr5).subtract(calculateY_FF1(blockCipher, valueOf, bArr, ceil, i6, i8, calculateP_FF1, sArr4)).mod(calculateModUV[i8 & 1]), i7, sArr5, 0);
            i8--;
            sArr3 = sArr5;
        }
        return Arrays.concatenate(sArr3, sArr4);
    }

    private static short[] decFF3_1(BlockCipher blockCipher, int i2, byte[] bArr, int i3, int i4, int i5, short[] sArr, short[] sArr2) {
        BigInteger valueOf = BigInteger.valueOf(i2);
        int i6 = i5;
        BigInteger[] calculateModUV = calculateModUV(valueOf, i4, i6);
        rev(sArr);
        rev(sArr2);
        short[] sArr3 = sArr;
        short[] sArr4 = sArr2;
        int i7 = 7;
        while (i7 >= 0) {
            int i8 = i3 - i6;
            int i9 = i7 & 1;
            str(valueOf, num(valueOf, sArr4).subtract(calculateY_FF3(blockCipher, valueOf, bArr, 4 - (i9 * 4), i7, sArr3)).mod(calculateModUV[1 - i9]), i8, sArr4, 0);
            i7--;
            i6 = i8;
            short[] sArr5 = sArr4;
            sArr4 = sArr3;
            sArr3 = sArr5;
        }
        rev(sArr3);
        rev(sArr4);
        return Arrays.concatenate(sArr3, sArr4);
    }

    public static byte[] decryptFF1(BlockCipher blockCipher, int i2, byte[] bArr, byte[] bArr2, int i3, int i4) {
        checkArgs(blockCipher, true, i2, bArr2, i3, i4);
        int i5 = i4 / 2;
        int i6 = i4 - i5;
        return toByte(decFF1(blockCipher, i2, bArr, i4, i5, i6, toShort(bArr2, i3, i5), toShort(bArr2, i3 + i5, i6)));
    }

    public static short[] decryptFF1w(BlockCipher blockCipher, int i2, byte[] bArr, short[] sArr, int i3, int i4) {
        checkArgs(blockCipher, true, i2, sArr, i3, i4);
        int i5 = i4 / 2;
        int i6 = i4 - i5;
        short[] sArr2 = new short[i5];
        short[] sArr3 = new short[i6];
        System.arraycopy(sArr, i3, sArr2, 0, i5);
        System.arraycopy(sArr, i3 + i5, sArr3, 0, i6);
        return decFF1(blockCipher, i2, bArr, i4, i5, i6, sArr2, sArr3);
    }

    public static byte[] decryptFF3(BlockCipher blockCipher, int i2, byte[] bArr, byte[] bArr2, int i3, int i4) {
        checkArgs(blockCipher, false, i2, bArr2, i3, i4);
        if (bArr.length == 8) {
            return implDecryptFF3(blockCipher, i2, bArr, bArr2, i3, i4);
        }
        throw new IllegalArgumentException();
    }

    public static byte[] decryptFF3_1(BlockCipher blockCipher, int i2, byte[] bArr, byte[] bArr2, int i3, int i4) {
        checkArgs(blockCipher, false, i2, bArr2, i3, i4);
        if (bArr.length == 7) {
            return implDecryptFF3(blockCipher, i2, calculateTweak64_FF3_1(bArr), bArr2, i3, i4);
        }
        throw new IllegalArgumentException("tweak should be 56 bits");
    }

    public static short[] decryptFF3_1w(BlockCipher blockCipher, int i2, byte[] bArr, short[] sArr, int i3, int i4) {
        checkArgs(blockCipher, false, i2, sArr, i3, i4);
        if (bArr.length == 7) {
            return implDecryptFF3w(blockCipher, i2, calculateTweak64_FF3_1(bArr), sArr, i3, i4);
        }
        throw new IllegalArgumentException("tweak should be 56 bits");
    }

    private static short[] encFF1(BlockCipher blockCipher, int i2, byte[] bArr, int i3, int i4, int i5, short[] sArr, short[] sArr2) {
        int length = bArr.length;
        int ceil = (((int) Math.ceil((Math.log(i2) * i5) / LOG2)) + 7) / 8;
        int i6 = (((ceil + 3) / 4) * 4) + 4;
        byte[] calculateP_FF1 = calculateP_FF1(i2, (byte) i4, i3, length);
        BigInteger valueOf = BigInteger.valueOf(i2);
        BigInteger[] calculateModUV = calculateModUV(valueOf, i4, i5);
        short[] sArr3 = sArr;
        short[] sArr4 = sArr2;
        int i7 = i5;
        int i8 = 0;
        while (i8 < 10) {
            int i9 = i8;
            short[] sArr5 = sArr3;
            sArr3 = sArr4;
            int i10 = i3 - i7;
            str(valueOf, num(valueOf, sArr5).add(calculateY_FF1(blockCipher, valueOf, bArr, ceil, i6, i8, calculateP_FF1, sArr3)).mod(calculateModUV[i9 & 1]), i10, sArr5, 0);
            i8 = i9 + 1;
            i7 = i10;
            sArr4 = sArr5;
        }
        return Arrays.concatenate(sArr3, sArr4);
    }

    private static short[] encFF3_1(BlockCipher blockCipher, int i2, byte[] bArr, int i3, int i4, int i5, short[] sArr, short[] sArr2) {
        BigInteger valueOf = BigInteger.valueOf(i2);
        int i6 = i4;
        BigInteger[] calculateModUV = calculateModUV(valueOf, i6, i5);
        rev(sArr);
        rev(sArr2);
        short[] sArr3 = sArr;
        short[] sArr4 = sArr2;
        int i7 = 0;
        while (i7 < 8) {
            i6 = i3 - i6;
            int i8 = i7 & 1;
            str(valueOf, num(valueOf, sArr3).add(calculateY_FF3(blockCipher, valueOf, bArr, 4 - (i8 * 4), i7, sArr4)).mod(calculateModUV[1 - i8]), i6, sArr3, 0);
            i7++;
            short[] sArr5 = sArr4;
            sArr4 = sArr3;
            sArr3 = sArr5;
        }
        rev(sArr3);
        rev(sArr4);
        return Arrays.concatenate(sArr3, sArr4);
    }

    public static byte[] encryptFF1(BlockCipher blockCipher, int i2, byte[] bArr, byte[] bArr2, int i3, int i4) {
        checkArgs(blockCipher, true, i2, bArr2, i3, i4);
        int i5 = i4 / 2;
        int i6 = i4 - i5;
        return toByte(encFF1(blockCipher, i2, bArr, i4, i5, i6, toShort(bArr2, i3, i5), toShort(bArr2, i3 + i5, i6)));
    }

    public static short[] encryptFF1w(BlockCipher blockCipher, int i2, byte[] bArr, short[] sArr, int i3, int i4) {
        checkArgs(blockCipher, true, i2, sArr, i3, i4);
        int i5 = i4 / 2;
        int i6 = i4 - i5;
        short[] sArr2 = new short[i5];
        short[] sArr3 = new short[i6];
        System.arraycopy(sArr, i3, sArr2, 0, i5);
        System.arraycopy(sArr, i3 + i5, sArr3, 0, i6);
        return encFF1(blockCipher, i2, bArr, i4, i5, i6, sArr2, sArr3);
    }

    public static byte[] encryptFF3(BlockCipher blockCipher, int i2, byte[] bArr, byte[] bArr2, int i3, int i4) {
        checkArgs(blockCipher, false, i2, bArr2, i3, i4);
        if (bArr.length == 8) {
            return implEncryptFF3(blockCipher, i2, bArr, bArr2, i3, i4);
        }
        throw new IllegalArgumentException();
    }

    public static byte[] encryptFF3_1(BlockCipher blockCipher, int i2, byte[] bArr, byte[] bArr2, int i3, int i4) {
        checkArgs(blockCipher, false, i2, bArr2, i3, i4);
        if (bArr.length == 7) {
            return encryptFF3(blockCipher, i2, calculateTweak64_FF3_1(bArr), bArr2, i3, i4);
        }
        throw new IllegalArgumentException("tweak should be 56 bits");
    }

    public static short[] encryptFF3_1w(BlockCipher blockCipher, int i2, byte[] bArr, short[] sArr, int i3, int i4) {
        checkArgs(blockCipher, false, i2, sArr, i3, i4);
        if (bArr.length == 7) {
            return encryptFF3w(blockCipher, i2, calculateTweak64_FF3_1(bArr), sArr, i3, i4);
        }
        throw new IllegalArgumentException("tweak should be 56 bits");
    }

    public static short[] encryptFF3w(BlockCipher blockCipher, int i2, byte[] bArr, short[] sArr, int i3, int i4) {
        checkArgs(blockCipher, false, i2, sArr, i3, i4);
        if (bArr.length == 8) {
            return implEncryptFF3w(blockCipher, i2, bArr, sArr, i3, i4);
        }
        throw new IllegalArgumentException();
    }

    public static byte[] implDecryptFF3(BlockCipher blockCipher, int i2, byte[] bArr, byte[] bArr2, int i3, int i4) {
        int i5 = i4 / 2;
        int i6 = i4 - i5;
        return toByte(decFF3_1(blockCipher, i2, bArr, i4, i5, i6, toShort(bArr2, i3, i6), toShort(bArr2, i3 + i6, i5)));
    }

    public static short[] implDecryptFF3w(BlockCipher blockCipher, int i2, byte[] bArr, short[] sArr, int i3, int i4) {
        int i5 = i4 / 2;
        int i6 = i4 - i5;
        short[] sArr2 = new short[i6];
        short[] sArr3 = new short[i5];
        System.arraycopy(sArr, i3, sArr2, 0, i6);
        System.arraycopy(sArr, i3 + i6, sArr3, 0, i5);
        return decFF3_1(blockCipher, i2, bArr, i4, i5, i6, sArr2, sArr3);
    }

    public static byte[] implEncryptFF3(BlockCipher blockCipher, int i2, byte[] bArr, byte[] bArr2, int i3, int i4) {
        int i5 = i4 / 2;
        int i6 = i4 - i5;
        return toByte(encFF3_1(blockCipher, i2, bArr, i4, i5, i6, toShort(bArr2, i3, i6), toShort(bArr2, i3 + i6, i5)));
    }

    public static short[] implEncryptFF3w(BlockCipher blockCipher, int i2, byte[] bArr, short[] sArr, int i3, int i4) {
        int i5 = i4 / 2;
        int i6 = i4 - i5;
        short[] sArr2 = new short[i6];
        short[] sArr3 = new short[i5];
        System.arraycopy(sArr, i3, sArr2, 0, i6);
        System.arraycopy(sArr, i3 + i6, sArr3, 0, i5);
        return encFF3_1(blockCipher, i2, bArr, i4, i5, i6, sArr2, sArr3);
    }

    public static BigInteger num(BigInteger bigInteger, short[] sArr) {
        BigInteger bigInteger2 = BigIntegers.ZERO;
        for (short s2 : sArr) {
            bigInteger2 = bigInteger2.multiply(bigInteger).add(BigInteger.valueOf(s2 & 65535));
        }
        return bigInteger2;
    }

    public static byte[] prf(BlockCipher blockCipher, byte[] bArr) {
        if (bArr.length % 16 != 0) {
            throw new IllegalArgumentException();
        }
        int length = bArr.length / 16;
        byte[] bArr2 = new byte[16];
        for (int i2 = 0; i2 < length; i2++) {
            xor(bArr, i2 * 16, bArr2, 0, 16);
            blockCipher.processBlock(bArr2, 0, bArr2, 0);
        }
        return bArr2;
    }

    public static void rev(byte[] bArr) {
        int length = bArr.length / 2;
        int length2 = bArr.length - 1;
        for (int i2 = 0; i2 < length; i2++) {
            byte b = bArr[i2];
            int i3 = length2 - i2;
            bArr[i2] = bArr[i3];
            bArr[i3] = b;
        }
    }

    public static void str(BigInteger bigInteger, BigInteger bigInteger2, int i2, short[] sArr, int i3) {
        if (bigInteger2.signum() < 0) {
            throw new IllegalArgumentException();
        }
        for (int i4 = 1; i4 <= i2; i4++) {
            BigInteger[] divideAndRemainder = bigInteger2.divideAndRemainder(bigInteger);
            sArr[(i3 + i2) - i4] = (short) divideAndRemainder[1].intValue();
            bigInteger2 = divideAndRemainder[0];
        }
        if (bigInteger2.signum() != 0) {
            throw new IllegalArgumentException();
        }
    }

    private static byte[] toByte(short[] sArr) {
        int length = sArr.length;
        byte[] bArr = new byte[length];
        for (int i2 = 0; i2 != length; i2++) {
            bArr[i2] = (byte) sArr[i2];
        }
        return bArr;
    }

    private static short[] toShort(byte[] bArr, int i2, int i3) {
        short[] sArr = new short[i3];
        for (int i4 = 0; i4 != i3; i4++) {
            sArr[i4] = (short) (bArr[i2 + i4] & 255);
        }
        return sArr;
    }

    public static void xor(byte[] bArr, int i2, byte[] bArr2, int i3, int i4) {
        for (int i5 = 0; i5 < i4; i5++) {
            int i6 = i3 + i5;
            bArr2[i6] = (byte) (bArr2[i6] ^ bArr[i2 + i5]);
        }
    }

    public static void checkArgs(BlockCipher blockCipher, boolean z2, int i2, short[] sArr, int i3, int i4) {
        checkCipher(blockCipher);
        if (i2 < 2 || i2 > 65536) {
            throw new IllegalArgumentException();
        }
        checkData(z2, i2, sArr, i3, i4);
    }

    public static void checkData(boolean z2, int i2, short[] sArr, int i3, int i4) {
        checkLength(z2, i2, i4);
        for (int i5 = 0; i5 < i4; i5++) {
            if ((sArr[i3 + i5] & 65535) >= i2) {
                throw new IllegalArgumentException("input data outside of radix");
            }
        }
    }

    public static BigInteger num(byte[] bArr, int i2, int i3) {
        return new BigInteger(1, Arrays.copyOfRange(bArr, i2, i3 + i2));
    }

    public static void rev(short[] sArr) {
        int length = sArr.length / 2;
        int length2 = sArr.length - 1;
        for (int i2 = 0; i2 < length; i2++) {
            short s2 = sArr[i2];
            int i3 = length2 - i2;
            sArr[i2] = sArr[i3];
            sArr[i3] = s2;
        }
    }
}

package org.bouncycastle.util;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.bouncycastle.math.raw.Mod;
import org.bouncycastle.math.raw.Nat;

/* loaded from: classes.dex */
public final class BigIntegers {
    private static final int MAX_ITERATIONS = 1000;
    public static final BigInteger ZERO = BigInteger.valueOf(0);
    public static final BigInteger ONE = BigInteger.valueOf(1);
    public static final BigInteger TWO = BigInteger.valueOf(2);
    private static final BigInteger THREE = BigInteger.valueOf(3);
    private static final BigInteger SMALL_PRIMES_PRODUCT = new BigInteger("8138e8a0fcf3a4e84a771d40fd305d7f4aa59306d7251de54d98af8fe95729a1f73d893fa424cd2edc8636a6c3285e022b0e3866a565ae8108eed8591cd4fe8d2ce86165a978d719ebf647f362d33fca29cd179fb42401cbaf3df0c614056f9c8f3cfd51e474afb6bc6974f78db8aba8e9e517fded658591ab7502bd41849462f", 16);
    private static final int MAX_SMALL = BigInteger.valueOf(743).bitLength();

    /* JADX WARN: Code restructure failed: missing block: B:9:0x0012, code lost:
    
        if (r3.length != 1) goto L12;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void asUnsignedByteArray(BigInteger bigInteger, byte[] bArr, int i2, int i3) {
        int i4;
        byte[] byteArray = bigInteger.toByteArray();
        if (byteArray.length == i3) {
            System.arraycopy(byteArray, 0, bArr, i2, i3);
            return;
        }
        if (byteArray[0] == 0) {
            i4 = 1;
        }
        i4 = 0;
        int length = byteArray.length - i4;
        if (length > i3) {
            throw new IllegalArgumentException("standard length exceeded for value");
        }
        int i5 = (i3 - length) + i2;
        Arrays.fill(bArr, i2, i5, (byte) 0);
        System.arraycopy(byteArray, i4, bArr, i5, length);
    }

    public static byte byteValueExact(BigInteger bigInteger) {
        if (bigInteger.bitLength() <= 7) {
            return bigInteger.byteValue();
        }
        throw new ArithmeticException("BigInteger out of int range");
    }

    private static byte[] createRandom(int i2, SecureRandom secureRandom) {
        if (i2 < 1) {
            throw new IllegalArgumentException("bitLength must be at least 1");
        }
        int i3 = (i2 + 7) / 8;
        byte[] bArr = new byte[i3];
        secureRandom.nextBytes(bArr);
        bArr[0] = (byte) (bArr[0] & ((byte) (255 >>> ((i3 * 8) - i2))));
        return bArr;
    }

    public static BigInteger createRandomBigInteger(int i2, SecureRandom secureRandom) {
        return new BigInteger(1, createRandom(i2, secureRandom));
    }

    public static BigInteger createRandomInRange(BigInteger bigInteger, BigInteger bigInteger2, SecureRandom secureRandom) {
        BigInteger createRandomBigInteger;
        int compareTo = bigInteger.compareTo(bigInteger2);
        if (compareTo >= 0) {
            if (compareTo <= 0) {
                return bigInteger;
            }
            throw new IllegalArgumentException("'min' may not be greater than 'max'");
        }
        if (bigInteger.bitLength() > bigInteger2.bitLength() / 2) {
            createRandomBigInteger = createRandomInRange(ZERO, bigInteger2.subtract(bigInteger), secureRandom);
        } else {
            for (int i2 = 0; i2 < 1000; i2++) {
                BigInteger createRandomBigInteger2 = createRandomBigInteger(bigInteger2.bitLength(), secureRandom);
                if (createRandomBigInteger2.compareTo(bigInteger) >= 0 && createRandomBigInteger2.compareTo(bigInteger2) <= 0) {
                    return createRandomBigInteger2;
                }
            }
            createRandomBigInteger = createRandomBigInteger(bigInteger2.subtract(bigInteger).bitLength() - 1, secureRandom);
        }
        return createRandomBigInteger.add(bigInteger);
    }

    public static BigInteger createRandomPrime(int i2, int i3, SecureRandom secureRandom) {
        BigInteger bigInteger;
        if (i2 < 2) {
            throw new IllegalArgumentException("bitLength < 2");
        }
        if (i2 == 2) {
            return secureRandom.nextInt() < 0 ? TWO : THREE;
        }
        do {
            byte[] createRandom = createRandom(i2, secureRandom);
            createRandom[0] = (byte) (((byte) (1 << (7 - ((createRandom.length * 8) - i2)))) | createRandom[0]);
            int length = createRandom.length - 1;
            createRandom[length] = (byte) (createRandom[length] | 1);
            bigInteger = new BigInteger(1, createRandom);
            if (i2 > MAX_SMALL) {
                while (!bigInteger.gcd(SMALL_PRIMES_PRODUCT).equals(ONE)) {
                    bigInteger = bigInteger.add(TWO);
                }
            }
        } while (!bigInteger.isProbablePrime(i3));
        return bigInteger;
    }

    public static BigInteger fromUnsignedByteArray(byte[] bArr) {
        return new BigInteger(1, bArr);
    }

    public static int getUnsignedByteLength(BigInteger bigInteger) {
        if (bigInteger.equals(ZERO)) {
            return 1;
        }
        return (bigInteger.bitLength() + 7) / 8;
    }

    public static int intValueExact(BigInteger bigInteger) {
        if (bigInteger.bitLength() <= 31) {
            return bigInteger.intValue();
        }
        throw new ArithmeticException("BigInteger out of int range");
    }

    public static long longValueExact(BigInteger bigInteger) {
        if (bigInteger.bitLength() <= 63) {
            return bigInteger.longValue();
        }
        throw new ArithmeticException("BigInteger out of long range");
    }

    public static BigInteger modOddInverse(BigInteger bigInteger, BigInteger bigInteger2) {
        if (!bigInteger.testBit(0)) {
            throw new IllegalArgumentException("'M' must be odd");
        }
        if (bigInteger.signum() != 1) {
            throw new ArithmeticException("BigInteger: modulus not positive");
        }
        if (bigInteger2.signum() < 0 || bigInteger2.compareTo(bigInteger) >= 0) {
            bigInteger2 = bigInteger2.mod(bigInteger);
        }
        int bitLength = bigInteger.bitLength();
        int[] fromBigInteger = Nat.fromBigInteger(bitLength, bigInteger);
        int[] fromBigInteger2 = Nat.fromBigInteger(bitLength, bigInteger2);
        int length = fromBigInteger.length;
        int[] create = Nat.create(length);
        if (Mod.modOddInverse(fromBigInteger, fromBigInteger2, create) != 0) {
            return Nat.toBigInteger(length, create);
        }
        throw new ArithmeticException("BigInteger not invertible.");
    }

    public static BigInteger modOddInverseVar(BigInteger bigInteger, BigInteger bigInteger2) {
        if (!bigInteger.testBit(0)) {
            throw new IllegalArgumentException("'M' must be odd");
        }
        if (bigInteger.signum() != 1) {
            throw new ArithmeticException("BigInteger: modulus not positive");
        }
        BigInteger bigInteger3 = ONE;
        if (bigInteger.equals(bigInteger3)) {
            return ZERO;
        }
        if (bigInteger2.signum() < 0 || bigInteger2.compareTo(bigInteger) >= 0) {
            bigInteger2 = bigInteger2.mod(bigInteger);
        }
        if (bigInteger2.equals(bigInteger3)) {
            return bigInteger3;
        }
        int bitLength = bigInteger.bitLength();
        int[] fromBigInteger = Nat.fromBigInteger(bitLength, bigInteger);
        int[] fromBigInteger2 = Nat.fromBigInteger(bitLength, bigInteger2);
        int length = fromBigInteger.length;
        int[] create = Nat.create(length);
        if (Mod.modOddInverseVar(fromBigInteger, fromBigInteger2, create)) {
            return Nat.toBigInteger(length, create);
        }
        throw new ArithmeticException("BigInteger not invertible.");
    }

    public static short shortValueExact(BigInteger bigInteger) {
        if (bigInteger.bitLength() <= 15) {
            return bigInteger.shortValue();
        }
        throw new ArithmeticException("BigInteger out of int range");
    }

    public static byte[] asUnsignedByteArray(int i2, BigInteger bigInteger) {
        byte[] byteArray = bigInteger.toByteArray();
        if (byteArray.length == i2) {
            return byteArray;
        }
        int i3 = 0;
        if (byteArray[0] == 0 && byteArray.length != 1) {
            i3 = 1;
        }
        int length = byteArray.length - i3;
        if (length > i2) {
            throw new IllegalArgumentException("standard length exceeded for value");
        }
        byte[] bArr = new byte[i2];
        System.arraycopy(byteArray, i3, bArr, i2 - length, length);
        return bArr;
    }

    public static BigInteger fromUnsignedByteArray(byte[] bArr, int i2, int i3) {
        if (i2 != 0 || i3 != bArr.length) {
            byte[] bArr2 = new byte[i3];
            System.arraycopy(bArr, i2, bArr2, 0, i3);
            bArr = bArr2;
        }
        return new BigInteger(1, bArr);
    }

    public static byte[] asUnsignedByteArray(BigInteger bigInteger) {
        byte[] byteArray = bigInteger.toByteArray();
        if (byteArray[0] != 0 || byteArray.length == 1) {
            return byteArray;
        }
        int length = byteArray.length - 1;
        byte[] bArr = new byte[length];
        System.arraycopy(byteArray, 1, bArr, 0, length);
        return bArr;
    }
}

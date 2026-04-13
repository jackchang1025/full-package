package org.bouncycastle.crypto.generators;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.bouncycastle.math.ec.WNafUtil;
import org.bouncycastle.util.BigIntegers;

/* loaded from: classes.dex */
class DHParametersHelper {
    private static final BigInteger ONE = BigInteger.valueOf(1);
    private static final BigInteger TWO = BigInteger.valueOf(2);

    public static BigInteger[] generateSafePrimes(int i2, int i3, SecureRandom secureRandom) {
        int i4 = i2 - 1;
        int i5 = i2 >>> 2;
        while (true) {
            BigInteger createRandomPrime = BigIntegers.createRandomPrime(i4, 2, secureRandom);
            BigInteger add = createRandomPrime.shiftLeft(1).add(ONE);
            if (add.isProbablePrime(i3) && (i3 <= 2 || createRandomPrime.isProbablePrime(i3 - 2))) {
                if (WNafUtil.getNafWeight(add) >= i5) {
                    return new BigInteger[]{add, createRandomPrime};
                }
            }
        }
    }

    public static BigInteger selectGenerator(BigInteger bigInteger, BigInteger bigInteger2, SecureRandom secureRandom) {
        BigInteger modPow;
        BigInteger subtract = bigInteger.subtract(TWO);
        do {
            BigInteger bigInteger3 = TWO;
            modPow = BigIntegers.createRandomInRange(bigInteger3, subtract, secureRandom).modPow(bigInteger3, bigInteger);
        } while (modPow.equals(ONE));
        return modPow;
    }
}

package org.bouncycastle.crypto.generators;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.bouncycastle.crypto.params.DHParameters;
import org.bouncycastle.math.ec.WNafUtil;
import org.bouncycastle.util.BigIntegers;

/* loaded from: classes.dex */
class DHKeyGeneratorHelper {
    static final DHKeyGeneratorHelper INSTANCE = new DHKeyGeneratorHelper();
    private static final BigInteger ONE = BigInteger.valueOf(1);
    private static final BigInteger TWO = BigInteger.valueOf(2);

    private DHKeyGeneratorHelper() {
    }

    public BigInteger calculatePrivate(DHParameters dHParameters, SecureRandom secureRandom) {
        BigInteger createRandomInRange;
        BigInteger bit;
        int l2 = dHParameters.getL();
        if (l2 != 0) {
            int i2 = l2 >>> 2;
            do {
                bit = BigIntegers.createRandomBigInteger(l2, secureRandom).setBit(l2 - 1);
            } while (WNafUtil.getNafWeight(bit) < i2);
            return bit;
        }
        BigInteger bigInteger = TWO;
        int m2 = dHParameters.getM();
        BigInteger shiftLeft = m2 != 0 ? ONE.shiftLeft(m2 - 1) : bigInteger;
        BigInteger q = dHParameters.getQ();
        if (q == null) {
            q = dHParameters.getP();
        }
        BigInteger subtract = q.subtract(bigInteger);
        int bitLength = subtract.bitLength() >>> 2;
        do {
            createRandomInRange = BigIntegers.createRandomInRange(shiftLeft, subtract, secureRandom);
        } while (WNafUtil.getNafWeight(createRandomInRange) < bitLength);
        return createRandomInRange;
    }

    public BigInteger calculatePublic(DHParameters dHParameters, BigInteger bigInteger) {
        return dHParameters.getG().modPow(bigInteger, dHParameters.getP());
    }
}

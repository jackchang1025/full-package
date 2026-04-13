package org.bouncycastle.crypto.generators;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.params.CramerShoupParameters;
import org.bouncycastle.crypto.params.DHParameters;
import org.bouncycastle.util.BigIntegers;

/* loaded from: classes.dex */
public class CramerShoupParametersGenerator {
    private static final BigInteger ONE = BigInteger.valueOf(1);
    private int certainty;
    private SecureRandom random;
    private int size;

    public static class ParametersHelper {
        private static final BigInteger TWO = BigInteger.valueOf(2);

        private ParametersHelper() {
        }

        public static BigInteger[] generateSafePrimes(int i2, int i3, SecureRandom secureRandom) {
            BigInteger createRandomPrime;
            BigInteger add;
            int i4 = i2 - 1;
            while (true) {
                createRandomPrime = BigIntegers.createRandomPrime(i4, 2, secureRandom);
                add = createRandomPrime.shiftLeft(1).add(CramerShoupParametersGenerator.ONE);
                if (!add.isProbablePrime(i3) || (i3 > 2 && !createRandomPrime.isProbablePrime(i3))) {
                }
            }
            return new BigInteger[]{add, createRandomPrime};
        }

        public static BigInteger selectGenerator(BigInteger bigInteger, SecureRandom secureRandom) {
            BigInteger modPow;
            BigInteger subtract = bigInteger.subtract(TWO);
            do {
                BigInteger bigInteger2 = TWO;
                modPow = BigIntegers.createRandomInRange(bigInteger2, subtract, secureRandom).modPow(bigInteger2, bigInteger);
            } while (modPow.equals(CramerShoupParametersGenerator.ONE));
            return modPow;
        }
    }

    public CramerShoupParameters generateParameters() {
        BigInteger selectGenerator;
        BigInteger bigInteger = ParametersHelper.generateSafePrimes(this.size, this.certainty, this.random)[1];
        BigInteger selectGenerator2 = ParametersHelper.selectGenerator(bigInteger, this.random);
        do {
            selectGenerator = ParametersHelper.selectGenerator(bigInteger, this.random);
        } while (selectGenerator2.equals(selectGenerator));
        return new CramerShoupParameters(bigInteger, selectGenerator2, selectGenerator, new SHA256Digest());
    }

    public void init(int i2, int i3, SecureRandom secureRandom) {
        this.size = i2;
        this.certainty = i3;
        this.random = secureRandom;
    }

    public CramerShoupParameters generateParameters(DHParameters dHParameters) {
        BigInteger selectGenerator;
        BigInteger p2 = dHParameters.getP();
        BigInteger g2 = dHParameters.getG();
        do {
            selectGenerator = ParametersHelper.selectGenerator(p2, this.random);
        } while (g2.equals(selectGenerator));
        return new CramerShoupParameters(p2, g2, selectGenerator, new SHA256Digest());
    }
}
